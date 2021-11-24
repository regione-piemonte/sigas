/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.dispatcher.LuoghiDispatcher;
import it.csi.sigas.sigasbl.model.vo.luoghi.ComuneVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.NazioneVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.RegioneVO;
import it.csi.sigas.sigasbl.service.ILuoghiService;

@Component
public class LuoghiDispatcherImpl implements LuoghiDispatcher {
	
	@Autowired
	private ILuoghiService luoghiService;

	@Override
	public List<ProvinciaVO> getAllProvince() {
		return luoghiService.getAllProvince();
	}

	@Override
	public List<ComuneVO> getComuniByIdProvincia(Long idProvinciaSelezionata) {
		return luoghiService.getComuniByIdProvincia(idProvinciaSelezionata);
	}

	@Override
	public ProvinciaVO getProvinciaBySigla(String siglaProvncia) {
		return luoghiService.getProvinciaBySigla(siglaProvncia);
	}
	





	@Override
	public List<ComuneVO> getAllComuni() {
		return luoghiService.getAllComuni();
	}



	

	@Override
	public List<String> ricercaIndirizzo(String indirizzo, Long id) {
		return luoghiService.ricercaIndirizzo(indirizzo, id);
	}


}
