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
import it.csi.sigas.sigasbl.model.entity.SigasQuadroI;

@Repository
public interface SigasQuadroIRepository extends CrudRepository<SigasQuadroI, Long> {

	@Query("select COALESCE(SUM(u.consumi), 0) from SigasQuadroI u where u.provincia = :provincia and progRigo = :progRigo "
			+ "and u.codiceDitta = :codiceDitta and u.sigasImport = :sigasImport")
	long sumConsumi(@Param("progRigo") String progRigo, @Param("provincia") String provincia, 
			@Param("codiceDitta") String codiceDitta, 
			@Param("sigasImport") SigasImportUTF sigasImportUTF);
}
