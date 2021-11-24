/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import java.util.List;

import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaDichiaranteRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaPraticheAccreditamentoRequest;
import it.csi.sigas.sigasbl.vo.accreditamento.AccreditamentoVO;
import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.UtenteProvvisorioVO;

public interface IDichiaranteService {

	public List<UtenteProvvisorioVO> ricercaPratiche(RicercaPraticheAccreditamentoRequest ricercaPraticheAccreditamentoRequest);
	
	public AccreditamentoVO ricercaPraticaAccreditamento(RicercaAccreditamentoRequest ricercaAccreditamentoRequest) ;

	public void confermaPraticaAccreditamento(
			ConfermaPraticaAccreditamentoRequest confermaPraticaAccreditamentoRequest, String user);
	
	
	///OPERATORE FO
	
	public List<AnagraficaSoggettoVO> ricercaDichiarante(String denominazione, String codiceFiscale, String partitaIva, String codiceAzienda);

	public SigasDichiarante confermaDichiarante(DichiaranteVO dichiarante);

	public SigasDichiarante aggiornaDatiDichiarante(DichiaranteVO dichiaranteVoPerAggiornamento);

	public DichiaranteVO recuperaDichiarante(String codiceFiscaleInserito);

	public List<UtenteProvvisorioVO> ricercaPratiche(RicercaDichiaranteRequest ricercaDichiaranteRequest);

	
}
