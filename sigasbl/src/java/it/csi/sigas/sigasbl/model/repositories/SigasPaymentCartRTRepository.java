/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartRT;

@Repository
public interface SigasPaymentCartRTRepository extends CrudRepository<SigasPaymentCartRT, Long> {

	//SigasPaymentCartRT findByIdCarrelloRt(Long id);
	
	@Query(value="SELECT DISTINCT rt.* " +
			"	FROM sigas_carrello_rt rt  " +
			"		INNER JOIN sigas_carrello_pagamenti c ON c.codice_pagamento=rt.codice_pagamento" +
			"	WHERE c.iuv = ?1 ", nativeQuery = true)
	SigasPaymentCartRT getXmlByIuvCode(String iuv);
}
