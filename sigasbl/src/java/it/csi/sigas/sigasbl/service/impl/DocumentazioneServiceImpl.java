/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.integration.doqui.DoquiConstants;
import it.csi.sigas.sigasbl.integration.doqui.DoquiServiceFactory;
import it.csi.sigas.sigasbl.integration.doqui.bean.*;
import it.csi.sigas.sigasbl.integration.doqui.exception.FruitoreException;
import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.integration.doqui.helper.impl.CommonManageDocumentoHelperImpl;
import it.csi.sigas.sigasbl.integration.doqui.service.*;
import it.csi.sigas.sigasbl.integration.doqui.utils.DateFormat;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasAllegato;
import it.csi.sigas.sigasbl.model.entity.SigasDocumenti;
import it.csi.sigas.sigasbl.model.entity.SigasStatoDocumento;
import it.csi.sigas.sigasbl.model.entity.SigasTipoDocumento;
import it.csi.sigas.sigasbl.model.mapper.entity.*;
import it.csi.sigas.sigasbl.model.repositories.*;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.SigasFruitoreVO;
import it.csi.sigas.sigasbl.model.vo.documenti.AllegatoDocumentazioneVO;
import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;
import it.csi.sigas.sigasbl.model.vo.documenti.StatoDocumentoVO;
import it.csi.sigas.sigasbl.model.vo.documenti.TipoDocumentoVO;
import it.csi.sigas.sigasbl.model.vo.home.AnaComunicazioniVO;
import it.csi.sigas.sigasbl.request.documentazione.ConfermaDocumentazioneRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneBoRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneRequest;
import it.csi.sigas.sigasbl.rest.api.impl.UploadFile;
import it.csi.sigas.sigasbl.service.IDocumentazioneService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;
import it.csi.sigas.sigasbl.util.DocumentUtils;
import it.doqui.acta.acaris.relationshipsservice.AcarisException;
import it.doqui.acta.actasrv.dto.acaris.type.archive.IdStatoDiEfficaciaType;
import it.doqui.acta.actasrv.dto.acaris.type.common.*;
import it.doqui.acta.actasrv.dto.acaris.type.document.IdentificatoreDocumento;
import it.doqui.acta.actasrv.dto.acaris.type.management.VitalRecordCodeType;
import it.doqui.acta.actasrv.dto.acaris.type.officialbook.IdentificazioneRegistrazione;
import it.doqui.index.ecmengine.client.webservices.dto.Node;
import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegate;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegateServiceLocator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.rpc.ServiceException;


@Service
public class DocumentazioneServiceImpl extends CommonManageDocumentoHelperImpl implements IDocumentazioneService {

    protected static Logger log = Logger.getLogger(DocumentazioneServiceImpl.class);

    @Autowired
    private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;

    @Autowired
    private AnagraficaSoggettiEntityMapper anagraficaSoggettiEntityMapper;

    @Autowired
    private SigasTipoDocumentoRepository sigasTipoDocumentoRepository;

    @Autowired
    private TipoDocumentoEntityMapper tipoDocumentoEntityMapper;

    @Autowired
    private SigasDocumentiRepository sigasDocumentiRepository;

    @Autowired
    private DocumentiEntityMapper documentiEntityMapper;

    @Autowired
    private StatoDocumentoEntityMapper statoDocumentoEntityMapper;

    @Autowired
    private StatoArchiviazioneEntityMapper statoArchiviazioneEntityMapper;

    @Autowired
    private SigasStatoDocumentoRepository sigasStatoDocumentoRepository;

    @Autowired
    private SigasStatoArchiviazioneRepository sigasStatoArchiviazioneRepository;

    @Autowired
    private AcarisObjectService acarisObjectService;

    @Autowired
    private AcarisRepositoryService acarisRepositoryService;

    @Autowired
    private AcarisBackOfficeService acarisBackOfficeService;

    @Autowired
    private AcarisDocumentService acarisDocumentService;

    @Autowired
    private AcarisOfficialBookService acarisOfficialBookService;

    @Autowired
    private AcarisNavigationService acarisNavigationService;

    @Autowired
    private AcarisManagementService acarisManagementService;

    @Autowired
    private DoquiServiceFactory acarisServiceFactory;

    @Autowired
    private SigasCParametroRepository sigasCParametroRepository;
    
	@Autowired
	private SigasCMessaggiRepository sigasCMessaggiRepository;

    @Autowired
    private ActaManagementService actaManagementService;

	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;
    ///OPERATORE BO

    @Override
    public List<StatoDocumentoVO> listaStatoDocumenti() {
        return statoDocumentoEntityMapper.mapListEntityToListVO((List<SigasStatoDocumento>) sigasStatoDocumentoRepository.findAll());
    }

    @Override
    public List<AnagraficaSoggettoVO> ricercaAziendeDocumentiInoltrati() {
        return anagraficaSoggettiEntityMapper.mapListEntityToListVO(sigasAnagraficaSoggettiRepository.findAziendeDocumentiInoltrati());
    }

    @Override
    public List<DocumentiVO> ricercaDocumenti(RicercaDocumentazioneBoRequest ricercaDocumentazioneBoRequest) {
        
    	List<String> listKeyOperDoc = new ArrayList<>();
    	List<String> listKeyOperAll = new ArrayList<>();
    	CsiLogAudit csiLogAudit = null;
    	
        List<SigasDocumenti> listDoc = sigasDocumentiRepository.ricercaDocumentiBo(
                ricercaDocumentazioneBoRequest.getAnagraficaSoggettoVO() != null ? ricercaDocumentazioneBoRequest.getAnagraficaSoggettoVO().getIdAnag() : 0L,
                ricercaDocumentazioneBoRequest.getIdStatoDocumento() != null ? ricercaDocumentazioneBoRequest.getIdStatoDocumento() : 0,
                ricercaDocumentazioneBoRequest.getDataProtocolloDal() != null ? DateFormat.format(ricercaDocumentazioneBoRequest.getDataProtocolloDal(), "yyyyMMdd") : "",
                ricercaDocumentazioneBoRequest.getDataProtocolloAl() != null ? DateFormat.format(ricercaDocumentazioneBoRequest.getDataProtocolloAl(), "yyyyMMdd") : "");

    	for (SigasDocumenti doc : listDoc) {
    		listKeyOperDoc.add(String.valueOf(doc.getIdDocumento()) );
    		List<SigasAllegato> listAll = doc.getSigasAllegatos();
    		for (SigasAllegato all: listAll) {
    			listKeyOperAll.add(String.valueOf(all.getIdAllegato()));
    		}
		}
    	if (!listKeyOperDoc.isEmpty()) {
    		csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"CONSULTAZIONE DOCUMENTI", "sigas_documenti",String.join("_", listKeyOperDoc) );
    		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
    	}
    	if (!listKeyOperAll.isEmpty()) {
    		csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"CONSULTAZIONE DOCUMENTI", "sigas_allegato",String.join("_", listKeyOperAll) );
    		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
    	}
        return documentiEntityMapper.mapListEntityToListVO(listDoc);
        
    }

    @Override
    public void salvaDocumentazioneBO(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale) {
        confermaDocumentazioneRequest.getDocumentiVO().setModDate(new Timestamp(new Date().getTime()));
        confermaDocumentazioneRequest.getDocumentiVO().setModUser(codFiscale);
        SigasDocumenti sigasDocumento = sigasDocumentiRepository.save(documentiEntityMapper.mapVOtoEntity(confermaDocumentazioneRequest.getDocumentiVO()));
        CsiLogAudit csiLogAudit = null;
        if (DoquiConstants.CODICE_STATO_LETT_RISP.equals(confermaDocumentazioneRequest.getDocumentiVO().getStatoDocumentoVO().getCodiceStato()) && 
        		confermaDocumentazioneRequest.getDocumentiVO().getNote().contains("PEC inviata")) {
        	csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERIMENTO DATA INVIO PEC", "sigas_documenti",String.valueOf(sigasDocumento.getIdDocumento()) );
  			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
  					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
        }
        else {
	        csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"MODIFICA STATO DOCUMENTAZIONE", "sigas_documenti",String.valueOf(sigasDocumento.getIdDocumento() +"_"+sigasDocumento.getSigasStatoDocumento().getCodiceStato()) );
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
        }
		for (SigasAllegato allegatoTmp : sigasDocumento.getSigasAllegatos()) {
            allegatoTmp.setSigasDocumenti(sigasDocumento);
        }
        sigasDocumentiRepository.save(sigasDocumento);
        if (confermaDocumentazioneRequest.getLetteraRisposta() != null) {

        	SigasDocumenti sigasDocumenti = sigasDocumentiRepository.save(documentiEntityMapper.mapVOtoEntity(confermaDocumentazioneRequest.getLetteraRisposta()));
            
			csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERIMENTO LETTERA DI RISPOSTA", "sigas_documenti",String.valueOf(sigasDocumenti.getIdDocumento()) );
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());

        }
    }

    @Override
    public DocumentiVO protocollaLetteraRisp(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale) {
        if (confermaDocumentazioneRequest.getLetteraRisposta().getFileMaster() != null) {
            confermaDocumentazioneRequest.getLetteraRisposta().setAnagraficaSoggettoVO(confermaDocumentazioneRequest.getDocumentiVO().getAnagraficaSoggettoVO());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(confermaDocumentazioneRequest.getDocumentiVO().getDataProtocollazione());
            int year = calendar.get(Calendar.YEAR);
            confermaDocumentazioneRequest.getLetteraRisposta().setAnnualita(String.valueOf(year));
            confermaDocumentazioneRequest.getLetteraRisposta().setCfPiva(confermaDocumentazioneRequest.getDocumentiVO().getCfPiva());
            confermaDocumentazioneRequest.getLetteraRisposta().setInsDate(new Timestamp(new Date().getTime()));
            confermaDocumentazioneRequest.getLetteraRisposta().setInsUser(codFiscale);
            confermaDocumentazioneRequest.getLetteraRisposta().setRifArchivio(confermaDocumentazioneRequest.getDocumentiVO().getNProtocollo());
            confermaDocumentazioneRequest.getLetteraRisposta().setStatoDocumentoVO(null);
            TipoDocumentoVO tipoDocumentoLetteraRisposta = confermaDocumentazioneRequest.getDocumentiVO().getTipoDocumentoVO();
            if (confermaDocumentazioneRequest.getDocumentiVO().getTipoDocumentoVO().getIdTipoDocumentoPadre() != null) {
                tipoDocumentoLetteraRisposta = tipoDocumentoEntityMapper.mapEntityToVO(sigasTipoDocumentoRepository.findOne(confermaDocumentazioneRequest.getDocumentiVO().getTipoDocumentoVO().getIdTipoDocumentoPadre()));
            }
            confermaDocumentazioneRequest.getLetteraRisposta().setTipoDocumentoVO(tipoDocumentoLetteraRisposta);
            confermaDocumentazioneRequest.getLetteraRisposta().setSigasStatoArchiviazioneVO(statoArchiviazioneEntityMapper.mapEntityToVO(sigasStatoArchiviazioneRepository.findByCodiceStato(DoquiConstants.CODICE_STATO_ARCHIVIAZIONE_CARICATO)));
            KeyDocumentoActa keyDocumentoActa = null;
            try {
                keyDocumentoActa = protocollaDocumentoFisico(false, confermaDocumentazioneRequest.getLetteraRisposta());
            } catch (IntegrationException | FruitoreException e) {
                e.printStackTrace();
            }
            if (keyDocumentoActa != null) {
                confermaDocumentazioneRequest.getLetteraRisposta().setNProtocollo(keyDocumentoActa.getNumeroProtocollo());
                confermaDocumentazioneRequest.getLetteraRisposta().setDataProtocollazione(new Timestamp(new Date().getTime()));
            }
            //elimino annualita utilizzata come variabile di appoggio per la protocollazione della lettera di risposta
            confermaDocumentazioneRequest.getLetteraRisposta().setAnnualita(null);
            confermaDocumentazioneRequest.getLetteraRisposta().setStatoDocumentoVO(statoDocumentoEntityMapper.mapEntityToVO(sigasStatoDocumentoRepository.findByCodiceStato(DoquiConstants.CODICE_STATO_LETT_RISP)));
            SigasDocumenti sigasDocumenti = sigasDocumentiRepository.save(documentiEntityMapper.mapVOtoEntity(confermaDocumentazioneRequest.getLetteraRisposta()));
            
            CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERIMENTO LETTERA DI RISPOSTA", "sigas_documenti",String.valueOf(sigasDocumenti.getIdDocumento()) );
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
        }
        return confermaDocumentazioneRequest.getLetteraRisposta();
    }

    @Override
    public List<DocumentiVO> ricercaLetteraRisposta(String rifArchivio) {
        return documentiEntityMapper.mapListEntityToListVO(sigasDocumentiRepository.findByRifArchivio(rifArchivio));
    }

    //OPERATORE FO

    @Override
    public List<AnagraficaSoggettoVO> ricercaAziendeAccreditato(String codFiscale) {
        return anagraficaSoggettiEntityMapper.mapListEntityToListVO(sigasAnagraficaSoggettiRepository.findAziendeAccreditato(codFiscale));
    }

    @Override
    public List<TipoDocumentoVO> listaTipoDocumenti() {
        return tipoDocumentoEntityMapper.mapListEntityToListVO((List<SigasTipoDocumento>) sigasTipoDocumentoRepository.findAll());
    }

    @Override
    public List<DocumentiVO> ricercaDocumenti(RicercaDocumentazioneRequest ricercaDocumentazioneRequest, String codFiscale) {
        
    	List<String> listKeyOperDoc = new ArrayList<>();
    	List<String> listKeyOperAll = new ArrayList<>();
    	CsiLogAudit csiLogAudit = null;
    	
        List<SigasDocumenti> listDoc =sigasDocumentiRepository.ricercaDocumenti(
                ricercaDocumentazioneRequest.getAnagraficaSoggettoVO() != null ? ricercaDocumentazioneRequest.getAnagraficaSoggettoVO().getIdAnag() : 0L,
                ricercaDocumentazioneRequest.getNprotocollo() != null ? ricercaDocumentazioneRequest.getNprotocollo() : "",
                ricercaDocumentazioneRequest.getAnnualita() != null ? ricercaDocumentazioneRequest.getAnnualita().toString() : "",
                ricercaDocumentazioneRequest.getTipoDocumentoVO() != null ? ricercaDocumentazioneRequest.getTipoDocumentoVO().getIdTipoDocumento() : 0,
                codFiscale != null ? codFiscale : "");
    	for (SigasDocumenti doc : listDoc) {
    		listKeyOperDoc.add(String.valueOf(doc.getIdDocumento()) );
    		List<SigasAllegato> listAll = doc.getSigasAllegatos();
    		for (SigasAllegato all: listAll) {
    			listKeyOperAll.add(String.valueOf(all.getIdAllegato()));
    		}
		}
    	if (!listKeyOperDoc.isEmpty()) {
    		csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"CONSULTAZIONE DOCUMENTI", "sigas_documenti",String.join("_", listKeyOperDoc) );
    		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
    	}
    	if (!listKeyOperAll.isEmpty()) {
    		csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"CONSULTAZIONE DOCUMENTI", "sigas_allegato",String.join("_", listKeyOperAll) );
    		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
    	}
        return documentiEntityMapper.mapListEntityToListVO(listDoc);

    }

    @Override
    public void salvaDocumentazione(ConfermaDocumentazioneRequest confermaDocumentazioneRequest, String codFiscale) throws BusinessException, ServiceException {
    	List<String> listKeyOperAll = new ArrayList<>();
        try {
            salvaDocumentazioneIndex(confermaDocumentazioneRequest.getDocumentiVO());
            confermaDocumentazioneRequest.getDocumentiVO().setInsDate(new Timestamp(new Date().getTime()));
            confermaDocumentazioneRequest.getDocumentiVO().setInsUser(codFiscale);
            for (AllegatoDocumentazioneVO allegatoTmp : confermaDocumentazioneRequest.getDocumentiVO().getSigasAllegatos()) {
                allegatoTmp.setStatoArchiviazioneVO(statoArchiviazioneEntityMapper.mapEntityToVO(sigasStatoArchiviazioneRepository.findByCodiceStato(DoquiConstants.CODICE_STATO_ARCHIVIAZIONE_DA_CARICARE)));
            }
            confermaDocumentazioneRequest.getDocumentiVO().setStatoDocumentoVO(statoDocumentoEntityMapper.mapEntityToVO(sigasStatoDocumentoRepository.findByCodiceStato(DoquiConstants.CODICE_STATO_PRESENTATO)));
            confermaDocumentazioneRequest.getDocumentiVO().setSigasStatoArchiviazioneVO(statoArchiviazioneEntityMapper.mapEntityToVO(sigasStatoArchiviazioneRepository.findByCodiceStato(DoquiConstants.CODICE_STATO_ARCHIVIAZIONE_DA_CARICARE)));
            if (confermaDocumentazioneRequest.getDocumentiVO().getTipoDocumentoVO().getCodiceTipoDocumento().equalsIgnoreCase("COMV")) {
                confermaDocumentazioneRequest.getDocumentiVO().setTipoDocumentoVO(confermaDocumentazioneRequest.getDocumentiVO().getTipoComunicazioneVO());
            }

            if (confermaDocumentazioneRequest.getDocumentiVO().getTipoDocumentoVO().getCodiceTipoDocumento().equalsIgnoreCase("RIMB")) {
                confermaDocumentazioneRequest.getDocumentiVO().setTipoDocumentoVO(confermaDocumentazioneRequest.getDocumentiVO().getTipoRimborsoVO());
            }

            SigasDocumenti sigasDocumenti = sigasDocumentiRepository.save(documentiEntityMapper.mapVOtoEntity(confermaDocumentazioneRequest.getDocumentiVO()));
            for (SigasAllegato allegatoTmp : sigasDocumenti.getSigasAllegatos()) {
                allegatoTmp.setInsUser(codFiscale);
                allegatoTmp.setInsDate(new Timestamp(new Date().getTime()));
                allegatoTmp.setSigasDocumenti(sigasDocumenti);
                listKeyOperAll.add(String.valueOf(allegatoTmp.getIdAllegato()));
            }
            sigasDocumentiRepository.save(sigasDocumenti);
            
            CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERIMENTO DOCUMENTAZIONE", "sigas_documenti",String.valueOf(sigasDocumenti.getIdDocumento() ));
    		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
    				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
    		if (!listKeyOperAll.isEmpty()) {
    			csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERIMENTO DOCUMENTAZIONE", "sigas_allegato",String.join("_", listKeyOperAll));
    			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
     				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
    		}
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void salvaDocumentazioneIndex(DocumentiVO documentiVO) throws IOException, BusinessException, ServiceException {
        UploadFile uploadFile = new UploadFile();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        String UID = uploadFile.uploadDocumentazione(documentiVO.getNomeFile(), documentiVO.getFileMaster(), String.valueOf(year), documentiVO.getAnagraficaSoggettoVO().getIdAnag().toString(), sigasCParametroRepository, sigasCMessaggiRepository );
        if (StringUtils.isEmpty(UID)) {
            throw new BusinessException(sigasCMessaggiRepository.findByDescChiaveMessaggio(ErrorCodes.DOC_GEN_EXCEPTION).getValoreMessaggio(), ErrorCodes.DOC_GEN_EXCEPTION);
        }
        documentiVO.setIdIndex(UID);
        for (AllegatoDocumentazioneVO allegatoTmp : documentiVO.getSigasAllegatos()) {
            String UIDallegato = uploadFile.uploadDocumentazione(allegatoTmp.getNomeFile(), allegatoTmp.getFile(), String.valueOf(year), documentiVO.getAnagraficaSoggettoVO().getIdAnag().toString(), sigasCParametroRepository, sigasCMessaggiRepository);
            if (StringUtils.isEmpty(UIDallegato)) {
                throw new BusinessException(sigasCMessaggiRepository.findByDescChiaveMessaggio(ErrorCodes.DOC_GEN_EXCEPTION).getValoreMessaggio(), ErrorCodes.DOC_GEN_EXCEPTION);
            }
            allegatoTmp.setIdIndex(UIDallegato);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public KeyDocumentoActa protocollaDocumentoFisico(boolean isProtocollazioneInUscitaSenzaDocumento, DocumentiVO documentiVO) throws IntegrationException, FruitoreException {
        String method = "protocollaDocumentoFisico";
        log.debug(method + ". BEGIN");
        // recupero fruitore
        SigasFruitoreVO sigasFruitore = getFruitore();
        UtenteActa utenteActa = new UtenteActa();
        DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
        DocumentoElettronicoActa documentoElettronicoActaAllegato = null;
        List<DocumentoElettronicoActa> listDocumentoElettronicoActaAllegato = new ArrayList<DocumentoElettronicoActa>();
        
        this.valorizzaDocumentoElettronicoActaUtenteActa(utenteActa, documentoElettronicoActa, sigasFruitore, documentiVO, documentoElettronicoActaAllegato, listDocumentoElettronicoActaAllegato);
        
        ObjectIdType repositoryId = null;
        PrincipalIdType principalId = null;
        ObjectIdType rootId = null;
        VitalRecordCodeType[] elencoVitalRecordCodeType = null;
        IdentificatoreDocumento identificatoreDocumentoFisico = null;
        IdentificatoreDocumento identificatoreDocumentoFisicoAllegato = null;
        String UUIDDocumento = null;
        ObjectIdType idAOO = null;
        ObjectIdType idNodo = null;
        ObjectIdType idStruttura = null;
        IdentificazioneRegistrazione identificazioneRegistrazione = null;
        try {
            if (documentoElettronicoActa.getTipoStrutturaFolder() == null)
                throw new IntegrationException("Tipo struttura acta folder non valorizzato");
            if (documentoElettronicoActa.getTipoStrutturaRoot() == null)
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
            VitalRecordCodeType vitalRecordCodeType = estratiVitalRecordCodeTypeByDescrizione(elencoVitalRecordCodeType, utenteActa.getDescrizioneVitalrecordcodetype());
            //idStatoDiEfficacia
            log.debug(method + ". getStatoDiEfficacia...");            
            Integer idStatoDiEfficacia = null;
            try {
                idStatoDiEfficacia = Long.valueOf(getStatoEfficaciaByDescrizione(repositoryId, principalId, utenteActa.getDescrizioneStatoDiEfficacia()).getValue()).intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Integer idFormaDocumentaria = null;
            log.debug(method + ". Running Servizio recuperaIdAOO...");
            idAOO = acarisBackOfficeService.recuperaIdAOO(utenteActa.getIdAoo(), repositoryId, principalId);
            if (idAOO != null)
                log.debug(method + ". idAOO = " + idAOO.hashCode());
            //se la protocollazione è in arrivo,creiamo il fascicolo (verificando se esiste già)
            ObjectIdType strutturaId = null;
            if (documentoElettronicoActa.getSoggettoActa().isMittente()) {
                log.debug(method + ". Running Servizio recuperaFascicoloAnnualeFolderIdFolderId...");
                strutturaId = recuperaStrutturaFolderId(documentoElettronicoActa.getFolder(), repositoryId, principalId, rootId, documentoElettronicoActa.getTipoStrutturaFolder());
                if (strutturaId == null) {
                    log.debug(method + ". creazione fascicolo");
                    strutturaId = acarisObjectService.creaFascicolo(repositoryId, principalId, rootId, vitalRecordCodeType, documentoElettronicoActa, utenteActa);
                }
                if (strutturaId != null)
                    log.debug(method + ". strutturaId = " + strutturaId.hashCode());
            }
            //se la protocollazione è in partenza, attraverso la query recuperiamo il fascicolo della protocollazione in arrivo
            else {
                if (isProtocollazioneInUscitaSenzaDocumento) {
                    strutturaId = recuperaStrutturaFolderId(documentoElettronicoActa.getFolder(), repositoryId, principalId, rootId, documentoElettronicoActa.getTipoStrutturaFolder());
                    if (strutturaId == null) {
                        strutturaId = acarisObjectService.creaFascicolo(repositoryId, principalId, rootId, vitalRecordCodeType, documentoElettronicoActa, utenteActa);
                    }
                    if (strutturaId != null)
                        log.debug(method + ". strutturaId = " + strutturaId.hashCode());
                } else {
                    log.debug(method + ". Ho un documento effettuo il recupero della protocollazione in precedente");
                    log.debug(method + ". Running Servizio recuperaIdFascicoloProtocollazioneInEntrataAssociata...");
                    ObjectIdType idFascicolo = acarisOfficialBookService.recuperaIdFascicoloProtocollazioneInEntrataAssociata(repositoryId, principalId, documentoElettronicoActa.getMetadatiActa().getNumeroRegistrazionePrecedente(), documentoElettronicoActa.getMetadatiActa().getAnnoRegistrazionePrecedente(), idAOO);
                    strutturaId = acarisNavigationService.recuperaFascicoloProtocollazione(repositoryId, principalId, idFascicolo);

                }
            }
            idFormaDocumentaria = acarisObjectService.getIdFormaDocumentaria(repositoryId, principalId, utenteActa);
            
            //SIGAS-225
            //Creazione parola chiave
            documentoElettronicoActa.setParolaChiave(UUID.randomUUID().toString());
            //----
            
            // CREA DOCUMENTO ELETTRONICO
            identificatoreDocumentoFisico = acarisDocumentService.creaDocumentoElettronico(repositoryId, principalId, strutturaId, vitalRecordCodeType, idStatoDiEfficacia, idFormaDocumentaria, null, null, documentoElettronicoActa, isProtocollazioneInUscitaSenzaDocumento);
            if (identificatoreDocumentoFisico != null)
                log.debug(method + ". identificatoreDocumentoFisico = " + identificatoreDocumentoFisico.getObjectIdDocumento().hashCode());
            
            //GESTIONE ALLEGATI
            for (DocumentoElettronicoActa documentoElettronicoAllegatoActa : listDocumentoElettronicoActaAllegato) {            	
                identificatoreDocumentoFisicoAllegato = acarisDocumentService.creaDocumentoElettronico(repositoryId, principalId, identificatoreDocumentoFisico.getObjectIdClassificazione(), vitalRecordCodeType, idStatoDiEfficacia, idFormaDocumentaria, null, "", documentoElettronicoAllegatoActa, isProtocollazioneInUscitaSenzaDocumento);
            }
            log.debug(method + ". Running Servizio getUUIDDocumento...");            
            
            //SIGAS-225
            //recupero attraverso parola chiave e non più medinate oggetto del documento che puo essere identico per più documenti 
            UUIDDocumento = getUUIDDocumentoByParolaChiave(documentoElettronicoActa.getParolaChiave(), repositoryId, principalId);            
            log.debug(method + ". Running Servizio recuperaIdNodo...");

            idNodo = acarisBackOfficeService.recuperaIdNodo(utenteActa.getIdNodo(), repositoryId, principalId);
            if (idNodo != null)
                log.debug(method + ". idNodo = " + idNodo.hashCode());

            log.debug(method + ". Running Servizio recuperaIdStruttura...");
            idStruttura = acarisBackOfficeService.recuperaIdStruttura(utenteActa.getIdStruttura(), repositoryId, principalId);
            if (idStruttura != null)
                log.debug(method + ". idStruttura = " + idStruttura.hashCode());

            log.debug(method + ". isMittente() = " + documentoElettronicoActa.getSoggettoActa().isMittente());
            if (documentoElettronicoActa.getSoggettoActa().isMittente()) {
                //Arrivo (Mittente)
                identificazioneRegistrazione = acarisOfficialBookService.creaRegistrazioneInArrivoDaDocumentoElettronicoEsistente(repositoryId, principalId, identificatoreDocumentoFisico.getObjectIdClassificazione(), idStruttura, idNodo, idAOO, documentoElettronicoActa);
            } else {
                //Partenza (Destinatario)
                log.debug(method + ". Running Servizio creaRegistrazioneInPartenzaDaDocumentoElettronicoEsistente...");
                identificazioneRegistrazione = acarisOfficialBookService.creaRegistrazioneInPartenzaDaDocumentoElettronicoEsistente(repositoryId, principalId, identificatoreDocumentoFisico.getObjectIdClassificazione(), idStruttura, idNodo, idAOO, documentoElettronicoActa);
            }

            if (identificazioneRegistrazione != null) {
                log.debug(method + ". identificazioneRegistrazione Numero = " + identificazioneRegistrazione.getNumero());
            }
            /* SIGAS-226
            if (identificatoreDocumentoFisico != null) {

                String numeroProtocollo = documentoElettronicoActa.getIdDocumento() + " - " + identificazioneRegistrazione.getNumero() + "/" + DateFormat.getCurrentYear();
                log.debug(method + ". Running updatePropertiesOggettoDocumento...numeroProtocollo: " + numeroProtocollo);
                try {
                    acarisObjectService.updatePropertiesOggettoDocumentoConProtocollo(repositoryId, identificatoreDocumentoFisico.getObjectIdClassificazione(), identificatoreDocumentoFisico.getObjectIdDocumento(), principalId, numeroProtocollo);
                } catch (Throwable t) {
                    log.error("Errore nell'aggiornamento delle properties: ", t);
                }
            }
            */       
           
            
            return ottengoKeyActa(documentoElettronicoActa, UUIDDocumento, null, identificazioneRegistrazione, identificatoreDocumentoFisico);
        } finally {
            log.debug(method + ". END");
        }
    }

    @Override
    public byte[] getPacchettoDocumenti(Integer idDocumento) throws IOException {
        byte[] datadown = null;
        SigasDocumenti sigasDocumenti = sigasDocumentiRepository.findOne(idDocumento);
        if (sigasDocumenti.getSigasStatoArchiviazione().getCodiceStato().equals(DoquiConstants.CODICE_STATO_ARCHIVIAZIONE_DA_CARICARE) ||
                StringUtils.isEmpty(sigasDocumenti.getNProtocollo())) {

            byte[] fileMaster = getDocumentoMaster(sigasDocumenti.getIdDocumento());
            DocumentiVO documentoVO = documentiEntityMapper.mapEntityToVO(sigasDocumenti);
            documentoVO.setFileMaster(fileMaster);

            for (AllegatoDocumentazioneVO allegatoVO : documentoVO.getSigasAllegatos()) {
                byte[] allegatoFile = getAllegato(sigasDocumenti.getIdDocumento(), allegatoVO.getNomeFile());
                allegatoVO.setFile(allegatoFile);

            }
            return filesZip(documentoVO);
        } else {
            String[] params = sigasDocumenti.getNProtocollo().split("/");
            if (params.length < 2)
                throw new BusinessException("Il numero di protocollo '" + sigasDocumenti.getNProtocollo() + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION);

            datadown = actaManagementService.download(params[1], params[0]);

        }
        return datadown;
    }

    @Override
    public byte[] getDocumentoMaster(Integer idDocumento) throws IOException {
        InputStream is = getClass().getResourceAsStream("/application.properties");
        Properties p = new Properties();
        p.load(is);
        byte[] datadown = null;
        SigasDocumenti sigasDocumenti = sigasDocumentiRepository.findOne(idDocumento);
        if (sigasDocumenti.getSigasStatoArchiviazione().getCodiceStato().equals(DoquiConstants.CODICE_STATO_ARCHIVIAZIONE_DA_CARICARE) ||
                StringUtils.isEmpty(sigasDocumenti.getNProtocollo())) {
            return getFileFromIndex(sigasDocumenti.getNomeFile(), sigasDocumenti.getIdIndex());
        } else {
            String[] params = sigasDocumenti.getNProtocollo().split("/");

            if (params.length < 2)
                throw new BusinessException("Il numero di protocollo '" + sigasDocumenti.getNProtocollo() + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION);

            datadown = actaManagementService.downloadMaster(params[1], params[0]);
        }
        return datadown;
    }

    @Override
    public byte[] getAllegato(Integer idDocumento, String nomeFile) throws IOException {
        byte[] datadown = null;
        SigasDocumenti sigasDocumenti = sigasDocumentiRepository.findOne(idDocumento);
        if (sigasDocumenti.getSigasStatoArchiviazione().getCodiceStato().equals(DoquiConstants.CODICE_STATO_ARCHIVIAZIONE_DA_CARICARE) ||
                StringUtils.isEmpty(sigasDocumenti.getNProtocollo())) {
            for (SigasAllegato sigasAllegato : sigasDocumenti.getSigasAllegatos()) {
                if (sigasAllegato.getNomeFile().equalsIgnoreCase(nomeFile)) {
                    datadown = getFileFromIndex(sigasAllegato.getNomeFile(), sigasAllegato.getIdIndex());
                }
            }
        } else {
            String[] params = sigasDocumenti.getNProtocollo().split("/");
            if (params.length < 2)
                throw new BusinessException("Il numero di protocollo '" + sigasDocumenti.getNProtocollo() + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION);
            try {
                datadown = actaManagementService.downloadAllegato(params[1], params[0], nomeFile);
            } catch (AcarisException e) {
                throw new BusinessException("Il numero di protocollo '" + params[0] + "' non contiene allegati", ErrorCodes.BUSSINESS_EXCEPTION);
            }
        }
        return datadown;
    }

    private byte[] filesZip(DocumentiVO filePacchetto) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(bo);
        ZipEntry zipEntryMaster = new ZipEntry(filePacchetto.getNomeFile());
        zipOut.putNextEntry(zipEntryMaster);
        zipOut.write(filePacchetto.getFileMaster());
        zipOut.flush();
        zipOut.closeEntry();
        for (AllegatoDocumentazioneVO file : filePacchetto.getSigasAllegatos()) {
            ZipEntry zipEntry = new ZipEntry(file.getNomeFile());
            zipOut.putNextEntry(zipEntry);
            zipOut.write(file.getFile());
            zipOut.flush();
            zipOut.closeEntry();
        }
        zipOut.close();
        return bo.toByteArray();
    }

    private byte[] getFileFromIndex(String nomeFile, String idIndex) {
        EcmEngineWebServiceDelegateServiceLocator ecmengineLocator = new EcmEngineWebServiceDelegateServiceLocator();
        EcmEngineWebServiceDelegate ecmengineDelegate;
        byte[] datadown = null;
        try {
            String baseUrl = sigasCParametroRepository.findByDescParametro("INDEX_BASEURL").getValoreString();
            String urlWs1 = baseUrl.concat(sigasCParametroRepository.findByDescParametro("INDEX_URLWS1").getValoreString());
            ecmengineDelegate = ecmengineLocator.getEcmEngineManagement(new URL(urlWs1));

            OperationContext context = new OperationContext();
            context.setFruitore(sigasCParametroRepository.findByDescParametro("INDEX_FRUITORE").getValoreString());
            context.setNomeFisico(nomeFile); // il nome del file da inserire, se non esiste indicare il nome fruitore (comeda doc di integrazione)
            context.setRepository(sigasCParametroRepository.findByDescParametro("INDEX_REPOSITORY").getValoreString());
            context.setUsername(sigasCParametroRepository.findByDescParametro("INDEX_USERNAME").getValoreString());
            context.setPassword(sigasCParametroRepository.findByDescParametro("INDEX_PASSWORD").getValoreString());

            Node parentNode = new Node();
            parentNode.setUid(idIndex);  // uid nodo padre cm:sigas
            Content content = new Content();
            content.setTypePrefixedName(sigasCParametroRepository.findByDescParametro("INDEX_TYPEPREFIXEDNAME").getValoreString());
            content.setModelPrefixedName(sigasCParametroRepository.findByDescParametro("INDEX_MODELPREFIXEDNAME").getValoreString());
            content.setParentAssocTypePrefixedName(sigasCParametroRepository.findByDescParametro("INDEX_PARENTASSOCTYPEPREFIXEDNAME").getValoreString());
            content.setPrefixedName("cm:" + nomeFile);     //nome del file
            content.setContentPropertyPrefixedName(sigasCParametroRepository.findByDescParametro("INDEX_CONTENTPROPERTYPREFIXEDNAME").getValoreString());
            String mimeType = URLConnection.guessContentTypeFromName(nomeFile);
            content.setMimeType(mimeType);
            content.setOptimize(false);
            content.setEncoding("UTF-8");
            datadown = ecmengineDelegate.retrieveContentData(parentNode, content, context);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), ErrorCodes.BUSSINESS_EXCEPTION);
        }
        return datadown;
    }   
    

    private void valorizzaDocumentoElettronicoActaUtenteActa(UtenteActa utenteActa, DocumentoElettronicoActa documentoElettronicoActa,
                                                             SigasFruitoreVO sigasFruitore, DocumentiVO documentiVO, DocumentoElettronicoActa documentoElettronicoActaAllegato,
                                                             List<DocumentoElettronicoActa> listDocumentoElettronicoActaAllegato) {
        //POJO
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);

        utenteActa.setCodiceFiscale(sigasFruitore.getCfActa());
        utenteActa.setIdAoo(new Integer(sigasFruitore.getIdAooActa()));
        utenteActa.setIdNodo(new Integer(sigasFruitore.getIdNodoActa()));
        utenteActa.setIdStruttura(new Integer(sigasFruitore.getIdStrutturaActa()));
        utenteActa.setApplicationKeyActa(sigasCParametroRepository.findByDescParametro(DoquiConstants.APPLICATION_KEY_ACTA).getValoreString());
        utenteActa.setRepositoryName(sigasCParametroRepository.findByDescParametro(DoquiConstants.REPOSITORY_ACTA).getValoreString());
        utenteActa.setRootActa(calcolaSerieFascicoloRoot(documentiVO.getTipoDocumentoVO()));
        utenteActa.setDescrizioneVitalrecordcodetype(documentiVO.getTipoDocumentoVO().getDescVitalRecordCodeType());
        boolean isDocSigned = DocumentUtils.isDocumentSigned(documentiVO.getFileMaster(), documentiVO.getNomeFile());
        if (isDocSigned) {
            utenteActa.setDescrizioneStatoDiEfficacia("PERFETTO ED EFFICACE");
        } else {
            utenteActa.setDescrizioneStatoDiEfficacia("PERFETTO ED EFFICACE MA NON FIRMATO");
        }
        if (documentiVO.getStatoDocumentoVO() == null) {
            documentoElettronicoActa.setIdDocumento(calcolaOggettoLetteraRisposta(documentiVO));
        } else {
            documentoElettronicoActa.setIdDocumento(calcolaOggetto(documentiVO, year));
        }
        if (documentiVO.getTipoDocumentoVO().getIdTipoDocumentoPadre() != null) {
            TipoDocumentoVO tipoDocumento = tipoDocumentoEntityMapper.mapEntityToVO(sigasTipoDocumentoRepository.findOne(documentiVO.getTipoDocumentoVO().getIdTipoDocumentoPadre()));
            documentoElettronicoActa.setFolder(tipoDocumento.getCodiceTipoDocumento() + "-" + year);
        } else {
            documentoElettronicoActa.setFolder(documentiVO.getTipoDocumentoVO().getCodiceTipoDocumento() + "-" + year);
        }
        documentoElettronicoActa.setApplicativoAlimentante(sigasFruitore.getCodFruitore());
        documentoElettronicoActa.setStream(documentiVO.getFileMaster());
        documentoElettronicoActa.setNomeFile(documentiVO.getNomeFile());
        documentoElettronicoActa.setDescrizione(documentiVO.getTipoDocumentoVO().getDescrizione());
        String mimeType = URLConnection.guessContentTypeFromName(documentiVO.getNomeFile());
        //SIGAS-225
        if(mimeType == null &&
           documentiVO.getNomeFile().contains("p7m")) 
        {
        	/*
        	String mimeArray[] = documentiVO.getNomeFile().split("\\.");
        	if(mimeArray!=null && mimeArray.length >= 2) {
        		mimeType = mimeArray[1];
        		try
    			{        			
    				EnumMimeTypeType.fromValue(mimeType);
    			}
    			catch (IllegalArgumentException e)
    			{
    				log.error("ERRORE MIMETYPE NON VALIDO -> " + mimeType);
    			}
        	}
        	*/
        	mimeType = EnumMimeTypeType.APPLICATION_PKCS_7_MIME.value();        	
        }
        //------------------------------        
        documentoElettronicoActa.setMimeType(mimeType);
        
        if (documentiVO.getStatoDocumentoVO() == null || documentiVO.getSigasAllegatos() == null) {
            documentoElettronicoActa.setNumeroAllegati(0);
        } else {
            documentoElettronicoActa.setNumeroAllegati(documentiVO.getSigasAllegatos().size());
        }
        documentoElettronicoActa.setTipoStrutturaRoot(1);        // 20200630_LC
        documentoElettronicoActa.setTipoStrutturaFolder(1);    // 20200630_LC
        if (documentiVO.getStatoDocumentoVO() == null) {
            MetadatiActa metadatiActa = new MetadatiActa();
            metadatiActa.setNumeroRegistrazionePrecedente(documentiVO.getRifArchivio().split("/")[0]);
            metadatiActa.setAnnoRegistrazionePrecedente(documentiVO.getAnnualita());
            documentoElettronicoActa.setMetadatiActa(metadatiActa);
        }
        SoggettoActa soggettoActa = new SoggettoActa();
        soggettoActa.setMittente(documentiVO.getStatoDocumentoVO() != null);
        documentoElettronicoActa.setSoggettoActa(soggettoActa);
        documentoElettronicoActa.setAutoreGiuridico(documentiVO.getAnagraficaSoggettoVO().getDenominazione());
        documentoElettronicoActa.setDestinatarioGiuridico(DoquiConstants.DESTINATARIO_GIURIDICO);
        documentoElettronicoActa.setMittentiEsterni(documentiVO.getAnagraficaSoggettoVO().getDenominazione() + "_" + documentiVO.getCfPiva());

        SoggettoActa soggettoActaAllegato;
        if (documentiVO.getSigasAllegatos() != null) {
            for (AllegatoDocumentazioneVO allegato : documentiVO.getSigasAllegatos()) {
                documentoElettronicoActaAllegato = new DocumentoElettronicoActa();
                documentoElettronicoActaAllegato.setIdDocumento(allegato.getDescrizione());
                documentoElettronicoActaAllegato.setFolder(documentiVO.getTipoDocumentoVO().getCodiceTipoDocumento() + "-" + year);
                documentoElettronicoActaAllegato.setApplicativoAlimentante(sigasFruitore.getCodFruitore());
                documentoElettronicoActaAllegato.setStream(allegato.getFile());
                documentoElettronicoActaAllegato.setNomeFile(allegato.getNomeFile());
                documentoElettronicoActaAllegato.setDescrizione(documentiVO.getTipoDocumentoVO().getDescrizione());//cnmDTipoDocumento.getDescrTipoDocumento()
                	
                //SIGAS-225
                //documentoElettronicoActaAllegato.setMimeType(URLConnection.guessContentTypeFromName(allegato.getNomeFile()));
                documentoElettronicoActaAllegato.setMimeType(mimeType);
                //------------------
                
                documentoElettronicoActaAllegato.setNumeroAllegati(0);
                documentoElettronicoActaAllegato.setTipoStrutturaRoot(1);        // 20200630_LC
                documentoElettronicoActaAllegato.setTipoStrutturaFolder(1);    // 20200630_LC
                soggettoActaAllegato = new SoggettoActa();
                soggettoActa.setMittente(true);
                soggettoActaAllegato.setCognome(DoquiConstants.COGNOME_SOGGETTO_MITTENTE);
                soggettoActaAllegato.setNome(DoquiConstants.NOME_SOGGETTO_MITTENTE);
                documentoElettronicoActaAllegato.setSoggettoActa(soggettoActaAllegato);
                documentoElettronicoActaAllegato.setAutoreGiuridico(documentiVO.getAnagraficaSoggettoVO().getDenominazione() + " - " + documentiVO.getCfPiva());
                documentoElettronicoActaAllegato.setDestinatarioGiuridico(DoquiConstants.DESTINATARIO_GIURIDICO);
                
                documentoElettronicoActaAllegato.setAutoreFisico("NULL_VALUE");
                
                listDocumentoElettronicoActaAllegato.add(documentoElettronicoActaAllegato);
            }
        }
        
        //SIGAS-225
        documentoElettronicoActa.setAutoreFisico("NULL_VALUE");
        
    }

    private String calcolaSerieFascicoloRoot(TipoDocumentoVO tipoDocumento) {
        if (tipoDocumento.getIdTipoDocumentoPadre() != null) {
            tipoDocumento = tipoDocumentoEntityMapper.mapEntityToVO(sigasTipoDocumentoRepository.findOne(tipoDocumento.getIdTipoDocumentoPadre()));
        }
        if (tipoDocumento.getCodiceTipoDocumento().equalsIgnoreCase(DoquiConstants.DICHIARAZIONI)) {
            return sigasCParametroRepository.findByDescParametro("DICHIARAZIONI_SERIE_FASCICOLI_CODICE").getValoreString();
        } else if (tipoDocumento.getCodiceTipoDocumento().equalsIgnoreCase(DoquiConstants.RIMBORSI)) {
            return sigasCParametroRepository.findByDescParametro("RIMBORSI_SERIE_FASCICOLI_CODICE").getValoreString();
        } else if (tipoDocumento.getCodiceTipoDocumento().equalsIgnoreCase(DoquiConstants.DEPOSITI_CAUZIONALI)) {
            return sigasCParametroRepository.findByDescParametro("DEPOSITI_CAUZIONALI_SERIE_FASCICOLI_CODICE").getValoreString();
        } else if (tipoDocumento.getCodiceTipoDocumento().equalsIgnoreCase(DoquiConstants.COMUNICAZIONI_VARIE)) {
            return sigasCParametroRepository.findByDescParametro("COMUNICAZIONI_VARIE_SERIE_FASCICOLI_CODICE").getValoreString();
        } else if (tipoDocumento.getCodiceTipoDocumento().equalsIgnoreCase(DoquiConstants.ACCERTAMENTI)) {
            return sigasCParametroRepository.findByDescParametro("ACCERTAMENTI_VARIE_SERIE_FASCICOLI_CODICE").getValoreString();
        }
        return null;
    }

    private String calcolaOggetto(DocumentiVO documentoVO, int year) {
        if (documentoVO.getAnnualita() != null && !documentoVO.getAnnualita().isEmpty()) {
            year = Integer.parseInt(documentoVO.getAnnualita());
        }
        return documentoVO.getTipoDocumentoVO().getCodiceTipoDocumento() + "_" + year + "_" + documentoVO.getAnagraficaSoggettoVO().getIdAnag() + "_" + documentoVO.getCfPiva() + "_" + documentoVO.getAnagraficaSoggettoVO().getDenominazione();
    }

    private String calcolaOggettoLetteraRisposta(DocumentiVO documentoVO) {
       return "RISP" + "_" + documentoVO.getTipoDocumentoVO().getCodiceTipoDocumento() + "_" + documentoVO.getRifArchivio() + "_" + documentoVO.getAnnualita() + "_" + documentoVO.getAnagraficaSoggettoVO().getDenominazione() + "_" + documentoVO.getAnagraficaSoggettoVO().getCodiceAzienda() + "_" + documentoVO.getCfPiva();
    
    }

    private ObjectIdType recuperaRootFolderId(String codice, ObjectIdType repositoryId, PrincipalIdType principalId, Integer tipoStruttura) throws IntegrationException {
        String method = "recuperaRootFolderId";
        log.debug(method + ". BEGIN");
        ObjectIdType folderId = null;
        QueryableObjectType target = null;
        try {
            if (tipoStruttura == null)
                throw new IntegrationException("tipo struttura is null");

            log.debug(method + ". tipoStruttura= " + tipoStruttura);
            target = getTarget(EnumObjectType.SERIE_FASCICOLI_PROPERTIES_TYPE.value());
            folderId = acarisObjectService.getIdentificatoreTramiteCodice(repositoryId, principalId, target, codice, null);
            if (folderId == null) {
                throw new IntegrationException("Impossibile recuperare il parametro folderId");
            }

            if (log.isDebugEnabled()) {
                log.debug(method + ". folderId= " + folderId.getValue());
            }

        } catch (Exception e) {
            log.error(method + ". Si e' verificato un errore in fase di recuperoRootFolderId: " + e);
            throw new IntegrationException(e.getMessage(), e);
        } finally {
            log.debug(method + ". END");
        }
        return folderId;
    }

    private ObjectIdType recuperaStrutturaFolderId(String parolaChiave, ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType rootFolderId, Integer tipoStruttura) throws IntegrationException {
        String method = "recuperaStrutturaFolderId";
        log.debug(method + ". BEGIN");
        ObjectIdType folderId = null;
        try {
            QueryableObjectType q = null;

            if (tipoStruttura == null)
                throw new IntegrationException("tipo struttura is null");

            log.debug(method + ". tipoStruttura= " + tipoStruttura);

            q = getTarget(EnumObjectType.FASCICOLO_REALE_ANNUALE_PROPERTIES_TYPE.value());
            folderId = acarisObjectService.getIdentificatoreTramiteParolaChiave(repositoryId, principalId, q, parolaChiave, rootFolderId);
            log.debug(method + ". FascicoloAnnualeFolderId = " + folderId);
        } catch (Exception e) {
            log.error(method + ". Si e' verificato un errore in fase di recuperaStrutturaFolderId: " + e);
            throw new IntegrationException(e.getMessage(), e);
        } finally {
            log.debug(method + ". END");
        }
        return folderId;
    }

    private QueryableObjectType getTarget(String val) {
        QueryableObjectType target = new QueryableObjectType();
        target.setObject(val);
        return target;
    }

    private String getUUIDDocumentoByParolaChiave(String parolaChiave, ObjectIdType repositoryId, PrincipalIdType principalId) throws IntegrationException {
        String method = "getUUIDDocumento";
        log.debug(method + ". BEGIN");
        String UUIDDocumento = null;
        try {
            UUIDDocumento = acarisObjectService.getUUIDDocumento(repositoryId, principalId, getTarget(EnumObjectType.DOCUMENTO_SEMPLICE_PROPERTIES_TYPE.value()), parolaChiave);
            log.debug(method + ". getUUIDDocumento = " + UUIDDocumento);

        } finally {
            log.debug(method + ". END");
        }
        return UUIDDocumento;
    }

    private KeyDocumentoActa ottengoKeyActa(DocumentoActa documentoActa, String UUIDDocumento, String indiceClassificazione, IdentificazioneRegistrazione identificazioneRegistrazione, IdentificatoreDocumento identificatoreDocumento) {
        KeyDocumentoActa keyActa = new KeyDocumentoActa(documentoActa.getIdDocumento());

        if (UUIDDocumento != null) {
            keyActa.setUUIDDocumento(UUIDDocumento);
        }
        keyActa.setIndiceClassificazione(indiceClassificazione);

        if (identificazioneRegistrazione != null) {
            keyActa.setNumeroProtocollo(identificazioneRegistrazione.getNumero() + "/" + DateFormat.getCurrentYear());
            keyActa.setRegistrazioneId(identificazioneRegistrazione.getRegistrazioneId().getValue());
            keyActa.setClassificazioneId(identificazioneRegistrazione.getClassificazioneId().getValue());
        }
        if (identificatoreDocumento != null) {
            keyActa.setObjectIdClassificazione(identificatoreDocumento.getObjectIdClassificazione().getValue());
            keyActa.setObjectIdDocumento(identificatoreDocumento.getObjectIdDocumento().getValue());
        }
        return keyActa;
    }

    private VitalRecordCodeType estratiVitalRecordCodeType(VitalRecordCodeType[] elencoVitalRecordCodeType, int idVitalRecordCode) {
        String method = "estratiVitalRecordCodeType";
        log.info(method + ". BEGIN");
        VitalRecordCodeType result = null;
        if (log.isDebugEnabled())
            log.debug(method + ". idVitalRecordCode  = " + idVitalRecordCode);
        try {
            if (elencoVitalRecordCodeType != null) {
                for (int i = 0; i < elencoVitalRecordCodeType.length; i++) {
                    VitalRecordCodeType temp = elencoVitalRecordCodeType[i];
                    if (temp.getIdVitalRecordCode().getValue() == idVitalRecordCode) {
                        result = temp;
                        break;
                    }
                }
            }
            return result;
        } finally {
            if (log.isDebugEnabled())
                if (result != null)
                    log.debug(method + ". result  = " + result.getIdVitalRecordCode().hashCode());
                else
                    log.debug(method + ". result  = " + result);
            log.debug(method + ". END");
        }
    }

    private VitalRecordCodeType estratiVitalRecordCodeTypeByDescrizione(VitalRecordCodeType[] elencoVitalRecordCodeType, String descrizioneVitalRecordCode) {
        String method = "estratiVitalRecordCodeType";
        log.info(method + ". BEGIN");
        VitalRecordCodeType result = null;
        if (log.isDebugEnabled())
            log.debug(method + ". idVitalRecordCode  = " + descrizioneVitalRecordCode);
        try {
            if (elencoVitalRecordCodeType != null) {
                for (int i = 0; i < elencoVitalRecordCodeType.length; i++) {
                    VitalRecordCodeType temp = elencoVitalRecordCodeType[i];
                    if (temp.getDescrizione().equalsIgnoreCase(descrizioneVitalRecordCode)) {
                        result = temp;
                        break;
                    }
                }
            }
            return result;
        } finally {
            if (log.isDebugEnabled())
                if (result != null)
                    log.debug(method + ". result  = " + result.getIdVitalRecordCode().hashCode());
                else
                    log.debug(method + ". result  = " + result);
            log.debug(method + ". END");
        }
    }

    private IdStatoDiEfficaciaType getStatoEfficaciaByDescrizione(ObjectIdType repositoryId, PrincipalIdType principalId, String descrizioneStatoEfficacia) throws Exception {
        QueryableObjectType target = new QueryableObjectType();
        target.setObject("StatoDiEfficaciaDecodifica");

        PropertyFilterType filter = new PropertyFilterType();
        filter.setFilterType(EnumPropertyFilter.LIST);

        List<QueryNameType> richieste = new ArrayList<QueryNameType>();
        QueryNameType richiesta = new QueryNameType();
        richiesta.setClassName(target.getObject());
        richiesta.setPropertyName("dbKey");
        richieste.add(richiesta);
        filter.setPropertyList(richieste.toArray(new QueryNameType[richieste.size()]));

        List<QueryConditionType> criteria = new ArrayList<QueryConditionType>();
        QueryConditionType qct = new QueryConditionType();
        qct.setPropertyName("descrizione");
        qct.setOperator(EnumQueryOperator.EQUALS);
        qct.setValue(descrizioneStatoEfficacia);
        criteria.add(qct);

        IdStatoDiEfficaciaType idStatoEfficacia = null;

        try {
            PagingResponseType result = acarisServiceFactory.getAcarisService().getObjectServicePort().query(repositoryId, principalId, target, filter,
                    criteria.toArray(new QueryConditionType[criteria.size()]), null, null, new Integer(0));

            if (result != null && result.getObjectsLength() == 1 && result.getObjects(0) != null && result.getObjects(0).getPropertiesLength() > 0) {
                for (PropertyType current : result.getObjects(0).getProperties()) {
                    if ("dbKey".equals(current.getQueryName().getPropertyName()) && current.getValue() != null && current.getValue().getContentLength() == 1) {
                        idStatoEfficacia = new IdStatoDiEfficaciaType();
                        idStatoEfficacia.setValue(Long.parseLong(current.getValue().getContent(0)));
                        System.out.println("Trovato stato di efficacia " + descrizioneStatoEfficacia + ", value " + idStatoEfficacia.getValue());
                        break;
                    }
                }
            }

        } catch (it.doqui.acta.acaris.objectservice.AcarisException acEx) {
            System.err.println("errore nella ricerca dello stato di efficacia");
            System.err.println("acEx.getMessage(): " + acEx.getMessage());
            System.err.println("acEx.getFaultInfo().getErrorCode(): " + acEx.getFaultInfo().getErrorCode());
            System.err.println("acEx.getFaultInfo().getPropertyName(): " + acEx.getFaultInfo().getPropertyName());
            System.err.println("acEx.getFaultInfo().getObjectId(): " + acEx.getFaultInfo().getObjectId());
            System.err.println("acEx.getFaultInfo().getExceptionType(): " + acEx.getFaultInfo().getExceptionType());
            System.err.println("acEx.getFaultInfo().getClassName(): " + acEx.getFaultInfo().getClassName());
            throw acEx;
        } catch (Exception ex) {
            System.err.println("errore nella ricerca dello stato di efficacia");
            System.err.println("ex.getMessage() " + ex.getMessage());
            throw ex;
        }

        return idStatoEfficacia;
    }

    @Override
    public void eliminaDocumentazioneIndex(String uid) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.eliminaDocumentazioneIndex(uid, sigasCParametroRepository);
    }
}
