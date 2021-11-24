/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.documentazione;

import java.io.Serializable;

import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.documenti.TipoDocumentoVO;

public class RicercaDocumentazioneRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private  AnagraficaSoggettoVO anagraficaSoggettoVO;
	
    private  String nprotocollo;
    
    public TipoDocumentoVO tipoDocumentoVO;
    
    public Integer annualita;

	public AnagraficaSoggettoVO getAnagraficaSoggettoVO() {
		return anagraficaSoggettoVO;
	}

	public void setAnagraficaSoggettoVO(AnagraficaSoggettoVO anagraficaSoggettoVO) {
		this.anagraficaSoggettoVO = anagraficaSoggettoVO;
	}

	public String getNprotocollo() {
		return nprotocollo;
	}

	public void setNprotocollo(String nprotocollo) {
		this.nprotocollo = nprotocollo;
	}

	public TipoDocumentoVO getTipoDocumentoVO() {
		return tipoDocumentoVO;
	}

	public void setTipoDocumentoVO(TipoDocumentoVO tipoDocumentoVO) {
		this.tipoDocumentoVO = tipoDocumentoVO;
	}

	public Integer getAnnualita() {
		return annualita;
	}

	public void setAnnualita(Integer annualita) {
		this.annualita = annualita;
	}
	
}
