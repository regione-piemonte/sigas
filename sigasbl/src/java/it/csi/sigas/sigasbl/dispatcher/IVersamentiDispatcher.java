/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.PagamentiVersamentiVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoVersamentoVO;
import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.request.home.AllarmeRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoContabiliaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoRequest;
import it.csi.sigas.sigasbl.request.home.RicercaVersamentiRequest;


public interface IVersamentiDispatcher {

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<String> ricercaAnnualitaVersamenti(Long idAnag);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<ProvinciaVO> ricercaProvinceVersamenti(Long idAnag);

	@PreAuthorize(value = AuthorizationRoles.HOME +" OR "+ AuthorizationRoles.UTENTE)
	List<TipoVersamentoVO> ricercaTipoVersamenti();

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<String> ricercaMeseVersamenti(Long id, String annualita);

	@PreAuthorize(value = AuthorizationRoles.HOME +" OR "+ AuthorizationRoles.UTENTE)
	List<VersamentiPrVO> ricercaVersamenti(RicercaVersamentiRequest ricercaVersamentiRequest);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AllarmiSoggettoVO> ricercaAllarmi(Long idAnag, Long idTipoAllarme);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	AllarmiSoggettoVO ricercaAllarmiVersamento(Long idConsumo);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	void updateVersamento(ConfermaVersamentoRequest confermaVersamentoRequest, String username, String codFiscale);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	void allarmeSoggetto(AllarmeRequest allarmeRequest, String username);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	void insertVersamento(ConfermaVersamentoRequest confermaVersamentoRequest, String username, String codFiscale);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<PagamentiVersamentiVO> insertVersamentoContabilia(ConfermaVersamentoContabiliaRequest confermaVersamentoContabiliaRequest, String username, String codFiscale);
	

}
