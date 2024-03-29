/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/luoghi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ILuoghiApi {

	@GET
	@Path("/province")
	public Response getAllProvince();

	@GET
	@Path("/comuniById")
	public Response getComuniByIdProvincia(@QueryParam("idProvincia") Long idProvincia) ;


	@GET
	@Path("/provinciaBySigla")
	public Response provinciaBySigla(@QueryParam("siglaProvincia") String siglaProvincia) ;
	

	@GET
	@Path("/comuni")
	public Response getAllComuni();


	@GET
	@Path("/ricercaIndirizzo")
	public Response ricercaIndirizzo(@QueryParam("indirizzo") String indirizzo, @QueryParam("id") Long id);
	
	
}
