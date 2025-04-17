package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class RicercaSoggettoIncorporatoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idSoggettoIncorporato;
	private String annualita;
	
	public Long getIdSoggettoIncorporato() {
		return idSoggettoIncorporato;
	}
	public void setIdSoggettoIncorporato(Long idSoggettoIncorporato) {
		this.idSoggettoIncorporato = idSoggettoIncorporato;
	}
	public String getAnnualita() {
		return annualita;
	}
	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}	

}
