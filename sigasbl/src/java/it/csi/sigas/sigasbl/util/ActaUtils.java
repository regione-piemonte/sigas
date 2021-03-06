/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.doqui.acta.acaris.archive.AcarisRepositoryEntryType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.archive.EnumRelationshipDirectionType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.archive.EnumRelationshipObjectType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.archive.RelationshipPropertiesType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.backoffice.ClientApplicationInfo;
import it.csi.sigas.sigasbl.doqui.acta.acaris.backofficeservice.BackOfficeService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.AcarisContentStreamType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.CodiceFiscaleType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.EnumPropertyFilter;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.EnumQueryOperator;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.EnumStreamId;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.IdAOOType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.IdNodoType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.IdStrutturaType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.ObjectIdType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.PagingResponseType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.PrincipalIdType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.PropertyFilterType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.PropertyType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.QueryConditionType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.QueryableObjectType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.navigationservice.NavigationService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.objectservice.ObjectService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.relationshipsservice.RelationshipsService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.repositoryservice.RepositoryService;

//Esempio di recupero di repositoryId attraverso il nome del repository e il relativo PrincipalId
//Ricerca di un protocollo attraverso anno e numero di protocollo su di una determinata AOO
//Recupero dei documenti associati e download

public class ActaUtils {

	private String protocollo;
	private String anno;
	private long idAOOvalue;
	private ObjectService objeSer;
	private ObjectIdType repositoryId;
	private PrincipalIdType principalId;
	private QueryableObjectType target;
	private NavigationService naviSer;
	private RelationshipsService relaSer;
	private List<RelationshipPropertiesType> relationshipPropertiesType;
	private String uuid;
	private String descrizione;
	private String filename;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	private void commonRetrive(String anno, String protocollo) throws MalformedURLException {
		try {
			InputStream is = getClass().getResourceAsStream("/application.properties");
			Properties p=new Properties();  
			p.load(is);

			String endpoint=p.getProperty("endpoint");
			String repoName = p.getProperty("repoName");
			String appKey = p.getProperty("appKey");

			//Valori per la query
			this.protocollo = protocollo;
			this.anno = anno;

			CodiceFiscaleType idUtente= new CodiceFiscaleType();
			String id = p.getProperty("idUtente");
			idUtente.setValue(id);
			IdAOOType idAOO = new IdAOOType();
			long idAOOvalue = Long.parseLong(p.getProperty("idAOO"));
			idAOO.setValue(idAOOvalue);
			IdStrutturaType idStruttura= new IdStrutturaType();
			long idStrutturaValue = Long.parseLong(p.getProperty("idStruttura"));
			idStruttura.setValue(idStrutturaValue);
			IdNodoType idNodo= new IdNodoType();
			long idNodoValue = Long.parseLong(p.getProperty("idNodo"));
			idNodo.setValue(idNodoValue);

			this.target = new QueryableObjectType();
			String targetObject = p.getProperty("targetObject");
			target.setObject(targetObject);

			String repoWS = p.getProperty("repoWS");
			String backWS = p.getProperty("backWS");
			RepositoryService repoSer = new RepositoryService(new URL(endpoint+repoWS));
			BackOfficeService backSer = new BackOfficeService(new URL(endpoint+backWS));
			this.objeSer = new ObjectService(new URL(endpoint+"objectWS?wsdl"));
			// abilito MTOM per il multipart relativo al download dei documenti
			String namespaceURI = p.getProperty("namespaceURI");
			String localPart = p.getProperty("localPart");
			String endpointAddress = p.getProperty("endpointAddress");
			objeSer.addPort(new QName(namespaceURI, localPart),SOAPBinding.SOAP11HTTP_MTOM_BINDING,endpoint+endpointAddress);
			String naviWsdlLocation = p.getProperty("naviWsdlLocation");
			String relaWsdlLocation = p.getProperty("relaWsdlLocation");
			naviSer = new NavigationService(new URL(endpoint+naviWsdlLocation));
			relaSer = new RelationshipsService(new URL(endpoint+relaWsdlLocation));

			try {
				// Get RepositoryId from name
				this.repositoryId = null;
				List<AcarisRepositoryEntryType> resp=repoSer.getRepositoryServicePort().getRepositories();
				for(AcarisRepositoryEntryType aret : resp){
					if (aret.getRepositoryName().equals(repoName))
						repositoryId=aret.getRepositoryId();
				}
				System.out.println("repositoryId = "+ repositoryId.getValue());

				// Get PrincipalId
				this.principalId = null;
				ClientApplicationInfo clientApplicationInfo = new ClientApplicationInfo();
				clientApplicationInfo.setAppKey(appKey);;
				principalId=backSer.getBackOfficeServicePort().getPrincipalExt(repositoryId, idUtente, idAOO, idStruttura, idNodo, clientApplicationInfo).get(0).getPrincipalId();
			} catch (it.csi.sigas.sigasbl.doqui.acta.acaris.repositoryservice.AcarisException e) {
				e.printStackTrace();
			} catch (it.csi.sigas.sigasbl.doqui.acta.acaris.backofficeservice.AcarisException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//################# Servizio per memorizzare a DB il protocollo inserito dall'operatore solo se ha tornato un risultato, l'oggetto al posto del nome del file e l'UUID sulla falsa riga di INDEX			
	public String saveData(String anno, String protocollo) {
		try {
			InputStream is = getClass().getResourceAsStream("/application.properties");
			Properties p=new Properties();  
			p.load(is);
			
			commonRetrive(anno, protocollo);		
			
			String[] params = protocollo.split("/");

			if (params.length < 2)
				throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
			
			// query on anno, protocollo e tipo
			PropertyFilterType filter = new PropertyFilterType();
			filter.setFilterType(EnumPropertyFilter.ALL);

			QueryConditionType criteriaProtocollo = new QueryConditionType();
			criteriaProtocollo.setPropertyName(p.getProperty("codice"));
			criteriaProtocollo.setOperator(EnumQueryOperator.EQUALS);
			criteriaProtocollo.setValue(params[0]);
			QueryConditionType criteriaAnno = new QueryConditionType();
			criteriaAnno.setPropertyName(p.getProperty("anno"));
			criteriaAnno.setOperator(EnumQueryOperator.EQUALS);
			criteriaAnno.setValue(params[1]);
			QueryConditionType criteriaidAooProtocollante = new QueryConditionType();
			criteriaidAooProtocollante.setPropertyName(p.getProperty("idAooProtocollante"));
			criteriaidAooProtocollante.setOperator(EnumQueryOperator.EQUALS);
			
			criteriaidAooProtocollante.setValue(p.getProperty("idAOO"));
			List<QueryConditionType> criteria = Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante);

			PagingResponseType paging = objeSer.getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
			List<PropertyType> propertyTypes = paging.getObjects().get(0).getProperties(); // prendo il primo elemento poichè per il criterio di ricerca mi aspetto un unico risultato

			//cerco la properties con objectIdClassificazione usato per la getChildren come folderID
			ObjectIdType folderId = new ObjectIdType();
			for (PropertyType pt:propertyTypes) {
				System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
				if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
					folderId.setValue(pt.getValue().getContent().get(0));
				}
			}
			System.out.println("objectIdClassificazione=" + folderId.getValue());

			//getChildren per prendere il documentId
			PagingResponseType pagingChildren = naviSer.getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);

			ObjectIdType objectId =pagingChildren.getObjects().get(0).getObjectId(); //usato come objectId per la getRelationShips ( cerca tutte le relazioni associate al protocollo compresi gli allegati) 

			//Ricavo la descrizione del file(oggetto) e data. Ciclo per trovare l'objectType DocumentoSemplicePropertiesType con properties "oggetto" e "changeToken"
			for (PropertyType pt:pagingChildren.getObjects().get(0).getProperties()){
				if (pt.getQueryName().getClassName().equals("DocumentoSemplicePropertiesType") && pt.getQueryName().getPropertyName().equals("oggetto")){  // da memorizzare sul DB al posto del nome del file
					System.out.println("Descrizione File: "+  pt.getValue().getContent().get(0));
					descrizione = pt.getValue().getContent().get(0);
				}
			}
			System.out.println("objectId getChildren= " +objectId.getValue());

			//getRelationShip per prendere lo streamId dal targetId
			relationshipPropertiesType =relaSer.getRelationshipsServicePort().getObjectRelationships(repositoryId, principalId, objectId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, filter);			

			//getStreamContent per prendere il nome del file
			List<AcarisContentStreamType> contentStream= objeSer.getObjectServicePort().getContentStream(repositoryId, relationshipPropertiesType.get(0).getTargetId(), principalId, EnumStreamId.PRIMARY);
			filename = contentStream.get(0).getFilename();
			System.out.println("filename = " + filename);
			
			System.out.println("documentId="+relationshipPropertiesType.get(0).getTargetId().getValue()); // da memorizzare su DB come UUID di INDEX

			uuid = relationshipPropertiesType.get(0).getTargetId().getValue();

			//restituire una stringa 'success' e poi recuperare le 3 variabili private tramite getters
			if(null != uuid && null != descrizione && null != protocollo) {
				return "success";
			}
		} catch(it.csi.sigas.sigasbl.doqui.acta.acaris.relationshipsservice.AcarisException e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non contiene allegati", ErrorCodes.BUSSINESS_EXCEPTION);
		} catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
		return "Error";
	}



	//###################### Altro servizio per fare solo il download prendendo il targetId dal DB
	public byte[] download(String anno, String protocollo) {
		try {
			
			InputStream is = getClass().getResourceAsStream("/application.properties");
			Properties p=new Properties();  
			p.load(is);
			
			PropertyFilterType filter = new PropertyFilterType();
			filter.setFilterType(EnumPropertyFilter.ALL);
			
			commonRetrive(anno, protocollo);
			
			QueryConditionType criteriaProtocollo = new QueryConditionType();
			criteriaProtocollo.setPropertyName(p.getProperty("codice"));
			criteriaProtocollo.setOperator(EnumQueryOperator.EQUALS);
			criteriaProtocollo.setValue(protocollo);
			QueryConditionType criteriaAnno = new QueryConditionType();
			criteriaAnno.setPropertyName(p.getProperty("anno"));
			criteriaAnno.setOperator(EnumQueryOperator.EQUALS);
			criteriaAnno.setValue(anno);
			QueryConditionType criteriaidAooProtocollante = new QueryConditionType();
			criteriaidAooProtocollante.setPropertyName(p.getProperty("idAooProtocollante"));
			criteriaidAooProtocollante.setOperator(EnumQueryOperator.EQUALS);
			criteriaidAooProtocollante.setValue(p.getProperty("idAOO"));
			List<QueryConditionType> criteria = Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante);
			
			PagingResponseType paging = objeSer.getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
			List<PropertyType> propertyTypes = paging.getObjects().get(0).getProperties();
			
			
			ObjectIdType folderId = new ObjectIdType();
			for (PropertyType pt:propertyTypes) {
				System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
				if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
					folderId.setValue(pt.getValue().getContent().get(0));
				}
			}
			
			PagingResponseType pagingChildren = naviSer.getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
			ObjectIdType objectId =pagingChildren.getObjects().get(0).getObjectId();
	
			relationshipPropertiesType =relaSer.getRelationshipsServicePort().getObjectRelationships(repositoryId, principalId, objectId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, filter);
			
			//getStreamContent per fare il download
			List<AcarisContentStreamType> contentStream= objeSer.getObjectServicePort().getContentStream(repositoryId, relationshipPropertiesType.get(0).getTargetId(), principalId, EnumStreamId.PRIMARY);
			System.out.println(contentStream.get(0).getFilename());
	
			//salvo il file
			DataHandler datadown=contentStream.get(0).getStreamMTOM();
			
			byte[] bData = IOUtils.toByteArray(datadown.getInputStream());
			
			return bData;
		  
		}  catch(it.csi.sigas.sigasbl.doqui.acta.acaris.relationshipsservice.AcarisException e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non contiene allegati", ErrorCodes.BUSSINESS_EXCEPTION);
		} catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}
		
	public void checkProtocollo(String anno, String protocollo) {
		try {
			
			InputStream is = getClass().getResourceAsStream("/application.properties");
			Properties p=new Properties();  
			p.load(is);
			
			commonRetrive(anno, protocollo);
			
			String[] params = protocollo.split("/");

			if (params.length < 2)
				throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
			
			// query on anno, protocollo e tipo
			PropertyFilterType filter = new PropertyFilterType();
			filter.setFilterType(EnumPropertyFilter.ALL);

			QueryConditionType criteriaProtocollo = new QueryConditionType();
			criteriaProtocollo.setPropertyName(p.getProperty("codice"));
			criteriaProtocollo.setOperator(EnumQueryOperator.EQUALS);
			criteriaProtocollo.setValue(params[0]);
			QueryConditionType criteriaAnno = new QueryConditionType();
			criteriaAnno.setPropertyName(p.getProperty("anno"));
			criteriaAnno.setOperator(EnumQueryOperator.EQUALS);
			criteriaAnno.setValue(params[1]);
			QueryConditionType criteriaidAooProtocollante = new QueryConditionType();
			criteriaidAooProtocollante.setPropertyName(p.getProperty("idAooProtocollante"));
			criteriaidAooProtocollante.setOperator(EnumQueryOperator.EQUALS);
			criteriaidAooProtocollante.setValue(p.getProperty("idAOO"));
			List<QueryConditionType> criteria = Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante);

			PagingResponseType paging = objeSer.getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
			List<PropertyType> propertyTypes = paging.getObjects().get(0).getProperties(); // prendo il primo elemento poichè per il criterio di ricerca mi aspetto un unico risultato

			//cerco la properties con objectIdClassificazione usato per la getChildren come folderID
			ObjectIdType folderId = new ObjectIdType();
			for (PropertyType pt:propertyTypes) {
				System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
				if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
					folderId.setValue(pt.getValue().getContent().get(0));
				}
			}
			System.out.println("objectIdClassificazione=" + folderId.getValue());
			
		} catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}
}
