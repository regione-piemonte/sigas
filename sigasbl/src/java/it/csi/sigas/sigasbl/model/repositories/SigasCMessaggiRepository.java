/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasCMessaggi;


@Repository
public interface SigasCMessaggiRepository extends CrudRepository<SigasCMessaggi, Long> {
	

	public SigasCMessaggi findByDescChiaveMessaggio(String parametro);
}
