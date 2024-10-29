package it.csi.sigas.sigasbl.dispatcher;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.model.vo.home.RateoVO;
import it.csi.sigas.sigasbl.request.home.RicercaRateoRequest;

public interface IRateoDispatcher {
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public RateoVO findByIdRateo(Long id);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<RateoVO> ricercaRateoListByRequest(RicercaRateoRequest ricercaRateoRequest);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<RateoVO> findListaRateoByidAnagSiglaProvincia(Long idAnag, String siglaProvincia);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public RateoVO findRateoByidAnagSiglaProvinciaMese(Long idAnag, String siglaProvincia, String mese);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void inserisciRateo(RateoVO rateoVO);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void updateRateo(RateoVO rateoVO);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void deleteRateo(Long id);

}
