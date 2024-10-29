/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.print.PrintException;
import javax.sql.DataSource;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.model.entity.SigasReport;
import it.csi.sigas.sigasbl.model.repositories.SigasReportRepository;
import it.csi.sigas.sigasbl.service.IUtilsService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@Service
public class UtilsServiceImpl implements IUtilsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SigasReportRepository sigasReportRepository;

	
	@Override
	public byte[] printReportPDF(String codReport, Map<String, Object> jasperParam, Map<String, String> mapSubReport) throws PrintException, IOException, SQLException, JRException {

		if (mapSubReport != null) {
			for (Map.Entry<String, String> entry : mapSubReport.entrySet()) { 
				jasperParam.put(entry.getKey(), compileAndGetJasperFromDatabase(entry.getValue()));
			}
		}
		
		JasperPrint jasperPrint = printJasper(codReport, jasperParam);
		return JasperExportManager.exportReportToPdf(jasperPrint);

	}
	
	private JasperReport compileAndGetJasperFromDatabase(String codReport) throws PrintException, IOException, SQLException, JRException {

		InputStream bais;

		SigasReport sigasReport = sigasReportRepository.findByCodReport(codReport);
		if (null == sigasReport.getJasper()) {
			bais = compile(sigasReport.getJrxml().getBytes(StandardCharsets.UTF_8));
			sigasReport.setJasper(IOUtils.toByteArray(bais));
			sigasReport.setDataUpdate(new Timestamp(new Date().getTime()));
			sigasReport = sigasReportRepository.save(sigasReport);
			bais = null;
		}

		bais = new ByteArrayInputStream(sigasReport.getJasper());

		return (JasperReport) JRLoader.loadObject(bais);
	}

	public byte[] printReportWord(String codReport, Map<String, Object> jasperParam)
			throws PrintException, IOException, SQLException, JRException {

		JasperPrint jasperPrint = printJasper(codReport, jasperParam);

		ByteArrayOutputStream wordReport = new ByteArrayOutputStream();

		// Generazione byte array con report finale
		Exporter exporter = new JRDocxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(wordReport));

		exporter.exportReport();

		wordReport.flush();
		byte[] report = wordReport.toByteArray();
		wordReport.close();
		return report;
	}

	public byte[] printReportExcel(String codReport, Map<String, Object> jasperParam)
			throws PrintException, IOException, SQLException, JRException {

		JasperPrint jasperPrint = printJasper(codReport, jasperParam);

		// Generazione byte array con report finale
		ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));

		SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
		configuration.setOnePagePerSheet(false);
		configuration.setDetectCellType(true);
		configuration.setCollapseRowSpan(false);

		exporter.setConfiguration(configuration);
		exporter.exportReport();

		xlsReport.flush();
		byte[] report = xlsReport.toByteArray();
		xlsReport.close();
		return report;
	}

	private JasperPrint printJasper(String codReport, Map<String, Object> jasperParam) throws PrintException, IOException, SQLException, JRException {

		InputStream bais = null;
		JasperReport jasperReport = null;
		// Datasource per connessione a DB
		Connection connection = null;
		try {
			connection = dataSource.getConnection();

			SigasReport sigasReport = sigasReportRepository.findByCodReport(codReport);			
			if (null == sigasReport.getJasper()) {
				bais = compile(sigasReport.getJrxml().getBytes(StandardCharsets.UTF_8));
				sigasReport.setJasper(IOUtils.toByteArray(bais));
				sigasReport.setDataUpdate(new Timestamp(new Date().getTime()));
				sigasReport = sigasReportRepository.save(sigasReport);
				bais = null;
			}
			bais = new ByteArrayInputStream(sigasReport.getJasper());

			jasperReport = (JasperReport) JRLoader.loadObject(bais);
			bais = null;

			// Generazione report da compilato
			// JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParam, new JREmptyDataSource());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, jasperParam, connection);

			return jasperPrint;
		}
		finally {
			// closing manually DB connection
			if(connection != null) {
				try {
					connection.close();
				}
				catch(SQLException sqlEx) {
					logger.warn("Error in closing DB connection");
				}
			}
		}

	}

	private ByteArrayInputStream compile(byte[] template) throws PrintException {
		ByteArrayInputStream result = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {			
			ByteArrayInputStream bais = new ByteArrayInputStream(template);			
			JasperCompileManager.compileReportToStream(bais, os);
			result = new ByteArrayInputStream(os.toByteArray());
		} catch (JRException e) {
			throw new PrintException("Errore nella creazione del file jasper dal template jrxml", e);
		}
		return result;
	}

}
