/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.specification;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;


public class SigasAnagraficaSoggettoSpecification {

	
	
	public static Specification<SigasAnagraficaSoggetti> codiceAziendaOrLikeDenominazione(String codiceAzienda, String denominazione) {
		return new Specification<SigasAnagraficaSoggetti>() {
			@Override
			public Predicate toPredicate(Root<SigasAnagraficaSoggetti> root, CriteriaQuery<?> criteria, CriteriaBuilder builder) {
				final Collection<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(codiceAzienda)) {
					final Predicate cfPredicate = builder.or(builder.equal(root.get("codiceAzienda"), codiceAzienda));
					predicates.add(cfPredicate);
				}
				if (StringUtils.isNotEmpty(denominazione)) {
					final Predicate denominazionePredicate = builder.like(builder.upper(root.get("denominazione")), "%" + denominazione.toUpperCase() + "%");
					predicates.add(denominazionePredicate);
				}
				return builder.or(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

	}

}
