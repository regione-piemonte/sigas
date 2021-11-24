package it.csi.sigas.sigasbl.service;

import java.util.List;

import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartNotify;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartRT;
import it.csi.sigas.sigasbl.model.vo.home.PaymentCartVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentRTInfoVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentRedirectVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentSubjectVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentTypesVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoCarrelloVO;
import it.csi.sigas.sigasbl.request.home.SearchSubjectPaymentFoRequest;
import it.csi.sigas.sigasbl.request.home.StorePaymentCartRequest;

public interface IPaymentFoService {

	///////////////////////////////////////////////
	// GENERIC FUNZIONS
	public List<TipoCarrelloVO> retrievePaymentTypologies();
	
	public List<String> searchYearsForFoUser();
	
    public List<String> retrievePaidYears();
	
    public List<String> getAllPiemonteCounties();
    
	public List<String> getUserAmountsForPrevYear(String idAnag, String year);

	public List<PaymentSubjectVO> retrieveSubjectsFO(String year);
	
	public List<PaymentSubjectVO> retrieveFoSubjectsForSearch(String year);
	
	public List<PaymentSubjectVO> retrieveFoPaymentSubjectsForSearch(SearchSubjectPaymentFoRequest searchRequest);
	
	public PaymentSubjectVO retrieveFoPaymentSubjectDetail(String year, String idAnag);

	//public String searchSubjectPaymentFo(String name, String area);
	
	public List<PaymentTypesVO> retrievePaymentTypes();

	///////////////////////////////////////////////
	// CART SPECIFIC FUNCTIONS
	
	public PaymentCartVO storePaymentCart(StorePaymentCartRequest storePaymentCartRequest);
	
	public void startCartPayment(StorePaymentCartRequest storePaymentCartRequest);

	public void deletePaymentCartItem(StorePaymentCartRequest storePaymentCartRequest);
	
	public List<PaymentCartVO> searchCartItems(String year, String subject_name, boolean paidItems, String id);
	
	public void savePaymentInfoToCartItems(StorePaymentCartRequest paymentInfo);
	
	public void insertCartItemIUV(String paymentcode, String iuv, String idPosDebitoria, Integer newstatus);
	
	public SigasPaymentCartRT retrievePaymentCartRT(Long id);
	
	public SigasPaymentCartNotify retrievePaymentCartNotify(Long id);

	public PaymentRedirectVO getPaymentPagoPaRedirectInfo(StorePaymentCartRequest storePaymentCartRequest);
	
	public PaymentRTInfoVO getPaymentCartRT(String iuv);
	
	public void storePaymentCartRT(SigasPaymentCartRT cartRT) throws Exception;
	
	public Integer storePaymentCartNotify(SigasPaymentCartNotify cartNotify, String iuv);
	
	public void sendMail2ConfirmPayment(String paymentcode);
}
