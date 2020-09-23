/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;

@Repository
public interface SigasDichConsumiRepository extends CrudRepository<SigasDichConsumi, Long> {
	
	List<SigasDichConsumi> findBySigasAnagraficaSoggettiIdAnagAndAnnualita(Long idAnag, String anno);

	List<SigasDichConsumi> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndModIdConsumiOrderByProvinciaErogazioneAsc(
			Long idAnag, String anno, Long idmodiIdConsumiu);

	SigasDichConsumi findByModIdConsumi(Long idAnag);

	SigasDichConsumi findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndProvinciaErogazioneAndModIdConsumi(Long idAnag, String anno, String prov, long l);
}
