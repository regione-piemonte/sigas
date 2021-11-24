/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.template;

import org.apache.commons.lang3.builder.ToStringBuilder;

import it.csi.sigas.sigasbl.vo.template.DatiTemplateCompilatiVO;



public class DatiTemplateRequest {

	private Integer codiceTemplate;

	// template rateizzazione
	private Integer idDocumentazione;
	
	private String nProtocollo;


	// solo in fase di stampa
	private DatiTemplateCompilatiVO datiTemplateCompilatiVO;



	public Integer getCodiceTemplate() {
		return codiceTemplate;
	}



	public void setCodiceTemplate(Integer codiceTemplate) {
		this.codiceTemplate = codiceTemplate;
	}

	public DatiTemplateCompilatiVO getDatiTemplateCompilatiVO() {
		return datiTemplateCompilatiVO;
	}

	public void setDatiTemplateCompilatiVO(DatiTemplateCompilatiVO datiTemplateCompilatiVO) {
		this.datiTemplateCompilatiVO = datiTemplateCompilatiVO;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}



	public Integer getIdDocumentazione() {
		return idDocumentazione;
	}



	public void setIdDocumentazione(Integer idDocumentazione) {
		this.idDocumentazione = idDocumentazione;
	}



	public String getnProtocollo() {
		return nProtocollo;
	}



	public void setnProtocollo(String nProtocollo) {
		this.nProtocollo = nProtocollo;
	}
	
	
	

	
	

}
