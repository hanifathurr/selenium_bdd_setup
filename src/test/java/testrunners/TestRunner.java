package testrunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "json:target/cucumber-reports/CucumberTestReport.json",
                "html:target/cucumber-reports"
        },
        tags = "@regression" // Tag for filtering scenarios
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
