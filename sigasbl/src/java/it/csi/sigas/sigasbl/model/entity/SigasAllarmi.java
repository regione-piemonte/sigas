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

@Entity
@Table(name="sigas_allarmi")
@NamedQuery(name="SigasAllarmi.findAll", query="SELECT i FROM SigasAllarmi i")
public class SigasAllarmi implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_ALLAEME_IDALLARME_GENERATOR" , sequenceName="SEQ_ALLARMI", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_ALLAEME_IDALLARME_GENERATOR")
	@Column(name="id_allarme", unique=true, nullable=false)
	private Integer idAllarme;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="id_consumi")
	private SigasDichConsumi sigasDichConsumi;
	
	@ManyToOne
	@JoinColumn(name="id_comunicazione")
	private SigasAnaComunicazioni sigasAnaComunicazioni;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_allarme", nullable=false)
	private SigasTipoAllarmi sigasTipoAllarme;
	
	@Column(name="codice_azienda")
	private String codiceAzienda;
	
	@Column(name="annualita", nullable=false)
	private String annualita;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="attivazione", nullable=false)
	private Date attivazione;
	
	@Column(name="utente")
	private String utente;
	
	@Column(name="status")
	private int status;
	
	@Column(name="nota")
	private String nota;
	
	@ManyToOne
	@JoinColumn(name="id_versamento")
	private SigasDichVersamenti sigasDichVersamenti;
	
	
	public SigasAllarmi() {
		
	}

	public Integer getIdAllarme() {
		return idAllarme;
	}

	public void setIdAllarme(Integer idAllarme) {
		this.idAllarme = idAllarme;
	}

	public SigasDichConsumi getSigasDichConsumi() {
		return sigasDichConsumi;
	}

	public void setSigasDichConsumi(SigasDichConsumi sigasDichConsumi) {
		this.sigasDichConsumi = sigasDichConsumi;
	}

	public SigasAnaComunicazioni getSigasAnaComunicazioni() {
		return sigasAnaComunicazioni;
	}

	public void setSigasAnaComunicazioni(SigasAnaComunicazioni sigasAnaComunicazioni) {
		this.sigasAnaComunicazioni = sigasAnaComunicazioni;
	}

	public SigasTipoAllarmi getSigasTipoAllarme() {
		return sigasTipoAllarme;
	}

	public void setSigasTipoAllarme(SigasTipoAllarmi sigasTipoAllarme) {
		this.sigasTipoAllarme = sigasTipoAllarme;
	}

	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public Date getAttivazione() {
		return attivazione;
	}

	public void setAttivazione(Date attivazione) {
		this.attivazione = attivazione;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public SigasDichVersamenti getSigasDichVersamenti() {
		return sigasDichVersamenti;
	}

	public void setSigasDichVersamenti(SigasDichVersamenti sigasDichVersamenti) {
		this.sigasDichVersamenti = sigasDichVersamenti;
	}

}
