package uk.gov.ons.api.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.MARCH;
import static java.util.Calendar.MILLISECOND;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DateConverterUnitTest {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<Date>() {
            }.getType(), new DateConverter())
            .create();

    @Test
    public void shouldSerialiseDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2017, MARCH, 31);
        final Date date = calendar.getTime();

        final String json = gson.toJson(date);

        assertThat(json, is("\"31 March 2017\""));
    }

    @Test
    public void shouldDeserialiseDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2017, MARCH, 31, 0, 0, 0);
        calendar.set(MILLISECOND, 0);
        final Date expectedDate = calendar.getTime();

        final String payload = "\"31 March 2017\"";

        final Date date = gson.fromJson(payload, Date.class);

        assertThat(date, is(expectedDate));
    }

}