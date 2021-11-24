
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestataAvvisiScadutiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TestataAvvisiScadutiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdMessaggio" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="CFEnteCreditore" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="CodiceVersamento" type="{http://www.csi.it/epay/epaywso/types}CodiceVersamentoType"/>
 *         &lt;element name="NumeroAvvisiScaduti" type="{http://www.csi.it/epay/epaywso/types}Numero6CifreType"/>
 *         &lt;element name="ImportoTotaleAvvisiScaduti" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestataAvvisiScadutiType", propOrder = {
    "idMessaggio",
    "cfEnteCreditore",
    "codiceVersamento",
    "numeroAvvisiScaduti",
    "importoTotaleAvvisiScaduti"
})
public class TestataAvvisiScadutiType {

    @XmlElement(name = "IdMessaggio", required = true)
    protected String idMessaggio;
    @XmlElement(name = "CFEnteCreditore", required = true)
    protected String cfEnteCreditore;
    @XmlElement(name = "CodiceVersamento", required = true)
    protected String codiceVersamento;
    @XmlElement(name = "NumeroAvvisiScaduti")
    protected int numeroAvvisiScaduti;
    @XmlElement(name = "ImportoTotaleAvvisiScaduti", required = true)
    protected BigDecimal importoTotaleAvvisiScaduti;

    /**
     * Gets the value of the idMessaggio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMessaggio() {
        return idMessaggio;
    }

    /**
     * Sets the value of the idMessaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMessaggio(String value) {
        this.idMessaggio = value;
    }

    /**
     * Gets the value of the cfEnteCreditore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFEnteCreditore() {
        return cfEnteCreditore;
    }

    /**
     * Sets the value of the cfEnteCreditore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFEnteCreditore(String value) {
        this.cfEnteCreditore = value;
    }

    /**
     * Gets the value of the codiceVersamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceVersamento() {
        return codiceVersamento;
    }

    /**
     * Sets the value of the codiceVersamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceVersamento(String value) {
        this.codiceVersamento = value;
    }

    /**
     * Gets the value of the numeroAvvisiScaduti property.
     * 
     */
    public int getNumeroAvvisiScaduti() {
        return numeroAvvisiScaduti;
    }

    /**
     * Sets the value of the numeroAvvisiScaduti property.
     * 
     */
    public void setNumeroAvvisiScaduti(int value) {
        this.numeroAvvisiScaduti = value;
    }

    /**
     * Gets the value of the importoTotaleAvvisiScaduti property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotaleAvvisiScaduti() {
        return importoTotaleAvvisiScaduti;
    }

    /**
     * Sets the value of the importoTotaleAvvisiScaduti property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotaleAvvisiScaduti(BigDecimal value) {
        this.importoTotaleAvvisiScaduti = value;
    }

}
