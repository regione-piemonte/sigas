/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.repository.Repository;

import it.csi.sigas.sigasbl.model.entity.custom.SoggettoEntityCustom;

public interface SoggettoRepository extends Repository<SoggettoEntityCustom, Long> {

	List<SoggettoEntityCustom> findListaSoggetti(String filter, String anno);
	
	List<SoggettoEntityCustom> findListaSoggettiPerFusione(String anno);

}
