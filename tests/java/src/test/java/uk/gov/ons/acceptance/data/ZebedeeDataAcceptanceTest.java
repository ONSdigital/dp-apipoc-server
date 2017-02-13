package uk.gov.ons.acceptance.data;

import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.gov.ons.acceptance.BaseAcceptanceTest;
import uk.gov.ons.api.ApiClient;
import uk.gov.ons.api.model.Data;

import static java.util.Collections.singletonList;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SerenityRunner.class)
public class ZebedeeDataAcceptanceTest  extends BaseAcceptanceTest {
    private final String dataDir = acceptanceScenarios + "/data";
//    @BeforeClass
//    public static void prepareClass() {
//        ENVIRONMENT_VARIABLES.set("USE_WEBSITE", "false");
//    }

    @Test
    @Title("Given that the API Server is querying the Zebedee for data" +
            "<br>And the data I want exists" +
            "<br>When I request data" +
            "<br>Then my requested data is returned" +
            "<hr>")
    public void shouldGetDataWhenUsingZebedee() throws Exception {
        final JsonObject expectedData = jsonReader
                .getJson(dataDir, "cpcm_ukea.json")
                .getAsJsonObject();

        final HttpResponse<Data> response = ApiClient.set().dataset("UKEA").timeseries("CPCM").getData();

        final JsonObject actualData = jsonParser.parse(gson.toJson(response.getBody())).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualData, is(expectedData));
    }

    @Test
    @Title("Given that the API Server is querying the Zebedee for data" +
            "<br>And the data I want does not exists" +
            "<br>When I request data" +
            "<br>Then NOT FOUND is returned" +
            "<hr>")
    public void shouldNotFindDataWhenUsingZebedeeAndRecordDoesNotExist() throws Exception {
        final HttpResponse<Data> response = ApiClient.set().dataset("UKEA").timeseries("SNOW").getData();

        assertThat(response.getStatus(), is(SC_NOT_FOUND));
    }
}