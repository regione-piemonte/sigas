/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.impostazioni;

import java.util.Date;

import it.csi.sigas.sigasbl.common.rest.VO;

public class AliquoteVO implements VO {

	private static final long serialVersionUID = -1L;

	private Long id;
	private double aliquota;
	private String progRigo;
	private TipoAliquoteVO tipoAliquote;
	private Date validitaStart;
	private Date validitaEnd;
	private Long version;
	
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
	public double getAliquota() {
		return aliquota;
	}
	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}
	public String getProgRigo() {
		return progRigo;
	}
	public void setProgRigo(String progRigo) {
		this.progRigo = progRigo;
	}
	public TipoAliquoteVO getTipoAliquote() {
		return tipoAliquote;
	}
	public void setTipoAliquote(TipoAliquoteVO tipoAliquote) {
		this.tipoAliquote = tipoAliquote;
	}
	public Date getValiditaStart() {
		return validitaStart;
	}
	public void setValiditaStart(Date validitaStart) {
		this.validitaStart = validitaStart;
	}
	public Date getValiditaEnd() {
		return validitaEnd;
	}
	public void setValiditaEnd(Date validitaEnd) {
		this.validitaEnd = validitaEnd;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
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
