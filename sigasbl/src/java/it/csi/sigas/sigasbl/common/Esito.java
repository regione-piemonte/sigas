/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

public enum Esito {
	SUCCESS("S"), ERROR("E");

	private final String esito;

	private Esito(String esito) {
		this.esito = esito;
	}

	public String getEsito() {
		return esito;
	}

}
