/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasRimborso;
import it.csi.sigas.sigasbl.model.mapper.entity.RimborsoEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.RimborsoVO;


@Component
public class RimborsoEntityMapperImpl implements RimborsoEntityMapper {
	
	@Autowired
	private AnagraficaSoggettoEntityMapperImpl anagraficaSoggettoEntityMapperImpl;
	
	@Autowired
	private AnaComunicazioniEntityMapperImpl anaComunicazioniEntityMapperImpl;
	
	
	@Override
	public RimborsoVO mapEntityToVO(SigasRimborso dto) {
		if (dto == null)
			return null;
		RimborsoVO vo = new RimborsoVO();
		
		vo.setIdRimborso(dto.getIdRimborso());
		vo.setAnnualita(dto.getAnnualita());
		vo.setDataIstanza(dto.getDataIstanza());
		vo.setDataRimborso(dto.getDataRimborso());		
		vo.setImporto(dto.getImporto());
		vo.setImportoRimborsato(dto.getImportoRimborsato());
		vo.setStatoPratica(dto.getStatoPratica());
		vo.setVersion(dto.getVersion());
		vo.setAnagraficaSoggettoVO(anagraficaSoggettoEntityMapperImpl.mapEntityToVO(dto.getSigasAnagraficaSoggetti()));
		vo.setAnaComunicazioniVO(anaComunicazioniEntityMapperImpl.mapEntityToVO(dto.getSigasAnaComunicazioni()));
		vo.setVersion(dto.getVersion());
		vo.setDataVersamento(dto.getDataVersamento());
		vo.setInsDate(dto.getInsDate());
		vo.setInsUser(dto.getInsUser());
		vo.setModDate(dto.getModDate());
		vo.setModUser(dto.getModUser());
		return vo;
	}

	@Override
	public SigasRimborso mapVOtoEntity(RimborsoVO vo) {
		if (null == vo)
			return null;
		
		SigasRimborso dto = new SigasRimborso();
		
		dto.setIdRimborso(vo.getIdRimborso());
		dto.setAnnualita(vo.getAnnualita());
		dto.setDataIstanza(vo.getDataIstanza());
		dto.setDataRimborso(vo.getDataRimborso());
		dto.setImporto(vo.getImporto());
		dto.setImportoRimborsato(vo.getImportoRimborsato());
		dto.setStatoPratica(vo.getStatoPratica());		
		
		dto.setVersion(vo.getVersion());				
		
		dto.setSigasAnagraficaSoggetti(anagraficaSoggettoEntityMapperImpl.mapVOtoEntity(vo.getAnagraficaSoggettoVO()));
		dto.setSigasAnaComunicazioni(anaComunicazioniEntityMapperImpl.mapVOtoEntity(vo.getAnaComunicazioniVO()));
		dto.setVersion(vo.getVersion());
		dto.setDataVersamento(vo.getDataVersamento());
		dto.setModDate(vo.getModDate());
		dto.setModUser(vo.getModUser());
		dto.setInsDate(vo.getInsDate());
		dto.setInsUser(vo.getInsUser());
		return dto;
	}

	@Override
	public List<RimborsoVO> mapListEntityToListVO(List<SigasRimborso> en) {
		if (null == en)
			return null;
		List<RimborsoVO> v = new ArrayList<RimborsoVO>();
		for (SigasRimborso e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}


}
