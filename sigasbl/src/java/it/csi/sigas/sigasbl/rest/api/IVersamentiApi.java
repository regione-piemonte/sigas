/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.sigas.sigasbl.request.home.AllarmeRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoContabiliaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoRequest;
import it.csi.sigas.sigasbl.request.home.DownloadVersamentiReport;
import it.csi.sigas.sigasbl.request.home.RicercaVersamentiRequest;

@Path("/versamenti")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IVersamentiApi {

   
    
    /*Versamenti per soggetto*/
	@GET
    @Path("/annualitaVersamentiPerRicerca")
    Response annualitaVersamentiPerRicerca(@Valid @QueryParam("id") @NotNull(message = "L'id non deve essere null") Long id);
	
    @GET
    @Path("/ricercaAnnualitaVersamenti")
    Response ricercaAnnualitaVersamenti(@Valid @QueryParam("id") @NotNull(message = "L'id non deve essere null") Long id);
    
    @GET
    @Path("/ricercaProvinceVersamenti")
    Response ricercaProvinceVersamenti(@Valid @QueryParam("id") @NotNull(message = "L'id non deve essere null") Long id);
        
    @GET
    @Path("/ricercaTipoVersamenti")
    Response ricercaTipoVersamenti();
    
    @GET
    @Path("/ricercaMeseVersamenti")
    Response ricercaMeseVersamenti(@Valid @QueryParam("id") @NotNull(message = "L'id non deve essere null") Long id,
    		@Valid @QueryParam("annualita") @NotNull(message = "annualita non deve essere null") String annualita);
    
    @POST
    @Path("/ricercaVersamenti")
    Response ricercaVersamenti(RicercaVersamentiRequest ricercaVersamentiRequest);
    
    @GET
    @Path("/ricercaAllarmi")
    Response ricercaAllarmi(@Valid @QueryParam("id") @NotNull(message = "L'id non deve essere null") Long id,
    @Valid @QueryParam("idTipoAllarme") @NotNull(message = "tipo non deve essere null") Long annualita);
    
    @GET
    @Path("/ricercaAllarmiVersamento")
    Response ricercaAllarmiVersamento(@Valid @QueryParam("id") @NotNull(message = "L'id non deve essere null") Long id);

    @POST
    @Path("/allarmeSoggetto")
	Response allarmeSoggetto(AllarmeRequest allarmeRequest);

    @POST
    @Path("/salvaElencoVersamenti")
	Response salvaElencoVersamenti(DownloadVersamentiReport downloadVersamentReport);
    
    @POST
    @Path("/insertVersamento")
    Response insertVersamento(@Valid @NotNull(message = "ConfermaVersamentoRequest non deve essere vuota") ConfermaVersamentoRequest confermaVersamentoRequest);
    
    @POST
    @Path("/insertVersamentoContabilia")
    Response insertVersamentoContabilia(@Valid @NotNull(message = "ConfermaVersamentoContabiliaRequest non deve essere vuota") ConfermaVersamentoContabiliaRequest confermaVersamentoContabiliaRequest);
    
    @POST
    @Path("/insertElencoVersamenti")
    Response insertElencoVersamenti(@Valid @NotNull(message = "ConfermaVersamentoRequest non deve essere vuota") List<ConfermaVersamentoRequest> confermaVersamentoRequestList);
    
    @POST
    @Path("/updateVersamento")
    Response updateVersamento(@Valid @NotNull(message = "ConfermaVersamentoRequest non deve essere vuota") ConfermaVersamentoRequest confermaVersamentoRequest);
    
    @DELETE
    @Path("/delete/{idVersamento}")
    Response deleteVersamento(@Valid @PathParam("idVersamento") @NotNull(message = "idVersamento non deve essere null") Long idVersamento,
    						  @Valid @QueryParam("idAnag") @NotNull(message = "L'id anagrafica non deve essere null") Long idAnag);    
    
}
