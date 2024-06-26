/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

// INSERIRE QUA ERROR CODE BE
public class ErrorCodes {

	// GENERIC ERRORS
	public static String KO = "KO";
	public static String ACCESS_DENIED = "ACCESS DENIED";
	public static String SESSIONE_NON_VALIDA = "SESSION NOT VALID";
	public static String ACCESS_DENIED_ON_LOGIN = "ACCESS DENIED ON LOGIN";
	public static String NOT_AUTHENTICATED = "NOT AUTHENTICATED";
	public static String BAD_REQUEST_EXCEPTION_RESTEASY = "BAD REQUEST EXCEPTION RESTEASY";
	public static String IO_EXCEPTION = "IO EXCEPTION";
	public static String SYSTEM_EXCEPTION = "SYSTEM EXCEPTION";

	// IMPORT FILE ERROR
	public static String FILE_NOT_VALID = "FILE NOT VALID";
	
	// SERVICE VALIDATION ERRORS
	public static String FIELD_IS_NULL_OR_EMPTY = "FIELD IS NULL OR EMPTY";

	// BUSINESS EXCEPTIONS
	public static String BUSSINESS_EXCEPTION = "BUSSINESS EXCEPTION";

	// BADREQUEST EXCEPTION
	public static String BAD_REQUEST_EXCEPTION = "BAD REQUEST EXCEPTION";

	// RESTEASY EXCEPTION
	public static String RESTEASY_INVALID_PARAM_EXCEPTION = "RESTEASY INVALID PARAM EXCEPTION";
	
	public static final String LEGALE_RAPPRESENTANTE_CENSITO = "LRC";

	//DOCUMENT EXCEPTION
	public static final String DOC_GEN_EXCEPTION= "docGenericException"; //Si � verificato un errore durante il caricamento della documentazione
	public static final String DOC_SIGN_EXCEPTION= "docSignException"; //Errore nel processo di verifica delle firme
	
	
}
