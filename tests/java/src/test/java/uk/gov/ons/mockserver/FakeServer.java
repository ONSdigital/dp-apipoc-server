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
        mockGetJsonObject("/dataset", startIndex, pageSize, body);
    }

    public void mockGetDataset(String datasetId, Integer startIndex, Integer pageSize, String body) {
        mockGetJsonObject("/dataset/" + datasetId, startIndex, pageSize, body);
    }

    public void mockGetTimeseries(String timeseriesId, String body) {
        mockGetJsonObject("/timeseries/" + timeseriesId, body);
    }

    public void mockGetTimeseries(String datasetId, String timeseriesId, String body) {
        mockGetJsonObject("/dataset/" + datasetId + "/timeseries/" + timeseriesId, body);
    }

    public void mockGetTimeseries(Integer startIndex, Integer pageSize, String body) {
        mockGetJsonObject("/timeseries", startIndex, pageSize, body);
    }

    public void mockGetTimeseriesInDataset(String datasetId, Integer startIndex, Integer pageSize, String body) {
        mockGetJsonObject("/dataset/" + datasetId + "/timeseries", startIndex, pageSize, body);
    }

    public void mockSearch(String term, Integer startIndex, Integer pageSize, String body) {
        mockGetJsonObject("/search", term, startIndex, pageSize, body);
    }

    public void mockGetData(String datasetId, String timeseriesId, String body) {
        mockGetJsonObject("/dataset/" + datasetId + "/timeseries/" + timeseriesId + "/data", body);
    }

    private void mockGetJsonObject(String path, Integer startIndex, Integer pageSize, String body) {
        mockServer.when(
                        request()
                                .withMethod("GET")
                                .withHeader(
                                        new Header("Accepts", "application/json")
                                )
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
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Access-Control-Allow-Origin", "*")
                                )
                                .withBody(body)
                );
    }

    private void mockGetJsonObject(String path, String param, Integer startIndex, Integer pageSize, String body) {
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withHeader(
                                new Header("Accepts", "application/json")
                        )
                        .withPath(path)
                        .withQueryStringParameters(
                                new Parameter("q", param),
                                new Parameter("start", startIndex.toString()),
                                new Parameter("limit", pageSize.toString())
                        )
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Access-Control-Allow-Origin", "*")
                                )
                                .withBody(body)
                );
    }

    private void mockGetJsonObject(String path, String body) {
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withHeader(
                                new Header("Accepts", "application/json")
                        )
                        .withPath(path)
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Access-Control-Allow-Origin", "*")
                                )
                                .withBody(body)
                );
    }

}
