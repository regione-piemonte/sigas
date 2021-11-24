/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.Date;
import java.util.List;

//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasPagamenti;

import org.springframework.data.repository.Repository;


public interface SigasPagamentiRepository extends Repository<SigasPagamenti, Integer> {
//@Repository
//public interface SigasPagamentiRepository extends CrudRepository<SigasPagamenti, Integer> {
	
//	@Query("select distinct(u.annoOrdInc) from SigasPagamenti u ")
//	public List<String> findAnnualitaVersamenti();
//	
//	public List<SigasPagamenti> findByAnnoOrdIncAndConciliato(String annoOrdInc, boolean concialiato);
//	
//	@Query("select sp from SigasPagamenti sp  where (desc_ord_inc like '%' || :descOrdInc || '%'   or desc_ord_inc like '%' || :codiceAzienda || '%') and TO_CHAR(data_emissione, 'YYYY-MM-DD') >= :dataIncassoDa and TO_CHAR(data_emissione, 'YYYY-MM-DD') <= :dataIncassoA ")
//	public List<SigasPagamenti> findPagamentoByDataIncassoDaDataIncassoA(@Param("descOrdInc") String descOrdInc,@Param("codiceAzienda") String codiceAzienda, @Param("dataIncassoDa") String dataIncassoDa,@Param("dataIncassoA") String dataIncassoA );
//	
//	@Query("select sp from SigasPagamenti sp  where (desc_ord_inc like '%' || :descOrdInc || '%'   or desc_ord_inc like '%' || :codiceAzienda || '%') and TO_CHAR(data_emissione, 'YYYY-MM-DD') <= :dataIncassoA ")
//	public List<SigasPagamenti> findPagamentoByDataIncassoA(@Param("descOrdInc") String descOrdInc,@Param("codiceAzienda") String codiceAzienda, @Param("dataIncassoA") String dataIncassoA );
//	
//	@Query("select sp from SigasPagamenti sp  where (desc_ord_inc like '%' || :descOrdInc || '%'   or desc_ord_inc like '%' || :codiceAzienda || '%') and TO_CHAR(data_emissione, 'YYYY-MM-DD') >= :dataIncassoDa ")
//	public List<SigasPagamenti> findPagamentoByDataIncassoDa(@Param("descOrdInc") String descOrdInc,@Param("codiceAzienda") String codiceAzienda, @Param("dataIncassoDa") String dataIncassoDa );
//	
//	
//	public List<SigasPagamenti> findByDataEmissioneLessThanEqualAndDataEmissioneGreaterThanEqualAndDescOrdIncContainingIgnoreCaseOrDescOrdIncContainingIgnoreCase(@Param("dataIncassoDa") Date dataIncassoDa,@Param("dataIncassoA") Date dataIncassoA, @Param("descOrdInc") String descOrdInc,@Param("codiceAzienda") String codiceAzienda );
//	
//	@Query("select sp from SigasPagamenti sp  where desc_ord_inc like CONCAT('%',:descOrdInc,'%') or desc_ord_inc like CONCAT('%',:codiceAzienda,'%') ")
//	public List<SigasPagamenti> findPagamentoByDecsOrdInc(@Param("descOrdInc") String descOrdInc,@Param("codiceAzienda") String codiceAzienda  );
//	
//	@Query("select sp from SigasPagamenti sp  where dataEmissione >= :dataIncassoDa ")
//	public List<SigasPagamenti> findPagamentoByDataIncassoDa(@Param("dataIncassoDa") Date dataIncassoDa );
//	
//	@Query("select sp from SigasPagamenti sp  where dataEmissione <= :dataIncassoA")
//	public List<SigasPagamenti> findPagamentoByDataIncassoA(@Param("dataIncassoA") Date dataIncassoA );
//
//	@Query("select sp from SigasPagamenti sp  where  (desc_ord_inc like CONCAT('%',:descOrdInc,'%') or desc_ord_inc like CONCAT('%',:codiceAzienda,'%')) and TO_CHAR(data_emissione, 'YYYY-MM-DD') >= :dataIncassoDa and TO_CHAR(data_emissione, 'YYYY-MM-DD') <= :dataIncassoA and conciliato= :conciliato")
//	public List<SigasPagamenti> findPagamentoByDataIncassoDaDataIncassoAAndConciliato(@Param("descOrdInc") String descOrdInc,@Param("codiceAzienda") String codiceAzienda, @Param("dataIncassoDa") String dataIncassoDa,@Param("dataIncassoA") String dataIncassoA, @Param("conciliato") boolean conciliato);
//	
//	@Query("select sp from SigasPagamenti sp  where  (desc_ord_inc like CONCAT('%',:descOrdInc,'%') or desc_ord_inc like CONCAT('%',:codiceAzienda,'%')) and TO_CHAR(data_emissione, 'YYYY-MM-DD') >= :dataIncassoDa  and conciliato= :conciliato")
//	public List<SigasPagamenti> findPagamentoByDataIncassoDaAndConciliato(@Param("descOrdInc") String descOrdInc,@Param("codiceAzienda") String codiceAzienda, @Param("dataIncassoDa") String dataIncassoDa, @Param("conciliato") boolean conciliato);
//	
//	@Query("select sp from SigasPagamenti sp  where  (desc_ord_inc like CONCAT('%',:descOrdInc,'%') or desc_ord_inc like CONCAT('%',:codiceAzienda,'%')) and TO_CHAR(data_emissione, 'YYYY-MM-DD') <= :dataIncassoA and conciliato= :conciliato")
//	public List<SigasPagamenti> findPagamentoByDataIncassoAAndConciliato(@Param("descOrdInc") String descOrdInc,@Param("codiceAzienda") String codiceAzienda,@Param("dataIncassoA") String dataIncassoA, @Param("conciliato") boolean conciliato);
	
	public List<SigasPagamenti> findSigasPagamentiBy(String[] descOrdInc,Date dataIncassoDa,Date dataIncassoA, Boolean conciliato, Boolean conciliatoParziale);
	
}