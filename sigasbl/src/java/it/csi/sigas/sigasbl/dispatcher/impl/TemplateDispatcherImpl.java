/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.dispatcher.ITemplateDispatcher;
import it.csi.sigas.sigasbl.request.template.DatiTemplateRequest;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.service.ITemplateService;
import it.csi.sigas.sigasbl.vo.template.DatiTemplateVO;


/**
 * @author riccardo.bova
 * @date 09 gen 2019
 */
@Component
public class TemplateDispatcherImpl implements ITemplateDispatcher {

	@Autowired
	private ITemplateService iTemplateService;

	@Override
	public DatiTemplateVO getDatiTemplate(DatiTemplateRequest request, UserDetails userDetails ) {
		return iTemplateService.getDatiTemplate(request, userDetails);
	}

	@Override
	public byte[] stampaTemplate(DatiTemplateRequest request, UserDetails userDetails) {
		return iTemplateService.stampaTemplate(request, userDetails);
	}

	// 20200825_LC nuovo type per doc multiplo
//	public List<DocumentoScaricatoVO> downloadTemplate(DatiTemplateRequest request, UserDetails userDetails) {
//		return iTemplateService.downloadTemplate(request, userDetails);
//	}

	public DatiTemplateVO nomiTemplate(DatiTemplateRequest request, UserDetails userDetails) {
		return iTemplateService.nomiTemplate(request, userDetails);
	}
}
