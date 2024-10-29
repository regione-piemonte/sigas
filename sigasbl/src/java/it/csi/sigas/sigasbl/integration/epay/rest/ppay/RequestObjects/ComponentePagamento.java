package it.csi.sigas.sigasbl.integration.epay.rest.ppay.RequestObjects;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

public class ComponentePagamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Integer annoAccertamento;
	
	private String causale;
	
	private String datiSpecificRiscossione;
	
	private String numeroAccertamento;
	
	private Integer progessivo;
	
	private BigDecimal importo;
	
	@JsonProperty("annoAccertamento")
	public Integer getAnnoAccertamento() {
		return annoAccertamento;
	}

	public void setAnnoAccertamento(Integer annoAccertamento) {
		this.annoAccertamento = annoAccertamento;
	}
	
	@JsonProperty("causale")
	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}
	
	@JsonProperty("datiSpecificRiscossione")
	public String getDatiSpecificRiscossione() {
		return datiSpecificRiscossione;
	}

	public void setDatiSpecificRiscossione(String datiSpecificRiscossione) {
		this.datiSpecificRiscossione = datiSpecificRiscossione;
	}
	
	@JsonProperty("numeroAccertamento")
	public String getNumeroAccertamento() {
		return numeroAccertamento;
	}

	public void setNumeroAccertamento(String numeroAccertamento) {
		this.numeroAccertamento = numeroAccertamento;
	}
	
	@JsonProperty("progessivo")
	public Integer getProgessivo() {
		return progessivo;
	}

	public void setProgessivo(Integer progessivo) {
		this.progessivo = progessivo;
	}
	
	@JsonProperty("importo")
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}	

}
