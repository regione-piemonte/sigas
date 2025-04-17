package it.csi.sigas.sigasbl.model.repositories.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.custom.UTFStandaloneEntityCustom;
import it.csi.sigas.sigasbl.model.entity.custom.UTFStandaloneEntitySoggettiMacroReport;
import it.csi.sigas.sigasbl.model.repositories.SigasUTFStandaloneRepository;

@Repository
public class SigasUTFStandaloneRepositoryImpl implements SigasUTFStandaloneRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
									   
	
	/********************************************************************************
	 * La query mostra i dati per un dato import 
	 ********************************************************************************/
	private String queryImportUTF_MARTS ="SELECT CAST(SUM(sdc.usi_industriali_1200) AS FLOAT) as tot_usi_industriali_1200, " 
											   + "CAST(SUM(sdc.usi_industriali_up) AS FLOAT) AS tot_usi_industriali_up, "
											   + "CAST(SUM(sdc.usi_civili_120) AS FLOAT) AS tot_usi_civili_120, "
											   + "CAST(SUM(sdc.usi_civili_480) AS FLOAT) AS tot_usi_civili_480, "
											   + "CAST(SUM(sdc.usi_civili_1560) AS FLOAT) AS tot_usi_civili_1560, "
											   + "CAST(SUM(sdc.usi_civili_up) AS FLOAT) AS tot_usi_civili_up, "
											   + "CAST(COALESCE(SUM(tab_nuovi_allacmenti.imposta),0) AS FLOAT) as tot_nuovi_allacciamenti, "
											   + "CAST(SUM(sdc.tot_industriali) AS FLOAT) AS tot_industriali, "
											   + "CAST(SUM(sdc.tot_civili) AS FLOAT) AS tot_civili, "
											   + "CAST(SUM(sdc.conguaglio_dich) AS FLOAT) AS tot_conguaglio_dichiarato, "
											   + "CAST(SUM(sdc.totale_dich) AS FLOAT) AS tot_totale_dichiarato, "
											   + "CAST(SUM(sdc.rateo_dich) AS FLOAT) AS tot_rateo_dichiarato, "
											   + "sdc.provincia_erogazione "
										+ "FROM sigas_dich_consumi sdc "
											+ "INNER JOIN ( "		
												 + "SELECT * " 
												 +	"FROM sigas_storico_anagrafica_soggetti ssas " 
												 +	"WHERE id_storico_anag IN ( "
																				+ "SELECT MAX(id_storico_anag) "
																				+ "FROM sigas_storico_anagrafica_soggetti ssas "  
																				//+ "WHERE id_import = :idImport " 
																				+ "GROUP BY id_anag "
																			+ ")"		
											+ ") AS tbl_anagrafica "
												+"ON sdc.id_anag = tbl_anagrafica.id_anag "
											+ "LEFT OUTER JOIN ( "
																+ "SELECT sqmu.*, sa.validita_start, sa.validita_end "
																+ "FROM sigas_quadro_m_utf sqmu "
																	+ "INNER JOIN sigas_aliquote sa "
																		+ "ON sqmu.aliquota = sa.aliquota "
																		+ "AND sqmu.prog_rigo = sa.prog_rigo "
																		 + "AND EXTRACT(YEAR FROM sa.validita_start) <= :annualita "
																		 + "AND EXTRACT(YEAR FROM sa.validita_end) >= :annualita "
																	+ "INNER JOIN sigas_tipo_aliquote sta "
																		+ "ON sa.id_tipo_aliquota = sta.id_tipo_aliquota "
																	+ "INNER JOIN sigas_tipo_consumo stc "
																		+ "ON sta.id_tipo_consumo = stc.id_tipo_consumo "
																+ "WHERE stc.campo_dich_consumo  LIKE 'tot_nuovi_allacciamenti' "
															 + ") as tab_nuovi_allacmenti "
												+ "ON tab_nuovi_allacmenti.codice_ditta = tbl_anagrafica.codice_azienda "
												+ "AND tab_nuovi_allacmenti.anno = sdc.annualita "
												+ "AND tab_nuovi_allacmenti.provincia = sdc.provincia_erogazione "
												//+ "AND EXTRACT(YEAR FROM tab_nuovi_allacmenti.validita_start  ) <= CAST(sdc.annualita AS int) "
												//+ "AND EXTRACT(YEAR FROM tab_nuovi_allacmenti.validita_end  ) >= CAST(sdc.annualita AS int) "
										+ "WHERE mod_id_consumi IS NOT NULL "
											+ "AND provincia_erogazione IS NOT NULL "
											+ "AND sdc.id_import = :idImport "
										+ "GROUP BY sdc.provincia_erogazione";
	
	
	/********************************************************************************
	 * La query mostra i dati RAGRUPPATI PER ANGRAFICA per un dato import 
	 ********************************************************************************/
	private String queryImportUTF_DETTAGLIO_SOGGETTI ="SELECT sdc.id_anag, " 
														   + "CASE "  
																+ "WHEN tbl_anagrafica.denominazione IS NOT NULL THEN tbl_anagrafica.denominazione "
																+ "ELSE s_anag.denominazione "
														   + "END AS denominazione, " 
														   + "CAST(SUM(sdc.usi_industriali_1200) AS FLOAT) as tot_usi_industriali_1200, " 
														   + "CAST(SUM(sdc.usi_industriali_up) AS FLOAT) AS tot_usi_industriali_up, "
														   + "CAST(SUM(sdc.usi_civili_120) AS FLOAT) AS tot_usi_civili_120, "
														   + "CAST(SUM(sdc.usi_civili_480) AS FLOAT) AS tot_usi_civili_480, "
														   + "CAST(SUM(sdc.usi_civili_1560) AS FLOAT) AS tot_usi_civili_1560, "
														   + "CAST(SUM(sdc.usi_civili_up) AS FLOAT) AS tot_usi_civili_up, "
														   + "CAST(COALESCE(SUM(tab_nuovi_allacmenti.imposta),0) AS FLOAT) as tot_nuovi_allacciamenti, "
														   + "CAST(SUM(sdc.tot_industriali) AS FLOAT) AS tot_industriali, "
														   + "CAST(SUM(sdc.tot_civili) AS FLOAT) AS tot_civili, "
														   + "CAST(SUM(sdc.conguaglio_dich) AS FLOAT) AS tot_conguaglio_dichiarato, "
														   + "CAST(SUM(sdc.totale_dich) AS FLOAT) AS tot_totale_dichiarato, "
														   + "CAST(SUM(sdc.rateo_dich) AS FLOAT) AS tot_rateo_dichiarato "														   
													+ "FROM sigas_dich_consumi sdc "
														+ "LEFT OUTER JOIN ( "		
																		 + "SELECT * " 
																		 +	"FROM sigas_storico_anagrafica_soggetti ssas " 
																		 +	"WHERE id_storico_anag IN ( "
																										+ "SELECT MAX(id_storico_anag) "
																										+ "FROM sigas_storico_anagrafica_soggetti ssas "  
																										//+ "WHERE id_import = :idImport " 
																										+ "GROUP BY id_anag "
																									+ ")"		
																	+ ") AS tbl_anagrafica "
																		+"ON sdc.id_anag = tbl_anagrafica.id_anag "
														+ "LEFT OUTER JOIN sigas_anagrafica_soggetti s_anag "
																		+ "ON s_anag.id_anag = sdc.id_anag "
														+ "LEFT OUTER JOIN ( "
																			+ "SELECT sqmu.*, sa.validita_start, sa.validita_end "
																			+ "FROM sigas_quadro_m_utf sqmu "
																				+ "INNER JOIN sigas_aliquote sa "
																					+ "ON sqmu.aliquota = sa.aliquota "
																					+ "AND sqmu.prog_rigo = sa.prog_rigo "																
																				+ "INNER JOIN sigas_tipo_aliquote sta "
																					+ "ON sa.id_tipo_aliquota = sta.id_tipo_aliquota "
																				+ "INNER JOIN sigas_tipo_consumo stc "
																					+ "ON sta.id_tipo_consumo = stc.id_tipo_consumo "
																			+ "WHERE stc.campo_dich_consumo  LIKE 'tot_nuovi_allacciamenti' "
																		 + ") as tab_nuovi_allacmenti "
															+ "ON tab_nuovi_allacmenti.codice_ditta = tbl_anagrafica.codice_azienda "
															+ "AND tab_nuovi_allacmenti.anno = sdc.annualita "
															+ "AND tab_nuovi_allacmenti.provincia = sdc.provincia_erogazione "
															+ "AND EXTRACT(YEAR FROM tab_nuovi_allacmenti.validita_start  ) <= CAST(sdc.annualita AS int) "
															+ "AND EXTRACT(YEAR FROM tab_nuovi_allacmenti.validita_end  ) >= CAST(sdc.annualita AS int) "
													+ "WHERE mod_id_consumi IS NOT NULL "
														+ "AND provincia_erogazione IS NOT NULL "
														+ "AND sdc.id_import = :idImport "
													+ "GROUP BY sdc.id_anag, tbl_anagrafica.denominazione";
	
	
	/************************************************************
	 * La query effettua il compare tra due import
	 ************************************************************/
	private String queryImportUTF_REPORT_MACRO_SOGGETTI = "SELECT "
																+ "tbl_report_a.id_anag AS report_a_id_anag,"
																+ "CASE "  
																	+ "WHEN tbl_report_a.denominazione IS NOT NULL THEN tbl_report_a.denominazione "
																	+ "ELSE tbl_report_a.denominazione_da_anag "
																+ "END AS report_a_denominazione, "
																+ "CAST(tbl_report_a.tot_uso_industriale AS FLOAT) AS report_a_tot_uso_industriale, "
																+ "CAST(tbl_report_a.tot_uso_civile AS FLOAT) AS report_a_tot_uso_civile, "
																+ "CAST(tbl_report_a.tot_nuovi_allacciamenti AS FLOAT) AS report_a_tot_nuovi_allacciamenti, "
																+ "CAST(tbl_report_a.tot_dichiarazione AS FLOAT) AS report_a_tot_dichiarazione, "																
																+ "CASE "
																+ 	"WHEN (tbl_report_a.tot_uso_industriale + tbl_report_a.tot_uso_civile + tbl_report_a.tot_nuovi_allacciamenti + tbl_report_a.tot_dichiarazione) != "  
																	   + "(tbl_report_b.tot_uso_industriale + tbl_report_b.tot_uso_civile + tbl_report_b.tot_nuovi_allacciamenti + tbl_report_b.tot_dichiarazione) " 
																	   + "AND tbl_report_b.tot_dichiarazione IS NOT NULL  THEN 'UPD' "
																//+ 	"WHEN tbl_report_a.tot_dichiarazione IS NOT NULL AND tbl_report_b.tot_dichiarazione IS NULL AND tbl_report_a.anag_nuova_da_import = false THEN 'UPD' "
																+	"WHEN tbl_report_a.anag_nuova_da_import = true THEN 'INS' "
																+ 	"WHEN tbl_report_a.anag_modificata = true THEN 'UPD' "																
																+ 	"ELSE 'NOP'"
																+ "END AS azione, "
																+ "tbl_report_a.selected_import AS report_a_selected_import "
														+ "FROM "
																+ "("
																		+ "SELECT "
																				+ "CASE "
																				  + "WHEN sdc.selected_import = true THEN true "
																				  + "WHEN s_anag.selected_import = true AND s_anag.id_import = :idImportA THEN true "
																				  + "ELSE false "
																				+ "END as selected_import, "
																				+ "CASE " 
																					+ "WHEN s_anag.selected_import = false THEN true " // Import successivo non confermato
																					+ "WHEN s_anag.selected_import IS NULL THEN true " // primo import non confermato
																					+ "ELSE false " 
																				+ "END AS anag_nuova_da_import, "
																				+ "CASE "  
																					+ "WHEN UPPER(tbl_anagrafica.denominazione) not like upper(s_anag.denominazione)  THEN true "
																					+ "WHEN UPPER(tbl_anagrafica.codice_azienda) not like upper(s_anag.codice_azienda)  THEN true "
																					+ "WHEN UPPER(tbl_anagrafica.indirizzo) not like upper(s_anag.indirizzo)  THEN true "
																					+ "ELSE false "  
																					+ "END AS anag_modificata, "
																				+ "sdc.id_anag, "
																				+ "tbl_anagrafica.denominazione, "
																				+ "s_anag.denominazione as denominazione_da_anag, "
																				+ "s_anag.indirizzo as indirizzo_da_anag, "
																				+ "s_anag.codice_azienda as codice_azienda_da_anag, "
																				+ "SUM(sdc.usi_industriali_1200) + SUM(sdc.usi_industriali_up) AS tot_uso_industriale, "
																				+ "SUM(sdc.usi_civili_120) + SUM(sdc.usi_civili_480) + SUM(sdc.usi_civili_1560) + SUM(sdc.usi_civili_up) as tot_uso_civile, "
																				+ "SUM(sdc.tot_nuovi_allacciamenti) as tot_nuovi_allacciamenti, "
																				+ "SUM(sdc.conguaglio_dich) + SUM(sdc.totale_dich) + SUM(sdc.rateo_dich) as tot_dichiarazione "
																		+ "FROM sigas_dich_consumi sdc "
																		+ "LEFT OUTER JOIN ( "		
																						 + "SELECT * " 
																						 +	"FROM sigas_storico_anagrafica_soggetti ssas " 
																						 +	"WHERE id_storico_anag IN ( "
																														+ "SELECT MAX(id_storico_anag) "
																														+ "FROM sigas_storico_anagrafica_soggetti ssas "																														 
																														+ "GROUP BY id_anag "
																													+ ")"		
																					+ ") AS tbl_anagrafica "
																						+"ON sdc.id_anag = tbl_anagrafica.id_anag "
																		+ "LEFT OUTER JOIN sigas_anagrafica_soggetti s_anag "
																			+ "ON s_anag.id_anag = sdc.id_anag "
																		+ "LEFT OUTER JOIN ("
																							  + "SELECT sqmu.codice_ditta, sqmu.anno, sqmu.provincia, sa.validita_start, sa.validita_end "
																							  + "FROM sigas_quadro_m_utf sqmu "
																							  		+ "INNER JOIN sigas_aliquote sa "
																							  			+ "ON sqmu.aliquota = sa.aliquota "
																						  					+ "AND sqmu.prog_rigo = sa.prog_rigo "
																						  				    + "AND EXTRACT(YEAR FROM sa.validita_start) <= :annualita "
																										    + "AND EXTRACT(YEAR FROM sa.validita_end) >= :annualita "
																						  			+ "INNER JOIN sigas_tipo_aliquote sta "
																						  				+ "ON sa.id_tipo_aliquota = sta.id_tipo_aliquota "
																						  			+ "INNER JOIN sigas_tipo_consumo stc "
																						  				+ "ON sta.id_tipo_consumo = stc.id_tipo_consumo "
																						  	  + "WHERE stc.campo_dich_consumo  LIKE 'tot_nuovi_allacciamenti' "
																					  	 + ") as tab_nuovi_allacmenti "
																				+ "ON tab_nuovi_allacmenti.codice_ditta = tbl_anagrafica.codice_azienda "
																					+ "AND tab_nuovi_allacmenti.anno = sdc.annualita "
																					+ "AND tab_nuovi_allacmenti.provincia = sdc.provincia_erogazione "																					
																		+ "WHERE mod_id_consumi IS NOT NULL "
																			  + "AND provincia_erogazione IS NOT NULL "
																			  + "AND sdc.id_import = :idImportA	"
																		+ "GROUP BY "																
																			+ "sdc.id_anag, "
																			+ "sdc.selected_import, "
																			+ "tbl_anagrafica.denominazione, "
																			+ "tbl_anagrafica.codice_azienda, " 
																			+ "tbl_anagrafica.indirizzo, " 
																			+ "s_anag.denominazione, "
																			+ "s_anag.indirizzo, "
																			+ "s_anag.codice_azienda, "
																			+ "s_anag.selected_import, "
																			+ "s_anag.id_import, "
																			+ "s_anag.id_anag "
																  + ") AS tbl_report_a "
														+ "LEFT OUTER JOIN "
																   + "("
																		+ "SELECT "																		
																				+ "sdc.id_anag, "
																				+ "tbl_anagrafica.denominazione, "
																				+ "tbl_anagrafica.indirizzo, "
																				+ "tbl_anagrafica.codice_azienda, "
																				+ "SUM(sdc.usi_industriali_1200) + SUM(sdc.usi_industriali_up) AS tot_uso_industriale, "
																				+ "SUM(sdc.usi_civili_120) + SUM(sdc.usi_civili_480) + SUM(sdc.usi_civili_1560) + SUM(sdc.usi_civili_up) as tot_uso_civile, "
																				+ "SUM(sdc.tot_nuovi_allacciamenti) as tot_nuovi_allacciamenti, "
																				+ "SUM(sdc.conguaglio_dich) + SUM(sdc.totale_dich) + SUM(sdc.rateo_dich) AS tot_dichiarazione "
																		+ "FROM sigas_dich_consumi sdc "
																		+ "LEFT OUTER JOIN ( "		
																						 + "SELECT * " 
																						 +	"FROM sigas_storico_anagrafica_soggetti ssas " 
																						 +	"WHERE id_storico_anag IN ( "
																														+ "SELECT MAX(id_storico_anag) "
																														+ "FROM sigas_storico_anagrafica_soggetti ssas "
																														+ "WHERE id_import = :idImportB "
																														+ "GROUP BY id_anag "
																													+ ")"		
																					+ ") AS tbl_anagrafica "
																						+"ON sdc.id_anag = tbl_anagrafica.id_anag "
																			+ "LEFT OUTER JOIN ("
																								  + "SELECT sqmu.codice_ditta, sqmu.anno, sqmu.provincia, sa.validita_start, sa.validita_end "
																								  + "FROM sigas_quadro_m_utf sqmu "
																									  	+ "INNER JOIN sigas_aliquote sa "
																									  		+ "ON sqmu.aliquota = sa.aliquota "
																									  			+ "AND sqmu.prog_rigo = sa.prog_rigo "
																									  			+ "AND EXTRACT(YEAR FROM sa.validita_start) <= :annualita "
																											    + "AND EXTRACT(YEAR FROM sa.validita_end) >= :annualita "
																									  	+ "INNER JOIN sigas_tipo_aliquote sta "
																									  		+ "ON sa.id_tipo_aliquota = sta.id_tipo_aliquota "
																									  	+ "INNER JOIN sigas_tipo_consumo stc "
																									  		+ "ON sta.id_tipo_consumo = stc.id_tipo_consumo "
																								 + "WHERE stc.campo_dich_consumo  LIKE 'tot_nuovi_allacciamenti' "
																							+ ") AS tab_nuovi_allacmenti "
																					+ "ON tab_nuovi_allacmenti.codice_ditta = tbl_anagrafica.codice_azienda "
																						+ "AND tab_nuovi_allacmenti.anno = sdc.annualita "
																						+ "AND tab_nuovi_allacmenti.provincia = sdc.provincia_erogazione "																						
																		+ "WHERE mod_id_consumi IS NOT NULL "
																			   + "AND provincia_erogazione IS NOT NULL "
																			   + "AND sdc.id_import = :idImportB "
																		+ "GROUP BY "																		
																			  + "sdc.id_anag, "
																			  + "tbl_anagrafica.denominazione, "
																			  + "tbl_anagrafica.indirizzo, "
																			  + "tbl_anagrafica.codice_azienda "
																	+ ") AS tbl_report_b "
																+ "ON tbl_report_a.id_anag = tbl_report_b.id_anag";
	
	/*******************************************************************************
	 * La query viene eseguita quando l'import è UNICO per una data annualità	
	 *******************************************************************************/
		
	private String queryImportUTF_REPORT_MACRO_SOGGETTI_ONE_IMPORT = "SELECT "
																			+ "tbl_report_a.id_anag AS report_a_id_anag,"
																			+ "CASE "  
																				+ "WHEN tbl_report_a.denominazione IS NOT NULL THEN tbl_report_a.denominazione "
																				+ "ELSE tbl_report_a.denominazione_da_anag "
																			+ "END AS report_a_denominazione, "
																			+ "CAST(tbl_report_a.tot_uso_industriale AS FLOAT) AS report_a_tot_uso_industriale, "
																			+ "CAST(tbl_report_a.tot_uso_civile AS FLOAT) AS report_a_tot_uso_civile, "
																			+ "CAST(tbl_report_a.tot_nuovi_allacciamenti AS FLOAT) AS report_a_tot_nuovi_allacciamenti, "
																			+ "CAST(tbl_report_a.tot_dichiarazione AS FLOAT) AS report_a_tot_dichiarazione, "																
																			+ "CASE "
																			//+ 	"WHEN tbl_report_a.tot_dichiarazione != tbl_report_b.tot_dichiarazione AND tbl_report_b.tot_dichiarazione IS NOT NULL THEN 'UPD' "
																				+ 	"WHEN (tbl_report_a.tot_uso_industriale + tbl_report_a.tot_uso_civile + tbl_report_a.tot_nuovi_allacciamenti + tbl_report_a.tot_dichiarazione) != "  
																					   + "(tbl_report_b.tot_uso_industriale + tbl_report_b.tot_uso_civile + tbl_report_b.tot_nuovi_allacciamenti + tbl_report_b.tot_dichiarazione) " 
																					   + "AND tbl_report_b.tot_dichiarazione IS NOT NULL  THEN 'UPD' "
																			//+ 	"WHEN tbl_report_a.tot_dichiarazione IS NOT NULL AND tbl_report_b.tot_dichiarazione IS NULL AND tbl_report_a.anag_nuova_da_import = false THEN 'UPD' "
																			+ 	"WHEN tbl_report_a.anag_nuova_da_import = true THEN 'INS' "
																			+ 	"WHEN tbl_report_a.anag_modificata = true THEN 'UPD' "																			
																			+ 	"ELSE 'NOP'"
																			+ "END AS azione, "
																			+ "tbl_report_a.selected_import AS report_a_selected_import "
																	+ "FROM "
																			+ "("
																					+ "SELECT "
																							+ "CASE "
																							  + "WHEN sdc.selected_import = true THEN true "
																							  + "WHEN s_anag.selected_import = true AND s_anag.id_import = :idImportA THEN true "
																							  + "ELSE false "
																							+ "END as selected_import, "
																							+ "CASE " 
																								+ "WHEN s_anag.selected_import = false THEN true " // Import successivo non confermato
																								+ "WHEN s_anag.selected_import IS NULL THEN true " // primo import non confermato
																								+ "ELSE false " 
																							+ "END AS anag_nuova_da_import, "
																							+ "CASE "  
																								+ "WHEN UPPER(tbl_anagrafica.denominazione) not like upper(s_anag.denominazione)  THEN true "
																								+ "WHEN UPPER(tbl_anagrafica.codice_azienda) not like upper(s_anag.codice_azienda)  THEN true "
																								+ "WHEN UPPER(tbl_anagrafica.indirizzo) not like upper(s_anag.indirizzo)  THEN true "
																								+ "ELSE false "  
																							+ "END AS anag_modificata, "
																							+ "sdc.id_anag, "
																							+ "tbl_anagrafica.denominazione, "
																							+ "s_anag.denominazione as denominazione_da_anag, "
																							+ "s_anag.indirizzo as indirizzo_da_anag, "
																							+ "s_anag.codice_azienda as codice_azienda_da_anag, "
																							+ "SUM(sdc.usi_industriali_1200) + SUM(sdc.usi_industriali_up) AS tot_uso_industriale, "
																							+ "SUM(sdc.usi_civili_120) + SUM(sdc.usi_civili_480) + SUM(sdc.usi_civili_1560) + SUM(sdc.usi_civili_up) as tot_uso_civile, "
																							+ "SUM(sdc.tot_nuovi_allacciamenti) as tot_nuovi_allacciamenti, "
																							+ "SUM(sdc.conguaglio_dich) + SUM(sdc.totale_dich) + SUM(sdc.rateo_dich) as tot_dichiarazione "
																					+ "FROM sigas_dich_consumi sdc "
																					+ "LEFT OUTER JOIN ( "		
																									 + "SELECT * " 
																									 +	"FROM sigas_storico_anagrafica_soggetti ssas " 
																									 +	"WHERE id_storico_anag IN ( "
																																	+ "SELECT MAX(id_storico_anag) "
																																	+ "FROM sigas_storico_anagrafica_soggetti ssas "																																	 
																																	+ "GROUP BY id_anag "
																																+ ")"		
																								+ ") AS tbl_anagrafica "
																									+"ON sdc.id_anag = tbl_anagrafica.id_anag AND tbl_anagrafica.id_import = :idImportA "
																					+ "LEFT OUTER JOIN sigas_anagrafica_soggetti s_anag "
																						+ "ON s_anag.id_anag = sdc.id_anag "
																					+ "LEFT OUTER JOIN ("
																										  + "SELECT sqmu.codice_ditta, sqmu.anno, sqmu.provincia, sa.validita_start, sa.validita_end "
																										  + "FROM sigas_quadro_m_utf sqmu "
																										  		+ "INNER JOIN sigas_aliquote sa "
																										  			+ "ON sqmu.aliquota = sa.aliquota "
																									  					+ "AND sqmu.prog_rigo = sa.prog_rigo "
																									  				    + "AND EXTRACT(YEAR FROM sa.validita_start) <= :annualita "
																													    + "AND EXTRACT(YEAR FROM sa.validita_end) >= :annualita "
																									  			+ "INNER JOIN sigas_tipo_aliquote sta "
																									  				+ "ON sa.id_tipo_aliquota = sta.id_tipo_aliquota "
																									  			+ "INNER JOIN sigas_tipo_consumo stc "
																									  				+ "ON sta.id_tipo_consumo = stc.id_tipo_consumo "
																									  	  + "WHERE stc.campo_dich_consumo  LIKE 'tot_nuovi_allacciamenti' "
																								  	 + ") as tab_nuovi_allacmenti "
																							+ "ON tab_nuovi_allacmenti.codice_ditta = tbl_anagrafica.codice_azienda "
																								+ "AND tab_nuovi_allacmenti.anno = sdc.annualita "
																								+ "AND tab_nuovi_allacmenti.provincia = sdc.provincia_erogazione "																								
																					+ "WHERE mod_id_consumi IS NOT NULL "
																						  + "AND provincia_erogazione IS NOT NULL "
																						  + "AND sdc.id_import = :idImportA	"
																					+ "GROUP BY "																
																						+ "sdc.id_anag, "
																						+ "sdc.selected_import, "
																						+ "tbl_anagrafica.denominazione, "
																						+ "tbl_anagrafica.codice_azienda, "
																						+ "tbl_anagrafica.indirizzo, "
																						+ "s_anag.denominazione, "
																						+ "s_anag.indirizzo, "
																						+ "s_anag.codice_azienda, "
																						+ "s_anag.selected_import, "
																						+ "s_anag.id_import, "
																						+ "s_anag.id_anag "
																			  + ") AS tbl_report_a "
																	+ "LEFT OUTER JOIN "
																			   + "("
																					+ "SELECT "																		
																							+ "sdc.id_anag, "
																							+ "tbl_anagrafica.denominazione, "
																							+ "tbl_anagrafica.indirizzo, "
																							+ "tbl_anagrafica.codice_azienda, "
																							+ "SUM(sdc.usi_industriali_1200) + SUM(sdc.usi_industriali_up) AS tot_uso_industriale, "
																							+ "SUM(sdc.usi_civili_120) + SUM(sdc.usi_civili_480) + SUM(sdc.usi_civili_1560) + SUM(sdc.usi_civili_up) as tot_uso_civile, "
																							+ "SUM(sdc.tot_nuovi_allacciamenti) as tot_nuovi_allacciamenti, "
																							+ "SUM(sdc.conguaglio_dich) + SUM(sdc.totale_dich) + SUM(sdc.rateo_dich) AS tot_dichiarazione "
																					+ "FROM sigas_dich_consumi sdc "
																					+ "LEFT OUTER JOIN ( "		
																									 + "SELECT * " 
																									 +	"FROM sigas_storico_anagrafica_soggetti ssas " 
																									 +	"WHERE id_storico_anag IN ( "
																																	+ "SELECT MAX(id_storico_anag) "
																																	+ "FROM sigas_storico_anagrafica_soggetti ssas "																																	 
																																	+ "GROUP BY id_anag "
																																+ ")"		
																								+ ") AS tbl_anagrafica "
																									+"ON sdc.id_anag = tbl_anagrafica.id_anag "
																						+ "LEFT OUTER JOIN ("
																											  + "SELECT sqmu.codice_ditta, sqmu.anno, sqmu.provincia, sa.validita_start, sa.validita_end "
																											  + "FROM sigas_quadro_m_utf sqmu "
																												  	+ "INNER JOIN sigas_aliquote sa "
																												  		+ "ON sqmu.aliquota = sa.aliquota "
																												  			+ "AND sqmu.prog_rigo = sa.prog_rigo "
																												  			+ "AND EXTRACT(YEAR FROM sa.validita_start) <= :annualita "
																														    + "AND EXTRACT(YEAR FROM sa.validita_end) >= :annualita "
																												  	+ "INNER JOIN sigas_tipo_aliquote sta "
																												  		+ "ON sa.id_tipo_aliquota = sta.id_tipo_aliquota "
																												  	+ "INNER JOIN sigas_tipo_consumo stc "
																												  		+ "ON sta.id_tipo_consumo = stc.id_tipo_consumo "
																											 + "WHERE stc.campo_dich_consumo  LIKE 'tot_nuovi_allacciamenti' "
																										+ ") AS tab_nuovi_allacmenti "
																								+ "ON tab_nuovi_allacmenti.codice_ditta = tbl_anagrafica.codice_azienda "
																									+ "AND tab_nuovi_allacmenti.anno = sdc.annualita "
																									+ "AND tab_nuovi_allacmenti.provincia = sdc.provincia_erogazione "																									
																					+ "WHERE mod_id_consumi IS NOT NULL "
																						   + "AND provincia_erogazione IS NOT NULL "																						   
																					+ "GROUP BY "																		
																						  + "sdc.id_anag, "
																						  + "tbl_anagrafica.denominazione, "
																						  + "tbl_anagrafica.indirizzo, "
																						  + "tbl_anagrafica.codice_azienda "
																				+ ") AS tbl_report_b "
																			+ "ON tbl_report_a.id_anag = tbl_report_b.id_anag";
																			  
														
	
	private String queryImportUTF_DETTAGLIO_SOGGETTI_BY_ID_ANAG = "SELECT "
																		+ "sdc.id_anag, "
																		+ "CASE "  
																		+ "WHEN tbl_anagrafica.denominazione IS NOT NULL THEN tbl_anagrafica.denominazione "
																			+ "ELSE s_anag.denominazione "
																		+ "END AS denominazione, "
																		+ "CAST(sdc.usi_industriali_1200 AS FLOAT) AS usi_industriali_1200 , "
																		+ "CAST(sdc.usi_industriali_up AS FLOAT) AS usi_industriali_up, "
																		+ "CAST(sdc.usi_civili_120 AS FLOAT) AS usi_civili_120, "
																		+ "CAST(sdc.usi_civili_480 AS FLOAT) AS usi_civili_480, "
																		+ "CAST(sdc.usi_civili_1560 AS FLOAT) AS usi_civili_1560, "
																		+ "CAST(sdc.usi_civili_up AS FLOAT) AS usi_civili_up, "
																		+ "CAST(tab_nuovi_allacmenti.imposta AS FLOAT) AS imposta, "
																		+ "CAST(sdc.tot_industriali AS FLOAT) AS tot_industriali, "
																		+ "CAST(sdc.tot_civili AS FLOAT) AS tot_civili, "
																		+ "CAST(sdc.conguaglio_dich AS FLOAT) AS conguaglio_dich, "
																		+ "CAST(sdc.totale_dich AS FLOAT) AS totale_dich, "
																		+ "CAST(sdc.rateo_dich AS FLOAT) AS rateo_dich, "
																		+ "sdc.provincia_erogazione, "
																		+ "sdc.id_consumi, "
																		+ "CAST(sdc.rettifiche AS FLOAT) as rettifiche, "
																		+ "CAST(sdc.arrotondamenti AS FLOAT) as arrotondamenti "
																+ "FROM sigas_dich_consumi sdc "
																  + "LEFT OUTER JOIN ( "		
																					 + "SELECT * " 
																					 +	"FROM sigas_storico_anagrafica_soggetti ssas " 
																					 +	"WHERE id_storico_anag IN ( "
																													+ "SELECT MAX(id_storico_anag) "
																													+ "FROM sigas_storico_anagrafica_soggetti ssas "  
																													//+ "WHERE id_import = :idImport " 
																													+ "GROUP BY id_anag "
																												+ ")"		
																			  + ") AS tbl_anagrafica "
																				  +"ON sdc.id_anag = tbl_anagrafica.id_anag "
																	+ "LEFT OUTER JOIN sigas_anagrafica_soggetti s_anag "
																					+ "ON s_anag.id_anag = sdc.id_anag "
																	+ "LEFT OUTER JOIN ("
																					 + "SELECT sqmu.*, sa.validita_start, sa.validita_end "
																					 + "FROM sigas_quadro_m_utf sqmu "
																						+ "INNER JOIN sigas_aliquote sa "
																							+ "ON sqmu.aliquota = sa.aliquota "
																							+ "AND sqmu.prog_rigo = sa.prog_rigo "																					
																						+ "INNER JOIN sigas_tipo_aliquote sta "
																							+ "ON sa.id_tipo_aliquota = sta.id_tipo_aliquota "
																						+ "INNER JOIN sigas_tipo_consumo stc "
																							+ "ON sta.id_tipo_consumo = stc.id_tipo_consumo "
																					  + "WHERE stc.campo_dich_consumo  LIKE 'tot_nuovi_allacciamenti' "
																					+ ") as tab_nuovi_allacmenti "
																		  + "ON tab_nuovi_allacmenti.codice_ditta = tbl_anagrafica.codice_azienda "
																		  	 + "AND tab_nuovi_allacmenti.anno = sdc.annualita "
																		  	 + "AND tab_nuovi_allacmenti.provincia = sdc.provincia_erogazione "
																		  	 + "AND EXTRACT(YEAR FROM tab_nuovi_allacmenti.validita_start  ) <= CAST(sdc.annualita AS int) "
																		  	 + "AND EXTRACT(YEAR FROM tab_nuovi_allacmenti.validita_end  ) >= CAST(sdc.annualita AS int) "
																+ "WHERE mod_id_consumi IS NOT NULL "
																	  + "AND provincia_erogazione IS NOT NULL "
																	  + "AND sdc.id_import = :idImport "
																	  + "AND sdc.id_anag = :idAnag";

	@Override
	public List<UTFStandaloneEntityCustom> getUTFReportByIdImport(Long idImport, Integer annualita) {
		
		List<UTFStandaloneEntityCustom> utfStandaloneEntityCustomList = new ArrayList<>();
		
		/********************************************
		 * Setup parametri di input - INIZIO
		 ********************************************/
		
		//ID IMPORT
		String queryToExec = queryImportUTF_MARTS.replaceAll(":idImport", idImport.toString());
		
		//Annualità
		queryToExec = queryToExec.replaceAll(":annualita", annualita.toString());		
		
		/********************************************
		 * Setup parametri di input - FINE
		 ********************************************/
		
		List<Object[]> res = entityManager.createNativeQuery(queryToExec).getResultList();
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext())
		{
			UTFStandaloneEntityCustom rowUTFStandaloneEntityCustom = new UTFStandaloneEntityCustom();
			Object[] line = it.next();
			
			//idAnag
			//if(line[0]!=null)
			//{
			//	rowUTFStandaloneEntityCustom.setIdAnag((Integer)line[0]);
			//}
			
			//Denominazione
			//if(line[1]!=null)
			//{
			//	rowUTFStandaloneEntityCustom.setDenominazione((String)line[1]);
			//}
			
			//tot_usi_industriali_1200
			if(line[0]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_industriali_1200((Double)line[0]);
			}
			
			//tot_usi_industriali_up
			if(line[1]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_industriali_up((Double)line[1]);
			}
			
			//tot_usi_civili_120
			if(line[2]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_120((Double)line[2]);				
			}
			
			//tot_usi_civili_480
			if(line[3]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_480((Double)line[3]);								
			}
			
			//tot_usi_civili_1560
			if(line[4]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_1560((Double)line[4]);												
			}
			
			//tot_usi_civili_up
			if(line[5]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_up((Double)line[5]);																
			}
			
			//tot_nuovi_allacciamenti
			if(line[6]!=null)
			{
				rowUTFStandaloneEntityCustom.setTot_nuovi_allacciamenti((Double)line[6]);																				
			}
			
			//tot_industriali
			if(line[7]!=null)
			{
				rowUTFStandaloneEntityCustom.setTot_industriali((Double)line[7]);																								
			}
			
			//tot_civili
			if(line[8]!=null)
			{
				rowUTFStandaloneEntityCustom.setTot_civili((Double)line[8]);																												
			}
			
			//tot_conguaglio_dichiarato
			if(line[9]!=null)
			{
				rowUTFStandaloneEntityCustom.setConguaglio_dich((Double)line[9]);																																
			}
			
			//tot_totale_dichiarato
			if(line[10]!=null)
			{
				rowUTFStandaloneEntityCustom.setTotaleDich((Double)line[10]);																																				
			}
			
			//tot_rateo_dichiarato
			if(line[11]!=null)
			{
				rowUTFStandaloneEntityCustom.setRateo_dich((Double)line[11]);																																								
			}
			
			//prov erogazione
			if(line[12]!=null)
			{
				rowUTFStandaloneEntityCustom.setProvErogazione((String)line[12]);																																												
			}
						
			utfStandaloneEntityCustomList.add(rowUTFStandaloneEntityCustom);
		}
		
		return utfStandaloneEntityCustomList;
	}
	
	@Override
	public List<UTFStandaloneEntityCustom> getUTFReportDettaglioSoggettiByIdImport(Long idImport) {
		
		List<UTFStandaloneEntityCustom> utfStandaloneEntityCustomList = new ArrayList<>();
		
		/********************************************
		 * Setup parametri di input - INIZIO
		 ********************************************/
		
		//ID IMPORT
		String queryToExec = queryImportUTF_DETTAGLIO_SOGGETTI.replaceAll(":idImport", idImport.toString());
		
		/********************************************
		 * Setup parametri di input - FINE
		 ********************************************/
		
		List<Object[]> res = entityManager.createNativeQuery(queryToExec).getResultList();
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext())
		{
			UTFStandaloneEntityCustom rowUTFStandaloneEntityCustom = new UTFStandaloneEntityCustom();
			Object[] line = it.next();
			
			//idAnag
			if(line[0]!=null)
			{
				rowUTFStandaloneEntityCustom.setIdAnag((Integer)line[0]);
			}
			
			//Denominazione
			if(line[1]!=null)
			{
				rowUTFStandaloneEntityCustom.setDenominazione((String)line[1]);
			}
			
			//tot_usi_industriali_1200
			if(line[2]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_industriali_1200((Double)line[2]);
			}
			
			//tot_usi_industriali_up
			if(line[3]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_industriali_up((Double)line[3]);
			}
			
			//tot_usi_civili_120
			if(line[4]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_120((Double)line[4]);				
			}
			
			//tot_usi_civili_480
			if(line[5]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_480((Double)line[5]);								
			}
			
			//tot_usi_civili_1560
			if(line[6]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_1560((Double)line[6]);												
			}
			
			//tot_usi_civili_up
			if(line[7]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_up((Double)line[7]);																
			}
			
			//tot_nuovi_allacciamenti
			if(line[8]!=null)
			{
				rowUTFStandaloneEntityCustom.setTot_nuovi_allacciamenti((Double)line[8]);																				
			}
			
			//tot_industriali
			if(line[9]!=null)
			{
				rowUTFStandaloneEntityCustom.setTot_industriali((Double)line[9]);																								
			}
			
			//tot_civili
			if(line[10]!=null)
			{
				rowUTFStandaloneEntityCustom.setTot_civili((Double)line[10]);																												
			}
			
			//tot_conguaglio_dichiarato
			if(line[11]!=null)
			{
				rowUTFStandaloneEntityCustom.setConguaglio_dich((Double)line[11]);																																
			}
			
			//tot_totale_dichiarato
			if(line[12]!=null)
			{
				rowUTFStandaloneEntityCustom.setTotaleDich((Double)line[12]);																																				
			}
			
			//tot_rateo_dichiarato
			if(line[13]!=null)
			{
				rowUTFStandaloneEntityCustom.setRateo_dich((Double)line[13]);																																								
			}
			
			//prov erogazione
			/*
			if(line[14]!=null)
			{
				rowUTFStandaloneEntityCustom.setProvErogazione((String)line[14]);																																												
			}
			*/
						
			utfStandaloneEntityCustomList.add(rowUTFStandaloneEntityCustom);
		}
		
		return utfStandaloneEntityCustomList;
	}	
	
	@Override
	public List<UTFStandaloneEntitySoggettiMacroReport> getUTFSoggettiMacroReportByIdReport(Long idImportA, Long idImportB, Integer annualita) {
		
		List<UTFStandaloneEntitySoggettiMacroReport> utfStandaloneEntitySoggettiMacroReportList = new ArrayList<>();
		
		/********************************************
		 * Setup parametri di input - INIZIO
		 ********************************************/
		
		String queryToExec;
		if(idImportB != null) {
			
			//ID IMPORT A
			queryToExec = queryImportUTF_REPORT_MACRO_SOGGETTI.replaceAll(":idImportA", idImportA.toString());
			
			//ID IMPORT B
			queryToExec = queryToExec.replaceAll(":idImportB", idImportB.toString());
			
			//Annualità
			queryToExec = queryToExec.replaceAll(":annualita", annualita.toString());
			
		} else {
			
			//ID IMPORT A
			queryToExec = this.queryImportUTF_REPORT_MACRO_SOGGETTI_ONE_IMPORT.replaceAll(":idImportA", idImportA.toString());
			
			//Annualità
			queryToExec = queryToExec.replaceAll(":annualita", annualita.toString());
		}	
		
		
		/********************************************
		 * Setup parametri di input - FINE
		 ********************************************/
			
		
		List<Object[]> res = entityManager.createNativeQuery(queryToExec).getResultList();
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext())
		{
			UTFStandaloneEntitySoggettiMacroReport rowUTFStandaloneEntitySoggettiMacroReport = new UTFStandaloneEntitySoggettiMacroReport();
			Object[] line = it.next();
			
			//idAnag
			if(line[0]!=null)
			{
				rowUTFStandaloneEntitySoggettiMacroReport.setId_anag((Integer)line[0]);
			}
			
			//Denominazione
			if(line[1]!=null)
			{
				rowUTFStandaloneEntitySoggettiMacroReport.setDenominazione((String)line[1]);
			}
			
			//tot_uso_industriale
			if(line[2]!=null)
			{				
				rowUTFStandaloneEntitySoggettiMacroReport.setTot_uso_industriale((Double)line[2]);
				
			}
			
			//tot_uso_civile
			if(line[3]!=null)
			{
				rowUTFStandaloneEntitySoggettiMacroReport.setTot_uso_civile((Double)line[3]);
			}
			
			//tot_nuovi_allacciamenti
			if(line[4]!=null)
			{
				rowUTFStandaloneEntitySoggettiMacroReport.setTot_nuovi_allacciamenti((Double)line[4]);				
			}
			
			//tot_dichiarazione
			if(line[5]!=null)
			{
				rowUTFStandaloneEntitySoggettiMacroReport.setTot_dichiarazione((Double)line[5]);								
			}
			
			//azione
			if(line[6]!=null)
			{
				rowUTFStandaloneEntitySoggettiMacroReport.setAzione((String)line[6]);												
			}
			
			//selected_import
			if(line[7]!=null)
			{
				rowUTFStandaloneEntitySoggettiMacroReport.setSelectedImport((Boolean)line[7]);												
			}
			
						
			utfStandaloneEntitySoggettiMacroReportList.add(rowUTFStandaloneEntitySoggettiMacroReport);
		}
		
		return utfStandaloneEntitySoggettiMacroReportList;
	}
	
	public List<UTFStandaloneEntityCustom> getUTFReportDettaglioSoggettiByIdImportIdAnagrafica(Long idImport, Long idAnag)
	{
		List<UTFStandaloneEntityCustom> utfStandaloneEntityCustomList = new ArrayList<>();
		
		/********************************************
		 * Setup parametri di input - INIZIO
		 ********************************************/
		
		//ID IMPORT
		String queryToExec = queryImportUTF_DETTAGLIO_SOGGETTI_BY_ID_ANAG.replaceAll(":idImport", idImport.toString());
		
		//ID ANAG
		queryToExec = queryToExec.replaceAll(":idAnag", idAnag.toString());
		
		/********************************************
		 * Setup parametri di input - FINE
		 ********************************************/
		
		List<Object[]> res = entityManager.createNativeQuery(queryToExec).getResultList();
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext())
		{
			UTFStandaloneEntityCustom rowUTFStandaloneEntityCustom = new UTFStandaloneEntityCustom();
			Object[] line = it.next();
			
			//idAnag
			if(line[0]!=null)
			{
				rowUTFStandaloneEntityCustom.setIdAnag((Integer)line[0]);
			}
			
			//Denominazione
			if(line[1]!=null)
			{
				rowUTFStandaloneEntityCustom.setDenominazione((String)line[1]);
			}
			
			//tot_usi_industriali_1200
			if(line[2]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_industriali_1200((Double)line[2]);
			}
			
			//tot_usi_industriali_up
			if(line[3]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_industriali_up((Double)line[3]);
			}
			
			//tot_usi_civili_120
			if(line[4]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_120((Double)line[4]);				
			}
			
			//tot_usi_civili_480
			if(line[5]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_480((Double)line[5]);								
			}
			
			//tot_usi_civili_1560
			if(line[6]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_1560((Double)line[6]);												
			}
			
			//tot_usi_civili_up
			if(line[7]!=null)
			{
				rowUTFStandaloneEntityCustom.setUsi_civili_up((Double)line[7]);																
			}
			
			//tot_nuovi_allacciamenti
			if(line[8]!=null)
			{
				rowUTFStandaloneEntityCustom.setTot_nuovi_allacciamenti((Double)line[8]);																				
			}
			
			//tot_industriali
			if(line[9]!=null)
			{
				rowUTFStandaloneEntityCustom.setTot_industriali((Double)line[9]);																								
			}
			
			//tot_civili
			if(line[10]!=null)
			{
				rowUTFStandaloneEntityCustom.setTot_civili((Double)line[10]);																												
			}
			
			//tot_conguaglio_dichiarato
			if(line[11]!=null)
			{
				rowUTFStandaloneEntityCustom.setConguaglio_dich((Double)line[11]);																																
			}
			
			//tot_totale_dichiarato
			if(line[12]!=null)
			{
				rowUTFStandaloneEntityCustom.setTotaleDich((Double)line[12]);																																				
			}
			
			//tot_rateo_dichiarato
			if(line[13]!=null)
			{
				rowUTFStandaloneEntityCustom.setRateo_dich((Double)line[13]);																																								
			}
			
			//prov erogazione			
			if(line[14]!=null)
			{
				rowUTFStandaloneEntityCustom.setProvErogazione((String)line[14]);																																												
			}
			
			//id consumi			
			if(line[15]!=null)
			{
				Integer idConsumiIntValue = (Integer)line[15];
				Long idConsumi = Long.valueOf(idConsumiIntValue);
				rowUTFStandaloneEntityCustom.setIdConsumi(idConsumi);
			}
			
			//rettifiche
			if(line[16]!=null)
			{				
				rowUTFStandaloneEntityCustom.setRettifiche((Double)line[16]);
			}
			
			//arrotondamenti
			if(line[17]!=null)
			{				
				rowUTFStandaloneEntityCustom.setArrotondamenti((Double)line[17]);
			}
			
						
			utfStandaloneEntityCustomList.add(rowUTFStandaloneEntityCustom);
			
		}
		return utfStandaloneEntityCustomList;		
	}

}
