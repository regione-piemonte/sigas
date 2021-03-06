/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.model.vo.impostazioni.AliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TassoVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoAliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoTassoVO;
import it.csi.sigas.sigasbl.request.home.ConfermaAliquotaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaTassoRequest;

public interface IImpostazioniDispatcher {

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<TassoVO> getAllTassi();

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AliquoteVO> getAllAliquote();
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<TipoAliquoteVO> getAllTipoAliquoteByTipo(String tipo);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	TipoAliquoteVO getTipoAliquoteByDescrizione(Long idTipoAliquota);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	void insertAliquota(ConfermaAliquotaRequest confermaAliquotaRequest, String user);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	AliquoteVO updateAliquota(ConfermaAliquotaRequest confermaAliquotaRequest, String user);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	void eliminaAliquota(Long id);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	void eliminaTasso(Long idTasso);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	TassoVO aggiornaTasso(ConfermaTassoRequest confermaAggiornaTasso, String user);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	TassoVO aggiungiTasso(ConfermaTassoRequest confermaAggiungiTasso, String user);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<TipoTassoVO> getAllTipoTassi();

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<TassoVO> tassiByRimborso();

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<TassoVO> tassiByAccertamenti();
}
