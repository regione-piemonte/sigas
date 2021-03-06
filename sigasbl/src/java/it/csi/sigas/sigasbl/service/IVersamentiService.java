/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import java.util.List;

import it.csi.sigas.sigasbl.model.vo.home.AllarmiSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoVersamentoVO;
import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.request.home.AllarmeRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoRequest;
import it.csi.sigas.sigasbl.request.home.RicercaVersamentiRequest;

public interface IVersamentiService {

	List<String> ricercaAnnualitaVersamenti(Long id);

	List<ProvinciaVO> ricercaProvinceVersamenti(Long id);

	List<TipoVersamentoVO> ricercaTipoVersamenti();

	List<String> ricercaMeseVersamenti(Long idAnag, String annualita);

	List<VersamentiPrVO> ricercaVersamenti(RicercaVersamentiRequest ricercaVersamentiRequest);

	void allarmeSoggetto(AllarmeRequest allarmeRequest, String user);

	List<AllarmiSoggettoVO> ricercaAllarmi(Long idAnag, Long idTipoAllarme);

	AllarmiSoggettoVO ricercaAllarmiVersamento(Long idConsumo);

	void insertVersamento(ConfermaVersamentoRequest confermaVersamentoRequest, String user, String codFiscale);

	void updateVersamento(ConfermaVersamentoRequest confermaVersamentoRequest, String user, String codFiscale);



}
