/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.accreditamento;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.LegaleRappresentanteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.OperatoreVO;

public class ConfermaPraticaAccreditamentoRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "dichiarante non deve essere vuoto")
	private DichiaranteVO dichiarante;
	@NotNull(message = "operatore non deve essere vuoto")
	private OperatoreVO operatore;
	@NotNull(message = "legaleRappresentante non deve essere vuoto")
	private LegaleRappresentanteVO legaleRappresentante;
	
	private Long idUtenteProvv;
	
	private String stato;
	
	private String note;

	
	public DichiaranteVO getDichiarante() {
		return dichiarante;
	}

	public void setDichiarante(DichiaranteVO dichiarante) {
		this.dichiarante = dichiarante;
	}

	public OperatoreVO getOperatore() {
		return operatore;
	}

	public void setOperatore(OperatoreVO operatore) {
		this.operatore = operatore;
	}

	public LegaleRappresentanteVO getLegaleRappresentante() {
		return legaleRappresentante;
	}

	public void setLegaleRappresentante(LegaleRappresentanteVO legaleRappresentante) {
		this.legaleRappresentante = legaleRappresentante;
	}

	public Long getIdUtenteProvv() {
		return idUtenteProvv;
	}

	public void setIdUtenteProvv(Long idUtenteProvv) {
		this.idUtenteProvv = idUtenteProvv;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
}
