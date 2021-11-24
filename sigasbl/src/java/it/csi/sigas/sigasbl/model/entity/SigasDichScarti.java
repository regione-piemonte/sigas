/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;

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

@Entity
@Table(name="sigas_dich_scarti")
@NamedQuery(name="SigasDichScarti.findAll", query="SELECT i FROM SigasDichScarti i")
public class SigasDichScarti implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_DICH_SCARTI_IDSCARTI_GENERATOR" , sequenceName="SEQ_DICH_SCARTI", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_DICH_SCARTI_IDSCARTI_GENERATOR")
	@Column(name="id_scarti", unique=true, nullable=false)
	private long idScarti;

	@ManyToOne
	@JoinColumn(name="id_consumi", nullable=false)
	private SigasDichConsumi sigasDichConsumi;
	
	@Column(name="provincia")
	private String provincia;
	
	@Column(name="consumi")
	private int consumi;
	
	@Column(name="aliquota")
	private double aliquota;
	
	@Column(name="imposta")
	private double imposta;
	
	@Column(name="conciliato")
	private boolean conciliato;
	
	@Column(name="desc_uso_scarto")
	private String descUsoScarto;
	
	public SigasDichScarti() {
	}

	public long getIdScarti() {
		return idScarti;
	}

	public void setIdScarti(long idScarti) {
		this.idScarti = idScarti;
	}

	public SigasDichConsumi getSigasDichConsumi() {
		return sigasDichConsumi;
	}

	public void setSigasDichConsumi(SigasDichConsumi sigasDichConsumi) {
		this.sigasDichConsumi = sigasDichConsumi;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public int getConsumi() {
		return consumi;
	}

	public void setConsumi(int consumi) {
		this.consumi = consumi;
	}

	public double getAliquota() {
		return aliquota;
	}

	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}

	public double getImposta() {
		return imposta;
	}

	public void setImposta(double imposta) {
		this.imposta = imposta;
	}

	public boolean isConciliato() {
		return conciliato;
	}

	public void setConciliato(boolean conciliato) {
		this.conciliato = conciliato;
	}

	public String getDescUsoScarto() {
		return descUsoScarto;
	}

	public void setDescUsoScarto(String descUsoScarto) {
		this.descUsoScarto = descUsoScarto;
	}

	

}
