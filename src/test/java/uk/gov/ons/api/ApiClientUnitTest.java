package uk.gov.ons.api;

import com.mashape.unirest.http.HttpResponse;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockserver.integration.ClientAndServer;
import uk.gov.ons.api.exception.IllegalParameterException;
import uk.gov.ons.api.model.Datasets;
import uk.gov.ons.api.model.Timeseries;
import uk.gov.ons.api.model.Timeserieses;
import uk.gov.ons.mockserver.FakeServer;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class ApiClientUnitTest extends BaseUnitTest {
    private ClientAndServer mockServer;
    private FakeServer fakeServer;

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

        final Datasets expectedDatasets = gson.fromJson(dsFile, Datasets.class);

        final HttpResponse<Datasets> response = ApiClient.set().getDatasets();

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

        final Datasets expectedDatasets = gson.fromJson(dsFile, Datasets.class);

        final HttpResponse<Datasets> response = ApiClient.set().startIndex(3).getDatasets();

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

        final Datasets expectedDatasets = gson.fromJson(dsFile, Datasets.class);

        final HttpResponse<Datasets> response = ApiClient.set().itemsPerPage(2).getDatasets();

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

        final Datasets expectedDatasets = gson.fromJson(dsFile, Datasets.class);

        final HttpResponse<Datasets> response = ApiClient.set().startIndex(3).itemsPerPage(4).getDatasets();

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

        final Timeserieses expectedTimeserieses = gson.fromJson(dsFile, Timeserieses.class);

        final HttpResponse<Timeserieses> response = ApiClient.set().getTimeseries();

        assertThat(response.getBody(), is(expectedTimeserieses));
    }

    @Ignore
    @Test
    public void shouldGetSpecificDataset() throws Exception {
    }

    @Test
    public void shouldGetSpecificTimeseries() throws Exception {
        final String dsFile = jsonReader.readFile(unitScenarios + "/metadata", "specific_timeseries.json");
        fakeServer.mockGetTimeseries(
                "fccs",
                dsFile
        );

        final Timeserieses expectedTimeseries = gson.fromJson(dsFile, Timeserieses.class);

        final HttpResponse<Timeserieses> response = ApiClient.set().timeseries("FCCS").getTimeseries();

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

        final Timeserieses expectedTimeserieses = gson.fromJson(dsFile, Timeserieses.class);

        final HttpResponse<Timeserieses> response = ApiClient.set().dataset("UKEA").getTimeseries();

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

        final Timeseries expectedTimeseries = gson.fromJson(dsFile, Timeseries.class);

        final HttpResponse<Timeseries> response = ApiClient.set().dataset("UKEA").getTimeseries("FCCS");

        assertThat(response.getBody(), is(expectedTimeseries));
    }

    @Test
    public void shouldRejectRequestWhenStartingIndexIsNegative() throws Exception {
        exception.expect(IllegalParameterException.class);
        exception.expectMessage(containsString("Negative parameters not allowed"));

        ApiClient.set().startIndex(-3).itemsPerPage(4).getDatasets();
    }

    @Test
    public void shouldRejectRequestWhenPageSizeIsNegative() throws Exception {
        exception.expect(IllegalParameterException.class);
        exception.expectMessage(containsString("Negative parameters not allowed"));

        ApiClient.set().startIndex(3).itemsPerPage(-4).getDatasets();
    }

    @Test
    public void shouldRejectRequestWhenBothStartingIndexAndPageSizeAreNegative() throws Exception {
        exception.expect(IllegalParameterException.class);
        exception.expectMessage(containsString("Negative parameters not allowed"));

        ApiClient.set().startIndex(-3).itemsPerPage(-4).getDatasets();
    }

}