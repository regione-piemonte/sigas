package it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects;

import java.io.Serializable;

public class GeneraAvvisoPagamentoResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String description = null;
	private String code = null;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
