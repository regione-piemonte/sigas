/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the sigas_utente_provvisorio database table.
 * 
 */
@Entity
@Table(name="sigas_utente_provvisorio")
@NamedQuery(name="SigasUtenteProvvisorio.findAll", query="SELECT s FROM SigasUtenteProvvisorio s")
public class SigasUtenteProvvisorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_UTENTE_PROVVISORIO_IDUTENTEPROVV_GENERATOR" , sequenceName="SEQ_UTENTE_PROVVISORIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_UTENTE_PROVVISORIO_IDUTENTEPROVV_GENERATOR")
	@Column(name="id_utente_provv", unique=true, nullable=false)
	private long idUtenteProvv;

	@Column(name="id_pratica")
	private String idPratica;

	@Column(name="ins_date")
	private Timestamp insDate;

	@Column(name="ins_user")
	private String insUser;

	@Column(name="mod_date")
	private Timestamp modDate;

	@Column(name="mod_user")
	private String modUser;

	private String note;

	private String stato;

	//bi-directional many-to-one association to SigasDichiarante
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="id_soggetto_provvisorio")
	private SigasDichiarante sigasDichiarante;

	//bi-directional many-to-one association to SigasLegaleRappresent
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="id_legale_rapp")
	private SigasLegaleRappresent sigasLegaleRappresent;

	//bi-directional many-to-one association to SigasOperatore
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="id_operatore")
	private SigasOperatore sigasOperatore;

	public SigasUtenteProvvisorio() {
	}

	public long getIdUtenteProvv() {
		return this.idUtenteProvv;
	}

	public void setIdUtenteProvv(long idUtenteProvv) {
		this.idUtenteProvv = idUtenteProvv;
	}

	public String getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(String idPratica) {
		this.idPratica = idPratica;
	}

	public Timestamp getInsDate() {
		return this.insDate;
	}

	public void setInsDate(Timestamp insDate) {
		this.insDate = insDate;
	}

	public String getInsUser() {
		return this.insUser;
	}

	public void setInsUser(String insUser) {
		this.insUser = insUser;
	}

	public Timestamp getModDate() {
		return this.modDate;
	}

	public void setModDate(Timestamp modDate) {
		this.modDate = modDate;
	}

	public String getModUser() {
		return this.modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public SigasDichiarante getSigasDichiarante() {
		return this.sigasDichiarante;
	}

	public void setSigasDichiarante(SigasDichiarante sigasDichiarante) {
		this.sigasDichiarante = sigasDichiarante;
	}

	public SigasLegaleRappresent getSigasLegaleRappresent() {
		return this.sigasLegaleRappresent;
	}

	public void setSigasLegaleRappresent(SigasLegaleRappresent sigasLegaleRappresent) {
		this.sigasLegaleRappresent = sigasLegaleRappresent;
	}

	public SigasOperatore getSigasOperatore() {
		return this.sigasOperatore;
	}

	public void setSigasOperatore(SigasOperatore sigasOperatore) {
		this.sigasOperatore = sigasOperatore;
	}

}