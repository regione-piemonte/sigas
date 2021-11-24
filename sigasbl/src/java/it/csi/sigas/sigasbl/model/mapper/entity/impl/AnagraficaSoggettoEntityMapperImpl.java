/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasComune;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.mapper.entity.AnagraficaSoggettiEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasComuneRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;


@Component
public class AnagraficaSoggettoEntityMapperImpl implements AnagraficaSoggettiEntityMapper {

	@Autowired
	private SigasProvinciaRepository sigasProvinciaRepository;
	
	@Autowired
	private SigasComuneRepository sigasComuneRepository;
	
	@Override
	public AnagraficaSoggettoVO mapEntityToVO(SigasAnagraficaSoggetti dto) {

		if (dto == null)
			return null;
		AnagraficaSoggettoVO vo = new AnagraficaSoggettoVO();
		vo.setIdAnag(dto.getIdAnag());
		vo.setIdFusione(dto.getIdFusione());
		vo.setDenominazione(dto.getDenominazione());
		vo.setCodiceAzienda(dto.getCodiceAzienda());	
		vo.setIndirizzo(dto.getIndirizzo());
		vo.setIban(dto.getIban());
	    vo.setTipo(dto.getTipo());
	    vo.setStatus(dto.getStatus());
	    vo.setEmail(dto.getEmail());
	    vo.setFideussione(dto.isFideussione());
	    vo.setImportoFideussione(dto.getImportoFideussione());
	    vo.setPec(dto.getPec());
	    vo.setTelefono(dto.getTelefono());
	    vo.setDataFusione(dto.getDataFusione());
	    vo.setNote(dto.getNote());
	    vo.setRiferimento(dto.getRiferimento());
	    SigasProvincia provincia = dto.getSigasProvincia();
		if (provincia != null) {
			vo.setIdProvincia(provincia.getIdProvincia());
		} 
		SigasComune comune = dto.getSigasComune();
		if (comune != null) {
			vo.setIdComune(comune.getIdComune());
		} 
		vo.setVersion(dto.getVersion());
		

		vo.setInsDate(dto.getInsDate());
		vo.setInsUser(dto.getInsUser());
		vo.setModDate(dto.getModDate());
		vo.setModUser(dto.getModUser());
		vo.setCfPiva(dto.getCfPiva());
		return vo;
	}

	@Override
	public SigasAnagraficaSoggetti mapVOtoEntity(AnagraficaSoggettoVO vo) {
		if (null == vo)
			return null;
		SigasAnagraficaSoggetti dto = new SigasAnagraficaSoggetti();
		dto.setIdAnag(vo.getIdAnag().longValue());
		dto.setCodiceAzienda(vo.getCodiceAzienda());
		dto.setDenominazione(vo.getDenominazione());
		dto.setIndirizzo(vo.getIndirizzo());
		dto.setIban(vo.getIban());
		dto.setTipo(vo.getTipo());
		dto.setStatus(vo.getStatus());
		dto.setDataFusione(vo.getDataFusione());
		dto.setEmail(vo.getEmail());
		dto.setFideussione(vo.isFideussione());
		dto.setNote(vo.getNote());
		dto.setIdAnag(vo.getIdAnag());
		dto.setIdFusione(vo.getIdFusione());
		dto.setImportoFideussione(vo.getImportoFideussione());
		dto.setPec(vo.getPec());
		dto.setRiferimento(vo.getRiferimento());
		dto.setTelefono(vo.getTelefono());
		dto.setSigasProvincia(sigasProvinciaRepository.findOne(vo.getIdProvincia()));
		dto.setSigasComune(sigasComuneRepository.findOne(vo.getIdComune()));
		dto.setVersion(vo.getVersion());
		dto.setModDate(vo.getModDate());
		dto.setModUser(vo.getModUser());
		dto.setInsDate(vo.getInsDate());
		dto.setInsUser(vo.getInsUser());
		return dto;
	}

	@Override
	public List<AnagraficaSoggettoVO> mapListEntityToListVO(List<SigasAnagraficaSoggetti> en) {
		if (null == en)
			return null;
		List<AnagraficaSoggettoVO> v = new ArrayList<AnagraficaSoggettoVO>();
		for (SigasAnagraficaSoggetti e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
