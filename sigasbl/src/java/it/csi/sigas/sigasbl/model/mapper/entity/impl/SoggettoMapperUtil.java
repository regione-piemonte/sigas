package it.csi.sigas.sigasbl.model.mapper.entity.impl;

import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasComune;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;

public class SoggettoMapperUtil {


    protected AnagraficaSoggettoVO getAnagraficaSoggettoVO(SigasAnagraficaSoggetti anagraficaSoggetti) {
        AnagraficaSoggettoVO anagraficaSoggettoVO = new AnagraficaSoggettoVO();
        anagraficaSoggettoVO.setCodiceAzienda(anagraficaSoggetti.getCodiceAzienda());
        anagraficaSoggettoVO.setDataFusione(anagraficaSoggetti.getDataFusione());
        anagraficaSoggettoVO.setDenominazione(anagraficaSoggetti.getDenominazione());
        anagraficaSoggettoVO.setEmail(anagraficaSoggetti.getEmail());
        anagraficaSoggettoVO.setIban(anagraficaSoggetti.getIban());
        anagraficaSoggettoVO.setIdAnag(anagraficaSoggetti.getIdAnag());
        anagraficaSoggettoVO.setIdComune(anagraficaSoggetti.getSigasComune().getIdComune());
        anagraficaSoggettoVO.setIdFusione(anagraficaSoggetti.getIdFusione());
        anagraficaSoggettoVO.setIdProvincia(anagraficaSoggetti.getSigasProvincia().getIdProvincia());
        anagraficaSoggettoVO.setImportoFideussione(anagraficaSoggetti.getImportoFideussione());
        anagraficaSoggettoVO.setIndirizzo(anagraficaSoggetti.getIndirizzo());
        anagraficaSoggettoVO.setNote(anagraficaSoggetti.getNote());
        anagraficaSoggettoVO.setPec(anagraficaSoggetti.getPec());
        anagraficaSoggettoVO.setRiferimento(anagraficaSoggetti.getRiferimento());
        anagraficaSoggettoVO.setStatus(anagraficaSoggetti.getStatus());
        anagraficaSoggettoVO.setTelefono(anagraficaSoggetti.getTelefono());
        anagraficaSoggettoVO.setTipo(anagraficaSoggetti.getTipo());

        return anagraficaSoggettoVO;
    }


    protected SigasAnagraficaSoggetti getSigasAnagraficaSoggetti(AnagraficaSoggettoVO anagraficaSoggettoVO) {
        SigasAnagraficaSoggetti sigasAnagraficaSoggetti = new SigasAnagraficaSoggetti();
        sigasAnagraficaSoggetti.setIdAnag(anagraficaSoggettoVO.getIdAnag());
        sigasAnagraficaSoggetti.setDataFusione(anagraficaSoggettoVO.getDataFusione());
        sigasAnagraficaSoggetti.setCodiceAzienda(anagraficaSoggettoVO.getCodiceAzienda());
        sigasAnagraficaSoggetti.setDenominazione(anagraficaSoggettoVO.getDenominazione());
        sigasAnagraficaSoggetti.setIndirizzo(anagraficaSoggettoVO.getIndirizzo());
        sigasAnagraficaSoggetti.setIban(anagraficaSoggettoVO.getIban());
        sigasAnagraficaSoggetti.setEmail(anagraficaSoggettoVO.getEmail());
        sigasAnagraficaSoggetti.setImportoFideussione(anagraficaSoggettoVO.getImportoFideussione());
        sigasAnagraficaSoggetti.setNote(anagraficaSoggettoVO.getNote());
        sigasAnagraficaSoggetti.setPec(anagraficaSoggettoVO.getPec());
        sigasAnagraficaSoggetti.setRiferimento(anagraficaSoggettoVO.getRiferimento());
        sigasAnagraficaSoggetti.setStatus(anagraficaSoggettoVO.getStatus());
        sigasAnagraficaSoggetti.setTelefono(anagraficaSoggettoVO.getTelefono());
        sigasAnagraficaSoggetti.setTipo(anagraficaSoggettoVO.getTipo());
        SigasProvincia sigasProvincia = new SigasProvincia();
        sigasProvincia.setIdProvincia(anagraficaSoggettoVO.getIdProvincia());
        sigasAnagraficaSoggetti.setSigasProvincia(sigasProvincia);
        SigasComune sigasComune = new SigasComune();
        sigasComune.setIdComune(anagraficaSoggettoVO.getIdComune());
        sigasComune.setSigasProvincia(sigasProvincia);
        sigasAnagraficaSoggetti.setSigasComune(sigasComune);
        return sigasAnagraficaSoggetti;
    }


}
