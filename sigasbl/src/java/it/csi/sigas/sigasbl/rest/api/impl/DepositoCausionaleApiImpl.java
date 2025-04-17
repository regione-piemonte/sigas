package it.csi.sigas.sigasbl.rest.api.impl;


import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.request.depositocauzionale.RigeneraBollettinoPagamentoRequest;
import it.csi.sigas.sigasbl.dispatcher.IDepositoCausionaleDispatcher;

import it.csi.sigas.sigasbl.rest.api.IDepositoCausionaleApi;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;

@Service
public class DepositoCausionaleApiImpl extends SpringSupportedResource implements IDepositoCausionaleApi {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDepositoCausionaleDispatcher iDepositoCausionaleDispatcher;


	@Override
	public Response generaProtocollaBollettinoPagamento(MultipartFormDataInput input) {	
		
		logger.info("START: generaProtocollaBollettinoPagamento");
		
		this.iDepositoCausionaleDispatcher.protocollaDepositoCausionale(input);
		
		logger.info("END: generaProtocollaBollettinoPagamento");
		
		return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();		
	}	
	

	@Override
	public Response testResource() {
		return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}


	@Override
	public Response rigeneraBollettinoPagamento(RigeneraBollettinoPagamentoRequest rigeneraBollettinoPagamentoRequest) {
		
		logger.info("START: rigeneraBollettinoPagamento");
		
		this.iDepositoCausionaleDispatcher.rigeneraBollettinoPagamento(rigeneraBollettinoPagamentoRequest);
		
		logger.info("END: rigeneraBollettinoPagamento");
		
		return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

}
