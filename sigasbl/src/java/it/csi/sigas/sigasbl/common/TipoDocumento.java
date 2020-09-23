/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

public enum TipoDocumento {

	DICHIARAZIONE_UTF("Dichiarazione UTF");
	
	
	private final String tipoDocumento;

	private TipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getName() {
		return tipoDocumento;
	}

}
