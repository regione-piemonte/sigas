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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sigas_carrello_pagamenti")
@NamedNativeQueries({
	@NamedNativeQuery(name = "SigasPaymentCart.findFOPaymentTypes", query = "SELECT v.*" +
		"	FROM sigas_tipo_carrello v" +
		"	WHERE	NOT(v.denominazione = 'Accertamento') " +
		"	ORDER BY id_tipo_carrello", resultClass = SigasTipoCarrello.class)
})
//@SecondaryTable(name="SigasAnagraficaSoggetti", pkJoinColumns=@PrimaryKeyJoinColumn(name="id_anag", referencedColumnName="id_carrello"))
public class SigasPaymentCart implements Serializable {
	
	public final static int STATO_CARRELLO_APERTO = 10;
	public final static int STATO_CARRELLO_COMPLETO = 20;
	public final static int STATO_CARRELLO_PAGAMENTO_AVVIATO = 30;
	public final static int STATO_CARRELLO_PAGAMENTO_NOTIFICATO = 40;
	public final static int STATO_CARRELLO_PAGATO = 50;
	public final static int STATO_CARRELLO_PAGAMENTO_INCOMPLETO = 51;
	
	public final static int CART_PAYMENT_TYPE_PAGOPA = 1;
	public final static int CART_PAYMENT_TYPE_DIRECTDEBIT = 2;
	public final static int CART_PAYMENT_TYPE_IUV_PAYMENT = 3;
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_PAYMENT_CART_ID_CARRELLO_GENERATOR" , sequenceName="SEQ_CARRELLO_PAGAMENTI", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_PAYMENT_CART_ID_CARRELLO_GENERATOR")
	@Column(name="id_carrello", unique=true, nullable=false)
	private long idCarrello;

	@Column(name="codice_pagamento")
	private String codicePagamento;

	@Column(name="email")
	private String email;

	@Column(name="fk_modalita_pagamento")
	private Integer fkModalitaPagamento;

	@Column(name="data_pagamento")
	private Date dataPagamento = new Date();

	@Column(name="data_versamento")
	private Date dataVersamento;

	@Column(name="fk_tipo_pagamento")
	private Integer fkTipoPagamento;

	@Column(name="fk_stato_carrello")
	private Integer fkStatoCarrello = STATO_CARRELLO_APERTO;

	@Column(name="importo")
	private Double importo;

	@Column(name="denominazione_versante")
	private String denominazioneVersante;

	/*
	@Column(name="pagamento_riconciliato")
	private String pagamentoRiconciliato;

	@Column(name="importo_pagato_riconciliato")
	private Boolean importoPagatoRiconciliato;

	@Column(name="data_valuta_riconciliata")
	private Date dataValutaRiconciliata;

	@Column(name="data_quietanza")
	private Date dataQuietanza;

	@Column(name="imposta_riconciliata")
	private String impostaRiconciliata;

	@Column(name="sanzione_riconciliata")
	private String sanzioneRiconciliata;

	@Column(name="interesse_riconciliato")
	private String interesseRiconciliato;

	@Column(name="totale_riconciliato")
	private String totaleRiconciliato;
	 */
	
	@Column(name="fk_utente_insert")
	private Integer fkUtenteInsert;

	@Column(name="data_insert")
	private Date dataInsert;

	@Column(name="fk_utente_update")
	private Integer fkUtenteUpdate;

	@Column(name="data_update")
	private Date dataUpdate;

	@Column(name="version")
	private Integer version;

	@Column(name="mese")
	private Integer mese;

	@Column(name="anno")
	private String anno;

	@Column(name="fk_provincia")
	private Integer fkProvincia;

	@Column(name="fk_anag_soggetto")
	private Integer fkAnagSoggetto;

	@Column(name="fk_tipo_carrello")
	private Integer fkTipoCarello;

	@Column(name="fk_dich_versamento")
	private Integer fkDichVersamento;
	
	@Column(name="sigla_provincia")
	private String siglaProvincia;
	
	@Column(name="iuv")
	private String iuv;
	
	@Column(name="id_posizione_debitoria")
	private String IdPosDebitoria;
	
	@Column(name="note")
	private String note;	
	
	@Column(name="cf_piva", nullable=false)
	private String cfPiva;
	
	
	
	
	public String getCfPiva() {
		return cfPiva;
	}

	public void setCfPiva(String cfPiva) {
		this.cfPiva = cfPiva;
	}

	//@Transient
	//@Column(table="SigasAnagraficaSoggetti", name="codiceAzienda", insertable = false, updatable = false)
	//@Column(name="codice_azienda", insertable = false, updatable = false)
	@Column(name="codice_azienda")
	private String codiceAzienda;

	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	public Long getIdCarrello() {
		return idCarrello;
	}

	public void setIdCarrello(Long idCarrello) {
		this.idCarrello = idCarrello;
	}

	public String getCodicePagamento() {
		return codicePagamento;
	}

	public void setCodicePagamento(String codicePagamento) {
		this.codicePagamento = codicePagamento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFkModalitaPagamento() {
		return fkModalitaPagamento;
	}

	public void setFkModalitaPagamento(Integer fkModalitaPagamento) {
		this.fkModalitaPagamento = fkModalitaPagamento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVersamento() {
		return dataVersamento;
	}

	public void setDataVersamento(Date dataVersamento) {
		this.dataVersamento = dataVersamento;
	}

	public Integer getFkTipoPagamento() {
		return fkTipoPagamento;
	}

	public void setFkTipoPagamento(Integer fkTipoPagamento) {
		this.fkTipoPagamento = fkTipoPagamento;
	}

	public Integer getFkStatoCarrello() {
		return fkStatoCarrello;
	}

	public void setFkStatoCarrello(Integer fkStatoCarrello) {
		this.fkStatoCarrello = fkStatoCarrello;
	}

	public Double getImporto() {
		return importo;
	}

	public void setImporto(Double importo) {
		this.importo = importo;
	}

	public String getDenominazioneVersante() {
		return denominazioneVersante;
	}

	public void setDenominazioneVersante(String denominazioneVersante) {
		this.denominazioneVersante = denominazioneVersante;
	}

	/*
	public String getPagamentoRiconciliato() {
		return pagamentoRiconciliato;
	}

	public void setPagamentoRiconciliato(String pagamentoRiconciliato) {
		this.pagamentoRiconciliato = pagamentoRiconciliato;
	}

	public Boolean getImportoPagatoRiconciliato() {
		return importoPagatoRiconciliato;
	}

	public void setImportoPagatoRiconciliato(Boolean importoPagatoRiconciliato) {
		this.importoPagatoRiconciliato = importoPagatoRiconciliato;
	}

	public Date getDataValutaRiconciliata() {
		return dataValutaRiconciliata;
	}

	public void setDataValutaRiconciliata(Date dataValutaRiconciliata) {
		this.dataValutaRiconciliata = dataValutaRiconciliata;
	}

	public Date getDataQuietanza() {
		return dataQuietanza;
	}

	public void setDataQuietanza(Date dataQuietanza) {
		this.dataQuietanza = dataQuietanza;
	}

	public String getImpostaRiconciliata() {
		return impostaRiconciliata;
	}

	public void setImpostaRiconciliata(String impostaRiconciliata) {
		this.impostaRiconciliata = impostaRiconciliata;
	}

	public String getSanzioneRiconciliata() {
		return sanzioneRiconciliata;
	}

	public void setSanzioneRiconciliata(String sanzioneRiconciliata) {
		this.sanzioneRiconciliata = sanzioneRiconciliata;
	}

	public String getInteresseRiconciliato() {
		return interesseRiconciliato;
	}

	public void setInteresseRiconciliato(String interesseRiconciliato) {
		this.interesseRiconciliato = interesseRiconciliato;
	}

	public String getTotaleRiconciliato() {
		return totaleRiconciliato;
	}

	public void setTotaleRiconciliato(String totaleRiconciliato) {
		this.totaleRiconciliato = totaleRiconciliato;
	}
	*/

	public Integer getFkUtenteInsert() {
		return fkUtenteInsert;
	}

	public void setFkUtenteInsert(Integer fkUtenteInsert) {
		this.fkUtenteInsert = fkUtenteInsert;
	}

	public Date getDataInsert() {
		return dataInsert;
	}

	public void setDataInsert(Date dataInsert) {
		this.dataInsert = dataInsert;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getMese() {
		return mese;
	}

	public void setMese(Integer mese) {
		this.mese = mese;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public Integer getFkProvincia() {
		return fkProvincia;
	}

	public void setFkProvincia(Integer fkProvincia) {
		this.fkProvincia = fkProvincia;
	}

	public Integer getFkAnagSoggetto() {
		return fkAnagSoggetto;
	}

	public void setFkAnagSoggetto(Integer fkAnagSoggetto) {
		this.fkAnagSoggetto = fkAnagSoggetto;
	}

	public Integer getFkTipoCarrello() {
		return fkTipoCarello;
	}

	public void setFkTipoCarrello(Integer fkTipoCarello) {
		this.fkTipoCarello = fkTipoCarello;
	}

	public Integer getFkDichVersamento() {
		return fkDichVersamento;
	}

	public void setFkDichVersamento(Integer fkDichVersamento) {
		this.fkDichVersamento = fkDichVersamento;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getIdPosDebitoria() {
		return IdPosDebitoria;
	}

	public void setIdPosDebitoria(String idPosDebitoria) {
		IdPosDebitoria = idPosDebitoria;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String printAll() {
		return "idCarrello: " + idCarrello + ", " +
				"codicePagamento: " + codicePagamento + ", " +
				"email: " + email + ", " +
				"fkModalitaPagamento: " + fkModalitaPagamento + ", " +
				"dataPagamento" + dataPagamento + ", " +
				"dataVersamento: " + dataVersamento + ", " +
				"fkTipoPagamento: " + fkTipoPagamento + ", " +
				"STATO_CARRELLO_APERTO: " + STATO_CARRELLO_APERTO + ", " +
				"importo: " + importo + ", " +
				"denominazioneVersante: " + denominazioneVersante + ", " +
				"fkUtenteInsert: " + fkUtenteInsert + ", " +
				"dataInsert: " + dataInsert + ", " +
				"fkUtenteUpdate: " + fkUtenteUpdate + ", " +
				"dataUpdate: " + dataUpdate + ", " +
				"version: " + version + ", " +
				"mese: " + mese + ", " +
				"anno: " + anno + ", " +
				"fkProvincia: " + fkProvincia + ", " +
				"fkAnagSoggetto: " + fkAnagSoggetto + ", " +
				"fkTipoCarello: " + fkTipoCarello + ", " +
				"fkDichVersamento: " + fkDichVersamento + ", " +
				"siglaProvincia: " + siglaProvincia + ", " +
				"iuv: " + iuv + ", " +
				"IdPosDebitoria: " + IdPosDebitoria + ", " +
				"note: " + note;
	}
}
