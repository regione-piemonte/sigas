/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.dispatcher.IVersamentiDispatcher;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.PagamentiVersamentiVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoVersamentoVO;
import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.request.home.AllarmeRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoContabiliaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoRequest;
import it.csi.sigas.sigasbl.request.home.RicercaVersamentiRequest;
import it.csi.sigas.sigasbl.service.IVersamentiService;

@Component
public class VersamentiDispatcherImpl implements IVersamentiDispatcher {

	@Autowired
	private IVersamentiService versamentiService;
	
	@Override
	public List<String> ricercaAnnualitaVersamenti(Long id) {
		return versamentiService.ricercaAnnualitaVersamenti(id);
	}

	@Override
	public List<ProvinciaVO> ricercaProvinceVersamenti(Long id) {
		return versamentiService.ricercaProvinceVersamenti(id);
	}

	@Override
	public List<TipoVersamentoVO> ricercaTipoVersamenti() {
		return versamentiService.ricercaTipoVersamenti();
	}

	@Override
	public List<String> ricercaMeseVersamenti(Long idAnag, String annualita) {
		return versamentiService.ricercaMeseVersamenti(idAnag, annualita);
	}

	@Override
	public List<VersamentiPrVO> ricercaVersamenti(RicercaVersamentiRequest ricercaVersamentiRequest) {
		return versamentiService.ricercaVersamenti(ricercaVersamentiRequest);
	}

	@Override
	public void allarmeSoggetto(AllarmeRequest allarmeRequest, String user) {
		versamentiService.allarmeSoggetto(allarmeRequest, user);		
	}

	@Override
	public List<AllarmiSoggettoVO> ricercaAllarmi(Long idAnag, Long idTipoAllarme) {
		return versamentiService.ricercaAllarmi(idAnag, idTipoAllarme);
	}

	@Override
	public AllarmiSoggettoVO ricercaAllarmiVersamento(Long idConsumo) {
		return versamentiService.ricercaAllarmiVersamento(idConsumo);
	}
	
	@Override
	public void insertVersamento(ConfermaVersamentoRequest confermaVersamentoRequest, String user, String codFiscale) {
		versamentiService.insertVersamento(confermaVersamentoRequest,user, codFiscale);
	}
	
	@Override
	public List<PagamentiVersamentiVO> insertVersamentoContabilia(ConfermaVersamentoContabiliaRequest confermaVersamentoContabiliaRequest, String user, String codFiscale) {
		return versamentiService.insertVersamentoContabilia(confermaVersamentoContabiliaRequest,user, codFiscale);
	}

	@Override
	public void updateVersamento(ConfermaVersamentoRequest confermaVersamentoRequest, String user, String codFiscale) {
		versamentiService.updateVersamento(confermaVersamentoRequest,user, codFiscale);		
	}
	

}
