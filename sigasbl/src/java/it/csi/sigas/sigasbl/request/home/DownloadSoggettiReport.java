/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;
import java.util.List;

import it.csi.sigas.sigasbl.model.vo.home.SoggettiVO;
import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;

public class DownloadSoggettiReport implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<SoggettiVO> soggetti;
	private String anno;
	
	
	public List<SoggettiVO> getSoggetti() {
		return soggetti;
	}
	public void setSoggetti(List<SoggettiVO> soggetti) {
		this.soggetti = soggetti;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}

	
}
