
package it.csi.sigas.sigasbl.integration.epay.from.enti2epaywso.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ElencoPosizioniDaAggiornareType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElencoPosizioniDaAggiornareType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PosizioniDaAggiornare">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PosizioneDaAggiornare" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}PosizioneDaAggiornareType" maxOccurs="1000"/>
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
@XmlType(name = "ElencoPosizioniDaAggiornareType", propOrder = {
    "posizioniDaAggiornare"
})
public class ElencoPosizioniDaAggiornareType {

    @XmlElement(name = "PosizioniDaAggiornare", required = true)
    protected ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare posizioniDaAggiornare;

    /**
     * Gets the value of the posizioniDaAggiornare property.
     * 
     * @return
     *     possible object is
     *     {@link ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare }
     *     
     */
    public ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare getPosizioniDaAggiornare() {
        return posizioniDaAggiornare;
    }

    /**
     * Sets the value of the posizioniDaAggiornare property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare }
     *     
     */
    public void setPosizioniDaAggiornare(ElencoPosizioniDaAggiornareType.PosizioniDaAggiornare value) {
        this.posizioniDaAggiornare = value;
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
     *         &lt;element name="PosizioneDaAggiornare" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}PosizioneDaAggiornareType" maxOccurs="1000"/>
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
        "posizioneDaAggiornare"
    })
    public static class PosizioniDaAggiornare {

        @XmlElement(name = "PosizioneDaAggiornare", required = true)
        protected List<PosizioneDaAggiornareType> posizioneDaAggiornare;

        /**
         * Gets the value of the posizioneDaAggiornare property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the posizioneDaAggiornare property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPosizioneDaAggiornare().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PosizioneDaAggiornareType }
         * 
         * 
         */
        public List<PosizioneDaAggiornareType> getPosizioneDaAggiornare() {
            if (posizioneDaAggiornare == null) {
                posizioneDaAggiornare = new ArrayList<PosizioneDaAggiornareType>();
            }
            return this.posizioneDaAggiornare;
        }

    }

}
