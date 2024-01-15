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
@Table(name="sigas_quadro_h_utf")
@NamedQuery(name="SigasQudroH.findAll", query="SELECT i FROM SigasQuadroH i")
public class SigasQuadroH extends EntityQuadroBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_QUADRO_H_IDQUADROH_GENERATOR" , sequenceName="SEQ_QUADRO_H_UTF", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_QUADRO_H_IDQUADROH_GENERATOR")
	@Column(name="id_quadro_h", unique=true, nullable=false)
	private long idQuadroH;

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
	
	@Column(name="ubicazione")
	private String ubicazione;
	
	@Column(name="segno_importo")
	private String segnoImporto;
	
	@Column(name="importo" )
	private double importo;
	
	@Column(name="metri_cubi" )
	private double mc;
	
	@Column(name="aliquota")
	private double aliquota;
	
	@Column(name="totale_rigo", nullable=false)
	private double totaleRigo;
	
	public SigasQuadroH() {
	}
	
	public SigasQuadroH(
			SigasImportUTF sigasImport,
			Date dataEstrazione,
			String codiceDitta,
			String anno,
			String provincia,
			String quadro,
			String progRigo,
			String ubicazione,
			String segnoImporto,
			double importo,
			int mc,
			double aliquota,
			double totaleRigo) {
		
		this.dataEstrazione = dataEstrazione;
		this.codiceDitta = codiceDitta;
		this.anno = anno;
		this.provincia = provincia;
		this.quadro = quadro;
		this.progRigo = progRigo;
		this.ubicazione = ubicazione;
		this.segnoImporto = segnoImporto;
		this.importo = importo;
		this.mc = mc;
		this.aliquota = aliquota;
		this.totaleRigo = totaleRigo;
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
	*/

	public long getIdQuadroH() {
		return idQuadroH;
	}

	public void setIdQuadroH(long idQuadroH) {
		this.idQuadroH = idQuadroH;
	}
	
	/*
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
	*/

	public String getUbicazione() {
		return ubicazione;
	}

	public void setUbicazione(String ubicazione) {
		this.ubicazione = ubicazione;
	}

	public String getSegnoImporto() {
		return segnoImporto;
	}

	public void setSegnoImporto(String segnoImporto) {
		this.segnoImporto = segnoImporto;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public double getMc() {
		return mc;
	}

	public void setMc(double mc) {
		this.mc = mc;
	}

	public double getAliquota() {
		return aliquota;
	}

	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}

	public double getTotaleRigo() {
		return totaleRigo;
	}

	public void setTotaleRigo(double totaleRigo) {
		this.totaleRigo = totaleRigo;
	}
}
