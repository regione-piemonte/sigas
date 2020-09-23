/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The primary key class for the csi_log_audit database table.
 * 
 */
@Embeddable
public class CsiLogAuditPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="key_oper")
	private String keyOper;

	private String utente;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ora")
	private Date dataOra;

	public CsiLogAuditPK() {
	}
	public String getKeyOper() {
		return this.keyOper;
	}
	public void setKeyOper(String keyOper) {
		this.keyOper = keyOper;
	}
	public String getUtente() {
		return this.utente;
	}
	public void setUtente(String utente) {
		this.utente = utente;
	}
	public Date getDataOra() {
		return this.dataOra;
	}
	public void setDataOra(Date dataOra) {
		this.dataOra = dataOra;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsiLogAuditPK)) {
			return false;
		}
		CsiLogAuditPK castOther = (CsiLogAuditPK)other;
		return 
			this.keyOper.equals(castOther.keyOper)
			&& this.utente.equals(castOther.utente)
			&& this.dataOra.equals(castOther.dataOra);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.keyOper.hashCode();
		hash = hash * prime + this.utente.hashCode();
		hash = hash * prime + this.dataOra.hashCode();
		
		return hash;
	}
}
