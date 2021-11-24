/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.documenti;

import java.sql.Timestamp;

public class AllegatoDocumentazioneVO {
	
	private Integer idAllegato;

	private Timestamp dataOraProtocollo;

	private String descrizione;

	private String idIndex;

	private StatoArchiviazioneVO statoArchiviazioneVO;

	private Timestamp insDate;

	private String insUser;

	private String nomeFile;

	private String numeroProtocollo;
	
	private byte[] file;
	
	private Integer idAllegatoDocumento;
	
	public Integer getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}

	public Timestamp getDataOraProtocollo() {
		return dataOraProtocollo;
	}

	public void setDataOraProtocollo(Timestamp dataOraProtocollo) {
		this.dataOraProtocollo = dataOraProtocollo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getIdIndex() {
		return idIndex;
	}

	public void setIdIndex(String idIndex) {
		this.idIndex = idIndex;
	}


	

	public StatoArchiviazioneVO getStatoArchiviazioneVO() {
		return statoArchiviazioneVO;
	}

	public void setStatoArchiviazioneVO(StatoArchiviazioneVO statoArchiviazioneVO) {
		this.statoArchiviazioneVO = statoArchiviazioneVO;
	}

	public Timestamp getInsDate() {
		return insDate;
	}

	public void setInsDate(Timestamp insDate) {
		this.insDate = insDate;
	}

	public String getInsUser() {
		return insUser;
	}

	public void setInsUser(String insUser) {
		this.insUser = insUser;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public Integer getIdAllegatoDocumento() {
		return idAllegatoDocumento;
	}

	public void setIdAllegatoDocumento(Integer idAllegatoDocumento) {
		this.idAllegatoDocumento = idAllegatoDocumento;
	}

	
	
}
