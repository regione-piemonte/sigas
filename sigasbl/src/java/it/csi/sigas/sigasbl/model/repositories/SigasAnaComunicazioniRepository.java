/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasAnaComunicazioni;


@Repository
public interface SigasAnaComunicazioniRepository extends CrudRepository<SigasAnaComunicazioni, Long> {

	List<SigasAnaComunicazioni> findBySigasAnagraficaSoggettiIdAnagAndDelUserIsNullAndDelDateIsNullOrderByDataDocumentoAsc(Long idAnag);

	SigasAnaComunicazioni findByDescrizioneAndAnnualitaOrderByDataDocumentoAsc(String filename, String anno);

	SigasAnaComunicazioni findByDescrizione(String filename);
	
	SigasAnaComunicazioni findByNProtocolloOrderByDataDocumentoAsc(String nprotocollo);

	SigasAnaComunicazioni findByRifArchivioOrderByDataDocumentoAsc(String uid);

	List<SigasAnaComunicazioni> findByAnnualitaOrderByDataDocumentoAsc(String anno);

	List<SigasAnaComunicazioni> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndDelUserIsNullAndDelDateIsNullOrderByDataDocumentoAsc(Long idAnag, String anno);

	List<SigasAnaComunicazioni> findBySigasAnagraficaSoggettiIdAnagAndSigasTipoComunicazioniIdTipoComunicazioneAndDelUserIsNullAndDelDateIsNullOrderByDataDocumentoAsc(Long idAnag, Long idTipoComunicazione);

	List<SigasAnaComunicazioni> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasTipoComunicazioniIdTipoComunicazioneAndDelUserIsNullAndDelDateIsNullOrderByDataDocumentoAsc(Long idAnag, String anno, Long idTipoComunicazione);
	
	List<SigasAnaComunicazioni> findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasTipoComunicazioniDenominazioneOrderByDataDocumentoAsc(Long idAnag, String anno, String tipoComunicazione);
}
