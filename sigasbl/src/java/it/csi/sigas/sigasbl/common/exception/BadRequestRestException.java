/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common.exception;

public class BadRequestRestException extends RuntimeException {

	private static final long serialVersionUID = 6451630758923641509L;

	private String codice;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public BadRequestRestException(String message) {
		super(message);
	}

	public BadRequestRestException(String message, String codice) {
		super(message);
		this.codice = codice;
	}

	public BadRequestRestException(Throwable cause) {
		super(cause);
	}

	public BadRequestRestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestRestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
