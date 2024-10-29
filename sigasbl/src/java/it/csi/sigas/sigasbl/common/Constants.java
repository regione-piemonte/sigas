/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

public abstract class Constants {

	public static final String COMPONENT_NAME = "sigas";
	public static final String USERINFO_SESSIONATTR = "appDatacurrentUser";
	public static final String HANDLER_IRIDE_SRV = COMPONENT_NAME + ".integration.iride";
	public static final String HANDLER_SECURITY = COMPONENT_NAME + ".security";
	public static final String HANDLER_EXCEPTION = COMPONENT_NAME + ".ExceptionHandler";
	
	// MESSAGE REST
	public static final String MESSAGE_SUCCESS = "SUCCESS";

	
	public static final String irideAppId = "irideAppId";
	public static final String operatoreFO = "operatoreFO";
	public static final String operatoreBO = "operatoreBO";
	
	//SIGAS-225
	public static final String TESTO_ANNOTAZIONE_ART_65 = "Dichiarazione presentata ai sensi dell'art. 65, comma 1, lettera b) del CAD";
	
	public static final String AUTH_HEADER_ID = "Authorization";    
	public static final String AUTH_HEADER_PREFIX = "Basic";
	public static final String AUTH_HEADER_SEPARATOR = ":";
	public static final String AUTH_HEADER_BLANK_SPACE = " ";
	 
	public static final String COD_ESITO_PAGAMENTO_PPAY_OK    = "000";
	public static final String COD_ESITO_PAGAMENTO_PPAY_NON_AUTORIZZATO    = "400";
	public static final String ESITO_DONWLOAD_AVVISO_PAGAMENTO_IUV_SCADUTO    = "Non è possibile scaricare l'avviso di pagamento in quanto il relativo codice IUV è riferito ad un pagamento effettuato, annullato o in attesa di ricevuta oppure non più valido perché scaduto";
	
	public static final String RICEVUTA_PAGAMENTO_COD_REPORT = "PDF_RICEVUTA_PAGAMENTO";
	public static final String RICEVUTA_PAGAMENTO_FILE_NAME = "Ricevuta di pagemnto";
	public static final String RICEVUTA_PAGAMENTO_MINE_TYPE = "application/pdf";
	
	
	
}
