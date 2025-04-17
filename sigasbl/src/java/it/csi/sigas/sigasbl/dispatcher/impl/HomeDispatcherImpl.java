/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.dispatcher.IHomeDispatcher;
import it.csi.sigas.sigasbl.model.entity.custom.SigasStoricoAnagraficaSoggettiCustom;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.StoricoAnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.AnaComunicazioniVO;
import it.csi.sigas.sigasbl.model.vo.home.ConsumiPrVO;
import it.csi.sigas.sigasbl.model.vo.home.MessaggiVO;
import it.csi.sigas.sigasbl.model.vo.home.OrdinativiIncassoVO;
import it.csi.sigas.sigasbl.model.vo.home.RimborsoVO;
import it.csi.sigas.sigasbl.model.vo.home.ScartoVO;
import it.csi.sigas.sigasbl.model.vo.home.SoggettiVO;
import it.csi.sigas.sigasbl.model.vo.home.StoricoConsumiVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoComunicazioniVO;
import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.AliquoteVO;
import it.csi.sigas.sigasbl.request.home.AllarmeDocumentoRequest;
import it.csi.sigas.sigasbl.request.home.AssociaSoggettoRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaConsumiRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaPagamentoRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaSoggettoRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoRequest;
import it.csi.sigas.sigasbl.request.home.FusioneSoggettoRequest;
import it.csi.sigas.sigasbl.request.home.RicercaAnaComunicazioniRequest;
import it.csi.sigas.sigasbl.request.home.RicercaConsumiRequest;
import it.csi.sigas.sigasbl.request.home.RicercaOrdinativiRequest;
import it.csi.sigas.sigasbl.request.home.RicercaSoggettoIncorporatoRequest;
import it.csi.sigas.sigasbl.request.home.RicercaStoricoSoggettiRequest;
import it.csi.sigas.sigasbl.request.home.SalvaCompensazioneRequest;
import it.csi.sigas.sigasbl.request.home.SalvaRimborsoRequest;
import it.csi.sigas.sigasbl.request.home.UpdateAllarmeAccertamentoRequest;
import it.csi.sigas.sigasbl.service.IHomeService;

@Component
public class HomeDispatcherImpl implements IHomeDispatcher {

	@Autowired
	private IHomeService HomeService;
	
	@Override
	public List<String> ricercaAnnualita() {
		return HomeService.ricercaAnnualita();
	}
	
	@Override
	public List<String> ricercaAnnualitaPagamenti() {
		return HomeService.ricercaAnnualitaPagamenti();
	}
	
	@Override
	public List<SoggettiVO> ricercaConsumi(RicercaConsumiRequest ricercaConsumiRequest) {
		return HomeService.ricercaConsumi(ricercaConsumiRequest);
	}
	
	@Override
	public List<OrdinativiIncassoVO> ricercaOrdinativi(RicercaOrdinativiRequest ricercaOrdinativiRequest) {
		return HomeService.ricercaOrdinativi(ricercaOrdinativiRequest);
	}
		
	@Override
	public AnagraficaSoggettoVO ricercaSoggettoByID(Long id) {
		return HomeService.ricercaSoggettoByID(id);
	}
	
	@Override
	public AnagraficaSoggettoVO ricercaSoggettoByCode(String codice) {
		return HomeService.ricercaSoggettoByCode(codice);
	}
	
	@Override
	public List<AnagraficaSoggettoVO> ricercaSoggetti() {
		return HomeService.ricercaSoggetti();
	}		

	@Override
	public List<ConsumiPrVO> ricercaConsumiPerProvince(Long id, String anno) {
		return HomeService.ricercaConsumiPerProvince(id,anno);
	}
	
	@Override
	public List<VersamentiPrVO> ricercaVersamentiPerProvince(Long id, String anno) {
		return HomeService.ricercaVersamentiPerProvince(id,anno);
	}
	
	@Override
	public void insertSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest, String user) {
		HomeService.insertSoggetto(confermaSoggettoRequest, user);
	}

	@Override
	public AnagraficaSoggettoVO updateSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest, String user) throws BusinessException {
		return HomeService.updateSoggetto(confermaSoggettoRequest, user);
	}
	
	@Override
	public AnagraficaSoggettoVO fusioneSoggetto(FusioneSoggettoRequest fusioneSoggettoRequest, String user) {
		return HomeService.fusioneSoggetto(fusioneSoggettoRequest, user);
	}
	
	@Override
	public void cancellaFusioneSoggetto(Long idAnagraficaIncorporante, String user) {
		HomeService.cancellaFusioneSoggetto(idAnagraficaIncorporante, user);
	}

	@Override
	public AnagraficaSoggettoVO associateSoggetto(AssociaSoggettoRequest associaSoggettoRequest, String user) {
		return HomeService.associateSoggetto(associaSoggettoRequest, user);
	}

	@Override
	public List<AnagraficaSoggettoVO> ricercaListaSoggetti(RicercaConsumiRequest ricercaConsumiRequest) {
		return HomeService.ricercaListaSoggetti(ricercaConsumiRequest);
	}
	
	@Override
	public List<ScartoVO> ricercaScartiByIdConsumi(Long idConsumi) {
		return HomeService.ricercaScartiByIdConsumi(idConsumi);
	}
	
	@Override
	public List<AliquoteVO> getAllAliquote(Integer annoDichiarazione) {
		return HomeService.getAllAliquote(annoDichiarazione);
	}
	
	@Override
	public ConsumiPrVO updateConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user) {
		return HomeService.updateConsumi(confermaConsumiRequest, user);
	}
	
	@Override
	public ConsumiPrVO updateCompensazioneConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user) {
		return HomeService.updateCompensazioneConsumi(confermaConsumiRequest, user);
	}
	
	@Override
	public List<AnagraficaSoggettoVO> ricercaListaNuoviSoggetti() {
		return HomeService.ricercaListaNuoviSoggetti();
	}
	
	@Override
	public Long getNumberOfSoggetti() {
		return HomeService.getNumberOfSoggetti();
	}
	
	@Override
	public List<StoricoConsumiVO> storicoConsumi(Long idConsumi) {
		return HomeService.storicoConsumi(idConsumi);
	}

	@Override
	public ConsumiPrVO ripristinaModificaConsumi(Long idConsumi, Long idStoricoConsumi, String user) {
		return HomeService.ripristinaModificaConsumi(idConsumi, idStoricoConsumi, user);
	}

	@Override
	public void validaSoggetto(Long idAnag, String anno,boolean validato) {
		HomeService.validaSoggetto(idAnag, anno, validato);
	}

	@Override
	public List<AnaComunicazioniVO> ricercaDocumentiByIdAnag(Long idAnag) {
		return HomeService.ricercaDocumentiByIdAnag(idAnag);
	}

	@Override
	public List<AnaComunicazioniVO> ricercaDocumentiByAnnoAndTipologia(RicercaAnaComunicazioniRequest ricercaAnaComunicazioniRequest){
		return HomeService.ricercaDocumentiByAnnoAndTipologia(ricercaAnaComunicazioniRequest);
	}

	@Override
	public AllarmiSoggettoVO allarmeDocumento(AllarmeDocumentoRequest allarmeDocumentoRequest, String utente) {
		return HomeService.allarmeDocumento(allarmeDocumentoRequest, utente);
	}

	@Override
	public AnaComunicazioniVO addDocumento(Long id, String user) {
		return HomeService.addDocumento(id, user);
	}

	@Override
	public AnaComunicazioniVO addDocument(String uid, String nomeFile, Long idAnag, MultipartFormDataInput input, String user) {
		return HomeService.addDocument(uid, nomeFile, idAnag, input, user);
	}
	

	@Override
	public AnaComunicazioniVO updateDocumento(String uid, String nomeFile, Long idAnag, MultipartFormDataInput input, String user) {
		return HomeService.updateDocumento(uid, nomeFile, idAnag, input, user);
	}
	
	public AnaComunicazioniVO updateTestataDocumento(AnaComunicazioniVO anaComunicazione, String user) {
		return HomeService.updateTestataDocumento(anaComunicazione, user);
	}

	@Override
	public byte[] getDocumento(String descComunicazione) throws FileNotFoundException, IOException {
		return HomeService.getDocumento(descComunicazione);
	}
	
	@Override
	public byte[] getStampaAllegato(String descComunicazione,  String descAllegato) throws FileNotFoundException, IOException {
		return HomeService.getStampaAllegato(descComunicazione, descAllegato);
	}
	
	@Override
	public List<TipoComunicazioniVO> getAllTipoComunicazioni() {
		return HomeService.getAllTipoComunicazioni();
	}
	
	@Override
	public TipoComunicazioniVO ricercaTipoComunicazioniByIdTipoComunicazione(Long idTipoComunicazione) {
		return HomeService.ricercaTipoComunicazioniByIdTipoComunicazione(idTipoComunicazione);
	}

	@Override
	public List<AllarmiSoggettoVO> ricercaAllarmiByIdDocumentoAndCodiceAzienda(Long idDocumento, String codiceAzienda) {
		return HomeService.ricercaAllarmiByIdDocumentoAndCodiceAzienda(idDocumento, codiceAzienda);
	}

	@Override
	public List<RimborsoVO> ricercaListaRimborsi(long idAnag) {
		return HomeService.ricercaListaRimborsi(idAnag);
	}
	
	@Override
	public ConsumiPrVO ricercaConsumiPerProvinceAndAnnualita(Long idAnag, String anno, String prov) {
		return HomeService.ricercaConsumiPerProvinceAndAnnualita(idAnag, anno, prov);
	}

	@Override
	public RimborsoVO salvaRimborso(SalvaRimborsoRequest salvaRimborsoRequest) {
		RimborsoVO rimborsoVO =  HomeService.salvaRimborso(salvaRimborsoRequest);
		return HomeService.ricercaRimborso(rimborsoVO.getIdRimborso());
	}

	@Override
	public void salvaDetermina(Long idComunicazione) {
		HomeService.salvaDetermina(idComunicazione);		
	}
	
	@Override
	public List<VersamentiPrVO> listaAccertamentiAnagrafica(Long idAnag, String anno, String provincia) {
		return HomeService.ricercaAccertamentiPerAnagrafica(idAnag, anno, provincia);
	}

	@Override
	public void updateAccertamento(List<ConfermaVersamentoRequest> listaAccertamentiDaSalvare, String utente) {
		HomeService.updateAccertamento(listaAccertamentiDaSalvare,utente);
	}
	
	@Override
	public void updateAllarmeAccertamento(UpdateAllarmeAccertamentoRequest updateAllarmeAccertamentoRequest, String utente) {
		HomeService.updateAllarmeAccertamento(updateAllarmeAccertamentoRequest,utente);
	}
	
	
	@Override 
	public OrdinativiIncassoVO conciliaPagamento(ConfermaPagamentoRequest confermaPagamentoRequest, String user) {
		return HomeService.conciliaPagameto(confermaPagamentoRequest, user);
	}

	@Override
	public OrdinativiIncassoVO eliminaConciliazione(ConfermaPagamentoRequest confermaPagamentoRequest) {
		return HomeService.eliminaConciliazione(confermaPagamentoRequest);
	}

	@Override
	public List<MessaggiVO> controlloImportiConsumi(Long idConsumi) {
		return HomeService.controlloImportiConsumi(idConsumi);
	}

	@Override 
	public ConsumiPrVO updateTotaleDichConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user) {
		return HomeService.updateTotaleDichConsumi(confermaConsumiRequest, user);
	} 


	@Override
	public byte[] getDocumentoMaster(String descComunicazione) throws FileNotFoundException, IOException {
		return HomeService.getDocumentoMaster(descComunicazione);
	}

	@Override
	public boolean deleteDocumento(Long idDocumento) {
		return HomeService.deleteDocumento(idDocumento);
	}	
	
	@Override
	public boolean salvaCompensazione(SalvaCompensazioneRequest salvaCompensazioneRequest, String user) {
		return HomeService.salvaCompensazione(salvaCompensazioneRequest, user);
	}
	
	@Override
	public List<StoricoAnagraficaSoggettoVO> ricercaListaStoricoSoggettoByIdAngaRif(Long idAnagRif){
		return HomeService.ricercaListaStoricoSoggettoByIdAngaRif(idAnagRif);		
	}

	@Override
	public List<SigasStoricoAnagraficaSoggettiCustom> ricercaListaStoricoSoggetti(
			RicercaStoricoSoggettiRequest ricercaStoricoSoggettiRequest) {
		return HomeService.ricercaListaStoricoSoggetti(ricercaStoricoSoggettiRequest);
	}

	@Override
	public List<ConsumiPrVO> ricercaSoggettoIncorporato(RicercaSoggettoIncorporatoRequest ricercaSoggettoIncorporatoRequest) {
		return HomeService.ricercaSoggettoIncorporato(ricercaSoggettoIncorporatoRequest);
	}
}
