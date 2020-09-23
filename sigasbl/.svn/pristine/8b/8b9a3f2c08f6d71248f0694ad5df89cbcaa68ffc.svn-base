/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.web.serializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
		String dateAsString = jsonParser.getText();
		try {
			if (StringUtils.isEmpty(dateAsString))
				return null;
			Date date = CustomDateSerializer.FORMATTER.parse(dateAsString);
			/*
			 * Calendar calendar = Calendar.getInstance(
			 * CustomDateSerializer.LOCAL_TIME_ZONE,
			 * CustomDateSerializer.LOCALE_HUNGARIAN );
			 */
			// calendar.setTime(calendar.getTime());
			return date;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
