/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;
import it.csi.sigas.sigasbl.model.entity.SigasUtenteProvvisorio;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaAccreditamentoRequest;
import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.LegaleRappresentanteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.OperatoreVO;

public interface IInserisciAccrediatamentoService {

	void confermaLegaleRappresentante(LegaleRappresentanteVO legaleRappresentante, SigasDichiarante sigasDichiarante);

	void confermaOperatore(OperatoreVO operatore, SigasDichiarante sigasDichiarante);

	void confermaAccreditamento(ConfermaAccreditamentoRequest confermaAccreditamento);

	LegaleRappresentanteVO recuperaLegaleRappresentanteByCfDichiarante(String cfDichiarante, String piva);

	void verificaEsistenzaLegaleRappresentante(String legaleRappr, DichiaranteVO dichiaranteVO);

	SigasUtenteProvvisorio confermaUtenteProvvisorio(ConfermaAccreditamentoRequest confermaAccreditamentoRequest,
			SigasDichiarante sigasDichiarante);

	void verificaDichiarante(String codiceAzienda, String codiceFiscaleOperatore);

}
