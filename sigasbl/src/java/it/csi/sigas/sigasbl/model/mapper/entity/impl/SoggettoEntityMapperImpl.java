/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.custom.SoggettoEntityCustom;
import it.csi.sigas.sigasbl.model.mapper.entity.SoggettoEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiVO;
import it.csi.sigas.sigasbl.model.vo.home.SoggettiVO;


@Component
public class SoggettoEntityMapperImpl implements SoggettoEntityMapper {

	
	@Override
	public SoggettiVO mapEntityToVO(SoggettoEntityCustom dto) {

		if (dto == null)
			return null;
		SoggettiVO vo = new SoggettiVO();
		vo.setIdAnag(dto.getIdAnag());
		vo.setCodiceAzienda(dto.getCodiceAzienda());
		vo.setDenominazione(dto.getDenominazione());
		vo.setnProvince(dto.getnProvince());
		vo.setTotCalcolato(dto.getTotCalcolato());
		vo.setTotVersato(dto.getTotVersamenti());
		vo.setValidato(dto.getValidato());
		
		AllarmiVO allarmi = new AllarmiVO();
		if (dto.getAllarmi()[0] > 0)
			allarmi.setScarti(true);
		else 
			allarmi.setScarti(false);
		
		if (dto.getAllarmi()[1] > 0)
			allarmi.setCoerenza(true);
		else 
			allarmi.setCoerenza(false);
		
		if (dto.getAllarmi()[2] > 0)
			allarmi.setDoc(true);
		else 
			allarmi.setDoc(false);
		
		if (dto.getAllarmi()[3] > 0)
			allarmi.setRett(true);
		else 
			allarmi.setRett(false);
		
		if (dto.getAllarmi()[4] > 0)
			allarmi.setAcc(true);
		else 
			allarmi.setAcc(false);
		
		if (dto.getAllarmi()[5] > 0)
			allarmi.setNote(true);
		else 
			allarmi.setNote(false);
		
		if (dto.getAllarmi()[6] > 0)
			allarmi.setRimb(true);
		else 
			allarmi.setRimb(false);
		
		if (dto.getAllarmi()[7] > 0)
			allarmi.setRavv(true);
		else 
			allarmi.setRavv(false);
		
		if (dto.getAllarmi()[8] > 0)
			allarmi.setComp(true);
		else 
			allarmi.setComp(false);
		
		if (dto.getAllarmi()[9] > 0)
			allarmi.setVers(true);
		else 
			allarmi.setVers(false);
		
		vo.setAllarmi(allarmi);
		
		return vo;
	}

	@Override
	public SoggettoEntityCustom mapVOtoEntity(SoggettiVO vo) {
		
		if (null == vo)
			return null;
		
		SoggettoEntityCustom dto = new SoggettoEntityCustom();
		dto.setIdAnag(vo.getIdAnag());
		dto.setCodiceAzienda(vo.getCodiceAzienda());
		dto.setDenominazione(vo.getDenominazione());
		dto.setTotCalcolato(vo.getTotCalcolato());
		dto.setTotVersamenti(vo.getTotVersato());
		dto.setnProvince(vo.getnProvince());
		dto.setValidato(vo.getValidato());

		return dto;
	}

	@Override
	public List<SoggettiVO> mapListEntityToListVO(List<SoggettoEntityCustom> en) {
		if (null == en)
			return null;
		List<SoggettiVO> v = new ArrayList<SoggettiVO>();
		for (SoggettoEntityCustom e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
