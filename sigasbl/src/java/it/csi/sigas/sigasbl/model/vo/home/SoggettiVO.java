/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class SoggettiVO implements VO {

	private static final long serialVersionUID = 1L;
	private long idAnag;
	private String codiceAzienda;
	private String denominazione;
    private long nProvince;
    private double totCalcolato;
    private double totVersato;
    private String validato;
    private AllarmiVO allarmi;
    private String allReport;
   
	
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(long idAnag) {
		this.idAnag = idAnag;
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
	public String getValidato() {
		return validato;
	}
	public void setValidato(String validato) {
		this.validato = validato;
	}
	public AllarmiVO getAllarmi() {
		return allarmi;
	}
	public void setAllarmi(AllarmiVO allarmi) {
		this.allarmi = allarmi;
	}
	public String getAllReport() {
		return allReport;
	}
	public void setAllReport(String allReport) {
		this.allReport = allReport;
	} 
}
