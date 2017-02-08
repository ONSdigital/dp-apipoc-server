package uk.gov.ons.acceptance.data;


import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import org.junit.runner.RunWith;
import uk.gov.ons.acceptance.BaseAcceptanceTest;

@Narrative(text = {
        "In order to obtain statistical data",
        "As an ONS API user",
        "I want to request specific data"
})
@RunWith(SerenityRunner.class)
public class WebsiteDataTest extends BaseAcceptanceTest {
}
