/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.dispatcher.IUserDispatcher;
import it.csi.sigas.sigasbl.model.vo.user.ProfilaturaVO;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.service.IUserService;

@Component
public class UserDispatcherImpl implements IUserDispatcher {

	@Autowired
	private IUserService userService;
	
	@Override
	public ProfilaturaVO getProfilatura(UserDetails userDetails) {
		return userService.getProfilatura(userDetails);
	}
	
}
