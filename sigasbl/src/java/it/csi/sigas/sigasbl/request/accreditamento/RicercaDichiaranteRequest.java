/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.accreditamento;

import java.io.Serializable;

import javax.validation.constraints.Size;


public class RicercaDichiaranteRequest  implements Serializable {

	private static final long serialVersionUID = 5877743124350322383L;

	private String denominazione;
	@Size(message = "codiceFiscale deve essere lungo 16", min = 0, max = 16)
	private String codiceFiscale;
	@Size(message = "partita iva deve essere lunga 11", min = 0, max = 11)
	private String partitaIva;
	private String numeroProtocollo;
	
	private String codiceAzienda;

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	
	
	

}
