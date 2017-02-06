package uk.gov.ons.hornet.metadata;


import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Title;
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
        "I want to request data sets"
})
@RunWith(SerenityRunner.class)
public class GetByTypeAcceptanceTest extends BaseTest {
    private final String dataDir = acceptanceScenarios + "/metadata";

//    @ClassRule
//    public static DockerComposeContainer elasticSearch =
//            new DockerComposeContainer(new File("src/test/resources/compose-elastic.yml"))
//                    .withExposedService("elasticsearch_1", 9200);

    @Test
    @Title("Given that data exists in the dataset index" +
            "<br>When I request data sets" +
            "<br>Then a page of data sets should be returned" +
            "<hr>")
    public void shouldGetDataSet() throws Exception {
        final JsonObject expectedDatasets = jsonReader
                .getJson(dataDir, "datasets_page_1.json")
                .getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/dataset").asString();

        final JsonObject actualDatasets = jsonParser.parse(response.getBody()).getAsJsonObject();

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

        final HttpResponse<String> response = get(acceptanceUrl + "/timeseries").asString();

        final JsonObject actualTimeseries = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualTimeseries, is(expectedTimeseries));
    }

}
