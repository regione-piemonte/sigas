/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.dispatcher.IAccreditamentoDispatcher;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.request.accreditamento.AnnullaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaDichiaranteRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaPraticheAccreditamentoRequest;
import it.csi.sigas.sigasbl.service.IDichiaranteService;
import it.csi.sigas.sigasbl.service.IInserisciAccrediatamentoService;
import it.csi.sigas.sigasbl.service.IModificaAccreditamentoService;
import it.csi.sigas.sigasbl.vo.accreditamento.AccreditamentoVO;
import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.LegaleRappresentanteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.OperatoreVO;
import it.csi.sigas.sigasbl.vo.accreditamento.UtenteProvvisorioVO;
 

@Component
public class AccreditamentoDispatcherImpl implements IAccreditamentoDispatcher {

	
 
	@Autowired
	private IDichiaranteService iDichiaranteService;
	
	@Autowired
	private IInserisciAccrediatamentoService inserisciAccrediatamentoService;
	
	
	
	@Autowired
	private IModificaAccreditamentoService modificaAccreditamentoService;
	


	
	@Override
	public List<UtenteProvvisorioVO> ricercaPratiche(RicercaPraticheAccreditamentoRequest ricercaPraticheAccreditamentoRequest) {
		return iDichiaranteService.ricercaPratiche(ricercaPraticheAccreditamentoRequest);
	}

	@Override
	public AccreditamentoVO ricercaPraticaAccreditamento(RicercaAccreditamentoRequest ricercaAccreditamentoRequest) {
		return iDichiaranteService.ricercaPraticaAccreditamento(ricercaAccreditamentoRequest);
	}

	@Override
	public void confermaPraticaAccreditamento(
			ConfermaPraticaAccreditamentoRequest confermaPraticaAccreditamentoRequest, String user) {
		iDichiaranteService.confermaPraticaAccreditamento(confermaPraticaAccreditamentoRequest, user);
		
	}

	
	//OPERATORE FO
	
	@Override
	public List<AnagraficaSoggettoVO> ricercaDichiarante(String denominazione, String codiceFiscale, String partitaIva, String codiceAzienda) {
		return iDichiaranteService.ricercaDichiarante(denominazione, codiceFiscale, partitaIva, codiceAzienda); 
	}

	@Override
	public LegaleRappresentanteVO recuperaLegaleRappresentanteByCfDichiarante(String cfDichiarante, String piva) {
		return inserisciAccrediatamentoService.recuperaLegaleRappresentanteByCfDichiarante(cfDichiarante, piva);
	}


	@Override
	public void confermaAccreditamento(ConfermaAccreditamentoRequest confermaAccreditamento) {
		inserisciAccrediatamentoService.confermaAccreditamento(confermaAccreditamento);

	}

	@Override
	public DichiaranteVO recuperaDichiarante(String codiceFiscaleInserito) {
		return iDichiaranteService.recuperaDichiarante(codiceFiscaleInserito);
	}

	@Override
	public OperatoreVO recuperaOperatore(String codiceFiscaleUtente) {
		return modificaAccreditamentoService.recuperaOperatore(codiceFiscaleUtente);
	}

	@Override
	public LegaleRappresentanteVO recuperaLegaleRappresentante(String codiceFiscaleUtente) {
		return modificaAccreditamentoService.recuperaLegaleRappresentante(codiceFiscaleUtente);
	}

	@Override
	public void modificaAccreditamento(ConfermaAccreditamentoRequest confermaAccreditamentoRequest) {
		modificaAccreditamentoService.modificaAccreditamento(confermaAccreditamentoRequest);
	}
	
	
	@Override
	public List<UtenteProvvisorioVO> ricercaPratiche(RicercaDichiaranteRequest ricercaDichiaranteRequest) {
		return iDichiaranteService.ricercaPratiche(ricercaDichiaranteRequest); 
	}
 
	


	@Override
	public void verificaEsistenzaLegaleRappresentante(String cflegaleRappr, DichiaranteVO dichiaranteVO) {
		inserisciAccrediatamentoService.verificaEsistenzaLegaleRappresentante(cflegaleRappr, dichiaranteVO);

	}
 
	@Override
	public void cambiaStatoNoteAccreditamento(AnnullaPraticaAccreditamentoRequest annullaPraticaAccreditamentoRequest,String user) { 
		modificaAccreditamentoService.cambiaStatoNoteAccreditamento(annullaPraticaAccreditamentoRequest,user);
		
	}

	@Override
	public void verificaDichiarante(String codiceAzienda, String codiceFiscaleOperatore) {
		inserisciAccrediatamentoService.verificaDichiarante(codiceAzienda, codiceFiscaleOperatore);
		
	}
	 

}
