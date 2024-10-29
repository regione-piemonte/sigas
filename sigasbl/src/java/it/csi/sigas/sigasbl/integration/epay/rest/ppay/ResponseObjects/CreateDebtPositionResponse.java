package it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class CreateDebtPositionResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String identificativoPagamento;
	
	private String codiceEsito;
	
	private String descrizioneEsito;
	
	private String iuv;
	
	private String codiceAvviso;
	
	@JsonProperty("identificativoPagamento")
	public String getIdentificativoPagamento() {
		return identificativoPagamento;
	}

	public void setIdentificativoPagamento(String identificativoPagamento) {
		this.identificativoPagamento = identificativoPagamento;
	}
	
	@JsonProperty("codiceEsito")
	public String getCodiceEsito() {
		return codiceEsito;
	}

	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
	}
	
	@JsonProperty("descrizioneEsito")
	public String getDescrizioneEsito() {
		return descrizioneEsito;
	}

	public void setDescrizioneEsito(String descrizioneEsito) {
		this.descrizioneEsito = descrizioneEsito;
	}
	
	@JsonProperty("iuv")
	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	
	@JsonProperty("codiceAvviso")
	public String getCodiceAvviso() {
		return codiceAvviso;
	}

	public void setCodiceAvviso(String codiceAvviso) {
		this.codiceAvviso = codiceAvviso;
	}	

}
