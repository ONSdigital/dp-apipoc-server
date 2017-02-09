package uk.gov.ons.api.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InstantConverterUnitTest {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<Instant>() {
            }.getType(), new InstantConverter())
            .create();

    @Test
    public void shouldSerialiseInstant() {
        final Instant payload = Instant.parse("2015-12-23T00:00:00.002Z");
        final String json = gson.toJson(payload);

        assertThat(json, is("\"2015-12-23T00:00:00.002Z\""));
    }

    @Test
    public void shouldDeserialiseInstant() {
        final Instant expectedInstant = Instant.parse("2015-12-23T00:00:00.000Z");

        final String payload = "\"2015-12-23T00:00:00.000Z\"";

        final Instant instant = gson.fromJson(payload, Instant.class);

        assertThat(instant, is(expectedInstant));
    }

}