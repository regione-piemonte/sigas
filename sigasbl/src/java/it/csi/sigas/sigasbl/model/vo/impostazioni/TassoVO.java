/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.impostazioni;

import java.util.Date;

import it.csi.sigas.sigasbl.common.rest.VO;

public class TassoVO implements VO {

	private static final long serialVersionUID = -1L;

	private Long id;
	private double valore;
	private Long idTipoTasso;
	private Date dataStart;
	private Date dataEnd;
	long version;
	
	private String insUser;
	private Date insDate;
	private Date modDate;
	private String modUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getValore() {
		return valore;
	}

	public void setValore(double valore) {
		this.valore = valore;
	}

	public Long getIdTipoTasso() {
		return idTipoTasso;
	}

	public void setIdTipoTasso(Long idTipoTasso) {
		this.idTipoTasso = idTipoTasso;
	}
	
	public Date getDataStart() {
		return dataStart;
	}

	public void setDataStart(Date dataStart) {
		this.dataStart = dataStart;
	}

	public Date getDataEnd() {
		return dataEnd;
	}

	public void setDataEnd(Date dataEnd) {
		this.dataEnd = dataEnd;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getInsUser() {
		return insUser;
	}

	public void setInsUser(String insUser) {
		this.insUser = insUser;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	
}
