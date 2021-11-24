/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFODetailEntityCustom;
import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFOEntityCustomBase;
import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFOEntityGroupedCustom;
import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFOEntitySingleCustom;
import it.csi.sigas.sigasbl.model.mapper.entity.PaymentSubjectFOEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.PaymentSubjectVO;


@Component
public class PaymentSubjectFOEntityMapperImpl implements PaymentSubjectFOEntityMapper<PaymentSubjectFOEntityCustomBase, PaymentSubjectVO> {

	
	@Override
	public PaymentSubjectVO mapEntityToVO(PaymentSubjectFOEntityCustomBase dto) {

		if (dto == null)
			return null;
		PaymentSubjectVO vo = new PaymentSubjectVO();
		
		vo.setId(dto.getId());
		
		vo.setIdAnag(dto.getIdAnag());
		vo.setCodiceAzienda(dto.getCodiceAzienda());
		vo.setNomeAzienda(dto.getNomeAzienda());
		vo.setDenominazione(dto.getDenominazione());
		
		if(dto instanceof PaymentSubjectFOEntitySingleCustom) {
			vo.setComune(((PaymentSubjectFOEntitySingleCustom)dto).getComune());
			vo.setCap(((PaymentSubjectFOEntitySingleCustom)dto).getCap());
			vo.setIndirizzo(((PaymentSubjectFOEntitySingleCustom)dto).getIndirizzo());
			vo.setSiglaProvinciaAzienda(((PaymentSubjectFOEntitySingleCustom)dto).getSiglaProvinciaAzienda());
			vo.setSiglaProvincia(((PaymentSubjectFOEntitySingleCustom)dto).getSiglaProvincia());
			vo.setCodiceFiscale(((PaymentSubjectFOEntitySingleCustom)dto).getCodiceFiscale());
			vo.setCodiceFiscalePIva(((PaymentSubjectFOEntitySingleCustom)dto).getCodiceFiscalePIva()); 
		}
		
		if(dto instanceof PaymentSubjectFODetailEntityCustom) {
			vo.setMesi(((PaymentSubjectFODetailEntityCustom)dto).getAddizionaleLiquidata());
			vo.setComune(((PaymentSubjectFODetailEntityCustom)dto).getComune());
			vo.setCap(((PaymentSubjectFODetailEntityCustom)dto).getCap());
			vo.setIndirizzo(((PaymentSubjectFODetailEntityCustom)dto).getIndirizzo());
			vo.setSiglaProvinciaAzienda(((PaymentSubjectFODetailEntityCustom)dto).getSiglaProvinciaAzienda());
		}
		
		if(dto instanceof PaymentSubjectFOEntityGroupedCustom) {
			vo.setMesi(((PaymentSubjectFOEntityGroupedCustom)dto).getMesi());
			vo.setTotale(((PaymentSubjectFOEntityGroupedCustom)dto).getTotale());
			vo.setProvincie(((PaymentSubjectFOEntityGroupedCustom)dto).getProvincie());
		}
		
		return vo;
	}

	@Override
	public PaymentSubjectFOEntityCustomBase mapVOtoEntity(PaymentSubjectVO vo) {
		
		if (null == vo)
			return null;
		
		PaymentSubjectFOEntityCustomBase dto = new PaymentSubjectFOEntityCustomBase();
		dto.setId(vo.getId());
		
		dto.setIdAnag(vo.getIdAnag());
		dto.setCodiceAzienda(vo.getCodiceAzienda());
		dto.setNomeAzienda(vo.getNomeAzienda());
		dto.setDenominazione(vo.getDenominazione());

		/*
		dto.setComune(vo.getComune());
		dto.setCap(vo.getCap());
		dto.setIndirizzo(vo.getIndirizzo());
		
		dto.setMesi(vo.getMesi());
		dto.setTotale(vo.getTotale());
		dto.setProvincie(vo.getProvincie());
		*/
		
		return dto;
	}

	@Override
	public List<PaymentSubjectVO> mapListEntityToListVO(List<? extends PaymentSubjectFOEntityCustomBase> en) {
		if (null == en)
			return null;
		List<PaymentSubjectVO> v = new ArrayList<PaymentSubjectVO>();
		for (PaymentSubjectFOEntityCustomBase e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
