
package it.csi.sigas.sigasbl.integration.epay.from.enti2epaywso.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.sigas.sigasbl.integration.epay.from.clienttypes.EsitoInserimentoType;
import it.csi.sigas.sigasbl.integration.epay.from.clienttypes.ResponseType;


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
 *         &lt;element name="EsitoInserimento" type="{http://www.csi.it/epay/epaywso/types}EsitoInserimentoType" minOccurs="0"/>
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
    "esitoInserimento"
})
@XmlRootElement(name = "InserisciListaDiCaricoResponse")
public class InserisciListaDiCaricoResponse
    extends ResponseType
{

    @XmlElement(name = "EsitoInserimento")
    protected EsitoInserimentoType esitoInserimento;

    /**
     * Gets the value of the esitoInserimento property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoInserimentoType }
     *     
     */
    public EsitoInserimentoType getEsitoInserimento() {
        return esitoInserimento;
    }

    /**
     * Sets the value of the esitoInserimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoInserimentoType }
     *     
     */
    public void setEsitoInserimento(EsitoInserimentoType value) {
        this.esitoInserimento = value;
    }

}
