package testrunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "json:src/test/java/testreports/json/CucumberTestReport.json",
                "html:src/test/java/testreports/CucumberTestReport.html"
        }
//        tags = "@regression" // Tag for filtering scenarios
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
