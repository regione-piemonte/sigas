/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.documenti;

public class StatoArchiviazioneVO {
	
	private Integer idStatoArchiviazione;

	private String codiceStato;

	private String descrizione;

	

	public Integer getIdStatoArchiviazione() {
		return idStatoArchiviazione;
	}

	public void setIdStatoArchiviazione(Integer idStatoArchiviazione) {
		this.idStatoArchiviazione = idStatoArchiviazione;
	}

	public String getCodiceStato() {
		return codiceStato;
	}

	public void setCodiceStato(String codiceStato) {
		this.codiceStato = codiceStato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	
}
