package it.csi.sigas.sigasbl.model.repositories.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.custom.ConsumiSoggettoIncorporatoEntityCustom;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettoIncorporatoStandaloneRepository;

@Repository
public class SigasAnagraficaSoggettoIncorporatoStandaloneRepositoryImpl implements SigasAnagraficaSoggettoIncorporatoStandaloneRepository {

	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * query che recupera i consumi di un soggetto incorporato per certa annualita
	 */
	private String query_DETTAGLIO_CONSUMI_SOGGETTO_INCORPORATO = "SELECT "
			+ "s_anag.id_anag, "
			+ "s_anag.denominazione, "
			+ "s_anag.codice_azienda, "
			+ "s_anag.indirizzo, "
			+ "s_anag.fk_comune, "
			+ "s_anag.fk_provincia, "
			+ "CAST(sdc.usi_industriali_1200 AS FLOAT) AS usi_industriali_1200, "
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
			+ "INNER JOIN sigas_anagrafica_soggetti s_anag ON s_anag.id_anag = sdc.id_anag "
			+ "LEFT OUTER JOIN ("
			+ "	SELECT sqmu.*, sa.validita_start, sa.validita_end "
			+ "	FROM sigas_quadro_m_utf sqmu  "
			+ "	INNER JOIN sigas_aliquote sa ON sqmu.aliquota = sa.aliquota AND sqmu.prog_rigo = sa.prog_rigo  "
			+ "	INNER JOIN sigas_tipo_aliquote sta ON sa.id_tipo_aliquota = sta.id_tipo_aliquota  "
			+ "	INNER JOIN sigas_tipo_consumo stc ON sta.id_tipo_consumo = stc.id_tipo_consumo  "
			+ "	WHERE stc.campo_dich_consumo  LIKE 'tot_nuovi_allacciamenti'"
			+ ") as tab_nuovi_allacmenti ON tab_nuovi_allacmenti.codice_ditta = s_anag.codice_azienda  "
			+ "AND tab_nuovi_allacmenti.anno = sdc.annualita  "
			+ "AND tab_nuovi_allacmenti.provincia = sdc.provincia_erogazione  "
			+ "AND EXTRACT(YEAR FROM tab_nuovi_allacmenti.validita_start  ) <= CAST(sdc.annualita AS int)  "
			+ "AND EXTRACT(YEAR FROM tab_nuovi_allacmenti.validita_end  ) >= CAST(sdc.annualita AS int)  "
			+ "WHERE mod_id_consumi IS NOT NULL  "
			+ "AND provincia_erogazione IS NOT NULL "
			+ "AND sdc.selected_import = true "
			+ "AND sdc.annualita = ':annualita' "//2022
			+ "AND sdc.id_anag = :idFusione"; //4373
	
	@Override
	public List<ConsumiSoggettoIncorporatoEntityCustom> getDettaglioConsumiSoggettoIncorporatoByIdFusioneAndAnnualita(Long idFusione, String annualita) {
		
		List<ConsumiSoggettoIncorporatoEntityCustom> consumiSoggettoIncorporatoEntityCustomList = new ArrayList<>();
		
		/********************************************
		 * Setup parametri di input - INIZIO
		 ********************************************/
		
		//idFusione
		String queryToExec = query_DETTAGLIO_CONSUMI_SOGGETTO_INCORPORATO.replaceAll(":idFusione", idFusione.toString());
		
		//annualità
		queryToExec = queryToExec.replaceAll(":annualita", annualita.toString());		
		
		/********************************************
		 * Setup parametri di input - FINE
		 ********************************************/
		
		List<Object[]> res = entityManager.createNativeQuery(queryToExec).getResultList();
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext())
		{
			ConsumiSoggettoIncorporatoEntityCustom row = new ConsumiSoggettoIncorporatoEntityCustom();
			Object[] line = it.next();
			//idAnag
			if(line[0]!=null)
			{
				row.setIdAnag((Integer)line[0]);
			}
			
			//Denominazione
			if(line[1]!=null)
			{
				row.setDenominazione((String)line[1]);
			}
			
			//Codice azienda
			if(line[2]!=null)
			{
				row.setCodiceAzienda((String)line[2]);
			}
			
			//Indirizzo
			if(line[3]!=null)
			{
				row.setIndirizzo((String)line[3]);
			}
			
			//Denominazione comune
			if(line[4]!=null)
			{
				row.setFkComune((BigDecimal)line[4]);
			}
			
			//Sigla provicnia
			if(line[5]!=null)
			{
				row.setFkProvincia((BigDecimal)line[5]);
			}
			
			//tot_usi_industriali_1200
			if(line[6]!=null)
			{
				row.setUsi_industriali_1200((Double)line[6]);
			}
			
			//tot_usi_industriali_up
			if(line[7]!=null)
			{
				row.setUsi_industriali_up((Double)line[7]);
			}
			
			//tot_usi_civili_120
			if(line[8]!=null)
			{
				row.setUsi_civili_120((Double)line[8]);				
			}
			
			//tot_usi_civili_480
			if(line[9]!=null)
			{
				row.setUsi_civili_480((Double)line[9]);								
			}
			
			//tot_usi_civili_1560
			if(line[10]!=null)
			{
				row.setUsi_civili_1560((Double)line[10]);												
			}
			
			//tot_usi_civili_up
			if(line[11]!=null)
			{
				row.setUsi_civili_up((Double)line[11]);																
			}
			
			//tot_nuovi_allacciamenti
			if(line[12]!=null)
			{
				row.setTot_nuovi_allacciamenti((Double)line[12]);																				
			}
			
			//tot_industriali
			if(line[13]!=null)
			{
				row.setTot_industriali((Double)line[13]);																								
			}
			
			//tot_civili
			if(line[14]!=null)
			{
				row.setTot_civili((Double)line[14]);																												
			}
			
			//tot_conguaglio_dichiarato
			if(line[15]!=null)
			{
				row.setConguaglio_dich((Double)line[15]);																																
			}
			
			//tot_totale_dichiarato
			if(line[16]!=null)
			{
				row.setTotaleDich((Double)line[16]);																																				
			}
			
			//tot_rateo_dichiarato
			if(line[17]!=null)
			{
				row.setRateo_dich((Double)line[17]);																																								
			}
			
			//prov erogazione
			if(line[18]!=null)
			{
				row.setProvErogazione((String)line[18]);																																												
			}
			
			//id consumi
			if(line[19]!=null)
			{
				row.setIdConsumi((Integer)line[19]);																																												
			}
						
			consumiSoggettoIncorporatoEntityCustomList.add(row);
		}
		
		return consumiSoggettoIncorporatoEntityCustomList;
	}
}
