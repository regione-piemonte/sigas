/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class PaymentTypesVO implements VO {

	private static final long serialVersionUID = -1L;

	private Long idTipoPagamento;
	private String codTipoPagamento;
	private String descTipoPagamento;
	
	public Long getIdTipoPagamento() {
		return idTipoPagamento;
	}
	public void setIdTipoPagamento(Long idTipoPagamento) {
		this.idTipoPagamento = idTipoPagamento;
	}
	public String getCodTipoPagamento() {
		return codTipoPagamento;
	}
	public void setCodTipoPagamento(String codTipoPagamento) {
		this.codTipoPagamento = codTipoPagamento;
	}
	public String getDescTipoPagamento() {
		return descTipoPagamento;
	}
	public void setDescTipoPagamento(String descTipoPagamento) {
		this.descTipoPagamento = descTipoPagamento;
	}
	
}
