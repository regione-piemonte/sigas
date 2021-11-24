/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity.custom;

import javax.persistence.Entity;


@Entity
public class PaymentSubjectFODetailEntityCustom extends PaymentSubjectFOEntityCustomBase {
	
    private String cap;
    private String comune;
    private String indirizzo;
	private String SiglaProvinciaAzienda;
	private String addizionaleLiquidata;

	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getAddizionaleLiquidata() {
		return addizionaleLiquidata;
	}
	public void setAddizionaleLiquidata(String addizionaleLiquidata) {
		this.addizionaleLiquidata = addizionaleLiquidata;
	}
	public String getSiglaProvinciaAzienda() {
		return SiglaProvinciaAzienda;
	}
	public void setSiglaProvinciaAzienda(String siglaProvinciaAzienda) {
		SiglaProvinciaAzienda = siglaProvinciaAzienda;
	}
}
