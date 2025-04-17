package it.csi.sigas.sigasbl.model.vo;

import java.io.Serializable;

public class MappaInfoSoggettoIncorporatoPerSoggettoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String provincia;
	
	private double consumi;
	
	private double versamenti;

	public String getProvincia() {
		return provincia;
	}

	public double getConsumi() {
		return consumi;
	}

	public double getVersamenti() {
		return versamenti;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public void setConsumi(double consumi) {
		this.consumi = consumi;
	}

	public void setVersamenti(double versamenti) {
		this.versamenti = versamenti;
	}	

}
