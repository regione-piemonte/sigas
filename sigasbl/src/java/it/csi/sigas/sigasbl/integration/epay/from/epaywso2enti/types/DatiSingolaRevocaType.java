
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiSingolaRevocaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiSingolaRevocaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SingoloImportoRevocato" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/>
 *         &lt;element name="IUR" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="Causale" type="{http://www.csi.it/epay/epaywso/types}String140Type"/>
 *         &lt;element name="DatiAggiuntivi" type="{http://www.csi.it/epay/epaywso/types}String140Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiSingolaRevocaType", propOrder = {
    "singoloImportoRevocato",
    "iur",
    "causale",
    "datiAggiuntivi"
})
public class DatiSingolaRevocaType {

    @XmlElement(name = "SingoloImportoRevocato", required = true)
    protected BigDecimal singoloImportoRevocato;
    @XmlElement(name = "IUR", required = true)
    protected String iur;
    @XmlElement(name = "Causale", required = true)
    protected String causale;
    @XmlElement(name = "DatiAggiuntivi", required = true)
    protected String datiAggiuntivi;

    /**
     * Gets the value of the singoloImportoRevocato property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSingoloImportoRevocato() {
        return singoloImportoRevocato;
    }

    /**
     * Sets the value of the singoloImportoRevocato property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSingoloImportoRevocato(BigDecimal value) {
        this.singoloImportoRevocato = value;
    }

    /**
     * Gets the value of the iur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUR() {
        return iur;
    }

    /**
     * Sets the value of the iur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUR(String value) {
        this.iur = value;
    }

    /**
     * Gets the value of the causale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausale() {
        return causale;
    }

    /**
     * Sets the value of the causale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausale(String value) {
        this.causale = value;
    }

    /**
     * Gets the value of the datiAggiuntivi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiAggiuntivi() {
        return datiAggiuntivi;
    }

    /**
     * Sets the value of the datiAggiuntivi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiAggiuntivi(String value) {
        this.datiAggiuntivi = value;
    }

}
