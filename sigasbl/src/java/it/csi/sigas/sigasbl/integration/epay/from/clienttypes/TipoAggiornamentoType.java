
package it.csi.sigas.sigasbl.integration.epay.from.clienttypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoAggiornamentoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoAggiornamentoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MODIFICA"/>
 *     &lt;enumeration value="ANNULLAMENTO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoAggiornamentoType")
@XmlEnum
public enum TipoAggiornamentoType {

    MODIFICA,
    ANNULLAMENTO;

    public String value() {
        return name();
    }

    public static TipoAggiornamentoType fromValue(String v) {
        return valueOf(v);
    }

}
