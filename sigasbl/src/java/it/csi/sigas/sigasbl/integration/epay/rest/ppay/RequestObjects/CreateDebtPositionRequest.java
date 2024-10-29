package it.csi.sigas.sigasbl.integration.epay.rest.ppay.RequestObjects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class CreateDebtPositionRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	private String causale;
	
	private String codiceFiscalePartitaIVAPagatore;
	
	private String cognome;
	
	private String dataFineValidita;
	
	private String dataInizioValidita;
	
	private String dataScadenza;
	
	private String email;
	
	private String identificativoPagamento;
	
	private BigDecimal importo;
	
	private String nome;
	
	private String notePerIlPagatore;
	
	private List<ComponentePagamento> componentiPagamento;	
	
	@JsonProperty("causale") 
	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}
	
	@JsonProperty("codiceFiscalePartitaIVAPagatore")
	public String getCodiceFiscalePartitaIVAPagatore() {
		return codiceFiscalePartitaIVAPagatore;
	}

	public void setCodiceFiscalePartitaIVAPagatore(String codiceFiscalePartitaIVAPagatore) {
		this.codiceFiscalePartitaIVAPagatore = codiceFiscalePartitaIVAPagatore;
	}
	
	@JsonProperty("cognome")
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	@JsonProperty("dataFineValidita")
	public String getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	
	@JsonProperty("dataInizioValidita")
	public String getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(String dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	
	@JsonProperty("dataScadenza")
	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonProperty("identificativoPagamento")
	public String getIdentificativoPagamento() {
		return identificativoPagamento;
	}

	public void setIdentificativoPagamento(String identificativoPagamento) {
		this.identificativoPagamento = identificativoPagamento;
	}
	
	@JsonProperty("importo")
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	
	@JsonProperty("nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@JsonProperty("notePerIlPagatore")
	public String getNotePerIlPagatore() {
		return notePerIlPagatore;
	}

	public void setNotePerIlPagatore(String notePerIlPagatore) {
		this.notePerIlPagatore = notePerIlPagatore;
	}
	
	@JsonProperty("componentiPagamento")
	public List<ComponentePagamento> getComponentiPagamento() {
		return componentiPagamento;
	}

	public void setComponentiPagamento(List<ComponentePagamento> componentiPagamento) {
		this.componentiPagamento = componentiPagamento;
	}

}
