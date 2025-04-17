package it.csi.sigas.sigasbl.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.integration.doqui.helper.impl.CommonManageDocumentoHelperImpl;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.entity.SigasDepositoCausionale;
import it.csi.sigas.sigasbl.model.entity.SigasDocumenti;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.entity.SigasStoricoAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.mapper.entity.DepositoCausionaleEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDepositoCausionaleRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDocumentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentCartRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasStoricoAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.vo.depositocausionale.DepositoCausionaleVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentCartVO;
import it.csi.sigas.sigasbl.model.vo.home.ReportResponse;
import it.csi.sigas.sigasbl.request.home.StorePaymentCartRequest;
import it.csi.sigas.sigasbl.service.IDepositoCausionaleService;
import it.csi.sigas.sigasbl.service.IPaymentFoService;
import it.csi.sigas.sigasbl.service.IUtilsService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;

@Service
public class DepositoCausionaleServiceImpl extends CommonManageDocumentoHelperImpl implements IDepositoCausionaleService {
	
	protected static Logger log = Logger.getLogger(DepositoCausionaleServiceImpl.class);	
	
	@Autowired
	private DepositoCausionaleEntityMapper depositoCausionaleEntityMapper;
	
	@Autowired
	private SigasDepositoCausionaleRepository sigasDepositoCausionaleRepository;
	
	@Autowired
	private SigasStoricoAnagraficaSoggettiRepository sigasStoricoAnagraficaSoggettiRepository;
	
	@Autowired
	private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	@Autowired
	IUtilsService iUtilsService;
	
	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;
	
	@Autowired
	private IPaymentFoService iPaymentFoService;
	
	@Autowired
	private SigasProvinciaRepository sigasProvinciaRepository;
	
	@Autowired
	private SigasDocumentiRepository sigasDocumentiRepository;
	
	@Autowired
	private SigasPaymentCartRepository sigasPaymentCartRepository;

	@Override
	public List<DepositoCausionaleVO> salvaDepositoCausionale(DepositoCausionaleVO depositoCausionaleVO) {
		
		if(depositoCausionaleVO == null) {
			return null;
		}
		
		List<DepositoCausionaleVO> depositoCausionaleVOList = new ArrayList<>();
		
		//------------------------------------------------------------------------------------------
		// Aggiornamento indirizzo anagrafica soggetto previa memorizzazione in storico anagrafica
		//------------------------------------------------------------------------------------------
		if(depositoCausionaleVO.getAnagraficaSoggettoVO()!=null) {
			SigasAnagraficaSoggetti sigasAnagraficaSoggetti = this.sigasAnagraficaSoggettiRepository.findByIdAnag(depositoCausionaleVO.getAnagraficaSoggettoVO().idAnag);
			if(sigasAnagraficaSoggetti.getIndirizzo() != null) {
				if(!sigasAnagraficaSoggetti.getIndirizzo().equalsIgnoreCase(depositoCausionaleVO.getAnagraficaSoggettoVO().getIndirizzo())) {
					
					this.salvaStoricoAnagrafica(sigasAnagraficaSoggetti);
					
					this.sigasAnagraficaSoggettiRepository
						.aggiornaIndirizzoAnagraficaById(depositoCausionaleVO.getAnagraficaSoggettoVO().getIndirizzo(), 
													     depositoCausionaleVO.getAnagraficaSoggettoVO().getIdAnag());
					
				}
			}
			
			//Rieseguo la ricerca epr receprire eventuali variazioni.
			sigasAnagraficaSoggetti = this.sigasAnagraficaSoggettiRepository.findByIdAnag(depositoCausionaleVO.getAnagraficaSoggettoVO().idAnag);
			if((sigasAnagraficaSoggetti.getCfPiva() == null || !sigasAnagraficaSoggetti.getCfPiva().equalsIgnoreCase(depositoCausionaleVO.getDocumentoVO().getCfPiva())) &&
			   depositoCausionaleVO.getDocumentoVO().getCfPiva() != null) 
			{
				this.salvaStoricoAnagrafica(sigasAnagraficaSoggetti);
				
				this.sigasAnagraficaSoggettiRepository.aggiornaCFAnagraficaById(depositoCausionaleVO.getDocumentoVO().getCfPiva(), 
																				depositoCausionaleVO.getAnagraficaSoggettoVO().getIdAnag());				
			}
		}
		
		//------------------------------------------------------------------------------------------
		// SAVE deposito causionale
		//------------------------------------------------------------------------------------------
		BigDecimal importoUnitario = BigDecimal.ZERO;
		List<String> elencoProvince = null;
		if(Constants.RICHIESTA_DEP_CAUSIONALE_CODICE_TUTTE_PROVINCE.equalsIgnoreCase(depositoCausionaleVO.getProvinciaVO().getSigla())) {
			elencoProvince = this.iPaymentFoService.getAllPiemonteCounties();
		} else {
			elencoProvince = new ArrayList<>();
			elencoProvince.add(depositoCausionaleVO.getProvinciaVO().getSigla());
		}	
		
		if(depositoCausionaleVO.getImporto()!=null) {
			BigDecimal divisore = new BigDecimal(elencoProvince.size());
			//importoUnitario = depositoCausionaleVO.getImporto().divide(divisore).setScale(2,RoundingMode.HALF_UP);
			importoUnitario = depositoCausionaleVO.getImporto().divide(divisore);
		}
		
		Iterator<String> iterator = elencoProvince.iterator();		
		while(iterator.hasNext()) {
			String siglaProvincia = iterator.next();
			SigasDepositoCausionale sigasDepositoCausionaleEntity = this.depositoCausionaleEntityMapper
					   													.mapVOtoEntity(depositoCausionaleVO);
			
			SigasProvincia sigasProvincia = this.sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(siglaProvincia);
			sigasDepositoCausionaleEntity.setSigasProvincia(sigasProvincia);
			
			sigasDepositoCausionaleEntity.setImporto(importoUnitario);
			
			SigasDepositoCausionale sigasDepositoCausionale = this.sigasDepositoCausionaleRepository.save(sigasDepositoCausionaleEntity);
			depositoCausionaleVOList.add(this.depositoCausionaleEntityMapper.mapEntityToVO(sigasDepositoCausionale));
		}		 
		
		return depositoCausionaleVOList;
	}

	@Override
	public DepositoCausionaleVO ricercaDepositoCausionaleById(Long idDepositoCausionale) {
		
		if(idDepositoCausionale == null) {
			return null;
		}
		
		DepositoCausionaleVO depositoCausionaleVO = this.depositoCausionaleEntityMapper
														.mapEntityToVO(this.sigasDepositoCausionaleRepository
																		   .findByIdDepositoCausionale(idDepositoCausionale));
			
		return depositoCausionaleVO;
	}
	
	@Override
	public byte[] generaReportRichiestaDepositoCausionale(DepositoCausionaleVO depositoCausionaleVO) {
		ReportResponse reportResponse = null;
		Map<String, Object> jasperParam = null;
		byte[] export = null;				
    	
    	SigasCParametro codiceFiscaleEnteCreditore = sigasCParametroRepository.findByDescParametro("codiceFiscaleEnteCreditore");
    	SigasCParametro enteBeneficiario = sigasCParametroRepository.findByDescParametro("rest_ente_beneficiario");
    	
    	reportResponse = new ReportResponse();
    	jasperParam = new HashMap<>();
    	
    	BigDecimal importoTotale = BigDecimal.ZERO;
    	if(depositoCausionaleVO.getImporto()!=null) {
    		importoTotale = depositoCausionaleVO.getImporto(); 
    	}
    	
    	jasperParam.put("regione", enteBeneficiario.getValoreString());
		jasperParam.put("cfEnte", codiceFiscaleEnteCreditore.getValoreString());
		
		//jasperParam.put("importoPagato", importoTotale.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ","));
		jasperParam.put("importoPagato", importoTotale.setScale(2).toString().replace(".", ","));
		
		String denominazioneRagioneSociale = "";
		String cfPagatore = "";
		String indirizzo = "";
		String comune = "";
		String provinicia = "";
		if(depositoCausionaleVO.getAnagraficaSoggettoVO()!=null) {
			
			SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(depositoCausionaleVO.getAnagraficaSoggettoVO().idAnag);
			
			if(depositoCausionaleVO.getAnagraficaSoggettoVO().getDenominazione()!=null) {
				denominazioneRagioneSociale = depositoCausionaleVO.getAnagraficaSoggettoVO().getDenominazione() + 
											  " - " + 
											  depositoCausionaleVO.getAnagraficaSoggettoVO().getCodiceAzienda();
			}
			
			if(depositoCausionaleVO.getAnagraficaSoggettoVO().getCfPiva() != null) {
				cfPagatore = depositoCausionaleVO.getAnagraficaSoggettoVO().getCfPiva();
			}
			
			if(depositoCausionaleVO.getAnagraficaSoggettoVO().getIndirizzo() != null) {
				indirizzo = depositoCausionaleVO.getAnagraficaSoggettoVO().getIndirizzo();
			}
			
			if(depositoCausionaleVO.getAnagraficaSoggettoVO().getIdComune() != null) {
				comune = sigasAnagraficaSoggetti.getSigasComune().getDenomComune();
			}
			
			if(depositoCausionaleVO.getAnagraficaSoggettoVO().getIdProvincia() != null) {
				provinicia = sigasAnagraficaSoggetti.getSigasProvincia().getSiglaProvincia();
			}
		}
		jasperParam.put("ragioneSociale", denominazioneRagioneSociale);
		jasperParam.put("cfPagatore", cfPagatore);	
		jasperParam.put("indirizzo", indirizzo);
		jasperParam.put("comune", comune);
		jasperParam.put("provincia", provinicia);
    	
    	try {
			export = iUtilsService.printReportPDF(Constants.COD_REPORT_RICHIESTA_DEP_CAUSIONALE, jasperParam, null);
			
			String data = Base64.getEncoder().encodeToString(export);
    		
			reportResponse.setFile(export);
			reportResponse.setNome(Constants.RICHIESTA_DEP_CAUSIONALE_FILE_NAME);
			reportResponse.setMimeType(Constants.RICHIESTA_DEP_CAUSIONALE_MINE_TYPE);
			
		} catch (Exception e) {
			log.error("Eccezione durante la generazione della richiesta di deposito causionale", e);
			throw new BusinessException(e.getMessage(),  ErrorCodes.BUSSINESS_EXCEPTION);
		}    	
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository, "GENERA RICHIESTA DEP CAUSIONALE", 
															 "sigas_deposito_causionale", "ID DEP CAUSIONALE: " + depositoCausionaleVO.getIdDepositoCausionale());
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
										   csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), 
										   csiLogAudit.getId().getKeyOper());
		
		return export;
	}
	
	@Override
	public byte[] generaReportApprovazioneDepositoCausionale(DepositoCausionaleVO depositoCausionaleVO, Date dataInvioMail) {
		ReportResponse reportResponse = null;
		Map<String, Object> jasperParam = null;
		byte[] export = null;				
    	
    	SigasCParametro codiceFiscaleEnteCreditore = sigasCParametroRepository.findByDescParametro("codiceFiscaleEnteCreditore");
    	SigasCParametro enteBeneficiario = sigasCParametroRepository.findByDescParametro("rest_ente_beneficiario");
    	
    	reportResponse = new ReportResponse();
    	jasperParam = new HashMap<>();
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    	String dataInvioMailFormat = formatter.format(dataInvioMail);
    	jasperParam.put("dataInvioMail", dataInvioMailFormat);
    	
    	BigDecimal importoTotale = BigDecimal.ZERO;
    	if(depositoCausionaleVO.getImporto()!=null) {
    		importoTotale = depositoCausionaleVO.getImporto(); 
    	}
    	
    	jasperParam.put("regione", enteBeneficiario.getValoreString());
		jasperParam.put("cfEnte", codiceFiscaleEnteCreditore.getValoreString());		
		jasperParam.put("importoPagato", importoTotale.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ","));
		
		String denominazioneRagioneSociale = "";
		String cfPagatore = "";
		String indirizzo = "";
		String comune = "";
		String provinicia = "";
		if(depositoCausionaleVO.getAnagraficaSoggettoVO()!=null) {
			
			SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(depositoCausionaleVO.getAnagraficaSoggettoVO().idAnag);
				
			
			if(depositoCausionaleVO.getAnagraficaSoggettoVO().getDenominazione()!=null) {
				denominazioneRagioneSociale = depositoCausionaleVO.getAnagraficaSoggettoVO().getDenominazione() + 
											  " - " + 
											  depositoCausionaleVO.getAnagraficaSoggettoVO().getCodiceAzienda();
			}
			
			if(depositoCausionaleVO.getAnagraficaSoggettoVO().getCfPiva() != null) {
				cfPagatore = depositoCausionaleVO.getAnagraficaSoggettoVO().getCfPiva();
			}
			
			if(depositoCausionaleVO.getAnagraficaSoggettoVO().getIndirizzo() != null) {
				indirizzo = depositoCausionaleVO.getAnagraficaSoggettoVO().getIndirizzo();
			}
			
			if(depositoCausionaleVO.getAnagraficaSoggettoVO().getIdComune() != null) {
				comune = sigasAnagraficaSoggetti.getSigasComune().getDenomComune();
			}
			
			if(depositoCausionaleVO.getAnagraficaSoggettoVO().getIdProvincia() != null) {
				provinicia = sigasAnagraficaSoggetti.getSigasProvincia().getSiglaProvincia();
			}
		}
		jasperParam.put("ragioneSociale", denominazioneRagioneSociale);
		jasperParam.put("cfPagatore", cfPagatore);	
		jasperParam.put("indirizzo", indirizzo);
		jasperParam.put("comune", comune);
		jasperParam.put("provincia", provinicia);
    	
    	try {
			export = iUtilsService.printReportPDF(Constants.COD_REPORT_APPROVAZIONE_DEP_CAUSIONALE, jasperParam, null);
			
			String data = Base64.getEncoder().encodeToString(export);
    		
			reportResponse.setFile(export);
			reportResponse.setNome(Constants.APPROVAZIONE_DEP_CAUSIONALE_FILE_NAME);
			reportResponse.setMimeType(Constants.APPROVAZIONE_DEP_CAUSIONALE_MINE_TYPE);
			
		} catch (Exception e) {
			log.error("Eccezione durante la generazione della lettera di approvazione deposito causionale", e);
			throw new BusinessException(e.getMessage(),  ErrorCodes.BUSSINESS_EXCEPTION);
		}    	
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository, "GENERA RICHIESTA DEP CAUSIONALE", 
															 "sigas_deposito_causionale", "ID DEP CAUSIONALE: " + depositoCausionaleVO.getIdDepositoCausionale());
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
										   csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), 
										   csiLogAudit.getId().getKeyOper());
		
		return export;
	}
	
	@Override
	public byte[] generaCarrelloBollettinoPagamentoDepositoCausionaleByIdDicumentoRichiesta(Long idDocumento, Integer annoAccertamento, 
																							String numeroAccertamento, Integer tipoCarreloDepositoCauzionale) {
		
		if(idDocumento == null) {
			return null;
		}		
		
		PaymentCartVO ulitmoPaymentCartVO = _creaCarrelliPagmentoPerDepositoCausionale(idDocumento, tipoCarreloDepositoCauzionale);
				
		return this._generaBolletinoPagamento(ulitmoPaymentCartVO, 
											  Constants.RICHIESTA_DEP_CAUSIONALE_ID_TIPO_PAGAMENTO, 
											  tipoCarreloDepositoCauzionale,
											  annoAccertamento,
											  numeroAccertamento);
	}
	
	@Override
	public List<DepositoCausionaleVO> ricercaElencoDepositoCausionaleByIdDocumento(Long idDocumento) {
		
		List<DepositoCausionaleVO> depositoCausionaleVOs = null;
		
		List<SigasDepositoCausionale> sigasDepositoCausionales = this.sigasDepositoCausionaleRepository.findByIdDocumento(idDocumento);
		if(sigasDepositoCausionales!=null && !sigasDepositoCausionales.isEmpty()) {
			depositoCausionaleVOs = new ArrayList<>();
			
			Iterator<SigasDepositoCausionale> iterator = sigasDepositoCausionales.iterator();
			while(iterator.hasNext()) {
				SigasDepositoCausionale sigasDepositoCausionale = iterator.next();
				
				depositoCausionaleVOs.add(this.depositoCausionaleEntityMapper.mapEntityToVO(sigasDepositoCausionale));
			}			
		}		
		return depositoCausionaleVOs;
	}
	
	@Override
	public List<DepositoCausionaleVO> ricercaElencoDepositoCausionaleByCodicePagamento(String codicePagamento){
		
		List<DepositoCausionaleVO> depositoCausionaleVOs = null;
		
		if(codicePagamento==null) {
			return null;
		}
		
		List<SigasPaymentCart> sigasPaymentCartList =  this.sigasPaymentCartRepository.findByCodicePagamento(codicePagamento);
		if(sigasPaymentCartList!=null && !sigasPaymentCartList.isEmpty()) {
			
			depositoCausionaleVOs = new ArrayList<>();
			
			Iterator<SigasPaymentCart> iterator = sigasPaymentCartList.iterator();
			while(iterator.hasNext()) {
				SigasPaymentCart sigasPaymentCartItem = iterator.next();
				
				SigasDepositoCausionale sigasDepositoCausionale = this.sigasDepositoCausionaleRepository
																	  .findByIdCarrelloPagamenti(sigasPaymentCartItem.getIdCarrello());
				
				if(sigasDepositoCausionale!=null) {
					depositoCausionaleVOs.add(this.depositoCausionaleEntityMapper.mapEntityToVO(sigasDepositoCausionale));
				}				
			}
		}
		
		return depositoCausionaleVOs;		
	}
	
	@Override
	public boolean aggiornaImportoRichiestaDepositoCauzionale(List<DepositoCausionaleVO> depositoCausionaleVOs, BigDecimal importo, 
															  String annoAccertamento, String numeroAccertamento, String numeroDetermina) 
	{
		
		if(depositoCausionaleVOs==null || depositoCausionaleVOs.isEmpty() || importo == null) {
			return false;
		}
		
		BigDecimal importoUnitario = BigDecimal.ZERO;
		List<String> elencoProvince = this.iPaymentFoService.getAllPiemonteCounties();
		
		if(importo != null && depositoCausionaleVOs!=null && depositoCausionaleVOs.size() > 1) {
			BigDecimal divisore = new BigDecimal(elencoProvince.size());
			//importoUnitario = importo.divide(divisore).setScale(2,RoundingMode.HALF_UP);
			importoUnitario = importo.divide(divisore);
		} else {
			importoUnitario = importo;
		}
		
		Iterator<DepositoCausionaleVO> iterator = depositoCausionaleVOs.iterator();		
		while(iterator.hasNext()) {
			DepositoCausionaleVO depositoCausionaleVO = iterator.next();
			this.sigasDepositoCausionaleRepository.aggiornaDepositoCausionaleByParams(importoUnitario, depositoCausionaleVO.getIdDepositoCausionale(), 
																					  annoAccertamento, numeroAccertamento, numeroDetermina);			
		}
		
		return true;
	}
	
	@Override
	public boolean aggiornaRichiestaDepositoCauzionaleDatiAggiuntivi(List<DepositoCausionaleVO> depositoCausionaleVOs, 
																	 String annoAccertamento, String numeroAccertamento, 
																	 String numeroDetermina) 
	{
		
		if(depositoCausionaleVOs==null || depositoCausionaleVOs.isEmpty()) {
			return false;
		}
		
		Iterator<DepositoCausionaleVO> iterator = depositoCausionaleVOs.iterator();		
		while(iterator.hasNext()) {
			DepositoCausionaleVO depositoCausionaleVO = iterator.next();
			this.sigasDepositoCausionaleRepository.aggiornaDepositoCausionaleByParams(depositoCausionaleVO.getImporto(), 
																					  depositoCausionaleVO.getIdDepositoCausionale(), 
																					  annoAccertamento, numeroAccertamento, numeroDetermina);			
		}
		
		return true;
		
	}
	
	/*************************************************************************
	 * 
	 * FUNZIONI PRIVATE
	 * 
	 ************************************************************************/
	
	private void salvaStoricoAnagrafica(SigasAnagraficaSoggetti sigasAnagraficaSoggettiBeforeUpdate) 
	{
		if(sigasAnagraficaSoggettiBeforeUpdate == null) {
			return;
		}		
		
		SigasStoricoAnagraficaSoggetti sigasStoricoAnagraficaSoggetti = new SigasStoricoAnagraficaSoggetti();		
		sigasStoricoAnagraficaSoggetti.setDataRiferimento(Calendar.getInstance().getTime());		
		sigasStoricoAnagraficaSoggetti.setEmail(sigasAnagraficaSoggettiBeforeUpdate.getEmail());
		sigasStoricoAnagraficaSoggetti.setIban(sigasAnagraficaSoggettiBeforeUpdate.getIban());
		sigasStoricoAnagraficaSoggetti.setIdAnag(sigasAnagraficaSoggettiBeforeUpdate.getIdAnag());
		sigasStoricoAnagraficaSoggetti.setIndirizzo(sigasAnagraficaSoggettiBeforeUpdate.getIndirizzo());
		sigasStoricoAnagraficaSoggetti.setPec(sigasAnagraficaSoggettiBeforeUpdate.getPec());
		sigasStoricoAnagraficaSoggetti.setCfPiva(sigasAnagraficaSoggettiBeforeUpdate.getCfPiva());
		sigasStoricoAnagraficaSoggetti.setOwnerOperazione(Constants.OWNER_UPDATE_SOGGETTO_ANAG);
		
		if(sigasAnagraficaSoggettiBeforeUpdate.getSigasImport()!=null) {
			sigasStoricoAnagraficaSoggetti.setIdImport(sigasAnagraficaSoggettiBeforeUpdate.getSigasImport().getIdImport());
			sigasStoricoAnagraficaSoggetti.setAnnualita(sigasAnagraficaSoggettiBeforeUpdate.getSigasImport().getAnnualita());
		}		
		
		sigasStoricoAnagraficaSoggetti.setCodiceAzienda(sigasAnagraficaSoggettiBeforeUpdate.getCodiceAzienda());
		sigasStoricoAnagraficaSoggetti.setDenominazione(sigasAnagraficaSoggettiBeforeUpdate.getDenominazione());
		
		this.sigasStoricoAnagraficaSoggettiRepository.save(sigasStoricoAnagraficaSoggetti);		
	}
	
	private boolean _verificaPresenzaCarrelliEsistentiDepositoCauzionale(List<SigasDepositoCausionale> sigasDepositoCausionales, 
																		Integer tipoCarreloDepositoCauzionale) 
	{		
		
		if(sigasDepositoCausionales!=null && !sigasDepositoCausionales.isEmpty()) 
		{
			Iterator<SigasDepositoCausionale> iterator = sigasDepositoCausionales.iterator();		
			while(iterator.hasNext()) {
				SigasDepositoCausionale sigasDepositoCausionale = iterator.next();
				//Creazione carrello pagamenti di tipo Dep. Causionale			
				Integer idAnag = (int)sigasDepositoCausionale.getSigasAnagraficaSoggetti().getIdAnag();
				String subjectName = sigasDepositoCausionale.getSigasAnagraficaSoggetti().getDenominazione() + 
									 " - " + 
									 sigasDepositoCausionale.getSigasAnagraficaSoggetti().getCodiceAzienda();	    	
		    	String cfPiva = sigasDepositoCausionale.getSigasAnagraficaSoggetti().getCfPiva();
		    	String codiceAzienda = sigasDepositoCausionale.getSigasAnagraficaSoggetti().getCodiceAzienda();
		    		    	
				StorePaymentCartRequest storePaymentCartRequest = new StorePaymentCartRequest();
				storePaymentCartRequest.setIdAnag(idAnag);							 
				storePaymentCartRequest.setMonth(Calendar.JANUARY + 1);				
				storePaymentCartRequest.setYear(sigasDepositoCausionale.getSigasDocumento().getAnnualita());
				storePaymentCartRequest.setSubjectName(subjectName);
				storePaymentCartRequest.setCodiceFiscalePIva(cfPiva);			
				storePaymentCartRequest.setAmount(String.valueOf(sigasDepositoCausionale.getImporto()));							
				storePaymentCartRequest.setArea(sigasDepositoCausionale.getSigasProvincia().getSiglaProvincia());
				storePaymentCartRequest.setType(tipoCarreloDepositoCauzionale);
				storePaymentCartRequest.setEmail(sigasDepositoCausionale.getSigasAnagraficaSoggetti().getEmail());
				storePaymentCartRequest.setSubjectCode(codiceAzienda);
				
				List<PaymentCartVO> carrelliPresentiList  = this.iPaymentFoService.ricercaCartItemsPerParametri(storePaymentCartRequest);
				if(carrelliPresentiList!=null && !carrelliPresentiList.isEmpty()) {
					for(PaymentCartVO paymentCartVO : carrelliPresentiList) {
						if(paymentCartVO.getArea().equalsIgnoreCase(sigasDepositoCausionale.getSigasProvincia().getSiglaProvincia()) && 
						   paymentCartVO.getYear().equalsIgnoreCase(sigasDepositoCausionale.getSigasDocumento().getAnnualita()) &&
						   paymentCartVO.getMonth().equals(Calendar.JANUARY + 1)) 
						{
							return true;
							
						}
					}					
				}				
			}
		}
		
		return false;
	}
	
	private PaymentCartVO _creaCarrelliPagmentoPerDepositoCausionale(Long idDocumento, Integer tipoCarreloDepositoCauzionale) 
	{
				
		if(idDocumento == null)
		{
			return null;
		}
		
		boolean firstInsertion = true;
		PaymentCartVO paymentCartVO = null;
		
		List<SigasDepositoCausionale> sigasDepositoCausionales = this.sigasDepositoCausionaleRepository.findByIdDocumento(idDocumento);
		if(sigasDepositoCausionales!=null && !sigasDepositoCausionales.isEmpty()) 
		{
			if(this._verificaPresenzaCarrelliEsistentiDepositoCauzionale(sigasDepositoCausionales, tipoCarreloDepositoCauzionale)) {
				throw new BusinessException("Per i dati impostati e' gia' presente un carrello pagato o e' gia' stato emesso un bollettino.");
			}
			
			Iterator<SigasDepositoCausionale> iterator = sigasDepositoCausionales.iterator();		
			while(iterator.hasNext()) {
				SigasDepositoCausionale sigasDepositoCausionale = iterator.next();
				
				//Creazione carrello pagamenti di tipo Dep. Causionale			
				Integer idAnag = (int)sigasDepositoCausionale.getSigasAnagraficaSoggetti().getIdAnag();
				String subjectName = sigasDepositoCausionale.getSigasAnagraficaSoggetti().getDenominazione() + 
									 " - " + 
									 sigasDepositoCausionale.getSigasAnagraficaSoggetti().getCodiceAzienda();	    	
		    	String cfPiva = sigasDepositoCausionale.getSigasAnagraficaSoggetti().getCfPiva();
		    	String codiceAzienda = sigasDepositoCausionale.getSigasAnagraficaSoggetti().getCodiceAzienda();
		    		    	
				StorePaymentCartRequest storePaymentCartRequest = new StorePaymentCartRequest();
				storePaymentCartRequest.setIdAnag(idAnag);
				
				Calendar cal = Calendar.getInstance();
				//storePaymentCartRequest.setMonth(cal.get(Calendar.MONTH)+1); 
				storePaymentCartRequest.setMonth(Calendar.JANUARY + 1);
				
				storePaymentCartRequest.setYear(sigasDepositoCausionale.getSigasDocumento().getAnnualita());
				storePaymentCartRequest.setSubjectName(subjectName);
				storePaymentCartRequest.setCodiceFiscalePIva(cfPiva);			
				storePaymentCartRequest.setAmount(String.valueOf(sigasDepositoCausionale.getImporto()));			
							
				storePaymentCartRequest.setArea(sigasDepositoCausionale.getSigasProvincia().getSiglaProvincia());
				storePaymentCartRequest.setType(tipoCarreloDepositoCauzionale);
				storePaymentCartRequest.setEmail(sigasDepositoCausionale.getSigasAnagraficaSoggetti().getEmail());
				storePaymentCartRequest.setSubjectCode(codiceAzienda);
				
				if(firstInsertion) {				
					paymentCartVO = this.iPaymentFoService.insertPaymentCart(storePaymentCartRequest);
					firstInsertion = false;
					
				} else {
					storePaymentCartRequest.setPaymentCode(paymentCartVO.getPaymentCode());
					paymentCartVO = this.iPaymentFoService.storePaymentCart(storePaymentCartRequest);
				}
				
				this.sigasDepositoCausionaleRepository.aggiornaIdCarrelloPagamentiDepositoCausionale(paymentCartVO.getId(), 
																									 sigasDepositoCausionale.getIdDepositoCausionale());
			}
		}	
				
		return paymentCartVO;
	}
	
	
	private byte[] _generaBolletinoPagamento(PaymentCartVO ultimoPaymentCartCreato, 
										     Integer idTipoPagmentoCarrello, Integer tipoCarrelloDepCausionale,
										     Integer annoAccertamento, String numeroAccertamento) 
	{
		if(ultimoPaymentCartCreato==null) {
			return new byte[0];
		}
		
		StorePaymentCartRequest storePaymentCartRequest = new StorePaymentCartRequest();
		storePaymentCartRequest.setYear(ultimoPaymentCartCreato.getYear());
		storePaymentCartRequest.setSubjectName(ultimoPaymentCartCreato.getSubjectName());
		storePaymentCartRequest.setPaymentCode(ultimoPaymentCartCreato.getPaymentCode());
		storePaymentCartRequest.setEmail(ultimoPaymentCartCreato.getEmail());
		storePaymentCartRequest.setPaymentType(idTipoPagmentoCarrello);
		storePaymentCartRequest.setType(tipoCarrelloDepCausionale);
		return this.iPaymentFoService.generaAvvisoPagamento(storePaymentCartRequest, annoAccertamento, numeroAccertamento);
		
	}	

}
