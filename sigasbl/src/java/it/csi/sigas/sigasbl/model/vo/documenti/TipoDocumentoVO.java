/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.documenti;

public class TipoDocumentoVO {

	private Integer idTipoDocumento;

	private String codiceTipoDocumento;

	private String descrizione;
	
	private Integer idTipoDocumentoPadre;
	
	private String descVitalRecordCodeType;
	

	public Integer getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}


	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodiceTipoDocumento() {
		return codiceTipoDocumento;
	}

	public void setCodiceTipoDocumento(String codiceTipoDocumento) {
		this.codiceTipoDocumento = codiceTipoDocumento;
	}

	public Integer getIdTipoDocumentoPadre() {
		return idTipoDocumentoPadre;
	}

	public void setIdTipoDocumentoPadre(Integer idTipoDocumentoPadre) {
		this.idTipoDocumentoPadre = idTipoDocumentoPadre;
	}

	public String getDescVitalRecordCodeType() {
		return descVitalRecordCodeType;
	}

	public void setDescVitalRecordCodeType(String descVitalRecordCodeType) {
		this.descVitalRecordCodeType = descVitalRecordCodeType;
	}

	
}
