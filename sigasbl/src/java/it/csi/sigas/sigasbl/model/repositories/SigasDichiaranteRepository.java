/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;
import it.csi.sigas.sigasbl.model.entity.SigasOperatore;


@Repository
public interface SigasDichiaranteRepository extends CrudRepository<SigasDichiarante, Long>, JpaSpecificationExecutor<SigasDichiarante> {

	public SigasDichiarante findBySigasOperatores(SigasOperatore sigasOperatores);
	
	public List<SigasDichiarante> findByCodiceAziendaOrderByDataInsertDesc(String codiceAzienda);

}