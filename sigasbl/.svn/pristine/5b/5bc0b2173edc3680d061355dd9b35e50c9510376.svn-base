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
@Table(name="sigas_tipo_allarmi")
@NamedQuery(name="SigasTipoAllarmi.findAll", query="SELECT i FROM SigasTipoAllarmi i")
public class SigasTipoAllarmi implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_TIPO_ALLARME_IDTIPOALLARME_GENERATOR" , sequenceName="SEQ_TIPO_ALLARME", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_TIPO_ALLARME_IDTIPOALLARME_GENERATOR")
	@Column(name="id_tipo_allarme", unique=true, nullable=false)
	private Integer idTipoAllarme;

	@Column(name="denominazione")
	private String denominazione;
	
	@Column(name="descrizione")
	private String descrizione;
	
	public SigasTipoAllarmi() {
		
	}

	public Integer getIdTipoAllarme() {
		return idTipoAllarme;
	}

	public void setIdTipoAllarme(Integer idTipoAllarme) {
		this.idTipoAllarme = idTipoAllarme;
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
