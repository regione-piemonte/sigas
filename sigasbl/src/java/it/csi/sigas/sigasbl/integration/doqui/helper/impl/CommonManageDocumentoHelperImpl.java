/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.helper.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.sigas.sigasbl.integration.doqui.DoquiConstants;
import it.csi.sigas.sigasbl.integration.doqui.exception.FruitoreException;
import it.csi.sigas.sigasbl.integration.doqui.helper.CommonManageDocumentoHelper;
import it.csi.sigas.sigasbl.integration.doqui.utils.XmlSerializer;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.vo.SigasFruitoreVO;


public class CommonManageDocumentoHelperImpl implements CommonManageDocumentoHelper {

	
	



//	protected static final String LOGGER_PREFIX = DoquiConstants.LOGGER_PREFIX + ".helper";	
//	protected static final Logger log = Logger.getLogger(LOGGER_PREFIX);
	private static Logger log = Logger.getLogger(CommonManageDocumentoHelperImpl.class);
	
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;

	

	/*
	 * (non-Javadoc)
	 * @see it.csi.stacore.stadoc.business.stadoc.helper.CommonManageDocumentoHelper#getFruitore()
	 */
	
	SigasFruitoreVO sigasFruitoreVO = null;
	public SigasFruitoreVO getFruitore() throws FruitoreException {
		String method = "getFruitore";
		
		if(sigasFruitoreVO != null) {
			if(log.isDebugEnabled()){
				log.debug(method + ". SigasFruitoreVO \n " + XmlSerializer.objectToXml(sigasFruitoreVO));
			}
			return sigasFruitoreVO;
		}
		
		try
		{	
			String idAooActa = sigasCParametroRepository.findByDescParametro(DoquiConstants.ACTA_ID_AOO).getValoreString();	
			String idStrutturaActa = sigasCParametroRepository.findByDescParametro(DoquiConstants.ACTA_ID_STRUTTURA).getValoreString();	
			String idNodoActa = sigasCParametroRepository.findByDescParametro(DoquiConstants.ACTA_ID_NODO).getValoreString();	
			String codFruitore = sigasCParametroRepository.findByDescParametro(DoquiConstants.ACTA_CODE_FRUITORE).getValoreString();
			String cfActa = sigasCParametroRepository.findByDescParametro(DoquiConstants.ACTA_CF).getValoreString();
			String repositoryIndex = null;
			String passwordIndex = null;
			String usernameIndex = null;
			String fruitoreIndex = null;	
			String customModelIndex = null;	
			String descrFruitore = codFruitore;
			
			sigasFruitoreVO = new SigasFruitoreVO(1, codFruitore, descrFruitore, cfActa, idAooActa, idStrutturaActa, idNodoActa, repositoryIndex, usernameIndex, passwordIndex, fruitoreIndex, customModelIndex);
			if(log.isDebugEnabled()){
				log.debug(method + ". SigasFruitoreVO \n " + XmlSerializer.objectToXml(sigasFruitoreVO));
			}
			
		}
		catch (Exception e)
		{
			log.error(method + ". Accesso al DAO Fallito " + e);
			throw new FruitoreException(e.getMessage());
		}

		return sigasFruitoreVO;
	
	}
	
	

}
