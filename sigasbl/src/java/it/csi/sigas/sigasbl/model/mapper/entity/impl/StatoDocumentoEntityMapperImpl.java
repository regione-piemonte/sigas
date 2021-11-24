/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasStatoDocumento;
import it.csi.sigas.sigasbl.model.mapper.entity.StatoDocumentoEntityMapper;
import it.csi.sigas.sigasbl.model.vo.documenti.StatoDocumentoVO;


@Component
public class StatoDocumentoEntityMapperImpl implements StatoDocumentoEntityMapper {

	
	@Override
	public StatoDocumentoVO mapEntityToVO(SigasStatoDocumento dto) {

		if (dto == null)
			return null;
		StatoDocumentoVO vo = new StatoDocumentoVO();
		vo.setCodiceStato(dto.getCodiceStato());
		vo.setDescrizione(dto.getDescrizione());
		vo.setIdStatoDocumento(dto.getIdStatoDocumento());
		return vo;
	}

	@Override
	public SigasStatoDocumento mapVOtoEntity(StatoDocumentoVO vo) {
		if (null == vo)
			return null;
		SigasStatoDocumento dto = new SigasStatoDocumento();
		dto.setCodiceStato(vo.getCodiceStato());
		dto.setDescrizione(vo.getDescrizione());
		dto.setIdStatoDocumento(vo.getIdStatoDocumento());
		return dto;
	}

	@Override
	public List<StatoDocumentoVO> mapListEntityToListVO(List<SigasStatoDocumento> en) {
		if (null == en)
			return null;
		List<StatoDocumentoVO> v = new ArrayList<StatoDocumentoVO>();
		for (SigasStatoDocumento e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
