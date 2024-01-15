/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.accreditamento;

import it.csi.sigas.sigasbl.request.accreditamento.base.PraticaAccreditamentoBase;
import it.csi.sigas.sigasbl.vo.accreditamento.DichiaranteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.LegaleRappresentanteVO;
import it.csi.sigas.sigasbl.vo.accreditamento.OperatoreVO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ConfermaPraticaAccreditamentoRequest extends PraticaAccreditamentoBase implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotNull(message = "dichiarante non deve essere vuoto")
    private DichiaranteVO dichiarante;
    @NotNull(message = "operatore non deve essere vuoto")
    private OperatoreVO operatore;
    @NotNull(message = "legaleRappresentante non deve essere vuoto")
    private LegaleRappresentanteVO legaleRappresentante;


    public DichiaranteVO getDichiarante() {
        return dichiarante;
    }

    public void setDichiarante(DichiaranteVO dichiarante) {
        this.dichiarante = dichiarante;
    }

    public OperatoreVO getOperatore() {
        return operatore;
    }

    public void setOperatore(OperatoreVO operatore) {
        this.operatore = operatore;
    }

    public LegaleRappresentanteVO getLegaleRappresentante() {
        return legaleRappresentante;
    }

    public void setLegaleRappresentante(LegaleRappresentanteVO legaleRappresentante) {
        this.legaleRappresentante = legaleRappresentante;
    }

}
