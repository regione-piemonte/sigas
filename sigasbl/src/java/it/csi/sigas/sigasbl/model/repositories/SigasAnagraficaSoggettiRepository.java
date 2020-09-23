/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;

@Repository
public interface SigasAnagraficaSoggettiRepository extends CrudRepository<SigasAnagraficaSoggetti, Long> {
	
	SigasAnagraficaSoggetti findByCodiceAzienda(String codiceAzienda);
	
//	@Query("select u from SigasAnagraficaSoggetti u ORDER BY u.codiceAzienda, u.sigasProvincia.siglaProvincia")
//	List<SigasAnagraficaSoggetti> findAllOrderByCodiceAzienda();
//
//	
//	@Query("select u from SigasAnagraficaSoggetti u WHERE status =:status ORDER BY u.codiceAzienda, u.sigasProvincia.siglaProvincia")
//	List<SigasAnagraficaSoggetti> findByStatoOrderByCodiceAzienda(@Param("status") String status);
//	
//	@Query("select u from SigasAnagraficaSoggetti u, SigasValidazione v WHERE u.codiceAzienda = v.codiceAzienda and stato = :stato	ORDER BY u.codiceAzienda, u.sigasProvincia.siglaProvincia")
//	List<SigasAnagraficaSoggetti> findFilterOrderByCodiceAzienda(@Param("stato") String stato);

	List<SigasAnagraficaSoggetti> findAll();
	
//	@Query("select u from SigasDichConsumi u where u.sigasAnagraficaSoggetti.idAnag = ?1 and u.annualita = ?2 ORDER BY u.provinciaErogazione")
//	List<SigasDichConsumi> saveConsumiPerProvince(Long id, String anno);

	@Query("select u from SigasAnagraficaSoggetti u WHERE u.codiceAzienda NOT LIKE '%NEW%' ORDER BY u.codiceAzienda, u.sigasProvincia.siglaProvincia")
	List<SigasAnagraficaSoggetti> newFindFilterCodiceAzienda();
	
	List<SigasAnagraficaSoggetti> findByCodiceAziendaStartingWith(String match);
	
	SigasAnagraficaSoggetti findByIdAnag(Long id);
	
}
