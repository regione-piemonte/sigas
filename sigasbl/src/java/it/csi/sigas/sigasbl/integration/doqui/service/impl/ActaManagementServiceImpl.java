/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.common.ActaManagementBase;
import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.doqui.acta.acaris.navigationservice.NavigationService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.objectservice.ObjectService;
import it.csi.sigas.sigasbl.doqui.acta.acaris.relationshipsservice.RelationshipsService;
import it.csi.sigas.sigasbl.integration.doqui.DoquiServiceFactory;
import it.csi.sigas.sigasbl.integration.doqui.bean.DocumentoActa;
import it.csi.sigas.sigasbl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.sigas.sigasbl.integration.doqui.bean.DocumentoProtocollatoActa;
import it.csi.sigas.sigasbl.integration.doqui.bean.KeyDocumentoActa;
import it.csi.sigas.sigasbl.integration.doqui.bean.UtenteActa;
import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisBackOfficeService;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisDocumentService;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisManagementService;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisMultifilingService;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisNavigationService;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisObjectService;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisOfficialBookService;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisRelationshipService;
import it.csi.sigas.sigasbl.integration.doqui.service.AcarisRepositoryService;
import it.csi.sigas.sigasbl.integration.doqui.service.ActaManagementService;
import it.csi.sigas.sigasbl.integration.doqui.utils.DateFormat;
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
import it.doqui.acta.actasrv.dto.acaris.type.common.EnumObjectType;
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
import it.doqui.acta.actasrv.dto.acaris.type.common.ValueType;
import it.doqui.acta.actasrv.dto.acaris.type.document.IdentificatoreDocumento;
import it.doqui.acta.actasrv.dto.acaris.type.management.VitalRecordCodeType;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.IdentificazioneRegistrazione;

@Service
public class ActaManagementServiceImpl extends ActaManagementBase implements ActaManagementService {

	protected static Logger log = Logger.getLogger(ActaManagementServiceImpl.class);	

	@Autowired
	private AcarisRepositoryService acarisRepositoryService;
	@Autowired
	private AcarisBackOfficeService acarisBackOfficeService;
	@Autowired
	private AcarisObjectService	acarisObjectService;
	@Autowired
	private AcarisManagementService acarisManagementService;
	@Autowired
	private AcarisDocumentService acarisDocumentService;
	@Autowired
	private AcarisOfficialBookService acarisOfficialBookService;
	@Autowired
	private AcarisRelationshipService acarisRelationshipService;
	@Autowired
	private AcarisNavigationService acarisNavigationService;	
	@Autowired
	private AcarisMultifilingService acarisMultifilingService;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepositoryAM;	
	@Autowired
	private DoquiServiceFactory acarisServiceFactoryAM;
		
	
	public AcarisNavigationService getAcarisNavigationService() {
		return acarisNavigationService;
	}

	public void setAcarisNavigationService(
			AcarisNavigationService acarisNavigationService) {
		this.acarisNavigationService = acarisNavigationService;
	}

	public AcarisRelationshipService getAcarisRelationshipService()
	{
		return acarisRelationshipService;
	}

	public void setAcarisRelationshipService(
			AcarisRelationshipService acarisRelationshipService)
	{
		this.acarisRelationshipService = acarisRelationshipService;
	}

	public AcarisOfficialBookService getAcarisOfficialBookService()
	{
		return acarisOfficialBookService;
	}

	public void setAcarisOfficialBookService(AcarisOfficialBookService acarisOfficialBookService)
	{
		this.acarisOfficialBookService = acarisOfficialBookService;
	}

	public AcarisRepositoryService getAcarisRepositoryService() 
	{
		return acarisRepositoryService;
	}

	public void setAcarisRepositoryService(AcarisRepositoryService acarisRepositoryService)
	{
		this.acarisRepositoryService = acarisRepositoryService;
	}

	public AcarisBackOfficeService getAcarisBackOfficeService()
	{
		return acarisBackOfficeService;
	}

	public void setAcarisBackOfficeService(AcarisBackOfficeService acarisBackOfficeService) 
	{
		this.acarisBackOfficeService = acarisBackOfficeService;
	}

	public AcarisObjectService getAcarisObjectService() 
	{
		return acarisObjectService;
	}

	public void setAcarisObjectService(AcarisObjectService acarisObjectService) 
	{
		this.acarisObjectService = acarisObjectService;
	}

	public AcarisManagementService getAcarisManagementService()
	{
		return acarisManagementService;
	}

	public void setAcarisManagementService(AcarisManagementService acarisManagementService)
	{
		this.acarisManagementService = acarisManagementService;
	}

	public AcarisDocumentService getAcarisDocumentService()
	{
		return acarisDocumentService;
	}

	public void setAcarisDocumentService(
			AcarisDocumentService acarisDocumentService)
	{
		this.acarisDocumentService = acarisDocumentService;
	}

	// 20200728_LC
	public AcarisMultifilingService getAcarisMultifilingService()
	{
		return acarisMultifilingService;
	}

	public void setAcarisMultifilingService(
			AcarisMultifilingService acarisMultifilingService)
	{
		this.acarisMultifilingService = acarisMultifilingService;
	}	

	@PostConstruct
	public void init() {
		try{
			getAcarisRepositoryService().init();
			getAcarisBackOfficeService().init();
			getAcarisObjectService().init();
			getAcarisManagementService().init();
			getAcarisDocumentService().init();
			getAcarisOfficialBookService().init();
			getAcarisRelationshipService().init();
			getAcarisNavigationService().init();
			getAcarisMultifilingService().init();
			
			this.setSigasCParametroRepository(sigasCParametroRepositoryAM);
			this.setAcarisServiceFactory(acarisServiceFactoryAM);
			this.commonRetrive(ParamsCommonRetriveFont.DB_FIELD);
		}catch(Throwable t) {
			String method = "init";
			log.warn(method + ". Problem creating Acta service!");
		}
	}	
	
	
	//###################### Altro servizio per fare solo il download prendendo il targetId dal DB
	public byte[] download(String anno, String protocollo) {
		try {						 
			if(anno!=null && protocollo!=null) {
				return this.download(anno, protocollo, ParamsCommonRetriveFont.DB_FIELD);
			}
			return null;		  
		} catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}	
	
	//###################### Altro servizio per fare solo il download del documento master prendendo il targetId dal DB
	public byte[] downloadMaster(String anno, String protocollo) 
	{
		try {
			if(anno!=null && protocollo!=null) {				
				return this.downloadMaster(anno,protocollo,ParamsCommonRetriveFont.DB_FIELD);
			}
			return null;		
		} catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}		
	
	//###################### Altro servizio per fare solo il download prendendo il targetId dal DB
	public byte[] downloadAllegato(String anno, String protocollo, String descrizioneAllegato) throws it.doqui.acta.acaris.relationshipsservice.AcarisException {
		try {			
			if(anno!=null && protocollo!=null) {				
				return this.downloadAllegato(anno,protocollo,descrizioneAllegato,ParamsCommonRetriveFont.DB_FIELD);
			}
			return null;					  
		}  catch (Exception e) {
			throw new BusinessException("Il numero di protocollo '" + protocollo + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		}
	}	
	
	
	@Transactional(propagation=Propagation.REQUIRED)	
	public KeyDocumentoActa archiviaDocumentoLogico(DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException {
		String method = "archiviaDocumentoLogico";
		
		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;
		ObjectIdType folderId = null;
		VitalRecordCodeType[] elencoVitalRecordCodeType = null;
		IdentificatoreDocumento identificatoreDocumento = null;
		ObjectIdType strutturaId = null;
		String UUIDDocumento = null;
		try
		{
			log.debug(method + ". Running recuperaIdRepository...");
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());
			

			log.debug(method + ". Running recuperaPrincipalId...");
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			

			log.debug(method + ". Running recuperaRootFolderId...");	
			folderId = recuperaRootFolderId(utenteActa.getRootActa(), repositoryId, principalId, documentoActa.getTipoStrutturaRoot());
			
			log.debug(method + ". Running Servizio recuperaVitalRecordCode...");
			elencoVitalRecordCodeType = acarisManagementService.recuperaVitalRecordCode(repositoryId);
					
			VitalRecordCodeType vitalRecordCodeType = estratiVitalRecordCodeType(elencoVitalRecordCodeType, utenteActa.getIdvitalrecordcodetype());

			log.debug(method + ". Running Servizio recuperaFascicoloAnnualeFolderIdFolderId...");
			
			strutturaId = recuperaStrutturaFolderId(documentoActa.getFolder(), repositoryId, principalId, folderId, documentoActa.getTipoStrutturaFolder());
			
			if (strutturaId == null){
				
				if(documentoActa.getTipoStrutturaFolder().intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_VOLUME){
					log.debug(method + ". creazione volume");
					strutturaId = acarisObjectService.creaVolume(repositoryId, principalId, folderId, vitalRecordCodeType, documentoActa, utenteActa);
				}
				else if(documentoActa.getTipoStrutturaFolder().intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_FASCICOLO){
					log.debug(method + ". creazione fascicolo");
					strutturaId = acarisObjectService.creaFascicolo(repositoryId, principalId, folderId, vitalRecordCodeType, documentoActa, utenteActa);
				}				
				else{
					throw new IntegrationException("Tipo struttura acta non gestita");
					
				}				
			}				
			
			//idFormaDocumentaria
			Integer idFormaDocumentaria = acarisObjectService.getIdFormaDocumentaria(repositoryId, principalId, utenteActa);
			log.debug(method + ". Running Servizio creaDocumentoSoloMetadati...");
			
			//idStatoDiEfficacia
			log.debug(method + ". Running Servizio getStatoDiEfficacia...");
			Integer idStatoDiEfficacia = acarisObjectService.getStatoDiEfficacia(repositoryId, principalId, utenteActa.getIdStatoDiEfficacia());
			
			identificatoreDocumento = acarisDocumentService.creaDocumentoSoloMetadati(repositoryId, principalId, folderId, vitalRecordCodeType, idFormaDocumentaria, idStatoDiEfficacia, documentoActa);
			if (identificatoreDocumento != null)
				log.debug(method + ". identificatoreDocumento = " + identificatoreDocumento.getObjectIdDocumento().hashCode());
			
			log.debug(method + ". Running Servizio getUUIDDocumento...");			
			UUIDDocumento = getUUIDDocumentoByParolaChiave(documentoActa.getIdDocumento(), repositoryId, principalId);

			return ottengoKeyActa(documentoActa, UUIDDocumento, null, null, identificatoreDocumento);

		}
		finally
		{
			log.info(method + ". END");
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)	
	public KeyDocumentoActa archiviaDocumentoFisico(DocumentoElettronicoActa documentoActa, UtenteActa utenteActa) throws IntegrationException {
		String method = "archiviaDocumentoFisico";		
		
		String UUIDDocumento = null;
		try {
			
			// repository ID
			ObjectIdType repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());
			
			// principal ID
			PrincipalIdType principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);

			// folder ID
			ObjectIdType folderId = recuperaRootFolderId(utenteActa.getRootActa(), repositoryId, principalId, documentoActa.getTipoStrutturaRoot());

			// VitalRecordCodeType
			VitalRecordCodeType[] elencoVitalRecordCodeType = acarisManagementService.recuperaVitalRecordCode(repositoryId);

			VitalRecordCodeType vitalRecordCodeType = estratiVitalRecordCodeType(elencoVitalRecordCodeType, utenteActa.getIdvitalrecordcodetype());

			ObjectIdType strutturaId = recuperaStrutturaFolderId(documentoActa.getFolder(), repositoryId, principalId, folderId, documentoActa.getTipoStrutturaFolder());

			if (strutturaId == null){
				if(documentoActa.getTipoStrutturaFolder().intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_VOLUME){
					log.debug(method + ". creazione volume");
					strutturaId = acarisObjectService.creaVolume(repositoryId, principalId, folderId, vitalRecordCodeType, documentoActa, utenteActa);
				}
				else if(documentoActa.getTipoStrutturaFolder().intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_FASCICOLO){
					log.debug(method + ". creazione fascicolo");
					strutturaId = acarisObjectService.creaFascicolo(repositoryId, principalId, folderId, vitalRecordCodeType, documentoActa, utenteActa);
				}
				else{
					throw new IntegrationException("Tipo struttura acta non gestita");
				}
			}
			
			Integer idFormaDocumentaria = acarisObjectService.getIdFormaDocumentaria(repositoryId, principalId, utenteActa);
			
			// get Stato efficacia			
			Integer idStatoDiEfficacia = acarisObjectService.getStatoDiEfficacia(repositoryId, principalId, utenteActa.getIdStatoDiEfficacia());
						
			boolean isProtocollazioneInUscitaSenzaDocumento = false;			
		
			IdentificatoreDocumento identificatoreDocumento= acarisDocumentService.creaDocumentoElettronico(repositoryId, principalId, strutturaId, vitalRecordCodeType, idStatoDiEfficacia, idFormaDocumentaria, null, null, documentoActa, isProtocollazioneInUscitaSenzaDocumento);
			
			if(identificatoreDocumento != null)
				log.debug(method + ". identificatoreDocumentoFisico = " + identificatoreDocumento.getObjectIdDocumento().hashCode());
			
			UUIDDocumento = getUUIDDocumentoByParolaChiave(documentoActa.getIdDocumento(), repositoryId, principalId);

			KeyDocumentoActa keyDocumentoActa =  ottengoKeyActa(documentoActa, UUIDDocumento, null, null, identificatoreDocumento);		
			
			return keyDocumentoActa;

		}
		finally {
			log.debug(method + ". END");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)	
	public KeyDocumentoActa protocollaDocumentoLogico(DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException {

		String method = "protocollaDocumentoLogico";
		log.debug(method + ". BEGIN");
		
		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;
		ObjectIdType rootId = null;
		VitalRecordCodeType[] elencoVitalRecordCodeType = null;
		IdentificatoreDocumento identificatoreDocumento = null;
		String UUIDDocumento = null;
		ObjectIdType idAOO = null;
		ObjectIdType idNodo = null;
		ObjectIdType idStruttura = null;
		IdentificazioneRegistrazione identificazioneRegistrazione = null;

		try{
			
			if(documentoActa == null)
				throw new IntegrationException("Documento acta non valorizzato");
			
			if(utenteActa == null)
				throw new IntegrationException("Utente acta non valorizzato");
			
			if(documentoActa.getTipoStrutturaFolder() == null)
				throw new IntegrationException("Tipo struttura acta  folder non valorizzato");
			if(documentoActa.getTipoStrutturaRoot() == null)
				throw new IntegrationException("Tipo struttura acta root non valorizzato");			
			
			//repositoryId
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());
			if (repositoryId != null)
				log.debug(method + ". repositoryId = " + repositoryId.hashCode());

			//principalId
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			if (principalId != null)
				log.debug(method + ". principalId = " + principalId.hashCode());

			//rootId
			rootId = recuperaRootFolderId(utenteActa.getRootActa(), repositoryId, principalId, documentoActa.getTipoStrutturaRoot());
			if (rootId == null){
				throw new IntegrationException("Impossibile recuperare il parametro Root Folder Id"); 
			}
			log.debug(method + ". rootId = " + rootId.hashCode());

			//vitalRecordCodeType
			elencoVitalRecordCodeType = acarisManagementService.recuperaVitalRecordCode(repositoryId);
			if (elencoVitalRecordCodeType!= null)
				for (int i=0; i<elencoVitalRecordCodeType.length; i++)
				{
					log.debug(method + ".  = elencoVitalRecordCodeType["+i+"].id == " + elencoVitalRecordCodeType[i].getIdVitalRecordCode().getValue());
					log.debug(method + ".  = elencoVitalRecordCodeType["+i+"].descrizione == " + elencoVitalRecordCodeType[i].getDescrizione());
					log.debug(method + ".  = elencoVitalRecordCodeType["+i+"].tempoDiVitalita == " + elencoVitalRecordCodeType[i].getTempoDiVitalita());
				}
			VitalRecordCodeType vitalRecordCodeType = estratiVitalRecordCodeType(elencoVitalRecordCodeType, utenteActa.getIdvitalrecordcodetype());

			//idAOO
			idAOO = acarisBackOfficeService.recuperaIdAOO(utenteActa.getIdAoo(), repositoryId, principalId);
			if (idAOO != null)
				log.debug(method + ". idAOO = " + idAOO.hashCode());

			//se la protocollazione � in arrivo, creiamo il fascicolo (verificando se esiste gi�)
			//fascicoloAnnualeId
			ObjectIdType strutturaId = null;
			if (documentoActa.getSoggettoActa().isMittente()){
				log.debug(method + ". Running Servizio recuperaFascicoloAnnualeFolderIdFolderId...");
				
				strutturaId = recuperaStrutturaFolderId(documentoActa.getFolder(), repositoryId, principalId, rootId, documentoActa.getTipoStrutturaFolder());			
				
				if (strutturaId == null){
					strutturaId = creaStruttura(repositoryId, principalId, rootId, vitalRecordCodeType, documentoActa, utenteActa);
				}
				
				if (strutturaId != null)
					log.debug(method + ". strutturaId = " + strutturaId.hashCode());
				
				
			} 
			else //se la protocollazione � in partenza, attraverso la query recuperiamo il fascicolo della protocollazione in arrivo
			{
				/*
				 * DP 
				 * gestire nuovo scenario per la PEC:
				 * protocollazione in uscita non associata ad una protocollazione in ingresso (numeroRegistrazionePrecedente non valorizzato)
				 * deve creare la struttura con il folder passato come parametro
				 */
				if(StringUtils.isEmpty(documentoActa.getMetadatiActa().getNumeroRegistrazionePrecedente())){
					strutturaId = recuperaStrutturaFolderId(documentoActa.getFolder(), repositoryId, principalId, rootId, documentoActa.getTipoStrutturaFolder());			
					if (strutturaId == null){
						strutturaId = creaStruttura(repositoryId, principalId, rootId, vitalRecordCodeType, documentoActa, utenteActa);
					}
					if (strutturaId != null)
						log.debug(method + ". strutturaId = " + strutturaId.hashCode());
					
				}
				else{
					log.debug(method + ". Running Servizio recuperaIdFascicoloProtocollazioneInEntrataAssociata...");
					ObjectIdType idFascicolo = acarisOfficialBookService.recuperaIdFascicoloProtocollazioneInEntrataAssociata(repositoryId, principalId,documentoActa.getMetadatiActa().getNumeroRegistrazionePrecedente(),documentoActa.getMetadatiActa().getAnnoRegistrazionePrecedente(),idAOO);
					strutturaId = acarisNavigationService.recuperaFascicoloProtocollazione(repositoryId, principalId, idFascicolo);
				}
			}
			
			//idFormaDocumentaria
			Integer idFormaDocumentaria = acarisObjectService.getIdFormaDocumentaria(repositoryId, principalId, utenteActa);
			
			//idStatoDiEfficacia
			log.debug(method + ". Running Servizio getStatoDiEfficacia...");
			Integer idStatoDiEfficacia = acarisObjectService.getStatoDiEfficacia(repositoryId, principalId, utenteActa.getIdStatoDiEfficacia());			
		
			identificatoreDocumento = acarisDocumentService.creaDocumentoSoloMetadati(repositoryId, principalId, strutturaId, vitalRecordCodeType, idFormaDocumentaria, idStatoDiEfficacia, documentoActa);			
			if (identificatoreDocumento != null){
				log.debug(method + ". identificatoreDocumento = " + identificatoreDocumento.getObjectIdDocumento().hashCode());
				log.debug(method + ". identificatoreDocumento.idClassificazione = " + identificatoreDocumento.getObjectIdClassificazione());
				log.debug(method + ". identificatoreDocumento.idClassificazione.hashCode = " + identificatoreDocumento.getObjectIdClassificazione().hashCode());
			}
			//UUIDDocumento
			log.debug(method + ". Running Servizio getUUIDDocumento...");	

			UUIDDocumento = getUUIDDocumentoByParolaChiave(documentoActa.getIdDocumento(), repositoryId, principalId);
			//idNodo
			log.debug(method + ". Running Servizio recuperaIdNodo...");
			idNodo = acarisBackOfficeService.recuperaIdNodo(utenteActa.getIdNodo(), repositoryId, principalId);
			if (idNodo != null)
				log.debug(method + ". idNodo = " + idNodo.hashCode());

			//idStruttura
			log.debug(method + ". Running Servizio recuperaIdStruttura...");
			idStruttura = acarisBackOfficeService.recuperaIdStruttura(utenteActa.getIdStruttura(), repositoryId, principalId);
			if (idStruttura != null)
				log.debug(method + ". idStruttura = " + idStruttura.hashCode());

			//identificazioneRegistrazione
			
			if (documentoActa.getSoggettoActa().isMittente()) 
			{
				//Arrivo (Mittente)
				log.debug(method + ". Running Servizio creaRegistrazioneInArrivoDaDocumentoLogicoEsistente...");
				identificazioneRegistrazione = acarisOfficialBookService
											   .creaRegistrazioneInArrivoDaDocumentoLogicoEsistente(repositoryId, 
													   												principalId, 
													   												(identificatoreDocumento != null) ? identificatoreDocumento.getObjectIdClassificazione() : null, 
													   												idStruttura, 
													   												idNodo, 
													   												idAOO, 
													   												documentoActa);
			}
			else 
			{
				//Partenza (Destinatario)
				log.debug(method + ". Running Servizio creaRegistrazioneInPartenzaDaDocumentoLogicoEsistente...");
				identificazioneRegistrazione = acarisOfficialBookService
											   .creaRegistrazioneInPartenzaDaDocumentoLogicoEsistente(repositoryId, 
													   												  principalId, 
													   												  (identificatoreDocumento != null) ? identificatoreDocumento.getObjectIdClassificazione() : null, 
													   												  idStruttura, 
													   												  idNodo, 
													   												  idAOO, 
													   												  documentoActa);
			}
			
			if (identificazioneRegistrazione != null)
				log.debug(method + ". identificazioneRegistrazione Numero = " + identificazioneRegistrazione.getNumero());
			
			//INSERIRE CODICE CHE REPERISCE INDICE CLASSIFICAZIONE
			String indiceDiClassificazione = acarisObjectService
											 .getIdIndiceClassificazione(repositoryId, 
													 					 principalId, 
													 					(identificatoreDocumento != null) ? identificatoreDocumento.getObjectIdClassificazione() : null);
			log.debug(method + ". ***********************************************************************indiceDiClassificazione: " + indiceDiClassificazione);
			
			return ottengoKeyActa(documentoActa, UUIDDocumento, indiceDiClassificazione, identificazioneRegistrazione, identificatoreDocumento);
		}
		finally
		{
			log.info(method + ". END");
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)	
	public void associaDocumentoFisico(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa) throws IntegrationException 
	{
		String method = "associaDocumentoFisico";
		log.debug(method + ". BEGIN");
		if(log.isDebugEnabled())	
		{
			log.debug(method + ". documentoElettronicoActa.getNomeFile = " + documentoElettronicoActa.getNomeFile());

		}
		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;

		try
		{
			log.debug(method + ". Running Servizio recuperaIdRepository...");
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());

			if (repositoryId != null)
				log.debug(method + ". repositoryId = " + repositoryId.hashCode());

			log.debug(method + ". Running Servizio recuperaPrincipalId...");
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			if (principalId != null)
				log.debug(method + ". principalId = " + principalId.hashCode());

			Integer idStatoDiEfficacia = acarisObjectService.getStatoDiEfficacia(repositoryId, principalId, utenteActa.getIdStatoDiEfficacia());

			log.debug(method + ". Running Servizio trasformaDocumentoPlaceholderInDocumentoElettronico...");
			log.debug(method + ". idStatoDiEfficacia = " + idStatoDiEfficacia);

			acarisDocumentService.trasformaDocumentoPlaceholderInDocumentoElettronico(repositoryId, principalId, documentoElettronicoActa, idStatoDiEfficacia.intValue());
			
		}
		finally
		{
			log.info(method + ". END");
		}
	}
	
	private VitalRecordCodeType estratiVitalRecordCodeType(VitalRecordCodeType[] elencoVitalRecordCodeType, int idVitalRecordCode)
	{
		String method = "estratiVitalRecordCodeType";
		log.info(method + ". BEGIN");
		VitalRecordCodeType result = null;
		if(log.isDebugEnabled())	
			log.debug(method + ". idVitalRecordCode  = " + idVitalRecordCode);
		try
		{
			if (elencoVitalRecordCodeType != null)
			{
				for (int i=0; i< elencoVitalRecordCodeType.length; i++)
				{
					VitalRecordCodeType temp = elencoVitalRecordCodeType[i];
					if (temp.getIdVitalRecordCode().getValue() == idVitalRecordCode)
					{
						result = temp;
						break;
					}	
				}
			}
			return result;
		}
		finally
		{
			if(log.isDebugEnabled()) {	
				if (result != null) {
					log.debug(method + ". result  = " + result.getIdVitalRecordCode().hashCode());
				}
				else {
					log.debug(method + ". result  = " + result);
				}
			}
			log.debug(method + ". END");
		}
	}
	
	private KeyDocumentoActa ottengoKeyActa(DocumentoActa documentoActa, String UUIDDocumento, String indiceClassificazione, IdentificazioneRegistrazione identificazioneRegistrazione, IdentificatoreDocumento identificatoreDocumento)
	{
		KeyDocumentoActa keyActa = new KeyDocumentoActa(documentoActa.getIdDocumento());

		if (UUIDDocumento != null){
			keyActa.setUUIDDocumento(UUIDDocumento);
		}
		keyActa.setIndiceClassificazione(indiceClassificazione);
		
		if (identificazioneRegistrazione != null)
		{
			keyActa.setNumeroProtocollo(identificazioneRegistrazione.getNumero()+"/"+String.valueOf(DateFormat.getCurrentYear()));
			keyActa.setRegistrazioneId(identificazioneRegistrazione.getRegistrazioneId().getValue());
			keyActa.setClassificazioneId(identificazioneRegistrazione.getClassificazioneId().getValue());
		}
		if (identificatoreDocumento != null)
		{
			keyActa.setObjectIdClassificazione(identificatoreDocumento.getObjectIdClassificazione().getValue());
			keyActa.setObjectIdDocumento(identificatoreDocumento.getObjectIdDocumento().getValue());
		}


		return keyActa;
	}

	@Transactional(propagation=Propagation.REQUIRED)	
	public KeyDocumentoActa protocollaDocumentoFisico(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa,boolean isProtocollazioneInUscitaSenzaDocumento) throws IntegrationException 
	{
		String method = "protocollaDocumentoFisico";
		log.debug(method + ". BEGIN");

		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;
		ObjectIdType rootId = null;
		VitalRecordCodeType[] elencoVitalRecordCodeType = null;
		IdentificatoreDocumento identificatoreDocumentoFisico = null;
		String UUIDDocumento = null;
		ObjectIdType idAOO = null;
		ObjectIdType idNodo = null;
		ObjectIdType idStruttura = null;
		IdentificazioneRegistrazione identificazioneRegistrazione = null;

		try {
			
			if(documentoElettronicoActa == null)
				throw new IntegrationException("Documento acta non valorizzato");
			
			if(utenteActa == null)
				throw new IntegrationException("Utente acta non valorizzato");
			
			if(documentoElettronicoActa.getTipoStrutturaFolder() == null)
				throw new IntegrationException("Tipo struttura acta folder non valorizzato");
			
			if(documentoElettronicoActa.getTipoStrutturaRoot() == null)
				throw new IntegrationException("Tipo struttura acta root non valorizzato");
			
			//repositoryId
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());
			
			//principalId
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			
			//rootId
			log.debug(method + ". recupera root folder Id ...");
			rootId = recuperaRootFolderId(utenteActa.getRootActa(), repositoryId, principalId, documentoElettronicoActa.getTipoStrutturaRoot());
			
			//vitalRecordCodeType
			elencoVitalRecordCodeType = acarisManagementService.recuperaVitalRecordCode(repositoryId);
			VitalRecordCodeType vitalRecordCodeType = estratiVitalRecordCodeType(elencoVitalRecordCodeType, utenteActa.getIdvitalrecordcodetype());

			//idStatoDiEfficacia
			log.debug(method + ". getStatoDiEfficacia...");
			Integer idStatoDiEfficacia = acarisObjectService.getStatoDiEfficacia(repositoryId, principalId, utenteActa.getIdStatoDiEfficacia());
			
			//idFormaDocumentaria
			//log.debug(method + ". Running Servizio getIdFormaDocumentaria...");
			Integer idFormaDocumentaria = null;
			
			//idAOO
			log.debug(method + ". Running Servizio recuperaIdAOO...");
			idAOO = acarisBackOfficeService.recuperaIdAOO(utenteActa.getIdAoo(), repositoryId, principalId);
			if (idAOO != null)
				log.debug(method + ". idAOO = " + idAOO.hashCode());
					
			//se la protocollazione � in arrivo,creiamo il fascicolo (verificando se esiste gi�)
			//fascicoloAnnualeId
			ObjectIdType strutturaId = null;
			if (documentoElettronicoActa.getSoggettoActa().isMittente()){
				log.debug(method + ". Running Servizio recuperaFascicoloAnnualeFolderIdFolderId...");
				
				strutturaId = recuperaStrutturaFolderId(documentoElettronicoActa.getFolder(), repositoryId, principalId, rootId, documentoElettronicoActa.getTipoStrutturaFolder());			
				
				if (strutturaId == null){
					
					strutturaId = creaStruttura(repositoryId, principalId, rootId, vitalRecordCodeType, documentoElettronicoActa, utenteActa);				
					
				}
				if (strutturaId != null)
					log.debug(method + ". strutturaId = " + strutturaId.hashCode());
				
				
			} 
			else //se la protocollazione � in partenza, attraverso la query recuperiamo il fascicolo della protocollazione in arrivo
			{
				//per conam ho necssita di protocollare un documento in uscita senza un documento in ingresso gia protocollato
				if(isProtocollazioneInUscitaSenzaDocumento){
					strutturaId = recuperaStrutturaFolderId(documentoElettronicoActa.getFolder(), repositoryId, principalId, rootId, documentoElettronicoActa.getTipoStrutturaFolder());			
					if (strutturaId == null){
						strutturaId = creaStruttura(repositoryId, principalId, rootId, vitalRecordCodeType, documentoElettronicoActa, utenteActa);					
					}
					if (strutturaId != null)
						log.debug(method + ". strutturaId = " + strutturaId.hashCode());
				}else{
					log.debug(method + ". Ho un documento effettuo il recupero della protocollazione in precedente");
					log.debug(method + ". Running Servizio recuperaIdFascicoloProtocollazioneInEntrataAssociata...");
					ObjectIdType idFascicolo = acarisOfficialBookService.recuperaIdFascicoloProtocollazioneInEntrataAssociata(repositoryId, principalId,documentoElettronicoActa.getMetadatiActa().getNumeroRegistrazionePrecedente(),documentoElettronicoActa.getMetadatiActa().getAnnoRegistrazionePrecedente(),idAOO);
					strutturaId = acarisNavigationService.recuperaFascicoloProtocollazione(repositoryId, principalId, idFascicolo);
					
				}
				
			}
			
			idFormaDocumentaria =acarisObjectService.getIdFormaDocumentaria(repositoryId, principalId, utenteActa);
			
			//acarisDocumentServiceStadoc.creaDocumentoElettronico
			// CREA DOCUMENTO ELETTRONICO
			
			identificatoreDocumentoFisico = acarisDocumentService.creaDocumentoElettronico(repositoryId, principalId, strutturaId, vitalRecordCodeType, idStatoDiEfficacia, idFormaDocumentaria, null, null, documentoElettronicoActa,isProtocollazioneInUscitaSenzaDocumento);
			if (identificatoreDocumentoFisico != null)
				log.debug(method + ". identificatoreDocumentoFisico = " + identificatoreDocumentoFisico.getObjectIdDocumento().hashCode());

			//UUIDDocumento
			log.debug(method + ". Running Servizio getUUIDDocumento...");
			
			//UUIDDocumento = getUUIDDocumento(documentoElettronicoActa.getIdDocumento() + (documentoElettronicoActa.getMetadatiActa().getDescrizioneTipoLettera()!=null?" - " + documentoElettronicoActa.getMetadatiActa().getDescrizioneTipoLettera():""), repositoryId, principalId);

			UUIDDocumento = getUUIDDocumentoByParolaChiave(documentoElettronicoActa.getIdDocumento(), repositoryId, principalId);
			//idNodo
			log.debug(method + ". Running Servizio recuperaIdNodo...");
			idNodo = acarisBackOfficeService.recuperaIdNodo(utenteActa.getIdNodo(), repositoryId, principalId);
			if (idNodo != null)
				log.debug(method + ". idNodo = " + idNodo.hashCode());

			//idStruttura
			log.debug(method + ". Running Servizio recuperaIdStruttura...");
			idStruttura = acarisBackOfficeService.recuperaIdStruttura(utenteActa.getIdStruttura(), repositoryId, principalId);
			if (idStruttura != null)
				log.debug(method + ". idStruttura = " + idStruttura.hashCode());

			//identificazioneRegistrazione
			log.debug(method + ". isMittente() = " + documentoElettronicoActa.getSoggettoActa().isMittente());
			if (documentoElettronicoActa.getSoggettoActa().isMittente())
			{
				//Arrivo (Mittente)
				identificazioneRegistrazione = acarisOfficialBookService
											   .creaRegistrazioneInArrivoDaDocumentoElettronicoEsistente(repositoryId, 
													   													 principalId, 
													   													 (identificatoreDocumentoFisico != null)?identificatoreDocumentoFisico.getObjectIdClassificazione():null, 
													   													 idStruttura, 
													   													 idNodo, 
													   													 idAOO, 
													   													 documentoElettronicoActa);
			}
			else
			{
				//Partenza (Destinatario)
				log.debug(method + ". Running Servizio creaRegistrazioneInPartenzaDaDocumentoElettronicoEsistente...");
				identificazioneRegistrazione = acarisOfficialBookService
											   .creaRegistrazioneInPartenzaDaDocumentoElettronicoEsistente(repositoryId, 
													   													   principalId, 
													   													   (identificatoreDocumentoFisico != null)?identificatoreDocumentoFisico.getObjectIdClassificazione():null, 
													   													   idStruttura, 
													   													   idNodo, 
													   													   idAOO, 
													   													   documentoElettronicoActa);
			}
			
			String numeroProtocollo = null;
			if (identificazioneRegistrazione != null){
				log.debug(method + ". identificazioneRegistrazione Numero = " + identificazioneRegistrazione.getNumero());
				numeroProtocollo = " - " + identificazioneRegistrazione.getNumero() + "/" + String.valueOf(DateFormat.getCurrentYear());
			}
			
			if(identificatoreDocumentoFisico != null){				
				log.debug(method + ". Running updatePropertiesOggettoDocumento...numeroProtocollo: " + numeroProtocollo);
				try {
					acarisObjectService.updatePropertiesOggettoDocumentoConProtocollo(repositoryId, identificatoreDocumentoFisico.getObjectIdClassificazione(), identificatoreDocumentoFisico.getObjectIdDocumento(), principalId, numeroProtocollo);
				}catch (Throwable t) {
					log.error("Errore nell'aggiornamento delle properties: ", t);
				}
			}
			   
			return ottengoKeyActa(documentoElettronicoActa, UUIDDocumento, null, identificazioneRegistrazione, identificatoreDocumentoFisico);
		}
		finally
		{
			log.debug(method + ". END");
		}
	}

	public List<DocumentoElettronicoActa> ricercaDocumentoByParolaChiave(String parolaChiave, UtenteActa utenteActa) throws IntegrationException
	{
		String method = "ricercaDocumentoByParolaChiave";
		log.debug(method + ". BEGIN");
		boolean result = false;
		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;
		ObjectIdType documentoId = null;
		List<DocumentoElettronicoActa> documentoElettronicoActaList = new ArrayList<DocumentoElettronicoActa>();	// 20200803_LC
		List<ObjectIdType> contentStreamIdList = new ArrayList<ObjectIdType>();	// 20200803_LC
		try
		{
			log.debug(method + ". Running Servizio recuperaIdRepository...");
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());

			if (repositoryId != null)
				log.debug(method + ". repositoryId = " + repositoryId.hashCode());

			log.debug(method + ". Running Servizio recuperaPrincipalId...");
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			if (principalId != null)
				log.debug(method + ". principalId = " + principalId.hashCode());

			log.debug(method + ". Running Servizio recuperaDocumentoSemplice...");
			// documentoId = recuperaDocumentoSemplice(idDocumento, repositoryId, principalId);
						
			documentoId = recuperaDocumentoSemplice(parolaChiave, repositoryId, principalId);
			
			
			if (documentoId != null)
			{
				log.debug(method + ". documentoId Hash Code = " + documentoId.hashCode());
				log.debug(method + ". documentoId Value     = " + documentoId.getValue());

			}

			log.debug(method + ". Running Servizio recuperaIdContentStream...");
			contentStreamIdList = acarisRelationshipService.recuperaIdContentStream(repositoryId, principalId, documentoId);
			if (contentStreamIdList != null)
			{
				log.debug(method + ". contentStreamId Hash Code = " + contentStreamIdList.get(0).hashCode());
				log.debug(method + ". contentStreamId Value     = " + contentStreamIdList.get(0).getValue());

			}		
			
			// 20200825_LC gestione doc multiplo
			if(contentStreamIdList != null && contentStreamIdList.size() > 0) {		
				
				// gestione byte[]
				for (int i=0; i < contentStreamIdList.size(); i++) {
					DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
					AcarisContentStreamType[] contentStream = acarisObjectService.getContentStream(repositoryId, contentStreamIdList.get(i), principalId);	
					documentoElettronicoActa.setNomeFile(contentStream[0].getFilename());	
					documentoElettronicoActa.setIdDocFisico(contentStreamIdList.get(i).getValue());// 20200827_LC
					try {
						InputStream in = contentStream[0].getStreamMTOM().getInputStream();
						byte[] byteArray=org.apache.commons.io.IOUtils.toByteArray(in);
						documentoElettronicoActa.setStream(byteArray);
						documentoElettronicoActaList.add(documentoElettronicoActa);
					} catch (Exception e) {
						log.warn(method + ". getContentStream error: " + e);
					}
				}			
			}
			return documentoElettronicoActaList;			
		}
		finally
		{
			log.debug(method + ". END");
		}
	}


	private ObjectIdType recuperaRootFolderId(String parolaChiave, ObjectIdType repositoryId, PrincipalIdType  principalId, Integer tipoStruttura) throws IntegrationException {

		String method = "recuperaRootFolderId";
		log.debug(method + ". BEGIN");

		ObjectIdType folderId = null;
		QueryableObjectType target = null;
		
		try {
			
			if(tipoStruttura == null)
				throw new IntegrationException("tipo struttura is null");
			
			log.debug(method + ". tipoStruttura= " + tipoStruttura);
			
			if(tipoStruttura.intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_VOLUME){
				//q = getTarget(EnumObjectType.SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE.value());
				target = getTarget(EnumObjectType.SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE.value());
				
				//VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE
			}
			else if(tipoStruttura.intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_FASCICOLO){
				target = getTarget(EnumObjectType.SERIE_FASCICOLI_PROPERTIES_TYPE.value());
			}
			else if(tipoStruttura.intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_DOSSIER){
				//x conam � solo dossier
				target = getTarget(EnumObjectType.DOSSIER_PROPERTIES_TYPE.value());
			}
			else{
				throw new IntegrationException("Tipo struttura acta non gestita");
			}
			
			folderId = acarisObjectService.getIdentificatoreTramiteParolaChiave(repositoryId, principalId, target, parolaChiave, null);
			
			if(folderId == null){
				throw new IntegrationException("Impossibile recuperare il parametro folderId");
			}
			
			if(log.isDebugEnabled()){
				log.debug(method + ". folderId= " + folderId.getValue());
			}		
			
		}
		catch(Exception e){
			log.error(method + ". Si e' verificato un errore in fase di recuperoRootFolderId: " + e);
			throw new IntegrationException(e.getMessage(), e);
		}
		finally{
			log.debug(method + ". END");
		}
		return folderId;
	}	

	private ObjectIdType recuperaStrutturaFolderId(String parolaChiave, ObjectIdType repositoryId, PrincipalIdType  principalId, ObjectIdType rootFolderId, Integer tipoStruttura) throws IntegrationException{
		String method = "recuperaStrutturaFolderId";
		log.debug(method + ". BEGIN");
		ObjectIdType folderId = null;
		try{
			QueryableObjectType q = null;
			
			if(tipoStruttura == null)
				throw new IntegrationException("tipo struttura is null");
			
			log.debug(method + ". tipoStruttura= " + tipoStruttura);
			
			if(tipoStruttura.intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_VOLUME){
				q = getTarget(EnumObjectType.VOLUME_SERIE_TIPOLOGICA_DOCUMENTI_PROPERTIES_TYPE.value());
			}
			else if(tipoStruttura.intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_FASCICOLO){
				q = getTarget(EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE.value());
			}
			else if(tipoStruttura.intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_DOSSIER){
				q = getTarget(EnumObjectType.DOSSIER_PROPERTIES_TYPE.value());
			}
			else{
				throw new IntegrationException("Tipo struttura acta non gestita");
			}
			folderId = acarisObjectService.getIdentificatoreTramiteParolaChiave(repositoryId, principalId, q, parolaChiave, rootFolderId);
			log.debug(method + ". FascicoloAnnualeFolderId = " + folderId);
		}
		catch(Exception e){
			log.error(method + ". Si e' verificato un errore in fase di recuperaStrutturaFolderId: " + e);
			throw new IntegrationException(e.getMessage(), e);
		}
		finally{
			log.debug(method + ". END");
		}
		return folderId;
	}
	
	private ObjectIdType creaStruttura(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType parentId, VitalRecordCodeType vitalRecordCodeType, DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException{
		String method = "creaStruttura";
		log.debug(method + ". BEGIN");
		ObjectIdType strutturaId = null;
		try{
			if(documentoActa.getTipoStrutturaFolder().intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_VOLUME){
				log.debug(method + ". creazione volume");
				strutturaId = acarisObjectService.creaVolume(repositoryId, principalId, parentId, vitalRecordCodeType, documentoActa, utenteActa);
			}
			else if(documentoActa.getTipoStrutturaFolder().intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_FASCICOLO){
				log.debug(method + ". creazione fascicolo");
				strutturaId = acarisObjectService.creaFascicolo(repositoryId, principalId, parentId, vitalRecordCodeType, documentoActa, utenteActa);
			}
			else if(documentoActa.getTipoStrutturaFolder().intValue() == DocumentoElettronicoActa.TIPO_STRUTTURA_DOSSIER){
				log.debug(method + ". creazione dossier");
				strutturaId = acarisObjectService.creaDossier(repositoryId, principalId, parentId, vitalRecordCodeType, documentoActa, utenteActa);
			}
			else{
				throw new IntegrationException("Tipo struttura acta non gestita");
			}
			
		}
		finally{
			log.debug(method + ". END");
		}
		return strutturaId;
	}	

	private String getUUIDDocumentoByParolaChiave(String parolaChiave, ObjectIdType repositoryId, PrincipalIdType  principalId) throws IntegrationException
	{
		String method = "getUUIDDocumento";
		log.debug(method + ". BEGIN");
		String UUIDDocumento = null;
		try
		{
			UUIDDocumento = acarisObjectService.getUUIDDocumento(repositoryId, principalId, getTarget(EnumObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE.value()), parolaChiave);
			log.debug(method + ". getUUIDDocumento = " + UUIDDocumento);	

		}
		finally
		{
			log.debug(method + ". END");
		}
		return UUIDDocumento;
	}

	private ObjectIdType recuperaDocumentoSemplice(String parolaChiave, ObjectIdType repositoryId, PrincipalIdType  principalId) throws IntegrationException
	{
		String method = "recuperaDocumentoSemplice";
		log.debug(method + ". BEGIN");
		ObjectIdType documentoSempliceId = null;
		try
		{
				documentoSempliceId = acarisObjectService.getIdentificatoreTramiteParolaChiave(repositoryId, principalId, getTarget(EnumObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE.value()), parolaChiave, null);		
		}
		finally
		{
			log.debug(method + ". END");
		}

		log.debug(method + ". documentoSempliceId = " + documentoSempliceId);

		return documentoSempliceId;
	}

	private QueryableObjectType getTarget(String val) {
		QueryableObjectType target = new QueryableObjectType();
		target.setObject(val);
		return target;
	}
	
	
	@Transactional(propagation=Propagation.REQUIRED)	
	public KeyDocumentoActa aggiungiAllegato(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa, String objectIdClassificazionePadre, String numeroProtocolloPadre, String pkAllegato) throws IntegrationException 
	{
		String method = "aggiungiAllegato";
		log.debug(method + ". BEGIN");

		String result = null;
		KeyDocumentoActa keyDocActa = null;
		 
		try {
			
			//repositoryId
			log.debug(method + ". Running Servizio recuperaIdRepository...");
			ObjectIdType repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());

			//principalId
			log.debug(method + ". Running Servizio recuperaPrincipalId...");
			PrincipalIdType principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);

			//vitalRecordCodeType
			log.debug(method + ". Running Servizio recuperaVitalRecordCode...");
			VitalRecordCodeType[] elencoVitalRecordCodeType = acarisManagementService.recuperaVitalRecordCode(repositoryId);
			log.debug(method + ". elencoVitalRecordCodeType: " + elencoVitalRecordCodeType);
			log.debug(method + ". utenteActa.getIdvitalrecordcodetype(): " + utenteActa.getIdvitalrecordcodetype());
			VitalRecordCodeType vitalRecordCodeType = estratiVitalRecordCodeType(elencoVitalRecordCodeType, utenteActa.getIdvitalrecordcodetype());

			//idStatoDiEfficacia
			log.debug(method + ". Running Servizio getStatoDiEfficacia...");
			Integer idStatoDiEfficacia = acarisObjectService.getStatoDiEfficacia(repositoryId, principalId, utenteActa.getIdStatoDiEfficacia());
			
			//idFormaDocumentaria
			log.debug(method + ". Running Servizio getIdFormaDocumentaria...");
			Integer idFormaDocumentaria = null;
			idFormaDocumentaria = acarisObjectService.getIdFormaDocumentaria(repositoryId, principalId, utenteActa);
			
			//acarisDocumentServiceStadoc.creaDocumentoElettronico
			log.debug(method + ". Running Servizio creaDocumentoElettronico...");
			ObjectIdType objectTypeIdClassificazionePrincipale = new ObjectIdType();
			objectTypeIdClassificazionePrincipale.setValue(objectIdClassificazionePadre);
			
			IdentificatoreDocumento identificatoreDocumentoFisico = acarisDocumentService.creaDocumentoElettronico(repositoryId, principalId, objectTypeIdClassificazionePrincipale, vitalRecordCodeType, idStatoDiEfficacia, idFormaDocumentaria, numeroProtocolloPadre, pkAllegato,documentoElettronicoActa,false);
			if (identificatoreDocumentoFisico != null){
				log.debug(method + ". identificatoreDocumentoFisico = " + identificatoreDocumentoFisico.getObjectIdDocumento().hashCode());
				result = identificatoreDocumentoFisico.getObjectIdDocumento().getValue();
			}
			
			// 20200701_LC			
			String UUIDDocumento = getUUIDDocumentoByParolaChiave(documentoElettronicoActa.getIdDocumento(), repositoryId, principalId);
			keyDocActa = ottengoKeyActa(documentoElettronicoActa, UUIDDocumento, null, null, identificatoreDocumentoFisico);			
			
		}
		finally
		{
			log.debug(method + ". END");
		}
		return keyDocActa;
		
	}


	public List<DocumentoProtocollatoActa> ricercaDocumentoProtocollato(String numProtocollo, UtenteActa utenteActa) throws IntegrationException
	{
		String method = "ricercaDocumentoProtocollato";
		log.debug(method + ". BEGIN");
		boolean result = false;
		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;
//		ObjectIdType documentoId = null;
		List<DocumentoProtocollatoActa> documentoProtocollatoActaList = new ArrayList<DocumentoProtocollatoActa>();
//		ObjectIdType contentStreamId = null;
		ObjectIdType folderId = null;		// 20200707_LC è la stessa per master ed allegati
		
		try
		{
			log.debug(method + ". Running Servizio recuperaIdRepository...");
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());

			if (repositoryId != null)
				log.debug(method + ". repositoryId = " + repositoryId.hashCode());

			log.debug(method + ". Running Servizio recuperaPrincipalId...");
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			if (principalId != null)
				log.debug(method + ". principalId = " + principalId.hashCode());
			
			PagingResponseType response = acarisObjectService.getDocumentiTramiteProtocollo(repositoryId, principalId, getTarget("ClassificazioniProtocollateView"), numProtocollo, null);
			if(response != null && response.getObjectsLength() > 0){
				//documentoProtocollatoActaList = new ArrayList<>();
				
				for (int i = 0; i < response.getObjectsLength(); i++) {
					
					DocumentoProtocollatoActa documentoProtocollatoActa = new DocumentoProtocollatoActa();
					//String objectIdClassificazione = null;
					String idClassificazione = null;
					String idRegistrazione = null;
					ObjectIdType objectIdClassificazione = new ObjectIdType();
					
					// dalle properties recupero dataProtocollo e objectIdClassificazione che mi serve per recuperare l'indice classificazione estesa
					PropertyType[] prop = response.getObjects()[i].getProperties();
					for (int j = 0; j < prop.length; j++) {
						PropertyType propertyType = prop[j];
						log.debug(method + ". -> " + propertyType.getQueryName().getClassName());
						log.debug(method + ". -> " + propertyType.getQueryName().getPropertyName());	
						log.debug(method + ". -> " + propertyType.getValue());	
						if(propertyType.getQueryName().getPropertyName().equalsIgnoreCase("dataProtocollo")){
							ValueType vt = propertyType.getValue();
							documentoProtocollatoActa.setDataProtocollo(vt.getContent()[0]);
						}else if(propertyType.getQueryName().getPropertyName().equalsIgnoreCase("idClassificazione")) {	// 20200707_LC
							ValueType vt = propertyType.getValue();	
							idClassificazione = vt.getContent()[0];	
						}else if(propertyType.getQueryName().getPropertyName().equalsIgnoreCase("objectId")) {	// 20200707_LC
							ValueType vt = propertyType.getValue();	
							idRegistrazione = vt.getContent()[0];	
						}
					}
					
					if(StringUtils.isNotBlank(idClassificazione) && StringUtils.isNotBlank(idRegistrazione) ) {						
						log.debug(method + ". idClassificazione = " + idClassificazione);
						log.debug(method + ". idRegistrazione = " + idRegistrazione);						
						
						documentoProtocollatoActa.setClassificazioneId(idClassificazione);		// 20200707_LC è questo quello che ci si aspetta
						documentoProtocollatoActa.setRegistrazioneId(idRegistrazione);						
						
						String indiceClassificazioneEstesa = acarisObjectService.getIndiceClassificazioneEstesa(repositoryId, principalId, idClassificazione);	// 20200707_LC
						documentoProtocollatoActa.setClassificazioneEstesa(indiceClassificazioneEstesa);
						log.debug(method + ". indiceClassificazioneEstesa = " + indiceClassificazioneEstesa);
					
						objectIdClassificazione.setValue(idClassificazione);					
						ObjectResponseType documentoProtocollato = acarisNavigationService.recuperaChildren(repositoryId, principalId, objectIdClassificazione, 0);
						ObjectResponseType gruppoAllegati = acarisNavigationService.recuperaChildren(repositoryId, principalId, objectIdClassificazione, 1);
											
						// recupera folder
						folderId = acarisNavigationService.recuperaFascicoloProtocollazione(repositoryId, principalId, objectIdClassificazione);
						documentoProtocollatoActa.setFolderId(folderId.getValue());						

						if(documentoProtocollato!=null) {
							ObjectIdType docProtocollatoObj = documentoProtocollato.getObjectId();
							if (docProtocollatoObj != null) {
								log.debug(method + ". documentoId Hash Code = " + docProtocollatoObj.hashCode());
								log.debug(method + ". documentoId Value     = " + docProtocollatoObj.getValue());						
					
								documentoProtocollatoActa.setIdDocumento(docProtocollatoObj.getValue());
								log.debug(method + ". Running Servizio recuperaIdContentStream...");
							
								// se 1 elemento allora doc standard, se >1 è doc multiplo
								List<ObjectIdType> contentStreamIdList = acarisRelationshipService.recuperaIdContentStream(repositoryId, principalId, docProtocollatoObj);
								if (contentStreamIdList != null)
								{
									log.debug(method + "contentStreamId Hash Code = " + contentStreamIdList.get(0).hashCode());
									log.debug(method + "contentStreamId Value     = " + contentStreamIdList.get(0).getValue());
					
								}								
								if(contentStreamIdList != null && contentStreamIdList.size() > 0) {
									
									if (contentStreamIdList.size()==1) {
										// se size 1 c'è un solo doc fisico
										log.debug(method + "Running Servizio getContentStream...");
										AcarisContentStreamType[] contentStream = acarisObjectService.getContentStream(repositoryId, contentStreamIdList.get(0), principalId);	
										documentoProtocollatoActa.setNomeFile(contentStream[0].getFilename());
									} 
									else {
										// altrimenti si tratta di documento multiplo
										String oggettoDoc = "oggetto non disponibile";
										for (PropertyType propertyType : documentoProtocollato.getProperties()) {
											if(propertyType.getQueryName().getPropertyName().equals("oggetto"))
												oggettoDoc = propertyType.getValue().getContent()[0];
										}										
											documentoProtocollatoActa.setNomeFile("Documento multiplo - " + oggettoDoc);	// 20200828_LV metterci l'oggetto dopo un trattino	
										
											documentoProtocollatoActa.setFilenamesDocMultiplo(new ArrayList<String>());
											for (ObjectIdType docFisico : contentStreamIdList) {
												log.debug(method + "Running Servizio getContentStream...");
												AcarisContentStreamType[] contentStream = acarisObjectService.getContentStream(repositoryId, docFisico, principalId);	
												
												documentoProtocollatoActa.getFilenamesDocMultiplo().add(contentStream[0].getFilename());
											}
									}								
								}								
								
								// 20200711_LC recupera parola chiave e UUID
								String parolaChiave = acarisObjectService.getParolaChiaveDocumento(repositoryId, principalId, docProtocollatoObj);
								documentoProtocollatoActa.setParolaChiave(parolaChiave);
								String uuidDoc = getUUIDDocumentoByObjectIdDocumento(documentoProtocollatoActa.getIdDocumento(), repositoryId, principalId);
								documentoProtocollatoActa.setUiidDocumento(uuidDoc);								
							}
						}						

						// aggiunge master
						documentoProtocollatoActaList.add(documentoProtocollatoActa);						
						
						// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
						{
						// ------------------------------------------------------------------------------------------------------------------------------------
						// qui dovrebbe estrarre gli allegati ed aggiungerli anch'essi alla lista				
						// 1. getChildren della idClassificazioneMaster torna idGruppoAllegati ma anche idDOcumentoMaster
						// 2. getChildren della idGruppoAllegati torna  [idClassificazioneAllegato]
						// 3. getChildren di ogni [idClassificazioneAllegato] torna [idDocuemntoAllegato]				
						// creare per ogni allegato un DocumentoProtocollatoActa da aggiungere alla documentoProtocollatoActaList che ritorna questo metodo
						// ------------------------------------------------------------------------------------------------------------------------------------					

						if (gruppoAllegati!=null) {	// 20200708_LC
							ObjectIdType gruppoAllegatiObj = gruppoAllegati.getObjectId();
							PagingResponseType classificazioniAllegatiList = acarisNavigationService.recuperaAllChildrens(repositoryId, principalId, gruppoAllegatiObj);
							ObjectResponseType[] classificazioniAllegati = classificazioniAllegatiList.getObjects();
							
							for (int j = 0; j < classificazioniAllegati.length; j++) {
								
									DocumentoProtocollatoActa documentoProtocollatoActaAllegato = new DocumentoProtocollatoActa();
									documentoProtocollatoActaAllegato.setFolderId(folderId.getValue());	

									String idClassificazioneAllegato = classificazioniAllegati[j].getObjectId().getValue();
									ObjectIdType objectIdClassificazioneAllegato = new ObjectIdType();
									objectIdClassificazioneAllegato.setValue(idClassificazioneAllegato);
									documentoProtocollatoActaAllegato.setClassificazioneId(idClassificazioneAllegato);
									
									String indiceClassificazioneEstesaAllegato = acarisObjectService.getIndiceClassificazioneEstesa(repositoryId, principalId, idClassificazioneAllegato);
									documentoProtocollatoActaAllegato.setClassificazioneEstesa(indiceClassificazioneEstesaAllegato);
									
									ObjectResponseType documentoProtocollatoAllegato = acarisNavigationService.recuperaChildren(repositoryId, principalId, objectIdClassificazioneAllegato, 0);
									
									if(documentoProtocollatoAllegato!=null) {
										ObjectIdType docProtocollatoAllegatoObj = documentoProtocollatoAllegato.getObjectId();
										if (docProtocollatoAllegatoObj != null) {
											log.debug(method + ". documentoId Hash Code = " + docProtocollatoAllegatoObj.hashCode());
											log.debug(method + ". documentoId Value     = " + docProtocollatoAllegatoObj.getValue());						
								
											documentoProtocollatoActaAllegato.setIdDocumento(docProtocollatoAllegatoObj.getValue());
											log.debug(method + ". Running Servizio recuperaIdContentStream...");
										
											// se 1 elemento allora doc standard, se >1 è doc multiplo
											List<ObjectIdType> contentStreamIdList = acarisRelationshipService.recuperaIdContentStream(repositoryId, principalId, docProtocollatoAllegatoObj);
											if (contentStreamIdList != null)
											{
												log.debug(method + "contentStreamId Hash Code = " + contentStreamIdList.get(0).hashCode());
												log.debug(method + "contentStreamId Value     = " + contentStreamIdList.get(0).getValue());
								
											}
											
											// 20200825_LC gestione nome del documento considerando pure doc multiplo
											if(contentStreamIdList != null && contentStreamIdList.size() > 0) {
												
												if (contentStreamIdList.size()==1) {
													// se size 1 c'è un solo doc fisico
													log.debug(method + "Running Servizio getContentStream...");
													AcarisContentStreamType[] contentStream = acarisObjectService.getContentStream(repositoryId, contentStreamIdList.get(0), principalId);	
													documentoProtocollatoActaAllegato.setNomeFile(contentStream[0].getFilename());
												} 
												else {
													// altrimenti si tratta di documento multiplo
													String oggettoDoc = "oggetto non disponibile";
													for (PropertyType propertyType : documentoProtocollato.getProperties()) {
														if(propertyType.getQueryName().getPropertyName().equals("oggetto"))
															oggettoDoc = propertyType.getValue().getContent()[0];
													}										
													documentoProtocollatoActaAllegato.setNomeFile("Documento multiplo - " + oggettoDoc);	// 20200828_LV metterci l'oggetto dopo un trattino	
														
													documentoProtocollatoActaAllegato.setFilenamesDocMultiplo(new ArrayList<String>());
														for (ObjectIdType docFisico : contentStreamIdList) {
															log.debug(method + "Running Servizio getContentStream...");
															AcarisContentStreamType[] contentStream = acarisObjectService.getContentStream(repositoryId, docFisico, principalId);	
															documentoProtocollatoActaAllegato.getFilenamesDocMultiplo().add(contentStream[0].getFilename());
														}

												}
											
											}
											
											
											

											// 20200711_LC recupera parola chiave
											String parolaChiave = acarisObjectService.getParolaChiaveDocumento(repositoryId, principalId, docProtocollatoAllegatoObj);
											documentoProtocollatoActaAllegato.setParolaChiave(parolaChiave);
											String uuidDoc = getUUIDDocumentoByObjectIdDocumento(documentoProtocollatoActaAllegato.getIdDocumento(), repositoryId, principalId);
											documentoProtocollatoActaAllegato.setUiidDocumento(uuidDoc);

											
										}
									}
		
									
									documentoProtocollatoActaList.add(documentoProtocollatoActaAllegato);									
								
								

							}
							
						
							
						}
										
					}
					// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
						
				
						
					}
				
					
				}
		
				
				
			}

			return documentoProtocollatoActaList;
		}
		finally
		{
			log.debug(method + ". END");
		}
	}


	// 20200626_LC
	@Transactional(propagation=Propagation.REQUIRED)	
	public ObjectIdType spostaDocumento(DocumentoElettronicoActa documentoElettronicoActa, String numeroProtocollo, UtenteActa utenteActa) throws IntegrationException
	{
		String method = "spostaDocumento";
		log.debug(method + ". BEGIN");
		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;
		String idClassificazione = null;
		boolean isOfflineRequest = true; // (XXX) sempre true, se allegati<MAX allora lo fa in modo sincrono
		 
		// --
		
		try {
			
			log.debug(method + ". Running Servizio recuperaIdRepository...");
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());
			if (repositoryId != null) log.debug(method + ". repositoryId = " + repositoryId.hashCode());

			log.debug(method + ". Running Servizio recuperaPrincipalId...");
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			if (principalId != null) log.debug(method + ". principalId = " + principalId.hashCode());
			

		
			// input della moveDocument

			
			ObjectIdType rootId = recuperaRootFolderId(utenteActa.getRootActa(), repositoryId, principalId, documentoElettronicoActa.getTipoStrutturaRoot());
			
			// destinaton folder - se non esiste va creata come nella protocollazione standard
			ObjectIdType destFolderId = null;
			if (documentoElettronicoActa.getSoggettoActa().isMittente()){
				destFolderId = recuperaStrutturaFolderId(documentoElettronicoActa.getFolder(), repositoryId, principalId, rootId, documentoElettronicoActa.getTipoStrutturaFolder());				
				
				if (destFolderId == null){
					
					//vitalRecordCodeType
					VitalRecordCodeType[]  elencoVitalRecordCodeType = acarisManagementService.recuperaVitalRecordCode(repositoryId);
					VitalRecordCodeType vitalRecordCodeType = estratiVitalRecordCodeType(elencoVitalRecordCodeType, utenteActa.getIdvitalrecordcodetype());
					
					destFolderId = creaStruttura(repositoryId, principalId, rootId, vitalRecordCodeType, documentoElettronicoActa, utenteActa);				
					
				}
				if (destFolderId != null)
					log.debug(method + ".Creato folder, folderId = " + destFolderId.hashCode());
				
				
			} 
			
			
			
			// ricerca per protocollo per ottenere classificazioneId
			PagingResponseType responseRicercaProtocollo = acarisObjectService.getDocumentiTramiteProtocollo(repositoryId, principalId, getTarget("ClassificazioniProtocollateView"), numeroProtocollo, null);
			if(responseRicercaProtocollo != null && responseRicercaProtocollo.getObjectsLength() > 0){
				PropertyType[] prop = responseRicercaProtocollo.getObjects()[0].getProperties();
				for (int j = 0; j < prop.length; j++) {
					PropertyType propertyType = prop[j];
					log.debug(method + ". -> " + propertyType.getQueryName().getClassName());
					log.debug(method + ". -> " + propertyType.getQueryName().getPropertyName());	
					log.debug(method + ". -> " + propertyType.getValue());	
					if(propertyType.getQueryName().getPropertyName().equalsIgnoreCase("idClassificazione")) {	
						ValueType vt = propertyType.getValue();
						idClassificazione = vt.getContent()[0];	
					}
				}	
			}
			
			

			// classificazione documento
			ObjectIdType documentoClassificazioneId = new ObjectIdType();			
			documentoClassificazioneId.setValue(idClassificazione);


			// source folder
			ObjectIdType sourceFolderId = acarisNavigationService.recuperaFascicoloProtocollazione(repositoryId, principalId, documentoClassificazioneId);
			

	
			// lanciarla nel manage in modo che sia una SpostaDOcumentoException ?
			if (destFolderId == sourceFolderId) {
				throw new IntegrationException("Folder di partenza e di arrivo coincidenti.");
			}
			
			
			ObjectIdType idNuovaClassificazione = acarisObjectService.moveActaDocument(repositoryId, principalId, documentoClassificazioneId, sourceFolderId, destFolderId, isOfflineRequest);
			
			// gestire return type (KeyDocumentoActa come in protocolla?)
			return idNuovaClassificazione;
			
		}
		finally
		{
			log.debug(method + ". END");
		}
	}

	
	
	// 20200709_LC 
	public void aggiornaPropertiesActaPostSpostamento(String documentoId, String newParolaChiave, String newOggetto, UtenteActa utenteActa) throws IntegrationException
	{
		String method = "aggiornaPropertiesActaPostSpostamento";
		log.debug(method + ". BEGIN");
		try
		{
			ObjectIdType repositoryId = null;
			PrincipalIdType  principalId = null;
			ObjectIdType objectDocumentoId = new ObjectIdType();
			objectDocumentoId.setValue(documentoId);
			
			log.debug(method + ". Running Servizio recuperaIdRepository...");
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());

			if (repositoryId != null)
				log.debug(method + ". repositoryId = " + repositoryId.hashCode());

			log.debug(method + ". Running Servizio recuperaPrincipalId...");
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			if (principalId != null)
				log.debug(method + ". principalId = " + principalId.hashCode());
			
			// 20200721_LC aggiorna sia aprola chiave che oggetto
			acarisObjectService.updatePropertiesParolaChiaveDocumento(repositoryId, objectDocumentoId, principalId, newParolaChiave);

		}
		finally
		{
			log.debug(method + ". END");
		}
	}

	
	// 20200717_LC
	public List<DocumentoElettronicoActa> ricercaDocumentoByObjectIdDocumento(String objectIdDocumento, UtenteActa utenteActa) throws IntegrationException
	{
		String method = "ricercaDocumentoByObjectIdDocumento";
		log.debug(method + ". BEGIN");
		boolean result = false;
		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;
		//ObjectIdType documentoId = null;
		List<DocumentoElettronicoActa> documentoElettronicoActaList = new ArrayList<DocumentoElettronicoActa>();	// 20200803_LC
		List<ObjectIdType> contentStreamIdList = new ArrayList<ObjectIdType>();	// 20200803_LC
		try
		{
			log.debug(method + ". Running Servizio recuperaIdRepository...");
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());

			if (repositoryId != null)
				log.debug(method + ". repositoryId = " + repositoryId.hashCode());

			log.debug(method + ". Running Servizio recuperaPrincipalId...");
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			if (principalId != null)
				log.debug(method + ". principalId = " + principalId.hashCode());

			log.debug(method + ". Running Servizio recuperaDocumentoSemplice...");
			
			ObjectIdType documentoId = new ObjectIdType();
			documentoId.setValue(objectIdDocumento);

			log.debug(method + ". Running Servizio recuperaIdContentStream...");
			contentStreamIdList = acarisRelationshipService.recuperaIdContentStream(repositoryId, principalId, documentoId);
			if (contentStreamIdList != null)
			{
				log.debug(method + ". contentStreamId Hash Code = " + contentStreamIdList.get(0).hashCode());
				log.debug(method + ". contentStreamId Value     = " + contentStreamIdList.get(0).getValue());

			}
			
			// 20200825_LC gestione doc multiplo
			if(contentStreamIdList != null && contentStreamIdList.size() > 0) {
		
				
				// gestione byte[]
				for (int i=0; i < contentStreamIdList.size(); i++) {
					DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
					AcarisContentStreamType[] contentStream = acarisObjectService.getContentStream(repositoryId, contentStreamIdList.get(i), principalId);	
					documentoElettronicoActa.setNomeFile(contentStream[0].getFilename());
					documentoElettronicoActa.setIdDocFisico(contentStreamIdList.get(i).getValue());// 20200827_LC
					try {
						InputStream in = contentStream[0].getStreamMTOM().getInputStream();
						byte[] byteArray=org.apache.commons.io.IOUtils.toByteArray(in);
						documentoElettronicoActa.setStream(byteArray);
						documentoElettronicoActaList.add(documentoElettronicoActa);
					} catch (Exception e) {
						log.warn(method + ". getContentStream error: " + e);
					}
				}	
				
			
			}

			return documentoElettronicoActaList;

		}
		finally
		{
			log.debug(method + ". END");
		}
	}
	
	
	
	
	
	// 20200825_LC
	public List<DocumentoElettronicoActa> ricercaDocumentoByObjectIdDocumentoFisico(String objectIdDocumentoFisico, UtenteActa utenteActa) throws IntegrationException
	{
		String method = "ricercaDocumentoByObjectIdDocumentoFisico";
		log.debug(method + ". BEGIN");
		boolean result = false;
		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;
		//ObjectIdType documentoId = null;
		List<DocumentoElettronicoActa> documentoElettronicoActaList = new ArrayList<DocumentoElettronicoActa>();	// 20200803_LC
		//List<ObjectIdType> contentStreamIdList = null;	// 20200803_LC
		try
		{
			log.debug(method + ". Running Servizio recuperaIdRepository...");
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());

			if (repositoryId != null)
				log.debug(method + ". repositoryId = " + repositoryId.hashCode());

			log.debug(method + ". Running Servizio recuperaPrincipalId...");
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			if (principalId != null)
				log.debug(method + ". principalId = " + principalId.hashCode());

			log.debug(method + ". Running Servizio recuperaDocumentoSemplice...");
			
			
			// si ha già l'id del content, non serve fare il recuperaIdCOntentStream
			ObjectIdType documentoFisicoId = new ObjectIdType();
			documentoFisicoId.setValue(objectIdDocumentoFisico);	
			
			DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
			AcarisContentStreamType[] contentStream = acarisObjectService.getContentStream(repositoryId, documentoFisicoId, principalId);	
			documentoElettronicoActa.setNomeFile(contentStream[0].getFilename());	
			documentoElettronicoActa.setIdDocFisico(objectIdDocumentoFisico);// 20200827_LC
			try {
				InputStream in = contentStream[0].getStreamMTOM().getInputStream();
				byte[] byteArray=org.apache.commons.io.IOUtils.toByteArray(in);
				documentoElettronicoActa.setStream(byteArray);
			} catch (Exception e) {
				log.warn(method + ". getContentStream error: " + e);
			}			
	

			documentoElettronicoActaList.add(documentoElettronicoActa);
			return documentoElettronicoActaList;

		}
		finally
		{
			log.debug(method + ". END");
		}
	}	
	
	// 20200722_LC
	private String getUUIDDocumentoByObjectIdDocumento(String objectIdDocumento, ObjectIdType repositoryId, PrincipalIdType  principalId) throws IntegrationException
	{
		String method = "getUUIDDocumentoByObjectIdDocumento";
		log.debug(method + ". BEGIN");
		String UUIDDocumento = null;
		try
		{
		
			UUIDDocumento = acarisObjectService.getUUIDDocumentoByObjectIdDocumento(repositoryId, principalId, getTarget(EnumObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE.value()), objectIdDocumento);
			log.debug(method + ". getUUIDDocumento = " + UUIDDocumento);	

		}
		finally
		{
			log.debug(method + ". END");
		}
		return UUIDDocumento;
	}	
	
	@Transactional(propagation=Propagation.REQUIRED)	
	public ObjectIdType copiaDocumento(DocumentoElettronicoActa documentoElettronicoActa, String numeroProtocollo, UtenteActa utenteActa) throws IntegrationException
	{
		String method = "copiaDocumento";
		log.debug(method + ". BEGIN");
		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;
		String idClassificazione = null;
		boolean isOfflineRequest = true;  
		
		try {
			
			log.debug(method + ". Running Servizio recuperaIdRepository...");
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());
			if (repositoryId != null) log.debug(method + ". repositoryId = " + repositoryId.hashCode());

			log.debug(method + ". Running Servizio recuperaPrincipalId...");
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			if (principalId != null) log.debug(method + ". principalId = " + principalId.hashCode());
			
			ObjectIdType rootId = recuperaRootFolderId(utenteActa.getRootActa(), repositoryId, principalId, documentoElettronicoActa.getTipoStrutturaRoot());
			
			// destinaton folder - se non esiste va creata come nella protocollazione standard
			ObjectIdType destFolderId = null;
			if (documentoElettronicoActa.getSoggettoActa().isMittente()){
				destFolderId = recuperaStrutturaFolderId(documentoElettronicoActa.getFolder(), repositoryId, principalId, rootId, documentoElettronicoActa.getTipoStrutturaFolder());				
				
				if (destFolderId == null){
					
					//vitalRecordCodeType
					VitalRecordCodeType[]  elencoVitalRecordCodeType = acarisManagementService.recuperaVitalRecordCode(repositoryId);
					VitalRecordCodeType vitalRecordCodeType = estratiVitalRecordCodeType(elencoVitalRecordCodeType, utenteActa.getIdvitalrecordcodetype());
					
					destFolderId = creaStruttura(repositoryId, principalId, rootId, vitalRecordCodeType, documentoElettronicoActa, utenteActa);				
					
				}
				if (destFolderId != null)
					log.debug(method + ".Creato folder, folderId = " + destFolderId.hashCode());
				
				
			} 
			
			
			// (XXX) qui per ottenre il classificazioneId dovrebbe esser sufficiente leggerlo dal DB (ricerca per numProtocollo e registrazioneId not null, prendendo la prima occorrenza nel tempo?)
			// visto che i documenti copiati hanno un idClassificazione nel DB diverso da quello nuovo assegnatogli (nel DB c'è l'originale) si continua con la ricerca
			// ricerca per protocollo per ottenere classificazioneId
			PagingResponseType responseRicercaProtocollo = acarisObjectService.getDocumentiTramiteProtocollo(repositoryId, principalId, getTarget("ClassificazioniProtocollateView"), numeroProtocollo, null);
			if(responseRicercaProtocollo != null && responseRicercaProtocollo.getObjectsLength() > 0){
				PropertyType[] prop = responseRicercaProtocollo.getObjects()[0].getProperties();
				for (int j = 0; j < prop.length; j++) {
					PropertyType propertyType = prop[j];
					log.debug(method + ". -> " + propertyType.getQueryName().getClassName());
					log.debug(method + ". -> " + propertyType.getQueryName().getPropertyName());	
					log.debug(method + ". -> " + propertyType.getValue());	
					if(propertyType.getQueryName().getPropertyName().equalsIgnoreCase("idClassificazione")) {	
						ValueType vt = propertyType.getValue();
						idClassificazione = vt.getContent()[0];	
					}
				}	
			}			

			// classificazione documento
			ObjectIdType documentoClassificazioneId = new ObjectIdType();			
			documentoClassificazioneId.setValue(idClassificazione);
			
			// source folder
			ObjectIdType sourceFolderId = acarisNavigationService.recuperaFascicoloProtocollazione(repositoryId, principalId, documentoClassificazioneId);
	
			// lanciarla nel manage in modo che sia una SpostaDOcumentoException ?
			if (destFolderId == sourceFolderId) {
				throw new IntegrationException("Folder di partenza e di arrivo coincidenti.");
			}			
			
			ObjectIdType idNuovaClassificazione = acarisMultifilingService.aggiungiClassificazione(repositoryId, principalId, documentoClassificazioneId, destFolderId, isOfflineRequest);
			
			// gestire return type (KeyDocumentoActa come in protocolla?)
			return idNuovaClassificazione;
			
		}
		finally
		{
			log.debug(method + ". END");
		}
	}	
	
	public String getParolaChiaveByObjectIdDocumento(String objectIdDocumento, UtenteActa utenteActa) throws IntegrationException
	{
		String method = "getParolaChiaveByObjectIdDocumento";
		log.debug(method + ". BEGIN");
		String parolaChiave = null;
		ObjectIdType repositoryId = null;
		PrincipalIdType  principalId = null;
		ObjectIdType objIdDoc = new ObjectIdType();
		objIdDoc.setValue(objectIdDocumento);
		try
		{
			
			
			log.debug(method + ". Running Servizio recuperaIdRepository...");
			repositoryId = acarisRepositoryService.recuperaIdRepository(utenteActa.getRepositoryName());

			if (repositoryId != null)
				log.debug(method + ". repositoryId = " + repositoryId.hashCode());

			log.debug(method + ". Running Servizio recuperaPrincipalId...");
			principalId = acarisBackOfficeService.recuperaPrincipalId(utenteActa, repositoryId);
			if (principalId != null)
				log.debug(method + ". principalId = " + principalId.hashCode());
			
			
			parolaChiave = acarisObjectService.getParolaChiaveDocumento(repositoryId, principalId, objIdDoc);
			log.debug(method + ". parolaChiave = " + parolaChiave);	

		}
		finally
		{
			log.debug(method + ". END");
		}
		return parolaChiave;
	}	
	
}