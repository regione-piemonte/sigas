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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
