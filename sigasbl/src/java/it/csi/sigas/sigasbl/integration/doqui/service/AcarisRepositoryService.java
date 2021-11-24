/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.service;

import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;


public interface AcarisRepositoryService 
{	
	public ObjectIdType recuperaIdRepository(String repositoryName) throws IntegrationException;
	
	public void init();
	
}
