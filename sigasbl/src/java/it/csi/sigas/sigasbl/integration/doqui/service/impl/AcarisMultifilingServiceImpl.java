/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.integration.doqui.DoquiServiceFactory;
import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisMultifilingService;
import it.csi.sigas.sigasbl.integration.doqui.utils.XmlSerializer;
import it.doqui.acta.acaris.multifilingservice.AcarisException;
import it.doqui.acta.acaris.multifilingservice.MultifilingServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.common.ItemType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.VarargsType;

//20200728_LC
@Service
public class AcarisMultifilingServiceImpl extends CommonManagementServiceImpl implements AcarisMultifilingService
{
	
	private static Logger log = Logger.getLogger(AcarisMultifilingServiceImpl.class);	

	private MultifilingServicePort multifilingService;
	
	@Autowired
	private DoquiServiceFactory acarisServiceFactory;
	
	//private String pdFile;

	private MultifilingServicePort getMultifilingService(boolean forceLoading) throws Exception{
		String method = "getMultifilingService";
		log.debug(method + ". BEGIN");
		try{
			multifilingService = acarisServiceFactory.getAcarisService().getMultifilingServicePort();
			log.info(method + ". AcarisMultifilingServiceInterface loaded correctly");	
		}
		catch(Exception e){
			log.error(method + ". Exception loading interface " + e);
			throw e;
		}
		return multifilingService;
	}
	
	private MultifilingServicePort getMultifilingService() throws Exception{
		return getMultifilingService(false);
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
			if(log.isDebugEnabled()) 
				log.debug(method + ". pdFile= " + getPdFile());
			
			getMultifilingService(true);
		}
		catch(Exception e){
			log.error(method + ". Exception " + e);
			throw new RuntimeException();
		}
	}

	// 20200618_LC
	public ObjectIdType aggiungiClassificazione(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType sourceClassificazioneId, ObjectIdType destinationFolderId, boolean isRichiestaOffline) throws IntegrationException {
		String method = "spostaDocumento";

		// id nuova classificazione
		ObjectIdType classificazioneId = null;

		try {		
		
			//	properties	
			// -----------------------------------------------------------------------------------------------------					
			List<ItemType>	properties = new ArrayList<ItemType>();
			ItemType prop1 = null;
			prop1 = new ItemType();
			prop1.setKey("addConAllegati");
			prop1.setValue("true");
			ItemType prop2 = null;
			prop2 = new ItemType();
			prop2.setKey("offlineAddRequest");
			prop2.setValue(Boolean.toString(isRichiestaOffline));
			properties.add(prop1);
			properties.add(prop2);
			properties.toArray(new ItemType[0]);		
	
			VarargsType params = new VarargsType();		
			params.setItems(properties.toArray(new ItemType[0]));
			
			// -----------------------------------------------------------------------------------------------------
			
			// chiamata tramite WS
			classificazioneId = acarisServiceFactory.getAcarisService()
													.getMultifilingServicePort()
													.aggiungiClassificazione(repositoryId, principalId, sourceClassificazioneId, destinationFolderId, params);			
			if(classificazioneId == null){
				throw new IntegrationException("Non e' stato possibile copiare il documento, idNuovaClassificazione is null");
			}			
			if(log.isDebugEnabled()){
				log.debug(method + ". copiato documento, nuova classificazione Id\n " + XmlSerializer.objectToXml(classificazioneId));
			}			
		}
		catch (AcarisException acEx){
			log.error(method + ". Si e' verificato un errore in fase di copia del documento " + acEx.getMessage());
			if(acEx.getFaultInfo() != null) {				
				this.logAcarisException("AcarisMultifilingServiceImpl", method, acEx.getMessage(), acEx.getFaultInfo());
			}
			throw new IntegrationException("Impossibile copiare il documento ", acEx);
		}
		catch (Exception e) {
			log.error(method + ". Exception = " + e.getMessage());
			throw new IntegrationException("Impossibile copiare il documento ", e);
		}		
		return classificazioneId;
	}	

}

