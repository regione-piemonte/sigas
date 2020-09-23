/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasTipoAliquote;
import it.csi.sigas.sigasbl.model.entity.SigasTipoConsumo;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoAliquoteEntityMapper;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoAliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoConsumoVO;


@Component
public class TipoAliquoteEntityMapperImpl implements TipoAliquoteEntityMapper {
	
	@Override
	public TipoAliquoteVO mapEntityToVO(SigasTipoAliquote dto) {
		if (dto == null)
			return null;
		TipoAliquoteVO vo = new TipoAliquoteVO();
		vo.setIdTipoAliquota(dto.getIdTipoAliquota());
		vo.setNomeAliquota(dto.getNomeAliquota());
		vo.setDescrizione(dto.getDescrizione());
		vo.setNuovoAllacciamento(dto.isNuovoAllacciamentp());
		vo.setTipo(dto.getTipo());
		
		TipoConsumoVO tipoConsumo = new TipoConsumoVO();
		tipoConsumo.setIdTipoConsumo(dto.getSigasTipoConsumo().getIdTipoConsumo());
		tipoConsumo.setDescrizione(dto.getSigasTipoConsumo().getDescrizione());
		tipoConsumo.setCampoDichConsumo(dto.getSigasTipoConsumo().getCampoDichConsumo());
		vo.setTipoConsumo(tipoConsumo);

		return vo;
	}

	@Override
	public SigasTipoAliquote mapVOtoEntity(TipoAliquoteVO vo) {
		if (null == vo)
			return null;
		SigasTipoAliquote dto = new SigasTipoAliquote();
		dto.setIdTipoAliquota(vo.getIdTipoAliquota().longValue());
		dto.setNomeAliquota(vo.getNomeAliquota());
		dto.setDescrizione(vo.getDescrizione());
		dto.setNuovoAllacciamentp(vo.isNuovoAllacciamento());
		dto.setTipo(vo.getTipo());
		
		SigasTipoConsumo sigasTipoConsumo = new SigasTipoConsumo();
		sigasTipoConsumo.setIdTipoConsumo(vo.getTipoConsumo().getIdTipoConsumo());
		sigasTipoConsumo.setDescrizione(vo.getTipoConsumo().getDescrizione());
		sigasTipoConsumo.setCampoDichConsumo(vo.getTipoConsumo().getCampoDichConsumo());
		dto.setSigasTipoConsumo(sigasTipoConsumo);
		
		return dto;
	}

	@Override
	public List<TipoAliquoteVO> mapListEntityToListVO(List<SigasTipoAliquote> en) {
		if (null == en)
			return null;
		List<TipoAliquoteVO> v = new ArrayList<TipoAliquoteVO>();
		for (SigasTipoAliquote e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}


}
