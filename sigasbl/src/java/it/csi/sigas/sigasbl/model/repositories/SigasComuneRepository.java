/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasComune;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;

@Repository
public interface SigasComuneRepository extends CrudRepository<SigasComune, Long> {

	public List<SigasComune> findByFineValiditaIsNullAndSigasProvinciaIdProvinciaOrderByDenomComuneAsc(Long sigasProvincia);

	@Query("select u from SigasComune u where u.fineValidita is null and u.denomComune = ?1")
	public SigasComune findByDenomComune(String denomComune);
	
	
	public List<SigasComune> findByFineValiditaIsNullAndSigasProvinciaOrderByDenomComuneAsc(SigasProvincia sigasProvincia);

	public SigasComune findByDenomComuneAndFineValiditaIsNull(String descrizioneComune);

	public SigasComune findByCodIstatComuneAndFineValiditaIsNull(String codIstatComune);

	public List<SigasComune> findByFineValiditaIsNullOrderByDenomComuneAsc();

}
