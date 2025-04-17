/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.soap.util.mime.ByteArrayDataSource;
import org.springframework.scheduling.annotation.Async;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

//import com.lowagie.text.Document;
//import com.lowagie.text.pdf.PdfCopy;
//import com.lowagie.text.pdf.PdfReader;





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
		    //message.setContent(someHtmlMessage, "text/html; charset=utf-8");

		    // Aggiunta degli indirizzi del mittente e del destinatario
		    InternetAddress fromAddress = new InternetAddress(mitt);
		    InternetAddress toAddress = new InternetAddress(dest);
		    message.setFrom(fromAddress);
		    message.setRecipient(Message.RecipientType.TO, toAddress);		    

		    // Invio del messaggio
		    Transport.send(message);		    
		    
		  }	
	
	
	@Async
	public  static void sendMailWithAttach (String dest, String mitt, String oggetto, String testoEmail, 
											String mailSmtpHost, String mailSmtpPort, 
											byte[] attach, String attachName)
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
	    //message.setText(testoEmail);
	    
	    BodyPart messageBodyPart = new MimeBodyPart(); 
	    messageBodyPart.setText(testoEmail);
	    
	    MimeBodyPart attachmentPart = new MimeBodyPart(); 
	    ByteArrayDataSource bds = new ByteArrayDataSource(attach, "application/octet-stream"); 
	    attachmentPart.setDataHandler(new DataHandler(bds)); 
	    attachmentPart.setFileName(attachName);	    
	    
	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart);
	    multipart.addBodyPart(attachmentPart);	    
	    
	    message.setContent(multipart);
	
	    // Aggiunta degli indirizzi del mittente e del destinatario
	    InternetAddress fromAddress = new InternetAddress(mitt);
	    InternetAddress toAddress = new InternetAddress(dest);
	    message.setFrom(fromAddress);
	    message.setRecipient(Message.RecipientType.TO, toAddress);		    
	
	    // Invio del messaggio
	    Transport.send(message);		    
	    
	}
	
	private static MimeBodyPart createMimeBodyPart(byte[] attach, String attachName) throws MessagingException {
		 MimeBodyPart attachmentPart = new MimeBodyPart(); 
		 ByteArrayDataSource bds = new ByteArrayDataSource(attach, "application/octet-stream"); 
		 attachmentPart.setDataHandler(new DataHandler(bds)); 
		 attachmentPart.setFileName(attachName);
		 
		 return attachmentPart;
	}
	
	@Async
	public static void sendMailWithMultipleAttach (String dest, String mitt, String oggetto, String testoEmail, 
													String mailSmtpHost, String mailSmtpPort, 
													Map<String, byte[]> attachmentMap)
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
	    //message.setText(testoEmail);
	    
	    BodyPart messageBodyPart = new MimeBodyPart(); 
	    messageBodyPart.setText(testoEmail); 
	    	    
	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart);
	    if(attachmentMap != null && !attachmentMap.isEmpty()) {
	    	Set<Entry<String,byte[]>> set = attachmentMap.entrySet();
	    	Iterator<Entry<String,byte[]>> iterator = set.iterator();
	    	while(iterator.hasNext()) {
	    		Entry<String,byte[]> entry = iterator.next();
	    		multipart.addBodyPart(createMimeBodyPart(entry.getValue(), entry.getKey()));
	    	}    	
	    }	    
	    
	    message.setContent(multipart);
	
	    // Aggiunta degli indirizzi del mittente e del destinatario
	    InternetAddress fromAddress = new InternetAddress(mitt);
	    InternetAddress toAddress = new InternetAddress(dest);
	    message.setFrom(fromAddress);
	    message.setRecipient(Message.RecipientType.TO, toAddress);		    
	
	    // Invio del messaggio
	    Transport.send(message);		    
	    
	}
	
	
	public static byte[] mergePdfFiles(byte[] report, byte[] avvisoPagamento) {
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		List<byte[]> pdfBytesList = new ArrayList<>();
		
		try {
			
			pdfBytesList.add(report);
			pdfBytesList.add(avvisoPagamento);
			
			Document document = new Document();
			
			PdfCopy copy = new PdfCopy(document, outputStream);
			document.open();
			
			for (byte[] pdfByteArray : pdfBytesList) {
			
				ByteArrayInputStream readerStream = new ByteArrayInputStream(pdfByteArray);
		        PdfReader reader = new PdfReader(readerStream);
		        
		        
		        for (int i = 0; i < reader.getNumberOfPages(); ) {
		            copy.addPage(copy.getImportedPage(reader, ++i));
		        }
		        
		        copy.freeReader(reader);
		        reader.close();
	        
			}
			
			document.close();

		}catch(Exception e) {
			
		}
		
		return outputStream.toByteArray();
	}

}
