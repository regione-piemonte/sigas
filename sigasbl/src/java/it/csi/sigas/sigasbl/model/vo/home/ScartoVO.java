/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;
import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;

public class ScartoVO implements VO {

	private static final long serialVersionUID = -6556958673902732933L;
	
	private long idScarti;
	private SigasDichConsumi sigasDichConsumi;
	private String provincia;
	private int consumi;
	private double aliquota;
	private double imposta;
	private boolean conciliato;
	private String descUsoScarto;
	
	public long getIdScarti() {
		return idScarti;
	}
	public void setIdScarti(long idScarti) {
		this.idScarti = idScarti;
	}
	public SigasDichConsumi getSigasDichConsumi() {
		return sigasDichConsumi;
	}
	public void setSigasDichConsumi(SigasDichConsumi sigasDichConsumi) {
		this.sigasDichConsumi = sigasDichConsumi;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public int getConsumi() {
		return consumi;
	}
	public void setConsumi(int consumi) {
		this.consumi = consumi;
	}
	public double getAliquota() {
		return aliquota;
	}
	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}
	public double getImposta() {
		return imposta;
	}
	public void setImposta(double imposta) {
		this.imposta = imposta;
	}
	public boolean isConciliato() {
		return conciliato;
	}
	public void setConciliato(boolean conciliato) {
		this.conciliato = conciliato;
	}
	public String getDescUsoScarto() {
		return descUsoScarto;
	}
	public void setDescUsoScarto(String descUsoScarto) {
		this.descUsoScarto = descUsoScarto;
	}
	
}
