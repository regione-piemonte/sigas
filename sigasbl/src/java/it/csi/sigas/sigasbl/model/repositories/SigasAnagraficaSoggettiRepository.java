/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;

@Repository
public interface SigasAnagraficaSoggettiRepository extends CrudRepository<SigasAnagraficaSoggetti, Long> , JpaSpecificationExecutor<SigasAnagraficaSoggetti> {
		
	
	@Query(value="SELECT sas.* "
			   + "FROM sigas_anagrafica_soggetti sas "
			   + "WHERE UPPER(sas.codice_azienda) LIKE UPPER(:codiceAzienda) AND sas.selected_import = true "			   
		   , nativeQuery = true)
	SigasAnagraficaSoggetti findByCodiceAzienda(@Param("codiceAzienda") String codiceAzienda);
	
	@Query(value="SELECT sas.* "
			   + "FROM sigas_anagrafica_soggetti sas "
			   + "WHERE upper(REGEXP_REPLACE(sas.codice_azienda , '[\\s,.]', '', 'g')) like upper(REGEXP_REPLACE(:codiceAzienda , '[\\s,.]', '', 'g')) AND sas.selected_import = true "			   
		   , nativeQuery = true)
	SigasAnagraficaSoggetti findByCodiceAziendaForImport(@Param("codiceAzienda") String codiceAzienda);
	
	@Query(value="SELECT sas.* "
			   + "FROM sigas_anagrafica_soggetti sas "
			   + "WHERE UPPER(REGEXP_REPLACE(sas.denominazione , '[\\s,.]', '', 'g')) LIKE UPPER(REGEXP_REPLACE(:denominazione , '[\\s,.]', '', 'g')) " +
			   		   "AND UPPER(REGEXP_REPLACE(sas.indirizzo , '[\\s,.]', '', 'g')) LIKE UPPER(REGEXP_REPLACE(:indirizzo , '[\\s,.]', '', 'g')) " +
			   		   "AND fk_provincia = :idProvincia " +
			   		   "AND fk_comune = :idComune " +
			   		   "AND sas.selected_import = true "			   
		   , nativeQuery = true)
	SigasAnagraficaSoggetti findByDenominazioneAndIndirizzoForImport(@Param("denominazione") String denominazione,
																	 @Param("indirizzo") String indirizzo,
																	 @Param("idProvincia") BigDecimal idProvincia,
																	 @Param("idComune") BigDecimal idComune);

	@Query(value="SELECT sas.* "
			   + "FROM sigas_anagrafica_soggetti sas "
			   + "WHERE sas.selected_import = true"
		   , nativeQuery = true)
	List<SigasAnagraficaSoggetti> findAll();

	List<SigasAnagraficaSoggetti> findByCodiceAziendaStartingWith(String match);

	//SigasAnagraficaSoggetti findByIdAnag(Long id);
	
	@Query(value="SELECT sas.* "
			   + "FROM sigas_anagrafica_soggetti sas "
			   + "WHERE sas.selected_import = true and sas.id_anag = :id "
		   ,nativeQuery = true)
	SigasAnagraficaSoggetti findByIdAnag(@Param("id") Long id);
	
	
	@Query(value="SELECT sa.* " +
			 	 "FROM sigas_anagrafica_soggetti sa " + 
			 	 	"INNER JOIN sigas_provincia sp ON sa.fk_provincia = sp.id_provincia " + 
			 	 "WHERE sa.codice_azienda NOT LIKE '%NEW%' ORDER BY sa.codice_azienda, sp.sigla_provincia", 
		   nativeQuery = true)
	List<SigasAnagraficaSoggetti> newFindFilterCodiceAzienda();	
	
	
	@Query(value="select sas.* from sigas_anagrafica_soggetti sas where sas.id_anag not in (select sdc.id_anag from sigas_dich_consumi sdc) and sas.selected_import = true", nativeQuery = true)
	List<SigasAnagraficaSoggetti> findAnagraficaSoggettiNotInConsumi();	
	
	@Query(value=
			"select" + 
			"	s.*" + 
			" from sigas_utenti u" + 
			"	inner join sigas_utente_provvisorio up on (u.id_utente_provv = up.id_utente_provv AND up.stato='ACCETTATA')" +
			"	inner join sigas_anagrafica_utente us on u.id_utente_provv = us.id_utente_provv" + 
			"	inner join sigas_anagrafica_soggetti s on s.id_anag = us.id_anag and s.selected_import = true" + 
			" where" + 
			"	(:cf='' OR u.codice_fiscale = :cf)",nativeQuery = true)
	List<SigasAnagraficaSoggetti> findAziendeAccreditato(@Param("cf") String cf);	
	
	@Query(value=
			"select" + 
			"	distinct(s.*) " + 
			" from sigas_documenti sd" + 
			"	inner join sigas_anagrafica_soggetti s on s.id_anag = sd.id_anag and s.selected_import = true ",nativeQuery = true)
	List<SigasAnagraficaSoggetti> findAziendeDocumentiInoltrati();
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_anagrafica_soggetti "
				 + "SET selected_import = :value "
				 + "WHERE id_anag = :idAnag ", nativeQuery = true)	
	void aggiornaSelectFlagByIdAnag(@Param("value") Boolean value, 
									@Param("idAnag")Long idAnag);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_anagrafica_soggetti "
				 + "SET selected_import = :value, "
				 + "denominazione = :denominazione, "
				 + "codice_azienda = :codiceAzienda, "
				 + "indirizzo = :indirizzo "
				 + "WHERE id_anag = :idAnag ", nativeQuery = true)	
	void aggiornaAnagraficaByFields(@Param("value") Boolean value,
									@Param("denominazione") String denominazione,
									@Param("codiceAzienda") String codiceAzienda,
									@Param("idAnag")Long idAnag,
									@Param("indirizzo") String indirizzo);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_anagrafica_soggetti "
				 + "SET selected_import = :value, "
				 + "denominazione = :denominazione, "
				 + "codice_azienda = :codiceAzienda, "
				 + "indirizzo = :indirizzo, "
				 + "id_import = :idImportAggiornamento, "
				 + "fk_provincia = :idProvincia, "
				 + "fk_comune = :idComune "
				 + "WHERE id_anag = :idAnag", nativeQuery = true)	
	public void aggiornaAnagraficaByFieldsConfermaImport(@Param("value") Boolean value,
												  @Param("denominazione") String denominazione,
												  @Param("codiceAzienda") String codiceAzienda,
												  @Param("idAnag")Long idAnag,
												  @Param("indirizzo") String indirizzo,
												  @Param("idImportAggiornamento")BigDecimal idImportAggiornamento,
												  @Param("idProvincia")BigDecimal idProvincia,
												  @Param("idComune")BigDecimal idComune);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_anagrafica_soggetti "
				 + "SET indirizzo = :indirizzo "				 
				 + "WHERE id_anag = :idAnag ", nativeQuery = true)	
	void aggiornaIndirizzoAnagraficaById(@Param("indirizzo") String indirizzo,										 
										 @Param("idAnag")Long idAnag);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_anagrafica_soggetti "
				 + "SET cf_piva = :cf "				 
				 + "WHERE id_anag = :idAnag ", nativeQuery = true)	
	void aggiornaCFAnagraficaById(@Param("cf") String cf,										 
								  @Param("idAnag")Long idAnag);
	
	@Query(value="SELECT sas.* "
			   + "FROM sigas_anagrafica_soggetti sas "
			   + "WHERE sas.id_anag = :id "
		   ,nativeQuery = true)
	SigasAnagraficaSoggetti findByIdAnagPerImportFrontespizio(@Param("id") Long id);
	
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_anagrafica_soggetti "
				 + "SET id_fusione = 0, "
				 + "data_fusione = null, "
				 + "note = null "
				 + "WHERE id_anag = :idAnag ", nativeQuery = true)	
	void ripristinoDopoCancellazioneFusione(@Param("idAnag")Long idAnag);
	
}
