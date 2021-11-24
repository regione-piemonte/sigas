/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.sigas.sigasbl.model.entity.SigasCMessaggi;
import it.csi.sigas.sigasbl.model.entity.SigasCParametro;
import it.csi.sigas.sigasbl.model.repositories.SigasCMessaggiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.model.vo.home.UtilityCtrlVO;
import it.csi.sigas.sigasbl.service.IUtilityService;

@Service
public class UtilityServiceImpl implements IUtilityService {
	

	@Autowired
	private SigasCParametroRepository sigasCParametroRepository;
	
	
	@Autowired
	private SigasCMessaggiRepository sigasCMessaggiRepository;

	@Override
	public UtilityCtrlVO retriveMessageAndParameters(String descParametro, String descChiaveMessaggio) {
		SigasCParametro sigasParametro = null;
		SigasCMessaggi sigasCMessaggi = null;
		if(descParametro!=null && !descParametro.isEmpty()) {
			sigasParametro = sigasCParametroRepository.findByDescParametro(descParametro);
		}
		
		if(descChiaveMessaggio!=null && !descChiaveMessaggio.isEmpty()) {
			sigasCMessaggi = sigasCMessaggiRepository.findByDescChiaveMessaggio(descChiaveMessaggio);
		}

        
        UtilityCtrlVO res = new UtilityCtrlVO();
        
        if(sigasParametro!=null )
        res.setParameterInt(sigasParametro.getValoreNumber().toBigInteger().intValue());
        
        if(sigasCMessaggi!=null) {
            res.setMessage(sigasCMessaggi.getValoreMessaggio());
            res.setLevelMessagge(sigasCMessaggi.getLivelloMessaggio());
        }

        return res;
	}
	


    
}
