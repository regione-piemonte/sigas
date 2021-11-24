/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.utils;

import org.apache.commons.lang3.StringUtils;

import it.csi.sigas.sigasbl.integration.doqui.DoquiConstants;



public class CleanUtil {

	
	public static String cleanNullValue(String value){
		return (StringUtils.equalsIgnoreCase(value, DoquiConstants.NULL_VALUE) ?  null : value);
	}
	

}
