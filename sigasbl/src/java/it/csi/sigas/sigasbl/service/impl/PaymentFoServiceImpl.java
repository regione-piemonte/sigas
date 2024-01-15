package it.csi.sigas.sigasbl.service.impl;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import it.csi.sigas.sigasbl.common.config.EPayConfig;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.common.utils.Utilities;
import it.csi.sigas.sigasbl.facade.EPayServiceFacade;
import it.csi.sigas.sigasbl.integration.epay.mapper.EPayWsInputMapper;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.entity.SigasDichVersamenti;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartNotify;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartRT;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentTypes;
import it.csi.sigas.sigasbl.model.entity.SigasTipoCarrello;
import it.csi.sigas.sigasbl.model.entity.SigasTipoVersamento;
import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFODetailEntityCustom;
import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFOEntityGroupedCustom;
import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFOEntitySingleCustom;
import it.csi.sigas.sigasbl.model.mapper.entity.PaymentSubjectFOEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.PaymentTypeEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoCarrelloEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCMessaggiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichVersamentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentCartNotifyRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentCartRTRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentCartRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentSubjectFORepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentTypesRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoCarrelloRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoVersamentoRepository;
import it.csi.sigas.sigasbl.model.vo.home.PaymentCartVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentRTInfoVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentRedirectVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentSubjectVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentTypesVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoCarrelloVO;
import it.csi.sigas.sigasbl.request.home.SearchSubjectPaymentFoRequest;
import it.csi.sigas.sigasbl.request.home.StorePaymentCartRequest;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.service.IPaymentFoService;
import it.csi.sigas.sigasbl.service.IUserService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;

@Service
public class PaymentFoServiceImpl implements IPaymentFoService {

	static private final SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
	
	//@Autowired private SigasImportRepository sigasImportRepository;
	
	@Autowired private SigasDichVersamentiRepository sigasDichVersamentiRepository;

	@Autowired
	private SigasPaymentSubjectFORepository subjectFoRepository;

	@Autowired
	private PaymentSubjectFOEntityMapper soggettoEntityMapper;
	
	@Autowired
	private SigasPaymentTypesRepository paymentTypesRepository;
	
	@Autowired
	private SigasPaymentCartRepository paymentCartRepository;
	
	@Autowired
	private SigasPaymentCartRTRepository paymentCartRTRepository;
	
	@Autowired
	private SigasPaymentCartNotifyRepository paymentCartNotifyRepository;
	
	@Autowired
	private PaymentTypeEntityMapper paymentTypeEntityMapper;
	
	@Autowired
	private TipoCarrelloEntityMapper tipoVersamentoEntityMapper;
	
	@Autowired
	private EPayServiceFacade ePayServiceFacade;
	
	@Autowired
	private EPayWsInputMapper ePayWsInputMapper;
	
	@Autowired
	private SigasProvinciaRepository sigasProvinciaRepository;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;

//	@Autowired
//	private EPayConfig config;

	@Autowired
	SigasCMessaggiRepository sigasCMessaggiRepository;
	
	@Autowired 
	private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;
	
	@Autowired 
	private SigasTipoVersamentoRepository sigasTipoVersamentoRepository;
	
	@Autowired 
	private SigasTipoCarrelloRepository sigasTipoCarrelloRepository;
	
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private UserDetails getUser() {
		return (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	    
	private String getUserName() {
		return getUser().getUsername();
	}
	
	private String getTaxCode() {
//		if(SecurityUtils.hasRoles(new String[] { AuthorizationRoles.HOME } ))
//			return ""; // BO user will not not be limited to CF constraints
		if(iUserService.getProfilatura(getUser()).getAbilitaUtenteRegione())
			return "";
			
		return getUser().getIdentita().getCodFiscale();
	}
	
    @Override
	public List<TipoCarrelloVO> retrievePaymentTypologies() {
		List<SigasTipoCarrello> tipoVersamentoDBList = paymentCartRepository.findFOPaymentTypes();
		return	tipoVersamentoEntityMapper.mapListEntityToListVO(tipoVersamentoDBList);
    }
	
    @Override
    public List<String> searchYearsForFoUser() {
    	List<String> years = subjectFoRepository.findListaYearsPaymentFO();

    	// adds missing sigas_dich_consumi years 
    	List<String> res = new ArrayList<String>();
    	Integer currYear = LocalDateTime.now().getYear();
    	do {
    		if(years.size() == 0 || years.get(0) == null)
        		years.add(0, ""+currYear);
    		else if(Integer.parseInt(years.get(0)) != currYear)
        		years.add(0, ""+(Integer.parseInt(years.get(0)) + 1));
    	} while(Integer.parseInt(years.get(0)) < currYear);

    	return years;
    }
    
    public List<String> retrievePaidYears() {
    	List<String> years = subjectFoRepository.retrievePaidYears(getTaxCode());
    	return years;
    }
    
    @Override
    public List<String> getUserAmountsForPrevYear(String idAnag, String year) {
    	if(year == null || year.length() == 0) return new ArrayList<String>();
    	return subjectFoRepository.getUserAmountsForPrevYear(Long.parseLong(idAnag), ""+(Integer.parseInt(year)-1));
    }
    
    @Override
    public List<String> getAllPiemonteCounties() {
    	return subjectFoRepository.getAllPiemonteCounties();
    }
    
    @Override
	public List<PaymentSubjectVO> retrieveSubjectsFO(String year) {
    	List<PaymentSubjectVO> soggettiVOList = new ArrayList<PaymentSubjectVO>();
    	List<PaymentSubjectFOEntitySingleCustom> soggettoEntityCustomList = null;
    	
		if (year != null && year.length() > 0)
	    	soggettoEntityCustomList = subjectFoRepository.findPaymentSubjectsFO(getTaxCode());

    	soggettiVOList = soggettoEntityMapper.mapListEntityToListVO(soggettoEntityCustomList);
    	
    	logger.debug("I soggetti ritornati sono " + (soggettiVOList == null? 0:soggettiVOList.size()));
    	
		return soggettiVOList;
    	
    }
    
    @Override
	public List<PaymentSubjectVO> retrieveFoSubjectsForSearch(String year) {
    	List<PaymentSubjectFOEntitySingleCustom> soggettoEntityCustomList = subjectFoRepository.findFOSubjectsForPaymentSearch(getTaxCode(), year);
    	List<PaymentSubjectVO> soggettiVOList = soggettoEntityMapper.mapListEntityToListVO(soggettoEntityCustomList);
    	
    	logger.debug("I soggetti ritornati sono " + (soggettiVOList == null? 0:soggettiVOList.size()));
		return soggettiVOList;
    }

    @Override
	public List<PaymentSubjectVO> retrieveFoPaymentSubjectsForSearch(SearchSubjectPaymentFoRequest searchRequest) {
    	String year = searchRequest.getYear(); 
    	String fromDateStr = searchRequest.getDateFrom(); 
    	String toDateStr = searchRequest.getDateTo(); 
    	String fromMonthStr = searchRequest.getMonthFrom(); 
    	String toMonthStr = searchRequest.getMonthTo(); 
    	String subject = searchRequest.getSubjectName(); 
    	String fiscalcodeFO = searchRequest.getOperatorFO(); 
    	String vatcode = searchRequest.getVatCode(); 
    	String paytype = searchRequest.getPayType(); 
    	String area = searchRequest.getArea();
    	String codiceFiscalePIva = searchRequest.getCodiceFiscalePIva();
    	
    	String cf = getTaxCode();

		List<String> listCodicePagamento = new ArrayList<>();
		
//    	String cf = "";
    	if("".equals(cf) && fiscalcodeFO != null)
    		cf = fiscalcodeFO;
    	
    	Integer fromMonth = 0, toMonth = 0;
    	if(fromMonthStr != null && fromMonthStr.length() > 0)
    		fromMonth = Integer.parseInt(fromMonthStr);
    	if(toMonthStr != null && toMonthStr.length() > 0)
    		toMonth = Integer.parseInt(toMonthStr);
    	
    	String fromDate = "", toDate = "";
    	if(fromDateStr != null && fromDateStr.indexOf('-') > -1)
    		fromDate = fromDateStr.replaceAll("-", "");
    	if(toDateStr != null && toDateStr.indexOf('-') > -1)
    		toDate = toDateStr.replaceAll("-", "");
    	
//    	if(subject!=null && subject.lastIndexOf("-")!=-1) {
//    		subject = subject.substring(0, subject.lastIndexOf("-")).trim();
//    	}
    		
    	    		
    	List<PaymentSubjectFOEntityGroupedCustom> soggettoEntityCustomList = subjectFoRepository.searchFOPaymentsGrouppedByPayCode(cf,
    					year,
    					fromMonth, 
    					toMonth, 
    					fromDate, 
    					toDate, 
    	    			subject==null?"":subject, 
//    	    			vatcode==null?"":vatcode, 
    	    			paytype==null?0:Integer.parseInt(paytype), 
    					area==null?"":area,
    							SigasPaymentCart.STATO_CARRELLO_PAGATO,
    							SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_INCOMPLETO,
    							codiceFiscalePIva==null ? "":codiceFiscalePIva);
    	for (PaymentSubjectFOEntityGroupedCustom psegc :soggettoEntityCustomList) {
    		System.out.println("*******************CF=["+cf+"]");
    		System.out.println("*******************Denominazione=["+psegc.getDenominazione()+"]");
    		System.out.println("*******************Year=["+year+"]");
	    	List<SigasPaymentCart> cart_items = paymentCartRepository.retrieveCartItemsSearch(psegc.getId());
	    	if (cart_items.size()>0)
	    		listCodicePagamento.add(cart_items.get(0).getCodicePagamento());
    	}

    	List<PaymentSubjectVO> soggettiVOList = soggettoEntityMapper.mapListEntityToListVO(soggettoEntityCustomList);
    	

		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,(fromDateStr!=null)?"SIGAS BO - RICERCA PAGAMENTO":"SIGAS FO - RICERCA PAGAMENTO", "sigas_carrello_pagamenti",String.join("_", listCodicePagamento));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
			csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());

    	
    	logger.debug("I soggetti ritornati sono " + (soggettiVOList == null? 0:soggettiVOList.size()));
		return soggettiVOList;
	}
    
	public PaymentSubjectVO retrieveFoPaymentSubjectDetail(String year, 
			String idAnag) {
		
		List<PaymentSubjectFODetailEntityCustom> soggettoEntityCustomList;
		if(year == null || year.length() == 0)
			soggettoEntityCustomList = subjectFoRepository.getPaymentSubjectsFOByIdAnag(Integer.parseInt(idAnag));
		else
			soggettoEntityCustomList = subjectFoRepository.getPaymentSubjectsFODetails(year, Integer.parseInt(idAnag));		
		
    	List<PaymentSubjectVO> soggettiVOList = soggettoEntityMapper.mapListEntityToListVO(soggettoEntityCustomList);
    	
    	if(soggettiVOList.size() > 0)
    		return soggettiVOList.get(0);
    	
		return new PaymentSubjectVO();
	}
    
    /*
    @Override
	public String searchSubjectPaymentFo(String name, String area) {
    	if(area != null && area.length() > 0)
    		return subjectFoRepository.searchSubjectPaymentFo(getTaxCode(), name, area);
    	else
    		return subjectFoRepository.searchSubjectPaymentFoByName(getTaxCode(), name);
    }
    */
    
    @Override
	public List<PaymentTypesVO> retrievePaymentTypes() {
		List<SigasPaymentTypes> tipoDBList = paymentTypesRepository.findAll();
		return	paymentTypeEntityMapper.mapListEntityToListVO(tipoDBList);
    }
    
    @Override
	public void deletePaymentCartItem(StorePaymentCartRequest cartItem) {
    	List<SigasPaymentCart> cartList = new ArrayList<SigasPaymentCart>();
    	List<String> listIdCarrello = new ArrayList<String>();
    	CsiLogAudit csiLogAudit = null;
    	if(cartItem.getId() == -1l) // delete all?
    		cartList = paymentCartRepository.findByCodicePagamento(cartItem.getPaymentCode());
    	else
    		cartList.add(paymentCartRepository.findByIdCarrello(cartItem.getId()));

    	for(SigasPaymentCart cart : cartList) {
        	if(cart != null) {
        		if(paymentCartRepository.deleteCartItem(cart.getIdCarrello()) == 0)
            		throw new BusinessException(sigasCMessaggiRepository.findByDescChiaveMessaggio("carrelloErroreEliminazioneVoceCarrello").getValoreMessaggio());
        		listIdCarrello.add(String.valueOf(cart.getIdCarrello()));
         	}
       	}
    	if (cartItem.getId() == -1l)
    		csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SIGAS FO - ELIMINAZIONE CARRELLO PAGAMENTI", "sigas_carrello_pagamenti",String.valueOf(cartItem.getPaymentCode()+","+String.join("_", listIdCarrello)));
    	else
    		csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SIGAS FO - ELIMINAZIONE PAGAMENTO", "sigas_carrello_pagamenti",String.valueOf(cartItem.getPaymentCode()+","+listIdCarrello.get(0)));
  		 
    	csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
    				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
        		
    }

    @Override
	public void startCartPayment(StorePaymentCartRequest storePaymentCartRequest) {
    	String year = storePaymentCartRequest.getYear(); 
		String subject_name = storePaymentCartRequest.getSubjectName();
		
    	List<SigasPaymentCart> cart_items = paymentCartRepository.retrieveCartItems(getUser().getIdentita().getCodFiscale(),
    			SigasPaymentCart.STATO_CARRELLO_APERTO, SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_NOTIFICATO,
    			year,
    			subject_name);
    	if(cart_items.size() == 0 || storePaymentCartRequest.getPaymentType() == null) 
    		return;

    	boolean isPaymentStarted = false;
    	for(SigasPaymentCart cart : cart_items) {
			cart.setFkTipoPagamento(storePaymentCartRequest.getPaymentType());
			try {
				cart.setDataPagamento(simpleDate.parse(storePaymentCartRequest.getPayDate()));
			} catch (Exception e) {
		    	logger.error("Unexpected date format in storePaymentCart function: " + storePaymentCartRequest.getPayDate());
				cart.setDataPagamento(new Date());
			}
			cart.setEmail(storePaymentCartRequest.getEmail());
			
			isPaymentStarted = isPaymentStarted || cart.getFkStatoCarrello() >= SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_AVVIATO;
			
    		cart.setDataUpdate(new Date());
    		paymentCartRepository.save(cart);
    	}
 
    	if(!isPaymentStarted) {
	    	if(storePaymentCartRequest.getPaymentType() == SigasPaymentCart.CART_PAYMENT_TYPE_PAGOPA) {
	    		startPaymentPAGOPA(cart_items);
	    	}
	    	if(storePaymentCartRequest.getPaymentType() == SigasPaymentCart.CART_PAYMENT_TYPE_DIRECTDEBIT) {
	    		// removed
	    	}
	    	if(storePaymentCartRequest.getPaymentType() == SigasPaymentCart.CART_PAYMENT_TYPE_IUV_PAYMENT) {
	    		// removed
	    	}
	    	
	    	// update status if all checks are ok
	    	for(SigasPaymentCart cart : cart_items) {
				cart.setFkStatoCarrello(SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_AVVIATO);
	    		cart.setDataPagamento(new Date());
	    		paymentCartRepository.save(cart);
	    	}
			
			CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"CONFERMA PAGAMENTO", "sigas_carrello_pagamenti",storePaymentCartRequest.getPaymentCode());
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
    	}
    }
    
    
    @Override
	public List<PaymentCartVO> searchCartItems(String year, String subject_name, boolean paidItems, String id) {
		List<PaymentCartVO> res = new ArrayList<PaymentCartVO>();
		
		List<SigasPaymentCart> cart_items;
		List<String> listIdCarrello = new ArrayList<>();
		if(paidItems)
//			cart_items = paymentCartRepository.retrieveCartItems(getTaxCode(),
//	    			SigasPaymentCart.STATO_CARRELLO_PAGATO, SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_INCOMPLETO,
//	    			year,
//	    			subject_name);
			cart_items = paymentCartRepository.retrieveCartItemsSearch(id);
		else
			cart_items = paymentCartRepository.retrieveCartItems(getUser().getIdentita().getCodFiscale(),
    			SigasPaymentCart.STATO_CARRELLO_APERTO, SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_NOTIFICATO,
    			year,
    			subject_name);

		
    	for(SigasPaymentCart cart : cart_items) {
    		listIdCarrello.add(String.valueOf(cart.getIdCarrello()));
    		res.add(cart2VO(cart));
    	}
		if (listIdCarrello.size()>0) {
			CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"CONSULTA DETTAGLI PAGAMENTO", "sigas_carrello_pagamenti",String.join("_",listIdCarrello));
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
		}
		return res;
	}
	
    @Override
	public PaymentCartVO storePaymentCart(StorePaymentCartRequest storePaymentCartRequest) {
    	Integer idAnag = storePaymentCartRequest.getIdAnag();
    	Integer month = storePaymentCartRequest.getMonth();
    	String year = storePaymentCartRequest.getYear();
    	String subject_name = storePaymentCartRequest.getSubjectName();
    	String area = storePaymentCartRequest.getArea();
    	String cfPiva = storePaymentCartRequest.getCodiceFiscalePIva();
    	
    	if("ERROR".equals(storePaymentCartRequest.getStatus()))
    		return setCartItemInError(storePaymentCartRequest);
    	
    	List<SigasPaymentCart> cart_items = paymentCartRepository.retrieveCartItems(getUser().getIdentita().getCodFiscale(),
    			SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_AVVIATO, SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_NOTIFICATO,
    			year,
    			subject_name);
    	if(cart_items.size() > 0)
    		throw new BusinessException("Impossibile aggiungere voci ad un carrello per cui sia stato avviato il pagamento");
    	
    	SigasPaymentCart clonecart = null, cart = paymentCartRepository.findCartItem(getUser().getIdentita().getCodFiscale(), 
    			idAnag, 
    			year, 
    			month,
    			area);
    	if(cart == null) {
       		cart = new SigasPaymentCart();
        	clonecart = paymentCartRepository.findCartItem(getUser().getIdentita().getCodFiscale(), 
        			idAnag, 
        			year, 
        			0,
        			"");
    	}
    	else
    		clonecart = cart;

    	if(clonecart != null) {
    		cart.setFkStatoCarrello(clonecart.getFkStatoCarrello());
    		cart.setDataPagamento(clonecart.getDataPagamento());
    		cart.setDataVersamento(clonecart.getDataVersamento());
    		cart.setEmail(clonecart.getEmail());
    		cart.setFkTipoPagamento(clonecart.getFkTipoPagamento());
    	}
    	
		cart.setFkAnagSoggetto(idAnag);
		cart.setAnno(year);
		cart.setDenominazioneVersante(subject_name);
		cart.setMese(month);
		cart.setFkTipoCarrello(storePaymentCartRequest.getType());
		cart.setFkUtenteInsert(paymentCartRepository.getIdUser(getUser().getIdentita().getCodFiscale()));
		cart.setSiglaProvincia(storePaymentCartRequest.getArea());
		cart.setFkProvincia(paymentCartRepository.resolveAreaId(storePaymentCartRequest.getArea()));
		cart.setCodiceAzienda(storePaymentCartRequest.getSubjectCode());
		cart.setCfPiva(cfPiva);
		
		try {			
			cart.setImporto(new BigDecimal(storePaymentCartRequest.getAmount().replace(',', '.')));
		} catch (Exception skipAndGetNull) { }
    	
		cart.setCodicePagamento(paymentCartRepository.getUniquePaymentCode(idAnag, year, cart.getFkUtenteInsert()));

		cart.setDataInsert(new Date());
		
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(idAnag.longValue());
		sigasAnagraficaSoggetti.setCfPiva(cfPiva);
		sigasAnagraficaSoggettiRepository.save(sigasAnagraficaSoggetti);
		
		SigasPaymentCart newcart=paymentCartRepository.save(cart);
		PaymentCartVO ret=cart2VO(newcart);
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SIGAS FO - INSERIMENTO PAGAMENTO", "sigas_carrello_pagamenti",cart.getCodicePagamento()+"," +newcart.getIdCarrello());
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
			csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
		
		 
		return ret;
    }
    
	private PaymentCartVO setCartItemInError(StorePaymentCartRequest storePaymentCartRequest) {
		List<SigasPaymentCart> cart_items = paymentCartRepository.retrieveCartItemsSearch(storePaymentCartRequest.getPaymentCode());
		if(cart_items.size() > 0) {
	    	List<SigasPaymentCart> cart_items_invalid = paymentCartRepository.retrieveCartItems(getUser().getIdentita().getCodFiscale(),
	    			SigasPaymentCart.STATO_CARRELLO_APERTO, SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_INCOMPLETO,
	    			cart_items.get(0).getAnno(),
	    			cart_items.get(0).getDenominazioneVersante());

			for(SigasPaymentCart clonecart : cart_items_invalid)
				if(clonecart.getFkStatoCarrello() != SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_NOTIFICATO)
		    		return new PaymentCartVO(); // error already set 
		}
		
		for(SigasPaymentCart clonecart : cart_items) {
			clonecart.setFkStatoCarrello(SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_INCOMPLETO);
    		paymentCartRepository.save(clonecart);
    		
    		SigasPaymentCart cart = new SigasPaymentCart();
    		cart.setFkStatoCarrello(SigasPaymentCart.STATO_CARRELLO_COMPLETO);
    		cart.setDataPagamento(new Date());
    		cart.setDataVersamento(new Date());
			cart.setCodicePagamento(paymentCartRepository.getUniquePaymentCode(clonecart.getFkAnagSoggetto(), clonecart.getAnno(), clonecart.getFkUtenteInsert()));
			cart.setDataInsert(new Date());

			cart.setImporto(clonecart.getImporto());
    		cart.setEmail(clonecart.getEmail());
    		cart.setFkTipoPagamento(clonecart.getFkTipoPagamento());
			cart.setFkAnagSoggetto(clonecart.getFkAnagSoggetto());
			cart.setAnno(clonecart.getAnno());
			cart.setDenominazioneVersante(clonecart.getDenominazioneVersante());
			cart.setMese(clonecart.getMese());
			cart.setFkTipoCarrello(clonecart.getFkTipoCarrello());
			cart.setFkUtenteInsert(clonecart.getFkUtenteInsert());
			cart.setSiglaProvincia(clonecart.getSiglaProvincia());
			cart.setFkProvincia(clonecart.getFkProvincia());
			cart.setCodiceAzienda(clonecart.getCodiceAzienda());
			cart.setCfPiva(clonecart.getCfPiva());
    		
    		paymentCartRepository.save(cart);
		}
    	
		return new PaymentCartVO();
    }
	
    @Override
	public void savePaymentInfoToCartItems(StorePaymentCartRequest paymentInfo) {
    	List<SigasPaymentCart> cart_items = paymentCartRepository.findByCodicePagamento(paymentInfo.getPaymentCode());

    	Date paydate = new Date();
    	try { paydate = simpleDate.parse(paymentInfo.getPayDate()); } catch (Exception e) {}
    	
    	for(SigasPaymentCart cartItem : cart_items) {
    		cartItem.setFkStatoCarrello(SigasPaymentCart.STATO_CARRELLO_COMPLETO);
    		cartItem.setDataPagamento(paydate);
//    		cartItem.setDataVersamento(paydate);
    		cartItem.setEmail(paymentInfo.getEmail());
    		cartItem.setFkTipoPagamento(paymentInfo.getPaymentType());
        	cartItem.setDataUpdate(new Date());
    		
    		paymentCartRepository.save(cartItem);
    	}
	}
    
    
	
    @Override
    public PaymentRedirectVO getPaymentPagoPaRedirectInfo(StorePaymentCartRequest storePaymentCartRequest) {    	
    	
    	String year = storePaymentCartRequest.getYear(); 
		String subject_name = storePaymentCartRequest.getSubjectName();
		
    	String iuv;
    	SigasPaymentCart paymentInfo = null;
		if(year != null && year.length() > 0)
	    	try {	    		
	    		
	        	List<SigasPaymentCart> cart_items = paymentCartRepository.retrieveCartItems(getUser().getIdentita().getCodFiscale(),
	        			SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_NOTIFICATO, SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_NOTIFICATO,
	        			year,
	        			subject_name);
	        	
	        	if(cart_items.size() > 0
	        	   && ((paymentInfo = cart_items.get(0)) != null)
	        	   && paymentInfo.getFkStatoCarrello() != null
	        	   && paymentInfo.getFkStatoCarrello() == SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_NOTIFICATO
	        	   && paymentInfo.getFkTipoPagamento() == SigasPaymentCart.CART_PAYMENT_TYPE_PAGOPA
	        	   && ((iuv = paymentInfo.getIuv()) != null && iuv.length() > 0)) {
	        		
	        		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(paymentInfo.getFkAnagSoggetto().longValue());
	        			        		
	        		
	        		return ePayServiceFacade.getPaymentRedirectInfo(iuv, 
	    															paymentInfo.getCodicePagamento(),
	    															sigasAnagraficaSoggetti.getCfPiva(),
	    															sigasCMessaggiRepository.findByDescChiaveMessaggio("carrelloAttesaPagamentoRedirect").getValoreMessaggio());
	        	}
	        	
	        	else {
	        		if(paymentInfo != null && paymentInfo.getFkStatoCarrello() != null && paymentInfo.getFkStatoCarrello() > SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_NOTIFICATO)
	        			return null; // payment is allowed only when STATO_CARRELLO_PAGAMENTO_NOTIFICATO
	        		
	        		PaymentRedirectVO payInfo = new PaymentRedirectVO();
	        		payInfo.setWaitingUserMessage(sigasCMessaggiRepository.findByDescChiaveMessaggio("carrelloAttesaPagamento").getValoreMessaggio());
	        		
	        		return payInfo;
	        	}
			} catch (Exception ignore) {}
    	
		return new PaymentRedirectVO(); // not ready for payment or not valid
	}
    
    @Override
	public PaymentRTInfoVO getPaymentCartRT(String iuv) {
    	PaymentRTInfoVO info = new PaymentRTInfoVO();
    	
    	try {
    		SigasPaymentCartRT rt = paymentCartRTRepository.getXmlByIuvCode(iuv);
    		
    		if(rt != null) {
	    		byte[] xml = rt.getXml();
//	    		Element doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xml)).getDocumentElement();
	    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    		docBuilderFactory.setNamespaceAware(true);
	    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	    		Element doc = docBuilder.parse(new ByteArrayInputStream(xml)).getDocumentElement();
	    		
	    		SigasPaymentCart cart = paymentCartRepository.findByCodicePagamento(rt.getCodicePagamento()).get(0);
	    		String[] dateHour = getDomValue(doc, "dataOraMessaggioRicevuta", "").split("T");
	    		String[] date = dateHour[0].split("-");
	    		
	        	logger.info("PaymentFoServiceImpl.getPaymentCartRT: " + new String(rt.getXml()));
	    		
	    		//info.setIdAnag(getDomValue(doc, rt.get));
	    		info.setPaymentCode(rt.getCodicePagamento());
//	    		info.setTransactionCode(getDomValue(doc, "identificativoMessaggioRicevuta", ""));
	    		info.setTransactionCode(getDomValue(doc, "riferimentoMessaggioRichiesta", ""));
	    		info.setIuv(iuv);
//	    		info.setIdOriginPay(getDomValue(doc, "riferimentoMessaggioRichiesta", ""));
	    		info.setIdOriginPay(getDomValue(doc, "identificativoUnivocoRiscossione", ""));
	    		info.setMonths("" + cart.getMese());
	    		info.setYear(cart.getAnno());
	    		info.setPayDate(date[2] + "/" + date[1] + "/" + date[0] + " " + dateHour[1]);
	    		info.setSubjectName(cart.getDenominazioneVersante());
	    		info.setReceivingEntity(getDomValue(doc, "denominazioneBeneficiario", ""));
	    		//info.setVatCode(getDomValue(doc, "notexists", ""));
//	    		info.setTaxCode(getDomValue((Element)doc.getElementsByTagName("identificativoUnivocoPagatore").item(0), "codiceIdentificativoUnivoco", ""));
	    		info.setTaxCode(getDomValue((Element)doc.getElementsByTagNameNS("*", "identificativoUnivocoPagatore").item(0), "codiceIdentificativoUnivoco", ""));
	    		info.setTotalAmount(getDomValue(doc, "importoTotalePagato", ""));
//	    		info.setEntityCode(getDomValue((Element)doc.getElementsByTagName("enteBeneficiario").item(0), "codiceIdentificativoUnivoco", ""));
	    		info.setEntityCode(getDomValue((Element)doc.getElementsByTagNameNS("*", "enteBeneficiario").item(0), "codiceIdentificativoUnivoco", ""));
	    		
	    		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"SIGAS FO - RICERCA RICEVUTA TELEMATICA", "sigas_carrello_rt",String.valueOf(rt.getIdCarrelloRT()));
	    		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
	    			csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
    		}
    	} catch (Exception e) {
        	logger.error("PaymentFoServiceImpl.getPaymentCartRT exception", e);
    	}
    	
		return info;
	}
    
    private String getDomValue(Element elem, String tag, String def) {
//    	NodeList tags = elem.getElementsByTagName(tag);
    	NodeList tags = elem.getElementsByTagNameNS("*", tag);
    	if(tags.getLength() > 0) {
    		Node n = tags.item(0);
    		
        	if(n != null)
        		return n.getFirstChild().getNodeValue();
    	}
    	
    	return def;
    }

    @Override
	public void insertCartItemIUV(String paymentcode, String iuv, String idPosDebitoria, Integer newstatus) {
    	updateCartItem(paymentcode, iuv, idPosDebitoria, newstatus, false);
    }
    
	public void updateCartItem(String paymentcode, String iuv, String idPosDebitoria, Integer newstatus, boolean finalizePayment) {
    	List<SigasPaymentCart> cart_items = paymentCartRepository.findByCodicePagamento(paymentcode);
    	
    	Integer fkversamento = null;
    	if(finalizePayment && cart_items != null && cart_items.size() > 0) {
        	BigDecimal total = BigDecimal.ZERO;
    		for(SigasPaymentCart cartItem : cart_items) {
    			total = total.add(cartItem.getImporto());
    		}
    		
    		for (SigasPaymentCart cartItem : cart_items) {
    			SigasTipoCarrello sigasTipoCarrello = sigasTipoCarrelloRepository.findOne(cartItem.getFkTipoCarrello().longValue());
        		SigasTipoVersamento sigasTipoVersamento = null;
        		if(sigasTipoCarrello!=null) {
        			sigasTipoVersamento = sigasTipoVersamentoRepository.findByDenominazioneIgnoreCase(sigasTipoCarrello.getDenominazione());
        		}
        		SigasDichVersamenti versamentoEsistente = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndMeseAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaIdProvincia(
        				cartItem.getFkAnagSoggetto().longValue(),
        				subjectFoRepository.findMonthById(cartItem.getMese()),
        				sigasTipoVersamento!=null ? sigasTipoVersamento.getIdTipoVersamento():null,
        				String.valueOf(cartItem.getAnno()),
        				sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(cartItem.getSiglaProvincia()).getIdProvincia());
        		
        		
        		
        		if(versamentoEsistente==null) {
    	    		SigasDichVersamenti versamento = new SigasDichVersamenti();
    	    		
    	    		versamento.setAnnualita(cartItem.getAnno());
    	    		versamento.setDataVersamento(new Date());
    	    		versamento.setImporto(cartItem.getImporto().doubleValue());
    	    		versamento.setMese(subjectFoRepository.findMonthById(cartItem.getMese()));
    	    		versamento.setImportoComplessivo(total.doubleValue());
    	    		versamento.setNote(cartItem.getNote());
    	    		versamento.setSigasProvincia(sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(cartItem.getSiglaProvincia()));
    	    		versamento.setSigasAnagraficaSoggetti(sigasAnagraficaSoggettiRepository.findOne(cartItem.getFkAnagSoggetto().longValue()));
    	    		versamento.setSigasTipoVersamento(sigasTipoVersamento);
    	    		
    	            logger.info("PaymentFoServiceImpl.updateCartItem saving versamento" + versamento.printAll());
    	    		versamento = sigasDichVersamentiRepository.save(versamento);
    	    		fkversamento = (int)versamento.getIdVersamento();
        		}
        		
        		cartItem.setIuv(iuv);
            	cartItem.setIdPosDebitoria(idPosDebitoria);
            	cartItem.setDataUpdate(new Date());
            	cartItem.setDataVersamento(new Date());
            	cartItem.setFkStatoCarrello(newstatus);
            	if(fkversamento != null)
                	cartItem.setFkDichVersamento(fkversamento);
            	
                logger.info("PaymentFoServiceImpl.updateCartItem saving carts" + cartItem.printAll());
        		paymentCartRepository.save(cartItem);
			}
    		
    	}
    	
    	if(!finalizePayment) {
    		for(SigasPaymentCart cartItem : cart_items) {
            	cartItem.setIuv(iuv);
            	cartItem.setIdPosDebitoria(idPosDebitoria);
            	cartItem.setDataUpdate(new Date());
            	cartItem.setDataVersamento(new Date());
            	cartItem.setFkStatoCarrello(newstatus);
            	if(fkversamento != null)
                	cartItem.setFkDichVersamento(fkversamento);
            	
                logger.info("PaymentFoServiceImpl.updateCartItem saving carts" + cartItem.printAll());
        		paymentCartRepository.save(cartItem);
        	}
    	}
    	
	}
    
    @Override
	public void storePaymentCartRT(SigasPaymentCartRT cartRT) throws Exception {
    	String IUV;
    	
    	try {
    		byte[] xml = cartRT.getXml();
    		
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		docBuilderFactory.setNamespaceAware(true);
    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    		  
    		Element doc = docBuilder.parse(new ByteArrayInputStream(xml)).getDocumentElement();

    		IUV = getDomValue(doc, "identificativoUnivocoVersamento", "");
    	} catch (Exception e) {
        	logger.error("PaymentFoServiceImpl.storePaymentCartRT exception: impossible retrieve the IUV code", e);
        	throw e;
    	}
    	
    	String paymentcode = paymentCartRepository.findPaymentCodeByIUV(IUV);
    	
		cartRT.setDataUpdate(new Date());
		cartRT.setCodicePagamento(paymentcode);
		
        logger.info("PaymentFoServiceImpl.storePaymentCartNotify saving" + cartRT.printAll());
		paymentCartRTRepository.save(cartRT);
	}

    @Override
	public SigasPaymentCartRT retrievePaymentCartRT(Long id) {
		return null; //paymentCartRTRepository.findByIdCarrelloRt(id);
	}

    @Override
	public Integer storePaymentCartNotify(SigasPaymentCartNotify cartNotify, String iuv) {
    	String paymentcode = paymentCartRepository.findPaymentCodeByIUV(iuv);
    	
		cartNotify.setDataUpdate(new Date());
		cartNotify.setCodicePagamento(paymentcode);
		cartNotify.setIUV(iuv);
		
        logger.info("PaymentFoServiceImpl.storePaymentCartNotify saving" + cartNotify.printAll());
		SigasPaymentCartNotify cartNotifyRes = paymentCartNotifyRepository.save(cartNotify);
		

		// set PAID status for cart
		updateCartItem(paymentcode, cartNotify.getIUV(), cartNotify.getIdPosizioneDebitoria(), SigasPaymentCart.STATO_CARRELLO_PAGATO, true);
		
		return (int)cartNotifyRes.getIdCarrelloNotifica();
	}

    @Override
	public SigasPaymentCartNotify retrievePaymentCartNotify(Long id) {
		return paymentCartNotifyRepository.findByIdCarrelloNotifica(id);
	}


	static public PaymentCartVO cart2VO(SigasPaymentCart cart) {
		PaymentCartVO cartItem = new PaymentCartVO();
		
		cartItem.setId(cart.getIdCarrello());
		cartItem.setStatus(""+cart.getFkStatoCarrello());
		cartItem.setPaymentCode(cart.getCodicePagamento());
		cartItem.setPaymentType(cart.getFkTipoPagamento());
		cartItem.setCurrentDate(simpleDate.format(new Date()));
		cartItem.setPayDate(simpleDate.format(cart.getDataPagamento()));
		cartItem.setEmail(cart.getEmail());
		cartItem.setYear(cart.getAnno());
		cartItem.setSubjectName(cart.getDenominazioneVersante());
		cartItem.setSubjectCode(cart.getCodiceAzienda());
		cartItem.setSubjectArea(cart.getSiglaProvincia());
		cartItem.setArea(cart.getSiglaProvincia());
		cartItem.setIdAnag(cart.getFkAnagSoggetto());
		cartItem.setMonth(cart.getMese());
		cartItem.setAmount(String.format("%.2f", cart.getImporto()));
		cartItem.setType(cart.getFkTipoCarrello());
		cartItem.setCodiceFiscalePIva(cart.getCfPiva());
		
		return cartItem;
	}
	
	private void startPaymentPAGOPA(List<SigasPaymentCart> cart_items) {
		
		//CR WS Security
		SigasCParametro oggettoPagamentoPpay = sigasCParametroRepository.findByDescParametro("oggettoPagamentoPpay");
		SigasCParametro codiceFiscaleEnteCreditore = sigasCParametroRepository.findByDescParametro("codiceFiscaleEnteCreditore");
		SigasCParametro wsSecurityIsOn = sigasCParametroRepository.findByDescParametro("ws_security_is_on");
		
		if(wsSecurityIsOn != null && wsSecurityIsOn.getValoreBoolean() != null && wsSecurityIsOn.getValoreBoolean()) {
			SigasCParametro wsUserParam = sigasCParametroRepository.findByDescParametro("ws_user");
			SigasCParametro wsPWDParam = sigasCParametroRepository.findByDescParametro("ws_pwd");
			
			ePayServiceFacade.inserisciListaDiCarico(ePayWsInputMapper.mapPagamentoWsMapper(cart_items,	getTaxCode(),oggettoPagamentoPpay.getValoreString(),codiceFiscaleEnteCreditore.getValoreString()),
													 wsUserParam.getValoreString(),
													 wsPWDParam.getValoreString());
		} else {
			
			ePayServiceFacade.inserisciListaDiCarico(ePayWsInputMapper.mapPagamentoWsMapper(cart_items, getTaxCode(),oggettoPagamentoPpay.getValoreString(),codiceFiscaleEnteCreditore.getValoreString()));
			
		}		
	}

    @Override
	public void sendMail2ConfirmPayment(String iuv) {
    	List<SigasPaymentCart> cart_items = paymentCartRepository.findByIUV(iuv);
    	if(cart_items.size() == 0)
    		return;
    	
    	SigasPaymentCart cart = cart_items.get(0);
    	
		try {
	    	String mailSender; 
			SigasCParametro mittente = sigasCParametroRepository.findByDescParametro("pagamentoPpayMittenteMailConferma");
			if(mittente == null || 
					(mailSender = mittente.getValoreString()) == null ||
					"".equals(mailSender))
				return; // if no sender configured, then skip send mail
			
			String subject = sigasCMessaggiRepository.findByDescChiaveMessaggio("mailOggettoConfermaPagamento").getValoreMessaggio(); 
			String mailBody = sigasCMessaggiRepository.findByDescChiaveMessaggio("mailConfermaPagamento").getValoreMessaggio();
			SigasCParametro mailSmtpHost = sigasCParametroRepository.findByDescParametro("mailSmtpHost");
			SigasCParametro mailSmtpPort = sigasCParametroRepository.findByDescParametro("mailSmtpPort");
			
			Utilities.sendMail(cart.getEmail(), 
					mailSender, 
					replacePaymentTags(cart, subject), 
					replacePaymentTags(cart, mailBody),mailSmtpHost.getValoreString(), mailSmtpPort.getValoreString());
		} catch (Exception e) {
	    	logger.error("sendMail2ConfirmPayment: impossible send mail " + cart.getEmail() + " - " + cart.getCodicePagamento());
		}
	}
    
    private String replacePaymentTags(SigasPaymentCart cart, String str2replace) {
    	str2replace = str2replace.replaceAll("@MESE@", subjectFoRepository.findMonthById(cart.getMese()));
    	str2replace = str2replace.replaceAll("@ANNO@", cart.getAnno());
    	str2replace = str2replace.replaceAll("@DICHIARANTE@", cart.getDenominazioneVersante());
    	str2replace = str2replace.replaceAll("@PIVA@", "");
    	str2replace = str2replace.replaceAll("@CODICEFISCALE@", subjectFoRepository.retrieveCF(cart.getFkUtenteInsert()));
    	str2replace = str2replace.replaceAll("@IDTRANSAZIONE@", cart.getCodicePagamento());
    	str2replace = str2replace.replaceAll("@IUV@", cart.getIuv());
    	str2replace = str2replace.replaceAll("@URLSIGAS@", sigasCParametroRepository.findByDescParametro("pagamentoPpaySigasUrl").getValoreString());
    	
    	return str2replace;
    }

	public CsiLogAuditRepository getCsiLogAuditRepository() {
		return csiLogAuditRepository;
	}

	public void setCsiLogAuditRepository(CsiLogAuditRepository csiLogAuditRepository) {
		this.csiLogAuditRepository = csiLogAuditRepository;
	}

}
