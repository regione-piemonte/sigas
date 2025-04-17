package it.csi.sigas.sigasbl.request.depositocauzionale;

import java.io.Serializable;

public class RigeneraBollettinoPagamentoRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idDocumento;
	
	private Long idDepositoCauzionale;
	
	private String codicePagamento;

	public Long getIdDocumento() {
		return idDocumento;
	}

	public Long getIdDepositoCauzionale() {
		return idDepositoCauzionale;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public void setIdDepositoCauzionale(Long idDepositoCauzionale) {
		this.idDepositoCauzionale = idDepositoCauzionale;
	}

	public String getCodicePagamento() {
		return codicePagamento;
	}

	public void setCodicePagamento(String codicePagamento) {
		this.codicePagamento = codicePagamento;
	}

}
