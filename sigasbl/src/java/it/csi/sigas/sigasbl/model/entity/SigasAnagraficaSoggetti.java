/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="sigas_anagrafica_soggetti")
@NamedQuery(name="SigasAnagraficaSoggetti.findAll", query="SELECT i FROM SigasAnagraficaSoggetti i")
public class SigasAnagraficaSoggetti extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_ANAGRAFICA_SOGGETTI_IDANAG_GENERATOR" , sequenceName="SEQ_ANAGRAFICA_SOGGETTI", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_ANAGRAFICA_SOGGETTI_IDANAG_GENERATOR")
	@Column(name="id_anag", unique=true, nullable=false)
	private long idAnag;

	@Column(name="id_fusione", unique=true, nullable=false)
	private long idFusione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_fusione")
	private Date dataFusione;
	
	@Column(name="codice_azienda")
	private String codiceAzienda;

	@Column(name="denominazione")
	private String denominazione;
	
	@Column(name="indirizzo", nullable=false)
	private String indirizzo;
	
	@Column(name="iban", nullable=false)
	private String iban;
	
	@Column(name="tipo", nullable=false)
	private String tipo;
	
	@Column(name="status", nullable=false)
	private int status;
	
	@Column(name="telefono", nullable=false)
	private String telefono;
	
	@Column(name="riferimento", nullable=false)
	private String riferimento;
	
	@Column(name="fideussione", nullable=false)
	private boolean fideussione;
	
	@Column(name="importo_fideussione")
	private Double importoFideussione;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="pec", nullable=false)
	private String pec;
	
	@Column(name="note", nullable=false)
	private String note;	
		
	//bi-directional many-to-one association to IrbaDProvincia
	@ManyToOne
	@JoinColumn(name="fk_provincia", nullable=false)
	private SigasProvincia sigasProvincia;
	
	@ManyToOne
	@JoinColumn(name="fk_comune", nullable=false)
	private SigasComune sigasComune;	
	
	@Column(name="cf_piva", nullable=false)
	private String cfPiva;
	
	@ManyToOne
	@JoinColumn(name = "id_import", nullable = true)
	private SigasImportUTF sigasImport;
	
	@Column(name="selected_import", nullable=true)
	private Boolean selectedImport;
	
	//bi-directional many-to-one association
	@OneToMany(mappedBy="sigasAnagraficaSoggetti")
	private List<SigasDepositoCausionale> sigasDepositoCausionales;

	public SigasAnagraficaSoggetti() {
	}

	public long getIdAnag() {
		return idAnag;
	}

	public void setIdAnag(long idAnag) {
		this.idAnag = idAnag;
	}

	public long getIdFusione() {
		return idFusione;
	}

	public void setIdFusione(long idFusione) {
		this.idFusione = idFusione;
	}

	public Date getDataFusione() {
		return dataFusione;
	}

	public void setDataFusione(Date dataFusione) {
		this.dataFusione = dataFusione;
	}

	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	
	public boolean isFideussione() {
		return fideussione;
	}

	public void setFideussione(boolean fideussione) {
		this.fideussione = fideussione;
	}

	public Double getImportoFideussione() {
		return importoFideussione;
	}

	public void setImportoFideussione(Double importoFideussione) {
		this.importoFideussione = importoFideussione;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}	

	public SigasProvincia getSigasProvincia() {
		return sigasProvincia;
	}

	public void setSigasProvincia(SigasProvincia sigasProvincia) {
		this.sigasProvincia = sigasProvincia;
	}

	public SigasComune getSigasComune() {
		return sigasComune;
	}

	public void setSigasComune(SigasComune sigasComune) {
		this.sigasComune = sigasComune;
	}	

	public String getCfPiva() {
		return cfPiva;
	}

	public void setCfPiva(String cfPiva) {
		this.cfPiva = cfPiva;
	}

	public SigasImportUTF getSigasImport() {
		return sigasImport;
	}

	public Boolean getSelectedImport() {
		return selectedImport;
	}

	public void setSigasImport(SigasImportUTF sigasImport) {
		this.sigasImport = sigasImport;
	}

	public void setSelectedImport(Boolean selectedImport) {
		this.selectedImport = selectedImport;
	}

	public List<SigasDepositoCausionale> getSigasDepositoCausionales() {
		return sigasDepositoCausionales;
	}

	public void setSigasDepositoCausionales(List<SigasDepositoCausionale> sigasDepositoCausionales) {
		this.sigasDepositoCausionales = sigasDepositoCausionales;
	}	
	
}
