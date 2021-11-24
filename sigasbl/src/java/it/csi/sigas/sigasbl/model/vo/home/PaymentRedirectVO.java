/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class PaymentRedirectVO implements VO {

	private static final long serialVersionUID = 1L;

	private String url;
	private String codiceChiamante;
	private String digest;
	private String iuv;
	private String codiceFiscale;
	private String identificativoPagamento;
	private String waitingUserMessage;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCodiceChiamante() {
		return codiceChiamante;
	}
	public void setCodiceChiamante(String codiceChiamante) {
		this.codiceChiamante = codiceChiamante;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getIdentificativoPagamento() {
		return identificativoPagamento;
	}
	public void setIdentificativoPagamento(String identificativoPagamento) {
		this.identificativoPagamento = identificativoPagamento;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getWaitingUserMessage() {
		return waitingUserMessage;
	}
	public void setWaitingUserMessage(String waitingUserMessage) {
		this.waitingUserMessage = waitingUserMessage;
	}

}
