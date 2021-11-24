/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.csi.sigas.sigasbl.request.accreditamento.AnnullaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaDichiaranteRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaPraticheAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.VerificaEsistenzaLegaleRappresentanteRequest;



@Path("accreditamento")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface IAccreditamentoApi {
	
	
	// GESTIONE ACCREDITAMENTO BO
	@POST
	@Path("/ricercaPratiche")
	public Response ricercaPratiche(@Valid @NotNull(message = "RicercaPraticheAccreditamentoRequest non deve essere vuota") RicercaPraticheAccreditamentoRequest ricercaPraticheAccreditamentoRequest) ;
	
	
	@POST
	@Path("/ricercaPraticaAccreditamento")
	public Response ricercaPraticaAccreditamento(@Valid @NotNull(message = "UtenteProvvisorioRequest non deve essere vuota") RicercaAccreditamentoRequest ricercaAccreditamentoRequest) ;


	@POST
	@Path("/confermaPraticaAccreditamento")
	public Response confermaPraticaAccreditamento(@Valid @NotNull(message = "ConfermaPraticaAccreditamentoRequest non deve essere vuota") ConfermaPraticaAccreditamentoRequest confermaPraticaAccreditamentoRequest) ;
	
	
	
	// INSERIMENTO
	@POST
	@Path("/ricercaDichiarante")
	public Response ricercaDichiarante(@Valid @NotNull(message = "RicercaDichiaranteRequest non deve essere vuota") RicercaDichiaranteRequest ricercaDichiaranteRequest);

	@GET
	@Path("/recuperaLegaleRappresentanteByCfdichiaranteAndPi")
	public Response recuperaLegaleRappresentanteByCfdichiaranteAndPi(@Valid @QueryParam("cfDichiarante") @Size(message = "codiceFiscale deve essere lungo 16", min = 0, max = 16) String cfDichiarante,
			@QueryParam("piva") @Size(message = "partita iva deve essere lunga 11", min = 0, max = 11) String piva);
	
	@POST
	@Path("/verificaEsistenzaLegaleRappresentante")
	public Response verificaEsistenzaLegaleRappresentante(@Valid @NotNull(message = "request non deve essere null") VerificaEsistenzaLegaleRappresentanteRequest request);
	

	@POST
	@Path("/confermaInserimentoAccreditamento")
	public Response confermaAccreditamento(@Valid @NotNull(message = "ConfermaAccreditamento non deve essere null") ConfermaAccreditamentoRequest confermaAccreditamento);


	@POST
	@Path("/confermaAggiornamentoAccreditamento")
	public Response confermaAggiornamentoAccreditamento(@Valid @NotNull(message = "ConfermaAccreditamento non deve essere vuota") ConfermaAccreditamentoRequest confermaAccreditamento) ;

	
	@GET
	@Path("/ricercaPraticheFO")
	public Response ricercaPraticheFO() ;
	
	@POST
	@Path("/cambiaStatoNoteAccreditamento")
	public Response cambiaStatoNoteAccreditamento(@Valid @NotNull(message = "AnnullaPraticaAccreditamentoRequest non deve essere vuota") AnnullaPraticaAccreditamentoRequest annullaPraticaAccreditamentoRequest) ;
	

	
	@GET
	@Path("/verificaDichiarante")
	public Response verificaDichiarante(@QueryParam("codiceAzienda") String codiceAzienda, @Valid @QueryParam("codiceFiscaleOperatore") @Size(message = "codiceFiscale deve essere lungo 16", min = 0, max = 16) String  codiceFiscaleOperatore) ;



}
