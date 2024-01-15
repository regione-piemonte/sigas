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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.common.StatusAllarme;
import it.csi.sigas.sigasbl.common.TipoAllarme;
import it.csi.sigas.sigasbl.common.TipoVersamenti;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.common.utils.Utilities;
import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.SigasAllarmi;
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
	
//	@Autowired
//	private SigasPagamentiRepository sigasPagamentiRepository;
	
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
    	    	
		return sigasDichVersamentiRepository.findDistinctBySigasAnagraficaSoggettiIdAnag(idAnag);
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
    	if(annualitaVersamentiList!=null & !annualitaVersamentiList.isEmpty()) {
    		output.setAnno_ultimo_versamento(annualitaVersamentiList.get(0));
    	}    	
    	return output; 	
    	
    	    	
		//return sigasDichVersamentiRepository.findDistinctBySigasAnagraficaSoggettiIdAnag(idAnag);
    }

	@Override
	public List<ProvinciaVO> ricercaProvinceVersamenti(Long id) {
		List<SigasProvincia> sigasProvinciaDB = sigasDichVersamentiRepository.findDistinctProvBySigasAnagraficaSoggettiIdAnag(id);
		List<ProvinciaVO> provinceList = new ArrayList<ProvinciaVO>();
		provinceList = provinciaEntityMapper.mapListEntityToListVO(sigasProvinciaDB);
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
		 return this.creaMappaOridnataMesi(mesiVersamentoList);
	}

	 @Override
	 public List<VersamentiPrVO> ricercaVersamenti(RicercaVersamentiRequest ricercaVersamentiRequest) {

			List<VersamentiPrVO> versamentiVOList = new ArrayList<VersamentiPrVO>();
			List<SigasDichVersamenti> dichVersamentiListDB = new ArrayList<SigasDichVersamenti>();

			if (ricercaVersamentiRequest.getProvincia() != null && ricercaVersamentiRequest.getProvincia() == 0) {
				dichVersamentiListDB = sigasDichVersamentiRepository
						.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaOrderByDataVersamentoAsc(
								ricercaVersamentiRequest.getIdAnag(), ricercaVersamentiRequest.getAnno());
			} else {
				dichVersamentiListDB = sigasDichVersamentiRepository
						.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasProvinciaIdProvinciaOrderByDataVersamentoAsc(
								ricercaVersamentiRequest.getIdAnag(), ricercaVersamentiRequest.getAnno(),
								ricercaVersamentiRequest.getProvincia());
			}
				
				if (dichVersamentiListDB.size() > 0) {
					logger.debug("Trovati " + dichVersamentiListDB.size() + " versamenti");
					versamentiVOList = dichVersamentiEntityMapper.mapListEntityToListVO(dichVersamentiListDB);
				}
				
				Map<VersamentiPrVO, Integer> map = new HashMap<>();
				
				for (VersamentiPrVO versamento : versamentiVOList) {
					switch (StringUtils.upperCase(versamento.getMese())) {
						case "GENNAIO": 
							map.put(versamento,1);
							break;
						case "FEBBRAIO": 
							map.put(versamento,2);
							break;
						case "MARZO": 
							map.put(versamento,3);
							break;
						case "APRILE": 
							map.put(versamento,4);
							break;
						case "MAGGIO": 
							map.put(versamento,5);
							break;
						case "GIUGNO": 
							map.put(versamento,6);
							break;
						case "LUGLIO": 
							map.put(versamento,7);
							break;
						case "AGOSTO": 
							map.put(versamento,8);
							break;
						case "SETTEMBRE": 
							map.put(versamento,9);
							break;
						case "OTTOBRE": 
							map.put(versamento,10);
							break;
						case "NOVEMBRE": 
							map.put(versamento,11);
							break;
						case "DICEMBRE": 
							map.put(versamento,12);
							break;
					}
				}
				
				List<Map.Entry<VersamentiPrVO, Integer> > list = 
			               new LinkedList<Map.Entry<VersamentiPrVO, Integer> >(map.entrySet()); 
				
				// Sort the list 
		        Collections.sort(list, new Comparator<Map.Entry<VersamentiPrVO, Integer> >() { 
		            public int compare(Map.Entry<VersamentiPrVO, Integer> o1,  
		                               Map.Entry<VersamentiPrVO, Integer> o2) 
		            { 
		                return (o1.getValue()).compareTo(o2.getValue()); 
		            } 
		        }); 
		        
		        HashMap<VersamentiPrVO, Integer> temp = new LinkedHashMap<VersamentiPrVO, Integer>(); 
		        for (Map.Entry<VersamentiPrVO, Integer> aa : list) { 
		            temp.put(aa.getKey(), aa.getValue()); 
		        } 
		        
		        
		        List<String> listKeyOper = new ArrayList<String>();
		        if(ricercaVersamentiRequest.getIdAnag()!=null) {
		        	listKeyOper.add(ricercaVersamentiRequest.getIdAnag().toString());
		        }
		        
				for (SigasDichVersamenti dichVersamentiTmp : dichVersamentiListDB) {
		    		listKeyOper.add(String.valueOf(dichVersamentiTmp.getIdVersamento()) );
				}
				
		    	
		        CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"READ - ricercaVersamenti", "sigas_dich_versamenti", String.join("_", listKeyOper));
//				csiLogAuditRepository.save(csiLogAudit);
				csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
						csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	

				return new ArrayList<VersamentiPrVO>(temp.keySet());
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
	    SigasAnagraficaSoggetti anagSogg = sigasAnagraficaSoggettiRepository.findOne(idAnag);
	    List<AllarmiSoggettoVO> listAllarmiVo = new ArrayList<AllarmiSoggettoVO>();
		if(anagSogg!=null) {
	    	List<SigasAllarmi> allarmDBList = sigasAllarmiRepository.findByCodiceAziendaAndSigasTipoAllarmeIdTipoAllarme(anagSogg.getCodiceAzienda(), idTipoAllarme!=null ? idTipoAllarme.intValue():0);
	    	listAllarmiVo =  allarmiSoggettoEntityMapper.mapListEntityToListVO(allarmDBList);
	    }
	    return listAllarmiVo;
	}
	
	
	@Override
	public AllarmiSoggettoVO ricercaAllarmiVersamento(Long idConsumi) {
		SigasTipoAllarmi sigasTipoAllarmeVersamento = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(TipoAllarme.VERSAMENTO.getName());
    	SigasAllarmi allarmDB = sigasAllarmiRepository.findBySigasDichConsumiIdConsumiAndSigasTipoAllarmeIdTipoAllarme(idConsumi, sigasTipoAllarmeVersamento.getIdTipoAllarme()!=null ? sigasTipoAllarmeVersamento.getIdTipoAllarme().intValue():0);
    	return allarmiSoggettoEntityMapper.mapEntityToVO(allarmDB);
	}
	
	@Override
	@Transactional
	public void insertVersamento(ConfermaVersamentoRequest confermaVersamentoRequest,String user, String codFiscale) {
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
		
		SigasTipoVersamento tipoVersamento = sigasTipoVersamentoRepository.findOne(confermaVersamentoRequest.getVersamento().getTipo().getIdTipoVersamento());
		if(tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.CONGUAGLIO.getName())){
			//Il versamento a conquaglio deve essere effettuato entro il mese di Marzo dell'anno successivo
			//a quello cui si riferisce la dichiarazione
			Date dataVersamentoAConguaglio = Utilities.dataVersamentoConguaglio(confermaVersamentoRequest.getVersamento().getAnnualita());
			if(dataVersamentoAConguaglio.before(Utilities.azzeraOraMinuti(confermaVersamentoRequest.getVersamento().getDataVersamento())))
						throw new BusinessException("Il versamento a conguaglio va effettuato entro marzo dell'anno successivo a quello cui si riferisce la dichiarazione");
		}

		if (confermaVersamentoRequest.getVersamento().getConsumo() == null
				&& (tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.ACCERTAMENTO.getName())
						|| tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.RAVVEDIMENTO.getName()))) {
			// errore
			throw new BusinessException(
					"Il versamento di tipo "+ tipoVersamento.getDenominazione() +" deve essere relativo ad un consumo");

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
//		csiLogAuditRepository.save(csiLogAudit);
		csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(), 
				csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());	
		
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
		}else if(versamento !=null && confermaVersamentoContabiliaRequest.isConfermaInserimento()){
			SigasPagamentiVersamenti sigasPagamentiVersamentiToDelete = sigasPagamentiVersamentiRepository.findBySigasDichVersamentiIdVersamento(versamento.getIdVersamento());
			if(sigasPagamentiVersamentiToDelete!=null) {
				sigasPagamentiVersamentiRepository.delete(sigasPagamentiVersamentiToDelete);
			}
			
			
			sigasDichVersamentiRepository.save(versamento);
		}
		
		//Inserito per contabilia
//		List<SigasDichVersamenti> versamentoList = sigasDichVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndSigasTipoVersamentoIdTipoVersamentoAndAnnualitaAndSigasProvinciaIdProvincia(
//				confermaVersamentoContabiliaRequest.getVersamento().getIdAnag(),
//				confermaVersamentoContabiliaRequest.getVersamento().getTipo().getIdTipoVersamento(),
//				String.valueOf(confermaVersamentoContabiliaRequest.getVersamento().getAnnualita()),
//				provVersamento.getIdProvincia());
//		
//		Double totaleVersato = new Double(0D);
//		for (SigasDichVersamenti sigasDichVersamentiTmp : versamentoList) {
//			totaleVersato = Double.sum(totaleVersato, sigasDichVersamentiTmp.getImporto());
//		}
//		 
//		totaleVersato = Double.sum(totaleVersato, confermaVersamentoContabiliaRequest.getVersamento().getImporto());
//		if(totaleVersato.compareTo(confermaVersamentoContabiliaRequest.getPagamento().getImportoAttuale().doubleValue())==1) {
//			throw new BusinessException("La somma dei versamenti è superiore all'importo del pagamento");
//		}
		 
		//Fine inserimento per contabilia 
		
		SigasTipoVersamento tipoVersamento = sigasTipoVersamentoRepository.findOne(confermaVersamentoContabiliaRequest.getVersamento().getTipo().getIdTipoVersamento());
		if(tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.CONGUAGLIO.getName())){
			//Il versamento a conquaglio deve essere effettuato entro il mese di Marzo dell'anno successivo
			//a quello cui si riferisce la dichiarazione
			Date dataVersamentoAConguaglio = Utilities.dataVersamentoConguaglio(confermaVersamentoContabiliaRequest.getVersamento().getAnnualita());
			if(dataVersamentoAConguaglio.before(Utilities.azzeraOraMinuti(confermaVersamentoContabiliaRequest.getVersamento().getDataVersamento())))
						throw new BusinessException("Il versamento a conguaglio va effettuato entro marzo dell'anno successivo a quello cui si riferisce la dichiarazione");
		}

		SigasPagamentiVersamenti sigasPagamentiVersamenti=null;
		if (confermaVersamentoContabiliaRequest.getVersamento().getConsumo() == null
				&& (tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.ACCERTAMENTO.getName())
						|| tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.RAVVEDIMENTO.getName()))) {
			// errore
			throw new BusinessException(
					"Il versamento di tipo "+ tipoVersamento.getDenominazione() +" deve essere relativo ad un consumo");

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
//			pagamentiVersamenti.setFkVersamento(Long.valueOf(versamentoSavedDB.getIdVersamento()).intValue() );
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
		
//		List<SigasPagamentiVersamenti> listPagamentiVersamenti = sigasPagamentiVersamentiRepository.findBySigasAnagraficaSoggettiIdAnagAndSigasPagamentiIdPagamento(confermaVersamentoContabiliaRequest.getVersamento().getIdAnag(), confermaVersamentoContabiliaRequest.getPagamento().getIdPagamento());
		SigasPagamenti sigasPagamenti = sigasPagamentiCrudRepository.findOne(confermaVersamentoContabiliaRequest.getPagamento().getIdPagamento());
		if(sigasPagamentiVersamenti!=null && sigasPagamenti!=null && sigasPagamenti.getSigasPagamentiVersamentis()!=null) {
			sigasPagamenti.getSigasPagamentiVersamentis().add(sigasPagamentiVersamenti);
		}
		
//		return pagamentiVersamentiEntityMapper.mapListEntityToListVO(listPagamentiVersamenti);
		
		return pagamentiVersamentiEntityMapper.mapListEntityToListVO(sigasPagamenti.getSigasPagamentiVersamentis());
		
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
//		csiLogAuditRepository.save(csiLogAudit);
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
			//sigasAllarmi.setStatus(StatusAllarme.ATTIVO.getName());
			sigasAllarmi.setStatus(statusAllarme);
			sigasAllarmi.setSigasDichVersamenti(sigasDichVersamentiRepository.findOne(versamento.getIdVersamento()));
			
		return sigasAllarmi;
	}
}
