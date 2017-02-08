package uk.gov.ons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import uk.gov.ons.acceptance.reader.JsonReader;
import uk.gov.ons.api.converter.InstantConverter;

import java.lang.reflect.Type;
import java.time.Instant;

public abstract class BaseTest {
    protected final String CONTENT_TYPE = "Content-Type";
    protected final String APPLICATION_JSON = "application/json";

    private final Type INSTANT_TYPE = new TypeToken<Instant>(){}.getType();

    protected final Gson gson = new GsonBuilder()
            .registerTypeAdapter(INSTANT_TYPE, new InstantConverter())
            .create();

    protected final JsonParser jsonParser = new JsonParser();

    protected final JsonReader jsonReader = new JsonReader(gson, jsonParser);
}
