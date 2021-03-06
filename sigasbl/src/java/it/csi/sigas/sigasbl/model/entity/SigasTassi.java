/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="sigas_tassi")
@NamedQuery(name="SigasTassi.findAll", query="SELECT i FROM SigasTassi i")
public class SigasTassi implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_TASSI_IDTASSO_GENERATOR" , sequenceName="SEQ_TASSI", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_TASSI_IDTASSO_GENERATOR")
	@Column(name="id_tasso", unique=true, nullable=false)
	private long idTasso;

	@ManyToOne
	@JoinColumn(name="id_tipo_tasso", nullable=false)
	private SigasTipoTassi sigasTipoTassi;

	@Column(name="tasso")
	private double tasso;

	@Temporal(TemporalType.DATE)
	@Column(name="validita_start")
	private Date validitaStart;

	@Temporal(TemporalType.DATE)
	@Column(name="validita_end", nullable=false)
	private Date validitaEnd;
	
	@Column(name="ins_user", nullable=false)
	private String insUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ins_date", nullable=false)
	private Date insDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="mod_date", nullable=false)
	private Date modDate;
	 
	@Column(name="mod_user", nullable=false)
	private String modUser;

	@Version
	@Column(name="version", nullable=false)
	private long version;
	
	public SigasTassi() {
	}

	public long getIdTasso() {
		return idTasso;
	}

	public void setIdTasso(long idTasso) {
		this.idTasso = idTasso;
	}

	public SigasTipoTassi getSigasTipoTassi() {
		return sigasTipoTassi;
	}

	public void setSigasTipoTassi(SigasTipoTassi sigasTipoTassi) {
		this.sigasTipoTassi = sigasTipoTassi;
	}

	public double getTasso() {
		return tasso;
	}

	public void setTasso(double tasso) {
		this.tasso = tasso;
	}

	public Date getValiditaStart() {
		return validitaStart;
	}

	public void setValiditaStart(Date validitaStart) {
		this.validitaStart = validitaStart;
	}

	public Date getValiditaEnd() {
		return validitaEnd;
	}

	public void setValiditaEnd(Date validitaEnd) {
		this.validitaEnd = validitaEnd;
	}

	public String getInsUser() {
		return insUser;
	}

	public void setInsUser(String insUser) {
		this.insUser = insUser;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}


	
}
