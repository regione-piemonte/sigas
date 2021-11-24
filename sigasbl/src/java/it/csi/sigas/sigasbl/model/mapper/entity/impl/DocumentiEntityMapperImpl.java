/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasDocumenti;
import it.csi.sigas.sigasbl.model.mapper.entity.AllegatoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.AnagraficaSoggettiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DocumentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.StatoArchiviazioneEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.StatoDocumentoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoDocumentoEntityMapper;
import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;


@Component
public class DocumentiEntityMapperImpl implements DocumentiEntityMapper {

	@Autowired
	private TipoDocumentoEntityMapper tipoDocumentoEntityMapper;
	
	@Autowired
	private AnagraficaSoggettiEntityMapper anagraficaSoggettiEntityMapper;
	
	@Autowired
	private StatoDocumentoEntityMapper statoDocumentoEntityMapper;
	
	@Autowired
	private AllegatoEntityMapper allegatoEntityMapper;
	
	
	@Autowired
	private StatoArchiviazioneEntityMapper statoArchiviazioneEntityMapper;

	
	
	
	@Override
	public DocumentiVO mapEntityToVO(SigasDocumenti dto) { 

		if (dto == null)
			return null;
		DocumentiVO vo = new DocumentiVO();
		vo.setAnagraficaSoggettoVO(anagraficaSoggettiEntityMapper.mapEntityToVO(dto.getSigasAnagraficaSoggetti()));
		vo.setAnnualita(dto.getAnnualita());
		vo.setCfPiva(dto.getCfPiva());
		vo.setNomeFile(dto.getNomeFile());
		vo.setIdDocumento(dto.getIdDocumento());
		vo.setInsDate(dto.getInsDate());
		vo.setInsUser(dto.getInsUser());
		vo.setModDate(dto.getModDate());
		vo.setModUser(dto.getModUser());
		vo.setNote(dto.getNote());
		vo.setNProtocollo(dto.getNProtocollo());
		vo.setRifArchivio(dto.getRifArchivio());
		vo.setNoteBo(dto.getNoteBo());
		vo.setDataProtocollazione(dto.getDataProtocollazione());
		if(dto.getSigasStatoDocumento()!=null) {
			vo.setStatoDocumentoVO(statoDocumentoEntityMapper.mapEntityToVO(dto.getSigasStatoDocumento()));
		}
		
		vo.setTipoDocumentoVO(tipoDocumentoEntityMapper.mapEntityToVO(dto.getSigasTipoDocumento()));
		if(dto.getSigasAllegatos()!=null) {
			vo.setSigasAllegatos(allegatoEntityMapper.mapListEntityToListVO(dto.getSigasAllegatos()));
		}
		vo.setSigasStatoArchiviazioneVO(statoArchiviazioneEntityMapper.mapEntityToVO(dto.getSigasStatoArchiviazione()));
		vo.setIdIndex(dto.getIdIndex());
		vo.setnProtocolloAccertamento(dto.getnProtocolloAccertamento());
		vo.setAnnoProtocolloAccertamento(dto.getAnnoProtocolloAccertamento());
		return vo;
	}

	@Override
	public SigasDocumenti mapVOtoEntity(DocumentiVO vo) {
		if (null == vo)
			return null;
		SigasDocumenti dto = new SigasDocumenti();
		dto.setSigasAnagraficaSoggetti(anagraficaSoggettiEntityMapper.mapVOtoEntity(vo.getAnagraficaSoggettoVO()));
		dto.setAnnualita(vo.getAnnualita());
		dto.setCfPiva(vo.getCfPiva());
		dto.setNomeFile(vo.getNomeFile());
		dto.setIdDocumento(vo.getIdDocumento());
		dto.setInsDate(vo.getInsDate());
		dto.setInsUser(vo.getInsUser());
		dto.setModDate(vo.getModDate());
		dto.setModUser(vo.getModUser());
		dto.setNote(vo.getNote());
		dto.setNProtocollo(vo.getNProtocollo());
		dto.setRifArchivio(vo.getRifArchivio());
		dto.setNoteBo(vo.getNoteBo());
		dto.setDataProtocollazione(vo.getDataProtocollazione());
		if(vo.getStatoDocumentoVO()!=null) {
			dto.setSigasStatoDocumento(statoDocumentoEntityMapper.mapVOtoEntity(vo.getStatoDocumentoVO()));
		}
		
		dto.setSigasTipoDocumento(tipoDocumentoEntityMapper.mapVOtoEntity(vo.getTipoDocumentoVO()));
		dto.setIdIndex(vo.getIdIndex());
		if(vo.getSigasAllegatos()!=null) {
			dto.setSigasAllegatos(allegatoEntityMapper.mapListVoToListEntity(vo.getSigasAllegatos()));
		}
		dto.setSigasStatoArchiviazione(statoArchiviazioneEntityMapper.mapVOtoEntity(vo.getSigasStatoArchiviazioneVO()));

		dto.setnProtocolloAccertamento(vo.getnProtocolloAccertamento());
		dto.setAnnoProtocolloAccertamento(vo.getAnnoProtocolloAccertamento());
			
		return dto;
	}

	@Override
	public List<DocumentiVO> mapListEntityToListVO(List<SigasDocumenti> en) {
		if (null == en)
			return null;
		List<DocumentiVO> v = new ArrayList<DocumentiVO>();
		for (SigasDocumenti e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}
	
	

}
