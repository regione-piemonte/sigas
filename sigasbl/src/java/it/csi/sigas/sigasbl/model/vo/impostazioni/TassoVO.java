/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.impostazioni;

import it.csi.sigas.sigasbl.model.vo.base.VersionBaseVO;

import java.util.Date;

public class TassoVO extends VersionBaseVO {

    private static final long serialVersionUID = -1L;

    private Long id;
    private double valore;
    private Long idTipoTasso;
    private Date dataStart;
    private Date dataEnd;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValore() {
        return valore;
    }

    public void setValore(double valore) {
        this.valore = valore;
    }

    public Long getIdTipoTasso() {
        return idTipoTasso;
    }

    public void setIdTipoTasso(Long idTipoTasso) {
        this.idTipoTasso = idTipoTasso;
    }

    public Date getDataStart() {
        return dataStart;
    }

    public void setDataStart(Date dataStart) {
        this.dataStart = dataStart;
    }

    public Date getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(Date dataEnd) {
        this.dataEnd = dataEnd;
    }


}
