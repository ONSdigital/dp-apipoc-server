package uk.gov.ons.api.validation;

import uk.gov.ons.api.exception.InvalidParameterException;

public class Validator {

    public void validate(final Integer value) {
        if(value < 0) {
            throw new InvalidParameterException();
        }
    }
}
