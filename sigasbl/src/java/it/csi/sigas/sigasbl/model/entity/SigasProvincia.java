/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
 * The persistent class for the sigas_provincia database table.
 * 
 */
@Entity
@Table(name="sigas_provincia")
@NamedQuery(name="SigasProvincia.findAll", query="SELECT i FROM SigasProvincia i")
public class SigasProvincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_PROVINCIA_IDPROVINCIA_GENERATOR" , sequenceName="SEQ_PROVINCIA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_PROVINCIA_IDPROVINCIA_GENERATOR")
	@Column(name="id_provincia", unique=true, nullable=false, precision=7)
	private long idProvincia;

	@Column(name="cod_istat_provincia", nullable=false, length=3)
	private String codIstatProvincia;

	@Column(name="denom_provincia", nullable=false, length=100)
	private String denomProvincia;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita", nullable=false)
	private Date inizioValidita;

	@Column(name="sigla_provincia", length=2)
	private String siglaProvincia;

	//bi-directional many-to-one association to SigasComune
	@OneToMany(mappedBy="sigasProvincia")
	private List<SigasComune> sigasComunes;
	
	//bi-directional many-to-one association to sigasSigasAnagraficaSoggetti
	@OneToMany(mappedBy="sigasProvincia")
	private List<SigasAnagraficaSoggetti> sigasAnagraficaSoggettis;	
		
	//bi-directional many-to-one association to IrbaTDichiarante
	@OneToMany(targetEntity=SigasDichiarante.class, fetch= FetchType.LAZY, mappedBy="sigasProvincia")
	private List<SigasDichiarante> sigasDichiaranteList;
	
	//bi-directional many-to-one association to sigasSigasAnagraficaSoggetti
	@OneToMany(mappedBy="sigasProvincia")
	private List<SigasDepositoCausionale> sigasDepositoCausionales;
	
	public List<SigasDichiarante> getSigasDichiaranteList() {
		return sigasDichiaranteList;
	}

	public void setSigasDichiaranteList(List<SigasDichiarante> sigasDichiaranteList) {
		this.sigasDichiaranteList = sigasDichiaranteList;
	}


	public SigasProvincia() {
	}

	public long getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getCodIstatProvincia() {
		return this.codIstatProvincia;
	}

	public void setCodIstatProvincia(String codIstatProvincia) {
		this.codIstatProvincia = codIstatProvincia;
	}

	public String getDenomProvincia() {
		return this.denomProvincia;
	}

	public void setDenomProvincia(String denomProvincia) {
		this.denomProvincia = denomProvincia;
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

	public String getSiglaProvincia() {
		return this.siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public List<SigasComune> getSigasComunes() {
		return this.sigasComunes;
	}

	public void setSigasComunes(List<SigasComune> sigasComunes) {
		this.sigasComunes = sigasComunes;
	}

	public SigasComune addSigasComune(SigasComune sigasComune) {
		getSigasComunes().add(sigasComune);
		sigasComune.setSigasProvincia(this);

		return sigasComune;
	}

	public SigasComune removeSigasComune(SigasComune sigasComune) {
		getSigasComunes().remove(sigasComune);
		sigasComune.setSigasProvincia(null);

		return sigasComune;
	}

	public List<SigasAnagraficaSoggetti> getSigasAnagraficaSoggettis() {
		return sigasAnagraficaSoggettis;
	}

	public void setSigasSigasAnagraficaSoggettis(List<SigasAnagraficaSoggetti> sigasAnagraficaSoggettis) {
		this.sigasAnagraficaSoggettis = sigasAnagraficaSoggettis;
	}

	public List<SigasDepositoCausionale> getSigasDepositoCausionales() {
		return sigasDepositoCausionales;
	}

	public void setSigasDepositoCausionales(List<SigasDepositoCausionale> sigasDepositoCausionales) {
		this.sigasDepositoCausionales = sigasDepositoCausionales;
	}	

}
