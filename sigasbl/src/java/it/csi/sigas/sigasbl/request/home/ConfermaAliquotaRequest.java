/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.model.vo.impostazioni.AliquoteVO;

public class ConfermaAliquotaRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "aliquota non deve essere vuoto")
	private AliquoteVO aliquota;

	public AliquoteVO getAliquota() {
		return aliquota;
	}
	
	public void setAliquota(AliquoteVO aliquota) {
		this.aliquota = aliquota;
	}

}
