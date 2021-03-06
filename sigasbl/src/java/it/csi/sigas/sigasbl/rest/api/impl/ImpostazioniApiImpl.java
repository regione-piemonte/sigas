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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.dispatcher.IImpostazioniDispatcher;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.AliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TassoVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoAliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoTassoVO;
import it.csi.sigas.sigasbl.request.home.ConfermaAliquotaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaTassoRequest;
import it.csi.sigas.sigasbl.rest.api.IImpostazioniApi;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;


@Service
public class ImpostazioniApiImpl extends SpringSupportedResource implements IImpostazioniApi {
	
	@Autowired
	private IImpostazioniDispatcher impostazioniDispatcher;

	@Value("${application.code}")
	static private String APPLICATION_CODE;
	
	@Value("${year.to.show}")
	private int yearToShow;

	static Logger logger = LoggerFactory.getLogger(APPLICATION_CODE + ".business");

	@Override
	public Response getAllTassi() {
		logger.info("START: getAllTassi");
		List<TassoVO> lista = impostazioniDispatcher.getAllTassi();
		logger.info("END: getAllTassi");
		return Response.ok().entity(new ResponseVO<List<TassoVO>>(Esito.SUCCESS, lista)).build();
	}
	
	@Override
	public Response getAllTipoTassi() {
		logger.info("START: getAllTipoTassi");
		List<TipoTassoVO> lista = impostazioniDispatcher.getAllTipoTassi();
		logger.info("END: getAllTipoTassi");
		return Response.ok().entity(new ResponseVO<List<TipoTassoVO>>(Esito.SUCCESS, lista)).build();
	}
	
	
	@Override
	public Response eliminaTasso(Long idTasso) {
		logger.info("START: eliminaTasso");
		impostazioniDispatcher.eliminaTasso(idTasso/*, utente.getIdentita().getCodFiscale()*/);
		logger.info("END: eliminaTasso");
		return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}
	
	
	@Override
	public Response aggiungiTasso(ConfermaTassoRequest confermaAggiungiTasso) {
		logger.info("START: salvaTasso");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		
		TassoVO nuovoTasso = impostazioniDispatcher.aggiungiTasso(confermaAggiungiTasso, utente.getIdentita().getCodFiscale());
		logger.info("END: salvaTasso");
		return Response.ok(new ResponseVO<TassoVO>(Esito.SUCCESS, nuovoTasso)).build();
	}

	@Override
	public Response getAllAliquote() {
		logger.info("START: getAllAliquote");
		List<AliquoteVO> lista = impostazioniDispatcher.getAllAliquote();
		logger.info("END: getAllAliquote");
		return Response.ok().entity(new ResponseVO<List<AliquoteVO>>(Esito.SUCCESS, lista)).build();
	}
	
	@Override
	public Response tassiByRimborso() {
		logger.info("START: tassiByRimborso");
    	List<TassoVO> lista = impostazioniDispatcher.tassiByRimborso();
		logger.info("END: tassiByRimborso");
		return Response.ok().entity(new ResponseVO<List<TassoVO>>(Esito.SUCCESS, lista)).build();
	}
	
	@Override
	public Response tassiByAccertamenti() {
		logger.info("START: tassiByAccertamenti");
    	List<TassoVO> lista = impostazioniDispatcher.tassiByAccertamenti();
		logger.info("END: tassiByAccertamenti");
		return Response.ok().entity(new ResponseVO<List<TassoVO>>(Esito.SUCCESS, lista)).build();
	}

	@Override
	public Response getAllTipiAliquoteByTipo(String tipo) {
		logger.info("START: getAllTipiAliquoteByTipo");
    	List<TipoAliquoteVO> lista = impostazioniDispatcher.getAllTipoAliquoteByTipo(tipo);
		logger.info("END: getAllTipiAliquoteByTipo");
		return Response.ok().entity(new ResponseVO<List<TipoAliquoteVO>>(Esito.SUCCESS, lista)).build();
	}

	@Override
	public Response getTipoAliquoteByDescrizione(Long idTipoAliquota) {
		logger.info("START: getTipoAliquoteByDescrizione");
    	TipoAliquoteVO tipoAliquoteVO = impostazioniDispatcher.getTipoAliquoteByDescrizione(idTipoAliquota);
		logger.info("END: getTipoAliquoteByDescrizione");
		return Response.ok().entity(new ResponseVO<TipoAliquoteVO>(Esito.SUCCESS, tipoAliquoteVO)).build();
	}
	
	@Override
	public Response insertAliquota(ConfermaAliquotaRequest confermaAliquotaRequest) {
		logger.info("START: insertAliquota");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		
		this.impostazioniDispatcher.insertAliquota(confermaAliquotaRequest, utente.getIdentita().getCodFiscale());
		
		logger.info("END: insertAliquota");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

	@Override
	public Response updateAliquota(ConfermaAliquotaRequest confermaAliquotaRequest) {
		logger.info("START: updateAliquota");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;

		try {
			this.impostazioniDispatcher.updateAliquota(confermaAliquotaRequest, utente.getIdentita().getCodFiscale());
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException("Esiste gia' un'aliquota con tasso " + confermaAliquotaRequest.getAliquota().getAliquota() + 
							" e ProgRigo" + confermaAliquotaRequest.getAliquota().getProgRigo(), ErrorCodes.BUSSINESS_EXCEPTION);
		}
		
		logger.info("END: updateAliquota");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

	@Override
	public Response eliminaAliquota(Long id) {
		logger.info("START: eliminaAliquota");
		impostazioniDispatcher.eliminaAliquota(id);
		logger.info("END: eliminaAliquota");
		return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

	@Override
	public Response aggiornaTasso(ConfermaTassoRequest confermaAggiornaTasso) {
		logger.info("START: aggiornaTasso");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		TassoVO tassoAggiornato  = impostazioniDispatcher.aggiornaTasso(confermaAggiornaTasso, utente.getIdentita().getCodFiscale());
		logger.info("END: aggiornaTasso");
		return Response.ok(new ResponseVO<TassoVO>(Esito.SUCCESS,tassoAggiornato)).build();
	}

}
