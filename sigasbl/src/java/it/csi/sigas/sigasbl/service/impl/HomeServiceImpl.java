/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.StaleObjectStateException;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.StatoRimborso;
import it.csi.sigas.sigasbl.common.StatoValidazione;
import it.csi.sigas.sigasbl.common.StatusAllarme;
import it.csi.sigas.sigasbl.common.TipoAllarme;
import it.csi.sigas.sigasbl.common.TipoComunicazioni;
import it.csi.sigas.sigasbl.common.TipoVersamenti;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.integration.doqui.DoquiServiceFactory;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasAliquote;
import it.csi.sigas.sigasbl.model.entity.SigasAllarmi;
import it.csi.sigas.sigasbl.model.entity.SigasAnaComunicazioni;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasCMessaggi;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.entity.SigasDichCompensazioni;
import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;
import it.csi.sigas.sigasbl.model.entity.SigasDichScarti;
import it.csi.sigas.sigasbl.model.entity.SigasDichVersamenti;
import it.csi.sigas.sigasbl.model.entity.SigasImportUTF;
import it.csi.sigas.sigasbl.model.entity.SigasPagamenti;
import it.csi.sigas.sigasbl.model.entity.SigasPagamentiVersamenti;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.entity.SigasQuadroM;
import it.csi.sigas.sigasbl.model.entity.SigasRimborso;
import it.csi.sigas.sigasbl.model.entity.SigasTipoAllarmi;
import it.csi.sigas.sigasbl.model.entity.SigasTipoComunicazioni;
import it.csi.sigas.sigasbl.model.entity.SigasTipoVersamento;
import it.csi.sigas.sigasbl.model.entity.SigasValidazione;
import it.csi.sigas.sigasbl.model.entity.custom.SoggettoEntityCustom;
import it.csi.sigas.sigasbl.model.mapper.entity.AliquoteEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.AllarmiSoggettoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.AnaComunicazioniEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.AnagraficaSoggettiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichCompensazioniEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichConsumiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichScartiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichVersamentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.OrdinativoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.PagamentiVersamentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.RimborsoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.SoggettoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.StoricoConsumiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoComunicazioniEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAliquoteRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAllarmiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnaComunicazioniRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCMessaggiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichCompensazioniRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichConsumiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichScartiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichVersamentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasImportRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPagamentiCrudRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPagamentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPagamentiVersamentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasQuadroMRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasRimborsoRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoAllarmiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoComunicazioniRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoVersamentoRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasValidazioneRepository;
import it.csi.sigas.sigasbl.model.repositories.SoggettoRepository;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.AnaComunicazioniVO;
import it.csi.sigas.sigasbl.model.vo.home.CompensazionePrVO;
import it.csi.sigas.sigasbl.model.vo.home.ConsumiPrVO;
import it.csi.sigas.sigasbl.model.vo.home.MessaggiVO;
import it.csi.sigas.sigasbl.model.vo.home.NuovoAllacciamentoVO;
import it.csi.sigas.sigasbl.model.vo.home.OrdinativiIncassoVO;
import it.csi.sigas.sigasbl.model.vo.home.PagamentiVersamentiVO;
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
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.service.IHomeService;
import it.csi.sigas.sigasbl.util.ActaUtils;
import it.csi.sigas.sigasbl.util.CsiLogUtils;
import it.csi.sigas.sigasbl.validator.InputValidator;
import it.doqui.acta.acaris.relationshipsservice.AcarisException;
import it.doqui.index.ecmengine.client.webservices.dto.Node;
import it.doqui.index.ecmengine.client.webservices.dto.OperationContext;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Content;
import it.doqui.index.ecmengine.client.webservices.dto.engine.management.Property;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegate;
import it.doqui.index.ecmengine.client.webservices.engine.EcmEngineWebServiceDelegateServiceLocator;

@Service
public class HomeServiceImpl implements IHomeService {

	@Autowired
	private SigasImportRepository sigasImportRepository;
	
	@Autowired
	private SigasAllarmiRepository sigasAllarmiRepository;

	@Autowired
	private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;
	
	@Autowired
	private SigasDichConsumiRepository sigasDichConsumiRepository;
	
	@Autowired
	private SigasDichCompensazioniRepository sigasDichCompensazioniRepository;

	@Autowired
	private SigasDichScartiRepository sigasDichScartiRepository;

	@Autowired
	private SigasDichVersamentiRepository sigasDichVersamentiRepository;
	
	@Autowired
	private SigasTipoVersamentoRepository sigasTipoVersamentoRepository;
	
	@Autowired
	private SigasAnaComunicazioniRepository sigasAnaComunicazioniRepository;
	
	@Autowired
	private SigasAliquoteRepository sigasAliquoteRepository;

	@Autowired
	private SoggettoRepository soggettoRepository;
	
	@Autowired
	private SigasQuadroMRepository sigasQuadroMRepository;
	
	@Autowired
	private SigasRimborsoRepository sigasRimborsoRepository;
	
	@Autowired
	private SigasProvinciaRepository sigasProvinciaRepository;
	
	//Mappers
	@Autowired
	private AnagraficaSoggettiEntityMapper anagraficaSoggettiEntityMapper;
	
	@Autowired
	private DichConsumiEntityMapper dichConsumiEntityMapper;
	
	@Autowired
	private DichCompensazioniEntityMapper dichCompensazioniEntityMapper;
	
	@Autowired
	private DichVersamentiEntityMapper dichVersamentiEntityMapper;
	
	@Autowired
	private DichScartiEntityMapper dichScartiEntityMapper;

	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	
	@Autowired
	private OrdinativoEntityMapper ordinativoEntityMapper;
	
	@Autowired
	private StoricoConsumiEntityMapper storicoConsumiEntityMapper;
	
	@Autowired
	private SigasValidazioneRepository sigasValidazioneRepository;

	@Autowired
	private AliquoteEntityMapper aliquoteEntityMapper;
	
	@Autowired
	private AnaComunicazioniEntityMapper anaComunicazioniEntityMapper;

	@Autowired
	private SigasTipoComunicazioniRepository sigasTipoComunicazioniRepository;
	
	@Autowired
	private TipoComunicazioniEntityMapper tipoComunicazioniEntityMapper;
	
	@Autowired
	private SigasTipoAllarmiRepository sigasTipoAllarmeRepository;
	
	@Autowired
	private AllarmiSoggettoEntityMapper allarmiSoggettoEntityMapper;
	
	@Autowired
	private RimborsoEntityMapper rimborsoEntityMapper;
	
	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	@Autowired
	private SigasPagamentiRepository sigasPagamentiRepository;
	
	@Autowired
	private SigasPagamentiCrudRepository sigasPagamentiCrudRepository;
	
	@Autowired
	private PagamentiVersamentiEntityMapper pagamentiVersamentiEntityMapper;
	
	@Autowired
	private SigasCMessaggiRepository sigasCMessaggiRepository;

	@Autowired
	private SigasPagamentiVersamentiRepository sigasPagamentiVersamentiRepository;
	
	@Autowired
	private DoquiServiceFactory acarisServiceFactory;
	
	@Autowired
	@Qualifier("codiceFiscalePartitaIvaValidator")
	protected InputValidator codiceFiscalePartitaIvaValidator;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	    
    @Transactional
    @Override
    public List<String> ricercaAnnualita () {
    	
    	List<SigasImportUTF> sigasImportUTFList = sigasImportRepository.findImported();
    	List<String> annualitaList = new ArrayList<String>(); 
    	annualitaList.add("");
		for(SigasImportUTF sigasImportUTF : sigasImportUTFList) {
			annualitaList.add(sigasImportUTF.getAnnualita());
		}
    	
		return annualitaList;
    	
    }
    
    @Transactional
    @Override
    public List<String> ricercaAnnualitaPagamenti () {
    	
    	List<String> listaAnnualitaVersamenti = sigasPagamentiCrudRepository.findAnnualitaVersamenti();
    	List<String> annualitaList = new ArrayList<String>(); 
    	annualitaList.add("");
		for(String annoVersamento: listaAnnualitaVersamenti) {
			annualitaList.add(annoVersamento);
		}
    	
		return annualitaList;
    	
    }
    
    
    @Transactional
    @Override
	public List<SoggettiVO> ricercaConsumi(RicercaConsumiRequest ricercaConsumiRequest) {
    	
    	List<SoggettiVO> soggettiVOList = new ArrayList<SoggettiVO>();
    	List<SoggettoEntityCustom> soggettoEntityCustomList = null;
    	
		if (ricercaConsumiRequest != null && ricercaConsumiRequest.getAnno() != null
				&& (!ricercaConsumiRequest.getAnno().isEmpty()) && (!ricercaConsumiRequest.getValidato().equals("NUOVI"))) {

			logger.debug("Ricerca per soggetti con filtro " + ricercaConsumiRequest.getValidato());
			soggettoEntityCustomList = soggettoRepository.findListaSoggetti(ricercaConsumiRequest.getValidato(), ricercaConsumiRequest.getAnno() );
			logger.debug("Trovati " + soggettoEntityCustomList.size() + " soggetti");

		}
		//In questo caso vengono estratti esclusivamente i Nuovi soggetti senza 
		//annualita' dichiarazione
		if (ricercaConsumiRequest != null && (ricercaConsumiRequest.getAnno() == null
				|| ricercaConsumiRequest.getAnno().isEmpty())
				&& ricercaConsumiRequest.getValidato().equals("NUOVI")) {
			logger.debug("Ricerca per soggetti con filtro " + ricercaConsumiRequest.getValidato());
			soggettoEntityCustomList = estraiNuoviSoggetti();
		}
    	
    	soggettiVOList = soggettoEntityMapper.mapListEntityToListVO(soggettoEntityCustomList);
    	
    	logger.debug("I soggetti ritornati sono " + (soggettiVOList == null? 0:soggettiVOList.size()));
    	
		return soggettiVOList;
    	
    }
    
    
    
    @Transactional
    @Override
	public List<OrdinativiIncassoVO> ricercaOrdinativi(RicercaOrdinativiRequest ricercaOrdinativiRequest) {
    	
    	List<OrdinativiIncassoVO> ordinativoVOList = new ArrayList<OrdinativiIncassoVO>();
    	List<SigasPagamenti> sigasPagamentiList = null;    	
      	
    	SigasCParametro sigasCParametro = sigasCParametroRepository.findByDescParametro("filtroDescrizioneAzienda");
    	String[] filtroDescrizioneAzienda = sigasCParametro.getValoreString().split(",");
    	String descrizioneAzienda = "";
    	String[] descrizioneAziendaSplit = ricercaOrdinativiRequest.getAzienda()!=null ? ricercaOrdinativiRequest.getAzienda().split(" "):null;
    	boolean eliminaFiltro = false;
    	for (String elem : descrizioneAziendaSplit) {
	    	for (String filtro : filtroDescrizioneAzienda) {
	    		if(elem.equalsIgnoreCase(filtro.trim())) {
	    			eliminaFiltro = true;
	    		}
			}
	    	if(!eliminaFiltro) {
	    		descrizioneAzienda += elem + " ";
	    	}else {
	    		eliminaFiltro = false;
	    	}
    	}
    	
    	descrizioneAzienda = descrizioneAzienda.trim();
    	
    	if(ricercaOrdinativiRequest.getCodiceAzienda()!=null && !ricercaOrdinativiRequest.getCodiceAzienda().isEmpty()) {
    		descrizioneAzienda += " "+ricercaOrdinativiRequest.getCodiceAzienda();
    	}
    	
    	
    	
    	sigasPagamentiList = sigasPagamentiRepository.findSigasPagamentiBy(descrizioneAzienda.trim().split(" "), 
    																		ricercaOrdinativiRequest.getDataIncassoDa(), 
    																		ricercaOrdinativiRequest.getDataIncassoA(), 
    																		ricercaOrdinativiRequest.getConciliato()!=null ? ricercaOrdinativiRequest.getConciliato():null,
																			ricercaOrdinativiRequest.getConciliatoParziale()!=null ? ricercaOrdinativiRequest.getConciliatoParziale():null);
    	
    	ordinativoVOList = ordinativoEntityMapper.mapListEntityToListVO(sigasPagamentiList);
    	
    	logger.debug("Gli ordinativi ritornati sono " + (ordinativoVOList == null? 0:ordinativoVOList.size()));
    	
		return ordinativoVOList;
    	
    }


	private List<SoggettoEntityCustom> estraiNuoviSoggetti() {
		List<SoggettoEntityCustom> soggettoEntityCustomList;
		List<AnagraficaSoggettoVO> listaNuoviSoggetti;
		listaNuoviSoggetti = ricercaListaNuoviSoggetti();
		soggettoEntityCustomList = new ArrayList<SoggettoEntityCustom>();
		for (AnagraficaSoggettoVO anag : listaNuoviSoggetti) {

			SoggettoEntityCustom e = new SoggettoEntityCustom();
			e.setIdAnag(anag.getIdAnag());
			e.setCodiceAzienda(anag.getCodiceAzienda());
			e.setDenominazione(anag.getDenominazione());
			e.setTotCalcolato(0);
			e.setTotVersamenti(0);
			e.setnProvince(0);
			e.setValidato(null);
			Long[] allarmi = new Long[10];
			for (int idx = 0; idx < 10; idx++) {
				allarmi[idx] = 0L;
			}
			e.setAllarmi(allarmi);
			soggettoEntityCustomList.add(e);
		}
		return soggettoEntityCustomList;
	}


	@Override
	public AnagraficaSoggettoVO ricercaSoggettoByID(Long id) {
		AnagraficaSoggettoVO anagraficaSoggettoVO = new AnagraficaSoggettoVO();
		SigasAnagraficaSoggetti anagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(id);
		if(anagraficaSoggetti!=null) {
			anagraficaSoggettoVO = anagraficaSoggettiEntityMapper.mapEntityToVO(anagraficaSoggetti);
		}
			
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"READ - ricercaSoggettoByID", "sigas_anagrafica_soggetti", String.valueOf(anagraficaSoggetti.getIdAnag()));
//		csiLogAuditRepository.save(csiLogAudit);
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		return anagraficaSoggettoVO;
	}

	@Override
	public List<AnagraficaSoggettoVO> ricercaSoggetti() {
		List<AnagraficaSoggettoVO> anagraficaSoggettoVOList = new ArrayList<AnagraficaSoggettoVO>();
		List<SigasAnagraficaSoggetti> anagraficaSoggettiList = sigasAnagraficaSoggettiRepository.findAll();
		if(anagraficaSoggettiList!=null) {
			anagraficaSoggettoVOList = anagraficaSoggettiEntityMapper.mapListEntityToListVO(anagraficaSoggettiList);
		}
		return anagraficaSoggettoVOList;
	}
	
	@Override
	public List<ConsumiPrVO> ricercaConsumiPerProvince(Long idAnag, String anno) {
		List<NuovoAllacciamentoVO> nuovoAllacciamentoVOList = null;
		List<ConsumiPrVO> consumiPrVO_TempList = new ArrayList<ConsumiPrVO>();
		
		List<SigasDichConsumi> dichConsumiListDB = sigasDichConsumiRepository.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndModIdConsumiAndProvinciaErogazioneIsNotNullOrderByProvinciaErogazioneAsc(idAnag, anno, 0L);
		
		for (SigasDichConsumi sigasDichConsumi: dichConsumiListDB) {
			List<SigasQuadroM> sigasQuadroMList = sigasQuadroMRepository.findByAnnoAndCodiceDittaAndProvincia(sigasDichConsumi.getAnnualita(),
																											  sigasDichConsumi.getSigasAnagraficaSoggetti().getCodiceAzienda(), 
																											  sigasDichConsumi.getProvinciaErogazione());
			
			nuovoAllacciamentoVOList = new ArrayList<NuovoAllacciamentoVO>();			
			for (SigasQuadroM sigasQuadroM : sigasQuadroMList) {
				SigasAliquote sigasAliquote = sigasAliquoteRepository.findByAliquotaAndProgRigo(sigasQuadroM.getAliquota(),sigasQuadroM.getProgRigo(), Integer.parseInt(anno));
				
				if (sigasAliquote!= null && sigasAliquote.getSigasTipoAliquote().getSigasTipoConsumo().getCampoDichConsumo().equals("tot_nuovi_allacciamenti")) {
					NuovoAllacciamentoVO nuovoAllacciamentoVO = new NuovoAllacciamentoVO();
					
					nuovoAllacciamentoVO.setAliquota(sigasQuadroM.getAliquota());
					nuovoAllacciamentoVO.setConsumo(sigasQuadroM.getConsumi());
					nuovoAllacciamentoVO.setDescrizione(sigasAliquote.getSigasTipoAliquote().getDescrizione());
					nuovoAllacciamentoVO.setImporto(sigasQuadroM.getImposta());
					
					nuovoAllacciamentoVOList.add(nuovoAllacciamentoVO);
				}
			}				
			
			ConsumiPrVO consumiPrVO = new ConsumiPrVO();
			consumiPrVO.setNuoviAllacciamenti(nuovoAllacciamentoVOList);
			
			consumiPrVO_TempList.add(consumiPrVO);
		}
		
		List<ConsumiPrVO> ConsumiPrVOList = dichConsumiEntityMapper.mapListEntityToListVO(dichConsumiListDB);
		
		int i = 0;
		for (ConsumiPrVO consumiPrVO : ConsumiPrVOList) {			
			
			consumiPrVO.setNuoviAllacciamenti(consumiPrVO_TempList.get(i).getNuoviAllacciamenti());
			i++;			
						
			Map <String, Double> totaleVersatoConguaglioCalcMap = this.calcolaTotaleVersatoAPartiredaAnno(idAnag, 
																										  consumiPrVO.getAnnualita(), 
																										  (consumiPrVO.getProvincia_erogazione()==null) ? "" : consumiPrVO.getProvincia_erogazione());
			Double totaleVersato = totaleVersatoConguaglioCalcMap.get("totaleVersato");			
			Double conguaglioCalcolato = totaleVersatoConguaglioCalcMap.get("conguaglioCalcolato");
			consumiPrVO.setConguaglio_calc(conguaglioCalcolato);						
			consumiPrVO.setTotaleVersato(totaleVersato);
			
			consumiPrVO.setCompensazione(0D);
			
			SigasDichCompensazioni sigasDichCompensazioni = sigasDichCompensazioniRepository.cercaUltimaCompensazioneAssociataAlConsumo(consumiPrVO.getId_consumi());
			consumiPrVO.setCompensazionePrVO(this.dichCompensazioniEntityMapper.mapEntityToVO(sigasDichCompensazioni));
			
			SigasDichCompensazioni sigasDichCompensazioniTempoT0 = sigasDichCompensazioniRepository.cercaPrimaCompensazioneAssociataAlConsumo(consumiPrVO.getId_consumi());
			if(consumiPrVO.getCompensazionePrVO()!=null) {
				consumiPrVO.getCompensazionePrVO().setConguaglio_di_riferimento_t0(sigasDichCompensazioniTempoT0.getConguaglio_di_riferimento());
			}
			
		}
		
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(idAnag);
		
		// Aggiorno la visita sulla tabella validazione
		if (StringUtils.isNotEmpty(anno)) {
			if (null == sigasValidazioneRepository.findByAnnoAndCodiceAzienda(anno, sigasAnagraficaSoggetti.getCodiceAzienda())) {
				SigasValidazione sigasValidazione = new SigasValidazione();
				sigasValidazione.setAnno(anno);
				sigasValidazione.setCodiceAzienda(sigasAnagraficaSoggetti.getCodiceAzienda());
				sigasValidazione.setStato(StatoValidazione.NON_VALIDATO.getName());
				sigasValidazioneRepository.saveOrUpdate(sigasAnagraficaSoggetti.getCodiceAzienda(), anno, StatoValidazione.NON_VALIDATO.getName());
			}
		}
		
		return ConsumiPrVOList;
	}
	
	
	@Override
	@Transactional
	public void insertSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest, String user) {		
		SigasAnagraficaSoggetti sigasAnagraficaSoggettiInsert = anagraficaSoggettiEntityMapper.mapVOtoEntity(confermaSoggettoRequest.getSoggetto());
		sigasAnagraficaSoggettiInsert.setInsUser(user);
		sigasAnagraficaSoggettiInsert.setInsDate(new Date());
		
		
	    String codAzienda = "NEW_" +  Calendar.getInstance().getTimeInMillis();	    
		sigasAnagraficaSoggettiInsert.setCodiceAzienda(codAzienda);
		
		SigasAnagraficaSoggetti sigasAnagraficaSoggettiNuova = sigasAnagraficaSoggettiRepository.save(sigasAnagraficaSoggettiInsert);
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERT - insertSoggetto", "sigas_anagrafica_soggetti",String.valueOf(sigasAnagraficaSoggettiNuova.getIdAnag()) );
//		csiLogAuditRepository.save(csiLogAudit);
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
	}
	
	@Override
	@Transactional
	public AnagraficaSoggettoVO updateSoggetto(ConfermaSoggettoRequest confermaSoggettoRequest, String user) throws BusinessException {
		SigasAnagraficaSoggetti sigasAnagraficaSoggettiUpdate = anagraficaSoggettiEntityMapper.mapVOtoEntity(confermaSoggettoRequest.getSoggetto());
		
		try {
			codiceFiscalePartitaIvaValidator.validate("codice fiscale / partita IVA", sigasAnagraficaSoggettiUpdate.getCfPiva());
		} catch (Exception e) {
			throw new BusinessException("C.F / P.IVA formato non valido.", ErrorCodes.BUSSINESS_EXCEPTION);
		}
		
		sigasAnagraficaSoggettiUpdate.setModUser(user);
		sigasAnagraficaSoggettiUpdate.setModDate(new Date());
		
		sigasAnagraficaSoggettiRepository.save(sigasAnagraficaSoggettiUpdate);
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"UPDATE - updateSoggetto", "sigas_anagrafica_soggetti",String.valueOf(sigasAnagraficaSoggettiUpdate.getIdAnag()));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		
		return new AnagraficaSoggettoVO();
	}

	@Override
	@Transactional
	public AnagraficaSoggettoVO fusioneSoggetto(FusioneSoggettoRequest fusioneSoggettoRequest, String user) {
		String notaSogetto = null;
		SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MM/yyyy");
		
		SigasAnagraficaSoggetti sigasAnagraficaSoggetto = sigasAnagraficaSoggettiRepository.findOne(fusioneSoggettoRequest.getIdAnagConfluente());
		
		sigasAnagraficaSoggetto.setIdFusione(fusioneSoggettoRequest.getIdAnagDerivante());
		sigasAnagraficaSoggetto.setDataFusione(fusioneSoggettoRequest.getDataFusione());
		if (StringUtils.isNotEmpty(sigasAnagraficaSoggetto.getNote()) )
			notaSogetto = sigasAnagraficaSoggetto.getNote() + "\n";
		else
			notaSogetto = "";
		
		sigasAnagraficaSoggetto.setNote(notaSogetto + fusioneSoggettoRequest.getNote());
		sigasAnagraficaSoggetto.setModUser(user);
		sigasAnagraficaSoggetto.setModDate(new Date());
		
		sigasAnagraficaSoggetto = sigasAnagraficaSoggettiRepository.save(sigasAnagraficaSoggetto);
		
		// Aggionamento note soggeto derivante
		SigasAnagraficaSoggetti sigasAnagraficaSoggettoDerivante = sigasAnagraficaSoggettiRepository.findOne(fusioneSoggettoRequest.getIdAnagDerivante());
		if (StringUtils.isNotEmpty(sigasAnagraficaSoggettoDerivante.getNote()) )
			notaSogetto = sigasAnagraficaSoggettoDerivante.getNote() + "\n";
		else
			notaSogetto = "";
		
		sigasAnagraficaSoggettoDerivante.setNote(notaSogetto + "Fusione con il soggetto " + sigasAnagraficaSoggetto.getDenominazione() + 
											" in data " + simpleformat.format(fusioneSoggettoRequest.getDataFusione()));
		sigasAnagraficaSoggettoDerivante.setModUser(user);
		sigasAnagraficaSoggettoDerivante.setModDate(new Date());
		
		sigasAnagraficaSoggettiRepository.save(sigasAnagraficaSoggettoDerivante);
		
		return anagraficaSoggettiEntityMapper.mapEntityToVO(sigasAnagraficaSoggetto);
		
	}

	
	@Override
	@Transactional
	public AnagraficaSoggettoVO associateSoggetto(AssociaSoggettoRequest associaSoggettoRequest, String user) {
		
		SigasAnagraficaSoggetti sigasAnagSoggAssociateNew =
				anagraficaSoggettiEntityMapper.mapVOtoEntity(associaSoggettoRequest.getSoggettoNew());
		SigasAnagraficaSoggetti sigasAnagSoggAssociateSelezionato =
				anagraficaSoggettiEntityMapper.mapVOtoEntity(associaSoggettoRequest.getSoggettoSelezionato());
		
		List<SigasAnaComunicazioni> listaComunicazioni = sigasAnaComunicazioniRepository.findBySigasAnagraficaSoggettiIdAnagAndDelUserIsNullAndDelDateIsNullOrderByDataDocumentoAsc(sigasAnagSoggAssociateNew.getIdAnag());
		for (SigasAnaComunicazioni s : listaComunicazioni) {
			s.setSigasAnagraficaSoggetti(sigasAnagSoggAssociateSelezionato);
		}
		List<SigasDichVersamenti> listaVersamenti = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnag(sigasAnagSoggAssociateNew.getIdAnag());
		for (SigasDichVersamenti s : listaVersamenti) {
			s.setSigasAnagraficaSoggetti(sigasAnagSoggAssociateSelezionato);
			List<SigasDichConsumi> listaConsumi = sigasDichConsumiRepository.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndModIdConsumiAndProvinciaErogazioneIsNotNullOrderByProvinciaErogazioneAsc(sigasAnagSoggAssociateSelezionato.getIdAnag(), s.getAnnualita() , 0L);
			for (SigasDichConsumi c : listaConsumi) {
				if (c.getProvinciaErogazione().equals(s.getSigasProvincia().getSiglaProvincia())) {
					s.setSigasDichConsumi(c);
				}
			}
			
		}		
		sigasAnagSoggAssociateSelezionato.setModUser(user);
		sigasAnagSoggAssociateSelezionato.setModDate(new Date());
		sigasAnagSoggAssociateSelezionato = sigasAnagraficaSoggettiRepository.save(sigasAnagSoggAssociateSelezionato);
		sigasAnagraficaSoggettiRepository.delete(sigasAnagSoggAssociateNew);
		
		AnagraficaSoggettoVO anagraficaSoggettoVO = anagraficaSoggettiEntityMapper.mapEntityToVO(sigasAnagSoggAssociateSelezionato);
		
		return anagraficaSoggettoVO;
	}
	
	@Override
	@Transactional
	public List<AnagraficaSoggettoVO> ricercaListaSoggetti(RicercaConsumiRequest ricercaConsumiRequest) {
		List<AnagraficaSoggettoVO> anagraficaSoggettoVOList = new ArrayList<AnagraficaSoggettoVO>();
		double totaleVersato;
		List<SigasAnagraficaSoggetti> sigasAnagraficaSoggettiList = sigasAnagraficaSoggettiRepository.newFindFilterCodiceAzienda();

		logger.debug("Trovati " + sigasAnagraficaSoggettiList.size() + " soggetti");

		for (SigasAnagraficaSoggetti sigasAnagraficaSoggetti : sigasAnagraficaSoggettiList) {

			totaleVersato = sigasDichVersamentiRepository.sumByAnnoAndProvinciaAndSoggetto(ricercaConsumiRequest.getAnno(), 
					sigasAnagraficaSoggetti.getSigasProvincia().getSiglaProvincia(), sigasAnagraficaSoggetti);

			AnagraficaSoggettoVO anagraficaSoggettoVO = new AnagraficaSoggettoVO();
			anagraficaSoggettoVO.setIdAnag(sigasAnagraficaSoggetti.getIdAnag());
			anagraficaSoggettoVO.setDenominazione(sigasAnagraficaSoggetti.getDenominazione());
			anagraficaSoggettoVO.setCodiceAzienda(sigasAnagraficaSoggetti.getCodiceAzienda());
			anagraficaSoggettoVO.setTotVersato(totaleVersato);
			anagraficaSoggettoVOList.add(anagraficaSoggettoVO);
		}	    	

		logger.debug("I soggetti ritornati sono " + anagraficaSoggettoVOList.size());

		return anagraficaSoggettoVOList;
	}
	
	@Override
	@Transactional
	public List<AnagraficaSoggettoVO> ricercaListaNuoviSoggetti() {
		List<AnagraficaSoggettoVO> anagraficaNuoviSoggettoVOList = new ArrayList<AnagraficaSoggettoVO>();
		List<SigasAnagraficaSoggetti> anagraficaNuoviSoggettiList = sigasAnagraficaSoggettiRepository.findAnagraficaSoggettiNotInConsumi();

		logger.debug("Trovati " + anagraficaNuoviSoggettiList.size() + " nuovi soggetti");

		for (SigasAnagraficaSoggetti sigasAnagraficaSoggetti : anagraficaNuoviSoggettiList) {

			AnagraficaSoggettoVO anagraficaSoggettoVO = new AnagraficaSoggettoVO();
			anagraficaSoggettoVO.setIdAnag(sigasAnagraficaSoggetti.getIdAnag());
			anagraficaSoggettoVO.setDenominazione(sigasAnagraficaSoggetti.getDenominazione());
			anagraficaSoggettoVO.setCodiceAzienda(sigasAnagraficaSoggetti.getCodiceAzienda());
			anagraficaSoggettoVO.setTotVersato(0);
			anagraficaNuoviSoggettoVOList.add(anagraficaSoggettoVO);
		}	    

		logger.debug("I soggetti nuovi sono " + anagraficaNuoviSoggettoVOList.size());

		return anagraficaNuoviSoggettoVOList;
	}
	
	@Override
	@RequestMapping("/SigasAnagraficaSoggetti/count")
	public Long getNumberOfSoggetti(){
	    return sigasAnagraficaSoggettiRepository.count();
	}

	@Override
	@Transactional
	public List<ScartoVO> ricercaScartiByIdConsumi(Long idConsumi) {
		List<ScartoVO> listaScartoVO = new ArrayList<ScartoVO>();
		List<SigasDichScarti> listSigasDichScarti = sigasDichScartiRepository.findBySigasDichConsumiIdConsumi(idConsumi);
		listaScartoVO = dichScartiEntityMapper.mapListEntityToListVO(listSigasDichScarti);
		
		SigasDichConsumi sigasDichConsumi = sigasDichConsumiRepository.findOne(idConsumi);		

		List<SigasQuadroM> sigasQuadroMList = sigasQuadroMRepository.findByCodiceDittaAndProvinciaAndImportUTF(sigasDichConsumi.getSigasAnagraficaSoggetti().getCodiceAzienda(), 
				sigasDichConsumi.getProvinciaErogazione(), sigasDichConsumi.getSigasImport(), sigasDichConsumi.getAnnualita());		
		
		for (SigasQuadroM sigasQuadroM : sigasQuadroMList) {			
			if (sigasQuadroM.getAliquota() != 0) {
				// Cerca tipo consumo per aliquota e prog_rigo
    			SigasAliquote sigasAliquote = sigasAliquoteRepository.findByAliquotaAndProgRigo(sigasQuadroM.getAliquota(),sigasQuadroM.getProgRigo(), Integer.parseInt(sigasDichConsumi.getAnnualita()) );
    			if(sigasAliquote==null) {
    				for (ScartoVO scarto : listaScartoVO) {
    					if(scarto.getAliquota()==sigasQuadroM.getAliquota() && 
    							scarto.getConsumi()==sigasQuadroM.getConsumi() && 
    							scarto.getProvincia().equalsIgnoreCase(sigasQuadroM.getProvincia()) && (scarto.getDescUsoScarto()==null || scarto.getDescUsoScarto().isEmpty()) ) {
    						
    						List<SigasAliquote> listAliquote = sigasAliquoteRepository.findByProgRigoAndAnno(sigasQuadroM.getProgRigo(), Integer.parseInt(sigasQuadroM.getAnno()) );
	    					
	    					List<String> descUso = new ArrayList<>();
	    					for (SigasAliquote aliquota : listAliquote) {
	    						if(!descUso.contains(aliquota.getSigasTipoAliquote().getSigasTipoConsumo().getDescrizione()))
	    							descUso.add(aliquota.getSigasTipoAliquote().getSigasTipoConsumo().getDescrizione());
							}
	    					scarto.setDescUsoScarto(StringUtils.join(descUso," - "));
    						
    					}
    				}
    			}
			}
		}
		return listaScartoVO;
	}
	
	@Override
	@Transactional
	public List<AliquoteVO> getAllAliquote(Integer annoDichiarazione) {
		List<AliquoteVO> listaAliquoteVO = new ArrayList<AliquoteVO>();
		List<SigasAliquote> sigasAliquoteList = null;
		if(annoDichiarazione!=9999) {
			sigasAliquoteList = sigasAliquoteRepository.findAliquotaByValiditaStartValiditaEnd(annoDichiarazione);
		}else {
			sigasAliquoteList = sigasAliquoteRepository.findAll();
		}
		
		listaAliquoteVO = aliquoteEntityMapper.mapListEntityToListVO(sigasAliquoteList);
		return listaAliquoteVO;
	}	
	
	@Override
	@Transactional
	public ConsumiPrVO updateConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user) {
		
		// Consumi
		SigasDichConsumi sigasDichConsumiUpdate = dichConsumiEntityMapper.mapVOtoEntity(confermaConsumiRequest.getConsumi());
		SigasDichConsumi sigasDichConsumiStorico = sigasDichConsumiRepository.findOne(confermaConsumiRequest.getConsumi().getId_consumi());
		
		if (sigasDichConsumiStorico.getModIdConsumi() != 0) {
			throw new HibernateOptimisticLockingFailureException(new StaleObjectStateException(null, sigasDichConsumiStorico));
		}
		sigasDichConsumiUpdate.setIdConsumi(0);
		sigasDichConsumiUpdate.setAnnualita(sigasDichConsumiStorico.getAnnualita());
		sigasDichConsumiUpdate.setAddizionaleLiquidata(sigasDichConsumiStorico.getAddizionaleLiquidata());
		sigasDichConsumiUpdate.setSigasAnagraficaSoggetti(sigasDichConsumiStorico.getSigasAnagraficaSoggetti());
		sigasDichConsumiUpdate.setConguaglioDich(sigasDichConsumiStorico.getConguaglioDich());
		
		//Inizio Salvo il conguaglio senza i versamenti
		SigasProvincia sigasProvincia = sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(confermaConsumiRequest.getConsumi().getProvincia_erogazione());
		double totaleVersamenti = 0;
		if (null != sigasProvincia) {
			List<Long> idConsumoPadreList = calcolaIdConsumoPadre(confermaConsumiRequest.getConsumi().getId_consumi());
			List<SigasDichVersamenti> sigasDichVersamentiList = new ArrayList<SigasDichVersamenti>();
			if(idConsumoPadreList!=null && !idConsumoPadreList.isEmpty()) {
				for (Long longTmp : idConsumoPadreList) {
					sigasDichVersamentiList.addAll(sigasDichVersamentiRepository.findBySigasDichConsumiIdConsumiAndSigasProvinciaIdProvincia(
							longTmp.longValue(),
							sigasProvincia.getIdProvincia()) );
				}					
			} else {
				sigasDichVersamentiList.addAll(sigasDichVersamentiRepository.findBySigasDichConsumiIdConsumiAndSigasProvinciaIdProvincia(
						confermaConsumiRequest.getConsumi().getId_consumi(),
						sigasProvincia.getIdProvincia()) );
			}
			
			
			for (SigasDichVersamenti sigasDichVersamenti : sigasDichVersamentiList) {
				if (!sigasDichVersamenti.getSigasTipoVersamento().getDenominazione().equals((TipoVersamenti.ACCERTAMENTO).getName())) {
					totaleVersamenti += sigasDichVersamenti.getImporto();
				}
			}
		}
		sigasDichConsumiUpdate.setConguaglioCalcolato(confermaConsumiRequest.getConsumi().getConguaglio_calc() + totaleVersamenti);		
				
		sigasDichConsumiUpdate.setDataPresentazione(sigasDichConsumiStorico.getDataPresentazione());
		sigasDichConsumiUpdate.setProvinciaErogazione(sigasDichConsumiStorico.getProvinciaErogazione());
		sigasDichConsumiUpdate.setRateoDich(sigasDichConsumiStorico.getRateoDich());
		sigasDichConsumiUpdate.setStatoDich(sigasDichConsumiStorico.getStatoDich());
		sigasDichConsumiUpdate.setSigasImport(sigasDichConsumiStorico.getSigasImport());
		sigasDichConsumiUpdate.setTotaleDich(sigasDichConsumiStorico.getTotaleDich());
		sigasDichConsumiUpdate.setTotaleDichOrigine(sigasDichConsumiStorico.getTotaleDichOrigine());
		
		sigasDichConsumiUpdate = sigasDichConsumiRepository.save(sigasDichConsumiUpdate);
		
		if(confermaConsumiRequest.getConsumi()!= null && confermaConsumiRequest.getConsumi().getCompensazionePrVO()!=null) { 
			
			CompensazionePrVO compensazione = new CompensazionePrVO();    		
    	        	    
    	    compensazione.setCompensazione(confermaConsumiRequest.getConsumi().getCompensazionePrVO().getCompensazione());
    	    compensazione.setConguaglio_compensato(confermaConsumiRequest.getConsumi().getCompensazionePrVO().getConguaglio_compensato());
    	    compensazione.setConguaglio_di_riferimento(confermaConsumiRequest.getConsumi().getCompensazionePrVO().getConguaglio_di_riferimento());
    	    Calendar cal = Calendar.getInstance();
    	    compensazione.setData_compensazione(cal.getTime());
    	    compensazione.setId_consumi(sigasDichConsumiUpdate.getIdConsumi());
    		sigasDichCompensazioniRepository.save(this.dichCompensazioniEntityMapper.mapVOtoEntity(compensazione));    		
    	}
		
		sigasDichConsumiStorico.setModdate(new Date());
		sigasDichConsumiStorico.setModIdConsumi(sigasDichConsumiUpdate.getIdConsumi());
		sigasDichConsumiStorico.setModUser(user);
		sigasDichConsumiStorico = sigasDichConsumiRepository.save(sigasDichConsumiStorico);
		
		int scartiDaConciliare = 0;
		
		// Scarti
		List<SigasDichScarti> sigasDichScartiList = new ArrayList<SigasDichScarti>();
		for (ScartoVO scartoVO : confermaConsumiRequest.getScarti()) {
			SigasDichScarti sigasDichScartiUpdate = dichScartiEntityMapper.mapVOtoEntity(scartoVO);
			sigasDichScartiUpdate.setIdScarti(0);
			sigasDichScartiUpdate.setSigasDichConsumi(sigasDichConsumiUpdate);
			sigasDichScartiList.add(sigasDichScartiUpdate);
			
			if(scartoVO.isConciliato()) {
				scartiDaConciliare++;
			}

		}
		sigasDichScartiRepository.save(sigasDichScartiList);
		
		SigasTipoAllarmi sigasTipoAllarmeScarti = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(TipoAllarme.SCARTI.getName());
		
		SigasAllarmi sigasAllarmi = sigasAllarmiRepository.findBySigasDichConsumiIdConsumiAndSigasTipoAllarmeIdTipoAllarme(sigasDichConsumiStorico.getIdConsumi(), sigasTipoAllarmeScarti.getIdTipoAllarme()!=null ? sigasTipoAllarmeScarti.getIdTipoAllarme().intValue():0);
		if(sigasAllarmi != null) {
			if(confermaConsumiRequest.getScarti().size() == scartiDaConciliare) {
				sigasAllarmi.setStatus(StatusAllarme.NON_ATTIVO.getName());
			} else {
				sigasAllarmi.setStatus(StatusAllarme.ATTIVO.getName());
			}
			
			sigasAllarmi.setSigasDichConsumi(sigasDichConsumiUpdate);
			sigasAllarmiRepository.save(sigasAllarmi);
		}
		
		// Allarme compensazione
		int isAlarmActive = 0;
		if (confermaConsumiRequest.getConsumi()!= null && confermaConsumiRequest.getConsumi().getCompensazionePrVO()!=null)  {
			isAlarmActive = 1;
		} else {
			isAlarmActive = 0;
		}
		
		SigasTipoAllarmi sigasTipoAllarmeCompensazione = sigasTipoAllarmeRepository
														 .findByDenominazioneIgnoreCase(TipoAllarme.COMPENSAZIONE.getName());

		SigasAllarmi sigasAllarmiCompensazione = sigasAllarmiRepository
												 .findBySigasDichConsumiIdConsumiAndSigasTipoAllarmeIdTipoAllarme(sigasDichConsumiUpdate.getIdConsumi(), 
														 														  sigasTipoAllarmeCompensazione.getIdTipoAllarme());
		if (sigasAllarmiCompensazione == null && isAlarmActive == 1) {
					
			sigasAllarmiCompensazione = new SigasAllarmi();
			sigasAllarmiCompensazione.setAttivazione(new Date());
			sigasAllarmiCompensazione.setCodiceAzienda(sigasDichConsumiUpdate.getSigasAnagraficaSoggetti().getCodiceAzienda());
			sigasAllarmiCompensazione.setNota("Compensazione credito/debito consumo " + sigasDichConsumiUpdate.getSigasAnagraficaSoggetti().getCodiceAzienda());
			sigasAllarmiCompensazione.setSigasTipoAllarme(sigasTipoAllarmeCompensazione);
			sigasAllarmiCompensazione.setAnnualita(sigasDichConsumiUpdate.getAnnualita());
			sigasAllarmiCompensazione.setUtente(user);
			sigasAllarmiCompensazione.setSigasDichConsumi(sigasDichConsumiUpdate);
		} 
		
		if (sigasAllarmiCompensazione != null) {
			sigasAllarmiCompensazione.setStatus(isAlarmActive);
		
			sigasAllarmiRepository.save(sigasAllarmiCompensazione);
		}
		
		
		return dichConsumiEntityMapper.mapEntityToVO(sigasDichConsumiUpdate);
	}
	
	@Override
	@Transactional
	public ConsumiPrVO updateCompensazioneConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user) {
		
		int isAlarmActive = 0;
		
		// Consumi
		SigasDichConsumi sigasDichConsumiUpdate = sigasDichConsumiRepository.findOne(confermaConsumiRequest.getConsumi().getId_consumi());

		sigasDichConsumiUpdate.setCompensazione(confermaConsumiRequest.getConsumi().getCompensazione());
		
		sigasDichConsumiUpdate = sigasDichConsumiRepository.save(sigasDichConsumiUpdate);
		
		// Allarme
		if (sigasDichConsumiUpdate.getCompensazione() != 0)  {
			isAlarmActive = 1;
		} else {
			isAlarmActive = 0;
		}
		
		SigasTipoAllarmi sigasTipoAllarmeCompensazione = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(TipoAllarme.COMPENSAZIONE.getName());
		
		SigasAllarmi sigasAllarmi = sigasAllarmiRepository.findBySigasDichConsumiIdConsumiAndSigasTipoAllarmeIdTipoAllarme(sigasDichConsumiUpdate.getIdConsumi(), sigasTipoAllarmeCompensazione.getIdTipoAllarme()!=null  ? sigasTipoAllarmeCompensazione.getIdTipoAllarme().intValue():0);
		if (sigasAllarmi == null && isAlarmActive == 1) {
					
			sigasAllarmi = new SigasAllarmi();
			sigasAllarmi.setAttivazione(new Date());
			sigasAllarmi.setCodiceAzienda(sigasDichConsumiUpdate.getSigasAnagraficaSoggetti().getCodiceAzienda());
			sigasAllarmi.setNota("Compensazione credito/debito consumo " + sigasDichConsumiUpdate.getNote());
			sigasAllarmi.setSigasTipoAllarme(sigasTipoAllarmeCompensazione);
			sigasAllarmi.setAnnualita(sigasDichConsumiUpdate.getAnnualita());
			sigasAllarmi.setUtente(user);
			sigasAllarmi.setSigasDichConsumi(sigasDichConsumiUpdate);
		} 

		if (sigasAllarmi != null)
		{
			sigasAllarmi.setStatus(isAlarmActive);
			sigasAllarmiRepository.save(sigasAllarmi);
		}
		
		return dichConsumiEntityMapper.mapEntityToVO(sigasDichConsumiUpdate);
	}
	
	@Override
	@Transactional
	public List<StoricoConsumiVO> storicoConsumi(Long idConsumi) {		
		List<SigasDichConsumi> sigasStoricoConsumiList = new ArrayList<SigasDichConsumi>();
		SigasDichConsumi sigasStoricoConsumi = sigasDichConsumiRepository.findByModIdConsumi(idConsumi);		
		
		while ( sigasStoricoConsumi != null) {
			sigasStoricoConsumiList.add(sigasStoricoConsumi);
			sigasStoricoConsumi = sigasDichConsumiRepository.findByModIdConsumi(sigasStoricoConsumi.getIdConsumi());	
		}
		
		return storicoConsumiEntityMapper.mapListEntityToListVO(sigasStoricoConsumiList);
	}
	
	@Override
	@Transactional
	public ConsumiPrVO ripristinaModificaConsumi(Long idConsumi, Long idStoricoConsumi, String user) {
		
		SigasDichConsumi sigasStoricoConsumi = sigasDichConsumiRepository.findOne(idStoricoConsumi);
		SigasDichConsumi sigasConsumi = sigasDichConsumiRepository.findOne(idConsumi);
		
		SigasDichConsumi sigasConsumiRipristina = new SigasDichConsumi();
		sigasConsumiRipristina.setAddizionaleLiquidata(sigasStoricoConsumi.getAddizionaleLiquidata());
		sigasConsumiRipristina.setAnnualita(sigasStoricoConsumi.getAnnualita());
		sigasConsumiRipristina.setArrotondamenti(sigasStoricoConsumi.getArrotondamenti());
		sigasConsumiRipristina.setConguaglioCalcolato(sigasStoricoConsumi.getConguaglioCalcolato());
		sigasConsumiRipristina.setConguaglioDich(sigasStoricoConsumi.getConguaglioDich());
		sigasConsumiRipristina.setDataPresentazione(sigasStoricoConsumi.getDataPresentazione());
		
		String notaSoggetto;
		if (StringUtils.isNotEmpty(sigasStoricoConsumi.getNote()) )
			notaSoggetto = sigasStoricoConsumi.getNote() + "\n";
		else
			notaSoggetto = "";
		
		SimpleDateFormat frm  = new SimpleDateFormat("dd/MM/yyyy");
		String dataMod = frm.format( sigasStoricoConsumi.getModdate());
		
		sigasConsumiRipristina.setNote(notaSoggetto + "Ripristinata modifica del " + dataMod);
		sigasConsumiRipristina.setProvinciaErogazione(sigasStoricoConsumi.getProvinciaErogazione());
		sigasConsumiRipristina.setRateoDich(sigasStoricoConsumi.getRateoDich());
		sigasConsumiRipristina.setRettifiche(sigasStoricoConsumi.getRettifiche());
		sigasConsumiRipristina.setSigasAnagraficaSoggetti(sigasStoricoConsumi.getSigasAnagraficaSoggetti());
		sigasConsumiRipristina.setSigasImport(sigasStoricoConsumi.getSigasImport());
		sigasConsumiRipristina.setStatoDich(sigasStoricoConsumi.getStatoDich());
		sigasConsumiRipristina.setTotaleCalcolato(sigasStoricoConsumi.getTotaleCalcolato());
		sigasConsumiRipristina.setTotaleDich(sigasStoricoConsumi.getTotaleDich());
		sigasConsumiRipristina.setTotaleDichOrigine(sigasStoricoConsumi.getTotaleDichOrigine());
		sigasConsumiRipristina.setTotCivili(sigasStoricoConsumi.getTotCivili());
		sigasConsumiRipristina.setTotIndustriali(sigasStoricoConsumi.getTotIndustriali());
		sigasConsumiRipristina.setTotNuoviAllacciamenti(sigasStoricoConsumi.getTotNuoviAllacciamenti());
		sigasConsumiRipristina.setUsiCivili120(sigasStoricoConsumi.getUsiCivili120());
		sigasConsumiRipristina.setUsiCivili1560(sigasStoricoConsumi.getUsiCivili1560());
		sigasConsumiRipristina.setUsiCivili480(sigasStoricoConsumi.getUsiCivili480());
		sigasConsumiRipristina.setUsiCiviliUp(sigasStoricoConsumi.getUsiCiviliUp());
		sigasConsumiRipristina.setUsiIndustriali1200(sigasStoricoConsumi.getUsiIndustriali1200());
		sigasConsumiRipristina.setUsiIndustrialiUp(sigasStoricoConsumi.getUsiIndustrialiUp());

		sigasConsumiRipristina = sigasDichConsumiRepository.save(sigasConsumiRipristina);
		
		sigasConsumi.setModIdConsumi(sigasConsumiRipristina.getIdConsumi());
		sigasConsumi.setModdate(new Date());
		sigasConsumi.setModUser(user);
		
		sigasDichConsumiRepository.save(sigasConsumi);
		
		// Scarti
		List<SigasDichScarti> sigasDichScartiStoricoList = sigasDichScartiRepository.findBySigasDichConsumiIdConsumi(idStoricoConsumi);
		int scartiDaConciliare = 0;
		
		List<SigasDichScarti> sigasDichScartiList = new ArrayList<SigasDichScarti>();
		
		for (SigasDichScarti scarto : sigasDichScartiStoricoList) {
			SigasDichScarti sigasDichScarti = new SigasDichScarti();			
			sigasDichScarti.setAliquota(scarto.getAliquota());
			sigasDichScarti.setConciliato(scarto.isConciliato());
			sigasDichScarti.setConsumi(scarto.getConsumi());
			sigasDichScarti.setImposta(scarto.getImposta());
			sigasDichScarti.setProvincia(scarto.getProvincia());
			sigasDichScarti.setSigasDichConsumi(sigasConsumiRipristina);
			sigasDichScartiList.add(sigasDichScarti);
			if(scarto.isConciliato()) {
				scartiDaConciliare++;
			}
		}
		
		sigasDichScartiRepository.save(sigasDichScartiList);
		
		SigasTipoAllarmi sigasTipoAllarmeScarti = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(TipoAllarme.SCARTI.getName());
		
		SigasAllarmi sigasAllarmi = sigasAllarmiRepository.findBySigasDichConsumiIdConsumiAndSigasTipoAllarmeIdTipoAllarme(sigasConsumiRipristina.getIdConsumi(), sigasTipoAllarmeScarti.getIdTipoAllarme()!=null ? sigasTipoAllarmeScarti.getIdTipoAllarme().intValue():0);
		
		if (sigasAllarmi != null) {
			if(sigasDichScartiStoricoList.size() == scartiDaConciliare) {
				sigasAllarmi.setStatus(0);
			} else {
				sigasAllarmi.setStatus(1);
			}			
			sigasAllarmi.setSigasDichConsumi(sigasConsumiRipristina);
			sigasAllarmiRepository.save(sigasAllarmi);	
		}
		
		SigasTipoAllarmi sigasTipoAllarmeCompensazione = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(TipoAllarme.COMPENSAZIONE.getName());
		
		SigasAllarmi sigasAllarmiCompensazione = sigasAllarmiRepository.findBySigasDichConsumiIdConsumiAndSigasTipoAllarmeIdTipoAllarme(sigasConsumiRipristina.getIdConsumi(), sigasTipoAllarmeCompensazione.getIdTipoAllarme()!=null ? sigasTipoAllarmeCompensazione.getIdTipoAllarme().intValue():0);
		if (sigasAllarmiCompensazione != null) {
			sigasAllarmiCompensazione.setSigasDichConsumi(sigasConsumiRipristina);
			sigasAllarmiRepository.save(sigasAllarmiCompensazione);	
		}
		
		return dichConsumiEntityMapper.mapEntityToVO(sigasConsumiRipristina);
		
	}


	@Override
	public void validaSoggetto(Long idAnag, String anno, boolean validato) {
		
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(idAnag);
		
		SigasValidazione  sigasValidazione = sigasValidazioneRepository.findByAnnoAndCodiceAzienda(anno, sigasAnagraficaSoggetti.getCodiceAzienda());
		
		// Aggiorno la visita sulla tabella validazione
		if (null == sigasValidazione) {
			sigasValidazione = new SigasValidazione();
			sigasValidazione.setAnno(anno);
			sigasValidazione.setCodiceAzienda(sigasAnagraficaSoggetti.getCodiceAzienda());
			if (validato)
				sigasValidazione.setStato(StatoValidazione.VALIDATO.getName());
			else
				sigasValidazione.setStato(StatoValidazione.NON_VALIDATO.getName());
			
		} else {
			if (validato)
				sigasValidazione.setStato(StatoValidazione.VALIDATO.getName());
			else
				sigasValidazione.setStato(StatoValidazione.NON_VALIDATO.getName());
		}
		
		sigasValidazioneRepository.save(sigasValidazione);
		
	}
	
    @Transactional
    @Override
	public List<AnaComunicazioniVO> ricercaDocumentiByAnnoAndTipologia(RicercaAnaComunicazioniRequest ricercaAnaComunicazioniRequest) {
    	
    	List<AnaComunicazioniVO> anaComunicazioniVOList = new ArrayList<AnaComunicazioniVO>();
    	List<SigasAnaComunicazioni> sigasAnaComunicazioniEntityList = null;
    	List<String> listKeyOper = new ArrayList<>();
    	if(ricercaAnaComunicazioniRequest != null &&
    			(ricercaAnaComunicazioniRequest.getTipologia().equalsIgnoreCase("Tutte")) &&
    			(ricercaAnaComunicazioniRequest.getAnnoDocumento().equalsIgnoreCase("Tutti")) ) {
    		
        	//Entrambi sono valorizzati a 'tutti'
			logger.debug("Ricerca per tutti i documenti");
			sigasAnaComunicazioniEntityList = sigasAnaComunicazioniRepository.findBySigasAnagraficaSoggettiIdAnagAndDelUserIsNullAndDelDateIsNullOrderByDataDocumentoAsc(ricercaAnaComunicazioniRequest.getIdAnag());
			logger.debug("Trovati " + sigasAnaComunicazioniEntityList.size() + " documenti");

    	} else if (ricercaAnaComunicazioniRequest != null &&
    			(ricercaAnaComunicazioniRequest.getAnnoDocumento() != null && (!ricercaAnaComunicazioniRequest.getAnnoDocumento().isEmpty())) &&
    			(ricercaAnaComunicazioniRequest.getTipologia() == null || ricercaAnaComunicazioniRequest.getTipologia().equalsIgnoreCase("Tutte") ||
    			 Long.parseLong(ricercaAnaComunicazioniRequest.getTipologia()) == 0) ) {

        	//Soltanto Anno è valorizzato
			logger.debug("Ricerca per documenti con filtro " + ricercaAnaComunicazioniRequest.getAnnoDocumento());
			sigasAnaComunicazioniEntityList = sigasAnaComunicazioniRepository.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndDelUserIsNullAndDelDateIsNullOrderByDataDocumentoAsc(
					ricercaAnaComunicazioniRequest.getIdAnag(), ricercaAnaComunicazioniRequest.getAnnoDocumento());
			logger.debug("Trovati " + sigasAnaComunicazioniEntityList.size() + " documenti");
		} else if (ricercaAnaComunicazioniRequest != null &&
    			(ricercaAnaComunicazioniRequest.getTipologia() != null && (!ricercaAnaComunicazioniRequest.getTipologia().isEmpty())) &&
    			(ricercaAnaComunicazioniRequest.getAnnoDocumento() == null || ricercaAnaComunicazioniRequest.getAnnoDocumento().equalsIgnoreCase("Tutti") ||
    			 Long.parseLong(ricercaAnaComunicazioniRequest.getAnnoDocumento()) == 0) ) {

		    	//Soltanto Tipologia è valorizzato
				logger.debug("Ricerca per documenti con filtro " + ricercaAnaComunicazioniRequest.getTipologia());
				sigasAnaComunicazioniEntityList = sigasAnaComunicazioniRepository.findBySigasAnagraficaSoggettiIdAnagAndSigasTipoComunicazioniIdTipoComunicazioneAndDelUserIsNullAndDelDateIsNullOrderByDataDocumentoAsc(
				ricercaAnaComunicazioniRequest.getIdAnag(), Long.parseLong(ricercaAnaComunicazioniRequest.getTipologia()));
				logger.debug("Trovati " + sigasAnaComunicazioniEntityList.size() + " documenti");
    	} else if (ricercaAnaComunicazioniRequest != null &&
    			(ricercaAnaComunicazioniRequest.getTipologia() != null && (!ricercaAnaComunicazioniRequest.getTipologia().isEmpty())) &&
    			(ricercaAnaComunicazioniRequest.getAnnoDocumento() != null && (!ricercaAnaComunicazioniRequest.getAnnoDocumento().isEmpty())) &&
    			Long.parseLong(ricercaAnaComunicazioniRequest.getTipologia()) != 0 && Long.parseLong(ricercaAnaComunicazioniRequest.getAnnoDocumento()) != 0) {

        	//Entrambi sono valorizzati
			logger.debug("Ricerca per documenti con filtro " + ricercaAnaComunicazioniRequest.getTipologia() + " - " + ricercaAnaComunicazioniRequest.getAnnoDocumento());
			sigasAnaComunicazioniEntityList = sigasAnaComunicazioniRepository.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasTipoComunicazioniIdTipoComunicazioneAndDelUserIsNullAndDelDateIsNullOrderByDataDocumentoAsc(
					ricercaAnaComunicazioniRequest.getIdAnag(),
					ricercaAnaComunicazioniRequest.getAnnoDocumento(),
					Long.parseLong(ricercaAnaComunicazioniRequest.getTipologia()));
			logger.debug("Trovati " + sigasAnaComunicazioniEntityList.size() + " documenti");
    	}
    	
		anaComunicazioniVOList = anaComunicazioniEntityMapper.mapListEntityToListVO(sigasAnaComunicazioniEntityList);
    	
    	logger.debug("I documenti ritornati sono " + (anaComunicazioniVOList == null? 0:anaComunicazioniVOList.size()));
    	
    	for (AnaComunicazioniVO anaComunicazioniVO : anaComunicazioniVOList) {
    		if (!StringUtils.isEmpty(anaComunicazioniVO.getNProtocollo())) {
    			String[] params = anaComunicazioniVO.getNProtocollo().split("/");
        		if (params.length == 2) {
        			ActaUtils actaUtils = new ActaUtils(acarisServiceFactory);
            		
        			try {
						anaComunicazioniVO.setAllegati(actaUtils.listaAllegati(params[1], params[0]) );
					}
        			catch (BusinessException be) {
        				// TODO Auto-generated catch block
						be.printStackTrace();
        			}
        			catch (AcarisException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		
        		
    		}
			
		}
    	
    	
    	if(ricercaAnaComunicazioniRequest.getIdAnag()!=null) {
        	listKeyOper.add(ricercaAnaComunicazioniRequest.getIdAnag().toString());
        }
    	
    	for (AnaComunicazioniVO anaComunTmp : anaComunicazioniVOList) {
    		listKeyOper.add(String.valueOf(anaComunTmp.getIdComunicazione()) );
		}
    	
    	CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"READ - ricercaDocumentiByAnnoAndTipologia", "sigas_ana_comunicazioni", String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
										   csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), 
										   csiLogAudit.getId().getKeyOper());	
    	
		return anaComunicazioniVOList;
    	
    }
	
	@Override
	@Transactional
	public List<AnaComunicazioniVO> ricercaDocumentiByIdAnag(Long idAnag) {
		List<AnaComunicazioniVO> listaAnaComunicazioniVO = new ArrayList<AnaComunicazioniVO>();
		List<SigasAnaComunicazioni> listSigasAnaComunicazioni = sigasAnaComunicazioniRepository.findBySigasAnagraficaSoggettiIdAnagAndDelUserIsNullAndDelDateIsNullOrderByDataDocumentoAsc(idAnag);
		listaAnaComunicazioniVO = anaComunicazioniEntityMapper.mapListEntityToListVO(listSigasAnaComunicazioni);
		return listaAnaComunicazioniVO;
	}
	
	@Override
	public AllarmiSoggettoVO allarmeDocumento(AllarmeDocumentoRequest allarmeRequest, String utente) {

		SigasAnagraficaSoggetti sogg = sigasAnagraficaSoggettiRepository.findByIdAnag(allarmeRequest.getIdAnag());
		SigasAnaComunicazioni comunicazione = sigasAnaComunicazioniRepository.findOne(allarmeRequest.getIdComunicazione());
		SigasTipoAllarmi sigasTipoAllarmeAnaComunicazioni = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(TipoAllarme.COMUNICAZIONI.getName());

		SigasAllarmi sigasAllarmi = sigasAllarmiRepository.findBySigasAnaComunicazioniIdComunicazioneAndSigasTipoAllarmeIdTipoAllarme(comunicazione.getIdComunicazione(), sigasTipoAllarmeAnaComunicazioni.getIdTipoAllarme()!=null ? sigasTipoAllarmeAnaComunicazioni.getIdTipoAllarme().intValue():0);

		if (null == sigasAllarmi) {
			SigasTipoAllarmi sigasTipoAllarme = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(allarmeRequest.getTipoAllarme());

			sigasAllarmi = new SigasAllarmi();
			sigasAllarmi.setAttivazione(new Date());
			sigasAllarmi.setCodiceAzienda(sogg.getCodiceAzienda());
			sigasAllarmi.setSigasTipoAllarme(sigasTipoAllarme);
			sigasAllarmi.setSigasAnaComunicazioni(comunicazione);
			sigasAllarmi.setNota(allarmeRequest.getNota());
			sigasAllarmi.setAnnualita(comunicazione.getAnnualita());
			sigasAllarmi.setUtente(utente);
			if (allarmeRequest.isAllarmeOn())
				sigasAllarmi.setStatus(StatusAllarme.ATTIVO.getName());
			else
				sigasAllarmi.setStatus(StatusAllarme.NON_ATTIVO.getName());

		} else {
			if (allarmeRequest.isAllarmeOn())
				sigasAllarmi.setStatus(StatusAllarme.ATTIVO.getName());
			else
				sigasAllarmi.setStatus(StatusAllarme.NON_ATTIVO.getName());
		}

		 AllarmiSoggettoVO allarme = allarmiSoggettoEntityMapper.mapEntityToVO(sigasAllarmiRepository.save(sigasAllarmi));
		 return allarme;
	}
	
    @Transactional
    @Override
    public AnaComunicazioniVO addDocumento(Long id, String user) { 
    	SigasAnaComunicazioni doc = new SigasAnaComunicazioni();
   	 	Date date= new Date();
    	long time = date.getTime();
    	Timestamp ts = new Timestamp(time);
    	doc.setDataDocumento(ts);
    	doc.setDescrizione("test descrizione");
    	doc.setInsUser(user);
    	doc.setInsDate(new Date());
    	
    	SigasAnaComunicazioni anaComunicazioniEntity =  sigasAnaComunicazioniRepository.save(doc);
    	return anaComunicazioniEntityMapper.mapEntityToVO(anaComunicazioniEntity);
    }

    @Transactional
    @Override
    public AnaComunicazioniVO addDocument(String uid, String nomeFile, Long idAnag, MultipartFormDataInput input, String user) { 
    	SigasAnaComunicazioni anaComunicazioniEntity = null;
    	SigasRimborso istanzaRimborso = null;
    	SigasAllarmi istanzaAllarme = null;
    	SigasAllarmi istanzaAllarmeUtf = null;
    	
    	List<String> listKeyOper = new ArrayList<String>();
    	String oggOper="";

    	// Bisogna prima controllare se esiste già un file con quel nome e anno oppure uid
    	String anno, note, datiRiassuntivi, nprotocollo, idTipoComunicazione, data;
    	try {
    		anno = input.getFormDataPart("annualita", String.class, null);
    		SigasAnaComunicazioni testEntityNome = sigasAnaComunicazioniRepository.findByDescrizioneAndAnnualitaOrderByDataDocumentoAsc(nomeFile, anno);
    		SigasAnaComunicazioni testEntityUid = sigasAnaComunicazioniRepository.findByRifArchivioOrderByDataDocumentoAsc(uid);

    		if(null == testEntityNome) {
    			if(null == testEntityUid ||
    					(testEntityUid != null && testEntityUid.getRifArchivio().equals(""))) {
    				SigasAnaComunicazioni doc = new SigasAnaComunicazioni();
    				SigasAnagraficaSoggetti soggetto = new SigasAnagraficaSoggetti();
    				soggetto = sigasAnagraficaSoggettiRepository.findOne(idAnag);

    				SigasTipoComunicazioni tipoComunicazioni = new SigasTipoComunicazioni();
    			
    				//Recupero dei dati dal formData
    				note = input.getFormDataPart("note", String.class, null);
    				datiRiassuntivi = input.getFormDataPart("datiRiassuntivi", String.class, null);
    				nprotocollo = input.getFormDataPart("nprotocollo", String.class, null);
    				if(null == uid) {	//VERIFICARE - codice non raggiungibile
    					uid = input.getFormDataPart("rifArchivio", String.class, null);
    				}
    				idTipoComunicazione = input.getFormDataPart("idTipoComunicazione", String.class, null);
    				tipoComunicazioni = sigasTipoComunicazioniRepository.findOne(Long.parseLong(idTipoComunicazione));
    				data = input.getFormDataPart("dataDocumento", String.class, null);

					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date dataIstanza = df.parse(data);
    				doc.setDataDocumento(dataIstanza);
    				doc.setSigasTipoComunicazioni(tipoComunicazioni);
    				doc.setSigasAnagraficaSoggetti(soggetto);
    				doc.setAnnualita(anno);
        			doc.setDescrizione(anno + "_" + soggetto.getCodiceAzienda() + "_" + nomeFile);
    				doc.setNote(note);
    				doc.setDatiRiassuntivi(datiRiassuntivi);
    				doc.setNProtocollo(nprotocollo);
    				doc.setRifArchivio(uid);
    				doc.setInsDate(new Date());
    				doc.setInsUser(user);

    				anaComunicazioniEntity = sigasAnaComunicazioniRepository.save(doc);
    				
    				// Controllo su tipoComunicazioni per aggiornare sigas_rimborsi
    				if(tipoComunicazioni.getDenominazione().equals(TipoComunicazioni.ISTANZA_DI_RIMBORSO.getName())) {
    					RimborsoVO rimborsoVO = new RimborsoVO();
    					AnagraficaSoggettoVO anagraficaSoggetto = anagraficaSoggettiEntityMapper.mapEntityToVO(soggetto);
    					rimborsoVO.setAnagraficaSoggettoVO(anagraficaSoggetto);
    					rimborsoVO.setAnnualita(anno);
    					rimborsoVO.setDataIstanza(dataIstanza);
    					rimborsoVO.setStatoPratica("ISTANZA");
    					rimborsoVO.setAnaComunicazioniVO(anaComunicazioniEntityMapper.mapEntityToVO(anaComunicazioniEntity));
    					rimborsoVO.setInsDate(new Date());
    					rimborsoVO.setInsUser(user);
    					SigasRimborso sigasRimborso = rimborsoEntityMapper.mapVOtoEntity(rimborsoVO);
    					sigasRimborso.getSigasAnaComunicazioni().setInsDate(new Date());
    					sigasRimborso.getSigasAnaComunicazioni().setInsUser(user);
    					istanzaRimborso = sigasRimborsoRepository.save(sigasRimborso);
    					
    				}
    				
    				if (istanzaRimborso != null && anaComunicazioniEntity != null) {
    					SigasTipoAllarmi sigasTipoAllarmeRimb = sigasTipoAllarmeRepository
    							.findByDenominazioneIgnoreCase(TipoAllarme.RIMBORSO.getName());
    					SigasAllarmi sigasAllarmiRimborso = new SigasAllarmi();
    					sigasAllarmiRimborso.setAttivazione(new Date());
    					sigasAllarmiRimborso.setCodiceAzienda(soggetto.getCodiceAzienda());
    					sigasAllarmiRimborso
    							.setNota("Presentata istanza di Rimborso  " + soggetto.getCodiceAzienda());
    					sigasAllarmiRimborso.setSigasTipoAllarme(sigasTipoAllarmeRimb);
    					sigasAllarmiRimborso.setAnnualita(anno);
    					sigasAllarmiRimborso.setSigasAnaComunicazioni(anaComunicazioniEntity);
    					int isAlarmActive = 0;
    					if (istanzaRimborso.getStatoPratica().equalsIgnoreCase(StatoRimborso.ISTANZA.getName()))  {
    						isAlarmActive = 1;
    					} else {
    						isAlarmActive = 0;
    					}
    					sigasAllarmiRimborso.setStatus(isAlarmActive);
    					istanzaAllarme = sigasAllarmiRepository.save(sigasAllarmiRimborso);
    				}
    				
    				// In caso di documento UTF spengo allarme di mancanza di documento generato in import UTF
					if (tipoComunicazioni.getDenominazione().equals(TipoComunicazioni.DICHIARAZIONE_UTF.getName())) {
						SigasTipoAllarmi sigasTipoAllarmeComunicazione = sigasTipoAllarmeRepository
								.findByDenominazioneIgnoreCase(TipoAllarme.COMUNICAZIONI.getName()); 
						SigasAllarmi sigasAllarmeUTF = sigasAllarmiRepository
								.findBySigasDichConsumiIdConsumiAndSigasAnaComunicazioniIdComunicazioneAndCodiceAziendaAndSigasTipoAllarmeIdTipoAllarmeAndAnnualita(
										null, null, soggetto.getCodiceAzienda(),
										sigasTipoAllarmeComunicazione.getIdTipoAllarme()!=null ? sigasTipoAllarmeComunicazione.getIdTipoAllarme().intValue():0, anno);

						if (sigasAllarmeUTF != null) {
							sigasAllarmeUTF.setStatus(0);
							sigasAllarmeUTF.setSigasAnaComunicazioni(anaComunicazioniEntity);
							istanzaAllarmeUtf = sigasAllarmiRepository.save(sigasAllarmeUTF);
						}
					}

    			} else {
    				if (StringUtils.isEmpty(input.getFormDataPart("nprotocollo", String.class, null)))
        				throw new BusinessException("Il documento e' gia' presente a sistema");
        			else
        				throw new BusinessException("Il documento protocollato e' gia' presente a sistema");
    			}
    		} else {
    			throw new BusinessException("Esiste gia un file '"+ nomeFile + " per l'anno '" + anno);
    		}
    	} catch (IOException | ParseException e) {
    		e.printStackTrace();
    	}
    	
    	if(anaComunicazioniEntity!=null) {
    		listKeyOper.add(String.valueOf(anaComunicazioniEntity.getIdComunicazione()));
    		oggOper += "sigas_ana_comunicazioni ";
    	}
    	
    	if(istanzaRimborso!=null) {
    		listKeyOper.add(String.valueOf(istanzaRimborso.getIdRimborso()));
    		oggOper += "sigas_rimborso ";
    	}
    	
    	if(istanzaAllarme!=null) {
    		listKeyOper.add(String.valueOf(istanzaAllarme.getIdAllarme()));
    		oggOper += "sigas_allarmi ";
    	}
    	
    	if(istanzaAllarmeUtf!=null) {
    		listKeyOper.add(String.valueOf(istanzaAllarmeUtf.getIdAllarme()));
    		oggOper += "sigas_allarmi ";
    	}
    	
    	
    	CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERT - salvaAllegatoVerbale", oggOper, String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		
    	return anaComunicazioniEntityMapper.mapEntityToVO(anaComunicazioniEntity);
    }

    @Transactional
    @Override
    public AnaComunicazioniVO updateDocumento(String uid, String nomeFile, Long idAnag, MultipartFormDataInput input, String user) { 
    	SigasAnaComunicazioni anaComunicazioniEntity = null;

    	// Bisogna prima controllare se esiste già un file con quel nome e anno
    	String idComunic, anno, note, datiRiassuntivi, nprotocollo, idTipoComunicazione,dataDocumento;
    	try {
    		anno = input.getFormDataPart("annualita", String.class, null);
    		idComunic = input.getFormDataPart("idComunicazione", String.class, null);
    		SigasAnaComunicazioni doc = sigasAnaComunicazioniRepository.findOne(Long.parseLong(idComunic));
    		dataDocumento = input.getFormDataPart("dataDocumento", String.class, null);
    		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		Date dataDoc = df.parse(dataDocumento);
    		if(null != doc) {
    			//Recupero dei dati dal formData				
    			note = input.getFormDataPart("note", String.class, null);
    			datiRiassuntivi = input.getFormDataPart("datiRiassuntivi", String.class, null);
    			nprotocollo = input.getFormDataPart("nprotocollo", String.class, null);
    			idTipoComunicazione = input.getFormDataPart("idTipoComunicazione", String.class, null);
    			SigasTipoComunicazioni tipoComunicazioni = sigasTipoComunicazioniRepository.findOne(Long.parseLong(idTipoComunicazione));
    			
    			doc.setSigasTipoComunicazioni(tipoComunicazioni);
    			doc.setAnnualita(anno);
    			if (StringUtils.isNotEmpty(nomeFile))
    				doc.setDescrizione(anno + "_" + doc.getSigasAnagraficaSoggetti().getCodiceAzienda() + "_" + nomeFile);
    			doc.setNote(note);
    			doc.setDatiRiassuntivi(datiRiassuntivi);
    			doc.setNProtocollo(nprotocollo);
    			doc.setRifArchivio(uid);
    			doc.setDataDocumento(dataDoc);
    			doc.setModDate(new Date());
    			doc.setModUser(user);
    			
    			anaComunicazioniEntity = sigasAnaComunicazioniRepository.save(doc);    			
    			
    			// Controllo su tipoComunicazioni per aggiornare sigas_rimborsi
				if(tipoComunicazioni.getDenominazione().equals(TipoComunicazioni.ISTANZA_DI_RIMBORSO.getName())) {
					SigasRimborso sigasRimborso = sigasRimborsoRepository.findBySigasAnagraficaSoggettiIdAnagAndSigasAnaComunicazioniIdComunicazione(idAnag, doc.getIdComunicazione());
					if(sigasRimborso!=null && dataDoc.compareTo(sigasRimborso.getDataIstanza())!=0) {
						if(!"DETERMINA".equalsIgnoreCase(sigasRimborso.getStatoPratica())) {						
							sigasRimborso.setDataIstanza(dataDoc);
							sigasRimborso.getSigasAnaComunicazioni().setModDate(new Date());
							sigasRimborso.getSigasAnaComunicazioni().setModUser(user);
							
							sigasRimborsoRepository.save(sigasRimborso);
						}else {
							throw new BusinessException("Rimborso in stato di determina non è possibile modificare la Data Documento");
						}
					}
				}

    		} else {
    			throw new BusinessException("Il documento non esiste nel sistema");
    		}
    	} catch (IOException | ParseException e) {
    		e.printStackTrace();
    	}

    	CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"UPDATE - aggiornaAllegatoVerbale", "sigas_ana_comunicazioni",String.valueOf(anaComunicazioniEntity.getIdComunicazione())  );
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		
    	return anaComunicazioniEntityMapper.mapEntityToVO(anaComunicazioniEntity);
    }
    @Transactional
    @Override
    public AnaComunicazioniVO updateTestataDocumento(AnaComunicazioniVO anaComunicazione, String user) {
    	
    	SigasAnaComunicazioni anaComunicazioniEntity = null;
    	SigasAnaComunicazioni doc = new SigasAnaComunicazioni();
    	doc.setIdComunicazione(anaComunicazione.getIdComunicazione());
    	doc.setDescrizione(anaComunicazione.getDescrizione());
    	doc.setRifArchivio(anaComunicazione.getRifArchivio());
    	doc.setAnnualita(anaComunicazione.getAnnualita());
    	doc.setDataDocumento(anaComunicazione.getDataDocumento());
    	doc.setDatiRiassuntivi(anaComunicazione.getDatiRiassuntivi());
    	doc.setNote(anaComunicazione.getNote());
    	doc.setNProtocollo(anaComunicazione.getNProtocollo());
    	doc.setSigasAnagraficaSoggetti(anagraficaSoggettiEntityMapper.mapVOtoEntity(anaComunicazione.getAnagraficaSoggettoVO()));
    	doc.setSigasTipoComunicazioni(tipoComunicazioniEntityMapper.mapVOtoEntity(anaComunicazione.getTipoComunicazioneVO()));
    	doc.setModDate(new Date());
    	doc.setModUser(user);
    	anaComunicazioniEntity = sigasAnaComunicazioniRepository.save(doc);
    	
    	return anaComunicazioniEntityMapper.mapEntityToVO(anaComunicazioniEntity);
    }

    @Override
    public byte[] getDocumento(String descComunicazione) throws IOException {
    	InputStream is = getClass().getResourceAsStream("/application.properties");
    	Properties p=new Properties();    	
    	p.load(is);   	
    	byte[] datadown = null;

    	SigasAnaComunicazioni sigasAnaComunicazioni = sigasAnaComunicazioniRepository.findByDescrizione(descComunicazione);    	
    	if (StringUtils.isEmpty(sigasAnaComunicazioni.getNProtocollo())) {

			String baseUrl=p.getProperty("baseUrl");
			String urlWs1=baseUrl.concat(p.getProperty("urlWs1"));
		
			EcmEngineWebServiceDelegateServiceLocator ecmengineLocator = new EcmEngineWebServiceDelegateServiceLocator();
			EcmEngineWebServiceDelegate ecmengineDelegate;
			try {
				ecmengineDelegate = ecmengineLocator.getEcmEngineManagement(new URL(urlWs1));
		
				OperationContext context= new OperationContext();
				context.setFruitore(p.getProperty("fruitore"));
				context.setNomeFisico(sigasAnaComunicazioni.getDescrizione()); // il nome del file da inserire, se non esiste indicare il nome fruitore (comeda doc di integrazione)
		
				context.setRepository(p.getProperty("repository"));
				context.setUsername(p.getProperty("username")); 
				context.setPassword(p.getProperty("password"));
		
				Node parentNode= new Node();
				parentNode.setUid(sigasAnaComunicazioni.getRifArchivio());  // uid nodo padre cm:sigas
				Content content = new Content();
				content.setTypePrefixedName(p.getProperty("typePrefixedName"));
				content.setModelPrefixedName(p.getProperty("modelPrefixedName"));
				content.setParentAssocTypePrefixedName(p.getProperty("parentAssocTypePrefixedName"));
				content.setPrefixedName("cm:"+sigasAnaComunicazioni.getDescrizione() );     //nome del file
				content.setContentPropertyPrefixedName(p.getProperty("contentPropertyPrefixedName"));
				content.setMimeType("application/csv");			// si puo' prelevare dalla estensione del file
				content.setOptimize(false);
				content.setEncoding("UTF-8");
		
				// Setting dei metadati rispetto al Model presente in INDEX
				Property[] props =new Property[2];
				props[0] = new Property();
				props[0].setPrefixedName(p.getProperty("prefixedName"));
				props[0].setDataType(p.getProperty("dataType"));
				props[0].setMultivalue(false);
				props[0].setValues(new String [] {"tipoDocumentoArchiviatoesempio"});
				props[1] = new Property();
				props[1].setPrefixedName(p.getProperty("prefixedName2"));
				props[1].setDataType(p.getProperty("dataType2"));
				props[1].setMultivalue(false);
				props[1].setValues(new String [] {p.getProperty("anno")});
				content.setProperties(props);
		
				Content content2=new Content();
				content2.setContentPropertyPrefixedName(p.getProperty("contentPropertyPrefixedName"));
				datadown=ecmengineDelegate.retrieveContentData(parentNode, content2, context);
			} catch (Exception e) {
				throw new BusinessException(e.getMessage(), ErrorCodes.BUSSINESS_EXCEPTION);
			}
	
    	} else {
    		String[] params = sigasAnaComunicazioni.getNProtocollo().split("/");
    		if (params.length < 2)
    			throw new BusinessException("Il numero di protocollo '" + sigasAnaComunicazioni.getNProtocollo() + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
    		
    		ActaUtils actaUtils = new ActaUtils(acarisServiceFactory);    		
    		datadown = actaUtils.download(params[1], params[0]);
    	}
    
    	return datadown;			
    }    
    
    @Override
    public byte[] getDocumentoMaster(String descComunicazione) throws IOException { 
    	InputStream is = getClass().getResourceAsStream("/application.properties");
    	Properties p=new Properties();    	
    	p.load(is);    	
    	byte[] datadown = null;

    	SigasAnaComunicazioni sigasAnaComunicazioni = sigasAnaComunicazioniRepository.findByDescrizione(descComunicazione);    	
    	if (StringUtils.isEmpty(sigasAnaComunicazioni.getNProtocollo())) {

			String baseUrl=p.getProperty("baseUrl");
			String urlWs1=baseUrl.concat(p.getProperty("urlWs1"));
		
			EcmEngineWebServiceDelegateServiceLocator ecmengineLocator = new EcmEngineWebServiceDelegateServiceLocator();
			EcmEngineWebServiceDelegate ecmengineDelegate;
			try {
				ecmengineDelegate = ecmengineLocator.getEcmEngineManagement(new URL(urlWs1));
		
				OperationContext context= new OperationContext();
				context.setFruitore(p.getProperty("fruitore"));
				context.setNomeFisico(sigasAnaComunicazioni.getDescrizione()); // il nome del file da inserire, se non esiste indicare il nome fruitore (comeda doc di integrazione)
		
				context.setRepository(p.getProperty("repository"));
				context.setUsername(p.getProperty("username")); 
				context.setPassword(p.getProperty("password"));
		
				Node parentNode= new Node();
				parentNode.setUid(sigasAnaComunicazioni.getRifArchivio());  // uid nodo padre cm:sigas
				Content content = new Content();
				content.setTypePrefixedName(p.getProperty("typePrefixedName"));
				content.setModelPrefixedName(p.getProperty("modelPrefixedName"));
				content.setParentAssocTypePrefixedName(p.getProperty("parentAssocTypePrefixedName"));
				content.setPrefixedName("cm:"+sigasAnaComunicazioni.getDescrizione() );     //nome del file
				content.setContentPropertyPrefixedName(p.getProperty("contentPropertyPrefixedName"));
				content.setMimeType("application/csv");			// si puo' prelevare dalla estensione del file
				content.setOptimize(false);
				content.setEncoding("UTF-8");
		
				// Setting dei metadati rispetto al Model presente in INDEX
				Property[] props =new Property[2];
				props[0] = new Property();
				props[0].setPrefixedName(p.getProperty("prefixedName"));
				props[0].setDataType(p.getProperty("dataType"));
				props[0].setMultivalue(false);
				props[0].setValues(new String [] {"tipoDocumentoArchiviatoesempio"});
				props[1] = new Property();
				props[1].setPrefixedName(p.getProperty("prefixedName2"));
				props[1].setDataType(p.getProperty("dataType2"));
				props[1].setMultivalue(false);
				props[1].setValues(new String [] {p.getProperty("anno")});
				content.setProperties(props);
		
				Content content2=new Content();
				content2.setContentPropertyPrefixedName(p.getProperty("contentPropertyPrefixedName"));
				datadown=ecmengineDelegate.retrieveContentData(parentNode, content2, context);
			} catch (Exception e) {
				throw new BusinessException(e.getMessage(), ErrorCodes.BUSSINESS_EXCEPTION);
			}
	
    	} else {
    		String[] params = sigasAnaComunicazioni.getNProtocollo().split("/");

    		if (params.length < 2)
    			throw new BusinessException("Il numero di protocollo '" + sigasAnaComunicazioni.getNProtocollo() + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
    		
    		ActaUtils actaUtils = new ActaUtils(acarisServiceFactory);    		
    		datadown = actaUtils.downloadMaster(params[1], params[0]);
    	}
    
    	return datadown;			
    }    
    
    @Override
    public byte[] getStampaAllegato(String descComunicazione, String descAllegato) throws IOException {
    	InputStream is = getClass().getResourceAsStream("/application.properties");
    	Properties p=new Properties();    	
    	p.load(is);    	
    	byte[] datadown = null;

    	SigasAnaComunicazioni sigasAnaComunicazioni = sigasAnaComunicazioniRepository.findByDescrizione(descComunicazione);    	
		String[] params = sigasAnaComunicazioni.getNProtocollo().split("/");
		if (params.length < 2)
			throw new BusinessException("Il numero di protocollo '" + sigasAnaComunicazioni.getNProtocollo() + "' non e' valido", ErrorCodes.BUSSINESS_EXCEPTION); 
		
		ActaUtils actaUtils = new ActaUtils(acarisServiceFactory);		
		try {
			datadown = actaUtils.downloadAllegato(params[1], params[0], descAllegato);
		}
		catch (BusinessException be) {			
			throw be;
		}
		catch (AcarisException e) {
			throw new BusinessException("Il numero di protocollo '" + params[0] + "' non contiene allegati", ErrorCodes.BUSSINESS_EXCEPTION);
		}    
    	return datadown;			
    }
    
    @Override
    public List<TipoComunicazioniVO> getAllTipoComunicazioni() {
		List<TipoComunicazioniVO> listaTipoComunicazioniVO = new ArrayList<TipoComunicazioniVO>();
		List<SigasTipoComunicazioni> sigasTipoComunicazioniList = sigasTipoComunicazioniRepository.findAll();
		listaTipoComunicazioniVO = tipoComunicazioniEntityMapper.mapListEntityToListVO(sigasTipoComunicazioniList);
		return listaTipoComunicazioniVO;
    }

	@Override
	public TipoComunicazioniVO ricercaTipoComunicazioniByIdTipoComunicazione(Long idTipoComunicazione) {
		TipoComunicazioniVO tipoComunicazioniVO = new TipoComunicazioniVO();
		SigasTipoComunicazioni sigasTipoComunicazioni = sigasTipoComunicazioniRepository.findOne(idTipoComunicazione);
		tipoComunicazioniVO = tipoComunicazioniEntityMapper.mapEntityToVO(sigasTipoComunicazioni);
		return tipoComunicazioniVO;
    }
	
	@Override
	public List<VersamentiPrVO> ricercaVersamentiPerProvince(Long id, String anno) {
		List<SigasDichVersamenti> dichVersamentiListDB = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaOrderBySigasProvinciaSiglaProvinciaAsc(id,anno);
		return dichVersamentiEntityMapper.mapListEntityToListVO(dichVersamentiListDB);
	}
	
	@Override
	public List<AllarmiSoggettoVO> ricercaAllarmiByIdDocumentoAndCodiceAzienda(Long idDocumento, String codiceAzienda) {
	    SigasAnaComunicazioni sigasAnaComunicazioni = sigasAnaComunicazioniRepository.findOne(idDocumento);
	    List<AllarmiSoggettoVO> listAllarmiVo = new ArrayList<AllarmiSoggettoVO>();
		if(sigasAnaComunicazioni!=null) {
	    	List<SigasAllarmi> allarmDBList = sigasAllarmiRepository.findBySigasAnaComunicazioniIdComunicazioneAndCodiceAziendaAndStatus(idDocumento, codiceAzienda, 1);
	    	listAllarmiVo =  allarmiSoggettoEntityMapper.mapListEntityToListVO(allarmDBList);
	    }
	    return listAllarmiVo;
	}
	
	@Override 
	public List<RimborsoVO> ricercaListaRimborsi(long idAnag) {
		List<SigasRimborso> sigasRimborsoList = sigasRimborsoRepository.findBySigasAnagraficaSoggettiIdAnag(idAnag);
		
		List<RimborsoVO> rimborsoVoList = rimborsoEntityMapper.mapListEntityToListVO(sigasRimborsoList);
		
		List<String> listKeyOper = new ArrayList<String>();
   		listKeyOper.add(String.valueOf(idAnag));
    	    	
   		for (RimborsoVO rimborsoTmp : rimborsoVoList) {
			listKeyOper.add(String.valueOf(rimborsoTmp.getIdRimborso()) );
		}
    	CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"READ - ricercaListaRimborsi", "sigas_rimborso", String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		
		
		return rimborsoVoList;
	}	
	
	//CR Totale versato	
	private Map <String, Double> calcolaTotaleVersatoAPartiredaAnno(Long idAnag, String anno, String prov) {
		Map<String,Double> mappaTotaleDichiarato = new HashMap<String,Double>();
		Map<String,Double> mappaSommatoriaVersamenti = new HashMap<String,Double>();
		Map<String,Double> mappaTotaleVersato = new HashMap<String,Double>();
		Map<String,Double> mappaConguaglioCalcolato = new HashMap<String,Double>();
		Map<String,Double> mappaCompensazioni = new HashMap<String,Double>();
		
		//Determinazione Totale Dichiarato e Totale Versato per ogni annualita
		List<SigasDichConsumi> dichConsumiDBList = sigasDichConsumiRepository.findConsumiFinoAnnoByIdAnagProv(idAnag,prov, anno);
		if(dichConsumiDBList!= null && !dichConsumiDBList.isEmpty() ) {
			SigasProvincia sigasProvincia = sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(prov);
			Iterator<SigasDichConsumi> iter = dichConsumiDBList.iterator();
			while(iter.hasNext()) {
				SigasDichConsumi digasDichConsumi = iter.next();
				if(digasDichConsumi.getModdate() == null) {
					mappaTotaleDichiarato.put(digasDichConsumi.getAnnualita(), digasDichConsumi.getTotaleDich());
					double totaleVersamenti = 0;
					List<SigasDichVersamenti> sigasDichVersamentiList = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasProvinciaIdProvincia(idAnag, digasDichConsumi.getAnnualita(),sigasProvincia.getIdProvincia());
					Iterator<SigasDichVersamenti> iterVer = sigasDichVersamentiList.iterator();
					while(iterVer.hasNext()) {
						SigasDichVersamenti sigasDichVersamenti = iterVer.next();
						if (!sigasDichVersamenti.getSigasTipoVersamento().getDenominazione().equals((TipoVersamenti.ACCERTAMENTO).getName())) {
							totaleVersamenti += sigasDichVersamenti.getImporto();
						}
					}
					mappaSommatoriaVersamenti.put(digasDichConsumi.getAnnualita(), totaleVersamenti);
					
					SigasDichCompensazioni sigasDichCompensazioni = sigasDichCompensazioniRepository.cercaUltimaCompensazioneAssociataAlConsumo(digasDichConsumi.getIdConsumi());
					if(sigasDichCompensazioni!=null) {
						mappaCompensazioni.put(digasDichConsumi.getAnnualita(), sigasDichCompensazioni.getConguaglio_compensato());
					}					
				}				
			}			
		}
		
		//Determinazione Totale Versato e Conguaglio Calcolato per ogni annualita
		if(mappaTotaleDichiarato!=null && !mappaTotaleDichiarato.isEmpty()) {
			String annoLowerBound = Collections.min(mappaTotaleDichiarato.keySet());		
			for(int i = Integer.valueOf(annoLowerBound); i<= Integer.valueOf(anno); i++ ) {
				String annoRif = String.valueOf(i);
				String annoPrecedente = String.valueOf(i-1);
				Double sommaVersamentiAnnoRif =  (mappaSommatoriaVersamenti.get(annoRif)==null) ? 0D : mappaSommatoriaVersamenti.get(annoRif);
				
				Double conguaglioCalcolatoAnnoPrecedente = (mappaConguaglioCalcolato.get(annoPrecedente)==null) ? 0D : mappaConguaglioCalcolato.get(annoPrecedente);
				Double totaleVersatoAnnoRif = sommaVersamentiAnnoRif - conguaglioCalcolatoAnnoPrecedente;				
				mappaTotaleVersato.put(annoRif, totaleVersatoAnnoRif);
				
				Double totaleDichiaratoAnnoRif = (mappaTotaleDichiarato.get(annoRif)==null) ? 0D : mappaTotaleDichiarato.get(annoRif);
				
				Double conguagloCalcolatoAnnoRif = 0D;
				Double compensazioneAnnoRif = (mappaCompensazioni.get(annoRif)==null) ? 0D : mappaCompensazioni.get(annoRif);
				if(compensazioneAnnoRif!=0) {
					conguagloCalcolatoAnnoRif = compensazioneAnnoRif;
				} else {
					conguagloCalcolatoAnnoRif = totaleDichiaratoAnnoRif - totaleVersatoAnnoRif;
				}				
				//Double conguagloCalcolatoAnnoRif = totaleDichiaratoAnnoRif - totaleVersatoAnnoRif;
				mappaConguaglioCalcolato.put(annoRif, conguagloCalcolatoAnnoRif);
			}
		}	
		
		Map<String, Double> output = new HashMap<String, Double>();
		Double totaleVersatoAnnoRichiesto = (mappaTotaleVersato.get(anno) == null) ? 0D : mappaTotaleVersato.get(anno);
		output.put("totaleVersato", totaleVersatoAnnoRichiesto);
		
		Double conguaglioCalcolatoAnnoRichiesto = (mappaConguaglioCalcolato.get(anno) == null) ? 0D : mappaConguaglioCalcolato.get(anno);
		output.put("conguaglioCalcolato", conguaglioCalcolatoAnnoRichiesto);
		
		return output;		
	}
	//Marts
	@Override
	public ConsumiPrVO ricercaConsumiPerProvinceAndAnnualita(Long idAnag, String anno, String prov) {
		Double totaleVersato = 0D;
		Double conguaglioCalcolato = 0D;
		ConsumiPrVO consumiPrVO = null;
		SigasDichConsumi dichConsumiDB = sigasDichConsumiRepository.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndProvinciaErogazioneAndModIdConsumi(idAnag, anno, prov, 0L);		
		if(dichConsumiDB!=null) {			
						
			Map <String, Double> totaleVersatoConguaglioCalcMap = this.calcolaTotaleVersatoAPartiredaAnno(idAnag, anno, prov);
			totaleVersato = totaleVersatoConguaglioCalcMap.get("totaleVersato");
			conguaglioCalcolato = totaleVersatoConguaglioCalcMap.get("conguaglioCalcolato");			
			
			consumiPrVO = dichConsumiEntityMapper.mapEntityToVO(dichConsumiDB);
			consumiPrVO.setTotaleVersato(totaleVersato);
			consumiPrVO.setConguaglio_calc(conguaglioCalcolato);
			
			consumiPrVO.setCompensazione(0D);
			
			SigasDichCompensazioni sigasDichCompensazioni = sigasDichCompensazioniRepository.cercaUltimaCompensazioneAssociataAlConsumo(dichConsumiDB.getIdConsumi());
			consumiPrVO.setCompensazionePrVO(this.dichCompensazioniEntityMapper.mapEntityToVO(sigasDichCompensazioni));
			
			SigasDichCompensazioni sigasDichCompensazioniTempoT0 = sigasDichCompensazioniRepository.cercaPrimaCompensazioneAssociataAlConsumo(dichConsumiDB.getIdConsumi());
			if(consumiPrVO.getCompensazionePrVO()!=null) {
				consumiPrVO.getCompensazionePrVO().setConguaglio_di_riferimento_t0(sigasDichCompensazioniTempoT0.getConguaglio_di_riferimento());
			}
		}	
		
		return consumiPrVO;
	}

	private List<Long> calcolaIdConsumoPadre(long idConsumi) {		
		
		List<Long> sigasStoricoConsumiList = new ArrayList<Long>();
		sigasStoricoConsumiList.add(idConsumi);
		SigasDichConsumi sigasStoricoConsumi = sigasDichConsumiRepository.findByModIdConsumi(idConsumi);		
		while ( sigasStoricoConsumi != null) {
			sigasStoricoConsumiList.add(sigasStoricoConsumi.getIdConsumi());
			sigasStoricoConsumi = sigasDichConsumiRepository.findByModIdConsumi(sigasStoricoConsumi.getIdConsumi());	
		}		
		return sigasStoricoConsumiList;
	}


	@Override
	@Transactional
	public RimborsoVO salvaRimborso(SalvaRimborsoRequest salvaRimborsoRequest) {
		SigasRimborso sigasRimborso = rimborsoEntityMapper.mapVOtoEntity(salvaRimborsoRequest.getRimborso());
		sigasRimborsoRepository.save(sigasRimborso);
		return rimborsoEntityMapper.mapEntityToVO(sigasRimborso);
	}
	
	@Override
	@Transactional
	public RimborsoVO ricercaRimborso(long idRimborso) {

		return rimborsoEntityMapper.mapEntityToVO(sigasRimborsoRepository.findOne(idRimborso));
	}

	@Override
	public void salvaDetermina(Long idComunicazione) {
		SigasTipoAllarmi sigasTipoAllarmeRimborso = sigasTipoAllarmeRepository
				.findByDenominazioneIgnoreCase(TipoAllarme.RIMBORSO.getName());

		SigasAllarmi sigasAllarmi = sigasAllarmiRepository
				.findBySigasAnaComunicazioniIdComunicazioneAndSigasTipoAllarmeIdTipoAllarme(idComunicazione,
						sigasTipoAllarmeRimborso.getIdTipoAllarme()!=null ? sigasTipoAllarmeRimborso.getIdTipoAllarme().intValue():0);

		if (null != sigasAllarmi) {
			sigasAllarmi.setStatus(StatusAllarme.NON_ATTIVO.getName());
			sigasAllarmiRepository.save(sigasAllarmi);
		}
	}
	
	//ACCERTAMENTI
	@Override
	public List<VersamentiPrVO> ricercaAccertamentiPerAnagrafica(Long id, String anno, String provincia) {
		
		SigasTipoVersamento tipoAccertamento = sigasTipoVersamentoRepository.findByDenominazioneIgnoreCase(TipoAllarme.ACCERTAMENTO.getName());
		SigasTipoAllarmi sigasTipoAllarmeVersamento = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(TipoAllarme.ACCERTAMENTO.getName());
		
		List<SigasDichVersamenti> dichVersamentiListDB = new ArrayList<SigasDichVersamenti>();
		
		if ((!anno.equals("all") && !"undefined".equalsIgnoreCase(anno)) && (!provincia.equals("all") && !"undefined".equalsIgnoreCase(provincia))) {
			dichVersamentiListDB = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaSiglaProvincia(
					id,tipoAccertamento.getIdTipoVersamento(), anno, provincia);
		} else if (!anno.equals("all") && !"undefined".equalsIgnoreCase(anno)) {
			dichVersamentiListDB = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndSigasTipoVersamentoIdTipoVersamentoAndAnnualita(
					id,tipoAccertamento.getIdTipoVersamento(), anno);
		} else if (!provincia.equals("all") && !"undefined".equalsIgnoreCase(provincia)) {
			dichVersamentiListDB = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndSigasTipoVersamentoIdTipoVersamentoAndSigasProvinciaSiglaProvincia(
					id,tipoAccertamento.getIdTipoVersamento(), provincia);
		} else if(!("undefined".equalsIgnoreCase(anno) && "undefined".equalsIgnoreCase(provincia)) ){
			dichVersamentiListDB = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndSigasTipoVersamentoIdTipoVersamento(
					id,tipoAccertamento.getIdTipoVersamento());
		}
		
		List<VersamentiPrVO> accertamentoVo = dichVersamentiEntityMapper.mapListEntityToListVO(dichVersamentiListDB);
			
		for(VersamentiPrVO accertamento : accertamentoVo ) {
			List<SigasAllarmi> listAllarmDB = sigasAllarmiRepository.findBySigasDichVersamentiIdVersamentoAndSigasTipoAllarmeIdTipoAllarme(
							accertamento.getIdVersamento(), sigasTipoAllarmeVersamento.getIdTipoAllarme()!=null ? sigasTipoAllarmeVersamento.getIdTipoAllarme().intValue():0);
			for (SigasAllarmi sigasAllarmi : listAllarmDB) {
				if (sigasAllarmi != null) {
					accertamento.setAllarme(allarmiSoggettoEntityMapper.mapEntityToVO(sigasAllarmi));
				}
			}
		}
		
		List<String> listKeyOper = new ArrayList<String>();
		listKeyOper.add(String.valueOf(id));
		
		for (VersamentiPrVO versamentiPrVOTmp : accertamentoVo) {
			listKeyOper.add(String.valueOf(versamentiPrVOTmp.getIdVersamento()) );
		}
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"READ - elencoAccertamentiByIdAnag", "sigas_dich_versamenti", String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		
		return accertamentoVo;
	}


	@Override
	@Transactional
	public void updateAccertamento(List<ConfermaVersamentoRequest> listaAccertamenti, String user) {
		List<String> listKeyOper = new ArrayList<String>();
		SigasDichVersamenti sigasDichVersamenti = null;
		SigasAllarmi sigasAllarmi = null;
		for(ConfermaVersamentoRequest accertamento : listaAccertamenti) {
			SigasDichVersamenti sigasAccertamento = dichVersamentiEntityMapper.mapVOtoEntity(accertamento.getVersamento());
			sigasDichVersamenti = sigasDichVersamentiRepository.save(sigasAccertamento);
			SigasAllarmi sigasAllarmeTmp = allarmeAccertamento(accertamento.getVersamento(), user); 
			if(sigasAllarmeTmp!=null) {				
				sigasAllarmi = sigasAllarmiRepository.save(sigasAllarmeTmp);
			}
			 
			listKeyOper.add(String.valueOf(sigasDichVersamenti.getIdVersamento()) );			
			if(sigasAllarmi!=null) {
				listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
			}
			
		}
	
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"UPDATE - updateAccertamenti", "sigas_dich_versamenti sigas_allarmi", String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
	}
	
	@Override
	@Transactional
	public void updateAllarmeAccertamento(UpdateAllarmeAccertamentoRequest updateAllarmeAccertamentoRequest, String utente) {
		if(updateAllarmeAccertamentoRequest != null && updateAllarmeAccertamentoRequest.getIdAllarme() != null) {
			
			
			sigasAllarmiRepository.updateStatoAllarmeAccertamento(updateAllarmeAccertamentoRequest.getIdAllarme(),
																  updateAllarmeAccertamentoRequest.getStatoAllarme() );
			
			CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,
																 "UPDATE - UpdateAllarmeAccertamentoRequest", 
																 "sigas_allarmi", 
																 String.join("_", "id allarme: " + updateAllarmeAccertamentoRequest.getIdAllarme()));
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
			
		}		
	}
	
	
	private SigasAllarmi allarmeAccertamento(VersamentiPrVO versamento, String user) {
		SigasTipoAllarmi sigasTipoAllarmeAccertamento = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(TipoAllarme.ACCERTAMENTO.getName());
		SigasAnagraficaSoggetti sogg = sigasAnagraficaSoggettiRepository.findByIdAnag(versamento.getIdAnag());
	    
		SigasDichConsumi consumo = null;
		
		if (versamento.getConsumo() != null) {
			consumo = sigasDichConsumiRepository.findOne(versamento.getConsumo().getId_consumi());
		}
			    
	    List<SigasAllarmi> sigasAllarmiList = sigasAllarmiRepository.findBySigasDichVersamentiIdVersamentoAndSigasTipoAllarmeIdTipoAllarme(
	    		versamento.getIdVersamento(), sigasTipoAllarmeAccertamento.getIdTipoAllarme()!=null ? sigasTipoAllarmeAccertamento.getIdTipoAllarme().intValue():0);

		SigasAllarmi sigasAllarmi = null;
		if (null == sigasAllarmiList || sigasAllarmiList.size()==0) {
			
			sigasAllarmi = new SigasAllarmi();
			sigasAllarmi.setAttivazione(new Date());
			sigasAllarmi.setCodiceAzienda(sogg.getCodiceAzienda());
			sigasAllarmi.setSigasTipoAllarme(sigasTipoAllarmeAccertamento);
			sigasAllarmi.setSigasDichConsumi(consumo != null ? consumo : null);
			sigasAllarmi.setAnnualita(String.valueOf(versamento.getAnnualita()));
			sigasAllarmi.setUtente(user);
			sigasAllarmi.setSigasDichVersamenti(sigasDichVersamentiRepository.findOne(versamento.getIdVersamento()));
			sigasAllarmi.setNota("Allarme accertamento per il soggetto " + versamento.getIdAnag());
			sigasAllarmi.setStatus(StatusAllarme.NON_ATTIVO.getName());
		}	
		
		return sigasAllarmi;
	}
	
	@Override
	@Transactional
	public OrdinativiIncassoVO conciliaPagameto(ConfermaPagamentoRequest confermaPagamentoRequest, String user) {
		confermaPagamentoRequest.getPagamento().setConciliato(true);
		SigasPagamenti sigasPagamentiUpdate = ordinativoEntityMapper.mapVOtoEntity(confermaPagamentoRequest.getPagamento());		
		sigasPagamentiCrudRepository.save(sigasPagamentiUpdate);
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"UPDATE - conciliaPagamento", "sigas_pagamenti",String.valueOf(sigasPagamentiUpdate.getIdPagamento()));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
										   csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		
		return new OrdinativiIncassoVO();
	}

	@Override
	public OrdinativiIncassoVO eliminaConciliazione(ConfermaPagamentoRequest confermaPagamentoRequest) {
		
		for (PagamentiVersamentiVO pagamentoVesamentoVo: confermaPagamentoRequest.getPagamento().getSigasPagamentiVersamentis()) {
			sigasPagamentiVersamentiRepository.delete(pagamentoVesamentoVo.getIdPagamentoVersamento());
			SigasDichVersamenti sigasversamentiToDelete = sigasDichVersamentiRepository.findById(pagamentoVesamentoVo.getFkVersamento().getIdVersamento());
			sigasDichVersamentiRepository.delete(sigasversamentiToDelete);
			
			CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"DELETE - eliminaConciliazione", "sigas_pagamenti_versamenti",String.valueOf(pagamentoVesamentoVo.getIdPagamentoVersamento()));
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
			
			csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"DELETE - eliminaConciliazione", "sigas_dich_versamenti",String.valueOf(sigasversamentiToDelete.getIdVersamento()));
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		
		}
		
		if(confermaPagamentoRequest.getPagamento().getConciliato()) {
			OrdinativiIncassoVO ordIncVO = confermaPagamentoRequest.getPagamento();
			ordIncVO.setConciliato(false);
			sigasPagamentiCrudRepository.save(ordinativoEntityMapper.mapVOtoEntity(ordIncVO) );
			
			CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"UPDATE - eliminaConciliazione", "sigas_pagamenti",String.valueOf(ordIncVO.getIdPagamento()));
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		}		
		
		return new OrdinativiIncassoVO();
	}

	@Override
	@Transactional
	public List<MessaggiVO> controlloImportiConsumi(Long idConsumi) {
		List<MessaggiVO> listMsg = new ArrayList<MessaggiVO>();
		SigasDichConsumi sigasDichConsumi = sigasDichConsumiRepository.findOne(idConsumi);
		
		List<AliquoteVO> listaAliquoteVO = new ArrayList<AliquoteVO>();
		List<SigasAliquote> sigasAliquoteList = null;
		if(!sigasDichConsumi.getAnnualita().equalsIgnoreCase("9999")) {
			sigasAliquoteList = sigasAliquoteRepository.findAliquotaByValiditaStartValiditaEnd(Integer.parseInt(sigasDichConsumi.getAnnualita()) );
		}else {
			sigasAliquoteList = sigasAliquoteRepository.findAll();
		}
		
		listaAliquoteVO = aliquoteEntityMapper.mapListEntityToListVO(sigasAliquoteList);
		
		double aliquotaUsiIndustrialiUp = 0;
		for (SigasAliquote sigasAliquote : sigasAliquoteList) {
			if(sigasAliquote.getSigasTipoAliquote().getIdTipoAliquota()==6L) {
				aliquotaUsiIndustrialiUp = sigasAliquote.getAliquota();
			}
		}
		double importoUsiIndustrialiUp = Long.valueOf(sigasDichConsumi.getUsiIndustrialiUp()).doubleValue()*aliquotaUsiIndustrialiUp;
		
		
		
		//////////////////////////////////////////////////////////
		// Calcola i consumi su quadro M
		int usiIndustrialiUp = 0;
		int usiIndustriali1200 = 0;
		int usiCivili120 = 0;
		int usiCivili480 = 0;
		int usiCivili1560 = 0;
		int usiCiviliUp = 0;
		double totNuoviAllacciamenti = 0.0;
		double totCivili120 = 0, totCivili480 = 0, totCivili1560 = 0, totCiviliUp = 0;
		double totIIndustriali = 0, totIIndustrialiUp = 0, totIIndustriali1200 = 0;
		double  totaleDich = 0, arrotondamenti = 0, rettifiche = 0;
		List<SigasDichScarti> sigasDichScartiList = new ArrayList<SigasDichScarti>();
		
		List<SigasQuadroM> sigasQuadroMList = sigasQuadroMRepository.findByCodiceDittaAndProvinciaAndImportUTF(sigasDichConsumi.getSigasAnagraficaSoggetti().getCodiceAzienda(), 
				sigasDichConsumi.getProvinciaErogazione(), sigasDichConsumi.getSigasImport(), sigasDichConsumi.getAnnualita());
		
		
		for (SigasQuadroM sigasQuadroM : sigasQuadroMList) {
			
			if (sigasQuadroM.getAliquota() != 0) {
				// Cerca tipo consumo per aliquota e prog_rigo
    			SigasAliquote sigasAliquote = sigasAliquoteRepository.findByAliquotaAndProgRigo(sigasQuadroM.getAliquota(),sigasQuadroM.getProgRigo(), Integer.parseInt(sigasDichConsumi.getAnnualita()) );
    			
    			GregorianCalendar aliquotaStartDate = null;
    			GregorianCalendar aliquotaEndDate = null;
    			if (sigasAliquote != null) {
    				aliquotaStartDate = new GregorianCalendar();
	    			aliquotaStartDate.setTime(sigasAliquote.getValiditaStart());
	    			aliquotaEndDate = new GregorianCalendar();
	    			aliquotaEndDate.setTime(sigasAliquote.getValiditaEnd());
    			}    			
    			
    			if (sigasAliquote != null && 
    					Integer.parseInt(sigasDichConsumi.getAnnualita()) >= aliquotaStartDate.get(Calendar.YEAR) &&
    					Integer.parseInt(sigasDichConsumi.getAnnualita()) <= aliquotaEndDate.get(Calendar.YEAR) ) {
    			
    				switch(sigasAliquote.getSigasTipoAliquote().getSigasTipoConsumo().getCampoDichConsumo()) {
    					case "usi_industriali_up":
    						usiIndustrialiUp += sigasQuadroM.getConsumi();
    						//totIIndustrialiUp += sigasQuadroM.getImposta();
    						totIIndustrialiUp += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
    						break;
    					case "usi_industriali_1200":
    						usiIndustriali1200 += sigasQuadroM.getConsumi();
    						//totIIndustriali1200 += sigasQuadroM.getImposta();
    						totIIndustriali1200 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
    						break;
    					case "usi_civili_120":
    						usiCivili120 += sigasQuadroM.getConsumi();
    						//totCivili120 += sigasQuadroM.getImposta();
    						totCivili120 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
    						break;
    					case "usi_civili_480":
    						usiCivili480 += sigasQuadroM.getConsumi();
    						//totCivili480 += sigasQuadroM.getImposta();
    						totCivili480 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
    						break;
    					case "usi_civili_1560":
    						usiCivili1560 += sigasQuadroM.getConsumi();
    						//totCivili1560 += sigasQuadroM.getImposta();
    						totCivili1560 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
    						break;
    					case "usi_civili_up":
    						usiCiviliUp += sigasQuadroM.getConsumi();
    						//totCiviliUp += sigasQuadroM.getImposta();
    						totCiviliUp += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
    						break;
    					case "tot_nuovi_allacciamenti":
    						totNuoviAllacciamenti += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
    						break;
    						
    				}
    			}
			}
			
			
		}
		
		SigasCMessaggi sigasMessaggio = sigasCMessaggiRepository.findByDescChiaveMessaggio("msgUsoErr");
		
		if(importoUsiIndustrialiUp!=totIIndustrialiUp) {
			MessaggiVO messaggiVO = new MessaggiVO(); 
			messaggiVO.setValoreMessaggio(sigasMessaggio.getValoreMessaggio().replaceAll("<<USO>>", "Usi industriali oltre i 1200 Mc") );
			messaggiVO.setLivelloMessaggio(sigasMessaggio.getLivelloMessaggio());
			listMsg.add(messaggiVO);
		}
		
		return listMsg;
	}

	@Override
	public ConsumiPrVO updateTotaleDichConsumi(ConfermaConsumiRequest confermaConsumiRequest, String user) {
		// Consumi
		if(confermaConsumiRequest.getConsumi()!=null) {
			SigasDichConsumi sigasDichConsumiUpdate = sigasDichConsumiRepository.findOne(confermaConsumiRequest.getConsumi().getId_consumi());
			sigasDichConsumiUpdate.setTotaleDich(confermaConsumiRequest.getConsumi().getTotaleDich());
			sigasDichConsumiUpdate = sigasDichConsumiRepository.save(sigasDichConsumiUpdate);
			
			
			return dichConsumiEntityMapper.mapEntityToVO(sigasDichConsumiUpdate);
		}
		
		return null;
		
	}

    @Transactional
    @Override
    public boolean deleteDocumento(Long idDocumento) {
        try {
            SigasAnaComunicazioni documento= this.sigasAnaComunicazioniRepository.findOne(idDocumento);
            if (documento == null) {
                throw new BusinessException("Nessun documento trovato per l'ID: " + idDocumento);
            }
            
            documento.setDelDate(new Date());
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		Object principal = auth.getPrincipal();
    		UserDetails utente = (UserDetails) principal;
    		
            documento.setDelUser(utente.getIdentita().getCodFiscale());
            
            this.sigasAnaComunicazioniRepository.save(documento);			
            CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"CANCELLAZIONE LOGICA DOCUMENTO", "sigas_ana_comunicazioni",String.valueOf(idDocumento));
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
											   csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), 
											   csiLogAudit.getId().getKeyOper());			
			
            return true;
        } catch (Exception e) {
            this.logger.error("deleteDocumento: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
	@Transactional
	public boolean salvaCompensazione(SalvaCompensazioneRequest salvaCompensazioneRequest, String user) {
    	boolean output = false;
    	if(salvaCompensazioneRequest.getCompensazionePrVO()!=null) {
    		
    		//Setting data compensazione
    	    Calendar cal = Calendar.getInstance();
    		salvaCompensazioneRequest.getCompensazionePrVO().setData_compensazione(cal.getTime());
    		sigasDichCompensazioniRepository.save(this.dichCompensazioniEntityMapper.mapVOtoEntity(salvaCompensazioneRequest.getCompensazionePrVO()));
    		
    		SigasTipoAllarmi sigasTipoAllarmeCompensazione = sigasTipoAllarmeRepository
					 										 .findByDenominazioneIgnoreCase(TipoAllarme.COMPENSAZIONE.getName());

			SigasAllarmi sigasAllarmiCompensazione = sigasAllarmiRepository
						 							 .findBySigasDichConsumiIdConsumiAndSigasTipoAllarmeIdTipoAllarme(salvaCompensazioneRequest.getCompensazionePrVO().getId_consumi(), 
						 									 														  sigasTipoAllarmeCompensazione.getIdTipoAllarme());
			//Se non presente un allarme va inserito
			if(sigasAllarmiCompensazione == null) {
				SigasDichConsumi sigasDichConsumi = sigasDichConsumiRepository
													.findOne(salvaCompensazioneRequest.getCompensazionePrVO().getId_consumi());

				sigasAllarmiCompensazione = new SigasAllarmi();
				sigasAllarmiCompensazione.setAttivazione(new Date());
				sigasAllarmiCompensazione.setCodiceAzienda(sigasDichConsumi.getSigasAnagraficaSoggetti().getCodiceAzienda());
				sigasAllarmiCompensazione.setNota("Compensazione credito/debito consumo " + sigasDichConsumi.getSigasAnagraficaSoggetti().getCodiceAzienda());
				sigasAllarmiCompensazione.setSigasTipoAllarme(sigasTipoAllarmeCompensazione);
				sigasAllarmiCompensazione.setAnnualita(sigasDichConsumi.getAnnualita());
				sigasAllarmiCompensazione.setUtente(user);
				sigasAllarmiCompensazione.setSigasDichConsumi(sigasDichConsumi);			
				
				int isAlarmActive = 1;
				sigasAllarmiCompensazione.setStatus(isAlarmActive);				
				sigasAllarmiRepository.save(sigasAllarmiCompensazione);
			}			
    		
    		output = true;
    	}
    	return output;    	
	}

}
