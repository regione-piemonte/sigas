/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.documentazione;

import java.io.Serializable;

import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;

public class ConfermaDocumentazioneRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private DocumentiVO documentiVO;
	
	private DocumentiVO letteraRisposta;

	public DocumentiVO getDocumentiVO() {
		return documentiVO;
	}

	public void setDocumentiVO(DocumentiVO documentiVO) {
		this.documentiVO = documentiVO;
	}

	public DocumentiVO getLetteraRisposta() {
		return letteraRisposta;
	}

	public void setLetteraRisposta(DocumentiVO letteraRisposta) {
		this.letteraRisposta = letteraRisposta;
	}

	
	
	
	
}
