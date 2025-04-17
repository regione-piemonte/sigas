/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.security;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.UserMock;
import it.csi.sigas.sigasbl.facade.IrideServFacade;
import it.csi.sigas.sigasbl.integration.iride.Identita;
import it.csi.sigas.sigasbl.rest.SpringApplicationContextHelper;


public class ShibbolethAuthenticationFilter extends RequestHeaderAuthenticationFilter {
	// RequestHeader
	private String shibbIdentity; // Shib-Iride-IdentitaDigitale

	private String shibbTestMode;

	private Logger log = LoggerFactory.getLogger(Constants.HANDLER_SECURITY);

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		IdentityDetails principal = new IdentityDetails();
		String token = request.getHeader(this.shibbIdentity);
		if (token == null && "enabled".equalsIgnoreCase(shibbTestMode)) {

			IrideServFacade irideServFacade = (IrideServFacade) SpringApplicationContextHelper.getBean("irideServFacadeImpl");
			Identita i = null;
			try {
				/* <[REPLACE_HERE]> */
				i = irideServFacade.identificaUserPassword(UserMock.DEMO22, "P!_MONTe2022");// rp
				//i = irideServFacade.identificaUserPassword(UserMock.DEMO24, "P!eMONT*2022"); // OPERATORE per il dichiarante 34
				//i = irideServFacade.identificaUserPassword(UserMock.DEMO43, "P!eMONT*2022"); // LEGALE per il dichiarante 34
				/* <[/REPLACE_HERE]> */
			} catch (Exception e) {
				//e.printStackTrace();
			}
			
			if(i != null) {
				token = i.getCodFiscale() + "/" + i.getNome() + "/" + i.getCognome() + "/" + i.getIdProvider() + "/" + i.getTimestamp() + "/" + i.getLivelloAutenticazione() + "/" + i.getMac();
			}			
		}

		log.info("[ShibbolethAuthenticationFilter::getPreAuthenticatedPrincipal] <" + request.getRequestURI() + "> (token::" + token + ")");

		principal.setIdentity(token);

		return principal;
	}

	public String getShibbTestMode() {
		return shibbTestMode;
	}

	public void setShibbTestMode(String shibbTestMode) {
		this.shibbTestMode = shibbTestMode;
	}

	public String getShibbIdentity() {
		return shibbIdentity;
	}

	public void setShibbIdentity(String shibbIdentity) {
		this.shibbIdentity = shibbIdentity;
	}

}
