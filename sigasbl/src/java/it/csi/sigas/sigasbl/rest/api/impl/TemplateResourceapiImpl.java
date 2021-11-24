/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.dispatcher.ITemplateDispatcher;
import it.csi.sigas.sigasbl.request.template.DatiTemplateRequest;
import it.csi.sigas.sigasbl.rest.api.ITemplateResourceApi;
import it.csi.sigas.sigasbl.security.SecurityUtils;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;
import it.csi.sigas.sigasbl.vo.template.DatiTemplateVO;


@Service
public class TemplateResourceapiImpl extends SpringSupportedResource implements ITemplateResourceApi{

	@Autowired
	private ITemplateDispatcher iTemplateDispatcher;

	@Override
	public Response getDatiTemplate(DatiTemplateRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		DatiTemplateVO response = iTemplateDispatcher.getDatiTemplate(request, userDetails);
		return Response.ok().entity(response).build();
	}

	@Override
	public Response stampaTemplate(DatiTemplateRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		byte[] response = iTemplateDispatcher.stampaTemplate(request, userDetails);
		return Response.ok().entity(response).build();
	}

//	@POST
//	@Path("/downloadTemplate")
//	//@Produces(MediaType.APPLICATION_OCTET_STREAM)
//	public Response downloadTemplate(@Valid @NotNull(message = "RESCON19") DatiTemplateRequest request) {
//		// 20200825_LC gestione doc multiplo
//		UserDetails userDetails = SecurityUtils.getUser();
//		List<DocumentoScaricatoVO> docList = iTemplateDispatcher.downloadTemplate(request, userDetails);
//		
//		// 20200827_LC
//		byte[] file = null;
//		
//		if (docList.size()==1)
//			file=docList.get(0).getFile();
//		
//		return Response.ok().entity(file).build();
//		
//	}

	@Override
	public Response nomiTemplate(DatiTemplateRequest request) {
		UserDetails userDetails = SecurityUtils.getUser();
		DatiTemplateVO vo = iTemplateDispatcher.nomiTemplate(request, userDetails);
		return Response.ok(vo).build();
	}

}
