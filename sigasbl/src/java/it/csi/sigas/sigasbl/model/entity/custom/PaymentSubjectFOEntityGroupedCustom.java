/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity.custom;

import javax.persistence.Entity;


@Entity
public class PaymentSubjectFOEntityGroupedCustom extends PaymentSubjectFOEntityCustomBase {
	

	private String mesi = null;
	private String totale = null;
	private String provincie = null;
    
	public String getMesi() {
		return mesi;
	}
	public void setMesi(String mesi) {
		this.mesi = mesi;
	}
	public String getTotale() {
		return totale;
	}
	public void setTotale(String totale) {
		this.totale = totale;
	}
	public String getProvincie() {
		return provincie;
	}
	public void setProvincie(String provincie) {
		this.provincie = provincie;
	}
	
}
