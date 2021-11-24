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
import it.csi.sigas.sigasbl.model.entity.SigasDichiarante;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.mapper.entity.ComuneEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichiaranteAccreditamentoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasComuneRepository;
import it.csi.sigas.sigasbl.model.vo.luoghi.ComuneVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;


@Component
public class DichiaranteAccreditamentoEntityMapperImpl implements DichiaranteAccreditamentoEntityMapper {

	@Autowired
	private SigasComuneRepository sigasComuneRepository;


	@Autowired
	private ComuneEntityMapper comuneEntityMapper;


	@Override 
	public DichiaranteVO mapEntityToVO(SigasDichiarante dto) {
		if (dto == null)
			return null;
		else {
			DichiaranteVO dichiaranteVO = new DichiaranteVO(); 
			dichiaranteVO.setIdDichiarante(dto.getIdDichiarante());
			dichiaranteVO.setDenominazione(StringUtils.trimToEmpty(dto.getDenomDichiarante()));
			dichiaranteVO.setCodiceAzienda(dto.getCodiceAzienda());
			SigasComune comune = dto.getSigasComune();
			ProvinciaVO provincia = new ProvinciaVO();
			if (comune != null) { 
				dichiaranteVO.setComune(comuneEntityMapper.mapEntityToVO(comune));
				SigasProvincia prov = comune.getSigasProvincia();				
				provincia.setDenominazione(prov.getDenomProvincia());
				provincia.setId(prov.getIdProvincia());
				provincia.setSigla(prov.getSiglaProvincia());
				dichiaranteVO.setProvincia(provincia);
//				dichiaranteVO.setRegione(regioneEntityMapper.mapEntityToVO(prov.getSigasRegione()));
			} else {
				dichiaranteVO.setProvincia(provincia);
				dichiaranteVO.setComune(new ComuneVO());
			}
//			else {
//				NazioneVO nazioneVO = new NazioneVO();
//				nazioneVO.setDenominazione(dto.getSigasNazione().getDenomNazione());
//				nazioneVO.setId(dto.getSigasNazione().getIdNazione());
//				dichiaranteVO.setStatoEstero(nazioneVO);
//			}
			dichiaranteVO.setIndirizzo(dto.getIndirizzoSeleLegaleDichiar());
			
			dichiaranteVO.setEmailDichiarante(dto.getEmailDichiarante());
			dichiaranteVO.setIban(dto.getIban());
			dichiaranteVO.setNote(dto.getNote());
			dichiaranteVO.setPecDichiarante(dto.getPecDichiarante());
			dichiaranteVO.setTelefonoDichiarante(dto.getTelefonoDichiarante());

			// flag censito
			dichiaranteVO.setFlagCensito(Boolean.TRUE);
			return dichiaranteVO;

		}
	}

	@Override
	public SigasDichiarante mapVOtoEntity(DichiaranteVO vo) {
		if (vo == null)
			return null;
		else {
			SigasDichiarante dto = new SigasDichiarante();
//			dto.setDenomDichiarante(Utilities.nullOrUppercase(vo.getDenominazione()));
			dto.setDenomDichiarante(vo.getDenominazione());
			if (vo.getComune() != null && vo.getComune().getId() != null) {
				SigasComune comune = sigasComuneRepository.findOne(vo.getComune().getId());
				dto.setSigasComune(comune);				
				dto.setSigasProvincia(comune.getSigasProvincia());
			} 
//			else
//				throw new BadRequestException("comune non valorizzato");

			dto.setIndirizzoSeleLegaleDichiar(Utilities.nullOrUppercase(vo.getIndirizzo()));
			dto.setCodiceAzienda(vo.getCodiceAzienda());
			dto.setEmailDichiarante(vo.getEmailDichiarante());
			dto.setIban(vo.getIban());
			dto.setNote(vo.getNote());
			dto.setPecDichiarante(vo.getPecDichiarante());
			dto.setTelefonoDichiarante(vo.getTelefonoDichiarante());
			return dto;
		}
	}

	@Override
	public List<DichiaranteVO> mapListEntityToListVO(List<SigasDichiarante> en) {
		// TODO Auto-generated method stub
		return null;
	}

}