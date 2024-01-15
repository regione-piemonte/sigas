/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.luoghi;

import it.csi.sigas.sigasbl.model.vo.base.LuogoBaseVO;

public class ProvinciaVO extends LuogoBaseVO {

    private static final long serialVersionUID = -1L;

    private String sigla;


    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public String toString() {
        return "ProvinciaVO " + super.toString();
    }

}
