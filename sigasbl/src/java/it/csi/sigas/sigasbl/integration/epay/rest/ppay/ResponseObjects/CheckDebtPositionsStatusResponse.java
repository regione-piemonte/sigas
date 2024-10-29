package it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects;

import java.io.Serializable;

public class CheckDebtPositionsStatusResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ResultBaseResponse result;
	
	private String code;
	
	private String description;

	public ResultBaseResponse getResult() {
		return result;
	}

	public void setResult(ResultBaseResponse result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

}
