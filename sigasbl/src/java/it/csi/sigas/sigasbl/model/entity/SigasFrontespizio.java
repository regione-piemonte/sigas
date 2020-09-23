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
@Table(name="sigas_frontespizio_utf")
@NamedQuery(name="SigasFrontespizio.findAll", query="SELECT i FROM SigasFrontespizio i")
public class SigasFrontespizio implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_FRONTESPIZIO_IDFRONTESPIZIO_GENERATOR" , sequenceName="SEQ_FRONTESPIZIO_UTF", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_FRONTESPIZIO_IDFRONTESPIZIO_GENERATOR")
	@Column(name="id_frontespizio", unique=true, nullable=false)
	private long idFrontespizio;

	@ManyToOne
	@JoinColumn(name="id_import", nullable=false)
	private SigasImportUTF sigasImport;

	@Temporal(TemporalType.DATE)
	@Column(name="data_estrazione", nullable=false)
	private Date dataEstrazione;
	
	@Column(name="codice_ditta", nullable=false)
	private String codiceDitta;
	
	@Column(name="denominazione", nullable=false)
	private String denominazione;
	
	@Column(name="provincia_sede", nullable=false)
	private String provinciaSede;
	
	@Column(name="comune_sede", nullable=false)
	private String comuneSede;
	
	@Column(name="indirizzo_sede", nullable=false)
	private String indirizzoSede;
	
	@Column(name="tipo_soggetto", nullable=false)
	private String tipoSoggetto;
	
	@Column(name="anno", nullable=false)
	private String anno;
	
	@Column(name="provincia", nullable=false)
	private String provincia;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_presentazione")
	private Date dataPresentazione;

	@Column(name="Stato_dichiarazione", nullable=false)
	private String StatoDichiarazione;
	
	public SigasFrontespizio() {
	}
	
	public SigasFrontespizio(
			SigasImportUTF sigasImport,
			Date dataEstrazione,
			String codiceDitta,
			String denominazione,
			String provinciaSede,
			String comuneSede,
			String indirizzoSede,
			String tipoSoggetto,
			String anno,
			String provincia,
			Date dataPresentazione,
			String StatoDichiarazione) {
		
		this.dataEstrazione = dataEstrazione;
		this.codiceDitta = codiceDitta;
		this.denominazione = denominazione;
		this.provinciaSede = provinciaSede;
		this.comuneSede = comuneSede;
		this.indirizzoSede = indirizzoSede;
		this.tipoSoggetto = tipoSoggetto;
		this.anno=anno;
		this.provincia = provincia;
		this.dataPresentazione = dataPresentazione;
		this.StatoDichiarazione = StatoDichiarazione;
	}


	public long getIdFrontespizio() {
		return idFrontespizio;
	}

	public void setIdFrontespizio(long idFrontespizio) {
		this.idFrontespizio = idFrontespizio;
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

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getProvinciaSede() {
		return provinciaSede;
	}

	public void setProvinciaSede(String provinciaSede) {
		this.provinciaSede = provinciaSede;
	}

	public String getComuneSede() {
		return comuneSede;
	}

	public void setComuneSede(String comuneSede) {
		this.comuneSede = comuneSede;
	}

	public String getIndirizzoSede() {
		return indirizzoSede;
	}

	public void setIndirizzoSede(String indirizzoSede) {
		this.indirizzoSede = indirizzoSede;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
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

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Date getDataPresentazione() {
		return dataPresentazione;
	}

	public void setDataPresentazione(Date dataPresentazione) {
		this.dataPresentazione = dataPresentazione;
	}

	public String getStatoDichiarazione() {
		return StatoDichiarazione;
	}

	public void setStatoDichiarazione(String statoDichiarazione) {
		StatoDichiarazione = statoDichiarazione;
	}

}
