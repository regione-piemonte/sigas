/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.facade;

import it.csi.sigas.sigasbl.integration.iride.Application;
import it.csi.sigas.sigasbl.integration.iride.Identita;
import it.csi.sigas.sigasbl.integration.iride.Ruolo;
import it.csi.sigas.sigasbl.integration.iride.UseCase;

public interface IrideServFacade {

	Ruolo[] findRuoliForPersonaInApplication(Identita identita, Application application) throws Exception;

	String getInfoPersonaInUseCase(Identita identita, String codRuoloCompleto) throws Exception;

	Ruolo[] findRuoliForPersonaInUseCase(Identita identita, UseCase useCase) throws Exception;

	UseCase[] findUseCasesForPersonaInApplication(Identita identita, String codApplicativo) throws java.lang.Exception;

	Identita identificaUserPassword(String username, String password) throws Exception;

}
