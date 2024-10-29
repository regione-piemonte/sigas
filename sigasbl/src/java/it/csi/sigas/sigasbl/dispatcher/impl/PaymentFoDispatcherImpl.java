package it.csi.sigas.sigasbl.dispatcher.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.dispatcher.IPaymentFoDispatcher;
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
import it.csi.sigas.sigasbl.service.IExportPaymentService;
import it.csi.sigas.sigasbl.service.IPaymentFoService;

@Component
public class PaymentFoDispatcherImpl implements IPaymentFoDispatcher {

	@Autowired
	private IPaymentFoService paymentFoService;
	
	@Autowired
	private IExportPaymentService ExportService;

	@Override
	public List<TipoCarrelloVO> retrievePaymentTypologies() {
		return paymentFoService.retrievePaymentTypologies();
	}
	
	@Override
	public List<String> searchYearsForFoUser() {
		return paymentFoService.searchYearsForFoUser();
	}

	@Override
    public List<String> retrievePaidYears() {
		return paymentFoService.retrievePaidYears();
	}
	
	@Override
	public List<String> getAllPiemonteCounties() {
		return paymentFoService.getAllPiemonteCounties();
	}
	
	@Override
	public List<String> getUserAmountsForPrevYear(String idAnag, String year) {
		return paymentFoService.getUserAmountsForPrevYear(idAnag, year);
	}
	
	@Override
	public List<PaymentSubjectVO> retrieveSubjectsFO(String year) {
		return paymentFoService.retrieveSubjectsFO(year);
	}
	
	@Override
	public List<PaymentSubjectVO> retrieveFoSubjectsForSearch(String year) {
		return paymentFoService.retrieveFoSubjectsForSearch(year);
	}
	
	@Override
	public List<PaymentSubjectVO> retrieveFoPaymentSubjectsForSearch(SearchSubjectPaymentFoRequest searchRequest) {
		return paymentFoService.retrieveFoPaymentSubjectsForSearch(searchRequest);
	}
	
	@Override
	public PaymentSubjectVO retrieveFoPaymentSubjectDetail(String year, String idAnag) {
		return paymentFoService.retrieveFoPaymentSubjectDetail(year, idAnag);
	}
	
	/*@Override
	public String searchSubjectPaymentFo(String name, String area) {
		return paymentFoService.searchSubjectPaymentFo(name, area);
	}*/
	
	@Override
	public List<PaymentTypesVO> retrievePaymentTypes() {
		return paymentFoService.retrievePaymentTypes();
	}

	///////////////////////////////////////////////
	// CART SPECIFIC FUNCTIONS
	
	public PaymentCartVO storePaymentCart(StorePaymentCartRequest storePaymentCartRequest) {
		return paymentFoService.storePaymentCart(storePaymentCartRequest);
	}
	
	public PaymentCartVO insertPaymentCart(StorePaymentCartRequest storePaymentCartRequest) {
		return paymentFoService.insertPaymentCart(storePaymentCartRequest);
	}
	
	public void startCartPayment(StorePaymentCartRequest storePaymentCartRequest) {
		paymentFoService.startCartPayment(storePaymentCartRequest);
	}

	public void deletePaymentCartItem(StorePaymentCartRequest storePaymentCartRequest) {
		paymentFoService.deletePaymentCartItem(storePaymentCartRequest);
	}

	public List<PaymentCartVO> searchCartItems(String year, String subject_name, boolean paidItems, String id) {
		return paymentFoService.searchCartItems(year, subject_name, paidItems, id);
	}
	
	public void savePaymentInfoToCartItems(StorePaymentCartRequest paymentInfo) {
		paymentFoService.savePaymentInfoToCartItems(paymentInfo);
	}
	
	public PaymentRedirectVO getPaymentPagoPaRedirectInfo(StorePaymentCartRequest storePaymentCartRequest) {
		return paymentFoService.getPaymentPagoPaRedirectInfo(storePaymentCartRequest);
	}
	
	public PaymentRTInfoVO getPaymentCartRT(String iuv) {
		return paymentFoService.getPaymentCartRT(iuv);
	}

	public void storePaymentCartNotify(SigasPaymentCartNotify cartNotify, String paymentcode) {
		paymentFoService.storePaymentCartNotify(cartNotify, paymentcode);
	}
	
	public void storePaymentCartRT(SigasPaymentCartRT cartRT) throws Exception {
		paymentFoService.storePaymentCartRT(cartRT);
	}

	public void insertCartItemIUV(String paymentcode, String iuv, String idPosDebitoria, Integer newstatus) {
		paymentFoService.insertCartItemIUV(paymentcode, iuv, idPosDebitoria, newstatus);
	}

	@Override
	public byte[] saveCartExcel(StorePaymentCartRequest downloadReport) {
		return ExportService.getExcel(downloadReport, "EXCEL_CARRELLO_PAGAMENTI");
	}
	
	@Override
	public byte[] downloadAvvisoPagamento(String iuv) {
		return paymentFoService.downloadAvvisoPagamento(iuv);
	}
	
	@Override
	public byte[] generaAvvisoPagamento(StorePaymentCartRequest storePaymentCartRequest) {
		return paymentFoService.generaAvvisoPagamento(storePaymentCartRequest);
	}
	
	@Override
	public byte[] downloadRicevutaPagamento(String iuv) {
		return paymentFoService.downloadRicevutaPagamento(iuv);
	}
	
	@Override
	public RicevutaPagamento previewRicevutaPagamento(String iuv) {
		return paymentFoService.previewRicevutaPagamento(iuv);
	}
	
	@Override
	public PaymentRedirectVO getPaymentPagoPaRedirectUrl(StorePaymentCartRequest storePaymentCartRequest) {
		return paymentFoService.getPaymentPagoPaRedirectUrl(storePaymentCartRequest);
	}	
}
