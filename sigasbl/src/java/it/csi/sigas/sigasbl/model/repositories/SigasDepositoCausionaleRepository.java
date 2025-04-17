package it.csi.sigas.sigasbl.model.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.model.entity.SigasDepositoCausionale;

@Repository
public interface SigasDepositoCausionaleRepository extends CrudRepository<SigasDepositoCausionale,Long>, JpaSpecificationExecutor<SigasDepositoCausionale> {
	
	public SigasDepositoCausionale findByIdDepositoCausionale(Long id);
	
	
	@Query(value=
			"SELECT	sdc.* " + 
			"FROM sigas_deposito_cauzionale sdc " +				
			"WHERE sdc.id_documento=:idDocumento",
		   nativeQuery = true)
	public List<SigasDepositoCausionale> findByIdDocumento(@Param("idDocumento") Long idDocumento);
	
	@Query(value=
			"SELECT	sdc.* " + 
			"FROM sigas_deposito_cauzionale sdc " +				
			"WHERE sdc.fk_carrello_pagamenti=:idCarrelloPagamenti",
		   nativeQuery = true)
	public SigasDepositoCausionale findByIdCarrelloPagamenti(@Param("idCarrelloPagamenti") Long idCarrelloPagamenti);
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_deposito_cauzionale "
				 + "SET importo = :importo "				 
				 + "WHERE id_deposito_cauzionale = :idDepositoCausionale ", nativeQuery = true)	
	void aggiornaImportDepositoCausionale(@Param("importo") BigDecimal importo,										 
										  @Param("idDepositoCausionale")Long idDepositoCausionale);
	
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_deposito_cauzionale "
				 + "SET fk_carrello_pagamenti = :idCarrelloPagamenti "				 
				 + "WHERE id_deposito_cauzionale = :idDepositoCausionale ", nativeQuery = true)	
	void aggiornaIdCarrelloPagamentiDepositoCausionale(@Param("idCarrelloPagamenti") Long idCarrelloPagamenti,										 
										  			   @Param("idDepositoCausionale")Long idDepositoCausionale);
	
	
	@Modifying(clearAutomatically = true)
    @Transactional
	@Query(value = "UPDATE sigas_deposito_cauzionale "
				 + "SET importo = :importo, "
				 + "anno_acccertamento = :annoAcccertamento, "
				 + "numero_accertamento = :numeroAcccertamento, "
				 + "numero_determina = :numeroDetermina "
				 + "WHERE id_deposito_cauzionale = :idDepositoCausionale ", nativeQuery = true)	
	void aggiornaDepositoCausionaleByParams(@Param("importo") BigDecimal importo,										 
										    @Param("idDepositoCausionale")Long idDepositoCausionale,
										    @Param("annoAcccertamento") String annoAcccertamento,
										    @Param("numeroAcccertamento") String numeroAcccertamento,
										    @Param("numeroDetermina") String numeroDetermina);

}
