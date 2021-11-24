/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.model.entity.SigasValidazione;


@Repository
public interface SigasValidazioneRepository extends CrudRepository<SigasValidazione, Long> {
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO sigas_validazione (id_valid, codice_azienda, anno, stato) VALUES(nextval('SEQ_VALIDAZIONE'), ?1, ?2, ?3) ON CONFLICT ON CONSTRAINT ak_validazione_02 DO NOTHING ", nativeQuery=true)
	void saveOrUpdate(String codiceDitta, String anno, String stato);
	
	public SigasValidazione findByAnnoAndCodiceAzienda(String anno, String codiceAzienda);

}
