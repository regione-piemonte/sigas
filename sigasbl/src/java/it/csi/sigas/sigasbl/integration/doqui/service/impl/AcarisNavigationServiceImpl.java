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
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisNavigationService;
import it.doqui.acta.acaris.navigationservice.NavigationServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumPropertyFilter;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PagingResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyFilterType;
@Service
public class AcarisNavigationServiceImpl extends CommonManagementServiceImpl implements AcarisNavigationService {
	
	private static Logger log = Logger.getLogger(AcarisNavigationServiceImpl.class);	
	private NavigationServicePort navigationService;

	@Autowired
	private DoquiServiceFactory acarisServiceFactory;
	
	/*
	private String pdFile;
	
	public String getPdFile() {
		return pdFile;
	}

	public void setPdFile(String pdFile) {
		this.pdFile = pdFile;
	}
	*/

	private NavigationServicePort getNavigationService() throws Exception{
		return getNavigationService(false);
	}
	
	private NavigationServicePort getNavigationService(boolean forceLoading) throws Exception{
		String method = "getNavigationService";
		log.debug(method + ". BEGIN");
		try{
			navigationService = acarisServiceFactory.getAcarisService().getNavigationServicePort();
			log.info(method + ". AcarisNavigationServiceInterface loaded correctly");	
		}
		catch(Exception e){
			log.error(method + ". Exception loading interface " + e);
			throw e;
		}
		return navigationService;
	}
	
	public void init(){
		String method = "init";		
		try{
			super.init();
			if(log.isDebugEnabled()){
				log.debug(method + ". pdFile= " + getPdFile());
			}	
			getNavigationService(true);
		}
		catch(Exception e)
		{
			log.error(method + ". Exception " + e);
			throw new RuntimeException();
		}		
	}
	
	public ObjectIdType recuperaFascicoloProtocollazione(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType idFascicolo) throws IntegrationException
	{		
		String method = "recuperaFascicoloProtocollazione";
		log.debug(method + ". BEGIN");
		
		ObjectResponseType fascicolo = null;		
        PropertyFilterType filter = new PropertyFilterType() ;
        filter.setFilterType(EnumPropertyFilter.ALL);
        
        try{
        	//fascicolo.getObjectId() -> l'objectId dell'aggregazione(fascicolo) che dovrai 
        	//usare come destinazione del doc della reg in PARTENZA
        	//fascicolo contiene anche un array di properies relative al fascicolo (fascicolo.getProperties()) 
    		//che potresti visualizzare in fase di debug per verifica ad esempio codice, iggetto datacreazione ...
        	fascicolo = getNavigationService().getFolderParent(repositoryId, idFascicolo , principalId, filter); 
        	if (fascicolo!=null){  
        		log.debug(method + ". Il fascicolo esiste");        		
        		return fascicolo.getObjectId();
		    }
        }catch (it.doqui.acta.actasrv.util.acaris.wrapper.exception.AcarisException acEx)
        {
        	throw logAcarisExceptionLight(method, acEx);						
		}
		catch (Exception e) 
		{
			throw createAndLogIntegrationException(method,e);
		}
		finally
		{
			log.debug(method + ". END");			
		}
        return null;
	}

	public ObjectResponseType recuperaChildren(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectId, int index) throws IntegrationException{
		
		String method = "recuperaChildren";
		log.debug(method + ". BEGIN");
		
		PagingResponseType children = null;
		ObjectResponseType response = null;
		
        PropertyFilterType filter = new PropertyFilterType() ;
        filter.setFilterType(EnumPropertyFilter.ALL);
        
        try{
        	children = getNavigationService().getChildren(repositoryId, objectId, principalId, filter, null, null); 
        	if (children!=null){
        		
        		log.debug(method + ". Il children esiste");
        		
        		if (children.getObjectsLength()>index) {
                    response = children.getObjects(index);        	
                }
        	}
        }catch (it.doqui.acta.actasrv.util.acaris.wrapper.exception.AcarisException acEx) 
		{					
        	throw logAcarisExceptionLight(method, acEx);			
		}
		catch (Exception e) 
		{
			throw createAndLogIntegrationException(method,e);			
		}
		finally
		{
			log.debug(method + ". END");			
		}   
        /*
        if (children.getObjectsLength()>index) {
            response = children.getObjects(index);        	
        }
        */
        return response;
	}	
	
	public PagingResponseType recuperaDescendants(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectId, Integer depth) throws IntegrationException{
		
		String method = "recuperaChildren";
		log.debug(method + ". BEGIN");
		
		PagingResponseType descendants = null;		
        PropertyFilterType filter = new PropertyFilterType() ;
        filter.setFilterType(EnumPropertyFilter.ALL);
                
        try{
        	descendants = getNavigationService().getDescendants( repositoryId, objectId, principalId, depth, filter, null, null); 
        	if (descendants!=null){ 
        		log.debug(method + ". Il descendants esiste");
        	}
        }
        catch (it.doqui.acta.actasrv.util.acaris.wrapper.exception.AcarisException acEx) 
        {					
        	throw logAcarisExceptionLight(method, acEx);			
		}
		catch (Exception e) 
		{
			throw createAndLogIntegrationException(method,e);
		}
		finally
		{
			log.debug(method + ". END");			
		}        
         return descendants;
	}	
	
	public PagingResponseType recuperaAllChildrens(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectId) throws IntegrationException{
		
		String method = "recuperaChildren";
		log.debug(method + ". BEGIN");
		
		PagingResponseType children = null;		
        PropertyFilterType filter = new PropertyFilterType() ;
        filter.setFilterType(EnumPropertyFilter.ALL);        
        try{
        	children = getNavigationService().getChildren(repositoryId, objectId, principalId, filter, null, null); 
        	if (children!=null){ 
        		log.debug(method + ". Il children esiste");
        	}
        }
        catch (it.doqui.acta.actasrv.util.acaris.wrapper.exception.AcarisException acEx)
		{			
        	throw logAcarisExceptionLight(method, acEx);			
		}
		catch (Exception e) 
		{
			throw createAndLogIntegrationException(method,e);
		}
		finally
		{
			log.debug(method + ". END");			
		}        
        return children;
	}	
}
