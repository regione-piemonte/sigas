/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.facade.IrideServFacade;
import it.csi.sigas.sigasbl.facade.impl.IrideServFacadeImpl;
import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.integration.iride.Application;
import it.csi.sigas.sigasbl.integration.iride.Identita;
import it.csi.sigas.sigasbl.integration.iride.Ruolo;
import it.csi.sigas.sigasbl.integration.iride.UseCase;

@Service
public class UserDetailsService implements ShibbolethDetailService {

	@Autowired
	private IrideServFacade irideServFacade;
	private Logger logger = LoggerFactory.getLogger(Constants.HANDLER_SECURITY);

	@Override
	public UserDetails caricaUtenteDaIdentita(IdentityDetails identityDetails, String irideAPP) throws UsernameNotFoundException {

		logger.debug("[UserDetailsService::caricaUtenteDaIdentita] loadUserIdentity::" + (identityDetails != null ? identityDetails.getIdentity() : "null"));

		List<GrantedAuthority> auths = null;

		Identita identitaOrch = null;
		Application applicationOrch = null;

		try {
			identitaOrch = IrideServFacadeImpl.getIdentitaFromToken(identityDetails.getIdentity());

			applicationOrch = new Application(irideAPP);

			auths = findUseCasesForIdentita(identitaOrch, applicationOrch);

		} catch (Exception e) {
			logger.error("Si e verificato un errore in fase di autenticazione", e);
		}

		return this.createUserDetails(identitaOrch, applicationOrch, auths);
	}

	public List<GrantedAuthority> findUseCasesForIdentita(Identita identita, Application application) throws Exception {
		List<GrantedAuthority> sga = new ArrayList<GrantedAuthority>();
		UseCase[] useCases = irideServFacade.findUseCasesForPersonaInApplication(identita, application.getId());

		if (null != useCases) {
			for (UseCase useCase : useCases)
				sga.add(new SigasGrantedAuthority(useCase.getId(), useCase.getId(), SigasGrantedAuthority.TIPO_AUTHORITY_USE_CASE_IRIDE));
		}

		sga.add(new SigasGrantedAuthority("UTENTE", "UTENTE", SigasGrantedAuthority.TIPO_AUTHORITY_ALTRO));

		return sga;
	}

	public List<GrantedAuthority> findRuoliIride(Identita identita, Application application) throws Exception {
		Ruolo[] ruoloOrchArray = null;

		ruoloOrchArray = irideServFacade.findRuoliForPersonaInApplication(identita, application);
		List<GrantedAuthority> sga = new ArrayList<GrantedAuthority>();
		logger.debug("############ RUOLI TROVATI con aggancio diretto al client ws orchflai");
		if (ruoloOrchArray != null && ruoloOrchArray.length > 0) {
			for (int i = 0; i < ruoloOrchArray.length; i++) {
				Ruolo ruolo = ruoloOrchArray[i];
				logger.debug("Trovata ruolo n. " + (i + 1));
				logger.debug("ruolo.codiceRuolo = " + ruolo.getCodiceRuolo());
				logger.debug("ruolo.codiceDominio = " + ruolo.getCodiceDominio());
				logger.debug("ruolo.mnemonico = " + ruolo.getMnemonico());

				sga.add(new SigasGrantedAuthority(ruolo.getMnemonico(), ruolo.getCodiceRuolo(), SigasGrantedAuthority.TIPO_AUTHORITY_RUOLO_IRIDE));
			}
		} else {
			logger.debug("Non sono stati trovati ruoli ");
		}

		sga.add(new SigasGrantedAuthority("UTENTE", "UTENTE", SigasGrantedAuthority.TIPO_AUTHORITY_ALTRO));

		return sga;
	}

	@Deprecated
	public List<SigasGrantedAuthority> findUseCasesDaRuolo(SigasGrantedAuthority ruoloFlaidoor, Identita identita, Application application) throws Exception {
		List<SigasGrantedAuthority> sga = new ArrayList<SigasGrantedAuthority>();
		UseCase[] useCases = irideServFacade.findUseCasesForPersonaInApplication(identita, application.getId());

		if (null != useCases) {
			for (UseCase useCase : useCases) {
				Ruolo[] ruoli = irideServFacade.findRuoliForPersonaInUseCase(identita, useCase);
				for (Ruolo ruolo : ruoli) {
					if (ruolo.getMnemonico().compareTo(ruoloFlaidoor.getCodice()) == 0) {
						sga.add(new SigasGrantedAuthority(useCase.getId(), useCase.getId(), SigasGrantedAuthority.TIPO_AUTHORITY_USE_CASE_IRIDE));
					}
				}
			}
		}

		return sga;
	}

	@Deprecated
	public List<GrantedAuthority> findRuoliAndUseCaseIride(Identita identita, Application application) throws Exception {
		Ruolo[] ruoloOrchArray = null;

		ruoloOrchArray = irideServFacade.findRuoliForPersonaInApplication(identita, application);
		List<GrantedAuthority> sga = new ArrayList<GrantedAuthority>();
		logger.debug("############ RUOLI TROVATI con aggancio diretto al client ws orchflai");
		if (ruoloOrchArray != null && ruoloOrchArray.length > 0) {
			for (int i = 0; i < ruoloOrchArray.length; i++) {
				Ruolo ruolo = ruoloOrchArray[i];
				logger.debug("Trovata ruolo n. " + (i + 1));
				logger.debug("ruolo.codiceRuolo = " + ruolo.getCodiceRuolo());
				logger.debug("ruolo.codiceDominio = " + ruolo.getCodiceDominio());
				logger.debug("ruolo.mnemonico = " + ruolo.getMnemonico());

				// RUOLO
				SigasGrantedAuthority authRuolo = new SigasGrantedAuthority(ruolo.getMnemonico(), ruolo.getCodiceRuolo(), SigasGrantedAuthority.TIPO_AUTHORITY_RUOLO_IRIDE);
				sga.add(authRuolo);
				// USECASE
				List<SigasGrantedAuthority> useCase = findUseCasesDaRuolo(authRuolo, identita, application);
				sga.addAll(useCase);

			}
		} else {
			logger.debug("Non sono stati trovati ruoli ");
		}

		sga.add(new SigasGrantedAuthority("UTENTE", "UTENTE", SigasGrantedAuthority.TIPO_AUTHORITY_ALTRO));

		return sga;
	}

	/**
	 * Metodo di utility
	 * 
	 * @param username
	 * @param userVo
	 * @param combinedAuthorities
	 * @return
	 */
	protected UserDetails createUserDetails(Identita identita, Application application, List<GrantedAuthority> combinedAuthorities) {

		UserDetails result = new UserDetails(identita.getNome() + " " + identita.getCognome(), "", true, true, true, true, combinedAuthorities, identita, application);

		return result;

	}

}
