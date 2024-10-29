package it.csi.sigas.sigasbl.integration.epay.rest.ppay;

import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.RequestObjects.CreateDebtPositionRequest;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.CheckDebtPositionsStatusResponse;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.CreateDebtPositionResponse;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.GeneraAvvisoPagamentoResponse;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.PaymentResponse;

public interface PiemontePayRestApi {
	
	/*
	 * Il servizio permette di ricevere lo IUV ed il codice avviso dallo Sportello.
	 * -------------------------------------------------------------------------------------------------------
	 * 
	 * OUTPUT
	 * *********
	 * codiceAvviso 			-> Codice Avviso creato da PiemontePay
	 * codiceEsito  			-> Esito codificato dei controlli della chiamata
	 * descrizioneEsito			-> Esito descrittivo che dettaglia il precedente
	 * identificativoPagamento	-> È un identificativo univoco: unico vincolo è la sua univocità ad ogni invocazione 
	 * 							   (viene deciso dal gestionale chiamante e utilizzato per ricondurre alla posizione debitoria).
	 * 							   Servirà a identificare la posizione sulla quale è stato effettuato il pagamento e verrà restituito nell'esito, 
	 *     						   permettendo al chiamante di risalire alla stessa.
	 *     
	 * iuv						-> IUV corrispondente alla posizione debitoria	  
	 */
	public CreateDebtPositionResponse createDebtPosition(CreateDebtPositionRequest createDebtPositionRequest) throws IntegrationException;
	
	/*
	 * Il servizio permette di effettuare la generazione / download del bollettino (avviso di pgamento)	 	  
	 */
	public GeneraAvvisoPagamentoResponse generaAvvisoPagamento(String iuv) throws IntegrationException;
	
	/*
	 * Il servizio restituisce l'url di redirect PAGOPA dove effettuare il pagamento	 	  
	 */
	public PaymentResponse riceviPagoPAURLByIUV(String iuv) throws IntegrationException;
	
	/*
	 * Il servizio permette di verifcare lo stato di una posizione debitoria	 	  
	 */
	public CheckDebtPositionsStatusResponse checkDebtPosition(String iuv) throws IntegrationException;

}
