package it.csi.sigas.sigasbl.rest.api;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.sigas.sigasbl.request.depositocauzionale.RigeneraBollettinoPagamentoRequest;

@Path("/deposito-causionale")
@Consumes({ MediaType.APPLICATION_JSON  })
@Produces({ MediaType.APPLICATION_JSON })
public interface IDepositoCausionaleApi {
	
	@POST
	@Path("/bollettino-pagamento")
	@Consumes("multipart/form-data")
	Response generaProtocollaBollettinoPagamento(MultipartFormDataInput input);
	
	@POST
	@Path("/bollettino-pagamento/rigenera")	
	Response rigeneraBollettinoPagamento(RigeneraBollettinoPagamentoRequest rigeneraBollettinoPagamentoRequest);
	
	@GET
	@Path("/test-resource")
	Response testResource();	
}
