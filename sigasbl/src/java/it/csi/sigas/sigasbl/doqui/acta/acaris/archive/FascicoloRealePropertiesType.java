/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.IdVitalRecordCodeType;


/**
 * <p>Classe Java per FascicoloRealePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FascicoloRealePropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{archive.acaris.acta.doqui.it}AggregazionePropertiesType">
 *       &lt;sequence>
 *         &lt;element name="anno" type="{archive.acaris.acta.doqui.it}AnnoType"/>
 *         &lt;element name="numero" type="{common.acaris.acta.doqui.it}string"/>
 *         &lt;element name="oggetto" type="{archive.acaris.acta.doqui.it}OggettoType"/>
 *         &lt;element name="soggetto" type="{archive.acaris.acta.doqui.it}SoggettoType"/>
 *         &lt;element name="creatoFascStd" type="{archive.acaris.acta.doqui.it}CreatoFascStdType"/>
 *         &lt;element name="modificatoFascStd" type="{archive.acaris.acta.doqui.it}ModificatoFascStdType"/>
 *         &lt;element name="numeroInterno" type="{archive.acaris.acta.doqui.it}NumeroInternoType"/>
 *         &lt;element name="idVitalRecordCode" type="{common.acaris.acta.doqui.it}IdVitalRecordCodeType"/>
 *         &lt;element name="idFascicoloStdRiferimento" type="{archive.acaris.acta.doqui.it}IdFascicoloStandardType"/>
 *         &lt;element name="stato" type="{archive.acaris.acta.doqui.it}enumFascicoloRealeStatoType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FascicoloRealePropertiesType", propOrder = {
    "anno",
    "numero",
    "oggetto",
    "soggetto",
    "creatoFascStd",
    "modificatoFascStd",
    "numeroInterno",
    "idVitalRecordCode",
    "idFascicoloStdRiferimento",
    "stato"
})
@XmlSeeAlso({
    FascicoloRealeAnnualePropertiesType.class,
    FascicoloRealeLegislaturaPropertiesType.class,
    FascicoloRealeLiberoPropertiesType.class,
    FascicoloRealeContinuoPropertiesType.class,
    FascicoloRealeEreditatoPropertiesType.class
})
public abstract class FascicoloRealePropertiesType
    extends AggregazionePropertiesType
{

    protected int anno;
    @XmlElement(required = true)
    protected String numero;
    @XmlElement(required = true)
    protected String oggetto;
    @XmlElement(required = true)
    protected String soggetto;
    protected boolean creatoFascStd;
    protected boolean modificatoFascStd;
    protected int numeroInterno;
    @XmlElement(required = true)
    protected IdVitalRecordCodeType idVitalRecordCode;
    @XmlElement(required = true)
    protected IdFascicoloStandardType idFascicoloStdRiferimento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumFascicoloRealeStatoType stato;

    /**
     * Recupera il valore della propriet� anno.
     * 
     */
    public int getAnno() {
        return anno;
    }

    /**
     * Imposta il valore della propriet� anno.
     * 
     */
    public void setAnno(int value) {
        this.anno = value;
    }

    /**
     * Recupera il valore della propriet� numero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Imposta il valore della propriet� numero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Recupera il valore della propriet� oggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Imposta il valore della propriet� oggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Recupera il valore della propriet� soggetto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoggetto() {
        return soggetto;
    }

    /**
     * Imposta il valore della propriet� soggetto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoggetto(String value) {
        this.soggetto = value;
    }

    /**
     * Recupera il valore della propriet� creatoFascStd.
     * 
     */
    public boolean isCreatoFascStd() {
        return creatoFascStd;
    }

    /**
     * Imposta il valore della propriet� creatoFascStd.
     * 
     */
    public void setCreatoFascStd(boolean value) {
        this.creatoFascStd = value;
    }

    /**
     * Recupera il valore della propriet� modificatoFascStd.
     * 
     */
    public boolean isModificatoFascStd() {
        return modificatoFascStd;
    }

    /**
     * Imposta il valore della propriet� modificatoFascStd.
     * 
     */
    public void setModificatoFascStd(boolean value) {
        this.modificatoFascStd = value;
    }

    /**
     * Recupera il valore della propriet� numeroInterno.
     * 
     */
    public int getNumeroInterno() {
        return numeroInterno;
    }

    /**
     * Imposta il valore della propriet� numeroInterno.
     * 
     */
    public void setNumeroInterno(int value) {
        this.numeroInterno = value;
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

    /**
     * Recupera il valore della propriet� idFascicoloStdRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link IdFascicoloStandardType }
     *     
     */
    public IdFascicoloStandardType getIdFascicoloStdRiferimento() {
        return idFascicoloStdRiferimento;
    }

    /**
     * Imposta il valore della propriet� idFascicoloStdRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link IdFascicoloStandardType }
     *     
     */
    public void setIdFascicoloStdRiferimento(IdFascicoloStandardType value) {
        this.idFascicoloStdRiferimento = value;
    }

    /**
     * Recupera il valore della propriet� stato.
     * 
     * @return
     *     possible object is
     *     {@link EnumFascicoloRealeStatoType }
     *     
     */
    public EnumFascicoloRealeStatoType getStato() {
        return stato;
    }

    /**
     * Imposta il valore della propriet� stato.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumFascicoloRealeStatoType }
     *     
     */
    public void setStato(EnumFascicoloRealeStatoType value) {
        this.stato = value;
    }

}
