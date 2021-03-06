/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;
import it.csi.sigas.sigasbl.model.mapper.entity.StoricoConsumiEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.StoricoConsumiVO;

@Component
public class StoricoConsumiEntityMapperImpl implements StoricoConsumiEntityMapper {
	
	@Override
	public StoricoConsumiVO mapEntityToVO(SigasDichConsumi dto) {
		if (dto == null)
			return null;

		StoricoConsumiVO storicoConsumiVO = new StoricoConsumiVO();
		storicoConsumiVO.setModIdCosumi(dto.getModIdConsumi());
		storicoConsumiVO.setIdConsumi(dto.getIdConsumi());
		storicoConsumiVO.setModDate(dto.getModdate());
		storicoConsumiVO.setModUser(dto.getModUser());
		storicoConsumiVO.setNote(dto.getNote());

				
		return storicoConsumiVO;
	}

	@Override
	public List<StoricoConsumiVO> mapListEntityToListVO(List<SigasDichConsumi> en) {
			if (null == en)
				return null;
			List<StoricoConsumiVO> v = new ArrayList<StoricoConsumiVO>();
			for (SigasDichConsumi e : en) {
				v.add(mapEntityToVO(e));
			}
			return v;
	}

	@Override
	public SigasDichConsumi mapVOtoEntity(StoricoConsumiVO vo) {
		if (vo == null)
			return null;
		
		SigasDichConsumi dto = new SigasDichConsumi();
		
		
		dto.setModIdConsumi(vo.getModIdCosumi());
		dto.setIdConsumi(vo.getIdConsumi());
		dto.setModdate(vo.getModDate());
		dto.setModUser(vo.getModUser());
		dto.setNote(vo.getNote());
		
		return dto;

	}

}
