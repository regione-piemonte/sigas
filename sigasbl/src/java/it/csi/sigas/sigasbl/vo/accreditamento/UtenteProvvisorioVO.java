/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.vo.accreditamento;

import java.sql.Timestamp;

public class UtenteProvvisorioVO {
	
	private long idUtenteProvv;


	private LegaleRappresentanteVO legaleRappresentanteVO;

	private OperatoreVO operatoreVO;

	private DichiaranteVO dichiaranteVO;

	private String note;

	private String idPratica;

	private String stato;
	
	
	private Timestamp insDateUP;

	private String insUserUP;

	private Timestamp modDateUP;

	private String modUserUP;


	public long getIdUtenteProvv() {
		return idUtenteProvv;
	}

	public void setIdUtenteProvv(long idUtenteProvv) {
		this.idUtenteProvv = idUtenteProvv;
	}

	

	
	public LegaleRappresentanteVO getLegaleRappresentanteVO() {
		return legaleRappresentanteVO;
	}

	public void setLegaleRappresentanteVO(LegaleRappresentanteVO legaleRappresentanteVO) {
		this.legaleRappresentanteVO = legaleRappresentanteVO;
	}

	public OperatoreVO getOperatoreVO() {
		return operatoreVO;
	}

	public void setOperatoreVO(OperatoreVO operatoreVO) {
		this.operatoreVO = operatoreVO;
	}

	public DichiaranteVO getDichiaranteVO() {
		return dichiaranteVO;
	}

	public void setDichiaranteVO(DichiaranteVO dichiaranteVO) {
		this.dichiaranteVO = dichiaranteVO;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	

	public String getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(String idPratica) {
		this.idPratica = idPratica;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Timestamp getInsDate() {
		return insDateUP;
	}

	public void setInsDate(Timestamp insDate) {
		this.insDateUP = insDate;
	}

	public String getInsUser() {
		return insUserUP;
	}

	public void setInsUser(String insUser) {
		this.insUserUP = insUser;
	}

	public Timestamp getModDate() {
		return modDateUP;
	}

	public void setModDate(Timestamp modDate) {
		this.modDateUP = modDate;
	}

	public String getModUser() {
		return modUserUP;
	}

	public void setModUser(String modUser) {
		this.modUserUP = modUser;
	}
	

}
