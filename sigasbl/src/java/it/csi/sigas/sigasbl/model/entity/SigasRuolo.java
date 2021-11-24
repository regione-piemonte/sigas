/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sigas_ruolo database table.
 * 
 */
@Entity
@Table(name="sigas_ruolo")
@NamedQuery(name="SigasRuolo.findAll", query="SELECT s FROM SigasRuolo s")
public class SigasRuolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_ruolo")
	private long idRuolo;

	private String descrizione;

	//bi-directional many-to-one association to SigasUtenti
	@OneToMany(mappedBy="sigasRuolo")
	private List<SigasUtenti> sigasUtentis;

	public SigasRuolo() {
	}

	public long getIdRuolo() {
		return this.idRuolo;
	}

	public void setIdRuolo(long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<SigasUtenti> getSigasUtentis() {
		return this.sigasUtentis;
	}

	public void setSigasUtentis(List<SigasUtenti> sigasUtentis) {
		this.sigasUtentis = sigasUtentis;
	}

	public SigasUtenti addSigasUtenti(SigasUtenti sigasUtenti) {
		getSigasUtentis().add(sigasUtenti);
		sigasUtenti.setSigasRuolo(this);

		return sigasUtenti;
	}

	public SigasUtenti removeSigasUtenti(SigasUtenti sigasUtenti) {
		getSigasUtentis().remove(sigasUtenti);
		sigasUtenti.setSigasRuolo(null);

		return sigasUtenti;
	}

}