package it.csi.sigas.sigasbl.rest.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.dispatcher.IPaymentFoDispatcher;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.GeneraAvvisoPagamentoResponse;
import it.csi.sigas.sigasbl.model.repositories.SigasCMessaggiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentCartVO;
import it.csi.sigas.sigasbl.model.vo.home.UtilityCtrlVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentRTInfoVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentRedirectVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentSubjectVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentTypesVO;
import it.csi.sigas.sigasbl.model.vo.home.ReportResponse;
import it.csi.sigas.sigasbl.model.vo.home.RicevutaPagamento;
import it.csi.sigas.sigasbl.model.vo.home.TipoCarrelloVO;
import it.csi.sigas.sigasbl.request.home.RicercaConsumiRequest;
import it.csi.sigas.sigasbl.request.home.SearchSubjectPaymentFoRequest;
import it.csi.sigas.sigasbl.request.home.StorePaymentCartRequest;
import it.csi.sigas.sigasbl.rest.api.IPaymentFoApi;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;

@Service
public class PaynentFoApiImpl extends SpringSupportedResource implements IPaymentFoApi {
    
    @Autowired
    private IPaymentFoDispatcher payDispatcher;
    
    @Autowired
    private SigasCParametroRepository sigasCParametroRepository;
    
    @Autowired
    private SigasCMessaggiRepository sigasCMessaggiRepository;
    
    public PaynentFoApiImpl() {
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
    public Response searchYearsForFoUser() {
    	logger.info("START: searchYearsForFoUser");
    	List<String> ricercaAnnualitaList = this.payDispatcher.searchYearsForFoUser();
		
    	logger.info("END: searchYearsForFoUser");
        return Response.ok(new ResponseVO<List<String>>(Esito.SUCCESS, ricercaAnnualitaList)).build();
    }
	
	@Override
    public Response retrievePaidYears() {
    	logger.info("START: retrievePaidYears");
    	List<String> ricercaAnnualitaList = this.payDispatcher.retrievePaidYears();
		
    	logger.info("END: retrievePaidYears");
        return Response.ok(new ResponseVO<List<String>>(Esito.SUCCESS, ricercaAnnualitaList)).build();
    }
	
	@Override
	public Response getAllPiemonteCounties() {
    	logger.info("START: getAllPiemonteCounties");

    	List<String> list = this.payDispatcher.getAllPiemonteCounties();
		
    	logger.info("END: getAllPiemonteCounties");
        return Response.ok(new ResponseVO<List<String>>(Esito.SUCCESS, list)).build();
	}
	
	@Override
    public Response getUserAmountsForPrevYear(String idAnag, String year) {
    	logger.info("START: getUserAmountsForPrevYear");

    	List<String> list = this.payDispatcher.getUserAmountsForPrevYear(idAnag, year);
		
    	logger.info("END: getUserAmountsForPrevYear");
        return Response.ok(new ResponseVO<List<String>>(Esito.SUCCESS, list)).build();
	}   
	

    @Override
    public Response retrieveSubjectsForFoUser(RicercaConsumiRequest ricercaSoggettiUtente) {
    	logger.info("START: retrieveSubjectsForFoUser");
    	List<PaymentSubjectVO> ricercaConsumiList = this.payDispatcher.retrieveSubjectsFO(ricercaSoggettiUtente.getAnno());
		
    	logger.info("END: retrieveSubjectsForFoUser");
        return Response.ok(new ResponseVO<List<PaymentSubjectVO>>(Esito.SUCCESS, ricercaConsumiList)).build();
    }
    
    @Override
    public Response retrieveSubjectPaymentFo(SearchSubjectPaymentFoRequest subject) {
        logger.info("START: retrieveSubjectPaymentFo");        
    	
    	String idAnag = subject.getSubjectId();
    	String year = subject.getYear();
    	String name = subject.getSubjectName();
    	String area = subject.getArea();
    	
    	List<PaymentSubjectVO> subjects = new ArrayList<PaymentSubjectVO>();
        if(idAnag != null && idAnag.length() > 0) {
	    	PaymentSubjectVO subjectIdAnag = this.payDispatcher.retrieveFoPaymentSubjectDetail(year, idAnag);
	    	subjects.add(subjectIdAnag);
        }
        else {
        	subjects = this.payDispatcher.retrieveSubjectsFO(year);        	
        }
        
    	logger.info("END: retrieveSubjectPaymentFo");
    	
        return Response.ok(new ResponseVO<List<PaymentSubjectVO>>(Esito.SUCCESS, subjects)).build();
    }

    @Override				// search subject
    public Response retrieveFoSubjectsForSearch(RicercaConsumiRequest ricercaSoggettiUtente) {
    	logger.info("START: retrieveFoSubjectsForSearch");
    	List<PaymentSubjectVO> subjects = this.payDispatcher.retrieveFoSubjectsForSearch(ricercaSoggettiUtente.getAnno());
		
    	logger.info("END: retrieveFoSubjectsForSearch");
        return Response.ok(new ResponseVO<List<PaymentSubjectVO>>(Esito.SUCCESS, subjects)).build();
    }
    
    @Override				// search results
    public Response retrieveFoPaymentSubjectsForSearch(SearchSubjectPaymentFoRequest searchRequest) {
        logger.info("START: retrieveFoPaymentSubjectsForSearch");
    	List<PaymentSubjectVO> subjects = this.payDispatcher.retrieveFoPaymentSubjectsForSearch(searchRequest);
    	
    	logger.info("END: retrieveFoPaymentSubjectsForSearch");
        return Response.ok(new ResponseVO<List<PaymentSubjectVO>>(Esito.SUCCESS, subjects)).build();
    }
    
    @Override				// get subject infos
    public Response retrieveFoPaymentSubjectDetail(String year, String idAnag) {
        logger.info("START: retrieveFoPaymentSubjectDetail");
        PaymentSubjectVO subject = this.payDispatcher.retrieveFoPaymentSubjectDetail(year, idAnag); 
    	
    	logger.info("END: retrieveFoPaymentSubjectDetail");
        return Response.ok(new ResponseVO<PaymentSubjectVO>(Esito.SUCCESS, subject)).build();
    }
    
    @Override
    public Response retrievePaymentTypes() {
		logger.info("START: retrievePaymentTypes");
	   	
    	List<TipoCarrelloVO> paymentTypesList = payDispatcher.retrievePaymentTypologies();
		
    	logger.info("END: retrievePaymentTypes");
        return Response.ok(new ResponseVO<List<TipoCarrelloVO>>(Esito.SUCCESS, paymentTypesList)).build();
    }
    
    @Override
    public Response retrievePaymentMethods() {
		logger.info("START: retrievePaymentMethods");
	   	
    	List<PaymentTypesVO> list = payDispatcher.retrievePaymentTypes();
		
    	logger.info("END: retrievePaymentMethods");
        return Response.ok(new ResponseVO<List<PaymentTypesVO>>(Esito.SUCCESS, list)).build();
    }
    
    @Override
    public Response storePaymentCart(StorePaymentCartRequest storePaymentCartRequest) {
		logger.info("START: storePaymentCart");
		
		PaymentCartVO cart = this.payDispatcher.storePaymentCart(storePaymentCartRequest);
		logger.info("END: storePaymentCart");
        return Response.ok(new ResponseVO<PaymentCartVO>(Esito.SUCCESS, cart)).build();
    }
    
    @Override
    public Response insertPaymentCart(StorePaymentCartRequest storePaymentCartRequest) {
    	logger.info("START: insertPaymentCart");
		
		PaymentCartVO cart = this.payDispatcher.insertPaymentCart(storePaymentCartRequest);
		
		logger.info("END: insertPaymentCart");
        return Response.ok(new ResponseVO<PaymentCartVO>(Esito.SUCCESS, cart)).build();
    	
    };

    @Override
	public Response startCartPayment(StorePaymentCartRequest storePaymentCartRequest) {
		logger.info("START: startCartPayment");
		
    	payDispatcher.startCartPayment(storePaymentCartRequest);
		logger.info("END: startCartPayment");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

    @Override
	public Response searchCartItems(SearchSubjectPaymentFoRequest request) {
        logger.info("START: searchCartItems");
        
    	List<PaymentCartVO> res = payDispatcher.searchCartItems(request.getYear(), request.getSubjectName(), false, request.getId());
    	logger.info("END: searchCartItems");
        return Response.ok(new ResponseVO<List<PaymentCartVO>>(Esito.SUCCESS, res)).build();
    }
    
    @Override
	public Response searchPaidCartItems(SearchSubjectPaymentFoRequest request) {
        logger.info("START: searchCartItems");
        
    	List<PaymentCartVO> res = payDispatcher.searchCartItems(request.getYear(), request.getSubjectName(), true, request.getId());
    	logger.info("END: searchCartItems");
        return Response.ok(new ResponseVO<List<PaymentCartVO>>(Esito.SUCCESS, res)).build();
    }
    
    @Override
    public Response deletePaymentCartItem(StorePaymentCartRequest paymentInfo) {
        logger.info("START: deletePaymentCartItem");
    	
    	payDispatcher.deletePaymentCartItem(paymentInfo);
    	logger.info("END: deletePaymentCartItem");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
    }
    
    @Override
    public Response savePaymentInfoToCartItems(StorePaymentCartRequest paymentInfo/*, List<StorePaymentCartRequest> cartItems*/) {
        logger.info("START: storePaymentAllCartItems");
        
    	payDispatcher.savePaymentInfoToCartItems(paymentInfo);
    	logger.info("END: searchCartItems");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
    }

    @Override
	public Response getPaymentPagoPaRedirectInfo(StorePaymentCartRequest storePaymentCartRequest) {
        logger.info("START: getPaymentPagoPaRedirectInfo");
        
        PaymentRedirectVO res = payDispatcher.getPaymentPagoPaRedirectInfo(storePaymentCartRequest);
    	logger.info("END: getPaymentPagoPaRedirectInfo");
        return Response.ok(new ResponseVO<PaymentRedirectVO>(Esito.SUCCESS, res)).build();
	}
	
    @Override
	public Response getPaymentCartRT(String iuv) {
        logger.info("START: getPaymentCartRT");
        
        PaymentRTInfoVO res = payDispatcher.getPaymentCartRT(iuv);
    	logger.info("END: getPaymentCartRT");
        return Response.ok(new ResponseVO<PaymentRTInfoVO>(Esito.SUCCESS, res)).build();
    }

	@Override
	public Response savePaymentCart(StorePaymentCartRequest storePaymentCartRequest) {
		logger.info("START: savePaymentCart");
		
		byte[] file = this.payDispatcher.saveCartExcel(storePaymentCartRequest);
    	logger.info("END: savePaymentCart");
    	return Response.ok().entity(file).build();
	}

	@Override
	public Response downloadAvvisoPagamento(String iuv) {
		logger.info("START: downloadAvvisoPagamento");
				
		byte[] output = this.payDispatcher.downloadAvvisoPagamento(iuv);
    	logger.info("END: downloadAvvisoPagamento");
    	return Response.ok().entity(output).build();
	}
	
	@Override
	public Response generaAvvisoPagamento(StorePaymentCartRequest storePaymentCartRequest) {
		logger.info("START: generaAvvisoPagamento");
				
		byte[] output = this.payDispatcher.generaAvvisoPagamento(storePaymentCartRequest);
    	logger.info("END: generaAvvisoPagamento");
    	return Response.ok().entity(output).build();
	}	
	
	@Override
	public Response downloadRicevutaPagamento(String iuv) {
		logger.info("START: downloadRicevutaPagamento");		
		
		byte[] output = this.payDispatcher.downloadRicevutaPagamento(iuv);
		
    	logger.info("END: downloadRicevutaPagamento");
    	return Response.ok().entity(output).build();
	}

	@Override
	public Response previewRicevutaPagamento(String iuv) {
		logger.info("START: previewRicevutaPagamento");		
		
		RicevutaPagamento previewPagmentoResponse = this.payDispatcher.previewRicevutaPagamento(iuv);
    	logger.info("END: previewRicevutaPagamento");
    	return Response.ok().entity(previewPagmentoResponse).build();
	}
	
	@Override
	public Response getPaymentPagoPaRedirectUrl(StorePaymentCartRequest storePaymentCartRequest) {
		logger.info("START: getPaymentPagoPaRedirectUrl");		
		
		PaymentRedirectVO res = payDispatcher.getPaymentPagoPaRedirectUrl(storePaymentCartRequest);
    	logger.info("END: getPaymentPagoPaRedirectUrl");
    	return Response.ok().entity(res).build();
	}
    
}
