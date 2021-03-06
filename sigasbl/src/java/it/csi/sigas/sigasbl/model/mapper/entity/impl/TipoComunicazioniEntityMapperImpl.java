/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasTipoComunicazioni;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoComunicazioniEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.TipoComunicazioniVO;


@Component
public class TipoComunicazioniEntityMapperImpl implements TipoComunicazioniEntityMapper {
	
	@Override
	public TipoComunicazioniVO mapEntityToVO(SigasTipoComunicazioni dto) {
		if (dto == null)
			return null;
		TipoComunicazioniVO vo = new TipoComunicazioniVO();
		vo.setIdTipoComunicazione(dto.getIdTipoComunicazione());
		vo.setDenominazione(dto.getDenominazione());
		vo.setDescrizione(dto.getDescrizione());
		
		return vo;
	}

	@Override
	public SigasTipoComunicazioni mapVOtoEntity(TipoComunicazioniVO vo) {
		if (null == vo)
			return null;
		SigasTipoComunicazioni dto = new SigasTipoComunicazioni();
		dto.setIdTipoComunicazione(vo.getIdTipoComunicazione());
		dto.setDenominazione(vo.getDenominazione());
		dto.setDescrizione(vo.getDescrizione());

		return dto;
	}

	@Override
	public List<TipoComunicazioniVO> mapListEntityToListVO(List<SigasTipoComunicazioni> en) {
		if (null == en)
			return null;
		List<TipoComunicazioniVO> v = new ArrayList<TipoComunicazioniVO>();
		for (SigasTipoComunicazioni e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}


}
