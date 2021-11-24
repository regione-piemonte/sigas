/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import java.io.IOException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.integration.doqui.bean.KeyDocumentoActa;
import it.csi.sigas.sigasbl.integration.doqui.exception.FruitoreException;
import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;
import it.csi.sigas.sigasbl.model.vo.documenti.StatoDocumentoVO;
import it.csi.sigas.sigasbl.model.vo.documenti.TipoDocumentoVO;
import it.csi.sigas.sigasbl.request.documentazione.ConfermaDocumentazioneRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneBoRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneRequest;

public interface IDocumentazioneService {
	
	public List<AnagraficaSoggettoVO> ricercaAziendeAccreditato(String codFiscale);

	public List<TipoDocumentoVO> listaTipoDocumenti();

	public List<DocumentiVO> ricercaDocumenti(RicercaDocumentazioneRequest ricercaDocumentazioneRequest, String codFiscale);

	public void salvaDocumentazione(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale) throws BusinessException, ServiceException;

	public List<StatoDocumentoVO> listaStatoDocumenti();

	public List<AnagraficaSoggettoVO> ricercaAziendeDocumentiInoltrati();

	public List<DocumentiVO> ricercaDocumenti(RicercaDocumentazioneBoRequest ricercaDocumentazioneBoRequest);

	public void salvaDocumentazioneBO(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale);


	KeyDocumentoActa protocollaDocumentoFisico(boolean isProtocollazioneInUscitaSenzaDocumento, DocumentiVO documentiVO)
			throws IntegrationException, FruitoreException;

	byte[] getPacchettoDocumenti(Integer idDocumento) throws IOException;

	byte[] getDocumentoMaster(Integer idDocumento) throws IOException;

	byte[] getAllegato(Integer idDocumento, String nomeFile) throws IOException;
	
	public void eliminaDocumentazioneIndex(String uid);

	public DocumentiVO protocollaLetteraRisp(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale);

	public List<DocumentiVO> ricercaLetteraRisposta(String rifArchivio);

}
