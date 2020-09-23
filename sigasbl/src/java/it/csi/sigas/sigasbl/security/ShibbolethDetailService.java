/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ShibbolethDetailService {
	public UserDetails caricaUtenteDaIdentita(IdentityDetails identitaIride, String irideAPP)
			throws UsernameNotFoundException;

}
