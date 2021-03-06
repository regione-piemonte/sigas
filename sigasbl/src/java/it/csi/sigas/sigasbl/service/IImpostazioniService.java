/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import java.util.List;

import it.csi.sigas.sigasbl.model.vo.impostazioni.AliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TassoVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoAliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoTassoVO;
import it.csi.sigas.sigasbl.request.home.ConfermaAliquotaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaTassoRequest;

public interface IImpostazioniService {

	//Aliquote
	List<AliquoteVO> getAllAliquote();

	List<TipoAliquoteVO> getAllTipoAliquoteByTipo(String tipo);

	TipoAliquoteVO getTipoAliquoteByDescrizione(Long idTipoAliquota);
	
	void insertAliquota(ConfermaAliquotaRequest confermaAliquotaRequest, String user);

	AliquoteVO updateAliquota(ConfermaAliquotaRequest confermaAliquotaRequest, String user);
	
	void eliminaAliquota(Long id);
	
	//Tassi di interesse
	List<TipoTassoVO> getAllTipoTassi();
	
	List<TassoVO> getAllTassi();
	
	void eliminaTasso(Long idTasso);

	TassoVO aggiungiTasso(ConfermaTassoRequest confermaAggiungiTasso, String user);

	TassoVO aggiornaTasso(ConfermaTassoRequest confermaAggiornaTasso, String user);

	List<TassoVO> tassiByRimborso();
	
	List<TassoVO> tassiByAccertamenti();
}
