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

import it.csi.sigas.sigasbl.model.entity.SigasImportUTF;
import it.csi.sigas.sigasbl.model.entity.SigasQuadroM;

@Repository
public interface SigasQuadroMRepository extends CrudRepository<SigasQuadroM, Long> {
	
	@Query("select u from SigasQuadroM u where u.codiceDitta = :codiceDitta and u.provincia = :provincia and u.sigasImport = :importUTF and u.anno = :anno")
	List<SigasQuadroM> findByCodiceDittaAndProvinciaAndImportUTF(@Param("codiceDitta") String codiceDitta, @Param("provincia") String provincia, @Param("importUTF") SigasImportUTF annmportUTFo, 
																	@Param("anno") String anno);

	@Query("select COALESCE(SUM(u.consumi), 0) from SigasQuadroM u where u.provincia = :provincia and progRigo = :progRigo "
			+ "and u.codiceDitta = :codiceDitta and u.sigasImport = :sigasImport")
	long sumConsumi(@Param("progRigo") String progRigo, @Param("provincia") String provincia, 
			@Param("codiceDitta") String codiceDitta, 
			@Param("sigasImport") SigasImportUTF sigasImportUTF);
	
	
	List<SigasQuadroM> findByAnnoAndCodiceDittaAndProvincia(String anno, String codiceDitta, String provincia);
	
	List<SigasQuadroM> findByAliquotaAndProgRigo(double aliquota, String progRigo);
	
	List<SigasQuadroM> findBySigasImportIdImportAndCodiceDittaAndAnnoAndProvinciaAndProgRigoAndAliquota(long idImport, String codiceDitta, String anno, String provincia, double aliquota, String progRigo);
}
