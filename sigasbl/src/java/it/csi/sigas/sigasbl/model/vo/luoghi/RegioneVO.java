/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.luoghi;


public class RegioneVO {

	private static final long serialVersionUID = -2458784235134687017L;

	private Long id;
	private String denominazione;

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

	@Override
	public String toString() {
		return "RegioneVO [id=" + id + ", denominazione=" + denominazione + "]";
	}

}
