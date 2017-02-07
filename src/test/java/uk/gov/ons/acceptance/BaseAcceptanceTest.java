package uk.gov.ons.acceptance;

import uk.gov.ons.BaseTest;

public abstract class BaseAcceptanceTest extends BaseTest {
    protected final String acceptanceScenarios = "scenarios";

    protected final String acceptanceUrl = "http://localhost:3000/api";
}
