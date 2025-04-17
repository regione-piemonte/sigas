/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.entity.SigasTipoCarrello;

@Repository
public interface SigasPaymentCartRepository extends CrudRepository<SigasPaymentCart, Long> {
	
	@Query(value="SELECT c.*, s.codice_azienda as codice_azienda " +
			"	FROM sigas_carrello_pagamenti c " +
			"		INNER JOIN sigas_anagrafica_soggetti s ON s.id_anag=c.fk_anag_soggetto AND s.selected_import = true " +
			"	WHERE c.id_carrello = ?1 ", nativeQuery = true)
	SigasPaymentCart findByIdCarrello(Long id);
	
	@Query(value="SELECT c.*, s.codice_azienda as codice_azienda " +
			"	FROM sigas_carrello_pagamenti c " +
			"		INNER JOIN sigas_anagrafica_soggetti s ON s.id_anag=c.fk_anag_soggetto AND s.selected_import = true " +
			"	WHERE c.codice_pagamento = ?1 ", nativeQuery = true)
	List<SigasPaymentCart> findByCodicePagamento(String code);
	
	@Query(value="SELECT c.*, s.codice_azienda as codice_azienda " +
			"	FROM sigas_carrello_pagamenti c " +
			"		INNER JOIN sigas_anagrafica_soggetti s ON s.id_anag=c.fk_anag_soggetto AND s.selected_import = true " +
			"	WHERE c.iuv = ?1 ", nativeQuery = true)
	List<SigasPaymentCart> findByIUV(String iuv);
	
	@Query(value="SELECT MAX(codice_pagamento) " +
			"	FROM sigas_carrello_pagamenti " +
			"	WHERE iuv = ?1 ", nativeQuery = true)
	String findPaymentCodeByIUV(String iuv);
	
	@Query(nativeQuery = true)
	List<SigasTipoCarrello> findFOPaymentTypes();
	
	
	@Query(value="SELECT c.*, s.codice_azienda as codice_azienda " +
			"	FROM sigas_utenti u " +
			"		INNER JOIN sigas_carrello_pagamenti c on u.id_utente=c.fk_utente_insert" +
			"		LEFT JOIN sigas_provincia p on c.fk_provincia=p.id_provincia" +
			"		INNER JOIN sigas_anagrafica_soggetti s ON s.id_anag=c.fk_anag_soggetto AND s.selected_import = true " +
			"	WHERE	u.codice_fiscale = ?1 " +
			"		AND c.fk_stato_carrello >= ?2 " +
			"		AND c.fk_stato_carrello <= ?3 " +
			"		AND c.anno = ?4 " +
			"		AND c.denominazione_versante = ?5 " +
			"		AND (?6 = '' OR c.codice_pagamento like ?6 )" +
			"	ORDER BY c.fk_utente_insert" ,nativeQuery = true)
	List<SigasPaymentCart> retrieveCartItems(	String cf, 
												int min_status, 
												int max_status, 
												String subjectName, 
												String year,
												String codicePagamento);
	
	@Query(value="SELECT c.*, s.codice_azienda as codice_azienda " +
			"	FROM sigas_utenti u " +
			"		INNER JOIN sigas_carrello_pagamenti c on u.id_utente=c.fk_utente_insert" +
			"		LEFT JOIN sigas_provincia p on c.fk_provincia=p.id_provincia" +
			"		INNER JOIN sigas_anagrafica_soggetti s ON s.id_anag=c.fk_anag_soggetto AND s.selected_import = true " +
			"	WHERE	u.codice_fiscale = ?1 " +
			"		AND c.fk_stato_carrello >= ?2 " +
			"		AND c.fk_stato_carrello <= ?3 " +
			"		AND c.anno = ?4 " +
			"		AND c.denominazione_versante = ?5 " +
			"		AND c.mese = ?6 " +
			"		AND c.sigla_provincia = ?7 " +
			"  		AND (?8=0 OR c.fk_tipo_carrello = ?8) " +
			"	ORDER BY c.fk_utente_insert" ,nativeQuery = true)
	List<SigasPaymentCart> retrieveCartItemsByYearMonth(String cf,
														int min_status,	int max_status, 
														String year, String subjectName,
														int month, String provincia,
														int tipoCarrello);
	
	@Query(value="SELECT c.* " +
			"	FROM sigas_utenti u " +
			"		INNER JOIN sigas_carrello_pagamenti c on u.id_utente=c.fk_utente_insert" +
			"		LEFT JOIN sigas_provincia p on c.fk_provincia=p.id_provincia" +
			"		INNER JOIN sigas_anagrafica_soggetti s ON s.id_anag=c.fk_anag_soggetto AND s.selected_import = true " +
			"	WHERE " +
			"		c.codice_pagamento = ?1 " +
			"	ORDER BY c.fk_utente_insert" ,nativeQuery = true)
	List<SigasPaymentCart> retrieveCartItemsSearch(String id);
	
	@Query(value="SELECT c.*, s.codice_azienda as codice_azienda " +
			"	FROM sigas_utenti u " +
			"		INNER JOIN sigas_carrello_pagamenti c on u.id_utente=c.fk_utente_insert " +
			"		INNER JOIN sigas_anagrafica_soggetti s ON s.id_anag=c.fk_anag_soggetto AND s.selected_import = true " +
			"	WHERE	u.codice_fiscale = ?1 " +
			"		AND c.fk_anag_soggetto = ?2 " +
			"		AND c.fk_stato_carrello <= " + SigasPaymentCart.STATO_CARRELLO_COMPLETO +
			"		AND c.anno = ?3 " +
			"		AND (?4=0 OR c.mese = ?4) " +
			"		AND (?5='' OR c.sigla_provincia = ?5) " +
			"	ORDER BY c.fk_utente_insert" +
			"	LIMIT 1" ,nativeQuery = true)
	SigasPaymentCart findCartItem(	String cf, 
									Integer idAnag, 
									String year, 
									Integer month,
									String area);
	
	@Query(value="SELECT c.*, s.codice_azienda as codice_azienda " +
			"	FROM sigas_utenti u " +
			"		INNER JOIN sigas_carrello_pagamenti c on u.id_utente=c.fk_utente_insert " +
			"		INNER JOIN sigas_anagrafica_soggetti s ON s.id_anag=c.fk_anag_soggetto AND s.selected_import = true " +
			"	WHERE	u.codice_fiscale = ?1 " +
			"		AND c.fk_anag_soggetto = ?2 " +
			"		AND c.fk_stato_carrello <= " + SigasPaymentCart.STATO_CARRELLO_COMPLETO +
			"		AND c.anno = ?3 " +
			"		AND (?4=0 OR c.mese = ?4) " +
			"		AND (?5='' OR c.sigla_provincia = ?5) " +
			"  		AND (?6=0 OR c.fk_tipo_carrello = ?6) " +
			"		AND (?7='' OR c.codice_pagamento = ?7) " +	
			"	ORDER BY c.fk_utente_insert" +
			"	LIMIT 1" ,nativeQuery = true)
	SigasPaymentCart findCartItemForStoreCart(String cf, Integer idAnag, 
											  String year, Integer month,
											  String area, Integer tipo,
											  String paymentCode);
	
	@Query(value="SELECT MAX(u.id_utente)\\:\\:int " +
			"	FROM sigas_utenti u " +
			"	WHERE	u.codice_fiscale = :cf ",nativeQuery = true)
	Integer getIdUser(@Param("cf") String cf);
	
	
	@Query(value="SELECT id_provincia\\:\\:int " +
			"	FROM sigas_provincia" +
			"	WHERE sigla_provincia = ?1 ",nativeQuery = true)
	Integer resolveAreaId(String areadCode);
	
	@Query(value="SELECT COALESCE(c.codice_pagamento\\:\\:varchar, (TO_CHAR(NOW(), 'YYYYMMDDMS')||n.new_codice_pagamento\\:\\:varchar)) AS payCode " +
			"	FROM (SELECT count(1) as new_codice_pagamento, ?1\\:\\:int AS FakeIdAnag FROM sigas_carrello_pagamenti) n " +
			"		LEFT JOIN sigas_carrello_pagamenti c ON (n.FakeIdAnag = c.fk_anag_soggetto AND c.anno = ?2 AND c.fk_stato_carrello <= " + SigasPaymentCart.STATO_CARRELLO_COMPLETO + " AND c.fk_utente_insert = ?3 ) " +
			"	WHERE n.FakeIdAnag = ?1 ",nativeQuery = true)
	List<String> getUniquePaymentCode(Integer idAnag, String year, Integer cfUtenteInsert);
	
	@Query(value="SELECT (TO_CHAR(NOW(), 'YYYYMMDDMS')||n.new_codice_pagamento\\:\\:varchar) AS payCode " +
				 "FROM (SELECT count(1) as new_codice_pagamento, ?1\\:\\:int AS FakeIdAnag FROM sigas_carrello_pagamenti) n " +
				 		"LEFT JOIN sigas_carrello_pagamenti c ON (n.FakeIdAnag = c.fk_anag_soggetto AND c.anno = ?2 AND c.fk_stato_carrello <= " + SigasPaymentCart.STATO_CARRELLO_COMPLETO + 
				 												" AND c.fk_utente_insert = ?3 ) " +
				 "WHERE n.FakeIdAnag = ?1 ",nativeQuery = true)
	String getUniquePaymentCodeForInsert(Integer idAnag, String year, Integer cfUtenteInsert);

	
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value="DELETE FROM sigas_carrello_pagamenti WHERE id_carrello = ?1 AND fk_stato_carrello <= " + SigasPaymentCart.STATO_CARRELLO_COMPLETO, nativeQuery = true)
    int deleteCartItem(Long id);
    
    @Query(value="SELECT c.* " +
			"	FROM sigas_carrello_pagamenti c " +
			"	WHERE c.fk_stato_carrello  = ?1 "
			+ " ORDER BY c.codice_pagamento DESC", nativeQuery = true)
    List<SigasPaymentCart> findByFkStatoCarrello(Integer idStato);
    
    @Query(value="SELECT * " +			
		 	 " FROM	sigas_carrello_pagamenti c " +
			 " WHERE c.fk_dich_versamento = :idVersamento ",
	   nativeQuery = true)
    List<SigasPaymentCart> getPaymentByIdVersamento(@Param("idVersamento") long idVersamento);
    
    @Query(value="SELECT c.* " +
			     "FROM sigas_carrello_pagamenti c " +
			     "WHERE	DATE_PART('day', now() - c.data_update) >= :numGiorniScadenza " +
			     	   "AND c.fk_stato_carrello = 45" , nativeQuery = true)
	List<SigasPaymentCart> retrieveCartItemsScaduti(@Param("numGiorniScadenza") int numGiorniScadenza);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value="UPDATE sigas_carrello_pagamenti " + 
    			 "SET fk_stato_carrello = :idStato " +    			
    		     "WHERE id_carrello = :idCarrello", nativeQuery = true)
    int updateCartItemStatus(@Param("idCarrello") long idCarrello, @Param("idStato") int idStato);   
    
}
