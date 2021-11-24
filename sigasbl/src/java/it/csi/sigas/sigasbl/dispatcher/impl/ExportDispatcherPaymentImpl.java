/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.dispatcher.IExportDispatcherPayment;
import it.csi.sigas.sigasbl.request.home.StorePaymentCartRequest;
import it.csi.sigas.sigasbl.service.IExportPaymentService;

@Component
public class ExportDispatcherPaymentImpl implements IExportDispatcherPayment {
	
	@Autowired
	private IExportPaymentService ExportService;

	@Override
	public byte[] saveCartExcel(StorePaymentCartRequest downloadReport) {
		return ExportService.getExcel(downloadReport, "EXCEL_CARRELLO_PAGAMENTI");
	}
	
}
