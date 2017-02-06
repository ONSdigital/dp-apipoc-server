package uk.gov.ons.hornet.metadata;


import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Title;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.gov.ons.hornet.BaseTest;

import static com.mashape.unirest.http.Unirest.get;
import static java.util.Collections.singletonList;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Narrative(text = {
        "In order to obtain statistical data",
        "As an ONS API user",
        "I want to request specific data"
})
@RunWith(SerenityRunner.class)
public class GetByIdAcceptanceTest extends BaseTest {
    private final String dataDir = acceptanceScenarios + "/metadata";

    @Ignore
    @Test
    @Title("Given that data sets exist with identifiers" +
            "<br>When I request a specific data set" +
            "<br>Then the metadata of my desired data set should be returned" +
            "<hr>")
    public void shouldGetSpecificDataset() throws Exception {
        final JsonObject expectedDatasets = jsonParser.parse("{}").getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/dataset/UKEA").asString();

        final JsonObject actualDataset = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualDataset, is(expectedDatasets));
    }

    @Test
    @Title("Given that time series exist with identifiers" +
            "<br>When I request a specific time series" +
            "<br>Then the metadata of my desired time series should be returned" +
            "<hr>")
    public void shouldGetSpecificTimeSeries() throws Exception {
        final JsonObject expectedTimeseries = jsonReader
                .getJson(dataDir, "specific_timeseries.json")
                .getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/timeseries/FCCS").asString();

        final JsonObject actualTimeseries = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualTimeseries, is(expectedTimeseries));
    }

    @Ignore
    @Test
    @Title("Given that data sets exist with time series identifiers" +
            "<br>When I request the data sets for a specific time series" +
            "<br>Then a listing of data sets, the specified time series belongs to, should be returned" +
            "<hr>")
    public void shouldGetDatasetsSpecificTimeSeriesBelongsTo() throws Exception {
        final JsonObject expectedDataset = jsonParser.parse("{}").getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/timeseries/nmcu/dataset").asString();

        final JsonObject actualDataset = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualDataset, is(expectedDataset));
    }

    @Test
    @Title("Given that time series exist in data sets" +
            "<br>When I request the time series in a specific data set" +
            "<br>Then a listing of time series, in the specified data set, should be returned" +
            "<hr>")
    public void shouldGetTimeSeriesInSpecificDataset() throws Exception {
        final JsonObject expectedTimeseries = jsonReader
                .getJson(dataDir, "timeseries_in_specific_dataset.json")
                .getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/dataset/ukea/timeseries").asString();

        final JsonObject actualTimeseries = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualTimeseries, is(expectedTimeseries));
    }

    @Test
    @Title("Given that time series exist in data sets" +
            "<br>When I request a specific data set's specific time series" +
            "<br>Then the metadata of my specified time series in the specified data set should be returned" +
            "<hr>")
    public void shouldGetSpecificDatasetSpecificTimeSeries() throws Exception {
        final JsonObject expectedDatasets = jsonReader
                .getJson(dataDir, "specific_timeseries_in_specific_dataset.json")
                .getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/dataset/UKEA/timeseries/M9LE").asString();

        final JsonObject actualDataset = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualDataset, is(expectedDatasets));
    }

    @Test
    @Title("Given that time series exist in data sets" +
            "<br>When I request a specific time series in a specific data set" +
            "<br>Then the metadata of the specified time series in the specified data set should be returned" +
            "<hr>")
    public void shouldGetSpecificTimeSeriesInSpecificDataset() throws Exception {
        final JsonObject expectedDatasets = jsonReader
                .getJson(dataDir, "specific_timeseries_in_specific_dataset.json")
                .getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/timeseries/M9LE/dataset/UKEA").asString();

        final JsonObject actualDataset = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualDataset, is(expectedDatasets));
    }


}