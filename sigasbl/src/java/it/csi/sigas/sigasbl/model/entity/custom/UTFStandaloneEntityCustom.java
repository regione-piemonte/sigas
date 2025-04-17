package it.csi.sigas.sigasbl.model.entity.custom;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class UTFStandaloneEntityCustom implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idAnag;
	private String denominazione;
	private String provErogazione;
	private Long idConsumi;	
	
	private Double usi_industriali_1200;
	private Double usi_industriali_up;
	private Double usi_civili_120;
	private Double usi_civili_480;
	private Double usi_civili_1560;
	private Double usi_civili_up;
	
	private Double tot_nuovi_allacciamenti;
	private Double tot_industriali;
	private Double tot_civili;	
	private Double rateo_dich;	
	private Double conguaglio_dich;	
	private Double totaleDich;	
	private Double rettifiche;
	private Double arrotondamenti;
	
	public Integer getIdAnag() {
		return idAnag;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public String getProvErogazione() {
		return provErogazione;
	}
	public Double getUsi_industriali_1200() {
		return usi_industriali_1200;
	}
	public Double getUsi_industriali_up() {
		return usi_industriali_up;
	}
	public Double getUsi_civili_120() {
		return usi_civili_120;
	}
	public Double getUsi_civili_480() {
		return usi_civili_480;
	}
	public Double getUsi_civili_1560() {
		return usi_civili_1560;
	}
	public Double getUsi_civili_up() {
		return usi_civili_up;
	}
	public Double getTot_nuovi_allacciamenti() {
		return tot_nuovi_allacciamenti;
	}
	public Double getTot_industriali() {
		return tot_industriali;
	}
	public Double getTot_civili() {
		return tot_civili;
	}
	public Double getRateo_dich() {
		return rateo_dich;
	}
	public Double getConguaglio_dich() {
		return conguaglio_dich;
	}
	public Double getTotaleDich() {
		return totaleDich;
	}
	public void setIdAnag(Integer idAnag) {
		this.idAnag = idAnag;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public void setProvErogazione(String provErogazione) {
		this.provErogazione = provErogazione;
	}
	public void setUsi_industriali_1200(Double usi_industriali_1200) {
		this.usi_industriali_1200 = usi_industriali_1200;
	}
	public void setUsi_industriali_up(Double usi_industriali_up) {
		this.usi_industriali_up = usi_industriali_up;
	}
	public void setUsi_civili_120(Double usi_civili_120) {
		this.usi_civili_120 = usi_civili_120;
	}
	public void setUsi_civili_480(Double usi_civili_480) {
		this.usi_civili_480 = usi_civili_480;
	}
	public void setUsi_civili_1560(Double usi_civili_1560) {
		this.usi_civili_1560 = usi_civili_1560;
	}
	public void setUsi_civili_up(Double usi_civili_up) {
		this.usi_civili_up = usi_civili_up;
	}
	public void setTot_nuovi_allacciamenti(Double tot_nuovi_allacciamenti) {
		this.tot_nuovi_allacciamenti = tot_nuovi_allacciamenti;
	}
	public void setTot_industriali(Double tot_industriali) {
		this.tot_industriali = tot_industriali;
	}
	public void setTot_civili(Double tot_civili) {
		this.tot_civili = tot_civili;
	}
	public void setRateo_dich(Double rateo_dich) {
		this.rateo_dich = rateo_dich;
	}
	public void setConguaglio_dich(Double conguaglio_dich) {
		this.conguaglio_dich = conguaglio_dich;
	}
	public void setTotaleDich(Double totaleDich) {
		this.totaleDich = totaleDich;
	}
	public Long getIdConsumi() {
		return idConsumi;
	}
	public void setIdConsumi(Long idConsumi) {
		this.idConsumi = idConsumi;
	}
	public Double getRettifiche() {
		return rettifiche;
	}
	public Double getArrotondamenti() {
		return arrotondamenti;
	}
	public void setRettifiche(Double rettifiche) {
		this.rettifiche = rettifiche;
	}
	public void setArrotondamenti(Double arrotondamenti) {
		this.arrotondamenti = arrotondamenti;
	}		
}
