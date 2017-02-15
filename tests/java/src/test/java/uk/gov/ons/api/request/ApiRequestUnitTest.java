package uk.gov.ons.api.request;

import com.mashape.unirest.http.HttpResponse;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockserver.integration.ClientAndServer;
import uk.gov.ons.api.BaseUnitTest;
import uk.gov.ons.api.exception.ApiClientException;
import uk.gov.ons.api.exception.IllegalParameterException;
import uk.gov.ons.api.model.Data;
import uk.gov.ons.api.model.Record;
import uk.gov.ons.api.model.Records;
import uk.gov.ons.mockserver.FakeServer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class ApiRequestUnitTest extends BaseUnitTest {
    private ClientAndServer mockServer;
    private FakeServer fakeServer;
    
    private final ApiRequest apiRequest = new ApiRequest();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws Exception {
        mockServer = startClientAndServer(4040);
        TimeUnit.SECONDS.sleep(1);

        fakeServer = new FakeServer(mockServer);
    }

    @After
    public void tearDown() throws Exception {
        mockServer.stop();
    }

    @Test
    public void shouldGetDefaultFirstPageOfDatasets() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "datasets_default_page_1.json");
        fakeServer.mockGetDatasets(
                0,
                5,
                dsFile
        );

        final Records expectedDatasets = gson.fromJson(dsFile, Records.class);

        final HttpResponse<Records> response = apiRequest.getDatasets();

        assertThat(response.getBody(), is(expectedDatasets));
    }

    @Test
    public void shouldGetDatasetsWhenOnlyStartingIndexIsSpecified() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "datasets_start_index_only.json");
        fakeServer.mockGetDatasets(
                3,
                5,
                dsFile
        );

        final Records expectedDatasets = gson.fromJson(dsFile, Records.class);

        final HttpResponse<Records> response = apiRequest.startIndex(3).getDatasets();

        assertThat(response.getBody(), is(expectedDatasets));
    }

    @Test
    public void shouldGetDatasetsWhenOnlyLimitIsSpecified() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "datasets_limit_only.json");
        fakeServer.mockGetDatasets(
                0,
                2,
                dsFile
        );

        final Records expectedDatasets = gson.fromJson(dsFile, Records.class);

        final HttpResponse<Records> response = apiRequest.itemsPerPage(2).getDatasets();

        assertThat(response.getBody(), is(expectedDatasets));
    }

    @Test
    public void shouldGetDatasetsWhenBothStartingIndexAndLimitAreSpecified() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "datasets_start_index_and_limit.json");
        fakeServer.mockGetDatasets(
                3,
                4,
                dsFile
        );

        final Records expectedDatasets = gson.fromJson(dsFile, Records.class);

        final HttpResponse<Records> response = apiRequest.startIndex(3).itemsPerPage(4).getDatasets();

        assertThat(response.getBody(), is(expectedDatasets));
    }

    @Test
    public void shouldGetDefaultFirstPageOfTimeseries() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "timeseries_default_page_1.json");
        fakeServer.mockGetTimeseries(
                0,
                5,
                dsFile
        );

        final Records expectedTimeserieses = gson.fromJson(dsFile, Records.class);

        final HttpResponse<Records> response = apiRequest.getTimeseries();

        assertThat(response.getBody(), is(expectedTimeserieses));
    }

    @Test
    public void shouldGetSpecificDataset() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "specific_datasets.json");
        fakeServer.mockGetDataset("ashe:%20table%206", 0, 7, dsFile);

        final Records expectedDatasets = gson.fromJson(dsFile, Records.class);

        final HttpResponse<Records> response = apiRequest.itemsPerPage(7).dataset("ASHE: Table 6").getDatasets();

        assertThat(response.getBody(), is(expectedDatasets));
    }

    @Test
    public void shouldGetSpecificTimeseries() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "specific_timeseries.json");
        fakeServer.mockGetTimeseries(
                "fccs",
                dsFile
        );

        final Records expectedTimeseries = gson.fromJson(dsFile, Records.class);

        final HttpResponse<Records> response = apiRequest.timeseries("FCCS").getTimeseries();

        assertThat(response.getBody(), is(expectedTimeseries));
    }

    @Test
    public void shouldGetTimeseriesInSpecificDataset() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "timeseries_in_specific_dataset.json");
        fakeServer.mockGetTimeseriesInDataset(
                "ukea",
                0,
                5,
                dsFile
        );

        final Records expectedTimeserieses = gson.fromJson(dsFile, Records.class);

        final HttpResponse<Records> response = apiRequest.dataset("UKEA").getTimeseries();

        assertThat(response.getBody(), is(expectedTimeserieses));
    }

    @Ignore
    @Test
    public void shouldGetDatasetsASpecificTimeseriesBelongsTo() throws Exception {
    }

    @Test
    public void shouldGetSpecificTimeseriesInSpecificDataset() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "specific_timeseries_in_specific_dataset.json");
        fakeServer.mockGetTimeseries(
                "ukea",
                "fccs",
                dsFile
        );

        final Record expectedTimeseries = gson.fromJson(dsFile, Record.class);

        final HttpResponse<Record> response = apiRequest.dataset("UKEA").getTimeseries("FCCS");

        assertThat(response.getBody(), is(expectedTimeseries));
    }

    @Test
    public void shouldGetMatchingTimeseriesWhenISearch() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "search.json");
        fakeServer.mockSearch(
                "travel",
                0,
                5,
                dsFile
        );

        final Records expectedTimeserieses = gson.fromJson(dsFile, Records.class);

        final HttpResponse<Records> response = apiRequest.search("Travel");

        assertThat(response.getBody(), is(expectedTimeserieses));
    }

    @Test
    public void shouldRejectRequestWhenNoDatasetIdIsSetAndGetTimeseriesWithParameterIsCalled() throws Exception {
        exception.expect(ApiClientException.class);
        exception.expectMessage(containsString("dataset is not set"));

        apiRequest.getTimeseries("FCCS");
    }

    @Test
    public void shouldRejectRequestWhenStartingIndexIsNegative() throws Exception {
        exception.expect(IllegalParameterException.class);
        exception.expectMessage(containsString("Negative parameters not allowed"));

        apiRequest.startIndex(-3).itemsPerPage(4).getDatasets();
    }

    @Test
    public void shouldRejectRequestWhenPageSizeIsNegative() throws Exception {
        exception.expect(IllegalParameterException.class);
        exception.expectMessage(containsString("Negative parameters not allowed"));

        apiRequest.startIndex(3).itemsPerPage(-4).getDatasets();
    }

    @Test
    public void shouldRejectRequestWhenBothStartingIndexAndPageSizeAreNegative() throws Exception {
        exception.expect(IllegalParameterException.class);
        exception.expectMessage(containsString("Negative parameters not allowed"));

        apiRequest.startIndex(-3).itemsPerPage(-4).getDatasets();
    }

    @Test
    public void shouldGetData() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/data", "fccs_ukea.json");
        fakeServer.mockGetData(
                "ukea",
                "fccs",
                dsFile
        );

        final Data expectedData = gson.fromJson(dsFile, Data.class);

        final HttpResponse<Data> response = apiRequest.dataset("UKEA").timeseries("FCCS").getData();

        assertThat(response.getBody(), is(expectedData));
    }

    @Test
    public void shouldRejectGetDataRequestWhenDatasetAndTimeseriesAreNotSet() throws Exception {
        exception.expect(ApiClientException.class);
        exception.expectMessage(containsString("dataset is not set"));

        apiRequest.getData();
    }

    @Test
    public void shouldRejectGetDataRequestWhenDatasetIsSetAndTimeseriesIsNotSet() throws Exception {
        exception.expect(ApiClientException.class);
        exception.expectMessage(containsString("timeseries is not set"));

        apiRequest.dataset("UKEA").getData();
    }

    @Test
    public void shouldRejectGetDataRequestWhenDatasetIsNotSetAndTimeseriesIsSet() throws Exception {
        exception.expect(ApiClientException.class);
        exception.expectMessage(containsString("dataset is not set"));

        apiRequest.timeseries("FCCS").getData();
    }

    @Ignore
    @Test
    public void should() {
        final CompletableFuture<HttpResponse<Data>> httpResponseCompletableFuture = supplyAsync(() -> {
            try {
                return apiRequest.dataset("UKEA").timeseries("FCCS").getData();
            } catch (ApiClientException e) {
                throw new RuntimeException();
            }
        });

        final CompletableFuture<HttpResponse<Records>> httpResponseCompletableFuture1 = supplyAsync(() -> {
            try {
                return apiRequest.dataset("UKEA").getTimeseries();
            } catch (ApiClientException e) {
                throw new RuntimeException();
            }
        });

        final CompletableFuture<HttpResponse<Records>> httpResponseCompletableFuture2 = supplyAsync(() -> {
            try {
                return apiRequest.getDatasets();
            } catch (ApiClientException e) {
                throw new RuntimeException();
            }
        });




    }

}