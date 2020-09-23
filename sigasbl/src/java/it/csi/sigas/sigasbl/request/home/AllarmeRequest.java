/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class AllarmeRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idAnag;
	private Long idConsumi;
	private boolean status;
	

	public Long getIdConsumi() {
		return idConsumi;
	}
	public void setIdConsumi(Long idConsumi) {
		this.idConsumi = idConsumi;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Long idAnag) {
		this.idAnag = idAnag;
	} 
	
	
	
	
}
