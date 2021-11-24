package it.csi.sigas.sigasbl.ppay;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.EsitoAggiornaPosizioniDebitorieRequest;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.EsitoInserimentoListaDiCaricoRequest;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.NotificaPagamentoType;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.RTType;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.TestataEsitoType;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.TestataNotifichePagamentoType;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.TestataRTType;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.TrasmettiAvvisiScadutiRequest;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.TrasmettiNotifichePagamentoRequest;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.TrasmettiRTRequest;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.TrasmettiRichiesteDiRevocaRequest;
import it.csi.sigas.sigasbl.integration.epay.from.epaywso2entisrv.EPaywso2EntiService;
import it.csi.sigas.sigasbl.integration.epay.from.types.EsitoAggiornamentoType;
import it.csi.sigas.sigasbl.integration.epay.from.types.EsitoInserimentoType;
import it.csi.sigas.sigasbl.integration.epay.from.types.PosizioneDebitoriaType;
import it.csi.sigas.sigasbl.integration.epay.from.types.ResponseType;
import it.csi.sigas.sigasbl.integration.epay.from.types.ResultType;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartNotify;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCartRT;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoVersamentoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.service.IPaymentFoService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebService(targetNamespace = "http://www.csi.it/epay/epaywso/epaywso2entisrv", name = "EPaywso2EntiService", serviceName = "ppay/EPaywso2EntiService")
@XmlSeeAlso({it.csi.sigas.sigasbl.integration.epay.from.types.ObjectFactory.class, it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class EPaywso2EntiServiceImpl extends SpringBeanAutowiringSupport implements EPaywso2EntiService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;
	
	@Autowired
	private IPaymentFoService paymentFoService;

	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;

	private ResponseType getResult(String code, String messagge) {
		ResponseType res = new ResponseType();
		ResultType resType = new ResultType();
		res.setResult(resType);
		
		resType.setCodice(code);
		resType.setMessaggio(messagge);
		
		return res;
	}

	private ResponseType getOkResult() {
		return getResult("000", "L'invocazione del servizio si è conclusa correttamente");
	}
	
	private ResponseType getErrorResult() {
		return getResult("001", "Si è verificato un errore generico");
	}
	
	String extractPeymentCodeFromIdMessage(String idMsg) {
		return idMsg.substring(0, idMsg.indexOf('-'));
	}
	
	
	@Override
	public ResponseType esitoInserimentoListaDiCarico(EsitoInserimentoListaDiCaricoRequest parameters) {
		String codResult = null;
		String msgResult = null;
		try {
			codResult = parameters.getResult().getCodice();
			msgResult = parameters.getResult().getMessaggio();
			
			TestataEsitoType testata = parameters.getTestataEsito();
			String codVer = testata.getCodiceVersamento();
			String cfCred = testata.getCFEnteCreditore();
			String idMsg = testata.getIdMessaggio();
			
			boolean isOk = "000".equals(codResult) || Integer.parseInt(codResult) == 0; 
			
	        logger.info("EPaywso2EntiService.esitoInserimentoListaDiCarico OK [codVer:" + codVer + ", cfCred: " + cfCred + ", idMsg: " + idMsg + ", codResult : " + codResult + ", msgResult: " + msgResult + "]: " + msgResult);
	        
			EsitoInserimentoType esito = parameters.getEsitoInserimento();
			List<PosizioneDebitoriaType> listaInserimenti = esito.getElencoPosizioniDebitorieInserite().getPosizioneDebitoriaInserita();
			for(PosizioneDebitoriaType iserimento: listaInserimenti)
				paymentFoService.insertCartItemIUV(extractPeymentCodeFromIdMessage(idMsg), 
						iserimento.getIUV(), 
						idMsg,
						isOk? SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_NOTIFICATO : SigasPaymentCart.STATO_CARRELLO_PAGAMENTO_INCOMPLETO);
				
			return getOkResult();
		} catch (Exception e) {
			logger.error("EPaywso2EntiService.esitoInserimentoListaDiCarico", e);
		}
		
		return getErrorResult();
	}

	@Override
	public ResponseType esitoAggiornaPosizioniDebitorie(EsitoAggiornaPosizioniDebitorieRequest parameters) {
		// NOT USED
		logger.error("EPaywso2EntiService.esitoAggiornaPosizioniDebitorie: not used");
		return getErrorResult();
	}

	@Override
	public ResponseType trasmettiRichiesteDiRevoca(TrasmettiRichiesteDiRevocaRequest parameters) {
		// NOT USED
		logger.error("EPaywso2EntiService.trasmettiRichiesteDiRevoca: not used");
		return getErrorResult();
	}

	@Override
	public ResponseType trasmettiRT(TrasmettiRTRequest parameters) {
		try {
			TestataRTType testata = parameters.getTestata();
			String codVer = testata.getCodiceVersamento();
			String cfCred = testata.getCFEnteCreditore();
			String idMsg = testata.getIdMessaggio();
			Integer numRT = testata.getNumeroRT();
			
	        logger.info("EPaywso2EntiService.trasmettiRT[codVer:" + codVer + ", cfCred: " + cfCred + ", idMsg: " + idMsg + ", numRT: " + numRT);
	        
			for(RTType item : parameters.getCorpoRT().getElencoRT().getRT()) {
				SigasPaymentCartRT cartRT = new SigasPaymentCartRT();
				cartRT.setIdRT(item.getId()); 
				cartRT.setNumeroRT(numRT); 
				cartRT.setXml(item.getXML());
				
				paymentFoService.storePaymentCartRT(cartRT);
			}

			return getOkResult();
		} catch (Exception e) {
			logger.error("EPaywso2EntiService.trasmettiRT", e);
		}
		
		return getErrorResult();
	}

	@Override
	public ResponseType trasmettiNotifichePagamento(TrasmettiNotifichePagamentoRequest parameters) {
		try {
			TestataNotifichePagamentoType testata = parameters.getTestata();
			String codVer = testata.getCodiceVersamento();
			String cfCred = testata.getCFEnteCreditore();
			String idMsg = testata.getIdMessaggio();
			String importo = testata.getImportoTotalePagamenti().toString();
			int npagamneti = testata.getNumeroPagamenti();
			
	        logger.info("EPaywso2EntiService.trasmettiNotifichePagamento[codVer:" + codVer + ", cfCred: " + cfCred + ", idMsg: " + idMsg + ", importo: " + importo + ", npagamneti: " +npagamneti);
			
			for(NotificaPagamentoType notifica : parameters.getCorpoNotifichePagamento().getElencoNotifichePagamento().getNotificaPagamento()) {
				SigasPaymentCartNotify cartNotify = new SigasPaymentCartNotify();

				String IUV = notifica.getIUV();

				cartNotify.setIdPosizioneDebitoria(notifica.getIdPosizioneDebitoria());
				cartNotify.setIUV(IUV);
				cartNotify.setDescrizioneCausaleVersamento(notifica.getDescrizioneCausaleVersamento());
				cartNotify.setDatiSpecificiRiscossione(notifica.getDatiSpecificiRiscossione());
				cartNotify.setNote(notifica.getNote());
				cartNotify.setCodiceAvviso(notifica.getCodiceAvviso());
				
				try { cartNotify.setAnnoDiRiferimento(notifica.getAnnoDiRiferimento().toString()); } catch (Exception setToNull) { }
				try { cartNotify.setImportoPagato(""+notifica.getImportoPagato().toBigInteger().floatValue()); } catch (Exception setToNull) { }
				
				// optional params
				try { cartNotify.setDataScadenza(notifica.getDataScadenza().toGregorianCalendar().getTime()); } catch (Exception setToNull) { }
				try { cartNotify.setDataEsitoPagamento(notifica.getDataEsitoPagamento().toGregorianCalendar().getTime()); } catch (Exception setToNull) { }
				try { cartNotify.setSoggettoDebitore(notifica.getSoggettoDebitore().getIdentificativoUnivocoFiscale()); } catch (Exception setToNull) { }
				try { cartNotify.setSoggettoVersante(notifica.getSoggettoVersante().getIdentificativoUnivocoFiscale()); } catch (Exception setToNull) { }
				try { cartNotify.setDatiTransazionePSP(notifica.getDatiTransazionePSP().toString()); } catch (Exception setToNull) { }

				Integer idcar = paymentFoService.storePaymentCartNotify(cartNotify, IUV);
				paymentFoService.sendMail2ConfirmPayment(IUV);

				try {
			        CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERT - pagamentoPPay", "sigas_carrello_notifica", ""+idcar, true);
					csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
							csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
				} catch (Exception e) {
					logger.error("EPaywso2EntiService.trasmettiNotifichePagamento audit exception", e);
				}
				
			}

			return getOkResult();
		} catch (Throwable e) {
			logger.error("EPaywso2EntiService.trasmettiNotifichePagamento exception", e);
		}
		
		return getErrorResult();
	}
	
	@Override
	public ResponseType trasmettiAvvisiScaduti(TrasmettiAvvisiScadutiRequest parameters) {
		// NOT USED
		logger.error("EPaywso2EntiService.trasmettiAvvisiScaduti: not used");
		return getErrorResult();
	}
}
