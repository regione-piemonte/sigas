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
 * Classe per la gestione dei consumi inseriti nella tabella Quadro F.
 * Utilizzo classe base EntityQuadroBase
 *
 *****************************************************************************/
@Entity
@Table(name="sigas_quadro_f_utf")
@NamedQuery(name="SigasQudroF.findAll", query="SELECT i FROM SigasQuadroF i")
public class SigasQuadroF extends EntityQuadroBase implements Serializable {
	
	/***************************************************
	 * Variabili
	 **************************************************/	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_QUADRO_F_IDQUADROF_GENERATOR" , sequenceName="SEQ_QUADRO_F_UTF", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_QUADRO_F_IDQUADROF_GENERATOR")
	@Column(name="id_quadro_f", unique=true, nullable=false)
	private long idQuadroF;

	@ManyToOne
	@JoinColumn(name="id_import", nullable=false)
	private SigasImportUTF sigasImport;
		
	@Column(name="utenze", nullable=false)
	private String utenze;
	
	@Column(name="utenze_mc", nullable=false)
	private int utenzeMc;
	
	@Column(name="fascia_clima", nullable=false)
	private String fasciaClima;	
	
	/***************************************************
	 * Costruttori
	 **************************************************/
	public SigasQuadroF() {
	}
	
	public SigasQuadroF(
			SigasImportUTF sigasImport,
			Date dataEstrazione,
			String codiceDitta,
			String anno,
			String provincia,
			String quadro,
			String fasciaClima,
			String progRigo,
			String utenze,
			int utenzeMc) {
		
		this.dataEstrazione = dataEstrazione;
		this.codiceDitta = codiceDitta;
		this.anno = anno;
		this.provincia = provincia;
		this.quadro = quadro;
		this.fasciaClima = fasciaClima;
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

	public long getIdQuadroF() {
		return idQuadroF;
	}

	public void setIdQuadroF(long idQuadroF) {
		this.idQuadroF = idQuadroF;
	}
	

	public String getFasciaClima() {
		return fasciaClima;
	}

	public void setFasciaClima(String fasciaClima) {
		this.fasciaClima = fasciaClima;
	}	

}
