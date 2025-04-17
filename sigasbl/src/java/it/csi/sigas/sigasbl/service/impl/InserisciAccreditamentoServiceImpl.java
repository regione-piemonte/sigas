/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.ErrorMessages;
import it.csi.sigas.sigasbl.common.exception.BadRequestException;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.common.utils.Utilities;
import it.csi.sigas.sigasbl.integration.specification.SigasDichiaranteSpecification;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasCMessaggi;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;
import it.csi.sigas.sigasbl.model.entity.SigasLegaleRappresent;
import it.csi.sigas.sigasbl.model.entity.SigasOperatore;
import it.csi.sigas.sigasbl.model.entity.SigasUtenteProvvisorio;
import it.csi.sigas.sigasbl.model.mapper.entity.LegaleRappresentanteEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.OperatoreEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCMessaggiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichiaranteRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasLegaleRappresentRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasOperatoreRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasUtenteProvvisorioRepository;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaAccreditamentoRequest;
import it.csi.sigas.sigasbl.service.IDichiaranteService;
import it.csi.sigas.sigasbl.service.IInserisciAccrediatamentoService;
import it.csi.sigas.sigasbl.service.IModificaAccreditamentoService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;
import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.LegaleRappresentanteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.OperatoreVO;


@Service
public class InserisciAccreditamentoServiceImpl implements IInserisciAccrediatamentoService {


	@Autowired
	private SigasDichiaranteRepository sigasDichiaranteRepository;

	@Autowired
	private SigasLegaleRappresentRepository sigasLegaleRappresentRepository;

	@Autowired
	private SigasOperatoreRepository sigasOperatoreRepository;
	
	
	@Autowired
	private SigasUtenteProvvisorioRepository sigasUtenteProvvisorioRepository;

	@Autowired
	LegaleRappresentanteEntityMapper legaleRappresentanteEntityMapper;

	@Autowired
	private OperatoreEntityMapper operatoreEntityMapper;

	@Autowired
	private IModificaAccreditamentoService modificaAccreditamentoService;

	@Autowired
	IDichiaranteService dichiaranteService;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;
	
	@Autowired
	private SigasCMessaggiRepository sigasCMessaggiRepository;
	
	public static final String ANNULLATA = "ANNULLATA"; 
	

	@Override
	@Transactional
	public void confermaAccreditamento(ConfermaAccreditamentoRequest confermaAccreditamento) {
				
		
		SigasDichiarante sigasDichiarante = dichiaranteService.confermaDichiarante(confermaAccreditamento.getDichiarante());
		confermaLegaleRappresentante(confermaAccreditamento.getLegaleRappresentante(), sigasDichiarante);
		confermaOperatore(confermaAccreditamento.getOperatore(), sigasDichiarante);
		SigasUtenteProvvisorio utenteProvvisorio = confermaUtenteProvvisorio(confermaAccreditamento,sigasDichiarante);
		
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SIGAS FO - INSERIMENTO DOMANDA ACCREDITAMENTO", "sigas_utente_provvisorio",String.valueOf(utenteProvvisorio.getIdUtenteProvv()));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
		
		SigasCParametro linkInformativaTrattamentoDatiPers = sigasCParametroRepository.findByDescParametro("linkInformativaTrattamentoDatiPers");
		SigasCParametro mailSmtpHost = sigasCParametroRepository.findByDescParametro("mailSmtpHost");
		SigasCParametro mailSmtpPort = sigasCParametroRepository.findByDescParametro("mailSmtpPort");
		
		
		SigasCParametro mittenteMailSigasFO = sigasCParametroRepository.findByDescParametro("mittenteMailDomandaAccreditamento");
		SigasCParametro oggettoMailSigasFO = sigasCParametroRepository.findByDescParametro("oggettoMailDomandaAccreditamento");
		SigasCParametro corpoMailSigasFO = sigasCParametroRepository.findByDescParametro("corpoMailDomandaAccreditamento");		
		String corpoMailSigasFOWithReplace = corpoMailSigasFO.getValoreString().replaceAll("<<NOME>>", confermaAccreditamento.getOperatore().getNome()).
				replaceAll("<<COGNOME>>", confermaAccreditamento.getOperatore().getCognome()).
				replaceAll("<<CODICE_RICHIESTA>>", utenteProvvisorio.getIdPratica()).
				replaceAll("<<link>>", linkInformativaTrattamentoDatiPers.getValoreString());
		
		
		SigasCParametro mittenteMailValutazioneAccreditamento = sigasCParametroRepository.findByDescParametro("mittenteMailValutazioneAccreditamento");
		SigasCParametro destinatarioMailValutazioneAccreditamento = sigasCParametroRepository.findByDescParametro("destinatarioMailValutazioneAccreditamento");
		SigasCParametro oggettoMailValutazioneAccreditamento = sigasCParametroRepository.findByDescParametro("oggettoMailValutazioneAccreditamento");
		SigasCParametro corpoMailValutazioneAccreditamento = sigasCParametroRepository.findByDescParametro("corpoMailValutazioneAccreditamento");		
		String corpoMailValutazioneAccreditamentoWithReplace = corpoMailValutazioneAccreditamento.getValoreString().replaceAll("<<NOME>>", confermaAccreditamento.getOperatore().getNome()).
				replaceAll("<<COGNOME>>", confermaAccreditamento.getOperatore().getCognome()).
				replaceAll("<<CF_OPERATORE_FO>>", confermaAccreditamento.getOperatore().getCodiceFiscale()).
				replaceAll("<<CODICE_RICHIESTA>>", utenteProvvisorio.getIdPratica()).
				replaceAll("<<DENOMINAZIONE_SOC>>", confermaAccreditamento.getDichiarante().getDenominazione()).
				replaceAll("<<CODICE_SOCIETA>>", confermaAccreditamento.getDichiarante().getCodiceAzienda()).
				replaceAll("<<link>>", linkInformativaTrattamentoDatiPers.getValoreString());
		
		
		try {
			Utilities.sendMail(confermaAccreditamento.getOperatore().getEmail(), mittenteMailSigasFO.getValoreString(), oggettoMailSigasFO.getValoreString(),corpoMailSigasFOWithReplace,mailSmtpHost.getValoreString(), mailSmtpPort.getValoreString());
			Utilities.sendMail(destinatarioMailValutazioneAccreditamento.getValoreString(), 
									mittenteMailValutazioneAccreditamento.getValoreString(), 
										oggettoMailValutazioneAccreditamento.getValoreString(),
										corpoMailValutazioneAccreditamentoWithReplace,mailSmtpHost.getValoreString(), mailSmtpPort.getValoreString());
			
			
			csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SEND_MAIL - Valutazione Accreditamento", "sigas_utente_provvisorio",utenteProvvisorio.getIdPratica());
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
			
			
			csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SEND_MAIL - Domanda Accreditamento", "sigas_utente_provvisorio",utenteProvvisorio.getIdPratica());
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());

			
		} catch (MessagingException e) {
			//e.printStackTrace();
		}
	}

	// RECUPERA IL LEGALE RAPPRESENTANTE SE CENSITO SUL SISTEMA IRBA
	@Override
	public LegaleRappresentanteVO recuperaLegaleRappresentanteByCfDichiarante(String cfDichiarante, String piva) {

		if (Strings.isNullOrEmpty(cfDichiarante) && Strings.isNullOrEmpty(piva)) {
			throw new BadRequestException("Codice fiscale vuoto. Impossibile recuperare dati");
		}

		SigasDichiarante dichiaranteIrba = sigasDichiaranteRepository.findOne(SigasDichiaranteSpecification.codiceFiscaleOrPartitaIvaOrDenominazione(StringUtils.trim(cfDichiarante), piva, null));
		if (dichiaranteIrba == null)
			throw new BusinessException("Dichiarante non trovato nel sistema irba", ErrorCodes.FIELD_IS_NULL_OR_EMPTY);

		SigasLegaleRappresent legaleRappresentanteTrovato = sigasLegaleRappresentRepository.findBySigasDichiarante(dichiaranteIrba);

		return legaleRappresentanteEntityMapper.mapEntityToVO(legaleRappresentanteTrovato); 

	}

	/***
	 *
	 * @param legaleRappresentante
	 */
	@Override
	@Transactional
	public void confermaLegaleRappresentante(LegaleRappresentanteVO legaleRappresentante, SigasDichiarante sigasDichiarante) {

		if (legaleRappresentante == null || sigasDichiarante == null) {
			throw new BadRequestException("Dati del legale rappresentante e/o del utente vuoti. Per favore valorizzare i campi richiesti", ErrorCodes.FIELD_IS_NULL_OR_EMPTY);
		}

		SigasLegaleRappresent legaleRappresentanteInDb = sigasLegaleRappresentRepository.findBySigasDichiarante(sigasDichiarante);

		SigasLegaleRappresent sigasLegaleRappresentEntity = null;
		if (legaleRappresentanteInDb != null) {
			modificaAccreditamentoService.aggiornaDatiLegaleRappresentante(legaleRappresentante, sigasDichiarante);
		} else {
			// INSERISCO
			sigasLegaleRappresentEntity = legaleRappresentanteEntityMapper.mapVOtoEntity(legaleRappresentante);
			sigasLegaleRappresentEntity.setSigasDichiarante(sigasDichiarante);
//			sigasLegaleRappresentEntity.setSigasUtente2(sigasDichiarante.getSigasUtente2());
			sigasLegaleRappresentEntity.setDataInsert(new Timestamp(new Date().getTime()));
			sigasLegaleRappresentRepository.save(sigasLegaleRappresentEntity);
		}

	}

	/***
	 *
	 * @param operatore
	 */
	@Override
	@Transactional
	public void confermaOperatore(OperatoreVO operatore, SigasDichiarante sigasDichiarante) {

		if (operatore == null) {
			throw new BadRequestException("Dati dell'operatore e/o del utente vuoti. Per favore valorizzare i campi richiesti", ErrorCodes.FIELD_IS_NULL_OR_EMPTY);
		}
		if (sigasDichiarante == null) {
			throw new BadRequestException("Il dichiarante non è stato valorizzato. Per favore valorizzare i campi richiesti", ErrorCodes.FIELD_IS_NULL_OR_EMPTY);
		}
//		if (cfUtenteConnesso == null) {
//			throw new BadRequestException("Cf utente connesso non valorizzato. Per favore valorizzare i campi richiesti", ErrorCodes.FIELD_IS_NULL_OR_EMPTY);
//		}

		SigasOperatore sigasOperatoreDB = sigasOperatoreRepository.findByCfOperatore(operatore.getCodiceFiscale());
		// SE NON ESISTE SUL DB
		if (sigasOperatoreDB == null) {
			// RECUPERO I DATI UTENTE
//			sigasUtente utente = sigasUtenteRepository.findByCfUtente(cfUtenteConnesso);
			// MAPPO ENTITY OPERATORE
			SigasOperatore operatoreEntity = operatoreEntityMapper.mapVOtoEntity(operatore);

			operatoreEntity.setSigasDichiarante(sigasDichiarante);
//			operatoreEntity.setsigasUtente2(utente);
//			operatoreEntity.setsigasUtente3(utente);
			operatoreEntity.setDataInsert(new Timestamp(new Date().getTime()));

			SigasOperatore operatoreSalvato = sigasOperatoreRepository.save(operatoreEntity);

//			serviziService.salvaServiziAssociatiAOperatore(operatoreSalvato, operatore.getServizi(), sigasDichiarante);
		} else {
			modificaAccreditamentoService.aggiornaDatiOperatore(operatore, sigasDichiarante);
		}
	}
	
	
	@Override
	@Transactional
	public SigasUtenteProvvisorio confermaUtenteProvvisorio(ConfermaAccreditamentoRequest confermaAccreditamentoRequest, SigasDichiarante sigasDichiarante) {

		if (confermaAccreditamentoRequest.getOperatore() == null) {
			throw new BadRequestException("Dati dell'operatore e/o del utente vuoti. Per favore valorizzare i campi richiesti", ErrorCodes.FIELD_IS_NULL_OR_EMPTY);
		}
		
		if (confermaAccreditamentoRequest.getLegaleRappresentante() == null) {
			throw new BadRequestException("Dati del Rappresentate legale e/o del utente vuoti. Per favore valorizzare i campi richiesti", ErrorCodes.FIELD_IS_NULL_OR_EMPTY);
		}		
		
		if (sigasDichiarante == null) {
			throw new BadRequestException("Il dichiarante non è stato valorizzato. Per favore valorizzare i campi richiesti", ErrorCodes.FIELD_IS_NULL_OR_EMPTY);
		}
		

		SigasOperatore sigasOperatoreDB = sigasOperatoreRepository.findByCfOperatore(confermaAccreditamentoRequest.getOperatore().getCodiceFiscale());
		
		SigasLegaleRappresent legaleRappresentanteInDb = sigasLegaleRappresentRepository.findBySigasDichiarante(sigasDichiarante);
		
		//Salvo l'utente provvisorio
		SigasUtenteProvvisorio  utenteProvvisorioEntity = new SigasUtenteProvvisorio();

		utenteProvvisorioEntity.setSigasLegaleRappresent(legaleRappresentanteInDb);
		utenteProvvisorioEntity.setSigasOperatore(sigasOperatoreDB );
		utenteProvvisorioEntity.setSigasDichiarante(sigasDichiarante );
		utenteProvvisorioEntity.setStato("IN_LAVORAZIONE");
		utenteProvvisorioEntity.setInsDate(new Timestamp(new Date().getTime()));
		utenteProvvisorioEntity.setInsUser(confermaAccreditamentoRequest.getOperatore().getCodiceFiscale());
		
		utenteProvvisorioEntity = sigasUtenteProvvisorioRepository.save(utenteProvvisorioEntity);
		
		utenteProvvisorioEntity.setIdPratica(utenteProvvisorioEntity.getIdUtenteProvv()+"/"+Calendar.getInstance().get(Calendar.YEAR));
		
		return sigasUtenteProvvisorioRepository.save(utenteProvvisorioEntity);

	}

	@Override
	@Transactional
	public void verificaEsistenzaLegaleRappresentante(String cflegaleRappr, DichiaranteVO dichiaranteVO) {
//		SigasLegaleRappresent sigasLegaleRappresent = sigasLegaleRappresentRepository.findByCfLegaleRappresent(cflegaleRappr.toUpperCase());
//		vecchio codice
//		SigasDichiarante dichiaranteIrba = sigasDichiaranteRepository.findOne(SigasDichiaranteSpecification.codiceFiscaleOrPartitaIvaOrDenominazione(StringUtils.trim(dichiaranteVO.getCodiceFiscale()),
//				dichiaranteVO.getPartitaIva(), dichiaranteVO.getDenominazione()));
		
		//Nuovo codice
//		SigasDichiarante dichiaranteSigas = sigasDichiaranteRepository.findOne(dichiaranteVO.getIdDichiarante());
//		if (sigasLegaleRappresent == null)
//			return;
//		if (dichiaranteSigas != null) {
//			List<SigasLegaleRappresent> listRappr = dichiaranteSigas.getSigasLegaleRappresents();
//			if (listRappr != null && !listRappr.isEmpty() && listRappr.get(0).getCfLegaleRappresent().equalsIgnoreCase(sigasLegaleRappresent.getCfLegaleRappresent()))
//				return;
//		}
//		throw new BusinessException(String.format(ErrorMessages.MSG_LEGALE_CENSITO, cflegaleRappr.toUpperCase()), ErrorCodes.LEGALE_RAPPRESENTANTE_CENSITO);

		return;
	}

	@Override
	public void verificaDichiarante(String codiceAzienda, String codiceFiscaleOperatore) {
		List<SigasUtenteProvvisorio> listPratiche = sigasUtenteProvvisorioRepository.findBySigasOperatoreCfOperatore(codiceFiscaleOperatore);
		
		for (SigasUtenteProvvisorio sigasUtenteProvvisorioTmp : listPratiche) {
			if(sigasUtenteProvvisorioTmp.getSigasDichiarante().getCodiceAzienda().equalsIgnoreCase(codiceAzienda) && !sigasUtenteProvvisorioTmp.getStato().equalsIgnoreCase(ANNULLATA)) {
				SigasCMessaggi sigasMessaggio = sigasCMessaggiRepository.findByDescChiaveMessaggio("richiestaAccreditamentoGiaInserita");
				throw new BusinessException(sigasMessaggio.getValoreMessaggio(), sigasMessaggio.getLivelloMessaggio());
			}
		}
	}

}