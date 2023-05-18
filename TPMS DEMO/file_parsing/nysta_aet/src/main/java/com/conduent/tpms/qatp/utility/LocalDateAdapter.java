package com.conduent.tpms.qatp.utility;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * LocalDateAdapter used by GSON for converting LocalDate into the given format
 * 
 * @author deepeshb
 *
 */
public class LocalDateAdapter implements JsonSerializer<LocalDate> {

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(dateFormatter.format(date)); // "yyyy-mm-dd"
	}

}