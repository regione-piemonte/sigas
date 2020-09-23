/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.EsitoImport;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.dispatcher.IUTFDispatcher;
import it.csi.sigas.sigasbl.model.entity.SigasImportUTF;
import it.csi.sigas.sigasbl.model.vo.importUTF.AnnualitaImportVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.ImportUTFVO;
import it.csi.sigas.sigasbl.service.IUTFService;

@Component
public class UTFDispatcherImpl implements IUTFDispatcher {

	@Autowired
	private IUTFService UTFService;
	
	@Override
	public ImportUTFVO importUTF(MultipartFormDataInput input, String username) {
		
		ImportUTFVO importUTFVO = null;
		SigasImportUTF sigasImportUTF = null;
		
		// Controlla se c'e' stato gia' un import per la stessa annualita' e inizializza import
		sigasImportUTF = UTFService.checkImportUTF(input, username);

		try {
			// import UTF
			importUTFVO = UTFService.importUTF(input, username, sigasImportUTF.getIdImport());	
		} catch (Exception e) {
			sigasImportUTF = UTFService.updateImportState(sigasImportUTF.getIdImport(), EsitoImport.ERROR_DOWNLOAD.getEsitoImport(), 
					e.getMessage() );
			throw new BusinessException(e.getMessage() , ErrorCodes.BUSSINESS_EXCEPTION);
		}
		
		return importUTFVO;
	}
	
	
	
	@Override
	public void populateConsumi(ImportUTFVO importUTFVO) {
		
		SigasImportUTF sigasImportUTF = null;
		
		UTFService.checkStateUTF(importUTFVO.getImportId(), EsitoImport.DOWNLOAD_COMPLETE.getEsitoImport());

		// Controlla se la fase di popolamento nel DB e' completato
		try {
			// Aggiornamento Anagrafica Soggetti e Popolamento tabella sigas_dich_consumi
			sigasImportUTF = UTFService.updateImportState(importUTFVO.getImportId(), EsitoImport.CALCULATE_INPROGRESS.getEsitoImport(), null);			
			UTFService.populateConsumi(importUTFVO.getAnno(), sigasImportUTF.getIdImport());
		} catch (Exception e) {
			sigasImportUTF = UTFService.updateImportState(importUTFVO.getImportId(), EsitoImport.ERROR_CALCULATE.getEsitoImport(), 
					"Lo stato dell'importazione e' inaspettato; il valore atteso era: '"+ EsitoImport.CALCULATE_INPROGRESS.getEsitoImport() + "'" );
			throw new BusinessException("Lo stato dell'importazione e' inaspettato; il valore atteso era: '"+ EsitoImport.CALCULATE_INPROGRESS.getEsitoImport() + "'", ErrorCodes.BUSSINESS_EXCEPTION);
		}
		
				
	}
	
	@Override
	public List<AnnualitaImportVO> ricercaAnnualita() {
		
		return UTFService.ricercaAnnualita();
				
	}
}
