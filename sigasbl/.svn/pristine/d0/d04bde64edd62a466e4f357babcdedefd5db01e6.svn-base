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

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.ObjectIdType;


/**
 * <p>Classe Java per GruppoAllegatiPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="GruppoAllegatiPropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{archive.acaris.acta.doqui.it}FolderPropertiesType">
 *       &lt;sequence>
 *         &lt;element name="numeroAllegati" type="{archive.acaris.acta.doqui.it}NumeroAllegatiType"/>
 *         &lt;element name="dataInizio" type="{archive.acaris.acta.doqui.it}DataInizioType"/>
 *         &lt;element name="dataFine" type="{archive.acaris.acta.doqui.it}DataFineType"/>
 *         &lt;element name="classificazionePrincipale" type="{common.acaris.acta.doqui.it}ObjectIdType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GruppoAllegatiPropertiesType", propOrder = {
    "numeroAllegati",
    "dataInizio",
    "dataFine",
    "classificazionePrincipale"
})
public class GruppoAllegatiPropertiesType
    extends FolderPropertiesType
{

    protected int numeroAllegati;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizio;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFine;
    @XmlElement(required = true)
    protected ObjectIdType classificazionePrincipale;

    /**
     * Recupera il valore della propriet� numeroAllegati.
     * 
     */
    public int getNumeroAllegati() {
        return numeroAllegati;
    }

    /**
     * Imposta il valore della propriet� numeroAllegati.
     * 
     */
    public void setNumeroAllegati(int value) {
        this.numeroAllegati = value;
    }

    /**
     * Recupera il valore della propriet� dataInizio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizio() {
        return dataInizio;
    }

    /**
     * Imposta il valore della propriet� dataInizio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizio(XMLGregorianCalendar value) {
        this.dataInizio = value;
    }

    /**
     * Recupera il valore della propriet� dataFine.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFine() {
        return dataFine;
    }

    /**
     * Imposta il valore della propriet� dataFine.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFine(XMLGregorianCalendar value) {
        this.dataFine = value;
    }

    /**
     * Recupera il valore della propriet� classificazionePrincipale.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getClassificazionePrincipale() {
        return classificazionePrincipale;
    }

    /**
     * Imposta il valore della propriet� classificazionePrincipale.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setClassificazionePrincipale(ObjectIdType value) {
        this.classificazionePrincipale = value;
    }

}
