/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sigas_dich_consumi")
@NamedQuery(name = "SigasDichConsumi.findAll", query = "SELECT i FROM SigasDichConsumi i")
public class SigasDichConsumi implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SIGAS_DICH_CONSUMI_IDCONSUMI_GENERATOR", sequenceName = "SEQ_DICH_CONSUMI", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIGAS_DICH_CONSUMI_IDCONSUMI_GENERATOR")
	@Column(name = "id_consumi", unique = true, nullable = false)
	private long idConsumi;

	@ManyToOne
	@JoinColumn(name = "id_anag", nullable = false)
	private SigasAnagraficaSoggetti sigasAnagraficaSoggetti;

	@ManyToOne
	@JoinColumn(name = "id_import", nullable = false)
	private SigasImportUTF sigasImport;

	@Column(name = "annualita", unique = true, nullable = false)
	private String annualita;

	@Column(name = "provincia_erogazione")
	private String provinciaErogazione;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_presentazione")
	private Date dataPresentazione;

	@Column(name = "stato_dich")
	private String statoDich;

	@Column(name = "usi_industriali_1200")
	private long usiIndustriali1200;

	@Column(name = "usi_industriali_up", nullable = false)
	private long usiIndustrialiUp;

	@Column(name = "usi_civili_120", nullable = false)
	private long usiCivili120;

	@Column(name = "usi_civili_480", nullable = false)
	private long usiCivili480;

	@Column(name = "usi_civili_1560", nullable = false)
	private long usiCivili1560;

	@Column(name = "usi_civili_up", nullable = false)
	private long usiCiviliUp;

	@Column(name = "tot_nuovi_allacciamenti", nullable = false)
	private double totNuoviAllacciamenti;

	@Column(name = "tot_industriali", nullable = false)
	private double totIndustriali;

	@Column(name = "tot_civili", nullable = false)
	private double totCivili;

	@Column(name = "rettifiche", nullable = false)
	private double rettifiche;

	@Column(name = "arrotondamenti", nullable = false)
	private double arrotondamenti;

	@Column(name = "conguaglio_dich", nullable = false)
	private double conguaglioDich;

	@Column(name = "conguaglio_calcolato", nullable = false)
	private double conguaglioCalcolato;

	@Column(name = "rateo_dich", nullable = false)
	private double rateoDich;

	@Column(name = "totale_dich", nullable = false)
	private double totaleDich;

	@Column(name = "totale_calcolato", nullable = false)
	private double totaleCalcolato;

	@Column(name="addizionale_liquidata", nullable=false)
	private double addizionaleLiquidata;
	
	@Column(name="mod_id_consumi", nullable=false)
	private long modIdConsumi;

	@Column(name = "mod_user", nullable = false)
	private String modUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "mod_date")
	private Date moddate;

	@Column(name = "note", nullable = false)
	private String note;
	
	@Column(name = "compensazione", nullable = false)
	private double compensazione;
	
	@Column(name = "totale_dich_origine", nullable = false)
	private double totaleDichOrigine;
	
	public SigasDichConsumi() {
	}

	public long getIdConsumi() {
		return idConsumi;
	}

	public void setIdConsumi(long idConsumi) {
		this.idConsumi = idConsumi;
	}

	public SigasAnagraficaSoggetti getSigasAnagraficaSoggetti() {
		return sigasAnagraficaSoggetti;
	}

	public void setSigasAnagraficaSoggetti(SigasAnagraficaSoggetti sigasAnagraficaSoggetti) {
		this.sigasAnagraficaSoggetti = sigasAnagraficaSoggetti;
	}

	public SigasImportUTF getSigasImport() {
		return sigasImport;
	}

	public void setSigasImport(SigasImportUTF sigasImport) {
		this.sigasImport = sigasImport;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public String getProvinciaErogazione() {
		return provinciaErogazione;
	}

	public void setProvinciaErogazione(String provinciaErogazione) {
		this.provinciaErogazione = provinciaErogazione;
	}

	public Date getDataPresentazione() {
		return dataPresentazione;
	}

	public void setDataPresentazione(Date dataPresentazione) {
		this.dataPresentazione = dataPresentazione;
	}

	public String getStatoDich() {
		return statoDich;
	}

	public void setStatoDich(String statoDich) {
		this.statoDich = statoDich;
	}

	public long getUsiIndustriali1200() {
		return usiIndustriali1200;
	}

	public void setUsiIndustriali1200(long usiIndustriali1200) {
		this.usiIndustriali1200 = usiIndustriali1200;
	}

	public long getUsiIndustrialiUp() {
		return usiIndustrialiUp;
	}

	public void setUsiIndustrialiUp(long usiIndustrialiUp) {
		this.usiIndustrialiUp = usiIndustrialiUp;
	}

	public long getUsiCivili120() {
		return usiCivili120;
	}

	public void setUsiCivili120(long usiCivili120) {
		this.usiCivili120 = usiCivili120;
	}

	public long getUsiCivili480() {
		return usiCivili480;
	}

	public void setUsiCivili480(long usiCivili480) {
		this.usiCivili480 = usiCivili480;
	}

	public long getUsiCivili1560() {
		return usiCivili1560;
	}

	public void setUsiCivili1560(long usiCivili1560) {
		this.usiCivili1560 = usiCivili1560;
	}

	public long getUsiCiviliUp() {
		return usiCiviliUp;
	}

	public void setUsiCiviliUp(long usiCiviliUp) {
		this.usiCiviliUp = usiCiviliUp;
	}

	public double getTotNuoviAllacciamenti() {
		return totNuoviAllacciamenti;
	}

	public void setTotNuoviAllacciamenti( double totNuoviAllacciamenti) {
		this.totNuoviAllacciamenti = totNuoviAllacciamenti;
	}

	public double getCompensazione() {
		return compensazione;
	}

	public void setCompensazione(double compensazione) {
		this.compensazione = compensazione;
	}

	public double getTotIndustriali() {
		return totIndustriali;
	}

	public void setTotIndustriali(double totIndustriali) {
		this.totIndustriali = totIndustriali;
	}

	public double getTotCivili() {
		return totCivili;
	}

	public void setTotCivili(double totCivili) {
		this.totCivili = totCivili;
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

	public double getConguaglioDich() {
		return conguaglioDich;
	}

	public void setConguaglioDich(double conguaglioDich) {
		this.conguaglioDich = conguaglioDich;
	}

	public double getConguaglioCalcolato() {
		return conguaglioCalcolato;
	}

	public void setConguaglioCalcolato(double conguaglioCalcolato) {
		this.conguaglioCalcolato = conguaglioCalcolato;
	}

	public double getRateoDich() {
		return rateoDich;
	}

	public void setRateoDich(double rateoDich) {
		this.rateoDich = rateoDich;
	}

	public double getTotaleDich() {
		return totaleDich;
	}

	public void setTotaleDich(double totaleDich) {
		this.totaleDich = totaleDich;
	}

	public double getTotaleCalcolato() {
		return totaleCalcolato;
	}

	public void setTotaleCalcolato(double totaleCalcolato) {
		this.totaleCalcolato = totaleCalcolato;
	}

	public double getAddizionaleLiquidata() {
		return addizionaleLiquidata;
	}

	public void setAddizionaleLiquidata(double addizionaleLiquidata) {
		this.addizionaleLiquidata = addizionaleLiquidata;
	}

	public long getModIdConsumi() {
		return modIdConsumi;
	}

	public void setModIdConsumi(long modIdConsumi) {
		this.modIdConsumi = modIdConsumi;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public Date getModdate() {
		return moddate;
	}

	public void setModdate(Date moddate) {
		this.moddate = moddate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getTotaleDichOrigine() {
		return totaleDichOrigine;
	}

	public void setTotaleDichOrigine(double totaleDichOrigine) {
		this.totaleDichOrigine = totaleDichOrigine;
	}
	
	

}
