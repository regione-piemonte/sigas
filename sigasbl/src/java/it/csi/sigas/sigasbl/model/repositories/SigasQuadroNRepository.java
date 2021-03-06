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
import it.csi.sigas.sigasbl.model.entity.SigasQuadroN;

@Repository
public interface SigasQuadroNRepository extends CrudRepository<SigasQuadroN, Long> {

	@Query("select u from SigasQuadroN u where u.codiceDitta = :codiceDitta and u.sigasImport = :importUTF and provincia = :provincia")
	public List<SigasQuadroN> findByImportOrderByCodiceDitta(@Param("codiceDitta") String codiceDitta, @Param("provincia") String provincia, @Param("importUTF") SigasImportUTF idImport);
}
