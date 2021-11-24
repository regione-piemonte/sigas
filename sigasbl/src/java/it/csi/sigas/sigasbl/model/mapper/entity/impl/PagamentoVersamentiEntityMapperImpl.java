/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasPagamentiVersamenti;
import it.csi.sigas.sigasbl.model.mapper.entity.AnagraficaSoggettiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichVersamentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.OrdinativoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.PagamentiVersamentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.ProvinciaEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoVersamentoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasPagamentiCrudRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPagamentiRepository;
import it.csi.sigas.sigasbl.model.vo.home.PagamentiVersamentiVO;


@Component
public class PagamentoVersamentiEntityMapperImpl implements PagamentiVersamentiEntityMapper {

	@Autowired
	private OrdinativoEntityMapper ordinativoEntityMapper;
	 
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	
	@Autowired
	private TipoVersamentoEntityMapper tipoVersamentoEntityMapper;
	
	@Autowired
	private AnagraficaSoggettiEntityMapper anagraficaSoggettiEntityMapper;
	
	@Autowired
	private SigasPagamentiCrudRepository sigasPagamentiCrudRepository;
	
	@Autowired
	private DichVersamentiEntityMapper dichVersamentiEntityMapper;
	
	@Override
	public PagamentiVersamentiVO mapEntityToVO(SigasPagamentiVersamenti dto) {
 
		if (dto == null)
			return null;
		PagamentiVersamentiVO vo = new PagamentiVersamentiVO();
		vo.setIdPagamentoVersamento(dto.getIdPagamentoVersamento());
		vo.setAnno(dto.getAnno());
		vo.setDataVersamento(dto.getDataVersamento());
		vo.setFkProvincia(provinciaEntityMapper.mapEntityToVO(dto.getSigasProvincia()) );
//		vo.setFkPagamento(ordinativoEntityMapper.mapEntityToVO(dto.getSigasPagamenti()) );
		vo.setFkPagamento(dto.getSigasPagamenti().getIdPagamento() );
		vo.setFkAnag(anagraficaSoggettiEntityMapper.mapEntityToVO(dto.getSigasAnagraficaSoggetti()) );
//		vo.setFkVersamento(dto.getFkVersamento());
		vo.setFkVersamento(dichVersamentiEntityMapper.mapEntityToVO(dto.getSigasDichVersamenti()) ); 
		vo.setIdTipoVersamento(tipoVersamentoEntityMapper.mapEntityToVO(dto.getSigasTipoVersamento()) );
		vo.setImporto(dto.getImporto());
		vo.setMese(dto.getMese());
		
		return vo; 
	}

	@Override
	public SigasPagamentiVersamenti mapVOtoEntity(PagamentiVersamentiVO vo) {
		 
		if (null == vo)
			return null;
		
		SigasPagamentiVersamenti dto = new SigasPagamentiVersamenti();
		dto.setIdPagamentoVersamento(vo.getIdPagamentoVersamento());
		dto.setAnno(vo.getAnno()); 
		dto.setDataVersamento(vo.getDataVersamento());
		dto.setSigasProvincia(provinciaEntityMapper.mapVOtoEntity(vo.getFkProvincia()) );
		dto.setSigasPagamenti(sigasPagamentiCrudRepository.findOne(vo.getFkPagamento()) );
		dto.setSigasAnagraficaSoggetti(anagraficaSoggettiEntityMapper.mapVOtoEntity(vo.getFkAnag()) );
//		dto.setFkVersamento(vo.getFkVersamento());
		dto.setSigasDichVersamenti(dichVersamentiEntityMapper.mapVOtoEntity(vo.getFkVersamento()));
		dto.setSigasTipoVersamento(tipoVersamentoEntityMapper.mapVOtoEntity(vo.getIdTipoVersamento()) );
		dto.setImporto(vo.getImporto());
		dto.setMese(vo.getMese());


		return dto;
	}

	@Override
	public List<PagamentiVersamentiVO> mapListEntityToListVO(List<SigasPagamentiVersamenti> en) {
		if (null == en)
			return null;
		List<PagamentiVersamentiVO> v = new ArrayList<PagamentiVersamentiVO>();
		for (SigasPagamentiVersamenti e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
