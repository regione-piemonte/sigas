
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
 * <p>Java class for RichiestaDiRevocaResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RichiestaDiRevocaResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificativoDominio" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="ApplicationId" type="{http://www.csi.it/epay/epaywso/types}String50Type"/>
 *         &lt;element name="IdentificativoMessaggioEsito" type="{http://www.csi.it/epay/epaywso/types}String50Type"/>
 *         &lt;element name="DataOraMessaggioEsito" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="RiferimentoMessaggioRevoca" type="{http://www.csi.it/epay/epaywso/types}String50Type"/>
 *         &lt;element name="RiferimentoDataOraRevoca" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IstitutoAttestante" type="{http://www.csi.it/epay/epaywso/types}SoggettoType"/>
 *         &lt;element name="ImportoPagato" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/>
 *         &lt;element name="IUV" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="CodiceContestoPagamento" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="InvioOkRispostaRevoca" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;sequence>
 *           &lt;element name="XML" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="DatiSingoloEsitoRevoca" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}DatiEsitoSingolaRevocaType"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaDiRevocaResponseType", propOrder = {
    "identificativoDominio",
    "applicationId",
    "identificativoMessaggioEsito",
    "dataOraMessaggioEsito",
    "riferimentoMessaggioRevoca",
    "riferimentoDataOraRevoca",
    "istitutoAttestante",
    "importoPagato",
    "iuv",
    "codiceContestoPagamento",
    "invioOkRispostaRevoca",
    "xml",
    "datiSingoloEsitoRevoca"
})
public class RichiestaDiRevocaResponseType {

    @XmlElement(name = "IdentificativoDominio", required = true)
    protected String identificativoDominio;
    @XmlElement(name = "ApplicationId", required = true)
    protected String applicationId;
    @XmlElement(name = "IdentificativoMessaggioEsito", required = true)
    protected String identificativoMessaggioEsito;
    @XmlElement(name = "DataOraMessaggioEsito", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraMessaggioEsito;
    @XmlElement(name = "RiferimentoMessaggioRevoca", required = true)
    protected String riferimentoMessaggioRevoca;
    @XmlElement(name = "RiferimentoDataOraRevoca", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar riferimentoDataOraRevoca;
    @XmlElement(name = "IstitutoAttestante", required = true)
    protected SoggettoType istitutoAttestante;
    @XmlElement(name = "ImportoPagato", required = true)
    protected BigDecimal importoPagato;
    @XmlElement(name = "IUV", required = true)
    protected String iuv;
    @XmlElement(name = "CodiceContestoPagamento", required = true)
    protected String codiceContestoPagamento;
    @XmlElement(name = "InvioOkRispostaRevoca")
    protected boolean invioOkRispostaRevoca;
    @XmlElement(name = "XML", required = true)
    protected byte[] xml;
    @XmlElement(name = "DatiSingoloEsitoRevoca", required = true)
    protected DatiEsitoSingolaRevocaType datiSingoloEsitoRevoca;

    /**
     * Gets the value of the identificativoDominio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoDominio() {
        return identificativoDominio;
    }

    /**
     * Sets the value of the identificativoDominio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoDominio(String value) {
        this.identificativoDominio = value;
    }

    /**
     * Gets the value of the applicationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Sets the value of the applicationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationId(String value) {
        this.applicationId = value;
    }

    /**
     * Gets the value of the identificativoMessaggioEsito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoMessaggioEsito() {
        return identificativoMessaggioEsito;
    }

    /**
     * Sets the value of the identificativoMessaggioEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoMessaggioEsito(String value) {
        this.identificativoMessaggioEsito = value;
    }

    /**
     * Gets the value of the dataOraMessaggioEsito property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraMessaggioEsito() {
        return dataOraMessaggioEsito;
    }

    /**
     * Sets the value of the dataOraMessaggioEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraMessaggioEsito(XMLGregorianCalendar value) {
        this.dataOraMessaggioEsito = value;
    }

    /**
     * Gets the value of the riferimentoMessaggioRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoMessaggioRevoca() {
        return riferimentoMessaggioRevoca;
    }

    /**
     * Sets the value of the riferimentoMessaggioRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoMessaggioRevoca(String value) {
        this.riferimentoMessaggioRevoca = value;
    }

    /**
     * Gets the value of the riferimentoDataOraRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRiferimentoDataOraRevoca() {
        return riferimentoDataOraRevoca;
    }

    /**
     * Sets the value of the riferimentoDataOraRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRiferimentoDataOraRevoca(XMLGregorianCalendar value) {
        this.riferimentoDataOraRevoca = value;
    }

    /**
     * Gets the value of the istitutoAttestante property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoType }
     *     
     */
    public SoggettoType getIstitutoAttestante() {
        return istitutoAttestante;
    }

    /**
     * Sets the value of the istitutoAttestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoType }
     *     
     */
    public void setIstitutoAttestante(SoggettoType value) {
        this.istitutoAttestante = value;
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
     * Gets the value of the codiceContestoPagamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceContestoPagamento() {
        return codiceContestoPagamento;
    }

    /**
     * Sets the value of the codiceContestoPagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceContestoPagamento(String value) {
        this.codiceContestoPagamento = value;
    }

    /**
     * Gets the value of the invioOkRispostaRevoca property.
     * 
     */
    public boolean isInvioOkRispostaRevoca() {
        return invioOkRispostaRevoca;
    }

    /**
     * Sets the value of the invioOkRispostaRevoca property.
     * 
     */
    public void setInvioOkRispostaRevoca(boolean value) {
        this.invioOkRispostaRevoca = value;
    }

    /**
     * Gets the value of the xml property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getXML() {
        return xml;
    }

    /**
     * Sets the value of the xml property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setXML(byte[] value) {
        this.xml = value;
    }

    /**
     * Gets the value of the datiSingoloEsitoRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link DatiEsitoSingolaRevocaType }
     *     
     */
    public DatiEsitoSingolaRevocaType getDatiSingoloEsitoRevoca() {
        return datiSingoloEsitoRevoca;
    }

    /**
     * Sets the value of the datiSingoloEsitoRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiEsitoSingolaRevocaType }
     *     
     */
    public void setDatiSingoloEsitoRevoca(DatiEsitoSingolaRevocaType value) {
        this.datiSingoloEsitoRevoca = value;
    }

}
