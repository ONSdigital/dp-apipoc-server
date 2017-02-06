package uk.gov.ons.hornet;

import com.google.gson.JsonParser;
import uk.gov.ons.hornet.reader.JsonReader;

public abstract class BaseTest {
    protected final String CONTENT_TYPE = "Content-Type";
    protected final String APPLICATION_JSON = "application/json";

    protected final JsonParser jsonParser = new JsonParser();

    protected final JsonReader jsonReader = new JsonReader(jsonParser);

    protected final String acceptanceScenarios = "scenarios";

    protected final String acceptanceUrl = "http://localhost:3000/api";
}
