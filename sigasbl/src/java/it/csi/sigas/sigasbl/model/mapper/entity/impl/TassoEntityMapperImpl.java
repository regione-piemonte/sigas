/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasTassi;
import it.csi.sigas.sigasbl.model.entity.SigasTipoTassi;
import it.csi.sigas.sigasbl.model.mapper.entity.TassoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoTassiRepository;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TassoVO;


@Component
public class TassoEntityMapperImpl implements TassoEntityMapper {
	
	@Autowired
	private SigasTipoTassiRepository sigasTipoTassiRepository;
	
	
	@Override
	public TassoVO mapEntityToVO(SigasTassi dto) {
		if (dto == null)
			return null;
		TassoVO vo = new TassoVO();
		vo.setId(dto.getIdTasso());
		vo.setValore(dto.getTasso());
		vo.setDataStart(dto.getValiditaStart());
		vo.setDataEnd(dto.getValiditaEnd());
		SigasTipoTassi tipo = dto.getSigasTipoTassi();
		if (tipo != null)
			vo.setIdTipoTasso(tipo.getIdTipoTasso());
		vo.setVersion(dto.getVersion());
		vo.setInsDate(dto.getInsDate());
		vo.setInsUser(dto.getInsUser());
		vo.setModDate(dto.getModDate());
		vo.setModUser(dto.getModUser());
		return vo;
	}

	@Override
	public SigasTassi mapVOtoEntity(TassoVO vo) {
		if (null == vo)
			return null;
		SigasTassi dto = new SigasTassi();
		dto.setIdTasso(vo.getId().longValue());
		dto.setTasso(vo.getValore());
		dto.setValiditaStart(vo.getDataStart());
		dto.setValiditaEnd(vo.getDataEnd());
		dto.setSigasTipoTassi(sigasTipoTassiRepository.findOne(vo.getIdTipoTasso()));
		dto.setVersion(vo.getVersion());
		dto.setInsDate(vo.getInsDate());
		dto.setInsUser(vo.getInsUser());
		dto.setModDate(vo.getModDate());
		dto.setModUser(vo.getModUser());
		return dto;
	}

	@Override
	public List<TassoVO> mapListEntityToListVO(List<SigasTassi> en) {
		if (null == en)
			return null;
		List<TassoVO> v = new ArrayList<TassoVO>();
		for (SigasTassi e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}


}
