package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sigas_rateo")
@NamedQuery(name="SigasRateo.findAll", query="SELECT i FROM SigasRateo i")
public class SigasRateo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SIGAS_RATEO_IDRATEO_GENERATOR" , sequenceName="SEQ_SIGAS_RATEO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIGAS_RATEO_IDRATEO_GENERATOR")
	@Column(name="id_rateo", unique=true, nullable=false)
	private long idRateo;
	
	@Column(name="id_anag", nullable=false)
	private long idAnag;
	
	@Column(name="importo")
	private double importo;
	
	@Column(name="mese", nullable=false)
	private String mese;
	
	@Column(name="id_provincia", nullable=false)
	private long idProvincia;
	
	@Column(name="annualita", nullable=false)
	private String annualita;

	public long getIdRateo() {
		return idRateo;
	}

	public void setIdRateo(long idRateo) {
		this.idRateo = idRateo;
	}

	public long getIdAnag() {
		return idAnag;
	}

	public void setIdAnag(long idAnag) {
		this.idAnag = idAnag;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public String getMese() {
		return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}	

}
