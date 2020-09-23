/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PropertyFilterConfigurationInfoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PropertyFilterConfigurationInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operation" type="{common.acaris.acta.doqui.it}enumPropertyFilterOperation"/>
 *         &lt;element name="isAllAllowed" type="{common.acaris.acta.doqui.it}boolean"/>
 *         &lt;element name="isNoneAllowed" type="{common.acaris.acta.doqui.it}boolean"/>
 *         &lt;element name="isListAllowed" type="{common.acaris.acta.doqui.it}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyFilterConfigurationInfoType", propOrder = {
    "operation",
    "isAllAllowed",
    "isNoneAllowed",
    "isListAllowed"
})
public class PropertyFilterConfigurationInfoType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumPropertyFilterOperation operation;
    protected boolean isAllAllowed;
    protected boolean isNoneAllowed;
    protected boolean isListAllowed;

    /**
     * Recupera il valore della propriet� operation.
     * 
     * @return
     *     possible object is
     *     {@link EnumPropertyFilterOperation }
     *     
     */
    public EnumPropertyFilterOperation getOperation() {
        return operation;
    }

    /**
     * Imposta il valore della propriet� operation.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumPropertyFilterOperation }
     *     
     */
    public void setOperation(EnumPropertyFilterOperation value) {
        this.operation = value;
    }

    /**
     * Recupera il valore della propriet� isAllAllowed.
     * 
     */
    public boolean isIsAllAllowed() {
        return isAllAllowed;
    }

    /**
     * Imposta il valore della propriet� isAllAllowed.
     * 
     */
    public void setIsAllAllowed(boolean value) {
        this.isAllAllowed = value;
    }

    /**
     * Recupera il valore della propriet� isNoneAllowed.
     * 
     */
    public boolean isIsNoneAllowed() {
        return isNoneAllowed;
    }

    /**
     * Imposta il valore della propriet� isNoneAllowed.
     * 
     */
    public void setIsNoneAllowed(boolean value) {
        this.isNoneAllowed = value;
    }

    /**
     * Recupera il valore della propriet� isListAllowed.
     * 
     */
    public boolean isIsListAllowed() {
        return isListAllowed;
    }

    /**
     * Imposta il valore della propriet� isListAllowed.
     * 
     */
    public void setIsListAllowed(boolean value) {
        this.isListAllowed = value;
    }

}
