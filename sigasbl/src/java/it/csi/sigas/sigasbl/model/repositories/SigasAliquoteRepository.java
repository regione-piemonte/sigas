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

import it.csi.sigas.sigasbl.model.entity.SigasAliquote;


@Repository
public interface SigasAliquoteRepository extends CrudRepository<SigasAliquote, Long> {
	
	@Query("select u from SigasAliquote u where u.aliquota = :aliquota and u.progRigo = :progRigo and EXTRACT(YEAR FROM u.validitaStart ) <= :anno and EXTRACT(YEAR FROM u.validitaEnd ) >= :anno ")
	public SigasAliquote findByAliquotaAndProgRigo(@Param("aliquota") double Aliquota, @Param("progRigo") String progRigo, @Param("anno") Integer anno);
	
	@Query("select u from SigasAliquote u where u.progRigo = :progRigo and EXTRACT(YEAR FROM u.validitaStart ) <= :anno and EXTRACT(YEAR FROM u.validitaEnd ) >= :anno ")
	public List<SigasAliquote> findByProgRigoAndAnno( @Param("progRigo") String progRigo, @Param("anno") Integer anno);

	public SigasAliquote findByProgRigo(String progRigo);
	
	public List<SigasAliquote> findAll();
	
	public SigasAliquote findByIdAliquota(Long id);

	@Query("select u from SigasAliquote u order by idAliquota asc")
	public List<SigasAliquote> findAllOrderByIdAliquota();

	public List<SigasAliquote> findByProgRigoAndSigasTipoAliquoteIdTipoAliquota(String progRigo, Long idTipoAliquota);
	
	@Query("select sa from SigasAliquote sa  where :annoDichiarazione between extract(year from sa.validitaStart  ) and extract(year from sa.validitaEnd  )")
	public List<SigasAliquote> findAliquotaByValiditaStartValiditaEnd(@Param("annoDichiarazione") Integer annoDichiarazione );
	
}
