/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasTipoCarrello;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoCarrelloEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.TipoCarrelloVO;


@Component
public class TipoCarrelloEntityMapperImpl implements TipoCarrelloEntityMapper {
	
	
	
	@Override
	public TipoCarrelloVO mapEntityToVO(SigasTipoCarrello dto) {
		if (dto == null)
			return null;
		TipoCarrelloVO vo = new TipoCarrelloVO();
		vo.setIdTipoVersamento(dto.getIdTipoCarrello());
		vo.setDenominazione(dto.getDenominazione());
		vo.setDescrizione(dto.getDescrizione());
		return vo;
	}

	@Override
	public SigasTipoCarrello mapVOtoEntity(TipoCarrelloVO vo) {
		if (null == vo)
			return null;
		SigasTipoCarrello dto = new SigasTipoCarrello();
		dto.setIdTipoCarrello(vo.getIdTipoVersamento());
		dto.setDenominazione(vo.getDenominazione());
		dto.setDescrizione(vo.getDescrizione());
		return dto;
	}

	@Override
	public List<TipoCarrelloVO> mapListEntityToListVO(List<SigasTipoCarrello> en) {
		if (null == en)
			return null;
		List<TipoCarrelloVO> v = new ArrayList<TipoCarrelloVO>();
		for (SigasTipoCarrello e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}


}
