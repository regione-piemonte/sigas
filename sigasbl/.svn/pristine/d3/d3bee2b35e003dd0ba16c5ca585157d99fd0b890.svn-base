/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.csi.sigas.sigasbl.model.entity.CsiLogAudit;
import it.csi.sigas.sigasbl.model.entity.CsiLogAuditPK;
import it.csi.sigas.sigasbl.model.repositories.SigasCParametroRepository;
import it.csi.sigas.sigasbl.security.UserDetails;

public class CsiLogUtils {
	
	private static final String CODICE_PRODOTTO = "codiceProdotto";
	
	private static final String CODICE_LINEA_CLIENTE = "codiceLineaCliente";
	
	private static final String CODICE_AMBIENTE = "codiceAmbiente";
	
	private static final String CODICE_UNITA_INSTALLAZIONE = "codiceUnitaInstallazione";
	
	
	
	public static CsiLogAudit getCsiLogAudit(SigasCParametroRepository sigasCParametroRepository, String operazione, String oggOper, String keyOper) {
		
//		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
		        .getRequest();
		
		

		String codiceProdotto = sigasCParametroRepository.findByDescParametro(CODICE_PRODOTTO).getValoreString();
		String codiceLineaCliente = sigasCParametroRepository.findByDescParametro(CODICE_LINEA_CLIENTE).getValoreString();
		String codiceAmbiente = sigasCParametroRepository.findByDescParametro(CODICE_AMBIENTE).getValoreString();
		String codiceInstallazione = sigasCParametroRepository.findByDescParametro(CODICE_UNITA_INSTALLAZIONE).getValoreString();
		
//		System.out.println(System.getProperties());
		
//		int endIndex = nomeUtenteMacchina.indexOf("-");
//		String installazione ="";
//		String numeroInstallazione = "";
//		if(endIndex!=-1) {
//			installazione = nomeUtenteMacchina.substring(0, endIndex);		
//			numeroInstallazione = nomeUtenteMacchina.substring(nomeUtenteMacchina.lastIndexOf("-"), nomeUtenteMacchina.length());			
//		}
		
		String ip = request.getRemoteAddr();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails utente = (UserDetails) principal;
		
		CsiLogAudit csiLogAudit = new CsiLogAudit();
		CsiLogAuditPK csiLogAuditPK = new CsiLogAuditPK();
		csiLogAuditPK.setDataOra(new Date());
//		csiLogAuditPK.setKeyOper(request.getRequestedSessionId());
		csiLogAuditPK.setKeyOper(keyOper);
		csiLogAuditPK.setUtente(utente.getIdentita().getCodFiscale());
		
		csiLogAudit.setId(csiLogAuditPK);
		csiLogAudit.setIdAddress(ip);
		csiLogAudit.setIdApp(codiceProdotto+"_"+codiceLineaCliente+"_"+codiceAmbiente+"_"+codiceInstallazione);
		csiLogAudit.setOggOper(oggOper);
		csiLogAudit.setOperazione(operazione);
		
		return csiLogAudit;
		
		
	}

}
