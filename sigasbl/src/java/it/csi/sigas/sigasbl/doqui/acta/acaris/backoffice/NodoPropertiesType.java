/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per NodoPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NodoPropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{backoffice.acaris.acta.doqui.it}OrganizationUnitPropertiesType">
 *       &lt;sequence>
 *         &lt;element name="responsabile" type="{backoffice.acaris.acta.doqui.it}ResponsabileType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodoPropertiesType", propOrder = {
    "responsabile"
})
public class NodoPropertiesType
    extends OrganizationUnitPropertiesType
{

    protected boolean responsabile;

    /**
     * Recupera il valore della propriet� responsabile.
     * 
     */
    public boolean isResponsabile() {
        return responsabile;
    }

    /**
     * Imposta il valore della propriet� responsabile.
     * 
     */
    public void setResponsabile(boolean value) {
        this.responsabile = value;
    }

}
