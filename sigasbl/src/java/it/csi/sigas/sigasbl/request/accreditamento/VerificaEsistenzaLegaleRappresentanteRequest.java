/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.accreditamento;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;


/**
 * @author riccardo.bova
 * @date 12 giu 2018
 */
public class VerificaEsistenzaLegaleRappresentanteRequest  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Size(message = "codiceFiscale deve essere lungo 16", min = 0, max = 16)
	String cflegaleRappr;
	@NotNull(message = "Dichiarante non deve essere vuoto")
	DichiaranteVO dichiarante;

	public String getCflegaleRappr() {
		return cflegaleRappr;
	}

	public void setCflegaleRappr(String cflegaleRappr) {
		this.cflegaleRappr = cflegaleRappr;
	}

	public DichiaranteVO getDichiarante() {
		return dichiarante;
	}

	public void setDichiarante(DichiaranteVO dichiarante) {
		this.dichiarante = dichiarante;
	}

}
