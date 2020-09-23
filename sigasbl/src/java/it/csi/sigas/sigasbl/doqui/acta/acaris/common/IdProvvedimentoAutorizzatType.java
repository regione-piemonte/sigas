/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IdProvvedimentoAutorizzatType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IdProvvedimentoAutorizzatType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="value" type="{common.acaris.acta.doqui.it}IDDBType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdProvvedimentoAutorizzatType", propOrder = {
    "value"
})
public class IdProvvedimentoAutorizzatType {

    protected long value;

    /**
     * Recupera il valore della propriet� value.
     * 
     */
    public long getValue() {
        return value;
    }

    /**
     * Imposta il valore della propriet� value.
     * 
     */
    public void setValue(long value) {
        this.value = value;
    }

}
