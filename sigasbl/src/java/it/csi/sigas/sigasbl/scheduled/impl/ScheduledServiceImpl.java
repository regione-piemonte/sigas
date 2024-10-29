/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.scheduled.impl;

import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPaymentCartRepository;
import it.csi.sigas.sigasbl.scheduled.ScheduledService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ScheduledServiceImpl implements ScheduledService {

    protected static Logger logger = Logger.getLogger(ScheduledServiceImpl.class);

    @Autowired
    private SigasPaymentCartRepository sigasPaymentCartRepository;

    @Autowired
    private SigasCParametroRepository sigasCParametroRepository;

    // metodo da cancellare, anche dalla interface, task gestito tramite scheduler
    @Override
    @Scheduled(fixedDelay = 800000)    
    public void closeCartPending() {
        logger.info("inizio closeCartPending");
        List<SigasPaymentCart> listCartPay = sigasPaymentCartRepository.findByFkStatoCarrello(SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_NOTIFICATO);
        SigasCParametro delaytimeCloseCartPending = sigasCParametroRepository.findByDescParametro("tempoChiusuraCarrelliPending");
        
        String codicePagamentoPrecedenteItem = null;
        String codicePagamentoGenerato = null;
        
       for (SigasPaymentCart sigasPaymentCart : listCartPay) {
            long seconds = (sigasPaymentCart.getDataUpdate().getTime() - new Date().getTime()) / 1000;
            int hours = (int) (seconds / 3600);
            if (Math.abs(hours) >= delaytimeCloseCartPending.getValoreNumber().longValue()) {

                sigasPaymentCart.setFkStatoCarrello(SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_INCOMPLETO);
                sigasPaymentCartRepository.save(sigasPaymentCart);

                SigasPaymentCart cart = new SigasPaymentCart();
                cart.setFkStatoCarrello(SigasPaymentCart.STATO_CARRELLO_COMPLETO);
                cart.setDataPagamento(new Date());
                cart.setDataVersamento(new Date());
                
                //------------------------------------------------
                //CR PAGAMENTO REST FUL - CR-REQ-10
                //------------------------------------------------
                //cart.setCodicePagamento(sigasPaymentCartRepository.getUniquePaymentCode(sigasPaymentCart.getFkAnagSoggetto(), sigasPaymentCart.getAnno(), sigasPaymentCart.getFkUtenteInsert()));
                
                
                if(codicePagamentoPrecedenteItem == null || 
                   !sigasPaymentCart.getCodicePagamento().equalsIgnoreCase(codicePagamentoPrecedenteItem)) 
                {
                	codicePagamentoGenerato = sigasPaymentCartRepository.getUniquePaymentCodeForInsert(sigasPaymentCart.getFkAnagSoggetto(), 
																									   sigasPaymentCart.getAnno(), 
																									   sigasPaymentCart.getFkUtenteInsert());                	
                }
                cart.setCodicePagamento(codicePagamentoGenerato);
                codicePagamentoPrecedenteItem = sigasPaymentCart.getCodicePagamento();                                
                //------------------------------------------------
                //CR PAGAMENTO REST FUL - CR-REQ-10 - FINE
                //------------------------------------------------
                
                cart.setDataInsert(new Date());

                cart.setImporto(sigasPaymentCart.getImporto());
                cart.setEmail(sigasPaymentCart.getEmail());
                cart.setFkTipoPagamento(sigasPaymentCart.getFkTipoPagamento());
                cart.setFkAnagSoggetto(sigasPaymentCart.getFkAnagSoggetto());
                cart.setAnno(sigasPaymentCart.getAnno());
                cart.setDenominazioneVersante(sigasPaymentCart.getDenominazioneVersante());
                cart.setMese(sigasPaymentCart.getMese());
                cart.setFkTipoCarrello(sigasPaymentCart.getFkTipoCarrello());
                cart.setFkUtenteInsert(sigasPaymentCart.getFkUtenteInsert());
                cart.setSiglaProvincia(sigasPaymentCart.getSiglaProvincia());
                cart.setFkProvincia(sigasPaymentCart.getFkProvincia());
                cart.setCodiceAzienda(sigasPaymentCart.getCodiceAzienda());
                cart.setCfPiva(sigasPaymentCart.getCfPiva());

                sigasPaymentCartRepository.save(cart);
            }
        }
        logger.info("fine closeCartPending");
    }
}
