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

import it.csi.sigas.sigasbl.model.entity.SigasPaymentTypes;

@Repository
public interface SigasPaymentTypesRepository extends CrudRepository<SigasPaymentTypes, Long> {
	
	List<SigasPaymentTypes> findAll();
	
	@Query(value="SELECT desc_tipo_pagamento FROM sigas_tipo_pagamento WHERE id_tipo_pagamento=:id", nativeQuery = true)
	String findByIdTipoPagamento(@Param("id") Integer id);
	
	@Query(value="SELECT denominazione FROM sigas_tipo_carrello WHERE id_tipo_carrello=:id", nativeQuery = true)
	String findTipoVersamento(@Param("id") Integer id);
	
	SigasPaymentTypes findByCodTipoPagamento(String denominazione);
	
	SigasPaymentTypes findByDescTipoPagamento(String desc);
}
