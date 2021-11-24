/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.service;

import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.doqui.index.ecmengine.client.webservices.dto.Node;
import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Mimetype;
import it.doqui.index.ecmengine.client.webservices.dto.engine.search.SearchParams;

public interface EcmEngineWebService {


	public boolean testResource() throws IntegrationException;

	public String getMimeType(Mimetype mimeType) throws IntegrationException;

	public String luceneSearchNoMetadata(SearchParams params, OperationContext operationContext) throws IntegrationException;

	public Node createContent(Node node, Content content, OperationContext operationContext) throws IntegrationException;

	public byte[] retrieveContentData(Node node, Content content, OperationContext operationContext) throws IntegrationException;

	
	// 20200612_LC
	
	public String luceneSearch(SearchParams params, OperationContext operationContext) throws IntegrationException;

	public Node deleteContent(Node node, OperationContext operationContext) throws IntegrationException;

	public void init();

}
