/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasDichVersamenti;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;

@Repository
public interface SigasDichVersamentiRepository extends CrudRepository<SigasDichVersamenti, Long> {
	
	
	@Query("select COALESCE(SUM(u.importo), 0) from SigasDichVersamenti u where u.annualita = :annualita and u.sigasProvincia.siglaProvincia = :provincia"
			+ " and u.sigasAnagraficaSoggetti = :sigasAnagraficaSoggetti")
	double sumByAnnoAndProvinciaAndSoggetto(@Param("annualita") String annualita, @Param("provincia") String provincia,
			@Param("sigasAnagraficaSoggetti") SigasAnagraficaSoggetti sigasAnagraficaSoggetti);
	
	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaOrderBySigasProvinciaSiglaProvinciaAsc(Long idAnag, String anno);
	
	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnag(Long idAnag);

	@Query("SELECT DISTINCT a.annualita FROM SigasDichVersamenti a where a.sigasAnagraficaSoggetti.idAnag = :id")
	List<String> findDistinctBySigasAnagraficaSoggettiIdAnag(@Param("id")Long id);
	
	@Query("SELECT DISTINCT a.sigasProvincia FROM SigasDichVersamenti a where a.sigasAnagraficaSoggetti.idAnag = :id")
	List<SigasProvincia> findDistinctProvBySigasAnagraficaSoggettiIdAnag(@Param("id")Long id);

	@Query("SELECT DISTINCT a.mese FROM SigasDichVersamenti a where a.sigasAnagraficaSoggetti.idAnag = :id"
			+ " and a.annualita = :annualita")
	List<String> findDistinctMonthBySigasAnagraficaSoggettiIdAnag(@Param("id")Long id, @Param("annualita") String annualita);

	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndMeseAndSigasTipoVersamentoIdTipoVersamentoAndSigasProvinciaIdProvincia(
			Long idAnag, String annualita, String mese, Long IdTipoVersament, Long IdProvincia);
	
	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasTipoVersamentoIdTipoVersamentoAndSigasProvinciaIdProvinciaOrderBySigasProvinciaIdProvinciaAsc(
			Long idAnag, String annualita, Long IdTipoVersament, Long IdProvincia);

	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndMeseAndSigasProvinciaIdProvincia(
			Long idAnag, String anno, String mese, Long provincia);

	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasProvinciaIdProvincia(Long idAnag,
			String anno, Long provincia);
	
	

	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndAnnualita(Long idAnag, String anno);
	
	//@Query(" FROM SigasDichVersamenti a where a.sigasAnagraficaSoggetti.idAnag = :id and a.sigasTipoVersamento.idTipoVersamento = 4")
	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndSigasTipoVersamentoIdTipoVersamento(Long idAnag,Long tipoAccertamento);

	SigasDichVersamenti findByMeseAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaIdProvincia(
			String mese, Long idTipoVersamento, String annualita, long idProvincia);
	
	SigasDichVersamenti findBySigasAnagraficaSoggettiIdAnagAndMeseAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaIdProvincia(
			Long id, String mese, Long idTipoVersamento, String annualita, long idProvincia);
	
	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaIdProvincia(
			Long id, Long idTipoVersamento, String annualita, long idProvincia);

	SigasDichVersamenti findByIdVersamentoNotAndMeseAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaIdProvinciaAndSigasAnagraficaSoggettiIdAnag(
			long idVersamento, String mese, Long idTipoVersamento, String valueOf, long idProvincia, long idAnag);

	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaSiglaProvincia(
			Long id, long idVersamento, String anno, String provincia);
	
	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndSigasTipoVersamentoIdTipoVersamentoAndAnnualita(
			Long id, long idVersamento, String anno);
	
	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndSigasTipoVersamentoIdTipoVersamentoAndSigasProvinciaSiglaProvincia(
			Long id, long idVersamento, String provincia);

	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasProvinciaIdProvinciaOrderByDataVersamentoAsc(
			Long idAnag, String anno, Long provincia);
	
	List<SigasDichVersamenti> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaOrderByDataVersamentoAsc(
			Long idAnag, String anno);
	
	List<SigasDichVersamenti> findBySigasDichConsumiIdConsumiAndSigasProvinciaIdProvincia(Long idConsumi, Long provincia);
	
	@Query("SELECT a FROM SigasDichVersamenti a where a.idVersamento = :id")
	SigasDichVersamenti findById(@Param("id")Long id);

}
