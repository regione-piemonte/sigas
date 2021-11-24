/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasPagamenti;
import it.csi.sigas.sigasbl.model.mapper.entity.OrdinativoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.PagamentiVersamentiEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.OrdinativiIncassoVO;


@Component
public class OrdinativoEntityMapperImpl implements OrdinativoEntityMapper {

	@Autowired
	private PagamentiVersamentiEntityMapper pagamentiVersamentiEntityMapper;
	
	@Override
	public OrdinativiIncassoVO mapEntityToVO(SigasPagamenti dto) {

		if (dto == null)
			return null;
		OrdinativiIncassoVO vo = new OrdinativiIncassoVO();
		vo.setIdPagamento(dto.getIdPagamento());
		vo.setAnnoAttoAmministrativo(vo.getAnnoAttoAmministrativo());
		vo.setAnnoOrdInc(dto.getAnnoOrdInc());
		vo.setCfSoggetto(dto.getCfSoggetto());
		vo.setCodCapitolo(dto.getCodCapitolo());
		vo.setCodSoggetto(dto.getCodSoggetto());
		vo.setConciliato(dto.getConciliato());
		vo.setDataElaborazione(dto.getDataElaborazione());
		vo.setDataEmissione(dto.getDataEmissione());
		vo.setDataFirma(dto.getDataFirma());
		vo.setDescCapitolo(dto.getDescCapitolo());
		vo.setDescDistinta(dto.getDescDistinta());
		vo.setDescOrdInc(dto.getDescOrdInc());
		vo.setDescPdcFinanziarioIv(dto.getDescPdcFinanziarioIv());
		vo.setDescPdcFinanziarioV(dto.getDescPdcFinanziarioV());
		vo.setDescSoggetto(dto.getDescSoggetto());
		vo.setDescStatoOrdInc(dto.getDescStatoOrdInc());
		vo.setDescTipoAttoAmministrativo(dto.getDescTipoAttoAmministrativo());
		vo.setDichiarante(dto.getDichiarante());
		vo.setFirma(dto.getFirma());
		vo.setImportoAttuale(dto.getImportoAttuale());
		vo.setImportoIniziale(dto.getImportoIniziale());
		vo.setNoteAttoAmministrativo(dto.getNoteAttoAmministrativo());
		vo.setNumAttoAmministrativo(dto.getNumAttoAmministrativo());
		vo.setNumOrdInc(dto.getNumOrdInc());
		vo.setOggettoAttoAmministrativo(dto.getOggettoAttoAmministrativo());
		vo.setpIvaSoggetto(dto.getPIvaSoggetto());
		vo.setSoggettoId(dto.getSoggettoId());
		vo.setSigasPagamentiVersamentis(pagamentiVersamentiEntityMapper.mapListEntityToListVO(dto.getSigasPagamentiVersamentis()));
		
		return vo;
	}

	@Override
	public SigasPagamenti mapVOtoEntity(OrdinativiIncassoVO vo) {
		
		if (null == vo)
			return null;
		
		SigasPagamenti dto = new SigasPagamenti();
		dto.setIdPagamento(vo.getIdPagamento());
		dto.setAnnoAttoAmministrativo(vo.getAnnoAttoAmministrativo());
		dto.setAnnoOrdInc(vo.getAnnoOrdInc());
		dto.setCfSoggetto(vo.getCfSoggetto());
		dto.setCodCapitolo(vo.getCodCapitolo());
		dto.setCodSoggetto(vo.getCodSoggetto());
		dto.setConciliato(vo.getConciliato());
		dto.setDataElaborazione(vo.getDataElaborazione());
		dto.setDataEmissione(vo.getDataEmissione());
		dto.setDataFirma(vo.getDataFirma());
		dto.setDescCapitolo(vo.getDescCapitolo());
		dto.setDescDistinta(vo.getDescDistinta());
		dto.setDescOrdInc(vo.getDescOrdInc());
		dto.setDescPdcFinanziarioIv(vo.getDescPdcFinanziarioIv());
		dto.setDescPdcFinanziarioV(vo.getDescPdcFinanziarioV());
		dto.setDescSoggetto(vo.getDescSoggetto());
		dto.setDescStatoOrdInc(vo.getDescStatoOrdInc());
		dto.setDescTipoAttoAmministrativo(vo.getDescTipoAttoAmministrativo());
		dto.setDichiarante(vo.getDichiarante());
		dto.setFirma(vo.getFirma());
		dto.setImportoAttuale(vo.getImportoAttuale());
		dto.setImportoIniziale(vo.getImportoIniziale());
		dto.setNoteAttoAmministrativo(vo.getNoteAttoAmministrativo());
		dto.setNumAttoAmministrativo(vo.getNumAttoAmministrativo());
		dto.setNumOrdInc(vo.getNumOrdInc());
		dto.setOggettoAttoAmministrativo(vo.getOggettoAttoAmministrativo());
		dto.setPIvaSoggetto(vo.getpIvaSoggetto());
		dto.setSoggettoId(vo.getSoggettoId());

		return dto;
	}

	@Override
	public List<OrdinativiIncassoVO> mapListEntityToListVO(List<SigasPagamenti> en) {
		if (null == en)
			return null;
		List<OrdinativiIncassoVO> v = new ArrayList<OrdinativiIncassoVO>();
		for (SigasPagamenti e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
