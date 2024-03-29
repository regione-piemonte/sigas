/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.integration.doqui.DoquiServiceFactory;
import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisManagementService;
import it.csi.sigas.sigasbl.integration.doqui.utils.XmlSerializer;
import it.doqui.acta.acaris.managementservice.ManagementServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.management.VitalRecordCodeType;

@Service
public class AcarisManagementServiceImpl extends CommonManagementServiceImpl implements AcarisManagementService
{

	private static Logger log = Logger.getLogger(AcarisManagementServiceImpl.class);

	private ManagementServicePort managementService;

	@Autowired
	private DoquiServiceFactory acarisServiceFactory;
		
	//private String pdFile;
		
	private ManagementServicePort getManagementService(boolean forceLoading) throws Exception{
		String method = "getManagementService";
		log.debug(method + ". BEGIN");
		try{
			managementService = acarisServiceFactory.getAcarisService().getManagementServicePort();
			log.info(method + ". AcarisManagementServiceInterface loaded correctly");	
		}
		catch(Exception e){
			log.error(method + ". Exception loading interface " + e);
			throw e;
		}
		return managementService;
	}
	
	private ManagementServicePort getManagementService() throws Exception{
		return getManagementService(false);
	}
	/*
	public String getPdFile() 
	{
		return pdFile;
	}

	public void setPdFile(String pdFile)
	{
		this.pdFile = pdFile;
	}
	*/
	
	public void init(){
		String method = "init";
		
		try{
			super.init();
			if(log.isDebugEnabled()){
				log.debug(method + ". pdFile= " + getPdFile());
			}	
			
			getManagementService(true);
		}
		catch(Exception e)
		{
			log.error(method + ". Exception " + e);
			throw new RuntimeException();
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see it.csi.stacore.stadoc.business.stadoc.integration.AcarisManagementServiceStadoc#recuperaVitalRecordCode(it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType)
	 */
	public VitalRecordCodeType[] recuperaVitalRecordCode(it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType repositoryId) throws IntegrationException {
		String method = "recuperaVitalRecordCode";
		VitalRecordCodeType[] elencoVitalRecordCodeType = null;
		try {		
			
			elencoVitalRecordCodeType = getManagementService().getVitalRecordCode(repositoryId);
			if(elencoVitalRecordCodeType == null){
				throw new IntegrationException("Impossibile recuperare VitalRecordCode: elencoVitalRecordCodeType is null");
			}
			if(log.isDebugEnabled()){
				log.debug(method + ". elencoVitalRecordCodeType\n " + XmlSerializer.objectToXml(elencoVitalRecordCodeType));	
			}	
		} 
		catch (it.doqui.acta.actasrv.util.acaris.wrapper.exception.AcarisException acEx) {			
			this.logAcarisException("AcarisManagementServiceImpl",method,acEx.getMessage(),acEx.getFaultInfo());
			throw new IntegrationException("AcarisException ", acEx);
		} 
		catch (Exception e) {
			log.error(method + ". Exception = " + e.getMessage(), e);
			throw new IntegrationException("Impossibile recuperare VitalRecordCode ", e);
		}
		return elencoVitalRecordCodeType;
	}

	
}

