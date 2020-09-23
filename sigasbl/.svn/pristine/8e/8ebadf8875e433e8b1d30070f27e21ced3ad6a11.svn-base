/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    
	
}
