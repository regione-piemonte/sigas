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
 * The persistent class for the sigas_dichiarante database table.
 * 
 */
@Entity
@Table(name="sigas_dichiarante")
@NamedQuery(name="SigasDichiarante.findAll", query="SELECT s FROM SigasDichiarante s")
public class SigasDichiarante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_DICHIARANTE_IDDICHIARANTE_GENERATOR" , sequenceName="SEQ_T_DICHIARANTE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_DICHIARANTE_IDDICHIARANTE_GENERATOR")
	@Column(name="id_dichiarante", unique=true, nullable=false)
	private long idDichiarante;

	@Column(name="codice_azienda")
	private String codiceAzienda;

	@Column(name="data_insert")
	private Timestamp dataInsert;

	@Column(name="denom_dichiarante")
	private String denomDichiarante;

	@Column(name="email_dichiarante")
	private String emailDichiarante;

	private String iban;

	@Column(name="indirizzo_sele_legale_dichiar")
	private String indirizzoSeleLegaleDichiar;

	private String note;

	@Column(name="pec_dichiarante")
	private String pecDichiarante;

	@Column(name="telefono_dichiarante")
	private String telefonoDichiarante;

	//bi-directional many-to-one association to SigasComune
	@ManyToOne(cascade=CascadeType.ALL)	
	@JoinColumn(name="fk_comune_sede_legale_dichiar")
	private SigasComune sigasComune;

	//bi-directional many-to-one association to SigasProvincia
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_provincia")
	private SigasProvincia sigasProvincia;

	//bi-directional many-to-one association to SigasLegaleRappresent
	@OneToMany(mappedBy="sigasDichiarante")
	private List<SigasLegaleRappresent> sigasLegaleRappresents;

	//bi-directional many-to-one association to SigasOperatore
	@OneToMany(mappedBy="sigasDichiarante")
	private List<SigasOperatore> sigasOperatores;

	//bi-directional many-to-one association to SigasUtenteProvvisorio
	@OneToMany(mappedBy="sigasDichiarante")
	private List<SigasUtenteProvvisorio> sigasUtenteProvvisorios;

	public SigasDichiarante() {
	}

	public long getIdDichiarante() {
		return this.idDichiarante;
	}

	public void setIdDichiarante(long idDichiarante) {
		this.idDichiarante = idDichiarante;
	}

	public String getCodiceAzienda() {
		return this.codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public Timestamp getDataInsert() {
		return this.dataInsert;
	}

	public void setDataInsert(Timestamp dataInsert) {
		this.dataInsert = dataInsert;
	}

	public String getDenomDichiarante() {
		return this.denomDichiarante;
	}

	public void setDenomDichiarante(String denomDichiarante) {
		this.denomDichiarante = denomDichiarante;
	}

	public String getEmailDichiarante() {
		return this.emailDichiarante;
	}

	public void setEmailDichiarante(String emailDichiarante) {
		this.emailDichiarante = emailDichiarante;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getIndirizzoSeleLegaleDichiar() {
		return this.indirizzoSeleLegaleDichiar;
	}

	public void setIndirizzoSeleLegaleDichiar(String indirizzoSeleLegaleDichiar) {
		this.indirizzoSeleLegaleDichiar = indirizzoSeleLegaleDichiar;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPecDichiarante() {
		return this.pecDichiarante;
	}

	public void setPecDichiarante(String pecDichiarante) {
		this.pecDichiarante = pecDichiarante;
	}

	public String getTelefonoDichiarante() {
		return this.telefonoDichiarante;
	}

	public void setTelefonoDichiarante(String telefonoDichiarante) {
		this.telefonoDichiarante = telefonoDichiarante;
	}

	public SigasComune getSigasComune() {
		return this.sigasComune;
	}

	public void setSigasComune(SigasComune sigasComune) {
		this.sigasComune = sigasComune;
	}

	public SigasProvincia getSigasProvincia() {
		return this.sigasProvincia;
	}

	public void setSigasProvincia(SigasProvincia sigasProvincia) {
		this.sigasProvincia = sigasProvincia;
	}

	public List<SigasLegaleRappresent> getSigasLegaleRappresents() {
		return this.sigasLegaleRappresents;
	}

	public void setSigasLegaleRappresents(List<SigasLegaleRappresent> sigasLegaleRappresents) {
		this.sigasLegaleRappresents = sigasLegaleRappresents;
	}

	public SigasLegaleRappresent addSigasLegaleRappresent(SigasLegaleRappresent sigasLegaleRappresent) {
		getSigasLegaleRappresents().add(sigasLegaleRappresent);
		sigasLegaleRappresent.setSigasDichiarante(this);

		return sigasLegaleRappresent;
	}

	public SigasLegaleRappresent removeSigasLegaleRappresent(SigasLegaleRappresent sigasLegaleRappresent) {
		getSigasLegaleRappresents().remove(sigasLegaleRappresent);
		sigasLegaleRappresent.setSigasDichiarante(null);

		return sigasLegaleRappresent;
	}

	public List<SigasOperatore> getSigasOperatores() {
		return this.sigasOperatores;
	}

	public void setSigasOperatores(List<SigasOperatore> sigasOperatores) {
		this.sigasOperatores = sigasOperatores;
	}

	public SigasOperatore addSigasOperatore(SigasOperatore sigasOperatore) {
		getSigasOperatores().add(sigasOperatore);
		sigasOperatore.setSigasDichiarante(this);

		return sigasOperatore;
	}

	public SigasOperatore removeSigasOperatore(SigasOperatore sigasOperatore) {
		getSigasOperatores().remove(sigasOperatore);
		sigasOperatore.setSigasDichiarante(null);

		return sigasOperatore;
	}

	public List<SigasUtenteProvvisorio> getSigasUtenteProvvisorios() {
		return this.sigasUtenteProvvisorios;
	}

	public void setSigasUtenteProvvisorios(List<SigasUtenteProvvisorio> sigasUtenteProvvisorios) {
		this.sigasUtenteProvvisorios = sigasUtenteProvvisorios;
	}

	public SigasUtenteProvvisorio addSigasUtenteProvvisorio(SigasUtenteProvvisorio sigasUtenteProvvisorio) {
		getSigasUtenteProvvisorios().add(sigasUtenteProvvisorio);
		sigasUtenteProvvisorio.setSigasDichiarante(this);

		return sigasUtenteProvvisorio;
	}

	public SigasUtenteProvvisorio removeSigasUtenteProvvisorio(SigasUtenteProvvisorio sigasUtenteProvvisorio) {
		getSigasUtenteProvvisorios().remove(sigasUtenteProvvisorio);
		sigasUtenteProvvisorio.setSigasDichiarante(null);

		return sigasUtenteProvvisorio;
	}

}