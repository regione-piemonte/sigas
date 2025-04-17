package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="sigas_deposito_cauzionale")
@NamedQuery(name="SigasDepositoCausionale.findAll", query="SELECT i FROM SigasDepositoCausionale i")
public class SigasDepositoCausionale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SIGAS_DEPOSITO_CAUZIONALE_GENERATOR" , sequenceName="seq_sigas_deposito_cauzionale", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_DEPOSITO_CAUZIONALE_GENERATOR")
	@Column(name="id_deposito_cauzionale", unique=true, nullable=false)
	private long idDepositoCausionale;
	
	@Column(name="importo")
	private BigDecimal importo;	
		
	@ManyToOne	
	@JoinColumn(name="id_provincia", nullable=false)
	private SigasProvincia sigasProvincia;
		
	@ManyToOne	
	@JoinColumn(name="id_documento", nullable=false)
	private SigasDocumenti sigasDocumento;
		
	@ManyToOne
	@JoinColumn(name="id_anag", nullable=false)
	private SigasAnagraficaSoggetti sigasAnagraficaSoggetti;
	
	@Column(name="anno_acccertamento", nullable=true)
	private String annoAcccertamento;
	
	@Column(name="numero_accertamento", nullable=true)
	private String numeroAccertamento;
	
	@Column(name="numero_determina", nullable=true)
	private String numeroDetermina;
	
	@Column(name="fk_carrello_pagamenti", nullable=true)
	private Long fkCarrelloPagementi;

	public long getIdDepositoCausionale() {
		return idDepositoCausionale;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public SigasProvincia getSigasProvincia() {
		return sigasProvincia;
	}

	public SigasDocumenti getSigasDocumento() {
		return sigasDocumento;
	}

	public SigasAnagraficaSoggetti getSigasAnagraficaSoggetti() {
		return sigasAnagraficaSoggetti;
	}

	public void setIdDepositoCausionale(long idDepositoCausionale) {
		this.idDepositoCausionale = idDepositoCausionale;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public void setSigasProvincia(SigasProvincia sigasProvincia) {
		this.sigasProvincia = sigasProvincia;
	}

	public void setSigasDocumento(SigasDocumenti sigasDocumento) {
		this.sigasDocumento = sigasDocumento;
	}

	public void setSigasAnagraficaSoggetti(SigasAnagraficaSoggetti sigasAnagraficaSoggetti) {
		this.sigasAnagraficaSoggetti = sigasAnagraficaSoggetti;
	}

	public String getAnnoAcccertamento() {
		return annoAcccertamento;
	}

	public String getNumeroAccertamento() {
		return numeroAccertamento;
	}

	public String getNumeroDetermina() {
		return numeroDetermina;
	}

	public void setAnnoAcccertamento(String annoAcccertamento) {
		this.annoAcccertamento = annoAcccertamento;
	}

	public void setNumeroAccertamento(String numeroAccertamento) {
		this.numeroAccertamento = numeroAccertamento;
	}

	public void setNumeroDetermina(String numeroDetermina) {
		this.numeroDetermina = numeroDetermina;
	}

	public Long getFkCarrelloPagementi() {
		return fkCarrelloPagementi;
	}

	public void setFkCarrelloPagementi(Long fkCarrelloPagementi) {
		this.fkCarrelloPagementi = fkCarrelloPagementi;
	}

}
