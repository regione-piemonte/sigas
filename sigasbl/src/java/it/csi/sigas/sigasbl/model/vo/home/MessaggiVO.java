/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class MessaggiVO implements VO {

	private static final long serialVersionUID = -6556958673902732933L;
	
	private long idMessaggio;

	private String descChiaveMessaggio;

	private String livelloMessaggio;

	private String valoreMessaggio;

	public long getIdMessaggio() {
		return idMessaggio;
	}

	public void setIdMessaggio(long idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public String getDescChiaveMessaggio() {
		return descChiaveMessaggio;
	}

	public void setDescChiaveMessaggio(String descChiaveMessaggio) {
		this.descChiaveMessaggio = descChiaveMessaggio;
	}

	public String getLivelloMessaggio() {
		return livelloMessaggio;
	}

	public void setLivelloMessaggio(String livelloMessaggio) {
		this.livelloMessaggio = livelloMessaggio;
	}

	public String getValoreMessaggio() {
		return valoreMessaggio;
	}

	public void setValoreMessaggio(String valoreMessaggio) {
		this.valoreMessaggio = valoreMessaggio;
	}
	
	
	
}
