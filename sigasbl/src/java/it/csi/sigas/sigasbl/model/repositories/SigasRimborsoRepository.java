/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasRimborso;


@Repository
public interface SigasRimborsoRepository extends CrudRepository<SigasRimborso, Long> {
	
	List<SigasRimborso> findBySigasAnagraficaSoggettiIdAnag(long idAnag);

	SigasRimborso findBySigasAnagraficaSoggettiIdAnagAndSigasAnaComunicazioniIdComunicazione(long idAnag, long idComunicazione);
}
