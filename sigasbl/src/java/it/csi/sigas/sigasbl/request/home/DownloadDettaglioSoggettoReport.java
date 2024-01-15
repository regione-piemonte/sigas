package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;
import java.util.List;

public class DownloadDettaglioSoggettoReport implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	
	private String denominazione;
	private String codiceAzienda;
	private String indirizzo;
	private String iban;
	private String telefono;
	private String pec;
	private String email;
	private String note;
	private String fideussione;	
	private Double importoFideussione;
	private String comune;
	private String provincia;
	
    private List<ItemSoggettoReport> itemSoggettoReportList;

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFideussione() {
		return fideussione;
	}

	public void setFideussione(String fideussione) {
		this.fideussione = fideussione;
	}

	public Double getImportoFideussione() {
		return importoFideussione;
	}

	public void setImportoFideussione(Double importoFideussione) {
		this.importoFideussione = importoFideussione;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public List<ItemSoggettoReport> getItemSoggettoReportList() {
		return itemSoggettoReportList;
	}

	public void setItemSoggettoReportList(List<ItemSoggettoReport> itemSoggettoReportList) {
		this.itemSoggettoReportList = itemSoggettoReportList;
	}    

}
