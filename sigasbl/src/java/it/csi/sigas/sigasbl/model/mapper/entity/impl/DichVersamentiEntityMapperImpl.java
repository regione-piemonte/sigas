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
import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;
import it.csi.sigas.sigasbl.model.entity.SigasDichVersamenti;
import it.csi.sigas.sigasbl.model.entity.SigasPagamentiVersamenti;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.entity.SigasTipoVersamento;
//import it.csi.sigas.sigasbl.model.entity.custom.PaymentSubjectFODetailEntityCustom;
import it.csi.sigas.sigasbl.model.mapper.entity.DichConsumiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichVersamentiEntityMapper;
//import it.csi.sigas.sigasbl.model.mapper.entity.PagamentiVersamentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoVersamentoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichConsumiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentCartRepository;
//import it.csi.sigas.sigasbl.model.repositories.SigasPaymentSubjectFORepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoVersamentoRepository;
//import it.csi.sigas.sigasbl.model.vo.home.PagamentiVersamentiVO;
import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;

@Component
public class DichVersamentiEntityMapperImpl implements DichVersamentiEntityMapper {
	
	@Autowired
	private TipoVersamentoEntityMapper tipoVersamentoEntityMapper;
	
	@Autowired
	private DichConsumiEntityMapper dichConsumiEntityMapper;
	
	@Autowired
	private SigasProvinciaRepository provinciaRepository;
	
	@Autowired
	private	SigasTipoVersamentoRepository tipoVersamentoRepository;
	
	@Autowired
	private	SigasDichConsumiRepository dichConsumiRepository;
	
	@Autowired
	private SigasAnagraficaSoggettiRepository anagraficaSoggettiRepository;
	
	@Autowired
	private SigasPaymentCartRepository sigasPaymentCartRepository;	
	
	@Override
	public VersamentiPrVO mapEntityToVO(SigasDichVersamenti dto) {
		if (dto == null)
			return null;

		VersamentiPrVO versamentiPrVO = new VersamentiPrVO();
		versamentiPrVO.setAnnualita(Integer.valueOf(dto.getAnnualita()));
		versamentiPrVO.setDataVersamento(dto.getDataVersamento());
		versamentiPrVO.setIdVersamento(dto.getIdVersamento());
		versamentiPrVO.setImporto(dto.getImporto());
		versamentiPrVO.setMese(dto.getMese());
		versamentiPrVO.setNote(dto.getNote());
		versamentiPrVO.setIdVersamento(dto.getIdVersamento());
		versamentiPrVO.setDataAccertamento(dto.getDataAccertamento());
		versamentiPrVO.setImportoComplessivo(dto.getImportoComplessivo());
		SigasProvincia provincia = dto.getSigasProvincia();
		if (provincia != null) {
			versamentiPrVO.setProvincia(provincia.getSiglaProvincia());
		} 
		SigasTipoVersamento tipo = dto.getSigasTipoVersamento();
		if (tipo!= null) {
			versamentiPrVO.setTipo(tipoVersamentoEntityMapper.mapEntityToVO(tipo));
		}
		SigasDichConsumi consumi = dto.getSigasDichConsumi();
		if (consumi!= null) {
			versamentiPrVO.setConsumo(dichConsumiEntityMapper.mapEntityToVO(consumi));
		}
		SigasAnagraficaSoggetti anagSogg = dto.getSigasAnagraficaSoggetti();
		if(anagSogg!=null)
			versamentiPrVO.setIdAnag(anagSogg.getIdAnag());
		
		versamentiPrVO.setInsDate(dto.getInsDate());
		versamentiPrVO.setInsUser(dto.getInsUser());
		versamentiPrVO.setModDate(dto.getModDate());
		versamentiPrVO.setModUser(dto.getModUser());
		
		/*
		List<SigasPagamentiVersamenti> pagamentiVersamenti = dto.getSigasPagamentiVersamenti();
		if(pagamentiVersamenti != null && pagamentiVersamenti.size() > 0) {
			versamentiPrVO.setHasPagamentiVersamenti(true);			
			List<Long> idsPagamentiVersametni = new ArrayList<Long>();			
			for(SigasPagamentiVersamenti pagamentoVersamento: pagamentiVersamenti) {
				idsPagamentiVersametni.add(pagamentoVersamento.getIdPagamentoVersamento());
			}			
			versamentiPrVO.setPagamentiVersamenti(idsPagamentiVersametni);
			
		} else {			
			versamentiPrVO.setHasPagamentiVersamenti(false);
			versamentiPrVO.setPagamentiVersamenti(null);
		}
		*/
		
		List<SigasPaymentCart> pagamentiList = sigasPaymentCartRepository.getPaymentByIdVersamento(versamentiPrVO.getIdVersamento());
		if(pagamentiList != null && !pagamentiList.isEmpty()) {
			versamentiPrVO.setHasPagamentiVersamenti(true);			
			List<Long> idsPagamentiVersametni = new ArrayList<Long>();			
			for(SigasPaymentCart sigasPaymentCart: pagamentiList) {
				idsPagamentiVersametni.add(sigasPaymentCart.getIdCarrello());
			}			
			versamentiPrVO.setPagamentiVersamenti(idsPagamentiVersametni);
			
		} else {			
			versamentiPrVO.setHasPagamentiVersamenti(false);
			versamentiPrVO.setPagamentiVersamenti(null);
		}
		
		
		return versamentiPrVO;
	}

	@Override
	public List<VersamentiPrVO> mapListEntityToListVO(List<SigasDichVersamenti> en) {
		if (null == en)
			return null;
		List<VersamentiPrVO> v = new ArrayList<VersamentiPrVO>();
		for (SigasDichVersamenti e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

	@Override
	public SigasDichVersamenti mapVOtoEntity(VersamentiPrVO vo) {
		if (null == vo)
			return null;
		SigasDichVersamenti dto = new SigasDichVersamenti();
		SigasDichConsumi cons = null;
		SigasAnagraficaSoggetti anagSogg = null;
		
		dto.setIdVersamento(vo.getIdVersamento());
		if(vo.getConsumo()!=null)
			 cons = dichConsumiRepository.findOne(vo.getConsumo().getId_consumi());
		else if(vo.getIdConsumi()!= 0) 
			 cons = dichConsumiRepository.findOne(vo.getIdConsumi());
			
		if(cons!=null) {
			dto.setSigasDichConsumi(cons);
//			dto.setSigasProvincia(provinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(cons.getProvinciaErogazione()));
		}
		dto.setSigasProvincia(provinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(vo.getProvincia()));
		
		if (vo.getIdAnag() != 0) {
			anagSogg = anagraficaSoggettiRepository.findOne(vo.getIdAnag());
			if (anagSogg != null) {
				dto.setSigasAnagraficaSoggetti(anagSogg);
				dto.setIbanVers(anagSogg.getIban());
			}
		}
		
		dto.setAnnualita(String.valueOf(vo.getAnnualita()));
		dto.setMese(vo.getMese());
		dto.setSigasTipoVersamento(tipoVersamentoRepository.findOne(vo.getTipo().getIdTipoVersamento()));
		dto.setDataVersamento(vo.getDataVersamento());
		dto.setImporto(vo.getImporto());
		dto.setNote(vo.getNote());
		dto.setDataAccertamento(vo.getDataAccertamento());
		dto.setImportoComplessivo(vo.getImportoComplessivo());
		dto.setModDate(vo.getModDate());
		dto.setModUser(vo.getModUser());
		dto.setInsDate(vo.getInsDate());
		dto.setInsUser(vo.getInsUser());
//		dto.setEliminato(vo.isEliminato());
		
		return dto;
	}
}
