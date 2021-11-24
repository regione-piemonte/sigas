
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CorpoRichiesteDiRevocaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CorpoRichiesteDiRevocaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RichiestaDiRevocaResponse" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}RichiestaDiRevocaResponseType"/>
 *         &lt;element name="ElencoRichiesteDiRevoca">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RichiestaDiRevoca" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}RichiestaDiRevocaType" maxOccurs="1000"/>
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
@XmlType(name = "CorpoRichiesteDiRevocaType", propOrder = {
    "richiestaDiRevocaResponse",
    "elencoRichiesteDiRevoca"
})
public class CorpoRichiesteDiRevocaType {

    @XmlElement(name = "RichiestaDiRevocaResponse", required = true)
    protected RichiestaDiRevocaResponseType richiestaDiRevocaResponse;
    @XmlElement(name = "ElencoRichiesteDiRevoca", required = true)
    protected CorpoRichiesteDiRevocaType.ElencoRichiesteDiRevoca elencoRichiesteDiRevoca;

    /**
     * Gets the value of the richiestaDiRevocaResponse property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaDiRevocaResponseType }
     *     
     */
    public RichiestaDiRevocaResponseType getRichiestaDiRevocaResponse() {
        return richiestaDiRevocaResponse;
    }

    /**
     * Sets the value of the richiestaDiRevocaResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaDiRevocaResponseType }
     *     
     */
    public void setRichiestaDiRevocaResponse(RichiestaDiRevocaResponseType value) {
        this.richiestaDiRevocaResponse = value;
    }

    /**
     * Gets the value of the elencoRichiesteDiRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link CorpoRichiesteDiRevocaType.ElencoRichiesteDiRevoca }
     *     
     */
    public CorpoRichiesteDiRevocaType.ElencoRichiesteDiRevoca getElencoRichiesteDiRevoca() {
        return elencoRichiesteDiRevoca;
    }

    /**
     * Sets the value of the elencoRichiesteDiRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorpoRichiesteDiRevocaType.ElencoRichiesteDiRevoca }
     *     
     */
    public void setElencoRichiesteDiRevoca(CorpoRichiesteDiRevocaType.ElencoRichiesteDiRevoca value) {
        this.elencoRichiesteDiRevoca = value;
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
     *         &lt;element name="RichiestaDiRevoca" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}RichiestaDiRevocaType" maxOccurs="1000"/>
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
        "richiestaDiRevoca"
    })
    public static class ElencoRichiesteDiRevoca {

        @XmlElement(name = "RichiestaDiRevoca", required = true)
        protected List<RichiestaDiRevocaType> richiestaDiRevoca;

        /**
         * Gets the value of the richiestaDiRevoca property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the richiestaDiRevoca property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRichiestaDiRevoca().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RichiestaDiRevocaType }
         * 
         * 
         */
        public List<RichiestaDiRevocaType> getRichiestaDiRevoca() {
            if (richiestaDiRevoca == null) {
                richiestaDiRevoca = new ArrayList<RichiestaDiRevocaType>();
            }
            return this.richiestaDiRevoca;
        }

    }

}
