/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.common.StatoValidazione;
import it.csi.sigas.sigasbl.model.entity.custom.SoggettoEntityCustom;
import it.csi.sigas.sigasbl.model.repositories.SoggettoRepository;


@Repository
public class SoggettoRepositoryImpl implements SoggettoRepository {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
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
				"	(select count(al5) from SigasDichConsumi al5 where u.idAnag = al5.sigasAnagraficaSoggetti.idAnag and al5.annualita = '" + anno + "' and al5.note is not null), " +
				"	(select count(al6) from SigasAllarmi al6 where u.codiceAzienda = al6.codiceAzienda and al6.annualita = '" + anno + "' and al6.sigasTipoAllarme.denominazione = 'RIMBORSO' and al6.status = 1), " +
				"	(select count(al7) from SigasAllarmi al7 where u.codiceAzienda = al7.codiceAzienda and al7.annualita = '" + anno + "' and al7.sigasTipoAllarme.denominazione = 'RAVVEDIMENTO' and al7.status = 1), " +
				"	(select count(al8) from SigasAllarmi al8 where u.codiceAzienda = al8.codiceAzienda and al8.annualita = '" + anno + "' and al8.sigasTipoAllarme.denominazione = 'COMPENSAZIONE' and al8.status = 1), " +
				"	(select count(al9) from SigasAllarmi al9 where u.codiceAzienda = al9.codiceAzienda and al9.annualita = '" + anno + "' and al9.sigasTipoAllarme.denominazione = 'VERSAMENTO' and al9.status = 1) " +
				"	from SigasDichConsumi c, SigasAnagraficaSoggetti u" + fromLine + 
				whereLine + " and c.modIdConsumi = 0 and c.annualita = '" + anno + "'" +
				"	group by u.idAnag, u.codiceAzienda Order by u.idAnag";
		
		@SuppressWarnings("unchecked")
		List<Object[]> res = entityManager.createQuery(jpqlQuery).getResultList();
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
}
