package it.csi.sigas.sigasbl.scheduled.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.PiemontePayRestApi;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.ResponseObjects.CheckDebtPositionsStatusResponse;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentCartRepository;
import it.csi.sigas.sigasbl.scheduled.IAvvisoPagamentoService;

@Service
public class AvvisoPagamentoServiceImpl implements IAvvisoPagamentoService {
	
	protected static Logger logger = Logger.getLogger(AvvisoPagamentoServiceImpl.class);
	
	private static final String DEBT_POSITION_STATUS_SCADUTO = "4";
	
	private static int FK_CARRELLO_SCADUTO = 52;
	
	private SigasPaymentCartRepository sigasPaymentCartRepository;
		
	private SigasCParametroRepository sigasCParametroRepository;
	
	private PiemontePayRestApi piemontePayRestApi;
	
	
    	
	@Autowired
	public AvvisoPagamentoServiceImpl(SigasPaymentCartRepository sigasPaymentCartRepository,
									  SigasCParametroRepository sigasCParametroRepository,
									  PiemontePayRestApi piemontePayRestApi) 
	{
		this.sigasPaymentCartRepository = sigasPaymentCartRepository;
		this.sigasCParametroRepository = sigasCParametroRepository;
		this.piemontePayRestApi = piemontePayRestApi;
	}


	@Override
	public String call() throws Exception 
	{
		logger.info("Esecuzione controllo via call PPAY Service delle DEBT POSITION");
        execCheckDebtPositionStatus();
        return "OK";
	}
	
	/*****************************************************************
	 * Funzioni Private
	 *****************************************************************/
	private void execCheckDebtPositionStatus() {
		
		logger.info("INIZIO: execCheckDebtPositionStatus");
		
		BigDecimal limiteNumeroGiorniParam = getParametroByDescrizione("check_debtposition_status_limite_numero_giorni").getValoreNumber();
		if(limiteNumeroGiorniParam==null) {
			logger.error("ERRORE execCheckDebtPositionStatus: parametro check_debtposition_status_limite_numero_giorni non presente");
			return;
		}				
		
		List<SigasPaymentCart> cartItemList = this.sigasPaymentCartRepository.retrieveCartItemsScaduti(limiteNumeroGiorniParam.intValue());
		if(cartItemList!=null && !cartItemList.isEmpty()) {
			logger.info("execCheckDebtPositionStatus numero di posizione scadute: " + cartItemList.size());
			Iterator<SigasPaymentCart> iterartor = cartItemList.iterator();
			while(iterartor.hasNext()) {
				SigasPaymentCart sigasPaymentCart = iterartor.next();
				try {
					
					/******************************************************
					 * Stato Posizione Debitoria 		Descrizione
					 * --------------------------       ------------------
					 *      0								Pagato
					 *      1								Non pagato
					 *      2								Annullato dall’Ente
					 *      3								Non ancora attiva
					 *      4								Non più attiva
					 *******************************************************/
					CheckDebtPositionsStatusResponse checkDebtPositionsStatusResponse  = this.piemontePayRestApi.checkDebtPosition(sigasPaymentCart.getIuv());
					if(DEBT_POSITION_STATUS_SCADUTO.equals(checkDebtPositionsStatusResponse.getCode())) {
						this.sigasPaymentCartRepository.updateCartItemStatus(sigasPaymentCart.getIdCarrello(), FK_CARRELLO_SCADUTO);
						logger.info("execCheckDebtPositionStatus aggionata posizione con id carrello: " + sigasPaymentCart.getIdCarrello());
					}					
					
				} catch (IntegrationException e) {
					logger.error("ERRORE execCheckDebtPositionStatus: errore durante la chiamata al servizio PPAY " + e.getMessage());
				}
			}
			
		}
		
		logger.info("FINE: execCheckDebtPositionStatus");
		
	}
	
	private SigasCParametro getParametroByDescrizione(String descrizione) {
		return sigasCParametroRepository.findByDescParametro(descrizione);
	}

}
