package it.csi.sigas.sigasbl.facade.impl;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.security.MessageDigest;

//import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;

import org.apache.axis.client.Stub;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		Enti2EPaywsoService_ServiceLocator locator = new Enti2EPaywsoService_ServiceLocator();
//		locator.setEnti2EPaywsoServiceSOAPEndpointAddress(config.getEpayServiceEndpointUrl());
//		binding = (Enti2EPaywsoServiceSOAPStub) locator.getEnti2EPaywsoServiceSOAP();
//		
//		String uid = config.getEpay_service_endpoint_uid();
//		String pwd = config.getEpay_service_endpoint_pwd();
//		
//		binding.setUsername(uid);
//		binding.setPassword(pwd);
//		binding.setTimeout(100000);
//		
//		//binding.setProperty(WsseClientHandler.PASSWORD_OPTION, WsseClientHandler.PASSWORD_DIGEST_WITH_NONCE);
//		//binding.setClientHandlers(new WsseClientHandler(), null);
//
//		//System.out.print((String)binding.invoke(new Object[] {}));		
//	}
	
	public void init() throws ServiceException {
	
	Enti2EPaywsoService_ServiceLocator locator = new Enti2EPaywsoService_ServiceLocator();
	locator.setEnti2EPaywsoServiceSOAPEndpointAddress(sigasCParametroRepository.findByDescParametro("epay_service_endpoint_url").getValoreString());
	binding = (Enti2EPaywsoServiceSOAPStub) locator.getEnti2EPaywsoServiceSOAP();
	
	String uid = sigasCParametroRepository.findByDescParametro("epay_service_endpoint_uid").getValoreString();
	String pwd = sigasCParametroRepository.findByDescParametro("epay_service_endpoint_pwd").getValoreString();
	
	binding.setUsername(uid);
	binding.setPassword(pwd);
	binding.setTimeout(100000);
	
}

	void addWsSecurityHeader(Enti2EPaywsoServiceSOAPStub binding, String wsUser,String wsPass) throws Exception {
	    // Create the top-level WS-Security SOAP header XML name.
	    QName headerName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
	    SOAPHeaderElement header = new SOAPHeaderElement(headerName);
	    //  no intermediate actors are involved.
	    header.setActor(null);
	    // not important, "wsse" is standard
	    header.setPrefix("wsse");
	    header.setMustUnderstand(true);

	    // Add the UsernameToken element to the WS-Security header
	    SOAPElement utElem = header.addChildElement("UsernameToken");
	    SOAPElement userNameElem = utElem.addChildElement("Username");
	    userNameElem.removeContents();
	    userNameElem.setValue(wsUser);

	    SOAPElement passwordElem = utElem.addChildElement("Password");
	    passwordElem.setValue(wsPass);
	    
	    /*
		<wsu:Timestamp wsu:Id="TS-829D150E6BC958F7DF16061230788228">
			<wsu:Created>2020-11-23T09:17:58.822Z</wsu:Created>
			<wsu:Expires>2020-11-24T13:04:38.822Z</wsu:Expires>
		</wsu:Timestamp>
	    */
	    
	    
	    binding.setHeader(header);
	}

	@Override
	public void inserisciListaDiCarico(InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest) {
		ResponseType response;
		
		try {
			if(binding==null) {
				init();
			}			
			response = binding.inserisciListaDiCarico(inserisciListaDiCaricoRequest);
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
	public PaymentRedirectVO getPaymentRedirectInfo(String iuv, 
													String identificativoPagamento, 
													String codiceFiscale,
													String defaultWaitMsg) throws Exception {
		String url = sigasCParametroRepository.findByDescParametro("epay_service_callback").getValoreString(); // epay_service_endpoint_url 
				
		String codiceChiamante = sigasCParametroRepository.findByDescParametro("epay_service_endpoint_redirect_callid").getValoreString();
		//String codVersamento = sigasCParametroRepository.findByDescParametro("epay_service_codice_versamento").getValoreString();
		
		// {passphrase} + "%%%%" + {codiceChiamante}+{identificativo_pagamento}+{iuv}+{url}+ "%%%%"
		String digestStr = sigasCParametroRepository.findByDescParametro("epay_service_endpoint_redirect_call_pass").getValoreString() + "%%%%" + 
				codiceChiamante + 
				identificativoPagamento + iuv + url + "%%%%";
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(digestStr.getBytes(StandardCharsets.UTF_8));
		byte[] base64digest = Base64.encodeBase64(hash);
		
		String urlRedicetPpay = sigasCParametroRepository.findByDescParametro("epay_service_redirect").getValoreString();
    	logger.info("EPayServiceFacadeImpl.getPaymentRedirectInfo url: " + urlRedicetPpay + " digest: " + digestStr + " --> digested-base64: " + new String(base64digest));
    	
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
