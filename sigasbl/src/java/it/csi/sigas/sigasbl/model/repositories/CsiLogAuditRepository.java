/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.CsiLogAuditPK;


@Repository
public interface CsiLogAuditRepository extends CrudRepository<CsiLogAudit, CsiLogAuditPK> {
	@Transactional
	@Modifying
	@Query(value="INSERT INTO csi_log_audit (data_ora, id_app, id_address, utente, operazione, ogg_oper, key_oper) VALUES(?1, ?2, ?3, ?4, ?5, ?6, ?7) ON CONFLICT ON CONSTRAINT pk_csi_log_audit DO NOTHING ", nativeQuery=true)
	void saveOrUpdate(Date dataOra, String idApp, String idAddress, String utente, String operazione, String oggOper, String keyOper);


}
