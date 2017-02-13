package uk.gov.ons.api.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import uk.gov.ons.api.common.Encoder;
import uk.gov.ons.api.common.InstantConverter;
import uk.gov.ons.api.exception.ApiClientException;
import uk.gov.ons.api.model.Data;
import uk.gov.ons.api.model.Record;
import uk.gov.ons.api.model.Records;
import uk.gov.ons.api.validation.Validator;

import java.io.UnsupportedEncodingException;
import java.time.Instant;

import static com.mashape.unirest.http.Unirest.get;
import static org.apache.http.util.TextUtils.isBlank;

public class ApiRequest {
    private Integer start = 0;
    private Integer limit = 5;
    private String dataset;
    private String timeseries;

    private static String BASE_URL;
    private static final String ONS_WEBSITE_URL = "https://www.ons.gov.uk.api";

    private static final Validator VALIDATOR = new Validator();
    private static final Encoder ENCODER = new Encoder();

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<Instant>() {
                }.getType(), new InstantConverter());

        Unirest.setObjectMapper(new ObjectMapper() {
            final Gson gson = gsonBuilder.create();

            @Override
            public <T> T readValue(String json, Class<T> aClass) {
                return gson.fromJson(json, aClass);
            }

            @Override
            public String writeValue(Object json) {
                return gson.toJson(json);
            }
        });

        final String testServerRootUrl = System.getenv("TEST_SERVER_ROOT_URL");
        BASE_URL = testServerRootUrl != null && !testServerRootUrl.trim().isEmpty() ? testServerRootUrl : ONS_WEBSITE_URL;
    }

    public ApiRequest startIndex(final Integer start) {
        VALIDATOR.validate(start);
        this.start = start;
        return this;
    }

    public ApiRequest itemsPerPage(final Integer limit) {
        VALIDATOR.validate(limit);
        this.limit = limit;
        return this;
    }

    public ApiRequest dataset(final String dataset) {
        this.dataset = dataset.trim().toLowerCase();
        return this;
    }

    public ApiRequest timeseries(final String timeseries) {
        this.timeseries = timeseries.trim().toLowerCase();
        return this;
    }

    public HttpResponse<Records> getDatasets() throws ApiClientException {
        try {
            final String uri = isBlank(timeseries) ?
                    "/dataset" + (isBlank(dataset) ? "" : "/" + ENCODER.encodeUriComponent(dataset)) :
                    "/timeseries/" + timeseries + "/dataset";

            return get(BASE_URL + uri)
                    .queryString("start", start)
                    .queryString("limit", limit)
                    .asObject(Records.class);
        } catch (UnirestException | UnsupportedEncodingException e) {
            throw new ApiClientException(e);
        }
    }

    public HttpResponse<Records> getTimeseries() throws ApiClientException {
        try {
            final String uri = isBlank(dataset) ?
                    "/timeseries" + (isBlank(timeseries) ? "" : "/" + ENCODER.encodeUriComponent(timeseries)) :
                    "/dataset/" + dataset + "/timeseries";

            return get(BASE_URL + uri)
                    .queryString("start", start)
                    .queryString("limit", limit)
                    .asObject(Records.class);
        } catch (UnirestException | UnsupportedEncodingException e) {
            throw new ApiClientException(e);
        }
    }

    public HttpResponse<Record> getDataset(final String id) throws ApiClientException {
        try {
            return get(BASE_URL + "/dataset/" + id.trim().toLowerCase()).asObject(Record.class);
        } catch (UnirestException e) {
            throw new ApiClientException(e);
        }
    }

    public HttpResponse<Record> getTimeseries(final String id) throws ApiClientException {
        try {
            VALIDATOR.validateParameter("dataset", dataset);

            final String uri = "/dataset/" + dataset + "/timeseries/" + id.trim().toLowerCase();

            return get(BASE_URL + uri).asObject(Record.class);
        } catch (UnirestException e) {
            throw new ApiClientException(e);
        }
    }

    public HttpResponse<Records> search(final String term) throws ApiClientException {
        try {
            return get(BASE_URL + "/search")
                    .queryString("q", term.trim().toLowerCase())
                    .queryString("start", start)
                    .queryString("limit", limit)
                    .asObject(Records.class);
        } catch (UnirestException e) {
            throw new ApiClientException(e);
        }
    }

    public HttpResponse<Data> getData() throws ApiClientException {
        try {
            VALIDATOR.validateParameter("dataset", dataset);
            VALIDATOR.validateParameter("timeseries", timeseries);

            final String uri = "/dataset/" + dataset + "/timeseries/" + timeseries + "/data";

            return get(BASE_URL + uri).asObject(Data.class);
        } catch (UnirestException e) {
            throw new ApiClientException(e);
        }
    }

}