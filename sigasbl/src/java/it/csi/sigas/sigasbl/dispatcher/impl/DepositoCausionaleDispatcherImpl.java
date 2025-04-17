package it.csi.sigas.sigasbl.dispatcher.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.xml.rpc.ServiceException;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.common.utils.Utilities;
import it.csi.sigas.sigasbl.dispatcher.IDepositoCausionaleDispatcher;
import it.csi.sigas.sigasbl.dispatcher.IDocumentazioneDispatcher;
import it.csi.sigas.sigasbl.integration.doqui.DoquiConstants;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.entity.SigasDepositoCausionale;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.mapper.entity.StatoDocumentoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoDocumentoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasStatoDocumentoRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoDocumentoRepository;
import it.csi.sigas.sigasbl.model.vo.depositocausionale.DepositoCausionaleVO;
import it.csi.sigas.sigasbl.model.vo.documenti.AllegatoDocumentazioneVO;
import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;
import it.csi.sigas.sigasbl.request.depositocauzionale.RigeneraBollettinoPagamentoRequest;
import it.csi.sigas.sigasbl.request.documentazione.ConfermaDocumentazioneRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneBoRequest;
import it.csi.sigas.sigasbl.request.documentazione.RicercaDocumentazioneRequest;
import it.csi.sigas.sigasbl.scheduled.IExecutorServiceProvider;
import it.csi.sigas.sigasbl.scheduled.IMoveDocumentService;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.service.IDepositoCausionaleService;
import it.csi.sigas.sigasbl.service.IPaymentFoService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;

@Component
public class DepositoCausionaleDispatcherImpl implements IDepositoCausionaleDispatcher {
	
	@Autowired
	private IDepositoCausionaleService iDepositoCausionaleService; 
	
	@Autowired
    private SigasTipoDocumentoRepository sigasTipoDocumentoRepository;

    @Autowired
    private TipoDocumentoEntityMapper tipoDocumentoEntityMapper;
    
    @Autowired
    private IExecutorServiceProvider executorServiceProvider;

    @Autowired
    private IMoveDocumentService iMoveDocumentService;
    
    @Autowired
    private IDocumentazioneDispatcher iDocumentazioneDispatcher;
    
    @Autowired
    private SigasStatoDocumentoRepository sigasStatoDocumentoRepository;
    
    @Autowired
    private StatoDocumentoEntityMapper statoDocumentoEntityMapper;
    
    @Autowired
	private SigasCParametroRepository sigasCParametroRepository;
    
    @Autowired
	private IPaymentFoService iPaymentFoService;
    

	@Override
	public byte[] generaBollettinoPagamento(Long idDocumento, Integer annoAccertamento, String numeroAccertamento, Integer tipoCarreloDepositoCauzionale) 
	{		
		byte[] bollettinoPagamento = this.iDepositoCausionaleService.generaCarrelloBollettinoPagamentoDepositoCausionaleByIdDicumentoRichiesta(idDocumento, annoAccertamento, 
																																			   numeroAccertamento, 
																																			   tipoCarreloDepositoCauzionale);
		
		return bollettinoPagamento;
	}

	@Override
	public List<DepositoCausionaleVO> ricercaElencoDepositoCausionaleByIdDocumento(Long idDocumento) {
		return this.iDepositoCausionaleService.ricercaElencoDepositoCausionaleByIdDocumento(idDocumento);		
	}

	@Override
	public void protocollaDepositoCausionale(MultipartFormDataInput input) 
	{
		Map<String, List<InputPart>> map = input.getFormDataMap();
        List<InputPart> file = map.get("dataFileRisposta");
        byte[] determina = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UserDetails utente = (UserDetails) principal;
        
        String idDocumentoFromMultipart;
        String codiceTipoDocumentoFromMultipart;
        String importoStringValue;
        String annoAccertamentoStringValue;
        String numeroAccertamentoStringValue;
        String numeroDeterminaStringValue;
		try {
			idDocumentoFromMultipart = input.getFormDataPart("idDocumento", String.class, null);
			codiceTipoDocumentoFromMultipart = input.getFormDataPart("codiceTipoDocumento", String.class, null);
			importoStringValue = input.getFormDataPart("importo", String.class, null);
			annoAccertamentoStringValue = input.getFormDataPart("annoAccertamento", String.class, null);
			numeroAccertamentoStringValue = input.getFormDataPart("numeroAccertamento", String.class, null);
			numeroDeterminaStringValue = input.getFormDataPart("numeroDetermina", String.class, null);
			if (idDocumentoFromMultipart != null && !idDocumentoFromMultipart.isEmpty()) {
	        	
				Long idDocumento = Long.valueOf(idDocumentoFromMultipart);
				BigDecimal importo = new BigDecimal(importoStringValue);
				BigDecimal importoStoricizzatoRichiesta = BigDecimal.ZERO;
				List<DepositoCausionaleVO> depositoCausionaleVOs = this.ricercaElencoDepositoCausionaleByIdDocumento(idDocumento);
				if(depositoCausionaleVOs!=null && !depositoCausionaleVOs.isEmpty()) {
					importoStoricizzatoRichiesta = _calcolaImportoTotaleDepositoCauzionale(depositoCausionaleVOs);
					//if(importoStoricizzatoRichiesta.compareTo(importo) != 0) 
					//{
						if(!this.iDepositoCausionaleService.aggiornaImportoRichiestaDepositoCauzionale(depositoCausionaleVOs, importo, 
																									   annoAccertamentoStringValue, numeroAccertamentoStringValue, 
																									   numeroDeterminaStringValue)) 
						{
							throw new BusinessException("Si e' verificato un errore durante l'eseczuione dell'aggiornamento import deposito cauzionale.");
						}
					//}
				}			
				
				Integer annoAccertamento = Integer.valueOf(annoAccertamentoStringValue);
				Integer tipoCarrelloDepositoCauzionale = Constants.RICHIESTA_DEP_CAUSIONALE_ID_TIPO_CARRELLO_PAGAMENTO;
				if(codiceTipoDocumentoFromMultipart!=null && 
				   codiceTipoDocumentoFromMultipart.equalsIgnoreCase(Constants.RICHIESTA_DEP_CAUSIONALE_INTEGRAZIONE_CODICE_TIPO_DOCUMENTO)) 
				{
					tipoCarrelloDepositoCauzionale = Constants.RICHIESTA_DEP_CAUSIONALE_INTEGRAZIONE_ID_TIPO_CARRELLO_PAGAMENTO;
				}
				
	        	byte[] bollettinoPagamento = null;
	        	try {
	        		bollettinoPagamento = this.generaBollettinoPagamento(idDocumento, annoAccertamento, numeroAccertamentoStringValue, tipoCarrelloDepositoCauzionale);
	        	} catch (Exception e) {
	        		throw new BusinessException(e.getMessage() , ErrorCodes.BUSSINESS_EXCEPTION);
	        	}        	
	        	
	        	// Recupero dal multipart i file caricati inerenti la determina
	            if (file != null && !file.isEmpty()) {
	                try {
	                    for (InputPart inputPart : file) {
	                        InputStream inputStream = inputPart.getBody(InputStream.class, null);
	                        determina = IOUtils.toByteArray(inputStream);
	                    }
	                } catch (IOException e) {
	                    //throw new RuntimeException("Errore estrazione file in upload", e);
	                }
	            }	            
	            
	            depositoCausionaleVOs = this.ricercaElencoDepositoCausionaleByIdDocumento(idDocumento);
	            if(depositoCausionaleVOs!=null && !depositoCausionaleVOs.isEmpty()) {
	            	DepositoCausionaleVO depositoCausionaleVODiRiferimento = depositoCausionaleVOs.get(0);
	            	depositoCausionaleVODiRiferimento.setImporto(_calcolaImportoTotaleDepositoCauzionale(depositoCausionaleVOs));	            	
	            	
	            	
	            	// VERSIONE SALVA DOCUMENTO
	            	//_protocollaDeterminaEBollettinoPagamentoDepCausionale(depositoCausionaleVO,bollettinoPagamento,determina);
	            	
	            	// VERSIONE PROTOCOLLA LETTERA DI RISPOSTA
	            	Calendar calendar = Calendar.getInstance();
	            	Date dataInvioMail = calendar.getTime();
	            	byte[] fileLetteraRisposta = _protocollaDeterminaEBollettinoPagamentoDepCausionale(depositoCausionaleVODiRiferimento, 
	            																					   bollettinoPagamento, determina, 
	            																					   dataInvioMail);
	            	
	            	String nomeFileLetteraRisposta = "avviso_approvazione_deposito_cauzionale_" +	            									
													 depositoCausionaleVODiRiferimento.getDocumentoVO().getAnagraficaSoggettoVO().getCodiceAzienda() +
													 ".pdf";
	            	
	            	_inviaMailConfermaDepositoCauzionale(depositoCausionaleVODiRiferimento, fileLetteraRisposta, nomeFileLetteraRisposta, bollettinoPagamento);	            	
	            }            
	        }
		} catch (IOException | BusinessException | ServiceException e1) {
			throw new BusinessException("Si è verificato un errore durante l'eseczuione del servizio.");
		}		
	}
	
	@Override
	public void inviaMailRifiutoDepositoCauzionale(Long idDocumento, String noteRifiuto) 
	{
		
		List<DepositoCausionaleVO> depositoCausionaleVOs = this.ricercaElencoDepositoCausionaleByIdDocumento(idDocumento);
        if(depositoCausionaleVOs!=null && !depositoCausionaleVOs.isEmpty()) {
        	DepositoCausionaleVO depositoCausionaleVODiRiferimento = depositoCausionaleVOs.get(0);
        	this._inviaMailRifiutoDepositoCauzionale(depositoCausionaleVODiRiferimento, noteRifiuto);
        }
		
	}
	
	@Override
	public void aggiornaDepositoCauzionaleDatiAggiuntivi(Long idDocumento, String annoAccertamento, String numeroAccertamento, 
														 String numeroDetermina, BigDecimal importo) 
	{
		List<DepositoCausionaleVO> depositoCausionaleVOs = this.ricercaElencoDepositoCausionaleByIdDocumento(idDocumento);
		if(depositoCausionaleVOs!=null && !depositoCausionaleVOs.isEmpty()) {			
			
			if(!this.iDepositoCausionaleService.aggiornaImportoRichiestaDepositoCauzionale(depositoCausionaleVOs, importo,  
																						   annoAccertamento, numeroAccertamento, 
																						   numeroDetermina)) 
			{
				throw new BusinessException("Si e' verificato un errore durante l'eseczuione dell'aggiornamento del deposito cauzionale.");
			}
			
		}
		
	}
	
	@Override
	public byte[] rigeneraBollettinoPagamento(RigeneraBollettinoPagamentoRequest rigeneraBollettinoPagamentoRequest) {
		if(rigeneraBollettinoPagamentoRequest == null) {
			throw new BusinessException("La richiesta di regenrazione non può essere vuota.");
		}		
		
		//Gestione in Base al Codice Pagamento
		if(rigeneraBollettinoPagamentoRequest.getCodicePagamento()!=null) {
			
			List<DepositoCausionaleVO> depositoCausionaleVOList = this.iDepositoCausionaleService
																      .ricercaElencoDepositoCausionaleByCodicePagamento(rigeneraBollettinoPagamentoRequest
																    		  											.getCodicePagamento());
			
			if(depositoCausionaleVOList!=null && !depositoCausionaleVOList.isEmpty()) {
				DepositoCausionaleVO depositoCausionaleVO = depositoCausionaleVOList.get(0);
				Long idDocumento = depositoCausionaleVO.getDocumentoVO().getIdDocumento().longValue();
				Integer annoAccertamento = Integer.valueOf(depositoCausionaleVO.getAnnoAcccertamento());
				String numeroAccertamento = depositoCausionaleVO.getNumeroAccertamento();				
				Integer tipoCarrelloDepositoCauzionale = null;
				if(Constants.RICHIESTA_DEP_CAUSIONALE_CODICE_TIPO_DOCUMENTO.equalsIgnoreCase(depositoCausionaleVO.getDocumentoVO().getTipoDocumentoVO().getCodiceTipoDocumento())) {
					tipoCarrelloDepositoCauzionale = Constants.RICHIESTA_DEP_CAUSIONALE_ID_TIPO_CARRELLO_PAGAMENTO;
				} else {
					tipoCarrelloDepositoCauzionale = Constants.RICHIESTA_DEP_CAUSIONALE_INTEGRAZIONE_ID_TIPO_CARRELLO_PAGAMENTO;
				}
				
				return this.iDepositoCausionaleService
							.generaCarrelloBollettinoPagamentoDepositoCausionaleByIdDicumentoRichiesta(idDocumento,	
																									   annoAccertamento, numeroAccertamento,
																							   		   tipoCarrelloDepositoCauzionale);
			}
			
		}
		
		return null;
	}
	
	/******************************************************************
	 * Funzioni PRIVATE
	 ******************************************************************/	
 	private BigDecimal _calcolaImportoTotaleDepositoCauzionale(List<DepositoCausionaleVO> depositoCausionaleVOs) {
		BigDecimal output = BigDecimal.ZERO;
		if(depositoCausionaleVOs == null || depositoCausionaleVOs.isEmpty()) {
			return output;
		}
		
		Iterator<DepositoCausionaleVO> iterator = depositoCausionaleVOs.iterator();
		while(iterator.hasNext()){
			DepositoCausionaleVO depositoCausionaleVO = iterator.next();
			if(depositoCausionaleVO.getImporto()!=null) {
				output = output.add(depositoCausionaleVO.getImporto());
			}
		}		
		
		return output;
	}
	
	/*
	 * Funzionalità di riferimento
	 * 
	 * public Response salvaDocumentazione(MultipartFormDataInput input) throws IOException, BusinessException, ServiceException	 
	 *	  
	 */	
	private void _protocollaDeterminaEBollettinoPagamentoDepCausionaleUtilizzaACTADocumento(DepositoCausionaleVO depositoCausionaleVO, 
																	   			   			byte[] bollettinoPagamentoPDF,
																	   			   			byte[] determina) throws BusinessException, ServiceException 
	{
        
		Integer idTipoDoumentoAccertamento = 4;
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UserDetails utente = (UserDetails) principal;
                 
        ConfermaDocumentazioneRequest confermaDocumentazioneRequest = new ConfermaDocumentazioneRequest();
        DocumentiVO vo = new DocumentiVO();
        vo.setAnagraficaSoggettoVO(depositoCausionaleVO.getAnagraficaSoggettoVO());
        vo.setAnnualita(depositoCausionaleVO.getDocumentoVO().getAnnualita());
        vo.setCfPiva(depositoCausionaleVO.getAnagraficaSoggettoVO().getCfPiva());
        vo.setNomeFile("AVVISO_PAGAMENTO_DEPOSITO_CAUZIONALE_" + depositoCausionaleVO.getAnagraficaSoggettoVO().getCodiceAzienda()  + ".pdf");        
        vo.setNote("Avviso prodotto mediante applicativo SIGAS");        
        vo.setRifArchivio(null);
        vo.setTipoDocumentoVO(tipoDocumentoEntityMapper.mapEntityToVO(sigasTipoDocumentoRepository.findOne(idTipoDoumentoAccertamento)));        
        vo.setFileMaster(bollettinoPagamentoPDF);        
        
        List<AllegatoDocumentazioneVO> listAllegati = new ArrayList<AllegatoDocumentazioneVO>();
        vo.setSigasAllegatos(listAllegati);       
        
        confermaDocumentazioneRequest.setDocumentiVO(vo);
        
        iDocumentazioneDispatcher.protocollaLetteraRisp(confermaDocumentazioneRequest, utente.getIdentita().getCodFiscale());
                
        // Faccio submit del task asincrono per spostare i documenti da INDEX -> ACTA
        executorServiceProvider.getExecutorService().submit(iMoveDocumentService);
        // Fine        
    }

	/*
	 * Funzionalità di riferimento
	 * 
	 * public Response protocollaLetteraRisp(MultipartFormDataInput input)	 
	 *	  
	 */		
	private byte[] _protocollaDeterminaEBollettinoPagamentoDepCausionale(DepositoCausionaleVO depositoCausionaleVO, 
																	   byte[] bollettinoPagamentoPDF,
																	   byte[] determina,
																	   Date dataInvioMail) throws BusinessException, ServiceException 
	{
	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UserDetails utente = (UserDetails) principal;      
        
               

        ConfermaDocumentazioneRequest confermaDocumentazioneRequest = new ConfermaDocumentazioneRequest();
        confermaDocumentazioneRequest.setDocumentiVO(depositoCausionaleVO.getDocumentoVO());
        
        //Setting a stato ACCETTATO del documento
        depositoCausionaleVO.getDocumentoVO().setStatoDocumentoVO(statoDocumentoEntityMapper
        														 .mapEntityToVO(sigasStatoDocumentoRepository
        																 		.findByCodiceStato(DoquiConstants.CODICE_STATO_ACCETTATA)));        
        
		iDocumentazioneDispatcher.salvaDocumentazioneBO(confermaDocumentazioneRequest, utente.getIdentita().getCodFiscale());
        
        List<AllegatoDocumentazioneVO> listAllegati = new ArrayList<AllegatoDocumentazioneVO>();
        depositoCausionaleVO.getDocumentoVO().setSigasAllegatos(listAllegati);         
        
        DocumentiVO mergeDocumentazioneInRisposta = new DocumentiVO();
        
        byte[] templateApprovazioneDepositoCauzionale = iDepositoCausionaleService.generaReportApprovazioneDepositoCausionale(depositoCausionaleVO, dataInvioMail);
        byte[] mergeFileAll = null;
        byte[] determinaMerged = null;        
        byte[] mergePackageAll = null;
        
        /*
        if(determina != null) {
        	determinaMerged = Utilities.mergePdfFiles(bollettinoPagamentoPDF, determina);
        }
        */
        
        byte[] templateRichiestaDepositoCauzionale = iDepositoCausionaleService.generaReportRichiestaDepositoCausionale(depositoCausionaleVO);        
        if(determina != null) {
        	mergeFileAll = Utilities.mergePdfFiles(templateRichiestaDepositoCauzionale, determina);
        	mergePackageAll = Utilities.mergePdfFiles(templateApprovazioneDepositoCauzionale,mergeFileAll);
        }        
        else {
        	//mergeFileAll = Utilities.mergePdfFiles(templateRichiestaDepositoCauzionale, bollettinoPagamentoPDF);
        	mergePackageAll = Utilities.mergePdfFiles(templateApprovazioneDepositoCauzionale,templateRichiestaDepositoCauzionale);
        }               
        
        //byte[] mergePackageAll = Utilities.mergePdfFiles(templateApprovazioneDepositoCauzionale,mergeFileAll);
        
        mergeDocumentazioneInRisposta.setFileMaster(mergePackageAll);
        mergeDocumentazioneInRisposta.setNomeFile("avviso_approvazione_deposito_cauzionale_" +
        										  depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getCodiceAzienda() +
        										  ".pdf");

        confermaDocumentazioneRequest.setLetteraRisposta(mergeDocumentazioneInRisposta);
	
        mergeDocumentazioneInRisposta = iDocumentazioneDispatcher
        						       .protocollaLetteraRisp(confermaDocumentazioneRequest, utente.getIdentita().getCodFiscale());		
        
		
		// Faccio submit del task asincrono per spostare i documenti da INDEX -> ACTA
     	executorServiceProvider.getExecutorService().submit(iMoveDocumentService);		
		// Fine 
     	
     	return mergePackageAll;
	}
	
	private void _inviaMailConfermaDepositoCauzionale(DepositoCausionaleVO depositoCausionaleVO, 
													  byte[] depositoCauzionaleApprovazione, String nomeFileDepositoCauzionaleApprovazione,
													  byte[] bollettinoPagamento) 
	{
		
		SigasCParametro mittenteMailDepositoCauzionale = sigasCParametroRepository.findByDescParametro("mittente_mail_deposito_cauzionale");
		SigasCParametro oggettoMailDepositoCauzionale = sigasCParametroRepository.findByDescParametro("oggetto_mail_deposito_cauzionale");
		SigasCParametro corpoMailDepositoCauzionale = sigasCParametroRepository.findByDescParametro("corpo_mail_deposito_cauzionale");		
		SigasCParametro mailSmtpHost = sigasCParametroRepository.findByDescParametro("mailSmtpHost");
		SigasCParametro mailSmtpPort = sigasCParametroRepository.findByDescParametro("mailSmtpPort");
		
		try {
			
			if(depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getEmail()!=null) {
				
				/*
				Utilities.sendMailWithAttach(depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getEmail(),
											 mittenteMailDepositoCauzionale.getValoreString(), 
											 oggettoMailDepositoCauzionale.getValoreString(),
											 corpoMailDepositoCauzionale.getValoreString().replaceAll("<<DENOMINAZIONE>>", depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getDenominazione() +
												                                                                           " - " + 
												                                                                           depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getCodiceAzienda()),
											 mailSmtpHost.getValoreString(), 
											 mailSmtpPort.getValoreString(),
											 depositoCauzionaleApprovazione,
											 nomeFileDepositoCauzionaleApprovazione);
											 
				*/
				Map<String, byte[]> attachMap = new HashMap<>();
				attachMap.put(nomeFileDepositoCauzionaleApprovazione, depositoCauzionaleApprovazione);
				attachMap.put("bollettino_di_pagamento_deposito_cauzionale.pdf", bollettinoPagamento);
				Utilities.sendMailWithMultipleAttach(depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getEmail(),
													 mittenteMailDepositoCauzionale.getValoreString(), 
													 oggettoMailDepositoCauzionale.getValoreString(),
													 corpoMailDepositoCauzionale.getValoreString().replaceAll("<<DENOMINAZIONE>>", depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getDenominazione() +
														                                                                           " - " + 
														                                                                           depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getCodiceAzienda()),
													 mailSmtpHost.getValoreString(), 
													 mailSmtpPort.getValoreString(),
													 attachMap);				
			}
			
		} catch (MessagingException e) {
			//e.printStackTrace();
		}
	}
	
	private void _inviaMailRifiutoDepositoCauzionale(DepositoCausionaleVO depositoCausionaleVO, String noteRifiuto) 
	{
	
		SigasCParametro mittenteMailDepositoCauzionale = sigasCParametroRepository.findByDescParametro("mittente_mail_deposito_cauzionale");
		SigasCParametro oggettoMailDepositoCauzionale = sigasCParametroRepository.findByDescParametro("oggetto_mail_rifiuto_deposito_cauzionale");
		SigasCParametro corpoMailDepositoCauzionale = sigasCParametroRepository.findByDescParametro("corpo_mail_rifiuto_deposito_cauzionale");		
		SigasCParametro mailSmtpHost = sigasCParametroRepository.findByDescParametro("mailSmtpHost");
		SigasCParametro mailSmtpPort = sigasCParametroRepository.findByDescParametro("mailSmtpPort");
		
		try {
		
			if(depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getEmail()!=null) {
				
				String corpoMail = corpoMailDepositoCauzionale.getValoreString().replaceAll("<<DENOMINAZIONE>>", depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getDenominazione() +
			                       " - " + 
			                       depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getCodiceAzienda());
				
				corpoMail = corpoMail.replaceAll("<<NOTE>>", (noteRifiuto==null) ? "" : noteRifiuto);
				
				Utilities.sendMail(depositoCausionaleVO.getDocumentoVO().getAnagraficaSoggettoVO().getEmail(),
								 mittenteMailDepositoCauzionale.getValoreString(), 
								 oggettoMailDepositoCauzionale.getValoreString(),
								 corpoMail,
								 mailSmtpHost.getValoreString(), 
								 mailSmtpPort.getValoreString());	
			
			}
			
		} catch (MessagingException e) {
			//e.printStackTrace();
		}
	}	

}
