package it.csi.sigas.sigasbl.rest.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.sigas.sigasbl.model.vo.home.RateoVO;
import it.csi.sigas.sigasbl.request.home.RicercaRateoRequest;

@Path("/rateo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IRateoApi {
	
	 @GET
	 @Path("/{id}")
	 Response ricercaRateo(@Valid @PathParam("id") @NotNull(message = "L'id non deve essere null") Long id);
	 
	 @POST
	 @Path("/ricerca-rateo")
	 Response ricercaRateoByParams(@Valid @NotNull(message = "RicercaRateoRequest non deve essere vuota") RicercaRateoRequest ricercaRateoRequest);
	 
	 @PUT
	 @Path("/inserisci")
	 Response inserisciRateo(@Valid @NotNull(message = "RateoVO non deve essere vuota") RateoVO rateoVO);
	 
	 @POST
	 @Path("/modifica")
	 Response modificaRateo(@Valid @NotNull(message = "RateoVO non deve essere vuota") RateoVO rateoVO);
	 
	 @DELETE
	 @Path("/cancella/{id}")
	 Response cancellaRateo(@Valid @PathParam("id") @NotNull(message = "L'id non deve essere null") Long id);

}
