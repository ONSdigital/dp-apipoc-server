package uk.gov.ons.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import uk.gov.ons.api.converter.DateConverter;
import uk.gov.ons.api.converter.InstantConverter;
import uk.gov.ons.api.exception.ClientException;
import uk.gov.ons.api.model.Dataset;
import uk.gov.ons.api.model.Datasets;
import uk.gov.ons.api.model.Timeseries;
import uk.gov.ons.api.model.Timeserieses;
import uk.gov.ons.api.validation.Validator;

import java.time.Instant;
import java.util.Date;

import static com.mashape.unirest.http.Unirest.get;

public class Client {
    private static final String ONS_WEBSITE_URL = "https://www.ons.gov.uk.api";
    private static final Validator validator = new Validator();
    private static String BASE_URL;

    private Integer start = 0;
    private Integer limit = 5;
    private String dataset;
    private String timeseries;

    static {
        final GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<Instant>() {
                }.getType(), new InstantConverter())
                .registerTypeAdapter(new TypeToken<Date>() {
                }.getType(), new DateConverter())
                .serializeNulls();

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

    public Client startIndex(final Integer start) {
        validator.validate(start);
        this.start = start;
        return this;
    }

    public Client pageSize(final Integer limit) {
        validator.validate(limit);
        this.limit = limit;
        return this;
    }

    public Client dataset(final String dataset) {
        this.dataset = dataset;
        return this;
    }

    public Client timeseries(final String timeseries) {
        this.timeseries = timeseries;
        return this;
    }

    public HttpResponse<Datasets> getDatasets() throws ClientException {
        try {
            final String url = (timeseries != null && !timeseries.trim().isEmpty()) ?
                    BASE_URL + "/timeseries/" + timeseries.trim().toLowerCase() + "/dataset" :
                    BASE_URL + "/dataset";

            return get(url)
                    .queryString("start", start)
                    .queryString("limit", limit)
                    .asObject(Datasets.class);
        } catch (UnirestException e) {
            throw new ClientException(e);
        }
    }

    public HttpResponse<Timeserieses> getTimeseries() throws ClientException {
        try {
            final String url = (dataset != null && !dataset.trim().isEmpty()) ?
                BASE_URL + "/dataset/" + dataset.trim().toLowerCase() + "/timeseries" :
                BASE_URL + "/timeseries";

            return get(url)
                    .queryString("start", start)
                    .queryString("limit", limit)
                    .asObject(Timeserieses.class);
        } catch (UnirestException e) {
            throw new ClientException(e);
        }
    }

    public HttpResponse<Dataset> getDataset(final String id) throws ClientException {
        try {
            return get(BASE_URL + "/dataset/" + id.trim().toLowerCase())
                    .asObject(Dataset.class);
        } catch (UnirestException e) {
            throw new ClientException(e);
        }
    }

    public HttpResponse<Timeseries> getTimeseries(final String id) throws ClientException {
        try {
            final String url = (dataset != null && !dataset.trim().isEmpty()) ?
                    BASE_URL + "/dataset/" + dataset.trim().toLowerCase() + "/timeseries/" + id.trim().toLowerCase() :
                    BASE_URL + "/timeseries/" + id.trim().toLowerCase();


            return get(url)
                    .asObject(Timeseries.class);
        } catch (UnirestException e) {
            throw new ClientException(e);
        }
    }


}
