/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import it.csi.sigas.sigasbl.model.entity.SigasAnaComunicazioni;
import it.csi.sigas.sigasbl.model.entity.SigasTipoComunicazioni;
import it.csi.sigas.sigasbl.model.mapper.entity.AnaComunicazioniEntityMapper;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.AnaComunicazioniVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoComunicazioniVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

;


@Component
public class AnaComunicazioniEntityMapperImpl extends SoggettoMapperUtil implements AnaComunicazioniEntityMapper {

    @Override
    public AnaComunicazioniVO mapEntityToVO(SigasAnaComunicazioni dto) {
        if (dto == null)
            return null;
        AnaComunicazioniVO vo = new AnaComunicazioniVO();
        vo.setIdComunicazione(dto.getIdComunicazione());
        AnagraficaSoggettoVO anagraficaSoggettoVO = getAnagraficaSoggettoVO(dto.getSigasAnagraficaSoggetti());
        anagraficaSoggettoVO.setIdAnag(dto.getSigasAnagraficaSoggetti().getIdAnag());
        vo.setAnagraficaSoggettoVO(anagraficaSoggettoVO);
        vo.setAnnualita(dto.getAnnualita());
        TipoComunicazioniVO tipoComunicazioniVO = new TipoComunicazioniVO();
        tipoComunicazioniVO.setIdTipoComunicazione(dto.getSigasTipoComunicazione().getIdTipoComunicazione());
        tipoComunicazioniVO.setDenominazione(dto.getSigasTipoComunicazione().getDenominazione());
        tipoComunicazioniVO.setDescrizione(dto.getSigasTipoComunicazione().getDescrizione());
        vo.setTipoComunicazioneVO(tipoComunicazioniVO);
        vo.setDataDocumento(dto.getDataDocumento());
        vo.setDescrizione(dto.getDescrizione());
        vo.setNote(dto.getNote());
        vo.setDatiRiassuntivi(dto.getDatiRiassuntivi());
        vo.setNProtocollo(dto.getNProtocollo());
        vo.setRifArchivio(dto.getRifArchivio());
        vo.setInsDate(dto.getInsDate());
        vo.setInsUser(dto.getInsUser());
        vo.setModDate(dto.getModDate());
        vo.setModUser(dto.getModUser());
        return vo;
    }

    @Override
    public SigasAnaComunicazioni mapVOtoEntity(AnaComunicazioniVO vo) {
        if (vo == null)
            return null;
        SigasAnaComunicazioni dto = new SigasAnaComunicazioni();
        dto.setIdComunicazione(vo.getIdComunicazione());
        dto.setSigasAnagraficaSoggetti(getSigasAnagraficaSoggetti(vo.getAnagraficaSoggettoVO()));
        SigasTipoComunicazioni sigasTipoComunicazioni = new SigasTipoComunicazioni();
        sigasTipoComunicazioni.setIdTipoComunicazione(vo.getTipoComunicazioneVO().getIdTipoComunicazione());
        sigasTipoComunicazioni.setDenominazione(vo.getTipoComunicazioneVO().getDenominazione());
        sigasTipoComunicazioni.setDescrizione(vo.getTipoComunicazioneVO().getDescrizione());
        dto.setSigasTipoComunicazioni(sigasTipoComunicazioni);
        dto.setDescrizione(vo.getDescrizione());
        dto.setDataDocumento(vo.getDataDocumento());
        dto.setAnnualita(vo.getAnnualita());
        dto.setNote(vo.getNote());
        dto.setDatiRiassuntivi(vo.getDatiRiassuntivi());
        dto.setNProtocollo(vo.getNProtocollo());
        dto.setRifArchivio(vo.getRifArchivio());
        dto.setModDate(vo.getModDate());
        dto.setModUser(vo.getModUser());
        dto.setInsDate(vo.getInsDate());
        dto.setInsUser(vo.getInsUser());
        return dto;
    }

    @Override
    public List<AnaComunicazioniVO> mapListEntityToListVO(List<SigasAnaComunicazioni> en) {
        if (null == en)
            return null;
        List<AnaComunicazioniVO> v = new ArrayList<AnaComunicazioniVO>();
        for (SigasAnaComunicazioni e : en) {
            v.add(mapEntityToVO(e));
        }
        return v;
    }

}
