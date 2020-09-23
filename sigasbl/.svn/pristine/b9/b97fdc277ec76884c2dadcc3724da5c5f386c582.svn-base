/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import it.csi.sigas.sigasbl.request.home.DownloadAccertamentiReport;
import it.csi.sigas.sigasbl.request.home.DownloadDocumentazioneReport;
import it.csi.sigas.sigasbl.request.home.DownloadReport;
import it.csi.sigas.sigasbl.request.home.DownloadSoggettiReport;
import it.csi.sigas.sigasbl.request.home.DownloadVersamentiReport;

public interface IExportService {

	byte[] getExcel(DownloadSoggettiReport downloadSoggettiReport, String reportName);

	byte[] getWord(DownloadReport downloadReport, String reportName);

	byte[] getExcel(DownloadVersamentiReport downloadVersamentiReport, String reportName);

	byte[] getExcel(DownloadDocumentazioneReport downloadDocumentazioneReport, String reportName);

	byte[] getExcel(DownloadAccertamentiReport downloadAccertamentiReport, String reportName);

	byte[] getExcel(DownloadReport downloadReport, String reportName);

}
