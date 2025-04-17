/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.Roles;
import it.csi.sigas.sigasbl.facade.IrideServFacade;
import it.csi.sigas.sigasbl.integration.iride.Application;
import it.csi.sigas.sigasbl.integration.iride.Ruolo;
import it.csi.sigas.sigasbl.model.entity.SigasCMessaggi;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.entity.SigasUtenti;
import it.csi.sigas.sigasbl.model.repositories.SigasCMessaggiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasUtentiRepository;
import it.csi.sigas.sigasbl.model.vo.user.ProfilaturaVO;
import it.csi.sigas.sigasbl.model.vo.user.ServizioVO;
import it.csi.sigas.sigasbl.rest.SpringApplicationContextHelper;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	

	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	@Autowired
	private SigasUtentiRepository sigasUtentiRepository;
	
	@Autowired
	private SigasCMessaggiRepository sigasCMessaggiRepository;
	
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
		
		
		Boolean isPrivato = Boolean.FALSE;
		Boolean isUtenteRegionale = Boolean.FALSE;
		Boolean privatoNonAccareditato = Boolean.FALSE;
		
		
		IrideServFacade irideServFacade = (IrideServFacade) SpringApplicationContextHelper.getBean("irideServFacadeImpl");
		Ruolo[] ruoli = null;
		try {
			/* <[REPLACE_HERE]> */
			SigasCParametro irideAppId = sigasCParametroRepository.findByDescParametro(Constants.irideAppId);
			ruoli = irideServFacade.findRuoliForPersonaInApplication(userDetails.getIdentita(), new Application(irideAppId.getValoreString()));
			/* <[/REPLACE_HERE]> */
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		SigasCParametro operatoreFO = sigasCParametroRepository.findByDescParametro(Constants.operatoreFO);
		SigasCParametro operatoreBO = sigasCParametroRepository.findByDescParametro(Constants.operatoreBO);
		String ruolis="";
//		if(ruoli!=null && ruoli.length>0) {
//			for(int i=0;i<ruoli.length;i++) {
//				if (ruoli[i].getCodiceRuolo().equalsIgnoreCase(Roles.OPERATORE))
//					isPrivato = Boolean.TRUE;
//				if (ruoli[i].getCodiceRuolo().equalsIgnoreCase(Roles.UTENTE_REGIONE) || ruoli[i].getCodiceRuolo().equalsIgnoreCase(Roles.UTENTE_REGIONE_ACCR) )
//					isUtenteRegionale = Boolean.TRUE;
//				
//				ruolis += ruoli[i].getCodiceRuolo() + "@" +ruoli[i].getCodiceDominio() + " ";
//			}
//		}else {
//			privatoNonAccareditato = Boolean.TRUE;
//		}
		
		
		
		if(ruoli!=null && ruoli.length>0) {
			for(int i=0;i<ruoli.length;i++) {
				if (operatoreFO.getValoreString().toUpperCase().contains(ruoli[i].getCodiceRuolo().toUpperCase()))
					isPrivato = Boolean.TRUE;
				if (operatoreBO.getValoreString().toUpperCase().contains(ruoli[i].getCodiceRuolo().toUpperCase()))
					isUtenteRegionale = Boolean.TRUE;
				
				ruolis += ruoli[i].getCodiceRuolo() + "@" +ruoli[i].getCodiceDominio() + " ";
			}
		}else {
			privatoNonAccareditato = Boolean.TRUE;
		}
		

		List<SigasUtenti> utenteTrovato = sigasUtentiRepository.findByCodiceFiscale(StringUtils.trim(userDetails.getIdentita().getCodFiscale()));

		
		String messaggioWarning = "";
		String levelMessage = "";
		Boolean showMessage = false;
		if(utenteTrovato!=null && !utenteTrovato.isEmpty() && privatoNonAccareditato) {
			SigasCMessaggi sigasMessaggio = sigasCMessaggiRepository.findByDescChiaveMessaggio("utenteFoAttesaRegistrazioneIride");
			messaggioWarning = sigasMessaggio.getValoreMessaggio();
			levelMessage = sigasMessaggio.getLivelloMessaggio();
			showMessage = true;
		}
		
		profilatura.setAbilitaUtenteRegione(isUtenteRegionale);
//		isPrivato = Boolean.TRUE;
		profilatura.setAbilitaPrivato(isPrivato);
//		privatoNonAccareditato = Boolean.TRUE;
		profilatura.setPrivatoNonAccareditato(privatoNonAccareditato);
		profilatura.setRuoli(ruolis);
		profilatura.setMessaggioWarning(messaggioWarning);
		profilatura.setLevelMessage(levelMessage);
		profilatura.setShowMessage(showMessage);
		
		return profilatura;
	}

    
}
