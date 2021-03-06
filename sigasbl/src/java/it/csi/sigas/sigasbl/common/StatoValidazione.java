/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

public enum StatoValidazione {

	VALIDATO("VALIDATO"),
	NON_VALIDATO("NON_VALIDATO");
	
	
	private final String statoValidazione;

	private StatoValidazione(String statoValidazione) {
		this.statoValidazione = statoValidazione;
	}

	public String getName() {
		return statoValidazione;
	}

}
