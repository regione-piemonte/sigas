/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import java.io.IOException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.dispatcher.IDocumentazioneDispatcher;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;
import it.csi.sigas.sigasbl.model.vo.documenti.StatoDocumentoVO;
import it.csi.sigas.sigasbl.model.vo.documenti.TipoDocumentoVO;
import it.csi.sigas.sigasbl.request.documentazione.ConfermaDocumentazioneRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneBoRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneRequest;
import it.csi.sigas.sigasbl.service.IDocumentazioneService;
 

@Component
public class DocumentazioneDispatcherImpl implements IDocumentazioneDispatcher {
	
 
	@Autowired
	private IDocumentazioneService iDocumentazioneService;


	@Override
	public List<StatoDocumentoVO> listaStatoDocumenti() {
		return iDocumentazioneService.listaStatoDocumenti();
	}
	
	
	@Override
	public List<AnagraficaSoggettoVO> ricercaAziendeDocumentiInoltrati() {
		return iDocumentazioneService.ricercaAziendeDocumentiInoltrati();
	}
	
	
	@Override
	public List<DocumentiVO> ricercaDocumenti(RicercaDocumentazioneBoRequest ricercaDocumentazioneBoRequest) {
		return iDocumentazioneService.ricercaDocumenti(ricercaDocumentazioneBoRequest);
	}
	
	@Override
	public void salvaDocumentazioneBO(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale) {
		 iDocumentazioneService.salvaDocumentazioneBO(confermaDocumentazioneRequest, codFiscale);
		
	}	
	
	@Override
	public List<DocumentiVO> ricercaLetteraRisposta(String rifArchivio) {
		return iDocumentazioneService.ricercaLetteraRisposta(rifArchivio);
	}
	
	//OPERATORE FO	

	@Override
	public List<AnagraficaSoggettoVO> ricercaAziendeAccreditato(String codFiscale) {
		return iDocumentazioneService.ricercaAziendeAccreditato(codFiscale);
	}



	@Override
	public List<TipoDocumentoVO> listaTipoDocumenti() {
		return iDocumentazioneService.listaTipoDocumenti();
	}



	@Override
	public List<DocumentiVO> ricercaDocumenti(RicercaDocumentazioneRequest ricercaDocumentazioneRequest, String codFiscale) { 
		return iDocumentazioneService.ricercaDocumenti(ricercaDocumentazioneRequest, codFiscale);
	}



	@Override
	public DocumentiVO salvaDocumentazione(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale) throws BusinessException, ServiceException {
			return iDocumentazioneService.salvaDocumentazione(confermaDocumentazioneRequest, codFiscale);		
	}
	
	
	@Override
	public byte[] getPacchettoDocumenti(Integer idDocumento) throws IOException {
		return iDocumentazioneService.getPacchettoDocumenti(idDocumento);
		
	}
	
	@Override
	public byte[] getDocumentoMaster(Integer idDocumento) throws IOException {
		return iDocumentazioneService.getDocumentoMaster(idDocumento);
		
	}
	
	@Override
	public byte[] getAllegato(Integer idDocumento, String nomeFile) throws IOException {
		return iDocumentazioneService.getAllegato(idDocumento, nomeFile);
		
	}


	@Override
	public DocumentiVO protocollaLetteraRisp(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale) {
		return iDocumentazioneService.protocollaLetteraRisp(confermaDocumentazioneRequest, codFiscale);
		
	}


	




}
