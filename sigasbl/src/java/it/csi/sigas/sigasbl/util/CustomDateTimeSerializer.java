package it.csi.sigas.sigasbl.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * @author 
 * @date 
 */
public class CustomDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
	public static final Locale LOCALE_ITALIAN = new Locale("it", "IT");
	public static final TimeZone LOCAL_TIME_ZONE = TimeZone.getTimeZone("Europe/Paris");

	@Override
	public void serialize(LocalDateTime date, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
		if (date == null) {
			jsonGenerator.writeNull();
		} else {
			jsonGenerator.writeString(date.format(FORMATTER));
		}

	}

}