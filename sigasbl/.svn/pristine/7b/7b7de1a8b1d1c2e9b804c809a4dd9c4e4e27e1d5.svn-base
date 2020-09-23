/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.util;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public abstract class SpringSupportedResource {

	public boolean springBeansInjected = false;
	
	private static Logger logger = Logger.getLogger(SpringSupportedResource.class);

	public void contextInitialized(ServletContext sc) {
		logger.info("inizializzo risorsa " + this.getClass().getSimpleName());
	}

	public boolean isSpringBeansInjected() {
		return springBeansInjected;
	}

	public void setSpringBeansInjected(boolean springBeansInjected) {
		this.springBeansInjected = springBeansInjected;
	}

}
