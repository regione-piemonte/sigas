/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class RicercaConsumiRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private String anno;
	private String validato;
	
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getValidato() {
		return validato;
	}
	public void setValidato(String validato) {
		this.validato = validato;
	}
	
	
}
