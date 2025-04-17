package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.model.entity.SigasDepositoCausionale;
import it.csi.sigas.sigasbl.model.mapper.entity.AnagraficaSoggettiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DepositoCausionaleEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DocumentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.ProvinciaEntityMapper;
import it.csi.sigas.sigasbl.model.vo.depositocausionale.DepositoCausionaleVO;

@Component
public class DepositoCausionaleEntityMapperImpl implements DepositoCausionaleEntityMapper {
	
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	
	@Autowired
	private DocumentiEntityMapper documentiEntityMapper;
	
	@Autowired
	private AnagraficaSoggettiEntityMapper anagraficaSoggettiEntityMapper;

	@Override
	public DepositoCausionaleVO mapEntityToVO(SigasDepositoCausionale dto) {
		
		if(dto == null) {
			return null;
		}
		
		DepositoCausionaleVO depositoCausionaleVO = new DepositoCausionaleVO();
		
		depositoCausionaleVO.setIdDepositoCausionale(dto.getIdDepositoCausionale());		
		depositoCausionaleVO.setImporto(dto.getImporto());
		depositoCausionaleVO.setNumeroAccertamento(dto.getNumeroAccertamento());
		depositoCausionaleVO.setAnnoAcccertamento(dto.getAnnoAcccertamento());
		depositoCausionaleVO.setNumeroDetermina(dto.getNumeroDetermina());
		
		if(dto.getSigasProvincia() != null) {
			depositoCausionaleVO.setProvinciaVO(this.provinciaEntityMapper.mapEntityToVO(dto.getSigasProvincia()));
		}
		
		if(dto.getSigasDocumento() != null) {
			depositoCausionaleVO.setDocumentoVO(this.documentiEntityMapper.mapEntityToVO(dto.getSigasDocumento()));
		}
		
		if(dto.getSigasAnagraficaSoggetti() != null) {
			depositoCausionaleVO.setAnagraficaSoggettoVO(this.anagraficaSoggettiEntityMapper.mapEntityToVO(dto.getSigasAnagraficaSoggetti()));
		}
		
		return depositoCausionaleVO;
	}

	@Override
	public SigasDepositoCausionale mapVOtoEntity(DepositoCausionaleVO vo) {
		
		if(vo == null) {
			return null;
		}
		
		SigasDepositoCausionale sigasDepositoCausionale = new SigasDepositoCausionale();
		
		sigasDepositoCausionale.setIdDepositoCausionale(vo.getIdDepositoCausionale());
		sigasDepositoCausionale.setImporto(vo.getImporto());
		sigasDepositoCausionale.setAnnoAcccertamento(vo.getAnnoAcccertamento());
		sigasDepositoCausionale.setNumeroAccertamento(vo.getNumeroAccertamento());
		sigasDepositoCausionale.setNumeroDetermina(vo.getNumeroDetermina());
		
		if(vo.getProvinciaVO()!=null) {
			sigasDepositoCausionale.setSigasProvincia(this.provinciaEntityMapper.mapVOtoEntity(vo.getProvinciaVO()));
		}		
		
		if(vo.getDocumentoVO()!=null) {
			sigasDepositoCausionale.setSigasDocumento(this.documentiEntityMapper.mapVOtoEntity(vo.getDocumentoVO()));
		}
		
		if(vo.getAnagraficaSoggettoVO() != null) {
			sigasDepositoCausionale.setSigasAnagraficaSoggetti(this.anagraficaSoggettiEntityMapper.mapVOtoEntity(vo.getAnagraficaSoggettoVO()));
		}
		
		return sigasDepositoCausionale;
	}

	@Override
	public List<DepositoCausionaleVO> mapListEntityToListVO(List<SigasDepositoCausionale> en) {
		
		if(en == null || en.isEmpty()) {
			return null;
		}
		
		List<DepositoCausionaleVO> depositoCausionaleVOList = new ArrayList<>();
		
		Iterator<SigasDepositoCausionale> iterator = en.iterator();
		while(iterator.hasNext()) {
			depositoCausionaleVOList.add(mapEntityToVO(iterator.next()));
		}		
		
		
		return depositoCausionaleVOList;
	}
	
	public List<SigasDepositoCausionale> mapListVOToListEnttity(List<DepositoCausionaleVO> vn){
		if(vn == null || vn.isEmpty()) {
			return null;
		}
		
		List<SigasDepositoCausionale> sigasDepositoCausionaleList = new ArrayList<>();
		Iterator<DepositoCausionaleVO> iterator = vn.iterator();
		while(iterator.hasNext()) {
			DepositoCausionaleVO depositoCausionaleVO = iterator.next();
			sigasDepositoCausionaleList.add(mapVOtoEntity(depositoCausionaleVO));
		}
		
		return sigasDepositoCausionaleList;
	}

}
