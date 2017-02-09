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
import uk.gov.ons.api.model.Dataset;
import uk.gov.ons.api.model.Datasets;
import uk.gov.ons.api.model.Timeseries;
import uk.gov.ons.api.model.Timeserieses;
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
        this.dataset = dataset;
        return this;
    }

    public ApiRequest timeseries(final String timeseries) {
        this.timeseries = timeseries;
        return this;
    }

    public HttpResponse<Datasets> getDatasets() throws ApiClientException {
        try {
            final String uri = isBlank(timeseries) ?
                    "/dataset" + (isBlank(dataset) ? "" : "/" + ENCODER.encodeUriComponent(dataset.trim().toLowerCase())) :
                    "/timeseries/" + timeseries.trim().toLowerCase() + "/dataset";

            return get(BASE_URL + uri)
                    .queryString("start", start)
                    .queryString("limit", limit)
                    .asObject(Datasets.class);
        } catch (UnirestException | UnsupportedEncodingException e) {
            throw new ApiClientException(e);
        }
    }

    public HttpResponse<Timeserieses> getTimeseries() throws ApiClientException {
        try {
            final String uri = isBlank(dataset) ?
                    "/timeseries" + (isBlank(timeseries) ? "" : "/" + ENCODER.encodeUriComponent(timeseries.trim().toLowerCase())) :
                    "/dataset/" + dataset.trim().toLowerCase() + "/timeseries";

            return get(BASE_URL + uri)
                    .queryString("start", start)
                    .queryString("limit", limit)
                    .asObject(Timeserieses.class);
        } catch (UnirestException | UnsupportedEncodingException e) {
            throw new ApiClientException(e);
        }
    }

    public HttpResponse<Dataset> getDataset(final String id) throws ApiClientException {
        try {
            return get(BASE_URL + "/dataset/" + id.trim().toLowerCase()).asObject(Dataset.class);
        } catch (UnirestException e) {
            throw new ApiClientException(e);
        }
    }

    public HttpResponse<Timeseries> getTimeseries(final String id) throws ApiClientException {
        try {
            VALIDATOR.validateParameter("dataset", dataset);

            final String uri = "/dataset/" + dataset.trim().toLowerCase() + "/timeseries/" + id.trim().toLowerCase();

            return get(BASE_URL + uri).asObject(Timeseries.class);
        } catch (UnirestException e) {
            throw new ApiClientException(e);
        }
    }

}