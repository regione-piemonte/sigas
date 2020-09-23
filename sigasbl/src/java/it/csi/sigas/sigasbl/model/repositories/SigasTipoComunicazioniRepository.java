/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.common.TipoComunicazioni;
import it.csi.sigas.sigasbl.model.entity.SigasTipoComunicazioni;


@Repository
public interface SigasTipoComunicazioniRepository extends CrudRepository<SigasTipoComunicazioni, Long> {

	public List<SigasTipoComunicazioni> findAll();

	public SigasTipoComunicazioni findByDenominazione(TipoComunicazioni dichiarazioneUtf);

}
