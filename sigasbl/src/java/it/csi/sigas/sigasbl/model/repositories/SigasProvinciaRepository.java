/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;

@Repository
public interface SigasProvinciaRepository extends CrudRepository<SigasProvincia, Long>, JpaSpecificationExecutor<SigasProvincia>  {

	@Query("select u from SigasProvincia u where u.fineValidita is null ORDER BY u.denomProvincia")
	public List<SigasProvincia> findByFineValiditaIsNullOrderByDenomProvinciaAsc();

//	@Query("select u from SigasProvincia u where u.fineValidita is null and u.siglaProvincia = ?1")
	public SigasProvincia findBySiglaProvinciaAndFineValiditaIsNull(String siglaProvincia);

	public SigasProvincia findByDenomProvinciaAndFineValiditaIsNull(String descrizioneProvincia);



}
