/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.vo.template;

import java.util.Date;

import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;


public class DatiTemplateVO{

	private static final long serialVersionUID = -8153081670459225437L;

	// template rateizzazione
//	private List<SoggettoVO> listaSoggetti;
//	private BigDecimal importoTotale;
//	private BigDecimal numeroRate;
//	private Boolean scadenzaDefinita;
//	private BigDecimal importoPrimaRata;
//	@JsonSerialize(using = CustomDateSerializer.class)
//	@JsonDeserialize(using = CustomDateDeserializer.class)
//	private LocalDate scadenzaPrimaRata;
//	private BigDecimal importoAltreRate;
//	private BigDecimal importoUltimaRata;
//	private String dirigente;
//	private String funzionario;	
	
	private AnagraficaSoggettoVO anagraficaSoggettoVO;
	
	private String mailSettoreTributi;	

	// header
	private String numeroProtocollo;
	private String classificazione;
//	private String fn;

	private String nomeDirigente;
	private String cognomeDirigente;
	
	private Date dataProtocollazione;

//	public BigDecimal getImportoTotale() {
//		return importoTotale;
//	}
//
//	public BigDecimal getNumeroRate() {
//		return numeroRate;
//	}
//
//	public Boolean getScadenzaDefinita() {
//		return scadenzaDefinita;
//	}
//
//	public BigDecimal getImportoPrimaRata() {
//		return importoPrimaRata;
//	}
//
//	public LocalDate getScadenzaPrimaRata() {
//		return scadenzaPrimaRata;
//	}
//
//	public BigDecimal getImportoAltreRate() {
//		return importoAltreRate;
//	}
//
//	public BigDecimal getImportoUltimaRata() {
//		return importoUltimaRata;
//	}
//
//	public String getDirigente() {
//		return dirigente;
//	}
//
//	public void setImportoTotale(BigDecimal importoTotale) {
//		this.importoTotale = importoTotale;
//	}
//
//	public void setNumeroRate(BigDecimal numeroRate) {
//		this.numeroRate = numeroRate;
//	}
//
//	public void setScadenzaDefinita(Boolean scadenzaDefinita) {
//		this.scadenzaDefinita = scadenzaDefinita;
//	}
//
//	public void setImportoPrimaRata(BigDecimal importoPrimaRata) {
//		this.importoPrimaRata = importoPrimaRata;
//	}
//
//	public void setScadenzaPrimaRata(LocalDate scadenzaPrimaRata) {
//		this.scadenzaPrimaRata = scadenzaPrimaRata;
//	}
//
//	public void setImportoAltreRate(BigDecimal importoAltreRate) {
//		this.importoAltreRate = importoAltreRate;
//	}
//
//	public void setImportoUltimaRata(BigDecimal importoUltimaRata) {
//		this.importoUltimaRata = importoUltimaRata;
//	}
//
//	public void setDirigente(String dirigente) {
//		this.dirigente = dirigente;
//	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public String getClassificazione() {
		return classificazione;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}

//	public List<SoggettoVO> getListaSoggetti() {
//		return listaSoggetti;
//	}
//
//	public void setListaSoggetti(List<SoggettoVO> listaSoggetti) {
//		this.listaSoggetti = listaSoggetti;
//	}

//	public String getFn() {
//		return fn;
//	}
//
//	public void setFn(String fn) {
//		this.fn = fn;
//	}

//	public String getNome() {
//		return nome;
//	}
//
//	public void setNome(String nome) {
//		this.nome = nome;
//	}
//	
//	public String getFunzionario() {
//		return funzionario;
//	}
//
//	public void setFunzionario(String funzionario) {
//		this.funzionario = funzionario;
//	}
	
	public String getMailSettoreTributi() {
		return mailSettoreTributi;
	}

	public void setMailSettoreTributi(String mailSettoreTributi) {
		this.mailSettoreTributi = mailSettoreTributi;
	}

	public AnagraficaSoggettoVO getAnagraficaSoggettoVO() {
		return anagraficaSoggettoVO;
	}

	public void setAnagraficaSoggettoVO(AnagraficaSoggettoVO anagraficaSoggettoVO) {
		this.anagraficaSoggettoVO = anagraficaSoggettoVO;
	}

	public String getNomeDirigente() {
		return nomeDirigente;
	}

	public void setNomeDirigente(String nomeDirigente) {
		this.nomeDirigente = nomeDirigente;
	}

	public String getCognomeDirigente() {
		return cognomeDirigente;
	}

	public void setCognomeDirigente(String cognomeDirigente) {
		this.cognomeDirigente = cognomeDirigente;
	}

	public Date getDataProtocollazione() {
		return dataProtocollazione;
	}

	public void setDataProtocollazione(Date dataProtocollazione) {
		this.dataProtocollazione = dataProtocollazione;
	}
	
	
	

}
