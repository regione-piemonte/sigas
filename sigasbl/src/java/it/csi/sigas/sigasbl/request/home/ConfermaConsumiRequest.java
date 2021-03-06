/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.model.vo.home.ConsumiPrVO;
import it.csi.sigas.sigasbl.model.vo.home.ScartoVO;

public class ConfermaConsumiRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "consumi non deve essere vuoto")
	private ConsumiPrVO consumi;
	
	@NotNull(message = "scarti non deve essere vuoto")
	private List<ScartoVO> scarti;

	public ConsumiPrVO getConsumi() {
		return consumi;
	}
	
	public void setConsumi(ConsumiPrVO consumi) {
		this.consumi = consumi;
	}

	public List<ScartoVO> getScarti() {
		return scarti;
	}
	
	public void setScarti(List<ScartoVO> scarti) {
		this.scarti = scarti;
	}
}
