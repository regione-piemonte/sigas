/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;
import it.csi.sigas.sigasbl.model.entity.SigasOperatore;


@Repository
public interface SigasDichiaranteRepository extends CrudRepository<SigasDichiarante, Long>, JpaSpecificationExecutor<SigasDichiarante> {

//	public SigasDichiarante findByCfDichiarante(String cfDichiarante);
//	
//	public SigasDichiarante findByPivaDichiarante(String pIvaDichiarante);

	public SigasDichiarante findBySigasOperatores(SigasOperatore sigasOperatores);

	// public IrbaTDichiarante findByCfDichiaranteOrPivaDichiarante(String
	// cfDichiarante, String pivaDichiarante);

}