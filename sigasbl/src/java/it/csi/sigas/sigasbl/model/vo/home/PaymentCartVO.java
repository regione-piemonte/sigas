/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.common.rest.VO;

public class PaymentCartVO implements VO {

	private static final long serialVersionUID = 1L;
	
	private String status;
	private String paymentCode;
	private Integer paymentType;
	private String currentDate;
	private String payDate;
	private String email;
	private String year;
	private String subjectName;
	private String subjectArea;
	private String area;
	private Integer idAnag;
	private Integer month;
	private String amount;
	private Integer type;
	private Integer cartOption;
	private String subjectCode;
	private Long id;
	private String typeDesc;
	private String monthDesc;
	private String codiceFiscalePIva;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subject_name) {
		this.subjectName = subject_name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Integer id_anag) {
		this.idAnag = id_anag;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getCartOption() {
		return cartOption;
	}
	public void setCartOption(Integer cartOption) {
		this.cartOption = cartOption;
	}
	public String getSubjectArea() {
		return subjectArea;
	}
	public void setSubjectArea(String subjectArea) {
		this.subjectArea = subjectArea;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getMonthDesc() {
		return monthDesc;
	}
	public void setMonthDesc(String monthDesc) {
		this.monthDesc = monthDesc;
	}
	public String getCodiceFiscalePIva() {
		return codiceFiscalePIva;
	}
	public void setCodiceFiscalePIva(String codiceFiscalePIva) {
		this.codiceFiscalePIva = codiceFiscalePIva;
	}

	
	
}
