
package it.csi.sigas.sigasbl.integration.epay.from.enti2epaywso.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaDiCarico complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaDiCarico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PosizioniDaInserire">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PosizioneDaInserire" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}PosizioneDaInserireType" maxOccurs="1000"/>
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
@XmlType(name = "ListaDiCarico", propOrder = {
    "posizioniDaInserire"
})
public class ListaDiCarico {

    @XmlElement(name = "PosizioniDaInserire", required = true)
    protected ListaDiCarico.PosizioniDaInserire posizioniDaInserire;

    /**
     * Gets the value of the posizioniDaInserire property.
     * 
     * @return
     *     possible object is
     *     {@link ListaDiCarico.PosizioniDaInserire }
     *     
     */
    public ListaDiCarico.PosizioniDaInserire getPosizioniDaInserire() {
        return posizioniDaInserire;
    }

    /**
     * Sets the value of the posizioniDaInserire property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaDiCarico.PosizioniDaInserire }
     *     
     */
    public void setPosizioniDaInserire(ListaDiCarico.PosizioniDaInserire value) {
        this.posizioniDaInserire = value;
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
     *         &lt;element name="PosizioneDaInserire" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}PosizioneDaInserireType" maxOccurs="1000"/>
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
        "posizioneDaInserire"
    })
    public static class PosizioniDaInserire {

        @XmlElement(name = "PosizioneDaInserire", required = true)
        protected List<PosizioneDaInserireType> posizioneDaInserire;

        /**
         * Gets the value of the posizioneDaInserire property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the posizioneDaInserire property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPosizioneDaInserire().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PosizioneDaInserireType }
         * 
         * 
         */
        public List<PosizioneDaInserireType> getPosizioneDaInserire() {
            if (posizioneDaInserire == null) {
                posizioneDaInserire = new ArrayList<PosizioneDaInserireType>();
            }
            return this.posizioneDaInserire;
        }

    }

}
