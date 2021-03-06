/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api;

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

import it.csi.sigas.sigasbl.request.home.ConfermaAliquotaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaTassoRequest;


@Path("/impostazioni")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IImpostazioniApi {

	@GET
	@Path("/tassi")
	public Response getAllTassi();

	@GET
	@Path("/aliquote")
	public Response getAllAliquote();

	@GET
	@Path("/tipiAliquoteByTipo")
	public Response getAllTipiAliquoteByTipo(@QueryParam("tipo") @NotNull(message = "il tipo non deve essere null") String tipo);

	@GET
	@Path("/tipiAliquoteByDescrizione")
	public Response getTipoAliquoteByDescrizione(@Valid @QueryParam("id") @NotNull(message = "id non deve essere null") Long id);

    @POST
    @Path("/insertAliquota")
    Response insertAliquota(@Valid @NotNull(message = "ConfermaAliquota non deve essere vuota") ConfermaAliquotaRequest confermaAliquotaRequest);

    @POST
    @Path("/updateAliquota")
    Response updateAliquota(@Valid @NotNull(message = "ConfermaAliquota non deve essere vuota") ConfermaAliquotaRequest confermaAliquotaRequest);

	@POST
	@Path("/eliminaAliquota/{id}")
	public Response eliminaAliquota(@Valid @PathParam("id") @NotNull(message = "Id non deve essere null") Long id);

	
	@GET
	@Path("/tipoTassi")
	public Response getAllTipoTassi();
	
	@POST
	@Path("/eliminaTasso/{idTasso}")
	public Response eliminaTasso(@Valid @PathParam("idTasso") @NotNull(message = "Id Tasso non deve essere vuoto") Long idTasso);
	
	@POST
	@Path("/aggiornaTasso")
	public Response aggiornaTasso(@Valid @NotNull(message = "La request non deve essere vuota") ConfermaTassoRequest confermaTasso);
	
	@POST
	@Path("/aggiungiTasso")
	public Response aggiungiTasso(@Valid @NotNull(message = "La request non deve essere vuota") ConfermaTassoRequest confermaTasso);

	@GET
	@Path("/tassiByRimborso")
	Response tassiByRimborso();
	
	@GET
	@Path("/tassiByAccertamenti")
	Response tassiByAccertamenti();
	
	
	
}
