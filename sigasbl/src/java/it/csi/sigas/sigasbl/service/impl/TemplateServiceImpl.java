/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.print.PrintException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.integration.doqui.DoquiConstants;
import it.csi.sigas.sigasbl.model.entity.SigasDocumenti;
import it.csi.sigas.sigasbl.model.mapper.entity.DocumentiEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDocumentiRepository;
import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;
import it.csi.sigas.sigasbl.request.template.DatiTemplateRequest;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.service.ITemplateService;
import it.csi.sigas.sigasbl.service.IUtilsService;
import it.csi.sigas.sigasbl.vo.template.DatiTemplateCompilatiVO;
import it.csi.sigas.sigasbl.vo.template.DatiTemplateVO;
import net.sf.jasperreports.engine.JRException;


@Service
public class TemplateServiceImpl implements ITemplateService {


	@Autowired
	private IUtilsService utilsService;


	@Autowired
	private SigasDocumentiRepository sigasDocumentiRepository;
	
	@Autowired
	private DocumentiEntityMapper documentiEntityMapper;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	
	@Override
	public DatiTemplateVO getDatiTemplate(DatiTemplateRequest request, UserDetails userDetails ) {
		if (request.getCodiceTemplate() == null)
			throw new IllegalArgumentException("codice template non valorizzato");

		DatiTemplateVO response = null;
		response = getDatiTemplateLetteraRisposta(request.getIdDocumentazione());

		return response;
	}

	@Override
	public byte[] stampaTemplate(DatiTemplateRequest request, UserDetails userDetails) {
		if (request.getCodiceTemplate() == null)
			throw new IllegalArgumentException("codice template non valorizzato");

		byte[] 	response = stampaTemplateByCodice(request, "PDF_LETTERA_RISPOSTA", getDatiTemplateLetteraRisposta(request.getIdDocumentazione()));

		return response;

	}

	@Override
	public DatiTemplateVO nomiTemplate(DatiTemplateRequest request, UserDetails userDetails) {
		DatiTemplateVO response = null;

		return response;
	}



	private byte[] stampaTemplateByCodice(DatiTemplateRequest request, String codice, DatiTemplateVO dati) {
		DatiTemplateCompilatiVO datiTemplateCompilatiVO = request.getDatiTemplateCompilatiVO();
		if (datiTemplateCompilatiVO == null)
			throw new IllegalArgumentException("ddatiTemplateCompilatiVO = null");

		byte[] report;
		Map<String, Object> jasperParam = new HashMap<>();
 
		// compilati da utente
		jasperParam.put("idMailSettoreTributi", dati.getMailSettoreTributi());
		
		jasperParam.put("nprotocollo", StringUtils.trimToEmpty(request.getnProtocollo()));
		jasperParam.put("classificazione", StringUtils.trimToEmpty(dati.getClassificazione()));
		
		jasperParam.put("intestazione", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getIntestazione()));
		jasperParam.put("indirizzo", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getIndirizzo()));		
		jasperParam.put("capComuneProvincia", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getCapComuneProvinvcia()));
		
		jasperParam.put("oggetto", StringUtils.trimToEmpty(datiTemplateCompilatiVO.getOggetto()));
		jasperParam.put("testoLibero", datiTemplateCompilatiVO.getDescrizione() == null ? "":datiTemplateCompilatiVO.getDescrizione());

		jasperParam.put("dirigente", StringUtils.trimToEmpty(dati.getNomeDirigente()) +" "+ StringUtils.trimToEmpty(dati.getCognomeDirigente()));
		try {
			report = utilsService.printReportPDF(codice, jasperParam, null);
		} catch (PrintException | IOException | SQLException | JRException e) {
			throw new RuntimeException(e);
		}
		return report;
	}


	
	private DatiTemplateVO getDatiTemplateLetteraRisposta(Integer idDocumentazione) {
	if (idDocumentazione == null)
		throw new IllegalArgumentException("idDocumentazione non valorizzato");

	SigasDocumenti sigasDocumenti = sigasDocumentiRepository.findOne(idDocumentazione);
	
	DocumentiVO documentiVo = documentiEntityMapper.mapEntityToVO(sigasDocumenti);
	
	DatiTemplateVO datiTemplateVO = new DatiTemplateVO();
	datiTemplateVO.setAnagraficaSoggettoVO(documentiVo.getAnagraficaSoggettoVO());
	
	String codiceClassificazione= sigasCParametroRepository.findByDescParametro(DoquiConstants.CODICE_CLASSIFICAZIONE).getValoreString() + " ";
	String codiceAOO="A11000";
	String anno=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	
    if (sigasDocumenti.getSigasTipoDocumento().getCodiceTipoDocumento().equalsIgnoreCase(DoquiConstants.DICHIARAZIONI)) {
    	codiceClassificazione+= sigasCParametroRepository.findByDescParametro("DICHIARAZIONI_SERIE_FASCICOLI_CODICE").getValoreString() + "/"+codiceAOO+" 4/"+anno+"A/"+codiceAOO;
    } else if (sigasDocumenti.getSigasTipoDocumento().getCodiceTipoDocumento().contains(DoquiConstants.RIMBORSI)) {
    	codiceClassificazione+= sigasCParametroRepository.findByDescParametro("RIMBORSI_SERIE_FASCICOLI_CODICE").getValoreString() + "/"+codiceAOO+" 5/"+anno+"A/"+codiceAOO;
    } else if (sigasDocumenti.getSigasTipoDocumento().getCodiceTipoDocumento().equalsIgnoreCase(DoquiConstants.DEPOSITI_CAUZIONALI)) {
    	codiceClassificazione+= sigasCParametroRepository.findByDescParametro("DEPOSITI_CAUZIONALI_SERIE_FASCICOLI_CODICE").getValoreString() + "/"+codiceAOO+" 3/"+anno+"A/"+codiceAOO;
    } else if (sigasDocumenti.getSigasTipoDocumento().getCodiceTipoDocumento().equalsIgnoreCase(DoquiConstants.ACCERTAMENTI)) {
    	codiceClassificazione+= sigasCParametroRepository.findByDescParametro("ACCERTAMENTI_VARIE_SERIE_FASCICOLI_CODICE").getValoreString() + "/"+codiceAOO+" 1/"+anno+"A/"+codiceAOO;
    } else {
    	codiceClassificazione+= sigasCParametroRepository.findByDescParametro("COMUNICAZIONI_VARIE_SERIE_FASCICOLI_CODICE").getValoreString() + "/"+codiceAOO+" 2/"+anno+"A/"+codiceAOO;
    }
	
	datiTemplateVO.setClassificazione(codiceClassificazione);
	
	String cognomeDirigente= sigasCParametroRepository.findByDescParametro(DoquiConstants.COGNOME_DIRIGENTE).getValoreString();
	String nomeDirigente= sigasCParametroRepository.findByDescParametro(DoquiConstants.NOME_DIRIGENTE).getValoreString();
	datiTemplateVO.setCognomeDirigente(cognomeDirigente);
	datiTemplateVO.setNomeDirigente(nomeDirigente);
	
	String mailSettoreTributi= sigasCParametroRepository.findByDescParametro(DoquiConstants.MAIL_SETTORE_TRIBUTI).getValoreString();
	datiTemplateVO.setMailSettoreTributi(mailSettoreTributi);
	
	datiTemplateVO.setDataProtocollazione(sigasDocumenti.getDataProtocollazione());
	datiTemplateVO.setNumeroProtocollo(sigasDocumenti.getNProtocollo());

	return datiTemplateVO;
}

}
