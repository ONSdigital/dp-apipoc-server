package uk.gov.ons.api.validation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import uk.gov.ons.api.exception.IllegalParameterException;

import static org.hamcrest.CoreMatchers.containsString;

public class ValidatorUnitTest {
    private final Validator validator = new Validator();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldThrowExceptionWhenNumberIsNegative() throws Exception {
        exception.expect(IllegalParameterException.class);
        exception.expectMessage(containsString("Negative parameters not allowed"));

        validator.validate(-1);
    }

}