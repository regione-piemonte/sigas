package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class ItemSoggettoReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private String provinciaErogazione;	
	private Double totaleVersato;
	private Double totaleDichiarazione;
	private Double addizionaleLiquidata;
	
	public String getProvinciaErogazione() {
		return provinciaErogazione;
	}
	public void setProvinciaErogazione(String provinciaErogazione) {
		this.provinciaErogazione = provinciaErogazione;
	}
	public Double getTotaleVersato() {
		return totaleVersato;
	}
	public void setTotaleVersato(Double totaleVersato) {
		this.totaleVersato = totaleVersato;
	}
	public Double getTotaleDichiarazione() {
		return totaleDichiarazione;
	}
	public void setTotaleDichiarazione(Double totaleDichiarazione) {
		this.totaleDichiarazione = totaleDichiarazione;
	}
	public Double getAddizionaleLiquidata() {
		return addizionaleLiquidata;
	}
	public void setAddizionaleLiquidata(Double addizionaleLiquidata) {
		this.addizionaleLiquidata = addizionaleLiquidata;
	}	
	
}
