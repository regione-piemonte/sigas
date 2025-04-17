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
	
	private String datiSpecificiRiscossione;
	
	private String numeroAccertamento;
	
	private Integer progressivo;
	
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
	
	@JsonProperty("datiSpecificiRiscossione")
	public String getDatiSpecificiRiscossione() {
		return datiSpecificiRiscossione;
	}

	public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {
		this.datiSpecificiRiscossione = datiSpecificiRiscossione;
	}
	
	@JsonProperty("numeroAccertamento")
	public String getNumeroAccertamento() {
		return numeroAccertamento;
	}

	public void setNumeroAccertamento(String numeroAccertamento) {
		this.numeroAccertamento = numeroAccertamento;
	}
	
	@JsonProperty("progressivo")
	public Integer getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}
	
	@JsonProperty("importo")
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}	

}
