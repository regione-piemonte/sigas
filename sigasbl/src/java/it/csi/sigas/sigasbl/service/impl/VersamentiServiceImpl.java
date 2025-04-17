/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.StatusAllarme;
import it.csi.sigas.sigasbl.common.TipoAllarme;
import it.csi.sigas.sigasbl.common.TipoVersamenti;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.common.utils.Utilities;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasAllarmi;
import it.csi.sigas.sigasbl.model.entity.SigasAnaComunicazioni;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;
import it.csi.sigas.sigasbl.model.entity.SigasDichVersamenti;
import it.csi.sigas.sigasbl.model.entity.SigasPagamenti;
import it.csi.sigas.sigasbl.model.entity.SigasPagamentiVersamenti;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.entity.SigasTipoAllarmi;
import it.csi.sigas.sigasbl.model.entity.SigasTipoVersamento;
import it.csi.sigas.sigasbl.model.mapper.entity.AllarmiSoggettoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.AnagraficaSoggettiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichVersamentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.OrdinativoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.PagamentiVersamentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.ProvinciaEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoVersamentoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAllarmiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichConsumiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichVersamentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPagamentiCrudRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPagamentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasPagamentiVersamentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoAllarmiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoVersamentoRepository;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.AnnualitaVersamentiVO;
import it.csi.sigas.sigasbl.model.vo.home.PagamentiVersamentiVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoVersamentoVO;
import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.request.home.AllarmeRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoContabiliaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaVersamentoRequest;
import it.csi.sigas.sigasbl.request.home.RicercaVersamentiRequest;
import it.csi.sigas.sigasbl.security.UserDetails;
import it.csi.sigas.sigasbl.service.IVersamentiService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;

@Service
public class VersamentiServiceImpl implements IVersamentiService {

	@Autowired
	private SigasAllarmiRepository sigasAllarmiRepository;

	@Autowired
	private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;
	
	@Autowired
	private SigasDichConsumiRepository sigasDichConsumiRepository;

	@Autowired
	private SigasDichVersamentiRepository sigasDichVersamentiRepository;
	
	@Autowired
	private SigasPagamentiVersamentiRepository sigasPagamentiVersamentiRepository;
	
	@Autowired
	private SigasTipoVersamentoRepository sigasTipoVersamentoRepository;
	
	@Autowired
	private DichVersamentiEntityMapper dichVersamentiEntityMapper;
	
	@Autowired
	private PagamentiVersamentiEntityMapper pagamentiVersamentiEntityMapper;
	
	@Autowired
	private SigasProvinciaRepository sigasProvinciaRepository;
	
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	
	@Autowired
	private TipoVersamentoEntityMapper tipoVersamentoEntityMapper;
	
	@Autowired
	private SigasTipoAllarmiRepository sigasTipoAllarmeRepository;
	
	@Autowired
	private AllarmiSoggettoEntityMapper allarmiSoggettoEntityMapper;	
	
	@Autowired
	private CsiLogAuditRepository csiLogAuditRepository;
	
	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	@Autowired
	private SigasPagamentiCrudRepository sigasPagamentiCrudRepository;
	
	@Autowired
	private OrdinativoEntityMapper ordinativoEntityMapper;
	
	@Autowired
	private AnagraficaSoggettiEntityMapper anagraficaSoggettiEntityMapper;
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String PARAM_NUOVO_VERSAMENTO_ANNI_PREVISTI = "nuovo_versamento_numero_anni";
	private final String PARAM_NUOVO_VERSAMENTO_ANNO_RIFERIMENTO = "nuovo_versamento_anno_riferimento";
    
	
	/* Versamenti per Soggetto*/
	@Override
    public List<String> annualitaVersamentiPerRicerca(Long idAnag) {
		
		//Lettura oggetto società
		//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(idAnag);
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(idAnag);
		Map<String, String> elencoAnnualitaIncorporatoMap = null;
		if(sigasAnagraficaSoggetti!=null && sigasAnagraficaSoggetti.getIdFusione() > 0) {
			List<String> elencoAnnualitaIncorporato = sigasDichVersamentiRepository
													  .findDistinctBySigasAnagraficaSoggettiIdAnag(sigasAnagraficaSoggetti.getIdFusione());
			
			if(elencoAnnualitaIncorporato!=null && !elencoAnnualitaIncorporato.isEmpty()) {
				elencoAnnualitaIncorporatoMap = new HashMap<>();
				Iterator<String> iterator = elencoAnnualitaIncorporato.iterator();
				while(iterator.hasNext()) {
					String annualita = iterator.next();
					elencoAnnualitaIncorporatoMap.put(annualita, annualita);					
				}
			}			
		}
		
		List<String> elencoAnnualitaTotale = sigasDichVersamentiRepository.findDistinctBySigasAnagraficaSoggettiIdAnag(idAnag);
		if(elencoAnnualitaTotale!=null && !elencoAnnualitaTotale.isEmpty() ) {
			
			Iterator<String> iterator = elencoAnnualitaTotale.iterator();
			while(iterator.hasNext()) {
				String annualita = iterator.next();
				if(elencoAnnualitaIncorporatoMap!=null && 
				   !elencoAnnualitaIncorporatoMap.isEmpty() && 
				   elencoAnnualitaIncorporatoMap.containsKey(annualita)) 
				{
					elencoAnnualitaIncorporatoMap.remove(annualita);
				}
			}
			if(elencoAnnualitaIncorporatoMap!=null && !elencoAnnualitaIncorporatoMap.isEmpty()) {
				elencoAnnualitaTotale.addAll(new ArrayList<>(elencoAnnualitaIncorporatoMap.values()));
			}
		} else if(elencoAnnualitaIncorporatoMap!=null && !elencoAnnualitaIncorporatoMap.isEmpty()) {
			elencoAnnualitaTotale = new ArrayList<>(elencoAnnualitaIncorporatoMap.values());
		}
    	    	
		//return sigasDichVersamentiRepository.findDistinctBySigasAnagraficaSoggettiIdAnag(idAnag);
		return elencoAnnualitaTotale;
    }
	
    @Override
    public AnnualitaVersamentiVO ricercaAnnualitaVersamenti(Long idAnag) {
    	
    	AnnualitaVersamentiVO output = new AnnualitaVersamentiVO();
    	
    	//Lettura parametri applicativi
    	Integer limiteAnni = sigasCParametroRepository.findByDescParametro(PARAM_NUOVO_VERSAMENTO_ANNI_PREVISTI).getValoreNumber().intValue();
    	Integer annoRiferimento;
    	BigDecimal annoRiferimentoParam = sigasCParametroRepository.findByDescParametro(PARAM_NUOVO_VERSAMENTO_ANNO_RIFERIMENTO).getValoreNumber();    	
    	List<String> anniPresenti = new ArrayList<>();
    	if(annoRiferimentoParam==null) {
    		Calendar cal = Calendar.getInstance();        	
    		annoRiferimento = cal.get(Calendar.YEAR);    		
    	} else {
    		annoRiferimento = annoRiferimentoParam.intValue();
    	}
    	
    	anniPresenti.add(annoRiferimento.toString());
    	for(int i=1; i <= limiteAnni; i++) {
    		anniPresenti.add(String.valueOf(annoRiferimento-i));
    	}
    	
    	Collections.sort(anniPresenti, Collections.reverseOrder());    	
    	output.setLista_annualita(anniPresenti);
    	
    	List<String> annualitaVersamentiList = sigasDichVersamentiRepository.findDistinctBySigasAnagraficaSoggettiIdAnag(idAnag);
    	if(annualitaVersamentiList!=null && !annualitaVersamentiList.isEmpty()) {
    		output.setAnno_ultimo_versamento(annualitaVersamentiList.get(0));
    	}    	
    	return output;		
    }

	@Override
	public List<ProvinciaVO> ricercaProvinceVersamenti(Long id) {
		List<SigasProvincia> sigasProvinciaDB = sigasDichVersamentiRepository.findDistinctProvBySigasAnagraficaSoggettiIdAnag(id);
		List<ProvinciaVO> provinceList = new ArrayList<ProvinciaVO>();
		provinceList = provinciaEntityMapper.mapListEntityToListVO(sigasProvinciaDB);
		
		//Lettura oggetto società
		
		//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(id);
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(id);
		Map<String, ProvinciaVO> elencoProvinciaIncorporatoMap = null;
		if(sigasAnagraficaSoggetti!=null && sigasAnagraficaSoggetti.getIdFusione() > 0) {
			List<SigasProvincia> elencoProvinciaEntityIncorporato = sigasDichVersamentiRepository
													  		        .findDistinctProvBySigasAnagraficaSoggettiIdAnag(sigasAnagraficaSoggetti.getIdFusione());
			
			if(elencoProvinciaEntityIncorporato!=null && !elencoProvinciaEntityIncorporato.isEmpty()) {
				elencoProvinciaIncorporatoMap = new HashMap<>();
				Iterator<SigasProvincia> iterator = elencoProvinciaEntityIncorporato.iterator();
				while(iterator.hasNext()) {
					SigasProvincia sigasProvincia = iterator.next();
					elencoProvinciaIncorporatoMap.put(sigasProvincia.getSiglaProvincia(), provinciaEntityMapper.mapEntityToVO(sigasProvincia));					
				}
			}		
		}
		
		if(provinceList!=null && !provinceList.isEmpty()) {
			Iterator<ProvinciaVO> iterator = provinceList.iterator();
			while(iterator.hasNext()) {
				ProvinciaVO provinciaVO = iterator.next();
				if(elencoProvinciaIncorporatoMap!=null && 
				   !elencoProvinciaIncorporatoMap.isEmpty() &&
				   elencoProvinciaIncorporatoMap.get(provinciaVO.getSigla())!=null) 
				{
					elencoProvinciaIncorporatoMap.remove(provinciaVO.getSigla());
				}
			}
			
			if(elencoProvinciaIncorporatoMap!=null && 
			   !elencoProvinciaIncorporatoMap.isEmpty()) 
			{
				provinceList.addAll(new ArrayList<>(elencoProvinciaIncorporatoMap.values()));
			}			
		} else if(elencoProvinciaIncorporatoMap!=null && 
				  !elencoProvinciaIncorporatoMap.isEmpty()) 
		{
			provinceList = new ArrayList<ProvinciaVO>();
			provinceList.addAll(new ArrayList<>(elencoProvinciaIncorporatoMap.values()));
		}
		
		
		return provinceList;
	}

	@Override
	public List<TipoVersamentoVO> ricercaTipoVersamenti() {
		List<SigasTipoVersamento> tipoVersamentoDBList = sigasTipoVersamentoRepository.findAll();		
		return	tipoVersamentoEntityMapper.mapListEntityToListVO(tipoVersamentoDBList);
	}
	
	private List<String> creaMappaOridnataMesi(List<String> mesiDBList){
		if(mesiDBList==null) {
			return null;
		}
		Map<String, Integer> map = new HashMap<>();
		
		for (String mese : mesiDBList) {
			switch (StringUtils.upperCase(mese)) {
				case "GENNAIO": 
					map.put(mese,1);
					break;
				case "FEBBRAIO": 
					map.put(mese,2);
					break;
				case "MARZO": 
					map.put(mese,3);
					break;
				case "APRILE": 
					map.put(mese,4);
					break;
				case "MAGGIO": 
					map.put(mese,5);
					break;
				case "GIUGNO": 
					map.put(mese,6);
					break;
				case "LUGLIO": 
					map.put(mese,7);
					break;
				case "AGOSTO": 
					map.put(mese,8);
					break;
				case "SETTEMBRE": 
					map.put(mese,9);
					break;
				case "OTTOBRE": 
					map.put(mese,10);
					break;
				case "NOVEMBRE": 
					map.put(mese,11);
					break;
				case "DICEMBRE": 
					map.put(mese,12);
					break;
				default:
					map.put(mese, -1);
					break;
			}
		}
		
		List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(map.entrySet()); 
		
		// Sort the list 
	    Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
	         public int compare(Map.Entry<String, Integer> o1,  
	                            Map.Entry<String, Integer> o2) 
	         { 
	             return (o1.getValue()).compareTo(o2.getValue()); 
	         } 
	    });
	    
	    List<String> output = new ArrayList<>();
	    Iterator<Map.Entry<String, Integer>> iter = list.iterator();
	    while(iter.hasNext()) {
	    	output.add(iter.next().getKey());
	    }		
		return output;
	}

	@Override
	public List<String> ricercaMeseVersamenti(Long id, String annualita) {
		List<String> mesiVersamentoList = new ArrayList<String>();
		mesiVersamentoList.add("Tutti");
		List<String> mesiDBList = sigasDichVersamentiRepository.findDistinctMonthBySigasAnagraficaSoggettiIdAnag(id, annualita);
		for(String meseCurr : mesiDBList) {
			mesiVersamentoList.add(meseCurr);
		}
		
		//Lettura oggetto società
		//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(id);
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(id);
		Map<String, String> elencoMesiIncorporatoMap = null;
		if(sigasAnagraficaSoggetti!=null && sigasAnagraficaSoggetti.getIdFusione() > 0) {
			List<String> elencoMesiIncorporato = sigasDichVersamentiRepository
												 .findDistinctMonthBySigasAnagraficaSoggettiIdAnag(sigasAnagraficaSoggetti.getIdFusione(), annualita);
			
			if(elencoMesiIncorporato!=null && !elencoMesiIncorporato.isEmpty()) {
				elencoMesiIncorporatoMap = new HashMap<>();
				Iterator<String> iterator = elencoMesiIncorporato.iterator();
				while(iterator.hasNext()) {
					String meseIncorporato = iterator.next();
					elencoMesiIncorporatoMap.put(meseIncorporato, meseIncorporato);					
				}
			}			
		}		
		
		if(mesiVersamentoList!=null && !mesiVersamentoList.isEmpty()) {
			Iterator<String> iterator = mesiVersamentoList.iterator();
			while(iterator.hasNext()) {
				String mese = iterator.next();
				
				if(elencoMesiIncorporatoMap!=null && 
				   !elencoMesiIncorporatoMap.isEmpty() && 
				   elencoMesiIncorporatoMap.containsKey(mese)) 
				{
					elencoMesiIncorporatoMap.remove(mese);
				}				
			}
			if(elencoMesiIncorporatoMap!=null && !elencoMesiIncorporatoMap.isEmpty()) {				
				mesiVersamentoList.addAll(new ArrayList<>(elencoMesiIncorporatoMap.values()));				
			}
		} else if(elencoMesiIncorporatoMap!=null && !elencoMesiIncorporatoMap.isEmpty()) {
			mesiVersamentoList = new ArrayList<>(elencoMesiIncorporatoMap.values());
		}		
		
		return this.creaMappaOridnataMesi(mesiVersamentoList);
	}

	 @Override
	 public List<VersamentiPrVO> ricercaVersamenti(RicercaVersamentiRequest ricercaVersamentiRequest) {

			List<VersamentiPrVO> versamentiVOList = new ArrayList<VersamentiPrVO>();
			List<SigasDichVersamenti> dichVersamentiListDB = new ArrayList<SigasDichVersamenti>();
			
			//Controllo presenza fusione
			List<SigasDichVersamenti> dichVersamentiListDBSoggettoIncorporato = null;
			List<VersamentiPrVO> versamentiVOListSoggettoIncorporato = null;
			long idFusione = 0;
			Date dataFusione = null;
			//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(ricercaVersamentiRequest.getIdAnag());
			SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(ricercaVersamentiRequest.getIdAnag());
			if(sigasAnagraficaSoggetti.getIdFusione() > 0) {
				idFusione = sigasAnagraficaSoggetti.getIdFusione();
				dataFusione = sigasAnagraficaSoggetti.getDataFusione();
			}

			if (ricercaVersamentiRequest.getProvincia() != null && ricercaVersamentiRequest.getProvincia() == 0) {
				dichVersamentiListDB = sigasDichVersamentiRepository
									   .findBySigasAnagraficaSoggettiIdAnagAndAnnualitaOrderByDataVersamentoAsc(ricercaVersamentiRequest.getIdAnag(), 
											   																	ricercaVersamentiRequest.getAnno());
				if(idFusione > 0) {
					dichVersamentiListDBSoggettoIncorporato = sigasDichVersamentiRepository
							   			   .findBySigasAnagraficaSoggettiIdAnagAndAnnualitaOrderByDataVersamentoAsc(idFusione, 
									   																				ricercaVersamentiRequest.getAnno());
				}
				
			} else {
				dichVersamentiListDB = sigasDichVersamentiRepository
									   .findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasProvinciaIdProvinciaOrderByDataVersamentoAsc(ricercaVersamentiRequest.getIdAnag(), 
											   																								ricercaVersamentiRequest.getAnno(),
											   																								ricercaVersamentiRequest.getProvincia());
				
				if(idFusione > 0) {
					dichVersamentiListDBSoggettoIncorporato = sigasDichVersamentiRepository
							   			   					  .findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasProvinciaIdProvinciaOrderByDataVersamentoAsc(idFusione, 
									   																															   ricercaVersamentiRequest.getAnno(),
									   																															   ricercaVersamentiRequest.getProvincia());
				}
			}
				
			if (dichVersamentiListDB.size() > 0) {
				logger.debug("Trovati " + dichVersamentiListDB.size() + " versamenti");
				versamentiVOList = dichVersamentiEntityMapper.mapListEntityToListVO(dichVersamentiListDB);
			}			
			
			Map<VersamentiPrVO, Integer> map = new HashMap<>();			
			for (VersamentiPrVO versamento : versamentiVOList) {
				switch (StringUtils.upperCase(versamento.getMese())) {
					case "GENNAIO":
						versamento.setCodMese(1);
						map.put(versamento,1);
						break;
					case "FEBBRAIO": 
						versamento.setCodMese(2);
						map.put(versamento,2);
						break;
					case "MARZO": 
						versamento.setCodMese(3);
						map.put(versamento,3);
						break;
					case "APRILE":
						versamento.setCodMese(4);
						map.put(versamento,4);
						break;
					case "MAGGIO": 
						versamento.setCodMese(5);
						map.put(versamento,5);
						break;
					case "GIUGNO": 
						versamento.setCodMese(6);
						map.put(versamento,6);
						break;
					case "LUGLIO":
						versamento.setCodMese(7);
						map.put(versamento,7);
						break;
					case "AGOSTO": 
						versamento.setCodMese(8);
						map.put(versamento,8);
						break;
					case "SETTEMBRE":
						versamento.setCodMese(9);
						map.put(versamento,9);
						break;
					case "OTTOBRE":
						versamento.setCodMese(10);
						map.put(versamento,10);
						break;
					case "NOVEMBRE": 
						versamento.setCodMese(11);
						map.put(versamento,11);
						break;
					case "DICEMBRE":
						versamento.setCodMese(12);
						map.put(versamento,12);
						break;
				}
			}
			
			List<Map.Entry<VersamentiPrVO, Integer> > list = new LinkedList<Map.Entry<VersamentiPrVO, Integer> >(map.entrySet()); 
			
			// Sort the list 
	        Collections.sort(list, new Comparator<Map.Entry<VersamentiPrVO, Integer> >() { 
	            public int compare(Map.Entry<VersamentiPrVO, Integer> o1,  
	                               Map.Entry<VersamentiPrVO, Integer> o2) 
	            { 
	                return (o1.getValue()).compareTo(o2.getValue()); 
	            } 
	        });
	        
	        //GESTIONE SOGGETTO INCORPORATO
	        Map<String,  VersamentiPrVO> mappaVersamentiSoggettoIncorporato = null;
	        if (dichVersamentiListDBSoggettoIncorporato != null && dichVersamentiListDBSoggettoIncorporato.size() > 0) {
				logger.debug("Trovati " + dichVersamentiListDBSoggettoIncorporato.size() + " versamenti");
				versamentiVOListSoggettoIncorporato = dichVersamentiEntityMapper.mapListEntityToListVO(dichVersamentiListDBSoggettoIncorporato);
				mappaVersamentiSoggettoIncorporato = this._creaMappaVersamenti(versamentiVOListSoggettoIncorporato);				
			}
	        
	        HashMap<VersamentiPrVO, Integer> temp = new LinkedHashMap<VersamentiPrVO, Integer>(); 
	        for (Map.Entry<VersamentiPrVO, Integer> aa : list) {
	        	
	        	VersamentiPrVO versamentoPrVO =  aa.getKey();
	        		        		
        		String searchKey= String.valueOf(versamentoPrVO.getAnnualita()) + 
		      					  versamentoPrVO.getProvincia() + 
		      					  versamentoPrVO.getTipo().getIdTipoVersamento().toString() +
		      					  versamentoPrVO.getCodMese();
        		
		      	if(mappaVersamentiSoggettoIncorporato != null && !mappaVersamentiSoggettoIncorporato.isEmpty()) {
		      		VersamentiPrVO versamentoPrVOIncorporato = mappaVersamentiSoggettoIncorporato.get(searchKey);
		      		if(versamentoPrVOIncorporato!=null) 
		      		{	        			
		      			if(_checkApplicabilitaCalcoliFusione(versamentoPrVO, dataFusione)) 
			        	{
		      				versamentoPrVO.setImporto(versamentoPrVO.getImporto() + versamentoPrVOIncorporato.getImporto());
					        versamentoPrVO.setImporto_prec(versamentoPrVO.getImporto_prec() + versamentoPrVOIncorporato.getImporto_prec());
					        versamentoPrVO.setInteressi(versamentoPrVO.getInteressi() + versamentoPrVOIncorporato.getInteressi());
					        versamentoPrVO.setInteressiMora(versamentoPrVO.getInteressiMora() + versamentoPrVOIncorporato.getInteressiMora());
					        versamentoPrVO.setDifferenza(versamentoPrVO.getDifferenza()+ versamentoPrVOIncorporato.getDifferenza());
					        versamentoPrVO.setImportoComplessivo(versamentoPrVO.getImportoComplessivo() +versamentoPrVOIncorporato.getImportoComplessivo());
			        	}			      			        				        			
			        		
			        	mappaVersamentiSoggettoIncorporato.remove(searchKey);
		      		}	        			        		
		      	}    			        		        	
	        	
	        	temp.put(versamentoPrVO, aa.getValue());
	        	
	        	//OLD CODE
	            //temp.put(aa.getKey(), aa.getValue()); 
	        }	        
	        
	        List<VersamentiPrVO> versamentiPrVOListRimanenti = null;
	        List<VersamentiPrVO> versamentiPrVOListTotale = new ArrayList<VersamentiPrVO>(temp.keySet());
	        if(mappaVersamentiSoggettoIncorporato!=null && !mappaVersamentiSoggettoIncorporato.isEmpty()) {
	        	versamentiPrVOListRimanenti = new ArrayList<>(mappaVersamentiSoggettoIncorporato.values());
	        	versamentiPrVOListTotale.addAll(versamentiVOListSoggettoIncorporato);
	        	
	        	// Sort the list 
		        Collections.sort(versamentiPrVOListTotale, new Comparator<VersamentiPrVO>() {
					@Override
					public int compare(VersamentiPrVO o1, VersamentiPrVO o2) {						
						return Integer.valueOf(o1.getCodMese()).compareTo(Integer.valueOf(o2.getCodMese()));
					} 
		        });
	        }
	        
	        List<String> listKeyOper = new ArrayList<String>();
	        if(ricercaVersamentiRequest.getIdAnag()!=null) {
	        	listKeyOper.add(ricercaVersamentiRequest.getIdAnag().toString());
	        }
	        
			for (SigasDichVersamenti dichVersamentiTmp : dichVersamentiListDB) {
	    		listKeyOper.add(String.valueOf(dichVersamentiTmp.getIdVersamento()) );
			}			
	    	
	        CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,
	        													 "READ - ricercaVersamenti", 
	        													 "sigas_dich_versamenti", 
	        													 String.join("_", listKeyOper));
	        
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), 
											   csiLogAudit.getIdAddress(), csiLogAudit.getId().getUtente(), 
											   csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), 
											   csiLogAudit.getId().getKeyOper());	

			//return new ArrayList<VersamentiPrVO>(temp.keySet());
			return versamentiPrVOListTotale;
	 }
	 
	 private boolean _checkApplicabilitaCalcoliFusione(VersamentiPrVO versamentiPrVOIncorporante, Date dataFusione) {
		 if(dataFusione==null || versamentiPrVOIncorporante == null) {
			 return false;
		 } 
		 
		 
		 if(versamentiPrVOIncorporante.getInsDate()!=null && 
			(versamentiPrVOIncorporante.getInsDate().equals(dataFusione) ||	versamentiPrVOIncorporante.getInsDate().after(dataFusione))) 
		 {
			 return false;
		 }		 
		 
		 if(versamentiPrVOIncorporante.getModDate()!=null && 
			(versamentiPrVOIncorporante.getModDate().equals(dataFusione) ||	versamentiPrVOIncorporante.getModDate().after(dataFusione))) 
		 {
			 return false;
		 }
		 
		 return true;
	 }
	 
	 private Map<String, VersamentiPrVO> _creaMappaVersamenti(List<VersamentiPrVO> versamentiVOList){
	 	Map<String,  VersamentiPrVO> map = new HashMap<>();			
		for (VersamentiPrVO versamento : versamentiVOList) {
			String partialKey = String.valueOf(versamento.getAnnualita()) + 
								versamento.getProvincia() + 
								versamento.getTipo().getIdTipoVersamento().toString();
			switch (StringUtils.upperCase(versamento.getMese())) {				 
				case "GENNAIO":
					versamento.setCodMese(1);
					map.put(partialKey + "1" ,versamento);
					break;
				case "FEBBRAIO": 
					versamento.setCodMese(2);
					map.put(partialKey + "2" ,versamento);
					break;
				case "MARZO": 
					versamento.setCodMese(3);
					map.put(partialKey + "3" ,versamento);
					break;
				case "APRILE":
					versamento.setCodMese(4);
					map.put(partialKey + "4" ,versamento);
					break;
				case "MAGGIO": 
					versamento.setCodMese(5);
					map.put(partialKey + "5" ,versamento);
					break;
				case "GIUGNO": 
					versamento.setCodMese(6);
					map.put(partialKey + "6" ,versamento);
					break;
				case "LUGLIO":
					versamento.setCodMese(7);
					map.put(partialKey + "7" ,versamento);
					break;
				case "AGOSTO": 
					versamento.setCodMese(8);
					map.put(partialKey + "8" ,versamento);
					break;
				case "SETTEMBRE":
					versamento.setCodMese(9);
					map.put(partialKey + "9" ,versamento);
					break;
				case "OTTOBRE":
					versamento.setCodMese(10);
					map.put(partialKey + "10" ,versamento);
					break;
				case "NOVEMBRE": 
					versamento.setCodMese(11);
					map.put(partialKey + "11" ,versamento);
					break;
				case "DICEMBRE":
					versamento.setCodMese(12);
					map.put(partialKey + "12" ,versamento);
					break;
			}
		}		        
        return map;
	 }
	 
	@Override
	public void allarmeSoggetto(AllarmeRequest allarmeRequest, String user) {
				
      SigasAnagraficaSoggetti sogg = sigasAnagraficaSoggettiRepository.findByIdAnag(allarmeRequest.getIdAnag());
      SigasDichConsumi consumo = sigasDichConsumiRepository.findOne(allarmeRequest.getIdConsumi());
      SigasTipoAllarmi sigasTipoAllarmeVersamento = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(TipoAllarme.VERSAMENTO.getName());
      
      SigasAllarmi sigasAllarmi = sigasAllarmiRepository.findBySigasDichConsumiIdConsumiAndSigasTipoAllarmeIdTipoAllarme(consumo.getIdConsumi(), sigasTipoAllarmeVersamento.getIdTipoAllarme()!=null ? sigasTipoAllarmeVersamento.getIdTipoAllarme().intValue():0);
        
		if (null == sigasAllarmi) {

			sigasAllarmi = new SigasAllarmi();
			sigasAllarmi.setAttivazione(new Date());
			sigasAllarmi.setCodiceAzienda(sogg.getCodiceAzienda());
			sigasAllarmi.setSigasTipoAllarme(sigasTipoAllarmeVersamento);
			sigasAllarmi.setSigasDichConsumi(sigasDichConsumiRepository.findOne(allarmeRequest.getIdConsumi()));
			sigasAllarmi.setAnnualita(consumo.getAnnualita());
			sigasAllarmi.setUtente(user);
			sigasAllarmi.setNota("Mancato versamento per il soggetto " + allarmeRequest.getIdAnag());
			if (allarmeRequest.isStatus())
				sigasAllarmi.setStatus(StatusAllarme.ATTIVO.getName());
			else
				sigasAllarmi.setStatus(StatusAllarme.NON_ATTIVO.getName());

		} else {
			if (allarmeRequest.isStatus())
				sigasAllarmi.setStatus(StatusAllarme.ATTIVO.getName());
			else
				sigasAllarmi.setStatus(StatusAllarme.NON_ATTIVO.getName());
		}
		
		sigasAllarmiRepository.save(sigasAllarmi);
	}

	@Override
	public List<AllarmiSoggettoVO> ricercaAllarmi(Long idAnag, Long idTipoAllarme) {
	    //SigasAnagraficaSoggetti anagSogg = sigasAnagraficaSoggettiRepository.findOne(idAnag);
		SigasAnagraficaSoggetti anagSogg = sigasAnagraficaSoggettiRepository.findByIdAnag(idAnag);
	    List<AllarmiSoggettoVO> listAllarmiVo = new ArrayList<AllarmiSoggettoVO>();
		if(anagSogg!=null) {
	    	List<SigasAllarmi> allarmDBList = sigasAllarmiRepository.findByCodiceAziendaAndSigasTipoAllarmeIdTipoAllarme(anagSogg.getCodiceAzienda(), idTipoAllarme!=null ? idTipoAllarme.intValue():0);
	    	listAllarmiVo =  allarmiSoggettoEntityMapper.mapListEntityToListVO(allarmDBList);
	    	
	    	//Gestione Fusione
			//----------------------------------------------------
			if(anagSogg.getIdFusione() > 0) {
				//SigasAnagraficaSoggetti anagSoggIncorporato = sigasAnagraficaSoggettiRepository.findOne(anagSogg.getIdFusione());
				SigasAnagraficaSoggetti anagSoggIncorporato = sigasAnagraficaSoggettiRepository.findByIdAnag(anagSogg.getIdFusione());
				List<SigasAllarmi> allarmDBListIncorporato = sigasAllarmiRepository.findByCodiceAziendaAndSigasTipoAllarmeIdTipoAllarme(anagSoggIncorporato.getCodiceAzienda(), 
																																		idTipoAllarme!=null ? idTipoAllarme.intValue():0);
				if(allarmDBListIncorporato!=null && !allarmDBListIncorporato.isEmpty()) {
					List<AllarmiSoggettoVO> listAllarmiVoIncorporato = new ArrayList<AllarmiSoggettoVO>();
					listAllarmiVoIncorporato =  allarmiSoggettoEntityMapper.mapListEntityToListVO(allarmDBListIncorporato);
					listAllarmiVo.addAll(listAllarmiVoIncorporato);
				}
		    	
			}		
			//----------------------------------------------------
	    }	
		
	    return listAllarmiVo;
	}
	
	
	@Override
	public AllarmiSoggettoVO ricercaAllarmiVersamento(Long idConsumi) {
		SigasTipoAllarmi sigasTipoAllarmeVersamento = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(TipoAllarme.VERSAMENTO.getName());
    	SigasAllarmi allarmDB = sigasAllarmiRepository
    							.findBySigasDichConsumiIdConsumiAndSigasTipoAllarmeIdTipoAllarme(idConsumi, 
    																							 sigasTipoAllarmeVersamento.getIdTipoAllarme()!=null 
    																							 ? sigasTipoAllarmeVersamento.getIdTipoAllarme().intValue()
    																							 :0);   	
    	
    	
    	return allarmiSoggettoEntityMapper.mapEntityToVO(allarmDB);
	}
	
	@Override
	@Transactional
	public void insertVersamento(ConfermaVersamentoRequest confermaVersamentoRequest,String user, String codFiscale) {
		this._insertVersamento(confermaVersamentoRequest, user, codFiscale);		
	}
	
	
	@Override
	@Transactional
	public List<PagamentiVersamentiVO> insertVersamentoContabilia(ConfermaVersamentoContabiliaRequest confermaVersamentoContabiliaRequest,String user, String codFiscale) {
		SigasAllarmi sigasAllarmi;
		List<String> listKeyOper = new ArrayList<String>();
		String oggOper ="";
		SigasProvincia provVersamento = sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(confermaVersamentoContabiliaRequest.getVersamento().getProvincia());
		//Controllo esistenza
		SigasDichVersamenti versamento = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndMeseAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaIdProvincia(
				confermaVersamentoContabiliaRequest.getVersamento().getIdAnag(),
				confermaVersamentoContabiliaRequest.getVersamento().getMese(),
				confermaVersamentoContabiliaRequest.getVersamento().getTipo().getIdTipoVersamento(),
				String.valueOf(confermaVersamentoContabiliaRequest.getVersamento().getAnnualita()),
				provVersamento.getIdProvincia());
		
		if(versamento != null  && !confermaVersamentoContabiliaRequest.isConfermaInserimento()) {
			throw new BusinessException("Versamento gia' presente", "VGP");
		}else if(versamento !=null && confermaVersamentoContabiliaRequest.isConfermaInserimento()) {
			SigasPagamentiVersamenti sigasPagamentiVersamentiToDelete = sigasPagamentiVersamentiRepository.findBySigasDichVersamentiIdVersamento(versamento.getIdVersamento());
			if(sigasPagamentiVersamentiToDelete!=null) {
				sigasPagamentiVersamentiRepository.delete(sigasPagamentiVersamentiToDelete);
			}			
			
			sigasDichVersamentiRepository.save(versamento);
		}		
		
		SigasTipoVersamento tipoVersamento = sigasTipoVersamentoRepository.findOne(confermaVersamentoContabiliaRequest.getVersamento().getTipo().getIdTipoVersamento());
		if(tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.CONGUAGLIO.getName())){
			//Il versamento a conquaglio deve essere effettuato entro il mese di Marzo dell'anno successivo
			//a quello cui si riferisce la dichiarazione
			Date dataVersamentoAConguaglio = Utilities.dataVersamentoConguaglio(confermaVersamentoContabiliaRequest.getVersamento().getAnnualita());
			if(dataVersamentoAConguaglio.before(Utilities.azzeraOraMinuti(confermaVersamentoContabiliaRequest.getVersamento().getDataVersamento())))
						throw new BusinessException("Il versamento a conguaglio va effettuato entro marzo dell'anno successivo a quello cui si riferisce la dichiarazione");
		}

		SigasPagamentiVersamenti sigasPagamentiVersamenti=null;
		if (confermaVersamentoContabiliaRequest.getVersamento().getConsumo() == null && 
			(tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.ACCERTAMENTO.getName()) || 
			 tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.RAVVEDIMENTO.getName()))) 
		{			
			throw new BusinessException("Il versamento di tipo " + 
										tipoVersamento.getDenominazione() + 
										" deve essere relativo ad un consumo");

		} else {

			SigasDichVersamenti sigasVersamento = dichVersamentiEntityMapper
												  .mapVOtoEntity(confermaVersamentoContabiliaRequest.getVersamento());
			sigasVersamento.setInsDate(new Date());
			sigasVersamento.setInsUser(codFiscale);
			SigasDichVersamenti versamentoSavedDB = sigasDichVersamentiRepository.save(sigasVersamento);
			VersamentiPrVO versamentoVO = dichVersamentiEntityMapper.mapEntityToVO(versamentoSavedDB);			
			
			//Inserito per contabilia   
			PagamentiVersamentiVO pagamentiVersamenti = new PagamentiVersamentiVO();
			pagamentiVersamenti.setFkPagamento(confermaVersamentoContabiliaRequest.getPagamento().getIdPagamento());
			pagamentiVersamenti.setFkAnag(anagraficaSoggettiEntityMapper.mapEntityToVO(sigasAnagraficaSoggettiRepository.findByIdAnag(confermaVersamentoContabiliaRequest.getVersamento().getIdAnag())) );
			pagamentiVersamenti.setFkVersamento(dichVersamentiEntityMapper.mapEntityToVO(versamentoSavedDB));
			pagamentiVersamenti.setAnno(Integer.valueOf(confermaVersamentoContabiliaRequest.getVersamento().getAnnualita()).toString());
			pagamentiVersamenti.setDataVersamento(new Timestamp(confermaVersamentoContabiliaRequest.getVersamento().getDataVersamento().getTime()));
			pagamentiVersamenti.setFkProvincia(provinciaEntityMapper.mapEntityToVO(provVersamento) );
			
			pagamentiVersamenti.setIdTipoVersamento(confermaVersamentoContabiliaRequest.getVersamento().getTipo() );
			pagamentiVersamenti.setImporto(BigDecimal.valueOf(confermaVersamentoContabiliaRequest.getVersamento().getImporto()) );
			pagamentiVersamenti.setMese(confermaVersamentoContabiliaRequest.getVersamento().getMese());
			
			SigasPagamentiVersamenti sigasPagamentiVers = pagamentiVersamentiEntityMapper.mapVOtoEntity(pagamentiVersamenti);
			sigasPagamentiVersamenti = sigasPagamentiVersamentiRepository.save(sigasPagamentiVers);			
			//Fine inserimento per contabilia

			oggOper += "sigas_dich_versamenti ";
			listKeyOper.add(String.valueOf(versamentoSavedDB.getIdVersamento()) );
			if (tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.ACCERTAMENTO.getName())) {
				sigasAllarmi = sigasAllarmiRepository.save(allarmeAccertamento(versamentoVO, TipoVersamenti.ACCERTAMENTO.getName(), user, StatusAllarme.NON_ATTIVO.getName()));
				oggOper += "sigas_allarmi ";
				listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
			}
			if (tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.RAVVEDIMENTO.getName())) {
				sigasAllarmi = sigasAllarmiRepository.save(allarmeAccertamento(versamentoVO, TipoVersamenti.RAVVEDIMENTO.getName(), user, StatusAllarme.ATTIVO.getName()));
				oggOper += "sigas_allarmi ";
				listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
			}
		}
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERT - insertVersamentoContabilia", oggOper,String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		
		
		csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERT - insertVersamentoContabilia", "sigas_pagamenti_versamenti",String.valueOf(sigasPagamentiVersamenti.getIdPagamentoVersamento()) );
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		
		
		if(confermaVersamentoContabiliaRequest.isConciliato()) {
			confermaVersamentoContabiliaRequest.getPagamento().setConciliato(true);
			SigasPagamenti sigasPagamentiUpdate = ordinativoEntityMapper.mapVOtoEntity(confermaVersamentoContabiliaRequest.getPagamento());

			
			sigasPagamentiCrudRepository.save(sigasPagamentiUpdate);
			
			csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"UPDATE - conciliaPagamento", "sigas_pagamenti",String.valueOf(sigasPagamentiUpdate.getIdPagamento()));
			csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
					csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		}
		
		SigasPagamenti sigasPagamenti = sigasPagamentiCrudRepository.findOne(confermaVersamentoContabiliaRequest.getPagamento().getIdPagamento());
		if(sigasPagamentiVersamenti!=null && sigasPagamenti!=null && sigasPagamenti.getSigasPagamentiVersamentis()!=null) {
			sigasPagamenti.getSigasPagamentiVersamentis().add(sigasPagamentiVersamenti);
			return pagamentiVersamentiEntityMapper.mapListEntityToListVO(sigasPagamenti.getSigasPagamentiVersamentis());
		}
		
		return null;
		
	}

	@Override
	@Transactional
	public void updateVersamento(ConfermaVersamentoRequest confermaVersamentoRequest, String user, String codFiscale) {
		SigasAllarmi sigasAllarmi;
		List<String> listKeyOper = new ArrayList<String>();
		String oggOper ="";
		SigasProvincia provVersamento = sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(confermaVersamentoRequest.getVersamento().getProvincia());
		//Controllo esistenza
		SigasDichVersamenti versamento = sigasDichVersamentiRepository.findByIdVersamentoNotAndMeseAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaIdProvinciaAndSigasAnagraficaSoggettiIdAnag(
				confermaVersamentoRequest.getVersamento().getIdVersamento(),
				confermaVersamentoRequest.getVersamento().getMese(),
				confermaVersamentoRequest.getVersamento().getTipo().getIdTipoVersamento(),
				String.valueOf(confermaVersamentoRequest.getVersamento().getAnnualita()),
				provVersamento.getIdProvincia(),
				confermaVersamentoRequest.getVersamento().getIdAnag());
		
		if(versamento != null) {
			throw new BusinessException("Versamento gia' presente");
		}
		
		//GESTIONE FUSIONE
		long idFusione = 0;
		Date dataFusione = null;
		List<SigasDichVersamenti> dichiarazioneVersamentiIncorporatoList = null;
		//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(confermaVersamentoRequest.getVersamento().getIdAnag());
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(confermaVersamentoRequest.getVersamento().getIdAnag());
		if(sigasAnagraficaSoggetti.getIdFusione() > 0) {
			idFusione = sigasAnagraficaSoggetti.getIdFusione();
			dataFusione = sigasAnagraficaSoggetti.getDataFusione();
			SigasDichVersamenti versamentoIncorporato = sigasDichVersamentiRepository.findById(confermaVersamentoRequest.getVersamento().getIdVersamento());
			if(versamentoIncorporato!=null && versamentoIncorporato.getSigasAnagraficaSoggetti().getIdAnag() == idFusione) {
				throw new BusinessException("Versamento associato a Soggetto coinvolto in un'azione di FUSIONE. Impossibile portare a termine l'operazione di aggiornamento.");
			}
			
			/*
			 * ATTENZIONE
			 * -----------------------------------------------------------------
			 * 
			 * CI POSSONO ESSERE PIU' Versamenti per stessa combinazione di idAnag, Annualità, Prov, idTipologiaVersamento
			 * 
			 * -----------------------------------------------------------------
			 */
			
			
			dichiarazioneVersamentiIncorporatoList = sigasDichVersamentiRepository
												     .findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndMeseAndSigasTipoVersamentoIdTipoVersamentoAndSigasProvinciaIdProvincia(
												    		idFusione, 
															String.valueOf(confermaVersamentoRequest.getVersamento().getAnnualita()), 
															confermaVersamentoRequest.getVersamento().getMese(), 
															confermaVersamentoRequest.getVersamento().getTipo().getIdTipoVersamento(), 
															provVersamento.getIdProvincia());
			
		}		
		//-----------------
		
		
		SigasTipoVersamento tipoVersamento = sigasTipoVersamentoRepository.findOne(confermaVersamentoRequest.getVersamento().getTipo().getIdTipoVersamento());
		if(tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.CONGUAGLIO.getName())){
			//Data fine versamento a conguaglio
			Date dataFineVersamentoConguaglio = Utilities.dataVersamentoConguaglio(confermaVersamentoRequest.getVersamento().getAnnualita());
			if(dataFineVersamentoConguaglio.before(Utilities.azzeraOraMinuti(confermaVersamentoRequest.getVersamento().getDataVersamento())))
						throw new BusinessException("Il versamento a conguaglio va effettuato entro marzo dell'anno successivo a quello cui si riferisce la dichiarazione");
		}
		SigasDichVersamenti sigasVersamento = dichVersamentiEntityMapper.mapVOtoEntity(confermaVersamentoRequest.getVersamento());
		sigasVersamento.setModDate(new Date());
		sigasVersamento.setModUser(codFiscale);
		SigasDichVersamenti versamentoSavedDB = sigasDichVersamentiRepository.save(sigasVersamento);
		
		//GESTIONE FUSIONE
		//----------------------------------------------------
		if(dichiarazioneVersamentiIncorporatoList!=null && !dichiarazioneVersamentiIncorporatoList.isEmpty()) {
			Iterator<SigasDichVersamenti> iterator = dichiarazioneVersamentiIncorporatoList.iterator();
			while(iterator.hasNext()) {
				SigasDichVersamenti sigasDichVersamentiIncorparatoDaEscludere = iterator.next();
				
				sigasDichVersamentiIncorparatoDaEscludere.setModDate(new Date());
				sigasDichVersamentiIncorparatoDaEscludere.setModUser(Constants.OWNER_UPDATE_VERSAMENTI_FUSIONE);			
				
				sigasDichVersamentiRepository.save(sigasDichVersamentiIncorparatoDaEscludere);
			}
		}		
		//----------------------------------------------------
		
		oggOper += "sigas_dich_versamenti ";
		listKeyOper.add(String.valueOf(versamentoSavedDB.getIdVersamento()) );		
		if(tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.ACCERTAMENTO.getName())){
			sigasAllarmi = sigasAllarmiRepository.save(allarmeAccertamento(confermaVersamentoRequest.getVersamento(), TipoVersamenti.ACCERTAMENTO.getName(),user, StatusAllarme.NON_ATTIVO.getName()));
			oggOper += "sigas_allarmi ";
			listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
		}
		if(tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.RAVVEDIMENTO.getName())){
			sigasAllarmi = sigasAllarmiRepository.save(allarmeAccertamento(confermaVersamentoRequest.getVersamento(), TipoVersamenti.RAVVEDIMENTO.getName(), user, StatusAllarme.ATTIVO.getName()));
			oggOper += "sigas_allarmi ";
			listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
		}
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"UPDATE - updateVersamento", oggOper, String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
	}
	
	private SigasAllarmi allarmeAccertamento(VersamentiPrVO versamento,String tipoAllarme, String user, int statusAllarme) {
		SigasTipoAllarmi sigasTipoAllarme = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(tipoAllarme);
		SigasAnagraficaSoggetti sogg = sigasAnagraficaSoggettiRepository.findByIdAnag(versamento.getIdAnag());
		SigasDichConsumi consumo = null;
		
		if (versamento.getConsumo() != null) {
			consumo = sigasDichConsumiRepository.findOne(versamento.getConsumo().getId_consumi());
		}			    
	    SigasAllarmi sigasAllarmi = new SigasAllarmi();
			sigasAllarmi.setAttivazione(new Date());
			sigasAllarmi.setCodiceAzienda(sogg.getCodiceAzienda());
			sigasAllarmi.setSigasTipoAllarme(sigasTipoAllarme);
			sigasAllarmi.setSigasDichConsumi(consumo != null ? consumo : null);
			sigasAllarmi.setAnnualita(String.valueOf(versamento.getAnnualita()));
			sigasAllarmi.setUtente(user);
			sigasAllarmi.setNota("Allarme "+ tipoAllarme + " per id versamento - " + versamento.getIdVersamento());			
			sigasAllarmi.setStatus(statusAllarme);
			sigasAllarmi.setSigasDichVersamenti(sigasDichVersamentiRepository.findOne(versamento.getIdVersamento()));
			
		return sigasAllarmi;
	}

	private void _insertVersamento(ConfermaVersamentoRequest confermaVersamentoRequest,String user, String codFiscale){
		SigasAllarmi sigasAllarmi;
		List<String> listKeyOper = new ArrayList<String>();
		String oggOper ="";
		SigasProvincia provVersamento = sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(confermaVersamentoRequest.getVersamento().getProvincia());
		//Controllo esistenza
		SigasDichVersamenti versamento = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndMeseAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaIdProvincia(
				confermaVersamentoRequest.getVersamento().getIdAnag(),
				confermaVersamentoRequest.getVersamento().getMese(),
				confermaVersamentoRequest.getVersamento().getTipo().getIdTipoVersamento(),
				String.valueOf(confermaVersamentoRequest.getVersamento().getAnnualita()),
				provVersamento.getIdProvincia());
		
		if(versamento != null) {
			throw new BusinessException("Versamento gia' presente");
		}
		
		//GESTIONE FUSIONE
		long idFusione = 0;
		Date dataFusione = null;		
		//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(confermaVersamentoRequest.getVersamento().getIdAnag());
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(confermaVersamentoRequest.getVersamento().getIdAnag());
		if(sigasAnagraficaSoggetti.getIdFusione() > 0) {
			idFusione = sigasAnagraficaSoggetti.getIdFusione();
			dataFusione = sigasAnagraficaSoggetti.getDataFusione();
			SigasDichVersamenti versamentoIncorporato = sigasDichVersamentiRepository.findById(confermaVersamentoRequest.getVersamento().getIdVersamento());
			if(versamentoIncorporato!=null && versamentoIncorporato.getSigasAnagraficaSoggetti().getIdAnag() == idFusione) {
				throw new BusinessException("Versamento associato a Soggetto coinvolto in un'azione di FUSIONE. Impossibile portare a termine l'operazione di cancellazione.");
			}			
		}		
		//-----------------
		
		SigasTipoVersamento tipoVersamento = sigasTipoVersamentoRepository.findOne(confermaVersamentoRequest.getVersamento().getTipo().getIdTipoVersamento());
		if(tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.CONGUAGLIO.getName())){
			//Il versamento a conquaglio deve essere effettuato entro il mese di Marzo dell'anno successivo
			//a quello cui si riferisce la dichiarazione
			Date dataVersamentoAConguaglio = Utilities.dataVersamentoConguaglio(confermaVersamentoRequest.getVersamento().getAnnualita());
			if(dataVersamentoAConguaglio.before(Utilities.azzeraOraMinuti(confermaVersamentoRequest.getVersamento().getDataVersamento())))
						throw new BusinessException("Il versamento a conguaglio va effettuato entro marzo dell'anno successivo a quello cui si riferisce la dichiarazione");
		}

		if (confermaVersamentoRequest.getVersamento().getConsumo() == null && 
			(tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.ACCERTAMENTO.getName()) || 
			 tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.RAVVEDIMENTO.getName()))) 
		{			
			throw new BusinessException("Il versamento di tipo " + 
										tipoVersamento.getDenominazione() + 
										" deve essere relativo ad un consumo");

		} else {

			SigasDichVersamenti sigasVersamento = dichVersamentiEntityMapper
					.mapVOtoEntity(confermaVersamentoRequest.getVersamento());
			sigasVersamento.setInsDate(new Date());
			sigasVersamento.setInsUser(codFiscale);
			SigasDichVersamenti versamentoSavedDB = sigasDichVersamentiRepository.save(sigasVersamento);
			VersamentiPrVO versamentoVO = dichVersamentiEntityMapper.mapEntityToVO(versamentoSavedDB);

			oggOper += "sigas_dich_versamenti ";
			listKeyOper.add(String.valueOf(versamentoSavedDB.getIdVersamento()) );
			if (tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.ACCERTAMENTO.getName())) {
				sigasAllarmi = sigasAllarmiRepository.save(allarmeAccertamento(versamentoVO, TipoVersamenti.ACCERTAMENTO.getName(), user, StatusAllarme.NON_ATTIVO.getName()));
				oggOper += "sigas_allarmi ";
				listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
			}
			if (tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.RAVVEDIMENTO.getName())) {
				sigasAllarmi = sigasAllarmiRepository.save(allarmeAccertamento(versamentoVO, TipoVersamenti.RAVVEDIMENTO.getName(), user, StatusAllarme.ATTIVO.getName()));
				oggOper += "sigas_allarmi ";
				listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
			} 
		}
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERT - insertVersamento", oggOper,String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
		
	}
	
	
	@Override
	@Transactional
	public void insertVersamentoList(List<ConfermaVersamentoRequest> confermaVersamentoRequestList,String user, String codFiscale) {		
		List<String> listKeyOper = new ArrayList<>();
		String oggOper ="";
		
		if(confermaVersamentoRequestList == null || confermaVersamentoRequestList.isEmpty()) {
			throw new BusinessException("Elenco versamenti vuoto");
		}
		
		Iterator<ConfermaVersamentoRequest> iterator = confermaVersamentoRequestList.iterator();
		while(iterator.hasNext()) {
			ConfermaVersamentoRequest confermaVersamentoRequest = iterator.next();
			this._insertVersamento(confermaVersamentoRequest, user, codFiscale);
		}				
		
		oggOper += "sigas_dich_versamenti ";
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERT - insertVersamentoList", oggOper, String.join("_", listKeyOper));
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
										   csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), 
										   csiLogAudit.getId().getKeyOper());	
		
	}
	
	@Override
	@Transactional
	public boolean deleteVersamento(Long idVersamento,Long idAnag, String user, String codFiscale) {
		
		 try {
	            SigasDichVersamenti versamentoToDelete= this.sigasDichVersamentiRepository.findById(idVersamento);
	            if (versamentoToDelete == null) {
	                throw new BusinessException("Nessun versamento trovato per l'ID: " + idVersamento);
	            }
	            
	            //GESTIONE FUSIONE
	    		long idFusione = 0;
	    		Date dataFusione = null;
	    		//SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findOne(idAnag);
	    		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(idAnag);
	    		if(sigasAnagraficaSoggetti.getIdFusione() > 0) {
	    			idFusione = sigasAnagraficaSoggetti.getIdFusione();
	    			dataFusione = sigasAnagraficaSoggetti.getDataFusione();
	    			SigasDichVersamenti versamentoIncorporato = sigasDichVersamentiRepository.findById(idVersamento);
	    			if(versamentoIncorporato!=null && versamentoIncorporato.getSigasAnagraficaSoggetti().getIdAnag() == idFusione) {
	    				throw new BusinessException("Versamento associato a Soggetto coinvolto in un'azione di FUSIONE. Impossibile portare a termine l'operazione.");
	    			}
	    		}		
	    		//-----------------
	            
	            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    		Object principal = auth.getPrincipal();
	    		UserDetails utente = (UserDetails) principal;	            
	            
	            this.sigasDichVersamentiRepository.delete(idVersamento);			
	            CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"CANCELLAZIONE VERSAMENTO", "sigas_dich_versamenti",String.valueOf(idVersamento));
				csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
												   csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), 
												   csiLogAudit.getId().getKeyOper());			
				
	            return true;
	        } catch (Exception e) {
	            this.logger.error("deleteVersamento: {}", e.getMessage());
	            //return false;
	            throw e;
	        }
		
	}
	
}
