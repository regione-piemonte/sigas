package it.csi.sigas.sigasbl.dispatcher.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.dispatcher.IRateoDispatcher;
import it.csi.sigas.sigasbl.model.vo.home.RateoVO;
import it.csi.sigas.sigasbl.request.home.RicercaRateoRequest;
import it.csi.sigas.sigasbl.service.IRateoService;

@Component
public class RateoDispatcherImpl implements IRateoDispatcher {
	
	@Autowired
	IRateoService rateoService;

	@Override
	public RateoVO findByIdRateo(Long id) {		
		return rateoService.findByIdRateo(id);
	}

	@Override
	public List<RateoVO> findListaRateoByidAnagSiglaProvincia(Long idAnag, String siglaProvincia) {		
		return rateoService.findListaRateoByidAnagSiglaProvincia(idAnag, siglaProvincia);
	}

	@Override
	public RateoVO findRateoByidAnagSiglaProvinciaMese(Long idAnag, String siglaProvincia, String mese) {		
		return rateoService.findRateoByidAnagSiglaProvinciaMese(idAnag, siglaProvincia, mese);
	}

	@Override
	public void inserisciRateo(RateoVO rateoVO) {
		rateoService.inserisciRateo(rateoVO);		
	}

	@Override
	public void updateRateo(RateoVO rateoVO) {
		rateoService.updateRateo(rateoVO);
		
	}

	@Override
	public List<RateoVO> ricercaRateoListByRequest(RicercaRateoRequest ricercaRateoRequest) {		
		return rateoService.ricercaRateoListByRequest(ricercaRateoRequest);
	}
	
	@Override
	public void deleteRateo(Long id) {
		rateoService.deleteRateo(id);		
	}

}
