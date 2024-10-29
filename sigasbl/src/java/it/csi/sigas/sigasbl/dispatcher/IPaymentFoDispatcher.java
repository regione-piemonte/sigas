package it.csi.sigas.sigasbl.dispatcher;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.GeneraAvvisoPagamentoResponse;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartNotify;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartRT;
import it.csi.sigas.sigasbl.model.vo.home.PaymentCartVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentRTInfoVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentRedirectVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentSubjectVO;
import it.csi.sigas.sigasbl.model.vo.home.PaymentTypesVO;
import it.csi.sigas.sigasbl.model.vo.home.ReportResponse;
import it.csi.sigas.sigasbl.model.vo.home.RicevutaPagamento;
import it.csi.sigas.sigasbl.model.vo.home.TipoCarrelloVO;
import it.csi.sigas.sigasbl.request.home.SearchSubjectPaymentFoRequest;
import it.csi.sigas.sigasbl.request.home.StorePaymentCartRequest;


public interface IPaymentFoDispatcher {
	
	public List<TipoCarrelloVO> retrievePaymentTypologies();	
	
	public List<String> searchYearsForFoUser();	
	
    public List<String> retrievePaidYears();	
	
	public List<String> getUserAmountsForPrevYear(String idAnag, String year);	
	
	public List<String> getAllPiemonteCounties();	
	
	public List<PaymentSubjectVO> retrieveSubjectsFO(String year);	
	
	public List<PaymentSubjectVO> retrieveFoSubjectsForSearch(String year);	
	
	public List<PaymentSubjectVO> retrieveFoPaymentSubjectsForSearch(SearchSubjectPaymentFoRequest searchRequest);	

	public PaymentSubjectVO retrieveFoPaymentSubjectDetail(String year, String idAnag);	

	public List<PaymentTypesVO> retrievePaymentTypes();
	
	public PaymentCartVO storePaymentCart(StorePaymentCartRequest storePaymentCartRequest);
	
	public PaymentCartVO insertPaymentCart(StorePaymentCartRequest storePaymentCartRequest);

	public void startCartPayment(StorePaymentCartRequest storePaymentCartRequest);
	
	public List<PaymentCartVO> searchCartItems(String year, String subject_name, boolean paidItems, String id);	

	public void savePaymentInfoToCartItems(StorePaymentCartRequest paymentInfo);

	public void deletePaymentCartItem(StorePaymentCartRequest storePaymentCartRequest);	

	public PaymentRedirectVO getPaymentPagoPaRedirectInfo(StorePaymentCartRequest storePaymentCartRequest);

	public PaymentRTInfoVO getPaymentCartRT(String iuv);

	public void insertCartItemIUV(String paymentcode, String iuv, String idPosDebitoria, Integer newstatus);	

	public void storePaymentCartRT(SigasPaymentCartRT cartRT) throws Exception;	

	public void storePaymentCartNotify(SigasPaymentCartNotify cartNotify, String paymentcode);	

	public byte[] saveCartExcel(StorePaymentCartRequest downloadReport);
	
	public byte[] downloadAvvisoPagamento(String iuv);
	
	public byte[] generaAvvisoPagamento(StorePaymentCartRequest storePaymentCartRequest);	
	
	public byte[] downloadRicevutaPagamento(String iuv);
	
	public RicevutaPagamento previewRicevutaPagamento(String iuv);
	
	public PaymentRedirectVO getPaymentPagoPaRedirectUrl(StorePaymentCartRequest storePaymentCartRequest);
	
}
