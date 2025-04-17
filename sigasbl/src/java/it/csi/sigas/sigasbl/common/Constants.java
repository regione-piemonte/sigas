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
	
	public static final String OWNER_UPDATE_SOGGETTO_ANAG = "OPERATORE";	
	public static final String QUERY_LIKE_WILDCARDS = "%";
	
	public static final int MAX_RESULT_SIZE_RICERCA = 750;
	
	public static final String COD_REPORT_RICHIESTA_DEP_CAUSIONALE = "PDF_RICHIESTA_DEPOSITO_CAUSIONALE";
	public static final String RICHIESTA_DEP_CAUSIONALE_FILE_NAME = "Ricevuta di pagamento";
	public static final String RICHIESTA_DEP_CAUSIONALE_MINE_TYPE = "application/pdf";
	public static final String COD_REPORT_APPROVAZIONE_DEP_CAUSIONALE = "PDF_APPROVAZIONE_DEPOSITO_CAUSIONALE";
	public static final String APPROVAZIONE_DEP_CAUSIONALE_FILE_NAME = "Lettera di approvazione deposito cauzionale";
	public static final String APPROVAZIONE_DEP_CAUSIONALE_MINE_TYPE = "application/pdf";	
	
	public static final String RICHIESTA_DEP_CAUSIONALE_CODICE_TUTTE_PROVINCE = "ZZ";
	public static final Integer RICHIESTA_DEP_CAUSIONALE_ID_FITTIZIO_TUTTE_PROVINCE = 99;
	
	public static final Integer RICHIESTA_DEP_CAUSIONALE_ID_TIPO_CARRELLO_PAGAMENTO = 5;
	public static final Integer RICHIESTA_DEP_CAUSIONALE_INTEGRAZIONE_ID_TIPO_CARRELLO_PAGAMENTO = 6;
	
	public static final Integer RICHIESTA_DEP_CAUSIONALE_ID_TIPO_PAGAMENTO = 1;
	public static final String RICHIESTA_DEP_CAUSIONALE_CODICE_TIPO_DOCUMENTO = "DEPO";
	public static final String RICHIESTA_DEP_CAUSIONALE_INTEGRAZIONE_CODICE_TIPO_DOCUMENTO = "DEPO_INT";
	public static final Integer RICHIESTA_DEP_CAUSIONALE_ID_COD_STATO_LETTERA_RISPOSTA = 5;
	
	
	public static final String OWNER_UPDATE_VERSAMENTI_FUSIONE = "FUSIONE";
	
}
