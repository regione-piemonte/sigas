/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api.impl;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.dispatcher.IDepositoCausionaleDispatcher;
import it.csi.sigas.sigasbl.dispatcher.IDocumentazioneDispatcher;
import it.csi.sigas.sigasbl.integration.doqui.DoquiConstants;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasDocumenti;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.entity.SigasStoricoAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.mapper.entity.AnagraficaSoggettiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DocumentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.ProvinciaEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.StatoDocumentoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoDocumentoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDocumentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasStatoDocumentoRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasStoricoAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoDocumentoRepository;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.model.vo.depositocausionale.DepositoCausionaleVO;
import it.csi.sigas.sigasbl.model.vo.documenti.AllegatoDocumentazioneVO;
import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;
import it.csi.sigas.sigasbl.model.vo.documenti.StatoDocumentoVO;
import it.csi.sigas.sigasbl.model.vo.documenti.TipoDocumentoVO;
import it.csi.sigas.sigasbl.model.vo.home.ReportResponse;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.request.documentazione.ConfermaDocumentazioneRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneBoRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneRequest;
import it.csi.sigas.sigasbl.rest.api.IDocumentazioneApi;
import it.csi.sigas.sigasbl.scheduled.IExecutorServiceProvider;
import it.csi.sigas.sigasbl.scheduled.IMoveDocumentService;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.service.IDepositoCausionaleService;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.xml.rpc.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class DocumentazioneApiImpl extends SpringSupportedResource implements IDocumentazioneApi {

    @Autowired
    private IDocumentazioneDispatcher iDocumentazioneDispatcher;

    @Autowired
    private DocumentiEntityMapper documentiEntityMapper;

    @Autowired
    private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;

    @Autowired
    private SigasStatoDocumentoRepository sigasStatoDocumentoRepository;

    @Autowired
    private SigasTipoDocumentoRepository sigasTipoDocumentoRepository;

    @Autowired
    private TipoDocumentoEntityMapper tipoDocumentoEntityMapper;

    @Autowired
    private AnagraficaSoggettiEntityMapper anagraficaSoggettiEntityMapper;

    @Autowired
    private StatoDocumentoEntityMapper statoDocumentoEntityMapper;

    @Autowired
    private SigasDocumentiRepository sigasDocumentiRepository;

    @Autowired
    private IExecutorServiceProvider executorServiceProvider;

    @Autowired
    private IMoveDocumentService iMoveDocumentService;
    
    @Autowired
    private IDepositoCausionaleService iDepositoCausionaleService;
    
    @Autowired
    private SigasProvinciaRepository sigasProvinciaRepository;
    
    @Autowired
    private ProvinciaEntityMapper provinciaEntityMapper;
    
    @Autowired
	private IDepositoCausionaleDispatcher iDepositoCausionaleDispatcher;
    

    @Override
    public Response listaStatoDocumenti() {
        List<StatoDocumentoVO> listaStatoDocumenti = iDocumentazioneDispatcher.listaStatoDocumenti();
        return Response.ok(new ResponseVO<List<StatoDocumentoVO>>(Esito.SUCCESS, listaStatoDocumenti)).build();
    }

    @Override
    public Response ricercaAziendeDocumentiInoltrati() {
        List<AnagraficaSoggettoVO> listaPratiche = iDocumentazioneDispatcher.ricercaAziendeDocumentiInoltrati();
        return Response.ok(new ResponseVO<List<AnagraficaSoggettoVO>>(Esito.SUCCESS, listaPratiche)).build();
    }

    @Override
    public Response ricercaDocumentiBO(RicercaDocumentazioneBoRequest ricercaDocumentazioneBoRequest) {
        List<DocumentiVO> listaDocumenti = iDocumentazioneDispatcher.ricercaDocumenti(ricercaDocumentazioneBoRequest);
        return Response.ok(new ResponseVO<List<DocumentiVO>>(Esito.SUCCESS, listaDocumenti)).build();
    }

    @Override
    public Response salvaDocumentazioneBO(MultipartFormDataInput input) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UserDetails utente = (UserDetails) principal;
        ConfermaDocumentazioneRequest confermaDocumentazioneRequest = new ConfermaDocumentazioneRequest();
        String idDocumento = input.getFormDataPart("idDocumento", String.class, null);
        SigasDocumenti sigasDocumento = sigasDocumentiRepository.findOne(Integer.parseInt(idDocumento));
        DocumentiVO vo = documentiEntityMapper.mapEntityToVO(sigasDocumento);
        String idStatoSelezionato = input.getFormDataPart("idStatoSelezionato", String.class, null);
        vo.setStatoDocumentoVO(statoDocumentoEntityMapper.mapEntityToVO(sigasStatoDocumentoRepository.findOne(Integer.parseInt(idStatoSelezionato))));
        String noteBo = input.getFormDataPart("noteBo", String.class, null);
        vo.setNoteBo(noteBo);
        confermaDocumentazioneRequest.setDocumentiVO(vo);
        String notaDataInvioPec = input.getFormDataPart("notaDataInvioPec", String.class, null);
        if (notaDataInvioPec != null && !notaDataInvioPec.isEmpty()) {
            if(DoquiConstants.CODICE_STATO_LETT_RISP.equals(vo.getStatoDocumentoVO().getCodiceStato())) {
                vo.setNote(notaDataInvioPec);
            } else {
                List<DocumentiVO> listaDocumenti = iDocumentazioneDispatcher.ricercaLetteraRisposta(sigasDocumento.getNProtocollo());
                if (listaDocumenti != null && listaDocumenti.size() > 0) {
                    DocumentiVO letteraRisposta = listaDocumenti.get(0);
                    letteraRisposta.setNote(notaDataInvioPec);
                    confermaDocumentazioneRequest.setLetteraRisposta(letteraRisposta);
                }
            }
        }
        iDocumentazioneDispatcher.salvaDocumentazioneBO(confermaDocumentazioneRequest, utente.getIdentita().getCodFiscale());
        
        /**********************************************
         * CR-REQ-06
         *********************************************/
        if(vo.getTipoDocumentoVO().getCodiceTipoDocumento().equalsIgnoreCase(DoquiConstants.DEPOSITI_CAUZIONALI) || 
           vo.getTipoDocumentoVO().getCodiceTipoDocumento().equalsIgnoreCase(DoquiConstants.DEPOSITI_CAUZIONALI_INTEGRAZIONE)) 
        {
        	if(!DoquiConstants.CODICE_STATO_LETT_RISP.equals(vo.getStatoDocumentoVO().getCodiceStato())) {
        		
        		String annoAccertamento = input.getFormDataPart("annoAccertamento", String.class, null);
            	String numeroAccertamento = input.getFormDataPart("numeroAccertamento", String.class, null);
            	String numeroDetermina = input.getFormDataPart("numeroDetermina", String.class, null);
            	String importoStringValue = input.getFormDataPart("importo", String.class, null);
            	
            	if(idDocumento != null && annoAccertamento!=null && 
            	   numeroAccertamento!=null && numeroDetermina != null &&
            	   importoStringValue != null) 
            	{   
            		
            		BigDecimal importoDepositoCauzionale = new BigDecimal(importoStringValue); 
            		            		
            		this.iDepositoCausionaleDispatcher.aggiornaDepositoCauzionaleDatiAggiuntivi(Long.valueOf(idDocumento), 
            																					annoAccertamento, numeroAccertamento, 
            																					numeroDetermina, importoDepositoCauzionale);
            	}
            	
            	if(DoquiConstants.CODICE_STATO_RIFIUTATA.equals(confermaDocumentazioneRequest.getDocumentiVO().getStatoDocumentoVO().getCodiceStato())) {
            		this.iDepositoCausionaleDispatcher.inviaMailRifiutoDepositoCauzionale(vo.getIdDocumento().longValue(), noteBo);
            	}        		
        	}        	        	
        }
        /**********************************************
         * CR-REQ-06 - FINE
         *********************************************/
        
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, vo.getStatoDocumentoVO().getCodiceStato())).build();
    }

    @Override
    public Response protocollaLetteraRisp(MultipartFormDataInput input) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UserDetails utente = (UserDetails) principal;


        ConfermaDocumentazioneRequest confermaDocumentazioneRequest = new ConfermaDocumentazioneRequest();


        String idDocumento = input.getFormDataPart("idDocumento", String.class, null);
        SigasDocumenti sigasDocumento = sigasDocumentiRepository.findOne(Integer.parseInt(idDocumento));
        DocumentiVO vo = documentiEntityMapper.mapEntityToVO(sigasDocumento);

        confermaDocumentazioneRequest.setDocumentiVO(vo);


        DocumentiVO letteraRisposta = new DocumentiVO();

        Map<String, List<InputPart>> map = input.getFormDataMap();
        List<InputPart> file = map.get("dataFileRisposta");
        byte[] content = null;

        // Recupero dal multipart i file caricati
        if (file != null && !file.isEmpty()) {
            try {
                for (InputPart inputPart : file) {
                    InputStream inputStream = inputPart.getBody(InputStream.class, null);
                    content = IOUtils.toByteArray(inputStream);
                }
            } catch (IOException e) {
                throw new RuntimeException("Errore estrazione file in upload", e);
            }
        }

        letteraRisposta.setFileMaster(content);
        letteraRisposta.setNomeFile(input.getFormDataPart("nomeFileLetteraRisposta", String.class, null));

        confermaDocumentazioneRequest.setLetteraRisposta(letteraRisposta);


        DocumentiVO letteraRispProtocollata = iDocumentazioneDispatcher.protocollaLetteraRisp(confermaDocumentazioneRequest, utente.getIdentita().getCodFiscale());
        return Response.ok(new ResponseVO<DocumentiVO>(Esito.SUCCESS, letteraRispProtocollata)).build();
    }

    @Override
    public Response ricercaLetteraRisposta(String rifArchivio) {
        List<DocumentiVO> listaDocumenti = iDocumentazioneDispatcher.ricercaLetteraRisposta(rifArchivio);
        return Response.ok(new ResponseVO<List<DocumentiVO>>(Esito.SUCCESS, listaDocumenti)).build();
    }

    @Override
    public Response getPacchettoDocumenti(String idDocumento) {
        byte[] file = null;
        try {
            file = this.iDocumentazioneDispatcher.getPacchettoDocumenti(Integer.parseInt(idDocumento));
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return Response.ok().entity(file).build();
    }

    @Override
    public Response getDocumentoMaster(String idDocumento) {
        byte[] file = null;
        try {
            file = this.iDocumentazioneDispatcher.getDocumentoMaster(Integer.parseInt(idDocumento));
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return Response.ok().entity(file).build();
    }

    @Override
    public Response getAllegato(String idDocumento, String nomeFileAllegato) {
        byte[] file = null;
        try {
            file = this.iDocumentazioneDispatcher.getAllegato(Integer.parseInt(idDocumento), nomeFileAllegato);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return Response.ok().entity(file).build();
    }

    // OPERATORE FO
    @Override
    public Response ricercaAziendeAccreditato() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UserDetails utente = (UserDetails) principal;

        List<AnagraficaSoggettoVO> listaPratiche = iDocumentazioneDispatcher.ricercaAziendeAccreditato(utente.getIdentita().getCodFiscale());
        return Response.ok(new ResponseVO<List<AnagraficaSoggettoVO>>(Esito.SUCCESS, listaPratiche)).build();
    }

    @Override
    public Response listaTipoDocumenti() {
        List<TipoDocumentoVO> listaTipoDocumenti = iDocumentazioneDispatcher.listaTipoDocumenti();
        return Response.ok(new ResponseVO<List<TipoDocumentoVO>>(Esito.SUCCESS, listaTipoDocumenti)).build();
    }

    @Override
    public Response ricercaDocumenti(RicercaDocumentazioneRequest ricercaDocumentazioneRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UserDetails utente = (UserDetails) principal;

        List<DocumentiVO> listaDocumenti = iDocumentazioneDispatcher.ricercaDocumenti(ricercaDocumentazioneRequest, utente.getIdentita().getCodFiscale());
        return Response.ok(new ResponseVO<List<DocumentiVO>>(Esito.SUCCESS, listaDocumenti)).build();
    }

    @Override    
    public Response salvaDocumentazione(MultipartFormDataInput input) throws IOException, BusinessException, ServiceException {
        Map<String, List<InputPart>> map = input.getFormDataMap();
        List<InputPart> file = map.get("data");
        byte[] content = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UserDetails utente = (UserDetails) principal;
        ConfermaDocumentazioneRequest confermaDocumentazioneRequest = new ConfermaDocumentazioneRequest();
        DocumentiVO vo = new DocumentiVO();
        SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(Long.parseLong(input.getFormDataPart("idAnag", String.class, null)));
        vo.setAnagraficaSoggettoVO(anagraficaSoggettiEntityMapper.mapEntityToVO(sigasAnagraficaSoggetti));
        vo.setAnnualita(input.getFormDataPart("annualita", String.class, null));
        vo.setCfPiva(input.getFormDataPart("cfPiva", String.class, null));
        vo.setNomeFile(input.getFormDataPart("nomeFile", String.class, null));        
        vo.setNote(input.getFormDataPart("note", String.class, null));        
        vo.setRifArchivio(input.getFormDataPart("rifArchivio", String.class, null));
        vo.setTipoDocumentoVO(tipoDocumentoEntityMapper.mapEntityToVO(sigasTipoDocumentoRepository.findOne(Integer.parseInt(input.getFormDataPart("idTipoDocumento", String.class, null)))));
        String idTipoComunicazione = input.getFormDataPart("idTipoComunicazione", String.class, null);
        if (idTipoComunicazione != null && !idTipoComunicazione.isEmpty()) {
            vo.setTipoComunicazioneVO(tipoDocumentoEntityMapper.mapEntityToVO(sigasTipoDocumentoRepository.findOne(Integer.parseInt(idTipoComunicazione))));
        }
        String idTipoRimborso = input.getFormDataPart("idTipoRimborso", String.class, null);
        if (idTipoRimborso != null && !idTipoRimborso.isEmpty()) {
            vo.setTipoRimborsoVO(tipoDocumentoEntityMapper.mapEntityToVO(sigasTipoDocumentoRepository.findOne(Integer.parseInt(idTipoRimborso))));
        }
        String nProtocolloAccertamento = input.getFormDataPart("nProtocolloAccertamento", String.class, null);
        if (nProtocolloAccertamento != null && !nProtocolloAccertamento.isEmpty()) {
            vo.setnProtocolloAccertamento(nProtocolloAccertamento);
        }
        String annoProtocolloAccertamento = input.getFormDataPart("annoProtocolloAccertamento", String.class, null);
        if (annoProtocolloAccertamento != null && !annoProtocolloAccertamento.isEmpty()) {
            vo.setAnnoProtocolloAccertamento(annoProtocolloAccertamento);
        }
        // Recupero dal multipart i file caricati
        if (file != null && !file.isEmpty()) {
            try {
                for (InputPart inputPart : file) {
                    InputStream inputStream = inputPart.getBody(InputStream.class, null);
                    content = IOUtils.toByteArray(inputStream);
                }
            } catch (IOException e) {
                throw new RuntimeException("Errore estrazione file in upload", e);
            }
        }        
        
        /********************************************
         * Gestione Deposito Causionale - INZIO
         ********************************************/        
        //OLD CODE
        //vo.setFileMaster(content);
        
        DepositoCausionaleVO depositoCausionaleVO = null;
        if(vo.getTipoDocumentoVO()!= null && 
           (vo.getTipoDocumentoVO().getCodiceTipoDocumento().equalsIgnoreCase(Constants.RICHIESTA_DEP_CAUSIONALE_CODICE_TIPO_DOCUMENTO) ||
        	vo.getTipoDocumentoVO().getCodiceTipoDocumento().equalsIgnoreCase(Constants.RICHIESTA_DEP_CAUSIONALE_INTEGRAZIONE_CODICE_TIPO_DOCUMENTO))) 
        {
        	String importoStringValue = input.getFormDataPart("importo", String.class, null);
        	String codiceProvincia = input.getFormDataPart("codiceProvincia", String.class, null);
        	if(importoStringValue!=null) {
        		depositoCausionaleVO = new DepositoCausionaleVO();
            	depositoCausionaleVO.setAnagraficaSoggettoVO(vo.getAnagraficaSoggettoVO());        	
            	depositoCausionaleVO.setImporto(new BigDecimal(importoStringValue));
            	
            	ProvinciaVO provinciaVO = null;
            	if(Constants.RICHIESTA_DEP_CAUSIONALE_CODICE_TUTTE_PROVINCE.equalsIgnoreCase(codiceProvincia)) {
            		provinciaVO = new ProvinciaVO();
            		provinciaVO.setDenominazione(Constants.RICHIESTA_DEP_CAUSIONALE_CODICE_TUTTE_PROVINCE);
            		provinciaVO.setSigla(Constants.RICHIESTA_DEP_CAUSIONALE_CODICE_TUTTE_PROVINCE);
            		provinciaVO.setId(Long.valueOf(Constants.RICHIESTA_DEP_CAUSIONALE_ID_FITTIZIO_TUTTE_PROVINCE));
            	} else {
            		SigasProvincia sigasProvinciaEntity = this.sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(codiceProvincia);
            		provinciaVO = this.provinciaEntityMapper.mapEntityToVO(sigasProvinciaEntity);
            	}
            	depositoCausionaleVO.setProvinciaVO(provinciaVO);
            	//depositoCausionaleVO.setDocumentoVO(vo);
            	
            	//ReportResponse reportResponse = this.iDepositoCausionaleService.generaReportRichiestaDepositoCausionale(depositoCausionaleVO);
            	//vo.setFileMaster(reportResponse.getFile());
            	vo.setFileMaster(this.iDepositoCausionaleService.generaReportRichiestaDepositoCausionale(depositoCausionaleVO));            	            	
        	}
        	
        } else {
        	vo.setFileMaster(content);
        }        
        /********************************************
         * Gestione Deposito Causionale - FINE
         ********************************************/        
        
        Integer numeroAllegati = input.getFormDataPart("numeroAllegati", String.class, null) != null ? Integer.valueOf(input.getFormDataPart("numeroAllegati", String.class, null)) : 0;
        List<AllegatoDocumentazioneVO> listAllegati = new ArrayList<AllegatoDocumentazioneVO>();
        AllegatoDocumentazioneVO allegato = new AllegatoDocumentazioneVO();
        for (int i = 0; i < numeroAllegati; i++) {
            content = null;
            allegato = new AllegatoDocumentazioneVO();
            allegato.setDescrizione(input.getFormDataPart("allegatoDescrizione" + i, String.class, null));
            List<InputPart> fileAllegato = map.get("allegatoData" + i);
            if (fileAllegato != null && !fileAllegato.isEmpty()) {
                try {
                    for (InputPart inputPart : fileAllegato) {
                        InputStream inputStream = inputPart.getBody(InputStream.class, null);
                        content = IOUtils.toByteArray(inputStream);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Errore estrazione file in upload", e);
                }
            }
            allegato.setFile(content);
            allegato.setNomeFile(input.getFormDataPart("allegatoNome" + i, String.class, null));
            listAllegati.add(allegato);
        }
        vo.setSigasAllegatos(listAllegati);
        confermaDocumentazioneRequest.setDocumentiVO(vo);
        DocumentiVO documentoVOSalvato = iDocumentazioneDispatcher.salvaDocumentazione(confermaDocumentazioneRequest, utente.getIdentita().getCodFiscale());
        
        /********************************************
         * Gestione Deposito Causionale - INZIO
         ********************************************/
        if(depositoCausionaleVO != null &&
           vo.getTipoDocumentoVO()!= null &&           
           (vo.getTipoDocumentoVO().getCodiceTipoDocumento().equalsIgnoreCase(Constants.RICHIESTA_DEP_CAUSIONALE_CODICE_TIPO_DOCUMENTO)||
        	vo.getTipoDocumentoVO().getCodiceTipoDocumento().equalsIgnoreCase(Constants.RICHIESTA_DEP_CAUSIONALE_INTEGRAZIONE_CODICE_TIPO_DOCUMENTO))) 
        {
        	//Aggiornamento ANAGRAFICA
        	String depCausionaleIndirizzo = input.getFormDataPart("depCausionaleIndirizzo", String.class, null);
            if (depCausionaleIndirizzo != null && !depCausionaleIndirizzo.isEmpty()) {                	
            	if(vo.getAnagraficaSoggettoVO().getIndirizzo() != null && 
            	   !vo.getAnagraficaSoggettoVO().getIndirizzo().equalsIgnoreCase(depCausionaleIndirizzo)) 
            	{                		
            		depositoCausionaleVO.getAnagraficaSoggettoVO().setIndirizzo(depCausionaleIndirizzo);                		               		
            	}
            }
        	depositoCausionaleVO.setDocumentoVO(documentoVOSalvato);
        	this.iDepositoCausionaleService.salvaDepositoCausionale(depositoCausionaleVO);
        }        
        /********************************************
         * Gestione Deposito Causionale - FINE
         ********************************************/
        
        
        // Faccio submit del task asincrono per spostare i documenti da INDEX -> ACTA
        executorServiceProvider.getExecutorService().submit(iMoveDocumentService);
        // Fine
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
    }    
    
}
