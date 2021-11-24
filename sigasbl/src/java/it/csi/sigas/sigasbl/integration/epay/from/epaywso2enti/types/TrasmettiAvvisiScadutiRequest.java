
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
 *         &lt;element name="Testata" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TestataAvvisiScadutiType"/>
 *         &lt;element name="CorpoAvvisiScaduti" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}CorpoAvvisiScadutiType"/>
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
    "corpoAvvisiScaduti"
})
@XmlRootElement(name = "TrasmettiAvvisiScadutiRequest")
public class TrasmettiAvvisiScadutiRequest {

    @XmlElement(name = "Testata", required = true)
    protected TestataAvvisiScadutiType testata;
    @XmlElement(name = "CorpoAvvisiScaduti", required = true)
    protected CorpoAvvisiScadutiType corpoAvvisiScaduti;

    /**
     * Gets the value of the testata property.
     * 
     * @return
     *     possible object is
     *     {@link TestataAvvisiScadutiType }
     *     
     */
    public TestataAvvisiScadutiType getTestata() {
        return testata;
    }

    /**
     * Sets the value of the testata property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestataAvvisiScadutiType }
     *     
     */
    public void setTestata(TestataAvvisiScadutiType value) {
        this.testata = value;
    }

    /**
     * Gets the value of the corpoAvvisiScaduti property.
     * 
     * @return
     *     possible object is
     *     {@link CorpoAvvisiScadutiType }
     *     
     */
    public CorpoAvvisiScadutiType getCorpoAvvisiScaduti() {
        return corpoAvvisiScaduti;
    }

    /**
     * Sets the value of the corpoAvvisiScaduti property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorpoAvvisiScadutiType }
     *     
     */
    public void setCorpoAvvisiScaduti(CorpoAvvisiScadutiType value) {
        this.corpoAvvisiScaduti = value;
    }

}
