/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasTipoAllarmi;


@Repository
public interface SigasTipoAllarmiRepository extends CrudRepository<SigasTipoAllarmi, Long> {

	
//	@Query("select u from SigasTipoAllarmi u where u.denominazione = :denominazione")
	public SigasTipoAllarmi findByDenominazioneIgnoreCase(String denominazione);
	
}
