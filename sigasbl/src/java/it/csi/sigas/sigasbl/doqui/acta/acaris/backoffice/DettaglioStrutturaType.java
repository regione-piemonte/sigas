/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.IdStrutturaType;


/**
 * <p>Classe Java per DettaglioStrutturaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioStrutturaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idStruttura" type="{common.acaris.acta.doqui.it}IdStrutturaType"/>
 *         &lt;element name="codice" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="denominazione" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="indirizzo" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="citta" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="email" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="emailPEC" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="sitoWeb" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="telefono" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="fax" type="{common.acaris.acta.doqui.it}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioStrutturaType", propOrder = {
    "idStruttura",
    "codice",
    "denominazione",
    "indirizzo",
    "citta",
    "email",
    "emailPEC",
    "sitoWeb",
    "telefono",
    "fax"
})
public class DettaglioStrutturaType {

    @XmlElement(required = true)
    protected IdStrutturaType idStruttura;
    @XmlElement(required = true)
    protected String codice;
    @XmlElement(required = true)
    protected String denominazione;
    @XmlElement(required = true)
    protected String indirizzo;
    @XmlElement(required = true)
    protected String citta;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String emailPEC;
    @XmlElement(required = true)
    protected String sitoWeb;
    @XmlElement(required = true)
    protected String telefono;
    @XmlElement(required = true)
    protected String fax;

    /**
     * Recupera il valore della propriet� idStruttura.
     * 
     * @return
     *     possible object is
     *     {@link IdStrutturaType }
     *     
     */
    public IdStrutturaType getIdStruttura() {
        return idStruttura;
    }

    /**
     * Imposta il valore della propriet� idStruttura.
     * 
     * @param value
     *     allowed object is
     *     {@link IdStrutturaType }
     *     
     */
    public void setIdStruttura(IdStrutturaType value) {
        this.idStruttura = value;
    }

    /**
     * Recupera il valore della propriet� codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della propriet� codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della propriet� denominazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Imposta il valore della propriet� denominazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Recupera il valore della propriet� indirizzo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta il valore della propriet� indirizzo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Recupera il valore della propriet� citta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitta() {
        return citta;
    }

    /**
     * Imposta il valore della propriet� citta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitta(String value) {
        this.citta = value;
    }

    /**
     * Recupera il valore della propriet� email.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta il valore della propriet� email.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Recupera il valore della propriet� emailPEC.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailPEC() {
        return emailPEC;
    }

    /**
     * Imposta il valore della propriet� emailPEC.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailPEC(String value) {
        this.emailPEC = value;
    }

    /**
     * Recupera il valore della propriet� sitoWeb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitoWeb() {
        return sitoWeb;
    }

    /**
     * Imposta il valore della propriet� sitoWeb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitoWeb(String value) {
        this.sitoWeb = value;
    }

    /**
     * Recupera il valore della propriet� telefono.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il valore della propriet� telefono.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Recupera il valore della propriet� fax.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Imposta il valore della propriet� fax.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

}
