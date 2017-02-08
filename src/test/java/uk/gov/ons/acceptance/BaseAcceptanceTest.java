package uk.gov.ons.acceptance;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import uk.gov.ons.BaseTest;

public abstract class BaseAcceptanceTest extends BaseTest {
    protected final String acceptanceScenarios = "acceptance";

    protected final String apiServerUrl = "http://localhost:3000/api";

    @ClassRule
    public static final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @BeforeClass
    public static void prepare() {
        environmentVariables.set("TEST_SERVER_ROOT_URL", "http://localhost:3000/api");
    }
}
