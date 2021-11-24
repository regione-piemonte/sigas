/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.vo.accreditamento;

import javax.persistence.Column;

import it.csi.sigas.sigasbl.model.vo.luoghi.ComuneVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.RegioneVO;

public class DichiaranteVO{

	/**
	 *
	 */
	private static final long serialVersionUID = -6450653595297894754L;
	
	private Long idDichiarante;

	private String denominazione;
	private ProvinciaVO provincia;
	private ComuneVO comune;
	private RegioneVO regione;
	private String indirizzo;
	private String codiceAzienda;

	private String emailDichiarante;

	private String iban;

	private String note;

	private String pecDichiarante;

	private String telefonoDichiarante;
	
	// Per sapere se il dichiarante censito
	private Boolean flagCensito;
	
	


	public Long getIdDichiarante() {
		return idDichiarante;
	}

	public void setIdDichiarante(Long idDichiarante) {
		this.idDichiarante = idDichiarante;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}


	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	


	public Boolean getFlagCensito() {
		return flagCensito;
	}

	public void setFlagCensito(Boolean flagCensito) {
		this.flagCensito = flagCensito;
	}

	public ComuneVO getComune() {
		return comune;
	}

	public void setComune(ComuneVO comune) {
		this.comune = comune;
	}

	public RegioneVO getRegione() {
		return regione;
	}

	public void setRegione(RegioneVO regione) {
		this.regione = regione;
	}

	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public String getEmailDichiarante() {
		return emailDichiarante;
	}

	public void setEmailDichiarante(String emailDichiarante) {
		this.emailDichiarante = emailDichiarante;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPecDichiarante() {
		return pecDichiarante;
	}

	public void setPecDichiarante(String pecDichiarante) {
		this.pecDichiarante = pecDichiarante;
	}

	public String getTelefonoDichiarante() {
		return telefonoDichiarante;
	}

	public void setTelefonoDichiarante(String telefonoDichiarante) {
		this.telefonoDichiarante = telefonoDichiarante;
	}

	
}
