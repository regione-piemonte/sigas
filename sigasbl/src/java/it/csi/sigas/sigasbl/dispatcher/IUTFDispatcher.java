/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.model.vo.importUTF.AnnualitaImportVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.ImportUTFVO;


public interface IUTFDispatcher {

	@PreAuthorize(value = AuthorizationRoles.HOME)
	ImportUTFVO importUTF(MultipartFormDataInput input, String Username);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	void populateConsumi(ImportUTFVO importUTFVO);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AnnualitaImportVO> ricercaAnnualita();

}
