/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import it.csi.sigas.sigasbl.request.home.StorePaymentCartRequest;

public interface IExportPaymentService {

	byte[] getExcel(StorePaymentCartRequest downloadSoggettiReport, String reportName);

}
