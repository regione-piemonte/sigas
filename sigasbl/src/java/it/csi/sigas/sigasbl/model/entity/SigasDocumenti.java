/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the sigas_documenti database table.
 * 
 */
@Entity
@Table(name="sigas_documenti")
@NamedQuery(name="SigasDocumenti.findAll", query="SELECT s FROM SigasDocumenti s")
public class SigasDocumenti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_DOCUMENTO_IDDOCUMENTO_GENERATOR" , sequenceName="SEQ_ALLEGATO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_DOCUMENTO_IDDOCUMENTO_GENERATOR")
	@Column(name="id_documento", unique=true, nullable=false, precision=8)
	private Integer idDocumento;

	private String annualita;

	@Column(name="cf_piva")
	private String cfPiva;

	@Column(name="data_protocollazione")
	private Timestamp dataProtocollazione;

	@Column(name="id_index")
	private String idIndex;

	@Column(name="ins_date")
	private Timestamp insDate;

	@Column(name="ins_user")
	private String insUser;

	@Column(name="mod_date")
	private Timestamp modDate;

	@Column(name="mod_user")
	private String modUser;

	@Column(name="n_protocollo")
	private String nProtocollo;

	@Column(name="nome_file")
	private String nomeFile;

	private String note;

	@Column(name="note_bo")
	private String noteBo;

	@Column(name="rif_archivio")
	private String rifArchivio;
	
	@Column(name="n_protocollo_accertamento")
	private String nProtocolloAccertamento;
	
	@Column(name="anno_protocollo_accertamento")
	private String annoProtocolloAccertamento;

	//bi-directional many-to-one association to SigasAllegato
	@OneToMany(fetch = FetchType.EAGER, mappedBy="sigasDocumenti", cascade = CascadeType.ALL)
	private List<SigasAllegato> sigasAllegatos;

	//bi-directional many-to-one association to SigasAnagraficaSoggetti
	@ManyToOne
	@JoinColumn(name="id_anag")
	private SigasAnagraficaSoggetti sigasAnagraficaSoggetti;

	//bi-directional many-to-one association to SigasStatoArchiviazione
	@ManyToOne
	@JoinColumn(name="id_stato_archiviazione")
	private SigasStatoArchiviazione sigasStatoArchiviazione;

	//bi-directional many-to-one association to SigasStatoDocumento
	@ManyToOne
	@JoinColumn(name="id_stato")
	private SigasStatoDocumento sigasStatoDocumento;

	//bi-directional many-to-one association to SigasTipoDocumento
	@ManyToOne
	@JoinColumn(name="id_tipo_documento")
	private SigasTipoDocumento sigasTipoDocumento;
	
	//bi-directional many-to-one association
	@OneToMany(mappedBy="sigasDocumento")
	private List<SigasDepositoCausionale> sigasDepositoCausionales;

	public SigasDocumenti() {
	}

	public Integer getIdDocumento() {
		return this.idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getAnnualita() {
		return this.annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public String getCfPiva() {
		return this.cfPiva;
	}

	public void setCfPiva(String cfPiva) {
		this.cfPiva = cfPiva;
	}

	public Timestamp getDataProtocollazione() {
		return this.dataProtocollazione;
	}

	public void setDataProtocollazione(Timestamp dataProtocollazione) {
		this.dataProtocollazione = dataProtocollazione;
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

	public Timestamp getModDate() {
		return this.modDate;
	}

	public void setModDate(Timestamp modDate) {
		this.modDate = modDate;
	}

	public String getModUser() {
		return this.modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public String getNProtocollo() {
		return this.nProtocollo;
	}

	public void setNProtocollo(String nProtocollo) {
		this.nProtocollo = nProtocollo;
	}

	public String getNomeFile() {
		return this.nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNoteBo() {
		return this.noteBo;
	}

	public void setNoteBo(String noteBo) {
		this.noteBo = noteBo;
	}

	public String getRifArchivio() {
		return this.rifArchivio;
	}

	public void setRifArchivio(String rifArchivio) {
		this.rifArchivio = rifArchivio;
	}

	public List<SigasAllegato> getSigasAllegatos() {
		return this.sigasAllegatos;
	}

	public void setSigasAllegatos(List<SigasAllegato> sigasAllegatos) {
		this.sigasAllegatos = sigasAllegatos;
	}

	public SigasAllegato addSigasAllegato(SigasAllegato sigasAllegato) {
		getSigasAllegatos().add(sigasAllegato);
		sigasAllegato.setSigasDocumenti(this);

		return sigasAllegato;
	}

	public SigasAllegato removeSigasAllegato(SigasAllegato sigasAllegato) {
		getSigasAllegatos().remove(sigasAllegato);
		sigasAllegato.setSigasDocumenti(null);

		return sigasAllegato;
	}

	public SigasAnagraficaSoggetti getSigasAnagraficaSoggetti() {
		return this.sigasAnagraficaSoggetti;
	}

	public void setSigasAnagraficaSoggetti(SigasAnagraficaSoggetti sigasAnagraficaSoggetti) {
		this.sigasAnagraficaSoggetti = sigasAnagraficaSoggetti;
	}

	public SigasStatoArchiviazione getSigasStatoArchiviazione() {
		return this.sigasStatoArchiviazione;
	}

	public void setSigasStatoArchiviazione(SigasStatoArchiviazione sigasStatoArchiviazione) {
		this.sigasStatoArchiviazione = sigasStatoArchiviazione;
	}

	public SigasStatoDocumento getSigasStatoDocumento() {
		return this.sigasStatoDocumento;
	}

	public void setSigasStatoDocumento(SigasStatoDocumento sigasStatoDocumento) {
		this.sigasStatoDocumento = sigasStatoDocumento;
	}

	public SigasTipoDocumento getSigasTipoDocumento() {
		return this.sigasTipoDocumento;
	}

	public void setSigasTipoDocumento(SigasTipoDocumento sigasTipoDocumento) {
		this.sigasTipoDocumento = sigasTipoDocumento;
	}

	

	public String getAnnoProtocolloAccertamento() {
		return annoProtocolloAccertamento;
	}

	public void setAnnoProtocolloAccertamento(String annoProtocolloAccertamento) {
		this.annoProtocolloAccertamento = annoProtocolloAccertamento;
	}

	public String getnProtocolloAccertamento() {
		return nProtocolloAccertamento;
	}

	public void setnProtocolloAccertamento(String nProtocolloAccertamento) {
		this.nProtocolloAccertamento = nProtocolloAccertamento;
	}

	public List<SigasDepositoCausionale> getSigasDepositoCausionales() {
		return sigasDepositoCausionales;
	}

	public void setSigasDepositoCausionales(List<SigasDepositoCausionale> sigasDepositoCausionales) {
		this.sigasDepositoCausionales = sigasDepositoCausionales;
	}	

}