/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasAllarmi;


@Repository
public interface SigasAllarmiRepository extends CrudRepository<SigasAllarmi, Integer> {

	@Query("select count(u) from SigasAllarmi u where codiceAzienda = :codiceAzienda and sigasTipoAllarme.denominazione = :denominazioneAllarme")
	long findByCodiceAziendaAndTipo(@Param("codiceAzienda") String codiceAzienda,
			@Param("denominazioneAllarme") String denominazioneAllarme);

	// versamenti
	List<SigasAllarmi> findByCodiceAziendaAndSigasTipoAllarmeIdTipoAllarme(String codiceAzienda, int idTipoAllarme);

	// UTFService
	List<SigasAllarmi> findBySigasDichConsumiAnnualitaAndSigasTipoAllarmeIdTipoAllarme(String anno, int idTipoAllarme);

	SigasAllarmi findBySigasDichConsumiIdConsumiAndSigasAnaComunicazioniIdComunicazioneAndCodiceAziendaAndSigasTipoAllarmeIdTipoAllarmeAndAnnualita(
			Long idConsumi, Long idComunciazione, String codiceAzienda, int idTipoAllarme, String anno);

	// documenti
	List<SigasAllarmi> findBySigasAnaComunicazioniIdComunicazioneAndCodiceAziendaAndStatus(long idComunicazione,
			String codiceAzienda, int status);

	SigasAllarmi findBySigasDichConsumiIdConsumiAndSigasTipoAllarmeIdTipoAllarme(Long idConsumi, int idTipoAllarme);

	List<SigasAllarmi> findBySigasDichVersamentiIdVersamentoAndSigasTipoAllarmeIdTipoAllarme(Long idVersamento,
			int idTipoAllarme);

	SigasAllarmi findBySigasAnaComunicazioniIdComunicazioneAndSigasTipoAllarmeIdTipoAllarme(Long idComunicazione,
			int idTipoAllarme);
	
	@Query(value="select id_allarme from sigas_allarmi sa where id_versamento =?1 and id_tipo_allarme =?2 ", nativeQuery=true)
	Integer findByIdVersamentoAndIdTipoAllarme(long idVersamento, int idTipoAllarme);
	
	
	List<SigasAllarmi> findBySigasAnaComunicazioniIdComunicazione(Long idComunicazione);
	
	@Modifying
	@Query(value="update sigas_allarmi set status = :status  where id_allarme = :idAllarme ", nativeQuery=true)	
	void updateStatoAllarmeAccertamento(@Param("idAllarme") int idAllarme, @Param("status") int status);

}
