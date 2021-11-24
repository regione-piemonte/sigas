/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;
import it.csi.sigas.sigasbl.model.entity.SigasLegaleRappresent;



/***
 *
 * @author riccardo.bova
 * @date 08 mar 2018
 *
 ***/

@Repository
public interface SigasLegaleRappresentRepository extends CrudRepository<SigasLegaleRappresent, Long> {

//	public SigasLegaleRappresent findBySigasUtente2(SigasUtente utenteTrovato);

	public SigasLegaleRappresent findByCfLegaleRappresent(String cfLegaleRappresent);

	public SigasLegaleRappresent findBySigasDichiarante(SigasDichiarante sigasDichiarante);
	public SigasLegaleRappresent findBySigasDichiaranteIdDichiarante(long idDichiarante);

}
