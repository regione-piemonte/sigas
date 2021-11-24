/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasStatoArchiviazione;
import it.csi.sigas.sigasbl.model.mapper.entity.StatoArchiviazioneEntityMapper;
import it.csi.sigas.sigasbl.model.vo.documenti.StatoArchiviazioneVO;


@Component
public class StatoAllegatoEntityMapperImpl implements StatoArchiviazioneEntityMapper { 

	
	
	@Override
	public StatoArchiviazioneVO mapEntityToVO(SigasStatoArchiviazione dto) {

		if (dto == null)
			return null;
		StatoArchiviazioneVO vo = new StatoArchiviazioneVO();

		vo.setCodiceStato(dto.getCodiceStato());
		vo.setDescrizione(dto.getDescrizione());
		vo.setIdStatoArchiviazione(dto.getIdStatoArchiviazione());
		
		return vo;
	}

	@Override
	public SigasStatoArchiviazione mapVOtoEntity(StatoArchiviazioneVO vo) {
		if (null == vo)
			return null;
		SigasStatoArchiviazione dto = new SigasStatoArchiviazione();
		
		dto.setCodiceStato(vo.getCodiceStato());
		dto.setDescrizione(vo.getDescrizione());
		dto.setIdStatoArchiviazione(vo.getIdStatoArchiviazione());

		return dto;
	}

	@Override
	public List<StatoArchiviazioneVO> mapListEntityToListVO(List<SigasStatoArchiviazione> en) {
		if (null == en)
			return null;
		List<StatoArchiviazioneVO> v = new ArrayList<StatoArchiviazioneVO>();
		for (SigasStatoArchiviazione e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}
	
}
