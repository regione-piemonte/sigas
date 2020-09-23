/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo;

import java.io.Serializable;

import it.csi.sigas.sigasbl.common.Esito;

public class ResponseVO<T> implements Serializable {

	private static final long serialVersionUID = -4502846804873900046L;

	private String esito;
	private T object;

	public ResponseVO(Esito esito, T object) {
		super();
		this.esito = esito.getEsito();
		this.object = object;
	}

	public String getEsito() {
		return esito;
	}

	public T getObject() {
		return object;
	}

}
