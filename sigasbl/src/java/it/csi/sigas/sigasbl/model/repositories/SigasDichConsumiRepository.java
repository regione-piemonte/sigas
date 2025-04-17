/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;

@Repository
public interface SigasDichConsumiRepository extends CrudRepository<SigasDichConsumi, Long> {
	
	@Query(value = "SELECT sdc.* "
			     + "FROM sigas_dich_consumi sdc "			     				     
			     + "WHERE sdc.id_anag = :idAnag "			     
			     	   + "AND sdc.annualita = :anno "
			     	   + "AND sdc.selected_import = true"
		,  nativeQuery = true)	
	List<SigasDichConsumi> 
	findBySigasAnagraficaSoggettiIdAnagAndAnnualita(@Param("idAnag") Long idAnag, @Param("anno")String anno);
	
	@Query(value = "SELECT sdc.* "
			     + "FROM sigas_dich_consumi sdc "						     
			     + "WHERE sdc.id_anag = :idAnag "			     
					   + "AND sdc.annualita = :anno "
					   + "AND sdc.mod_id_consumi = :idmodiIdConsumiu "
					   + "AND sdc.provincia_erogazione IS NOT NULL "			     
					   + "AND sdc.selected_import = true "
			     + "ORDER BY provincia_erogazione ASC"
		    ,  nativeQuery = true)
	List<SigasDichConsumi> 
	findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndModIdConsumiAndProvinciaErogazioneIsNotNullOrderByProvinciaErogazioneAsc(@Param("idAnag") Long idAnag, 
																															   @Param("anno") String anno, 
																															   @Param("idmodiIdConsumiu") Long idmodiIdConsumiu);
	
	@Query(value = "SELECT sdc.* "
			     + "FROM sigas_dich_consumi sdc "			     			     
			     + "WHERE sdc.mod_id_consumi = :idModConsumi "
			     	   + "AND sdc.selected_import = true "
		   ,  nativeQuery = true)
	SigasDichConsumi 
	findByModIdConsumi(@Param("idModConsumi") Long idModConsumi);

	@Query(value = "SELECT sdc.* "
			     + "FROM sigas_dich_consumi sdc "			     			     
			     + "WHERE sdc.id_anag = :idAnag "			     
					   + "AND sdc.annualita = :anno "
					   + "AND sdc.mod_id_consumi = :l "
					   + "AND sdc.provincia_erogazione LIKE :prov "			     
				 	   + "AND sdc.selected_import = true "
			     + "ORDER BY provincia_erogazione ASC"
			,  nativeQuery = true)
	SigasDichConsumi 
	findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndProvinciaErogazioneAndModIdConsumi(@Param("idAnag") Long idAnag, 
																						 @Param("anno") String anno, 
																						 @Param("prov") String prov, 
																						 @Param("l") long l);
	
	//SIGAS-245 totale versato
	@Query(value = "SELECT sdc.* "
				 + "FROM sigas_dich_consumi sdc "				
				 + "WHERE sdc.id_anag = :idAnag "
					   + "AND sdc.provincia_erogazione like :prov "
					   + "AND sdc.annualita <= :anno "
					   + "AND sdc.selected_import = true "
				 + "ORDER BY sdc.annualita DESC"
			,  nativeQuery = true)	
	List<SigasDichConsumi> 
	findConsumiFinoAnnoByIdAnagProv(@Param("idAnag")Long idAnag, @Param("prov")String prov, @Param("anno")String anno);
	
	 
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_dich_consumi "
				 + "SET selected_import = :value "
				 + "WHERE id_anag = :idAnag "
				 + "AND annualita LIKE :annualita", nativeQuery = true)	
	public void aggiornaSelectFlagByIdAnagAnnualita(@Param("value") Boolean value, 
										     @Param("idAnag")Long idAnag, 
											 @Param("annualita")String annualita);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_dich_consumi "
				 + "SET selected_import = :value "
				 + "WHERE annualita LIKE :annualita", nativeQuery = true)	
	void aggiornaSelectFlagByAnnualita(@Param("value") Boolean value,									    
									   @Param("annualita")String annualita);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_dich_consumi "
				 + "SET selected_import = :value "
				 + "WHERE id_anag = :idAnag "
				 + "AND annualita LIKE :annualita "
				 + "AND id_import = :idImport", nativeQuery = true)	
	public void aggiornaSelectFlagByIdAnagAnnualitaIdImport(@Param("value") Boolean value, 
										     		 @Param("idAnag")Long idAnag, 
										     		 @Param("annualita")String annualita,
										     		 @Param("idImport")Long idImport);
	    
	@Query(value = "SELECT sdc.* "
			 	 + "FROM sigas_dich_consumi sdc "				
			     + "WHERE sdc.id_anag = :idAnag "				   
				   + "AND sdc.annualita = :annualita "
				   + "AND id_import = :idImport "
				   + "AND sdc.selected_import = true "
			 + "ORDER BY sdc.annualita DESC"
		   ,  nativeQuery = true)	
	List<SigasDichConsumi>
	findConsumiByIdAnagAnnualitaIdImportSelected(@Param("idAnag")Long idAnag, 
										     	 @Param("annualita")String annualita,
										     	 @Param("idImport")Long idImport);
	
	@Query(value = "SELECT sdc.* "
		 	 + "FROM sigas_dich_consumi sdc "				
		     + "WHERE sdc.id_anag = :idAnag "				   
			   + "AND sdc.annualita = :annualita "
			   + "AND id_import = :idImport "
			   + "AND sdc.selected_import = false "
		 + "ORDER BY sdc.annualita DESC"
	   ,  nativeQuery = true)	
	List<SigasDichConsumi>
	findConsumiByIdAnagAnnualitaToConfirm(@Param("idAnag")Long idAnag, 
										  @Param("annualita")String annualita,
										  @Param("idImport")Long idImport);
	
	
}
