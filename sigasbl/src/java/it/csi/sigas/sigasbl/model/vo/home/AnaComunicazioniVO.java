/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.vo.home;

import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.base.InsModBaseVO;

import java.util.Date;
import java.util.List;

public class AnaComunicazioniVO extends InsModBaseVO {

	private static final long serialVersionUID = 8629077478321324164L;

	private long idComunicazione;
	private AnagraficaSoggettoVO anagraficaSoggettoVO;
	private String annualita;
	private TipoComunicazioniVO tipoComunicazioneVO;
	//@JsonSerialize(using = CustomDateSerializer.class)
	//@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date dataDocumento;
	private String descrizione;
	private String note;
	private String datiRiassuntivi;
	private String nprotocollo;
	private String rifArchivio;
	private boolean allarmeOn;
	private List<AllegatoVO> allegati;
	private Date delDate;
	private String delUser;

	public long getIdComunicazione() {
		return idComunicazione;
	}
	public void setIdComunicazione(long idComunicazione) {
		this.idComunicazione = idComunicazione;
	}
	public AnagraficaSoggettoVO getAnagraficaSoggettoVO() {
		return anagraficaSoggettoVO;
	}
	public void setAnagraficaSoggettoVO(AnagraficaSoggettoVO anagraficaSoggettoVO) {
		this.anagraficaSoggettoVO = anagraficaSoggettoVO;
	}
	public String getAnnualita() {
		return annualita;
	}
	public void setAnnualita(String annualita) {
		this.annualita = annualita;
	}
	public TipoComunicazioniVO getTipoComunicazioneVO() {
		return tipoComunicazioneVO;
	}
	public void setTipoComunicazioneVO(TipoComunicazioniVO tipoComunicazioneVO) {
		this.tipoComunicazioneVO = tipoComunicazioneVO;
	}
	public Date getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDatiRiassuntivi() {
		return datiRiassuntivi;
	}
	public void setDatiRiassuntivi(String datiRiassuntivi) {
		this.datiRiassuntivi = datiRiassuntivi;
	}
	public String getNProtocollo() {
		return nprotocollo;
	}
	public void setNProtocollo(String nprotocollo) {
		this.nprotocollo = nprotocollo;
	}
	public String getRifArchivio() {
		return rifArchivio;
	}
	public void setRifArchivio(String rifArchivio) {
		this.rifArchivio = rifArchivio;
	}
	public boolean isAllarmeOn() {
		return allarmeOn;
	}
	public void setAllarmeOn(boolean allarmeOn) {
		this.allarmeOn = allarmeOn;
	}
	
	public List<AllegatoVO> getAllegati() {
		return allegati;
	}
	public void setAllegati(List<AllegatoVO> allegati) {
		this.allegati = allegati;
	}
	public Date getDelDate() {
		return delDate;
	}
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	public String getDelUser() {
		return delUser;
	}
	public void setDelUser(String delUser) {
		this.delUser = delUser;
	}
	
	
	
}
