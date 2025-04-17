package it.csi.sigas.sigasbl.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;

import it.csi.sigas.sigasbl.common.ActaManagementBase.ParamsCommonRetriveFont;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.doqui.acta.acaris.backofficeservice.BackOfficeService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.navigationservice.NavigationService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.objectservice.ObjectService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.relationshipsservice.RelationshipsService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.repositoryservice.RepositoryService;
import it.csi.sigas.sigasbl.integration.doqui.DoquiServiceFactory;
import it.csi.sigas.sigasbl.integration.doqui.service.impl.ActaManagementServiceImpl;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
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

public abstract class ActaManagementBase {
	
	protected static Logger log = Logger.getLogger(ActaManagementBase.class);
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	@Autowired
	private DoquiServiceFactory acarisServiceFactory;
	
	private RelationshipPropertiesType[] relationshipPropertiesType;
	protected QueryableObjectType target;
	private NavigationService naviSer;
	private ObjectService objeSer;
	private RelationshipsService relaSer;
	protected ObjectIdType repositoryId;
	protected PrincipalIdType principalId;	
	
	protected String codice;
	protected String annoCode;
	protected String idAOOProtocollante;
	protected long idAOOvalue;	
	
	protected enum ParamsCommonRetriveFont {
        PROPERTIES_FILES,
        DB_FIELD;
    }	
	
	private static final String B_DATA_MASTER_KEY = "bDataMaster";
	private static final String PAGING_CHILDREN_KEY = "pagingChildren";
	private static final String CONTENT_STREAM_KEY = "contentStream";
	
	private class ParamsCommonRetrive {
		String endpoint;
		String repoName;
		String appKey;
		String id;
		long idAOOvalue;
		long idStrutturaValue;
		long idNodoValue;
		String targetObject;
		String namespaceURI;
		String localPart;
		String endpointAddress;
		String naviWsdlLocation;
		String relaWsdlLocation;		
		String codice;
		String annoCode;
		String idAOOProtocollante; 
		
		
		ParamsCommonRetrive(ParamsCommonRetriveFont paramsCommonRetriveFont){
			if(paramsCommonRetriveFont == ParamsCommonRetriveFont.PROPERTIES_FILES ) {
				InputStream is = getClass().getResourceAsStream("/application.properties");
				Properties p=new Properties();  
				try{
					p.load(is);
					
					setupProperties(p.getProperty("endpoint"),
									p.getProperty("repoName"),
									p.getProperty("appKey"),
									p.getProperty("idUtente"),
									Long.parseLong(p.getProperty("idAOO")),
									Long.parseLong(p.getProperty("idStruttura")),
									Long.parseLong(p.getProperty("idNodo")),
									p.getProperty("targetObject"),
									p.getProperty("namespaceURI"),
									p.getProperty("localPart"),
									p.getProperty("endpointAddress"),
									p.getProperty("naviWsdlLocation"),
									p.getProperty("relaWsdlLocation"),
									p.getProperty("codice"),
									p.getProperty("anno"),
									p.getProperty("idAooProtocollante"));
					
				} catch (IOException e) {
					log.error("Impossible read application.properties. Problem creating Acta service!");
				}				
				
			}else {
				String endpoint = sigasCParametroRepository.findByDescParametro("ACTA_SERVER_PROTOCOL").getValoreString()+
								  sigasCParametroRepository.findByDescParametro("ACTA_SERVER").getValoreString()+
								  sigasCParametroRepository.findByDescParametro("ACTA_CONTEXT").getValoreString();
				
				setupProperties(endpoint,
								sigasCParametroRepository.findByDescParametro("REPOSITORY_ACTA").getValoreString(),
								sigasCParametroRepository.findByDescParametro("APPLICATION_KEY_ACTA").getValoreString(),
								sigasCParametroRepository.findByDescParametro("ACTA_CF").getValoreString(),
								Long.parseLong(sigasCParametroRepository.findByDescParametro("ACTA_ID_AOO").getValoreString()),
								Long.parseLong(sigasCParametroRepository.findByDescParametro("ACTA_ID_STRUTTURA").getValoreString()),
								Long.parseLong(sigasCParametroRepository.findByDescParametro("ACTA_ID_NODO").getValoreString()),
								sigasCParametroRepository.findByDescParametro("ACTA_TARGETOBJECT").getValoreString(),
								sigasCParametroRepository.findByDescParametro("ACTA_NAMESPACEURI").getValoreString(),
								sigasCParametroRepository.findByDescParametro("ACTA_LOCALPART").getValoreString(),
								sigasCParametroRepository.findByDescParametro("ACTA_ENDPOINTADDRESS").getValoreString(),
								sigasCParametroRepository.findByDescParametro("ACTA_NAVIWSDLLOCATION").getValoreString(),
								sigasCParametroRepository.findByDescParametro("ACTA_RELAWSDLLOCATION").getValoreString(),
								sigasCParametroRepository.findByDescParametro("ACTA_CODICE").getValoreString(),
								sigasCParametroRepository.findByDescParametro("ACTA_ANNO").getValoreString(),
								sigasCParametroRepository.findByDescParametro("ACTA_IDAOOPROTOCOLLANTE").getValoreString());
			}
		}
		
		private void setupProperties(String endpoint, String repoName, String appKey, String id, long idAOOvalue,
									 long idStrutturaValue, long idNodoValue, String targetObject, String namespaceURI,
									 String localPart, String endpointAddress, String naviWsdlLocation, String relaWsdlLocation,
									 String codice, String anno, String idAOOProtocollante) 
		{
			this.endpoint = endpoint;
			this.repoName = repoName;
			this.appKey = appKey;
			this.id = id;
			this.idAOOvalue=idAOOvalue;
			this.idStrutturaValue=idStrutturaValue;
			this.idNodoValue=idNodoValue;
			this.targetObject=targetObject;
			this.namespaceURI=namespaceURI;
			this.localPart=localPart;
			this.endpointAddress=endpointAddress;
			this.naviWsdlLocation=naviWsdlLocation;
			this.relaWsdlLocation=relaWsdlLocation;
			this.codice=codice;
			this.annoCode=anno;
			this.idAOOProtocollante=idAOOProtocollante;
		}
		
	}	
	
	
	public SigasCParametroRepository getSigasCParametroRepository() {
		return sigasCParametroRepository;
	}

	public void setSigasCParametroRepository(SigasCParametroRepository sigasCParametroRepository) {
		this.sigasCParametroRepository = sigasCParametroRepository;
	}

	public DoquiServiceFactory getAcarisServiceFactory() {
		return acarisServiceFactory;
	}

	public void setAcarisServiceFactory(DoquiServiceFactory acarisServiceFactory) {
		this.acarisServiceFactory = acarisServiceFactory;
	}

	protected void commonRetrive(ParamsCommonRetriveFont paramsCommonRetriveFont) throws MalformedURLException {
		try {
			
			ParamsCommonRetrive paramsCommonRetrive = new ParamsCommonRetrive(paramsCommonRetriveFont);
			
			codice = paramsCommonRetrive.codice;
			annoCode = paramsCommonRetrive.annoCode;
			idAOOProtocollante = paramsCommonRetrive.idAOOProtocollante;
			idAOOvalue = paramsCommonRetrive.idAOOvalue;

			String endpoint = paramsCommonRetrive.endpoint;
			String repoName = paramsCommonRetrive.repoName;
			String appKey = paramsCommonRetrive.appKey;
			
			CodiceFiscaleType idUtente= new CodiceFiscaleType();
			String id = paramsCommonRetrive.id;
			idUtente.setValue(id);
			
			IdAOOType idAOO = new IdAOOType();			
			long idAOOvalue = paramsCommonRetrive.idAOOvalue;
			idAOO.setValue(idAOOvalue);
			
			IdStrutturaType idStruttura= new IdStrutturaType();
			long idStrutturaValue = paramsCommonRetrive.idStrutturaValue;
			idStruttura.setValue(idStrutturaValue);
			
			IdNodoType idNodo= new IdNodoType();
			long idNodoValue = paramsCommonRetrive.idNodoValue;
			idNodo.setValue(idNodoValue);

			this.target = new QueryableObjectType();
			String targetObject = paramsCommonRetrive.targetObject;
			target.setObject(targetObject);

			this.objeSer = new ObjectService(new URL(endpoint+"objectWS?wsdl"));
			
			// abilito MTOM per il multipart relativo al download dei documenti
			String namespaceURI =paramsCommonRetrive.namespaceURI;
			String localPart = paramsCommonRetrive.localPart;
			String endpointAddress = paramsCommonRetrive.endpointAddress;
			objeSer.addPort(new QName(namespaceURI, localPart),SOAPBinding.SOAP11HTTP_MTOM_BINDING,endpoint+endpointAddress);
			String naviWsdlLocation = paramsCommonRetrive.naviWsdlLocation;
			String relaWsdlLocation = paramsCommonRetrive.relaWsdlLocation;
			naviSer = new NavigationService(new URL(endpoint+naviWsdlLocation));
			relaSer = new RelationshipsService(new URL(endpoint+relaWsdlLocation));			

			try {
				// Get RepositoryId from name
				this.repositoryId = null;
				AcarisRepositoryEntryType[] resp = this.acarisServiceFactory.getAcarisService().getRepositoryServicePort().getRepositories();
				for(AcarisRepositoryEntryType aret : resp){
					if (aret.getRepositoryName().equals(repoName))
						repositoryId=aret.getRepositoryId();
				}
				
				// Get PrincipalId
				this.principalId = null;
				ClientApplicationInfo clientApplicationInfo = new ClientApplicationInfo();
				clientApplicationInfo.setAppKey(appKey);;
				principalId= this.acarisServiceFactory.getAcarisService().getBackOfficeServicePort().getPrincipalExt(repositoryId, idUtente, idAOO, idStruttura, idNodo, clientApplicationInfo)[0].getPrincipalId();
			} catch (AcarisException e) {
				//e.printStackTrace();
			} catch (it.doqui.acta.acaris.repositoryservice.AcarisException e) {
				//e.printStackTrace();
			} 
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}	
	
	protected QueryConditionType createQueryConditionType(String propertyName, EnumQueryOperator enumQueryOperator, String value) {
		if(propertyName==null) {
			return null;
		}
		QueryConditionType criteria = new QueryConditionType();
		criteria.setPropertyName(propertyName);
		criteria.setOperator(enumQueryOperator);
		if(value != null) {
			criteria.setValue(value);
		}
		return criteria;
	}
	
	private Map<String,Object> createDowloandObjects(String annoValue, String protocollo, ParamsCommonRetriveFont paramsCommonRetriveFont) throws it.doqui.acta.acaris.relationshipsservice.AcarisException, Exception{
		if(annoValue==null || protocollo==null) {
			return null;
		}
		
		Map<String,Object> output = new HashMap<String,Object>();
		
		PropertyFilterType filter = new PropertyFilterType();
		filter.setFilterType(EnumPropertyFilter.ALL);
		
		commonRetrive(paramsCommonRetriveFont);
				
		QueryConditionType[] criteria = Arrays.asList(this.createQueryConditionType(codice,
																				    EnumQueryOperator.EQUALS, 
																				    protocollo),
													  this.createQueryConditionType(annoCode, 
															 					    EnumQueryOperator.EQUALS, 
															 					    annoValue),
													  this.createQueryConditionType(idAOOProtocollante, 
															 					    EnumQueryOperator.EQUALS, 
															 					    String.valueOf(idAOOvalue))).toArray(new QueryConditionType[0]);

		NavigationConditionInfoType navigationLimits = null;
		Integer maxItems  = null;
		Integer skipCount = 0;			
		
		PagingResponseType paging  = this.acarisServiceFactory.getAcarisService().getObjectServicePort().query(repositoryId, principalId, target , filter, criteria , navigationLimits , maxItems, skipCount);			
		PropertyType[] propertyTypes = paging.getObjects(0).getProperties();			
		
		ObjectIdType folderId = new ObjectIdType();
		for (PropertyType pt:propertyTypes) {
			System.out.println(pt.getQueryName().getPropertyName()+" "+pt.getValue().getContent());
			if (pt.getQueryName().getPropertyName().equals("objectIdClassificazione")){
				folderId.setValue(pt.getValue().getContent(0));
			}
		}			
		
		PagingResponseType pagingChildren = this.acarisServiceFactory.getAcarisService().getNavigationServicePort().getChildren(repositoryId, folderId, principalId, filter, null, 0);
		output.put(PAGING_CHILDREN_KEY, pagingChildren);
		ObjectIdType objectId =pagingChildren.getObjects(0).getObjectId();
		
		relationshipPropertiesType = this.acarisServiceFactory.getAcarisService().getRelationshipsServicePort().getObjectRelationships(repositoryId, principalId, objectId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, filter);			
		
		AcarisContentStreamType[] contentStream= this.acarisServiceFactory.getAcarisService().getObjectServicePort().getContentStream(repositoryId, relationshipPropertiesType[0].getTargetId(), principalId, EnumStreamId.PRIMARY);
		output.put(CONTENT_STREAM_KEY, contentStream);

		//salvo il file
		DataHandler datadown=contentStream[0].getStreamMTOM();		
		byte[] bDataMaster = IOUtils.toByteArray(datadown.getInputStream());
		output.put(B_DATA_MASTER_KEY, bDataMaster);
		
		return output;
	}
	
	protected PagingResponseType recuperaAllChildrens(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectId) throws AcarisException {		
		
		PagingResponseType children = null;		
        PropertyFilterType filter = new PropertyFilterType() ;
        filter.setFilterType(EnumPropertyFilter.ALL);        
        try{
        	children = this.acarisServiceFactory.getAcarisService().getNavigationServicePort().getChildren(repositoryId, objectId, principalId, filter, null, null); 
        }
		catch (Exception e) 
		{
			//e.printStackTrace();
		}        
         return children;
	}
		
	protected AcarisContentStreamType[] getContentStream(ObjectIdType repositoryId, ObjectIdType documentoId, PrincipalIdType principalId) throws AcarisException {
		AcarisContentStreamType[] contentStream = null;
		try {
			// Chiamate tramite WSDL
			contentStream  = this.acarisServiceFactory.getAcarisService().getObjectServicePort().getContentStream(repositoryId, documentoId, principalId, EnumStreamId.PRIMARY);			
			if(contentStream == null){
				return null;
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		return contentStream;
	}
			
	protected List<ObjectIdType> recuperaIdContentStream(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType documentoId) throws AcarisException 
	{		
		RelationshipPropertiesType[] relationshipPropertiesType = null;
		List<ObjectIdType> contentStreamId = null;		
		try 
		{
			relationshipPropertiesType = this.acarisServiceFactory.getAcarisService().getRelationshipsServicePort().getObjectRelationships(repositoryId, principalId, documentoId, EnumRelationshipObjectType.DOCUMENT_COMPOSITION_PROPERTIES_TYPE, EnumRelationshipDirectionType.EITHER, getPropertyFilterAll());			
			if(relationshipPropertiesType !=null && relationshipPropertiesType.length >0) {				
				contentStreamId = new ArrayList<ObjectIdType>();		
				
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
			//e.printStackTrace();
		}
		return contentStreamId;
	}	
		
	protected ObjectResponseType recuperaChildren(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType objectId, int index) throws it.doqui.acta.actasrv.util.acaris.wrapper.exception.AcarisException
	{			
		PagingResponseType children = null;
		ObjectResponseType response = null;
		
        PropertyFilterType filter = new PropertyFilterType() ;
        filter.setFilterType(EnumPropertyFilter.ALL);
        
        try{
        	children = this.acarisServiceFactory.getAcarisService().getNavigationServicePort().getChildren(repositoryId, objectId, principalId, filter, null, null); 
        	
        }catch (Exception e) 
		{
			//e.printStackTrace();
		}        
        
        if (children != null && children.getObjectsLength()>index) {
            response = children.getObjects(index);        	
        }
         return response;
	}
	
	private boolean checkNomeAllegatoPresente(String nomeAllegato, List<AllegatoVO> allegatiImpacchettati) {
		/*
		if(allegatiImpacchettati.stream().filter(allegato -> allegato.getFileName().equalsIgnoreCase(nomeAllegato))
				 						 .findAny().orElse(null) != null) 
		{
			return true;
		}
		else {
			return false;
		}
		*/
		boolean output = false;
		if(allegatiImpacchettati== null || allegatiImpacchettati.isEmpty()) {
			return output;
		}
		if(nomeAllegato == null) {
			return output;
		}
		Iterator<AllegatoVO> iterator = allegatiImpacchettati.iterator();
		while(iterator.hasNext()) {
			AllegatoVO allegatoVO  = iterator.next();
			if(allegatoVO.getFileName().equalsIgnoreCase(nomeAllegato)) {
				output = true;
			}
		}		
		return output;
	}
	
	private byte[] filesZip(List<AllegatoVO> filePacchetto) throws IOException {
		
		List<AllegatoVO> allegatiImpacchettati = null;
		
	    ByteArrayOutputStream bo = new ByteArrayOutputStream();
	    ZipOutputStream zipOut= new ZipOutputStream(bo);
	    for(AllegatoVO file:filePacchetto){	    	
	    	String nomeAllegato = file.getFileName();
	    	if(allegatiImpacchettati == null) {
	    		allegatiImpacchettati = new ArrayList<>();
	    		allegatiImpacchettati.add(file);
	    	}else {
	    		if(this.checkNomeAllegatoPresente(nomeAllegato,allegatiImpacchettati)) 
	    		{
	    			String systemTimeSuffix = String.valueOf(System.currentTimeMillis());
	    			String estensioneFile = nomeAllegato.substring(nomeAllegato.lastIndexOf('.')+1);
	    			String nomeFileSenzaEstensione = nomeAllegato.substring(0, nomeAllegato.lastIndexOf('.'));
	    			nomeAllegato = nomeFileSenzaEstensione + " (" + systemTimeSuffix + ")." + estensioneFile;
	    		}else {
	    			allegatiImpacchettati.add(file);
	    		}
	    	}	    	
	        //ZipEntry zipEntry = new ZipEntry(file.getFileName());
	    	ZipEntry zipEntry = new ZipEntry(nomeAllegato);
	        zipOut.putNextEntry(zipEntry);
	        zipOut.write(file.getbData());
	        zipOut.flush();
	        zipOut.closeEntry();
	    }
	    zipOut.close();
	    return bo.toByteArray();		
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
	
	protected byte[] downloadMaster(String annoValue, String protocollo, ParamsCommonRetriveFont paramsCommonRetriveFont) 
	{
		try {
			if(annoValue!=null && protocollo!=null) {
				Map<String,Object> downlloandObjects = this.createDowloandObjects(annoValue, protocollo, paramsCommonRetriveFont);
				return (byte[])downlloandObjects.get(B_DATA_MASTER_KEY);
			}
			return null;						
		  
		}  catch(it.doqui.acta.acaris.relationshipsservice.AcarisException e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non contiene allegati", ErrorCodes.BUSSINESS_EXCEPTION);
		} catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}
	
	protected byte[] download(String annoValue, String protocollo, ParamsCommonRetriveFont paramsCommonRetriveFont) {
		try {						 
			
			Map<String,Object> downloandObjects = this.createDowloandObjects(annoValue, protocollo, paramsCommonRetriveFont);
			PagingResponseType pagingChildren = (PagingResponseType) downloandObjects.get(PAGING_CHILDREN_KEY);
			AcarisContentStreamType[] contentStream = (AcarisContentStreamType[]) downloandObjects.get(CONTENT_STREAM_KEY);
			byte[] bDataMaster = (byte[]) downloandObjects.get(B_DATA_MASTER_KEY);
			
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
							if(contentStreamIdList != null && !contentStreamIdList.isEmpty() &&  contentStreamIdList.size()==1) 
							{								
									// se size 1 c'è un solo doc fisico
									AcarisContentStreamType[] contentStreamAllegati = getContentStream(repositoryId, contentStreamIdList.get(0), principalId);	
									DataHandler datadownAllegati=contentStreamAllegati[0].getStreamMTOM();
									
									byte[] bDataAllegati = IOUtils.toByteArray(datadownAllegati.getInputStream());
									
									allegato.setFileName(contentStreamAllegati[0].getFilename());
									allegato.setbData(bDataAllegati);							
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
	
	protected byte[] downloadAllegato(String annoValue, String protocollo, String descrizioneAllegato, ParamsCommonRetriveFont paramsCommonRetriveFont) throws it.doqui.acta.acaris.relationshipsservice.AcarisException {
		try {
			
			byte[] bData = null;			
			Map<String,Object> downloandObjects = this.createDowloandObjects(annoValue, protocollo, paramsCommonRetriveFont);
			PagingResponseType pagingChildren = (PagingResponseType) downloandObjects.get(PAGING_CHILDREN_KEY);
			AcarisContentStreamType[] contentStream = (AcarisContentStreamType[]) downloandObjects.get(CONTENT_STREAM_KEY);	
			
			
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
							if(contentStreamIdList != null && !contentStreamIdList.isEmpty() && contentStreamIdList.size()==1) {								
								// se size 1 c'è un solo doc fisico
								AcarisContentStreamType[] contentStreamAllegati = getContentStream(repositoryId, contentStreamIdList.get(0), principalId);	
								if(descrizioneAllegato.equalsIgnoreCase(contentStreamAllegati[0].getFilename())) {
									DataHandler datadown=contentStreamAllegati[0].getStreamMTOM();										
									bData = IOUtils.toByteArray(datadown.getInputStream());										
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
}
