/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.security;

import org.springframework.security.core.GrantedAuthority;

public class SigasGrantedAuthority implements GrantedAuthority {

	public static final int TIPO_AUTHORITY_RUOLO_IRIDE = 1;
	public static final int TIPO_AUTHORITY_USE_CASE_IRIDE = 2;
	public static final int TIPO_AUTHORITY_ALTRO = 3;

	private String codice;
	private int tipoAuthority;
	private String descrizione;
	private String descrizioneEstesa;
	private String descrizioneMoltoEstesa;

	private static final long serialVersionUID = 7104984587412214819L;

	public SigasGrantedAuthority(String codice, String descrizione, String descEstesa, String descMoltoEstesa, int tipoAuthority) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
		this.tipoAuthority = tipoAuthority;
		this.descrizioneEstesa = descEstesa;
		this.descrizioneMoltoEstesa = descMoltoEstesa;
	}

	public SigasGrantedAuthority(String codice, String descrizione, int tipoAuthority) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
		this.tipoAuthority = tipoAuthority;
		this.descrizioneEstesa = descrizione;
		this.descrizioneMoltoEstesa = descrizione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((descrizioneEstesa == null) ? 0 : descrizioneEstesa.hashCode());
		result = prime * result + ((descrizioneMoltoEstesa == null) ? 0 : descrizioneMoltoEstesa.hashCode());
		result = prime * result + tipoAuthority;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SigasGrantedAuthority other = (SigasGrantedAuthority) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (descrizioneEstesa == null) {
			if (other.descrizioneEstesa != null)
				return false;
		} else if (!descrizioneEstesa.equals(other.descrizioneEstesa))
			return false;
		if (descrizioneMoltoEstesa == null) {
			if (other.descrizioneMoltoEstesa != null)
				return false;
		} else if (!descrizioneMoltoEstesa.equals(other.descrizioneMoltoEstesa))
			return false;
		if (tipoAuthority != other.tipoAuthority)
			return false;
		return true;
	}

	@Override
	public String getAuthority() {
		return this.getCodice();
	}

	public int getTipoAuthority() {
		return tipoAuthority;
	}

	public void setTipoAuthority(int tipoAuthority) {
		this.tipoAuthority = tipoAuthority;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	@Override
	public String toString() {
		return "SigasGrantedAuthority [codice=" + codice + ", tipoAuthority=" + tipoAuthority + ", descrizione=" + descrizione + ", descrizioneEstesa=" + descrizioneEstesa + ", descrizioneMoltoEstesa="
				+ descrizioneMoltoEstesa + "]";
	}

}
