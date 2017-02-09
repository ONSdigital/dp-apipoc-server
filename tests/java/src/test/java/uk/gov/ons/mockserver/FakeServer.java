package uk.gov.ons.mockserver;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class FakeServer {
    private final MockServerClient mockServer;

    public FakeServer(MockServerClient mockServer) {
        this.mockServer = mockServer;
    }

    public void mockGetDatasets(Integer startIndex, Integer pageSize, String body) {
        mockGetJsonObject("/api/dataset", startIndex, pageSize, body);
    }

    public void mockGetDataset(String datasetId, Integer startIndex, Integer pageSize, String body) {
        mockGetJsonObject("/api/dataset/" + datasetId, startIndex, pageSize, body);
    }

    public void mockGetTimeseries(String timeseriesId, String body) {
        mockGetJsonObject("/api/timeseries/" + timeseriesId, body);
    }

    public void mockGetTimeseries(String datasetId, String timeseriesId, String body) {
        mockGetJsonObject("/api/dataset/" + datasetId + "/timeseries/" + timeseriesId, body);
    }

    public void mockGetTimeseries(Integer startIndex, Integer pageSize, String body) {
        mockGetJsonObject("/api/timeseries", startIndex, pageSize, body);
    }

    public void mockGetTimeseriesInDataset(String datasetId, Integer startIndex, Integer pageSize, String body) {
        mockGetJsonObject("/api/dataset/" + datasetId + "/timeseries", startIndex, pageSize, body);
    }

    private void mockGetJsonObject(String path, Integer startIndex, Integer pageSize, String body) {
        mockServer.when(
                        request()
                                .withMethod("GET")
                                .withPath(path)
                                .withQueryStringParameters(
                                        new Parameter("start", startIndex.toString()),
                                        new Parameter("limit", pageSize.toString())
                                )
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8")
                                )
                                .withBody(body)
                );
    }

    private void mockGetJsonObject(String path, String body) {
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath(path)
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8")
                                )
                                .withBody(body)
                );
    }

}
