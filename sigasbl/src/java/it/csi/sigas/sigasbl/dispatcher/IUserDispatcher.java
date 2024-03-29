/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.model.vo.user.ProfilaturaVO;
import it.csi.sigas.sigasbl.security.UserDetails;


public interface IUserDispatcher {

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public ProfilaturaVO getProfilatura(UserDetails userDetails);

}
