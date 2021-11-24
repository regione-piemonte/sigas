/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasAllegato;
import it.csi.sigas.sigasbl.model.mapper.entity.AllegatoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.StatoArchiviazioneEntityMapper;
import it.csi.sigas.sigasbl.model.vo.documenti.AllegatoDocumentazioneVO;

@Component
public class AllegatoEntityMapperImpl implements AllegatoEntityMapper { 

	@Autowired
	private StatoArchiviazioneEntityMapper statoArchiviazioneEntityMapper;
	
	
	@Override
	public AllegatoDocumentazioneVO mapEntityToVO(SigasAllegato dto) {

		if (dto == null)
			return null;
		AllegatoDocumentazioneVO vo = new AllegatoDocumentazioneVO();

		vo.setDataOraProtocollo(dto.getDataOraProtocollo());
		vo.setDescrizione(dto.getDescrizione());
		vo.setIdAllegato(dto.getIdAllegato());
		vo.setIdIndex(dto.getIdIndex());
		vo.setStatoArchiviazioneVO(statoArchiviazioneEntityMapper.mapEntityToVO(dto.getSigasStatoArchiviazione()));
		vo.setInsDate(dto.getInsDate());
		vo.setInsUser(dto.getInsUser());
		vo.setNomeFile(dto.getNomeFile());
		vo.setNumeroProtocollo(dto.getNumeroProtocollo());
//		vo.setIdAllegatoDocumento(dto.getIdAllegatoDocumento());
		return vo;
	}

	@Override
	public SigasAllegato mapVOtoEntity(AllegatoDocumentazioneVO vo) {
		if (null == vo)
			return null;
		SigasAllegato dto = new SigasAllegato();
		
		dto.setDataOraProtocollo(vo.getDataOraProtocollo());
		dto.setDescrizione(vo.getDescrizione());
		dto.setIdAllegato(vo.getIdAllegato());
		dto.setIdIndex(vo.getIdIndex());
		dto.setSigasStatoArchiviazione(statoArchiviazioneEntityMapper.mapVOtoEntity(vo.getStatoArchiviazioneVO()));
		dto.setInsDate(vo.getInsDate());
		dto.setInsUser(vo.getInsUser());
		dto.setNomeFile(vo.getNomeFile());
		dto.setNumeroProtocollo(vo.getNumeroProtocollo());
//		dto.setIdAllegatoDocumento(vo.getIdAllegatoDocumento());

		return dto;
	}

	@Override
	public List<AllegatoDocumentazioneVO> mapListEntityToListVO(List<SigasAllegato> en) {
		if (null == en)
			return null;
		List<AllegatoDocumentazioneVO> v = new ArrayList<AllegatoDocumentazioneVO>();
		for (SigasAllegato e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}
	
	@Override
	public List<SigasAllegato> mapListVoToListEntity(List<AllegatoDocumentazioneVO> en) {
		if (null == en)
			return null;
		List<SigasAllegato> v = new ArrayList<SigasAllegato>();
		for (AllegatoDocumentazioneVO e : en) {
			v.add(mapVOtoEntity(e));
		}
		return v;
	}

}
