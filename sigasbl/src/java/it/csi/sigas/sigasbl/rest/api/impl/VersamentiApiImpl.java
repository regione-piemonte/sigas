/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.dispatcher.IExportDispatcher;
import it.csi.sigas.sigasbl.dispatcher.IVersamentiDispatcher;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.PagamentiVersamentiVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoVersamentoVO;
import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.request.home.AllarmeRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoContabiliaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoRequest;
import it.csi.sigas.sigasbl.request.home.DownloadVersamentiReport;
import it.csi.sigas.sigasbl.request.home.RicercaVersamentiRequest;
import it.csi.sigas.sigasbl.rest.api.IVersamentiApi;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;

@Service
public class VersamentiApiImpl extends SpringSupportedResource implements IVersamentiApi {
    
    @Autowired
    private IVersamentiDispatcher versamentiDispatcher;
    
    @Autowired
	private IExportDispatcher exportDispatcher;


    public VersamentiApiImpl() {
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	
	/*Versamenti per soggetto*/
	
	@Override
    public Response ricercaAnnualitaVersamenti(Long idAnag) {
    	
    	logger.info("START: ricercaAnnualitaVersamenti");
   	
    	List<String> ricercaAnnualitaVersList = this.versamentiDispatcher.ricercaAnnualitaVersamenti(idAnag);
		
    	logger.info("END: ricercaAnnualitaVersamenti");
        return Response.ok(new ResponseVO<List<String>>(Esito.SUCCESS, ricercaAnnualitaVersList)).build();
    }

	@Override
	public Response ricercaProvinceVersamenti(Long idAnag) {
		logger.info("START: ricercaProvinceVersamenti");
	   	
    	List<ProvinciaVO> ricercaProvinceVersamentiList = this.versamentiDispatcher.ricercaProvinceVersamenti(idAnag);
		
    	logger.info("END: ricercaProvinceVersamenti");
        return Response.ok(new ResponseVO<List<ProvinciaVO>>(Esito.SUCCESS, ricercaProvinceVersamentiList)).build();
	}

	@Override
	public Response ricercaTipoVersamenti() {
		logger.info("START: ricercaTipoVersamenti");
	   	
    	List<TipoVersamentoVO> ricercaTipoVersamentiList = this.versamentiDispatcher.ricercaTipoVersamenti();
		
    	logger.info("END: ricercaTipoVersamenti");
        return Response.ok(new ResponseVO<List<TipoVersamentoVO>>(Esito.SUCCESS, ricercaTipoVersamentiList)).build();
	}

	@Override
	public Response ricercaMeseVersamenti(Long id,String annualita) {
		logger.info("START: ricercaMeseVersamenti");
	   	
    	List<String> ricercaMeseVersamentiList = this.versamentiDispatcher.ricercaMeseVersamenti(id,annualita);
		
    	logger.info("END: ricercaMeseVersamenti");
        return Response.ok(new ResponseVO<List<String>>(Esito.SUCCESS, ricercaMeseVersamentiList)).build();
	}

	@Override
	public Response ricercaVersamenti(RicercaVersamentiRequest ricercaVersamentiRequest) {
		logger.info("START: ricercaVersamenti");
	   	
    	List<VersamentiPrVO> ricercaVersamentiList = this.versamentiDispatcher.ricercaVersamenti(ricercaVersamentiRequest);
		
    	logger.info("END: ricercaVersamenti");
        return Response.ok(new ResponseVO<List<VersamentiPrVO>>(Esito.SUCCESS, ricercaVersamentiList)).build();
	}
	
	@Override
	public Response ricercaAllarmi(Long idAnag,Long idTipoAllarme) {
		logger.info("START: ricercaAllarmi");
	   	
    	List<AllarmiSoggettoVO> ricercaAllarmiList = this.versamentiDispatcher.ricercaAllarmi(idAnag,idTipoAllarme);
		
    	logger.info("END: ricercaAllarmi");
        return Response.ok(new ResponseVO<List<AllarmiSoggettoVO>>(Esito.SUCCESS, ricercaAllarmiList)).build();
	}
	
	
	@Override
	public Response ricercaAllarmiVersamento(Long idConsumo) {
		logger.info("START: ricercaAllarmiVersamento");
	   	
    	AllarmiSoggettoVO ricercaAllarmi = this.versamentiDispatcher.ricercaAllarmiVersamento(idConsumo);
		
    	logger.info("END: ricercaAllarmiVersamento");
        return Response.ok(new ResponseVO<AllarmiSoggettoVO>(Esito.SUCCESS, ricercaAllarmi)).build();
	}

	@Override
	public Response allarmeSoggetto(AllarmeRequest allarmeRequest) {
		logger.info("START: allarmeRequest : "+ allarmeRequest.getIdConsumi() + " - " + allarmeRequest.getIdAnag() + " - " + allarmeRequest.isStatus());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		this.versamentiDispatcher.allarmeSoggetto(allarmeRequest, utente.getUsername());

		logger.info("END: allarmeRequest");
		return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}
	
	
	@Override
	public Response insertVersamento(ConfermaVersamentoRequest confermaVersamentoRequest) {
		logger.info("START: insertVersamento");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		this.versamentiDispatcher.insertVersamento(confermaVersamentoRequest, utente.getUsername(), utente.getIdentita().getCodFiscale());
		logger.info("END: insertVersamento");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

	@Override
	public Response insertVersamentoContabilia(ConfermaVersamentoContabiliaRequest confermaVersamentoContabiliaRequest) { 
		logger.info("START: insertVersamentoContabilia");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		List<PagamentiVersamentiVO> pagamentiVersamentiVO = this.versamentiDispatcher.insertVersamentoContabilia(confermaVersamentoContabiliaRequest, utente.getUsername(), utente.getIdentita().getCodFiscale());
		logger.info("END: insertVersamentoContabilia");
        return Response.ok(new ResponseVO<List<PagamentiVersamentiVO>>(Esito.SUCCESS, pagamentiVersamentiVO)).build();
	}
	
	
	@Override
	public Response updateVersamento(ConfermaVersamentoRequest confermaVersamentoRequest) {
		logger.info("START: updateVersamento");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		this.versamentiDispatcher.updateVersamento(confermaVersamentoRequest, utente.getUsername(), utente.getIdentita().getCodFiscale());
		logger.info("END: updateVersamento");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}
	
	@Override
	public Response salvaElencoVersamenti(DownloadVersamentiReport downloadVersamentiReport) {
		logger.info("START: salvaElencoVersamenti");
		
		byte[] file = this.exportDispatcher.salvaElencoVersamenti(downloadVersamentiReport);
    	logger.info("END: salvaElencoVersamenti");
		
    	return Response.ok().entity(file).build();
	}
	

	
}
