/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import it.doqui.index.ecmengine.client.webservices.dto.Node;
import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Property;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegate;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegateServiceLocator;

public class UploadFile {

	public String upload(String filename,byte[] contentFile, String annualita, String tipologiaComunicazione, String codiceAzienda) {
		try {
			InputStream is = getClass().getResourceAsStream("/application.properties");
			Properties p=new Properties();  
			p.load(is);

			String toReturn="";
			String baseUrl=p.getProperty("baseUrl");
			String urlWs1=baseUrl.concat(p.getProperty("urlWs1"));

			EcmEngineWebServiceDelegateServiceLocator ecmengineLocator = new EcmEngineWebServiceDelegateServiceLocator();
			EcmEngineWebServiceDelegate ecmengineDelegate;
			try {
				ecmengineDelegate = ecmengineLocator.getEcmEngineManagement(new URL(urlWs1));

				OperationContext context= new OperationContext();
				context.setFruitore(p.getProperty("fruitore"));
				context.setNomeFisico(filename); // il nome del file da inserire, se non esiste indicare il nome fruitore (comeda doc di integrazione)

				context.setRepository(p.getProperty("repository"));
				context.setUsername(p.getProperty("username")); 
				context.setPassword(p.getProperty("password"));

				Node parentNode= new Node();
				parentNode.setUid(p.getProperty("uidPadre"));  // uid nodo padre cm:sigas
				Content content = new Content();
				content.setTypePrefixedName(p.getProperty("typePrefixedName"));
				content.setModelPrefixedName(p.getProperty("modelPrefixedName"));
				content.setParentAssocTypePrefixedName(p.getProperty("parentAssocTypePrefixedName"));
				content.setPrefixedName("cm:"+annualita + "_" + codiceAzienda + "_" + filename );     //nome del file
				content.setContentPropertyPrefixedName(p.getProperty("contentPropertyPrefixedName"));
				content.setMimeType("application/csv");			// si puo' prelevare dalla estensione del file
				content.setOptimize(false);
				content.setEncoding("UTF-8");

				//Lettura File
				byte[] data=contentFile;
				content.setContent(data);

				// Setting dei metadati rispetto al Model presente in INDEX
				Property[] props =new Property[2];
				props[0] = new Property();
				props[0].setPrefixedName(p.getProperty("prefixedName"));
				props[0].setDataType(p.getProperty("dataType"));
				props[0].setMultivalue(false);
				props[0].setValues(new String [] {tipologiaComunicazione});
				props[1] = new Property();
				props[1].setPrefixedName(p.getProperty("prefixedName2"));
				props[1].setDataType(p.getProperty("dataType2"));
				props[1].setMultivalue(false);
				props[1].setValues(new String [] {annualita});
				content.setProperties(props);
				Node resNode=ecmengineDelegate.createContent(parentNode,  content, context);
				toReturn=resNode.getUid();			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return toReturn;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "Error";

	}

	public String update(String filename,byte[] contentFile, String annualita, String tipologiaComunicazione, String codiceAzienda) {
		try {
			InputStream is = getClass().getResourceAsStream("/application.properties");
			Properties p=new Properties();  
			p.load(is);

			String toReturn="";
			String baseUrl=p.getProperty("baseUrl");
			String urlWs1=baseUrl.concat(p.getProperty("urlWs1"));

			EcmEngineWebServiceDelegateServiceLocator ecmengineLocator = new EcmEngineWebServiceDelegateServiceLocator();
			EcmEngineWebServiceDelegate ecmengineDelegate;
			try {
				ecmengineDelegate = ecmengineLocator.getEcmEngineManagement(new URL(urlWs1));

				OperationContext context= new OperationContext();
				context.setFruitore(p.getProperty("fruitore"));
				context.setNomeFisico(filename); // il nome del file da inserire, se non esiste indicare il nome fruitore (comeda doc di integrazione)

				context.setRepository(p.getProperty("repository"));
				context.setUsername(p.getProperty("username")); 
				context.setPassword(p.getProperty("password"));

				Node parentNode= new Node();
				parentNode.setUid(p.getProperty("uidPadre"));  // uid nodo padre cm:sigas
				Content content = new Content();
				content.setTypePrefixedName(p.getProperty("typePrefixedName"));
				content.setModelPrefixedName(p.getProperty("modelPrefixedName"));
				content.setParentAssocTypePrefixedName(p.getProperty("parentAssocTypePrefixedName"));
				content.setPrefixedName("cm:"+annualita + "_" + codiceAzienda + "_" + filename );     //nome del file
				content.setContentPropertyPrefixedName(p.getProperty("contentPropertyPrefixedName"));
				content.setMimeType("application/csv");			// si puo' prelevare dalla estensione del file
				content.setOptimize(false);
				content.setEncoding("UTF-8");

				//Lettura File
				byte[] data=contentFile;
				content.setContent(data);

				// Setting dei metadati rispetto al Model presente in INDEX
				Property[] props =new Property[2];
				props[0] = new Property();
				props[0].setPrefixedName(p.getProperty("prefixedName"));
				props[0].setDataType(p.getProperty("dataType"));
				props[0].setMultivalue(false);
				props[0].setValues(new String [] {tipologiaComunicazione});
				props[1] = new Property();
				props[1].setPrefixedName(p.getProperty("prefixedName2"));
				props[1].setDataType(p.getProperty("dataType2"));
				props[1].setMultivalue(false);
				props[1].setValues(new String [] {p.getProperty(annualita)});
				content.setProperties(props);
				//Inserire la delete del documento su INDEX per poi creare quello aggiornato
//				ecmengineDelegate.deleteContent(parentNode, context);
				Node resNode=ecmengineDelegate.createContent(parentNode,  content, context);
				toReturn=resNode.getUid();			
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
