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
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
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
        final JsonObject expectedRecords = jsonReader
                .getJson(dataDir, "search.json")
                .getAsJsonObject();

        final HttpResponse<Records> response = ApiClient.set().search("Travel Fare");

        final JsonObject actualRecords = jsonParser.parse(gson.toJson(response.getBody())).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualRecords, is(expectedRecords));
    }

    @Test
    @Title("Given that metadata index does not contains my search term" +
            "<br>When I search" +
            "<br>Then a response code of 404 should be returned" +
            "<hr>")
    public void shouldNotFindRecords() throws Exception {
        final HttpResponse<Records> response = ApiClient.set().search("A dog chases a rose");

        assertThat(response.getStatus(), is(SC_NOT_FOUND));
    }

}