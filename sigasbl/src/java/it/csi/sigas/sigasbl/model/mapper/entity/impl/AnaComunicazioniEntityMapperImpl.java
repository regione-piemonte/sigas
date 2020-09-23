/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasAnaComunicazioni;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasComune;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.entity.SigasTipoComunicazioni;
import it.csi.sigas.sigasbl.model.mapper.entity.AnaComunicazioniEntityMapper;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.AnaComunicazioniVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoComunicazioniVO;;


@Component
public class AnaComunicazioniEntityMapperImpl implements AnaComunicazioniEntityMapper {

	@Override
	public AnaComunicazioniVO mapEntityToVO(SigasAnaComunicazioni dto) {
		if (dto == null)
			return null;
		AnaComunicazioniVO vo = new AnaComunicazioniVO();
		vo.setIdComunicazione(dto.getIdComunicazione());
		AnagraficaSoggettoVO anagraficaSoggettoVO = new AnagraficaSoggettoVO();
		anagraficaSoggettoVO.setIdAnag(dto.getSigasAnagraficaSoggetti().getIdAnag());
	    anagraficaSoggettoVO.setCodiceAzienda(dto.getSigasAnagraficaSoggetti().getCodiceAzienda());
	    anagraficaSoggettoVO.setDataFusione(dto.getSigasAnagraficaSoggetti().getDataFusione());
	    anagraficaSoggettoVO.setDenominazione(dto.getSigasAnagraficaSoggetti().getDenominazione());
	    anagraficaSoggettoVO.setEmail(dto.getSigasAnagraficaSoggetti().getEmail());
	    anagraficaSoggettoVO.setIban(dto.getSigasAnagraficaSoggetti().getIban());
	    anagraficaSoggettoVO.setIdAnag(dto.getSigasAnagraficaSoggetti().getIdAnag());
	    anagraficaSoggettoVO.setIdComune(dto.getSigasAnagraficaSoggetti().getSigasComune().getIdComune());
	    anagraficaSoggettoVO.setIdFusione(dto.getSigasAnagraficaSoggetti().getIdFusione());
	    anagraficaSoggettoVO.setIdProvincia(dto.getSigasAnagraficaSoggetti().getSigasProvincia().getIdProvincia());
	    anagraficaSoggettoVO.setImportoFideussione(dto.getSigasAnagraficaSoggetti().getImportoFideussione());
	    anagraficaSoggettoVO.setIndirizzo(dto.getSigasAnagraficaSoggetti().getIndirizzo());
	    anagraficaSoggettoVO.setNote(dto.getSigasAnagraficaSoggetti().getNote());
	    anagraficaSoggettoVO.setPec(dto.getSigasAnagraficaSoggetti().getPec());
	    anagraficaSoggettoVO.setRiferimento(dto.getSigasAnagraficaSoggetti().getRiferimento());
	    anagraficaSoggettoVO.setStatus(dto.getSigasAnagraficaSoggetti().getStatus());
	    anagraficaSoggettoVO.setTelefono(dto.getSigasAnagraficaSoggetti().getTelefono());
	    anagraficaSoggettoVO.setTipo(dto.getSigasAnagraficaSoggetti().getTipo());
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
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = new SigasAnagraficaSoggetti();
		sigasAnagraficaSoggetti.setIdAnag(vo.getAnagraficaSoggettoVO().getIdAnag());
		sigasAnagraficaSoggetti.setDataFusione(vo.getAnagraficaSoggettoVO().getDataFusione());
		sigasAnagraficaSoggetti.setCodiceAzienda(vo.getAnagraficaSoggettoVO().getCodiceAzienda());
		sigasAnagraficaSoggetti.setDenominazione(vo.getAnagraficaSoggettoVO().getDenominazione());
		sigasAnagraficaSoggetti.setIndirizzo(vo.getAnagraficaSoggettoVO().getIndirizzo());
		sigasAnagraficaSoggetti.setIban(vo.getAnagraficaSoggettoVO().getIban());
		sigasAnagraficaSoggetti.setEmail(vo.getAnagraficaSoggettoVO().getEmail());
		sigasAnagraficaSoggetti.setImportoFideussione(vo.getAnagraficaSoggettoVO().getImportoFideussione());
		sigasAnagraficaSoggetti.setNote(vo.getAnagraficaSoggettoVO().getNote());
		sigasAnagraficaSoggetti.setPec(vo.getAnagraficaSoggettoVO().getPec());
		sigasAnagraficaSoggetti.setRiferimento(vo.getAnagraficaSoggettoVO().getRiferimento());
		sigasAnagraficaSoggetti.setStatus(vo.getAnagraficaSoggettoVO().getStatus());
		sigasAnagraficaSoggetti.setTelefono(vo.getAnagraficaSoggettoVO().getTelefono());
		sigasAnagraficaSoggetti.setTipo(vo.getAnagraficaSoggettoVO().getTipo());
		SigasProvincia sigasProvincia = new SigasProvincia();
		sigasProvincia.setIdProvincia(vo.getAnagraficaSoggettoVO().getIdProvincia());
		sigasAnagraficaSoggetti.setSigasProvincia(sigasProvincia);
		SigasComune sigasComune = new SigasComune();
		sigasComune.setIdComune(vo.getAnagraficaSoggettoVO().getIdComune());
		sigasComune.setSigasProvincia(sigasProvincia);
		sigasAnagraficaSoggetti.setSigasComune(sigasComune);
		dto.setSigasAnagraficaSoggetti(sigasAnagraficaSoggetti);
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
