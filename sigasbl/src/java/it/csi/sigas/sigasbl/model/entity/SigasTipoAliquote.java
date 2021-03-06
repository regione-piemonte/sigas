/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;

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

@Entity
@Table(name="sigas_tipo_aliquote")
@NamedQuery(name="SigasTipoAliquote.findAll", query="SELECT i FROM SigasAliquote i")
public class SigasTipoAliquote implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_TIPO_ALIQUOTE_IDTIPOALIQUOTA_GENERATOR" , sequenceName="SEQ_TIPO_ALIQUOTE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_TIPO_ALIQUOTE_IDTIPOALIQUOTA_GENERATOR")
	@Column(name="id_tipo_aliquota", unique=true, nullable=false)
	private long idTipoAliquota;

	@Column(name="nome_aliquota")
	private String nomeAliquota;

	@Column(name="descrizione")
	private String descrizione;

	@Column(name="nuovo_allacciamento")
	private boolean nuovoAllacciamentp;

	@ManyToOne
	@JoinColumn(name="id_tipo_consumo", nullable=false)
	private SigasTipoConsumo sigasTipoConsumo;
	
	@Column(name="tipo")
	private String tipo;

	public SigasTipoAliquote() {
	}

	public long getIdTipoAliquota() {
		return idTipoAliquota;
	}

	public void setIdTipoAliquota(long idTipoAliquota) {
		this.idTipoAliquota = idTipoAliquota;
	}

	public String getNomeAliquota() {
		return nomeAliquota;
	}

	public void setNomeAliquota(String nomeAliquota) {
		this.nomeAliquota = nomeAliquota;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isNuovoAllacciamentp() {
		return nuovoAllacciamentp;
	}

	public void setNuovoAllacciamentp(boolean nuovoAllacciamentp) {
		this.nuovoAllacciamentp = nuovoAllacciamentp;
	}

	public SigasTipoConsumo getSigasTipoConsumo() {
		return sigasTipoConsumo;
	}

	public void setSigasTipoConsumo(SigasTipoConsumo sigasTipoConsumo) {
		this.sigasTipoConsumo = sigasTipoConsumo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
