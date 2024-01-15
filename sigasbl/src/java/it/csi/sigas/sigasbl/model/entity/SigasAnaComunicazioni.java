/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name="sigas_ana_comunicazioni")
@NamedQuery(name="SigasAnaComunicazioni.findAll", query="SELECT i FROM SigasAnaComunicazioni i")
public class SigasAnaComunicazioni extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_ANA_COMINUCAZIONI_IDANAGA_GENERATOR" , sequenceName="SEQ_ANA_COMUNICAZIONI", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_ANA_COMINUCAZIONI_IDANAGA_GENERATOR")
	@Column(name="id_comunicazione", unique=true, nullable=false)
	private long idComunicazione;

	@ManyToOne
	@JoinColumn(name="id_tipo_comunicazione", nullable=false)
	private SigasTipoComunicazioni sigasTipoComunicazioni;
	
	@ManyToOne
	@JoinColumn(name="id_anag", nullable=false)
	private SigasAnagraficaSoggetti sigasAnagraficaSoggetti;

	@Column(name="descrizione")
	private String descrizione;

	@Temporal(TemporalType.DATE)
	@Column(name="data_documento")
	private Date dataDocumento;

	@Column(name="annualita", nullable=false)
	private String annualita;

	@Column(name="note")
	private String note;
	
	@Column(name="dati_riassuntivi")
	private String datiRiassuntivi;

	@Column(name="n_protocollo")
	private String nProtocollo;

	@Column(name="rif_archivio")
	private String rifArchivio;	
	
	@Column(name="del_date")
	private Date delDate;

	@Column(name="del_user")
	private String delUser;
	

	public SigasAnaComunicazioni() {
	}

	public long getIdComunicazione() {
		return idComunicazione;
	}

	public void setIdComunicazione(long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}

	public SigasTipoComunicazioni getSigasTipoComunicazione() {
		return sigasTipoComunicazioni;
	}

	public void setSigasTipoComunicazioni(SigasTipoComunicazioni sigasTipoComunicazioni) {
		this.sigasTipoComunicazioni = sigasTipoComunicazioni;
	}

	public SigasAnagraficaSoggetti getSigasAnagraficaSoggetti() {
		return sigasAnagraficaSoggetti;
	}

	public void setSigasAnagraficaSoggetti(SigasAnagraficaSoggetti sigasAnagraficaSoggetti) {
		this.sigasAnagraficaSoggetti = sigasAnagraficaSoggetti;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDatiRiassuntivi() {
		return datiRiassuntivi;
	}

	public void setDatiRiassuntivi(String datiRiassuntivi) {
		this.datiRiassuntivi = datiRiassuntivi;
	}

	public String getNProtocollo() {
		return nProtocollo;
	}

	public void setNProtocollo(String nProtocollo) {
		this.nProtocollo = nProtocollo;
	}

	public String getRifArchivio() {
		return rifArchivio;
	}

	public void setRifArchivio(String rifArchivio) {
		this.rifArchivio = rifArchivio;
	}

	public String getnProtocollo() {
		return nProtocollo;
	}

	public void setnProtocollo(String nProtocollo) {
		this.nProtocollo = nProtocollo;
	}	

	public SigasTipoComunicazioni getSigasTipoComunicazioni() {
		return sigasTipoComunicazioni;
	}	
	
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	public String getDelUser() {
		return delUser;
	}

	public void setDelUser(String delUser) {
		this.delUser = delUser;
	}	

}
