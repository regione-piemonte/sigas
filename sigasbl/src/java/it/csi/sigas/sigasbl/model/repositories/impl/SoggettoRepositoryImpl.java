/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories.impl;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import it.csi.sigas.sigasbl.common.StatoValidazione;
import it.csi.sigas.sigasbl.model.entity.custom.SoggettoEntityCustom;
import it.csi.sigas.sigasbl.model.repositories.SoggettoRepository;
import it.csi.sigas.sigasbl.model.vo.MappaAllarmiPerSoggetto;
import it.csi.sigas.sigasbl.model.vo.MappaInfoSoggettoIncorporatoPerSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.MappaProvincePerSoggettoVO;


@Repository
public class SoggettoRepositoryImpl implements SoggettoRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	
	private String _ricavaDenominazioneAllarmeById(Integer idTipoAllarme) {
				
		switch(idTipoAllarme) {
			case 1:
				return "SCARTI";
			case 2: 
				return "COERENZA";
			case 3:
				return "COMUNICAZIONI";
			case 4:
				return "RETTIFICA";
			case 5:
				return "ACCERTAMENTO";
			case 6:
				return "NOTE";
			case 7:
				return "RIMBORSO";
			case 8:
				return "RAVVEDIMENTO";
			case 9:
				return "COMPENSAZIONE";
			case 10:
				return "VERSAMENTO";
			case 11:
				return "RIMBORSI SCADUTI";
		}
		
		return "";
	}
	
	private Long _getQuantitaAllarmeByDenominazione(Integer idTipoAllarme, List<MappaAllarmiPerSoggetto> mappaAllarmiPerSoggettoList) {		
		Long output = 0L;
		String denominazione = this._ricavaDenominazioneAllarmeById(idTipoAllarme);
		
		Iterator<MappaAllarmiPerSoggetto> iterator = mappaAllarmiPerSoggettoList.iterator();
		while(iterator.hasNext()) {
			MappaAllarmiPerSoggetto item = iterator.next();
			if(denominazione.equalsIgnoreCase(item.getDenominazione())) {
				output = item.getCount().longValue();
			}
		}		
		return output;
	}
	
	private String _formattaClausolaWhereAggiuntiva(String filter) {
		String whereLine = "";
		if (StringUtils.isNotEmpty(filter)) {
			if (filter.equals(StatoValidazione.VALIDATO.getName())) {				
				whereLine = " AND anagrafica.codice_azienda LIKE val.codice_azienda AND val.stato LIKE 'VALIDATO' AND val.anno =?1";
			} else if ( filter.equals(StatoValidazione.NON_VALIDATO.getName())){				
				whereLine = " AND (not exists (SELECT * FROM sigas_validazione val_int WHERE anagrafica.codice_azienda = val_int.codice_azienda and val_int.anno =?1)" +
							" OR (SELECT COUNT(*) FROM sigas_validazione val_int_2 WHERE anagrafica.codice_azienda = val_int_2.codice_azienda AND val_int_2.stato = 'NON_VALIDATO' AND val_int_2.anno =?1) > 0)";
			} 
		} 
		
		return whereLine;
	}
	
	@SuppressWarnings("unchecked")
	private List<SoggettoEntityCustom> _findListaSoggetti(String filter, String anno) {
		
		Gson gson = new Gson();
		List<MappaAllarmiPerSoggetto> mappaAllarmiPerSoggettoList = new ArrayList<>();
		Type listType = new TypeToken<List<MappaAllarmiPerSoggetto>>() {}.getType();
		
		List<MappaProvincePerSoggettoVO> mappaProvincePerSoggettoVO = new ArrayList<>();
		Type listTypeProvincePerSoggetto = new TypeToken<List<MappaProvincePerSoggettoVO>>() {}.getType();
		
		List<MappaInfoSoggettoIncorporatoPerSoggettoVO> mappaInfoSoggettoIncorporatoPerSoggettoVO = new ArrayList<>();
		Type listTypeInfoSoggettoIncorporatoPerSoggetto = new TypeToken<List<MappaInfoSoggettoIncorporatoPerSoggettoVO>>() {}.getType();
		
		List<MappaInfoSoggettoIncorporatoPerSoggettoVO> mappaInfoSoggettoIncorporanteVO = new ArrayList<>();
		Type listTypeInfoSoggettoIncorporante = new TypeToken<List<MappaInfoSoggettoIncorporatoPerSoggettoVO>>() {}.getType();
		
		String whereLine = null;
		
		if (StringUtils.isNotEmpty(filter)) {
			if (filter.equals(StatoValidazione.VALIDATO.getName())) {				
				whereLine = //" AND ag.id_anag = dc.id_anag and (ag.data_fusione IS NULL OR date_trunc('year',ag.data_fusione) >= date_trunc('year',current_date)) " +
						   " AND anagrafica.codice_azienda LIKE val.codice_azienda AND val.stato LIKE 'VALIDATO' AND val.anno =?1";
			} else if ( filter.equals(StatoValidazione.NON_VALIDATO.getName())){				
				whereLine = //" AND ag.id_anag = dc.id_anag AND (ag.data_fusione is null or date_trunc('year',ag.data_fusione) >= date_trunc('year',current_date)) " +
							" AND (not exists (SELECT * FROM sigas_validazione val_int WHERE anagrafica.codice_azienda = val_int.codice_azienda and val_int.anno =?1)" +
							" OR (SELECT COUNT(*) FROM sigas_validazione val_int_2 WHERE anagrafica.codice_azienda = val_int_2.codice_azienda AND val_int_2.stato = 'NON_VALIDATO' AND val_int_2.anno =?1) > 0)";
			} else {				 
				//whereLine = " AND ag.id_anag = c.id_anag AND (ag.data_fusione IS NULL OR date_trunc('year',ag.data_fusione) >= date_trunc('year',current_date))";
				whereLine = "";
			}
		} else {			 
			//whereLine = " AND ag.id_anag = dc.id_anag AND (ag.data_fusione is null or date_trunc('year',ag.data_fusione) >= date_trunc('year',current_date))";
			whereLine = "";
		}
		
		String query_MARTS = "SELECT  anagrafica.id_anag, " 
									+ "anagrafica.codice_azienda, " 
									+ "anagrafica.denominazione, "
									+ "CAST((WITH sq AS " 
									+ "( " 
										 + "SELECT sta.denominazione, COUNT(id_allarme) " 
										 + "FROM sigas_allarmi sa " 
											+ "INNER JOIN sigas_tipo_allarmi sta " 
												+ "ON sta.id_tipo_allarme = sa.id_tipo_allarme " 
											+ "WHERE sa.annualita= ?1 AND sa.codice_azienda LIKE anagrafica.codice_azienda " 
											+ "GROUP BY sta.denominazione " 
									+ ") " 
								 	+ "SELECT json_agg(row_to_json(sq)) FROM sq) AS VARCHAR) AS allarmi, "
								 	+ "(SELECT val.stato " 
								 	+ "FROM sigas_validazione val " 
									+ "WHERE val.codice_azienda LIKE anagrafica.codice_azienda " 
										   + "AND val.anno = ?1) AS stato, "
									+ "(SELECT COALESCE(SUM(sdv.importo),0.0) "
									+ "FROM sigas_dich_versamenti sdv "
									+ "WHERE sdv.id_anag = anagrafica.id_anag AND sdv.annualita = ?1) AS tot_versato_incorporante, "		
								  	+ "CAST((WITH sq_info_incorporante AS "  
											+ "( "
												+ "SELECT consumi_incorporato.provincia_erogazione AS provincia, " 
												       + "SUM(consumi_incorporato.totale_calcolato) AS consumi "					       
												 + "FROM ( "
												     	+ "SELECT dc.* "
														+ "FROM sigas_dich_consumi dc " 
															+ "INNER JOIN ( " 			
																			+ "SELECT id_anag, provincia_erogazione, MAX(id_consumi) AS id_consumi " 
																			+ "FROM sigas_dich_consumi sdc "  
																			+ "WHERE annualita = ?1 " 
																				  + "AND selected_import = true "																  
																			+ "GROUP BY id_anag, provincia_erogazione "  
															+ ") AS consumi_operativi ON dc.id_anag = consumi_operativi.id_anag " 
																					+ "AND dc.provincia_erogazione = consumi_operativi.provincia_erogazione " 
																					+ "AND dc.id_consumi = consumi_operativi.id_consumi "
														+ "WHERE (dc.mod_user NOT LIKE 'FUSIONE' OR dc.mod_user is NULL)" 
												+ ") AS consumi_incorporato "								     									     					
												+ "WHERE consumi_incorporato.id_anag = anagrafica.id_anag "						
												+ "GROUP BY consumi_incorporato.provincia_erogazione "
										 + ")"  
									+ "SELECT json_agg(row_to_json(sq_info_incorporante)) FROM sq_info_incorporante) AS VARCHAR) AS info_incorporante, "
									+ "(SELECT COALESCE(SUM(sdv.importo),0.0) "
									 + "FROM sigas_dich_versamenti sdv "
									 + "WHERE sdv.id_anag = anagrafica.id_fusione AND anagrafica.id_fusione > 0 AND sdv.annualita = ?1) AS tot_versato_incorporato, "
									+ "CAST((WITH sq_info_incorporato AS "  
											+ "( "
												+ "SELECT consumi_incorporato.provincia_erogazione AS provincia, " 
												       + "SUM(consumi_incorporato.totale_calcolato) AS consumi "					       
												+ "FROM ( "
												     	+ "SELECT dc.* "
														+ "FROM sigas_dich_consumi dc " 
															+ "INNER JOIN ( " 			
																			+ "SELECT id_anag, provincia_erogazione, MAX(id_consumi) AS id_consumi " 
																			+ "FROM sigas_dich_consumi sdc "  
																			+ "WHERE annualita = ?1 " 
																				  + "AND selected_import = true "																  
																			+ "GROUP BY id_anag, provincia_erogazione "  
															+ ") AS consumi_operativi ON dc.id_anag = consumi_operativi.id_anag " 
																					+ "AND dc.provincia_erogazione = consumi_operativi.provincia_erogazione " 
																					+ "AND dc.id_consumi = consumi_operativi.id_consumi "
														+ "WHERE (dc.mod_user NOT LIKE 'FUSIONE' OR dc.mod_user IS NULL) " 
													+ ") AS consumi_incorporato "	     									     					
												+ "WHERE consumi_incorporato.id_anag = anagrafica.id_fusione and anagrafica.id_fusione > 0 "						
												+ "GROUP BY consumi_incorporato.provincia_erogazione "
											+ ") "  
									+ "SELECT json_agg(row_to_json(sq_info_incorporato)) FROM sq_info_incorporato) AS VARCHAR) AS info_incorporato "
							+ "FROM sigas_anagrafica_soggetti anagrafica "
								 + "INNER JOIN ( "
												+ "SELECT sas.id_anag, sas.codice_azienda, sas.denominazione, sas.selected_import "  
												+ "FROM sigas_anagrafica_soggetti sas "  
												+ "LEFT OUTER JOIN (SELECT sas.id_anag "  
																 + "FROM sigas_anagrafica_soggetti sas "  
																	  + "INNER JOIN sigas_anagrafica_soggetti lft_outer ON sas.id_anag = lft_outer.id_fusione ) AS anag_fuse " 
													 + "ON sas.id_anag = anag_fuse.id_anag " 
												+ "WHERE anag_fuse.id_anag IS NULL " 
											+ ") AS ag ON ag.id_anag = anagrafica.id_anag "
								 + "INNER JOIN ( "
												+ "SELECT distinct dc.id_anag " 
												+ "FROM sigas_dich_consumi dc "  
												  	+ "INNER JOIN ( "  			
																+ "SELECT id_anag, provincia_erogazione, MAX(id_consumi) AS id_consumi "  
																+ "FROM sigas_dich_consumi sdc "   
																+ "WHERE annualita = ?1 "  
																	 + " AND selected_import = true " 																  
																+ "GROUP BY id_anag, provincia_erogazione "   
												+ ") AS consumi_operativi ON dc.id_anag = consumi_operativi.id_anag "  
																	   + "AND dc.provincia_erogazione = consumi_operativi.provincia_erogazione "  
																	   + "AND dc.id_consumi = consumi_operativi.id_consumi " 
											    + "WHERE (dc.mod_user NOT LIKE 'FUSIONE' OR dc.mod_user IS NULL) " 
										 + ") AS anagrafiche_confermate ON anagrafica.id_anag = anagrafiche_confermate.id_anag "
								+ "LEFT OUTER JOIN sigas_validazione val ON val.codice_azienda LIKE anagrafica.codice_azienda AND val.anno = ?1 "
							+ "WHERE anagrafica.selected_import = true "
								 //+ whereLine --> DA VERIFICARE SE REPRISTINARE <---
								  + "AND ((?2 LIKE 'VALIDATO' AND val.stato LIKE 'VALIDATO' AND anno = ?1) OR "
								  	   + "(?2 LIKE 'NON_VALIDATO' and val.stato LIKE 'NON_VALIDATO' AND anno = ?1) OR "
								  	   + "(?2 LIKE '')) "
							+ "ORDER BY anagrafica.id_anag ASC "; 
		
		//query_MARTS = query_MARTS.replace(":anno", anno);
		
		Query martsQuery = entityManager.createNativeQuery(query_MARTS);
		martsQuery.setParameter(1, anno);
		martsQuery.setParameter(2, (StringUtils.isNotEmpty(filter)) ? filter : "");
		List<Object[]> res = martsQuery.getResultList();
		
		/*
		@SuppressWarnings("unchecked")		
		List<Object[]> res = entityManager.createNativeQuery(query_MARTS)										  
										  .getResultList();
		*/
		final List<SoggettoEntityCustom> list = new LinkedList<>();
		
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext()){
			Object[] line = it.next();			
			
			SoggettoEntityCustom soggettoEntityCustom = new SoggettoEntityCustom();
			
			soggettoEntityCustom.setIdAnag(((Integer)line[0]).longValue());
			
			soggettoEntityCustom.setCodiceAzienda((String)line[1]);
			
			soggettoEntityCustom.setDenominazione((String)line[2]);
			
			mappaAllarmiPerSoggettoList = gson.fromJson((String)line[3], listType);			
			if(mappaAllarmiPerSoggettoList!=null) {
				Long[] allarmi = new Long[10];
				for (int idx = 0; idx < 10; idx++) {					
					allarmi[idx] = this._getQuantitaAllarmeByDenominazione(idx+1,mappaAllarmiPerSoggettoList);
				}
				soggettoEntityCustom.setAllarmi(allarmi);
			}
			
			soggettoEntityCustom.setValidato((String)line[4]);
			
			BigDecimal totVersamenti = (BigDecimal)line[5];
			if(totVersamenti!=null) {
				soggettoEntityCustom.setTotVersamenti(totVersamenti.doubleValue());
			}
			
			Map<String, String> elencoProvinceIncorporante = new HashMap<>();
			if(line[6]!=null) {
				double totaleCalcolatoIncorporante = 0d;
				mappaInfoSoggettoIncorporanteVO = gson.fromJson((String)line[6], listTypeInfoSoggettoIncorporante);
				if(mappaInfoSoggettoIncorporanteVO!=null && mappaProvincePerSoggettoVO!=null) {					
					Iterator<MappaInfoSoggettoIncorporatoPerSoggettoVO> iterator = mappaInfoSoggettoIncorporanteVO.iterator();
					while(iterator.hasNext()) {
						MappaInfoSoggettoIncorporatoPerSoggettoVO item = iterator.next();
						elencoProvinceIncorporante.put(item.getProvincia(), item.getProvincia());
						
						totaleCalcolatoIncorporante = totaleCalcolatoIncorporante + item.getConsumi();						
					}					
					soggettoEntityCustom.setTotCalcolato(totaleCalcolatoIncorporante);					
				}
			}
			
			BigDecimal totVersamentiIncorporato = (BigDecimal)line[7];
			if(totVersamentiIncorporato!=null) {
				soggettoEntityCustom.setTotVersamenti(soggettoEntityCustom.getTotVersamenti() + totVersamentiIncorporato.doubleValue());
			}
			
			Map<String, String> elencoProvinceIncorporato = new HashMap<>();
			if(line[8]!=null) {
				double totaleCalcolatoIncorporato = 0d;
				mappaInfoSoggettoIncorporanteVO = gson.fromJson((String)line[8], listTypeInfoSoggettoIncorporatoPerSoggetto);
				if(mappaInfoSoggettoIncorporanteVO!=null && mappaProvincePerSoggettoVO!=null) {					
					Iterator<MappaInfoSoggettoIncorporatoPerSoggettoVO> iterator = mappaInfoSoggettoIncorporanteVO.iterator();
					while(iterator.hasNext()) {
						MappaInfoSoggettoIncorporatoPerSoggettoVO item = iterator.next();
						elencoProvinceIncorporato.put(item.getProvincia(), item.getProvincia());
						
						totaleCalcolatoIncorporato = totaleCalcolatoIncorporato + item.getConsumi();						
					}					
					soggettoEntityCustom.setTotCalcolato(soggettoEntityCustom.getTotCalcolato() + totaleCalcolatoIncorporato);					
				}
			}
						
			Integer numProvince = 0;
			if(elencoProvinceIncorporante != null && elencoProvinceIncorporante.size() > 0) {
				numProvince = elencoProvinceIncorporante.size();
			}
			
			if(elencoProvinceIncorporato != null && elencoProvinceIncorporato.size() > 0 ) {
				Iterator<Entry<String, String>> iterator = elencoProvinceIncorporato.entrySet().iterator();
				while(iterator.hasNext()) {
					Entry<String, String> item = iterator.next();
					if(!elencoProvinceIncorporante.containsKey(item.getKey())) {
						numProvince = numProvince + 1;
					}
				}
			}				
			
			soggettoEntityCustom.setnProvince(numProvince.longValue());
						
					
		    list.add(soggettoEntityCustom);
		}
		
		return list;
	}
	
	private long _determinaNumeroProvince(List<MappaProvincePerSoggettoVO> mappaProvincePerSoggettoVO, Map<String, String> elencoProvinceIncorporato) {
		
		long numeroProvince = mappaProvincePerSoggettoVO.size();
		Iterator<MappaProvincePerSoggettoVO> iterator = mappaProvincePerSoggettoVO.iterator();
		while(iterator.hasNext()) {
			MappaProvincePerSoggettoVO itemProvince = iterator.next();
			if(elencoProvinceIncorporato!=null && 
			   !elencoProvinceIncorporato.isEmpty() && 
			   elencoProvinceIncorporato.get(itemProvince.getProvincia())!=null) 
			{
				elencoProvinceIncorporato.remove(itemProvince.getProvincia());
			}			
		}
		
		long numeroProvinceIncorporato = 0;
		if(elencoProvinceIncorporato!=null && !elencoProvinceIncorporato.isEmpty() && !elencoProvinceIncorporato.containsKey(null)) {
			numeroProvinceIncorporato = elencoProvinceIncorporato.size();
		}
		
		return numeroProvince + numeroProvinceIncorporato;
	}
	
	@Override
	public List<SoggettoEntityCustom> findListaSoggettiPerFusione(String anno) {
		
		
		String query_MARTS ="SELECT ag.id_anag, "
								+ " ag.codice_azienda, "
								+ "	ag.denominazione, "
								+ "	SUM(dc.totale_calcolato), "
								+ "	COALESCE(SUM(sdv.importo),0.0), "
								+ "	COUNT(dc.id_consumi), "
								+ "	val.stato "
				+ " FROM sigas_dich_consumi dc "					
					+ "	INNER JOIN ("
									+ "SELECT sas.id_anag, sas.codice_azienda, sas.denominazione, sas.selected_import, sas.id_fusione " 
									+ "FROM sigas_anagrafica_soggetti sas " 
									+ "LEFT OUTER JOIN (SELECT sas.id_anag " 
									 				 + "FROM sigas_anagrafica_soggetti sas " 
													      + "INNER JOIN sigas_anagrafica_soggetti lft_outer ON sas.id_anag = lft_outer.id_fusione ) AS anag_fuse "
										 + "ON sas.id_anag = anag_fuse.id_anag "
									+ "WHERE anag_fuse.id_anag IS NULL "
					+ ") AS ag "
						+ "	ON dc.id_anag = ag.id_anag AND ag.selected_import = true "
					+ "	LEFT OUTER JOIN sigas_validazione val "
						+ " ON val.codice_azienda like ag.codice_azienda "
							+ "	AND val.anno = dc.annualita "
					+ "	LEFT OUTER JOIN sigas_dich_versamenti sdv "
						+ "	ON sdv.id_anag = ag.id_anag "
							+ "	AND sdv.annualita = dc.annualita "														
				+ " WHERE dc.annualita = ':anno' "
					   + "AND dc.selected_import = true "
					   + "AND ag.codice_azienda NOT LIKE '%NEW%' "
					   + "AND ag.id_fusione = 0 "
				+ "GROUP BY ag.id_anag , ag.codice_azienda, ag.denominazione, val.stato "
				+ "ORDER BY ag.id_anag";
		
		query_MARTS = query_MARTS.replace(":anno", anno);
		
		@SuppressWarnings("unchecked")		
		List<Object[]> res = entityManager.createNativeQuery(query_MARTS)										  
										  .getResultList();
		final List<SoggettoEntityCustom> list = new LinkedList<>();
		
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext()){
			Object[] line = it.next();			
			
			SoggettoEntityCustom soggettoEntityCustom = new SoggettoEntityCustom();
			soggettoEntityCustom.setIdAnag(((Integer)line[0]).longValue());
			soggettoEntityCustom.setCodiceAzienda((String)line[1]);
			soggettoEntityCustom.setDenominazione((String)line[2]);
			
			BigDecimal totcalcolato = (BigDecimal)line[3];
			if(totcalcolato!=null) {
				soggettoEntityCustom.setTotCalcolato(totcalcolato.doubleValue());
			}
			
			BigDecimal totVersamenti = (BigDecimal)line[4];
			if(totVersamenti!=null) {
				soggettoEntityCustom.setTotVersamenti(totVersamenti.doubleValue());
			}
			
			BigInteger numProvince = (BigInteger)line[5];
			if(numProvince!=null) {
				soggettoEntityCustom.setnProvince(numProvince.longValue());
			}		
			
			soggettoEntityCustom.setValidato((String)line[6]);						
					
		    list.add(soggettoEntityCustom);
		}
		
		return list;
	}
	
	
	@Override
	public List<SoggettoEntityCustom> findListaSoggetti(String filter, String anno) {
		return _findListaSoggetti(filter,anno);
	}
	/*
	public List<SoggettoEntityCustom> findListaSoggetti(String filter, String anno) {
		
		String fromLine = null;
		String whereLine = null;

		if (StringUtils.isNotEmpty(filter)) {
			if (filter.equals(StatoValidazione.VALIDATO.getName())) {
				fromLine = " 	  ,SigasValidazione val  ";
				whereLine = "	where u.idAnag = c.sigasAnagraficaSoggetti.idAnag and ( u.dataFusione is null or date_trunc('year',u.dataFusione) >= date_trunc('year',current_date)) "
						+ " and u.codiceAzienda = val.codiceAzienda and val.stato = 'VALIDATO' and val.anno ='"+anno+"'";
			} else if ( filter.equals(StatoValidazione.NON_VALIDATO.getName())){
				fromLine = "";
				whereLine = "	where u.idAnag = c.sigasAnagraficaSoggetti.idAnag and ( u.dataFusione is null or date_trunc('year',u.dataFusione) >= date_trunc('year',current_date)) " +
							"		and ( not exists (select val from SigasValidazione val where u.codiceAzienda = val.codiceAzienda and val.anno ='"+anno+"')" +
							"			or (select count(val) from SigasValidazione val where u.codiceAzienda = val.codiceAzienda and val.stato = 'NON_VALIDATO' and val.anno ='"+anno+"') > 0)";
			} else {
				fromLine = ""; 
				whereLine = "	where u.idAnag = c.sigasAnagraficaSoggetti.idAnag and ( u.dataFusione is null or date_trunc('year',u.dataFusione) >= date_trunc('year',current_date))";
			}
		} else {

			fromLine = ""; 
			whereLine = "	where u.idAnag = c.sigasAnagraficaSoggetti.idAnag and ( u.dataFusione is null or date_trunc('year',u.dataFusione) >= date_trunc('year',current_date))";
		}
		
		
		final String jpqlQuery = "select u.idAnag, u.codiceAzienda, u.denominazione, sum(c.totaleCalcolato), " + 
				"	(select COALESCE(sum(v.importo),0.0) from SigasDichVersamenti v where u.idAnag = v.sigasAnagraficaSoggetti.idAnag and v.annualita = '" + anno + "' ), " +				
				"	count(c), " + 
				"  	(select val.stato from SigasValidazione val where u.codiceAzienda = val.codiceAzienda and val.anno =  '" + anno + "'), " + 
				"	(select count(al) from SigasAllarmi al where u.codiceAzienda = al.codiceAzienda and al.annualita = '" + anno + "' and al.sigasTipoAllarme.denominazione = 'SCARTI' and al.status = 1), " +
				"	(select count(al1) from SigasAllarmi al1 where u.codiceAzienda = al1.codiceAzienda and al1.annualita = '" + anno + "' and al1.sigasTipoAllarme.denominazione = 'COERENZA' and al1.status = 1), " +
				"	(select count(al2) from SigasAllarmi al2 where u.codiceAzienda = al2.codiceAzienda and al2.annualita = '" + anno + "' and al2.sigasTipoAllarme.denominazione = 'COMUNICAZIONI' and al2.status = 1), " +
				"	(select count(al3) from SigasAllarmi al3 where u.codiceAzienda = al3.codiceAzienda and al3.annualita = '" + anno + "' and al3.sigasTipoAllarme.denominazione = 'RETTIFICA' and al3.status = 1), " +
				"	(select count(al4) from SigasAllarmi al4 where u.codiceAzienda = al4.codiceAzienda and al4.annualita = '" + anno + "' and al4.sigasTipoAllarme.denominazione = 'ACCERTAMENTO' and al4.status = 1), " +
				"	(select count(al5) from SigasDichConsumi al5 where u.idAnag = al5.sigasAnagraficaSoggetti.idAnag and al5.annualita = '" + anno + "' and al5.note is not null and al5.sigasImport.selectedImport=true), " +				
				"	(select count(al6) from SigasAllarmi al6 where u.codiceAzienda = al6.codiceAzienda and al6.annualita = '" + anno + "' and al6.sigasTipoAllarme.denominazione = 'RIMBORSO' and al6.status = 1), " +
				"	(select count(al7) from SigasAllarmi al7 where u.codiceAzienda = al7.codiceAzienda and al7.annualita = '" + anno + "' and al7.sigasTipoAllarme.denominazione = 'RAVVEDIMENTO' and al7.status = 1), " +
				"	(select count(al8) from SigasAllarmi al8 where u.codiceAzienda = al8.codiceAzienda and al8.annualita = '" + anno + "' and al8.sigasTipoAllarme.denominazione = 'COMPENSAZIONE' and al8.status = 1), " +
				"	(select count(al9) from SigasAllarmi al9 where u.codiceAzienda = al9.codiceAzienda and al9.annualita = '" + anno + "' and al9.sigasTipoAllarme.denominazione = 'VERSAMENTO' and al9.status = 1) " +
				"	from SigasDichConsumi c, SigasAnagraficaSoggetti u" + fromLine + 
				whereLine + " and c.modIdConsumi = 0 and c.annualita = '" + anno + "'" + " and c.sigasAnagraficaSoggetti.idAnag = u.idAnag and c.sigasImport.selectedImport = true "+
				"	group by u.idAnag, u.codiceAzienda Order by u.idAnag";
		
		@SuppressWarnings("unchecked")		
		List<Object[]> res = entityManager.createQuery(jpqlQuery)										  
										  .getResultList();
		final List<SoggettoEntityCustom> list = new LinkedList<>();
		
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext()){
			Object[] line = it.next();
		     	
			SoggettoEntityCustom soggettoEntityCustom = new SoggettoEntityCustom();
			soggettoEntityCustom.setIdAnag((Long)line[0]);
			soggettoEntityCustom.setCodiceAzienda((String)line[1]);
			soggettoEntityCustom.setDenominazione((String)line[2]);
			soggettoEntityCustom.setTotCalcolato((Double)line[3]);
			soggettoEntityCustom.setTotVersamenti((Double)line[4]);
			soggettoEntityCustom.setnProvince((long)line[5]);
			soggettoEntityCustom.setValidato((String)line[6]);
			
			Long[] allarmi = new Long[10];
			for (int idx = 0; idx < 10; idx++) {
				allarmi[idx] = (Long.valueOf((long)line[idx+7]));
			}
			
			soggettoEntityCustom.setAllarmi(allarmi);
					
		    list.add(soggettoEntityCustom);
		}
		
		return list;
	}
	*/
}
