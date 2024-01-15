/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;

@Repository
public interface SigasDichConsumiRepository extends CrudRepository<SigasDichConsumi, Long> {
	
	List<SigasDichConsumi> findBySigasAnagraficaSoggettiIdAnagAndAnnualita(Long idAnag, String anno);

	List<SigasDichConsumi> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndModIdConsumiAndProvinciaErogazioneIsNotNullOrderByProvinciaErogazioneAsc(Long idAnag, String anno, Long idmodiIdConsumiu);

	SigasDichConsumi findByModIdConsumi(Long idAnag);

	SigasDichConsumi findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndProvinciaErogazioneAndModIdConsumi(Long idAnag, String anno, String prov, long l);
	
	//SIGAS-245 totale versato
	@Query(value = "SELECT sdc.* FROM sigas_dich_consumi sdc WHERE sdc.id_anag = :idAnag AND sdc.provincia_erogazione like :prov AND sdc.annualita <= :anno ORDER BY sdc.annualita DESC",  nativeQuery = true)	
	List<SigasDichConsumi> findConsumiFinoAnnoByIdAnagProv(@Param("idAnag")Long idAnag, @Param("prov")String prov, @Param("anno")String anno);	
}
