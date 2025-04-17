/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.sigas.sigasbl.model.vo.home.ConsumiPrVO;
import it.csi.sigas.sigasbl.request.utf.UTFConfermaDichiarazioneRequest;
import it.csi.sigas.sigasbl.request.utf.UTFConfermaSoggettoDichiarazioneRequest;
import it.csi.sigas.sigasbl.request.utf.UTFSoggettiMacroReportByIdReportRequest;

@Path("/UTF")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IUTFApi {

    @POST
    @Path("/importazioneUTF")
    @Consumes("multipart/form-data")
    Response importUTF(MultipartFormDataInput input);
    
    @POST
    @Path("/popolamentoConsumi")
    void populateConsumi(String idImportUtf, String anno);
    
    @GET
    @Path("/ricercaAnnualita")
    Response ricercaAnnualita();
        
    
    @GET
    @Path("/utf-report/{idImport}/{annualita}")
    Response getUTFReportByIdImport(@Valid @PathParam("idImport") @NotNull(message = "idImport non deve essere null") Long idImport,
    								@Valid @PathParam("annualita") @NotNull(message = "annualita non deve essere null") Integer annualita);
        
    @GET
    @Path("/utf-report/{idImport}/dettaglio-soggetti")
    Response getUTFReportDettaglioSoggettiByIdImport(@Valid @PathParam("idImport") @NotNull(message = "idImport non deve essere null") Long idImport);
    
    @GET
    @Path("/utf-report/{idImport}/dettaglio-soggetti/{idAnag}")
    Response getUTFReportDettaglioSoggettiByIdImportIdAnag(@Valid @PathParam("idImport") @NotNull(message = "idImport non deve essere null") Long idImport,
    												 	   @Valid @PathParam("idAnag") @NotNull(message = "idAnag non deve essere null") Long idAnag);
    
    @POST
    @Path("/utf-report/dettaglio-soggetti-macro")
    Response getUTFSoggettiMacroReportByIdReport(@Valid @NotNull(message = "UTFSoggettiMacroReportByIdReportRequest non deve essere null") UTFSoggettiMacroReportByIdReportRequest utfSoggettiMacroReportByIdReportRequest);
    
    @GET
    @Path("/elenco/{anno}")
    Response getImportUTFListByAnno(@Valid @PathParam("anno") @NotNull(message = "anno non deve essere null") Long anno);
    
    @POST
    @Path("/conferma-soggetto-dichiarazioni")
    Response confermaSoggettoDichiarazioniUTF(@Valid @NotNull(message = "UTFConfermaSoggettoDichiarazioneRequest non deve essere vuota") 
    					   				  	  UTFConfermaSoggettoDichiarazioneRequest utfConfermaSoggettoDichiarazioneRequest);
    
    @POST
    @Path("/conferma-dichiarazioni")
    Response confermaDichiarazioniUTF(@Valid @NotNull(message = "UTFConfermaDichiarazioneRequest non deve essere vuota") 
    								  UTFConfermaDichiarazioneRequest utfConfermaDichiarazioneRequest);

 }
