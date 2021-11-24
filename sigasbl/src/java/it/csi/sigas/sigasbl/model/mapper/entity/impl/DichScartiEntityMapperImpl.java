/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasDichScarti;
import it.csi.sigas.sigasbl.model.mapper.entity.DichScartiEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.ScartoVO;


@Component
public class DichScartiEntityMapperImpl implements DichScartiEntityMapper {

	@Override
	public ScartoVO mapEntityToVO(SigasDichScarti dto) {
		if (dto == null)
			return null;
		ScartoVO vo = new ScartoVO();
		vo.setIdScarti(dto.getIdScarti());
		vo.setProvincia(dto.getProvincia());
		vo.setConsumi(dto.getConsumi());
		vo.setAliquota(dto.getAliquota());
		vo.setImposta(dto.getImposta());
		vo.setConciliato(dto.isConciliato());
		vo.setDescUsoScarto(dto.getDescUsoScarto());

		return vo;
	}

	@Override
	public SigasDichScarti mapVOtoEntity(ScartoVO vo) {
		if (vo == null)
			return null;
		SigasDichScarti dto = new SigasDichScarti();
		dto.setIdScarti(vo.getIdScarti());;
		dto.setProvincia(vo.getProvincia());;
		dto.setConsumi(vo.getConsumi());;
		dto.setAliquota(vo.getAliquota());;
		dto.setImposta(vo.getImposta());
		dto.setConciliato(vo.isConciliato());
		dto.setDescUsoScarto(dto.getDescUsoScarto());
		
		return dto;
	}

	@Override
	public List<ScartoVO> mapListEntityToListVO(List<SigasDichScarti> en) {
		if (null == en)
			return null;
		List<ScartoVO> v = new ArrayList<ScartoVO>();
		for (SigasDichScarti e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
