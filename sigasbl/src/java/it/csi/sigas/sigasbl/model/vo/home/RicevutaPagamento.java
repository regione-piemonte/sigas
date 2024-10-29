package it.csi.sigas.sigasbl.model.vo.home;

import java.io.Serializable;
import java.math.BigDecimal;

public class RicevutaPagamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String enteBeneficiario;
	
	private String cfEnte;
	
	private BigDecimal importoPagato;
	
	private String codiceAvviso;
	
	private String iuv;
	
	private String ragioneSociale;
	
	private String cfRivenditore;

	public String getEnteBeneficiario() {
		return enteBeneficiario;
	}

	public void setEnteBeneficiario(String enteBeneficiario) {
		this.enteBeneficiario = enteBeneficiario;
	}

	public String getCfEnte() {
		return cfEnte;
	}

	public void setCfEnte(String cfEnte) {
		this.cfEnte = cfEnte;
	}

	public BigDecimal getImportoPagato() {
		return importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public String getCodiceAvviso() {
		return codiceAvviso;
	}

	public void setCodiceAvviso(String codiceAvviso) {
		this.codiceAvviso = codiceAvviso;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getCfRivenditore() {
		return cfRivenditore;
	}

	public void setCfRivenditore(String cfRivenditore) {
		this.cfRivenditore = cfRivenditore;
	}			

}
