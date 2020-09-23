/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.utf;

import java.io.File;
import java.io.Serializable;

public class UTFRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private File utf;
	private String annualita;
	
	public File getUtf() {
		return utf;
	}
	public void setUtf(File utf) {
		this.utf = utf;
	}
	public String getAnnualita() {
		return annualita;
	}
	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}
	
}
