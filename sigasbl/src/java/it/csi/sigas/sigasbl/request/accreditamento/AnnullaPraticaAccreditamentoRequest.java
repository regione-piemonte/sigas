/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.accreditamento;

import java.io.Serializable;

public class AnnullaPraticaAccreditamentoRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Long idUtenteProvv;
	
	private String stato;
	
	private String note;

	
	

	public Long getIdUtenteProvv() {
		return idUtenteProvv;
	}

	public void setIdUtenteProvv(Long idUtenteProvv) {
		this.idUtenteProvv = idUtenteProvv;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
}
