package it.csi.sigas.sigasbl.model.vo.home;

import java.io.Serializable;

public class RateoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long idRateo;
	
	private long idAnag;
	
	private String mese;
	
	private long idProvincia;
	
	private String annualita;
	
	private double importo;

	public long getIdRateo() {
		return idRateo;
	}

	public void setIdRateo(long idRateo) {
		this.idRateo = idRateo;
	}

	public long getIdAnag() {
		return idAnag;
	}

	public void setIdAnag(long idAnag) {
		this.idAnag = idAnag;
	}

	public String getMese() {
		return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}	

}
