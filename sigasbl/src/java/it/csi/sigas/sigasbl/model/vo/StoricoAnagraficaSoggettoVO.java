package it.csi.sigas.sigasbl.model.vo;

import java.util.Date;

import it.csi.sigas.sigasbl.common.rest.VO;

public class StoricoAnagraficaSoggettoVO implements VO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idStoricoAnag;
	private Long idAnag;
	private String codiceAzienda;    
	private String denominazione;
	private String indirizzo;
	private String iban;    
	private String pec;
	private String email;
	private Date dataRiferimento;
    
    private String cfPiva;
    private String ownerOperazione;
    private Long idImport;
    private String annualita;
    
    
	public Long getIdStoricoAnag() {
		return idStoricoAnag;
	}
	public void setIdStoricoAnag(Long idStoricoAnag) {
		this.idStoricoAnag = idStoricoAnag;
	}
	public Long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Long idAnag) {
		this.idAnag = idAnag;
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
	public Long getIdImport() {
		return idImport;
	}
	public void setIdImport(Long idImport) {
		this.idImport = idImport;
	}
	public String getAnnualita() {
		return annualita;
	}
	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}	

}
