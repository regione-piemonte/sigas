/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo;

import java.util.Date;

import it.csi.sigas.sigasbl.common.rest.VO;

public class AnagraficaSoggettoVO implements VO {

	private static final long serialVersionUID = -3157914097064569723L;
	public Long idAnag;
	public Long idFusione;
    public String codiceAzienda;
    public String denominazione;
    public String indirizzo;
    public String iban;
    public String tipo;
    public Integer status; 
    public boolean fideussione;
    public Double importoFideussione;
    public String telefono;
    public String pec;
    public String email;
    public Date dataFusione;
    public String note;
    public String riferimento;
    public Long idProvincia;
    public Long idComune;
    public double totVersato;
    private Long version;

	private String insUser;
	private Date insDate;
	private Date modDate;
	private String modUser;
	private String cfPiva;

	public Long getIdAnag() {

        return idAnag;
    }

    public void setIdAnag(Long idAnag) {
        this.idAnag = idAnag;
    }

    public Long getIdFusione() {
		return idFusione;
	}

	public void setIdFusione(Long idFusione) {
		this.idFusione = idFusione;
	}

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

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public boolean isFideussione() {
		return fideussione;
	}

	public void setFideussione(boolean fideussione) {
		this.fideussione = fideussione;
	}

	public Double getImportoFideussione() {
		return importoFideussione;
	}

	public void setImportoFideussione(Double importoFideussione) {
		this.importoFideussione = importoFideussione;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataFusione() {
		return dataFusione;
	}

	public void setDataFusione(Date dataFusione) {
		this.dataFusione = dataFusione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	
	public Long getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Long idProvincia) {
		this.idProvincia = idProvincia;
	}

	public Long getIdComune() {
		return idComune;
	}

	public void setIdComune(Long idComune) {
		this.idComune = idComune;
	}
	
	public double getTotVersato() {
		return totVersato;
	}
	
	public void setTotVersato(double totVersato) {
		this.totVersato = totVersato;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

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

	public String getCfPiva() {
		return cfPiva;
	}

	public void setCfPiva(String cfPiva) {
		this.cfPiva = cfPiva;
	}
	
	
}
