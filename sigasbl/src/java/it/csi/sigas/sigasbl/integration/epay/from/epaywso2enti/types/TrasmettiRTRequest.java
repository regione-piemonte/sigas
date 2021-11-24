
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
 *         &lt;element name="Testata" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TestataRTType"/>
 *         &lt;element name="CorpoRT" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}CorpoRTType"/>
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
    "corpoRT"
})
@XmlRootElement(name = "TrasmettiRTRequest")
public class TrasmettiRTRequest {

    @XmlElement(name = "Testata", required = true)
    protected TestataRTType testata;
    @XmlElement(name = "CorpoRT", required = true)
    protected CorpoRTType corpoRT;

    /**
     * Gets the value of the testata property.
     * 
     * @return
     *     possible object is
     *     {@link TestataRTType }
     *     
     */
    public TestataRTType getTestata() {
        return testata;
    }

    /**
     * Sets the value of the testata property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestataRTType }
     *     
     */
    public void setTestata(TestataRTType value) {
        this.testata = value;
    }

    /**
     * Gets the value of the corpoRT property.
     * 
     * @return
     *     possible object is
     *     {@link CorpoRTType }
     *     
     */
    public CorpoRTType getCorpoRT() {
        return corpoRT;
    }

    /**
     * Sets the value of the corpoRT property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorpoRTType }
     *     
     */
    public void setCorpoRT(CorpoRTType value) {
        this.corpoRT = value;
    }

}
