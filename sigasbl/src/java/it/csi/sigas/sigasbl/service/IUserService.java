/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import it.csi.sigas.sigasbl.model.vo.user.ProfilaturaVO;
import it.csi.sigas.sigasbl.security.UserDetails;

public interface IUserService {

	ProfilaturaVO getProfilatura(UserDetails userDetails);
	
}
