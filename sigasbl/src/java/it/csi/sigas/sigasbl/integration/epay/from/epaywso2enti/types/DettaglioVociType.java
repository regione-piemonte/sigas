
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DettaglioVociType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DettaglioVociType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DettaglioVoce">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Tipo" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TipoDettaglioVoce"/>
 *                   &lt;element name="Descrizione" type="{http://www.csi.it/epay/epaywso/types}String100Type" minOccurs="0"/>
 *                   &lt;element name="Importo" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioVociType", propOrder = {
    "dettaglioVoce"
})
public class DettaglioVociType {

    @XmlElement(name = "DettaglioVoce", required = true)
    protected DettaglioVociType.DettaglioVoce dettaglioVoce;

    /**
     * Gets the value of the dettaglioVoce property.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioVociType.DettaglioVoce }
     *     
     */
    public DettaglioVociType.DettaglioVoce getDettaglioVoce() {
        return dettaglioVoce;
    }

    /**
     * Sets the value of the dettaglioVoce property.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioVociType.DettaglioVoce }
     *     
     */
    public void setDettaglioVoce(DettaglioVociType.DettaglioVoce value) {
        this.dettaglioVoce = value;
    }


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
     *         &lt;element name="Tipo" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}TipoDettaglioVoce"/>
     *         &lt;element name="Descrizione" type="{http://www.csi.it/epay/epaywso/types}String100Type" minOccurs="0"/>
     *         &lt;element name="Importo" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/>
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
        "tipo",
        "descrizione",
        "importo"
    })
    public static class DettaglioVoce {

        @XmlElement(name = "Tipo", required = true)
        protected TipoDettaglioVoce tipo;
        @XmlElement(name = "Descrizione")
        protected String descrizione;
        @XmlElement(name = "Importo", required = true)
        protected BigDecimal importo;

        /**
         * Gets the value of the tipo property.
         * 
         * @return
         *     possible object is
         *     {@link TipoDettaglioVoce }
         *     
         */
        public TipoDettaglioVoce getTipo() {
            return tipo;
        }

        /**
         * Sets the value of the tipo property.
         * 
         * @param value
         *     allowed object is
         *     {@link TipoDettaglioVoce }
         *     
         */
        public void setTipo(TipoDettaglioVoce value) {
            this.tipo = value;
        }

        /**
         * Gets the value of the descrizione property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescrizione() {
            return descrizione;
        }

        /**
         * Sets the value of the descrizione property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescrizione(String value) {
            this.descrizione = value;
        }

        /**
         * Gets the value of the importo property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getImporto() {
            return importo;
        }

        /**
         * Sets the value of the importo property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setImporto(BigDecimal value) {
            this.importo = value;
        }

    }

}
