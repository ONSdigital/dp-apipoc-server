package uk.gov.ons.api.converter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantConverter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    @Override
    public Instant deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        return formatter.parse(json.getAsString(), Instant::from);
    }

    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(formatter.format(instant));
    }
}