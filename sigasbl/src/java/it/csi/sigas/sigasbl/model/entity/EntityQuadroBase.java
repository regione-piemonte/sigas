package it.csi.sigas.sigasbl.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class EntityQuadroBase {
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_estrazione", nullable=false)
	protected Date dataEstrazione;
	
	@Column(name="codice_ditta", nullable=false)
	protected String codiceDitta;
	
	@Column(name="anno", nullable=false)
	protected String anno;
	
	@Column(name="provincia", nullable=false)
	protected String provincia;
	
	@Column(name="quadro", nullable=false)
	protected String quadro;
	
	@Column(name="prog_rigo", nullable=false)
	protected String progRigo;
	

	public Date getDataEstrazione() {
		return dataEstrazione;
	}

	public void setDataEstrazione(Date dataEstrazione) {
		this.dataEstrazione = dataEstrazione;
	}

	public String getCodiceDitta() {
		return codiceDitta;
	}

	public void setCodiceDitta(String codiceDitta) {
		this.codiceDitta = codiceDitta;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getQuadro() {
		return quadro;
	}

	public void setQuadro(String quadro) {
		this.quadro = quadro;
	}

	public String getProgRigo() {
		return progRigo;
	}

	public void setProgRigo(String progRigo) {
		this.progRigo = progRigo;
	}		
}
