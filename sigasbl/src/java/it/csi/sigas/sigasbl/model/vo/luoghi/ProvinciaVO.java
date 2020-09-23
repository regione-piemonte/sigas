/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.luoghi;

import it.csi.sigas.sigasbl.common.rest.VO;

public class ProvinciaVO implements VO {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String denominazione;
	private String sigla;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	@Override
	public String toString() {
		return "ProvinciaVO [id=" + id + ", denominazione=" + denominazione + "]";
	}

}
