/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasStatoDocumento;


@Repository
public interface SigasStatoDocumentoRepository extends CrudRepository<SigasStatoDocumento, Integer> {
	
	
	public SigasStatoDocumento findByCodiceStato(String codiceStato);
}
