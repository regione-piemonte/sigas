package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class ItemVersamentiReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String anno;
	private String mese;
	private String provincia;
	private String tipo;
	//Importo Versato
	private Double importo;
	private Double importo_calcolato;
	private Double differenza;
	private String note;
	
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	
	public Double getImporto() {
		return importo;
	}
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	public Double getImporto_calcolato() {
		return importo_calcolato;
	}
	public void setImporto_calcolato(Double importo_calcolato) {
		this.importo_calcolato = importo_calcolato;
	}
	public Double getDifferenza() {
		return differenza;
	}
	public void setDifferenza(Double differenza) {
		this.differenza = differenza;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getMese() {
		return mese;
	}
	public void setMese(String mese) {
		this.mese = mese;
	}	

}
