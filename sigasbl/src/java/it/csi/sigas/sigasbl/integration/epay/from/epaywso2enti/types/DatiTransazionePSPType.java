
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DatiTransazionePSPType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiTransazionePSPType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdPSP" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="RagioneSocialePSP" type="{http://www.csi.it/epay/epaywso/types}String250Type" minOccurs="0"/>
 *         &lt;element name="TipoVersamento" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TipoVersamentoType" minOccurs="0"/>
 *         &lt;element name="IdFlussoRendicontazionePSP" type="{http://www.csi.it/epay/epaywso/types}String35Type" minOccurs="0"/>
 *         &lt;element name="DataOraAvvioTransazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IUR" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="DataOraAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="TipoSicurezza" type="{http://www.csi.it/epay/epaywso/types}String50Type" minOccurs="0"/>
 *         &lt;element name="ImportoTransato" type="{http://www.csi.it/epay/epaywso/types}ImportoType" minOccurs="0"/>
 *         &lt;element name="ImportoCommissioni" type="{http://www.csi.it/epay/epaywso/types}ImportoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiTransazionePSPType", propOrder = {
    "idPSP",
    "ragioneSocialePSP",
    "tipoVersamento",
    "idFlussoRendicontazionePSP",
    "dataOraAvvioTransazione",
    "iur",
    "dataOraAutorizzazione",
    "tipoSicurezza",
    "importoTransato",
    "importoCommissioni"
})
public class DatiTransazionePSPType {

    @XmlElement(name = "IdPSP", required = true)
    protected String idPSP;
    @XmlElement(name = "RagioneSocialePSP")
    protected String ragioneSocialePSP;
    @XmlElement(name = "TipoVersamento")
    protected String tipoVersamento;
    @XmlElement(name = "IdFlussoRendicontazionePSP")
    protected String idFlussoRendicontazionePSP;
    @XmlElement(name = "DataOraAvvioTransazione", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraAvvioTransazione;
    @XmlElement(name = "IUR", required = true)
    protected String iur;
    @XmlElement(name = "DataOraAutorizzazione")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraAutorizzazione;
    @XmlElement(name = "TipoSicurezza")
    protected String tipoSicurezza;
    @XmlElement(name = "ImportoTransato")
    protected BigDecimal importoTransato;
    @XmlElement(name = "ImportoCommissioni")
    protected BigDecimal importoCommissioni;

    /**
     * Gets the value of the idPSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPSP() {
        return idPSP;
    }

    /**
     * Sets the value of the idPSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPSP(String value) {
        this.idPSP = value;
    }

    /**
     * Gets the value of the ragioneSocialePSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSocialePSP() {
        return ragioneSocialePSP;
    }

    /**
     * Sets the value of the ragioneSocialePSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSocialePSP(String value) {
        this.ragioneSocialePSP = value;
    }

    /**
     * Gets the value of the tipoVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoVersamento() {
        return tipoVersamento;
    }

    /**
     * Sets the value of the tipoVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoVersamento(String value) {
        this.tipoVersamento = value;
    }

    /**
     * Gets the value of the idFlussoRendicontazionePSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFlussoRendicontazionePSP() {
        return idFlussoRendicontazionePSP;
    }

    /**
     * Sets the value of the idFlussoRendicontazionePSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFlussoRendicontazionePSP(String value) {
        this.idFlussoRendicontazionePSP = value;
    }

    /**
     * Gets the value of the dataOraAvvioTransazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraAvvioTransazione() {
        return dataOraAvvioTransazione;
    }

    /**
     * Sets the value of the dataOraAvvioTransazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraAvvioTransazione(XMLGregorianCalendar value) {
        this.dataOraAvvioTransazione = value;
    }

    /**
     * Gets the value of the iur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUR() {
        return iur;
    }

    /**
     * Sets the value of the iur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUR(String value) {
        this.iur = value;
    }

    /**
     * Gets the value of the dataOraAutorizzazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraAutorizzazione() {
        return dataOraAutorizzazione;
    }

    /**
     * Sets the value of the dataOraAutorizzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraAutorizzazione(XMLGregorianCalendar value) {
        this.dataOraAutorizzazione = value;
    }

    /**
     * Gets the value of the tipoSicurezza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoSicurezza() {
        return tipoSicurezza;
    }

    /**
     * Sets the value of the tipoSicurezza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoSicurezza(String value) {
        this.tipoSicurezza = value;
    }

    /**
     * Gets the value of the importoTransato property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTransato() {
        return importoTransato;
    }

    /**
     * Sets the value of the importoTransato property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTransato(BigDecimal value) {
        this.importoTransato = value;
    }

    /**
     * Gets the value of the importoCommissioni property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoCommissioni() {
        return importoCommissioni;
    }

    /**
     * Sets the value of the importoCommissioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoCommissioni(BigDecimal value) {
        this.importoCommissioni = value;
    }

}
