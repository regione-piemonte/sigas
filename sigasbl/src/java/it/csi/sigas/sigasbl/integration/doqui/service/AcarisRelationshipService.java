/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.service;

import java.util.List;

import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;
import it.doqui.acta.actasrv.dto.acaris.type.common.PrincipalIdType;


public interface AcarisRelationshipService 
{	
	// 20200824_LC gestione documento multiplo
	public List<ObjectIdType> recuperaIdContentStream(ObjectIdType repositoryId, PrincipalIdType principalId, ObjectIdType documentoId) throws IntegrationException;
	
	public void init();
	
}
