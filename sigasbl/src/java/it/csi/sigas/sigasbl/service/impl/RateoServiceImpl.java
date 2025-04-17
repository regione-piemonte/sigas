package it.csi.sigas.sigasbl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.entity.SigasRateo;
import it.csi.sigas.sigasbl.model.mapper.entity.SigasRateoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
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
	
	@Autowired
	private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;
	
	@Autowired
	private SigasProvinciaRepository sigasProvinciaRepository;
		

	@Override
	public RateoVO findByIdRateo(Long id) {
		RateoVO rateVO = null;
		if(id != null) {
			rateVO = sigasRateoEntityMapper.mapEntityToVO(sigasRateoRepository.findByIdRateo(id));
			if(rateVO != null) 
			{
				//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(rateVO.getIdAnag());
				SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(rateVO.getIdAnag());
				if(sigasAnagraficaSoggetti != null && sigasAnagraficaSoggetti.getIdFusione() > 0) {
					
					SigasProvincia sigasProvincia = sigasProvinciaRepository.findOne(rateVO.getIdProvincia());
					RateoVO rateVOIncorporato = sigasRateoEntityMapper.mapEntityToVO(sigasRateoRepository.findRateoByidAnagSiglaProvinciaMese(sigasAnagraficaSoggetti.getIdFusione(), 
																																			  sigasProvincia.getCodIstatProvincia(), 
																																			  rateVO.getMese()));				
					if(rateVOIncorporato!=null) {						
						rateVO.setImporto(rateVO.getImporto() + rateVOIncorporato.getImporto());						
					}				
				}
				
			}	
			
		}
		return rateVO;
	}

	@Override
	public List<RateoVO> findListaRateoByidAnagSiglaProvincia(Long idAnag, String siglaProvincia) {
		List<RateoVO> rateoVOList = null;
		List<RateoVO> rateoVOListTotale = null;
		if(idAnag != null && siglaProvincia != null) {
			rateoVOList = sigasRateoEntityMapper.mapListEntityToListVO(sigasRateoRepository.findListaRateoByidAnagSiglaProvincia(idAnag, siglaProvincia));
			
			//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(idAnag);
			SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(idAnag);
			if(sigasAnagraficaSoggetti != null && sigasAnagraficaSoggetti.getIdFusione() > 0) {
				List<SigasRateo> sigasRateoListSoggettoIncorporato = sigasRateoRepository.findListaRateoByidAnagSiglaProvincia(idAnag, siglaProvincia);
				if(sigasRateoListSoggettoIncorporato!=null && !sigasRateoListSoggettoIncorporato.isEmpty()) {
					List<RateoVO> rateoVOListSoggettoIncorporato = sigasRateoEntityMapper.mapListEntityToListVO(sigasRateoListSoggettoIncorporato);
					Map<String, RateoVO> sigasRateoListSoggettoIncorporatoMap = new HashMap<>();
					Iterator<RateoVO> iterator =  rateoVOListSoggettoIncorporato.iterator();
					while(iterator.hasNext()) {
						RateoVO rateoVO = iterator.next();
						String key = rateoVO.getIdProvincia() + rateoVO.getAnnualita() + rateoVO.getMese();
						sigasRateoListSoggettoIncorporatoMap.put(key, rateoVO);
					}
					
					rateoVOListTotale = new ArrayList<>();
					if(rateoVOList != null && !rateoVOList.isEmpty()) {
						Iterator<RateoVO> iteratorFinale = rateoVOList.iterator();
						while(iteratorFinale.hasNext()) {
							RateoVO rateoVO = iteratorFinale.next();
							String key = rateoVO.getIdProvincia() + rateoVO.getAnnualita() + rateoVO.getMese();
							
							RateoVO rateoVOIncorprato = sigasRateoListSoggettoIncorporatoMap.get(key);
							if(rateoVOIncorprato!=null) {
								rateoVO.setImporto(rateoVO.getImporto() + rateoVOIncorprato.getImporto());
								sigasRateoListSoggettoIncorporatoMap.remove(key);
							}
							rateoVOListTotale.add(rateoVO);
						}
						if(sigasRateoListSoggettoIncorporatoMap !=null && !sigasRateoListSoggettoIncorporatoMap.isEmpty()) {
							rateoVOListTotale.addAll(new ArrayList<>(sigasRateoListSoggettoIncorporatoMap.values()));
						}
					
					} else {						
						rateoVOListTotale.addAll(rateoVOListSoggettoIncorporato);
					}					
				}
			} else {
				return rateoVOList;
			}
		}		
		return rateoVOListTotale;
	}

	@Override
	public RateoVO findRateoByidAnagSiglaProvinciaMese(Long idAnag, String siglaProvincia, String mese) {
		RateoVO rateVO = null;
		
		if(idAnag != null && siglaProvincia != null && mese != null) {
			
			rateVO = sigasRateoEntityMapper.mapEntityToVO(sigasRateoRepository.findRateoByidAnagSiglaProvinciaMese(idAnag, siglaProvincia, mese));
			
			//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(idAnag);
			SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(idAnag);
			if(sigasAnagraficaSoggetti != null && sigasAnagraficaSoggetti.getIdFusione() > 0) {
				RateoVO rateVOIncorporato = sigasRateoEntityMapper.mapEntityToVO(sigasRateoRepository.findRateoByidAnagSiglaProvinciaMese(sigasAnagraficaSoggetti.getIdFusione(), 
																																		  siglaProvincia, 
																																		  mese));				
				if(rateVOIncorporato!=null) {
					if(rateVO != null) {
						rateVO.setImporto(rateVO.getImporto()+rateVOIncorporato.getImporto());
					} else {
						return rateVOIncorporato;
					}
				}				
			}			
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
		List<RateoVO> rateoVOListTotale = null;
		if(ricercaRateoRequest != null) {
			
			List<SigasRateo> sigasRateoList = sigasRateoRepository.findRateoListByParams(ricercaRateoRequest.getIdAnag(),
																						 (ricercaRateoRequest.getSiglaProvincia() == null) ? "" : ricercaRateoRequest.getSiglaProvincia(),
																			             (ricercaRateoRequest.getMese() == null) ? "" : ricercaRateoRequest.getMese(),
																			             (ricercaRateoRequest.getAnnualita() == null) ? "" : ricercaRateoRequest.getAnnualita());
			rateoVOList = sigasRateoEntityMapper.mapListEntityToListVO(sigasRateoList);
						
			//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(ricercaRateoRequest.getIdAnag());
			SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(ricercaRateoRequest.getIdAnag());
			if(sigasAnagraficaSoggetti != null && sigasAnagraficaSoggetti.getIdFusione() > 0) {

				List<SigasRateo> sigasRateoListSoggettoIncorporato = sigasRateoRepository.findRateoListByParams(sigasAnagraficaSoggetti.getIdFusione(),
																							 					(ricercaRateoRequest.getSiglaProvincia() == null) ? "" : ricercaRateoRequest.getSiglaProvincia(),
																							 					(ricercaRateoRequest.getMese() == null) ? "" : ricercaRateoRequest.getMese(),
																							 					(ricercaRateoRequest.getAnnualita() == null) ? "" : ricercaRateoRequest.getAnnualita());
				if(sigasRateoListSoggettoIncorporato!=null && !sigasRateoListSoggettoIncorporato.isEmpty()) {
					List<RateoVO> rateoVOListSoggettoIncorporato = sigasRateoEntityMapper.mapListEntityToListVO(sigasRateoListSoggettoIncorporato);
					Map<String, RateoVO> sigasRateoListSoggettoIncorporatoMap = new HashMap<>();
					Iterator<RateoVO> iterator =  rateoVOListSoggettoIncorporato.iterator();
					while(iterator.hasNext()) {
						RateoVO rateoVO = iterator.next();
						String key = rateoVO.getIdProvincia() + rateoVO.getAnnualita() + rateoVO.getMese();
						sigasRateoListSoggettoIncorporatoMap.put(key, rateoVO);
					}
					
					rateoVOListTotale = new ArrayList<>();
					if(rateoVOList != null && !rateoVOList.isEmpty()) {
						Iterator<RateoVO> iteratorFinale = rateoVOList.iterator();
						while(iteratorFinale.hasNext()) {
							RateoVO rateoVO = iteratorFinale.next();
							String key = rateoVO.getIdProvincia() + rateoVO.getAnnualita() + rateoVO.getMese();
							
							RateoVO rateoVOIncorprato = sigasRateoListSoggettoIncorporatoMap.get(key);
							if(rateoVOIncorprato!=null) {
								rateoVO.setImporto(rateoVO.getImporto() + rateoVOIncorprato.getImporto());
								sigasRateoListSoggettoIncorporatoMap.remove(key);
							}
							rateoVOListTotale.add(rateoVO);
						}
						if(sigasRateoListSoggettoIncorporatoMap!=null && !sigasRateoListSoggettoIncorporatoMap.isEmpty()) {
							rateoVOListTotale.addAll(new ArrayList<>(sigasRateoListSoggettoIncorporatoMap.values()));
						}
						
					} else {						
						rateoVOListTotale.addAll(rateoVOListSoggettoIncorporato);
					}					
				}
			} else {
				return rateoVOList;
			}
			
		}
		//return rateoVOList;
		return rateoVOListTotale;
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
	
	/**************************************************************************
	 * FUNZIONI PRIVATE
	 */

}
