/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.sigas.sigasbl.doqui.acta.acaris.common.DecodificaExtType;


/**
 * <p>Classe Java per CollocazioneUtente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CollocazioneUtente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="aoo" type="{common.acaris.acta.doqui.it}DecodificaExtType"/>
 *         &lt;element name="struttura" type="{common.acaris.acta.doqui.it}DecodificaExtType"/>
 *         &lt;element name="nodo" type="{common.acaris.acta.doqui.it}DecodificaExtType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CollocazioneUtente", propOrder = {
    "aoo",
    "struttura",
    "nodo"
})
public class CollocazioneUtente {

    @XmlElement(required = true)
    protected DecodificaExtType aoo;
    @XmlElement(required = true)
    protected DecodificaExtType struttura;
    @XmlElement(required = true)
    protected DecodificaExtType nodo;

    /**
     * Recupera il valore della propriet� aoo.
     * 
     * @return
     *     possible object is
     *     {@link DecodificaExtType }
     *     
     */
    public DecodificaExtType getAoo() {
        return aoo;
    }

    /**
     * Imposta il valore della propriet� aoo.
     * 
     * @param value
     *     allowed object is
     *     {@link DecodificaExtType }
     *     
     */
    public void setAoo(DecodificaExtType value) {
        this.aoo = value;
    }

    /**
     * Recupera il valore della propriet� struttura.
     * 
     * @return
     *     possible object is
     *     {@link DecodificaExtType }
     *     
     */
    public DecodificaExtType getStruttura() {
        return struttura;
    }

    /**
     * Imposta il valore della propriet� struttura.
     * 
     * @param value
     *     allowed object is
     *     {@link DecodificaExtType }
     *     
     */
    public void setStruttura(DecodificaExtType value) {
        this.struttura = value;
    }

    /**
     * Recupera il valore della propriet� nodo.
     * 
     * @return
     *     possible object is
     *     {@link DecodificaExtType }
     *     
     */
    public DecodificaExtType getNodo() {
        return nodo;
    }

    /**
     * Imposta il valore della propriet� nodo.
     * 
     * @param value
     *     allowed object is
     *     {@link DecodificaExtType }
     *     
     */
    public void setNodo(DecodificaExtType value) {
        this.nodo = value;
    }

}
