/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasUtenteProvvisorio;


/**
 * @author riccardo.bova
 * @date 08 mar 2018
 */
@Repository
public interface SigasUtenteProvvisorioRepository extends CrudRepository<SigasUtenteProvvisorio, Long> {
	
	@Query("select u from SigasUtenteProvvisorio u order by insDate desc")
	List<SigasUtenteProvvisorio> findAllOrderByInsDateDesc();
	
	List<SigasUtenteProvvisorio> findByStatoOrderByInsDateDesc(String stato);
	
//	List<SigasUtenteProvvisorio> findByNumeroProtocolloAndSigasOperatoreCfOperatore(String numeroProtocollo, String cfOperatore);
//	
//	
//	List<SigasUtenteProvvisorio> findByNumeroProtocollo(String numeroProtocollo);
	
	
	List<SigasUtenteProvvisorio> findBySigasOperatoreCfOperatore(String cfOperatore);
	
	
	SigasUtenteProvvisorio findBySigasOperatoreCfOperatoreAndSigasDichiaranteCodiceAzienda(String cfOperatore,String codiceAzienda);

}
