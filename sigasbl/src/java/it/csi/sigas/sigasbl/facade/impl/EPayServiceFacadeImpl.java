package it.csi.sigas.sigasbl.facade.impl;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

//import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;

import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import it.csi.sigas.sigasbl.common.config.EPayConfig;
import it.csi.sigas.sigasbl.common.exception.RemoteWebServiceException;
import it.csi.sigas.sigasbl.facade.EPayServiceFacade;
import it.csi.sigas.sigasbl.integration.epay.EPayErrorCodes;
import it.csi.sigas.sigasbl.integration.epay.to.Enti2EPaywsoServiceSOAPStub;
import it.csi.sigas.sigasbl.integration.epay.to.Enti2EPaywsoService_ServiceLocator;
import it.csi.sigas.sigasbl.integration.epay.to.InserisciListaDiCaricoRequest;
import it.csi.sigas.sigasbl.integration.epay.to.ResponseType;
import it.csi.sigas.sigasbl.integration.epay.to.ResultType;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.vo.home.PaymentRedirectVO;


/**
 * @author 
 * @date 
 */
@Service
public class EPayServiceFacadeImpl implements EPayServiceFacade{

//	@Autowired
//	private EPayConfig config;
	
	private static final Logger logger = Logger.getLogger(EPayServiceFacadeImpl.class);

	private Enti2EPaywsoServiceSOAPStub binding;

	private static final String PIEMONTE_PAY_ESITO_OK = "000";
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	public void init(Boolean wsSecurityOn) throws ServiceException {
	
		String urlService = null;
		if(wsSecurityOn){
			urlService = sigasCParametroRepository.findByDescParametro("epay_service_endpoint_url_ws_security").getValoreString();
		}else {
			urlService = sigasCParametroRepository.findByDescParametro("epay_service_endpoint_url").getValoreString();
		}
			
		Enti2EPaywsoService_ServiceLocator locator = new Enti2EPaywsoService_ServiceLocator();
		locator.setEnti2EPaywsoServiceSOAPEndpointAddress(urlService);
		binding = (Enti2EPaywsoServiceSOAPStub) locator.getEnti2EPaywsoServiceSOAP();
		
		String uid = sigasCParametroRepository.findByDescParametro("epay_service_endpoint_uid").getValoreString();
		String pwd = sigasCParametroRepository.findByDescParametro("epay_service_endpoint_pwd").getValoreString();
		
		binding.setUsername(uid);
		binding.setPassword(pwd);
		binding.setTimeout(100000);
	
	}	
	
	private void _inserisciListaDiCarico(InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest, 
										 Boolean wsSecurityOn, String wsUser, String wsPWD) {
		ResponseType response;		
		try {
			if(binding==null) {
				init(wsSecurityOn);
			}
			if(wsSecurityOn) {
				response = binding.inserisciListaDiCarico(inserisciListaDiCaricoRequest, wsUser, wsPWD);
			}else {
				response = binding.inserisciListaDiCarico(inserisciListaDiCaricoRequest);
			}			
		} catch (RemoteException e) {
			throw new RemoteWebServiceException(EPayErrorCodes.PIEMONTE_PAY_LISTA_CARICO_NON_DISPONIBILE);
		} catch (ServiceException e) {
			throw new RemoteWebServiceException(EPayErrorCodes.PIEMONTE_PAY_LISTA_CARICO_NON_DISPONIBILE);
		}
		finally {
			try {
				Stub stub = (org.apache.axis.client.Stub) binding;
				logger.info("inserisciListaDiCarico request: " + stub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString());
				logger.info("inserisciListaDiCarico response: " + stub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString());
			} catch (Exception skip) { }
		}

		if (response == null) {
			logger.error("Piemonte pay ha risposto con null");
			throw new RemoteWebServiceException(EPayErrorCodes.PIEMONTE_PAY_LISTA_CARICO_CON_ERRORI);
		}

		ResultType result = response.getResult();
		String codice = result.getCodice();

		if (!codice.equals(PIEMONTE_PAY_ESITO_OK)) {
			logger.error("Piemonte pay ha risposto con il seguente codice di errore: " + codice + " e messaggio: " + result.getMessaggio());
			throw new RemoteWebServiceException(EPayErrorCodes.PIEMONTE_PAY_LISTA_CARICO_CON_ERRORI);
		}
	}

	@Override
	public void inserisciListaDiCarico(InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest) {		
		_inserisciListaDiCarico(inserisciListaDiCaricoRequest,false,null,null);		
	}
	
	//CR WS Security
	@Override
	public void inserisciListaDiCarico(InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest,  String wsUser, String wsPWD) {
		_inserisciListaDiCarico(inserisciListaDiCaricoRequest,true,wsUser,wsPWD);		
	}	
/*

	@Value("${epay_service_redirect}")
	private String epay_service_redirect;
	
	@Value("${epay_service_callback}")
	private String epay_service_callback;

	@Value("${epay_service_endpoint_redirect_callid}")
	private String epay_service_endpoint_redirect_callid;
	
	@Value("${epay_service_endpoint_redirect_call_pass}")
	private String epay_service_endpoint_redirect_call_pass;

 */
	public boolean checkPiemontePayServiceHealth(String url) {    	
    	RestTemplate restTemplate = new RestTemplate();    	
    	ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    	logger.info("EPayServiceFacadeImpl.checkPiemontePayServiceHealth response: " + response.getBody());
    	if(response.getBody().contains("risulta in manutenzione")) {
    		return false; 
    	}    	
    	return true;   	
    }
	
	public PaymentRedirectVO getPaymentRedirectInfo(String iuv, 
													String identificativoPagamento, 
													String codiceFiscale,
													String defaultWaitMsg) throws Exception {
		String url = sigasCParametroRepository.findByDescParametro("epay_service_callback").getValoreString(); // epay_service_endpoint_url 
				
		String codiceChiamante = sigasCParametroRepository.findByDescParametro("epay_service_endpoint_redirect_callid").getValoreString();
		
		String digestStr = sigasCParametroRepository.findByDescParametro("epay_service_endpoint_redirect_call_pass").getValoreString() + "%%%%" + 
				codiceChiamante + 
				identificativoPagamento + iuv + url + "%%%%";
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(digestStr.getBytes(StandardCharsets.UTF_8));
		byte[] base64digest = Base64.encodeBase64(hash);
		
		String urlRedicetPpay = sigasCParametroRepository.findByDescParametro("epay_service_redirect").getValoreString();
    	logger.info("EPayServiceFacadeImpl.getPaymentRedirectInfo url: " + urlRedicetPpay + " digest: " + digestStr + " --> digested-base64: " + new String(base64digest));
    	
    	if(checkPiemontePayServiceHealth(urlRedicetPpay)) {
    		defaultWaitMsg = "Attenzione l'applicazione risulta in manutenzione riprova tra qualche istante";
		}
    	
		PaymentRedirectVO payInfo = new PaymentRedirectVO();
		payInfo.setDigest(new String(base64digest));
		
		payInfo.setCodiceChiamante(codiceChiamante);
		payInfo.setCodiceFiscale(codiceFiscale);
		payInfo.setIdentificativoPagamento(identificativoPagamento);
		payInfo.setIuv(iuv);
		payInfo.setUrl(urlRedicetPpay);
		payInfo.setWaitingUserMessage(defaultWaitMsg);
		
		return  payInfo;
	}

}
