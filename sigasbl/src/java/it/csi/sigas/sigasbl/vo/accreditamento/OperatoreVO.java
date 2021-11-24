/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.vo.accreditamento;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import it.csi.sigas.sigasbl.web.serializer.CustomDateDeserializer;
import it.csi.sigas.sigasbl.web.serializer.CustomDateSerializer;


public class OperatoreVO{

	private static final long serialVersionUID = 4917524949915963523L;

	private String cognome;
	private String nome;
	private String codiceFiscale;
	private String telefono;
	private String email;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	
	private List<Long> servizi;

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String mail) {
		this.email = mail;
	}

	

	public List<Long> getServizi() {
		return servizi;
	}

	public void setServizi(List<Long> servizi) {
		this.servizi = servizi;
	}

}