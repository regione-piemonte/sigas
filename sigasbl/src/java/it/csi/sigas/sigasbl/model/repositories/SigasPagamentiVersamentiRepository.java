/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasPagamentiVersamenti;


@Repository
public interface SigasPagamentiVersamentiRepository extends CrudRepository<SigasPagamentiVersamenti, Long> {
	
	SigasPagamentiVersamenti findBySigasDichVersamentiIdVersamento(long idVersamento);
	
	List<SigasPagamentiVersamenti> findBySigasAnagraficaSoggettiIdAnagAndSigasPagamentiIdPagamento(long idAnag, Integer idPagamento);
	
}