/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;

public class ConfermaVersamentoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "versamento non deve essere vuoto")
	private VersamentiPrVO versamento;

	public VersamentiPrVO getVersamento() {
		return versamento;
	}

	public void setVersamento(VersamentiPrVO versamento) {
		this.versamento = versamento;
	}


}
