/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasTipoAliquote;

@Repository
public interface SigasTipoAliquoteRepository extends CrudRepository<SigasTipoAliquote, Long> {

	public List<SigasTipoAliquote> findByTipo(String tipo);

	public SigasTipoAliquote findByIdTipoAliquota(Long idTipoAliquota);

}
