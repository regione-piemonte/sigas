/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class AllarmeDocumentoRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idAnag;
	private Long idComunicazione;
	private String tipoAllarme;
	private boolean allarmeOn;
	private String nota;

	public Long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Long idAnag) {
		this.idAnag = idAnag;
	}
	public Long getIdComunicazione() {
		return idComunicazione;
	}
	public void setIdComunicazione(Long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}
	public String getTipoAllarme() {
		return tipoAllarme;
	}
	public void setTipoAllarme(String tipoAllarme) {
		this.tipoAllarme = tipoAllarme;
	}
	public boolean isAllarmeOn() {
		return allarmeOn;
	}
	public void setAllarmeOn(boolean allarmeOn) {
		this.allarmeOn = allarmeOn;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
}
