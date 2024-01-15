/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.impostazioni;

import it.csi.sigas.sigasbl.model.vo.base.VersionBaseVO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AliquoteVO extends VersionBaseVO {

    private static final long serialVersionUID = -1L;

    private Long id;
    private double aliquota;
    private String progRigo;
    private TipoAliquoteVO tipoAliquote;
    private Date validitaStart;
    private Date validitaEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAliquota() {
        return aliquota;
    }

    public void setAliquota(double aliquota) {
        this.aliquota = aliquota;
    }

    public String getProgRigo() {
        return progRigo;
    }

    public void setProgRigo(String progRigo) {
        this.progRigo = progRigo;
    }

    public TipoAliquoteVO getTipoAliquote() {
        return tipoAliquote;
    }

    public void setTipoAliquote(TipoAliquoteVO tipoAliquote) {
        this.tipoAliquote = tipoAliquote;
    }

    public Date getValiditaStart() {
        return validitaStart;
    }

    public void setValiditaStart(Date validitaStart) {
        this.validitaStart = validitaStart;
    }

    public Date getValiditaEnd() {
        return validitaEnd;
    }

    public void setValiditaEnd(Date validitaEnd) {
        this.validitaEnd = validitaEnd;
    }

}
