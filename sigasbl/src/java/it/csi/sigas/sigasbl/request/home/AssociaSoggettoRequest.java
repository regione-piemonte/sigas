/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;

public class AssociaSoggettoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "soggettoNew non deve essere vuoto")
	private AnagraficaSoggettoVO soggettoNew;

	@NotNull(message = "soggettoSelezionato non deve essere vuoto")
	private AnagraficaSoggettoVO soggettoSelezionato;

	public AnagraficaSoggettoVO getSoggettoNew() {
		return soggettoNew;
	}

	public void setSoggettoNew(AnagraficaSoggettoVO soggettoNew) {
		this.soggettoNew = soggettoNew;
	}

	public AnagraficaSoggettoVO getSoggettoSelezionato() {
		return soggettoSelezionato;
	}

	public void setSoggettoSelezionato(AnagraficaSoggettoVO soggettoSelezionato) {
		this.soggettoSelezionato = soggettoSelezionato;
	}

}
