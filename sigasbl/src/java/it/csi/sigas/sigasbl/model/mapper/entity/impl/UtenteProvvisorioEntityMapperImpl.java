/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasUtenteProvvisorio;
import it.csi.sigas.sigasbl.model.mapper.entity.DichiaranteAccreditamentoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.LegaleRappresentanteEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.OperatoreEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.UtenteProvvisorioEntityMapper;
import it.csi.sigas.sigasbl.vo.accreditamento.UtenteProvvisorioVO;



@Component
public class UtenteProvvisorioEntityMapperImpl implements UtenteProvvisorioEntityMapper{
	
	@Autowired
	public  LegaleRappresentanteEntityMapper  legaleRappresentateEntityMapper;

	@Autowired
	public  OperatoreEntityMapper  operatoreEntityMapper;
	
	@Autowired
	public DichiaranteAccreditamentoEntityMapper dichiaranteAccreditamentoEntityMapper;
	
	@Override
	public UtenteProvvisorioVO mapEntityToVO(SigasUtenteProvvisorio dto) {
		if (dto == null)
			return null;

		UtenteProvvisorioVO utenteProvvisorioVO = new UtenteProvvisorioVO();
		 
		utenteProvvisorioVO.setLegaleRappresentanteVO(legaleRappresentateEntityMapper.mapEntityToVO(dto.getSigasLegaleRappresent()));		
		utenteProvvisorioVO.setOperatoreVO(operatoreEntityMapper.mapEntityToVO(dto.getSigasOperatore()));		
		utenteProvvisorioVO.setDichiaranteVO(dichiaranteAccreditamentoEntityMapper.mapEntityToVO(dto.getSigasDichiarante()));		
		utenteProvvisorioVO.setIdUtenteProvv(dto.getIdUtenteProvv());
		utenteProvvisorioVO.setNote(dto.getNote());
		utenteProvvisorioVO.setIdPratica(dto.getIdPratica());
		utenteProvvisorioVO.setStato(dto.getStato());
		utenteProvvisorioVO.setInsDate(dto.getInsDate());
		utenteProvvisorioVO.setInsUser(dto.getInsUser());
		utenteProvvisorioVO.setModDate(dto.getModDate());

		utenteProvvisorioVO.setModUser(dto.getModUser());
		return utenteProvvisorioVO;
	}

	@Override
	public SigasUtenteProvvisorio mapVOtoEntity(UtenteProvvisorioVO vo) {
		SigasUtenteProvvisorio sigasUtenteProvvisorio = new SigasUtenteProvvisorio();
		sigasUtenteProvvisorio.setSigasLegaleRappresent(legaleRappresentateEntityMapper.mapVOtoEntity(vo.getLegaleRappresentanteVO()));		
		sigasUtenteProvvisorio.setSigasOperatore(operatoreEntityMapper.mapVOtoEntity(vo.getOperatoreVO()));
		sigasUtenteProvvisorio.setSigasDichiarante(dichiaranteAccreditamentoEntityMapper.mapVOtoEntity(vo.getDichiaranteVO()));
		sigasUtenteProvvisorio.setIdUtenteProvv(vo.getIdUtenteProvv());
		sigasUtenteProvvisorio.setNote(vo.getNote());
		sigasUtenteProvvisorio.setIdPratica(vo.getIdPratica());
		sigasUtenteProvvisorio.setStato(vo.getStato());
		sigasUtenteProvvisorio.setInsDate(vo.getInsDate());
		sigasUtenteProvvisorio.setInsUser(vo.getInsUser());
		sigasUtenteProvvisorio.setModDate(vo.getModDate());
		sigasUtenteProvvisorio.setModUser(vo.getModUser());
		return sigasUtenteProvvisorio;
	}

	@Override
	public List<UtenteProvvisorioVO> mapListEntityToListVO(List<SigasUtenteProvvisorio> en) {
		if (null == en)
			return null;
		List<UtenteProvvisorioVO> v = new ArrayList<UtenteProvvisorioVO>();
		for (SigasUtenteProvvisorio e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
