/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.util;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
//import org.apache.tika.Tika;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfAcroForm;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;

//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.PageSize;
//import com.lowagie.text.Paragraph;
//import com.lowagie.text.pdf.AcroFields;
//import com.lowagie.text.pdf.PdfAcroForm;
//import com.lowagie.text.pdf.PdfDictionary;
//import com.lowagie.text.pdf.PdfName;
//import com.lowagie.text.pdf.PdfReader;
//import com.lowagie.text.pdf.PdfSignatureAppearance;
//import com.lowagie.text.pdf.PdfStamper;
//import com.lowagie.text.pdf.PdfString;
//import com.lowagie.text.pdf.PdfWriter;

import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.integration.doqui.utils.XmlSerializer;




public class DocumentUtils {
	
	protected static Logger logger = Logger.getLogger(DocumentUtils.class);
	
	/*
	 * in caso di file .p7m, oppure .pdf restituisce true se risulta firmato digitalmente 
	 */
	public static boolean isDocumentSigned (byte[] document, String nomeFile) {
		
		boolean ret = false;
		if(nomeFile != null && nomeFile.toUpperCase().endsWith(".P7M")) {
			return isP7MSigned(nomeFile, document);
		}
		
		// TODO PP - 20200708 PP- aggiungere controllo firma (PAdES) in caso di file .pdf con lib openPDF
		//20200726_ET aggiunta verifica pdf firmati, DA TESTARE CON PDF FIRMATI!!!!
		else if(document!=null && nomeFile!=null && nomeFile.toUpperCase().endsWith(".PDF")) {			
			try {
				PdfReader reader = new PdfReader(document);
				AcroFields fields = reader.getAcroFields();
				ArrayList signatureNames = fields.getSignatureNames();
				if(signatureNames != null && !signatureNames.isEmpty()) {
					return true;
				}
			} catch (IOException e) {
//				TODO: che si deve fare? rilancio una eccezione oppure vado avanti?
				logger.error("Non e' stato possibile verificare se il pdf e' firmato", e);
			}
			
		}
		return ret;
		
	}
	
	public static String getMimeTypeFomFilename(String fileName, String estensione) {
		if(StringUtils.isBlank(fileName)) 
			return null;

		Path path = new File(fileName).toPath();
		logger.info("getMimeTypeFomFilename" + ">>>>>>MARTS PATH: " + XmlSerializer.objectToXml(path));
		String mimeType;
		try {
			
			if(estensione.equalsIgnoreCase("p10")) {
				mimeType = "application/pkcs10";
			} else if(estensione.equalsIgnoreCase("p12")) {
				mimeType = "application/pkcs-12";
			} else if(estensione.equalsIgnoreCase("p7a")) {				
				mimeType = "application/x-pkcs7-signature";
			} else if(estensione.equalsIgnoreCase("p7c")||
					  estensione.equalsIgnoreCase("p7m")) 
			{
				mimeType = "application/pkcs7-mime";
			} else if(estensione.equalsIgnoreCase("p7r")) {
				mimeType = "application/x-pkcs7-certreqresp"; 
			} else if(estensione.equalsIgnoreCase("p7s")) {
				mimeType = "application/pkcs7-signature"; 
			} else {
				File file = new File(fileName);
			    FileNameMap fileNameMap = URLConnection.getFileNameMap();
			    mimeType = fileNameMap.getContentTypeFor(file.getName());
			}			
		    		    
			/* Possibile altra libreria 
			File file = new File(fileName);
		    MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		    mimeType = fileTypeMap.getContentType(file.getName());
		    */
		    
			
		    //OLD CODE
			//mimeType = Files.probeContentType(path);		    
		 
		    
			logger.info("getMimeTypeFomFilename" + ">>>>>>MARTS MIME TYPE: " + mimeType);
		//} catch (IOException e) {
		} catch (Exception e) {
			throw new BusinessException("Mime type non estratto");
		}

		return mimeType;
	}
	
	// controlli firma in sperimentazione
	
	private static void bigFileSignature(byte[] pdf) throws DocumentException, IOException {
        checkSignature(pdf);
        checkSignature(enlarge(pdf, 100001));
        checkSignature(enlarge(pdf, 16777217)); // must be odd, as only the last bit is lost
    }

    private static boolean checkSignature(byte[] pdf) throws IOException {
    	boolean ret = false;
        byte[] produced = fakeSignature(pdf);
        PdfReader r = null;
        try {
        	r = new PdfReader(produced);
//            boolean b = r.getAcroFields().signatureCoversWholeDocument("Signature1");
            boolean b = r.getAcroFields().signatureCoversWholeDocument("mysig");
            System.out.println(b);
        }catch(Throwable t) {
        	if(r!=null)r.close();
        }
        return ret;
    }

    private static byte[] fakeSignature(byte[] pdf) throws IOException {
    	PdfReader reader = null;; 
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        	reader = new PdfReader(pdf); 
            PdfStamper stp = PdfStamper.createSignature(reader, baos, '\0', null, true);

            PdfSignatureAppearance sap = stp.getSignatureAppearance();

            PdfDictionary dic = new PdfDictionary();
            dic.put(PdfName.FILTER, PdfName.ADOBE_PPKLITE);

            sap.setCryptoDictionary(dic);
            sap.setCertificationLevel(2);
            sap.setReason("Test");

            Map<PdfName, Integer> exc = new HashMap<>();
            exc.put(PdfName.CONTENTS, 10);
            sap.preClose((HashMap) exc);

            PdfDictionary update = new PdfDictionary();
            update.put(PdfName.CONTENTS, new PdfString("aaaa").setHexWriting(true));
            sap.close(update);
			
            return baos.toByteArray();
        } catch (DocumentException e) {
        	if(reader!=null)reader.close();
			//e.printStackTrace();
			return null;
		}
    }

    private static byte[] enlarge(byte[] pdf, int size) {
        int trailerSize = 23;
        byte[] out = new byte[size];
        Arrays.fill(out, (byte) '\n'); // fill with newlines
        System.arraycopy(pdf, 0, out, 0, pdf.length - trailerSize);
        System.arraycopy(pdf, pdf.length - trailerSize, out, out.length - trailerSize, trailerSize);
        return out;
    }    

    public static boolean isP7MSigned(final String fileName, final byte[] p7bytes) {
    	
        try {

            if (p7bytes == null)
                return false;

            if (!fileName.toUpperCase().endsWith(".P7M")) {
                return false;
            }

            // This is where I get the exception
            CMSSignedData cms = new CMSSignedData(p7bytes); 

            if (cms.getSignedContent() == null) {
                logger.error("Unable to find signed Content during decoding from P7M for file: " + fileName);               
                return false;
            }

        } catch (CMSException e) {
            logger.error("CMSException from P7M for file: " + fileName, e);
            return false;
        }
        
        
        return true;
    }
		
	public static void main(String[] args) {
	        
		String fileName = "rapporto_signed.pdf";
	        System.out.println("Signature");
	        
	        // step 1: creation of a document-object
	        Document document = new Document(PageSize.A4);
	        
	        try {
	            
	            // step 2:
	            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
	            
	            // step 3: we open the document
	            document.open();
	            
	            // step 4:
	            PdfAcroForm acroForm = writer.getAcroForm();
	            document.add(new Paragraph("Hello World"));
	            acroForm.addSignature("mysig", 73, 705, 149, 759);
	            
	        }
	        catch(DocumentException | IOException de) {
	            System.err.println(de.getMessage());
	        }
	
	        // step 5: we close the document
	        document.close();
	        
	        
	        Path pdfPath = Paths.get(fileName);
	        try {
				byte[] pdf = Files.readAllBytes(pdfPath);
				bigFileSignature(pdf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
	        
	    }
	}
