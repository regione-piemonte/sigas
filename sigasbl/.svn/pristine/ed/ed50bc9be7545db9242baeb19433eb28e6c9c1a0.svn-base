/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author riccardo.bova
 * @date 03 mag 2018
 */
public enum ExportReport {
	REPORT_EXCEL_DICHIARAZIONE_CONSUMI("EXCEL_DICHIARAZIONE_CONSUMI", "Report excel per una dichiarazione consumi", "DichiarazioneConsumiExcel.jrxml"),
	REPORT_EXCEL_SOGGETTO("EXCEL_SOGGETTO", "Report excel per un soggetto", "SoggettoExcel.jrxml"),
	REPORT_EXCEL_ELENCO_SOGGETTI("EXCEL_ELENCO_SOGGETTI", "Report excel per l'elenco soggetti", "ElencoSoggettiExcel.jrxml"),
	REPORT_WORD_DETERMINA("WORD_DETERMINA", "Determina di n rimborso", "DeterminaWord.jrxml"),
	REPORT_EXCEL_DICHIARAZIONE_VERSAMENTI("EXCEL_DICHIARAZIONE_VERSAMENTI", "Report excel per l'elenco dei versamenti", "ElencoVeramentiExcel.jrxml"),
	REPORT_EXCEL_DOCUMENTAZIONE("EXCEL_DOCUMENTAZIONE", "Report excel per l'elenco della documentazione", "ElencoDocumentazioneExcel.jrxml"),
	REPORT_EXCEL_ACCERTAMENTI("EXCEL_ACCERTAMENTI", "Report excel per esportare la lista degli accertamenti", "ElencoAccertamentiExcel.jrxml");

	private String codice;
	private String descrizione;
	private String fileName;

	private ExportReport(String codice, String descrizione, String fileName) {
		this.codice = codice;
		this.descrizione = descrizione;
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	static public ExportReport getByCodice(String codice) {
		if (null == codice)
			throw new IllegalArgumentException("codice null");
		for (ExportReport e : ExportReport.values()) {
			if (e.codice.equals(codice))
				return e;
		}
		throw new IllegalArgumentException("codice null");
	}

	static public ExportReport getByFileName(String fileName) {
		if (null == fileName)
			throw new IllegalArgumentException("fileName null");
		for (ExportReport e : ExportReport.values()) {
			if (e.fileName.equals(fileName))
				return e;
		}
		return null;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
