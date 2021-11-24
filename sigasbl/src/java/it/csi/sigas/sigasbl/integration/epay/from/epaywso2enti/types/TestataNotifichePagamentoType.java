
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestataNotifichePagamentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TestataNotifichePagamentoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdMessaggio" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="CFEnteCreditore" type="{http://www.csi.it/epay/epaywso/types}String35Type"/>
 *         &lt;element name="CodiceVersamento" type="{http://www.csi.it/epay/epaywso/types}CodiceVersamentoType"/>
 *         &lt;element name="PagamentiSpontanei" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="NumeroPagamenti" type="{http://www.csi.it/epay/epaywso/types}Numero6CifreType"/>
 *         &lt;element name="ImportoTotalePagamenti" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TestataNotifichePagamentoType", propOrder = {
    "idMessaggio",
    "cfEnteCreditore",
    "codiceVersamento",
    "pagamentiSpontanei",
    "numeroPagamenti",
    "importoTotalePagamenti"
})
public class TestataNotifichePagamentoType {

    @XmlElement(name = "IdMessaggio", required = true)
    protected String idMessaggio;
    @XmlElement(name = "CFEnteCreditore", required = true)
    protected String cfEnteCreditore;
    @XmlElement(name = "CodiceVersamento", required = true)
    protected String codiceVersamento;
    @XmlElement(name = "PagamentiSpontanei")
    protected boolean pagamentiSpontanei;
    @XmlElement(name = "NumeroPagamenti")
    protected int numeroPagamenti;
    @XmlElement(name = "ImportoTotalePagamenti", required = true)
    protected BigDecimal importoTotalePagamenti;

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
     * Gets the value of the pagamentiSpontanei property.
     * 
     */
    public boolean isPagamentiSpontanei() {
        return pagamentiSpontanei;
    }

    /**
     * Sets the value of the pagamentiSpontanei property.
     * 
     */
    public void setPagamentiSpontanei(boolean value) {
        this.pagamentiSpontanei = value;
    }

    /**
     * Gets the value of the numeroPagamenti property.
     * 
     */
    public int getNumeroPagamenti() {
        return numeroPagamenti;
    }

    /**
     * Sets the value of the numeroPagamenti property.
     * 
     */
    public void setNumeroPagamenti(int value) {
        this.numeroPagamenti = value;
    }

    /**
     * Gets the value of the importoTotalePagamenti property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotalePagamenti() {
        return importoTotalePagamenti;
    }

    /**
     * Sets the value of the importoTotalePagamenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotalePagamenti(BigDecimal value) {
        this.importoTotalePagamenti = value;
    }

}
