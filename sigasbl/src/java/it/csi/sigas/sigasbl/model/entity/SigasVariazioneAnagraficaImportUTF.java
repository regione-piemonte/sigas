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
@Table(name="sigas_variazione_anagrafica_importUTF")
@NamedQuery(name="SigasVariazioneAnagraficaImportUTF.findAll", query="SELECT i FROM SigasVariazioneAnagraficaImportUTF i")
public class SigasVariazioneAnagraficaImportUTF extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@SequenceGenerator(name="SIGAS_VARIAZIONE_ANAGRAFICA_IMPORTUTF_IDVARIAZIONEANAG_GENERATOR" , sequenceName="SEQ_SIGAS_VARIAZIONE_ANAGRAFICA_IMPORTUTF", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_VARIAZIONE_ANAGRAFICA_IMPORTUTF_IDVARIAZIONEANAG_GENERATOR")
	@Column(name="id_variazione_anag", unique=true, nullable=false)
	private long idVariazioneAnag;
	
	@Column(name="id_anag", unique=true, nullable=false)
	private long idAnag;
	
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
		
	@Column(name="fk_provincia", nullable=false)
	private Long fkProvincia;
	
	@Column(name="fk_comune", nullable=false)
	private Long fkComune;
		
	@Column(name="cf_piva", nullable=false)
	private String cfPiva;
	
	@Column(name="id_import", nullable=false)
	private Long idImport;
	

	public SigasVariazioneAnagraficaImportUTF() {
		
	}


	public long getIdVariazioneAnag() {
		return idVariazioneAnag;
	}


	public void setIdVariazioneAnag(long idVariazioneAnag) {
		this.idVariazioneAnag = idVariazioneAnag;
	}


	public long getIdAnag() {
		return idAnag;
	}


	public void setIdAnag(long idAnag) {
		this.idAnag = idAnag;
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


	public Long getFkProvincia() {
		return fkProvincia;
	}


	public void setFkProvincia(Long fkProvincia) {
		this.fkProvincia = fkProvincia;
	}


	public Long getFkComune() {
		return fkComune;
	}


	public void setFkComune(Long fkComune) {
		this.fkComune = fkComune;
	}


	public String getCfPiva() {
		return cfPiva;
	}


	public void setCfPiva(String cfPiva) {
		this.cfPiva = cfPiva;
	}


	public Long getIdImport() {
		return idImport;
	}


	public void setIdImport(Long idImport) {
		this.idImport = idImport;
	}	

}
