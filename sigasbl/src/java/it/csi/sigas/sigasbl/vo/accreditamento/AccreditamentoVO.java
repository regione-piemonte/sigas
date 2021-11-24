/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.vo.accreditamento;

public class AccreditamentoVO {
	
	private UtenteProvvisorioVO utenteProvvisorio;
	
	private DichiaranteVO dichiarante;
	
	private LegaleRappresentanteVO legaleRappresentante;
	
	private OperatoreVO operatore;

	public UtenteProvvisorioVO getUtenteProvvisorio() {
		return utenteProvvisorio;
	}

	public void setUtenteProvvisorio(UtenteProvvisorioVO utenteProvvisorio) {
		this.utenteProvvisorio = utenteProvvisorio;
	}

	public DichiaranteVO getDichiarante() {
		return dichiarante;
	}

	public void setDichiarante(DichiaranteVO dichiarante) {
		this.dichiarante = dichiarante;
	}

	public LegaleRappresentanteVO getLegaleRappresentante() {
		return legaleRappresentante;
	}

	public void setLegaleRappresentante(LegaleRappresentanteVO legaleRappresentante) {
		this.legaleRappresentante = legaleRappresentante;
	}

	public OperatoreVO getOperatore() {
		return operatore;
	}

	public void setOperatore(OperatoreVO operatore) {
		this.operatore = operatore;
	}

	

}
