/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasTipoVersamento;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoVersamentoEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.TipoVersamentoVO;


@Component
public class TipoVersamentoEntityMapperImpl implements TipoVersamentoEntityMapper {
	
	
	
	@Override
	public TipoVersamentoVO mapEntityToVO(SigasTipoVersamento dto) {
		if (dto == null)
			return null;
		TipoVersamentoVO vo = new TipoVersamentoVO();
		vo.setIdTipoVersamento(dto.getIdTipoVersamento());
		vo.setDenominazione(dto.getDenominazione());
		vo.setDescrizione(dto.getDescrizione());
		return vo;
	}

	@Override
	public SigasTipoVersamento mapVOtoEntity(TipoVersamentoVO vo) {
		if (null == vo)
			return null;
		SigasTipoVersamento dto = new SigasTipoVersamento();
		dto.setIdTipoVersamento(vo.getIdTipoVersamento());
		dto.setDenominazione(vo.getDenominazione());
		dto.setDescrizione(vo.getDescrizione());
		return dto;
	}

	@Override
	public List<TipoVersamentoVO> mapListEntityToListVO(List<SigasTipoVersamento> en) {
		if (null == en)
			return null;
		List<TipoVersamentoVO> v = new ArrayList<TipoVersamentoVO>();
		for (SigasTipoVersamento e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}


}
