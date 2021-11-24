
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.csi.sigas.sigasbl.integration.epay.from.types.SoggettoType;


/**
 * <p>Java class for NotificaPagamentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificaPagamentoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdPosizioneDebitoria" type="{http://www.csi.it/epay/epaywso/types}String50Type" minOccurs="0"/>
 *         &lt;element name="AnnoDiRiferimento" type="{http://www.csi.it/epay/epaywso/types}AnnoType" minOccurs="0"/>
 *         &lt;element name="IUV" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="ImportoPagato" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/>
 *         &lt;element name="DataScadenza" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DescrizioneCausaleVersamento" type="{http://www.csi.it/epay/epaywso/types}String140Type" minOccurs="0"/>
 *         &lt;element name="DataEsitoPagamento" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="SoggettoDebitore" type="{http://www.csi.it/epay/epaywso/types}SoggettoType"/>
 *         &lt;element name="SoggettoVersante" type="{http://www.csi.it/epay/epaywso/types}SoggettoType" minOccurs="0"/>
 *         &lt;element name="DatiTransazionePSP" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}DatiTransazionePSPType"/>
 *         &lt;element name="DatiSpecificiRiscossione" type="{http://www.csi.it/epay/epaywso/types}String140Type" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.csi.it/epay/epaywso/types}String2000Type" minOccurs="0"/>
 *         &lt;element name="CodiceAvviso" type="{http://www.csi.it/epay/epaywso/types}String35Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificaPagamentoType", propOrder = {
    "idPosizioneDebitoria",
    "annoDiRiferimento",
    "iuv",
    "importoPagato",
    "dataScadenza",
    "descrizioneCausaleVersamento",
    "dataEsitoPagamento",
    "soggettoDebitore",
    "soggettoVersante",
    "datiTransazionePSP",
    "datiSpecificiRiscossione",
    "note",
    "codiceAvviso"
})
public class NotificaPagamentoType {

    @XmlElement(name = "IdPosizioneDebitoria")
    protected String idPosizioneDebitoria;
    @XmlElement(name = "AnnoDiRiferimento")
    protected Integer annoDiRiferimento;
    @XmlElement(name = "IUV", required = true)
    protected String iuv;
    @XmlElement(name = "ImportoPagato", required = true)
    protected BigDecimal importoPagato;
    @XmlElement(name = "DataScadenza")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataScadenza;
    @XmlElement(name = "DescrizioneCausaleVersamento")
    protected String descrizioneCausaleVersamento;
    @XmlElement(name = "DataEsitoPagamento", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataEsitoPagamento;
    @XmlElement(name = "SoggettoDebitore", required = true)
    protected SoggettoType soggettoDebitore;
    @XmlElement(name = "SoggettoVersante")
    protected SoggettoType soggettoVersante;
    @XmlElement(name = "DatiTransazionePSP", required = true)
    protected DatiTransazionePSPType datiTransazionePSP;
    @XmlElement(name = "DatiSpecificiRiscossione")
    protected String datiSpecificiRiscossione;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "CodiceAvviso")
    protected String codiceAvviso;

    /**
     * Gets the value of the idPosizioneDebitoria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPosizioneDebitoria() {
        return idPosizioneDebitoria;
    }

    /**
     * Sets the value of the idPosizioneDebitoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPosizioneDebitoria(String value) {
        this.idPosizioneDebitoria = value;
    }

    /**
     * Gets the value of the annoDiRiferimento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoDiRiferimento() {
        return annoDiRiferimento;
    }

    /**
     * Sets the value of the annoDiRiferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoDiRiferimento(Integer value) {
        this.annoDiRiferimento = value;
    }

    /**
     * Gets the value of the iuv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUV() {
        return iuv;
    }

    /**
     * Sets the value of the iuv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUV(String value) {
        this.iuv = value;
    }

    /**
     * Gets the value of the importoPagato property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoPagato() {
        return importoPagato;
    }

    /**
     * Sets the value of the importoPagato property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoPagato(BigDecimal value) {
        this.importoPagato = value;
    }

    /**
     * Gets the value of the dataScadenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Sets the value of the dataScadenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

    /**
     * Gets the value of the descrizioneCausaleVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneCausaleVersamento() {
        return descrizioneCausaleVersamento;
    }

    /**
     * Sets the value of the descrizioneCausaleVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneCausaleVersamento(String value) {
        this.descrizioneCausaleVersamento = value;
    }

    /**
     * Gets the value of the dataEsitoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEsitoPagamento() {
        return dataEsitoPagamento;
    }

    /**
     * Sets the value of the dataEsitoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEsitoPagamento(XMLGregorianCalendar value) {
        this.dataEsitoPagamento = value;
    }

    /**
     * Gets the value of the soggettoDebitore property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoType }
     *     
     */
    public SoggettoType getSoggettoDebitore() {
        return soggettoDebitore;
    }

    /**
     * Sets the value of the soggettoDebitore property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoType }
     *     
     */
    public void setSoggettoDebitore(SoggettoType value) {
        this.soggettoDebitore = value;
    }

    /**
     * Gets the value of the soggettoVersante property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoType }
     *     
     */
    public SoggettoType getSoggettoVersante() {
        return soggettoVersante;
    }

    /**
     * Sets the value of the soggettoVersante property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoType }
     *     
     */
    public void setSoggettoVersante(SoggettoType value) {
        this.soggettoVersante = value;
    }

    /**
     * Gets the value of the datiTransazionePSP property.
     * 
     * @return
     *     possible object is
     *     {@link DatiTransazionePSPType }
     *     
     */
    public DatiTransazionePSPType getDatiTransazionePSP() {
        return datiTransazionePSP;
    }

    /**
     * Sets the value of the datiTransazionePSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiTransazionePSPType }
     *     
     */
    public void setDatiTransazionePSP(DatiTransazionePSPType value) {
        this.datiTransazionePSP = value;
    }

    /**
     * Gets the value of the datiSpecificiRiscossione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiSpecificiRiscossione() {
        return datiSpecificiRiscossione;
    }

    /**
     * Sets the value of the datiSpecificiRiscossione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiSpecificiRiscossione(String value) {
        this.datiSpecificiRiscossione = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the codiceAvviso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAvviso() {
        return codiceAvviso;
    }

    /**
     * Sets the value of the codiceAvviso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAvviso(String value) {
        this.codiceAvviso = value;
    }

}
