package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.model.vo.home.CompensazionePrVO;

public class SalvaCompensazioneRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "compensazione non deve essere vuoto")
	private CompensazionePrVO compensazionePrVO;

	public CompensazionePrVO getCompensazionePrVO() {
		return compensazionePrVO;
	}

	public void setCompensazionePrVO(CompensazionePrVO compensazionePrVO) {
		this.compensazionePrVO = compensazionePrVO;
	}
	

}
