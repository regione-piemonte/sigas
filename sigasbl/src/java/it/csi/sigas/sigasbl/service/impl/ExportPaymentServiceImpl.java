/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentCartRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentSubjectFORepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentTypesRepository;
import it.csi.sigas.sigasbl.model.vo.home.PaymentCartVO;
import it.csi.sigas.sigasbl.request.home.StorePaymentCartRequest;
import it.csi.sigas.sigasbl.service.IExportPaymentService;
import it.csi.sigas.sigasbl.service.IUtilsService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;

@Service
public class ExportPaymentServiceImpl implements IExportPaymentService {

	@Autowired
	private IUtilsService iUtilsService;
	
	@Autowired
	private SigasPaymentCartRepository sigasPaymentCartRepository;
	
	@Autowired
	private SigasPaymentTypesRepository sigasPaymentTypesRepository;
	
	@Autowired
	SigasPaymentSubjectFORepository sigasPaymentSubjectFORepository;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;
	
	protected static Logger logger = Logger.getLogger(IUtilsService.class);

	@Override
	public byte[] getExcel(StorePaymentCartRequest cartRequest, String reportName) {
		Map<String, Object> jasperParam = null;
		byte[] export = null;
		
		List<String> listIdCarrello= new ArrayList<>();
		
		jasperParam = new HashMap<String, Object>();
		
        List<PaymentCartVO> carrello = new ArrayList<PaymentCartVO>();
		List<SigasPaymentCart> cartItems = sigasPaymentCartRepository.findByCodicePagamento(cartRequest.getPaymentCode());
		for(SigasPaymentCart cart : cartItems) {
			PaymentCartVO cartVo = PaymentFoServiceImpl.cart2VO(cart);

			if(cart.getFkTipoPagamento() != null)
				cartVo.setTypeDesc(sigasPaymentTypesRepository.findTipoVersamento(cart.getFkTipoCarrello()));
			
			if(cart.getMese() != null)
				cartVo.setMonthDesc(sigasPaymentSubjectFORepository.findMonthById(cart.getMese()));
			
			carrello.add(cartVo);
			listIdCarrello.add(String.valueOf(cart.getIdCarrello()));
		}
		
        /* Convert List to JRBeanCollectionDataSource */
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(carrello);
        
        
        jasperParam.put("soggetti", itemsJRBean);
		jasperParam.put("anno", cartRequest.getYear());
		if(carrello.get(0)!=null) {
			jasperParam.put("denominazioneAzienda", carrello.get(0).getSubjectName());
		} else {
			jasperParam.put("denominazioneAzienda", cartRequest.getSubjectName());
		}		
		
		try {
			export = iUtilsService.printReportExcel(reportName, jasperParam);
		} catch (Exception e) {
			logger.error("Eccezione durante la generazione del report excel", e);
			throw new BusinessException(e.getMessage(),  ErrorCodes.BUSSINESS_EXCEPTION);
		}
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository, "SCARICA REPORT EXCEL PAGAMENTI", "sigas_carrello_pagamenti",String.join("_", listIdCarrello));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
			csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());

		return export;
	}
	

}
