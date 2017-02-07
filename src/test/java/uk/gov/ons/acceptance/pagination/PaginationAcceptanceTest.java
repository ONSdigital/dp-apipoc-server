package uk.gov.ons.acceptance.pagination;

import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.gov.ons.acceptance.BaseAcceptanceTest;

import static com.mashape.unirest.http.Unirest.get;
import static java.util.Collections.singletonList;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Narrative(text = {
        "In order for the API Application - Anthill - to respond quickly",
        "As an ONS API user",
        "I want to request data in pages"
})
@RunWith(SerenityRunner.class)
public class PaginationAcceptanceTest extends BaseAcceptanceTest {
    private final String dataDir = acceptanceScenarios + "/metadata";

    @Test
    @Title("When I request data" +
            "<br>And I do not specify the starting index" +
            "<br>Or the page size" +
            "<br>Then page one of the data, starting at index 0" +
            "<br>And containing a default number of items, should be returned" +
            "<hr>")
    public void shouldGetDataSet() throws Exception {
        final JsonObject expectedDatasets = jsonReader
                .getJson(dataDir, "datasets_default_page_1.json")
                .getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/dataset").asString();

        final JsonObject actualDatasets = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualDatasets, is(expectedDatasets));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a starting index" +
            "<br>But I don't specify a page size" +
            "<br>Then a listing of the data, starting at the desired index item " +
            "<br>And containing a default number of items, should be returned" +
            "<hr>")
    public void shouldGetDataSetsWithStartingIndex() throws Exception {
        final JsonObject expectedDatasets = jsonReader
                .getJson(dataDir, "datasets_start_index_only.json")
                .getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/dataset?start=3").asString();

        final JsonObject actualDatasets = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualDatasets, is(expectedDatasets));
    }

    @Test
    @Title("When I request data" +
            "<br>And I don't specify a starting index" +
            "<br>But I specify a page size" +
            "<br>Then a listing of the data, starting at the default index item of 0" +
            "<br>And containing my desired number of items, should be returned" +
            "<hr>")
    public void shouldGetDataSetWithLimit() throws Exception {
        final JsonObject expectedDatasets = jsonReader
                .getJson(dataDir, "datasets_limit_only.json")
                .getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/dataset?limit=2").asString();

        final JsonObject actualDatasets = jsonParser.parse(response.getBody())
                .getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualDatasets, is(expectedDatasets));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a starting index" +
            "<br>And a page size" +
            "<br>Then a listing of the data sets, starting at the desired index item" +
            "<br>And containing my desired number of items, should be returned" +
            "<hr>")
    public void shouldGetDataSetsWithStartingIndexAndLimit() throws Exception {
        final JsonObject expectedDatasets = jsonReader
                .getJson(dataDir, "datasets_start_index_and_limit.json")
                .getAsJsonObject();

        final HttpResponse<String> response = get(acceptanceUrl + "/dataset?start=3&limit=4").asString();

        final JsonObject actualDatasets = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualDatasets, is(expectedDatasets));
    }

}
