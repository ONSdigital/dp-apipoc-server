package uk.gov.ons.api.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Encoder {
    public String encodeUriComponent(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }
}