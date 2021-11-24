/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api;

import java.io.IOException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.rpc.ServiceException;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneBoRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneRequest;



@Path("documentazione")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface IDocumentazioneApi {
	
	
	// GESTIONE ACCREDITAMENTO BO
	
	@GET
	@Path("/listaStatoDocumenti")
	public Response listaStatoDocumenti();
	
	
	@GET
	@Path("/ricercaAziendeDocumentiInoltrati")
	public Response ricercaAziendeDocumentiInoltrati();
	
	@POST
	@Path("/ricercaDocumentiBO")
	public Response ricercaDocumentiBO(@Valid @NotNull(message = "RicercaDocumentazioneBoRequest non deve essere vuota") RicercaDocumentazioneBoRequest ricercaDocumentazioneBoRequest) ;
	
	@POST
	@Path("/salvaDocumentazioneBO")
	@Consumes("multipart/form-data")
	public Response salvaDocumentazioneBO(MultipartFormDataInput input)  throws IOException;
	
	
	
	@POST
	@Path("/protocollaLetteraRisp")
	@Consumes("multipart/form-data")
	public Response protocollaLetteraRisp(MultipartFormDataInput input)  throws IOException;
	

	@GET
	@Path("/ricercaLetteraRisposta")
	public Response ricercaLetteraRisposta(@QueryParam("rifArchivio") String rifArchivio) ;


	
	//GESTIONE DOCUMENTAZIONE FO
	@POST
	@Path("/ricercaDocumenti")
	public Response ricercaDocumenti(@Valid @NotNull(message = "RicercaDocumentazioneRequest non deve essere vuota") RicercaDocumentazioneRequest ricercaDocumentazioneRequest) ;
	
	@GET
	@Path("/ricercaAziendeAccreditato")
	public Response ricercaAziendeAccreditato();
	
	@GET
	@Path("/listaTipoDocumenti")
	public Response listaTipoDocumenti();
	
	@POST
	@Path("/salvaDocumentazione")
    @Consumes("multipart/form-data")
	public Response salvaDocumentazione(MultipartFormDataInput input) throws IOException, BusinessException, ServiceException ;


	@GET
	@Path("/getPacchettoDocumenti/{idDocumento}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	Response getPacchettoDocumenti(@Valid @PathParam("idDocumento") @NotNull(message = "idDocumento non deve essere null") String idDocumento);


	@GET
	@Path("/getDocumentoMaster/{idDocumento}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	Response getDocumentoMaster(@Valid @PathParam("idDocumento") @NotNull(message = "idDocumento non deve essere null") String idDocumento);


	@GET
	@Path("/getAllegato/{idDocumento}/{nomeFileAllegato}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	Response getAllegato(@Valid @PathParam("idDocumento") @NotNull(message = "idDocumento non deve essere null") String idDocumento,
			@Valid @PathParam("nomeFileAllegato") @NotNull(message = "nomeFileAllegato non deve essere null") String nomeFileAllegato);

	



}
