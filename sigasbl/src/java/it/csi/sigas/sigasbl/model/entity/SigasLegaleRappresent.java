/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the sigas_legale_rappresent database table.
 * 
 */
@Entity
@Table(name="sigas_legale_rappresent")
@NamedQuery(name="SigasLegaleRappresent.findAll", query="SELECT s FROM SigasLegaleRappresent s")
public class SigasLegaleRappresent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_LEGALE_RAPPRESENT_IDLEGALERAPPRESENT_GENERATOR" , sequenceName="SEQ_T_LEGALE_RAPPRESENT", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_LEGALE_RAPPRESENT_IDLEGALERAPPRESENT_GENERATOR")
	@Column(name="id_legale_rappresent", unique=true, nullable=false)
	private long idLegaleRappresent;

	@Column(name="cf_legale_rappresent")
	private String cfLegaleRappresent;

	@Column(name="cognome_legale_rappresent")
	private String cognomeLegaleRappresent;

	@Column(name="data_insert")
	private Timestamp dataInsert;

	@Column(name="data_update")
	private Timestamp dataUpdate;

	@Column(name="email_legale_rappresent")
	private String emailLegaleRappresent;

	@Column(name="fk_utente_update")
	private BigDecimal fkUtenteUpdate;

	@Column(name="nome_legale_rappresent")
	private String nomeLegaleRappresent;

	@Column(name="telefono_legale_rappresent")
	private String telefonoLegaleRappresent;

	//bi-directional many-to-one association to SigasDichiarante
	@ManyToOne
	@JoinColumn(name="fk_dichiarante")
	private SigasDichiarante sigasDichiarante;

	//bi-directional many-to-one association to SigasUtenteProvvisorio
	@OneToMany(mappedBy="sigasLegaleRappresent")
	private List<SigasUtenteProvvisorio> sigasUtenteProvvisorios;

	public SigasLegaleRappresent() {
	}

	public long getIdLegaleRappresent() {
		return this.idLegaleRappresent;
	}

	public void setIdLegaleRappresent(long idLegaleRappresent) {
		this.idLegaleRappresent = idLegaleRappresent;
	}

	public String getCfLegaleRappresent() {
		return this.cfLegaleRappresent;
	}

	public void setCfLegaleRappresent(String cfLegaleRappresent) {
		this.cfLegaleRappresent = cfLegaleRappresent;
	}

	public String getCognomeLegaleRappresent() {
		return this.cognomeLegaleRappresent;
	}

	public void setCognomeLegaleRappresent(String cognomeLegaleRappresent) {
		this.cognomeLegaleRappresent = cognomeLegaleRappresent;
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

	public String getEmailLegaleRappresent() {
		return this.emailLegaleRappresent;
	}

	public void setEmailLegaleRappresent(String emailLegaleRappresent) {
		this.emailLegaleRappresent = emailLegaleRappresent;
	}

	public BigDecimal getFkUtenteUpdate() {
		return this.fkUtenteUpdate;
	}

	public void setFkUtenteUpdate(BigDecimal fkUtenteUpdate) {
		this.fkUtenteUpdate = fkUtenteUpdate;
	}

	public String getNomeLegaleRappresent() {
		return this.nomeLegaleRappresent;
	}

	public void setNomeLegaleRappresent(String nomeLegaleRappresent) {
		this.nomeLegaleRappresent = nomeLegaleRappresent;
	}

	public String getTelefonoLegaleRappresent() {
		return this.telefonoLegaleRappresent;
	}

	public void setTelefonoLegaleRappresent(String telefonoLegaleRappresent) {
		this.telefonoLegaleRappresent = telefonoLegaleRappresent;
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
		sigasUtenteProvvisorio.setSigasLegaleRappresent(this);

		return sigasUtenteProvvisorio;
	}

	public SigasUtenteProvvisorio removeSigasUtenteProvvisorio(SigasUtenteProvvisorio sigasUtenteProvvisorio) {
		getSigasUtenteProvvisorios().remove(sigasUtenteProvvisorio);
		sigasUtenteProvvisorio.setSigasLegaleRappresent(null);

		return sigasUtenteProvvisorio;
	}

}