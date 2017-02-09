package uk.gov.ons.api.validation;

import uk.gov.ons.api.exception.ApiClientException;
import uk.gov.ons.api.exception.IllegalParameterException;

import static org.apache.http.util.TextUtils.isBlank;

public class Validator {

    public void validate(final Integer value) {
        if (value < 0) {
            throw new IllegalParameterException("Negative parameters not allowed");
        }
    }

    public void validateParameter(String paramName, String value) throws ApiClientException {
        if (isBlank(value)) {
            throw new ApiClientException(new Exception(paramName + " is not set"));
        }
    }
}
