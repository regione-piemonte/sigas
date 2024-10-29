package it.csi.sigas.sigasbl.service;


import java.util.List;

import it.csi.sigas.sigasbl.model.vo.home.RateoVO;
import it.csi.sigas.sigasbl.request.home.RicercaRateoRequest;

public interface IRateoService {
	
	public RateoVO findByIdRateo(Long id);
	
	public List<RateoVO> findListaRateoByidAnagSiglaProvincia(Long idAnag, String siglaProvincia);
	
	public RateoVO findRateoByidAnagSiglaProvinciaMese(Long idAnag, String siglaProvincia, String mese);
	
	public void inserisciRateo(RateoVO rateoVO);
	
	public void updateRateo(RateoVO rateoVO);
	
	public List<RateoVO> ricercaRateoListByRequest(RicercaRateoRequest ricercaRateoRequest);
	
	public void deleteRateo(Long id);
}
