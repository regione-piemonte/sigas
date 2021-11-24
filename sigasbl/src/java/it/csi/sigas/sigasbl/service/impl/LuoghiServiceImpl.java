/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.common.exception.BadRequestException;
import it.csi.sigas.sigasbl.model.entity.SigasComune;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.mapper.entity.ComuneEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.ProvinciaEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasComuneRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.vo.luoghi.ComuneVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;
import it.csi.sigas.sigasbl.service.ILuoghiService;

@Service
public class LuoghiServiceImpl implements ILuoghiService {
	@Autowired
	private SigasProvinciaRepository sigasProvinciaRepository;
	
	@Autowired
	private SigasComuneRepository sigasComuneRepository;
	
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;
	
    @Transactional
    @Override
    public List<ProvinciaVO> getAllProvince(){
    	List<SigasProvincia> listaProvinciaDB = (List<SigasProvincia>) sigasProvinciaRepository.findByFineValiditaIsNullOrderByDenomProvinciaAsc();

		return provinciaEntityMapper.mapListEntityToListVO(listaProvinciaDB);
	}

    @Transactional
    @Override
	public List<ComuneVO> getComuniByIdProvincia(Long idProvinciaSelezionata) {

		List<SigasComune> listaComuniDB = sigasComuneRepository
				.findByFineValiditaIsNullAndSigasProvinciaIdProvinciaOrderByDenomComuneAsc(idProvinciaSelezionata);

		return comuneEntityMapper.mapListEntityToListVO(listaComuniDB);
	}

	@Override
	public ProvinciaVO getProvinciaBySigla(String siglaProvncia) {
		SigasProvincia prov = sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(siglaProvncia);
		return provinciaEntityMapper.mapEntityToVO(prov);
	}

	/***
	 *
	 */


	/***
	 * @return restituisce tutti i comuni italiani presenti sul database, sotto
	 *         forma di List<ComuneVO>
	 */
	@Override
	public List<ComuneVO> getAllComuni() {

		List<SigasComune> listaComuniDB = (List<SigasComune>) sigasComuneRepository.findByFineValiditaIsNullOrderByDenomComuneAsc();

		return comuneEntityMapper.mapListEntityToListVO(listaComuniDB);
	}

	

	

	@Override
	public ComuneVO getComuneByDescrizione(String descrizioneComune) {

		SigasComune comune = sigasComuneRepository.findByDenomComuneAndFineValiditaIsNull(descrizioneComune);

		return comuneEntityMapper.mapEntityToVO(comune);
	}

	@Override
	public ProvinciaVO getProvinciaByDescrizione(String descrizioneProvincia) {

		SigasProvincia provincia = sigasProvinciaRepository.findByDenomProvinciaAndFineValiditaIsNull(descrizioneProvincia);

		return provinciaEntityMapper.mapEntityToVO(provincia);
		
	}

	@Override
	public ProvinciaVO getProvinciaByIdComune(Long idComune) {

		SigasComune comune = sigasComuneRepository.findOne(idComune);
		SigasProvincia provincia = comune.getSigasProvincia();

		return provinciaEntityMapper.mapEntityToVO(provincia);
		
	}

	

	@Override
	public List<String> ricercaIndirizzo(String indirizzo, Long id) {
		if (indirizzo == null || indirizzo.length() < 2)
			throw new BadRequestException("indirizzo non valorizzato o minore di 2 caratteri");
		if (id == null)
			throw new BadRequestException("id comune non valorizzato");

		SigasComune irbaDComune = sigasComuneRepository.findOne(id);
		if (irbaDComune == null)
			throw new BadRequestException("id comune non esiste sul db ");

		String codiceIstat = irbaDComune.getCodIstatComune();
		List<String> response = new ArrayList<String>();
		List<String> vie = null;
//		try {
//			Comune comune = limmEnteServiceFacade.cercaComunePerCodiceIstat(codiceIstat);
//			vie = lanciViaServiceFacade.cercaVieComunaliPerNomeAndidComune(indirizzo, comune.getId());
//			if (vie != null)
//				response.addAll(vie);
//			vie = lanciViaServiceFacade.cercaVieSovracomunaliPerNome(indirizzo);
//			if (vie != null)
//				response.addAll(vie);
//		} catch (RemoteException e) {
//			logger.error("ERRORE RECUPERO INDIRIZZO GINEVRA:" + e);
//		}

		return response;
	}

   
    
	
}
