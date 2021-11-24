
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.sigas.sigasbl.integration.epay.from.types.EsitoAggiornamentoType;
import it.csi.sigas.sigasbl.integration.epay.from.types.ResponseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.csi.it/epay/epaywso/types}ResponseType">
 *       &lt;sequence>
 *         &lt;element name="TestataEsito" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TestataEsitoType"/>
 *         &lt;element name="EsitoAggiornamento" type="{http://www.csi.it/epay/epaywso/types}EsitoAggiornamentoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "testataEsito",
    "esitoAggiornamento"
})
@XmlRootElement(name = "EsitoAggiornaPosizioniDebitorieRequest")
public class EsitoAggiornaPosizioniDebitorieRequest
    extends ResponseType
{

    @XmlElement(name = "TestataEsito", required = true)
    protected TestataEsitoType testataEsito;
    @XmlElement(name = "EsitoAggiornamento")
    protected EsitoAggiornamentoType esitoAggiornamento;

    /**
     * Gets the value of the testataEsito property.
     * 
     * @return
     *     possible object is
     *     {@link TestataEsitoType }
     *     
     */
    public TestataEsitoType getTestataEsito() {
        return testataEsito;
    }

    /**
     * Sets the value of the testataEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestataEsitoType }
     *     
     */
    public void setTestataEsito(TestataEsitoType value) {
        this.testataEsito = value;
    }

    /**
     * Gets the value of the esitoAggiornamento property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoAggiornamentoType }
     *     
     */
    public EsitoAggiornamentoType getEsitoAggiornamento() {
        return esitoAggiornamento;
    }

    /**
     * Sets the value of the esitoAggiornamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoAggiornamentoType }
     *     
     */
    public void setEsitoAggiornamento(EsitoAggiornamentoType value) {
        this.esitoAggiornamento = value;
    }

}
