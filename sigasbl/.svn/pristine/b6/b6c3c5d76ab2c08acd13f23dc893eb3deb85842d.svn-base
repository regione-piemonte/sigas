/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.model.vo.impostazioni.TassoVO;

public class ConfermaTassoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "tasso non deve essere vuoto")
	private TassoVO tasso;

	public TassoVO getTasso() {
		return tasso;
	}

	public void setTasso(TassoVO tasso) {
		this.tasso = tasso;
	}
	
}
