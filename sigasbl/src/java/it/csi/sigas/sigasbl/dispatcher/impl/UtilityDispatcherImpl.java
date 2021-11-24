/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.dispatcher.IUtilityDispatcher;
import it.csi.sigas.sigasbl.model.vo.home.UtilityCtrlVO;
import it.csi.sigas.sigasbl.service.IUtilityService;

@Component
public class UtilityDispatcherImpl implements IUtilityDispatcher {

	@Autowired
	private IUtilityService utilityService;

	@Override
	public UtilityCtrlVO retriveMessageAndParameters(String descParametro, String descChiaveMessaggio) {
		return utilityService.retriveMessageAndParameters(descParametro, descChiaveMessaggio);
	}
	
	
	
}
