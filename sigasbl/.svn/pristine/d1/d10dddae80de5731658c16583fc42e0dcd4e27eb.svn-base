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

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.ObjectIdType;


/**
 * <p>Classe Java per RelationshipPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RelationshipPropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{archive.acaris.acta.doqui.it}ArchivePropertiesType">
 *       &lt;sequence>
 *         &lt;element name="relationType" type="{archive.acaris.acta.doqui.it}enumRelationshipObjectType"/>
 *         &lt;element name="sourceId" type="{common.acaris.acta.doqui.it}ObjectIdType"/>
 *         &lt;element name="sourceType" type="{archive.acaris.acta.doqui.it}enumArchiveObjectType"/>
 *         &lt;element name="targetId" type="{common.acaris.acta.doqui.it}ObjectIdType"/>
 *         &lt;element name="targetType" type="{archive.acaris.acta.doqui.it}enumArchiveObjectType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationshipPropertiesType", propOrder = {
    "relationType",
    "sourceId",
    "sourceType",
    "targetId",
    "targetType"
})
@XmlSeeAlso({
    DocumentAssociationPropertiesType.class,
    DocumentCompositionPropertiesType.class,
    HistoryModificheTecnichePropertiesType.class,
    HistoryVecchieVersioniPropertiesType.class
})
public class RelationshipPropertiesType
    extends ArchivePropertiesType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumRelationshipObjectType relationType;
    @XmlElement(required = true)
    protected ObjectIdType sourceId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumArchiveObjectType sourceType;
    @XmlElement(required = true)
    protected ObjectIdType targetId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumArchiveObjectType targetType;

    /**
     * Recupera il valore della propriet� relationType.
     * 
     * @return
     *     possible object is
     *     {@link EnumRelationshipObjectType }
     *     
     */
    public EnumRelationshipObjectType getRelationType() {
        return relationType;
    }

    /**
     * Imposta il valore della propriet� relationType.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumRelationshipObjectType }
     *     
     */
    public void setRelationType(EnumRelationshipObjectType value) {
        this.relationType = value;
    }

    /**
     * Recupera il valore della propriet� sourceId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getSourceId() {
        return sourceId;
    }

    /**
     * Imposta il valore della propriet� sourceId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setSourceId(ObjectIdType value) {
        this.sourceId = value;
    }

    /**
     * Recupera il valore della propriet� sourceType.
     * 
     * @return
     *     possible object is
     *     {@link EnumArchiveObjectType }
     *     
     */
    public EnumArchiveObjectType getSourceType() {
        return sourceType;
    }

    /**
     * Imposta il valore della propriet� sourceType.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumArchiveObjectType }
     *     
     */
    public void setSourceType(EnumArchiveObjectType value) {
        this.sourceType = value;
    }

    /**
     * Recupera il valore della propriet� targetId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getTargetId() {
        return targetId;
    }

    /**
     * Imposta il valore della propriet� targetId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setTargetId(ObjectIdType value) {
        this.targetId = value;
    }

    /**
     * Recupera il valore della propriet� targetType.
     * 
     * @return
     *     possible object is
     *     {@link EnumArchiveObjectType }
     *     
     */
    public EnumArchiveObjectType getTargetType() {
        return targetType;
    }

    /**
     * Imposta il valore della propriet� targetType.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumArchiveObjectType }
     *     
     */
    public void setTargetType(EnumArchiveObjectType value) {
        this.targetType = value;
    }

}
