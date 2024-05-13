/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.dispatcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
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
import it.csi.sigas.sigasbl.request.home.SalvaCompensazioneRequest;
import it.csi.sigas.sigasbl.request.home.SalvaRimborsoRequest;
import it.csi.sigas.sigasbl.request.home.UpdateAllarmeAccertamentoRequest;


public interface IHomeDispatcher {

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<String> ricercaAnnualita();
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<String> ricercaAnnualitaPagamenti();
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<SoggettiVO> ricercaConsumi(RicercaConsumiRequest ricercaConsumiRequest);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<OrdinativiIncassoVO> ricercaOrdinativi(RicercaOrdinativiRequest ricercaOrdinativiRequest);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	AnagraficaSoggettoVO ricercaSoggettoByID(Long id);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AnagraficaSoggettoVO> ricercaSoggetti();

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<ConsumiPrVO> ricercaConsumiPerProvince(Long id, String anno);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<VersamentiPrVO> ricercaVersamentiPerProvince(Long id, String anno);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	void insertSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest, String user);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	AnagraficaSoggettoVO updateSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest, String user) throws BusinessException;
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	AnagraficaSoggettoVO fusioneSoggetto(FusioneSoggettoRequest fusioneSoggettoRequest, String user);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	AnagraficaSoggettoVO associateSoggetto(AssociaSoggettoRequest associaSoggettoRequest, String user);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AnagraficaSoggettoVO> ricercaListaSoggetti(RicercaConsumiRequest ricercaConsumiRequest);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<ScartoVO> ricercaScartiByIdConsumi(Long idConsumi);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<MessaggiVO> controlloImportiConsumi(Long idConsumi);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AliquoteVO> getAllAliquote(Integer annoDichiarazione);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	ConsumiPrVO updateConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	ConsumiPrVO updateTotaleDichConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	ConsumiPrVO updateCompensazioneConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AnagraficaSoggettoVO> ricercaListaNuoviSoggetti();

	@PreAuthorize(value = AuthorizationRoles.HOME)
	Long getNumberOfSoggetti();

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<StoricoConsumiVO> storicoConsumi(Long idConsumi);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	ConsumiPrVO ripristinaModificaConsumi(Long idConsumi, Long idStoricoConsumi, String user);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	void validaSoggetto(Long idAnag, String anno, boolean validato);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AllarmiSoggettoVO> ricercaAllarmiByIdDocumentoAndCodiceAzienda(Long idDocumento, String codiceAzienda);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<RimborsoVO> ricercaListaRimborsi(long idAnag);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	ConsumiPrVO ricercaConsumiPerProvinceAndAnnualita(Long idAnag, String anno, String prov);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<AnaComunicazioniVO> ricercaDocumentiByIdAnag(Long idAnag);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<AnaComunicazioniVO> ricercaDocumentiByAnnoAndTipologia(RicercaAnaComunicazioniRequest ricercaAnaComunicazioniRequest);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	AllarmiSoggettoVO allarmeDocumento(AllarmeDocumentoRequest allarmeDocumentoRequest, String utente);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	public AnaComunicazioniVO addDocumento(Long id, String user);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public AnaComunicazioniVO addDocument(String uid, String nomeFile, Long idAnag, MultipartFormDataInput input, String user);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	AnaComunicazioniVO updateDocumento(String uid, String nomeFile, Long idAnag, MultipartFormDataInput input, String user);
	@PreAuthorize(value = AuthorizationRoles.HOME)
	AnaComunicazioniVO updateTestataDocumento(AnaComunicazioniVO anaComunicazione, String user);
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public byte[] getDocumento(String descComunicazione) throws FileNotFoundException, IOException;
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public byte[] getStampaAllegato(String descComunicazione, String descAllegato) throws FileNotFoundException, IOException;
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<TipoComunicazioniVO> getAllTipoComunicazioni();
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public TipoComunicazioniVO ricercaTipoComunicazioniByIdTipoComunicazione(Long idTipoComunicazione);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	RimborsoVO salvaRimborso(SalvaRimborsoRequest salvaRimborsoRequest);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	void salvaDetermina(Long idComunicazione);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	void updateAccertamento(List<ConfermaVersamentoRequest> confermaVersamentoRequest, String utente);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	void updateAllarmeAccertamento(UpdateAllarmeAccertamentoRequest updateAllarmeAccertamentoRequest, String utente);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	List<VersamentiPrVO> listaAccertamentiAnagrafica(Long idAnag, String anno, String provincia);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	OrdinativiIncassoVO conciliaPagamento(ConfermaPagamentoRequest confermaPagamentoRequest, String user);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	OrdinativiIncassoVO eliminaConciliazione(ConfermaPagamentoRequest confermaPagamentoRequest);

	@PreAuthorize(value = AuthorizationRoles.HOME)
	public byte[] getDocumentoMaster(String descComunicazione) throws FileNotFoundException, IOException;
	
    @PreAuthorize(value = AuthorizationRoles.HOME)
    boolean deleteDocumento(Long idDocumento);
    
    @PreAuthorize(value = AuthorizationRoles.HOME)
    public boolean salvaCompensazione(SalvaCompensazioneRequest salvaCompensazioneRequest, String user);

}
