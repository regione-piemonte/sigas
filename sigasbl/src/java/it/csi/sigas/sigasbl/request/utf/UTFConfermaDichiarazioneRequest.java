package it.csi.sigas.sigasbl.request.utf;

import java.io.Serializable;

public class UTFConfermaDichiarazioneRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idImportA;
	private Long idImportB;
	private String annualita;	
	private String azioneRichiesta;
	
	public Long getIdImportA() {
		return idImportA;
	}
	public Long getIdImportB() {
		return idImportB;
	}
	public String getAnnualita() {
		return annualita;
	}
	public String getAzioneRichiesta() {
		return azioneRichiesta;
	}
	public void setIdImportA(Long idImportA) {
		this.idImportA = idImportA;
	}
	public void setIdImportB(Long idImportB) {
		this.idImportB = idImportB;
	}
	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}
	public void setAzioneRichiesta(String azioneRichiesta) {
		this.azioneRichiesta = azioneRichiesta;
	}	

}
