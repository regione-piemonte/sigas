/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the sigas_operatore database table.
 * 
 */
@Entity
@Table(name="sigas_operatore")
@NamedQuery(name="SigasOperatore.findAll", query="SELECT s FROM SigasOperatore s")
public class SigasOperatore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_OPERATORE_IDOPERATORE_GENERATOR" , sequenceName="SEQ_T_OPERATORE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_OPERATORE_IDOPERATORE_GENERATOR")
	@Column(name="id_operatore", unique=true, nullable=false)
	private long idOperatore;

	@Column(name="cf_operatore")
	private String cfOperatore;

	@Column(name="cognome_operatore")
	private String cognomeOperatore;

	@Column(name="data_insert")
	private Timestamp dataInsert;

	@Column(name="data_update")
	private Timestamp dataUpdate;

	@Column(name="email_operatore")
	private String emailOperatore;

	@Column(name="nome_operatore")
	private String nomeOperatore;

	@Column(name="telefono_operatore")
	private String telefonoOperatore;

	//bi-directional many-to-one association to SigasDichiarante
	@ManyToOne
	@JoinColumn(name="fk_dichiarante")
	private SigasDichiarante sigasDichiarante;

	//bi-directional many-to-one association to SigasUtenteProvvisorio
	@OneToMany(mappedBy="sigasOperatore")
	private List<SigasUtenteProvvisorio> sigasUtenteProvvisorios;

	public SigasOperatore() {
	}

	public long getIdOperatore() {
		return this.idOperatore;
	}

	public void setIdOperatore(long idOperatore) {
		this.idOperatore = idOperatore;
	}

	public String getCfOperatore() {
		return this.cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	public String getCognomeOperatore() {
		return this.cognomeOperatore;
	}

	public void setCognomeOperatore(String cognomeOperatore) {
		this.cognomeOperatore = cognomeOperatore;
	}

	public Timestamp getDataInsert() {
		return this.dataInsert;
	}

	public void setDataInsert(Timestamp dataInsert) {
		this.dataInsert = dataInsert;
	}

	public Timestamp getDataUpdate() {
		return this.dataUpdate;
	}

	public void setDataUpdate(Timestamp dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

	public String getEmailOperatore() {
		return this.emailOperatore;
	}

	public void setEmailOperatore(String emailOperatore) {
		this.emailOperatore = emailOperatore;
	}

	public String getNomeOperatore() {
		return this.nomeOperatore;
	}

	public void setNomeOperatore(String nomeOperatore) {
		this.nomeOperatore = nomeOperatore;
	}

	public String getTelefonoOperatore() {
		return this.telefonoOperatore;
	}

	public void setTelefonoOperatore(String telefonoOperatore) {
		this.telefonoOperatore = telefonoOperatore;
	}

	public SigasDichiarante getSigasDichiarante() {
		return this.sigasDichiarante;
	}

	public void setSigasDichiarante(SigasDichiarante sigasDichiarante) {
		this.sigasDichiarante = sigasDichiarante;
	}

	public List<SigasUtenteProvvisorio> getSigasUtenteProvvisorios() {
		return this.sigasUtenteProvvisorios;
	}

	public void setSigasUtenteProvvisorios(List<SigasUtenteProvvisorio> sigasUtenteProvvisorios) {
		this.sigasUtenteProvvisorios = sigasUtenteProvvisorios;
	}

	public SigasUtenteProvvisorio addSigasUtenteProvvisorio(SigasUtenteProvvisorio sigasUtenteProvvisorio) {
		getSigasUtenteProvvisorios().add(sigasUtenteProvvisorio);
		sigasUtenteProvvisorio.setSigasOperatore(this);

		return sigasUtenteProvvisorio;
	}

	public SigasUtenteProvvisorio removeSigasUtenteProvvisorio(SigasUtenteProvvisorio sigasUtenteProvvisorio) {
		getSigasUtenteProvvisorios().remove(sigasUtenteProvvisorio);
		sigasUtenteProvvisorio.setSigasOperatore(null);

		return sigasUtenteProvvisorio;
	}

}