/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasTipoDocumento;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoDocumentoEntityMapper;
import it.csi.sigas.sigasbl.model.vo.documenti.TipoDocumentoVO;


@Component
public class TipoDocumentoEntityMapperImpl implements TipoDocumentoEntityMapper {

	
	@Override
	public TipoDocumentoVO mapEntityToVO(SigasTipoDocumento dto) {

		if (dto == null)
			return null;
		TipoDocumentoVO vo = new TipoDocumentoVO();
		vo.setIdTipoDocumento(dto.getIdTipoDocumento());
		vo.setCodiceTipoDocumento(dto.getCodiceTipoDocumento());
		vo.setDescrizione(dto.getDescrizione());
		vo.setIdTipoDocumentoPadre(dto.getIdTipoDocumentoPadre());
		vo.setDescVitalRecordCodeType(dto.getDescVitalRecordCodeType());
		return vo;
	}

	@Override
	public SigasTipoDocumento mapVOtoEntity(TipoDocumentoVO vo) {
		if (null == vo)
			return null;
		SigasTipoDocumento dto = new SigasTipoDocumento();
		dto.setIdTipoDocumento(vo.getIdTipoDocumento());
		dto.setCodiceTipoDocumento(vo.getCodiceTipoDocumento());
		dto.setDescrizione(vo.getDescrizione());
		dto.setIdTipoDocumentoPadre(vo.getIdTipoDocumentoPadre());
		dto.setDescVitalRecordCodeType(vo.getDescVitalRecordCodeType());
		return dto;
	}

	@Override
	public List<TipoDocumentoVO> mapListEntityToListVO(List<SigasTipoDocumento> en) {
		if (null == en)
			return null;
		List<TipoDocumentoVO> v = new ArrayList<TipoDocumentoVO>();
		for (SigasTipoDocumento e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
