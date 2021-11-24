/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.helper;

import it.csi.sigas.sigasbl.integration.doqui.exception.FruitoreException;
import it.csi.sigas.sigasbl.model.vo.SigasFruitoreVO;


public interface CommonManageDocumentoHelper {
	
	/**
	 * 
	 * @return
	 * @throws FruitoreException
	 */
	public SigasFruitoreVO getFruitore() throws FruitoreException;
	
	

	
	
	
	
	
	
}
