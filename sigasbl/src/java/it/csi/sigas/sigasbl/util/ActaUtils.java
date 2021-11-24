/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.doqui.acta.acaris.backofficeservice.BackOfficeService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.navigationservice.NavigationService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.objectservice.ObjectService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.relationshipsservice.RelationshipsService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.repositoryservice.RepositoryService;
import it.csi.sigas.sigasbl.integration.doqui.DoquiServiceFactory;
import it.csi.sigas.sigasbl.model.vo.home.AllegatoVO;
import it.doqui.acta.acaris.backofficeservice.AcarisException;
import it.doqui.acta.actasrv.dto.acaris.type.archive.AcarisRepositoryEntryType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumRelationshipDirectionType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.EnumRelationshipObjectType;
import it.doqui.acta.actasrv.dto.acaris.type.archive.RelationshipPropertiesType;
import it.doqui.acta.actasrv.dto.acaris.type.backoffice.ClientApplicationInfo;
import it.doqui.acta.actasrv.dto.acaris.type.common.AcarisContentStreamType;
import it.doqui.acta.actasrv.dto.acaris.type.common.CodiceFiscaleType;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumPropertyFilter;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumQueryOperator;
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumStreamId;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdAOOType;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdNodoType;
import it.doqui.acta.actasrv.dto.acaris.type.common.IdStrutturaType;
import it.doqui.acta.actasrv.dto.acaris.type.common.NavigationConditionInfoType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PagingResponseType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyFilterType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PropertyType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryConditionType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryNameType;
import it.doqui.acta.actasrv.dto.acaris.type.common.QueryableObjectType;

//Esempio di recupero di repositoryId attraverso il nome del repository e il relativo PrincipalId
//Ricerca di un protocollo attraverso anno e numero di protocollo su di una determinata AOO
//Recupero dei documenti associati e download

public class ActaUtils {
	
	
	private DoquiServiceFactory acarisServiceFactory;

	private String protocollo;
	private String anno;
	private long idAOOvalue;
	private ObjectService objeSer;
	private ObjectIdType repositoryId;
	private PrincipalIdType principalId;
	private QueryableObjectType target;
	private NavigationService naviSer;
	private RelationshipsService relaSer;
	private RelationshipPropertiesType[] relationshipPropertiesType;
	private String uuid;
	private String descrizione;
	private String filename;
	
	protected static Logger log = Logger.getLogger(ActaUtils.class);
	

	public ActaUtils(DoquiServiceFactory acarisServiceFactory) {
		super();
		this.acarisServiceFactory = acarisServiceFactory;
	}

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
//				List<AcarisRepositoryEntryType> resp=repoSer.getRepositoryServicePort().getRepositories();
				AcarisRepositoryEntryType[] resp=acarisServiceFactory.getAcarisService().getRepositoryServicePort().getRepositories();
				for(AcarisRepositoryEntryType aret : resp){
					if (aret.getRepositoryName().equals(repoName))
						repositoryId=aret.getRepositoryId();
				}
				System.out.println("repositoryId = "+ repositoryId.getValue());

				// Get PrincipalId
				this.principalId = null;
				ClientApplicationInfo clientApplicationInfo = new ClientApplicationInfo();
				clientApplicationInfo.setAppKey(appKey);;
				principalId=acarisServiceFactory.getAcarisService().getBackOfficeServicePort().getPrincipalExt(repositoryId, idUtente, idAOO, idStruttura, idNodo, clientApplicationInfo)[0].getPrincipalId();
			} catch (AcarisException e) {
				e.printStackTrace();
			} catch (it.doqui.acta.acaris.repositoryservice.AcarisException e) {
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
			QueryConditionType[] criteria = (QueryConditionType[]) Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante).toArray();

//			PagingResponseType paging = objeSer.getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
			PagingResponseType paging = acarisServiceFactory.getAcarisService().getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
			PropertyType[] propertyTypes = paging.getObjects(0).getProperties(); // prendo il primo elemento poichè per il criterio di ricerca mi aspetto un unico risultato

			//cerco la properties con objectIdClassificazione usato per la getChildren come folderID
			ObjectIdType folderId = new ObjectIdType();
			for (PropertyType pt:propertyTypes) {
				System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
				if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
					folderId.setValue(pt.getValue().getContent(0));
				}
			}
			System.out.println("objectIdClassificazione=" + folderId.getValue());

			//getChildren per prendere il documentId
//			PagingResponseType pagingChildren = naviSer.getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
			PagingResponseType pagingChildren = acarisServiceFactory.getAcarisService().getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
			

			ObjectIdType objectId =pagingChildren.getObjects(0).getObjectId(); //usato come objectId per la getRelationShips ( cerca tutte le relazioni associate al protocollo compresi gli allegati) 

			//Ricavo la descrizione del file(oggetto) e data. Ciclo per trovare l'objectType DocumentoSemplicePropertiesType con properties "oggetto" e "changeToken"
			for (PropertyType pt:pagingChildren.getObjects(0).getProperties()){
				if (pt.getQueryName().getClassName().equals("DocumentoSemplicePropertiesType") && pt.getQueryName().getPropertyName().equals("oggetto")){  // da memorizzare sul DB al posto del nome del file
					System.out.println("Descrizione File: "+  pt.getValue().getContent(0));
					descrizione = pt.getValue().getContent(0);
				}
			}
			System.out.println("objectId getChildren= " +objectId.getValue());

			//getRelationShip per prendere lo streamId dal targetId
//			relationshipPropertiesType =relaSer.getRelationshipsServicePort().getObjectRelationships(repositoryId, principalId, objectId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, filter);
			relationshipPropertiesType =acarisServiceFactory.getAcarisService().getRelationshipsServicePort().getObjectRelationships(repositoryId, principalId, objectId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, filter);

			//getStreamContent per prendere il nome del file
//			List<AcarisContentStreamType> contentStream= objeSer.getObjectServicePort().getContentStream(repositoryId, relationshipPropertiesType.get(0).getTargetId(), principalId, EnumStreamId.PRIMARY);
			AcarisContentStreamType[] contentStream= acarisServiceFactory.getAcarisService().getObjectServicePort().getContentStream(repositoryId, relationshipPropertiesType[0].getTargetId(), principalId, EnumStreamId.PRIMARY);
			filename = contentStream[0].getFilename();
			System.out.println("filename = " + filename);
			
			System.out.println("documentId="+relationshipPropertiesType[0].getTargetId().getValue()); // da memorizzare su DB come UUID di INDEX

			uuid = relationshipPropertiesType[0].getTargetId().getValue();

			//restituire una stringa 'success' e poi recuperare le 3 variabili private tramite getters
			if(null != uuid && null != descrizione && null != protocollo) {
				return "success";
			}
		} catch(it.doqui.acta.acaris.relationshipsservice.AcarisException e) {
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
//			List<QueryConditionType> criteria = Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante).to;
			QueryConditionType[] criteria = (QueryConditionType[]) Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante).toArray();
			
//			PagingResponseType paging = objeSer.getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
			NavigationConditionInfoType navigationLimits = null;
			Integer maxItems  = null;
			Integer skipCount = new Integer(0);
			PagingResponseType paging  = acarisServiceFactory.getAcarisService().getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , navigationLimits , maxItems, skipCount);
			PropertyType[] propertyTypes = paging.getObjects(0).getProperties();
			
			
			ObjectIdType folderId = new ObjectIdType();
			for (PropertyType pt:propertyTypes) {
				System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
				if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
					folderId.setValue(pt.getValue().getContent(0));
				}
			}
			
//			PagingResponseType pagingChildren = naviSer.getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
			PagingResponseType pagingChildren = acarisServiceFactory.getAcarisService().getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
			ObjectIdType objectId =pagingChildren.getObjects(0).getObjectId();
	
			relationshipPropertiesType =acarisServiceFactory.getAcarisService().getRelationshipsServicePort().getObjectRelationships(repositoryId, principalId, objectId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, filter);
			
			//getStreamContent per fare il download
//			List<AcarisContentStreamType> contentStream= objeSer.getObjectServicePort().getContentStream(repositoryId, relationshipPropertiesType.get(0).getTargetId(), principalId, EnumStreamId.PRIMARY);
			AcarisContentStreamType[] contentStream= acarisServiceFactory.getAcarisService().getObjectServicePort().getContentStream(repositoryId, relationshipPropertiesType[0].getTargetId(), principalId, EnumStreamId.PRIMARY);
	
			//salvo il file
			DataHandler datadown=contentStream[0].getStreamMTOM();
			
			byte[] bDataMaster = IOUtils.toByteArray(datadown.getInputStream());
			

			
			
			
			
			
			if(pagingChildren.getObjectsLength()>1) {
				List<AllegatoVO> filePacchetto = new ArrayList<AllegatoVO>();
				AllegatoVO fileMaster = new AllegatoVO();
				fileMaster.setFileName(contentStream[0].getFilename());
				fileMaster.setbData(bDataMaster);
				
				filePacchetto.add(fileMaster);	
			
				
				ObjectIdType gruppoAllegatiObj = pagingChildren.getObjects(1).getObjectId();
				PagingResponseType classificazioniAllegatiList = recuperaAllChildrens(repositoryId, principalId, gruppoAllegatiObj);
				ObjectResponseType[] classificazioniAllegati = classificazioniAllegatiList.getObjects();
				
				for (int j = 0; j < classificazioniAllegati.length; j++) {
					
					AllegatoVO allegato = new AllegatoVO();
	
					String idClassificazioneAllegato = classificazioniAllegati[j].getObjectId().getValue();
					ObjectIdType objectIdClassificazioneAllegato = new ObjectIdType();
					objectIdClassificazioneAllegato.setValue(idClassificazioneAllegato);
					
					
					ObjectResponseType documentoProtocollatoAllegato = recuperaChildren(repositoryId, principalId, objectIdClassificazioneAllegato, 0);
					
					if(documentoProtocollatoAllegato!=null) {
						ObjectIdType docProtocollatoAllegatoObj = documentoProtocollatoAllegato.getObjectId();
						if (docProtocollatoAllegatoObj != null) {
				
						
							// se 1 elemento allora doc standard, se >1 è doc multiplo
							List<ObjectIdType> contentStreamIdList = recuperaIdContentStream(repositoryId, principalId, docProtocollatoAllegatoObj);
						
							
							// 20200825_LC gestione nome del documento considerando pure doc multiplo
							if(contentStreamIdList != null && contentStreamIdList.size() > 0) {
								
								if (contentStreamIdList.size()==1) {
									// se size 1 c'è un solo doc fisico
									AcarisContentStreamType[] contentStreamAllegati = getContentStream(repositoryId, contentStreamIdList.get(0), principalId);	
									DataHandler datadownAllegati=contentStreamAllegati[0].getStreamMTOM();
									
									byte[] bDataAllegati = IOUtils.toByteArray(datadownAllegati.getInputStream());
									
									allegato.setFileName(contentStreamAllegati[0].getFilename());
									allegato.setbData(bDataAllegati);
								} 
							
							}
	
							
						}
					}
	
					
					filePacchetto.add(allegato);									
				
				}
				return filesZip(filePacchetto);
			}else {
				return bDataMaster;
			}
			
			
			
			
			
			
		  
		}  catch(it.doqui.acta.acaris.relationshipsservice.AcarisException e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non contiene allegati", ErrorCodes.BUSSINESS_EXCEPTION);
		} catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}
	
	
	
	//###################### Altro servizio per fare solo il download del documento master prendendo il targetId dal DB
			public byte[] downloadMaster(String anno, String protocollo) {
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
//					List<QueryConditionType> criteria = Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante).to;
					QueryConditionType[] criteria = (QueryConditionType[]) Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante).toArray();
					
//					PagingResponseType paging = objeSer.getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
					NavigationConditionInfoType navigationLimits = null;
					Integer maxItems  = null;
					Integer skipCount = new Integer(0);
					PagingResponseType paging  = acarisServiceFactory.getAcarisService().getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , navigationLimits , maxItems, skipCount);
					PropertyType[] propertyTypes = paging.getObjects(0).getProperties();
					
					
					ObjectIdType folderId = new ObjectIdType();
					for (PropertyType pt:propertyTypes) {
						System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
						if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
							folderId.setValue(pt.getValue().getContent(0));
						}
					}
					
//					PagingResponseType pagingChildren = naviSer.getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
					PagingResponseType pagingChildren = acarisServiceFactory.getAcarisService().getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
					ObjectIdType objectId =pagingChildren.getObjects(0).getObjectId();
			
					relationshipPropertiesType =acarisServiceFactory.getAcarisService().getRelationshipsServicePort().getObjectRelationships(repositoryId, principalId, objectId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, filter);
					
					//getStreamContent per fare il download
//					List<AcarisContentStreamType> contentStream= objeSer.getObjectServicePort().getContentStream(repositoryId, relationshipPropertiesType.get(0).getTargetId(), principalId, EnumStreamId.PRIMARY);
					AcarisContentStreamType[] contentStream= acarisServiceFactory.getAcarisService().getObjectServicePort().getContentStream(repositoryId, relationshipPropertiesType[0].getTargetId(), principalId, EnumStreamId.PRIMARY);
			
					//salvo il file
					DataHandler datadown=contentStream[0].getStreamMTOM();
					
					byte[] bDataMaster = IOUtils.toByteArray(datadown.getInputStream());
					
					return bDataMaster;
					
					
				  
				}  catch(it.doqui.acta.acaris.relationshipsservice.AcarisException e) {
					throw new BusinessException("Il numero di protocollo '" + protocollo + "' non contiene allegati", ErrorCodes.BUSSINESS_EXCEPTION);
				} catch (Exception e) {
					throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
				}
			}
			
			
	private byte[] filesZip(List<AllegatoVO> filePacchetto) throws IOException {

	    ByteArrayOutputStream bo = new ByteArrayOutputStream();
	    ZipOutputStream zipOut= new ZipOutputStream(bo);
	    for(AllegatoVO file:filePacchetto){
	        ZipEntry zipEntry = new ZipEntry(file.getFileName());
	        zipOut.putNextEntry(zipEntry);
	        zipOut.write(file.getbData());
	        zipOut.flush();
	        zipOut.closeEntry();
	    }
	    zipOut.close();
	    return bo.toByteArray();
		

		
		
	}
	

	
	
	//###################### Altro servizio per fare solo il download prendendo il targetId dal DB
		public byte[] downloadAllegato(String anno, String protocollo, String descrizioneAllegato) throws it.doqui.acta.acaris.relationshipsservice.AcarisException {
			try {
				byte[] bData = null;
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
//				List<QueryConditionType> criteria = Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante).to;
				QueryConditionType[] criteria = (QueryConditionType[]) Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante).toArray();
				
//				PagingResponseType paging = objeSer.getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
				NavigationConditionInfoType navigationLimits = null;
				Integer maxItems  = null;
				Integer skipCount = new Integer(0);
				PagingResponseType paging  = acarisServiceFactory.getAcarisService().getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , navigationLimits , maxItems, skipCount);
				PropertyType[] propertyTypes = paging.getObjects(0).getProperties();
				
				
				ObjectIdType folderId = new ObjectIdType();
				for (PropertyType pt:propertyTypes) {
					System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
					if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
						folderId.setValue(pt.getValue().getContent(0));
					}
				}
				
				PagingResponseType pagingChildren = acarisServiceFactory.getAcarisService().getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
				if(pagingChildren.getObjectsLength()>1) {
				
					
					ObjectIdType gruppoAllegatiObj = pagingChildren.getObjects(1).getObjectId();
					PagingResponseType classificazioniAllegatiList = recuperaAllChildrens(repositoryId, principalId, gruppoAllegatiObj);
					ObjectResponseType[] classificazioniAllegati = classificazioniAllegatiList.getObjects();
					
					for (int j = 0; j < classificazioniAllegati.length; j++) {
						
	
						String idClassificazioneAllegato = classificazioniAllegati[j].getObjectId().getValue();
						ObjectIdType objectIdClassificazioneAllegato = new ObjectIdType();
						objectIdClassificazioneAllegato.setValue(idClassificazioneAllegato);
						
						
						ObjectResponseType documentoProtocollatoAllegato = recuperaChildren(repositoryId, principalId, objectIdClassificazioneAllegato, 0);
						
						if(documentoProtocollatoAllegato!=null) {
							ObjectIdType docProtocollatoAllegatoObj = documentoProtocollatoAllegato.getObjectId();
							if (docProtocollatoAllegatoObj != null) {
					
							
								// se 1 elemento allora doc standard, se >1 è doc multiplo
								List<ObjectIdType> contentStreamIdList = recuperaIdContentStream(repositoryId, principalId, docProtocollatoAllegatoObj);
							
								
								// 20200825_LC gestione nome del documento considerando pure doc multiplo
								if(contentStreamIdList != null && contentStreamIdList.size() > 0) {
									
									if (contentStreamIdList.size()==1) {
										// se size 1 c'è un solo doc fisico
										AcarisContentStreamType[] contentStream = getContentStream(repositoryId, contentStreamIdList.get(0), principalId);	
										if(descrizioneAllegato.equalsIgnoreCase(contentStream[0].getFilename())) {
											DataHandler datadown=contentStream[0].getStreamMTOM();
											
											bData = IOUtils.toByteArray(datadown.getInputStream());
											
											
										}
										
										
									} 
								
								}
	
								
							}
						}
	
						
					
					}

			}
				
				return bData;
			  
			}  catch (Exception e) {
				throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
			}
		}
	
	
	//###################### Altro servizio per fare la lista degli allegati##########################################
	public List<AllegatoVO> listaAllegati(String anno, String protocollo) throws it.doqui.acta.acaris.relationshipsservice.AcarisException {
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
//			List<QueryConditionType> criteria = Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante).to;
			QueryConditionType[] criteria = (QueryConditionType[]) Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante).toArray();
			
//			PagingResponseType paging = objeSer.getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
			NavigationConditionInfoType navigationLimits = null;
			Integer maxItems  = null;
			Integer skipCount = new Integer(0);
			PagingResponseType paging  = acarisServiceFactory.getAcarisService().getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , navigationLimits , maxItems, skipCount);
			PropertyType[] propertyTypes = paging.getObjects(0).getProperties();
			
			
			ObjectIdType folderId = new ObjectIdType();
			for (PropertyType pt:propertyTypes) {
				System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
				if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
					folderId.setValue(pt.getValue().getContent(0));
				}
			}
			
			PagingResponseType pagingChildren = acarisServiceFactory.getAcarisService().getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
			List<AllegatoVO> allegati = null;
			if(pagingChildren.getObjectsLength()>1) {
				allegati = new ArrayList<AllegatoVO>();
			
				
				ObjectIdType gruppoAllegatiObj = pagingChildren.getObjects(1).getObjectId();
				PagingResponseType classificazioniAllegatiList = recuperaAllChildrens(repositoryId, principalId, gruppoAllegatiObj);
				ObjectResponseType[] classificazioniAllegati = classificazioniAllegatiList.getObjects();
				
			for (int j = 0; j < classificazioniAllegati.length; j++) {
				
				AllegatoVO allegato = new AllegatoVO();

				String idClassificazioneAllegato = classificazioniAllegati[j].getObjectId().getValue();
				ObjectIdType objectIdClassificazioneAllegato = new ObjectIdType();
				objectIdClassificazioneAllegato.setValue(idClassificazioneAllegato);
				
				
				ObjectResponseType documentoProtocollatoAllegato = recuperaChildren(repositoryId, principalId, objectIdClassificazioneAllegato, 0);
				
				if(documentoProtocollatoAllegato!=null) {
					ObjectIdType docProtocollatoAllegatoObj = documentoProtocollatoAllegato.getObjectId();
					if (docProtocollatoAllegatoObj != null) {
			
					
						// se 1 elemento allora doc standard, se >1 è doc multiplo
						List<ObjectIdType> contentStreamIdList = recuperaIdContentStream(repositoryId, principalId, docProtocollatoAllegatoObj);
					
						
						// 20200825_LC gestione nome del documento considerando pure doc multiplo
						if(contentStreamIdList != null && contentStreamIdList.size() > 0) {
							
							if (contentStreamIdList.size()==1) {
								// se size 1 c'è un solo doc fisico
								AcarisContentStreamType[] contentStream = getContentStream(repositoryId, contentStreamIdList.get(0), principalId);	
								allegato.setFileName(contentStream[0].getFilename());
							} 
						
						}

						
					}
				}

				
				allegati.add(allegato);									
			
			}

		}
			
			return allegati;
		  
		}  catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}
	
	
	private PagingResponseType recuperaAllChildrens(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectId) throws AcarisException {
		
		
		PagingResponseType children = null;
		
        PropertyFilterType filter = new PropertyFilterType() ;
        filter.setFilterType(EnumPropertyFilter.ALL);
        
        try{
        	children = acarisServiceFactory.getAcarisService().getNavigationServicePort().getChildren(repositoryId, objectId, principalId, filter, null, null); 
        }
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        
         return children;		// 20200707_LC
	}
	
	private AcarisContentStreamType[] getContentStream(ObjectIdType repositoryId, ObjectIdType documentoId, PrincipalIdType principalId) throws AcarisException {
		AcarisContentStreamType[] contentStream = null;
		try {
			// Chiamate tramite WSDL
//			contentStream  = objectService.getContentStream(repositoryId, documentoId, principalId, EnumStreamId.PRIMARY);
			contentStream  = acarisServiceFactory.getAcarisService().getObjectServicePort().getContentStream(repositoryId, documentoId, principalId, EnumStreamId.PRIMARY);
			
			if(contentStream == null){
				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return contentStream;
	}
		
	private List<ObjectIdType> recuperaIdContentStream(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType documentoId) throws AcarisException 
	{
		
		RelationshipPropertiesType[] relationshipPropertiesType = null;
		List<ObjectIdType> contentStreamId = null;	// 20200803_LC
		
		try 
		{
			relationshipPropertiesType = acarisServiceFactory.getAcarisService().getRelationshipsServicePort().getObjectRelationships(repositoryId, principalId, documentoId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, getPropertyFilterAll());
			
			if(relationshipPropertiesType !=null && relationshipPropertiesType.length >0) {
	
				//RelationshipPropertiesType response = relationshipPropertiesType[0];
				
				contentStreamId = new ArrayList<ObjectIdType>();
								
				// 20200803_LC gestione contenuto fisico >1
				for(int i=0; i < relationshipPropertiesType.length; i++) {
					RelationshipPropertiesType response = relationshipPropertiesType[i];
				
					if(response != null)
					{
						contentStreamId.add(response.getTargetId());
					}
				}
			}		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return contentStreamId;
	}
	
	private static PropertyFilterType getPropertyFilterAll()
	{
		return getPropertyFilter(EnumPropertyFilter.ALL, null, null, null);
	}
	
	
	private static PropertyFilterType getPropertyFilter(EnumPropertyFilter type, String[] className, String[] propertyName, PropertyFilterType prevFilter )
	{
		PropertyFilterType filter = null;
		if(type != null)
		{
			if(type.value().equals(EnumPropertyFilter.LIST.value()))
			{
				filter = (prevFilter != null) ? prevFilter : new PropertyFilterType();
				filter.setFilterType(type);
				List<QueryNameType> properties = new ArrayList<QueryNameType>();
				QueryNameType property = null;
				if(className.length == propertyName.length)
				{
					if(	prevFilter != null && 
						prevFilter.getFilterType().value().equals(EnumPropertyFilter.LIST.value()) && 
						prevFilter.getPropertyListLength() > 0)
					{
						for (int j = 0; j < prevFilter.getPropertyListLength(); j++) 
						{
							properties.add(prevFilter.getPropertyList(j));
						}
					}
					for (int i = 0; i < propertyName.length; i++) 
					{
						property = new QueryNameType();
						property.setClassName(className[i]);
						property.setPropertyName(propertyName[i]);
						properties.add(property);
					}
					filter.setPropertyList(properties.toArray(new QueryNameType[0]));
				}
				else return null;
				
			}
			else
			{
				filter = new PropertyFilterType();
				filter.setFilterType(type);
			}
		}
		return filter;
	}
	
	private ObjectResponseType recuperaChildren(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectId, int index) throws it.doqui.acta.actasrv.util.acaris.wrapper.exception.AcarisException{
		
		
		PagingResponseType children = null;
		ObjectResponseType response = null;
		
        PropertyFilterType filter = new PropertyFilterType() ;
        filter.setFilterType(EnumPropertyFilter.ALL);
        
        try{
        	children = acarisServiceFactory.getAcarisService().getNavigationServicePort().getChildren(repositoryId, objectId, principalId, filter, null, null); 
        	
        }catch (Exception e) 
		{
			e.printStackTrace();
		}
		
        
        // 20200713_LC
        if (children.getObjectsLength()>index) {
            response = children.getObjects(index);        	
        }
         return response;
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
			QueryConditionType[] criteria = (QueryConditionType[]) Arrays.asList(criteriaProtocollo,criteriaAnno,criteriaidAooProtocollante).toArray();

			PagingResponseType paging = acarisServiceFactory.getAcarisService().getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
			PropertyType[] propertyTypes = paging.getObjects(0).getProperties(); // prendo il primo elemento poichè per il criterio di ricerca mi aspetto un unico risultato

			//cerco la properties con objectIdClassificazione usato per la getChildren come folderID
			ObjectIdType folderId = new ObjectIdType();
			for (PropertyType pt:propertyTypes) {
				System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
				if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
					folderId.setValue(pt.getValue().getContent(0));
				}
			}
			System.out.println("objectIdClassificazione=" + folderId.getValue());
			
		} catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}
	

}
