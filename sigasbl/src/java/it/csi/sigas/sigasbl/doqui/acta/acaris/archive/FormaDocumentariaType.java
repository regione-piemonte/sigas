/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.IdVitalRecordCodeType;


/**
 * <p>Classe Java per FormaDocumentariaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FormaDocumentariaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idFormaDocumentaria" type="{archive.acaris.acta.doqui.it}IdFormaDocumentariaType"/>
 *         &lt;element name="descrizione" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="firmato" type="{common.acaris.acta.doqui.it}boolean"/>
 *         &lt;element name="dataFineValidita" type="{common.acaris.acta.doqui.it}date"/>
 *         &lt;element name="daConservareSostitutiva" type="{common.acaris.acta.doqui.it}boolean"/>
 *         &lt;element name="originale" type="{common.acaris.acta.doqui.it}boolean"/>
 *         &lt;element name="unico" type="{common.acaris.acta.doqui.it}boolean"/>
 *         &lt;element name="idVitalRecordCode" type="{common.acaris.acta.doqui.it}IdVitalRecordCodeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FormaDocumentariaType", propOrder = {
    "idFormaDocumentaria",
    "descrizione",
    "firmato",
    "dataFineValidita",
    "daConservareSostitutiva",
    "originale",
    "unico",
    "idVitalRecordCode"
})
public class FormaDocumentariaType {

    @XmlElement(required = true)
    protected IdFormaDocumentariaType idFormaDocumentaria;
    @XmlElement(required = true)
    protected String descrizione;
    protected boolean firmato;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineValidita;
    protected boolean daConservareSostitutiva;
    protected boolean originale;
    protected boolean unico;
    @XmlElement(required = true)
    protected IdVitalRecordCodeType idVitalRecordCode;

    /**
     * Recupera il valore della propriet� idFormaDocumentaria.
     * 
     * @return
     *     possible object is
     *     {@link IdFormaDocumentariaType }
     *     
     */
    public IdFormaDocumentariaType getIdFormaDocumentaria() {
        return idFormaDocumentaria;
    }

    /**
     * Imposta il valore della propriet� idFormaDocumentaria.
     * 
     * @param value
     *     allowed object is
     *     {@link IdFormaDocumentariaType }
     *     
     */
    public void setIdFormaDocumentaria(IdFormaDocumentariaType value) {
        this.idFormaDocumentaria = value;
    }

    /**
     * Recupera il valore della propriet� descrizione.
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
     * Imposta il valore della propriet� descrizione.
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
     * Recupera il valore della propriet� firmato.
     * 
     */
    public boolean isFirmato() {
        return firmato;
    }

    /**
     * Imposta il valore della propriet� firmato.
     * 
     */
    public void setFirmato(boolean value) {
        this.firmato = value;
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
     * Recupera il valore della propriet� daConservareSostitutiva.
     * 
     */
    public boolean isDaConservareSostitutiva() {
        return daConservareSostitutiva;
    }

    /**
     * Imposta il valore della propriet� daConservareSostitutiva.
     * 
     */
    public void setDaConservareSostitutiva(boolean value) {
        this.daConservareSostitutiva = value;
    }

    /**
     * Recupera il valore della propriet� originale.
     * 
     */
    public boolean isOriginale() {
        return originale;
    }

    /**
     * Imposta il valore della propriet� originale.
     * 
     */
    public void setOriginale(boolean value) {
        this.originale = value;
    }

    /**
     * Recupera il valore della propriet� unico.
     * 
     */
    public boolean isUnico() {
        return unico;
    }

    /**
     * Imposta il valore della propriet� unico.
     * 
     */
    public void setUnico(boolean value) {
        this.unico = value;
    }

    /**
     * Recupera il valore della propriet� idVitalRecordCode.
     * 
     * @return
     *     possible object is
     *     {@link IdVitalRecordCodeType }
     *     
     */
    public IdVitalRecordCodeType getIdVitalRecordCode() {
        return idVitalRecordCode;
    }

    /**
     * Imposta il valore della propriet� idVitalRecordCode.
     * 
     * @param value
     *     allowed object is
     *     {@link IdVitalRecordCodeType }
     *     
     */
    public void setIdVitalRecordCode(IdVitalRecordCodeType value) {
        this.idVitalRecordCode = value;
    }

}
