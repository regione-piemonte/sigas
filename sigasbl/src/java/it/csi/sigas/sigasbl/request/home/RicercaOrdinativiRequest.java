/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;
import java.util.Date;

public class RicercaOrdinativiRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private String anno;
	
    private Date dataIncassoDa;    
	
    private Date dataIncassoA;
    
    private Boolean conciliato;
    
    private String azienda;
    
    private String codiceAzienda;
    
    private Boolean conciliatoParziale;
	
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public Date getDataIncassoDa() {
		return dataIncassoDa;
	}
	public void setDataIncassoDa(Date dataIncassoDa) {
		this.dataIncassoDa = dataIncassoDa;
	}
	public Date getDataIncassoA() {
		return dataIncassoA;
	}
	public void setDataIncassoA(Date dataIncassoA) {
		this.dataIncassoA = dataIncassoA;
	}
	public Boolean getConciliato() {
		return conciliato;
	}
	public void setConciliato(Boolean conciliato) {
		this.conciliato = conciliato;
	}
	public String getAzienda() {
		return azienda;
	}
	public void setAzienda(String azienda) {
		this.azienda = azienda;
	}
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	public Boolean getConciliatoParziale() {
		return conciliatoParziale;
	}
	public void setConciliatoParziale(Boolean conciliatoParziale) {
		this.conciliatoParziale = conciliatoParziale;
	}
	
	
	
}
