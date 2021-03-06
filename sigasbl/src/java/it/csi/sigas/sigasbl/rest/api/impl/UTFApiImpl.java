/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.dispatcher.IUTFDispatcher;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.AnnualitaImportVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.ImportUTFVO;
import it.csi.sigas.sigasbl.rest.api.IUTFApi;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;

@Service
public class UTFApiImpl extends SpringSupportedResource implements IUTFApi {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private IUTFDispatcher utfDispatcher;

    public UTFApiImpl() {
    }


    @Override
    public void importUTF(MultipartFormDataInput input) {
    	
    	logger.info("START: importUTF");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
    	
    	// import UTF
		ImportUTFVO importUTF = this.utfDispatcher.importUTF(input, utente.getUsername());
		
		// Populate Consumi
		this.utfDispatcher.populateConsumi(importUTF);
    	
		
		logger.info("END: importUTF");
        //return Response.ok(new ResponseVO<ImportUTFVO>(Esito.SUCCESS, importUTF)).build();
    }

    @Override
    public Response ricercaAnnualita() {
    	
    	logger.info("START: ricercaAnnualita");
   	
    	List<AnnualitaImportVO> annualitaImportVOList = this.utfDispatcher.ricercaAnnualita();
		
    	logger.info("END: ricercaAnnualita");
        return Response.ok(new ResponseVO<List<AnnualitaImportVO>>(Esito.SUCCESS, annualitaImportVOList)).build();
    }


	@Override
	public void populateConsumi(String idImportUtf, String anno) {

    	logger.info("START: populateConsumi");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
    	
    	// import UTF
//		ImportUTFVO importUTF = this.utfDispatcher.importUTF(input, utente.getUsername());
		
		ImportUTFVO importUTF = new ImportUTFVO();
		importUTF.setImportId(Long.parseLong("15"));
		importUTF.setAnno("2018");
		// Populate Consumi
		this.utfDispatcher.populateConsumi(importUTF);
    	
		
		logger.info("END: populateConsumi");
	}
        
}
