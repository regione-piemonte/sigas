package it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects;

import java.io.Serializable;

public class GeneraAvvisoPagamentoResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private byte[] paymentnotice = null;
	private GeneraAvvisoPagamentoResult result;
		 
	public byte[] getPaymentnotice() {
		return paymentnotice;
	}
	public void setPaymentnotice(byte[] paymentnotice) {
		this.paymentnotice = paymentnotice;
	}	
	 
	public GeneraAvvisoPagamentoResult getResult() {
		return result;
	}
	public void setResult(GeneraAvvisoPagamentoResult result) {
		this.result = result;
	}

}
