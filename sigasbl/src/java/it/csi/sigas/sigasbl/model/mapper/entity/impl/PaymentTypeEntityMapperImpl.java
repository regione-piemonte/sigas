/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasPaymentTypes;
import it.csi.sigas.sigasbl.model.mapper.entity.PaymentTypeEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.PaymentTypesVO;


@Component
public class PaymentTypeEntityMapperImpl implements PaymentTypeEntityMapper {
	
	@Override
	public PaymentTypesVO mapEntityToVO(SigasPaymentTypes dto) {
		if (dto == null)
			return null;
		PaymentTypesVO vo = new PaymentTypesVO();
		vo.setIdTipoPagamento(dto.getIdTipoPagamento());
		vo.setCodTipoPagamento(dto.getCodTipoPagamento());
		vo.setDescTipoPagamento(dto.getDescTipoPagamento());
		return vo;
	}

	@Override
	public SigasPaymentTypes mapVOtoEntity(PaymentTypesVO vo) {
		if (null == vo)
			return null;
		SigasPaymentTypes dto = new SigasPaymentTypes();
		dto.setIdTipoPagamento(vo.getIdTipoPagamento());
		dto.setCodTipoPagamento(vo.getCodTipoPagamento());
		dto.setDescTipoPagamento(vo.getDescTipoPagamento());
		return dto;
	}

	@Override
	public List<PaymentTypesVO> mapListEntityToListVO(List<SigasPaymentTypes> en) {
		if (null == en)
			return null;
		List<PaymentTypesVO> v = new ArrayList<PaymentTypesVO>();
		for (SigasPaymentTypes e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}


}
