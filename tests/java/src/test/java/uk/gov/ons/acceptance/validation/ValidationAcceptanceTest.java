package uk.gov.ons.acceptance.validation;

import com.mashape.unirest.http.HttpResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.gov.ons.acceptance.BaseAcceptanceTest;

import static com.mashape.unirest.http.Unirest.get;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@Narrative(text = {
        "In order for the API Application - Anthill - to respond correctly",
        "As the API developer",
        "I want to validate the request and query parameters"
})
@RunWith(SerenityRunner.class)
public class ValidationAcceptanceTest extends BaseAcceptanceTest {

    @Test
    @Title("When I request data" +
            "<br>And I specify a negative starting index" +
            "<br>But I don't specify a page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNegative() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/dataset?start=-1").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a fraction starting index" +
            "<br>But I don't specify a page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsFraction() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/dataset?start=0.2").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a non-numeric starting index" +
            "<br>But I don't specify a page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNotNumeric() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/dataset?start=abc").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I don't specify a starting index" +
            "<br>And I specify a negative page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenPageSizeIsNegative() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/timeseries?limit=-1").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I don't specify a starting index" +
            "<br>And I specify a fraction page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenPageSizeIsFraction() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/timeseries?limit=1.5").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I don't specify a starting index" +
            "<br>And I specify a non-numeric page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenPageSizeIsNotNumeric() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/timeseries?limit=quit").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a starting index" +
            "<br>And I specify a negative page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNumericButPageSizeIsNegative() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/timeseries?start=3&limit=-3").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a starting index" +
            "<br>And I specify a fraction page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNumericButPageSizeIsFraction() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/timeseries?start=3&limit=2.3").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a starting index" +
            "<br>And I specify a non-numeric page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNumericButPageSizeIsNotNumeric() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/timeseries?start=3&limit=vue").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a negative starting index" +
            "<br>And I specify a numeric page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNegativeButPageSizeIsNumeric() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/timeseries/CPCM/dataset?start=-9&limit=39").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a fraction starting index" +
            "<br>And I specify a numeric page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsFractionButPageSizeIsNumeric() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/timeseries/CPCM/dataset?start=4.9&limit=39").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a non-number starting index" +
            "<br>And I specify a numeric page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNotNumericButPageSizeIsNumeric() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/timeseries/CPCM/dataset?start=goodyear&limit=39").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a negative starting index" +
            "<br>And I specify a negative page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNegativeButPageSizeIsNegative() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/dataset/UKEA/timeseries?start=-1&limit=-27").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a fraction starting index" +
            "<br>And I specify a fraction page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsFractionButPageSizeIsFraction() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/dataset/UKEA/timeseries?start=1.1&limit=7.3").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

    @Test
    @Title("When I request data" +
            "<br>And I specify a non-number starting index" +
            "<br>And I specify a non-numeric page size" +
            "<br>Then a response indicating bad request should be returned" +
            "<hr>")
    public void shouldGetBadRequestWhenStartingIndexIsNotNumericButPageSizeIsNotNumeric() throws Exception {
        final HttpResponse<String> response = get(apiServerUrl + "/dataset/UKEA/timeseries?start=forget&limit=therapy").asString();

        assertThat(response.getStatus(), is(SC_BAD_REQUEST));
    }

}