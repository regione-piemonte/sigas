package it.csi.sigas.sigasbl.integration.epay.mapper.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.integration.epay.mapper.PPayRestCreateDebtPositionRequestMapper;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.RequestObjects.ComponentePagamento;
import it.csi.sigas.sigasbl.integration.epay.rest.ppay.RequestObjects.CreateDebtPositionRequest;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichVersamentiRepository;

@Component
public class PPayRestCreateDebtPositionRequestMapperImpl implements PPayRestCreateDebtPositionRequestMapper {
	
	
	@Autowired
	private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;
	
	@Autowired
	private SigasDichVersamentiRepository sigasDichVersamentiRepository;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;

	@Override
	public CreateDebtPositionRequest mapCreateDebtRequest(List<SigasPaymentCart> cart, 
														  String cf, 
														  String descrizioneCausaleVersamento, 
														  String cfEnte,
														  Integer annoAccertamento,
														  String numeroAccertamento) 
	{
		CreateDebtPositionRequest createDebtPositionRequest = new CreateDebtPositionRequest();
		
		List<ComponentePagamento> componentePagamentoList = new ArrayList<>();
		
		SigasPaymentCart cartInfo = cart.get(0);
		
		BigDecimal limiteNumeroGiorniParam = getParametroByDescrizione("check_debtposition_status_limite_numero_giorni").getValoreNumber();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));
		Calendar calendar = Calendar.getInstance();
		Date systemDate = calendar.getTime();
		
		//Determinazione anagrafica associata
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(cartInfo.getFkAnagSoggetto().longValue());		
		
		//Causale pagamento
		createDebtPositionRequest.setCausale(descrizioneCausaleVersamento);
		
		//Codice Fiscale / PIVA Pagatore
		createDebtPositionRequest.setCodiceFiscalePartitaIVAPagatore(sigasAnagraficaSoggetti.getCfPiva());
		
		//Cognome Pagatore
		//-->Forzo l'utilizzo di questo campo allo scopo di visualizzare il codice azienda nel bollettino<--
		createDebtPositionRequest.setCognome(sigasAnagraficaSoggetti.getCodiceAzienda());
		
		//Nome Pagatore				
		createDebtPositionRequest.setNome(sigasAnagraficaSoggetti.getDenominazione());
		
		//Email pagatore
		if(cartInfo.getEmail()!=null && cartInfo.getEmail().length() > 0) {
			createDebtPositionRequest.setEmail(cartInfo.getEmail());
		}		
				
		//Identificativo pagamento
		createDebtPositionRequest.setIdentificativoPagamento(cartInfo.getCodicePagamento());		
		
		//Importo Totale
		BigDecimal totalAmount = BigDecimal.ZERO;		
		//totalAmount.setScale(2, RoundingMode.HALF_UP);		
		
		//Note		
		String notePerIlPagatore = cartInfo.getCodiceAzienda();
		if(Constants.RICHIESTA_DEP_CAUSIONALE_ID_TIPO_CARRELLO_PAGAMENTO.equals(cart.get(0).getFkTipoCarrello()) || 
		   Constants.RICHIESTA_DEP_CAUSIONALE_INTEGRAZIONE_ID_TIPO_CARRELLO_PAGAMENTO.equals(cart.get(0).getFkTipoCarrello()) ) 
		{
			for(SigasPaymentCart sigasRSoggRata : cart) {				
				//totalAmount = totalAmount.add(sigasRSoggRata.getImporto().setScale(2, RoundingMode.HALF_UP));
				totalAmount = totalAmount.add(sigasRSoggRata.getImporto());
			}
			SigasPaymentCart cartItem = cart.get(0);
			if(cart.size() > 1) {
				String importoString = totalAmount.setScale(2).toString().replace(".",",");
				notePerIlPagatore += " -  Tutte le province, " + cartInfo.getAnno() + ": " + importoString;
			} else {
				String importoString = cartItem.getImporto().setScale(2).toString().replace(".",",");
				notePerIlPagatore += " - " + cartItem.getSiglaProvincia() + ", " + cartInfo.getAnno() + ": " + 
									 importoString;
			}
			
			//Componenti Accertamento setup
			//cartItem.setImporto(totalAmount);
			Integer progressivoComponente = 1;
			cartItem.setImporto(totalAmount.setScale(2));
			ComponentePagamento componentePagamento = getComponentePagamentoFromCartItem(cartItem, descrizioneCausaleVersamento, 
																						 annoAccertamento, numeroAccertamento,
																						 progressivoComponente);
			if(componentePagamento!=null) {
				componentePagamentoList.add(componentePagamento);
			}
		} else {
			Integer progressivoComponente = 0;
			for(SigasPaymentCart sigasRSoggRata : cart) {
				
				progressivoComponente = progressivoComponente + 1;
				
				notePerIlPagatore += " - " + sigasRSoggRata.getSiglaProvincia() + ", " + 
									 sigasRSoggRata.getMese() + "/" + cartInfo.getAnno() + ": " + 
									 sigasRSoggRata.getImporto().setScale(2, RoundingMode.HALF_UP);
				totalAmount = totalAmount.add(sigasRSoggRata.getImporto().setScale(2, RoundingMode.HALF_UP));
				
				//Componenti Accertamento setup				
				ComponentePagamento componentePagamento = getComponentePagamentoFromCartItem(sigasRSoggRata, descrizioneCausaleVersamento, 
																							 annoAccertamento, numeroAccertamento,
																							 progressivoComponente);
				/*
				if(componentePagamento!=null) {
					componentePagamentoList.add(componentePagamento);
				}
				*/
							
			}
		}
		
		createDebtPositionRequest.setImporto(totalAmount);
		
		//Note
		createDebtPositionRequest.setNotePerIlPagatore(notePerIlPagatore);
		
		//Componenti Pagamento
		createDebtPositionRequest.setComponentiPagamento(componentePagamentoList);		
				
		//Data fine validità
		calendar.add(Calendar.DATE, limiteNumeroGiorniParam.intValue());
		createDebtPositionRequest.setDataFineValidita(simpleDateFormat.format(calendar.getTime()));
		
		//Data scadenza
		Boolean inviaScadenzaToPPAY = getParametroByDescrizione("check_debtposition_send_scadenza_to_ppay").getValoreBoolean();
		if(inviaScadenzaToPPAY!=null&&inviaScadenzaToPPAY==true) {
			createDebtPositionRequest.setDataScadenza(simpleDateFormat.format(calendar.getTime()));
		}				
		
		return createDebtPositionRequest;
	}	
	
	private SigasCParametro getParametroByDescrizione(String descrizione) {
		return sigasCParametroRepository.findByDescParametro(descrizione);
	}
	
	private ComponentePagamento getComponentePagamentoFromCartItem(SigasPaymentCart cart, String descrizioneCausaleVersamento, 
																   Integer annoAccertamento, String numeroAccertamento,
																   Integer progressivoComponente) 
	{
		
		ComponentePagamento componentePagamento = null;
		
		//if(cart != null && cart.getFkDichVersamento()!=null) {
		if(cart != null) {
			
			//Componenti Pagamento [NON obbligatorio]			
			componentePagamento = new ComponentePagamento();
			String annualita = null;
			if(cart.getFkDichVersamento()!=null) {
				annualita = sigasDichVersamentiRepository.findById(Long.valueOf(cart.getFkDichVersamento())).getAnnualita();
			}			
			if(annoAccertamento != null) {
				annualita = annoAccertamento.toString();
			}
			
			//Anno accertamento
			if(annualita!=null) {
				componentePagamento.setAnnoAccertamento(Integer.valueOf(annualita));
			}			
			
			//Causale
			componentePagamento.setCausale((descrizioneCausaleVersamento!=null) ? descrizioneCausaleVersamento : "");
			
			//Dati Specifici Riscossione [DA DEFINIRE]
			//componentePagamento.setDatiSpecificRiscossione("");
			
			//Importo
			componentePagamento.setImporto(cart.getImporto().setScale(2));
			
			//Numero accertamento
			if(numeroAccertamento!=null) {
				componentePagamento.setNumeroAccertamento(numeroAccertamento);
			}				
			
			//Progressivo [DA DEFINIRE]
			componentePagamento.setProgressivo(progressivoComponente);
			
		}	
		
		return componentePagamento;
		
	}

}
