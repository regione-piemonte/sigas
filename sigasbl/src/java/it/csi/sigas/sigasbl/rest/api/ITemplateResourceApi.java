/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.spi.validation.ValidateRequest;

import it.csi.sigas.sigasbl.request.template.DatiTemplateRequest;



@Path("template")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public interface ITemplateResourceApi{


	@POST
	@Path("/getDatiTemplate")
	public Response getDatiTemplate(@Valid @NotNull(message = "DatiTemplateRequest non deve essere vuota")  DatiTemplateRequest request);

	@POST
	@Path("/stampaTemplate")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response stampaTemplate(@Valid @NotNull(message = "DatiTemplateRequest non deve essere vuota")  DatiTemplateRequest request);
//	@POST
//	@Path("/downloadTemplate")
//	public Response downloadTemplate(@Valid @NotNull(message = "RESCON19") DatiTemplateRequest request);

	@POST
	@Path("/nomiTemplate")
	public Response nomiTemplate(@Valid @NotNull(message = "DatiTemplateRequest non deve essere vuota") DatiTemplateRequest request);

}
