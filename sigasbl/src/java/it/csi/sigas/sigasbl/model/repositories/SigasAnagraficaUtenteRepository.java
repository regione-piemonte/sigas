/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.math.BigDecimal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaUtente;


/**
 * @author riccardo.bova
 * @date 08 mar 2018
 */
@Repository
public interface SigasAnagraficaUtenteRepository extends CrudRepository<SigasAnagraficaUtente, Long> {
	
	
	SigasAnagraficaUtente findByIdAnagAndIdUtenteProvv(Integer idAnag, BigDecimal idUtenteProvv);
}
