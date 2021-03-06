/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import java.util.Date;

import it.csi.sigas.sigasbl.common.rest.VO;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;

public class RimborsoVO implements VO {

	private static final long serialVersionUID = -1;
	private long idRimborso;
	private AnagraficaSoggettoVO anagraficaSoggettoVO;
	private AnaComunicazioniVO anaComunicazioniVO;
	private String annualita;
	private Date dataIstanza;
	private Date dataRimborso;
	private double importo;
	private double importoRimborsato;
	private String statoPratica;
	private boolean allarme;
	private long version;
	private Date dataVersamento;
	private String insUser;
	private Date insDate;
	private Date modDate;
	private String modUser;
	
	public boolean isAllarme() {
		return allarme;
	}
	public void setAllarme(boolean allarme) {
		this.allarme = allarme;
	}
	
	public long getIdRimborso() {
		return idRimborso;
	}
	public void setIdRimborso(long idRimborso) {
		this.idRimborso = idRimborso;
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
	public Date getDataIstanza() {
		return dataIstanza;
	}
	public void setDataIstanza(Date dataIstanza) {
		this.dataIstanza = dataIstanza;
	}
	public Date getDataRimborso() {
		return dataRimborso;
	}
	public void setDataRimborso(Date dataRimborso) {
		this.dataRimborso = dataRimborso;
	}
	public double getImporto() {
		return importo;
	}
	public void setImporto(double importo) {
		this.importo = importo;
	}
	public double getImportoRimborsato() {
		return importoRimborsato;
	}
	public void setImportoRimborsato(double importoRimborsato) {
		this.importoRimborsato = importoRimborsato;
	}
	public String getStatoPratica() {
		return statoPratica;
	}
	public void setStatoPratica(String statoPratica) {
		this.statoPratica = statoPratica;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public AnaComunicazioniVO getAnaComunicazioniVO() {
		return anaComunicazioniVO;
	}
	public void setAnaComunicazioniVO(AnaComunicazioniVO anaComunicazioniVO) {
		this.anaComunicazioniVO = anaComunicazioniVO;
	}
	public Date getDataVersamento() {
		return dataVersamento;
	}
	public void setDataVersamento(Date dataVersamento) {
		this.dataVersamento = dataVersamento;
	}
	public String getInsUser() {
		return insUser;
	}
	public void setInsUser(String insUser) {
		this.insUser = insUser;
	}
	public Date getInsDate() {
		return insDate;
	}
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	public String getModUser() {
		return modUser;
	}
	public void setModUser(String modUser) {
		this.modUser = modUser;
	}
	
	
	
}
