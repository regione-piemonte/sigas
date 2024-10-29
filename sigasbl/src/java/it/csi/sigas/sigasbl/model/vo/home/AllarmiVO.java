/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class AllarmiVO implements VO {

	private static final long serialVersionUID = 1L;
	
	private boolean scarti;
	private boolean coerenza; 
	private boolean doc;
	private boolean rett;
	private boolean acc;
	private boolean note;
	private boolean rimb;
	private boolean ravv;
	private boolean vers;
	private boolean comp;
	private boolean nuovo;
    
	public boolean isRavv() {
		return ravv;
	}
	public void setRavv(boolean ravv) {
		this.ravv = ravv;
	}
	public boolean isScarti() {
		return scarti;
	}
	public void setScarti(boolean scarti) {
		this.scarti = scarti;
	}
	public boolean isCoerenza() {
		return coerenza;
	}
	public void setCoerenza(boolean coerenza) {
		this.coerenza = coerenza;
	}
	public boolean isRett() {
		return rett;
	}
	public void setRett(boolean rett) {
		this.rett = rett;
	}
	public boolean isAcc() {
		return acc;
	}
	public void setAcc(boolean acc) {
		this.acc = acc;
	}
	public boolean isNote() {
		return note;
	}
	public void setNote(boolean note) {
		this.note = note;
	}
	public boolean isRimb() {
		return rimb;
	}
	public void setRimb(boolean rimb) {
		this.rimb = rimb;
	}
	public boolean isDoc() {
		return doc;
	}
	public void setDoc(boolean doc) {
		this.doc = doc;
	}
	public boolean isVers() {
		return vers;
	}
	public void setVers(boolean vers) {
		this.vers = vers;
	}
	public boolean isComp() {
		return comp;
	}
	public void setComp(boolean comp) {
		this.comp = comp;
	}
	public boolean isNuovo() {
		return nuovo;
	}
	public void setNuovo(boolean nuovo) {
		this.nuovo = nuovo;
	}	
}
