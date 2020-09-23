/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;
import java.util.List;

import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;

public class DownloadAccertamentiReport implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<VersamentiPrVO> accertamenti;
	private Long idAnag;
	
	public List<VersamentiPrVO> getAccertamenti() {
		return accertamenti;
	}
	public void setAccertamenti(List<VersamentiPrVO> accertamenti) {
		this.accertamenti = accertamenti;
	}
	public Long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Long idAnag) {
		this.idAnag = idAnag;
	}

	
}
