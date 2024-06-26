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
	
}
