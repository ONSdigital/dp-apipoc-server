package uk.gov.ons.api;

import uk.gov.ons.api.request.ApiRequest;

public class ApiClient {
    public static ApiRequest set() {
        return new ApiRequest();
    }
}