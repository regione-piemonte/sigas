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
@Table(name="sigas_tipo_versamento")
@NamedQuery(name="SigasTipoVersamento.findAll", query="SELECT i FROM SigasTipoVersamento i")
public class SigasTipoVersamento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_TIPO_VERSAMENTO_IDTIPOVERSAMENTO_GENERATOR" , sequenceName="SEQ_TIPO_VERSAMENTO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_TIPO_VERSAMENTO_IDTIPOVERSAMENTO_GENERATOR")
	@Column(name="id_tipo_versamento", unique=true, nullable=false)
	private long idTipoVersamento;

	@Column(name="denominazione")
	private String denominazione;
	
	@Column(name="descrizione")
	private String descrizione;
	
	public SigasTipoVersamento() {
	}
	
	public long getIdTipoVersamento() {
		return idTipoVersamento;
	}

	public void setIdTipoVersamento(long idTipoVersamento) {
		this.idTipoVersamento = idTipoVersamento;
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
