/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api;

import java.io.FileNotFoundException;
import java.io.IOException;
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

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.sigas.sigasbl.model.vo.home.AnaComunicazioniVO;
import it.csi.sigas.sigasbl.request.home.AllarmeDocumentoRequest;
import it.csi.sigas.sigasbl.request.home.AssociaSoggettoRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaConsumiRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaPagamentoRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaSoggettoRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoRequest;
import it.csi.sigas.sigasbl.request.home.DownloadAccertamentiReport;
import it.csi.sigas.sigasbl.request.home.DownloadDettaglioSoggettoReport;
import it.csi.sigas.sigasbl.request.home.DownloadDocumentazioneReport;
import it.csi.sigas.sigasbl.request.home.DownloadReport;
import it.csi.sigas.sigasbl.request.home.DownloadSoggettiReport;
import it.csi.sigas.sigasbl.request.home.FusioneSoggettoRequest;
import it.csi.sigas.sigasbl.request.home.RicercaAnaComunicazioniRequest;
import it.csi.sigas.sigasbl.request.home.RicercaConsumiRequest;
import it.csi.sigas.sigasbl.request.home.RicercaOrdinativiRequest;
import it.csi.sigas.sigasbl.request.home.RicercaSoggettoIncorporatoRequest;
import it.csi.sigas.sigasbl.request.home.RicercaStoricoSoggettiRequest;
import it.csi.sigas.sigasbl.request.home.SalvaCompensazioneRequest;
import it.csi.sigas.sigasbl.request.home.SalvaRimborsoRequest;
import it.csi.sigas.sigasbl.request.home.UpdateAllarmeAccertamentoRequest;
import it.csi.sigas.sigasbl.request.home.ValidazioneRequest;

@Path("/home")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IHomeApi {

    @GET
    @Path("/ricercaAnnualita")
    Response ricercaAnnualita();

    @GET
    @Path("/ricercaAnnualitaPagamenti")
    Response ricercaAnnualitaPagamenti();

    @POST
    @Path("/ricercaConsumi")
    Response ricercaConsumi(RicercaConsumiRequest ricercaConsumiRequest);
    
    
    @POST
    @Path("/ricercaOrdinativi")
    Response ricercaOrdinativi(RicercaOrdinativiRequest ricercaOrdinativiRequest);
    
    @POST
    @Path("/conciliaPagamento")
    Response conciliaPagamento(ConfermaPagamentoRequest confermaPagamentoRequest);


    @POST
    @Path("/ricercaListaSoggetti")
    Response ricercaListaSoggetti(RicercaConsumiRequest ricercaConsumiRequest);

    @GET
    @Path("/ricercaSoggettoByID")
    Response ricercaSoggettoByID(@Valid @QueryParam("id") @NotNull(message = "id non deve essere null") Long id);
    
    @GET
    @Path("/ricercaSoggettoByCode")
    Response ricercaSoggettoByCode(@Valid @QueryParam("codice") @NotNull(message = "Codice non deve essere null") String codice);

    @GET
    @Path("/ricercaSoggetti")
	Response ricercaSoggetti();
    
    @GET
    @Path("/ricercaConsumiPerProvince")
	Response ricercaConsumiPerProvince(@Valid @QueryParam("id") @NotNull(message = "L'id non deve essere null") Long id,
			@QueryParam("anno") @NotNull(message = "il codice ditta non deve essere null") String anno);
    
    @GET
    @Path("/ricercaVersamentiPerProvince")
	Response ricercaVersamentiPerProvince(@Valid @QueryParam("id") @NotNull(message = "L'id non deve essere null") Long id,
			@QueryParam("anno") @NotNull(message = "il codice ditta non deve essere null") String anno);

    @POST
    @Path("/insertSoggetto")
    Response insertSoggetto(@Valid @NotNull(message = "ConfermaSoggetto non deve essere vuota") ConfermaSoggettoRequest confermaSoggettoRequest);
    
    @POST
    @Path("/updateSoggetto")
    Response updateSoggetto(@Valid @NotNull(message = "ConfermaSoggetto non deve essere vuota") ConfermaSoggettoRequest confermaSoggettoRequest);

    @POST
    @Path("/associateSoggetto")
    Response associateSoggetto(@Valid @NotNull(message = "AssociaSoggetto non deve essere vuota") AssociaSoggettoRequest associaSoggettoRequest);

    @POST
    @Path("/fusioneSoggetto")
    Response fusioneSoggetto(@Valid @NotNull(message = "FusioneSoggetto non deve essere vuota") FusioneSoggettoRequest fusioneSoggettoRequest);
    
    @POST
    @Path("/cancella-fusione-soggetto/{id}")
    Response cancellaFusioneSoggetto(@Valid @PathParam("id") @NotNull(message = "id anagrafica soggetto incorporante non deve essere null") Long idAnagraficaSoggettoIncorporante);
    
    
    @POST
    @Path("/salvaConsumiPerProvince")
    Response salvaConsumiPerProvince(DownloadReport downloadReport);
    
    @POST
    @Path("/salvaSoggetto")
    //Response salvaSoggetto(DownloadReport downloadReport);
    Response salvaSoggetto(DownloadDettaglioSoggettoReport downloadReport);    

    @POST
    @Path("/salvaElencoSoggetti")
    Response salvaElencoSoggetti(DownloadSoggettiReport downloadSoggettiReport);
    
    @GET
    @Path("/ricercaScartiByIdConsumi")
    Response ricercaScartiByIdConsumi(@Valid @QueryParam("id") @NotNull(message = "id non deve essere null") Long idConsumi);
    
    @GET
    @Path("/controlloImportiConsumi")
    Response controlloImportiConsumi(@Valid @QueryParam("id") @NotNull(message = "id non deve essere null") Long idConsumi);

    @GET
    @Path("/getAllAliquote")
    Response getAllAliquote(@Valid @QueryParam("annoDichiarazione") @NotNull(message = "annoDichiarazione non deve essere null") Integer annoDichiarazione);

    @POST
    @Path("/updateConsumi")
    Response updateConsumi(@Valid @NotNull(message = "ConfermaConsumi non deve essere vuota") ConfermaConsumiRequest confermaConsumiRequest);
    
    @POST
    @Path("/updateTotaleDichConsumi")
    Response updateTotaleDichConsumi(@Valid @NotNull(message = "ConfermaConsumi non deve essere vuota") ConfermaConsumiRequest confermaConsumiRequest);

    @POST
    @Path("/updateCompensazioneConsumi")
	Response updateCompensazioneConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user);
    
    @GET
    @Path("/ricercaListaNuoviSoggetti")
	Response ricercaListaNuoviSoggetti();

    @GET
    @Path("/numberOfSoggetti")
	Response getNumberOfSoggetti();

    @GET
    @Path("/storicoConsumi")
	Response storicoConsumi(@Valid @QueryParam("id") @NotNull(message = "id non deve essere null") Long idConsumi);

    @GET
    @Path("/ripristinaModificaConsumi")
	Response ripristinaModificaConsumi(@Valid @QueryParam("id") @NotNull(message = "id non deve essere null") Long idConsumoo,
									   @Valid @QueryParam("idStorico") @NotNull(message = "id storico non deve essere null") Long idConsumoStorico);

    @POST
    @Path("/validaSoggetto")
	Response validaSoggetto(ValidazioneRequest validazioneRequest);
    
    @GET
    @Path("/ricercaConsumiPerProvinceAndAnnualita")
	Response ricercaConsumiPerProvinceAndAnnualita(@Valid @QueryParam("id") @NotNull(message = "L'id non deve essere null") Long id,
												   @QueryParam("anno") @NotNull(message = "anno non deve essere null") String anno,
												   @QueryParam("provincia") @NotNull(message = "anno non deve essere null") String prov);
    
    @GET
    @Path("/ricercaAllarmiByIdDocumentoAndCodiceAzienda")
    Response ricercaAllarmiByIdDocumentoAndCodiceAzienda(@Valid @QueryParam("idDocumento") @NotNull(message = "L'idDocumento non deve essere null") Long idDocumento,
    													 @Valid @QueryParam("codiceAzienda") @NotNull(message = "codiceAzienda non deve essere null") String codiceAzienda);

    @GET
    @Path("/ricercaListaRimborsi")
	Response ricercaListaRimborsi(@Valid @QueryParam("idAnag") @NotNull(message = "L'id non deve essere null") Long idAnag);
    
    @POST
    @Path("/ricercaDocumentiByAnnoAndTipologia")
    Response ricercaDocumentiByAnnoAndTipologia(RicercaAnaComunicazioniRequest ricercaAnaComunicazioniRequest);

    @GET
    @Path("/ricercaDocumentiByIdAnag")
    Response ricercaDocumentiByIdAnag(@Valid @QueryParam("id") @NotNull(message = "id non deve essere null") Long idAnag);

    @POST
    @Path("/allarmeDocumento")
	Response allarmeDocumento(AllarmeDocumentoRequest allarmeDocumentoRequest);
    
    @POST
    @Path("/salvaAllegatoVerbale")
    @Consumes("multipart/form-data")
    Response salvaAllegato(MultipartFormDataInput input) throws IOException; 

    @POST
    @Path("/aggiornaAllegatoVerbale")
    @Consumes("multipart/form-data")
    Response aggiornaAllegato(MultipartFormDataInput input) throws IOException;
    
    @POST
    @Path("/aggiornaTestaAllegato")
    Response aggiornaTestataAllegato(AnaComunicazioniVO anaComunicazione);

    @GET
	@Path("/stampaDocumento/{descComunicazione}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response stampaDocumento(@Valid @PathParam("descComunicazione") @NotNull(message = "descComunicazione non deve essere null") String descComunicazione) throws FileNotFoundException, IOException;
    
    @GET
	@Path("/stampaDocumentoAllegato/{descComunicazione}/{descAllegato}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response stampaDocumentoAllegato(@Valid @PathParam("descComunicazione") @NotNull(message = "descComunicazione non deve essere null") String descComunicazione,
											@Valid @PathParam("descAllegato") @NotNull(message = "descAllegato non deve essere null") String descAllegato) throws FileNotFoundException, IOException;

    @GET
    @Path("/getAllTipoComunicazioni")
    Response getAllTipoComunicazioni();
    
    @GET
    @Path("/ricercaTipoComunicazioniByIdTipoComunicazione")
    Response ricercaTipoComunicazioniByIdTipoComunicazione(@Valid @QueryParam("id") @NotNull(message = "id non deve essere null") Long idTipoComunicazione);

    // ACCERTAMENTI 
    @GET
    @Path("/elencoAccertamentiByIdAnag")
    Response getListaAccertamentiAnagrafica(@Valid @QueryParam("idAnag") @NotNull(message="id non deve essere null") Long idAnag,
								    		@Valid @QueryParam("anno") @NotNull(message="anno non deve essere null") String anno,
								    		@Valid @QueryParam("provincia") @NotNull(message="provincia non deve essere null") String provincia);
    
    @POST
    @Path("/updateAccertamenti")
    Response updateAccertamenti(@Valid @NotNull(message = "ConfermaVersamentoRequest non deve essere vuota") List<ConfermaVersamentoRequest> accertamentiDaSalvare);
    
    @POST
    @Path("/updateAllarmeAccertamento")
    Response updateAllarmeAccertamento(@Valid @NotNull(message = "UpdateAllarmeAccertamentoRequest non deve essere vuota") 
    								   UpdateAllarmeAccertamentoRequest UpdateAllarmeAccertamentoRequest);

    @POST
    @Path("/salvaRimborso")
    Response salvaRimborso(@Valid @NotNull(message = "SalvaRimborsoRequest non deve essere vuota") SalvaRimborsoRequest salvaRimborsoRequest);

    @POST
    @Path("/salvaDetermina")
	Response salvaDetermina(DownloadReport downloadReport);

    @POST
    @Path("/salvaElencoDocumenti")
	Response salvaElencoDocumenti(DownloadDocumentazioneReport downloadDocumentazioneReport);

    @POST
    @Path("/salvaElencoAccertamenti")
    Response salvaElencoAccertamenti(DownloadAccertamentiReport downloadAccertamentiReport);
    
    @POST
    @Path("/eliminaConciliazione")
    Response eliminaConciliazione(ConfermaPagamentoRequest confermaPagamentoRequest);
    
    @GET
	@Path("/stampaDocumentoMaster/{descComunicazione}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response stampaDocumentoMaster(@Valid @PathParam("descComunicazione") @NotNull(message = "descComunicazione non deve essere null") String descComunicazione) throws FileNotFoundException, IOException;
    
    @DELETE
    @Path("/delete/{idDocumento}")
    Response deleteDocumento(@Valid @PathParam("idDocumento") @NotNull(message = "idDocumento non deve essere null") Long idDocumento);
    
    @POST
    @Path("/salvaCompensazione")
    Response salvaCompensazione(@Valid @NotNull(message = "SalvaCompensazione non deve essere vuota") SalvaCompensazioneRequest salvaCompensazioneRequest);
    
    @GET
    @Path("/ricerca-lista-storico-soggetto")
    Response ricercaListaStoricoSoggetto(@Valid @QueryParam("id") @NotNull(message = "id non deve essere null") Long id);
    
    @POST
    @Path("/ricerca-lista-storico-soggetti")
    Response ricercaListaStoricoSoggetti(@Valid @NotNull(message = "RicercaStoricoSoggettiRequest non deve essere vuota") RicercaStoricoSoggettiRequest ricercaStoricoSoggettiRequest);
    
    @POST
    @Path("/ricerca-soggetto-incorporato")
    Response ricercaSoggettoIncorporato(@Valid @NotNull(message = "RicercaSoggettoIncorporatoRequest non deve essere vuota") RicercaSoggettoIncorporatoRequest ricercaSoggettoIncorporatoRequest);

}
