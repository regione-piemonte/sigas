
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CorpoRTType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CorpoRTType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ElencoRT">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RT" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}RTType" maxOccurs="1000"/>
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
@XmlType(name = "CorpoRTType", propOrder = {
    "elencoRT"
})
public class CorpoRTType {

    @XmlElement(name = "ElencoRT", required = true)
    protected CorpoRTType.ElencoRT elencoRT;

    /**
     * Gets the value of the elencoRT property.
     * 
     * @return
     *     possible object is
     *     {@link CorpoRTType.ElencoRT }
     *     
     */
    public CorpoRTType.ElencoRT getElencoRT() {
        return elencoRT;
    }

    /**
     * Sets the value of the elencoRT property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorpoRTType.ElencoRT }
     *     
     */
    public void setElencoRT(CorpoRTType.ElencoRT value) {
        this.elencoRT = value;
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
     *         &lt;element name="RT" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}RTType" maxOccurs="1000"/>
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
        "rt"
    })
    public static class ElencoRT {

        @XmlElement(name = "RT", required = true)
        protected List<RTType> rt;

        /**
         * Gets the value of the rt property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the rt property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRT().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RTType }
         * 
         * 
         */
        public List<RTType> getRT() {
            if (rt == null) {
                rt = new ArrayList<RTType>();
            }
            return this.rt;
        }

    }

}
