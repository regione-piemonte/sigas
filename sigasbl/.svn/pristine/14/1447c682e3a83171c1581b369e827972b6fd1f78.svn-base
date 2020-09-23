/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.mapper.entity.ProvinciaEntityMapper;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;

@Component
public class ProvinciaEntityMapperImpl implements ProvinciaEntityMapper {

	@Override
	public ProvinciaVO mapEntityToVO(SigasProvincia dto) {
		if (dto == null)
			return null;

		ProvinciaVO provinciaVO = new ProvinciaVO();
		provinciaVO.setId(dto.getIdProvincia());
		provinciaVO.setDenominazione(dto.getDenomProvincia());
		provinciaVO.setSigla(dto.getSiglaProvincia());
		return provinciaVO;
	}

	@Override
	public List<ProvinciaVO> mapListEntityToListVO(List<SigasProvincia> en) {
		if (null == en)
			return null;
		List<ProvinciaVO> v = new ArrayList<ProvinciaVO>();
		for (SigasProvincia e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

	@Override
	public SigasProvincia mapVOtoEntity(ProvinciaVO vo) {
		SigasProvincia dto = new SigasProvincia();
		
		dto.setDenomProvincia(vo.getDenominazione());
		dto.setIdProvincia(vo.getId());
		
		return dto;
	}

}
