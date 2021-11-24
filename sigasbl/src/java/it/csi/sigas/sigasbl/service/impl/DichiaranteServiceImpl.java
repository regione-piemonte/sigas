/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.exception.BadRequestException;
import it.csi.sigas.sigasbl.common.utils.Utilities;
import it.csi.sigas.sigasbl.integration.specification.SigasAnagraficaSoggettoSpecification;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaUtente;
import it.csi.sigas.sigasbl.model.entity.SigasCMessaggi;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;
import it.csi.sigas.sigasbl.model.entity.SigasLegaleRappresent;
import it.csi.sigas.sigasbl.model.entity.SigasOperatore;
import it.csi.sigas.sigasbl.model.entity.SigasRuolo;
import it.csi.sigas.sigasbl.model.entity.SigasUtenteProvvisorio;
import it.csi.sigas.sigasbl.model.entity.SigasUtenti;
import it.csi.sigas.sigasbl.model.mapper.entity.AnagraficaSoggettiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichiaranteAccreditamentoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.LegaleRappresentanteEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.OperatoreEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.UtenteProvvisorioEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaUtenteRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCMessaggiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichiaranteRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasLegaleRappresentRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasOperatoreRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasRuoloRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasUtenteProvvisorioRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasUtentiRepository;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaDichiaranteRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaPraticheAccreditamentoRequest;
import it.csi.sigas.sigasbl.service.IDichiaranteService;
import it.csi.sigas.sigasbl.service.IInserisciAccrediatamentoService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;
import it.csi.sigas.sigasbl.vo.accreditamento.AccreditamentoVO;
import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.UtenteProvvisorioVO;


@Service
public class DichiaranteServiceImpl implements IDichiaranteService {

	
	@Autowired
	private UtenteProvvisorioEntityMapper utenteProvvisorioEntityMapper;

	
	@Autowired
	private  LegaleRappresentanteEntityMapper legaleRappresentanteEntityMapper;
	
	@Autowired
	private  OperatoreEntityMapper operatoreEntityMapper;
	
	@Autowired
	private SigasUtenteProvvisorioRepository sigasUtenteProvvisorioRepository;
	
	@Autowired
	private SigasDichiaranteRepository sigasDichiaranteRepository;

	@Autowired
	private SigasOperatoreRepository sigasOperatoreRepository;
	
	
	@Autowired
	private SigasLegaleRappresentRepository sigasLegaleRappresentRepository;
	
	@Autowired
	private SigasRuoloRepository sigasRuoloRepository;
	
	@Autowired
	private SigasUtentiRepository sigasUtentiRepository;
	
	
	@Autowired
	private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;
	
	@Autowired
	private SigasAnagraficaUtenteRepository sigasAnagraficaUtenteRepository;
	
	@Autowired
	private DichiaranteAccreditamentoEntityMapper dichiaranteAccreditamentoEntityMapper;
	
	@Autowired
	private AnagraficaSoggettiEntityMapper anagraficaSoggettiEntityMapper; 
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;
	
	@Autowired
	IInserisciAccrediatamentoService iInserisciAccrediatamentoService; 
	
	@Autowired
	private SigasCMessaggiRepository sigasCMessaggiRepository;
	
	public static final String IN_LAVORAZIONE = "IN_LAVORAZIONE";
	public static final String ACCETTATA = "ACCETTATA";
	public static final String RIFIUTATA = "RIFIUTATA";
	

	@Override
	public List<UtenteProvvisorioVO> ricercaPratiche(RicercaPraticheAccreditamentoRequest ricercaPraticheAccreditamentoRequest){
		List<SigasUtenteProvvisorio> listUtenteProvvisorio = new ArrayList<SigasUtenteProvvisorio>();
		List<String> listKeyOperAll = new ArrayList<>();
		if("TUTTE".equalsIgnoreCase(ricercaPraticheAccreditamentoRequest.getStato())) {
			listUtenteProvvisorio = (List<SigasUtenteProvvisorio>) sigasUtenteProvvisorioRepository.findAllOrderByInsDateDesc();
		}else {
			listUtenteProvvisorio = (List<SigasUtenteProvvisorio>) sigasUtenteProvvisorioRepository.findByStatoOrderByInsDateDesc(ricercaPraticheAccreditamentoRequest.getStato());
		}
		
		for (SigasUtenteProvvisorio utente: listUtenteProvvisorio) {
			listKeyOperAll.add(String.valueOf(utente.getIdUtenteProvv()));
		}
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SIGAS BO - RICERCA RICHIESTE ACCREDITAMENTO", "sigas_utente_provvisorio",String.join("_", listKeyOperAll) );
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
		
		
		return utenteProvvisorioEntityMapper.mapListEntityToListVO(listUtenteProvvisorio );
	}

	@Override
	public AccreditamentoVO ricercaPraticaAccreditamento(RicercaAccreditamentoRequest ricercaAccreditamentoRequest) {
		AccreditamentoVO accreditamentoVO = new AccreditamentoVO();
		
		SigasUtenteProvvisorio utenteProvvisorio = sigasUtenteProvvisorioRepository.findOne(ricercaAccreditamentoRequest.getIdUtenteProvv());
		
		SigasLegaleRappresent sigasLegaleRappresent = sigasLegaleRappresentRepository.findOne(utenteProvvisorio.getSigasLegaleRappresent().getIdLegaleRappresent());
		SigasDichiarante sigasDichiarante = sigasDichiaranteRepository.findOne(utenteProvvisorio.getSigasDichiarante().getIdDichiarante());
		SigasOperatore sigasOperatore = sigasOperatoreRepository.findOne(utenteProvvisorio.getSigasOperatore().getIdOperatore());
		
		accreditamentoVO.setDichiarante(dichiaranteAccreditamentoEntityMapper.mapEntityToVO(sigasDichiarante));
		accreditamentoVO.setOperatore(operatoreEntityMapper.mapEntityToVO(sigasOperatore));
		accreditamentoVO.setLegaleRappresentante(legaleRappresentanteEntityMapper.mapEntityToVO(sigasLegaleRappresent));
		accreditamentoVO.setUtenteProvvisorio(utenteProvvisorioEntityMapper.mapEntityToVO(utenteProvvisorio));
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SIGAS - CONSULTA RICHIESTA ACCREDITAMENTO", "sigas_utente_provvisorio",String.valueOf(utenteProvvisorio.getIdUtenteProvv()) );
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
		
		return accreditamentoVO;
	}

	@Override
	public void confermaPraticaAccreditamento(
			ConfermaPraticaAccreditamentoRequest confermaPraticaAccreditamentoRequest, String user) {
		
		
		SigasDichiarante sigasDichiarante = confermaDichiarante(confermaPraticaAccreditamentoRequest.getDichiarante());
		iInserisciAccrediatamentoService.confermaLegaleRappresentante(confermaPraticaAccreditamentoRequest.getLegaleRappresentante(), sigasDichiarante);
		iInserisciAccrediatamentoService.confermaOperatore(confermaPraticaAccreditamentoRequest.getOperatore(), sigasDichiarante);
		
		SigasUtenteProvvisorio utenteProvvisorio = sigasUtenteProvvisorioRepository.findOne(confermaPraticaAccreditamentoRequest.getIdUtenteProvv());
		String statoPrecUtenteProvv = utenteProvvisorio.getStato();
		utenteProvvisorio.setStato(confermaPraticaAccreditamentoRequest.getStato());
		utenteProvvisorio.setInsUser(user);
		utenteProvvisorio.setInsDate(new Timestamp(new Date().getTime())  );
		if(confermaPraticaAccreditamentoRequest.getNote()!=null && 
				!confermaPraticaAccreditamentoRequest.getNote().isEmpty()) {
			utenteProvvisorio.setNote(confermaPraticaAccreditamentoRequest.getNote());
		}
		
		sigasUtenteProvvisorioRepository.save(utenteProvvisorio);
		
		if(ACCETTATA.equalsIgnoreCase(confermaPraticaAccreditamentoRequest.getStato())) {
			SigasOperatore sigasOperatore = sigasOperatoreRepository.findOne(utenteProvvisorio.getSigasOperatore().getIdOperatore());
			SigasUtenti sigasUtenteTmp = sigasUtentiRepository.findByCodiceFiscaleAndIdUtenteProvv(sigasOperatore.getCfOperatore(), BigDecimal.valueOf(utenteProvvisorio.getIdUtenteProvv()) );
			if(sigasUtenteTmp==null) {
				SigasRuolo sigasRuolo = sigasRuoloRepository.findOne(1L);
				SigasUtenti sigasUtenti = new SigasUtenti();		
				sigasUtenti.setCodiceFiscale(sigasOperatore.getCfOperatore());
				sigasUtenti.setIdUtenteProvv(BigDecimal.valueOf(utenteProvvisorio.getIdUtenteProvv()) );
				sigasUtenti.setSigasRuolo(sigasRuolo);
				
				sigasUtentiRepository.save(sigasUtenti);
			}
			
		}
		 
		
		
		
		if(ACCETTATA.equalsIgnoreCase(confermaPraticaAccreditamentoRequest.getStato()) ){
			sigasDichiarante = sigasDichiaranteRepository.findOne(utenteProvvisorio.getSigasDichiarante().getIdDichiarante());
			
			SigasAnagraficaUtente sigasAnagraficaUtente = new SigasAnagraficaUtente();
			
			sigasAnagraficaUtente.setIdUtenteProvv(BigDecimal.valueOf(utenteProvvisorio.getIdUtenteProvv()) );
			SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByCodiceAzienda(sigasDichiarante.getCodiceAzienda());
			if(sigasAnagraficaSoggetti!=null) {
				sigasAnagraficaUtente.setIdAnag(Long.valueOf((sigasAnagraficaSoggetti.getIdAnag())).intValue());
				
	//			sigasAnagraficaSoggetti.setEmail(sigasDichiarante.getEmailDichiarante());
	//			sigasAnagraficaSoggetti.setPec(sigasDichiarante.getPecDichiarante());
	//			sigasAnagraficaSoggetti.setTelefono(sigasDichiarante.getTelefonoDichiarante());
	//			
	//			sigasAnagraficaSoggettiRepository.save(sigasAnagraficaSoggetti);
	//			
	//			CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"UPDATE - updateSoggetto", "sigas_anagrafica_soggetti",String.valueOf(sigasAnagraficaSoggetti.getIdAnag()) );
	//			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
	//					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
			}else {
				SigasAnagraficaSoggetti sigasAnagraficaSoggettiInsert = new SigasAnagraficaSoggetti();
				sigasAnagraficaSoggettiInsert.setCodiceAzienda(sigasDichiarante.getCodiceAzienda());
				sigasAnagraficaSoggettiInsert.setDenominazione(sigasDichiarante.getDenomDichiarante());
				sigasAnagraficaSoggettiInsert.setIndirizzo(sigasDichiarante.getIndirizzoSeleLegaleDichiar()!=null ? sigasDichiarante.getIndirizzoSeleLegaleDichiar():"");
				sigasAnagraficaSoggettiInsert.setInsUser(user);
				sigasAnagraficaSoggettiInsert.setInsDate(new Date());
				sigasAnagraficaSoggettiInsert.setSigasProvincia(sigasDichiarante.getSigasProvincia());
				sigasAnagraficaSoggettiInsert.setSigasComune(sigasDichiarante.getSigasComune());
				sigasAnagraficaSoggettiInsert.setEmail(sigasDichiarante.getEmailDichiarante());
				sigasAnagraficaSoggettiInsert.setIban(sigasDichiarante.getIban());
				sigasAnagraficaSoggettiInsert.setNote(sigasDichiarante.getNote());
				sigasAnagraficaSoggettiInsert.setPec(sigasDichiarante.getPecDichiarante());
				sigasAnagraficaSoggettiInsert.setTelefono(sigasDichiarante.getTelefonoDichiarante());
	
				sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.save(sigasAnagraficaSoggettiInsert);
							
				sigasAnagraficaUtente.setIdAnag(Long.valueOf((sigasAnagraficaSoggetti.getIdAnag())).intValue());
				
				CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERT - insertSoggetto", "sigas_anagrafica_soggetti",String.valueOf(sigasAnagraficaSoggetti.getIdAnag()) );
				csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
						csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
			}
		
		
			SigasAnagraficaUtente sigasAnagraficaUtenteTmp =  sigasAnagraficaUtenteRepository.findByIdAnagAndIdUtenteProvv(sigasAnagraficaUtente.getIdAnag(), BigDecimal.valueOf(utenteProvvisorio.getIdUtenteProvv()) );
			
			if(sigasAnagraficaUtenteTmp==null) {
				sigasAnagraficaUtenteRepository.save(sigasAnagraficaUtente);
			}
		}
		
			
		
		
		
		SigasCParametro linkInformativaTrattamentoDatiPers = sigasCParametroRepository.findByDescParametro("linkInformativaTrattamentoDatiPers");
		//COME DA SIGAS-175 eliminiamo la mail di conferma accreditamento oggettoMailConfermaAccreditamento
//		SigasCParametro linkServizioSigas = sigasCParametroRepository.findByDescParametro("linkServizioSigas");
		SigasCParametro mailSmtpHost = sigasCParametroRepository.findByDescParametro("mailSmtpHost");
		SigasCParametro mailSmtpPort = sigasCParametroRepository.findByDescParametro("mailSmtpPort");
		
		if(ACCETTATA.equalsIgnoreCase(confermaPraticaAccreditamentoRequest.getStato()) && !statoPrecUtenteProvv.equalsIgnoreCase(ACCETTATA)) {
			SigasCParametro mittenteMailRichiestaAbilitazione = sigasCParametroRepository.findByDescParametro("mittenteMailRichiestaAbilitazione");
			SigasCParametro destinatarioMailRichiestaAbilitazione = sigasCParametroRepository.findByDescParametro("destinatarioMailRichiestaAbilitazione");
			SigasCParametro oggettoMailRichiestaAbilitazione = sigasCParametroRepository.findByDescParametro("oggettoMailRichiestaAbilitazione");
			SigasCParametro corpoMailRichiestaAbilitazione = sigasCParametroRepository.findByDescParametro("corpoMailRichiestaAbilitazione");		
			String corpoMailRichiestaAbilitazioneWithReplace = corpoMailRichiestaAbilitazione.getValoreString().replaceAll("<<NOME>>", utenteProvvisorio.getSigasOperatore().getNomeOperatore()).
																					replaceAll("<<COGNOME>>", utenteProvvisorio.getSigasOperatore().getCognomeOperatore()).
																					replaceAll("<<CF_OPERATORE_FO>>", utenteProvvisorio.getSigasOperatore().getCfOperatore());
			
			
			
			//COME DA SIGAS-175 eliminiamo la mail di conferma accreditamento oggettoMailConfermaAccreditamento
//			SigasCParametro mittenteMailConfermaAccreditamento = sigasCParametroRepository.findByDescParametro("mittenteMailConfermaAccreditamento");
//			SigasCParametro oggettoMailConfermaAccreditamento = sigasCParametroRepository.findByDescParametro("oggettoMailConfermaAccreditamento");
//			SigasCParametro corpoMailConfermaAccreditamento = sigasCParametroRepository.findByDescParametro("corpoMailConfermaAccreditamento");		
//			String corpoMailConfermaAccreditamentoWithReplace = corpoMailConfermaAccreditamento.getValoreString().replaceAll("<<NOME>>", utenteProvvisorio.getSigasOperatore().getNomeOperatore()).
//																					replaceAll("<<COGNOME>>", utenteProvvisorio.getSigasOperatore().getCognomeOperatore()).
//																					replaceAll("<<link>>", linkInformativaTrattamentoDatiPers.getValoreString()).
//																					replaceAll("<<link_servizio_sigas>>", linkServizioSigas.getValoreString());
			
			 
			try {
				//COME DA SIGAS-175 eliminiamo la mail di conferma accreditamento oggettoMailConfermaAccreditamento
//				Utilities.sendMail(utenteProvvisorio.getSigasOperatore().getEmailOperatore(), mittenteMailConfermaAccreditamento.getValoreString(), oggettoMailConfermaAccreditamento.getValoreString(),corpoMailConfermaAccreditamentoWithReplace,mailSmtpHost.getValoreString(), mailSmtpPort.getValoreString());
				Utilities.sendMail(destinatarioMailRichiestaAbilitazione.getValoreString(), 
						mittenteMailRichiestaAbilitazione.getValoreString(), 
						oggettoMailRichiestaAbilitazione.getValoreString(),
						corpoMailRichiestaAbilitazioneWithReplace,mailSmtpHost.getValoreString(), mailSmtpPort.getValoreString());
				
				//COME DA SIGAS-175 eliminiamo la mail di conferma accreditamento oggettoMailConfermaAccreditamento
//				CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SEND_MAIL - Conferma Accreditamento", "sigas_utente_provvisorio",utenteProvvisorio.getIdPratica());
//				csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
//						csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
				
				
				CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SEND_MAIL - Richiesta Abilitazione Accreditamento", "sigas_utente_provvisorio",utenteProvvisorio.getIdPratica());
				csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
						csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
				
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}else if(RIFIUTATA.equalsIgnoreCase(confermaPraticaAccreditamentoRequest.getStato()) && !statoPrecUtenteProvv.equalsIgnoreCase(RIFIUTATA)){
			SigasCParametro mittenteMailDiniegoAccreditamento = sigasCParametroRepository.findByDescParametro("mittenteMailDiniegoAccreditamento");
			SigasCParametro oggettoMailDiniegoAccreditamento = sigasCParametroRepository.findByDescParametro("oggettoMailDiniegoAccreditamento");
			SigasCParametro corpoMailDiniegoAccreditamento = sigasCParametroRepository.findByDescParametro("corpoMailDiniegoAccreditamento");		
			String corpoMailDiniegoAccreditamentoWithReplace = corpoMailDiniegoAccreditamento.getValoreString().replaceAll("<<NOME>>", utenteProvvisorio.getSigasOperatore().getNomeOperatore()).
																					replaceAll("<<COGNOME>>", utenteProvvisorio.getSigasOperatore().getCognomeOperatore()).
																					replaceAll("<<NOTE>>", confermaPraticaAccreditamentoRequest.getNote()!=null ? confermaPraticaAccreditamentoRequest.getNote():"").
																					replaceAll("<<link>>", linkInformativaTrattamentoDatiPers.getValoreString());
			
			
			try {
				Utilities.sendMail(utenteProvvisorio.getSigasOperatore().getEmailOperatore(), mittenteMailDiniegoAccreditamento.getValoreString(), 
						oggettoMailDiniegoAccreditamento.getValoreString(),corpoMailDiniegoAccreditamentoWithReplace,mailSmtpHost.getValoreString(), mailSmtpPort.getValoreString());

				CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SEND_MAIL - Diniego Accreditamento", "sigas_utente_provvisorio",utenteProvvisorio.getIdPratica());
				csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
						csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
				
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SIGAS BO - MODIFICA STATO RICHIESTA ACCREDITAMENTO", "sigas_utente_provvisorio",String.valueOf(utenteProvvisorio.getIdUtenteProvv()) +"_"+confermaPraticaAccreditamentoRequest.getStato());
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
		
		
	}
	
	
	
	/////OPERATORE FO
	
	
	// RICERCA IL DICHIARANTE DA OSCAR O DA IRBA
		@Override 
		public List<AnagraficaSoggettoVO> ricercaDichiarante(String denominazione, String codiceFiscale, String partitaIva, String codiceAzienda) {

			/**
			 * L'utente inserisce almeno uno (o due o tutti e tre) dei seguenti
			 * dati: la Denominazione, e/o il Codice Fiscale e/o la Partita Iva del
			 * Dichiarante e preme il pulsante 'Cerca' (RICERCA DICHIARANTE)
			 */

			if (Strings.isNullOrEmpty(denominazione) && Strings.isNullOrEmpty(codiceAzienda)) {
				throw new BadRequestException("Valorizzare almeno uno dei campi obbligatori!", ErrorCodes.FIELD_IS_NULL_OR_EMPTY);
			} 

			List<SigasAnagraficaSoggetti> listDichiaranteSigas = sigasAnagraficaSoggettiRepository
					.findAll(SigasAnagraficaSoggettoSpecification.codiceAziendaOrLikeDenominazione(codiceAzienda, denominazione));
			if (listDichiaranteSigas != null && !listDichiaranteSigas.isEmpty()) {
				return anagraficaSoggettiEntityMapper.mapListEntityToListVO(listDichiaranteSigas);
			} 
//			else {
//				TitolareItem[] titolariOsscar;
//				try {
//					titolariOsscar = osscarServiceFacade.getTitolari(denominazione.trim().toUpperCase(), codiceFiscale.trim().toUpperCase(), partitaIva.trim().toUpperCase());
//				} catch (RemoteException e) {
//					throw new BusinessException("Il servizio esterno osscar non è al momento disponibile.Si prega di inserire i dati manualmente", ErrorCodes.OSSCAROKWITHERROR);
//				}
	//
//				if (titolariOsscar != null && titolariOsscar.length > 0) {
//					return dichiaranteOscarMapper.mapWstoVO(titolariOsscar[0]);
//				}
//			}

//			throw new BusinessException("Il titolare non è stato trovato. Si prega di inserire dati esistenti", ErrorCodes.OSSCAROKWITHERROR);
			
			
			return null;
		}

		@Override
		@Transactional
		public SigasDichiarante confermaDichiarante(DichiaranteVO dichiarante) { 

			if (dichiarante == null) {
				throw new BadRequestException("Dati del dichiarante e/o del utente vuoti. Per favore valorizzare i campi richiesti", ErrorCodes.FIELD_IS_NULL_OR_EMPTY);
			}

			// RECUPERO I DATI UTENTE
//			IrbaTUtente utente = sigasTUtenteRepository.findByCfUtente(codiceFiscaleCliente);

			// VERIFICO CHE IL DICHIARANTE NON ESISTA GIA
			SigasDichiarante dichiaranteSigas = null;
//			if (StringUtils.isNotEmpty(dichiarante.getPartitaIva()) || StringUtils.isNotEmpty(dichiarante.getCodiceFiscale()))
//				dichiaranteSigas = sigasDichiaranteRepository
//						.findOne(SigasDichiaranteSpecification.codiceFiscaleOrPartitaIvaOrDenominazione(dichiarante.getCodiceFiscale(), dichiarante.getPartitaIva(), dichiarante.getDenominazione()));

			SigasDichiarante dichiaranteEntity = null;
			// SE ESISTE AGGIORNO
			if (dichiaranteSigas != null) {
				dichiaranteEntity = aggiornaDatiDichiarante(dichiarante);
			} else {
				// SE NON ESISTE INSERISCO
				dichiaranteEntity = dichiaranteAccreditamentoEntityMapper.mapVOtoEntity(dichiarante);
//				dichiaranteEntity.setIrbaTUtente2(utente);
				dichiaranteEntity.setDataInsert(new Timestamp(new Date().getTime()));
				dichiaranteEntity = sigasDichiaranteRepository.save(dichiaranteEntity);
			}

			return dichiaranteEntity;

		}

		

		@Override
		@Transactional
		public SigasDichiarante aggiornaDatiDichiarante(DichiaranteVO dichiaranteVoPerAggiornamento) {
			if (dichiaranteVoPerAggiornamento == null) {
				throw new BadRequestException("Dichiarante vuoto. Impossibile aggiornare i dati del dichiarante");
			}
			SigasDichiarante sigasDichiaranteDB = sigasDichiaranteRepository
					.findOne(dichiaranteVoPerAggiornamento.getIdDichiarante());
//
//			if (sigasDichiaranteDB == null) {
//				throw new BusinessException("E' stata inserita una denominazione dichiarante che non è associata al codice fiscale nei nostri database. ");
//			}

//			storicizzazioneDichiarante(irbaTDichiaranteDB, irbaTUtente);

			SigasDichiarante sigasDichiaranteNew = dichiaranteAccreditamentoEntityMapper.mapVOtoEntity(dichiaranteVoPerAggiornamento);

//			irbaTDichiaranteNew.setIrbaTUtente1(irbaTUtente);
//			sigasDichiaranteNew.setDataUpdate(new Timestamp(new Date().getTime()));
			sigasDichiaranteNew.setDataInsert(sigasDichiaranteDB.getDataInsert());
//			irbaTDichiaranteNew.setSigasUtente2(irbaTDichiaranteDB.getSigasUtente2());
			sigasDichiaranteNew.setIdDichiarante(sigasDichiaranteDB.getIdDichiarante());

			// Eseguo il salvataggio dell'entity con i campi aggiornati
			return sigasDichiaranteRepository.save(sigasDichiaranteNew);
		}

		@Override
		public DichiaranteVO recuperaDichiarante(String codiceFiscaleUtente) {

			if (Strings.isNullOrEmpty(codiceFiscaleUtente)) {
				throw new BadRequestException("Codice fiscale vuoto. Impossibile recuperare dati");
			}

			SigasOperatore sigasOperatore = sigasOperatoreRepository.findByCfOperatore(StringUtils.trim(codiceFiscaleUtente));

			SigasDichiarante sigasDichiarante = sigasDichiaranteRepository.findBySigasOperatores(sigasOperatore);

			return dichiaranteAccreditamentoEntityMapper.mapEntityToVO(sigasDichiarante);

		}  

		
		
		
		public List<UtenteProvvisorioVO> ricercaPratiche(RicercaDichiaranteRequest ricercaDichiaranteRequest){
			List<SigasUtenteProvvisorio> listUtenteProvvisorio = null;
			List<String> listKeyOperAll = new ArrayList<>();
			if(ricercaDichiaranteRequest.getCodiceFiscale()!=null && !ricercaDichiaranteRequest.getCodiceFiscale().isEmpty() && 
					ricercaDichiaranteRequest.getNumeroProtocollo()!=null && !ricercaDichiaranteRequest.getNumeroProtocollo().isEmpty()) {
//				listUtenteProvvisorio = (List<SigasUtenteProvvisorio>) sigasUtenteProvvisorioRepository.findByNumeroProtocolloAndSigasOperatoreCfOperatore(ricercaDichiaranteRequest.getNumeroProtocollo(), ricercaDichiaranteRequest.getCodiceFiscale());
			}else if((ricercaDichiaranteRequest.getCodiceFiscale()==null || ricercaDichiaranteRequest.getCodiceFiscale().isEmpty()) &&
					ricercaDichiaranteRequest.getNumeroProtocollo()!=null && !ricercaDichiaranteRequest.getNumeroProtocollo().isEmpty()) {
//				listUtenteProvvisorio = (List<SigasUtenteProvvisorio>) sigasUtenteProvvisorioRepository.findByNumeroProtocollo(ricercaDichiaranteRequest.getNumeroProtocollo());
			}else if(ricercaDichiaranteRequest.getCodiceFiscale()!=null && !ricercaDichiaranteRequest.getCodiceFiscale().isEmpty() &&
					(ricercaDichiaranteRequest.getNumeroProtocollo()==null || ricercaDichiaranteRequest.getNumeroProtocollo().isEmpty()) ) {
				listUtenteProvvisorio = (List<SigasUtenteProvvisorio>) sigasUtenteProvvisorioRepository.findBySigasOperatoreCfOperatore(ricercaDichiaranteRequest.getCodiceFiscale());
			}
//			List<SigasUtenteProvvisorio> listUtenteProvvisorio = (List<SigasUtenteProvvisorio>) sigasUtenteProvvisorioRepository.findAll();
			for (SigasUtenteProvvisorio utente: listUtenteProvvisorio) {
				listKeyOperAll.add(String.valueOf(utente.getIdUtenteProvv()));
			}
			CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SIGAS FO - CONSULTA DOMANDE ACCREDITAMENTO", "sigas_utente_provvisorio",String.join("_", listKeyOperAll) );
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
			
			return utenteProvvisorioEntityMapper.mapListEntityToListVO(listUtenteProvvisorio );
		}
	
	
}
