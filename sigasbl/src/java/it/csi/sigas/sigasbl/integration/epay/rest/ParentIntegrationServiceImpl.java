package it.csi.sigas.sigasbl.integration.epay.rest;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.impl.PiemontePayRestApiImpl;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;

public abstract class ParentIntegrationServiceImpl {
	
	protected static Logger logger = Logger.getLogger(ParentIntegrationServiceImpl.class);
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	protected SigasCParametro getParametroByDescrizione(String descrizione) {
		return sigasCParametroRepository.findByDescParametro(descrizione);
	}	
	
	protected String getRestPiemometPayBaseUrl() {
		//URL Template param
		//---------------------------------------------------------------------------------------------------------------------
		//http://tst-srv-pay.bilancio.csi.it/epayapi/api/v1/organizations
		return sigasCParametroRepository.findByDescParametro("rest_piemometpay_base_url").getValoreString();
	}
	
	protected RestTemplate getRestTemplate(boolean withBasicAuthInterceptor) {		
		
		RestTemplate restTemplate = new RestTemplate();
		
		//List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		//MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		//converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		//messageConverters.add(converter);
		//restTemplate.setMessageConverters(messageConverters);
		if(withBasicAuthInterceptor) {
			//setBasicAuthInterceptor(restTemplate);
		}		
		return restTemplate;
	}
	
	protected void setBasicAuthInterceptor(RestTemplate restTemplate) {		
		String userRestBasicAuth = sigasCParametroRepository.findByDescParametro("rest_user_basic_auth").getValoreString();
		String pwdRestBasicAuth = sigasCParametroRepository.findByDescParametro("rest_pwd_basic_auth").getValoreString();
		if(StringUtils.isNotBlank(userRestBasicAuth) && StringUtils.isNotBlank(pwdRestBasicAuth)) {
			//restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userRestBasicAuth, pwdRestBasicAuth));
		}		
	}
	
	protected HttpHeaders getHeadersWithBasicAuth() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.ALL));
		headers.setContentType(MediaType.APPLICATION_JSON);
		//headers.setContentType(MediaType.APPLICATION_JSON);		
		//headers.setAccept(Collections.singletonList(MediaType.ALL));
		//headers.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
		String userRestBasicAuth = sigasCParametroRepository.findByDescParametro("rest_user_basic_auth").getValoreString();
		String pwdRestBasicAuth = sigasCParametroRepository.findByDescParametro("rest_pwd_basic_auth").getValoreString();
		
		return setBasicAuthHeader(headers,userRestBasicAuth,pwdRestBasicAuth);
	}
	
	protected HttpHeaders setBasicAuthHeader(HttpHeaders headers, String user, String pwd) {
		if(user != null && pwd != null && headers != null) {
			String token = user + Constants.AUTH_HEADER_SEPARATOR + pwd;
			String encondingBase64 = Base64.encodeBytes(token.getBytes());			    
			headers.set(Constants.AUTH_HEADER_ID, Constants.AUTH_HEADER_PREFIX + Constants.AUTH_HEADER_BLANK_SPACE + encondingBase64);
			
		}
		return headers;
	}
	
	protected Logger getLogger() {
		return this.logger;
	}

}
