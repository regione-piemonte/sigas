package it.csi.sigas.sigasbl.integration.epay.rest.ppay.impl;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

//import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.integration.epay.rest.ParentIntegrationServiceImpl;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.PiemontePayRestApi;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.RequestObjects.CreateDebtPositionRequest;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.CheckDebtPositionsStatusResponse;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.CreateDebtPositionResponse;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.GeneraAvvisoPagamentoResponse;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.GeneraAvvisoPagamentoResult;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.PaymentResponse;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.ResultBaseResponse;
import it.csi.sigas.sigasbl.scheduled.impl.ScheduledServiceImpl;


@Service
public class PiemontePayRestApiImpl extends ParentIntegrationServiceImpl implements PiemontePayRestApi {	
	
	
	@Override
	public CreateDebtPositionResponse createDebtPosition(CreateDebtPositionRequest createDebtPositionRequest) throws IntegrationException 
	{
		ObjectMapper objectMapper = new ObjectMapper();
		CreateDebtPositionResponse responseObject = null;
		String serviceURL = "";
		
		try 
		{
			
			//URL Template param rest_url_create_debt_position
			//---------------------------------------------------------------------------------------------------------------------
			// /{codiceFiscaleEnteCreditore}/paymenttypes/{epay-service-codice-versamento}/debtpositions
			//---------------------------------------------------------------------------------------------------------------------
			String createDebtPositionURL = getParametroByDescrizione("rest_url_create_debt_position").getValoreString();
			String codiceFiscaleEnteCreditore = getParametroByDescrizione("codiceFiscaleEnteCreditore").getValoreString();
			String epayServiceCodiceVersamento = getParametroByDescrizione("epay_service_codice_versamento").getValoreString();		
						
			//Composizione URI
			Map<String, String> pathParams = new HashMap<String, String>();
			pathParams.put("codiceFiscaleEnteCreditore", codiceFiscaleEnteCreditore);
			pathParams.put("epay-service-codice-versamento", epayServiceCodiceVersamento);
			URI uri = UriComponentsBuilder.fromUriString(createDebtPositionURL)
									      .buildAndExpand(pathParams)
									      .toUri();
			
			
			//Creazione headers - inclusione BASIC AUTH
			HttpHeaders headers = getHeadersWithBasicAuth();			
			HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(createDebtPositionRequest),headers);

			//Esecuzione chiamata						
			serviceURL = this.getRestPiemometPayBaseUrl() + URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8.toString());			
			logger.info(">>>>>>>>> CREATE DEBT POSISTION URL: " + serviceURL);					
			
						
			logger.info(">>>>>>>>> CREATE DEBT POSISTION REQUEST: " + objectMapper.writeValueAsString(request));
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> rest= restTemplate.exchange(serviceURL,
									        HttpMethod.POST, 
									        request, 
									        String.class);						
			// check response			
			if (rest.getStatusCode() == HttpStatus.OK) {			    
				responseObject = objectMapper.readValue(rest.getBody(), CreateDebtPositionResponse.class);
				if(responseObject != null && !responseObject.getCodiceEsito().equals(Constants.COD_ESITO_PAGAMENTO_PPAY_OK)) 
				{
					this.getLogger().error("Errore in fase di creazione / ricezione IUV. PPAY Rest Service: " + serviceURL + " Response: " + responseObject.getDescrizioneEsito());
					throw new Exception("Errore in fase di creazione / ricezione IUV: " + responseObject.getDescrizioneEsito());
				}				
			} else {
				throw new Exception("Errore in fase di comomunicazione con " + serviceURL + " Code: " + rest.getStatusCode().name());
			}						
			
		} catch(Exception e) {
			this.getLogger().error("Errore in fase di creazione / ricezione IUV. PPAY Rest Service: " + serviceURL + " Response: " + e);
			throw new IntegrationException("Errore in fase di creazione / ricezione IUV. Rest " + serviceURL);
		}				
		return responseObject;
	}
	
	public GeneraAvvisoPagamentoResponse generaAvvisoPagamento(String iuv) throws IntegrationException
	{
		GeneraAvvisoPagamentoResponse generaAvvisoPagamentoResponse = null;
		ObjectMapper objectMapper = new ObjectMapper();
		String serviceURL = "";
		
		try 
		{
			
			//URL Template param rest_url_create_debt_position
			//---------------------------------------------------------------------------------------------------------------------
			// /{codiceFiscaleEnteCreditore}/paymenttypes/{epay-service-codice-versamento}/debtpositions/{iuv}/paymentnotice
			//---------------------------------------------------------------------------------------------------------------------
			String createPaymentNoticeURL = getParametroByDescrizione("rest_url_paymentnotice").getValoreString();
			String codiceFiscaleEnteCreditore = getParametroByDescrizione("codiceFiscaleEnteCreditore").getValoreString();
			String epayServiceCodiceVersamento = getParametroByDescrizione("epay_service_codice_versamento").getValoreString();
			
			
			//Composizione URI
			Map<String, String> pathParams = new HashMap<String, String>();
			pathParams.put("codiceFiscaleEnteCreditore", codiceFiscaleEnteCreditore);
			pathParams.put("epay-service-codice-versamento", epayServiceCodiceVersamento);
			pathParams.put("iuv", iuv);
			URI uri = UriComponentsBuilder.fromUriString(createPaymentNoticeURL)
									      .buildAndExpand(pathParams)
									      .toUri();
			
			
			//Creazione headers - inclusione BASIC AUTH
			HttpHeaders headers = getHeadersWithBasicAuth();
			HttpEntity<Void> request = new HttpEntity<>(headers);			

			//Esecuzione chiamata			
			serviceURL = this.getRestPiemometPayBaseUrl() + URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8.toString());
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(serviceURL,
																	HttpMethod.GET,
																	request,
																	String.class);
			
			// check response
			if (response.getStatusCode() == HttpStatus.OK) {
				generaAvvisoPagamentoResponse = objectMapper.readValue(response.getBody(), GeneraAvvisoPagamentoResponse.class);
				if(generaAvvisoPagamentoResponse != null && !generaAvvisoPagamentoResponse.getResult().getCode().equals(Constants.COD_ESITO_PAGAMENTO_PPAY_OK)) 
				{
					this.getLogger().error("Errore in fase di genreazione avviso di pagamento. PPAY Rest Service: " + serviceURL + 
										   " Response: " + generaAvvisoPagamentoResponse.getResult().getDescription());
					throw new Exception("Errore in fase di genreazione avviso di pagamento " + generaAvvisoPagamentoResponse.getResult().getDescription());
				}
									
			} else {
				generaAvvisoPagamentoResponse = objectMapper.readValue(response.getBody(), GeneraAvvisoPagamentoResponse.class);
				this.getLogger().error("Errore in fase di generazione / ricezione Avviso di Pagamento. PPAY Rest Service: " + serviceURL + 
									   " Response: " + generaAvvisoPagamentoResponse.getResult().getDescription());
				throw new Exception("Errore in fase di generazione avviso di pagamento " + generaAvvisoPagamentoResponse.getResult().getDescription());
			}
			
		}catch(Exception e) {
			generaAvvisoPagamentoResponse = new GeneraAvvisoPagamentoResponse();
			GeneraAvvisoPagamentoResult generaAvvisoPagamentoResult = new GeneraAvvisoPagamentoResult();
			generaAvvisoPagamentoResult.setCode("300");
			generaAvvisoPagamentoResult.setDescription(e.getMessage());
			generaAvvisoPagamentoResponse.setResult(generaAvvisoPagamentoResult);
			this.getLogger().error("Errore in fase di generazione / ricezione Avviso di Pagamento. PPAY Rest Service: " + serviceURL + " Response: " + e.getMessage());			
		}
		
		return generaAvvisoPagamentoResponse;		
	}

	@Override
	public PaymentResponse riceviPagoPAURLByIUV(String iuv) throws IntegrationException {
		PaymentResponse paymentResponse = null;
		ObjectMapper objectMapper = new ObjectMapper();
		String serviceURL = "";
		
		try 
		{
			//URL Template param rest_url_create_debt_position
			//---------------------------------------------------------------------------------------------------------------------
			// /{codiceFiscaleEnteCreditore}/paymenttypes/{epay-service-codice-versamento}/debtpositions
			//---------------------------------------------------------------------------------------------------------------------
			String paymentURL = getParametroByDescrizione("rest_url_payment").getValoreString();
			String codiceFiscaleEnteCreditore = getParametroByDescrizione("codiceFiscaleEnteCreditore").getValoreString();					
						
			//Composizione URI
			Map<String, String> pathParams = new HashMap<String, String>();
			pathParams.put("codiceFiscaleEnteCreditore", codiceFiscaleEnteCreditore);
			pathParams.put("iuv", iuv);
			URI uri = UriComponentsBuilder.fromUriString(paymentURL)
									      .buildAndExpand(pathParams)
									      .toUri();
			
			
			//Creazione headers - inclusione BASIC AUTH
			HttpHeaders headers = getHeadersWithBasicAuth();			
			HttpEntity<String> request = new HttpEntity<>(headers);			

			//Esecuzione chiamata						
			serviceURL = this.getRestPiemometPayBaseUrl() + URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8.toString());			
			logger.info(">>>>>>>>> PAYMENT URL: " + serviceURL);					
			
						
			logger.info(">>>>>>>>> PAYMENT REQUEST: " + objectMapper.writeValueAsString(request));
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> rest= restTemplate.exchange(serviceURL,
									        HttpMethod.POST, 
									        request, 
									        String.class);						
			// check response			
			if (rest.getStatusCode() == HttpStatus.OK) {			    
				paymentResponse = objectMapper.readValue(rest.getBody(), PaymentResponse.class);
				if(paymentResponse != null && !paymentResponse.getCodiceEsito().equals(Constants.COD_ESITO_PAGAMENTO_PPAY_OK)) 
				{
					this.getLogger().error("Errore in fase di ricezione URL redirect. PPAY Rest Service: " + serviceURL + " Response: " + paymentResponse.getDescrizioneEsito());
					throw new Exception("Errore in fase di ricezione URL redirect: " + paymentResponse.getDescrizioneEsito());
				}				
			} else {
				throw new Exception("Errore in fase di comomunicazione con " + serviceURL + " Code: " + rest.getStatusCode().name());
			}
			
		}catch(Exception e) {			
			this.getLogger().error("Errore in fase di ricezione URL redirect. PPAY Rest Service: " + serviceURL + " Response: " + e.getMessage());			
		}
		return paymentResponse;
	}

	@Override
	public CheckDebtPositionsStatusResponse checkDebtPosition(String iuv) throws IntegrationException 
	{
		CheckDebtPositionsStatusResponse checkDebtPositionsStatusResponse = null;
		ObjectMapper objectMapper = new ObjectMapper();
		String serviceURL = "";
		
		try 
		{
			
			//URL Template param rest_url_check_debtposition_status
			//---------------------------------------------------------------------------------------------------------------------
			// /{codiceFiscaleEnteCreditore}/paymenttypes/{epay-service-codice-versamento}/debtpositions/{iuv}/status
			//---------------------------------------------------------------------------------------------------------------------
			String checkDebtPositionStatusURL = getParametroByDescrizione("rest_url_check_debtposition_status").getValoreString();
			String codiceFiscaleEnteCreditore = getParametroByDescrizione("codiceFiscaleEnteCreditore").getValoreString();
			String epayServiceCodiceVersamento = getParametroByDescrizione("epay_service_codice_versamento").getValoreString();
			
			
			//Composizione URI
			Map<String, String> pathParams = new HashMap<String, String>();
			pathParams.put("codiceFiscaleEnteCreditore", codiceFiscaleEnteCreditore);
			pathParams.put("epay-service-codice-versamento", epayServiceCodiceVersamento);
			pathParams.put("iuv", iuv);
			URI uri = UriComponentsBuilder.fromUriString(checkDebtPositionStatusURL)
									      .buildAndExpand(pathParams)
									      .toUri();
			
			
			//Creazione headers - inclusione BASIC AUTH
			HttpHeaders headers = getHeadersWithBasicAuth();
			HttpEntity<Void> request = new HttpEntity<>(headers);			

			//Esecuzione chiamata			
			serviceURL = this.getRestPiemometPayBaseUrl() + URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8.toString());
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(serviceURL,
																	HttpMethod.GET,
																	request,
																	String.class);
			
			// check response
			if (response.getStatusCode() == HttpStatus.OK) {
				checkDebtPositionsStatusResponse = objectMapper.readValue(response.getBody(), CheckDebtPositionsStatusResponse.class);
				if(checkDebtPositionsStatusResponse != null && !checkDebtPositionsStatusResponse.getResult().getCode().equals(Constants.COD_ESITO_PAGAMENTO_PPAY_OK)) 
				{
					this.getLogger().error("Errore in fase di verifica status posizione debitoria. PPAY Rest Service: " + serviceURL + 
										   " Response: " + checkDebtPositionsStatusResponse.getResult().getDescription());
					throw new Exception("Errore in fase di verifica status posizione debitoria " + checkDebtPositionsStatusResponse.getResult().getDescription());
				}
									
			} else {
				checkDebtPositionsStatusResponse = objectMapper.readValue(response.getBody(), CheckDebtPositionsStatusResponse.class);
				this.getLogger().error("Errore in fase di verifica status posizione debitoria. PPAY Rest Service: " + serviceURL + 
									   " Response: " + checkDebtPositionsStatusResponse.getResult().getDescription());
				throw new Exception("Errore in fase di verifica status posizione debitoria " + checkDebtPositionsStatusResponse.getResult().getDescription());
			}
			
		}catch(Exception e) {
			checkDebtPositionsStatusResponse = new CheckDebtPositionsStatusResponse();
			ResultBaseResponse resultBaseResponse = new ResultBaseResponse();
			resultBaseResponse.setCode("300");
			resultBaseResponse.setDescription(e.getMessage());
			checkDebtPositionsStatusResponse.setResult(resultBaseResponse);
			this.getLogger().error("Errore in fase di verifica status posizione debitoria. PPAY Rest Service: " + serviceURL + " Response: " + e.getMessage());			
		}
		return checkDebtPositionsStatusResponse;
	}

}
