package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasRateo;
import it.csi.sigas.sigasbl.model.mapper.entity.SigasRateoEntityMapper;
import it.csi.sigas.sigasbl.model.vo.home.RateoVO;

@Component
public class SigasRateoEntityMapperImpl implements SigasRateoEntityMapper {

	@Override
	public RateoVO mapEntityToVO(SigasRateo dto) {		
		return mapRateoVOFromDTO(dto);
	}

	@Override
	public SigasRateo mapVOtoEntity(RateoVO vo) {
		SigasRateo sigasRateo = null;
		if(vo != null) {
			sigasRateo = new SigasRateo();
			sigasRateo.setAnnualita(vo.getAnnualita());
			sigasRateo.setIdAnag(vo.getIdAnag());
			sigasRateo.setIdProvincia(vo.getIdProvincia());
			sigasRateo.setIdRateo(vo.getIdRateo());
			sigasRateo.setImporto(vo.getImporto());
			sigasRateo.setMese(vo.getMese());			
		}
		
		return sigasRateo;
	}

	@Override
	public List<RateoVO> mapListEntityToListVO(List<SigasRateo> en) {		
		List<RateoVO> rateoVOLista = null;
		if(en != null && !en.isEmpty() ) {
			rateoVOLista = new ArrayList<>();
			Iterator<SigasRateo> iterator = en.iterator();
			while(iterator.hasNext()) {
				rateoVOLista.add(mapRateoVOFromDTO(iterator.next()));
			}			
		}
		return rateoVOLista;
	}
	
	private RateoVO mapRateoVOFromDTO(SigasRateo dto) {
		RateoVO rateoVO = null;
		if(dto != null) {
			rateoVO = new RateoVO();
			rateoVO.setAnnualita(dto.getAnnualita());
			rateoVO.setIdAnag(dto.getIdAnag());
			rateoVO.setIdProvincia(dto.getIdProvincia());
			rateoVO.setIdRateo(dto.getIdRateo());
			rateoVO.setMese(dto.getMese());
			rateoVO.setImporto(dto.getImporto());
		}
		
		return rateoVO;
	}
	

}
