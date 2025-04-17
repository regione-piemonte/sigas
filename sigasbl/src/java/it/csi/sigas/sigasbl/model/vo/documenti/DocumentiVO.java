/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.documenti;

import java.sql.Timestamp;
import java.util.List;

import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.depositocausionale.DepositoCausionaleVO;

public class DocumentiVO {


	private Integer idDocumento;

	private String annualita;

	private String cfPiva;

	private Timestamp dataProtocollazione;

	private String nomeFile;

	private Timestamp insDate;

	private String insUser;

	private Timestamp modDate;

	private String modUser;

	private String nProtocollo;

	private String note;

	private String rifArchivio;

	private AnagraficaSoggettoVO anagraficaSoggettoVO;

	private StatoDocumentoVO statoDocumentoVO;

	private TipoDocumentoVO tipoDocumentoVO;
	
	private TipoDocumentoVO tipoComunicazioneVO;
	
	private TipoDocumentoVO tipoRimborsoVO;
	
	private String noteBo;
	
	private List<AllegatoDocumentazioneVO> sigasAllegatos;
	
	private byte[] fileMaster;
	
	private String idIndex;
	
	private StatoArchiviazioneVO sigasStatoArchiviazioneVO;
	
	private String nProtocolloAccertamento;
	
	private String annoProtocolloAccertamento;
	
	private List<DepositoCausionaleVO> depositoCausionaleVOs;

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getAnnualita() {
		return annualita;
	}

	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}

	public String getCfPiva() {
		return cfPiva;
	}

	public void setCfPiva(String cfPiva) {
		this.cfPiva = cfPiva;
	}


	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
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

	public Timestamp getModDate() {
		return modDate;
	}

	public void setModDate(Timestamp modDate) {
		this.modDate = modDate;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public String getNProtocollo() {
		return this.nProtocollo;
	}

	public void setNProtocollo(String nProtocollo) {
		this.nProtocollo = nProtocollo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRifArchivio() {
		return rifArchivio;
	}

	public void setRifArchivio(String rifArchivio) {
		this.rifArchivio = rifArchivio;
	}

	public AnagraficaSoggettoVO getAnagraficaSoggettoVO() {
		return anagraficaSoggettoVO;
	}

	public void setAnagraficaSoggettoVO(AnagraficaSoggettoVO anagraficaSoggettoVO) {
		this.anagraficaSoggettoVO = anagraficaSoggettoVO;
	}

	public StatoDocumentoVO getStatoDocumentoVO() {
		return statoDocumentoVO;
	}

	public void setStatoDocumentoVO(StatoDocumentoVO statoDocumentoVO) {
		this.statoDocumentoVO = statoDocumentoVO;
	}

	public TipoDocumentoVO getTipoDocumentoVO() {
		return tipoDocumentoVO;
	}

	public void setTipoDocumentoVO(TipoDocumentoVO tipoDocumentoVO) {
		this.tipoDocumentoVO = tipoDocumentoVO;
	}

	public String getNoteBo() {
		return noteBo;
	}

	public void setNoteBo(String noteBo) {
		this.noteBo = noteBo;
	}

	public Timestamp getDataProtocollazione() {
		return dataProtocollazione;
	}

	public void setDataProtocollazione(Timestamp dataProtocollazione) {
		this.dataProtocollazione = dataProtocollazione;
	}

	public List<AllegatoDocumentazioneVO> getSigasAllegatos() {
		return sigasAllegatos;
	}

	public void setSigasAllegatos(List<AllegatoDocumentazioneVO> sigasAllegatos) {
		this.sigasAllegatos = sigasAllegatos;
	}

	public byte[] getFileMaster() {
		return fileMaster;
	}

	public void setFileMaster(byte[] fileMaster) {
		this.fileMaster = fileMaster;
	}

	public String getIdIndex() {
		return idIndex;
	}

	public void setIdIndex(String idIndex) {
		this.idIndex = idIndex;
	}

	public StatoArchiviazioneVO getSigasStatoArchiviazioneVO() {
		return sigasStatoArchiviazioneVO;
	}

	public void setSigasStatoArchiviazioneVO(StatoArchiviazioneVO sigasStatoArchiviazioneVO) {
		this.sigasStatoArchiviazioneVO = sigasStatoArchiviazioneVO;
	}

	public TipoDocumentoVO getTipoComunicazioneVO() {
		return tipoComunicazioneVO;
	}

	public void setTipoComunicazioneVO(TipoDocumentoVO tipoComunicazioneVO) {
		this.tipoComunicazioneVO = tipoComunicazioneVO;
	}

	public TipoDocumentoVO getTipoRimborsoVO() {
		return tipoRimborsoVO;
	}

	public void setTipoRimborsoVO(TipoDocumentoVO tipoRimborsoVO) {
		this.tipoRimborsoVO = tipoRimborsoVO;
	}

	public String getnProtocolloAccertamento() {
		return nProtocolloAccertamento;
	}

	public void setnProtocolloAccertamento(String nProtocolloAccertamento) {
		this.nProtocolloAccertamento = nProtocolloAccertamento;
	}

	public String getAnnoProtocolloAccertamento() {
		return annoProtocolloAccertamento;
	}

	public void setAnnoProtocolloAccertamento(String annoProtocolloAccertamento) {
		this.annoProtocolloAccertamento = annoProtocolloAccertamento;
	}

	public List<DepositoCausionaleVO> getDepositoCausionaleVOs() {
		return depositoCausionaleVOs;
	}

	public void setDepositoCausionaleVOs(List<DepositoCausionaleVO> depositoCausionaleVOs) {
		this.depositoCausionaleVOs = depositoCausionaleVOs;
	}	
	
}
