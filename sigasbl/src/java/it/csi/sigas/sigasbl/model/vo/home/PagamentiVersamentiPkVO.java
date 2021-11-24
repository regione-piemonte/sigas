/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class PagamentiVersamentiPkVO implements VO {

	private static final long serialVersionUID = 1L;


	private Integer fkAnag;

	private Integer fkVersamento;

	

	public Integer getFkAnag() {
		return fkAnag;
	}

	public void setFkAnag(Integer fkAnag) {
		this.fkAnag = fkAnag;
	}

	public Integer getFkVersamento() {
		return fkVersamento;
	}

	public void setFkVersamento(Integer fkVersamento) {
		this.fkVersamento = fkVersamento;
	}

	
	
	
}
