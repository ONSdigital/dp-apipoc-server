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
        "In order to find data",
        "As an ONS API user",
        "I want to search for it"
})
@RunWith(SerenityRunner.class)
public class SearchAcceptanceTest extends BaseAcceptanceTest {
    private final String dataDir = acceptanceScenarios + "/metadata";

    @Test
    @Title("Given that metadata exists" +
            "<br>And index contains my search term" +
            "<br>When I search" +
            "<br>Then a page of records matching my search term should be returned" +
            "<hr>")
    public void shouldFindRecords() throws Exception {
        final JsonObject expectedTimeseries = jsonReader
                .getJson(dataDir, "search.json")
                .getAsJsonObject();

        final HttpResponse<Records> response = ApiClient.set().search("Travel Fare");

        final JsonObject actualTimeseries = jsonParser.parse(gson.toJson(response.getBody())).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualTimeseries, is(expectedTimeseries));
    }


    // don't find "a cat chases a dog"

}