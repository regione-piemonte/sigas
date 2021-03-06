/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasComune;
import it.csi.sigas.sigasbl.model.mapper.entity.ComuneEntityMapper;
import it.csi.sigas.sigasbl.model.vo.luoghi.ComuneVO;

@Component
public class ComuneEntityMapperImpl implements ComuneEntityMapper {

	@Override
	public ComuneVO mapEntityToVO(SigasComune irbaDComune) {
		if (irbaDComune == null)
			return null;
		ComuneVO comuneVO = new ComuneVO();
		comuneVO.setId(irbaDComune.getIdComune());
		comuneVO.setDenominazione(irbaDComune.getDenomComune());

		return comuneVO;
	}

	@Override
	public List<ComuneVO> mapListEntityToListVO(List<SigasComune> en) {
		if (null == en)
			return null;
		List<ComuneVO> v = new ArrayList<ComuneVO>();
		for (SigasComune e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

	@Override
	public SigasComune mapVOtoEntity(ComuneVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

}
