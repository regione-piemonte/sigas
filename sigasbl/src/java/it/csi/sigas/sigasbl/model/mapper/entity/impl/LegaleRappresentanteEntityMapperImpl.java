/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.common.utils.Utilities;
import it.csi.sigas.sigasbl.model.entity.SigasComune;
import it.csi.sigas.sigasbl.model.entity.SigasLegaleRappresent;
import it.csi.sigas.sigasbl.model.mapper.entity.LegaleRappresentanteEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasComuneRepository;
import it.csi.sigas.sigasbl.vo.accreditamento.LegaleRappresentanteVO;


@Component
public class LegaleRappresentanteEntityMapperImpl implements LegaleRappresentanteEntityMapper {

//	@Autowired
//	private SigasComuneRepository sigasDComuneRepository;
//
//	@Autowired
//	private SigasDNazioneRepository sigasDNazioneRepository;

	@Override
	public LegaleRappresentanteVO mapEntityToVO(SigasLegaleRappresent dto) {
		if (dto == null)
			return null;
		else {
			LegaleRappresentanteVO vo = new LegaleRappresentanteVO();
			vo.setCodiceFiscale(Utilities.nullOrUppercase(dto.getCfLegaleRappresent()));
			vo.setCognome(dto.getCognomeLegaleRappresent());
			vo.setNome(dto.getNomeLegaleRappresent());
			vo.setTelefono(dto.getTelefonoLegaleRappresent());
			vo.setEmail(dto.getEmailLegaleRappresent());
//			vo.setDataDiNascita(dto.getDataLegaleRappresent());
//			SigasComune sigasComune = dto.getSigasComune();
//			if (sigasComune != null) {
//				vo.setIdComune(sigasComune.getIdComune());
//				vo.setIdProvincia(sigasComune.getSigasProvincia().getIdProvincia());
//			} else {
//				vo.setCittaEstera(dto.getCittaEsteraLegaleRappresent());
//				vo.setIdStatoEstero(dto.getSigasNazione().getIdNazione());
//			}
			return vo;
		}

	}

	@Override
	public SigasLegaleRappresent mapVOtoEntity(LegaleRappresentanteVO vo) {
		if (vo == null)
			return null;
		else {
			SigasLegaleRappresent dto = new SigasLegaleRappresent();
			dto.setNomeLegaleRappresent(Utilities.nullOrUppercase(vo.getNome()));
			dto.setCfLegaleRappresent(StringUtils.trimToEmpty(vo.getCodiceFiscale()).toUpperCase());
//			dto.setCittaEsteraLegaleRappresent(Utilities.nullOrUppercase(vo.getCittaEstera()));
			dto.setCognomeLegaleRappresent(Utilities.nullOrUppercase(vo.getCognome()));
//			dto.setDataLegaleRappresent(vo.getDataDiNascita());
			dto.setTelefonoLegaleRappresent(vo.getTelefono());
			dto.setEmailLegaleRappresent(vo.getEmail());
//			if (vo.getIdComune() != null) {
//				dto.setSigasComune(sigasDComuneRepository.findOne(vo.getIdComune()));
//			} else {
//				dto.setSigasNazione(sigasDNazioneRepository.findOne(vo.getIdStatoEstero()));
//			}
			return dto;
		}

	}

	@Override
	public List<LegaleRappresentanteVO> mapListEntityToListVO(List<SigasLegaleRappresent> en) {
		// TODO Auto-generated method stub
		return null;
	}

}
