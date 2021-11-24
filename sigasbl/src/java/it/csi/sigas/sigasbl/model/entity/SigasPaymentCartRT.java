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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name="sigas_carrello_rt")
public class SigasPaymentCartRT implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_PAYMENT_CART_ID_CARRELLO_RT_GENERATOR" , sequenceName="seq_carrello_rt", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_PAYMENT_CART_ID_CARRELLO_RT_GENERATOR")
	@Column(name="id_carrello_rt", unique=true, nullable=false)
	private long idCarrelloRT;

	@Column(name="codice_pagamento")
	private String codicePagamento;

	@Column(name="data_ricevuta")
	private Date dataPagamento = new Date();

	@Column(name="data_insert")
	private Date dataInsert = new Date();

	@Column(name="fk_utente_insert")
	private Integer fkUtenteInsert;

	@Column(name="fk_utente_update")
	private Integer fkUtenteUpdate;

	@Column(name="data_update")
	private Date dataUpdate;

	@Column(name="numero_rt")
	private Integer numeroRT;

	@Column(name="id_rt")
	private String idRT;

	@Column(name="xml")
	private byte[] xml;
	
	public long getIdCarrelloRT() {
		return idCarrelloRT;
	}

	public void setIdCarrelloRT(long idCarrelloRT) {
		this.idCarrelloRT = idCarrelloRT;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataInsert() {
		return dataInsert;
	}

	public void setDataInsert(Date dataInsert) {
		this.dataInsert = dataInsert;
	}

	public Integer getFkUtenteInsert() {
		return fkUtenteInsert;
	}

	public void setFkUtenteInsert(Integer fkUtenteInsert) {
		this.fkUtenteInsert = fkUtenteInsert;
	}

	public Integer getFkUtenteUpdate() {
		return fkUtenteUpdate;
	}

	public void setFkUtenteUpdate(Integer fkUtenteUpdate) {
		this.fkUtenteUpdate = fkUtenteUpdate;
	}

	public Date getDataUpdate() {
		return dataUpdate;
	}

	public void setDataUpdate(Date dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

	public Integer getNumeroRT() {
		return numeroRT;
	}

	public void setNumeroRT(Integer numeroRT) {
		this.numeroRT = numeroRT;
	}

	public String getIdRT() {
		return idRT;
	}

	public void setIdRT(String idRT) {
		this.idRT = idRT;
	}

	public byte[] getXml() {
		return xml;
	}

	public void setXml(byte[] xml) {
		this.xml = xml;
	}

	public String getCodicePagamento() {
		return codicePagamento;
	}

	public void setCodicePagamento(String codicePagamento) {
		this.codicePagamento = codicePagamento;
	}

	public String printAll() {
		return "idCarrelloRT: " + idCarrelloRT + ", " +
				"codicePagamento: " + codicePagamento + ", " +
				"dataPagamento: " + dataPagamento + ", " +
				"dataInsert: " + dataInsert + ", " +
				"fkUtenteInsert: " + fkUtenteInsert + ", " +
				"fkUtenteUpdate: " + fkUtenteUpdate + ", " +
				"dataUpdate: " + dataUpdate + ", " +
				"numeroRT: " + numeroRT + ", " +
				"idRT: " + idRT + ", " +
				"xml: " + xml;
	}
	
}
