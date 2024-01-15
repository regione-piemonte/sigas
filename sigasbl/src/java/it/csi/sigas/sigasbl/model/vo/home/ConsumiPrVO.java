/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import it.csi.sigas.sigasbl.common.rest.VO;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.web.serializer.CustomDateDeserializer;
import it.csi.sigas.sigasbl.web.serializer.CustomDateSerializer;

public class ConsumiPrVO implements VO {
 
	private static final long serialVersionUID = -1;
	private long id_consumi;
	private AnagraficaSoggettoVO anagraficaSoggettoVO;
	private String annualita;
	private String user_import;
	private String provincia_erogazione;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date dataAtto;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date data_presentazione;
	private String stato_dich;
	private long usi_industriali_1200;
	private long usi_industriali_up;
	private long usi_civili_120;
	private long usi_civili_480;
	private long usi_civili_1560;
	private long usi_civili_up;
	private double tot_nuovi_allacciamenti;
	private double tot_industriali;
	private double tot_civili;
	private double rettifiche;
	private double arrotondamenti;
	private double rateo_dich;
	private double rateo_dich_prec;
	private double conguaglio_dich;
	private double conguaglio_dich_prec;
	private double conguaglio_calc;
	private double addizionale_liquidata;
	private double totaleCalcolato;
	private double totaleDich;
	private List<NuovoAllacciamentoVO> nuoviAllacciamenti;
	private String note;
	private boolean concilia;
	private boolean scarti;
	private double compensazione;
	private double totaleDichOrigine;
	
	//SIGAS-245
	private double totaleVersato;	

	public double getTotaleVersato() {
		return totaleVersato;
	}

	public void setTotaleVersato(double totaleVersato) {
		this.totaleVersato = totaleVersato;
	}
	//-----
	
	private CompensazionePrVO compensazionePrVO;	

	public CompensazionePrVO getCompensazionePrVO() {
		return compensazionePrVO;
	}

	public void setCompensazionePrVO(CompensazionePrVO compensazionePrVO) {
		this.compensazionePrVO = compensazionePrVO;
	}

	public long getId_consumi() {
		return id_consumi;
	}

	public void setId_consumi(long id_consumi) {
		this.id_consumi = id_consumi;
	}
	
	public AnagraficaSoggettoVO getAnagraficaSoggettoVO() {
		return anagraficaSoggettoVO;
	}
	
	public void setAnagraficaSoggettoVO(AnagraficaSoggettoVO anagraficaSoggettoVO) {
		this.anagraficaSoggettoVO = anagraficaSoggettoVO;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public String getUser_import() {
		return user_import;
	}

	public void setUser_import(String user_import) {
		this.user_import = user_import;
	}

	public String getProvincia_erogazione() {
		return provincia_erogazione;
	}

	public void setProvincia_erogazione(String provincia_erogazione) {
		this.provincia_erogazione = provincia_erogazione;
	}

	public Date getData_presentazione() {
		return data_presentazione;
	}

	public void setData_presentazione(Date data_presentazione) {
		this.data_presentazione = data_presentazione;
	}

	public String getStato_dich() {
		return stato_dich;
	}

	public void setStato_dich(String stato_dich) {
		this.stato_dich = stato_dich;
	}

	public long getUsi_industriali_1200() {
		return usi_industriali_1200;
	}

	public void setUsi_industriali_1200(long usi_industriali_1200) {
		this.usi_industriali_1200 = usi_industriali_1200;
	}

	public long getUsi_industriali_up() {
		return usi_industriali_up;
	}

	public void setUsi_industriali_up(long usi_industriali_up) {
		this.usi_industriali_up = usi_industriali_up;
	}

	public long getUsi_civili_120() {
		return usi_civili_120;
	}

	public void setUsi_civili_120(long usi_civili_120) {
		this.usi_civili_120 = usi_civili_120;
	}

	public long getUsi_civili_480() {
		return usi_civili_480;
	}

	public void setUsi_civili_480(long usi_civili_480) {
		this.usi_civili_480 = usi_civili_480;
	}

	public long getUsi_civili_1560() {
		return usi_civili_1560;
	}

	public void setUsi_civili_1560(long usi_civili_1560) {
		this.usi_civili_1560 = usi_civili_1560;
	}

	public long getUsi_civili_up() {
		return usi_civili_up;
	}

	public void setUsi_civili_up(long usi_civili_up) {
		this.usi_civili_up = usi_civili_up;
	}

	public double getTot_nuovi_allacciamenti() {
		return tot_nuovi_allacciamenti;
	}

	public void setTot_nuovi_allacciamenti(double tot_nuovi_allacciamenti) {
		this.tot_nuovi_allacciamenti = tot_nuovi_allacciamenti;
	}

	public double getTot_industriali() {
		return tot_industriali;
	}

	public void setTot_industriali(double tot_industriali) {
		this.tot_industriali = tot_industriali;
	}

	public double getTot_civili() {
		return tot_civili;
	}

	public void setTot_civili(double tot_civili) {
		this.tot_civili = tot_civili;
	}

	public double getRettifiche() {
		return rettifiche;
	}

	public void setRettifiche(double rettifiche) {
		this.rettifiche = rettifiche;
	}

	public double getArrotondamenti() {
		return arrotondamenti;
	}

	public void setArrotondamenti(double arrotondamenti) {
		this.arrotondamenti = arrotondamenti;
	}

	public double getConguaglio_dich() {
		return conguaglio_dich;
	}

	public void setConguaglio_dich(double conguaglio_dich) {
		this.conguaglio_dich = conguaglio_dich;
	}
	
	public double getConguaglio_dich_prec() {
		return conguaglio_dich_prec;
	}
	
	public void setConguaglio_dich_prec(double conguaglio_dich_prec) {
		this.conguaglio_dich_prec = conguaglio_dich_prec;
	}

	public double getConguaglio_calc() {
		return conguaglio_calc;
	}

	public void setConguaglio_calc(double conguaglio_calc) {
		this.conguaglio_calc = conguaglio_calc;
	}

	public double getAddizionale_liquidata() {
		return addizionale_liquidata;
	}

	public void setAddizionale_liquidata(double addizionale_liquidata) {
		this.addizionale_liquidata = addizionale_liquidata;
	}

	public double getTotaleCalcolato() {
		return totaleCalcolato;
	}

	public void setTotaleCalcolato(double totaleCalcolato) {
		this.totaleCalcolato = totaleCalcolato;
	}

	public List<NuovoAllacciamentoVO> getNuoviAllacciamenti() {
		return nuoviAllacciamenti;
	}

	public void setNuoviAllacciamenti(List<NuovoAllacciamentoVO> nuoviAllacciamenti) {
		this.nuoviAllacciamenti = nuoviAllacciamenti;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}
	
	public boolean isConcilia() {
		return concilia;
	}
	
	public void setConcilia(boolean concilia) {
		this.concilia = concilia;
	}

	public boolean isScarti() {
		return scarti;
	}

	public void setScarti(boolean isScarti) {
		this.scarti = isScarti;
	}

	public double getRateo_dich() {
		return rateo_dich;
	}

	public void setRateo_dich(double rateo_dich) {
		this.rateo_dich = rateo_dich;
	}
	
	public double getRateo_dich_prec() {
		return rateo_dich_prec;
	}
	
	public void setRateo_dich_prec(double rateo_dich_prec) {
		this.rateo_dich_prec = rateo_dich_prec;
	}

	public double getTotaleDich() {
		return totaleDich;
	}

	public void setTotaleDich(double totaleDich) {
		this.totaleDich = totaleDich;
	}

	public double getCompensazione() {
		return compensazione;
	}

	public void setCompensazione(double compensazione) {
		this.compensazione = compensazione;
	}

	public double getTotaleDichOrigine() {
		return totaleDichOrigine;
	}

	public void setTotaleDichOrigine(double totaleDichOrigine) {
		this.totaleDichOrigine = totaleDichOrigine;
	}
	
	
}
