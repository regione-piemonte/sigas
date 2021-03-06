/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

public enum StatoRimborso {

	ISTANZA("ISTANZA"),
	CALCOLATA("CALCOLATA"),
	DETERMINA("DETERMINA");
	
	
	private final String statoRimborso;

	private StatoRimborso(String statoRimborso) {
		this.statoRimborso = statoRimborso;
	}

	public String getName() {
		return statoRimborso;
	}

}
