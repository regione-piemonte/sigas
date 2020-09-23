/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common.exception;

import it.csi.sigas.sigasbl.common.ErrorCodes;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -3912641400426238181L;

	private String codice;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public BusinessException(String message) {
		this(message, ErrorCodes.BUSSINESS_EXCEPTION);
	}

	public BusinessException(String message, String codice) {
		super(message);
		this.codice = codice;

	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	@Override
    public String getMessage() {
        return super.getMessage();
    }

}
