/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.accreditamento;

import java.io.Serializable;

public class RicercaAccreditamentoRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idUtenteProvv;

	public Long getIdUtenteProvv() {
		return idUtenteProvv;
	}

	public void setIdUtenteProvv(Long idUtenteProvv) {
		this.idUtenteProvv = idUtenteProvv;
	}
	
	
}
