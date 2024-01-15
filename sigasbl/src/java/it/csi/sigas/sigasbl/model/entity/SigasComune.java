/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the sigas_comune database table.
 * 
 */
@Entity
@Table(name="sigas_comune")
@NamedQuery(name="SigasComune.findAll", query="SELECT i FROM SigasComune i")
public class SigasComune implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	
	//**** SEQUENZE NON PRESENTE NEL DB **************************
	
	//@SequenceGenerator(name="SIGAS_COMUNE_IDCOMUNE_GENERATOR" , sequenceName="SEQ_COMUNE", allocationSize = 1)
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_COMUNE_IDCOMUNE_GENERATOR")
	@Column(name="id_comune", unique=true, nullable=false, precision=8)
	private long idComune;

	@Column(length=5)
	private String cap;

	@Column(name="cod_belfiore_comune", nullable=false, length=4)
	private String codBelfioreComune;

	@Column(name="cod_istat_comune", nullable=false, length=6)
	private String codIstatComune;

	@Column(name="denom_comune", nullable=false, length=100)
	private String denomComune;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita", nullable=false)
	private Date inizioValidita;

	//bi-directional many-to-one association to IrbaDProvincia
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_provincia", nullable=false)
	private SigasProvincia sigasProvincia;
		
	//bi-directional many-to-one association to IrbaTDichiarante
	@OneToMany(targetEntity=SigasDichiarante.class, fetch= FetchType.LAZY, mappedBy="sigasComune")
	private List<SigasDichiarante> sigasDichiaranteList;
	
	public List<SigasDichiarante> getSigasDichiaranteList() {
		return sigasDichiaranteList;
	}

	public void setSigasDichiaranteList(List<SigasDichiarante> sigasDichiaranteList) {
		this.sigasDichiaranteList = sigasDichiaranteList;
	}	

	public SigasComune() {
	}

	public long getIdComune() {
		return this.idComune;
	}

	public void setIdComune(long idComune) {
		this.idComune = idComune;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCodBelfioreComune() {
		return this.codBelfioreComune;
	}

	public void setCodBelfioreComune(String codBelfioreComune) {
		this.codBelfioreComune = codBelfioreComune;
	}

	public String getCodIstatComune() {
		return this.codIstatComune;
	}

	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	public String getDenomComune() {
		return this.denomComune;
	}

	public void setDenomComune(String denomComune) {
		this.denomComune = denomComune;
	}

	public Date getFineValidita() {
		return this.fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public Date getInizioValidita() {
		return this.inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

	public SigasProvincia getSigasProvincia() {
		return this.sigasProvincia;
	}

	public void setSigasProvincia(SigasProvincia sigasProvincia) {
		this.sigasProvincia = sigasProvincia;
	}


}
