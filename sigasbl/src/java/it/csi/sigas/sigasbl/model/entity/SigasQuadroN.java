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
@Table(name="sigas_quadro_n_utf")
@NamedQuery(name="SigasQudroN.findAll", query="SELECT i FROM SigasQuadroN i")
public class SigasQuadroN implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_QUADRO_N_IDQUADRON_GENERATOR" , sequenceName="SEQ_QUADRO_N_UTF", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_QUADRO_N_IDQUADRON_GENERATOR")
	@Column(name="id_quadro_n", unique=true, nullable=false)
	private long idQuadroN;

	@ManyToOne
	@JoinColumn(name="id_import", nullable=false)
	private SigasImportUTF sigasImport;

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
	
	@Column(name="imposta", nullable=false)
	private double imposta;
	
	public SigasQuadroN() {
	}
	
	public SigasQuadroN(
			SigasImportUTF sigasImport,
			Date dataEstrazione,
			String codiceDitta,
			String anno,
			String provincia,
			String quadro,
			String progRigo,
			double imposta) {
		
		this.dataEstrazione = dataEstrazione;
		this.codiceDitta = codiceDitta;
		this.anno = anno;
		this.provincia = provincia;
		this.quadro = quadro;
		this.progRigo = progRigo;
		this.imposta = imposta;
	}

	public SigasImportUTF getSigasImport() {
		return sigasImport;
	}

	public void setSigasImport(SigasImportUTF sigasImport) {
		this.sigasImport = sigasImport;
	}

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

	public String getQuadro() {
		return quadro;
	}

	public void setQuadro(String quadro) {
		this.quadro = quadro;
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


	public double getImposta() {
		return imposta;
	}

	public void setImposta(double imposta) {
		this.imposta = imposta;
	}

	public long getIdQuadroN() {
		return idQuadroN;
	}

	public void setIdQuadroN(long idQuadroN) {
		this.idQuadroN = idQuadroN;
	}

}
