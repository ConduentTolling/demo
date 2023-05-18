package com.conduent.tpms.unmatched.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
		return new JsonPrimitive(date.format(dateFormatter)); // "yyyy-mm-dd"
	}
	

	public String convertDate(String date) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-YY");
		Date convertedDate = format1.parse(date);
		String convertedDate2 = format2.format(convertedDate);
		System.out.println(convertedDate2);
		return convertedDate2;
	}

}