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

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.CommonPropertiesType;


/**
 * <p>Classe Java per BackOfficePropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="BackOfficePropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{common.acaris.acta.doqui.it}CommonPropertiesType">
 *       &lt;sequence>
 *         &lt;element name="objectTypeId" type="{backoffice.acaris.acta.doqui.it}enumBackOfficeObjectType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BackOfficePropertiesType", propOrder = {
    "objectTypeId"
})
@XmlSeeAlso({
    UtentePropertiesType.class,
    OrganizationUnitPropertiesType.class
})
public abstract class BackOfficePropertiesType
    extends CommonPropertiesType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumBackOfficeObjectType objectTypeId;

    /**
     * Recupera il valore della propriet� objectTypeId.
     * 
     * @return
     *     possible object is
     *     {@link EnumBackOfficeObjectType }
     *     
     */
    public EnumBackOfficeObjectType getObjectTypeId() {
        return objectTypeId;
    }

    /**
     * Imposta il valore della propriet� objectTypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumBackOfficeObjectType }
     *     
     */
    public void setObjectTypeId(EnumBackOfficeObjectType value) {
        this.objectTypeId = value;
    }

}
