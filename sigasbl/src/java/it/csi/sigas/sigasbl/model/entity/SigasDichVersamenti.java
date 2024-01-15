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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.FetchType;

@Entity
@Table(name="sigas_dich_versamenti")
@NamedQuery(name="SigasDichVersamenti.findAll", query="SELECT i FROM SigasDichVersamenti i")

public class SigasDichVersamenti extends EntityBase implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIGAS_DICH_VERSAMENTI_IDVERSAMENTI_GENERATOR" , sequenceName="SEQ_DICH_VERSAMENTI", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_DICH_VERSAMENTI_IDVERSAMENTI_GENERATOR")
	@Column(name="id_versamento", unique=true, nullable=false)
	private long idVersamento;
	
	@ManyToOne
	@JoinColumn(name="id_anag", nullable=false)
	private SigasAnagraficaSoggetti sigasAnagraficaSoggetti;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_versamento", nullable=false)
	private SigasTipoVersamento sigasTipoVersamento;
	
	@ManyToOne
	@JoinColumn(name="id_consumi", nullable=false)
	private SigasDichConsumi sigasDichConsumi;
	
	@ManyToOne
	@JoinColumn(name="fk_provincia", nullable=false)
	private SigasProvincia sigasProvincia;

	@Temporal(TemporalType.DATE)
	@Column(name="data_versamento")
	private Date dataVersamento;
	
	@Column(name="annualita", unique=true, nullable=false)
	private String annualita;
	
	@Column(name="importo")
	private double importo;
	
	@Column(name="iban_vers", nullable=false)
	private String ibanVers;	
	
	@Column(name="mese", nullable=false)
	private String mese;
	
	@Column(name="tipologia", nullable=false)
	private String tipologia;
	
	@Column(name = "note", nullable = false)
	private String note;
	
	// Aggiunta Campi per gestione Accertamento
	@Temporal(TemporalType.DATE)
	@Column(name="data_accertamento", nullable = true)
	private Date dataAccertamento;
	
	@Column(name="importo_complessivo",nullable = true)
	private double importoComplessivo;
	
	@OneToMany(mappedBy="sigasDichVersamenti", fetch = FetchType.EAGER)
	private List<SigasPagamentiVersamenti> sigasPagamentiVersamenti;
	
	public SigasDichVersamenti() {
	}
	
	public long getIdVersamento() {
		return idVersamento;
	}

	public void setIdVersamento(long idVersamento) {
		this.idVersamento = idVersamento;
	}

	public SigasAnagraficaSoggetti getSigasAnagraficaSoggetti() {
		return sigasAnagraficaSoggetti;
	}

	public void setSigasAnagraficaSoggetti(SigasAnagraficaSoggetti sigasAnagraficaSoggetti) {
		this.sigasAnagraficaSoggetti = sigasAnagraficaSoggetti;
	}

	public SigasTipoVersamento getSigasTipoVersamento() {
		return sigasTipoVersamento;
	}

	public void setSigasTipoVersamento(SigasTipoVersamento sigasTipoVersamento) {
		this.sigasTipoVersamento = sigasTipoVersamento;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public SigasProvincia getSigasProvincia() {
		return sigasProvincia;
	}

	public void setSigasProvincia(SigasProvincia sigasProvincia) {
		this.sigasProvincia = sigasProvincia;
	}
	
	public SigasDichConsumi getSigasDichConsumi() {
		return sigasDichConsumi;
	}

	public void setSigasDichConsumi(SigasDichConsumi sigasDichConsumi) {
		this.sigasDichConsumi = sigasDichConsumi;
	}

	public Date getDataVersamento() {
		return dataVersamento;
	}

	public void setDataVersamento(Date dataVersamento) {
		this.dataVersamento = dataVersamento;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public String getIbanVers() {
		return ibanVers;
	}

	public void setIbanVers(String ibanVers) {
		this.ibanVers = ibanVers;
	}	

	public String getMese() {
		return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDataAccertamento() {
		return dataAccertamento;
	}

	public void setDataAccertamento(Date dataAccertamento) {
		this.dataAccertamento = dataAccertamento;
	}

	public double getImportoComplessivo() {
		return importoComplessivo;
	}

	public void setImportoComplessivo(double importoComplessivo) {
		this.importoComplessivo = importoComplessivo;
	}

	public List<SigasPagamentiVersamenti> getSigasPagamentiVersamenti() {
		return sigasPagamentiVersamenti;
	}

	public void setSigasPagamentiVersamenti(List<SigasPagamentiVersamenti> sigasPagamentiVersamenti) {
		this.sigasPagamentiVersamenti = sigasPagamentiVersamenti;
	}

	public SigasPagamentiVersamenti addSigasPagamentoVersamento(SigasPagamentiVersamenti sigasPagamentoVersamento) {
		getSigasPagamentiVersamenti().add(sigasPagamentoVersamento);
		sigasPagamentoVersamento.setSigasDichVersamenti(this);
		return sigasPagamentoVersamento;
	}
	
	public SigasPagamentiVersamenti removeSigasPagamentoVersamento(SigasPagamentiVersamenti sigasPagamentoVersamento) {
		getSigasPagamentiVersamenti().remove(sigasPagamentoVersamento);
		sigasPagamentoVersamento.setSigasDichVersamenti(null);
		return sigasPagamentoVersamento;
	}
	
	public String printAll() {
		return 	"idVersamento: " + idVersamento + ", " +
				"sigasAnagraficaSoggetti: " + sigasAnagraficaSoggetti + ", " +
				"sigasTipoVersamento: " + sigasTipoVersamento + ", " +
				"sigasDichConsumi: " + sigasDichConsumi + ", " +
				"sigasProvincia: " + sigasProvincia + ", " +
				"dataVersamento: " + dataVersamento + ", " +
				"annualita: " + annualita + ", " +
				"importo: " + importo + ", " +
				"ibanVers: " + ibanVers + ", " +
				"insUser: " + insUser + ", " +
				"insDate: " + insDate + ", " +
				"modUser: " + modUser + ", " +
				"modDate: " + modDate + ", " +
				"mese: " + mese + ", " +
				"tipologia: " + tipologia + ", " +
				"note: " + note + ", " +
				"dataAccertamento: " + dataAccertamento + ", " +
				"importoComplessivo: " + importoComplessivo + ", " +
				"sigasPagamentiVersamenti: " + sigasPagamentiVersamenti;

	}
}
