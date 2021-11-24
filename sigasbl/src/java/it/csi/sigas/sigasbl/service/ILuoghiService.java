/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import java.util.List;

import it.csi.sigas.sigasbl.model.vo.luoghi.ComuneVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.NazioneVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.RegioneVO;

public interface ILuoghiService {

	List<ProvinciaVO> getAllProvince();

	List<ComuneVO> getComuniByIdProvincia(Long idProvinciaSelezionata);

	ProvinciaVO getProvinciaBySigla(String siglaProvncia);
	


	List<ComuneVO> getAllComuni();



	ComuneVO getComuneByDescrizione(String descrizione);

	ProvinciaVO getProvinciaByDescrizione(String descrizione);

	ProvinciaVO getProvinciaByIdComune(Long idComune);


	List<String> ricercaIndirizzo(String indirizzo, Long id);
	
	
}
