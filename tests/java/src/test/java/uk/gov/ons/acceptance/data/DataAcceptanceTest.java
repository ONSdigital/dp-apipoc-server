package uk.gov.ons.acceptance.data;


import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Title;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import uk.gov.ons.acceptance.BaseAcceptanceTest;
import uk.gov.ons.api.ApiClient;
import uk.gov.ons.api.exception.ApiClientException;
import uk.gov.ons.api.model.Data;

import static java.util.Collections.singletonList;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Narrative(text = {
        "In order to obtain statistical data",
        "As an ONS API user",
        "I want to request specific data"
})
@RunWith(SerenityRunner.class)
public class DataAcceptanceTest extends BaseAcceptanceTest {
    private final String dataDir = acceptanceScenarios + "/data";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    @Title("Given that the data I want exists" +
            "<br>When I request the data" +
            "<br>Then my requested data is returned" +
            "<hr>")
    public void shouldGetDataWhenRecordExits() throws Exception {
        final JsonObject expectedData = jsonReader
                .getJson(dataDir, "chaw_mm23.json")
                .getAsJsonObject();

        final HttpResponse<Data> response = ApiClient.set().dataset("MM23").timeseries("CHAW").getData();

        final JsonObject actualData = jsonParser.parse(gson.toJson(response.getBody())).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualData, is(expectedData));
    }

    @Test
    @Title("Given that the data I want does not exist" +
            "<br>When I request the data" +
            "<br>Then NOT FOUND is returned" +
            "<hr>")
    public void shouldNotFindDataWhenRecordDoesNotExist() throws Exception {
        final HttpResponse<Data> response = ApiClient.set().dataset("UKEA").timeseries("SNOW").getData();

        assertThat(response.getStatus(), is(SC_NOT_FOUND));
    }

    @Test
    @Title("Given I fail to provide a time series identifier" +
            "<br>And a data set identifier" +
            "<br>When I request data" +
            "<br>Then error should be thrown" +
            "<hr>")
    public void shouldRejectRequestWhenDatasetAndTimeseriesAreMissing() throws Exception {
        exception.expect(ApiClientException.class);
        exception.expectMessage(containsString("dataset is not set"));

        ApiClient.set().getData();
    }

    @Test
    @Title("Given I provide a time series identifier" +
            "<br>But fail to provide a data set identifier" +
            "<br>When I request data" +
            "<br>Then error should be thrown" +
            "<hr>")
    public void shouldRejectRequestWhenDatasetIsMissing() throws Exception {
        exception.expect(ApiClientException.class);
        exception.expectMessage(containsString("dataset is not set"));

        ApiClient.set().timeseries("FCCS").getData();
    }

    @Test
    @Title("Given I provide a data set identifier" +
            "<br>But fail to provide a time series identifier" +
            "<br>When I request data" +
            "<br>Then error should be thrown" +
            "<hr>")
    public void shouldRejectGetDataRequestWhenDatasetIsSetAndTimeseriesIsNotSet() throws Exception {
        exception.expect(ApiClientException.class);
        exception.expectMessage(containsString("timeseries is not set"));

        ApiClient.set().dataset("UKEA").getData();
    }

}