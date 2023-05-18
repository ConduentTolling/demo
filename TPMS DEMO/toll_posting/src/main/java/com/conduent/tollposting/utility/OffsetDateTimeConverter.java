package com.conduent.tollposting.utility;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.common.base.Preconditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Json (GSON) serializer/deserializer for converting JAVA 8 time api {@link ZonedDateTime} objects.
 *
 * @see JsonDeserializer
 * @see JsonDeserializer
 */
public class OffsetDateTimeConverter implements JsonSerializer<OffsetDateTime>, JsonDeserializer<OffsetDateTime> {

    /** Format specifier. */
    private final DateTimeFormatter dateTimeFormatter;

    public OffsetDateTimeConverter(final DateTimeFormatter dateTimeFormatter) {
        Preconditions.checkArgument(dateTimeFormatter != null, "dateTimeFormatter is null");
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public JsonElement serialize(OffsetDateTime offsetDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(offsetDateTime.format(dateTimeFormatter));
    }

    @Override
    public OffsetDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.getAsString() == null || jsonElement.getAsString().isEmpty()) {
            return null;
        }

        return OffsetDateTime.parse(jsonElement.getAsString(), dateTimeFormatter);
    }
}