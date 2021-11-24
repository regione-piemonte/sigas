/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the sigas_pagamenti database table.
 * 
 */
@Entity
@Table(name="sigas_pagamenti")
@NamedQuery(name="SigasPagamenti.findAll", query="SELECT s FROM SigasPagamenti s")
public class SigasPagamenti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_pagamento")
	private Integer idPagamento;

	@Column(name="anno_atto_amministrativo")
	private String annoAttoAmministrativo;

	@Column(name="anno_ord_inc")
	private String annoOrdInc;

	@Column(name="cf_soggetto")
	private String cfSoggetto;

	@Column(name="cod_capitolo")
	private Integer codCapitolo;

	@Column(name="cod_soggetto")
	private Integer codSoggetto;

	private Boolean conciliato;

	@Column(name="data_elaborazione")
	private Timestamp dataElaborazione;

	@Column(name="data_emissione")
	private Timestamp dataEmissione;

	@Column(name="data_firma")
	private Timestamp dataFirma;

	@Column(name="desc_capitolo")
	private String descCapitolo;

	@Column(name="desc_distinta")
	private String descDistinta;

	@Column(name="desc_ord_inc")
	private String descOrdInc;

	@Column(name="desc_pdc_finanziario_iv")
	private String descPdcFinanziarioIv;

	@Column(name="desc_pdc_finanziario_v")
	private String descPdcFinanziarioV;

	@Column(name="desc_soggetto")
	private String descSoggetto;

	@Column(name="desc_stato_ord_inc")
	private String descStatoOrdInc;

	@Column(name="desc_tipo_atto_amministrativo")
	private String descTipoAttoAmministrativo;

	private String dichiarante;

	private String firma;

	@Column(name="importo_attuale")
	private BigDecimal importoAttuale;

	@Column(name="importo_iniziale")
	private BigDecimal importoIniziale;

	@Column(name="note_atto_amministrativo")
	private String noteAttoAmministrativo;

	@Column(name="num_atto_amministrativo")
	private Integer numAttoAmministrativo;

	@Column(name="num_ord_inc")
	private Integer numOrdInc;

	@Column(name="oggetto_atto_amministrativo")
	private String oggettoAttoAmministrativo;

	@Column(name="p_iva_soggetto")
	private String pIvaSoggetto;

	@Column(name="soggetto_id")
	private Integer soggettoId;

	//bi-directional many-to-one association to SigasPagamentiVersamenti
	@OneToMany(mappedBy="sigasPagamenti",cascade = {CascadeType.ALL})
	private List<SigasPagamentiVersamenti> sigasPagamentiVersamentis;

	public SigasPagamenti() {
	}

	public Integer getIdPagamento() {
		return this.idPagamento;
	}

	public void setIdPagamento(Integer idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getAnnoAttoAmministrativo() {
		return this.annoAttoAmministrativo;
	}

	public void setAnnoAttoAmministrativo(String annoAttoAmministrativo) {
		this.annoAttoAmministrativo = annoAttoAmministrativo;
	}

	public String getAnnoOrdInc() {
		return this.annoOrdInc;
	}

	public void setAnnoOrdInc(String annoOrdInc) {
		this.annoOrdInc = annoOrdInc;
	}

	public String getCfSoggetto() {
		return this.cfSoggetto;
	}

	public void setCfSoggetto(String cfSoggetto) {
		this.cfSoggetto = cfSoggetto;
	}

	public Integer getCodCapitolo() {
		return this.codCapitolo;
	}

	public void setCodCapitolo(Integer codCapitolo) {
		this.codCapitolo = codCapitolo;
	}

	public Integer getCodSoggetto() {
		return this.codSoggetto;
	}

	public void setCodSoggetto(Integer codSoggetto) {
		this.codSoggetto = codSoggetto;
	}

	public Boolean getConciliato() {
		return this.conciliato;
	}

	public void setConciliato(Boolean conciliato) {
		this.conciliato = conciliato;
	}

	public Timestamp getDataElaborazione() {
		return this.dataElaborazione;
	}

	public void setDataElaborazione(Timestamp dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}

	public Timestamp getDataEmissione() {
		return this.dataEmissione;
	}

	public void setDataEmissione(Timestamp dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public Timestamp getDataFirma() {
		return this.dataFirma;
	}

	public void setDataFirma(Timestamp dataFirma) {
		this.dataFirma = dataFirma;
	}

	public String getDescCapitolo() {
		return this.descCapitolo;
	}

	public void setDescCapitolo(String descCapitolo) {
		this.descCapitolo = descCapitolo;
	}

	public String getDescDistinta() {
		return this.descDistinta;
	}

	public void setDescDistinta(String descDistinta) {
		this.descDistinta = descDistinta;
	}

	public String getDescOrdInc() {
		return this.descOrdInc;
	}

	public void setDescOrdInc(String descOrdInc) {
		this.descOrdInc = descOrdInc;
	}

	public String getDescPdcFinanziarioIv() {
		return this.descPdcFinanziarioIv;
	}

	public void setDescPdcFinanziarioIv(String descPdcFinanziarioIv) {
		this.descPdcFinanziarioIv = descPdcFinanziarioIv;
	}

	public String getDescPdcFinanziarioV() {
		return this.descPdcFinanziarioV;
	}

	public void setDescPdcFinanziarioV(String descPdcFinanziarioV) {
		this.descPdcFinanziarioV = descPdcFinanziarioV;
	}

	public String getDescSoggetto() {
		return this.descSoggetto;
	}

	public void setDescSoggetto(String descSoggetto) {
		this.descSoggetto = descSoggetto;
	}

	public String getDescStatoOrdInc() {
		return this.descStatoOrdInc;
	}

	public void setDescStatoOrdInc(String descStatoOrdInc) {
		this.descStatoOrdInc = descStatoOrdInc;
	}

	public String getDescTipoAttoAmministrativo() {
		return this.descTipoAttoAmministrativo;
	}

	public void setDescTipoAttoAmministrativo(String descTipoAttoAmministrativo) {
		this.descTipoAttoAmministrativo = descTipoAttoAmministrativo;
	}

	public String getDichiarante() {
		return this.dichiarante;
	}

	public void setDichiarante(String dichiarante) {
		this.dichiarante = dichiarante;
	}

	public String getFirma() {
		return this.firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public BigDecimal getImportoAttuale() {
		return this.importoAttuale;
	}

	public void setImportoAttuale(BigDecimal importoAttuale) {
		this.importoAttuale = importoAttuale;
	}

	public BigDecimal getImportoIniziale() {
		return this.importoIniziale;
	}

	public void setImportoIniziale(BigDecimal importoIniziale) {
		this.importoIniziale = importoIniziale;
	}

	public String getNoteAttoAmministrativo() {
		return this.noteAttoAmministrativo;
	}

	public void setNoteAttoAmministrativo(String noteAttoAmministrativo) {
		this.noteAttoAmministrativo = noteAttoAmministrativo;
	}

	public Integer getNumAttoAmministrativo() {
		return this.numAttoAmministrativo;
	}

	public void setNumAttoAmministrativo(Integer numAttoAmministrativo) {
		this.numAttoAmministrativo = numAttoAmministrativo;
	}

	public Integer getNumOrdInc() {
		return this.numOrdInc;
	}

	public void setNumOrdInc(Integer numOrdInc) {
		this.numOrdInc = numOrdInc;
	}

	public String getOggettoAttoAmministrativo() {
		return this.oggettoAttoAmministrativo;
	}

	public void setOggettoAttoAmministrativo(String oggettoAttoAmministrativo) {
		this.oggettoAttoAmministrativo = oggettoAttoAmministrativo;
	}

	public String getPIvaSoggetto() {
		return this.pIvaSoggetto;
	}

	public void setPIvaSoggetto(String pIvaSoggetto) {
		this.pIvaSoggetto = pIvaSoggetto;
	}

	public Integer getSoggettoId() {
		return this.soggettoId;
	}

	public void setSoggettoId(Integer soggettoId) {
		this.soggettoId = soggettoId;
	}

	public List<SigasPagamentiVersamenti> getSigasPagamentiVersamentis() {
		return this.sigasPagamentiVersamentis;
	}

	public void setSigasPagamentiVersamentis(List<SigasPagamentiVersamenti> sigasPagamentiVersamentis) {
		this.sigasPagamentiVersamentis = sigasPagamentiVersamentis;
	}

	public SigasPagamentiVersamenti addSigasPagamentiVersamenti(SigasPagamentiVersamenti sigasPagamentiVersamenti) {
		getSigasPagamentiVersamentis().add(sigasPagamentiVersamenti);
		sigasPagamentiVersamenti.setSigasPagamenti(this);

		return sigasPagamentiVersamenti;
	}

	public SigasPagamentiVersamenti removeSigasPagamentiVersamenti(SigasPagamentiVersamenti sigasPagamentiVersamenti) {
		getSigasPagamentiVersamentis().remove(sigasPagamentiVersamenti);
		sigasPagamentiVersamenti.setSigasPagamenti(null);

		return sigasPagamentiVersamenti;
	}

}