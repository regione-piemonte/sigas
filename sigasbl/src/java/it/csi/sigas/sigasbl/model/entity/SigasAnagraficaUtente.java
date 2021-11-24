/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the sigas_anagrafica_utente database table.
 * 
 */
@Entity
@Table(name="sigas_anagrafica_utente")
@NamedQuery(name="SigasAnagraficaUtente.findAll", query="SELECT s FROM SigasAnagraficaUtente s")
public class SigasAnagraficaUtente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_ANAGRAFICA_UTENTE_IDANAGUTENTE_GENERATOR" , sequenceName="SEQ_ANAGRAFICA_UTENTE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_ANAGRAFICA_UTENTE_IDANAGUTENTE_GENERATOR")
	@Column(name="id_anag_utente")
	private long idAnagUtente;

	@Column(name="id_anag")
	private Integer idAnag;

	@Column(name="id_utente_provv")
	private BigDecimal idUtenteProvv;

	public SigasAnagraficaUtente() {
	}

	public long getIdAnagUtente() {
		return this.idAnagUtente;
	}

	public void setIdAnagUtente(long idAnagUtente) {
		this.idAnagUtente = idAnagUtente;
	}

	public Integer getIdAnag() {
		return this.idAnag;
	}

	public void setIdAnag(Integer idAnag) {
		this.idAnag = idAnag;
	}

	public BigDecimal getIdUtenteProvv() {
		return this.idUtenteProvv;
	}

	public void setIdUtenteProvv(BigDecimal idUtenteProvv) {
		this.idUtenteProvv = idUtenteProvv;
	}

}