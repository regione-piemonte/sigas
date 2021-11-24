
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
 *         &lt;element name="Testata" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TestataNotifichePagamentoType"/>
 *         &lt;element name="CorpoNotifichePagamento" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}CorpoNotifichePagamentoType"/>
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
    "corpoNotifichePagamento"
})
@XmlRootElement(name = "TrasmettiNotifichePagamentoRequest")
public class TrasmettiNotifichePagamentoRequest {

    @XmlElement(name = "Testata", required = true)
    protected TestataNotifichePagamentoType testata;
    @XmlElement(name = "CorpoNotifichePagamento", required = true)
    protected CorpoNotifichePagamentoType corpoNotifichePagamento;

    /**
     * Gets the value of the testata property.
     * 
     * @return
     *     possible object is
     *     {@link TestataNotifichePagamentoType }
     *     
     */
    public TestataNotifichePagamentoType getTestata() {
        return testata;
    }

    /**
     * Sets the value of the testata property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestataNotifichePagamentoType }
     *     
     */
    public void setTestata(TestataNotifichePagamentoType value) {
        this.testata = value;
    }

    /**
     * Gets the value of the corpoNotifichePagamento property.
     * 
     * @return
     *     possible object is
     *     {@link CorpoNotifichePagamentoType }
     *     
     */
    public CorpoNotifichePagamentoType getCorpoNotifichePagamento() {
        return corpoNotifichePagamento;
    }

    /**
     * Sets the value of the corpoNotifichePagamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorpoNotifichePagamentoType }
     *     
     */
    public void setCorpoNotifichePagamento(CorpoNotifichePagamentoType value) {
        this.corpoNotifichePagamento = value;
    }

}
