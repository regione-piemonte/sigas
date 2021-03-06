/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author riccardo.bova
 * @date 19 gen 2018
 */
@Component
public class IrideConfig {

	@Value("${iride_service_endpoint_url}")
	private String irideServiceEndpointUrl;

	public String getIrideServiceEndpointUrl() {
		return irideServiceEndpointUrl;
	}

	public void setIrideServiceEndpointUrl(String irideServiceEndpointUrl) {
		this.irideServiceEndpointUrl = irideServiceEndpointUrl;
	}

}
