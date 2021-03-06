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
@Table(name="sigas_tipo_consumo")
@NamedQuery(name="SigasTipoConsumo.findAll", query="SELECT i FROM SigasTipoConsumo i")
public class SigasTipoConsumo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_TIPO_CONSUMO_IDTIPOCONSUMO_GENERATOR" , sequenceName="SEQ_TIPO_CONSUMO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_TIPO_CONSUMO_IDTIPOCONSUMO_GENERATOR")
	@Column(name="id_tipo_consumo", unique=true, nullable=false)
	private long idTipoConsumo;

	@Column(name="campo_dich_consumo", nullable=false)
	private String campoDichConsumo;

	@Column(name="descrizione")
	private String descrizione;

	public SigasTipoConsumo() {
		
	}
	
	public long getIdTipoConsumo() {
		return idTipoConsumo;
	}

	public void setIdTipoConsumo(long idTipoConsumo) {
		this.idTipoConsumo = idTipoConsumo;
	}

	public String getCampoDichConsumo() {
		return campoDichConsumo;
	}

	public void setCampoDichConsumo(String campoDichConsumo) {
		this.campoDichConsumo = campoDichConsumo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
