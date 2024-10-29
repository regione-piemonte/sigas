package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasRateo;

@Repository
public interface SigasRateoRepository extends CrudRepository<SigasRateo, Long>, JpaSpecificationExecutor<SigasRateo> {
	
	SigasRateo findByIdRateo(Long id);
	
	@Query(value =	"SELECT r.* " + 
					"FROM sigas_rateo r " +
					" INNER JOIN sigas_provincia pr ON r.id_provincia = pr.id_provincia " +
					"WHERE r.id_anag = :idAnag " +
						  " AND pr.sigla_provincia LIKE :siglaProvincia", nativeQuery = true)
	List<SigasRateo> findListaRateoByidAnagSiglaProvincia(@Param("idAnag") Long idAnag, 
														  @Param("siglaProvincia") String siglaProvincia);
	
	@Query(value =  "SELECT r.* " + 
					"FROM sigas_rateo r " +
					" INNER JOIN sigas_provincia pr ON r.id_provincia = pr.id_provincia " +
					"WHERE r.id_anag = :idAnag " +  
						  " AND pr.sigla_provincia LIKE :siglaProvincia " +
						  " AND r.mese LIKE :mese", nativeQuery = true)
	SigasRateo findRateoByidAnagSiglaProvinciaMese(@Param("idAnag") Long idAnag, 
					 							   @Param("siglaProvincia") String siglaProvincia,
												   @Param("mese") String mese);
	
	@Query(value =	"SELECT r.* " + 
					"FROM sigas_rateo r " +
					" INNER JOIN sigas_provincia pr ON r.id_provincia = pr.id_provincia " +
					"WHERE r.id_anag = :idAnag AND " +  
						  "(pr.sigla_provincia = :siglaProvincia OR :siglaProvincia = '') AND " +
						  "(r.mese = :mese OR :mese='') AND " +
						  "(r.annualita = :anno OR :anno = '')", nativeQuery = true)
	List<SigasRateo> findRateoListByParams(@Param("idAnag") Long idAnag, 
					 					   @Param("siglaProvincia") String siglaProvincia,
										   @Param("mese") String mese,
										   @Param("anno") String anno);

}
