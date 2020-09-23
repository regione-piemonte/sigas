/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasImportUTF;
import it.csi.sigas.sigasbl.model.entity.SigasQuadroF;

@Repository
public interface SigasQuadroFRepository extends CrudRepository<SigasQuadroF, Long> {

	@Query("select COALESCE(SUM(u.utenzeMc), 0) from SigasQuadroF u where u.provincia = :provincia and progRigo = :progRigo"
			+ " and u.codiceDitta = :codiceDitta and u.sigasImport = :sigasImport")
	long sumUtenzaMc(@Param("progRigo") String progRigo, @Param("provincia") String provincia, 
			@Param("codiceDitta") String codiceDitta, 
			@Param("sigasImport") SigasImportUTF sigasImportUTF);
}
