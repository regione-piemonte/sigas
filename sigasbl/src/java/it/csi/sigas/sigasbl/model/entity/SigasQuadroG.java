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

/******************************************************************************
 * 
 * @author mabuonai
 * 
 * Classe per la gestione dei consumi inseriti nella tabella Quadro G.
 * Utilizzo classe base EntityQuadroBase
 *
 *****************************************************************************/
@Entity
@Table(name="sigas_quadro_g_utf")
@NamedQuery(name="SigasQudroG.findAll", query="SELECT i FROM SigasQuadroG i")
public class SigasQuadroG extends EntityQuadroBase implements Serializable {
	
	/***************************************************
	 * Variabili
	 **************************************************/
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_QUADRO_G_IDQUADROG_GENERATOR" , sequenceName="SEQ_QUADRO_G_UTF", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_QUADRO_G_IDQUADROG_GENERATOR")
	@Column(name="id_quadro_g", unique=true, nullable=false)
	private long idQuadroG;

	@ManyToOne
	@JoinColumn(name="id_import", nullable=false)
	private SigasImportUTF sigasImport;	
	
	/***************************************************
	 * Costruttori
	 **************************************************/
	public SigasQuadroG() {
	}
	
	public SigasQuadroG(
			SigasImportUTF sigasImport,
			Date dataEstrazione,
			String codiceDitta,
			String anno,
			String provincia,
			String quadro,
			String progRigo,
			String utenze,
			int utenzeMc) {
		
		this.dataEstrazione = dataEstrazione;
		this.codiceDitta = codiceDitta;
		this.anno = anno;
		this.provincia = provincia;
		this.quadro = quadro;
		this.progRigo = progRigo;
		this.utenze = utenze;
		this.utenzeMc = utenzeMc;
	}
	
	/***************************************************
	 * Metodi publici
	 **************************************************/
	public SigasImportUTF getSigasImport() {
		return sigasImport;
	}

	public void setSigasImport(SigasImportUTF sigasImport) {
		this.sigasImport = sigasImport;
	}
	
	@Column(name="utenze", nullable=false)
	private String utenze;
	
	@Column(name="utenze_mc", nullable=false)
	private int utenzeMc;
	
	public String getUtenze() {
		return utenze;
	}

	public void setUtenze(String utenze) {
		this.utenze = utenze;
	}

	public int getUtenzeMc() {
		return utenzeMc;
	}

	public void setUtenzeMc(int utenzeMc) {
		this.utenzeMc = utenzeMc;
	}

}
