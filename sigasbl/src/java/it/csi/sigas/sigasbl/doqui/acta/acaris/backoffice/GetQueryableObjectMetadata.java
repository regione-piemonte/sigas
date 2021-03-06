/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.EnumPropertyFilterOperation;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.ObjectIdType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.QueryableObjectType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="repositoryId" type="{common.acaris.acta.doqui.it}ObjectIdType"/>
 *         &lt;element name="queryableObject" type="{common.acaris.acta.doqui.it}QueryableObjectType"/>
 *         &lt;element name="operation" type="{common.acaris.acta.doqui.it}enumPropertyFilterOperation"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "repositoryId",
    "queryableObject",
    "operation"
})
@XmlRootElement(name = "getQueryableObjectMetadata")
public class GetQueryableObjectMetadata {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected QueryableObjectType queryableObject;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumPropertyFilterOperation operation;

    /**
     * Recupera il valore della propriet� repositoryId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getRepositoryId() {
        return repositoryId;
    }

    /**
     * Imposta il valore della propriet� repositoryId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setRepositoryId(ObjectIdType value) {
        this.repositoryId = value;
    }

    /**
     * Recupera il valore della propriet� queryableObject.
     * 
     * @return
     *     possible object is
     *     {@link QueryableObjectType }
     *     
     */
    public QueryableObjectType getQueryableObject() {
        return queryableObject;
    }

    /**
     * Imposta il valore della propriet� queryableObject.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryableObjectType }
     *     
     */
    public void setQueryableObject(QueryableObjectType value) {
        this.queryableObject = value;
    }

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

}
