
package it.csi.sigas.sigasbl.integration.epay.from.clienttypes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoVersamento.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoVersamento">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SPONTANEO"/>
 *     &lt;enumeration value="ATTESO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoVersamento")
@XmlEnum
public enum TipoVersamento {

    SPONTANEO,
    ATTESO;

    public String value() {
        return name();
    }

    public static TipoVersamento fromValue(String v) {
        return valueOf(v);
    }

}
