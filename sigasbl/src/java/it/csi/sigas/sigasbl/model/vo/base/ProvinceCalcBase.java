package it.csi.sigas.sigasbl.model.vo.base;

import it.csi.sigas.sigasbl.common.rest.VO;

public class ProvinceCalcBase implements VO {

    private long nProvince;
    private double totVersato;
    private double totCalcolato;

    public long getnProvince() {
        return nProvince;
    }

    public void setnProvince(long nProvince) {
        this.nProvince = nProvince;
    }

    public double getTotVersato() {
        return totVersato;
    }

    public void setTotVersato(double totVersato) {
        this.totVersato = totVersato;
    }

    public double getTotCalcolato() {
        return totCalcolato;
    }

    public void setTotCalcolato(double d) {
        this.totCalcolato = d;
    }
}
