/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Version;

@Entity
@Table(name="sigas_rimborso")
@NamedQuery(name="SigasRimborso.findAll", query="SELECT i FROM SigasRimborso i")
public class SigasRimborso extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_RIMBORSO_IDRIMBORSO_GENERATOR" , sequenceName="SEQ_RIMBORSO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_RIMBORSO_IDRIMBORSO_GENERATOR")
	@Column(name="id_rimborso", unique=true, nullable=false)
	private long idRimborso;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="id_anag")
	private SigasAnagraficaSoggetti sigasAnagraficaSoggetti;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="fk_comunicazioni")
	private SigasAnaComunicazioni sigasAnaComunicazioni;
	
	@Column(name="annualita")
	private String annualita;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_istanza", nullable=false)
	private Date dataIstanza;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_rimborso", nullable=false)
	private Date dataRimborso;
	
	@Column(name="importo")
	private double importo;
	
	@Column(name="importo_rimborsato")
	private double importoRimborsato;
	
	@Column(name="stato_pratica")
	private String statoPratica;
		
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_versamento")
	private Date dataVersamento;	
	
	public SigasRimborso() {
		
	}

	public long getIdRimborso() {
		return idRimborso;
	}

	public void setIdRimborso(long idRimborso) {
		this.idRimborso = idRimborso;
	}

	public SigasAnagraficaSoggetti getSigasAnagraficaSoggetti() {
		return sigasAnagraficaSoggetti;
	}

	public void setSigasAnagraficaSoggetti(SigasAnagraficaSoggetti sigasAnagraficaSoggetti) {
		this.sigasAnagraficaSoggetti = sigasAnagraficaSoggetti;
	}

	public SigasAnaComunicazioni getSigasAnaComunicazioni() {
		return sigasAnaComunicazioni;
	}

	public void setSigasAnaComunicazioni(SigasAnaComunicazioni sigasAnaComunicazioni) {
		this.sigasAnaComunicazioni = sigasAnaComunicazioni;
	}
	
	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public Date getDataIstanza() {
		return dataIstanza;
	}

	public void setDataIstanza(Date dataIstanza) {
		this.dataIstanza = dataIstanza;
	}

	public Date getDataRimborso() {
		return dataRimborso;
	}

	public void setDataRimborso(Date dataRimborso) {
		this.dataRimborso = dataRimborso;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public double getImportoRimborsato() {
		return importoRimborsato;
	}

	public void setImportoRimborsato(double importoRimborsato) {
		this.importoRimborsato = importoRimborsato;
	}

	public String getStatoPratica() {
		return statoPratica;
	}

	public void setStatoPratica(String statoPratica) {
		this.statoPratica = statoPratica;
	}	

	public Date getDataVersamento() {
		return dataVersamento;
	}

	public void setDataVersamento(Date dataVersamento) {
		this.dataVersamento = dataVersamento;
	}		

}
