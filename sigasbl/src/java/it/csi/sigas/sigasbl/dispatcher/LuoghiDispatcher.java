/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.model.vo.luoghi.ComuneVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;

public interface LuoghiDispatcher {


	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<ProvinciaVO> getAllProvince();
//
//	@PreAuthorize(value = AuthorizationRoles.HOME)
//	public List<ComuneVO> getAllComuni();
//
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<ComuneVO> getComuniByIdProvincia(Long idProvinciaSelezionata);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public ProvinciaVO getProvinciaBySigla(String siglaProvncia);
}
