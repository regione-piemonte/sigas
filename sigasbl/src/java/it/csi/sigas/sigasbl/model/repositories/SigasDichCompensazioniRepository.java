package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasDichCompensazioni;

@Repository
public interface SigasDichCompensazioniRepository extends CrudRepository<SigasDichCompensazioni, Long> {
	
	@Query(value = "SELECT comp.* " + 
				   "FROM sigas_dich_compensazioni comp " + 
				   		 "INNER JOIN (SELECT MAX(data_compensazione) AS data_compensazione " + 
				                     "FROM sigas_dich_compensazioni " + 
				                     "GROUP BY id_consumi) tbl_max_comp " + 
				            "ON comp.data_compensazione  = tbl_max_comp.data_compensazione "
					+ "WHERE comp.id_consumi = :idConsumo",  
		   nativeQuery = true)
	SigasDichCompensazioni cercaUltimaCompensazioneAssociataAlConsumo(@Param("idConsumo")Long idConsumo);
	
	@Query(value = "SELECT comp.* " + 
				   "FROM sigas_dich_compensazioni comp " +			   		
				   "WHERE comp.id_consumi = :idConsumo " +
				   "ORDER BY comp.id_compensazione ASC " +
				   "LIMIT 1"
				   ,nativeQuery = true)
	SigasDichCompensazioni cercaPrimaCompensazioneAssociataAlConsumo(@Param("idConsumo")Long idConsumo);

}
