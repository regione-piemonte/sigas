/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;

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
	
	public static Date atEndOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    return calendar.getTime();
	}
	
	public static Date atStartOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
	
	@Async
	public  static void sendMail (String dest, String mitt, String oggetto, String testoEmail, String mailSmtpHost, String mailSmtpPort)
		      throws MessagingException
		  {
		    // Creazione di una mail session
		    Properties props = new Properties();
		    props.put("mail.smtp.host", mailSmtpHost);
		    props.put("mail.smtp.port", mailSmtpPort);
		    Session session = Session.getDefaultInstance(props);

		    // Creazione del messaggio da inviare
		    MimeMessage message = new MimeMessage(session);
		    message.setSubject(oggetto);
		    message.setText(testoEmail);

		    // Aggiunta degli indirizzi del mittente e del destinatario
		    InternetAddress fromAddress = new InternetAddress(mitt);
		    InternetAddress toAddress = new InternetAddress(dest);
		    message.setFrom(fromAddress);
		    message.setRecipient(Message.RecipientType.TO, toAddress);

		    // Invio del messaggio
		    Transport.send(message);
		  }

}
