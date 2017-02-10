package uk.gov.ons.acceptance.metadata;


import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.gov.ons.acceptance.BaseAcceptanceTest;
import uk.gov.ons.api.ApiClient;
import uk.gov.ons.api.model.Records;

import static java.util.Collections.singletonList;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Narrative(text = {
        "In order to obtain statistical data",
        "As an ONS API user",
        "I want to request data sets"
})
@RunWith(SerenityRunner.class)
public class GetByTypeAcceptanceTest extends BaseAcceptanceTest {
    private final String dataDir = acceptanceScenarios + "/metadata";

    @Test
    @Title("Given that data exists in the dataset index" +
            "<br>When I request data sets" +
            "<br>Then a page of data sets should be returned" +
            "<hr>")
    public void shouldGetDataSet() throws Exception {
        final JsonObject expectedDatasets = jsonReader
                .getJson(dataDir, "datasets_page_1.json")
                .getAsJsonObject();

        final HttpResponse<Records> response = ApiClient.set().getDatasets();

        //final HttpResponse<String> response = get(apiServerUrl + "/dataset").asString();

        final JsonObject actualDatasets = jsonParser.parse(gson.toJson(response.getBody())).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualDatasets, is(expectedDatasets));
    }

    @Test
    @Title("Given that data exists in the timeseries index" +
            "<br>When I request time series" +
            "<br>Then a page of time series should be returned" +
            "<hr>")
    public void shouldGetTimeSeries() throws Exception {
        final JsonObject expectedTimeseries = jsonReader
                .getJson(dataDir, "timeseries_page_1.json")
                .getAsJsonObject();

        final HttpResponse<Records> response = ApiClient.set().getTimeseries();

        //final HttpResponse<String> response = get(apiServerUrl + "/timeseries").asString();

        final JsonObject actualTimeseries = jsonParser.parse(gson.toJson(response.getBody())).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualTimeseries, is(expectedTimeseries));
    }

}
