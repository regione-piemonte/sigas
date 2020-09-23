/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.impostazioni;

import it.csi.sigas.sigasbl.common.rest.VO;

public class TipoConsumoVO implements VO {

	private static final long serialVersionUID = -1L;

	private Long idTipoConsumo;
	private String campoDichConsumo;
	private String descrizione;
	
	public Long getIdTipoConsumo() {
		return idTipoConsumo;
	}
	public void setIdTipoConsumo(Long idTipoConsumo) {
		this.idTipoConsumo = idTipoConsumo;
	}
	public String getCampoDichConsumo() {
		return campoDichConsumo;
	}
	public void setCampoDichConsumo(String campoDichConsumo) {
		this.campoDichConsumo = campoDichConsumo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
