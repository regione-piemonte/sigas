package it.csi.sigas.sigasbl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasRateo;
import it.csi.sigas.sigasbl.model.mapper.entity.SigasRateoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasRateoRepository;
import it.csi.sigas.sigasbl.model.vo.home.RateoVO;
import it.csi.sigas.sigasbl.request.home.RicercaRateoRequest;
import it.csi.sigas.sigasbl.service.IRateoService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;

@Service
public class RateoServiceImpl implements IRateoService {
	
	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	@Autowired
	SigasRateoRepository sigasRateoRepository;
	
	@Autowired
	SigasRateoEntityMapper sigasRateoEntityMapper;
		

	@Override
	public RateoVO findByIdRateo(Long id) {
		RateoVO rateVO = null;
		if(id != null) {
			rateVO = sigasRateoEntityMapper.mapEntityToVO(sigasRateoRepository.findByIdRateo(id));
		}
		return rateVO;
	}

	@Override
	public List<RateoVO> findListaRateoByidAnagSiglaProvincia(Long idAnag, String siglaProvincia) {
		List<RateoVO> rateoVOList = null;
		if(idAnag != null && siglaProvincia != null) {
			rateoVOList = sigasRateoEntityMapper.mapListEntityToListVO(sigasRateoRepository.findListaRateoByidAnagSiglaProvincia(idAnag, siglaProvincia));
		}
		return rateoVOList;
	}

	@Override
	public RateoVO findRateoByidAnagSiglaProvinciaMese(Long idAnag, String siglaProvincia, String mese) {
		RateoVO rateVO = null;
		if(idAnag != null && siglaProvincia != null && mese != null) {
			rateVO = sigasRateoEntityMapper.mapEntityToVO(sigasRateoRepository.findRateoByidAnagSiglaProvinciaMese(idAnag, siglaProvincia, mese));
		}
		return rateVO;
	}

	@Override
	public void inserisciRateo(RateoVO rateoVO) {
		List<String> listKeyOper = new ArrayList<>();
		String oggOper ="";
		
		oggOper = "sigas_rateo ";
		listKeyOper.add("idAnag:" + String.valueOf(rateoVO.getIdAnag()));
		listKeyOper.add("annualita:" + rateoVO.getAnnualita());
		listKeyOper.add("provincia:" + String.valueOf(rateoVO.getIdProvincia()));
		listKeyOper.add("mese:" + rateoVO.getMese());
		
		sigasRateoRepository.save(sigasRateoEntityMapper.mapVOtoEntity(rateoVO));
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERT - inserisciRateo", oggOper,String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
										   csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), 
										   csiLogAudit.getId().getKeyOper());
		
	}

	@Override
	public void updateRateo(RateoVO rateoVO) {
		List<String> listKeyOper = new ArrayList<>();
		String oggOper ="";
		
		oggOper = "sigas_rateo ";
		listKeyOper.add("idRateo:" + rateoVO.getIdRateo());
		listKeyOper.add("idAnag:" + String.valueOf(rateoVO.getIdAnag()));
		listKeyOper.add("annualita:" + rateoVO.getAnnualita());
		listKeyOper.add("provincia:" + String.valueOf(rateoVO.getIdProvincia()));
		listKeyOper.add("mese:" + rateoVO.getMese());
		
		sigasRateoRepository.save(sigasRateoEntityMapper.mapVOtoEntity(rateoVO));
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"UPDATE - updateRateo", oggOper,String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
										   csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), 
										   csiLogAudit.getId().getKeyOper());
		
	}

	@Override
	public List<RateoVO> ricercaRateoListByRequest(RicercaRateoRequest ricercaRateoRequest) {
		List<RateoVO> rateoVOList = null;
		if(ricercaRateoRequest != null) {
			List<SigasRateo> sigasRateoList = sigasRateoRepository.findRateoListByParams(ricercaRateoRequest.getIdAnag(),
																						 (ricercaRateoRequest.getSiglaProvincia() == null) ? "" : ricercaRateoRequest.getSiglaProvincia(),
																			             (ricercaRateoRequest.getMese() == null) ? "" : ricercaRateoRequest.getMese(),
																			             (ricercaRateoRequest.getAnnualita() == null) ? "" : ricercaRateoRequest.getAnnualita());
			rateoVOList = sigasRateoEntityMapper.mapListEntityToListVO(sigasRateoList);
		}
		return rateoVOList;
	}
	
	@Override
	public void deleteRateo(Long id) {		
		if(id != null) {
			
			List<String> listKeyOper = new ArrayList<>();
			String oggOper ="";
			
			oggOper = "sigas_rateo ";
			listKeyOper.add("idRateo:" + String.valueOf(id));			
			
			sigasRateoRepository.delete(id);
			
			CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"DELETE - deleteRateo", oggOper,String.join("_", listKeyOper));
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
											   csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), 
											   csiLogAudit.getId().getKeyOper());
		}		
	}

}
