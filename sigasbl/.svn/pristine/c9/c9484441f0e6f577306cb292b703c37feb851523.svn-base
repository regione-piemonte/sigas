/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Utilities {

	/**
	 * Il versamento a conguaglio deve essere effettuato entro il mese di Marzo dell'anno successivo
			   a quello cui si riferisce la dichiarazione
	 * @param annualita dichiarazione
	 * @return max dataVersamentoAConguaglio
	 */
	public static Date dataVersamentoConguaglio(int annualita) {
		Calendar cal = Calendar.getInstance();
		Date date = new Date(); 
		cal.set(Calendar.YEAR, annualita + 1);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();
		return date;
	}

	public static Date azzeraOraMinuti(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();
		return date;
	}
	
	public static String nullOrUppercase(String s) {
		return StringUtils.isBlank(s) ? null : s.toUpperCase();

	}

}
