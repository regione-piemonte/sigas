
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiEsitoSingolaRevocaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiEsitoSingolaRevocaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SingoloImportoRevocato" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/>
 *         &lt;element name="IUR" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="CausaleEsito" type="{http://www.csi.it/epay/epaywso/types}String140Type"/>
 *         &lt;element name="DatiAggiuntiviEsito" type="{http://www.csi.it/epay/epaywso/types}String140Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiEsitoSingolaRevocaType", propOrder = {
    "singoloImportoRevocato",
    "iur",
    "causaleEsito",
    "datiAggiuntiviEsito"
})
public class DatiEsitoSingolaRevocaType {

    @XmlElement(name = "SingoloImportoRevocato", required = true)
    protected BigDecimal singoloImportoRevocato;
    @XmlElement(name = "IUR", required = true)
    protected String iur;
    @XmlElement(name = "CausaleEsito", required = true)
    protected String causaleEsito;
    @XmlElement(name = "DatiAggiuntiviEsito", required = true)
    protected String datiAggiuntiviEsito;

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
     * Gets the value of the causaleEsito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausaleEsito() {
        return causaleEsito;
    }

    /**
     * Sets the value of the causaleEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausaleEsito(String value) {
        this.causaleEsito = value;
    }

    /**
     * Gets the value of the datiAggiuntiviEsito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiAggiuntiviEsito() {
        return datiAggiuntiviEsito;
    }

    /**
     * Sets the value of the datiAggiuntiviEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiAggiuntiviEsito(String value) {
        this.datiAggiuntiviEsito = value;
    }

}
