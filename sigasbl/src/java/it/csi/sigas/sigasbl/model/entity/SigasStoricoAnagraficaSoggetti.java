package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="sigas_storico_anagrafica_soggetti")
@NamedQuery(name="SigasStoricoAnagraficaSoggetti.findAll", query="SELECT i FROM SigasStoricoAnagraficaSoggetti i")
public class SigasStoricoAnagraficaSoggetti implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SIGAS_STORICO_ANAGRAFICA_SOGGETTI_IDSTORICOANAG_GENERATOR", 
					   sequenceName = "SEQ_SIGAS_STORICO_ANAGRAFICA_SOGGETTI", 
					   allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIGAS_STORICO_ANAGRAFICA_SOGGETTI_IDSTORICOANAG_GENERATOR")
	@Column(name = "id_storico_anag", unique = true, nullable = false)
	private long idStoricoAnag;
	
	@Column(name="codice_azienda")
	private String codiceAzienda;

	@Column(name="denominazione")
	private String denominazione;
	
	@Column(name="indirizzo", nullable=false)
	private String indirizzo;
	
	@Column(name="iban", nullable=false)
	private String iban;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="pec", nullable=false)
	private String pec;
	
	@Column(name="id_anag", nullable=false)	
	private long idAnag;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_riferimento")
	private Date dataRiferimento;
	
	@Column(name="cf_piva", nullable=true)
	private String cfPiva;
	
	@Column(name="owner_operazione", nullable=true)
	private String ownerOperazione;
	
	@Column(name="id_import", nullable=true)
	private Long idImport;
	
	@Column(name="annualita", nullable=true)
	private String annualita;
	
	@Column(name="selected_import", nullable=true)
	private Boolean selectedImport;
	
	@ManyToOne
	@JoinColumn(name="fk_provincia", nullable=false)
	private SigasProvincia sigasProvincia;
	
	@ManyToOne
	@JoinColumn(name="fk_comune", nullable=false)
	private SigasComune sigasComune;

	
	public long getIdStoricoAnag() {
		return idStoricoAnag;
	}

	public void setIdStoricoAnag(long idStoricoAnag) {
		this.idStoricoAnag = idStoricoAnag;
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

	public long getIdAnag() {
		return idAnag;
	}

	public void setIdAnag(long idAnag) {
		this.idAnag = idAnag;
	}

	public Date getDataRiferimento() {
		return dataRiferimento;
	}

	public void setDataRiferimento(Date dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}

	public String getCfPiva() {
		return cfPiva;
	}

	public void setCfPiva(String cfPiva) {
		this.cfPiva = cfPiva;
	}

	public String getOwnerOperazione() {
		return ownerOperazione;
	}

	public void setOwnerOperazione(String ownerOperazione) {
		this.ownerOperazione = ownerOperazione;
	}

	public Long getIdImport() {
		return idImport;
	}

	public void setIdImport(Long idImport) {
		this.idImport = idImport;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public Boolean getSelectedImport() {
		return selectedImport;
	}

	public void setSelectedImport(Boolean selectedImport) {
		this.selectedImport = selectedImport;
	}

	public SigasProvincia getSigasProvincia() {
		return sigasProvincia;
	}

	public SigasComune getSigasComune() {
		return sigasComune;
	}

	public void setSigasProvincia(SigasProvincia sigasProvincia) {
		this.sigasProvincia = sigasProvincia;
	}

	public void setSigasComune(SigasComune sigasComune) {
		this.sigasComune = sigasComune;
	}	

}
