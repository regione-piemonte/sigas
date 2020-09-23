/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.dispatcher.IImpostazioniDispatcher;
import it.csi.sigas.sigasbl.model.vo.impostazioni.AliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TassoVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoAliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoTassoVO;
import it.csi.sigas.sigasbl.request.home.ConfermaAliquotaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaTassoRequest;
import it.csi.sigas.sigasbl.service.IImpostazioniService;

@Component
public class ImpostazioniDispatcherImpl implements IImpostazioniDispatcher {
	
	@Autowired
	private IImpostazioniService impostazioniService;
	
	@Override
	public List<TassoVO> getAllTassi() {
		return impostazioniService.getAllTassi();
	}

	@Override
	public List<AliquoteVO> getAllAliquote() {
		return impostazioniService.getAllAliquote();
	}

	@Override
	public List<TipoAliquoteVO> getAllTipoAliquoteByTipo(String tipo) {
		return impostazioniService.getAllTipoAliquoteByTipo(tipo);
	}

	@Override
	public TipoAliquoteVO getTipoAliquoteByDescrizione(Long idTipoAliquota) {
		return impostazioniService.getTipoAliquoteByDescrizione(idTipoAliquota);
	}

	@Override
	public void insertAliquota(ConfermaAliquotaRequest confermaAliquotaRequest, String user) {
		
		impostazioniService.insertAliquota(confermaAliquotaRequest, user);		
	}
	
	@Override
	public AliquoteVO updateAliquota(ConfermaAliquotaRequest confermaAliquotaRequest, String user) {
	
		return impostazioniService.updateAliquota(confermaAliquotaRequest, user);
	}
	
	@Override
	public void eliminaAliquota(Long id) {
		impostazioniService.eliminaAliquota(id);
	}
	@Override
	public void eliminaTasso(Long idTasso) {
		impostazioniService.eliminaTasso(idTasso);
	}

	@Override
	public TassoVO aggiornaTasso(ConfermaTassoRequest confermaAggiornaTasso, String user) {
		return impostazioniService.aggiornaTasso(confermaAggiornaTasso, user);
	}

	@Override
	public TassoVO aggiungiTasso(ConfermaTassoRequest confermaAggiungiTasso, String user) {
		return impostazioniService.aggiungiTasso(confermaAggiungiTasso, user);
	}

	@Override
	public List<TipoTassoVO> getAllTipoTassi() {
		return impostazioniService.getAllTipoTassi();
	}
	
	@Override
	public List<TassoVO> tassiByRimborso() {
		return impostazioniService.tassiByRimborso();
	}

	@Override
	public List<TassoVO> tassiByAccertamenti() {
		return impostazioniService.tassiByAccertamenti();
	}

}
