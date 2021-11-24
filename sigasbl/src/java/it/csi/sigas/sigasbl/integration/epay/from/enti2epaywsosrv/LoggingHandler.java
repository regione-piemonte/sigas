package it.csi.sigas.sigasbl.integration.epay.from.enti2epaywsosrv;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {

private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public void close(MessageContext context) {
  }

  public boolean handleFault(SOAPMessageContext context) {
	logMessage(context, "SOAP Error is : ");
	return true;
  }

  public boolean handleMessage(SOAPMessageContext context) {
	logMessage(context, "SOAP Message is : ");
	return true;
  }

  public Set<QName> getHeaders() {
	return Collections.EMPTY_SET;
  }

  private boolean logMessage(MessageContext mc, String type) {
	try {
		if (logger.isDebugEnabled()) {
			logger.debug(type);
			SOAPMessage msg = ((SOAPMessageContext) mc)
			        .getMessage();
			
			// Print out the Mime Headers
			MimeHeaders mimeHeaders = msg.getMimeHeaders();
			Iterator mhIterator = mimeHeaders.getAllHeaders();
			MimeHeader mh;
			String header;
			logger.debug("  Mime Headers:");
			while (mhIterator.hasNext()) {
			    mh = (MimeHeader) mhIterator.next();
			    header = new StringBuffer(" Name: ")
			            .append(mh.getName()).append(" Value: ")
			            .append(mh.getValue()).toString();
			    logger.debug(header);
			}
			
			logger.debug(" SOAP Message: ");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			msg.writeTo(baos);
			logger.debug("   " + baos.toString());
			baos.close();
		}
		
		return true;
	} catch (Exception e) {
		if (logger.isErrorEnabled())
		    logger.error("Error logging SOAP message", e);
		
		return false;
	}
  }
}