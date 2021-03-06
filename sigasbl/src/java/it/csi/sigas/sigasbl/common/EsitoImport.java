/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

public enum EsitoImport {
	DOWNLOAD_INPROGRESS(1),
	DOWNLOAD_COMPLETE(2),
	CALCULATE_INPROGRESS(3),
	IMPORT_COMPLETE(4),
	ERROR_DOWNLOAD(-1),
	ERROR_POPULATE(-2),
	ERROR_CALCULATE(-3);
	
	private final int esito;

	private EsitoImport(int esito) {
		this.esito = esito;
	}

	public int getEsitoImport() {
		return esito;
	}

}
