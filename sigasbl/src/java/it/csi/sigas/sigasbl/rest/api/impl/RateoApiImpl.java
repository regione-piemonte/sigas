package it.csi.sigas.sigasbl.rest.api.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.dispatcher.IRateoDispatcher;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.model.vo.home.RateoVO;
import it.csi.sigas.sigasbl.request.home.RicercaRateoRequest;
import it.csi.sigas.sigasbl.rest.api.IRateoApi;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;

@Service
public class RateoApiImpl extends SpringSupportedResource implements IRateoApi {
	
	@Autowired
	IRateoDispatcher rateoDispatcher;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Response ricercaRateo(Long id) {
		logger.info("START: ricercaRateo");
	   	
    	RateoVO rateoVO = rateoDispatcher.findByIdRateo(id);    	
		
    	logger.info("END: ricercaRateo");
        return Response.ok(new ResponseVO<RateoVO>(Esito.SUCCESS, rateoVO)).build();
	}

	@Override
	public Response ricercaRateoByParams(RicercaRateoRequest ricercaRateoRequest) {
		logger.info("START: ricercaRateoByParams");
	   	
    	List<RateoVO> rateoVOList = rateoDispatcher.ricercaRateoListByRequest(ricercaRateoRequest);    	
		
    	logger.info("END: ricercaRateoByParams");
        return Response.ok(new ResponseVO<List<RateoVO>>(Esito.SUCCESS, rateoVOList)).build();
	}

	@Override
	public Response inserisciRateo(RateoVO rateoVO) {
		logger.info("START: inserisciRateo");
	   	
    	rateoDispatcher.inserisciRateo(rateoVO);    	
		
    	logger.info("END: inserisciRateo");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

	@Override
	public Response modificaRateo(RateoVO rateoVO) {
		logger.info("START: modificaRateo");
	   	
    	rateoDispatcher.updateRateo(rateoVO);    	
		
    	logger.info("END: modificaRateo");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

	@Override
	public Response cancellaRateo(Long id) {
		logger.info("START: cancellaRateo");
	   	
    	rateoDispatcher.deleteRateo(id);    	
		
    	logger.info("END: cancellaRateo");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

}
