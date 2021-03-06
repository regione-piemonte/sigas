/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasTassi;

@Repository
public interface SigasTassiRepository extends JpaRepository<SigasTassi, Long> {

	List<SigasTassi> findBySigasTipoTassiIdTipoTasso(Long idTipoTasso);

	List<SigasTassi> findBySigasTipoTassiIdTipoTassoOrderByValiditaStartAsc(Long i);
	
	List<SigasTassi>  findBySigasTipoTassiNomeTassoIgnoreCase(String nomeTasso);

}
