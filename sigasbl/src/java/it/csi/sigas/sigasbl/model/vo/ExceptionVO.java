/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo;

import java.io.Serializable;

public class ExceptionVO implements Serializable {

	private static final long serialVersionUID = -789337883818863578L;
	
	private Integer status;
	private String message;
	private String errorCode;

	public ExceptionVO(Integer status, String message, String errorCode) {
		super();
		this.status = status;
		this.message = message;
		this.errorCode = errorCode;
	}

	public ExceptionVO(Integer status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ExceptionVO [status=" + status + ", message=" + message + ", errorCode=" + errorCode + "]";
	}

}
