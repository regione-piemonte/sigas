/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class TipoCarrelloVO implements VO {

	private static final long serialVersionUID = -1L;

	private Long idTipoCarrello;
	private String denominazione;
	private String descrizione;
	
	public Long getIdTipoVersamento() {
		return idTipoCarrello;
	}
	public void setIdTipoVersamento(Long idTipoCarrello) {
		this.idTipoCarrello = idTipoCarrello;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
}
