package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class RicercaRateoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idAnag;
	private String siglaProvincia;
	private String mese;
	private String annualita; 
	
	public Long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Long idAnag) {
		this.idAnag = idAnag;
	}
	
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	
	public String getMese() {
		return mese;
	}
	public void setMese(String mese) {
		this.mese = mese;
	}
	
	public String getAnnualita() {
		return annualita;
	}
	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

}
