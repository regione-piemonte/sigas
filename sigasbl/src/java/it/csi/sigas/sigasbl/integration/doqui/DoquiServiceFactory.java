/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui;

import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoquiServiceFactory {

    @Autowired
    private SigasCParametroRepository sigasCParametroRepository;
    private DoquiService acarisService;

    public DoquiService getAcarisService() {
        if (acarisService == null) {
            String actaServer = sigasCParametroRepository.findByDescParametro("ACTA_SERVER").getValoreString();
            String actaContext = sigasCParametroRepository.findByDescParametro("ACTA_CONTEXT").getValoreString();
            int actaPort = sigasCParametroRepository.findByDescParametro("ACTA_PORT").getValoreNumber().intValue();
            Boolean actaIsWS = sigasCParametroRepository.findByDescParametro("ACTA_IS_WS").getValoreBoolean();
            acarisService = new DoquiService(actaServer, actaContext, actaPort, actaIsWS);
        }
        return acarisService;
    }
}
