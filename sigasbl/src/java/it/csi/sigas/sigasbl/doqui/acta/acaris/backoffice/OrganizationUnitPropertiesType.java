/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.ObjectIdType;


/**
 * <p>Classe Java per OrganizationUnitPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="OrganizationUnitPropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{backoffice.acaris.acta.doqui.it}BackOfficePropertiesType">
 *       &lt;sequence>
 *         &lt;element name="parentId" type="{common.acaris.acta.doqui.it}ObjectIdType"/>
 *         &lt;element name="parentIdInChiaro" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="codice" type="{backoffice.acaris.acta.doqui.it}CodiceType"/>
 *         &lt;element name="denominazione" type="{backoffice.acaris.acta.doqui.it}DenominazioneType"/>
 *         &lt;element name="indirizzo" type="{backoffice.acaris.acta.doqui.it}IndirizzoType"/>
 *         &lt;element name="citta" type="{backoffice.acaris.acta.doqui.it}CittaType"/>
 *         &lt;element name="email" type="{backoffice.acaris.acta.doqui.it}EmailType"/>
 *         &lt;element name="emailPEC" type="{backoffice.acaris.acta.doqui.it}EmailType"/>
 *         &lt;element name="sitoWeb" type="{backoffice.acaris.acta.doqui.it}SitoWebType"/>
 *         &lt;element name="telefono" type="{backoffice.acaris.acta.doqui.it}TelefonoType"/>
 *         &lt;element name="fax" type="{backoffice.acaris.acta.doqui.it}FaxType"/>
 *         &lt;element name="dataInizioValidita" type="{backoffice.acaris.acta.doqui.it}DataValiditaType"/>
 *         &lt;element name="dataFineValidita" type="{backoffice.acaris.acta.doqui.it}DataValiditaType"/>
 *         &lt;element name="valido" type="{backoffice.acaris.acta.doqui.it}ValidoType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganizationUnitPropertiesType", propOrder = {
    "parentId",
    "parentIdInChiaro",
    "codice",
    "denominazione",
    "indirizzo",
    "citta",
    "email",
    "emailPEC",
    "sitoWeb",
    "telefono",
    "fax",
    "dataInizioValidita",
    "dataFineValidita",
    "valido"
})
@XmlSeeAlso({
    StrutturaPropertiesType.class,
    EntePropertiesType.class,
    AOOPropertiesType.class,
    NodoPropertiesType.class
})
public abstract class OrganizationUnitPropertiesType
    extends BackOfficePropertiesType
{

    @XmlElement(required = true)
    protected ObjectIdType parentId;
    @XmlElement(required = true)
    protected String parentIdInChiaro;
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
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineValidita;
    protected boolean valido;

    /**
     * Recupera il valore della propriet� parentId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getParentId() {
        return parentId;
    }

    /**
     * Imposta il valore della propriet� parentId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setParentId(ObjectIdType value) {
        this.parentId = value;
    }

    /**
     * Recupera il valore della propriet� parentIdInChiaro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentIdInChiaro() {
        return parentIdInChiaro;
    }

    /**
     * Imposta il valore della propriet� parentIdInChiaro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentIdInChiaro(String value) {
        this.parentIdInChiaro = value;
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

    /**
     * Recupera il valore della propriet� dataInizioValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Imposta il valore della propriet� dataInizioValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioValidita(XMLGregorianCalendar value) {
        this.dataInizioValidita = value;
    }

    /**
     * Recupera il valore della propriet� dataFineValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Imposta il valore della propriet� dataFineValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineValidita(XMLGregorianCalendar value) {
        this.dataFineValidita = value;
    }

    /**
     * Recupera il valore della propriet� valido.
     * 
     */
    public boolean isValido() {
        return valido;
    }

    /**
     * Imposta il valore della propriet� valido.
     * 
     */
    public void setValido(boolean value) {
        this.valido = value;
    }

}
