/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.sigas.sigasbl.model.entity.SigasImportUTF;
import it.csi.sigas.sigasbl.model.vo.importUTF.AnnualitaImportVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.ImportUTFVO;

public interface IUTFService{

	ImportUTFVO importUTF(MultipartFormDataInput input, String username, long importId);
	void populateConsumi(String annualita, long importId);
	List<AnnualitaImportVO> ricercaAnnualita();
	SigasImportUTF updateImportState(long importId, int stato, String errorMessage);
	SigasImportUTF checkImportUTF(MultipartFormDataInput input, String username);
	boolean checkStateUTF(long importId, int esito);
}
