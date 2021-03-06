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
import it.csi.sigas.sigasbl.model.entity.SigasQuadroG;

@Repository
public interface SigasQuadroGRepository extends CrudRepository<SigasQuadroG, Long> {

	@Query("select u from SigasQuadroG u where u.provincia = :provincia and u.codiceDitta = :codiceDitta and u.sigasImport = :sigasImport")
	List<SigasQuadroG> findUtenzaMc(@Param("provincia") String provincia, 
			@Param("codiceDitta") String codiceDitta, 
			@Param("sigasImport") SigasImportUTF sigasImportUTF);
	
}
