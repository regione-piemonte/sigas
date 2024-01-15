/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;


import java.sql.Timestamp;


/**
 * The persistent class for the sigas_allegato database table.
 * 
 */
@Entity
@Table(name="sigas_allegato")
@NamedQuery(name="SigasAllegato.findAll", query="SELECT s FROM SigasAllegato s")
public class SigasAllegato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_ALLEGATO_IDALLEGATO_GENERATOR" , sequenceName="SEQ_ALLEGATO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_ALLEGATO_IDALLEGATO_GENERATOR")
	@Column(name="id_allegato", unique=true, nullable=false, precision=8)
	private Integer idAllegato;

	@Column(name="data_ora_protocollo")
	private Timestamp dataOraProtocollo;

	private String descrizione;

	@Column(name="id_index")
	private String idIndex;

	@Column(name="ins_date")
	private Timestamp insDate;

	@Column(name="ins_user")
	private String insUser;

	@Column(name="nome_file")
	private String nomeFile;

	@Column(name="numero_protocollo")
	private String numeroProtocollo;

	//bi-directional many-to-one association to SigasDocumenti
	@ManyToOne
	@JoinColumn(name="id_allegato_documento")
	private SigasDocumenti sigasDocumenti;

	//bi-directional many-to-one association to SigasStatoArchiviazione
	@ManyToOne
	@JoinColumn(name="id_stato_archiviazione")
	private SigasStatoArchiviazione sigasStatoArchiviazione;

	public SigasAllegato() {
	}

	public Integer getIdAllegato() {
		return this.idAllegato;
	}

	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}

	public Timestamp getDataOraProtocollo() {
		return this.dataOraProtocollo;
	}

	public void setDataOraProtocollo(Timestamp dataOraProtocollo) {
		this.dataOraProtocollo = dataOraProtocollo;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIdIndex() {
		return this.idIndex;
	}

	public void setIdIndex(String idIndex) {
		this.idIndex = idIndex;
	}
	
	
	public Timestamp getInsDate() {
		return this.insDate;
	}

	public void setInsDate(Timestamp insDate) {
		this.insDate = insDate;
	}

	public String getInsUser() {
		return this.insUser;
	}

	public void setInsUser(String insUser) {
		this.insUser = insUser;
	}	

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}	

	public SigasDocumenti getSigasDocumenti() {
		return sigasDocumenti;
	}

	public void setSigasDocumenti(SigasDocumenti sigasDocumenti) {
		this.sigasDocumenti = sigasDocumenti;
	}

	public SigasStatoArchiviazione getSigasStatoArchiviazione() {
		return this.sigasStatoArchiviazione;
	}

	public void setSigasStatoArchiviazione(SigasStatoArchiviazione sigasStatoArchiviazione) {
		this.sigasStatoArchiviazione = sigasStatoArchiviazione;
	}

}