/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.model.vo.home.RimborsoVO;

public class SalvaRimborsoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "rimborso non deve essere vuoto")
	private RimborsoVO rimborso;

	public RimborsoVO getRimborso() {
		return rimborso;
	}

	public void setRimborso(RimborsoVO rimborso) {
		this.rimborso = rimborso;
	}


}
