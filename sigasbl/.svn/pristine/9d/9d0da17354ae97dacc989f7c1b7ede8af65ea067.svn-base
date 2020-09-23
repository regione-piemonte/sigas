/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "sigas_report")
@NamedQuery(name = "SigasReport.findAll", query = "SELECT i FROM SigasReport i")
public class SigasReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_report", unique = true, nullable = false, precision = 2)
	private long idReport;

	@Column(name = "cod_report", nullable = false, length = 50)
	private String codReport;

	@Column(name = "data_insert", nullable = false)
	private Timestamp dataInsert;

	@Column(name = "data_update")
	private Timestamp dataUpdate;

	@Column(name = "desc_report", nullable = false, length = 200)
	private String descReport;

	private byte[] jasper;

	@ColumnTransformer(read = "jrxml", write = "xml(?)")
	@Column(nullable = false, columnDefinition = "XMLType")
	private String jrxml;

	public SigasReport() {
	}

	public long getIdReport() {
		return this.idReport;
	}

	public void setIdReport(long idReport) {
		this.idReport = idReport;
	}

	public String getCodReport() {
		return this.codReport;
	}

	public void setCodReport(String codReport) {
		this.codReport = codReport;
	}

	public Timestamp getDataInsert() {
		return this.dataInsert;
	}

	public void setDataInsert(Timestamp dataInsert) {
		this.dataInsert = dataInsert;
	}

	public Timestamp getDataUpdate() {
		return this.dataUpdate;
	}

	public void setDataUpdate(Timestamp dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

	public String getDescReport() {
		return this.descReport;
	}

	public void setDescReport(String descReport) {
		this.descReport = descReport;
	}

	public byte[] getJasper() {
		return this.jasper;
	}

	public void setJasper(byte[] jasper) {
		this.jasper = jasper;
	}

	public String getJrxml() {
		return this.jrxml;
	}

	public void setJrxml(String jrxml) {
		this.jrxml = jrxml;
	}

}
