package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class RicercaStoricoSoggettiRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String denominazioneSoggetto;
	private String annualita;
	private String indirizzo;
	private String codiceAzienda;
	private String cfPiva;
	
	public String getDenominazioneSoggetto() {
		return denominazioneSoggetto;
	}
	public void setDenominazioneSoggetto(String denominazioneSoggetto) {
		this.denominazioneSoggetto = denominazioneSoggetto;
	}
	public String getAnnualita() {
		return annualita;
	}
	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	public String getCfPiva() {
		return cfPiva;
	}
	public void setCfPiva(String cfPiva) {
		this.cfPiva = cfPiva;
	}
}
