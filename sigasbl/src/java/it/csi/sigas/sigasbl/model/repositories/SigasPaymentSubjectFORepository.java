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

import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFOEntityGroupedCustom;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFODetailEntityCustom;
import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFOEntityCustomBase;
import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFOEntitySingleCustom;

@Repository
public interface SigasPaymentSubjectFORepository extends CrudRepository<PaymentSubjectFOEntityCustomBase, Long> {	
		
	@Query(value="SELECT codice_fiscale FROM sigas_utenti WHERE id_utente = :id ", nativeQuery = true)
	String retrieveCF(@Param("id") Integer id);
	
	/*
	@Query(value="SELECT c.annualita " +
			  	 "FROM sigas_dich_consumi c " +
			  	 	"INNER JOIN sigas_import_utf siu " +
			  	 	 	"ON c.id_import = siu.id_import " +
			  	 "WHERE siu.selected_import = true " +
				 "GROUP BY c.annualita " +
				 "ORDER BY c.annualita DESC" ,nativeQuery = true)
	*/
	@Query(value="SELECT c.annualita " +
			  	 "FROM sigas_dich_consumi c " +
			  	 "WHERE selected_import = true " +
				 "GROUP BY c.annualita " +
				 "ORDER BY c.annualita DESC" ,nativeQuery = true)
	List<String> findListaYearsPaymentFO();

	@Query(value="SELECT mese FROM sigas_mesi WHERE id=:id" ,nativeQuery = true)
	String findMonthById(@Param("id") Integer id);

	@Query(value="select DISTINCT c.anno" +
			"	FROM sigas_utenti u " +
			"		inner join sigas_carrello_pagamenti c on c.fk_utente_insert = u.id_utente " +
			"	WHERE (:cf='' OR u.codice_fiscale = :cf) " +
			"	ORDER BY c.anno DESC" ,nativeQuery = true)
	List<String> retrievePaidYears(@Param("cf") String cf);
	
	@Query(value="SELECT to_char(SUM(importo)/12, 'FM999999999.00')" + 
			"	FROM sigas_dich_versamenti x" + 
			"	WHERE x.id_anag = ?1 " + 
			"		AND x.annualita = ?2" ,nativeQuery = true)
	List<String> getUserAmountsForPrevYear(Long idAnag, String year);
	
	@Query(value="SELECT sigla_provincia FROM sigas_provincia WHERE fk_regione=1 AND sigla_provincia NOT LIKE 'ZZ'" ,nativeQuery = true)
	List<String> getAllPiemonteCounties();	

	@Query(value=
			"select" + 
			"	distinct s.id_anag as id," + 
			"	denominazione as nomeAzienda," + 
			"	s.id_anag as IdAnag," + 
			"	sc.denom_comune as Comune," + 
			"	sc.cap as Cap," + 
			"	s.indirizzo as Indirizzo," + 
			"	s.codice_azienda as CodiceAzienda," + 
			"	(denominazione||' - '||codice_azienda) as Denominazione," + 
			"	spc.sigla_provincia as SiglaProvinciaAzienda," + 
			"	spc.sigla_provincia as SiglaProvincia," + 
			"	u.codice_fiscale as CodiceFiscale, " + 
			"	s.cf_piva as CodiceFiscalePIva " + 
			" from sigas_utenti u" + 
			"	inner join sigas_utente_provvisorio up on (u.id_utente_provv = up.id_utente_provv AND up.stato='ACCETTATA')" +
			"	inner join sigas_anagrafica_utente us on u.id_utente_provv = us.id_utente_provv" + 
			"	inner join sigas_anagrafica_soggetti s on s.id_anag = us.id_anag AND s.selected_import = true " + 
			"	left join sigas_comune sc on s.fk_comune = sc.id_comune" + 
			"	LEFT join sigas_provincia spc on s.fk_provincia = spc.id_provincia" + 
			" where" + 
			"	(:cf='' OR u.codice_fiscale = :cf)",nativeQuery = true)
	List<PaymentSubjectFOEntitySingleCustom> findPaymentSubjectsFO(@Param("cf") String cf);
	
	@Query(value="SELECT" +
			"	('' || max(s.id_anag)) as id," +
			"	max(denominazione) as nomeAzienda," +
			"	max(s.id_anag) as IdAnag," +
			"	max(sc.denom_comune) as Comune," +
			"	max(sc.cap) as Cap," +
			"	max(s.indirizzo) as Indirizzo," +
			"	max(s.codice_azienda) as CodiceAzienda," +
			"	('' || max(denominazione)," +
			"	' - '," +
			"	max(s.codice_azienda)) as Denominazione," +
			"	max(sp.sigla_provincia) as SiglaProvinciaAzienda," +
			"	null as AddizionaleLiquidata" +
			" FROM	sigas_anagrafica_soggetti s" +
			"		left join sigas_provincia sp on	s.fk_provincia = sp.id_provincia" +
			"		left join sigas_comune sc on s.fk_comune = sc.id_comune" +
			"		inner join sigas_carrello_pagamenti c on c.fk_anag_soggetto = s.id_anag AND s.selected_import = true" +
			" WHERE" +
			"	c.fk_anag_soggetto = :idanag" +
			"	and c.anno = :year",nativeQuery = true)
	List<PaymentSubjectFODetailEntityCustom> getPaymentSubjectsFODetails(@Param("year") String year, 
																		 @Param("idanag") Integer idAnag);
	
	@Query(value="SELECT  " + 
			"					(''||MAX(s.id_anag)) as id, " + 
			"					MAX(denominazione) as nomeAzienda, " + 
			"					MAX(s.id_anag) as IdAnag, " + 
			"					MAX(sc.denom_comune) as Comune, " + 
			"					MAX(sc.cap) as Cap, " + 
			"					MAX(s.indirizzo) as Indirizzo, " + 
			"					MAX(s.codice_azienda) as CodiceAzienda, " + 
			"					(''||MAX(denominazione), ' - ', MAX(codice_azienda)) as Denominazione, " + 
			"					MAX(sp.sigla_provincia) as SiglaProvinciaAzienda, " + 
			"					null as AddizionaleLiquidata " + 
			"				FROM sigas_anagrafica_soggetti s " + 
			"					LEFT JOIN sigas_provincia sp on s.fk_provincia=sp.id_provincia " + 
			"					LEFT JOIN sigas_comune sc on s.fk_comune = sc.id_comune " + 
			"				WHERE	s.id_anag = :idanag AND s.selected_import = true ", nativeQuery = true)
	List<PaymentSubjectFODetailEntityCustom> getPaymentSubjectsFOByIdAnag(@Param("idanag") Integer idAnag);	
	
	@Query(value="SELECT DISTINCT " +
			"		(s.id_anag||c.sigla_provincia) as id," + 
			"		denominazione as nomeAzienda," + 
			"		s.id_anag as IdAnag," + 
			"		sc.denom_comune as Comune," + 
			"		sc.cap as Cap," + 
			"		s.indirizzo as Indirizzo," + 
			"		s.codice_azienda as CodiceAzienda," + 
			"		(denominazione||' - '||s.codice_azienda) as Denominazione," + 
			"		sp.sigla_provincia as SiglaProvinciaAzienda," + 
			"		c.sigla_provincia as SiglaProvincia," +
			"		u.codice_fiscale as CodiceFiscale," +
			"	    s.cf_piva as CodiceFiscalePIva " + 
			"	FROM sigas_utenti u " +
			"		inner join sigas_utente_provvisorio up on (u.id_utente_provv = up.id_utente_provv AND up.stato='ACCETTATA')" +
			"		INNER JOIN sigas_anagrafica_utente us on u.id_utente_provv=us.id_utente_provv" +
			"		INNER JOIN sigas_anagrafica_soggetti s ON s.id_anag=us.id_anag AND s.selected_import = true " +
			"		LEFT JOIN sigas_provincia sp on s.fk_provincia=sp.id_provincia " +
			"		LEFT JOIN sigas_comune sc on s.fk_comune = sc.id_comune " +
			"		INNER JOIN sigas_carrello_pagamenti c on c.fk_anag_soggetto=s.id_anag " +
			"	WHERE (:cf='' OR u.codice_fiscale = :cf) " +
			"		AND c.anno = :year ",nativeQuery = true)
	List<PaymentSubjectFOEntitySingleCustom> findFOSubjectsForPaymentSearch(@Param("cf") String cf, 
			@Param("year") String year);

	// used to search payments
 	@Query(value="SELECT DISTINCT " +
			"				c.codice_pagamento as id, " +
			"				s.denominazione as nomeAzienda, " +
			"				s.id_anag as IdAnag," + 
			"				s.indirizzo as Indirizzo," + 
			"				s.codice_azienda as CodiceAzienda," + 
			"				(denominazione||' - '||s.codice_azienda) as Denominazione," + 
			"				m.months AS mesi, " +
			"				counties as provincie, " +
			"				totalAmount as totale " +			
			"		FROM sigas_utenti u" + 
			"			inner join sigas_utente_provvisorio up on (u.id_utente_provv = up.id_utente_provv AND up.stato='ACCETTATA')" +
			"			INNER JOIN sigas_anagrafica_utente us on u.id_utente_provv=us.id_utente_provv" + 
			"			INNER JOIN sigas_anagrafica_soggetti s ON s.id_anag=us.id_anag AND s.selected_import = true " + 
			"			INNER JOIN sigas_carrello_pagamenti c on c.fk_anag_soggetto=s.id_anag" + 
			"			INNER JOIN (SELECT c1.codice_pagamento, REGEXP_REPLACE(string_agg(DISTINCT (m1.id||m1.mese), ' - ' order by (m1.id||m1.mese)), '[0-9]', '', 'g') AS months" + 
			"					FROM sigas_carrello_pagamenti c1 " + 
			"						INNER JOIN sigas_mesi m1 on c1.mese=m1.id" + 
			"					GROUP BY c1.codice_pagamento) m ON c.codice_pagamento=m.codice_pagamento" + 
			"			INNER JOIN (SELECT codice_pagamento, string_agg(DISTINCT (''||sigla_provincia), ' - ') AS counties" + 
			"					FROM sigas_carrello_pagamenti " + 
			"					GROUP BY codice_pagamento) cp ON c.codice_pagamento=cp.codice_pagamento" + 
			"			INNER JOIN (SELECT codice_pagamento, sum(importo) as totalAmount" + 
			"					FROM sigas_carrello_pagamenti " + 
			"					GROUP BY codice_pagamento) ca ON c.codice_pagamento=ca.codice_pagamento" + 
			"		WHERE (:cf='' OR u.codice_fiscale = :cf)" + 
			"				AND (:cf='' OR (c.fk_utente_insert in (select su.id_utente from sigas_utenti su where su.codice_fiscale = :cf)))" +				
			"				AND c.anno = :year" + 
			"				AND (:fromMonth=0 OR (c.mese BETWEEN :fromMonth AND :toMonth))" + 
			"				AND (:fromDate='' OR (c.DATA_PAGAMENTO >= :fromDate\\:\\:date and c.DATA_PAGAMENTO <= :toDate\\:\\:date))" + 
			"				AND (:area='' OR counties like '%' || :area || '%')" + 
			"				AND (:paytype=0 OR c.fk_tipo_carrello = :paytype)" + 
			"				AND (:subject='' OR s.denominazione = :subject)" +
			"				AND (c.fk_stato_carrello >= :statoIniziale)" +
			"				AND (c.fk_stato_carrello <= :statoFinale)"+
			"				AND (:codiceFiscalePIva='' OR c.cf_piva = :codiceFiscalePIva)", nativeQuery = true)
	List<PaymentSubjectFOEntityGroupedCustom> searchFOPaymentsGrouppedByPayCode(@Param("cf") String cf, 
																				@Param("year") String year, 
																				@Param("fromMonth") Integer fromMonth, 
																				@Param("toMonth") Integer toMonth, 
																				@Param("fromDate") String fromDate, 
																				@Param("toDate") String toDate, 
																				@Param("subject") String subject,
																				@Param("paytype") Integer paytype,
																				@Param("area") String area,
																				@Param("statoIniziale") int statoIniziale,
																				@Param("statoFinale") int statoFinale,
																				@Param("codiceFiscalePIva") String codiceFiscalePIva); 		
	
}
