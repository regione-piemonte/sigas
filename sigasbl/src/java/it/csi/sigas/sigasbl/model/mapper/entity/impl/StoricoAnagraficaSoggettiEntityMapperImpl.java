package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasStoricoAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.mapper.entity.StoricoAnagraficaSoggettiEntityMapper;
import it.csi.sigas.sigasbl.model.vo.StoricoAnagraficaSoggettoVO;

@Component
public class StoricoAnagraficaSoggettiEntityMapperImpl implements StoricoAnagraficaSoggettiEntityMapper {

	@Override
	public StoricoAnagraficaSoggettoVO mapEntityToVO(SigasStoricoAnagraficaSoggetti dto) {				
		return mappingFromEntityToVO(dto);
	}

	@Override
	public SigasStoricoAnagraficaSoggetti mapVOtoEntity(StoricoAnagraficaSoggettoVO vo) {
		if(vo == null) {
			return null;
		}
		
		SigasStoricoAnagraficaSoggetti sigasStoricoAnagraficaSoggetti = new SigasStoricoAnagraficaSoggetti();
		
		sigasStoricoAnagraficaSoggetti.setCodiceAzienda(vo.getCodiceAzienda());
		sigasStoricoAnagraficaSoggetti.setDataRiferimento(vo.getDataRiferimento());
		sigasStoricoAnagraficaSoggetti.setDenominazione(vo.getDenominazione());
		sigasStoricoAnagraficaSoggetti.setEmail(vo.getEmail());
		sigasStoricoAnagraficaSoggetti.setIban(vo.getIban());
		sigasStoricoAnagraficaSoggetti.setIdAnag(vo.getIdAnag());		
		sigasStoricoAnagraficaSoggetti.setIdStoricoAnag(vo.getIdStoricoAnag());
		sigasStoricoAnagraficaSoggetti.setIndirizzo(vo.getIndirizzo());
		sigasStoricoAnagraficaSoggetti.setPec(vo.getPec());		
		sigasStoricoAnagraficaSoggetti.setAnnualita(vo.getAnnualita());
		sigasStoricoAnagraficaSoggetti.setCfPiva(vo.getCfPiva());
		sigasStoricoAnagraficaSoggetti.setIdImport(vo.getIdImport());
		sigasStoricoAnagraficaSoggetti.setOwnerOperazione(vo.getOwnerOperazione());
		
		return sigasStoricoAnagraficaSoggetti;
	}

	@Override
	public List<StoricoAnagraficaSoggettoVO> mapListEntityToListVO(List<SigasStoricoAnagraficaSoggetti> en) {
		if(en == null || en.isEmpty()) {
			return null;
		}
		List<StoricoAnagraficaSoggettoVO> output = new ArrayList<>();
		Iterator<SigasStoricoAnagraficaSoggetti> iterator = en.iterator();
		while(iterator.hasNext()) {
			StoricoAnagraficaSoggettoVO storicoAnagraficaSoggettoVO = this.mappingFromEntityToVO(iterator.next());
			output.add(storicoAnagraficaSoggettoVO);
		}		
		return output;
	}
	
	private StoricoAnagraficaSoggettoVO mappingFromEntityToVO(SigasStoricoAnagraficaSoggetti dto) {
		if(dto == null) {
			return null;
		}
		
		StoricoAnagraficaSoggettoVO storicoAnagraficaSoggettoVO = new StoricoAnagraficaSoggettoVO();
		
		storicoAnagraficaSoggettoVO.setCodiceAzienda(dto.getCodiceAzienda());
		storicoAnagraficaSoggettoVO.setDataRiferimento(dto.getDataRiferimento());
		storicoAnagraficaSoggettoVO.setDenominazione(dto.getDenominazione());
		storicoAnagraficaSoggettoVO.setEmail(dto.getEmail());
		storicoAnagraficaSoggettoVO.setIban(dto.getIban());
		storicoAnagraficaSoggettoVO.setIdAnag(dto.getIdAnag());
		storicoAnagraficaSoggettoVO.setIdStoricoAnag(dto.getIdStoricoAnag());
		storicoAnagraficaSoggettoVO.setIndirizzo(dto.getIndirizzo());
		storicoAnagraficaSoggettoVO.setPec(dto.getPec());
		
		storicoAnagraficaSoggettoVO.setAnnualita(dto.getAnnualita());
		storicoAnagraficaSoggettoVO.setOwnerOperazione(dto.getOwnerOperazione());
		storicoAnagraficaSoggettoVO.setCfPiva(dto.getCfPiva());
		storicoAnagraficaSoggettoVO.setIdImport(dto.getIdImport());
		
		return storicoAnagraficaSoggettoVO;
	}

}
