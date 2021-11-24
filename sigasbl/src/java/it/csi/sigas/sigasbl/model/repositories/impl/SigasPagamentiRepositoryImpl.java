package it.csi.sigas.sigasbl.model.repositories.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.common.utils.Utilities;
import it.csi.sigas.sigasbl.model.entity.SigasPagamenti;
import it.csi.sigas.sigasbl.model.entity.SigasPagamentiVersamenti;
import it.csi.sigas.sigasbl.model.repositories.SigasPagamentiRepository;

 
@Repository 
public class SigasPagamentiRepositoryImpl implements SigasPagamentiRepository{
	@PersistenceContext
	private EntityManager entityManager;
	
	
 	
	public List<SigasPagamenti> findSigasPagamentiBy(String[] descOrdInc,Date dataIncassoDa,Date dataIncassoA, Boolean conciliato, Boolean conciliatoParziale) {
		// CRITERIA BUILDER
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SigasPagamenti> criteria = builder.createQuery(SigasPagamenti.class);
		Root<SigasPagamenti> root = criteria.from(SigasPagamenti.class);

		List<Predicate> predicatesAnd = new ArrayList<>();
		List<Predicate> predicatesOr = new ArrayList<>();

		for (String filtroDescOrdInc : descOrdInc) {
			if(!filtroDescOrdInc.isEmpty()) {
				predicatesOr.add(builder.like(builder.upper(root.get("descOrdInc")), "" +  filtroDescOrdInc.trim().toUpperCase() + " %"));
				predicatesOr.add(builder.like(builder.upper(root.get("descOrdInc")), "% " +  filtroDescOrdInc.trim().toUpperCase() + ""));
				predicatesOr.add(builder.like(builder.upper(root.get("descOrdInc")), "% " +  filtroDescOrdInc.trim().toUpperCase() + " %"));
				predicatesOr.add(builder.equal(builder.upper(root.get("descOrdInc")),  filtroDescOrdInc.trim().toUpperCase()));
			}
			
		}
		Predicate predicateInOr = null;
		if(predicatesOr.size()>0) {
			predicateInOr
			  = builder.or(predicatesOr.toArray(new Predicate[predicatesOr.size()]));
		}
		
		

		if(dataIncassoDa!=null && dataIncassoA!=null) {
			predicatesAnd.add(builder.between(root.get("dataEmissione"), Utilities.atStartOfDay(dataIncassoDa), Utilities.atEndOfDay(dataIncassoA)) );			
		}else if(dataIncassoDa!=null && dataIncassoA==null) {
			predicatesAnd.add(builder.greaterThanOrEqualTo(root.get("dataEmissione"), Utilities.atStartOfDay(dataIncassoDa)) );
		}else if(dataIncassoDa==null && dataIncassoA!=null) {
			predicatesAnd.add(builder.lessThanOrEqualTo(root.get("dataEmissione"),  Utilities.atEndOfDay(dataIncassoA)) );
		}

		if(conciliato!=null && conciliato && !conciliatoParziale) {
			predicatesAnd.add(builder.isTrue(root.get("conciliato")));
			predicatesAnd.add(builder.isNotEmpty(root.get("sigasPagamentiVersamentis")) );
		}else if(conciliato!=null && !conciliato && !conciliatoParziale) {
			predicatesAnd.add(builder.isFalse(root.get("conciliato")));
			predicatesAnd.add(builder.isEmpty(root.get("sigasPagamentiVersamentis")) );
		}else if(conciliato!=null && !conciliato && conciliatoParziale) {
//			final Join<SigasPagamenti, SigasPagamentiVersamenti> pagamentiVersamenti = root.join("sigasPagamentiVersamentis");
//			predicatesAnd.add(builder.isNotEmpty(pagamentiVersamenti.get("sigasPagamenti")));
			predicatesAnd.add(builder.isNotEmpty(root.get("sigasPagamentiVersamentis")) );
			predicatesAnd.add(builder.isFalse(root.get("conciliato")));
		}
		
		
		
		Predicate predicateInAnd = null;
		if(predicatesAnd.size()>0) {
			predicateInAnd
			  = builder.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
		}
		
		

		if(predicateInOr!=null && predicateInAnd!=null) {
			criteria.where(builder.and(predicateInOr, predicateInAnd) );	
		}else if(predicateInOr==null && predicateInAnd!=null) {
			criteria.where(builder.and(predicateInAnd) );
		}else if(predicateInOr!=null && predicateInAnd==null) {
			criteria.where(builder.and(predicateInOr) );
		}
		

		return entityManager.createQuery(criteria).getResultList();
	}

}
