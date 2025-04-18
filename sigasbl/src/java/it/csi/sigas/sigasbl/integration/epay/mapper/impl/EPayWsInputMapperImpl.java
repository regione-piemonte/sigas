package it.csi.sigas.sigasbl.integration.epay.mapper.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import it.csi.sigas.sigasbl.common.config.EPayConfig;
import it.csi.sigas.sigasbl.integration.epay.mapper.EPayWsInputMapper;
import it.csi.sigas.sigasbl.integration.epay.to.InserisciListaDiCaricoRequest;
import it.csi.sigas.sigasbl.integration.epay.to.ListaDiCarico;
import it.csi.sigas.sigasbl.integration.epay.to.PersonaGiuridicaType;
import it.csi.sigas.sigasbl.integration.epay.to.PosizioneDaInserireType;
import it.csi.sigas.sigasbl.integration.epay.to.SoggettoType;
import it.csi.sigas.sigasbl.integration.epay.to.TestataListaCarico;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;

/**
 * @author
 * @date
 */

@Component
public class EPayWsInputMapperImpl implements EPayWsInputMapper {

//	@Autowired
//	private EPayConfig config;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	@Autowired
	private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;
	
	@Override
	public InserisciListaDiCaricoRequest mapPagamentoWsMapper(List<SigasPaymentCart> cart, 
								String cf, 
								String descrizioneCausaleVersamento, 
								String cfEnte) {
		InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest = new InserisciListaDiCaricoRequest();

		ListaDiCarico listaDiCarico = new ListaDiCarico();
		TestataListaCarico testataListaCarico = new TestataListaCarico();

		SigasPaymentCart rateInfo = cart.get(0);

		List<PosizioneDaInserireType> posInserireTypeList = new ArrayList<>();
		
		BigDecimal totalAmount = BigDecimal.ZERO;
		
		//2023-IM009345 - WO1
		//totalAmount.setScale(2, RoundingMode.HALF_UP);		
		//-----
		
		String notePerIlPagatore = rateInfo.getCodiceAzienda(); 
		for(SigasPaymentCart sigasRSoggRata : cart) {
			notePerIlPagatore += " - " + sigasRSoggRata.getSiglaProvincia() + ", " + 
								 sigasRSoggRata.getMese() + "/" + rateInfo.getAnno() + ": " + 
								 sigasRSoggRata.getImporto().setScale(2, RoundingMode.HALF_UP);
			totalAmount = totalAmount.add(sigasRSoggRata.getImporto().setScale(2, RoundingMode.HALF_UP));
		}
		
		
		PosizioneDaInserireType posizioneDaInserireType = new PosizioneDaInserireType();
		posizioneDaInserireType.setDescrizioneCausaleVersamento(descrizioneCausaleVersamento);
		posizioneDaInserireType.setIdPosizioneDebitoria(rateInfo.getCodicePagamento());
		posizioneDaInserireType.setImportoTotale(totalAmount);

		SoggettoType soggettoPagatore = new SoggettoType();	

		PersonaGiuridicaType personaGiuridica = new PersonaGiuridicaType();
		personaGiuridica.setRagioneSociale(StringUtils.abbreviate(rateInfo.getDenominazioneVersante(), 70));
		soggettoPagatore.setPersonaGiuridica(personaGiuridica); 		
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(rateInfo.getFkAnagSoggetto().longValue());
 		soggettoPagatore.setIdentificativoUnivocoFiscale(sigasAnagraficaSoggetti.getCfPiva());
		soggettoPagatore.setEMail(rateInfo.getEmail());	
		
		posizioneDaInserireType.setSoggettoPagatore(soggettoPagatore);
		
		posizioneDaInserireType.setNotePerIlPagatore(StringUtils.abbreviate(notePerIlPagatore, 1000));
		posInserireTypeList.add(posizioneDaInserireType);

		// testataListaDicarico
		testataListaCarico.setCFEnteCreditore(cfEnte);
		testataListaCarico.setCodiceVersamento(sigasCParametroRepository.findByDescParametro("epay_service_codice_versamento").getValoreString());
		testataListaCarico.setImportoTotaleListaDiCarico(totalAmount);
		testataListaCarico.setNumeroPosizioniDebitorie(BigInteger.valueOf(1));
		
		testataListaCarico.setIdMessaggio(rateInfo.getCodicePagamento() + "-" +(new SimpleDateFormat("hhMMssS").format(new Date())));

		listaDiCarico.setPosizioniDaInserire(posInserireTypeList.toArray(new PosizioneDaInserireType[0]));
		inserisciListaDiCaricoRequest.setListaDiCarico(listaDiCarico);
		inserisciListaDiCaricoRequest.setTestata(testataListaCarico);

		return inserisciListaDiCaricoRequest;
	}

}
