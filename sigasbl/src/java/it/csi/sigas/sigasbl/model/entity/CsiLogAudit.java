/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the csi_log_audit database table.
 * 
 */
@Entity
@Table(name="csi_log_audit")
@NamedQuery(name="CsiLogAudit.findAll", query="SELECT c FROM CsiLogAudit c")
public class CsiLogAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsiLogAuditPK id;

	@Column(name="id_address")
	private String idAddress;

	@Column(name="id_app")
	private String idApp;

	@Column(name="ogg_oper")
	private String oggOper;

	private String operazione;

	public CsiLogAudit() {
	}

	public CsiLogAuditPK getId() {
		return this.id;
	}

	public void setId(CsiLogAuditPK id) {
		this.id = id;
	}

	public String getIdAddress() {
		return this.idAddress;
	}

	public void setIdAddress(String idAddress) {
		this.idAddress = idAddress;
	}

	public String getIdApp() {
		return this.idApp;
	}

	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}

	public String getOggOper() {
		return this.oggOper;
	}

	public void setOggOper(String oggOper) {
		this.oggOper = oggOper;
	}

	public String getOperazione() {
		return this.operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

}
