package it.csi.sigas.sigasbl.model.entity.custom;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class SigasStoricoAnagraficaSoggettiCustom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idStoricoAnag;	
	private String codiceAzienda;
	private String denominazione;
	private String indirizzo;
	private String iban;
	private String email;
	private String pec;
	private Integer idAnag;
	private Date dataRiferimento;
	private String cfPiva;
	private String ownerOperazione;
	private BigInteger idImport;
	private String annualita;
	private String tipoElaborazione;
	
	public Integer getIdStoricoAnag() {
		return idStoricoAnag;
	}
	public void setIdStoricoAnag(Integer idStoricoAnag) {
		this.idStoricoAnag = idStoricoAnag;
	}
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPec() {
		return pec;
	}
	public void setPec(String pec) {
		this.pec = pec;
	}
	public Integer getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Integer idAnag) {
		this.idAnag = idAnag;
	}
	public Date getDataRiferimento() {
		return dataRiferimento;
	}
	public void setDataRiferimento(Date dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}
	public String getCfPiva() {
		return cfPiva;
	}
	public void setCfPiva(String cfPiva) {
		this.cfPiva = cfPiva;
	}
	public String getOwnerOperazione() {
		return ownerOperazione;
	}
	public void setOwnerOperazione(String ownerOperazione) {
		this.ownerOperazione = ownerOperazione;
	}
	public BigInteger getIdImport() {
		return idImport;
	}
	public void setIdImport(BigInteger idImport) {
		this.idImport = idImport;
	}
	public String getAnnualita() {
		return annualita;
	}
	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}
	public String getTipoElaborazione() {
		return tipoElaborazione;
	}
	public void setTipoElaborazione(String tipoElaborazione) {
		this.tipoElaborazione = tipoElaborazione;
	}
}
