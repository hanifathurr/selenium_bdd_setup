package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import utilities.drivermanager.GetDriverManager;
import utilities.config.PropertyFileReader;

import java.time.Duration;

public class Hooks {
    private static WebDriver driver;

    @Before
    public void setUp (){
        PropertyFileReader properties = new PropertyFileReader();
        String homePageUrl = properties.getHomePageURL();
        int implicityWait = properties.getImplicityWait();
        int pageLoadTimeout = properties.getPageLoadTimeout();

        driver = GetDriverManager.getDriver(properties.getBrowser());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicityWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage().window().maximize();
        driver.get(homePageUrl);
    }

    @After
    public void tearDown(){
        GetDriverManager.quitDriver();
    }
    }

