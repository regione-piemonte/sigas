/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import it.csi.sigas.sigasbl.request.template.DatiTemplateRequest;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.vo.template.DatiTemplateVO;

/**
 * @author riccardo.bova
 * @date 09 gen 2019
 */
public interface ITemplateDispatcher {

	DatiTemplateVO getDatiTemplate(DatiTemplateRequest request, UserDetails userDetails );

	byte[] stampaTemplate(DatiTemplateRequest request, UserDetails userDetails);

	// 20200825_LC nuovo type per doc multiplo
//	List<DocumentoScaricatoVO> downloadTemplate(DatiTemplateRequest request, UserDetails userDetails);

	DatiTemplateVO nomiTemplate(DatiTemplateRequest request, UserDetails userDetails);
}
