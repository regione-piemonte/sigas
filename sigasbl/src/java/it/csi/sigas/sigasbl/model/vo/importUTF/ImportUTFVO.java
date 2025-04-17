/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.importUTF;

import java.io.Serializable;
import java.util.Date;

public class ImportUTFVO implements Serializable {

	private static final long serialVersionUID = 285095876673453599L;

	private long importId;
	private String filename;
	private String anno;
	private int numeroFile;
	private Date dataRiferimento;
	private Boolean selectedImport;
	private int esito;
	
	public long getImportId() {
		return importId;
	}
	public void setImportId(long importId) {
		this.importId = importId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public int getNumeroFile() {
		return numeroFile;
	}
	public void setNumeroFile(int numeroFile) {
		this.numeroFile = numeroFile;
	}
	public Date getDataRiferimento() {
		return dataRiferimento;
	}
	public void setDataRiferimento(Date dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}
	public Boolean getSelectedImport() {
		return selectedImport;
	}
	public void setSelectedImport(Boolean selectedImport) {
		this.selectedImport = selectedImport;
	}
	public int getEsito() {
		return esito;
	}
	public void setEsito(int esito) {
		this.esito = esito;
	}

}
