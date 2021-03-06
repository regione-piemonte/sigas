/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class ConsumiVO implements VO {
	
	private static final long serialVersionUID = -3855049065633536125L;
    private DichiaranteVO dichiarante;
    private long nProvince;
    private double totVersato;
    private double totCalcolato;
    private String validazione;
    private AllarmiVO allarmi;
   
	public DichiaranteVO getDichiarante() {
		return dichiarante;
	}
	public void setDichiarante(DichiaranteVO dichiarante) {
		this.dichiarante = dichiarante;
	}
	public long getnProvince() {
		return nProvince;
	}
	public void setnProvince(long nProvince) {
		this.nProvince = nProvince;
	}
	public double getTotVersato() {
		return totVersato;
	}
	public void setTotVersato(double totVersato) {
		this.totVersato = totVersato;
	}
	public double getTotCalcolato() {
		return totCalcolato;
	}
	public void setTotCalcolato(double d) {
		this.totCalcolato = d;
	}
	public String getValidazione() {
		return validazione;
	}
	public void setValidazione(String validazione) {
		this.validazione = validazione;
	}
	public AllarmiVO getAllarmi() {
		return allarmi;
	}
	public void setAllarmi(AllarmiVO allarmi) {
		this.allarmi = allarmi;
	} 
}
