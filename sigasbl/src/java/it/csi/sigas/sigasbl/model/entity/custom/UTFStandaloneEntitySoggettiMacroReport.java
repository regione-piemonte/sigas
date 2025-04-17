package it.csi.sigas.sigasbl.model.entity.custom;

import java.io.Serializable;
import java.math.BigDecimal;

public class UTFStandaloneEntitySoggettiMacroReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id_anag;
	private String denominazione;	
	private Double tot_uso_industriale;
	private Double tot_uso_civile;
	private Double tot_nuovi_allacciamenti;	
	private Double tot_dichiarazione;
	private String azione;
	private Boolean selectedImport;
	
	public Integer getId_anag() {
		return id_anag;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public Double getTot_uso_industriale() {
		return tot_uso_industriale;
	}
	public Double getTot_uso_civile() {
		return tot_uso_civile;
	}
	public Double getTot_nuovi_allacciamenti() {
		return tot_nuovi_allacciamenti;
	}
	public Double getTot_dichiarazione() {
		return tot_dichiarazione;
	}
	public String getAzione() {
		return azione;
	}
	public void setId_anag(Integer id_anag) {
		this.id_anag = id_anag;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public void setTot_uso_industriale(Double tot_uso_industriale) {
		this.tot_uso_industriale = tot_uso_industriale;
	}
	public void setTot_uso_civile(Double tot_uso_civile) {
		this.tot_uso_civile = tot_uso_civile;
	}
	public void setTot_nuovi_allacciamenti(Double tot_nuovi_allacciamenti) {
		this.tot_nuovi_allacciamenti = tot_nuovi_allacciamenti;
	}
	public void setTot_dichiarazione(Double tot_dichiarazione) {
		this.tot_dichiarazione = tot_dichiarazione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	public Boolean getSelectedImport() {
		return selectedImport;
	}
	public void setSelectedImport(Boolean selectedImport) {
		this.selectedImport = selectedImport;
	}	

}
