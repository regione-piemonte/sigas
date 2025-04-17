/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import java.io.IOException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;
import it.csi.sigas.sigasbl.model.vo.documenti.StatoDocumentoVO;
import it.csi.sigas.sigasbl.model.vo.documenti.TipoDocumentoVO;
import it.csi.sigas.sigasbl.request.documentazione.ConfermaDocumentazioneRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneBoRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneRequest;

public interface IDocumentazioneDispatcher {

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<StatoDocumentoVO> listaStatoDocumenti();

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AnagraficaSoggettoVO> ricercaAziendeDocumentiInoltrati();
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<DocumentiVO> ricercaDocumenti(RicercaDocumentazioneBoRequest ricercaDocumentazioneBoRequest);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	void salvaDocumentazioneBO(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale);

	@PreAuthorize(value = AuthorizationRoles.HOME +" OR "+ AuthorizationRoles.UTENTE)
	byte[] getPacchettoDocumenti(Integer idDocumento) throws IOException;
	
	
	@PreAuthorize(value = AuthorizationRoles.HOME +" OR "+ AuthorizationRoles.UTENTE)
	byte[] getDocumentoMaster(Integer idDocumento) throws IOException;
	
	@PreAuthorize(value = AuthorizationRoles.HOME +" OR "+ AuthorizationRoles.UTENTE)
	byte[] getAllegato(Integer idDocumento, String nomeFile) throws IOException;
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	DocumentiVO protocollaLetteraRisp(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<DocumentiVO> ricercaLetteraRisposta(String rifArchivio);
	
//	@PreAuthorize(value = AuthorizationRoles.UTENTE)
//	public List<UtenteProvvisorioVO> ricercaPratiche(RicercaPraticheAccreditamentoRequest ricercaPraticheAccreditamentoRequest);
//	
//	@PreAuthorize(value = AuthorizationRoles.UTENTE)
//	public AccreditamentoVO ricercaPraticaAccreditamento(RicercaAccreditamentoRequest ricercaAccreditamentoRequest);
//
//	@PreAuthorize(value = AuthorizationRoles.HOME)
//	public void confermaPraticaAccreditamento(
//			ConfermaPraticaAccreditamentoRequest confermaPraticaAccreditamentoRequest, String user);
	
	
	
	//OPERATORE FO

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<AnagraficaSoggettoVO> ricercaAziendeAccreditato(String codFiscale);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<TipoDocumentoVO> listaTipoDocumenti();

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentiVO> ricercaDocumenti(RicercaDocumentazioneRequest ricercaDocumentazioneRequest, String codFiscale);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	DocumentiVO salvaDocumentazione(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale) throws BusinessException, ServiceException;

	

	

	



	
	




}
