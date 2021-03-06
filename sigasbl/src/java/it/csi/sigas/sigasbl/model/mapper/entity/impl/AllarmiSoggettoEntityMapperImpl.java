/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasAllarmi;
import it.csi.sigas.sigasbl.model.entity.SigasAnaComunicazioni;
import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;
import it.csi.sigas.sigasbl.model.mapper.entity.AllarmiSoggettoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.AnaComunicazioniEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichConsumiEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiSoggettoVO;

@Component
public class AllarmiSoggettoEntityMapperImpl implements AllarmiSoggettoEntityMapper {

	
	@Autowired
	private DichConsumiEntityMapper dichConsumiEntityMapper;

	@Autowired
	private AnaComunicazioniEntityMapper anaComunicazioniEntityMapper;

	@Override
	public AllarmiSoggettoVO mapEntityToVO(SigasAllarmi dto) {

		if (dto == null)
			return null;
		AllarmiSoggettoVO vo = new AllarmiSoggettoVO();  
		vo.setIdAllarme(dto.getIdAllarme().intValue()); 
		SigasDichConsumi consumi = dto.getSigasDichConsumi();
		if (consumi!= null) {
			vo.setIdConsumi(dichConsumiEntityMapper.mapEntityToVO(consumi).getId_consumi());
		}
		SigasAnaComunicazioni comunicazioni = dto.getSigasAnaComunicazioni();
		if (comunicazioni!= null) {
			vo.setIdComunicazioni(anaComunicazioniEntityMapper.mapEntityToVO(comunicazioni).getIdComunicazione());
		}
		vo.setCodiceAzienda(dto.getCodiceAzienda());
		vo.setAttivazione(dto.getAttivazione());
		vo.setStatus(dto.getStatus());
		vo.setNota(dto.getNota());
		vo.setIdTipoAllarme(dto.getSigasTipoAllarme().getIdTipoAllarme());
		return vo;
	}

	@Override
	public SigasAllarmi mapVOtoEntity(AllarmiSoggettoVO vo) {
		return null;
	}

	@Override
	public List<AllarmiSoggettoVO> mapListEntityToListVO(List<SigasAllarmi> en) {
		if (null == en)
			return null;
		List<AllarmiSoggettoVO> v = new ArrayList<AllarmiSoggettoVO>();
		for (SigasAllarmi e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
