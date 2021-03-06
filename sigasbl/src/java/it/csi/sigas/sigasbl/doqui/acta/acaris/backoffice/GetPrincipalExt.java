/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.CodiceFiscaleType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.IdAOOType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.IdNodoType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.IdStrutturaType;
import it.csi.sigas.sigasbl.doqui.acta.acaris.common.ObjectIdType;


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
 *         &lt;element name="idUtente" type="{common.acaris.acta.doqui.it}CodiceFiscaleType"/>
 *         &lt;element name="idAOO" type="{common.acaris.acta.doqui.it}IdAOOType"/>
 *         &lt;element name="idStruttura" type="{common.acaris.acta.doqui.it}IdStrutturaType" minOccurs="0"/>
 *         &lt;element name="idNodo" type="{common.acaris.acta.doqui.it}IdNodoType" minOccurs="0"/>
 *         &lt;element name="clientApplicationInfo" type="{backoffice.acaris.acta.doqui.it}ClientApplicationInfo"/>
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
    "idUtente",
    "idAOO",
    "idStruttura",
    "idNodo",
    "clientApplicationInfo"
})
@XmlRootElement(name = "getPrincipalExt")
public class GetPrincipalExt {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected CodiceFiscaleType idUtente;
    @XmlElement(required = true)
    protected IdAOOType idAOO;
    protected IdStrutturaType idStruttura;
    protected IdNodoType idNodo;
    @XmlElement(required = true)
    protected ClientApplicationInfo clientApplicationInfo;

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
     * Recupera il valore della propriet� idUtente.
     * 
     * @return
     *     possible object is
     *     {@link CodiceFiscaleType }
     *     
     */
    public CodiceFiscaleType getIdUtente() {
        return idUtente;
    }

    /**
     * Imposta il valore della propriet� idUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceFiscaleType }
     *     
     */
    public void setIdUtente(CodiceFiscaleType value) {
        this.idUtente = value;
    }

    /**
     * Recupera il valore della propriet� idAOO.
     * 
     * @return
     *     possible object is
     *     {@link IdAOOType }
     *     
     */
    public IdAOOType getIdAOO() {
        return idAOO;
    }

    /**
     * Imposta il valore della propriet� idAOO.
     * 
     * @param value
     *     allowed object is
     *     {@link IdAOOType }
     *     
     */
    public void setIdAOO(IdAOOType value) {
        this.idAOO = value;
    }

    /**
     * Recupera il valore della propriet� idStruttura.
     * 
     * @return
     *     possible object is
     *     {@link IdStrutturaType }
     *     
     */
    public IdStrutturaType getIdStruttura() {
        return idStruttura;
    }

    /**
     * Imposta il valore della propriet� idStruttura.
     * 
     * @param value
     *     allowed object is
     *     {@link IdStrutturaType }
     *     
     */
    public void setIdStruttura(IdStrutturaType value) {
        this.idStruttura = value;
    }

    /**
     * Recupera il valore della propriet� idNodo.
     * 
     * @return
     *     possible object is
     *     {@link IdNodoType }
     *     
     */
    public IdNodoType getIdNodo() {
        return idNodo;
    }

    /**
     * Imposta il valore della propriet� idNodo.
     * 
     * @param value
     *     allowed object is
     *     {@link IdNodoType }
     *     
     */
    public void setIdNodo(IdNodoType value) {
        this.idNodo = value;
    }

    /**
     * Recupera il valore della propriet� clientApplicationInfo.
     * 
     * @return
     *     possible object is
     *     {@link ClientApplicationInfo }
     *     
     */
    public ClientApplicationInfo getClientApplicationInfo() {
        return clientApplicationInfo;
    }

    /**
     * Imposta il valore della propriet� clientApplicationInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientApplicationInfo }
     *     
     */
    public void setClientApplicationInfo(ClientApplicationInfo value) {
        this.clientApplicationInfo = value;
    }

}
