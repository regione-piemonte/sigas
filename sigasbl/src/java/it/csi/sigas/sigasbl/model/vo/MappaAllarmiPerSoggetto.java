package it.csi.sigas.sigasbl.model.vo;

import java.io.Serializable;

public class MappaAllarmiPerSoggetto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String denominazione;
	
	private Double count;

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}	
	
}
