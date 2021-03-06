/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.backoffice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.CodiceFiscaleType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.ObjectIdType;


/**
 * <p>Classe Java per UtentePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="UtentePropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{backoffice.acaris.acta.doqui.it}BackOfficePropertiesType">
 *       &lt;sequence>
 *         &lt;element name="parentId" type="{common.acaris.acta.doqui.it}ObjectIdType"/>
 *         &lt;element name="parentIdInChiaro" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="nome" type="{backoffice.acaris.acta.doqui.it}NomeType"/>
 *         &lt;element name="cognome" type="{backoffice.acaris.acta.doqui.it}CognomeType"/>
 *         &lt;element name="codiceFiscale" type="{common.acaris.acta.doqui.it}CodiceFiscaleType"/>
 *         &lt;element name="email" type="{backoffice.acaris.acta.doqui.it}EmailType"/>
 *         &lt;element name="matricola" type="{backoffice.acaris.acta.doqui.it}MatricolaType"/>
 *         &lt;element name="note" type="{backoffice.acaris.acta.doqui.it}NoteType"/>
 *         &lt;element name="codiceIdentitaList" type="{backoffice.acaris.acta.doqui.it}CodiceIdentitaType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "UtentePropertiesType", propOrder = {
    "parentId",
    "parentIdInChiaro",
    "nome",
    "cognome",
    "codiceFiscale",
    "email",
    "matricola",
    "note",
    "codiceIdentitaList",
    "dataInizioValidita",
    "dataFineValidita",
    "valido"
})
public class UtentePropertiesType
    extends BackOfficePropertiesType
{

    @XmlElement(required = true)
    protected ObjectIdType parentId;
    @XmlElement(required = true)
    protected String parentIdInChiaro;
    @XmlElement(required = true)
    protected String nome;
    @XmlElement(required = true)
    protected String cognome;
    @XmlElement(required = true)
    protected CodiceFiscaleType codiceFiscale;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String matricola;
    @XmlElement(required = true)
    protected String note;
    protected List<String> codiceIdentitaList;
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
     * Recupera il valore della propriet� nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della propriet� nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della propriet� cognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della propriet� cognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della propriet� codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link CodiceFiscaleType }
     *     
     */
    public CodiceFiscaleType getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della propriet� codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceFiscaleType }
     *     
     */
    public void setCodiceFiscale(CodiceFiscaleType value) {
        this.codiceFiscale = value;
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
     * Recupera il valore della propriet� matricola.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatricola() {
        return matricola;
    }

    /**
     * Imposta il valore della propriet� matricola.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatricola(String value) {
        this.matricola = value;
    }

    /**
     * Recupera il valore della propriet� note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della propriet� note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the codiceIdentitaList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the codiceIdentitaList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodiceIdentitaList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCodiceIdentitaList() {
        if (codiceIdentitaList == null) {
            codiceIdentitaList = new ArrayList<String>();
        }
        return this.codiceIdentitaList;
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
