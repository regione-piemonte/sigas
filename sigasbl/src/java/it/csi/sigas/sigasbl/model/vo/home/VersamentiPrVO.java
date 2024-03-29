/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.model.vo.base.InsModBaseVO;

import java.util.Date;
import java.util.List;

public class VersamentiPrVO extends InsModBaseVO {

	private static final long serialVersionUID = -1;
	private long idVersamento;
	private long idConsumi;
	private long idAnag;
	private int annualita;
	private String provincia;
	private Date dataVersamento;
	private String mese;
	private double importo;
	private TipoVersamentoVO tipo;
	private ConsumiPrVO consumo;
	private String note;
	private Date dataAccertamento;
	private double importoComplessivo;
	private double importo_prec;
	private AllarmiSoggettoVO allarme;
	private double interessiMora;
	private double sanzioni;
	private double interessi;
	private double differenza;
	private boolean hasPagamentiVersamenti;
	private List<Long> pagamentiVersamenti;
	
	//SIGAS-245
	//--
	private int codMese;
	public int getCodMese() {
		return codMese;
	}
	public void setCodMese(int codMese) {
		this.codMese = codMese;
	}
	//---
	
	public long getIdVersamento() {
		return idVersamento;
	}
	public void setIdVersamento(long idVersamento) {
		this.idVersamento = idVersamento;
	}
	public int getAnnualita() {
		return annualita;
	}
	public void setAnnualita(int annualita) {
		this.annualita = annualita;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public Date getDataVersamento() {
		return dataVersamento;
	}
	public void setDataVersamento(Date dataVersamento) {
		this.dataVersamento = dataVersamento;
	}
	public String getMese() {
		return mese;
	}
	public void setMese(String mese) {
		this.mese = mese;
	}
	public double getImporto() {
		return importo;
	}
	public void setImporto(double importo) {
		this.importo = importo;
	}
	public TipoVersamentoVO getTipo() {
		return tipo;
	}
	public void setTipo(TipoVersamentoVO tipo) {
		this.tipo = tipo;
	}
	public ConsumiPrVO getConsumo() {
		return consumo;
	}
	public void setConsumo(ConsumiPrVO consumo) {
		this.consumo = consumo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public long getIdConsumi() {
		return idConsumi;
	}
	public void setIdConsumi(long idConsumi) {
		this.idConsumi = idConsumi;
	}
	public long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(long idAnag) {
		this.idAnag = idAnag;
	}
	public Date getDataAccertamento() {
		return dataAccertamento;
	}
	public void setDataAccertamento(Date dataAccertamento) {
		this.dataAccertamento = dataAccertamento;
	}
	public double getImportoComplessivo() {
		return importoComplessivo;
	}
	public void setImportoComplessivo(double importoComplessivo) {
		this.importoComplessivo = importoComplessivo;
	}
	public double getImporto_prec() {
		return importo_prec;
	}
	public void setImporto_prec(double importo_prec) {
		this.importo_prec = importo_prec;
	}
	public AllarmiSoggettoVO getAllarme() {
		return allarme;
	}
	public void setAllarme(AllarmiSoggettoVO allarme) {
		this.allarme = allarme;
	}
	public double getInteressiMora() {
		return interessiMora;
	}
	public void setInteressiMora(double interessiMora) {
		this.interessiMora = interessiMora;
	}
	public double getSanzioni() {
		return sanzioni;
	}
	public void setSanzioni(double sanzioni) {
		this.sanzioni = sanzioni;
	}
	public double getInteressi() {
		return interessi;
	}
	public void setInteressi(double interessi) {
		this.interessi = interessi;
	}
	public double getDifferenza() {
		return differenza;
	}
	public void setDifferenza(double differenza) {
		this.differenza = differenza;
	}
	public boolean isHasPagamentiVersamenti() {
		return hasPagamentiVersamenti;
	}
	public void setHasPagamentiVersamenti(boolean hasPagamentiVersamenti) {
		this.hasPagamentiVersamenti = hasPagamentiVersamenti;
	}
	public List<Long> getPagamentiVersamenti() {
		return pagamentiVersamenti;
	}
	public void setPagamentiVersamenti(List<Long> pagamentiVersamenti) {
		this.pagamentiVersamenti = pagamentiVersamenti;
	}	
}
