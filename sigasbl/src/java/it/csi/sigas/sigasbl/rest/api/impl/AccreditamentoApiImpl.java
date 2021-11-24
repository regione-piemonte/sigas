/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api.impl;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.dispatcher.IAccreditamentoDispatcher;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.request.accreditamento.AnnullaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaDichiaranteRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaPraticheAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.VerificaEsistenzaLegaleRappresentanteRequest;
import it.csi.sigas.sigasbl.rest.api.IAccreditamentoApi;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;
import it.csi.sigas.sigasbl.vo.accreditamento.AccreditamentoVO;
import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.LegaleRappresentanteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.UtenteProvvisorioVO;

@Service
public class AccreditamentoApiImpl  extends SpringSupportedResource implements IAccreditamentoApi {

    
    @Autowired
    private IAccreditamentoDispatcher iAccreditamentoDispatcher;
    
    //GESTIONE ACCREDITAMENTO BO
	@Override
	public Response ricercaPratiche(RicercaPraticheAccreditamentoRequest ricercaPraticheAccreditamentoRequest) {
		List<UtenteProvvisorioVO> listaPratiche = iAccreditamentoDispatcher.ricercaPratiche(ricercaPraticheAccreditamentoRequest);
		return Response.ok(new ResponseVO<List<UtenteProvvisorioVO>>(Esito.SUCCESS, listaPratiche)).build();
	}


	@Override
	public Response ricercaPraticaAccreditamento(RicercaAccreditamentoRequest ricercaAccreditamentoRequest) {
		AccreditamentoVO accreditamento = iAccreditamentoDispatcher.ricercaPraticaAccreditamento(ricercaAccreditamentoRequest);
		return Response.ok(new ResponseVO<AccreditamentoVO>(Esito.SUCCESS, accreditamento)).build();
	}
	
	
	@Override
	public Response confermaPraticaAccreditamento(ConfermaPraticaAccreditamentoRequest confermaPraticaAccreditamentoRequest) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		
		iAccreditamentoDispatcher.confermaPraticaAccreditamento(confermaPraticaAccreditamentoRequest, utente.getIdentita().getCodFiscale());
		return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}
	
	
	// INSERIMENTO OPERATORE FO
		@Override
		public Response ricercaDichiarante(@Valid @NotNull(message = "RicercaDichiaranteRequest non deve essere vuota") RicercaDichiaranteRequest ricercaDichiaranteRequest) {
			List<AnagraficaSoggettoVO> listaDichiarante = iAccreditamentoDispatcher.ricercaDichiarante(ricercaDichiaranteRequest.getDenominazione()!=null ? ricercaDichiaranteRequest.getDenominazione():"", null, 
					null, ricercaDichiaranteRequest.getCodiceAzienda());
			return Response.ok(new ResponseVO<List<AnagraficaSoggettoVO>>(Esito.SUCCESS, listaDichiarante)).build();
		}

		@Override
		public Response recuperaLegaleRappresentanteByCfdichiaranteAndPi(@Valid @QueryParam("cfDichiarante") @Size(message = "codiceFiscale deve essere lungo 16", min = 0, max = 16) String cfDichiarante,
				@QueryParam("piva") @Size(message = "partita iva deve essere lunga 11", min = 0, max = 11) String piva) {
			LegaleRappresentanteVO legaleRappresentanteVO = iAccreditamentoDispatcher.recuperaLegaleRappresentanteByCfDichiarante(cfDichiarante, piva);
			return Response.ok(new ResponseVO<LegaleRappresentanteVO>(Esito.SUCCESS, legaleRappresentanteVO)).build();
		}

		@Override
		public Response verificaEsistenzaLegaleRappresentante(@Valid @NotNull(message = "request non deve essere null") VerificaEsistenzaLegaleRappresentanteRequest request) {
			iAccreditamentoDispatcher.verificaEsistenzaLegaleRappresentante(request.getCflegaleRappr(), request.getDichiarante());
			return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
		}


		@POST
		@Path("/confermaInserimentoAccreditamento")
		public Response confermaAccreditamento(@Valid @NotNull(message = "ConfermaAccreditamento non deve essere null") ConfermaAccreditamentoRequest confermaAccreditamento) {
			

			iAccreditamentoDispatcher.confermaAccreditamento(confermaAccreditamento);
			return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
		}


		@Override
		public Response confermaAggiornamentoAccreditamento(@Valid @NotNull(message = "ConfermaAccreditamento non deve essere vuota") ConfermaAccreditamentoRequest confermaAccreditamento) {
			

			iAccreditamentoDispatcher.modificaAccreditamento(confermaAccreditamento);
			return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
		}
		

		@Override
		public Response ricercaPraticheFO() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Object principal = auth.getPrincipal();
			UserDetails utente = (UserDetails) principal;
			
			RicercaDichiaranteRequest ricercaDichiaranteRequest = new RicercaDichiaranteRequest();
			ricercaDichiaranteRequest.setCodiceFiscale(utente.getIdentita().getCodFiscale());
			
			
			List<UtenteProvvisorioVO> listaPratiche = iAccreditamentoDispatcher.ricercaPratiche(ricercaDichiaranteRequest);
			return Response.ok(new ResponseVO<List<UtenteProvvisorioVO>>(Esito.SUCCESS, listaPratiche)).build();
		}


		@Override
		public Response cambiaStatoNoteAccreditamento(
				AnnullaPraticaAccreditamentoRequest annullaPraticaAccreditamentoRequest) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Object principal = auth.getPrincipal();
			UserDetails utente = (UserDetails) principal;
			
			iAccreditamentoDispatcher.cambiaStatoNoteAccreditamento(annullaPraticaAccreditamentoRequest, utente.getIdentita().getCodFiscale());
			return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
		}


		@Override
		public Response verificaDichiarante(String codiceAzienda, String codiceFiscaleOperatore) {
			iAccreditamentoDispatcher.verificaDichiarante(codiceAzienda, codiceFiscaleOperatore);
			return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
		}


		

	
	
	
}
