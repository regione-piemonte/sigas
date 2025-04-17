/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import com.google.common.base.Strings;
import it.csi.sigas.sigasbl.common.exception.BadRequestException;
import it.csi.sigas.sigasbl.model.entity.*;
import it.csi.sigas.sigasbl.model.mapper.entity.LegaleRappresentanteEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.OperatoreEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.*;
import it.csi.sigas.sigasbl.request.accreditamento.AnnullaPraticaAccreditamentoRequest;
import it.csi.sigas.sigasbl.request.accreditamento.ConfermaAccreditamentoRequest;
import it.csi.sigas.sigasbl.service.IDichiaranteService;
import it.csi.sigas.sigasbl.service.IModificaAccreditamentoService;
import it.csi.sigas.sigasbl.util.CsiLogUtils;
import it.csi.sigas.sigasbl.vo.accreditamento.LegaleRappresentanteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.OperatoreVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;


@Service
public class ModificaAccreditamentoServiceImpl implements IModificaAccreditamentoService {

    @Autowired
    private SigasDichiaranteRepository irbaTDichiaranteRepository;

    @Autowired
    private SigasLegaleRappresentRepository irbaTLegaleRappresentRepository;

    @Autowired
    private SigasOperatoreRepository irbaTOperatoreRepository;

    @Autowired
    private LegaleRappresentanteEntityMapper legaleRappresentanteEntityMapper;

    @Autowired
    private OperatoreEntityMapper operatoreEntityMapper;

    @Autowired
    private IDichiaranteService dichiaranteService;

    @Autowired
    private SigasUtenteProvvisorioRepository sigasUtenteProvvisorioRepository;

    @Autowired
    private SigasCParametroRepository sigasCParametroRepository;

    @Autowired
    private CsiLogAuditRepository csiLogAuditRepository;

    @Override
    @Transactional
    public void modificaAccreditamento(ConfermaAccreditamentoRequest confermaAccreditamentoRequest) {
        SigasDichiarante sigasDichiarante = dichiaranteService.aggiornaDatiDichiarante(confermaAccreditamentoRequest.getDichiarante());
        aggiornaDatiLegaleRappresentante(confermaAccreditamentoRequest.getLegaleRappresentante(), sigasDichiarante);
        aggiornaDatiOperatore(confermaAccreditamentoRequest.getOperatore(), sigasDichiarante);
        CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(
                sigasCParametroRepository,
                "SIGAS FO - MODIFICA DOMANDA ACCREDITAMENTO",
                "sigas_utente_provvisorio",
                String.valueOf(confermaAccreditamentoRequest.getIdOperatoreProvvisorio()));
        csiLogAuditRepository.saveOrUpdate(
                csiLogAudit.getId().getDataOra(),
                csiLogAudit.getIdApp(),
                csiLogAudit.getIdAddress(),
                csiLogAudit.getId().getUtente(),
                csiLogAudit.getOperazione(),
                csiLogAudit.getOggOper(),
                csiLogAudit.getId().getKeyOper());
    }

    @Override
    @Transactional
    public void aggiornaDatiLegaleRappresentante(LegaleRappresentanteVO legaleRappresentanteVoPerAggiornamento, SigasDichiarante sigasDichiarante) {
        if (legaleRappresentanteVoPerAggiornamento == null || sigasDichiarante == null) {
            throw new BadRequestException("Legale rappresentante e/o codice fiscale vuoti. Impossibile aggiornare i dati del dichiarante");
        }
        SigasLegaleRappresent irbaTLegaleRappresentDB = irbaTLegaleRappresentRepository.findBySigasDichiarante(sigasDichiarante);
        SigasLegaleRappresent legaleRappresentanteNew = legaleRappresentanteEntityMapper.mapVOtoEntity(legaleRappresentanteVoPerAggiornamento);
        
        legaleRappresentanteNew.setSigasDichiarante(sigasDichiarante);
        legaleRappresentanteNew.setDataInsert(irbaTLegaleRappresentDB.getDataInsert());
        legaleRappresentanteNew.setDataUpdate(new Timestamp(new Date().getTime()));
        legaleRappresentanteNew.setIdLegaleRappresent(irbaTLegaleRappresentDB.getIdLegaleRappresent());
        
        // Eseguo il salvataggio dell'entity con i campi aggiornati
        irbaTLegaleRappresentRepository.save(legaleRappresentanteNew);
    }

    @Override
    @Transactional
    public void aggiornaDatiOperatore(OperatoreVO operatoreVoPerAggiornamento, SigasDichiarante sigasDichiarante) {
        // Controllo di primo livello sugli input che mi stanno arrivando
        if (operatoreVoPerAggiornamento == null) {
            throw new BadRequestException("Operatore e/o codice fiscale vuoti. Impossibile aggiornare i dati del dichiarante");
        }
        // RECUPERO I DATI UTENTE
        SigasOperatore irbaTOperatoreDB = irbaTOperatoreRepository.findByCfOperatore(operatoreVoPerAggiornamento.getCodiceFiscale());
        SigasOperatore operatoreDaAggiornare = operatoreEntityMapper.mapVOtoEntity(operatoreVoPerAggiornamento);
        operatoreDaAggiornare.setSigasDichiarante(sigasDichiarante);
        operatoreDaAggiornare.setDataInsert(irbaTOperatoreDB.getDataInsert());
        operatoreDaAggiornare.setIdOperatore(irbaTOperatoreDB.getIdOperatore());
        operatoreDaAggiornare.setDataUpdate(new Timestamp(new Date().getTime()));
        irbaTOperatoreRepository.save(operatoreDaAggiornare);
    }

    @Override
    public LegaleRappresentanteVO recuperaLegaleRappresentante(String codiceFiscaleUtente) {
        if (Strings.isNullOrEmpty(codiceFiscaleUtente)) {
            throw new BadRequestException("Codice fiscale vuoto. Impossibile recuperare dati");
        }
        SigasOperatore sigasOperatore = irbaTOperatoreRepository.findByCfOperatore(StringUtils.trim(codiceFiscaleUtente));
        SigasDichiarante sigasDichiarante = irbaTDichiaranteRepository.findBySigasOperatores(sigasOperatore);
        SigasLegaleRappresent sigasLegaleRappresent = irbaTLegaleRappresentRepository.findBySigasDichiarante(sigasDichiarante);
        return legaleRappresentanteEntityMapper.mapEntityToVO(sigasLegaleRappresent);
    }

    @Override
    public OperatoreVO recuperaOperatore(String codiceFiscaleUtente) {
        return null;
    }

    @Override
    public void cambiaStatoNoteAccreditamento(AnnullaPraticaAccreditamentoRequest annullaPraticaAccreditamentoRequest, String user) {
        SigasUtenteProvvisorio utenteProvvisorio = sigasUtenteProvvisorioRepository.findOne(annullaPraticaAccreditamentoRequest.getIdUtenteProvv());
        utenteProvvisorio.setStato(annullaPraticaAccreditamentoRequest.getStato());
        utenteProvvisorio.setModUser(user);
        utenteProvvisorio.setModDate(new Timestamp(new Date().getTime()));
        if (annullaPraticaAccreditamentoRequest.getNote() != null &&
                !annullaPraticaAccreditamentoRequest.getNote().isEmpty()) {
            utenteProvvisorio.setNote(annullaPraticaAccreditamentoRequest.getNote());
        }
        sigasUtenteProvvisorioRepository.save(utenteProvvisorio);
        CsiLogAudit csiLogAudit = CsiLogUtils.getCsiLogAudit(
                sigasCParametroRepository,
                "SIGAS FO - ANNULLA DOMANDA ACCREDITAMENTO",
                "sigas_utente_provvisorio",
                String.valueOf(annullaPraticaAccreditamentoRequest.getIdUtenteProvv()));
        csiLogAuditRepository.saveOrUpdate(csiLogAudit.getId().getDataOra(), csiLogAudit.getIdApp(), csiLogAudit.getIdAddress(),
                csiLogAudit.getId().getUtente(), csiLogAudit.getOperazione(), csiLogAudit.getOggOper(), csiLogAudit.getId().getKeyOper());
    }
}

