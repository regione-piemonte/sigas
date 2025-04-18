/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.facade.impl;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.config.IrideConfig;
import it.csi.sigas.sigasbl.facade.IrideServFacade;
import it.csi.sigas.sigasbl.integration.iride.Application;
import it.csi.sigas.sigasbl.integration.iride.Identita;
import it.csi.sigas.sigasbl.integration.iride.IdentitaNonAutenticaException;
import it.csi.sigas.sigasbl.integration.iride.PolicyEnforcerBaseService;
import it.csi.sigas.sigasbl.integration.iride.PolicyEnforcerBaseServiceServiceLocator;
import it.csi.sigas.sigasbl.integration.iride.Ruolo;
import it.csi.sigas.sigasbl.integration.iride.UseCase;
import it.csi.sigas.sigasbl.rest.SpringApplicationContextHelper;

@Component
public class IrideServFacadeImpl implements IrideServFacade {

	protected Logger logger = LoggerFactory.getLogger(Constants.HANDLER_IRIDE_SRV);

	private PolicyEnforcerBaseService binding;

	public IrideServFacadeImpl() throws AxisFault, ServiceException {

		PolicyEnforcerBaseServiceServiceLocator locator = new PolicyEnforcerBaseServiceServiceLocator();

		IrideConfig irideConfig = (IrideConfig) SpringApplicationContextHelper.getBean("irideConfig");
		locator.setPolicyEnforcerBaseEndpointAddress(irideConfig.getIrideServiceEndpointUrl());
		binding = (PolicyEnforcerBaseService) locator.getPolicyEnforcerBase();
	}

	@Override
	public Ruolo[] findRuoliForPersonaInApplication(Identita identita, Application application) throws java.lang.Exception {
		Ruolo[] ruoloArray = null;

		try {
			ruoloArray = binding.findRuoliForPersonaInApplication(identita, application);
		} catch (java.lang.Exception ex) {
			//ex.printStackTrace();
			throw ex;
		}

		return ruoloArray;
	}

	@Override
	public String getInfoPersonaInUseCase(Identita identita, String codRuoloCompleto) throws Exception {
		return binding.getInfoPersonaInUseCase(identita, new UseCase(null, codRuoloCompleto));
	}

	/*
	 * NON NECESSARIO public String getInfoPersonaInUseCase(Identita identita,
	 * RuoloVO ruolo) throws Exception { return
	 * binding.getInfoPersonaInUseCase(identita, new UseCase(null,
	 * ruolo.getCodice())); }
	 */
	@Override
	public Ruolo[] findRuoliForPersonaInUseCase(Identita identita, UseCase useCase) throws Exception {
		return binding.findRuoliForPersonaInUseCase(identita, useCase);
	}

	@Override
	public UseCase[] findUseCasesForPersonaInApplication(Identita identita, String codApplicativo) throws java.lang.Exception {
		UseCase[] useCaseArray = null;

		try {
			useCaseArray = new UseCase[] {};

			useCaseArray = binding.findUseCasesForPersonaInApplication(identita, new Application(codApplicativo));
		} catch (java.lang.Exception ex) {
			// TOIMPL Auto-generated catch block
			//ex.printStackTrace();
			throw ex;
		}

		return useCaseArray;
	}

	// Metodo di utilita
	public static Identita getIdentitaFromToken(String token)
			// throws MalformedIdTokenException
			throws Exception {
		Identita identita = new Identita();

		int slash1Index = token.indexOf('/');
		if (slash1Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		identita.setCodFiscale(token.substring(0, slash1Index));
		int slash2Index = token.indexOf('/', slash1Index + 1);
		if (slash2Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		identita.setNome(token.substring(slash1Index + 1, slash2Index));
		int slash3Index = token.indexOf('/', slash2Index + 1);
		if (slash3Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		identita.setCognome(token.substring(slash2Index + 1, slash3Index));
		int slash4Index = token.indexOf('/', slash3Index + 1);
		if (slash4Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		identita.setIdProvider(token.substring(slash3Index + 1, slash4Index));
		int slash5Index = token.indexOf('/', slash4Index + 1);
		if (slash5Index == -1)
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		identita.setTimestamp(token.substring(slash4Index + 1, slash5Index));
		int slash6Index = token.indexOf('/', slash5Index + 1);
		if (slash6Index == -1) {
			// throw new MalformedIdTokenException(token);
			throw new IdentitaNonAutenticaException();
		} else {
			identita.setLivelloAutenticazione(Integer.parseInt(token.substring(slash5Index + 1, slash6Index)));
			identita.setMac(token.substring(slash6Index + 1));
		}
		return identita;
	}

	@Override
	public Identita identificaUserPassword(String username, String password) throws Exception {
		Identita identita = null;
		try {
			identita = binding.identificaUserPassword(username, password);
		} catch (java.lang.Exception ex) {
			// TOIMPL Auto-generated catch block
			//ex.printStackTrace();
			throw ex;
		}

		return identita;
	}

}
