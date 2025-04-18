/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.sigas.sigasbl.common.exception.BusinessException;
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

public interface IHomeService {

	List<String> ricercaAnnualita();

	List<SoggettiVO> ricercaConsumi(RicercaConsumiRequest ricercaConsumiRequest);
	
	AnagraficaSoggettoVO ricercaSoggettoByID(Long id);
	
	AnagraficaSoggettoVO ricercaSoggettoByCode(String codice);	

	List<ConsumiPrVO> ricercaConsumiPerProvince(Long id, String anno);
	
	void insertSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest, String user);
	
	AnagraficaSoggettoVO updateSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest, String user) throws BusinessException;

	AnagraficaSoggettoVO associateSoggetto(AssociaSoggettoRequest associaSoggettoRequest, String user);

	List<VersamentiPrVO> ricercaVersamentiPerProvince(Long id, String anno);
	
	List<AnagraficaSoggettoVO> ricercaListaSoggetti(RicercaConsumiRequest ricercaConsumiRequest);	
	
	List<AnagraficaSoggettoVO> ricercaSoggetti();

	List<ScartoVO> ricercaScartiByIdConsumi(Long idConsumi);
		
	ConsumiPrVO updateConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user);
	
	ConsumiPrVO updateCompensazioneConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user);
	
	AnagraficaSoggettoVO fusioneSoggetto(FusioneSoggettoRequest confermaSoggettoRequest, String user);
	
	void cancellaFusioneSoggetto(Long idAnagraficaIncorporante, String user);

	List<AnagraficaSoggettoVO> ricercaListaNuoviSoggetti();

	Long getNumberOfSoggetti();

	List<StoricoConsumiVO> storicoConsumi(Long idConsumi);

	ConsumiPrVO ripristinaModificaConsumi(Long idConsumi, Long idStoricoConsumi, String user);

	void validaSoggetto(Long idAnag, String anno, boolean validato);
	
	List<AnaComunicazioniVO> ricercaDocumentiByAnnoAndTipologia(RicercaAnaComunicazioniRequest ricercaAnaComunicazioniRequest);

	List<AnaComunicazioniVO> ricercaDocumentiByIdAnag(Long idAnag);
	
	AllarmiSoggettoVO allarmeDocumento(AllarmeDocumentoRequest allarmeDocumentoRequest, String utente);

	AnaComunicazioniVO addDocumento(Long id, String user);

	AnaComunicazioniVO addDocument(String uid, String nomeFile, Long idAnag, MultipartFormDataInput input, String user);

	AnaComunicazioniVO updateDocumento(String uid, String nomeFile, Long idAnag, MultipartFormDataInput input, String user);
	
	AnaComunicazioniVO updateTestataDocumento(AnaComunicazioniVO anaComunicazione, String user);

	byte[] getDocumento(String descComunicazione) throws FileNotFoundException, IOException;
	
	byte[] getStampaAllegato(String descComunicazione, String descAllegato) throws FileNotFoundException, IOException;

	List<TipoComunicazioniVO> getAllTipoComunicazioni();

	TipoComunicazioniVO ricercaTipoComunicazioniByIdTipoComunicazione(Long idTipoComunicazione);

	List<AllarmiSoggettoVO> ricercaAllarmiByIdDocumentoAndCodiceAzienda(Long idDocumento, String codiceAzienda);

	List<RimborsoVO> ricercaListaRimborsi(long idAnag);

	ConsumiPrVO ricercaConsumiPerProvinceAndAnnualita(Long idAnag, String anno, String prov);

	RimborsoVO salvaRimborso(SalvaRimborsoRequest salvaRimborsoRequest);


	void salvaDetermina(Long idComunicazione);
	
	List<VersamentiPrVO> ricercaAccertamentiPerAnagrafica(Long id, String anno, String provincia);
	
	void updateAccertamento(List<ConfermaVersamentoRequest> listaAccertamenti, String utente);
	
	void updateAllarmeAccertamento(UpdateAllarmeAccertamentoRequest updateAllarmeAccertamentoRequest, String utente);

	RimborsoVO ricercaRimborso(long idRimborso);

	List<AliquoteVO> getAllAliquote(Integer annoDichiarazione);
	
	List<String> ricercaAnnualitaPagamenti();
	
	List<OrdinativiIncassoVO> ricercaOrdinativi(RicercaOrdinativiRequest ricercaOrdinativiRequest);
	
	OrdinativiIncassoVO conciliaPagameto(ConfermaPagamentoRequest confermaPagamentoRequest, String user);

	OrdinativiIncassoVO eliminaConciliazione(ConfermaPagamentoRequest confermaPagamentoRequest);
	 
	List<MessaggiVO> controlloImportiConsumi(Long idConsumi);
	
	ConsumiPrVO updateTotaleDichConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user);
	
	byte[] getDocumentoMaster(String descComunicazione) throws IOException;

	boolean deleteDocumento(Long idDocumento);
	
	boolean salvaCompensazione(SalvaCompensazioneRequest salvaCompensazioneRequest, String user);
	
	List<StoricoAnagraficaSoggettoVO> ricercaListaStoricoSoggettoByIdAngaRif(Long idAnagRif);
	
	List<SigasStoricoAnagraficaSoggettiCustom> ricercaListaStoricoSoggetti(RicercaStoricoSoggettiRequest ricercaStoricoSoggettiRequest);
	
	List<ConsumiPrVO> ricercaSoggettoIncorporato(RicercaSoggettoIncorporatoRequest ricercaSoggettoIncorporatoRequest);

}
