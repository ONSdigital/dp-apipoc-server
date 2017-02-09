package uk.gov.ons.api;

import com.mashape.unirest.http.Unirest;
import uk.gov.ons.api.request.ApiRequest;

public class ApiClient extends Unirest {
    public static ApiRequest set() {
        return new ApiRequest();
    }
}