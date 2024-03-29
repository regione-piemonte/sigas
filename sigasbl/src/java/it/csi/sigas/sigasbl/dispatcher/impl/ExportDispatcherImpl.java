/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.common.ExportReport;
import it.csi.sigas.sigasbl.dispatcher.IExportDispatcher;
import it.csi.sigas.sigasbl.request.home.DownloadAccertamentiReport;
import it.csi.sigas.sigasbl.request.home.DownloadDettaglioSoggettoReport;
import it.csi.sigas.sigasbl.request.home.DownloadDocumentazioneReport;
import it.csi.sigas.sigasbl.request.home.DownloadReport;
import it.csi.sigas.sigasbl.request.home.DownloadSoggettiReport;
import it.csi.sigas.sigasbl.request.home.DownloadVersamentiReport;
import it.csi.sigas.sigasbl.service.IExportService;

@Component
public class ExportDispatcherImpl implements IExportDispatcher {
	
	@Autowired
	private IExportService ExportService;

	@Override
	public byte[] salvaConsumiPerProvince(DownloadReport downloadReport) {
		return ExportService.getExcel(downloadReport, ExportReport.REPORT_EXCEL_DICHIARAZIONE_CONSUMI.getCodice());
	}
	
	@Override
	public byte[] salvaSoggetto(DownloadDettaglioSoggettoReport downloadReport) {
		return ExportService.getExcel(downloadReport, ExportReport.REPORT_EXCEL_SOGGETTO.getCodice());
	}
	
	@Override
	public byte[] salvaElencoSoggetti(DownloadSoggettiReport downloadSoggettiReport) {
		return ExportService.getExcel(downloadSoggettiReport, ExportReport.REPORT_EXCEL_ELENCO_SOGGETTI.getCodice());
	}
	
	@Override
	public byte[] salvaDetermina(DownloadReport downloadReport) {
		return ExportService.getWord(downloadReport, ExportReport.REPORT_WORD_DETERMINA.getCodice());
	}
	
	@Override
	public byte[] salvaElencoVersamenti(DownloadVersamentiReport downloadVersamentiReport) {
		return ExportService.getExcel(downloadVersamentiReport, ExportReport.REPORT_EXCEL_DICHIARAZIONE_VERSAMENTI.getCodice());
	}
	
	@Override
	public byte[] salvaElencoDocumenti(DownloadDocumentazioneReport downloadDocumentazioneReport) {
		return ExportService.getExcel(downloadDocumentazioneReport, ExportReport.REPORT_EXCEL_DOCUMENTAZIONE.getCodice());
	}

	@Override
	public byte[] salvaElencoAccertamenti(DownloadAccertamentiReport downloadAccertamentiReport) {
		return ExportService.getExcel(downloadAccertamentiReport, ExportReport.REPORT_EXCEL_ACCERTAMENTI.getCodice());
	}
}
