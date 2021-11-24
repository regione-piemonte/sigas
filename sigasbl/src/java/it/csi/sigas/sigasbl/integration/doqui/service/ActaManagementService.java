/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.service;

import java.util.List;

import it.csi.sigas.sigasbl.integration.doqui.bean.DocumentoActa;
import it.csi.sigas.sigasbl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.sigas.sigasbl.integration.doqui.bean.DocumentoProtocollatoActa;
import it.csi.sigas.sigasbl.integration.doqui.bean.KeyDocumentoActa;
import it.csi.sigas.sigasbl.integration.doqui.bean.UtenteActa;
import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;

public interface ActaManagementService {
	
	
	public void init();
	
	
	public KeyDocumentoActa archiviaDocumentoLogico(DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa protocollaDocumentoLogico(DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa archiviaDocumentoFisico(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa protocollaDocumentoFisico(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa,boolean isProtocollazioneInUscitaSenzaDocumento) throws IntegrationException;
	
	public List<DocumentoElettronicoActa> ricercaDocumentoByParolaChiave(String parolaChiave, UtenteActa utenteActa) throws IntegrationException;	// 20200717_LC
	
	public void associaDocumentoFisico(DocumentoElettronicoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa aggiungiAllegato(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa, String objectIdClassificazionePadre, String numeroProtocolloPadre, String pkAllegato) throws IntegrationException;
	
	public List<DocumentoProtocollatoActa>  ricercaDocumentoProtocollato(String numProtocollo, UtenteActa utenteActa) throws IntegrationException;
	
	// 20200626_LC
	public  ObjectIdType spostaDocumento(DocumentoElettronicoActa documentoElettronicoActa, String numeroProtocollo, UtenteActa utenteActa) throws IntegrationException;

	// 20200709_LC
	public void aggiornaPropertiesActaPostSpostamento(String documentoId, String newParolaChiave, String newOggetto, UtenteActa utenteActa) throws IntegrationException;

	// 20200717_LC
	public List<DocumentoElettronicoActa> ricercaDocumentoByObjectIdDocumento(String objectIdDocumento, UtenteActa utenteActa) throws IntegrationException;

	// 20200825_LC
	public List<DocumentoElettronicoActa> ricercaDocumentoByObjectIdDocumentoFisico(String objectIdDocumentoFisico, UtenteActa utenteActa) throws IntegrationException;
	
	
	// 20200728_LC
	public  ObjectIdType copiaDocumento(DocumentoElettronicoActa documentoElettronicoActa, String numeroProtocollo, UtenteActa utenteActa) throws IntegrationException;

	// 20200804_LC
	public String getParolaChiaveByObjectIdDocumento(String objectIdDocumento, UtenteActa utenteActa) throws IntegrationException;
	
	public byte[] download(String anno, String protocollo);
	
	public byte[] downloadMaster(String anno, String protocollo);
	
	public byte[] downloadAllegato(String anno, String protocollo, String descrizioneAllegato) throws it.doqui.acta.acaris.relationshipsservice.AcarisException;
} 