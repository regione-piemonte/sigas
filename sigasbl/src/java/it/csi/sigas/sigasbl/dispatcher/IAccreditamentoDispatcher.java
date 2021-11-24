/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import java.util.List;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.request.accreditamento.AnnullaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaDichiaranteRequest;
import it.csi.sigas.sigasbl.request.accreditamento.RicercaPraticheAccreditamentoRequest;
import it.csi.sigas.sigasbl.vo.accreditamento.AccreditamentoVO;
import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.LegaleRappresentanteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.OperatoreVO;
import it.csi.sigas.sigasbl.vo.accreditamento.UtenteProvvisorioVO;
import org.springframework.security.access.prepost.PreAuthorize;
 
public interface IAccreditamentoDispatcher {
	
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public List<UtenteProvvisorioVO> ricercaPratiche(RicercaPraticheAccreditamentoRequest ricercaPraticheAccreditamentoRequest);
	
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public AccreditamentoVO ricercaPraticaAccreditamento(RicercaAccreditamentoRequest ricercaAccreditamentoRequest);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void confermaPraticaAccreditamento(
			ConfermaPraticaAccreditamentoRequest confermaPraticaAccreditamentoRequest, String user);
	
	
	
	//OPERATORE FO
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public List<AnagraficaSoggettoVO> ricercaDichiarante(String denominazione, String codiceFiscale, String partitaIva, String codiceAzienda);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public LegaleRappresentanteVO recuperaLegaleRappresentanteByCfDichiarante(String cfDichiarante, String piva);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public void confermaAccreditamento(ConfermaAccreditamentoRequest confermaAccreditamento);
 
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public DichiaranteVO recuperaDichiarante(String codiceFiscaleInserito);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public OperatoreVO recuperaOperatore(String codiceFiscaleUtente);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public LegaleRappresentanteVO recuperaLegaleRappresentante(String codiceFiscaleUtente);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public void modificaAccreditamento(ConfermaAccreditamentoRequest confermaAccreditamentoRequest);
 
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public void verificaEsistenzaLegaleRappresentante(String cflegaleRappr, DichiaranteVO dichiaranteVO);
	
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public List<UtenteProvvisorioVO> ricercaPratiche(RicercaDichiaranteRequest ricercaDichiaranteRequest);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public void cambiaStatoNoteAccreditamento(AnnullaPraticaAccreditamentoRequest annullaPraticaAccreditamentoRequest, String user);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	public void verificaDichiarante(String codiceAzienda, String codiceFiscaleOperatore);

}
