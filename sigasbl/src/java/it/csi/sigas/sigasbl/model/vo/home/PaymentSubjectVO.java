/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class PaymentSubjectVO implements VO {

	private static final long serialVersionUID = 1L;
	private String id;
	private Long idAnag;
	private String codiceAzienda;
	private String nomeAzienda;
	private String denominazione;
    private String siglaProvincia;
    private String siglaProvinciaAzienda;
    private String comune;
    private String cap;
    private String indirizzo;
    
	private String mesi;
	private String totale;
	private String provincie;

	private String codiceFiscale;
	private String codiceFiscalePIva;
	
	public Long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Long idAnag) {
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
	public String getNomeAzienda() {
		return nomeAzienda;
	}
	public void setNomeAzienda(String nomeAzienda) {
		this.nomeAzienda = nomeAzienda;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
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
	public String getMesi() {
		return mesi;
	}
	public void setMesi(String mesi) {
		this.mesi = mesi;
	}
	public String getTotale() {
		return totale;
	}
	public void setTotale(String totale) {
		this.totale = totale;
	}
	public String getProvincie() {
		return provincie;
	}
	public void setProvincie(String provincie) {
		this.provincie = provincie;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
