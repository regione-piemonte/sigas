/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.model.vo.home.OrdinativiIncassoVO;

public class ConfermaPagamentoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "pagamento non deve essere vuoto")
	private OrdinativiIncassoVO pagamento;

	public OrdinativiIncassoVO getPagamento() {
		return pagamento;
	}

	public void setPagamento(OrdinativiIncassoVO pagamento) {
		this.pagamento = pagamento;
	}


}