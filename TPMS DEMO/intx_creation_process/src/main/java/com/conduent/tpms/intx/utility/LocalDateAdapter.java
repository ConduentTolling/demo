package com.conduent.tpms.intx.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

import com.conduent.tpms.intx.constants.IntxConstant;
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

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(IntxConstant.FORMAT_yyyy_MM_dd);

	/**
	 *To serialize LocalDate while using Gson
	 */
	@Override
	public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(date.format(dateFormatter)); // "yyyy-mm-dd"
	}

}