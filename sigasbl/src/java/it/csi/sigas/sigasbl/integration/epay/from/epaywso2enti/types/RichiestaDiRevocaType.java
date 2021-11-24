
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.csi.sigas.sigasbl.integration.epay.from.types.SoggettoType;


/**
 * <p>Java class for RichiestaDiRevocaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RichiestaDiRevocaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificativoDominio" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="ApplicationId" type="{http://www.csi.it/epay/epaywso/types}String50Type"/>
 *         &lt;element name="IdentificativoMessaggioRevoca" type="{http://www.csi.it/epay/epaywso/types}String50Type"/>
 *         &lt;element name="DataOraMessaggioRevoca" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IstitutoAttestante" type="{http://www.csi.it/epay/epaywso/types}SoggettoType"/>
 *         &lt;element name="ImportoPagato" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/>
 *         &lt;element name="IUV" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="CodiceContestoPagamento" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="TipoRevoca" type="{http://www.csi.it/epay/epaywso/types}Numero6CifreType"/>
 *         &lt;sequence>
 *           &lt;element name="XML" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;/sequence>
 *         &lt;element name="ListaDatiSingolaRevoca">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DatiSingolaRevoca" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}DatiSingolaRevocaType" maxOccurs="5"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaDiRevocaType", propOrder = {
    "identificativoDominio",
    "applicationId",
    "identificativoMessaggioRevoca",
    "dataOraMessaggioRevoca",
    "istitutoAttestante",
    "importoPagato",
    "iuv",
    "codiceContestoPagamento",
    "tipoRevoca",
    "xml",
    "listaDatiSingolaRevoca"
})
public class RichiestaDiRevocaType {

    @XmlElement(name = "IdentificativoDominio", required = true)
    protected String identificativoDominio;
    @XmlElement(name = "ApplicationId", required = true)
    protected String applicationId;
    @XmlElement(name = "IdentificativoMessaggioRevoca", required = true)
    protected String identificativoMessaggioRevoca;
    @XmlElement(name = "DataOraMessaggioRevoca", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOraMessaggioRevoca;
    @XmlElement(name = "IstitutoAttestante", required = true)
    protected SoggettoType istitutoAttestante;
    @XmlElement(name = "ImportoPagato", required = true)
    protected BigDecimal importoPagato;
    @XmlElement(name = "IUV", required = true)
    protected String iuv;
    @XmlElement(name = "CodiceContestoPagamento", required = true)
    protected String codiceContestoPagamento;
    @XmlElement(name = "TipoRevoca")
    protected int tipoRevoca;
    @XmlElement(name = "XML", required = true)
    protected byte[] xml;
    @XmlElement(name = "ListaDatiSingolaRevoca", required = true)
    protected RichiestaDiRevocaType.ListaDatiSingolaRevoca listaDatiSingolaRevoca;

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
     * Gets the value of the identificativoMessaggioRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoMessaggioRevoca() {
        return identificativoMessaggioRevoca;
    }

    /**
     * Sets the value of the identificativoMessaggioRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoMessaggioRevoca(String value) {
        this.identificativoMessaggioRevoca = value;
    }

    /**
     * Gets the value of the dataOraMessaggioRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataOraMessaggioRevoca() {
        return dataOraMessaggioRevoca;
    }

    /**
     * Sets the value of the dataOraMessaggioRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataOraMessaggioRevoca(XMLGregorianCalendar value) {
        this.dataOraMessaggioRevoca = value;
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
     * Gets the value of the tipoRevoca property.
     * 
     */
    public int getTipoRevoca() {
        return tipoRevoca;
    }

    /**
     * Sets the value of the tipoRevoca property.
     * 
     */
    public void setTipoRevoca(int value) {
        this.tipoRevoca = value;
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
     * Gets the value of the listaDatiSingolaRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaDiRevocaType.ListaDatiSingolaRevoca }
     *     
     */
    public RichiestaDiRevocaType.ListaDatiSingolaRevoca getListaDatiSingolaRevoca() {
        return listaDatiSingolaRevoca;
    }

    /**
     * Sets the value of the listaDatiSingolaRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaDiRevocaType.ListaDatiSingolaRevoca }
     *     
     */
    public void setListaDatiSingolaRevoca(RichiestaDiRevocaType.ListaDatiSingolaRevoca value) {
        this.listaDatiSingolaRevoca = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="DatiSingolaRevoca" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}DatiSingolaRevocaType" maxOccurs="5"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "datiSingolaRevoca"
    })
    public static class ListaDatiSingolaRevoca {

        @XmlElement(name = "DatiSingolaRevoca", required = true)
        protected List<DatiSingolaRevocaType> datiSingolaRevoca;

        /**
         * Gets the value of the datiSingolaRevoca property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the datiSingolaRevoca property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDatiSingolaRevoca().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DatiSingolaRevocaType }
         * 
         * 
         */
        public List<DatiSingolaRevocaType> getDatiSingolaRevoca() {
            if (datiSingolaRevoca == null) {
                datiSingolaRevoca = new ArrayList<DatiSingolaRevocaType>();
            }
            return this.datiSingolaRevoca;
        }

    }

}
