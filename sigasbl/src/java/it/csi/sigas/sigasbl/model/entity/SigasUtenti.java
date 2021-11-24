/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the sigas_utenti database table.
 * 
 */
@Entity
@Table(name="sigas_utenti")
@NamedQuery(name="SigasUtenti.findAll", query="SELECT s FROM SigasUtenti s")
public class SigasUtenti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_UTENTI_IDUTENTE_GENERATOR" , sequenceName="SEQ_UTENTI", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_UTENTI_IDUTENTE_GENERATOR")
	@Column(name="id_utente")
	private long idUtente;

	@Column(name="codice_fiscale")
	private String codiceFiscale;

	@Column(name="id_utente_provv")
	private BigDecimal idUtenteProvv;

	//bi-directional many-to-one association to SigasRuolo
	@ManyToOne
	@JoinColumn(name="ruolo")
	private SigasRuolo sigasRuolo;

	public SigasUtenti() {
	}

	public long getIdUtente() {
		return this.idUtente;
	}

	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public BigDecimal getIdUtenteProvv() {
		return this.idUtenteProvv;
	}

	public void setIdUtenteProvv(BigDecimal idUtenteProvv) {
		this.idUtenteProvv = idUtenteProvv;
	}

	public SigasRuolo getSigasRuolo() {
		return this.sigasRuolo;
	}

	public void setSigasRuolo(SigasRuolo sigasRuolo) {
		this.sigasRuolo = sigasRuolo;
	}

}