/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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




/**
 * The persistent class for the sigas_pagamenti_versamenti database table.
 * 
 */
@Entity
@Table(name="sigas_pagamenti_versamenti")
@NamedQuery(name="SigasPagamentiVersamenti.findAll", query="SELECT s FROM SigasPagamentiVersamenti s")
public class SigasPagamentiVersamenti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_PAGAMENTI_VERSAMENTI_IDPAGAMENTIVERSAMENTI_GENERATOR" , sequenceName="SEQ_PAGAMENTI_VERSAMENTI", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_PAGAMENTI_VERSAMENTI_IDPAGAMENTIVERSAMENTI_GENERATOR")
	@Column(name="id_pagamento_versamento", unique=true, nullable=false)
	private long idPagamentoVersamento;

	private String anno;

	@Column(name="data_versamento")
	private Timestamp dataVersamento;

	//bi-directional many-to-one association to SigasAnagraficaSoggetti
	@ManyToOne
	@JoinColumn(name="fk_anag")
	private SigasAnagraficaSoggetti sigasAnagraficaSoggetti;

	@ManyToOne
	@JoinColumn(name="fk_provincia")
	private SigasProvincia sigasProvincia;

	@ManyToOne
	@JoinColumn(name="fk_versamento")
	private SigasDichVersamenti sigasDichVersamenti;

	//bi-directional many-to-one association to SigasTipoVersamento
	@ManyToOne
	@JoinColumn(name="id_tipo_versamento")
	private SigasTipoVersamento sigasTipoVersamento;

	private BigDecimal importo;

	private String mese;

	//bi-directional many-to-one association to SigasPagamenti
//	@Column(name="fk_pagamento")
//	private Integer fkPagamento;
	
	@ManyToOne
	@JoinColumn(name="fk_pagamento")
	private SigasPagamenti sigasPagamenti;

	public SigasPagamentiVersamenti() {
	}

	public long getIdPagamentoVersamento() {
		return this.idPagamentoVersamento;
	}

	public void setIdPagamentoVersamento(long idPagamentoVersamento) {
		this.idPagamentoVersamento = idPagamentoVersamento;
	}

	public String getAnno() {
		return this.anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public Timestamp getDataVersamento() {
		return this.dataVersamento;
	}

	public void setDataVersamento(Timestamp dataVersamento) {
		this.dataVersamento = dataVersamento;
	}

	

	public SigasAnagraficaSoggetti getSigasAnagraficaSoggetti() {
		return sigasAnagraficaSoggetti;
	}

	public void setSigasAnagraficaSoggetti(SigasAnagraficaSoggetti sigasAnagraficaSoggetti) {
		this.sigasAnagraficaSoggetti = sigasAnagraficaSoggetti;
	}

	

	public SigasDichVersamenti getSigasDichVersamenti() {
		return sigasDichVersamenti;
	}

	public void setSigasDichVersamenti(SigasDichVersamenti sigasDichVersamenti) {
		this.sigasDichVersamenti = sigasDichVersamenti;
	}

	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getMese() {
		return this.mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public SigasPagamenti getSigasPagamenti() {
		return sigasPagamenti;
	}

	public void setSigasPagamenti(SigasPagamenti sigasPagamenti) {
		this.sigasPagamenti = sigasPagamenti;
	}

	public SigasProvincia getSigasProvincia() {
		return sigasProvincia;
	}

	public void setSigasProvincia(SigasProvincia sigasProvincia) {
		this.sigasProvincia = sigasProvincia;
	}

	public SigasTipoVersamento getSigasTipoVersamento() {
		return sigasTipoVersamento;
	}

	public void setSigasTipoVersamento(SigasTipoVersamento sigasTipoVersamento) {
		this.sigasTipoVersamento = sigasTipoVersamento;
	}

	
}