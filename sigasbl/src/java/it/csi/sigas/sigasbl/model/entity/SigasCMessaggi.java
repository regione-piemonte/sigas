/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sigas_c_messaggi database table.
 * 
 */
@Entity
@Table(name="sigas_c_messaggi")
@NamedQuery(name="SigasCMessaggi.findAll", query="SELECT s FROM SigasCMessaggi s")
public class SigasCMessaggi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_messaggio")
	private long idMessaggio;

	@Column(name="desc_chiave_messaggio")
	private String descChiaveMessaggio;

	@Column(name="livello_messaggio")
	private String livelloMessaggio;

	@Column(name="valore_messaggio")
	private String valoreMessaggio;

	public SigasCMessaggi() {
	}

	public long getIdMessaggio() {
		return this.idMessaggio;
	}

	public void setIdMessaggio(long idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public String getDescChiaveMessaggio() {
		return this.descChiaveMessaggio;
	}

	public void setDescChiaveMessaggio(String descChiaveMessaggio) {
		this.descChiaveMessaggio = descChiaveMessaggio;
	}

	public String getLivelloMessaggio() {
		return this.livelloMessaggio;
	}

	public void setLivelloMessaggio(String livelloMessaggio) {
		this.livelloMessaggio = livelloMessaggio;
	}

	public String getValoreMessaggio() {
		return this.valoreMessaggio;
	}

	public void setValoreMessaggio(String valoreMessaggio) {
		this.valoreMessaggio = valoreMessaggio;
	}

}