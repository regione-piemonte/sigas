/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

public enum Quadro {

	FRONTESPIZIO("FRONTESPIZIO"),
	QUADRO_F("QUADRO_F"),
	QUADRO_G("QUADRO_G"),
	QUADRO_H("QUADRO_H"),
	QUADRO_I("QUADRO_I"),
	QUADRO_M("QUADRO_M"),
	QUADRO_N("QUADRO_N");
	
	private final String quadro;

	private Quadro(String quadro) {
		this.quadro = quadro;
	}

	public String getName() {
		return quadro;
	}

}
