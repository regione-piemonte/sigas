/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasAliquote;
import it.csi.sigas.sigasbl.model.entity.SigasTipoAliquote;
import it.csi.sigas.sigasbl.model.entity.SigasTipoConsumo;
import it.csi.sigas.sigasbl.model.mapper.entity.AliquoteEntityMapper;
import it.csi.sigas.sigasbl.model.vo.impostazioni.AliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoAliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoConsumoVO;


@Component
public class AliquoteEntityMapperImpl implements AliquoteEntityMapper {
	
	@Override
	public AliquoteVO mapEntityToVO(SigasAliquote dto) {
		if (dto == null)
			return null;
		AliquoteVO vo = new AliquoteVO();
		vo.setId(dto.getIdAliquota());
		vo.setAliquota(dto.getAliquota());
		vo.setProgRigo(dto.getProgRigo());
		vo.setValiditaStart(dto.getValiditaStart());
		vo.setValiditaEnd(dto.getValiditaEnd());
		vo.setVersion(dto.getVersion());
		vo.setInsDate(dto.getInsDate());
		vo.setInsUser(dto.getInsUser());
		vo.setModDate(dto.getModDate());
		vo.setModUser(dto.getModUser());
		
		TipoAliquoteVO tipo = new TipoAliquoteVO();
		tipo.setIdTipoAliquota(dto.getSigasTipoAliquote().getIdTipoAliquota());
		tipo.setNomeAliquota(dto.getSigasTipoAliquote().getNomeAliquota());
		tipo.setDescrizione(dto.getSigasTipoAliquote().getDescrizione());
		tipo.setNuovoAllacciamento(dto.getSigasTipoAliquote().isNuovoAllacciamentp());
		tipo.setTipo(dto.getSigasTipoAliquote().getTipo());
		TipoConsumoVO tipoConsumo = new TipoConsumoVO();
		tipoConsumo.setIdTipoConsumo(dto.getSigasTipoAliquote().getSigasTipoConsumo().getIdTipoConsumo());
		tipoConsumo.setDescrizione(dto.getSigasTipoAliquote().getSigasTipoConsumo().getDescrizione());
		tipoConsumo.setCampoDichConsumo(dto.getSigasTipoAliquote().getSigasTipoConsumo().getCampoDichConsumo());
		tipo.setTipoConsumo(tipoConsumo);
		vo.setTipoAliquote(tipo);

		return vo;
	}

	@Override
	public SigasAliquote mapVOtoEntity(AliquoteVO vo) {
		if (null == vo)
			return null;
		SigasAliquote dto = new SigasAliquote();
		dto.setIdAliquota(vo.getId().longValue());
		dto.setAliquota(vo.getAliquota());
		dto.setValiditaStart(vo.getValiditaStart());
		dto.setValiditaEnd(vo.getValiditaEnd());
		dto.setProgRigo(vo.getProgRigo());
		dto.setVersion(vo.getVersion());
		dto.setInsDate(vo.getInsDate());
		dto.setInsUser(vo.getInsUser());
		dto.setModUser(vo.getModUser());
		dto.setModDate(vo.getModDate());
		
		SigasTipoAliquote sigasTipoAliquote = new SigasTipoAliquote();
		sigasTipoAliquote.setIdTipoAliquota(vo.getTipoAliquote().getIdTipoAliquota());
		sigasTipoAliquote.setNomeAliquota(vo.getTipoAliquote().getNomeAliquota());
		sigasTipoAliquote.setDescrizione(vo.getTipoAliquote().getDescrizione());
		sigasTipoAliquote.setNuovoAllacciamentp(vo.getTipoAliquote().isNuovoAllacciamento());
		sigasTipoAliquote.setTipo(vo.getTipoAliquote().getTipo());
		SigasTipoConsumo sigasTipoConsumo = new SigasTipoConsumo();
		sigasTipoConsumo.setIdTipoConsumo(vo.getTipoAliquote().getTipoConsumo().getIdTipoConsumo());
		sigasTipoConsumo.setDescrizione(vo.getTipoAliquote().getTipoConsumo().getDescrizione());
		sigasTipoConsumo.setCampoDichConsumo(vo.getTipoAliquote().getTipoConsumo().getCampoDichConsumo());
		sigasTipoAliquote.setSigasTipoConsumo(sigasTipoConsumo);
		dto.setSigasTipoAliquote(sigasTipoAliquote);

		return dto;
	}

	@Override
	public List<AliquoteVO> mapListEntityToListVO(List<SigasAliquote> en) {
		if (null == en)
			return null;
		List<AliquoteVO> v = new ArrayList<AliquoteVO>();
		for (SigasAliquote e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}


}
