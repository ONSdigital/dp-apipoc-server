package uk.gov.ons.api.validation;

import uk.gov.ons.api.exception.IllegalParameterException;

public class Validator {

    public void validate(final Integer value) {
        if (value < 0) {
            throw new IllegalParameterException("Negative parameters not allowed");
        }
    }
}
