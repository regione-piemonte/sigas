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

import it.csi.sigas.sigasbl.model.entity.SigasFrontespizio;
import it.csi.sigas.sigasbl.model.entity.SigasImportUTF;

@Repository
public interface SigasFrontespizioRepository extends CrudRepository<SigasFrontespizio, Long> {

	@Query("select u from SigasFrontespizio u where u.sigasImport = :importUTF order by u.codiceDitta")
	public List<SigasFrontespizio> findByImportOrderByCodiceDitta(@Param("importUTF") SigasImportUTF idImport);
	
	@Query("select distinct(u.codiceDitta) from SigasFrontespizio u where u.sigasImport = ?1")
	public List<String> findByDistinctCodiceDittaAndImportUtf(SigasImportUTF importUtf);
}
