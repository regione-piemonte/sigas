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
@Table(name="sigas_tipo_tassi")
@NamedQuery(name="SigasTipoTassi.findAll", query="SELECT i FROM SigasTipoTassi i")
public class SigasTipoTassi implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_TIPO_TASSI_IDTIPOTASSO_GENERATOR" , sequenceName="SEQ_TIPO_TASSI", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_TIPO_TASSI_IDTIPOTASSO_GENERATOR")
	@Column(name="id_tipo_tasso", unique=true, nullable=false)
	private long idTipoTasso;

	@Column(name="nome_tasso")
	private String nomeTasso;

	@Column(name="descrizione")
	private String descrizione;

	
	public SigasTipoTassi() {
	}

	public long getIdTipoTasso() {
		return idTipoTasso;
	}

	public void setIdTipoTasso(long idTipoTasso) {
		this.idTipoTasso = idTipoTasso;
	}

	public String getNomeTasso() {
		return nomeTasso;
	}

	public void setNomeTasso(String nomeTasso) {
		this.nomeTasso = nomeTasso;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	
}
