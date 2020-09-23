/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class ValidazioneRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idAnag;
	private String anno;
	private boolean validato;
	
	public Long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Long idAnag) {
		this.idAnag = idAnag;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public boolean isValidato() {
		return validato;
	}
	public void setValidato(boolean validato) {
		this.validato = validato;
	}	
	
}
