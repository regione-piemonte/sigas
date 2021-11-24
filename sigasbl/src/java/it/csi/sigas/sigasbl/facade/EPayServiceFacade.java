package it.csi.sigas.sigasbl.facade;

import it.csi.sigas.sigasbl.integration.epay.to.InserisciListaDiCaricoRequest;
import it.csi.sigas.sigasbl.model.vo.home.PaymentRedirectVO;

/**
 */
public interface EPayServiceFacade {

	void inserisciListaDiCarico(InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest);

	public PaymentRedirectVO getPaymentRedirectInfo(String iuv, String identificativoPagamento, String codiceFiscale, String defaultWaitMsg) throws Exception;

}
