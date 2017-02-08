package uk.gov.ons.api.exception;

public class IllegalParameterException extends RuntimeException {
    public IllegalParameterException(final String message) {
        super(message);
    }
}