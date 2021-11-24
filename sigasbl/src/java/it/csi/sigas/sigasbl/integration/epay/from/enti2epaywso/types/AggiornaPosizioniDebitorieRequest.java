
package it.csi.sigas.sigasbl.integration.epay.from.enti2epaywso.types;

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
 *         &lt;element name="Testata" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}TestataAggiornaPosizioniDebitorie"/>
 *         &lt;element name="ElencoPosizioniDaAggiornare" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}ElencoPosizioniDaAggiornareType"/>
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
    "elencoPosizioniDaAggiornare"
})
@XmlRootElement(name = "AggiornaPosizioniDebitorieRequest")
public class AggiornaPosizioniDebitorieRequest {

    @XmlElement(name = "Testata", required = true)
    protected TestataAggiornaPosizioniDebitorie testata;
    @XmlElement(name = "ElencoPosizioniDaAggiornare", required = true)
    protected ElencoPosizioniDaAggiornareType elencoPosizioniDaAggiornare;

    /**
     * Gets the value of the testata property.
     * 
     * @return
     *     possible object is
     *     {@link TestataAggiornaPosizioniDebitorie }
     *     
     */
    public TestataAggiornaPosizioniDebitorie getTestata() {
        return testata;
    }

    /**
     * Sets the value of the testata property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestataAggiornaPosizioniDebitorie }
     *     
     */
    public void setTestata(TestataAggiornaPosizioniDebitorie value) {
        this.testata = value;
    }

    /**
     * Gets the value of the elencoPosizioniDaAggiornare property.
     * 
     * @return
     *     possible object is
     *     {@link ElencoPosizioniDaAggiornareType }
     *     
     */
    public ElencoPosizioniDaAggiornareType getElencoPosizioniDaAggiornare() {
        return elencoPosizioniDaAggiornare;
    }

    /**
     * Sets the value of the elencoPosizioniDaAggiornare property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoPosizioniDaAggiornareType }
     *     
     */
    public void setElencoPosizioniDaAggiornare(ElencoPosizioniDaAggiornareType value) {
        this.elencoPosizioniDaAggiornare = value;
    }

}
