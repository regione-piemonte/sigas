/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class UtilityCtrlVO implements VO {

	private static final long serialVersionUID = 1L;

    private Integer parameterInt;
    
    private String message;

    private String levelMessagge;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public Integer getParameterInt() {
		return parameterInt;
	}

	public void setParameterInt(Integer parameterInt) {
		this.parameterInt = parameterInt;
	}

	public String getLevelMessagge() {
		return levelMessagge;
	}

	public void setLevelMessagge(String levelMessagge) {
		this.levelMessagge = levelMessagge;
	}

    
    
    
}
