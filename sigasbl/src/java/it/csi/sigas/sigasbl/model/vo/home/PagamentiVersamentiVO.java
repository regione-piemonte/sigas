/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.csi.sigas.sigasbl.common.rest.VO;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;

public class PagamentiVersamentiVO implements VO {

	private static final long serialVersionUID = 1L;

	private long idPagamentoVersamento;

	private String anno;

	private Timestamp dataVersamento;

	private AnagraficaSoggettoVO fkAnag;

	private ProvinciaVO fkProvincia;

//	private Integer fkVersamento;
	
	private VersamentiPrVO fkVersamento;

	private TipoVersamentoVO idTipoVersamento;

	private BigDecimal importo;

	private String mese;
	
	private Integer fkPagamento;

	public long getIdPagamentoVersamento() {
		return idPagamentoVersamento;
	}

	public void setIdPagamentoVersamento(long idPagamentoVersamento) {
		this.idPagamentoVersamento = idPagamentoVersamento;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public Timestamp getDataVersamento() {
		return dataVersamento;
	}

	public void setDataVersamento(Timestamp dataVersamento) {
		this.dataVersamento = dataVersamento;
	}

	public AnagraficaSoggettoVO getFkAnag() {
		return fkAnag;
	}

	public void setFkAnag(AnagraficaSoggettoVO fkAnag) {
		this.fkAnag = fkAnag;
	}

	

	public ProvinciaVO getFkProvincia() {
		return fkProvincia;
	}

	public void setFkProvincia(ProvinciaVO fkProvincia) {
		this.fkProvincia = fkProvincia;
	}

//	public Integer getFkVersamento() {
//		return fkVersamento;
//	}
//
//	public void setFkVersamento(Integer fkVersamento) {
//		this.fkVersamento = fkVersamento;
//	}
	
	

	public TipoVersamentoVO getIdTipoVersamento() {
		return idTipoVersamento;
	}

	public VersamentiPrVO getFkVersamento() {
		return fkVersamento;
	}

	public void setFkVersamento(VersamentiPrVO fkVersamento) {
		this.fkVersamento = fkVersamento;
	}

	public void setIdTipoVersamento(TipoVersamentoVO idTipoVersamento) {
		this.idTipoVersamento = idTipoVersamento;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getMese() {
		return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public Integer getFkPagamento() {
		return fkPagamento;
	}

	public void setFkPagamento(Integer fkPagamento) {
		this.fkPagamento = fkPagamento;
	}

	
	//bi-directional many-to-one association to SigasPagamenti
//	@ManyToOne
//	@JoinColumn(name="fk_pagamento")
//	private SigasPagamenti sigasPagamenti;
	
	

	

	
	
}
