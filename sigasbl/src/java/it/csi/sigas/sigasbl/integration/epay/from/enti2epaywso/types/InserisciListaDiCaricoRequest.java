
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
 *         &lt;element name="Testata" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}TestataListaCarico"/>
 *         &lt;element name="ListaDiCarico" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}ListaDiCarico"/>
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
    "listaDiCarico"
})
@XmlRootElement(name = "InserisciListaDiCaricoRequest")
public class InserisciListaDiCaricoRequest {

    @XmlElement(name = "Testata", required = true)
    protected TestataListaCarico testata;
    @XmlElement(name = "ListaDiCarico", required = true)
    protected ListaDiCarico listaDiCarico;

    /**
     * Gets the value of the testata property.
     * 
     * @return
     *     possible object is
     *     {@link TestataListaCarico }
     *     
     */
    public TestataListaCarico getTestata() {
        return testata;
    }

    /**
     * Sets the value of the testata property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestataListaCarico }
     *     
     */
    public void setTestata(TestataListaCarico value) {
        this.testata = value;
    }

    /**
     * Gets the value of the listaDiCarico property.
     * 
     * @return
     *     possible object is
     *     {@link ListaDiCarico }
     *     
     */
    public ListaDiCarico getListaDiCarico() {
        return listaDiCarico;
    }

    /**
     * Sets the value of the listaDiCarico property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListaDiCarico }
     *     
     */
    public void setListaDiCarico(ListaDiCarico value) {
        this.listaDiCarico = value;
    }

}
