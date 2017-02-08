package uk.gov.ons.acceptance;

import uk.gov.ons.BaseTest;

public abstract class BaseAcceptanceTest extends BaseTest {
    protected final String acceptanceScenarios = "acceptance";

    protected final String apiServerUrl = "http://localhost:3000/api";
}
