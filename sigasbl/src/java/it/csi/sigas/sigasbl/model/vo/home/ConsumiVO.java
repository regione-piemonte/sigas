/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.model.vo.base.ProvinceCalcBase;

public class ConsumiVO extends ProvinceCalcBase {

    private static final long serialVersionUID = -3855049065633536125L;
    private DichiaranteVO dichiarante;
    private String validazione;
    private AllarmiVO allarmi;

    public DichiaranteVO getDichiarante() {
        return dichiarante;
    }

    public void setDichiarante(DichiaranteVO dichiarante) {
        this.dichiarante = dichiarante;
    }

    public String getValidazione() {
        return validazione;
    }

    public void setValidazione(String validazione) {
        this.validazione = validazione;
    }

    public AllarmiVO getAllarmi() {
        return allarmi;
    }

    public void setAllarmi(AllarmiVO allarmi) {
        this.allarmi = allarmi;
    }
}
