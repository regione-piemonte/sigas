/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.vo.template;



public class DatiTemplateCompilatiVO{

	private static final long serialVersionUID = -8153081670459225437L;


	private String oggetto;
	private String descrizione;
	
	private String intestazione;
	private String indirizzo;
	private String capComuneProvinvcia;




	public String getOggetto() {
		return oggetto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIntestazione() {
		return intestazione;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCapComuneProvinvcia() {
		return capComuneProvinvcia;
	}

	public void setCapComuneProvinvcia(String capComuneProvinvcia) {
		this.capComuneProvinvcia = capComuneProvinvcia;
	}

	
	
	

}
