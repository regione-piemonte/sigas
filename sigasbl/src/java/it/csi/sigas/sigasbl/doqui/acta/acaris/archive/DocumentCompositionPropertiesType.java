/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DocumentCompositionPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentCompositionPropertiesType">
 *   &lt;complexContent>
 *     &lt;extension base="{archive.acaris.acta.doqui.it}RelationshipPropertiesType">
 *       &lt;sequence>
 *         &lt;element name="bypassControlli" type="{archive.acaris.acta.doqui.it}BypassControlliType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentCompositionPropertiesType", propOrder = {
    "bypassControlli"
})
public class DocumentCompositionPropertiesType
    extends RelationshipPropertiesType
{

    protected boolean bypassControlli;

    /**
     * Recupera il valore della propriet� bypassControlli.
     * 
     */
    public boolean isBypassControlli() {
        return bypassControlli;
    }

    /**
     * Imposta il valore della propriet� bypassControlli.
     * 
     */
    public void setBypassControlli(boolean value) {
        this.bypassControlli = value;
    }

}
