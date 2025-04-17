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

import it.csi.sigas.sigasbl.common.ActaManagementBase;
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

public class ActaUtils  extends ActaManagementBase {
		
	//private DoquiServiceFactory acarisServiceFactory;	
	
	private ObjectService objeSerAU;	
	private RelationshipPropertiesType[] relationshipPropertiesTypeAU;
	private String uuidAU;
	private String descrizioneAU;
	private String filenameAU;
	
	protected static Logger log = Logger.getLogger(ActaUtils.class);
	

	public ActaUtils(DoquiServiceFactory acarisServiceFactory) {
		super();
		//this.acarisServiceFactory = acarisServiceFactory;
		this.setAcarisServiceFactory(acarisServiceFactory);
	}

	public String getUuid() {
		return uuidAU;
	}

	public void setUuid(String uuid) {
		this.uuidAU = uuid;
	}

	public String getDescrizione() {
		return descrizioneAU;
	}

	public void setDescrizione(String descrizione) {
		this.descrizioneAU = descrizione;
	}

	public String getFilename() {
		return filenameAU;
	}

	public void setFilename(String filename) {
		this.filenameAU = filename;
	}	

	//################# Servizio per memorizzare a DB il protocollo inserito dall'operatore solo 
	//se ha tornato un risultato, l'oggetto al posto del nome del file e l'UUID sulla falsa riga di INDEX			
	public String saveData(String anno, String protocollo) {
		try {
			InputStream is = getClass().getResourceAsStream("/application.properties");
			Properties p=new Properties();  
			p.load(is);
			
			commonRetrive(ParamsCommonRetriveFont.PROPERTIES_FILES);			
			
			String[] params = protocollo.split("/");
			if (params.length < 2)
				throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
			
			// query on anno, protocollo e tipo
			PropertyFilterType filter = new PropertyFilterType();
			filter.setFilterType(EnumPropertyFilter.ALL);			
			
			/*
			QueryConditionType[] criteria = (QueryConditionType[]) Arrays.asList(this.createQueryConditionType(codice, EnumQueryOperator.EQUALS, params[0]),
					 															 this.createQueryConditionType(annoCode, EnumQueryOperator.EQUALS, params[1]),
					 															 this.createQueryConditionType(idAOOProtocollante, EnumQueryOperator.EQUALS, String.valueOf(idAOOvalue))).toArray();
			*/
			QueryConditionType[] criteria = Arrays.asList(this.createQueryConditionType(codice, EnumQueryOperator.EQUALS, params[0]),
														  this.createQueryConditionType(annoCode, EnumQueryOperator.EQUALS, params[1]),
														  this.createQueryConditionType(idAOOProtocollante, EnumQueryOperator.EQUALS, 
																  						String.valueOf(idAOOvalue))
														  ).toArray(new QueryConditionType[0]);

			PagingResponseType paging = this.getAcarisServiceFactory().getAcarisService().getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);			
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
			PagingResponseType pagingChildren = this.getAcarisServiceFactory().getAcarisService().getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
			ObjectIdType objectId =pagingChildren.getObjects(0).getObjectId(); //usato come objectId per la getRelationShips ( cerca tutte le relazioni associate al protocollo compresi gli allegati) 

			//Ricavo la descrizione del file(oggetto) e data. Ciclo per trovare l'objectType DocumentoSemplicePropertiesType con properties "oggetto" e "changeToken"
			for (PropertyType pt:pagingChildren.getObjects(0).getProperties()){
				if (pt.getQueryName().getClassName().equals("DocumentoSemplicePropertiesType") && pt.getQueryName().getPropertyName().equals("oggetto")){  // da memorizzare sul DB al posto del nome del file
					System.out.println("Descrizione File: "+  pt.getValue().getContent(0));
					descrizioneAU = pt.getValue().getContent(0);
				}
			}
			System.out.println("objectId getChildren= " +objectId.getValue());

			//getRelationShip per prendere lo streamId dal targetId
			relationshipPropertiesTypeAU =this.getAcarisServiceFactory().getAcarisService().getRelationshipsServicePort().getObjectRelationships(repositoryId, principalId, objectId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, filter);

			//getStreamContent per prendere il nome del file
			AcarisContentStreamType[] contentStream= this.getAcarisServiceFactory().getAcarisService().getObjectServicePort().getContentStream(repositoryId, relationshipPropertiesTypeAU[0].getTargetId(), principalId, EnumStreamId.PRIMARY);
			filenameAU = contentStream[0].getFilename();
			
			System.out.println("filename = " + filenameAU);			
			System.out.println("documentId="+relationshipPropertiesTypeAU[0].getTargetId().getValue()); // da memorizzare su DB come UUID di INDEX

			uuidAU = relationshipPropertiesTypeAU[0].getTargetId().getValue();

			//restituire una stringa 'success' e poi recuperare le 3 variabili private tramite getters
			if(null != uuidAU && null != descrizioneAU && null != protocollo) {
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
			if(anno!=null && protocollo!=null) {
				return this.download(anno, protocollo, ParamsCommonRetriveFont.PROPERTIES_FILES);
			}
			return null;		  
		} catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}	
	
	//###################### Altro servizio per fare solo il download del documento master prendendo il targetId dal DB
	public byte[] downloadMaster(String anno, String protocollo) {
		try {
			
			if(anno!=null && protocollo!=null) {				
				return this.downloadMaster(anno,protocollo,ParamsCommonRetriveFont.PROPERTIES_FILES);
			}						
			return null;
		  
		}  catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}			
	
	
	//###################### Altro servizio per fare solo il download prendendo il targetId dal DB
	public byte[] downloadAllegato(String anno, String protocollo, String descrizioneAllegato) throws it.doqui.acta.acaris.relationshipsservice.AcarisException {
		try {			
			if(anno!=null && protocollo!=null) {				
				return this.downloadAllegato(anno,protocollo,descrizioneAllegato,ParamsCommonRetriveFont.PROPERTIES_FILES);
			}
			return null;					  
		}  catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}
	
	
	//###################### Altro servizio per fare la lista degli allegati##########################################
	public List<AllegatoVO> listaAllegati(String anno, String protocollo) throws it.doqui.acta.acaris.relationshipsservice.AcarisException {
		try {	
			
			
			PropertyFilterType filter = new PropertyFilterType();
			filter.setFilterType(EnumPropertyFilter.ALL);
			
			commonRetrive(ParamsCommonRetriveFont.PROPERTIES_FILES);		
			
			/*
			QueryConditionType[] criteria = (QueryConditionType[]) Arrays.asList(this.createQueryConditionType(codice, EnumQueryOperator.EQUALS, protocollo),
					 															 this.createQueryConditionType(annoCode, EnumQueryOperator.EQUALS, anno),
					 															 this.createQueryConditionType(idAOOProtocollante, EnumQueryOperator.EQUALS, String.valueOf(idAOOvalue))).toArray();
			*/
			QueryConditionType[] criteria = Arrays.asList(this.createQueryConditionType(codice, EnumQueryOperator.EQUALS, protocollo),
										 				  this.createQueryConditionType(annoCode, EnumQueryOperator.EQUALS, anno),
										 				  this.createQueryConditionType(idAOOProtocollante, EnumQueryOperator.EQUALS, String.valueOf(idAOOvalue)))
												   .toArray(new QueryConditionType[0]);

			NavigationConditionInfoType navigationLimits = null;
			Integer maxItems  = null;
			Integer skipCount = 0;
			PagingResponseType paging  = this.getAcarisServiceFactory().getAcarisService().getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , navigationLimits , maxItems, skipCount);
			PropertyType[] propertyTypes = paging.getObjects(0).getProperties();			
			
			ObjectIdType folderId = new ObjectIdType();
			for (PropertyType pt:propertyTypes) {
				System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
				if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
					folderId.setValue(pt.getValue().getContent(0));
				}
			}
			
			PagingResponseType pagingChildren = this.getAcarisServiceFactory().getAcarisService().getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
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
							if(contentStreamIdList != null && !contentStreamIdList.isEmpty() && contentStreamIdList.size()==1) {								
								// se size 1 c'è un solo doc fisico
								AcarisContentStreamType[] contentStream = getContentStream(repositoryId, contentStreamIdList.get(0), principalId);	
								allegato.setFileName(contentStream[0].getFilename());
														
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

	public void checkProtocollo(String anno, String protocollo) {
		try {
						
			commonRetrive(ParamsCommonRetriveFont.PROPERTIES_FILES);
			
			String[] params = protocollo.split("/");

			if (params.length < 2)
				throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
			
			// query on anno, protocollo e tipo
			PropertyFilterType filter = new PropertyFilterType();
			filter.setFilterType(EnumPropertyFilter.ALL);			
			
			/*
			QueryConditionType[] criteria = (QueryConditionType[]) Arrays.asList(this.createQueryConditionType(codice, EnumQueryOperator.EQUALS, params[0]),
																				 this.createQueryConditionType(annoCode, EnumQueryOperator.EQUALS, params[1]),
																				 this.createQueryConditionType(idAOOProtocollante, EnumQueryOperator.EQUALS, String.valueOf(idAOOvalue))).toArray();
			*/
			
			QueryConditionType[] criteria = Arrays.asList(this.createQueryConditionType(codice, EnumQueryOperator.EQUALS, params[0]),
											 			  this.createQueryConditionType(annoCode, EnumQueryOperator.EQUALS, params[1]),
											 			  this.createQueryConditionType(idAOOProtocollante, EnumQueryOperator.EQUALS, String.valueOf(idAOOvalue)))
												  .toArray(new QueryConditionType[0]);

			PagingResponseType paging = this.getAcarisServiceFactory().getAcarisService().getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , null , null, 0);
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
