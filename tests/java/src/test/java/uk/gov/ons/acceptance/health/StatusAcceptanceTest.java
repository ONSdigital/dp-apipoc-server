package uk.gov.ons.acceptance.health;

import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Title;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.gov.ons.acceptance.BaseAcceptanceTest;

import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.head;
import static java.util.Collections.singletonList;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Narrative(text = {
        "In order to know the health of the API application",
        "As a member of the Digital Publishing API team",
        "I want to request it's status"
})
@RunWith(SerenityRunner.class)
public class StatusAcceptanceTest extends BaseAcceptanceTest {
    private final String healthScenarios = acceptanceScenarios + "/health";

    @Test
    @Title("Given that the API application is running" +
            "<br>When I 'ping' it" +
            "<br>Then an OK status should be returned" +
            "<hr>")
    public void shouldPingApplication() throws Exception {
        final HttpResponse<String> response = head(apiServerUrl + "/ops/ping").asString();

        assertThat(response.getStatus(), is(SC_OK));
    }

    @Test
    @Title("Given that the API application is running" +
            "<br>And it's dependencies are healthy" +
            "<br>When I request it's status" +
            "<br>Then a healthy status report of application and dependencies should be returned" +
            "<hr>")
    public void shouldGetApplicationStatus() throws Exception {
        final String statusTemplate = jsonReader.readFile(healthScenarios, "status.json");

        final HttpResponse<String> response = get(apiServerUrl + "/ops/status").asString();

        final JsonObject actualStatus = jsonParser.parse(response.getBody()).getAsJsonObject();

        final String instanceName = actualStatus.get("dependencies").getAsJsonObject()
                .get("elasticsearch").getAsJsonObject()
                .get("pingResponse").getAsJsonObject()
                .get("name").getAsString();


        final JsonObject expectedStatus = jsonParser.parse(
                statusTemplate.replace("%{elasticsearch_instance}", instanceName)
        ).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualStatus, is(expectedStatus));
    }

    @Ignore
    @Test
    @Title("Given that the API application is running" +
            "<br>And a dependency is not healthy" +
            "<br>When I request it's status" +
            "<br>Then a status report of application including the unhealthy dependencies should be returned" +
            "<hr>")
    public void shouldReportFailedDependency() throws Exception {
        final JsonObject expectedStatus = jsonReader.getJson(
                acceptanceScenarios + "/health",
                "unhealthy_dependency_status.json"
        ).getAsJsonObject();

        final HttpResponse<String> response = get(apiServerUrl + "/ops/status").asString();

        final JsonObject actualStatus = jsonParser.parse(response.getBody()).getAsJsonObject();

        assertThat(response.getStatus(), is(SC_OK));
        assertThat(response.getHeaders().get(CONTENT_TYPE), is(singletonList(APPLICATION_JSON)));
        assertThat(actualStatus, is(expectedStatus));
    }
}