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
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="sigas_import_utf")
@NamedQuery(name="SigasImportUTF.findAll", query="SELECT i FROM SigasImportUTF i")
public class SigasImportUTF implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_IMPORT_IDIMPORT_GENERATOR" , sequenceName="SEQ_IMPORT_UTF", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_IMPORT_IDIMPORT_GENERATOR")
	@Column(name="id_import", unique=true, nullable=false)
	private long idImport;

	@Column(name="user_id", nullable=false)
	private String userID;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="import_date", nullable=false)
	private Date importDate;
	
	@Column(name="annualita", nullable=false)
	private String annualita;

	@Column(name="esito", nullable=false)
	private int esito;
	
	@Column(name="errore", nullable=false)
	private String errore;
	
	public SigasImportUTF() {
	}

	public long getIdImport() {
		return idImport;
	}

	public void setIdImport(long idImport) {
		this.idImport = idImport;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public int getEsito() {
		return esito;
	}

	public void setEsito(int esito) {
		this.esito = esito;
	}

	public String getErrore() {
		return errore;
	}

	public void setErrore(String errore) {
		this.errore = errore;
	}

}
