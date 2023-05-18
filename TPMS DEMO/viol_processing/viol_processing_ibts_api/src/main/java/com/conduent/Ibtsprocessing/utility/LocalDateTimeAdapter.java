package com.conduent.Ibtsprocessing.utility;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * LocalDateTimeAdapter used by GSON for converting LocalDateTime into the given format
 * 
 * @author deepeshb
 *
 */
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime> {
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

	@Override
	public JsonElement serialize(LocalDateTime dateTime, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(dateTime.format(dateTimeFormatter)); // "yyyy-mm-dd"
	}
}
