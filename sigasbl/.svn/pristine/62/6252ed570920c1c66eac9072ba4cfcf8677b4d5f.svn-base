/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasTipoTassi;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoTassoEntityMapper;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoTassoVO;


@Component
public class TipoTassoEntityMapperImpl implements TipoTassoEntityMapper {
	
	
	
	@Override
	public TipoTassoVO mapEntityToVO(SigasTipoTassi dto) {
		if (dto == null)
			return null;
		TipoTassoVO vo = new TipoTassoVO();
		vo.setIdTipoTasso(dto.getIdTipoTasso());
		vo.setNomeTasso(dto.getNomeTasso());
		vo.setDescrizione(dto.getDescrizione());
		return vo;
	}

	@Override
	public SigasTipoTassi mapVOtoEntity(TipoTassoVO vo) {
		if (null == vo)
			return null;
		SigasTipoTassi dto = new SigasTipoTassi();
		dto.setIdTipoTasso(vo.getIdTipoTasso());
		dto.setNomeTasso(vo.getNomeTasso());
		dto.setDescrizione(vo.getDescrizione());
		return dto;
	}

	@Override
	public List<TipoTassoVO> mapListEntityToListVO(List<SigasTipoTassi> en) {
		if (null == en)
			return null;
		List<TipoTassoVO> v = new ArrayList<TipoTassoVO>();
		for (SigasTipoTassi e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}


}
