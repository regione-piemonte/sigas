/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EntePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EntePropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{backoffice.acaris.acta.doqui.it}OrganizationUnitPropertiesType">
 *       &lt;sequence>
 *         &lt;element name="abilitato" type="{backoffice.acaris.acta.doqui.it}AbilitatoType"/>
 *         &lt;element name="tipologiaEnte" type="{backoffice.acaris.acta.doqui.it}enumTipologiaEnteType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntePropertiesType", propOrder = {
    "abilitato",
    "tipologiaEnte"
})
public class EntePropertiesType
    extends OrganizationUnitPropertiesType
{

    protected boolean abilitato;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumTipologiaEnteType tipologiaEnte;

    /**
     * Recupera il valore della propriet� abilitato.
     * 
     */
    public boolean isAbilitato() {
        return abilitato;
    }

    /**
     * Imposta il valore della propriet� abilitato.
     * 
     */
    public void setAbilitato(boolean value) {
        this.abilitato = value;
    }

    /**
     * Recupera il valore della propriet� tipologiaEnte.
     * 
     * @return
     *     possible object is
     *     {@link EnumTipologiaEnteType }
     *     
     */
    public EnumTipologiaEnteType getTipologiaEnte() {
        return tipologiaEnte;
    }

    /**
     * Imposta il valore della propriet� tipologiaEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumTipologiaEnteType }
     *     
     */
    public void setTipologiaEnte(EnumTipologiaEnteType value) {
        this.tipologiaEnte = value;
    }

}
