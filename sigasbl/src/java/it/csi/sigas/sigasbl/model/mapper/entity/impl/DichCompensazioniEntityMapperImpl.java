package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasDichCompensazioni;
import it.csi.sigas.sigasbl.model.mapper.entity.DichCompensazioniEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.CompensazionePrVO;

@Component
public class DichCompensazioniEntityMapperImpl implements DichCompensazioniEntityMapper {
	
	private CompensazionePrVO _mapEntityToVO(SigasDichCompensazioni dto) {
		if(dto == null) {
			return null;
		}
		
		CompensazionePrVO compensazionePrVO = new CompensazionePrVO();
		
		compensazionePrVO.setId_compensazione(dto.getId_compensazione());
		compensazionePrVO.setId_consumi(dto.getId_consumi());
		compensazionePrVO.setConguaglio_compensato(dto.getConguaglio_compensato());
		compensazionePrVO.setConguaglio_di_riferimento(dto.getConguaglio_di_riferimento());
		compensazionePrVO.setCompensazione(dto.getCompensazione());
		compensazionePrVO.setData_compensazione(dto.getData_compensazione());
		
		return compensazionePrVO;
		
	}
	
	private SigasDichCompensazioni _mapVOToEntity(CompensazionePrVO vo) {
		if(vo == null) {
			return null;
		}
		
		SigasDichCompensazioni sigasDichCompensazioni = new SigasDichCompensazioni();
		
		sigasDichCompensazioni.setId_compensazione(vo.getId_compensazione());
		sigasDichCompensazioni.setId_consumi(vo.getId_consumi());
		sigasDichCompensazioni.setConguaglio_compensato(vo.getConguaglio_compensato());
		sigasDichCompensazioni.setConguaglio_di_riferimento(vo.getConguaglio_di_riferimento());
		sigasDichCompensazioni.setCompensazione(vo.getCompensazione());
		sigasDichCompensazioni.setData_compensazione(vo.getData_compensazione());
		
		return sigasDichCompensazioni;
		
	}

	@Override
	public CompensazionePrVO mapEntityToVO(SigasDichCompensazioni dto) {		
		return this._mapEntityToVO(dto);
	}

	@Override
	public SigasDichCompensazioni mapVOtoEntity(CompensazionePrVO vo) {		
		return this._mapVOToEntity(vo);
	}

	@Override
	public List<CompensazionePrVO> mapListEntityToListVO(List<SigasDichCompensazioni> en) {
		
		//TO DO
		
		return null;
		
	}

}
