/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.mapper.entity.DichiaranteEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.DichiaranteVO;


@Component
public class DichiaranteEntityMapperImpl implements DichiaranteEntityMapper {

	@Override
	public DichiaranteVO mapEntityToVO(SigasAnagraficaSoggetti dto) {

		if (dto == null)
			return null;
		DichiaranteVO vo = new DichiaranteVO();
		vo.setIdAnag(dto.getIdAnag());
		vo.setCodiceAzienda(dto.getCodiceAzienda());
		vo.setDenominazione(dto.getDenominazione());

		return vo;
	}

	@Override
	public SigasAnagraficaSoggetti mapVOtoEntity(DichiaranteVO vo) {
		if (null == vo)
			return null;
		SigasAnagraficaSoggetti dto = new SigasAnagraficaSoggetti();
		
		dto.setIdAnag(vo.getIdAnag());
		dto.setCodiceAzienda(vo.getCodiceAzienda());
		dto.setDenominazione(vo.getDenominazione());
		
		return dto;
	}

	@Override
	public List<DichiaranteVO> mapListEntityToListVO(List<SigasAnagraficaSoggetti> en) {
		// TODO Auto-generated method stub
		return null;
	}

}
