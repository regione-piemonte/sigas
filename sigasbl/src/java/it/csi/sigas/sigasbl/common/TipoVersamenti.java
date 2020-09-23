/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

public enum TipoVersamenti {

	RATEO("RATEO"),
	CONGUAGLIO("CONGUAGLIO"),
	RATEO_CONGUAGLIO("RATEO / CONGUAGLIO"),
	ACCERTAMENTO("ACCERTAMENTO"),
	RAVVEDIMENTO("RAVVEDIMENTO");
	
	
	private final String TipoVersamenti;

	private TipoVersamenti(String TipoVersamenti) {
		this.TipoVersamenti = TipoVersamenti;
	}

	public String getName() {
		return TipoVersamenti;
	}

}
