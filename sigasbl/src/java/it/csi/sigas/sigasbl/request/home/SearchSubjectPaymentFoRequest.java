/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class SearchSubjectPaymentFoRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private String year;
	private String subjectId;
	private String subjectCode;
	private String subjectName;
	private String area;
	private String dateFrom;
	private String dateTo;
	private String monthFrom;
	private String monthTo;
	private String operatorFO;
	private String vatCode;
	private String payType;
	private String iuv;
	private String id;
	private String codiceFiscalePIva;
	
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getOperatorFO() {
		return operatorFO;
	}
	public void setOperatorFO(String operatorFO) {
		this.operatorFO = operatorFO;
	}
	public String getVatCode() {
		return vatCode;
	}
	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public String getMonthFrom() {
		return monthFrom;
	}
	public void setMonthFrom(String monthFrom) {
		this.monthFrom = monthFrom;
	}
	public String getMonthTo() {
		return monthTo;
	}
	public void setMonthTo(String monthTo) {
		this.monthTo = monthTo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodiceFiscalePIva() {
		return codiceFiscalePIva;
	}
	public void setCodiceFiscalePIva(String codiceFiscalePIva) {
		this.codiceFiscalePIva = codiceFiscalePIva;
	}
	
	
	
}
