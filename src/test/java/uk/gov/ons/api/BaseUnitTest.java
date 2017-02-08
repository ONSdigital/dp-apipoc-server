package uk.gov.ons.api;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import uk.gov.ons.BaseTest;

public class BaseUnitTest extends BaseTest {
    protected final String unitScenarios = "unit";

    @ClassRule
    public static final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @BeforeClass
    public static void prepare() {
        environmentVariables.set("TEST_SERVER_ROOT_URL", "http://localhost:4040/api");
    }

}
