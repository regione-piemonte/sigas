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


/**
 * <p>Classe Java per SottofascicoloPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SottofascicoloPropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{archive.acaris.acta.doqui.it}AggregazionePropertiesType">
 *       &lt;sequence>
 *         &lt;element name="creatoFascStd" type="{archive.acaris.acta.doqui.it}CreatoFascStdType"/>
 *         &lt;element name="stato" type="{archive.acaris.acta.doqui.it}enumSottofascicoloStatoType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SottofascicoloPropertiesType", propOrder = {
    "creatoFascStd",
    "stato"
})
public class SottofascicoloPropertiesType
    extends AggregazionePropertiesType
{

    protected boolean creatoFascStd;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumSottofascicoloStatoType stato;

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
     * Recupera il valore della propriet� stato.
     * 
     * @return
     *     possible object is
     *     {@link EnumSottofascicoloStatoType }
     *     
     */
    public EnumSottofascicoloStatoType getStato() {
        return stato;
    }

    /**
     * Imposta il valore della propriet� stato.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumSottofascicoloStatoType }
     *     
     */
    public void setStato(EnumSottofascicoloStatoType value) {
        this.stato = value;
    }

}
