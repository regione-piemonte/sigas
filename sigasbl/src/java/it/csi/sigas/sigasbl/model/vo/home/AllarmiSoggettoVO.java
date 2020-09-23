/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import java.util.Date;

import it.csi.sigas.sigasbl.common.rest.VO;

public class AllarmiSoggettoVO implements VO {

	private static final long serialVersionUID = 1L;
	
	private int idAllarme;
	private long idConsumi;
	private long idComunicazioni;
	private long idTipoAllarme;
	private String codiceAzienda;
	private String nota;
	private Date attivazione;
	private int status;
	
	public int getIdAllarme() {
		return idAllarme;
	}
	public void setIdAllarme(int idAllarme) {
		this.idAllarme = idAllarme;
	}
	public long getIdConsumi() {
		return idConsumi;
	}
	public void setIdConsumi(long idConsumi) {
		this.idConsumi = idConsumi;
	}
	public long getIdComunicazioni() {
		return idComunicazioni;
	}
	public void setIdComunicazioni(long idComunicazioni) {
		this.idComunicazioni = idComunicazioni;
	}
	public long getIdTipoAllarme() {
		return idTipoAllarme;
	}
	public void setIdTipoAllarme(long idTipoAllarme) {
		this.idTipoAllarme = idTipoAllarme;
	}
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public Date getAttivazione() {
		return attivazione;
	}
	public void setAttivazione(Date attivazione) {
		this.attivazione = attivazione;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
