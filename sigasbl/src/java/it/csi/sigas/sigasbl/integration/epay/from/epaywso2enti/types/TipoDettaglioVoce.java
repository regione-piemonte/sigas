
package it.csi.sigas.sigasbl.integration.epay.from.epaywso2enti.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TipoDettaglioVoce.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TipoDettaglioVoce">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="IMPORTO_TRANSATO"/>
 *     &lt;enumeration value="IMPORTO_AUTORIZZATO"/>
 *     &lt;enumeration value="IMPORTO_COMMISSIONI"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoDettaglioVoce")
@XmlEnum
public enum TipoDettaglioVoce {

    IMPORTO_TRANSATO,
    IMPORTO_AUTORIZZATO,
    IMPORTO_COMMISSIONI;

    public String value() {
        return name();
    }

    public static TipoDettaglioVoce fromValue(String v) {
        return valueOf(v);
    }

}
