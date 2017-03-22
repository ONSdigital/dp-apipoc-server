package uk.gov.ons.acceptance;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import uk.gov.ons.BaseTest;

public abstract class BaseAcceptanceTest extends BaseTest {
    protected final String acceptanceScenarios = "acceptance";

    protected final String apiServerUrl = "http://localhost:3000";

    @ClassRule
    public static final EnvironmentVariables ENVIRONMENT_VARIABLES = new EnvironmentVariables();

    @BeforeClass
    public static void prepare() {
        ENVIRONMENT_VARIABLES.set("API_SERVER_ROOT", "http://localhost:3000");
    }
}
