package uk.gov.ons.api.common;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EncoderUnitTest {
    private final Encoder encoder = new Encoder();

    @Test
    public void shouldEncodePath() throws Exception {
        final String encodedUri = encoder.encodeUriComponent("ASHE: Table 6");
        assertThat(encodedUri, is("ASHE%3A+Table+6"));
    }
}