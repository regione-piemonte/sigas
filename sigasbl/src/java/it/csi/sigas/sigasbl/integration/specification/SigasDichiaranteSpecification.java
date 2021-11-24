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

import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;


public class SigasDichiaranteSpecification {

	public static Specification<SigasDichiarante> codiceFiscaleOrPartitaIvaOrLikeDenominazione(String codiceFiscale, String partitaIva, String denominazione) {
		return new Specification<SigasDichiarante>() {
			@Override
			public Predicate toPredicate(Root<SigasDichiarante> root, CriteriaQuery<?> criteria, CriteriaBuilder builder) {
				final Collection<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(codiceFiscale)) {
					final Predicate cfPredicate = builder.or(builder.equal(root.get("cfDichiarante"), codiceFiscale));
					predicates.add(cfPredicate);
				}
				if (StringUtils.isNotEmpty(partitaIva)) {
					final Predicate cfPredicate = builder.or(builder.equal(root.get("pivaDichiarante"), partitaIva));
					predicates.add(cfPredicate);
				}
				if (StringUtils.isNotEmpty(denominazione)) {
					final Predicate denominazionePredicate = builder.like(root.get("denomDichiarante"), "%" + denominazione.toUpperCase() + "%");
					predicates.add(denominazionePredicate);
				}
				return builder.or(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

	}
	
	public static Specification<SigasDichiarante> codiceAziendaOrLikeDenominazione(String codiceAzienda, String denominazione) {
		return new Specification<SigasDichiarante>() {
			@Override
			public Predicate toPredicate(Root<SigasDichiarante> root, CriteriaQuery<?> criteria, CriteriaBuilder builder) {
				final Collection<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(codiceAzienda)) {
					final Predicate cfPredicate = builder.or(builder.equal(root.get("codiceAzienda"), codiceAzienda));
					predicates.add(cfPredicate);
				}
				if (StringUtils.isNotEmpty(denominazione)) {
					final Predicate denominazionePredicate = builder.like(root.get("denomDichiarante"), "%" + denominazione.toUpperCase() + "%");
					predicates.add(denominazionePredicate);
				}
				return builder.or(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

	}

	public static Specification<SigasDichiarante> codiceFiscaleOrPartitaIvaOrDenominazione(String codiceFiscale, String partitaIva, String denominazione) {
		return new Specification<SigasDichiarante>() {
			@Override
			public Predicate toPredicate(Root<SigasDichiarante> root, CriteriaQuery<?> criteria, CriteriaBuilder builder) {
				final Collection<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(codiceFiscale)) {
					final Predicate cfPredicate = builder.or(builder.equal(root.get("cfDichiarante"), codiceFiscale));
					predicates.add(cfPredicate);
				}
				if (StringUtils.isNotEmpty(partitaIva)) {
					final Predicate cfPredicate = builder.or(builder.equal(root.get("pivaDichiarante"), partitaIva));
					predicates.add(cfPredicate);
				}
				if (StringUtils.isNotEmpty(denominazione)) {
					final Predicate denominazionePredicate = builder.equal(root.get("denomDichiarante"), denominazione.toUpperCase());
					predicates.add(denominazionePredicate);
				}
				return builder.or(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

	}
}
