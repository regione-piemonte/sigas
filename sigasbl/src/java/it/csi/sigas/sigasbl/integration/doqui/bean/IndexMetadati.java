/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.integration.doqui.bean;

/**
 * @author giuganino
 *
 */
public class IndexMetadati {

	public static final String META_DIRECTORY = "sivia:idIstanza";
	
	private String folder = null;
	private String fruitore = null;
			
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getFruitore() {
		return fruitore;
	}
	public void setFruitore(String fruitore) {
		this.fruitore = fruitore;
	}
	
}