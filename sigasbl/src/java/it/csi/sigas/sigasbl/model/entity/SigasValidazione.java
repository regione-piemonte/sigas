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
@Table(name="sigas_validazione")
@NamedQuery(name="SigasValidazione.findAll", query="SELECT i FROM SigasValidazione i")
public class SigasValidazione implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_VALIDAZIONE_IDVALID_GENERATOR" , sequenceName="SEQ_VALIDAZIONE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_VALIDAZIONE_IDVALID_GENERATOR")
	@Column(name="id_valid", unique=true, nullable=false)
	private long idValid;

	@Column(name="codice_azienda")
	private String codiceAzienda;

	@Column(name="anno")
	private String anno;
	
	@Column(name="stato")
	private String stato;
	
	public SigasValidazione() {
	}

	public long getIdValid() {
		return idValid;
	}

	public void setIdValid(long idValid) {
		this.idValid = idValid;
	}

	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
}
