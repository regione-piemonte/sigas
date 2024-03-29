/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.TipoTassi;
import it.csi.sigas.sigasbl.common.exception.BadRequestException;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.common.utils.DateContainer;
import it.csi.sigas.sigasbl.common.utils.DateRange;
import it.csi.sigas.sigasbl.common.utils.Utilities;
import it.csi.sigas.sigasbl.model.entity.SigasAliquote;
import it.csi.sigas.sigasbl.model.entity.SigasQuadroM;
import it.csi.sigas.sigasbl.model.entity.SigasTassi;
import it.csi.sigas.sigasbl.model.entity.SigasTipoAliquote;
import it.csi.sigas.sigasbl.model.entity.SigasTipoTassi;
import it.csi.sigas.sigasbl.model.mapper.entity.AliquoteEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TassoEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoAliquoteEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.TipoTassoEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasAliquoteRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasQuadroMRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTassiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoAliquoteRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoTassiRepository;
import it.csi.sigas.sigasbl.model.vo.impostazioni.AliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TassoVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoAliquoteVO;
import it.csi.sigas.sigasbl.model.vo.impostazioni.TipoTassoVO;
import it.csi.sigas.sigasbl.request.home.ConfermaAliquotaRequest;
import it.csi.sigas.sigasbl.request.home.ConfermaTassoRequest;
import it.csi.sigas.sigasbl.service.IImpostazioniService;

@Service
public class ImpostazioniServiceImpl implements IImpostazioniService {
	
	@Autowired
	private SigasTassiRepository sigasTassiRepository;
	
	@Autowired
	private SigasTipoTassiRepository sigasTipoTassiRepository;
		
	@Autowired
	private SigasTipoAliquoteRepository sigasTipoAliquoteRepository;
	
	@Autowired
	private SigasQuadroMRepository sigasQuadroMRepository;
	
	@Autowired
	private TassoEntityMapper tassoEntityMapper;
	
	@Autowired
	private SigasAliquoteRepository sigasAliquoteRepository;
	
	@Autowired
	private AliquoteEntityMapper aliquoteEntityMapper;

	@Autowired
	private TipoAliquoteEntityMapper tipoAliquoteEntityMapper;
	
	@Autowired
	private TipoTassoEntityMapper tipoTassoEntityMapper;
	

	@Override
	@Transactional
    public List<TassoVO> getAllTassi(){
    	List<TassoVO> listaTassi= new LinkedList<TassoVO>();
//    	List<SigasTassi> listaTassi = (List<SigasTassi>) sigasTassiRepository.findAll();
    	List<SigasTassi> listaTassiRimborsi = (List<SigasTassi>) sigasTassiRepository.findBySigasTipoTassiIdTipoTassoOrderByValiditaStartAsc(1L);
    	listaTassi.addAll(tassoEntityMapper.mapListEntityToListVO(listaTassiRimborsi));
    	List<SigasTassi> listaTassiAcc = (List<SigasTassi>) sigasTassiRepository.findBySigasTipoTassiIdTipoTassoOrderByValiditaStartAsc(2L);
    	listaTassi.addAll(tassoEntityMapper.mapListEntityToListVO(listaTassiAcc));
    	return listaTassi;
	}
    
    @Override
    @Transactional
    public List<TipoTassoVO> getAllTipoTassi(){
    	List<SigasTipoTassi> listaTipoTassiDB = (List<SigasTipoTassi>) sigasTipoTassiRepository.findAll();
    	
    	return tipoTassoEntityMapper.mapListEntityToListVO(listaTipoTassiDB);
    }
    
    
	@Override
	@Transactional
	public void eliminaTasso(Long idTasso) {
		if (idTasso == null)
			throw new BusinessException("idTasso non valorizzato");
		 SigasTassi tasso = sigasTassiRepository.findOne(idTasso);
		 if (tasso != null)
			 sigasTassiRepository.delete(tasso);
		 else
			 throw new BusinessException("Errore tasso non trovato");
	}


	@Override
	@Transactional
	public TassoVO aggiungiTasso(ConfermaTassoRequest confermaAggungiTasso, String user) {

		if (confermaAggungiTasso.getTasso().getDataStart() == null
				|| confermaAggungiTasso.getTasso().getDataEnd() == null)
			throw new BusinessException("Le date sono obbligatorie. ");

		if (Utilities.azzeraOraMinuti(confermaAggungiTasso.getTasso().getDataEnd())
				.before(Utilities.azzeraOraMinuti(confermaAggungiTasso.getTasso().getDataStart()))) {
			throw new BusinessException("La data fine validita' deve essere successiva la data di inizio validita'. ");
		}

		SigasTassi tassoDaAggiungere = tassoEntityMapper.mapVOtoEntity(confermaAggungiTasso.getTasso());

		List<SigasTassi> listTassiPerTipologia = sigasTassiRepository
				.findBySigasTipoTassiIdTipoTasso(confermaAggungiTasso.getTasso().getIdTipoTasso());
		Iterator<SigasTassi> it = listTassiPerTipologia.iterator();
		while (it.hasNext()) {
			SigasTassi curentTasso = (SigasTassi) it.next();
			if (DateRange.intersect(new DateContainer(curentTasso.getValiditaStart(), curentTasso.getValiditaEnd()),
					new DateContainer(tassoDaAggiungere.getValiditaStart(), tassoDaAggiungere.getValiditaEnd())))
				throw new BusinessException("Le date inserite non sono corrette");
		}

		tassoDaAggiungere.setInsDate(new Date());
		tassoDaAggiungere.setInsUser(user);
		
		return tassoEntityMapper.mapEntityToVO(sigasTassiRepository.save(tassoDaAggiungere));
	}

	@Override
	@Transactional
	public TassoVO aggiornaTasso(ConfermaTassoRequest confermaAggiornaTasso, String user) {

		if (confermaAggiornaTasso.getTasso().getDataStart() == null
				|| confermaAggiornaTasso.getTasso().getDataEnd() == null)
			throw new BusinessException("Le date sono obbligatorie. ");

		if (Utilities.azzeraOraMinuti(confermaAggiornaTasso.getTasso().getDataEnd())
				.before(Utilities.azzeraOraMinuti(confermaAggiornaTasso.getTasso().getDataStart()))) {
			throw new BusinessException("La data fine validita' deve essere successiva la data di inizio validita'. ");
		}
		
		SigasTassi tassoDaAggiornare = tassoEntityMapper.mapVOtoEntity(confermaAggiornaTasso.getTasso());
		SigasTassi tassoRicercato = sigasTassiRepository.findOne(confermaAggiornaTasso.getTasso().getId());
		
		tassoDaAggiornare.setInsDate(tassoRicercato.getInsDate());
		tassoDaAggiornare.setInsUser(tassoRicercato.getInsUser());
		
		List<SigasTassi> listTassiPerTipologia = sigasTassiRepository
				.findBySigasTipoTassiIdTipoTasso(confermaAggiornaTasso.getTasso().getIdTipoTasso());
		Iterator<SigasTassi> it = listTassiPerTipologia.iterator();
		while (it.hasNext()) {
			SigasTassi curentTasso = (SigasTassi) it.next();
			if (!(tassoDaAggiornare.getIdTasso() == curentTasso.getIdTasso())) {
				if (DateRange.intersect(new DateContainer(curentTasso.getValiditaStart(), curentTasso.getValiditaEnd()),
						new DateContainer(tassoDaAggiornare.getValiditaStart(), tassoDaAggiornare.getValiditaEnd())))
					throw new BusinessException("Le date inserite non sono corrette");
			}
		}
		
		tassoDaAggiornare.setModDate(new Date());
		tassoDaAggiornare.setModUser(user);

		return tassoEntityMapper.mapEntityToVO(sigasTassiRepository.save(tassoDaAggiornare));
	}

	//ALIQUOTE
	
    @Transactional
    @Override
    public List<AliquoteVO> getAllAliquote(){
    	List<SigasAliquote> listaAliquoteDB = (List<SigasAliquote>) sigasAliquoteRepository.findAllOrderByIdAliquota();

    	return aliquoteEntityMapper.mapListEntityToListVO(listaAliquoteDB);
	}
	
    @Transactional
    @Override
	public List<TipoAliquoteVO> getAllTipoAliquoteByTipo(String tipo) {
    	List<SigasTipoAliquote> listaTipoAliquoteDB = (List<SigasTipoAliquote>) sigasTipoAliquoteRepository.findByTipo(tipo);

    	return tipoAliquoteEntityMapper.mapListEntityToListVO(listaTipoAliquoteDB);
    }

    @Transactional
    @Override
	public TipoAliquoteVO getTipoAliquoteByDescrizione(Long idTipoAliquota) {
    	TipoAliquoteVO tipoAliquoteVO = new TipoAliquoteVO();
    	SigasTipoAliquote sigasTipoAliquote = sigasTipoAliquoteRepository.findByIdTipoAliquota(idTipoAliquota);
		if(sigasTipoAliquote!=null) {
			tipoAliquoteVO = tipoAliquoteEntityMapper.mapEntityToVO(sigasTipoAliquote);
		}
		return tipoAliquoteVO;
    }

	@Transactional
	@Override
	public void insertAliquota(ConfermaAliquotaRequest confermaAliquotaRequest, String user) {
		
		if (confermaAliquotaRequest.getAliquota().getValiditaStart() == null
				|| confermaAliquotaRequest.getAliquota().getValiditaEnd() == null)
			throw new BusinessException("Le date sono obbligatorie. ");
		
		Date start = new Date(confermaAliquotaRequest.getAliquota().getValiditaStart().getTime());
		Date end = new Date(confermaAliquotaRequest.getAliquota().getValiditaEnd().getTime());
			
		if (start.compareTo(end) > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			throw new BusinessException("La data di inizio validita' " + sdf.format(start) + " e' superiore alla di fine validita' " +  sdf.format(end), ErrorCodes.BUSSINESS_EXCEPTION);
		} 
		
		SigasAliquote sigasAliquotaToInsert = aliquoteEntityMapper.mapVOtoEntity(confermaAliquotaRequest.getAliquota());
		
//	  	List<SigasAliquote> listaAliqote = sigasAliquoteRepository.findByProgRigoAndSigasTipoAliquoteIdTipoAliquota(confermaAliquotaRequest.getAliquota().getProgRigo(),confermaAliquotaRequest.getAliquota().getTipoAliquote().getIdTipoAliquota());
	  	List<SigasAliquote> listaAliqote = sigasAliquoteRepository.findAll();


		if (listaAliqote != null) {
			Iterator<SigasAliquote> it = listaAliqote.iterator();
			while (it.hasNext()) {
				SigasAliquote curentAliquota = (SigasAliquote) it.next();
				if ((DateRange.intersect(
						new DateContainer(curentAliquota.getValiditaStart(), curentAliquota.getValiditaEnd()),
						new DateContainer(sigasAliquotaToInsert.getValiditaStart(),
								sigasAliquotaToInsert.getValiditaEnd())) || 
						DateRange.intersectYear(curentAliquota.getValiditaStart(), sigasAliquotaToInsert.getValiditaStart()) ||
						DateRange.intersectYear(curentAliquota.getValiditaEnd(), sigasAliquotaToInsert.getValiditaEnd())) &&
						curentAliquota.getSigasTipoAliquote().getIdTipoAliquota()==sigasAliquotaToInsert.getSigasTipoAliquote().getIdTipoAliquota())

//					throw new BusinessException("Esiste gia' un'aliquota con ProgRigo "
//							+ confermaAliquotaRequest.getAliquota().getProgRigo() + " di tipo:  "
//							+ confermaAliquotaRequest.getAliquota().getTipoAliquote().getDescrizione()
//							+ ", per le date indicate", ErrorCodes.BUSSINESS_EXCEPTION);
				
				throw new BusinessException("Esiste gia' un'aliquota di tipo:  "
						+ confermaAliquotaRequest.getAliquota().getTipoAliquote().getDescrizione()
						+ ", per le date indicate", ErrorCodes.BUSSINESS_EXCEPTION);
			}
		}
		
		sigasAliquotaToInsert.setInsDate(new Date());
		sigasAliquotaToInsert.setInsUser(user);
		
		sigasAliquoteRepository.save(sigasAliquotaToInsert);
	}

	@Transactional
	@Override
	public AliquoteVO updateAliquota(ConfermaAliquotaRequest confermaAliquotaRequest, String user) {
		if (confermaAliquotaRequest.getAliquota().getValiditaStart() == null
				|| confermaAliquotaRequest.getAliquota().getValiditaEnd() == null)
			throw new BusinessException("Le date sono obbligatorie. ");

		Date start = new Date(confermaAliquotaRequest.getAliquota().getValiditaStart().getTime());
		Date end = new Date(confermaAliquotaRequest.getAliquota().getValiditaEnd().getTime());

		if (start.compareTo(end) > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
			throw new BusinessException("La data di inizio validita' " + sdf.format(start)
					+ " e' superiore alla di fine validita' " + sdf.format(end), ErrorCodes.BUSSINESS_EXCEPTION);
		}

		if (sigasAliquoteRepository.findOne(confermaAliquotaRequest.getAliquota().getId()) == null) {
			throw new BusinessException(
					"Non esiste alcuna aliquota con ProgRigo " + confermaAliquotaRequest.getAliquota().getProgRigo()
							+ " di tipo:  " + confermaAliquotaRequest.getAliquota().getTipoAliquote().getDescrizione(),
					ErrorCodes.BUSSINESS_EXCEPTION);
		}

		SigasAliquote sigasAliquotaToUpdate = aliquoteEntityMapper.mapVOtoEntity(confermaAliquotaRequest.getAliquota());

//		List<SigasAliquote> listaAliqote = sigasAliquoteRepository.findByProgRigoAndSigasTipoAliquoteIdTipoAliquota(
//				confermaAliquotaRequest.getAliquota().getProgRigo(),
//				confermaAliquotaRequest.getAliquota().getTipoAliquote().getIdTipoAliquota());
		
		List<SigasAliquote> listaAliqote = sigasAliquoteRepository.findAll();

		if (listaAliqote != null) {
			Iterator<SigasAliquote> it = listaAliqote.iterator();
			while (it.hasNext()) {
				SigasAliquote curentAliquota = (SigasAliquote) it.next();
				if (!(sigasAliquotaToUpdate.getIdAliquota() == curentAliquota.getIdAliquota())) {
					if ((DateRange.intersect(
							new DateContainer(curentAliquota.getValiditaStart(), curentAliquota.getValiditaEnd()),
							new DateContainer(sigasAliquotaToUpdate.getValiditaStart(),
									sigasAliquotaToUpdate.getValiditaEnd())) || 
							DateRange.intersectYear(curentAliquota.getValiditaStart(), sigasAliquotaToUpdate.getValiditaStart()) ||
							DateRange.intersectYear(curentAliquota.getValiditaEnd(), sigasAliquotaToUpdate.getValiditaEnd())) &&
							curentAliquota.getSigasTipoAliquote().getIdTipoAliquota()==sigasAliquotaToUpdate.getSigasTipoAliquote().getIdTipoAliquota())
						

//						throw new BusinessException("Esiste gia' un'aliquota con ProgRigo "
//								+ confermaAliquotaRequest.getAliquota().getProgRigo() + " di tipo:  "
//								+ confermaAliquotaRequest.getAliquota().getTipoAliquote().getDescrizione()
//								+ ", per le date indicate", ErrorCodes.BUSSINESS_EXCEPTION);
						
						throw new BusinessException("Esiste gia' un'aliquota di tipo:  "
								+ confermaAliquotaRequest.getAliquota().getTipoAliquote().getDescrizione()
								+ ", per le date indicate", ErrorCodes.BUSSINESS_EXCEPTION);
				}

			}
		}

		sigasAliquotaToUpdate.setModDate(new Date());
		sigasAliquotaToUpdate.setModUser(user);
		
		sigasAliquoteRepository.save(sigasAliquotaToUpdate);

		return new AliquoteVO();

	}
	

	@Transactional
	@Override
	public void eliminaAliquota(Long id) {
		if (id == null)
			throw new BadRequestException("id non valorizzato");
		SigasAliquote aliquota = sigasAliquoteRepository.findByIdAliquota(id);
		 if (aliquota == null) 
			throw new BadRequestException("id non valido ");
		 	 
		 List<SigasQuadroM> sigasQuadroM = sigasQuadroMRepository.findByAliquotaAndProgRigo(aliquota.getAliquota(), aliquota.getProgRigo());
		 
		 if (sigasQuadroM != null && sigasQuadroM.size() > 0)
			 throw new BusinessException("Non e' possibile eliminare l' aliquota " + aliquota.getAliquota() + 
						" con ProgRigo " + aliquota.getProgRigo() + " perche' utilizzato!", ErrorCodes.BUSSINESS_EXCEPTION);
		 
		 sigasAliquoteRepository.delete(aliquota);
	}

	@Override
	public List<TassoVO> tassiByRimborso() {
		List<SigasTassi> sigasTassi = sigasTassiRepository.findBySigasTipoTassiNomeTassoIgnoreCase(TipoTassi.RIMBORSI.getName());
		
		return tassoEntityMapper.mapListEntityToListVO(sigasTassi);
	}

	@Override
	public List<TassoVO> tassiByAccertamenti() {
	List<SigasTassi> sigasTassi = sigasTassiRepository.findBySigasTipoTassiNomeTassoIgnoreCase(TipoTassi.ACCERTAMENTI.getName());
		
		return tassoEntityMapper.mapListEntityToListVO(sigasTassi);
	}

	
}
