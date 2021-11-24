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
@Table(name="sigas_tipo_pagamento")
@NamedQuery(name="SigasPaymentTypes.findAll", query="SELECT i FROM SigasPaymentTypes i")
public class SigasPaymentTypes implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_pagamento", unique=true, nullable=false)
	private long idTipoPagamento;

	@Column(name="cod_tipo_pagamento")
	private String codTipoPagamento;
	
	@Column(name="desc_tipo_pagamento")
	private String descTipoPagamento;
	
	public long getIdTipoPagamento() {
		return idTipoPagamento;
	}

	public void setIdTipoPagamento(long idTipoPagamento) {
		this.idTipoPagamento = idTipoPagamento;
	}

	public String getCodTipoPagamento() {
		return codTipoPagamento;
	}

	public void setCodTipoPagamento(String codTipoPagamento) {
		this.codTipoPagamento = codTipoPagamento;
	}

	public String getDescTipoPagamento() {
		return descTipoPagamento;
	}

	public void setDescTipoPagamento(String descTipoPagamento) {
		this.descTipoPagamento = descTipoPagamento;
	}

	public SigasPaymentTypes() {
	}



}
