/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sigas_tipo_carrello")
@NamedQuery(name="SigasTipoCarrello.findAll", query="SELECT i FROM SigasTipoCarrello i")
public class SigasTipoCarrello implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_carrello", unique=true, nullable=false)
	private long idTipoCarrello;

	@Column(name="denominazione")
	private String denominazione;
	
	@Column(name="descrizione")
	private String descrizione;
	
	public SigasTipoCarrello() {
	}
	
	public long getIdTipoCarrello() {
		return idTipoCarrello;
	}

	public void setIdTipoCarrello(long idTipoCarrello) {
		this.idTipoCarrello = idTipoCarrello;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
