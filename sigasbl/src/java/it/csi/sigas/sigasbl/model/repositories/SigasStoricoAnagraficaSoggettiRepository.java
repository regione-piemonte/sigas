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

import it.csi.sigas.sigasbl.model.entity.SigasStoricoAnagraficaSoggetti;

@Repository
public interface SigasStoricoAnagraficaSoggettiRepository extends CrudRepository<SigasStoricoAnagraficaSoggetti, Long> , JpaSpecificationExecutor<SigasStoricoAnagraficaSoggetti> {
	
	SigasStoricoAnagraficaSoggetti findByidStoricoAnag(Long id);
		
	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " +			 
			"WHERE UPPER(sta.codice_azienda) LIKE UPPER(:codiceAzienda) " +
			"ORDER BY sta.id_storico_anag DESC",nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findByCodiceAzienda(@Param("codiceAzienda") String codiceAzienda);
	
	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " +			 
			"WHERE upper(REGEXP_REPLACE(sta.codice_azienda, '[\\s,.]', '', 'g')) like upper(REGEXP_REPLACE(:codiceAzienda , '[\\s,.]', '', 'g')) " +
			"ORDER BY sta.id_storico_anag DESC",nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findByCodiceAziendaForImport(@Param("codiceAzienda") String codiceAzienda);
	
	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " +			 
			"WHERE UPPER(sta.denominazione) LIKE UPPER(:denominazione) " +
			"ORDER BY sta.id_storico_anag DESC",nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findByDenominazione(@Param("denominazione") String denominazione);
		
	
	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " +			 
			"WHERE upper(REGEXP_REPLACE(sta.denominazione, '[\\s,.]', '', 'g')) like upper(REGEXP_REPLACE(:denominazione , '[\\s,.]', '', 'g')) " +
			"ORDER BY sta.id_storico_anag DESC",nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findByDenominazioneForImport(@Param("denominazione") String denominazione);
	
	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " +			 
			"WHERE upper(REGEXP_REPLACE(sta.indirizzo, '[\\s,.]', '', 'g')) like upper(REGEXP_REPLACE(:indirizzo , '[\\s,.]', '', 'g')) " +
			"AND sta.fk_provincia = :idProvincia " +
	   		"AND sta.fk_comune = :idComune " +
			"ORDER BY sta.id_storico_anag DESC",nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findByIndirizzoForImport(@Param("indirizzo") String indirizzo,
																  @Param("idProvincia") BigDecimal idProvincia,
																  @Param("idComune") BigDecimal idComune);
	
	//Ricerca in base all'id anagrafica associata
	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " +			 
			"WHERE sta.id_anag = :id " +
			//"ORDER BY sta.id_storico_anag DESC",nativeQuery = true)
			"ORDER BY sta.data_riferimento DESC",nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findByIdAnag(@Param("id") Long id);
	
	//Ricerca in base all'id anagrafica associata soggetti confermati ed inseriti medinate import
	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " +			 
			"WHERE sta.id_anag = :id " +
				   "AND ((owner_operazione LIKE 'IMPORT' AND selected_import = true) OR (owner_operazione LIKE 'OPERATORE')) " +
			//"ORDER BY sta.data_riferimento DESC",nativeQuery = true)
			"ORDER BY sta.id_storico_anag DESC",nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findByIdAnagConfermati(@Param("id") Long id);
	
	//Ricerca in base all'id import UTF
	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " +			 
			"WHERE sta.id_import = :id " +
			"ORDER BY sta.id_storico_anag DESC",nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findByIdImportUTF(@Param("id") Long id);
	
	//Ricerca in base all'id import UTF delle ultime modifiche anagrafica
	@Query(value=
			"SELECT * " +
			"FROM sigas_storico_anagrafica_soggetti ssas " + 
			"INNER JOIN " + 
					"(SELECT id_anag , MAX(data_riferimento) AS data_riferimento " +  
					 "FROM sigas_storico_anagrafica_soggetti " +  
					 "WHERE id_import = :id " + 
					 "GROUP BY id_anag) AS tbl_unique_item " +  
				 "ON ssas.id_anag = tbl_unique_item.id_anag AND ssas.data_riferimento = tbl_unique_item.data_riferimento " +
			"WHERE ssas.id_import = :id", nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findUltimeAnagraficheModificateByIdImportUTF(@Param("id") Long id);
	
	//Update anagrafiche soggetto in base all'import utf 
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value=
			"UPDATE sigas_anagrafica_soggetti "
				 + "SET denominazione = tbl_selected.denominazione, "
				 	 + "codice_azienda = tbl_selected.codice_azienda, "
				 	 + "indirizzo = tbl_selected.indirizzo "
		  + "FROM ("
				+ "SELECT * "
				+ "FROM sigas_storico_anagrafica_soggetti "
				+ "WHERE id_storico_anag IN("
										 + "SELECT ssas.id_storico_anag "
										 + "FROM sigas_storico_anagrafica_soggetti ssas "
											 + "INNER JOIN "
											 			+ "(SELECT id_anag , MAX(data_riferimento) AS data_riferimento "
											 			 + "FROM sigas_storico_anagrafica_soggetti "
											 			 + "WHERE id_import = :id "
											 			 + "GROUP BY id_anag) AS tbl_unique_item "
											 	+ "ON ssas.id_anag = tbl_unique_item.id_anag AND ssas.data_riferimento = tbl_unique_item.data_riferimento "
										 + "WHERE ssas.id_import = :id) "
				+ ") AS tbl_selected "
		 + "WHERE tbl_selected.id_anag = sigas_anagrafica_soggetti.id_anag", nativeQuery = true)	
	void aggiornaAnagraficheByIdImportUTF(@Param("id") Long id);
	
	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " + 
			"	  INNER JOIN sigas_anagrafica_soggetti sas ON sta.id_anag = sas.id_anag AND sas.selected_import = true " + 
			"WHERE sas.denominazione LIKE :denominazione",nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findAllStoricoAnagraficaByDenominazioneAnagRiferimento(@Param("denominazione") String denominazione);
	
	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " + 
			"	  INNER JOIN sigas_anagrafica_soggetti sas ON sta.id_anag = sas.id_anag AND sas.selected_import = true " + 
			"WHERE sas.codiceAzienda LIKE :codiceAzienda",nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findAllStoricoAnagraficaByCodiceAziendaAnagRiferimento(@Param("codiceAzienda") String codiceAzienda);

	@Query(value=
			"SELECT sta.* " + 
			"FROM sigas_storico_anagrafica_soggetti sta " +
			"WHERE (:denominazioneSoggetto = '' OR UPPER(sta.denominazione) LIKE UPPER(:denominazioneSoggetto)) " +
			"AND (:annualita = '' OR sta.annualita = :annualita) " +
			"AND (:indirizzo = '' OR UPPER(sta.indirizzo) LIKE UPPER(:indirizzo)) " +
			"AND (:codiceAzienda = '' OR UPPER(sta.codice_azienda) LIKE UPPER(:codiceAzienda)) " +
			"AND (:cfPiva = '' OR UPPER(sta.cf_piva) =  UPPER(:cfPiva))", nativeQuery = true)
	List<SigasStoricoAnagraficaSoggetti> findStoricoAnagraficaSoggetti(@Param("denominazioneSoggetto") String denominazioneSoggetto,
																		@Param("annualita") String annualita,
																		@Param("indirizzo")String indirizzo,
																		@Param("codiceAzienda")String codiceAzienda,
																		@Param("cfPiva") String cfPiva);
	
	@Query(value= "SELECT * " 
	 +	"FROM sigas_storico_anagrafica_soggetti ssas " 
	 +	"WHERE id_storico_anag IN ( "
									+ "SELECT MAX(id_storico_anag) "
									+ "FROM sigas_storico_anagrafica_soggetti ssas "  
									+ "WHERE id_import = :idImport "
									+ "AND id_anag = :idAnag "									
								+ ")", nativeQuery = true)
	SigasStoricoAnagraficaSoggetti ricercaUltimaAnagraficaByIdImportIdAnag(@Param("idImport") Long idImport,
																		   @Param("idAnag") Long idAnag);
	
	@Query(value= "SELECT * " 
				+ "FROM sigas_storico_anagrafica_soggetti ssas " 
				+ "WHERE id_storico_anag IN ( "
												+ "SELECT MAX(id_storico_anag) "
												+ "FROM sigas_storico_anagrafica_soggetti ssas "  
												+ "WHERE id_anag = :idAnag"									
											+ ")", nativeQuery = true)
	SigasStoricoAnagraficaSoggetti ricercaUltimaAnagraficaByIdAnag(@Param("idAnag") Long idAnag);
	
	@Query(value= "SELECT * " 
			+ "FROM sigas_storico_anagrafica_soggetti ssas " 
			+ "WHERE id_storico_anag IN ( "
											+ "SELECT MAX(id_storico_anag) "
											+ "FROM sigas_storico_anagrafica_soggetti ssas "  
											+ "WHERE id_anag = :idAnag AND selected_import = true"									
										+ ")", nativeQuery = true)
	SigasStoricoAnagraficaSoggetti ricercaUltimaAnagraficaConfermataByIdAnag(@Param("idAnag") Long idAnag);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_storico_anagrafica_soggetti "
				 + "SET selected_import = :value, data_riferimento = now() "
				 + "WHERE id_storico_anag = :idStoricoAnag ", nativeQuery = true)	
	public void aggiornaFlagByIdStoricoAnag(@Param("value") Boolean value,									
									 @Param("idStoricoAnag")Long idStoricoAnag);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_storico_anagrafica_soggetti "
				 + "SET selected_import = :value, data_riferimento = now() "
				 + "WHERE id_anag = :idAnag AND id_import = :idImport ", nativeQuery = true)	
	public void aggiornaFlagByIdAnagAndIdImport(@Param("value") Boolean value,									
									 			@Param("idAnag")Long idAnag,
									 			@Param("idImport")BigDecimal idImport);
}
