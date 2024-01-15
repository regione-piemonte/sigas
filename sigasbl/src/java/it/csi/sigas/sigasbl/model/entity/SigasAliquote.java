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

@Entity
@Table(name="sigas_aliquote")
@NamedQuery(name="SigasAliquote.findAll", query="SELECT i FROM SigasAliquote i")
public class SigasAliquote extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_ALIQUOTE_IDALIQUOTA_GENERATOR" , sequenceName="SEQ_ALIQUOTE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_ALIQUOTE_IDALIQUOTA_GENERATOR")
	@Column(name="id_aliquota", unique=true, nullable=false)
	private long idAliquota;

	@ManyToOne
	@JoinColumn(name="id_tipo_aliquota", nullable=false)
	private SigasTipoAliquote sigasTipoAliquote;

	@Column(name="aliquota")
	private double aliquota;

	@Temporal(TemporalType.DATE)
	@Column(name="validita_start")
	private Date validitaStart;

	@Temporal(TemporalType.DATE)
	@Column(name="validita_end", nullable=false)
	private Date validitaEnd;

	@Column(name="prog_rigo")
	private String progRigo;	
	
	
	public SigasAliquote() {
	}

	public long getIdAliquota() {
		return idAliquota;
	}

	public void setIdAliquota(long idAliquota) {
		this.idAliquota = idAliquota;
	}

	public SigasTipoAliquote getSigasTipoAliquote() {
		return sigasTipoAliquote;
	}

	public void setSigasTipoAliquote(SigasTipoAliquote sigasTipoAliquote) {
		this.sigasTipoAliquote = sigasTipoAliquote;
	}

	public double getAliquota() {
		return aliquota;
	}

	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
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

	public String getProgRigo() {
		return progRigo;
	}

	public void setProgRigo(String progRigo) {
		this.progRigo = progRigo;
	}	
	
}
