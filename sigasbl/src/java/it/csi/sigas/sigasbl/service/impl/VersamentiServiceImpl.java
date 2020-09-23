/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.mapping.Array;
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
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.entity.SigasTipoAllarmi;
import it.csi.sigas.sigasbl.model.entity.SigasTipoVersamento;
import it.csi.sigas.sigasbl.model.mapper.entity.AllarmiSoggettoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.DichVersamentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.ProvinciaEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoVersamentoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.CsiLogAuditRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAllarmiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichConsumiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichVersamentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoAllarmiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoVersamentoRepository;
import it.csi.sigas.sigasbl.model.vo.home.AllarmiSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.AnaComunicazioniVO;
import it.csi.sigas.sigasbl.model.vo.home.TipoVersamentoVO;
import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.request.home.AllarmeRequest;
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
	private SigasTipoVersamentoRepository sigasTipoVersamentoRepository;
	
	@Autowired
	private DichVersamentiEntityMapper dichVersamentiEntityMapper;
	
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
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	
	/* Versamenti per Soggetto*/
    @Override
    public List<String> ricercaAnnualitaVersamenti(Long idAnag) {
		return sigasDichVersamentiRepository.findDistinctBySigasAnagraficaSoggettiIdAnag(idAnag);
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

	@Override
	public List<String> ricercaMeseVersamenti(Long id, String annualita) {
		List<String> mesiVersamentoList = new ArrayList<String>();
		mesiVersamentoList.add("Tutti");
		List<String> mesiDBList = sigasDichVersamentiRepository.findDistinctMonthBySigasAnagraficaSoggettiIdAnag(id, annualita);
		for(String meseCurr : mesiDBList) {
			mesiVersamentoList.add(meseCurr);
		}
		 return mesiVersamentoList;
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
				csiLogAuditRepository.save(csiLogAudit);

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
				sigasAllarmi = sigasAllarmiRepository.save(allarmeAccertamento(versamentoVO, TipoVersamenti.ACCERTAMENTO.getName(), user));
				oggOper += "sigas_allarmi ";
				listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
			}
			if (tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.RAVVEDIMENTO.getName())) {
				sigasAllarmi = sigasAllarmiRepository.save(allarmeAccertamento(versamentoVO, TipoVersamenti.RAVVEDIMENTO.getName(), user));
				oggOper += "sigas_allarmi ";
				listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
			}
		}
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"INSERT - insertVersamento", oggOper,String.join("_", listKeyOper));
		csiLogAuditRepository.save(csiLogAudit);
		
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
			sigasAllarmi = sigasAllarmiRepository.save(allarmeAccertamento(confermaVersamentoRequest.getVersamento(), TipoVersamenti.ACCERTAMENTO.getName(),user));
			oggOper += "sigas_allarmi ";
			listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
		}
		if(tipoVersamento.getDenominazione().equalsIgnoreCase(TipoVersamenti.RAVVEDIMENTO.getName())){
			sigasAllarmi = sigasAllarmiRepository.save(allarmeAccertamento(confermaVersamentoRequest.getVersamento(), TipoVersamenti.RAVVEDIMENTO.getName(), user));
			oggOper += "sigas_allarmi ";
			listKeyOper.add(String.valueOf(sigasAllarmi.getIdAllarme()) );
		}
		
		CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(sigasCParametroRepository,"UPDATE - updateVersamento", oggOper, String.join("_", listKeyOper));
		csiLogAuditRepository.save(csiLogAudit);
	}
	
	private SigasAllarmi allarmeAccertamento(VersamentiPrVO versamento,String tipoAllarme, String user) {
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
			sigasAllarmi.setStatus(StatusAllarme.ATTIVO.getName()); 
			sigasAllarmi.setSigasDichVersamenti(sigasDichVersamentiRepository.findOne(versamento.getIdVersamento()));
			
		return sigasAllarmi;
	}
}
