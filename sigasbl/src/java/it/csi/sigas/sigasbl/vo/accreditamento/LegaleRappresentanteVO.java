/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.vo.accreditamento;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import it.csi.sigas.sigasbl.web.serializer.CustomDateDeserializer;
import it.csi.sigas.sigasbl.web.serializer.CustomDateSerializer;

public class LegaleRappresentanteVO  {

	/**
	 *
	 */

	private static final long serialVersionUID = 9085503244961215152L;

	private String cognome;
	private String nome;
	private String codiceFiscale;
	private String telefono;
	private String email;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date dataDiNascita;
	private Long idProvincia;
	private Long idStatoEstero;
	private Long idComune;
	private String cittaEstera;

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

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public Long getIdStatoEstero() {
		return idStatoEstero;
	}

	public void setIdStatoEstero(Long idStatoEstero) {
		this.idStatoEstero = idStatoEstero;
	}

	public Long getIdComune() {
		return idComune;
	}

	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}

	public String getCittaEstera() {
		return cittaEstera;
	}

	public void setCittaEstera(String cittaEstera) {
		this.cittaEstera = cittaEstera;
	}

}