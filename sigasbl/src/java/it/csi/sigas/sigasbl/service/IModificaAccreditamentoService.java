/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;
import it.csi.sigas.sigasbl.request.accreditamento.AnnullaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaAccreditamentoRequest;
import it.csi.sigas.sigasbl.vo.accreditamento.LegaleRappresentanteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.OperatoreVO;

public interface IModificaAccreditamentoService {

	public OperatoreVO recuperaOperatore(String codiceFiscaleUtente);

	public LegaleRappresentanteVO recuperaLegaleRappresentante(String codiceFiscaleUtente);

	public void modificaAccreditamento(ConfermaAccreditamentoRequest confermaAccreditamentoRequest);

	public void aggiornaDatiLegaleRappresentante(LegaleRappresentanteVO legaleRappresentanteVoPerAggiornamento, SigasDichiarante sigasDichiarante);

	public void aggiornaDatiOperatore(OperatoreVO operatoreVoPerAggiornamento, SigasDichiarante sigasDichiarante);

	public void cambiaStatoNoteAccreditamento(AnnullaPraticaAccreditamentoRequest annullaPraticaAccreditamentoRequest, String user);

}