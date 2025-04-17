package it.csi.sigas.sigasbl.request.utf;

import java.io.Serializable;

public class UTFConfermaSoggettoDichiarazioneRequest implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private Long idImport;
	private String annualita;
	private Long idAnag;
	private String azioneRichiesta;

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
	public Long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Long idAnag) {
		this.idAnag = idAnag;
	}
	public String getAzioneRichiesta() {
		return azioneRichiesta;
	}
	public void setAzioneRichiesta(String azioneRichiesta) {
		this.azioneRichiesta = azioneRichiesta;
	}
}
