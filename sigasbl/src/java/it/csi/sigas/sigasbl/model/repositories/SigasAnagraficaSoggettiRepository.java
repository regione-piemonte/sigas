/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;

@Repository
public interface SigasAnagraficaSoggettiRepository extends CrudRepository<SigasAnagraficaSoggetti, Long> , JpaSpecificationExecutor<SigasAnagraficaSoggetti> {
	
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
	
	@Query(value="select sas.* from sigas_anagrafica_soggetti sas where sas.id_anag not in (select sdc.id_anag from sigas_dich_consumi sdc)", nativeQuery = true)
	List<SigasAnagraficaSoggetti> findAnagraficaSoggettiNotInConsumi();
	
	
	@Query(value=
			"select" + 
			"	s.*" + 
			" from sigas_utenti u" + 
			"	inner join sigas_utente_provvisorio up on (u.id_utente_provv = up.id_utente_provv AND up.stato='ACCETTATA')" +
			"	inner join sigas_anagrafica_utente us on u.id_utente_provv = us.id_utente_provv" + 
			"	inner join sigas_anagrafica_soggetti s on (s.id_anag = us.id_anag)" + 
			" where" + 
			"	(:cf='' OR u.codice_fiscale = :cf)",nativeQuery = true)
	List<SigasAnagraficaSoggetti> findAziendeAccreditato(@Param("cf") String cf);
	
	
	@Query(value=
			"select" + 
			"	distinct(s.*) " + 
			" from sigas_documenti sd" + 
			"	inner join sigas_anagrafica_soggetti s on (s.id_anag = sd.id_anag) ",nativeQuery = true)
	List<SigasAnagraficaSoggetti> findAziendeDocumentiInoltrati();
	
	
	
}
