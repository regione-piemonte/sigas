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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name="sigas_carrello_notifica")
public class SigasPaymentCartNotify implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_PAYMENT_CART_ID_CARRELLO_NOTIFY_GENERATOR" , sequenceName="seq_carrello_notifica", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_PAYMENT_CART_ID_CARRELLO_NOTIFY_GENERATOR")
	@Column(name="id_carrello_notifica", unique=true, nullable=false)
	private long idCarrelloNotifica;

	@Column(name="codice_pagamento")
	private String codicePagamento;

	@Column(name="data_insert")
	private Date dataInsert = new Date();

	@Column(name="fk_utente_insert")
	private Integer fkUtenteInsert;

	@Column(name="fk_utente_update")
	private Integer fkUtenteUpdate;

	@Column(name="data_update")
	private Date dataUpdate;

	@Column(name="id_posizione_debitoria")
	private String IdPosizioneDebitoria;
	
	@Column(name="anno_di_riferimento")
	private String AnnoDiRiferimento;
	
	@Column(name="iuv")
	private String IUV;
	
	@Column(name="importo_pagato")
	private String ImportoPagato;
	
	@Column(name="data_scadenza")
	private Date DataScadenza;
	
	@Column(name="descrizione_causale_versamento")
	private String DescrizioneCausaleVersamento;
	
	@Column(name="data_esito_pagamento")
	private Date DataEsitoPagamento;
	
	@Column(name="soggetto_debitore")
	private String SoggettoDebitore;
	
	@Column(name="soggetto_versante")
	private String SoggettoVersante;
	
	@Column(name="dati_transazione_psp")
	private String DatiTransazionePSP;
	
	@Column(name="dati_specifici_riscossione")
	private String DatiSpecificiRiscossione;
	
	@Column(name="note")
	private String Note;
	
	@Column(name="codice_avviso")
	private String CodiceAvviso;
	
	public long getIdCarrelloNotifica() {
		return idCarrelloNotifica;
	}

	public void setIdCarrelloNotifica(long idCarrelloNotifica) {
		this.idCarrelloNotifica = idCarrelloNotifica;
	}

	public Date getDataInsert() {
		return dataInsert;
	}

	public void setDataInsert(Date dataInsert) {
		this.dataInsert = dataInsert;
	}

	public Integer getFkUtenteInsert() {
		return fkUtenteInsert;
	}

	public void setFkUtenteInsert(Integer fkUtenteInsert) {
		this.fkUtenteInsert = fkUtenteInsert;
	}

	public Integer getFkUtenteUpdate() {
		return fkUtenteUpdate;
	}

	public void setFkUtenteUpdate(Integer fkUtenteUpdate) {
		this.fkUtenteUpdate = fkUtenteUpdate;
	}

	public Date getDataUpdate() {
		return dataUpdate;
	}

	public void setDataUpdate(Date dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

	public String getIdPosizioneDebitoria() {
		return IdPosizioneDebitoria;
	}

	public void setIdPosizioneDebitoria(String idPosizioneDebitoria) {
		IdPosizioneDebitoria = idPosizioneDebitoria;
	}

	public String getAnnoDiRiferimento() {
		return AnnoDiRiferimento;
	}

	public void setAnnoDiRiferimento(String annoDiRiferimento) {
		AnnoDiRiferimento = annoDiRiferimento;
	}

	public String getIUV() {
		return IUV;
	}

	public void setIUV(String iUV) {
		IUV = iUV;
	}

	public String getImportoPagato() {
		return ImportoPagato;
	}

	public void setImportoPagato(String importoPagato) {
		ImportoPagato = importoPagato;
	}

	public Date getDataScadenza() {
		return DataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		DataScadenza = dataScadenza;
	}

	public String getDescrizioneCausaleVersamento() {
		return DescrizioneCausaleVersamento;
	}

	public void setDescrizioneCausaleVersamento(String descrizioneCausaleVersamento) {
		DescrizioneCausaleVersamento = descrizioneCausaleVersamento;
	}

	public Date getDataEsitoPagamento() {
		return DataEsitoPagamento;
	}

	public void setDataEsitoPagamento(Date dataEsitoPagamento) {
		DataEsitoPagamento = dataEsitoPagamento;
	}

	public String getSoggettoDebitore() {
		return SoggettoDebitore;
	}

	public void setSoggettoDebitore(String soggettoDebitore) {
		SoggettoDebitore = soggettoDebitore;
	}

	public String getSoggettoVersante() {
		return SoggettoVersante;
	}

	public void setSoggettoVersante(String soggettoVersante) {
		SoggettoVersante = soggettoVersante;
	}

	public String getDatiTransazionePSP() {
		return DatiTransazionePSP;
	}

	public void setDatiTransazionePSP(String datiTransazionePSP) {
		DatiTransazionePSP = datiTransazionePSP;
	}

	public String getDatiSpecificiRiscossione() {
		return DatiSpecificiRiscossione;
	}

	public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {
		DatiSpecificiRiscossione = datiSpecificiRiscossione;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

	public String getCodiceAvviso() {
		return CodiceAvviso;
	}

	public void setCodiceAvviso(String codiceAvviso) {
		CodiceAvviso = codiceAvviso;
	}

	public String getCodicePagamento() {
		return codicePagamento;
	}

	public void setCodicePagamento(String codicePagamento) {
		this.codicePagamento = codicePagamento;
	}

	public String printAll() {
		return "idCarrelloNotifica: " + idCarrelloNotifica + ", " +
				"codicePagamento: " + codicePagamento + ", " +
				"dataInsert: " + dataInsert + ", " +
				"fkUtenteInsert: " + fkUtenteInsert + ", " +
				"fkUtenteUpdate: " + fkUtenteUpdate + ", " +
				"dataUpdate: " + dataUpdate + ", " +
				"IdPosizioneDebitoria: " + IdPosizioneDebitoria + ", " +
				"AnnoDiRiferimento: " + AnnoDiRiferimento + ", " +
				"IUV: " + IUV + ", " +
				"ImportoPagato: " + ImportoPagato + ", " +
				"DataScadenza: " + DataScadenza + ", " +
				"DescrizioneCausaleVersamento: " + DescrizioneCausaleVersamento + ", " +
				"DataEsitoPagamento: " + DataEsitoPagamento + ", " +
				"SoggettoDebitore: " + SoggettoDebitore + ", " +
				"SoggettoVersante: " + SoggettoVersante + ", " +
				"DatiTransazionePSP: " + DatiTransazionePSP + ", " +
				"DatiSpecificiRiscossione: " + DatiSpecificiRiscossione + ", " +
				"Note: " + Note + ", " +
				"CodiceAvviso: " + CodiceAvviso;
	}
	
}
