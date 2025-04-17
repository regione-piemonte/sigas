/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class FusioneSoggettoRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "L'identificativo del soggetto confluente non deve essere vuoto")
	private Long idAnagIncorporante;
	
	@NotNull(message = "L'identificativo del soggetto derivante non deve essere vuoto")
	private Long idAnagIncorporato;
	
	@NotNull(message = "La data di fusione non deve essere vuota")
	private Date dataFusione;
	
	@NotNull(message = "la nota della fusione non deve essere vuota")
	private String note;
	
	public Long getIdAnagIncorporante() {
		return idAnagIncorporante;
	}

	public void setIdAnagIncorporante(Long idAnagIncorporante) {
		this.idAnagIncorporante = idAnagIncorporante;
	}

	public Long getIdAnagIncorporato() {
		return idAnagIncorporato;
	}

	public void setIdAnagIncorporato(Long idAnagIncorporato) {
		this.idAnagIncorporato = idAnagIncorporato;
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

}
