/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="dettaglioAOO" type="{backoffice.acaris.acta.doqui.it}DettaglioAOOType" minOccurs="0"/>
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
    "dettaglioAOO"
})
@XmlRootElement(name = "getDettaglioAOOResponse")
public class GetDettaglioAOOResponse {

    protected DettaglioAOOType dettaglioAOO;

    /**
     * Recupera il valore della propriet� dettaglioAOO.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioAOOType }
     *     
     */
    public DettaglioAOOType getDettaglioAOO() {
        return dettaglioAOO;
    }

    /**
     * Imposta il valore della propriet� dettaglioAOO.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioAOOType }
     *     
     */
    public void setDettaglioAOO(DettaglioAOOType value) {
        this.dettaglioAOO = value;
    }

}
