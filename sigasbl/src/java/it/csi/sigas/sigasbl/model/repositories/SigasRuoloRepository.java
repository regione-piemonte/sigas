/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasRuolo;


/**
 * @author riccardo.bova
 * @date 08 mar 2018
 */
@Repository
public interface SigasRuoloRepository extends CrudRepository<SigasRuolo, Long> {

}
