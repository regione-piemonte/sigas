/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;

public class ConfermaSoggettoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "soggetto non deve essere vuoto")
	private AnagraficaSoggettoVO soggetto;
	
	private String annualita;
	
	private String ownerOperazione;

	public AnagraficaSoggettoVO getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(AnagraficaSoggettoVO soggetto) {
		this.soggetto = soggetto;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public String getOwnerOperazione() {
		return ownerOperazione;
	}

	public void setOwnerOperazione(String ownerOperazione) {
		this.ownerOperazione = ownerOperazione;
	}	

}
