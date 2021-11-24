/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.common;

public class AuthorizationRoles {

	
	public static final String HOME = "hasRole('UCSIGAS1')";
	public static final String ROLES_BO_FO = "hasRole('UCSIGAS1') or hasRole('TO BE DEFINED')"; // xmfROLE: TO BE DEFINED!!!!!
	// TUTTI HANNO IL RUOLO UTENTE
		public static final String UTENTE = "hasRole('UTENTE')";
}