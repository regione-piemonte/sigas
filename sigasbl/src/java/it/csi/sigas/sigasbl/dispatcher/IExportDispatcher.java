/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.request.home.DownloadAccertamentiReport;
import it.csi.sigas.sigasbl.request.home.DownloadDettaglioSoggettoReport;
import it.csi.sigas.sigasbl.request.home.DownloadDocumentazioneReport;
import it.csi.sigas.sigasbl.request.home.DownloadReport;
import it.csi.sigas.sigasbl.request.home.DownloadSoggettiReport;
import it.csi.sigas.sigasbl.request.home.DownloadVersamentiReport;

public interface IExportDispatcher {

	@PreAuthorize(value = AuthorizationRoles.HOME)
	byte[] salvaConsumiPerProvince(DownloadReport downloadReport);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	byte[] salvaSoggetto(DownloadDettaglioSoggettoReport downloadReport);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	byte[] salvaElencoSoggetti(DownloadSoggettiReport downloadSoggettiReport);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	byte[] salvaElencoVersamenti(DownloadVersamentiReport downloadVersamentiReport);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	byte[] salvaElencoDocumenti(DownloadDocumentazioneReport downloadDocumentazioneReport);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	byte[] salvaDetermina(DownloadReport downloadReport);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	byte[] salvaElencoAccertamenti(DownloadAccertamentiReport downloadAccertamentiReport);

}
