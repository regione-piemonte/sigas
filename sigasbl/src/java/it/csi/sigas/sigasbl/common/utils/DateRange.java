/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common.utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateRange implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Date azzeraOraMinuti(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();
		return date;
	}
	
	public static boolean intersect(DateContainer rng1, DateContainer rng2) {
		boolean cond = false;
		Date lb1 = azzeraOraMinuti(rng1.getValiditaStart());
		Date ub1 = azzeraOraMinuti(rng1.getValiditaEnd());
		Date lb2 = azzeraOraMinuti(rng2.getValiditaStart());
		Date ub2 = azzeraOraMinuti(rng2.getValiditaEnd());

		if (lb1 == null && ub1 == null || lb2 == null && ub2 == null) {
			return true;
		}
		if (lb1 != null && ub1 != null && lb2 != null && ub2 != null) {
			cond = !(lb1.after(ub2) || ub1.before(lb2));
		} else {
			if (lb1 == null && lb2 == null) {
				return true;
			}
			if (ub1 == null && ub2 == null) {
				return true;
			}
			if (lb1 == null) {
				cond = isInRange(rng2, ub1);
			} else if (ub1 == null) {
				cond = isInRange(rng2, lb1);
			} else if (lb2 == null) {
				cond = isInRange(rng1, ub2);
			} else if (ub2 == null) {
				cond = isInRange(rng1, lb2);
			}
		}
		return cond;
	}
	
	public static boolean intersectYear(Date rng1, Date rng2) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(rng1);
		int yearStart = cal.get(Calendar.YEAR);
		cal.setTime(rng2);
		int yearEnd = cal.get(Calendar.YEAR);
		if(yearStart==yearEnd)
			return true;
		else
			return false;
	}

	private static boolean isInRange(DateContainer curentTasso, Date ub12) {
		boolean l_res = true;
		boolean u_res = true;

		Calendar lb1 = Calendar.getInstance();
		lb1.setTime(curentTasso.getValiditaStart());

		Calendar ub1 = Calendar.getInstance();
		ub1.setTime(curentTasso.getValiditaEnd());

		Calendar cal = Calendar.getInstance();
		cal.setTime(ub12);

		if (cal.before(lb1)) {
			l_res = false;
		}
		if (cal.after(ub1)) {
			u_res = false;
		}
		return l_res && u_res;
	}

}
