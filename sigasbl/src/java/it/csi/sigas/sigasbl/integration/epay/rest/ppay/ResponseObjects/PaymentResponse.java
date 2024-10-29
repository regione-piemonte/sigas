package it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects;

import java.io.Serializable;

public class PaymentResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codiceEsito;
	
	private String descrizioneEsito;
	
	private String iuv;
	
	private String paymentUrl;

	public String getCodiceEsito() {
		return codiceEsito;
	}

	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
	}

	public String getDescrizioneEsito() {
		return descrizioneEsito;
	}

	public void setDescrizioneEsito(String descrizioneEsito) {
		this.descrizioneEsito = descrizioneEsito;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}	

}
