/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class NuovoAllacciamentoVO implements VO {

	private static final long serialVersionUID = 1L;
	
	private long consumo;
	private double aliquota;
    private double importo;
    private String descrizione;
    
	public long getConsumo() {
		return consumo;
	}
	public void setConsumo(long consumo) {
		this.consumo = consumo;
	}
	public double getAliquota() {
		return aliquota;
	}
	public void setAliquota(double aliquota) {
		this.aliquota = aliquota;
	}
	public double getImporto() {
		return importo;
	}
	public void setImporto(double importo) {
		this.importo = importo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	} 
    
	
}
