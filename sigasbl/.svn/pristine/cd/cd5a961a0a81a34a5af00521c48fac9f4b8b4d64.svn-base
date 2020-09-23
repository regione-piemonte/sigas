/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.AnaComunicazioniVO;
import it.csi.sigas.sigasbl.model.vo.home.ConsumiPrVO;
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
import it.csi.sigas.sigasbl.request.home.ConfermaSoggettoRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoRequest;
import it.csi.sigas.sigasbl.request.home.FusioneSoggettoRequest;
import it.csi.sigas.sigasbl.request.home.RicercaAnaComunicazioniRequest;
import it.csi.sigas.sigasbl.request.home.RicercaConsumiRequest;
import it.csi.sigas.sigasbl.request.home.SalvaRimborsoRequest;

public interface IHomeService {

	List<String> ricercaAnnualita();

	List<SoggettiVO> ricercaConsumi(RicercaConsumiRequest ricercaConsumiRequest);
	
	AnagraficaSoggettoVO ricercaSoggettoByID(Long id);

	List<ConsumiPrVO> ricercaConsumiPerProvince(Long id, String anno);
	
	void insertSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest, String user);
	
	AnagraficaSoggettoVO updateSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest, String user);

	AnagraficaSoggettoVO associateSoggetto(AssociaSoggettoRequest associaSoggettoRequest, String user);

	List<VersamentiPrVO> ricercaVersamentiPerProvince(Long id, String anno);
	
	List<AnagraficaSoggettoVO> ricercaListaSoggetti(RicercaConsumiRequest ricercaConsumiRequest);
	
	List<AnagraficaSoggettoVO> ricercaSoggetti();

	List<ScartoVO> ricercaScartiByIdConsumi(Long idConsumi);
	
	List<AliquoteVO> getAllAliquote();
	
	ConsumiPrVO updateConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user);
	
	ConsumiPrVO updateCompensazioneConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user);
	
	AnagraficaSoggettoVO fusioneSoggetto(FusioneSoggettoRequest confermaSoggettoRequest, String user);

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

	List<TipoComunicazioniVO> getAllTipoComunicazioni();

	TipoComunicazioniVO ricercaTipoComunicazioniByIdTipoComunicazione(Long idTipoComunicazione);

	List<AllarmiSoggettoVO> ricercaAllarmiByIdDocumentoAndCodiceAzienda(Long idDocumento, String codiceAzienda);

	List<RimborsoVO> ricercaListaRimborsi(long idAnag);

	ConsumiPrVO ricercaConsumiPerProvinceAndAnnualita(Long idAnag, String anno, String prov);

	RimborsoVO salvaRimborso(SalvaRimborsoRequest salvaRimborsoRequest);


	void salvaDetermina(Long idComunicazione);
	
	List<VersamentiPrVO> ricercaAccertamentiPerAnagrafica(Long id, String anno, String provincia);
	
	void updateAccertamento(List<ConfermaVersamentoRequest> listaAccertamenti, String utente);

	RimborsoVO ricercaRimborso(long idRimborso);
}
