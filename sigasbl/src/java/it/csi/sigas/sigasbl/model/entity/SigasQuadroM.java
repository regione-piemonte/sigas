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
@Table(name="sigas_quadro_m_utf")
@NamedQuery(name="SigasQudroM.findAll", query="SELECT i FROM SigasQuadroM i")
public class SigasQuadroM extends EntityQuadroBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_QUADRO_M_IDQUADROM_GENERATOR" , sequenceName="SEQ_QUADRO_M_UTF", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_QUADRO_M_IDQUADROM_GENERATOR")
	@Column(name="id_quadro_m", unique=true, nullable=false)
	private long idQuadroM;

	@ManyToOne
	@JoinColumn(name="id_import", nullable=false)
	private SigasImportUTF sigasImport;
	
	/*
	@Temporal(TemporalType.DATE)
	@Column(name="data_estrazione", nullable=false)
	private Date dataEstrazione;
	
	@Column(name="codice_ditta", nullable=false)
	private String codiceDitta;
	
	@Column(name="anno", nullable=false)
	private String anno;
	
	@Column(name="provincia", nullable=false)
	private String provincia;
	
	@Column(name="quadro", nullable=false)
	private String quadro;
	
	@Column(name="prog_rigo", nullable=false)
	private String progRigo;
	*/
	
	@Column(name="fascia_climatica", nullable=false)
	private String fasciaClimatica;
	
	@Column(name="consumi", nullable=false)
	private int consumi;
	
	@Column(name="aliquota", nullable=false)
	private double aliquota;
	
	@Column(name="imposta", nullable=false)
	private double imposta;
	
	public SigasQuadroM() {
	}
	
	public SigasQuadroM(
			SigasImportUTF sigasImport,
			Date dataEstrazione,
			String codiceDitta,
			String anno,
			String provincia,
			String quadro,
			String progRigo,
			String fasciaClimatica,
			int consumi,
			double aliquota,
			double imposta) {
		
		this.dataEstrazione = dataEstrazione;
		this.codiceDitta = codiceDitta;
		this.anno = anno;
		this.provincia = provincia;
		this.quadro = quadro;
		this.fasciaClimatica=fasciaClimatica;
		this.progRigo = progRigo;
		this.consumi = consumi;
		this.aliquota = aliquota;
		this.imposta = imposta;
		
	}


	public SigasImportUTF getSigasImport() {
		return sigasImport;
	}

	public void setSigasImport(SigasImportUTF sigasImport) {
		this.sigasImport = sigasImport;
	}
	/*
	public Date getDataEstrazione() {
		return dataEstrazione;
	}

	public void setDataEstrazione(Date dataEstrazione) {
		this.dataEstrazione = dataEstrazione;
	}

	public String getCodiceDitta() {
		return codiceDitta;
	}

	public void setCodiceDitta(String codiceDitta) {
		this.codiceDitta = codiceDitta;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getProgRigo() {
		return progRigo;
	}

	public void setProgRigo(String progRigo) {
		this.progRigo = progRigo;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	*/

	public double getAliquota() {
		return aliquota;
	}

	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}

	public int getConsumi() {
		return consumi;
	}

	public void setConsumi(int consumi) {
		this.consumi = consumi;
	}

	public long getIdQuadroM() {
		return idQuadroM;
	}

	public void setIdQuadroM(long idQuadroM) {
		this.idQuadroM = idQuadroM;
	}

	public String getFasciaClimatica() {
		return fasciaClimatica;
	}

	public void setFasciaClimatica(String fasciaClimatica) {
		this.fasciaClimatica = fasciaClimatica;
	}

	public String getQuadro() {
		return quadro;
	}

	public void setQuadro(String quadro) {
		this.quadro = quadro;
	}

	public double getImposta() {
		return imposta;
	}

	public void setImposta(double imposta) {
		this.imposta = imposta;
	}

}
