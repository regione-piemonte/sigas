package it.csi.sigas.sigasbl.request.utf;

import java.io.Serializable;

public class UTFSoggettiMacroReportByIdReportRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idImportA; 
	private Long idImportB;
	private Integer annualita;
	
	public Long getIdImportA() {
		return idImportA;
	}
	public Long getIdImportB() {
		return idImportB;
	}
	public void setIdImportA(Long idImportA) {
		this.idImportA = idImportA;
	}
	public void setIdImportB(Long idImportB) {
		this.idImportB = idImportB;
	}
	public Integer getAnnualita() {
		return annualita;
	}
	public void setAnnualita(Integer annualita) {
		this.annualita = annualita;
	}
}
