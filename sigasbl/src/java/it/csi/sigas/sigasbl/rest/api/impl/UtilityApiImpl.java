/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api.impl;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.dispatcher.IUtilityDispatcher;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.model.vo.home.UtilityCtrlVO;
import it.csi.sigas.sigasbl.rest.api.IUtilityApi;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;

@Service
public class UtilityApiImpl extends SpringSupportedResource implements IUtilityApi{

	@Autowired
	private IUtilityDispatcher utilityDispatcher;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
  

    public UtilityApiImpl() {
    }


	@Override
	public Response retriveMessageAndParameters(String descParametro, String descChiaveMessaggio) {
		logger.info("START: retriveMessageAndParameters");
		UtilityCtrlVO utilityCtrlVO = this.utilityDispatcher.retriveMessageAndParameters(descParametro, descChiaveMessaggio); 
    	
    	logger.info("END: retriveMessageAndParameters");
        return Response.ok(new ResponseVO<UtilityCtrlVO>(Esito.SUCCESS, utilityCtrlVO)).build();
	}
    
    

}
