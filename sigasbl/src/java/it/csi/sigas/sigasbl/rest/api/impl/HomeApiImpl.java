/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.rest.api.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.Esito;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.dispatcher.IExportDispatcher;
import it.csi.sigas.sigasbl.dispatcher.IHomeDispatcher;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.ResponseVO;
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
import it.csi.sigas.sigasbl.request.home.DownloadAccertamentiReport;
import it.csi.sigas.sigasbl.request.home.DownloadDocumentazioneReport;
import it.csi.sigas.sigasbl.request.home.DownloadReport;
import it.csi.sigas.sigasbl.request.home.DownloadSoggettiReport;
import it.csi.sigas.sigasbl.request.home.FusioneSoggettoRequest;
import it.csi.sigas.sigasbl.request.home.RicercaAnaComunicazioniRequest;
import it.csi.sigas.sigasbl.request.home.RicercaConsumiRequest;
import it.csi.sigas.sigasbl.request.home.SalvaRimborsoRequest;
import it.csi.sigas.sigasbl.request.home.ValidazioneRequest;
import it.csi.sigas.sigasbl.rest.api.IHomeApi;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.util.ActaUtils;
import it.csi.sigas.sigasbl.util.SpringSupportedResource;
import it.csi.sigas.sigasbl.util.UploadUtils;

@Service
public class HomeApiImpl extends SpringSupportedResource implements IHomeApi {
    
    @Autowired
    private IHomeDispatcher homeDispatcher;
    
    @Autowired
	private IExportDispatcher exportDispatcher;

    public HomeApiImpl() {
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
    public Response ricercaAnnualita() {
    	
    	logger.info("START: ricercaAnnualita");
   	
    	List<String> ricercaAnnualitaList = this.homeDispatcher.ricercaAnnualita();
		
    	logger.info("END: ricercaAnnualita");
        return Response.ok(new ResponseVO<List<String>>(Esito.SUCCESS, ricercaAnnualitaList)).build();
    }
    
    @Override
    public Response ricercaConsumi(RicercaConsumiRequest ricercaConsumiRequest) {
    	
    	logger.info("START: ricercaConsumi");
   	
    	List<SoggettiVO> ricercaConsumiList = this.homeDispatcher.ricercaConsumi(ricercaConsumiRequest);
		
    	logger.info("END: ricercaConsumi");
        return Response.ok(new ResponseVO<List<SoggettiVO>>(Esito.SUCCESS, ricercaConsumiList)).build();
    }
    
    
    @Override
	public Response ricercaSoggettoByID(Long id) {
		logger.info("START: ricercaSoggettoByID : "+ id);

		AnagraficaSoggettoVO soggettoVO = homeDispatcher.ricercaSoggettoByID(id /*, utente.getIdentita().getCodFiscale()*/);

		logger.info("END: ricercaSoggettoByID");
		return Response.ok(new ResponseVO<AnagraficaSoggettoVO>(Esito.SUCCESS, soggettoVO)).build();
	}
    
    @Override
	public Response ricercaSoggetti() {
		logger.info("START: ricercaSoggetti ");

		List<AnagraficaSoggettoVO> soggettiVO = homeDispatcher.ricercaSoggetti();
		logger.info("END: ricercaSoggetti");
		return Response.ok(new ResponseVO<List<AnagraficaSoggettoVO>>(Esito.SUCCESS, soggettiVO)).build();
	}
    
	@Override
	public Response ricercaConsumiPerProvince(Long id, String anno) {
		logger.info("START: ricercaConsumiPerProvince : "+ id + " anno : " +anno );

		List<ConsumiPrVO> ricercaConsumiPerProvinceList = this.homeDispatcher.ricercaConsumiPerProvince(id,anno);
    	logger.info("END: ricercaConsumiPerProvince");
		return Response.ok(new ResponseVO<List<ConsumiPrVO>>(Esito.SUCCESS, ricercaConsumiPerProvinceList)).build();
	}
	
	@Override
	public Response insertSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest) {
		logger.info("START: insertSoggetto");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		
		this.homeDispatcher.insertSoggetto(confermaSoggettoRequest, utente.getIdentita().getCodFiscale());
		
    	logger.info("END: insertSoggetto");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}
	

	@Override
	public Response updateSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest) {
		logger.info("START: updateSoggetto");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
	   	
		AnagraficaSoggettoVO soggetto = this.homeDispatcher.updateSoggetto(confermaSoggettoRequest, utente.getIdentita().getCodFiscale());
		
    	logger.info("END: updateSoggetto");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}
	
	@Override
	public Response fusioneSoggetto(FusioneSoggettoRequest fusioneSoggettoRequest) {
		logger.info("START: fusioneSoggetto");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
	   	
		AnagraficaSoggettoVO soggetto = this.homeDispatcher.fusioneSoggetto(fusioneSoggettoRequest, utente.getIdentita().getCodFiscale());
		
    	logger.info("END: fusioneSoggetto");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

	@Override
	public Response salvaConsumiPerProvince(DownloadReport downloadReport) {
		logger.info("START: salvaConsumiPerProvince : " + " downloadReport : " + downloadReport);
		
		byte[] file = this.exportDispatcher.salvaConsumiPerProvince(downloadReport);
    	logger.info("END: salvaConsumiPerProvince");
		
    	return Response.ok().entity(file).build();
	}
	
	@Override
	public Response salvaSoggetto(DownloadReport downloadReport) {
		logger.info("START: salvaSoggetto : " + " downloadReport : " + downloadReport);
		
		byte[] file = this.exportDispatcher.salvaSoggetto(downloadReport);
    	logger.info("END: salvaSoggetto");
		
    	return Response.ok().entity(file).build();
	}

	@Override
	public Response salvaElencoSoggetti(DownloadSoggettiReport downloadSoggettiReport) {
		
		byte[] file = this.exportDispatcher.salvaElencoSoggetti(downloadSoggettiReport);
    	logger.info("END: downloadSoggettiReport");
		
    	return Response.ok().entity(file).build();
	}
	
	@Override
	public Response associateSoggetto(AssociaSoggettoRequest associaSoggettoRequest) {
		logger.info("START: associateSoggetto");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		
		AnagraficaSoggettoVO soggetto = this.homeDispatcher.associateSoggetto(associaSoggettoRequest, utente.getIdentita().getCodFiscale());
		
    	logger.info("END: associateSoggetto");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}

	@Override
	public Response ricercaListaSoggetti(RicercaConsumiRequest ricercaConsumiRequest) {
		logger.info("START: ricercaListaSoggetti");
	   	
		List<AnagraficaSoggettoVO> listaSoggetti = this.homeDispatcher.ricercaListaSoggetti(ricercaConsumiRequest);
		
    	logger.info("END: ricercaListaSoggetti");
		return Response.ok(new ResponseVO<List<AnagraficaSoggettoVO>>(Esito.SUCCESS, listaSoggetti)).build();

	}
	
	@Override
	public Response ricercaListaNuoviSoggetti() {
		logger.info("START: ricercaListaNuoviSoggetti");
	   	
		List<AnagraficaSoggettoVO> listaSoggetti = this.homeDispatcher.ricercaListaNuoviSoggetti();
		
    	logger.info("END: ricercaListaNuoviSoggetti");
		return Response.ok(new ResponseVO<List<AnagraficaSoggettoVO>>(Esito.SUCCESS, listaSoggetti)).build();

	}

	@Override
	public Response getNumberOfSoggetti() {
		logger.info("START: getNumberOfSoggetti");
	   	
		Long numeroSoggetti = this.homeDispatcher.getNumberOfSoggetti();
		
    	logger.info("END: getNumberOfSoggetti");
		return Response.ok(new ResponseVO<Long>(Esito.SUCCESS, numeroSoggetti+1)).build();

	}

	@Override
	public Response ricercaScartiByIdConsumi(Long idConsumi) {
		logger.info("START: ricercaScartiByIdConsumi : "+ idConsumi);

		List<ScartoVO> listaScarti = this.homeDispatcher.ricercaScartiByIdConsumi(idConsumi);

		logger.info("END: ricercaScartiByIdConsumi");
		return Response.ok(new ResponseVO<List<ScartoVO>>(Esito.SUCCESS, listaScarti)).build();
	}
	
	@Override
	public Response getAllAliquote() {
		logger.info("START: getAllAliquote");

		List<AliquoteVO> aliquote = this.homeDispatcher.getAllAliquote();

		logger.info("END: getAllAliquote");
		return Response.ok(new ResponseVO<List<AliquoteVO>>(Esito.SUCCESS, aliquote)).build();
	}
	
	@Override
	public Response updateConsumi(ConfermaConsumiRequest confermaConsumiRequest) {
		logger.info("START: updateConsumi");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
	   	
		ConsumiPrVO consumi = this.homeDispatcher.updateConsumi(confermaConsumiRequest, utente.getIdentita().getCodFiscale());
		
    	logger.info("END: updateConsumi");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}
	
	@Override
	public Response updateCompensazioneConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user) {
		logger.info("START: updateCompensazioneConsumi");
	   	
		ConsumiPrVO consumi = this.homeDispatcher.updateCompensazioneConsumi(confermaConsumiRequest, user);
		
    	logger.info("END: updateCompensazioneConsumi");
        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}
	
	@Override
	public Response storicoConsumi(Long idConsumi) {
		logger.info("START: storicoConsumi : "+ idConsumi);

		List<StoricoConsumiVO> listaConsumi = this.homeDispatcher.storicoConsumi(idConsumi);

		logger.info("END: storicoConsumi");
		return Response.ok(new ResponseVO<List<StoricoConsumiVO>>(Esito.SUCCESS, listaConsumi)).build();
	}
	
	@Override
	public Response ripristinaModificaConsumi(Long idConsumi, Long idStoricoConsumi) {
		logger.info("START: ripristinaModificaConsumi : "+ idConsumi);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		
		ConsumiPrVO consumo  = this.homeDispatcher.ripristinaModificaConsumi(idConsumi, idStoricoConsumi, utente.getIdentita().getCodFiscale());

		logger.info("END: ripristinaModificaConsumi");
		return Response.ok(new ResponseVO<ConsumiPrVO>(Esito.SUCCESS, consumo)).build();
	}
	
	@Override
	public Response validaSoggetto(ValidazioneRequest validazioneRequest) {
		logger.info("START: validaSoggetto : "+ validazioneRequest.getIdAnag() + " - " + validazioneRequest.getIdAnag() + " - " + validazioneRequest.isValidato());

		this.homeDispatcher.validaSoggetto(validazioneRequest.getIdAnag(), validazioneRequest.getAnno(), validazioneRequest.isValidato());

		logger.info("END: validaSoggetto");
		return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
	}
	
    @Override
    public Response ricercaDocumentiByAnnoAndTipologia(RicercaAnaComunicazioniRequest ricercaAnaComunicazioniRequest) {
    	
    	logger.info("START: RicercaAnaComunicazioniRequest");
   	
    	List<AnaComunicazioniVO> ricercaAnaComunicazioniList = this.homeDispatcher.ricercaDocumentiByAnnoAndTipologia(ricercaAnaComunicazioniRequest);
		
    	logger.info("END: RicercaAnaComunicazioniRequest");
        return Response.ok(new ResponseVO<List<AnaComunicazioniVO>>(Esito.SUCCESS, ricercaAnaComunicazioniList)).build();
    }

	@Override
	public Response ricercaDocumentiByIdAnag(Long idAnag) {
		logger.info("START: ricercaDocumentiByIdAnag : "+ idAnag);

		List<AnaComunicazioniVO> listaAnaComunicazioni = this.homeDispatcher.ricercaDocumentiByIdAnag(idAnag);

		logger.info("END: ricercaDocumentiByIdAnag");
		return Response.ok(new ResponseVO<List<AnaComunicazioniVO>>(Esito.SUCCESS, listaAnaComunicazioni)).build();
	}
	
	@Override
	public Response allarmeDocumento(AllarmeDocumentoRequest allarmeDocumentoRequest) {
		logger.info("START: allarmeDocumento : "+ allarmeDocumentoRequest.getIdAnag() + " - " + allarmeDocumentoRequest.getIdComunicazione() + " - " + allarmeDocumentoRequest.isAllarmeOn());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;

		AllarmiSoggettoVO allarmiSoggettoVO = this.homeDispatcher.allarmeDocumento(allarmeDocumentoRequest, utente.getUsername());

		logger.info("END: allarmeDocumento");
		return Response.ok(new ResponseVO<AllarmiSoggettoVO>(Esito.SUCCESS, allarmiSoggettoVO)).build();
	}
	
	@Override
	public Response salvaAllegato(MultipartFormDataInput input) throws IOException {
		Map<String, List<InputPart>> map = input.getFormDataMap();

		String UID="";
		String fileName="";
		String id="";
		byte[] content = null;
		List<InputPart> data= map.get("id");
		List<InputPart> file=map.get("data");
		String annualita = input.getFormDataPart("annualita", String.class, null);
		String nprotocollo = input.getFormDataPart("nprotocollo", String.class, null);
		String dataFileName = input.getFormDataPart("fileName", String.class, null);
		
		if (data != null && !data.isEmpty()) {
			try {
				for (InputPart inputPart : data) {
					InputStream inputStream = inputPart.getBody(InputStream.class, null);
					id = IOUtils.toString(inputStream);
				}
			} catch (IOException e) {
				throw new RuntimeException("Errore estrazione dati input", e);
			}
		}
		
		if(null == dataFileName) {
			if(StringUtils.isNotEmpty(nprotocollo)) {	 		// 1° caso
				ActaUtils actaUtils = new ActaUtils();
				String esito = actaUtils.saveData(annualita, nprotocollo);
				if(null != esito) {
					UID=actaUtils.getUuid();
					fileName=actaUtils.getFilename();
				} else {
					throw new BusinessException("Si e' presentato un problema nel salavtaggio del documento.");
				}
			} else {
				throw new BusinessException("Il form non e' stato compilato correttamente, mancano troppi parametri");
			}
		} else {								// 2° e 3° caso,	la differenza verrà gestita nel serviceImpl
			if(StringUtils.isNotEmpty(nprotocollo)) {	 
				ActaUtils actaUtils = new ActaUtils();
				actaUtils.checkProtocollo(annualita, nprotocollo);
			}
			// Recupero dal multipart i file caricati
			if (file != null && !file.isEmpty()) {
				try {
					for (InputPart inputPart : file) {
						InputStream inputStream = inputPart.getBody(InputStream.class, null);
						fileName = UploadUtils.getFileName(inputPart);
						content = IOUtils.toByteArray(inputStream);		
					}
				} catch (IOException e) {
					throw new RuntimeException("Errore estrazione file in upload", e);
				}
			}
			
			UploadFile uploadFile = new UploadFile();
			String tipologiaComunicazione = input.getFormDataPart("tipologiaComunicazione", String.class, null);
			String codiceAzienda = input.getFormDataPart("codiceAzienda", String.class, null);
			UID=uploadFile.upload(fileName,content, annualita, tipologiaComunicazione, codiceAzienda);
			//La differenza tra 2° e 3° caso dipende da 'nprotocollo' è null o meno
		}
		if (StringUtils.isEmpty(UID)) {
			throw new BusinessException("Esiste gia' un file presente nel sistema", ErrorCodes.BUSSINESS_EXCEPTION);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		
		AnaComunicazioniVO anaComunicazioniVO = this.homeDispatcher.addDocument(UID, fileName, Long.parseLong(id), input, utente.getIdentita().getCodFiscale());
		return Response.ok(new ResponseVO<AnaComunicazioniVO>(Esito.SUCCESS, anaComunicazioniVO)).build();
	}
	
	@Override
	public Response aggiornaAllegato(MultipartFormDataInput input) throws IOException {
		Map<String, List<InputPart>> map = input.getFormDataMap();

		String UID= input.getFormDataPart("rifArchivio", String.class, null);
		String fileName="";
		String id= input.getFormDataPart("id", String.class, null);;
		byte[] content = null;
		List<InputPart> data= map.get("id");
		List<InputPart> file=map.get("data");
		String annualita = input.getFormDataPart("annualita", String.class, null);
		String nprotocollo = input.getFormDataPart("nprotocollo", String.class, null);
		String dataFileName = input.getFormDataPart("fileName", String.class, null);
		
		if(null == dataFileName) {
			if(StringUtils.isNotEmpty(nprotocollo)) {	 		// 1° caso
				ActaUtils actaUtils = new ActaUtils();
				String esito = actaUtils.saveData(annualita, nprotocollo);
				if(null != esito) {
					UID=actaUtils.getUuid();
					fileName=actaUtils.getFilename();
				} else {
					throw new BusinessException("Salvataggio dei dati 'uid', 'filename' o 'protocollo' errato");
				}
			} else {
				if (StringUtils.isEmpty(UID)) {
					throw new BusinessException("Il form non e' stato compilato correttamente, mancano troppi parametri");
				}
			}
		} else {								// 2° e 3° caso,	la differenza verrà gestita nel serviceImpl
			// Recupero dal multipart i file caricati
			if (file != null && !file.isEmpty()) {
				try {
					for (InputPart inputPart : file) {
						InputStream inputStream = inputPart.getBody(InputStream.class, null);
						fileName = UploadUtils.getFileName(inputPart);
						content = IOUtils.toByteArray(inputStream);		
					}
				} catch (IOException e) {
					throw new RuntimeException("Errore estrazione file in upload", e);
				}
			}
			if (data != null && !data.isEmpty()) {
				try {
					for (InputPart inputPart : data) {
						InputStream inputStream = inputPart.getBody(InputStream.class, null);
						id = IOUtils.toString(inputStream);
					}
				} catch (IOException e) {
					throw new RuntimeException("Errore estrazione file in upload", e);
				}
			}
			UploadFile uploadFile = new UploadFile();
			String tipologiaComunicazione = input.getFormDataPart("tipologiaComunicazione", String.class, null);
			String codiceAzienda = input.getFormDataPart("codiceAzienda", String.class, null);
			UID=uploadFile.upload(fileName,content, annualita, tipologiaComunicazione, codiceAzienda);
			//La differenza tra 2° e 3° caso dipende da 'nprotocollo' è null o meno
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		
		AnaComunicazioniVO anaComunicazioniVO = this.homeDispatcher.updateDocumento(UID, fileName, Long.parseLong(id), input, utente.getIdentita().getCodFiscale());
		return Response.ok(new ResponseVO<AnaComunicazioniVO>(Esito.SUCCESS, anaComunicazioniVO)).build();
	}
	
	@Override
	public Response aggiornaTestataAllegato(AnaComunicazioniVO anaComunicazione) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		AnaComunicazioniVO anaComunicazioniVO = this.homeDispatcher.updateTestataDocumento(anaComunicazione, utente.getUsername());
		return Response.ok(new ResponseVO<AnaComunicazioniVO>(Esito.SUCCESS, anaComunicazioniVO)).build();
	}

	@Override
	public Response stampaDocumento(String descComunicazione) throws FileNotFoundException, IOException {
		byte[] file = this.homeDispatcher.getDocumento(descComunicazione);
		return Response.ok().entity(file).build();
	}

	@Override
	public Response getAllTipoComunicazioni() {
		logger.info("START: getAllTipoComunicazioni");

		List<TipoComunicazioniVO> tipoComunicazioni = this.homeDispatcher.getAllTipoComunicazioni();

		logger.info("END: getAllTipoComunicazioni");
		return Response.ok(new ResponseVO<List<TipoComunicazioniVO>>(Esito.SUCCESS, tipoComunicazioni)).build();
	}
	
	@Override
	public Response ricercaTipoComunicazioniByIdTipoComunicazione(Long idTipoComunicazione) {
		logger.info("START: ricercaTipoComunicazioniByIdTipoComunicazione : "+ idTipoComunicazione);

		TipoComunicazioniVO tipoComunicazioni = this.homeDispatcher.ricercaTipoComunicazioniByIdTipoComunicazione(idTipoComunicazione);

		logger.info("END: ricercaTipoComunicazioniByIdTipoComunicazione");
		return Response.ok(new ResponseVO<TipoComunicazioniVO>(Esito.SUCCESS, tipoComunicazioni)).build();
	}
	
	
	
	@Override
	public Response ricercaVersamentiPerProvince(Long id, String anno) {
		logger.info("START: ricercaVersamentiPerProvince : "+ id + " anno : " +anno );

		List<VersamentiPrVO> ricercaVersamentiPerProvinceList = this.homeDispatcher.ricercaVersamentiPerProvince(id,anno);
    	logger.info("END: ricercaVersamentiPerProvince");
		return Response.ok(new ResponseVO<List<VersamentiPrVO>>(Esito.SUCCESS, ricercaVersamentiPerProvinceList)).build();
	}
	
	
	@Override
	public Response ricercaAllarmiByIdDocumentoAndCodiceAzienda(Long idDocumento, String codiceAzienda) {
		logger.info("START: ricercaAllarmiByIdDocumentoAndCodiceAzienda");
	   	
    	List<AllarmiSoggettoVO> ricercaAllarmiList = this.homeDispatcher.ricercaAllarmiByIdDocumentoAndCodiceAzienda(idDocumento,codiceAzienda);
		
    	logger.info("END: ricercaAllarmiByIdDocumentoAndCodiceAzienda");
        return Response.ok(new ResponseVO<List<AllarmiSoggettoVO>>(Esito.SUCCESS, ricercaAllarmiList)).build();
	}
	
	@Override
	public Response ricercaListaRimborsi(Long idAnag) {
		logger.info("START: ricercaListaRimborsi");
	   	
    	List<RimborsoVO> ricercaMeseVersamentiList = this.homeDispatcher.ricercaListaRimborsi(idAnag);
		
    	logger.info("END: ricercaListaRimborsi");
        return Response.ok(new ResponseVO<List<RimborsoVO>>(Esito.SUCCESS, ricercaMeseVersamentiList)).build();
	}
	
	@Override
	public Response ricercaConsumiPerProvinceAndAnnualita(Long idAnag, String anno, String prov) {
		logger.info("START: ricercaConsumiPerProvinceAndAnnualita : "+ idAnag + " anno : " +anno + " provincia : " +prov );

		ConsumiPrVO ricercaConsumiPerProvinceList = this.homeDispatcher.ricercaConsumiPerProvinceAndAnnualita(idAnag,anno,prov);
    	logger.info("END: ricercaConsumiPerProvinceAndAnnualita");
		return Response.ok(new ResponseVO<ConsumiPrVO>(Esito.SUCCESS, ricercaConsumiPerProvinceList)).build();
	}
	
	@Override
	public Response salvaRimborso(SalvaRimborsoRequest salvaRimborsoRequest) {
		logger.info("START: updateVersamento");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		salvaRimborsoRequest.getRimborso().setModDate(new Date());
		salvaRimborsoRequest.getRimborso().setModUser(utente.getIdentita().getCodFiscale());
		
		RimborsoVO rimborsoVO = this.homeDispatcher.salvaRimborso(salvaRimborsoRequest);
		
		logger.info("END: updateVersamento");
        return Response.ok(new ResponseVO<RimborsoVO>(Esito.SUCCESS, rimborsoVO)).build();
	}

	@Override
	public Response salvaDetermina(DownloadReport downloadReport) {
		logger.info("START: salvaDetermina");
		
		byte[] file = this.exportDispatcher.salvaDetermina(downloadReport);
		
		this.homeDispatcher.salvaDetermina(downloadReport.getIdComunicazioni());
		
    	logger.info("END: salvaDetermina");
		
    	return Response.ok().entity(file).build();
	}
	
	@Override
	public Response salvaElencoDocumenti(DownloadDocumentazioneReport downloadDocumentazioneReport) {
		logger.info("START: salvaElencoDocumenti");
		
		byte[] file = this.exportDispatcher.salvaElencoDocumenti(downloadDocumentazioneReport);
    	logger.info("END: salvaElencoDocumenti");
		
    	return Response.ok().entity(file).build();
	}

	@Override
	public Response salvaElencoAccertamenti(DownloadAccertamentiReport downloadAccertamentiReport) {
		logger.info("START: salvaElencoAccertamenti");
		
		byte[] file = this.exportDispatcher.salvaElencoAccertamenti(downloadAccertamentiReport);
    	logger.info("END: salvaElencoAccertamenti");
		
    	return Response.ok().entity(file).build();
	}

	
	@Override
	public Response getListaAccertamentiAnagrafica(Long idAnag, String anno, String provincia) {
		List<VersamentiPrVO> listaAccertamenti =  this.homeDispatcher.listaAccertamentiAnagrafica(idAnag, anno, provincia);
		return Response.ok(new ResponseVO<List<VersamentiPrVO>>(Esito.SUCCESS, listaAccertamenti)).build();
	}

	@Override
	public Response updateAccertamenti(List<ConfermaVersamentoRequest> accertamentiDaSalvare) {
			logger.info("START: updateVersamento");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Object principal = auth.getPrincipal();
			UserDetails utente = (UserDetails) principal;
			this.homeDispatcher.updateAccertamento(accertamentiDaSalvare, utente.getUsername());
			logger.info("END: updateVersamento");
	        return Response.ok(new ResponseVO<String>(Esito.SUCCESS, Constants.MESSAGE_SUCCESS)).build();
		
	}
}
