/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoVersamentoRepository;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiVO;
import it.csi.sigas.sigasbl.model.vo.home.SoggettiVO;
import it.csi.sigas.sigasbl.request.home.DownloadAccertamentiReport;
import it.csi.sigas.sigasbl.request.home.DownloadDocumentazioneReport;
import it.csi.sigas.sigasbl.request.home.DownloadReport;
import it.csi.sigas.sigasbl.request.home.DownloadSoggettiReport;
import it.csi.sigas.sigasbl.request.home.DownloadVersamentiReport;
import it.csi.sigas.sigasbl.service.IExportService;
import it.csi.sigas.sigasbl.service.IUtilsService;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ExportServiceImpl implements IExportService {

	@Autowired
	private IUtilsService iUtilsService;
	
	@Autowired
	private SigasTipoVersamentoRepository sigasTipoVersamentoRepository;
	
	@Autowired
	private SigasProvinciaRepository sigasProvinciaRepository;

	protected static Logger logger = Logger.getLogger(IUtilsService.class);

	@Override
	public byte[] getExcel(DownloadReport downloadReport, String reportName) {
		Map<String, Object> jasperParam = null;
		byte[] export = null;

		jasperParam = new HashMap<String, Object>();
		jasperParam.put("anno", downloadReport.getAnno());
		jasperParam.put("id_anag", downloadReport.getId());

		try {
			export = iUtilsService.printReportExcel(reportName, jasperParam);
		} catch (Exception e) {
			logger.error("Eccezione durante la generazione del report excel", e);
			throw new BusinessException(e.getMessage(),  ErrorCodes.BUSSINESS_EXCEPTION);
		}
		return export;
	}
	
	@Override
	public byte[] getExcel(DownloadSoggettiReport downloadSoggettiReport, String reportName) {
		Map<String, Object> jasperParam = null;
		byte[] export = null;

		jasperParam = new HashMap<String, Object>();
		
        
        List<SoggettiVO> soggetti = downloadSoggettiReport.getSoggetti();
        
       
        
        for (SoggettiVO soggetto : soggetti) {
        	StringBuffer allarmeString = new StringBuffer();
        	AllarmiVO allarme = soggetto.getAllarmi();
        	 boolean alreadyAcc = false;
             boolean alreadyCoer = false;
             boolean alreadyDoc = false;
             boolean alreadyNote = false;
             boolean alreadyRavv = false;
             boolean alreadyRett = false;
             boolean alreadyComp = false;
             boolean alreadyRimb = false;
             boolean alreadyScarti = false;
             boolean alreadyVers = false;
        	
        	if (allarme.isAcc() && !alreadyAcc) {
        		allarmeString.append("Acceratamento, ");
        		alreadyAcc = true;
        	}
        	if (allarme.isCoerenza() && !alreadyCoer) {
        		allarmeString.append("Coerenza, ");
        		alreadyAcc = true;
        	}
        	if (allarme.isDoc() && !alreadyDoc) {
        		allarmeString.append("Documenti, ");
        		alreadyDoc = true;
        	}
        	if (allarme.isNote() && !alreadyNote) {
        		allarmeString.append("Note, ");
        		alreadyNote = true;
        	}
        	if (allarme.isRavv() && !alreadyRavv) {
        		allarmeString.append("Ravvedimenti, ");
        		alreadyRavv = true;
        	}
        	if (allarme.isRett() && !alreadyRett) {
        		allarmeString.append("Rettifica, ");
        		alreadyRett = true;
        	}
        	if (allarme.isComp() && !alreadyComp) {
        		allarmeString.append("Compensazione, ");
        		alreadyComp = true;
        	}
        	if (allarme.isRimb() && !alreadyRimb) {
        		allarmeString.append("Rimborsi, ");
        		alreadyRimb = true;
        	}
        	if (allarme.isScarti() && !alreadyScarti) {
        		allarmeString.append("Scarti, ");
        		alreadyScarti = true;
        	}
        	if (allarme.isVers() && !alreadyVers) {
        		allarmeString.append("Versamenti, ");
        		alreadyVers = true;
        	}
        	
        	String allarmeSoggetto = allarmeString.toString();
        	soggetto.setAllReport(allarmeSoggetto);
        }//end for
		
        
        
        /* Convert List to JRBeanCollectionDataSource */
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(soggetti);
        
        jasperParam.put("soggetti", itemsJRBean);
		jasperParam.put("anno", downloadSoggettiReport.getAnno());
		
		try {
			export = iUtilsService.printReportExcel(reportName, jasperParam);
		} catch (Exception e) {
			logger.error("Eccezione durante la generazione del report excel", e);
			throw new BusinessException(e.getMessage(),  ErrorCodes.BUSSINESS_EXCEPTION);
		}
		return export;
	}
	
	
	@Override
	public byte[] getExcel(DownloadVersamentiReport downloadVersamentiReport, String reportName) {
		Map<String, Object> jasperParam = null;
		byte[] export = null;

		jasperParam = new HashMap<String, Object>();
		jasperParam.put("anno", downloadVersamentiReport.getAnno());
		jasperParam.put("id_anag", downloadVersamentiReport.getId_anag());
		jasperParam.put("id_provincia", downloadVersamentiReport.getId_provincia());
		jasperParam.put("id_tipo_versamento", downloadVersamentiReport.getId_tipo_versamento());
		jasperParam.put("mese", downloadVersamentiReport.getMese());
		
		if(downloadVersamentiReport.getId_tipo_versamento()!=null && downloadVersamentiReport.getId_tipo_versamento().intValue()!=0) {
			String descrizioneTipoVersamento = sigasTipoVersamentoRepository.findOne(downloadVersamentiReport.getId_tipo_versamento()).getDenominazione();
			jasperParam.put("tipologiaFiltro", descrizioneTipoVersamento);
		}else {
			jasperParam.put("tipologiaFiltro", null);
		}
		
		
		if(downloadVersamentiReport.getId_provincia()!=null && downloadVersamentiReport.getId_provincia().intValue()!=0) {
			String siglaProvincia = sigasProvinciaRepository.findOne(downloadVersamentiReport.getId_provincia()).getSiglaProvincia();
			jasperParam.put("provinciaFiltro", siglaProvincia);
		}else {
			jasperParam.put("provinciaFiltro", null);
		}
		
		
		try {
			export = iUtilsService.printReportExcel(reportName, jasperParam);
		} catch (Exception e) {
			logger.error("Eccezione durante la generazione del report excel", e);
			throw new BusinessException(e.getMessage(),  ErrorCodes.BUSSINESS_EXCEPTION);
		}
		return export;
	}
	
	@Override
	public byte[] getExcel(DownloadDocumentazioneReport downloadDocumentazioneReport, String reportName) {
		Map<String, Object> jasperParam = null;
		byte[] export = null;

		jasperParam = new HashMap<String, Object>();
		jasperParam.put("anno", "Tutti".equalsIgnoreCase(downloadDocumentazioneReport.getAnno()) ? null:downloadDocumentazioneReport.getAnno());
		jasperParam.put("id_anag", downloadDocumentazioneReport.getId_anag());
		jasperParam.put("id_tipo_comunicazione", downloadDocumentazioneReport.getId_tipo_comunicazione()!=null ? downloadDocumentazioneReport.getId_tipo_comunicazione():0);

		try {
			export = iUtilsService.printReportExcel(reportName, jasperParam);
		} catch (Exception e) {
			logger.error("Eccezione durante la generazione del report excel", e);
			throw new BusinessException(e.getMessage(),  ErrorCodes.BUSSINESS_EXCEPTION);
		}
		return export;
	}
	
	@Override
	public byte[] getWord(DownloadReport downloadReport, String reportName) {
		Map<String, Object> jasperParam = null;
		byte[] export = null;

		jasperParam = new HashMap<String, Object>();
		jasperParam.put("anno", downloadReport.getAnno());
		jasperParam.put("id_anag", downloadReport.getId());
		jasperParam.put("id_rimborso", downloadReport.getIdRimborso());

		try {
			export = iUtilsService.printReportWord(reportName, jasperParam);
		} catch (Exception e) {
			logger.error("Eccezione durante la generazione del report excel", e);
			throw new BusinessException(e.getMessage(),  ErrorCodes.BUSSINESS_EXCEPTION);
		}
		return export;
	}

	@Override
	public byte[] getExcel(DownloadAccertamentiReport downloadAccertamentiReport, String reportName) {
		Map<String, Object> jasperParam = null;
		byte[] export = null;

		jasperParam = new HashMap<String, Object>();
		
		 /* Convert List to JRBeanCollectionDataSource */
        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(downloadAccertamentiReport.getAccertamenti());
		
		jasperParam.put("versamenti", itemsJRBean);
		jasperParam.put("idAnag", downloadAccertamentiReport.getIdAnag());

		try {
			export = iUtilsService.printReportExcel(reportName, jasperParam);
		} catch (Exception e) {
			logger.error("Eccezione durante la generazione del report excel", e);
			throw new BusinessException(e.getMessage(),  ErrorCodes.BUSSINESS_EXCEPTION);
		}
		return export;
	}

}
