/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class PaymentRTInfoVO implements VO {

	private static final long serialVersionUID = 1L;

    private String idAnag;
    private String paymentCode;
    private String transactionCode;
    private String iuv;
    private String idOriginPay;
    private String months;
    private String year;
    private String payDate;
    private String subjectName;
    private String receivingEntity;
    private String vatCode;
    private String taxCode;
    private String totalAmount;
    private String entityCode;
	public String getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(String idAnag) {
		this.idAnag = idAnag;
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	
	public String getIdOriginPay() {
		return idOriginPay;
	}
	public void setIdOriginPay(String idOriginPay) {
		this.idOriginPay = idOriginPay;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getReceivingEntity() {
		return receivingEntity;
	}
	public void setReceivingEntity(String receivingEntity) {
		this.receivingEntity = receivingEntity;
	}
	public String getVatCode() {
		return vatCode;
	}
	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
    
    
}
