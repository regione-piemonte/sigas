
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="Testata" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TestataRichiesteDiRevocaType"/>
 *         &lt;element name="CorpoRichiesteDiRevoca" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}CorpoRichiesteDiRevocaType"/>
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
    "testata",
    "corpoRichiesteDiRevoca"
})
@XmlRootElement(name = "TrasmettiRichiesteDiRevocaRequest")
public class TrasmettiRichiesteDiRevocaRequest {

    @XmlElement(name = "Testata", required = true)
    protected TestataRichiesteDiRevocaType testata;
    @XmlElement(name = "CorpoRichiesteDiRevoca", required = true)
    protected CorpoRichiesteDiRevocaType corpoRichiesteDiRevoca;

    /**
     * Gets the value of the testata property.
     * 
     * @return
     *     possible object is
     *     {@link TestataRichiesteDiRevocaType }
     *     
     */
    public TestataRichiesteDiRevocaType getTestata() {
        return testata;
    }

    /**
     * Sets the value of the testata property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestataRichiesteDiRevocaType }
     *     
     */
    public void setTestata(TestataRichiesteDiRevocaType value) {
        this.testata = value;
    }

    /**
     * Gets the value of the corpoRichiesteDiRevoca property.
     * 
     * @return
     *     possible object is
     *     {@link CorpoRichiesteDiRevocaType }
     *     
     */
    public CorpoRichiesteDiRevocaType getCorpoRichiesteDiRevoca() {
        return corpoRichiesteDiRevoca;
    }

    /**
     * Sets the value of the corpoRichiesteDiRevoca property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorpoRichiesteDiRevocaType }
     *     
     */
    public void setCorpoRichiesteDiRevoca(CorpoRichiesteDiRevocaType value) {
        this.corpoRichiesteDiRevoca = value;
    }

}
