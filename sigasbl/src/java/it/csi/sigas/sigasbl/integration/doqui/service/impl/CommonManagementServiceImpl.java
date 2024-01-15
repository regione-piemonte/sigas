/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.service.impl;

import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.integration.doqui.utils.DateFormat;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

import java.util.Date;

import it.doqui.acta.actasrv.util.acaris.wrapper.exception.AcarisException;
import it.doqui.acta.actasrv.dto.acaris.type.common.AcarisFaultType;

public class CommonManagementServiceImpl {

    private static final Logger log = Logger.getLogger(CommonManagementServiceImpl.class);
    private static final int CACHE_TIME = 60 * 60 * 1000; //[MS]  1 ora sola di cache

    private Date lastCacheTime = null;
    
    private String pdFile;

    public Date getLastCacheTime() {
        return lastCacheTime;
    }

    public void setLastCacheTime(Date lastCacheTime) {
        this.lastCacheTime = lastCacheTime;
    }

    public boolean isCacheTimeExpired() {
        String method = "isCacheTimeExpired";
        boolean expired = false;
        Date now = new Date(System.currentTimeMillis());
        if (log.isDebugEnabled()) {
            log.debug(method + ". now           = " + DateFormat.format(now, DateFormat.DATE_FORMAT_DDMMYY_HHMMSS));
            log.debug(method + ". lastCacheTime = " + DateFormat.format(lastCacheTime.getTime(), DateFormat.DATE_FORMAT_DDMMYY_HHMMSS));
            log.debug(method + ". CACHE_TIME    = " + CACHE_TIME + " [ms]");
        }

        if (now.getTime() > lastCacheTime.getTime() + CACHE_TIME) {
            log.debug(method + ". cache time expired: refreshing cache");
            expired = true;
            lastCacheTime = new Date(System.currentTimeMillis());
        } else {
            log.debug(method + ". cache time still valid");
        }

        return expired;
    }

    public void init() {
        String method = "init";
        try {
            setLastCacheTime(new Date(System.currentTimeMillis()));
            if (log.isDebugEnabled()) {
                log.debug(method + ". lastCacheTime            =  " + DateFormatUtils.format(getLastCacheTime(), "dd/MM/yyyy HH:mm:ss"));
            }
        } catch (Exception e) {
            log.error(method + ". Exception " + e);
            throw new RuntimeException();
        }
    }
    
    protected void logAcarisException(String classOwner, String method, String msg, AcarisFaultType acarisFaultType) {
    	
    	log.error(classOwner + ":" + method + ". Impossibile recuperare recuperaVitalRecordCode: " + msg);
		log.error(classOwner + ":" + method + ". acEx.getFaultInfo().getErrorCode() =  " + acarisFaultType.getErrorCode());
		log.error(classOwner + ":" + method + ". acEx.getFaultInfo().getPropertyName() = " + acarisFaultType.getPropertyName());
		log.error(classOwner + ":" + method + ". acEx.getFaultInfo().getObjectId() = " + acarisFaultType.getObjectId());
		log.error(classOwner + ":" + method + ". acEx.getFaultInfo().getExceptionType() = " + acarisFaultType.getExceptionType());
		log.error(classOwner + ":" + method + ". acEx.getFaultInfo().getClassName() = " + acarisFaultType.getClassName());
		log.error(classOwner + ":" + method + ". acEx.getFaultInfo().getTechnicalInfo = " + acarisFaultType.getTechnicalInfo());
    }
    
    protected IntegrationException logAcarisExceptionLight(String method, AcarisException acEx) {
    	IntegrationException output = null;
    	log.error(method + ". AcarisException.getMessage() = " + acEx.getMessage());
    	
		if(acEx.getFaultInfo() != null)
		{
			this.logAcarisException("AcarisNavigationServiceImpl", method, acEx.getMessage(), acEx.getFaultInfo());				
			output = new IntegrationException("AcarisException ", acEx);
		}
		
		return output;
    }
    
    protected IntegrationException createAndLogIntegrationException(String method, Exception e) {
    	log.error(method + ". Exception" + e);
		log.error(method + ". e.getMessage() = " + e.getMessage());
    	return new IntegrationException("Exception ", e);
    }

	public String getPdFile() {
		return pdFile;
	}

	public void setPdFile(String pdFile) {
		this.pdFile = pdFile;
	} 
    
}
