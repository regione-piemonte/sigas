/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.model.vo.user.ProfilaturaVO;
import it.csi.sigas.sigasbl.model.vo.user.ServizioVO;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Override
	public ProfilaturaVO getProfilatura(UserDetails userDetails) {
		ProfilaturaVO profilatura = new ProfilaturaVO();
		List<String> authorityVOlist = new ArrayList<String>();
		List<ServizioVO> serviziVOList = new ArrayList<ServizioVO>();

		Collection<GrantedAuthority> auth = userDetails.getAuthorities();
		for (GrantedAuthority grant : auth) {
			authorityVOlist.add(grant.getAuthority());
		}
		
		profilatura.setServiziAbilitati(serviziVOList);
		profilatura.setUseCase(authorityVOlist);
		profilatura.setCodiceFiscaleUtente(StringUtils.trim(userDetails.getIdentita().getCodFiscale()));
		profilatura.setNome(StringUtils.trim(userDetails.getIdentita().getNome()));
		profilatura.setCognome(StringUtils.trim(userDetails.getIdentita().getCognome()));
		
		return profilatura;
	}

    
}
