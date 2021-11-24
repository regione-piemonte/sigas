/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sigas_stato_documento database table.
 * 
 */
@Entity
@Table(name="sigas_stato_documento")
@NamedQuery(name="SigasStatoDocumento.findAll", query="SELECT s FROM SigasStatoDocumento s")
public class SigasStatoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_stato_documento")
	private Integer idStatoDocumento;

	@Column(name="codice_stato")
	private String codiceStato;

	private String descrizione;

	//bi-directional many-to-one association to SigasDocumenti
	@OneToMany(mappedBy="sigasStatoDocumento")
	private List<SigasDocumenti> sigasDocumentis;

	public SigasStatoDocumento() {
	}

	public Integer getIdStatoDocumento() {
		return this.idStatoDocumento;
	}

	public void setIdStatoDocumento(Integer idStatoDocumento) {
		this.idStatoDocumento = idStatoDocumento;
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

	public List<SigasDocumenti> getSigasDocumentis() {
		return this.sigasDocumentis;
	}

	public void setSigasDocumentis(List<SigasDocumenti> sigasDocumentis) {
		this.sigasDocumentis = sigasDocumentis;
	}

	public SigasDocumenti addSigasDocumenti(SigasDocumenti sigasDocumenti) {
		getSigasDocumentis().add(sigasDocumenti);
		sigasDocumenti.setSigasStatoDocumento(this);

		return sigasDocumenti;
	}

	public SigasDocumenti removeSigasDocumenti(SigasDocumenti sigasDocumenti) {
		getSigasDocumentis().remove(sigasDocumenti);
		sigasDocumenti.setSigasStatoDocumento(null);

		return sigasDocumenti;
	}

}