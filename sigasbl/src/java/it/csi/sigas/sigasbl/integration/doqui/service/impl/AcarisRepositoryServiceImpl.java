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
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisRepositoryService;
import it.doqui.acta.acaris.repositoryservice.RepositoryServicePort;
import it.doqui.acta.actasrv.dto.acaris.type.archive.AcarisRepositoryEntryType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;

@Service
public class AcarisRepositoryServiceImpl extends CommonManagementServiceImpl implements AcarisRepositoryService
{	
	private static Logger log = Logger.getLogger(AcarisRepositoryServiceImpl.class);	
	private RepositoryServicePort repositoryService;
	
	@Autowired
	private DoquiServiceFactory acarisServiceFactory;
	
	//private String pdFile;
	
	private RepositoryServicePort getRepositoryService(boolean forceLoading) throws Exception{
		String method = "getRepositoryService";
		log.debug(method + ". BEGIN");
		try{
			if(repositoryService == null) {
				repositoryService = acarisServiceFactory.getAcarisService().getRepositoryServicePort();
			}
			log.info(method + ". AcarisRepositoryServiceInterface loaded correctly");	
		}
		catch(Exception e){
			log.error(method + ". Exception loading interface " + e);
			throw e;
		}
		return repositoryService;
	}
		
	private RepositoryServicePort getRepositoryService() throws Exception{
		return getRepositoryService(false);
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
			getRepositoryService(true);
		}
		catch(Exception e)
		{
			log.error(method + ". Exception " + e);
			throw new RuntimeException();
		}
		
	}

	public ObjectIdType recuperaIdRepository(String repositoryName) throws IntegrationException {
		
		String method = "recuperaIDRepository";
		ObjectIdType repositoryId = null;
		
		AcarisRepositoryEntryType[] elencoRepository = null;
		try {
			elencoRepository = getRepositoryService().getRepositories();
			for (int i = 0; i < elencoRepository.length; i++) {
				AcarisRepositoryEntryType repository = elencoRepository[i];
				if (repository.getRepositoryName().startsWith(repositoryName, 0)) {
					repositoryId = repository.getRepositoryId();
					break;
				}
			}
			if(repositoryId == null){
				throw new IntegrationException("Impossibile recuperare repositoryId");
			}	
			if(log.isDebugEnabled()){
				log.debug(method + ". repositoryId = " + repositoryId.getValue());
			}
		} 
		catch (it.doqui.acta.actasrv.util.acaris.wrapper.exception.AcarisException acEx) {			
			this.logAcarisException("AcarisObjectServiceImpl", method, acEx.getMessage(), acEx.getFaultInfo());
			throw new IntegrationException("Impossibile recuperare repositoryId: " + acEx.getMessage(), acEx);
		} 
		catch (Exception e) {
			log.error(method + ". Exception = " + e.getMessage());
			throw new IntegrationException("Si e' verificato un errore in fase di recupero repositoryId: " +  e.getMessage(), e);
		}
		return repositoryId;
	}

	

	

}

