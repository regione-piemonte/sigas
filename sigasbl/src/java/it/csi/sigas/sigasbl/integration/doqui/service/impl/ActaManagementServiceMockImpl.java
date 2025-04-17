/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.service.impl;

import java.security.SecureRandom;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.sigas.sigasbl.integration.doqui.bean.KeyDocumentoActa;
import it.csi.sigas.sigasbl.integration.doqui.bean.UtenteActa;
import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.integration.doqui.utils.DateFormat;

public class ActaManagementServiceMockImpl extends ActaManagementServiceImpl {

	@Transactional(propagation=Propagation.REQUIRED)	
	public KeyDocumentoActa archiviaDocumentoFisico(DocumentoElettronicoActa documentoActa, UtenteActa utenteActa) throws IntegrationException {
		final String method = "archiviaDocumentoFisico";
		log.debug(method + ". BEGIN");

		try {

			log.info(method + ". MOCK CLASS");						
			
			KeyDocumentoActa keyDocumentoActa = new KeyDocumentoActa(documentoActa.getIdDocumento());
			
			SecureRandom randomGenerator = new SecureRandom();			

			//keyDocumentoActa.setNumeroProtocollo("mock-" + RandomStringUtils.randomNumeric(7)+"/"  + DateFormat.getCurrentYear());
			//keyDocumentoActa.setUUIDDocumento("mock-"+ RandomStringUtils.randomAlphabetic(31));
			
			keyDocumentoActa.setNumeroProtocollo("mock-" + randomGenerator.nextInt(7) +"/"  + DateFormat.getCurrentYear());
			keyDocumentoActa.setUUIDDocumento("mock-"+ UUID.randomUUID().toString());

			return keyDocumentoActa; 
		}
		finally{
			log.debug(method + ". END");
		}
	}





}