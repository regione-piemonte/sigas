/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.model.vo.home.UtilityCtrlVO;


public interface IUtilityDispatcher {

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public UtilityCtrlVO retriveMessageAndParameters(String descParametro, String descChiaveMessaggio);

}
