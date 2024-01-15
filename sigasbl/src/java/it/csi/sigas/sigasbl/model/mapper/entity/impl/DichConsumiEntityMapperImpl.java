/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;
import it.csi.sigas.sigasbl.model.mapper.entity.DichConsumiEntityMapper;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.ConsumiPrVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DichConsumiEntityMapperImpl extends SoggettoMapperUtil implements DichConsumiEntityMapper {

    @Override
    public ConsumiPrVO mapEntityToVO(SigasDichConsumi dto) {
        if (dto == null)
            return null;

        ConsumiPrVO consumiPrVO = new ConsumiPrVO();
        consumiPrVO.setAnnualita(dto.getAnnualita());
        consumiPrVO.setId_consumi(dto.getIdConsumi());
        consumiPrVO.setStato_dich(dto.getStatoDich());
        consumiPrVO.setProvincia_erogazione(dto.getProvinciaErogazione());
        consumiPrVO.setData_presentazione(dto.getDataPresentazione());
        consumiPrVO.setUsi_industriali_1200(dto.getUsiIndustriali1200());
        consumiPrVO.setUsi_industriali_up(dto.getUsiIndustrialiUp());
        consumiPrVO.setTot_industriali(dto.getTotIndustriali());
        consumiPrVO.setUsi_civili_120(dto.getUsiCivili120());
        consumiPrVO.setUsi_civili_480(dto.getUsiCivili480());
        consumiPrVO.setUsi_civili_1560(dto.getUsiCivili1560());
        consumiPrVO.setUsi_civili_up(dto.getUsiCiviliUp());
        consumiPrVO.setTot_civili(dto.getTotCivili());
        consumiPrVO.setTot_nuovi_allacciamenti(dto.getTotNuoviAllacciamenti());
        consumiPrVO.setRateo_dich(dto.getRateoDich());
        consumiPrVO.setConguaglio_dich(dto.getConguaglioDich());
        consumiPrVO.setConguaglio_calc(dto.getConguaglioCalcolato());
        consumiPrVO.setAddizionale_liquidata(dto.getAddizionaleLiquidata());
        consumiPrVO.setRettifiche(dto.getRettifiche());
        consumiPrVO.setArrotondamenti(dto.getArrotondamenti());
        consumiPrVO.setTotaleCalcolato(dto.getTotaleCalcolato());
        consumiPrVO.setTotaleDich(dto.getTotaleDich());
        consumiPrVO.setTotaleDichOrigine(dto.getTotaleDichOrigine());
        consumiPrVO.setNote(dto.getNote());
        consumiPrVO.setCompensazione(dto.getCompensazione());
        AnagraficaSoggettoVO anagraficaSoggettoVO = getAnagraficaSoggettoVO(dto.getSigasAnagraficaSoggetti());
        consumiPrVO.setAnagraficaSoggettoVO(anagraficaSoggettoVO);
        return consumiPrVO;
    }

    @Override
    public List<ConsumiPrVO> mapListEntityToListVO(List<SigasDichConsumi> en) {
        if (null == en)
            return null;
        List<ConsumiPrVO> v = new ArrayList<ConsumiPrVO>();
        for (SigasDichConsumi e : en) {
            v.add(mapEntityToVO(e));
        }
        return v;
    }

    @Override
    public SigasDichConsumi mapVOtoEntity(ConsumiPrVO vo) {
        if (vo == null)
            return null;
        SigasDichConsumi dto = new SigasDichConsumi();
        dto.setIdConsumi(vo.getId_consumi());
        dto.setStatoDich(vo.getStato_dich());
        dto.setProvinciaErogazione(vo.getProvincia_erogazione());
        dto.setDataPresentazione(vo.getData_presentazione());
        dto.setUsiIndustriali1200(vo.getUsi_industriali_1200());
        dto.setUsiIndustrialiUp(vo.getUsi_industriali_up());
        dto.setTotIndustriali(vo.getTot_industriali());
        dto.setUsiCivili120(vo.getUsi_civili_120());
        dto.setUsiCivili480(vo.getUsi_civili_480());
        dto.setUsiCivili1560(vo.getUsi_civili_1560());
        dto.setUsiCiviliUp(vo.getUsi_civili_up());
        dto.setTotCivili(vo.getTot_civili());
        dto.setTotNuoviAllacciamenti(vo.getTot_nuovi_allacciamenti());
        dto.setRateoDich(vo.getRateo_dich());
        dto.setConguaglioDich(vo.getConguaglio_dich());
        dto.setConguaglioCalcolato(vo.getConguaglio_calc());
        dto.setAddizionaleLiquidata(vo.getAddizionale_liquidata());
        dto.setRettifiche(vo.getRettifiche());
        dto.setArrotondamenti(vo.getArrotondamenti());
        dto.setTotaleCalcolato(vo.getTotaleCalcolato());
        dto.setTotaleDich(vo.getTotaleDich());
        dto.setTotaleDichOrigine(vo.getTotaleDichOrigine());
        dto.setNote(vo.getNote());
        dto.setCompensazione(vo.getCompensazione());
        dto.setSigasAnagraficaSoggetti(getSigasAnagraficaSoggetti(vo.getAnagraficaSoggettoVO()));
        return dto;

    }

}
