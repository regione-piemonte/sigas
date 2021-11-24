/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.common.utils.Utilities;
import it.csi.sigas.sigasbl.model.entity.SigasComune;
import it.csi.sigas.sigasbl.model.entity.SigasOperatore;
import it.csi.sigas.sigasbl.model.mapper.entity.OperatoreEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasComuneRepository;
import it.csi.sigas.sigasbl.vo.accreditamento.OperatoreVO;


@Component
public class OperatoreEntityMapperImpl implements OperatoreEntityMapper {

	@Autowired
	private SigasComuneRepository sigasDComuneRepository;


	@Override
	public OperatoreVO mapEntityToVO(SigasOperatore dto) {
		if (dto == null)
			return null;
		else {
			OperatoreVO vo = new OperatoreVO();
			vo.setCognome(dto.getCognomeOperatore());
			vo.setNome(dto.getNomeOperatore());
			vo.setCodiceFiscale(dto.getCfOperatore());
			vo.setTelefono(dto.getTelefonoOperatore());
			vo.setEmail(dto.getEmailOperatore());
//			vo.setDataDiNascita(dto.getDataNascita());
//			SigasComune irbaDComune = dto.getSigasComune();
//			if (irbaDComune != null) {
//				vo.setIdComune(irbaDComune.getIdComune());
//				vo.setIdProvincia(irbaDComune.getSigasProvincia().getIdProvincia());
//			} else {
//				vo.setCittaEstera(dto.getCittaEsteraNascita());
//				vo.setIdStatoEstero(dto.getSigasNazione().getIdNazione());
//			}

			return vo;
		}

	}

	@Override
	public SigasOperatore mapVOtoEntity(OperatoreVO vo) {
		if (vo == null)
			return null;
		else {
			SigasOperatore dto = new SigasOperatore();

			dto.setNomeOperatore(Utilities.nullOrUppercase(vo.getNome()));
			dto.setTelefonoOperatore(vo.getTelefono());
			dto.setCfOperatore(Utilities.nullOrUppercase(vo.getCodiceFiscale()));
//			dto.setCittaEsteraNascita(Utilities.nullOrUppercase(vo.getCittaEstera()));
			dto.setCognomeOperatore(Utilities.nullOrUppercase(vo.getCognome()));
			dto.setEmailOperatore(vo.getEmail());
//			dto.setDataNascita(vo.getDataDiNascita());
//			if (vo.getIdComune() != null) {
//				dto.setSigasComune(sigasDComuneRepository.findOne(vo.getIdComune()));
//			} else {
//				dto.setSigasNazione(sigasDNazioneRepository.findOne(vo.getIdStatoEstero()));
//			}
			return dto;
		}
	}

	@Override
	public List<OperatoreVO> mapListEntityToListVO(List<SigasOperatore> en) {
		// TODO Auto-generated method stub
		return null;
	}
}
