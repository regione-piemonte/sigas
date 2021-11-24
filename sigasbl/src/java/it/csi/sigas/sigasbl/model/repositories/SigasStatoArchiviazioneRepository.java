/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasStatoArchiviazione;
 

@Repository
public interface SigasStatoArchiviazioneRepository extends CrudRepository<SigasStatoArchiviazione, Integer> {
	
	
	public SigasStatoArchiviazione findByCodiceStato(String codiceStato);
}
