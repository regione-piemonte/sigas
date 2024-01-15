package it.csi.sigas.sigasbl.model.vo.base;

import it.csi.sigas.sigasbl.common.rest.VO;

import java.util.Date;

public class InsModBaseVO implements VO {
    private String insUser;
    private Date insDate;
    private Date modDate;
    private String modUser;

    public String getInsUser() {
        return insUser;
    }

    public void setInsUser(String insUser) {
        this.insUser = insUser;
    }

    public Date getInsDate() {
        return insDate;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getModUser() {
        return modUser;
    }

    public void setModUser(String modUser) {
        this.modUser = modUser;
    }
}
