/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class DownloadReport implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String anno;
	private Long idComunicazioni;
	private Long idRimborso;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public Long getIdComunicazioni() {
		return idComunicazioni;
	}
	public void setIdComunicazioni(Long idComunicazioni) {
		this.idComunicazioni = idComunicazioni;
	}
	public Long getIdRimborso() {
		return idRimborso;
	}
	public void setIdRimborso(Long idRimborso) {
		this.idRimborso = idRimborso;
	}	
	
}
