/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.user;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import it.csi.sigas.sigasbl.common.rest.VO;

public class ProfilaturaVO implements VO {

	private static final long serialVersionUID = -3179072238186450660L;

	private List<String> useCase;
	private List<ServizioVO> serviziAbilitati;
	private String codiceFiscaleUtente;
	private String nome;
	private String cognome;
	
	private Boolean privatoNonAccareditato;
	private Boolean abilitaPrivato;
	private Boolean abilitaUtenteRegione;
	
	private String ruoli;
	
	private String messaggioWarning;
	private Boolean showMessage;
	private String levelMessage;

	// DOGANIERE
	private Boolean abilitaConsultaDoganiere;

	public List<String> getUseCase() {
		return useCase;
	}

	public void setUseCase(List<String> useCase) {
		this.useCase = useCase;
	}

	public List<ServizioVO> getServiziAbilitati() {
		return serviziAbilitati;
	}

	public void setServiziAbilitati(List<ServizioVO> serviziAbilitati) {
		this.serviziAbilitati = serviziAbilitati;
	}

	public String getCodiceFiscaleUtente() {
		return codiceFiscaleUtente;
	}

	public void setCodiceFiscaleUtente(String codiceFiscaleUtente) {
		this.codiceFiscaleUtente = codiceFiscaleUtente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Boolean getAbilitaConsultaDoganiere() {
		return abilitaConsultaDoganiere;
	}

	public void setAbilitaConsultaDoganiere(Boolean abilitaConsultaDoganiere) {
		this.abilitaConsultaDoganiere = abilitaConsultaDoganiere;
	}
	
	

	public Boolean getAbilitaPrivato() {
		return abilitaPrivato;
	}

	public void setAbilitaPrivato(Boolean abilitaPrivato) {
		this.abilitaPrivato = abilitaPrivato;
	}

	public Boolean getAbilitaUtenteRegione() {
		return abilitaUtenteRegione;
	}

	public void setAbilitaUtenteRegione(Boolean abilitaUtenteRegione) {
		this.abilitaUtenteRegione = abilitaUtenteRegione;
	}
	
	
	

	public Boolean getPrivatoNonAccareditato() {
		return privatoNonAccareditato;
	}

	public void setPrivatoNonAccareditato(Boolean privatoNonAccareditato) {
		this.privatoNonAccareditato = privatoNonAccareditato;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getRuoli() {
		return ruoli;
	}

	public void setRuoli(String ruoli) {
		this.ruoli = ruoli;
	}

	public String getMessaggioWarning() {
		return messaggioWarning;
	}

	public void setMessaggioWarning(String messaggioWarning) {
		this.messaggioWarning = messaggioWarning;
	}

	public Boolean getShowMessage() {
		return showMessage;
	}

	public void setShowMessage(Boolean showMessage) {
		this.showMessage = showMessage;
	}

	public String getLevelMessage() {
		return levelMessage;
	}

	public void setLevelMessage(String levelMessage) {
		this.levelMessage = levelMessage;
	}
	
	
	

}
