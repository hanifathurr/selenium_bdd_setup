package utilities.drivermanager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utilities.config.PropertyFileReader;

public class GetDriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final PropertyFileReader config = new PropertyFileReader();

    /**
     * Returns a WebDriver instance for the specified browser.
     *
     * @param browser the browser to use ("chrome", "firefox", "edge").
     * @return a WebDriver instance.
     */
    public static WebDriver getDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            WebDriver driver = initializeDriver(browser);
            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }

    /**
     * Initializes a WebDriver based on the specified browser.
     *
     * @param browser the browser to initialize.
     * @return the initialized WebDriver.
     */
    private static WebDriver initializeDriver(String browser) {
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(getChromeOptions());
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(getFirefoxOptions());
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver(getEdgeOptions());
                break;

            default:
                throw new IllegalArgumentException("Invalid browser name: " + browser);
        }
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Returns Chrome options for customization.
     *
     * @return ChromeOptions.
     */
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // Open browser in incognito mode
        options.addArguments("--disable-extensions"); // Disable extensions
        options.addArguments("--disable-infobars"); // Remove "Chrome is being controlled by automated software"
        options.addArguments("--disable-popup-blocking"); // Disable popup blocking
        options.addArguments("--disable-notifications"); // Disable browser notifications
        options.addArguments("--start-maximized"); // Start browser maximized

        if (Boolean.parseBoolean(config.getProperty("headless"))) {
            options.addArguments("--headless"); // Run in headless mode
            options.addArguments("--window-size=1920,1080"); // Set window size for headless mode
            options.addArguments("--disable-gpu"); // Disable GPU for compatibility with headless mode
        }
        return options;
    }

    /**
     * Returns Firefox options for customization.
     *
     * @return FirefoxOptions.
     */
    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--private"); // Open browser in private mode
        options.addPreference("dom.webnotifications.enabled", false); // Disable browser notifications
        options.addPreference("media.volume_scale", "0.0"); // Mute any audio in the browser
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf"); // Auto-download PDFs

        if (Boolean.parseBoolean(config.getProperty("headless"))) {
            options.addArguments("--headless"); // Run in headless mode
        }
        return options;
    }


    /**
     * Returns Edge options for customization.
     *
     * @return EdgeOptions.
     */
    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--inprivate"); // Open browser in private mode
        options.addArguments("--disable-extensions"); // Disable extensions
        options.addArguments("--disable-popup-blocking"); // Disable popup blocking
        options.addArguments("--disable-notifications"); // Disable browser notifications
        options.addArguments("--start-maximized"); // Start browser maximized

        if (Boolean.parseBoolean(config.getProperty("headless"))) {
            options.addArguments("--headless"); // Run in headless mode
            options.addArguments("--window-size=1920,1080"); // Set window size for headless mode
            options.addArguments("--disable-gpu");
        }
        return options;
    }

    /**
     * Quits the WebDriver instance for the current thread.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
