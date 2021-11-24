/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.documentazione;

import java.io.Serializable;
import java.util.Date;

import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;

public class RicercaDocumentazioneBoRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private  AnagraficaSoggettoVO anagraficaSoggettoVO;
	
    private  Integer idStatoDocumento;
    
    public Date dataProtocolloDal;
    
    public Date dataProtocolloAl;

	public AnagraficaSoggettoVO getAnagraficaSoggettoVO() {
		return anagraficaSoggettoVO;
	}

	public void setAnagraficaSoggettoVO(AnagraficaSoggettoVO anagraficaSoggettoVO) {
		this.anagraficaSoggettoVO = anagraficaSoggettoVO;
	}

	

	

	public Integer getIdStatoDocumento() {
		return idStatoDocumento;
	}

	public void setIdStatoDocumento(Integer idStatoDocumento) {
		this.idStatoDocumento = idStatoDocumento;
	}

	public Date getDataProtocolloDal() {
		return dataProtocolloDal;
	}

	public void setDataProtocolloDal(Date dataProtocolloDal) {
		this.dataProtocolloDal = dataProtocolloDal;
	}

	public Date getDataProtocolloAl() {
		return dataProtocolloAl;
	}

	public void setDataProtocolloAl(Date dataProtocolloAl) {
		this.dataProtocolloAl = dataProtocolloAl;
	}
	
	
}
