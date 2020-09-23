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


/**
 * <p>Classe Java per DocumentoDBPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentoDBPropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{archive.acaris.acta.doqui.it}DocumentoPropertiesType">
 *       &lt;sequence>
 *         &lt;element name="dataUltimoAggiornam" type="{archive.acaris.acta.doqui.it}DataUltimoAggiornamType"/>
 *         &lt;element name="attivo" type="{archive.acaris.acta.doqui.it}AttivoType"/>
 *         &lt;element name="ubicazione" type="{archive.acaris.acta.doqui.it}UbicazioneType"/>
 *         &lt;element name="versioneSW" type="{archive.acaris.acta.doqui.it}VersioneSWType"/>
 *         &lt;element name="tipoDocFisico" type="{archive.acaris.acta.doqui.it}enumTipoDocumentoType"/>
 *         &lt;element name="composizione" type="{archive.acaris.acta.doqui.it}enumDocPrimarioType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoDBPropertiesType", propOrder = {
    "dataUltimoAggiornam",
    "attivo",
    "ubicazione",
    "versioneSW",
    "tipoDocFisico",
    "composizione"
})
public class DocumentoDBPropertiesType
    extends DocumentoPropertiesType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataUltimoAggiornam;
    protected boolean attivo;
    @XmlElement(required = true)
    protected String ubicazione;
    @XmlElement(required = true)
    protected String versioneSW;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumTipoDocumentoType tipoDocFisico;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumDocPrimarioType composizione;

    /**
     * Recupera il valore della propriet� dataUltimoAggiornam.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataUltimoAggiornam() {
        return dataUltimoAggiornam;
    }

    /**
     * Imposta il valore della propriet� dataUltimoAggiornam.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataUltimoAggiornam(XMLGregorianCalendar value) {
        this.dataUltimoAggiornam = value;
    }

    /**
     * Recupera il valore della propriet� attivo.
     * 
     */
    public boolean isAttivo() {
        return attivo;
    }

    /**
     * Imposta il valore della propriet� attivo.
     * 
     */
    public void setAttivo(boolean value) {
        this.attivo = value;
    }

    /**
     * Recupera il valore della propriet� ubicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUbicazione() {
        return ubicazione;
    }

    /**
     * Imposta il valore della propriet� ubicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUbicazione(String value) {
        this.ubicazione = value;
    }

    /**
     * Recupera il valore della propriet� versioneSW.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersioneSW() {
        return versioneSW;
    }

    /**
     * Imposta il valore della propriet� versioneSW.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersioneSW(String value) {
        this.versioneSW = value;
    }

    /**
     * Recupera il valore della propriet� tipoDocFisico.
     * 
     * @return
     *     possible object is
     *     {@link EnumTipoDocumentoType }
     *     
     */
    public EnumTipoDocumentoType getTipoDocFisico() {
        return tipoDocFisico;
    }

    /**
     * Imposta il valore della propriet� tipoDocFisico.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumTipoDocumentoType }
     *     
     */
    public void setTipoDocFisico(EnumTipoDocumentoType value) {
        this.tipoDocFisico = value;
    }

    /**
     * Recupera il valore della propriet� composizione.
     * 
     * @return
     *     possible object is
     *     {@link EnumDocPrimarioType }
     *     
     */
    public EnumDocPrimarioType getComposizione() {
        return composizione;
    }

    /**
     * Imposta il valore della propriet� composizione.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumDocPrimarioType }
     *     
     */
    public void setComposizione(EnumDocPrimarioType value) {
        this.composizione = value;
    }

}
