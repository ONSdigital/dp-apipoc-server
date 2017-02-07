package uk.gov.ons.api.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException() {
        super("Negative parameters not allowed");
    }
}
