/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity.custom;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class SoggettoEntityCustom implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private long idAnag;
	private String codiceAzienda;
	private String denominazione;
	private double totCalcolato;
	private double totVersamenti;
	private long nProvince;
	private String validato;
	private Long[] allarmi;
	
	public long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(long idAnag) {
		this.idAnag = idAnag;
	}
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public long getnProvince() {
		return nProvince;
	}
	public void setnProvince(long nProvince) {
		this.nProvince = nProvince;
	}
	public double getTotCalcolato() {
		return totCalcolato;
	}
	public void setTotCalcolato(double totCalcolato) {
		this.totCalcolato = totCalcolato;
	}
	
	public double getTotVersamenti() {
		return totVersamenti;
	}
	public void setTotVersamenti(double totVersamenti) {
		this.totVersamenti = totVersamenti;
	}

	public String getValidato() {
		return validato;
	}
	public void setValidato(String validato) {
		this.validato = validato;
	}
	public Long[] getAllarmi() {
		return allarmi;
	}
	public void setAllarmi(Long[] allarmi) {
		this.allarmi = allarmi;
	}
	
}
