/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

public enum TipoAllarme {

	SCARTI("SCARTI"),
	COERENZA("COERENZA"),
	COMUNICAZIONI("COMUNICAZIONI"),
	RETTIFICA("RETTIFICA"),
	ACCERTAMENTO("ACCERTAMENTO"),
	NOTE("NOTE"),
	RIMBORSO("RIMBORSO"),
	RAVVEDIMENTO("RAVVEDIMENTO"),
	VERSAMENTO("VERSAMENTO"),
	COMPENSAZIONE("COMPENSAZIONE");
	
	
	private final String tipoAllarme;

	private TipoAllarme(String tipoAllarme) {
		this.tipoAllarme = tipoAllarme;
	}

	public String getName() {
		return tipoAllarme;
	}

}
