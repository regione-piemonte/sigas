/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasImportUTF;

@Repository
public interface SigasImportRepository extends CrudRepository<SigasImportUTF, Long> {

	@Query("select u from SigasImportUTF u  where u.annualita = :annualita order by idImport DESC")
	List<SigasImportUTF> findByAnno(@Param("annualita") String annualita);
	
	@Query("select u from SigasImportUTF u where u.annualita = :annualita and u.esito > 0")
	SigasImportUTF findDowloadedByAnno(@Param("annualita") String annualita);
	
	//@Query("SELECT u FROM SigasImportUTF u WHERE u.esito = 4 and u.selectedImport = true ORDER BY annualita DESC")
	@Query("SELECT u FROM SigasImportUTF u WHERE u.esito = 4 ORDER BY annualita DESC")
	List<SigasImportUTF> findImported();
	
	/*
	@Query(value = "SELECT annualita "
				 + "FROM sigas_import_utf "	
				 + "WHERE esito = 4 "
				 + "GROUP BY annualita "
				 + "ORDER BY annualita DESC"
		   ,nativeQuery = true)
	List<String> cercaAnnualitaImport();
	*/
	
	@Query(value = "SELECT sdc.annualita " + 
				   "FROM sigas_dich_consumi sdc " +   
				   "WHERE selected_import = true " + 																  
				   "GROUP BY sdc.annualita " + 
				   "ORDER BY annualita DESC "
		   ,nativeQuery = true)
	List<String> cercaAnnualitaImport();
	
	@Modifying
	@Query("UPDATE SigasImportUTF u set u.selectedImport = false WHERE u.annualita = :annualita")
	void resetSelectedImportPerAnnualita(@Param("annualita") String annualita);
	
	//NON NECESSARIO
	//@Modifying
	//@Query("UPDATE SigasImportUTF u set u.selectedImport = true WHERE u.idImport = :idImport")
	//void attivaImport(@Param("idImport") Long idImport);	
	
	//NON NECESSARIO
	//@Query("SELECT u FROM SigasImportUTF u WHERE u.esito = 4 and u.selectedImport = true ORDER BY annualita DESC")	
	//List<SigasImportUTF> findImportedSelected();
}
