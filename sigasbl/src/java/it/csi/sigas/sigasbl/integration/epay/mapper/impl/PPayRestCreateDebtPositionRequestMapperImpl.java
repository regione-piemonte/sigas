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
														  String cfEnte) 
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
		createDebtPositionRequest.setEmail(cartInfo.getEmail());
				
		//Identificativo pagamento
		createDebtPositionRequest.setIdentificativoPagamento(cartInfo.getCodicePagamento());		
		
		//Importo Totale
		BigDecimal totalAmount = BigDecimal.ZERO;		
		totalAmount.setScale(2, RoundingMode.HALF_UP);		
		
		//Note		
		String notePerIlPagatore = cartInfo.getCodiceAzienda(); 
		for(SigasPaymentCart sigasRSoggRata : cart) {
			notePerIlPagatore += " - " + sigasRSoggRata.getSiglaProvincia() + ", " + 
								 sigasRSoggRata.getMese() + "/" + cartInfo.getAnno() + ": " + 
								 sigasRSoggRata.getImporto().setScale(2, RoundingMode.HALF_UP);
			totalAmount = totalAmount.add(sigasRSoggRata.getImporto().setScale(2, RoundingMode.HALF_UP));
			
			//Componenti Accertamento setup
			ComponentePagamento componentePagamento = getComponentePagamentoFromCartItem(sigasRSoggRata, descrizioneCausaleVersamento);
			if(componentePagamento!=null) {
				componentePagamentoList.add(componentePagamento);
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
	
	private ComponentePagamento getComponentePagamentoFromCartItem(SigasPaymentCart cart, String descrizioneCausaleVersamento) {
		
		ComponentePagamento componentePagamento = null;
		
		if(cart != null || descrizioneCausaleVersamento != null) {
			
			//Componenti Pagamento [NON obbligatorio]
			if(cart.getFkDichVersamento()!=null) {
				componentePagamento = new ComponentePagamento();
				String annualita = sigasDichVersamentiRepository.findById(Long.valueOf(cart.getFkDichVersamento())).getAnnualita();
				
				//Anno accertamento
				componentePagamento.setAnnoAccertamento(Integer.valueOf(annualita));
				
				//Causale
				componentePagamento.setCausale(descrizioneCausaleVersamento);
				
				//Dati Specifici Riscossione [DA DEFINIRE]
				//componentePagamento.setDatiSpecificRiscossione("");
				
				//Importo
				componentePagamento.setImporto(cart.getImporto());
				
				//Numero accertamento [DA DEFINIRE]
				//componentePagamento.setNumeroAccertamento("");
				
				//Progressivo [DA DEFINIRE]
				//componentePagamento.setProgessivo(null);
			}
		}	
		
		return componentePagamento;
		
	}

}
