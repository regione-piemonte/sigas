package it.csi.sigas.sigasbl.model.vo.base;

import it.csi.sigas.sigasbl.common.rest.VO;

public class LuogoBaseVO implements VO {

    private Long id;
    private String denominazione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    @Override
    public String toString() {
        return "[id=" + getId() + ", denominazione=" + getDenominazione() + "]";
    }
}
