/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import java.util.Date;

import it.csi.sigas.sigasbl.common.rest.VO;

public class StoricoConsumiVO implements VO {

	private static final long serialVersionUID = 1L;
	
	private Long modIdCosumi;
	private Long idConsumi;
	private String modUser;
	private Date modDate;
	private String note;
	
	public Long getModIdCosumi() {
		return modIdCosumi;
	}
	public void setModIdCosumi(Long modIdCosumi) {
		this.modIdCosumi = modIdCosumi;
	}
	public Long getIdConsumi() {
		return idConsumi;
	}
	public void setIdConsumi(Long idCosumi) {
		this.idConsumi = idCosumi;
	}
	public String getModUser() {
		return modUser;
	}
	public void setModUser(String modUser) {
		this.modUser = modUser;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	

}
