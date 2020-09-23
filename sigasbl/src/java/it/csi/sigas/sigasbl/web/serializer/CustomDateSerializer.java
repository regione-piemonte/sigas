/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.web.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * @author riccardo.bova
 * @date 16 mar 2018
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
	public static final Locale LOCALE_HUNGARIAN = new Locale("it", "IT");
	public static final TimeZone LOCAL_TIME_ZONE = TimeZone.getTimeZone("Europe/Paris");

	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException {
		if (date == null || date.equals("")) {
			jsonGenerator.writeNull();
		} else {

			jsonGenerator.writeString(FORMATTER.format(date.getTime()));
		}

	}

}
