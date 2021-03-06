/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/UTF")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IUTFApi {

    @POST
    @Path("/importazioneUTF")
    @Consumes("multipart/form-data")
    void importUTF(MultipartFormDataInput input);
    
    @POST
    @Path("/popolamentoConsumi")
    void populateConsumi(String idImportUtf, String anno);
    
    @GET
    @Path("/ricercaAnnualita")
    Response ricercaAnnualita();

 }
