/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;
import it.csi.sigas.sigasbl.model.entity.SigasLegaleRappresent;



/***
 *
 * @author riccardo.bova
 * @date 08 mar 2018
 *
 ***/

@Repository
public interface SigasLegaleRappresentRepository extends CrudRepository<SigasLegaleRappresent, Long> {


	public SigasLegaleRappresent findByCfLegaleRappresent(String cfLegaleRappresent);

	public SigasLegaleRappresent findBySigasDichiarante(SigasDichiarante sigasDichiarante);
	
	public SigasLegaleRappresent findBySigasDichiaranteIdDichiarante(long idDichiarante);
	
	@Query(value="SELECT DISTINCT sd.codice_azienda "
			   + "FROM sigas_dichiarante sd "
			   		+ "INNER JOIN sigas_legale_rappresent slr ON slr.fk_dichiarante = sd.id_dichiarante "
			   + "WHERE slr.cf_legale_rappresent LIKE :cfLegaleRappresentante" ,nativeQuery = true)
	public List<String> findElencoCodiceAziendaAssociateByCF(@Param("cfLegaleRappresentante")String cfLegaleRappresentante);
	
	@Query(value="SELECT so.cf_operatore "
			   + "FROM sigas_operatore so "
			   	+ "INNER JOIN ("			
			   					+ "SELECT DISTINCT sd.id_dichiarante "
			   					+ "FROM sigas_dichiarante sd "
			   						+ "INNER JOIN sigas_legale_rappresent slr ON slr.fk_dichiarante = sd.id_dichiarante "
			   					+ "WHERE slr.cf_legale_rappresent LIKE :cfLegaleRappresentante "
			   				+ ") AS tbl_aziende_rapp ON tbl_aziende_rapp.id_dichiarante  = so.fk_dichiarante "
			   + "WHERE so.cf_operatore NOT LIKE :cfLegaleRappresentante",nativeQuery = true)
	public List<String> findElencoCFOperatoreAziendeLegaleRappresentante(@Param("cfLegaleRappresentante")String cfLegaleRappresentante);

}
