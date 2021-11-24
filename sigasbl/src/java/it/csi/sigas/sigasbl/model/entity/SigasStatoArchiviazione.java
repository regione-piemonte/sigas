/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sigas_stato_archiviazione database table.
 * 
 */
@Entity
@Table(name="sigas_stato_archiviazione")
@NamedQuery(name="SigasStatoArchiviazione.findAll", query="SELECT s FROM SigasStatoArchiviazione s")
public class SigasStatoArchiviazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_stato_archiviazione")
	private Integer idStatoArchiviazione;

	@Column(name="codice_stato")
	private String codiceStato;

	private String descrizione;

	//bi-directional many-to-one association to SigasAllegato
	@OneToMany(mappedBy="sigasStatoArchiviazione")
	private List<SigasAllegato> sigasAllegatos;

	//bi-directional many-to-one association to SigasDocumenti
	@OneToMany(mappedBy="sigasStatoArchiviazione")
	private List<SigasDocumenti> sigasDocumentis;

	public SigasStatoArchiviazione() {
	}

	public Integer getIdStatoArchiviazione() {
		return this.idStatoArchiviazione;
	}

	public void setIdStatoArchiviazione(Integer idStatoArchiviazione) {
		this.idStatoArchiviazione = idStatoArchiviazione;
	}

	public String getCodiceStato() {
		return this.codiceStato;
	}

	public void setCodiceStato(String codiceStato) {
		this.codiceStato = codiceStato;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<SigasAllegato> getSigasAllegatos() {
		return this.sigasAllegatos;
	}

	public void setSigasAllegatos(List<SigasAllegato> sigasAllegatos) {
		this.sigasAllegatos = sigasAllegatos;
	}

	public SigasAllegato addSigasAllegato(SigasAllegato sigasAllegato) {
		getSigasAllegatos().add(sigasAllegato);
		sigasAllegato.setSigasStatoArchiviazione(this);

		return sigasAllegato;
	}

	public SigasAllegato removeSigasAllegato(SigasAllegato sigasAllegato) {
		getSigasAllegatos().remove(sigasAllegato);
		sigasAllegato.setSigasStatoArchiviazione(null);

		return sigasAllegato;
	}

	public List<SigasDocumenti> getSigasDocumentis() {
		return this.sigasDocumentis;
	}

	public void setSigasDocumentis(List<SigasDocumenti> sigasDocumentis) {
		this.sigasDocumentis = sigasDocumentis;
	}

	public SigasDocumenti addSigasDocumenti(SigasDocumenti sigasDocumenti) {
		getSigasDocumentis().add(sigasDocumenti);
		sigasDocumenti.setSigasStatoArchiviazione(this);

		return sigasDocumenti;
	}

	public SigasDocumenti removeSigasDocumenti(SigasDocumenti sigasDocumenti) {
		getSigasDocumentis().remove(sigasDocumenti);
		sigasDocumenti.setSigasStatoArchiviazione(null);

		return sigasDocumenti;
	}

}