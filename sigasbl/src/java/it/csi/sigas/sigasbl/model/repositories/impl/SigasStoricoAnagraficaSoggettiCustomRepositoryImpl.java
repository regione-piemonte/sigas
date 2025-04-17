package it.csi.sigas.sigasbl.model.repositories.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.custom.SigasStoricoAnagraficaSoggettiCustom;
import it.csi.sigas.sigasbl.model.repositories.SigasStoricoAnagraficaSoggettiCustomRepository;

@Repository
public class SigasStoricoAnagraficaSoggettiCustomRepositoryImpl implements SigasStoricoAnagraficaSoggettiCustomRepository {


	@PersistenceContext
	private EntityManager entityManager;
	
	private String findStoricoAnagraficaSoggettiCustomQuery = "SELECT "
																	+ "sta.codice_azienda, "
																	+ "sta.denominazione, "
																	+ "sta.indirizzo, "
																	+ "sta.iban, "
																	+ "sta.email, "
																	+ "sta.pec, "
																	+ "sta.id_storico_anag, "
																	+ "sta.id_anag, "			
																	+ "sta.data_riferimento, "
																	+ "sta.cf_piva, "
																	+ "sta.owner_operazione, "
																	+ "sta.id_import, "
																	+ "sta.annualita, "
																	+ "case when sta.owner_operazione = 'OPERATORE' then 'Variata' "
																	+ "when sta.id_storico_anag > tmp.primo_storico then 'Variata' "
																	+ "when sas.selected_import = true and sta.id_storico_anag = tmp.primo_storico then 'Variata' "
																	+ "else 'Nuova' "
																	+ "end as tipo_elaborazione "
															+ "FROM sigas_storico_anagrafica_soggetti sta "
																	+ "left outer join sigas_anagrafica_soggetti sas on sta.id_anag = sas.id_anag "
																	+ "left outer join (select sto.id_anag, min(sto.id_storico_anag) as primo_storico "
																					 + "from sigas_storico_anagrafica_soggetti sto group by sto.id_anag) as tmp on sta.id_anag = tmp.id_anag "
															+ "WHERE (:denominazioneSoggetto = '' OR UPPER(sta.denominazione) LIKE UPPER(:denominazioneSoggetto)) "
																	//+ "AND (:annualita = '' OR sta.annualita = :annualita) "
																	+ "AND (:indirizzo = '' OR UPPER(sta.indirizzo) LIKE UPPER(:indirizzo)) "
																	+ "AND (:codiceAzienda = '' OR UPPER(sta.codice_azienda) LIKE UPPER(:codiceAzienda)) "
																	+ "AND (:cfPiva = '' OR UPPER(sta.cf_piva) =  UPPER(:cfPiva)) "
																	+ "AND sta.selected_import = true "
																	+ "ORDER BY sta.id_storico_anag DESC ";
	
	@Override
	public List<SigasStoricoAnagraficaSoggettiCustom> findStoricoAnagraficaSoggettiCustom(String denominazioneSoggetto,
			String annualita, String indirizzo, String codiceAzienda, String cfPiva) {
				
		List<SigasStoricoAnagraficaSoggettiCustom> storicoAnagraficaSoggettiCustomList = new ArrayList<>();
		
		//gestione parametri input query ---- INIZIO
		String queryToExec = findStoricoAnagraficaSoggettiCustomQuery.replaceAll(":denominazioneSoggetto", denominazioneSoggetto);		
		//queryToExec = queryToExec.replaceAll(":annualita", annualita);
		queryToExec = queryToExec.replaceAll(":indirizzo", indirizzo);
		queryToExec = queryToExec.replaceAll(":codiceAzienda", codiceAzienda);
		queryToExec = queryToExec.replaceAll(":cfPiva", cfPiva);
		//gestione parametri input query ---- FINE
		
		
		//Esecuzione ed elaborazione dati query
		List<Object[]> res = entityManager.createNativeQuery(queryToExec).getResultList();
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext())	{
			
			Object[] line = it.next();
			SigasStoricoAnagraficaSoggettiCustom sigasStoricoAnagraficaSoggettiCustom = new SigasStoricoAnagraficaSoggettiCustom();
						
			sigasStoricoAnagraficaSoggettiCustom.setCodiceAzienda(line[0]!=null ? (String) line[0] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setDenominazione(line[1]!=null ? (String) line[1] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setIndirizzo(line[2]!=null ? (String) line[2] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setIban(line[3]!=null ? (String) line[3] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setEmail(line[4]!=null ? (String) line[4] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setPec(line[5]!=null ? (String) line[5] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setIdStoricoAnag(line[6]!=null ? (Integer) line[6] : null);
				
			sigasStoricoAnagraficaSoggettiCustom.setIdAnag(line[7]!=null ? (Integer) line[7] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setDataRiferimento(line[8]!=null ? (Date) line[8] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setCfPiva(line[9]!=null ? (String) line[9] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setOwnerOperazione(line[10]!=null ? (String) line[10] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setIdImport(line[11]!=null ? (BigInteger) line[11] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setAnnualita(line[12]!=null ? (String) line[12] : null);
			
			sigasStoricoAnagraficaSoggettiCustom.setTipoElaborazione(line[13]!=null ? (String) line[13] : null);
			
			storicoAnagraficaSoggettiCustomList.add(sigasStoricoAnagraficaSoggettiCustom);
		}

		return storicoAnagraficaSoggettiCustomList;
	}

}
