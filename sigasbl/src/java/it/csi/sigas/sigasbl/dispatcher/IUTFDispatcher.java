/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.model.vo.home.ConsumiPrVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.AnnualitaImportVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.ImportUTFVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.UTFStandaloneEntitySoggettiMacroReportVO;
import it.csi.sigas.sigasbl.request.utf.UTFConfermaDichiarazioneRequest;
import it.csi.sigas.sigasbl.request.utf.UTFConfermaSoggettoDichiarazioneRequest;
import it.csi.sigas.sigasbl.request.utf.UTFSoggettiMacroReportByIdReportRequest;


public interface IUTFDispatcher {

	@PreAuthorize(value = AuthorizationRoles.HOME)
	ImportUTFVO importUTF(MultipartFormDataInput input, String Username);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	void populateConsumi(ImportUTFVO importUTFVO);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AnnualitaImportVO> ricercaAnnualita();
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<ConsumiPrVO> getUTFReportByIdImport(Long idImport, Integer annualita);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<ConsumiPrVO> getUTFReportDettaglioSoggettiByIdImport(Long idImport);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<ConsumiPrVO> getUTFReportDettaglioSoggettiByIdImportIdAnag(Long idImport, Long idAnag);	
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<ImportUTFVO> getImportUTFListByAnno(Long anno);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	void confermaSoggettoDichiarazioniUTF(UTFConfermaSoggettoDichiarazioneRequest utfConfermaSoggettoDichiarazioneRequest);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	void confermaDichiarazioniUTF(UTFConfermaDichiarazioneRequest utfConfermaDichiarazioneRequest);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<UTFStandaloneEntitySoggettiMacroReportVO> 
	getUTFSoggettiMacroReportByIdReport(UTFSoggettiMacroReportByIdReportRequest UTFStandaloneEntitySoggettiMacroReportRequest);
}
