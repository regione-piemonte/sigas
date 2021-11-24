/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import it.csi.sigas.sigasbl.common.rest.VO;
import it.csi.sigas.sigasbl.model.entity.SigasPagamentiVersamenti;

public class OrdinativiIncassoVO implements VO {

	private static final long serialVersionUID = 1L;

	private Integer idPagamento;

	private String annoAttoAmministrativo;

	private String annoOrdInc;

	private String cfSoggetto;

	private Integer codCapitolo;

	private Integer codSoggetto;

	private Boolean conciliato;

	private Timestamp dataElaborazione;

	private Timestamp dataEmissione;

	private Timestamp dataFirma;

	private String descCapitolo;

	private String descDistinta;

	private String descOrdInc;

	private String descPdcFinanziarioIv;

	private String descPdcFinanziarioV;

	private String descSoggetto;

	private String descStatoOrdInc;

	private String descTipoAttoAmministrativo;

	private String dichiarante;

	private String firma;

	private BigDecimal importoAttuale;

	private BigDecimal importoIniziale;

	private String noteAttoAmministrativo;

	private Integer numAttoAmministrativo;

	private Integer numOrdInc;

	private String oggettoAttoAmministrativo;

	private String pIvaSoggetto;

	private Integer soggettoId;
	
	private List<PagamentiVersamentiVO> sigasPagamentiVersamentis;


	public Integer getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(Integer idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getAnnoAttoAmministrativo() {
		return annoAttoAmministrativo;
	}

	public void setAnnoAttoAmministrativo(String annoAttoAmministrativo) {
		this.annoAttoAmministrativo = annoAttoAmministrativo;
	}

	public String getAnnoOrdInc() {
		return annoOrdInc;
	}

	public void setAnnoOrdInc(String annoOrdInc) {
		this.annoOrdInc = annoOrdInc;
	}

	public String getCfSoggetto() {
		return cfSoggetto;
	}

	public void setCfSoggetto(String cfSoggetto) {
		this.cfSoggetto = cfSoggetto;
	}

	public Integer getCodCapitolo() {
		return codCapitolo;
	}

	public void setCodCapitolo(Integer codCapitolo) {
		this.codCapitolo = codCapitolo;
	}

	public Integer getCodSoggetto() {
		return codSoggetto;
	}

	public void setCodSoggetto(Integer codSoggetto) {
		this.codSoggetto = codSoggetto;
	}

	public Boolean getConciliato() {
		return conciliato;
	}

	public void setConciliato(Boolean conciliato) {
		this.conciliato = conciliato;
	}

	public Timestamp getDataElaborazione() {
		return dataElaborazione;
	}

	public void setDataElaborazione(Timestamp dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}

	public Timestamp getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(Timestamp dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public Timestamp getDataFirma() {
		return dataFirma;
	}

	public void setDataFirma(Timestamp dataFirma) {
		this.dataFirma = dataFirma;
	}

	public String getDescCapitolo() {
		return descCapitolo;
	}

	public void setDescCapitolo(String descCapitolo) {
		this.descCapitolo = descCapitolo;
	}

	public String getDescDistinta() {
		return descDistinta;
	}

	public void setDescDistinta(String descDistinta) {
		this.descDistinta = descDistinta;
	}

	public String getDescOrdInc() {
		return descOrdInc;
	}

	public void setDescOrdInc(String descOrdInc) {
		this.descOrdInc = descOrdInc;
	}

	public String getDescPdcFinanziarioIv() {
		return descPdcFinanziarioIv;
	}

	public void setDescPdcFinanziarioIv(String descPdcFinanziarioIv) {
		this.descPdcFinanziarioIv = descPdcFinanziarioIv;
	}

	public String getDescPdcFinanziarioV() {
		return descPdcFinanziarioV;
	}

	public void setDescPdcFinanziarioV(String descPdcFinanziarioV) {
		this.descPdcFinanziarioV = descPdcFinanziarioV;
	}

	public String getDescSoggetto() {
		return descSoggetto;
	}

	public void setDescSoggetto(String descSoggetto) {
		this.descSoggetto = descSoggetto;
	}

	public String getDescStatoOrdInc() {
		return descStatoOrdInc;
	}

	public void setDescStatoOrdInc(String descStatoOrdInc) {
		this.descStatoOrdInc = descStatoOrdInc;
	}

	public String getDescTipoAttoAmministrativo() {
		return descTipoAttoAmministrativo;
	}

	public void setDescTipoAttoAmministrativo(String descTipoAttoAmministrativo) {
		this.descTipoAttoAmministrativo = descTipoAttoAmministrativo;
	}

	public String getDichiarante() {
		return dichiarante;
	}

	public void setDichiarante(String dichiarante) {
		this.dichiarante = dichiarante;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public BigDecimal getImportoAttuale() {
		return importoAttuale;
	}

	public void setImportoAttuale(BigDecimal importoAttuale) {
		this.importoAttuale = importoAttuale;
	}

	public BigDecimal getImportoIniziale() {
		return importoIniziale;
	}

	public void setImportoIniziale(BigDecimal importoIniziale) {
		this.importoIniziale = importoIniziale;
	}

	public String getNoteAttoAmministrativo() {
		return noteAttoAmministrativo;
	}

	public void setNoteAttoAmministrativo(String noteAttoAmministrativo) {
		this.noteAttoAmministrativo = noteAttoAmministrativo;
	}

	public Integer getNumAttoAmministrativo() {
		return numAttoAmministrativo;
	}

	public void setNumAttoAmministrativo(Integer numAttoAmministrativo) {
		this.numAttoAmministrativo = numAttoAmministrativo;
	}

	public Integer getNumOrdInc() {
		return numOrdInc;
	}

	public void setNumOrdInc(Integer numOrdInc) {
		this.numOrdInc = numOrdInc;
	}

	public String getOggettoAttoAmministrativo() {
		return oggettoAttoAmministrativo;
	}

	public void setOggettoAttoAmministrativo(String oggettoAttoAmministrativo) {
		this.oggettoAttoAmministrativo = oggettoAttoAmministrativo;
	}

	public String getpIvaSoggetto() {
		return pIvaSoggetto;
	}

	public void setpIvaSoggetto(String pIvaSoggetto) {
		this.pIvaSoggetto = pIvaSoggetto;
	}

	public Integer getSoggettoId() {
		return soggettoId;
	}

	public void setSoggettoId(Integer soggettoId) {
		this.soggettoId = soggettoId;
	}

	public List<PagamentiVersamentiVO> getSigasPagamentiVersamentis() {
		return sigasPagamentiVersamentis;
	}

	public void setSigasPagamentiVersamentis(List<PagamentiVersamentiVO> sigasPagamentiVersamentis) {
		this.sigasPagamentiVersamentis = sigasPagamentiVersamentis;
	}

	
	


}
