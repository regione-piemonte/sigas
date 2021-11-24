/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartNotify;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartRT;

@Repository
public interface SigasPaymentCartNotifyRepository extends CrudRepository<SigasPaymentCartNotify, Long> {

	SigasPaymentCartNotify findByIdCarrelloNotifica(Long id);
	
}
