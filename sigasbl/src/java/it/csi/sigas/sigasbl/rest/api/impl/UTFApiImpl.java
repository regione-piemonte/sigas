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

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.dispatcher.IUTFDispatcher;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.model.vo.home.ConsumiPrVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.AnnualitaImportVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.ImportUTFVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.UTFStandaloneEntitySoggettiMacroReportVO;
import it.csi.sigas.sigasbl.request.utf.UTFConfermaDichiarazioneRequest;
import it.csi.sigas.sigasbl.request.utf.UTFConfermaSoggettoDichiarazioneRequest;
import it.csi.sigas.sigasbl.request.utf.UTFSoggettiMacroReportByIdReportRequest;
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
    public Response importUTF(MultipartFormDataInput input) {
    	
    	logger.info("START: importUTF");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
    	
    	// import UTF
		ImportUTFVO importUTF = this.utfDispatcher.importUTF(input, utente.getUsername());
		
		// Populate Consumi
		this.utfDispatcher.populateConsumi(importUTF);
    	
		
		logger.info("END: importUTF");
		return Response.ok(Esito.SUCCESS).build();
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


	@Override
	public Response getUTFReportByIdImport(Long idImport, Integer annualita) {
		logger.info("START: getUTFReportByIdImport");
	   	
    	List<ConsumiPrVO> consumiPrVOList = this.utfDispatcher.getUTFReportByIdImport(idImport, annualita);
		
    	logger.info("END: getUTFReportByIdImport");
        return Response.ok(new ResponseVO<List<ConsumiPrVO>>(Esito.SUCCESS, consumiPrVOList)).build();
	}
	
	@Override
	public Response getUTFReportDettaglioSoggettiByIdImport(Long idImport) {
		logger.info("START: getUTFReportDettaglioSoggettiByIdImport");
	   	
    	List<ConsumiPrVO> consumiPrVOList = this.utfDispatcher.getUTFReportDettaglioSoggettiByIdImport(idImport);
		
    	logger.info("END: getUTFReportDettaglioSoggettiByIdImport");
        return Response.ok(new ResponseVO<List<ConsumiPrVO>>(Esito.SUCCESS, consumiPrVOList)).build();
	}
	
	@Override
	public Response getImportUTFListByAnno(Long anno) {
		logger.info("START: getImportUTFListByAnno");
	   	
    	List<ImportUTFVO> importUTFVOList = this.utfDispatcher.getImportUTFListByAnno(anno);
		
    	logger.info("END: getImportUTFListByAnno");
        return Response.ok(new ResponseVO<List<ImportUTFVO>>(Esito.SUCCESS, importUTFVOList)).build();
	}
		
	@Override
	public Response confermaSoggettoDichiarazioniUTF(UTFConfermaSoggettoDichiarazioneRequest utfConfermaSoggettoDichiarazioneRequest) {
		logger.info("START: confermaSoggettoDichiarazioniUTF");
		
		this.utfDispatcher.confermaSoggettoDichiarazioniUTF(utfConfermaSoggettoDichiarazioneRequest);
		
		logger.info("END: confermaSoggettoDichiarazioniUTF");
		
		return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}
	
	@Override
	public Response confermaDichiarazioniUTF(UTFConfermaDichiarazioneRequest utfConfermaDichiarazioneRequest) 
	{
		logger.info("START: confermaDichiarazioniUTF");
		
		this.utfDispatcher.confermaDichiarazioniUTF(utfConfermaDichiarazioneRequest);
		
		logger.info("END: confermaDichiarazioniUTF");
		
		return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}


	@Override
	public Response getUTFSoggettiMacroReportByIdReport(UTFSoggettiMacroReportByIdReportRequest utfSoggettiMacroReportByIdReportRequest) 
	{		
		logger.info("START: getUTFSoggettiMacroReportByIdReport");
		
		List<UTFStandaloneEntitySoggettiMacroReportVO> utfStandaloneEntitySoggettiMacroReportVOList = this.utfDispatcher
																										  .getUTFSoggettiMacroReportByIdReport(utfSoggettiMacroReportByIdReportRequest);
		
		logger.info("END: getUTFSoggettiMacroReportByIdReport");
		
		return Response.ok(new ResponseVO<List<UTFStandaloneEntitySoggettiMacroReportVO>>(Esito.SUCCESS, utfStandaloneEntitySoggettiMacroReportVOList)).build();
	}


	@Override
	public Response getUTFReportDettaglioSoggettiByIdImportIdAnag(Long idImport, Long idAnag) {
		logger.info("START: getUTFReportDettaglioSoggettiByIdImportIdAnag");
	   	
    	List<ConsumiPrVO> consumiPrVOList = this.utfDispatcher.getUTFReportDettaglioSoggettiByIdImportIdAnag(idImport, idAnag);
		
    	logger.info("END: getUTFReportDettaglioSoggettiByIdImportIdAnag");
        return Response.ok(new ResponseVO<List<ConsumiPrVO>>(Esito.SUCCESS, consumiPrVOList)).build();
	}	
	
        
}
