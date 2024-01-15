/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.model.vo.base.ProvinceCalcBase;

public class SoggettiVO extends ProvinceCalcBase {

    private static final long serialVersionUID = 1L;
    private long idAnag;
    private String codiceAzienda;
    private String denominazione;
    private String validato;
    private AllarmiVO allarmi;
    private String allReport;


    public String getCodiceAzienda() {
        return codiceAzienda;
    }

    public void setCodiceAzienda(String codiceAzienda) {
        this.codiceAzienda = codiceAzienda;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public long getIdAnag() {
        return idAnag;
    }

    public void setIdAnag(long idAnag) {
        this.idAnag = idAnag;
    }

    public String getValidato() {
        return validato;
    }

    public void setValidato(String validato) {
        this.validato = validato;
    }

    public AllarmiVO getAllarmi() {
        return allarmi;
    }

    public void setAllarmi(AllarmiVO allarmi) {
        this.allarmi = allarmi;
    }

    public String getAllReport() {
        return allReport;
    }

    public void setAllReport(String allReport) {
        this.allReport = allReport;
    }
}
