/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class TipoComunicazioniVO implements VO {
	
	private static final long serialVersionUID = 1L;

	private long idTipoComunicazione;
	private String denominazione;
	private String descrizione;
	
	public long getIdTipoComunicazione() {
		return idTipoComunicazione;
	}
	public void setIdTipoComunicazione(long idTipoComunicazione) {
		this.idTipoComunicazione = idTipoComunicazione;
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
