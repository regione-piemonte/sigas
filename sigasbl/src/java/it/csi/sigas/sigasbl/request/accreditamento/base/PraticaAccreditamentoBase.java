package it.csi.sigas.sigasbl.request.accreditamento.base;

public class PraticaAccreditamentoBase {

    private Long idUtenteProvv;

    private String stato;

    private String note;


    public Long getIdUtenteProvv() {
        return idUtenteProvv;
    }

    public void setIdUtenteProvv(Long idUtenteProvv) {
        this.idUtenteProvv = idUtenteProvv;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
