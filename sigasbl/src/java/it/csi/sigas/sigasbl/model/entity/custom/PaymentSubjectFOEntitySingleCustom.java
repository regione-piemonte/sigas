/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity.custom;

import javax.persistence.Entity;


@Entity
public class PaymentSubjectFOEntitySingleCustom extends PaymentSubjectFOEntityCustomBase {
	
    private String cap;
    private String comune;
    private String indirizzo;
	private String siglaProvincia;
	private String siglaProvinciaAzienda;
	private String codiceFiscale;
	private String codiceFiscalePIva;
	

	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public String getSiglaProvinciaAzienda() {
		return siglaProvinciaAzienda;
	}
	public void setSiglaProvinciaAzienda(String siglaProvinciaAzienda) {
		this.siglaProvinciaAzienda = siglaProvinciaAzienda;
	}

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
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCodiceFiscalePIva() {
		return codiceFiscalePIva;
	}
	public void setCodiceFiscalePIva(String codiceFiscalePIva) {
		this.codiceFiscalePIva = codiceFiscalePIva;
	}
	
	
	
}
