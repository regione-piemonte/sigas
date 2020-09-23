/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.print.PrintException;

import net.sf.jasperreports.engine.JRException;

public interface IUtilsService {

	public byte[] printReportExcel(String codReport, Map<String, Object> jasperParam) throws PrintException, IOException, SQLException, JRException;

	public byte[] printReportWord(String reportName, Map<String, Object> jasperParam) throws PrintException, IOException, SQLException, JRException;
}
