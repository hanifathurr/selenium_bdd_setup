package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.pageobjectmanager.PageObjectManager;
import utilities.drivermanager.GetDriverManager;
import utilities.config.PropertyFileReader;

import java.time.Duration;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class); // Logger instance
    private static final PropertyFileReader config = new PropertyFileReader();
    private PageObjectManager pageObjectManager;

    @Before
    public void setUp() {
        try {
            logger.info("Initializing browser setup...");
            String homePageUrl = config.getHomePageURL();
            int implicitWait = config.getImplicitWait();
            int pageLoadTimeout = config.getPageLoadTimeout();

            WebDriver driver = GetDriverManager.getDriver(config.getBrowser());
            logger.info("Browser initialized: {}", config.getBrowser());

            // Set timeouts
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
            logger.info("Timeouts set: Implicit - {} seconds, Page Load - {} seconds", implicitWait, pageLoadTimeout);

            // Maximize window and navigate to the home page
            driver.manage().window().maximize();
            driver.get(homePageUrl);
            logger.info("Navigated to homepage: {}", homePageUrl);

        } catch (Exception e) {
            logger.error("Error during setup: {}", e.getMessage(), e);
            throw e; // Rethrow the exception to ensure test execution stops
        }
    }

    @After
    public void tearDown() {
        try {
            logger.info("Tearing down the browser...");
            GetDriverManager.quitDriver();
            logger.info("Browser closed successfully.");
        } catch (Exception e) {
            logger.error("Error during teardown: {}", e.getMessage(), e);
        }
    }
}
