
package it.csi.sigas.sigasbl.integration.epay.from.enti2epaywso.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestataListaCarico complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TestataListaCarico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdMessaggio" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="CFEnteCreditore" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="CodiceVersamento" type="{http://www.csi.it/epay/epaywso/types}CodiceVersamentoType"/>
 *         &lt;element name="NumeroPosizioniDebitorie" type="{http://www.csi.it/epay/epaywso/types}Numero6CifreType"/>
 *         &lt;element name="ImportoTotaleListaDiCarico" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestataListaCarico", propOrder = {
    "idMessaggio",
    "cfEnteCreditore",
    "codiceVersamento",
    "numeroPosizioniDebitorie",
    "importoTotaleListaDiCarico"
})
public class TestataListaCarico {

    @XmlElement(name = "IdMessaggio", required = true)
    protected String idMessaggio;
    @XmlElement(name = "CFEnteCreditore", required = true)
    protected String cfEnteCreditore;
    @XmlElement(name = "CodiceVersamento", required = true)
    protected String codiceVersamento;
    @XmlElement(name = "NumeroPosizioniDebitorie")
    protected int numeroPosizioniDebitorie;
    @XmlElement(name = "ImportoTotaleListaDiCarico", required = true)
    protected BigDecimal importoTotaleListaDiCarico;

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
     * Gets the value of the numeroPosizioniDebitorie property.
     * 
     */
    public int getNumeroPosizioniDebitorie() {
        return numeroPosizioniDebitorie;
    }

    /**
     * Sets the value of the numeroPosizioniDebitorie property.
     * 
     */
    public void setNumeroPosizioniDebitorie(int value) {
        this.numeroPosizioniDebitorie = value;
    }

    /**
     * Gets the value of the importoTotaleListaDiCarico property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotaleListaDiCarico() {
        return importoTotaleListaDiCarico;
    }

    /**
     * Sets the value of the importoTotaleListaDiCarico property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotaleListaDiCarico(BigDecimal value) {
        this.importoTotaleListaDiCarico = value;
    }

}
