/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.model.repositories.SigasCMessaggiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.service.impl.DocumentazioneServiceImpl;
import it.csi.sigas.sigasbl.util.DocumentUtils;
import it.doqui.index.ecmengine.client.webservices.dto.Node;
import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Mimetype;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Property;
import it.doqui.index.ecmengine.client.webservices.dto.engine.security.EnvelopedContent;
import it.doqui.index.ecmengine.client.webservices.dto.engine.security.Signature;
import it.doqui.index.ecmengine.client.webservices.dto.engine.security.VerifyReport;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegate;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegateServiceLocator;

public class UploadFile {
	protected static Logger log = Logger.getLogger(DocumentazioneServiceImpl.class);

	public String uploadDocumentazione(String filename, byte[] contentFile, String annualita, String codiceAzienda,
			SigasCParametroRepository sigasCParametroRepository, SigasCMessaggiRepository sigasCMessaggiRepository)
			throws IOException,BusinessException, javax.xml.rpc.ServiceException {

		String toReturn = "";
		String baseUrl = sigasCParametroRepository.findByDescParametro("INDEX_BASEURL").getValoreString();
		String urlWs1 = baseUrl.concat(sigasCParametroRepository.findByDescParametro("INDEX_URLWS1").getValoreString());

		EcmEngineWebServiceDelegateServiceLocator ecmengineLocator = new EcmEngineWebServiceDelegateServiceLocator();
		EcmEngineWebServiceDelegate ecmengineDelegate;

		ecmengineDelegate = ecmengineLocator.getEcmEngineManagement(new URL(urlWs1));

		OperationContext context = new OperationContext();
		context.setFruitore(sigasCParametroRepository.findByDescParametro("INDEX_FRUITORE").getValoreString());
		context.setNomeFisico(filename); // il nome del file da inserire, se non esiste indicare il nome fruitore
											// (comeda doc di integrazione)

		context.setRepository(sigasCParametroRepository.findByDescParametro("INDEX_REPOSITORY").getValoreString());
		context.setUsername(sigasCParametroRepository.findByDescParametro("INDEX_USERNAME").getValoreString());
		context.setPassword(sigasCParametroRepository.findByDescParametro("INDEX_PASSWORD").getValoreString());

		Node parentNode = new Node();
		parentNode.setUid(sigasCParametroRepository.findByDescParametro("INDEX_UIDPADRE").getValoreString()); // uid
																												// nodo
																												// padre
																												// cm:sigas
		Content content = new Content();
		content.setTypePrefixedName(
				sigasCParametroRepository.findByDescParametro("INDEX_TYPEPREFIXEDNAME").getValoreString());
		content.setModelPrefixedName(
				sigasCParametroRepository.findByDescParametro("INDEX_MODELPREFIXEDNAME").getValoreString());
		content.setParentAssocTypePrefixedName(
				sigasCParametroRepository.findByDescParametro("INDEX_PARENTASSOCTYPEPREFIXEDNAME").getValoreString());
		content.setPrefixedName("cm:" + annualita + "_" + codiceAzienda + "_" + new Date().getTime() + "_" + filename); // nome
																														// del
																														// file
		content.setContentPropertyPrefixedName(
				sigasCParametroRepository.findByDescParametro("INDEX_CONTENTPROPERTYPREFIXEDNAME").getValoreString());

		String estensione = StringUtils.substringAfterLast(filename, ".");
		Mimetype mt = new Mimetype();
		mt.setFileExtension(estensione);
		content.setMimeType(ecmengineDelegate.getMimetype(mt)[0].getMimetype());

		// content.setMimeType(mimeType);
		content.setOptimize(false);
		content.setEncoding("UTF-8");

		// Lettura File
		byte[] data = contentFile;
		content.setContent(data);

		if (DocumentUtils.isDocumentSigned(contentFile, filename)) {
			EnvelopedContent envelopedContent = new EnvelopedContent();
			envelopedContent.setData(contentFile);
			envelopedContent.setStore(false);
			VerifyReport result = ecmengineDelegate.verifyDocument(envelopedContent, context);
			if (result.getErrorCode() != 0) {
				toReturn = null;
				log.error("Errore nel processo di verifica delle firme!");
				throw new BusinessException(sigasCMessaggiRepository
						.findByDescChiaveMessaggio(ErrorCodes.DOC_SIGN_EXCEPTION).getValoreMessaggio(),
						ErrorCodes.DOC_SIGN_EXCEPTION);
			} else {
				for (Signature sign : result.getSignature()) {
					if (sign.getErrorCode() != 0) {
						toReturn = null;
						log.error("Sul documento sono presenti firme non valide!");
						throw new BusinessException(sigasCMessaggiRepository
								.findByDescChiaveMessaggio(ErrorCodes.DOC_SIGN_EXCEPTION).getValoreMessaggio(),
								ErrorCodes.DOC_SIGN_EXCEPTION);
					}
				}
			}
		}
		if (toReturn != null) {
			Node resNode = ecmengineDelegate.createContent(parentNode, content, context);
			toReturn = resNode.getUid();
		}

		return toReturn;

	}

	public void eliminaDocumentazioneIndex(String uid, SigasCParametroRepository sigasCParametroRepository) {
		// InputStream is = getClass().getResourceAsStream("/application.properties");
		// Properties p=new Properties();
		// p.load(is);
		//
		String toReturn = "";
		String baseUrl = sigasCParametroRepository.findByDescParametro("INDEX_BASEURL").getValoreString();
		String urlWs1 = baseUrl.concat(sigasCParametroRepository.findByDescParametro("INDEX_URLWS1").getValoreString());

		EcmEngineWebServiceDelegateServiceLocator ecmengineLocator = new EcmEngineWebServiceDelegateServiceLocator();
		EcmEngineWebServiceDelegate ecmengineDelegate;
		try {
			ecmengineDelegate = ecmengineLocator.getEcmEngineManagement(new URL(urlWs1));

			OperationContext context = new OperationContext();
			context.setFruitore(sigasCParametroRepository.findByDescParametro("INDEX_FRUITORE").getValoreString());

			context.setRepository(sigasCParametroRepository.findByDescParametro("INDEX_REPOSITORY").getValoreString());
			context.setUsername(sigasCParametroRepository.findByDescParametro("INDEX_USERNAME").getValoreString());
			context.setPassword(sigasCParametroRepository.findByDescParametro("INDEX_PASSWORD").getValoreString());

			Node parentNode = new Node();
			parentNode.setUid(uid); // uid elemento da cancellare

			ecmengineDelegate.deleteContent(parentNode, context);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String upload(String filename, byte[] contentFile, String annualita, String tipologiaComunicazione,
			String codiceAzienda) {
		try {
			InputStream is = getClass().getResourceAsStream("/application.properties");
			Properties p = new Properties();
			p.load(is);

			String toReturn = "";
			String baseUrl = p.getProperty("baseUrl");
			String urlWs1 = baseUrl.concat(p.getProperty("urlWs1"));

			EcmEngineWebServiceDelegateServiceLocator ecmengineLocator = new EcmEngineWebServiceDelegateServiceLocator();
			EcmEngineWebServiceDelegate ecmengineDelegate;
			try {
				ecmengineDelegate = ecmengineLocator.getEcmEngineManagement(new URL(urlWs1));

				OperationContext context = new OperationContext();
				context.setFruitore(p.getProperty("fruitore"));
				context.setNomeFisico(filename); // il nome del file da inserire, se non esiste indicare il nome
													// fruitore (comeda doc di integrazione)

				context.setRepository(p.getProperty("repository"));
				context.setUsername(p.getProperty("username"));
				context.setPassword(p.getProperty("password"));

				Node parentNode = new Node();
				parentNode.setUid(p.getProperty("uidPadre")); // uid nodo padre cm:sigas
				Content content = new Content();
				content.setTypePrefixedName(p.getProperty("typePrefixedName"));
				content.setModelPrefixedName(p.getProperty("modelPrefixedName"));
				content.setParentAssocTypePrefixedName(p.getProperty("parentAssocTypePrefixedName"));
				content.setPrefixedName("cm:" + annualita + "_" + codiceAzienda + "_" + filename); // nome del file
				content.setContentPropertyPrefixedName(p.getProperty("contentPropertyPrefixedName"));
				content.setMimeType("application/csv"); // si puo' prelevare dalla estensione del file
				content.setOptimize(false);
				content.setEncoding("UTF-8");

				// Lettura File
				byte[] data = contentFile;
				content.setContent(data);

				// Setting dei metadati rispetto al Model presente in INDEX
				Property[] props = new Property[2];
				props[0] = new Property();
				props[0].setPrefixedName(p.getProperty("prefixedName"));
				props[0].setDataType(p.getProperty("dataType"));
				props[0].setMultivalue(false);
				props[0].setValues(new String[] { tipologiaComunicazione });
				props[1] = new Property();
				props[1].setPrefixedName(p.getProperty("prefixedName2"));
				props[1].setDataType(p.getProperty("dataType2"));
				props[1].setMultivalue(false);
				props[1].setValues(new String[] { annualita });
				content.setProperties(props);
				Node resNode = ecmengineDelegate.createContent(parentNode, content, context);
				toReturn = resNode.getUid();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return toReturn;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "Error";

	}

	public String update(String filename, byte[] contentFile, String annualita, String tipologiaComunicazione,
			String codiceAzienda) {
		try {
			InputStream is = getClass().getResourceAsStream("/application.properties");
			Properties p = new Properties();
			p.load(is);

			String toReturn = "";
			String baseUrl = p.getProperty("baseUrl");
			String urlWs1 = baseUrl.concat(p.getProperty("urlWs1"));

			EcmEngineWebServiceDelegateServiceLocator ecmengineLocator = new EcmEngineWebServiceDelegateServiceLocator();
			EcmEngineWebServiceDelegate ecmengineDelegate;
			try {
				ecmengineDelegate = ecmengineLocator.getEcmEngineManagement(new URL(urlWs1));

				OperationContext context = new OperationContext();
				context.setFruitore(p.getProperty("fruitore"));
				context.setNomeFisico(filename); // il nome del file da inserire, se non esiste indicare il nome
													// fruitore (comeda doc di integrazione)

				context.setRepository(p.getProperty("repository"));
				context.setUsername(p.getProperty("username"));
				context.setPassword(p.getProperty("password"));

				Node parentNode = new Node();
				parentNode.setUid(p.getProperty("uidPadre")); // uid nodo padre cm:sigas
				Content content = new Content();
				content.setTypePrefixedName(p.getProperty("typePrefixedName"));
				content.setModelPrefixedName(p.getProperty("modelPrefixedName"));
				content.setParentAssocTypePrefixedName(p.getProperty("parentAssocTypePrefixedName"));
				content.setPrefixedName("cm:" + annualita + "_" + codiceAzienda + "_" + filename); // nome del file
				content.setContentPropertyPrefixedName(p.getProperty("contentPropertyPrefixedName"));
				content.setMimeType("application/csv"); // si puo' prelevare dalla estensione del file
				content.setOptimize(false);
				content.setEncoding("UTF-8");

				// Lettura File
				byte[] data = contentFile;
				content.setContent(data);

				// Setting dei metadati rispetto al Model presente in INDEX
				Property[] props = new Property[2];
				props[0] = new Property();
				props[0].setPrefixedName(p.getProperty("prefixedName"));
				props[0].setDataType(p.getProperty("dataType"));
				props[0].setMultivalue(false);
				props[0].setValues(new String[] { tipologiaComunicazione });
				props[1] = new Property();
				props[1].setPrefixedName(p.getProperty("prefixedName2"));
				props[1].setDataType(p.getProperty("dataType2"));
				props[1].setMultivalue(false);
				props[1].setValues(new String[] { p.getProperty(annualita) });
				content.setProperties(props);
				// Inserire la delete del documento su INDEX per poi creare quello aggiornato
//				ecmengineDelegate.deleteContent(parentNode, context);
				Node resNode = ecmengineDelegate.createContent(parentNode, content, context);
				toReturn = resNode.getUid();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return toReturn;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "Error";

	}

}
