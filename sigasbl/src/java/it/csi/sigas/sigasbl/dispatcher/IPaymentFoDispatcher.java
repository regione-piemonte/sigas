package it.csi.sigas.sigasbl.dispatcher;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
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


public interface IPaymentFoDispatcher {

	//@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<TipoCarrelloVO> retrievePaymentTypologies();
	
	//@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<String> searchYearsForFoUser();
	
	//@PreAuthorize(value = AuthorizationRoles.HOME)
    public List<String> retrievePaidYears();
	
	//@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<String> getUserAmountsForPrevYear(String idAnag, String year);
	
	//@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<String> getAllPiemonteCounties();
	
	//@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<PaymentSubjectVO> retrieveSubjectsFO(String year);
	
	//@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<PaymentSubjectVO> retrieveFoSubjectsForSearch(String year);
	
	//@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<PaymentSubjectVO> retrieveFoPaymentSubjectsForSearch(SearchSubjectPaymentFoRequest searchRequest);
	
//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public PaymentSubjectVO retrieveFoPaymentSubjectDetail(String year, String idAnag);
	
	//@PreAuthorize(value = AuthorizationRoles.HOME_FO)
	//String searchSubjectPaymentFo(String name, String area);

//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<PaymentTypesVO> retrievePaymentTypes();

	//@PreAuthorize(value = AuthorizationRoles.HOME)
	public PaymentCartVO storePaymentCart(StorePaymentCartRequest storePaymentCartRequest);
	
//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void startCartPayment(StorePaymentCartRequest storePaymentCartRequest);

	//@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<PaymentCartVO> searchCartItems(String year, String subject_name, boolean paidItems, String id);
	
//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void savePaymentInfoToCartItems(StorePaymentCartRequest paymentInfo);

//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void deletePaymentCartItem(StorePaymentCartRequest storePaymentCartRequest);
	
//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public PaymentRedirectVO getPaymentPagoPaRedirectInfo(StorePaymentCartRequest storePaymentCartRequest);
	
//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public PaymentRTInfoVO getPaymentCartRT(String iuv);

//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void insertCartItemIUV(String paymentcode, String iuv, String idPosDebitoria, Integer newstatus);
	
//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void storePaymentCartRT(SigasPaymentCartRT cartRT) throws Exception;
	
//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void storePaymentCartNotify(SigasPaymentCartNotify cartNotify, String paymentcode);
	
//	@PreAuthorize(value = AuthorizationRoles.HOME)
	public byte[] saveCartExcel(StorePaymentCartRequest downloadReport);
	
}
