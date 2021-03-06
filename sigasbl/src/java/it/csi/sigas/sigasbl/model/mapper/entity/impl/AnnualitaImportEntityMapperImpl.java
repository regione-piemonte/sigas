/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasImportUTF;
import it.csi.sigas.sigasbl.model.mapper.entity.AnnualitaImportEntityMapper;
import it.csi.sigas.sigasbl.model.vo.importUTF.AnnualitaImportVO;


@Component
public class AnnualitaImportEntityMapperImpl implements AnnualitaImportEntityMapper {

	@Override
	public AnnualitaImportVO mapEntityToVO(SigasImportUTF dto) {

		if (dto == null)
			return null;
		AnnualitaImportVO vo = new AnnualitaImportVO();
		
		vo.setAnno(dto.getAnnualita());
		vo.setEsito(dto.getEsito());
		vo.setErrore(dto.getErrore());

		return vo;
	}

	@Override
	public SigasImportUTF mapVOtoEntity(AnnualitaImportVO vo) {
		if (null == vo)
			return null;
		SigasImportUTF dto = new SigasImportUTF();
		
		dto.setAnnualita(vo.getAnno());
		dto.setEsito(dto.getEsito());
		dto.setErrore(vo.getErrore());
		
		return dto;
	}

	@Override
	public List<AnnualitaImportVO> mapListEntityToListVO(List<SigasImportUTF> en) {
		// TODO Auto-generated method stub
		return null;
	}

}
