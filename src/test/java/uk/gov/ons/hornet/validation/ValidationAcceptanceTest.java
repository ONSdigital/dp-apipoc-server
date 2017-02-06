package uk.gov.ons.hornet.validation;

import com.mashape.unirest.http.HttpResponse;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import uk.gov.ons.hornet.BaseTest;

import static com.mashape.unirest.http.Unirest.get;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@Narrative(text = {
        "In order for the API Application - Anthill - to respond correctly",
        "As the API developer",
        "I want to validate the request and query parameters"
})
public class ValidationAcceptanceTest extends BaseTest {

    @Test
    @Title("When I request data" +
            "<br>And I specify a non-numeric starting index" +
            "<br>But I don't specify a page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNotNumeric() throws Exception {
        final HttpResponse<String> response = get(acceptanceUrl + "/dataset?start=abc").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I don't specify a starting index" +
            "<br>And I specify a non-numeric page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenPageSizeIsNotNumeric() throws Exception {
        final HttpResponse<String> response = get(acceptanceUrl + "/timeseries?limit=quit").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a starting index" +
            "<br>And I specify a non-numeric page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNumericButPageSizeIsNotNumeric() throws Exception {
        final HttpResponse<String> response = get(acceptanceUrl + "/timeseries?start=3&limit=vue").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a non-number starting index" +
            "<br>And I specify a numeric page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNotNumericButPageSizeIsNumeric() throws Exception {
        final HttpResponse<String> response = get(acceptanceUrl + "/timeseries/CPCM/dataset?start=goodyear&limit=39").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a non-number starting index" +
            "<br>And I specify a non-numeric page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNotNumericButPageSizeIsNotNumeric() throws Exception {
        final HttpResponse<String> response = get(acceptanceUrl + "/dataset/UKEA/timeseries?start=forget&limit=therapy").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

}